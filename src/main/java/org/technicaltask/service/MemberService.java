package org.technicaltask.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.technicaltask.convert.MemberConvertToDto;
import org.technicaltask.dto.MemberDto;
import org.technicaltask.entity.Book;
import org.technicaltask.entity.Member;
import org.technicaltask.exception.IdNotFoundException;
import org.technicaltask.repository.BookRepository;
import org.technicaltask.repository.MemberRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final BookRepository bookRepository;
    private final MemberConvertToDto memberConvertToDto;
    private final Integer limitBorrowBooks;

    @Autowired
    public MemberService(MemberRepository memberRepository, BookRepository bookRepository, MemberConvertToDto memberConvertToDto, @Value("${limit.borrow.books}") Integer limitBorrowBooks) {
        this.memberRepository = memberRepository;
        this.bookRepository = bookRepository;
        this.memberConvertToDto = memberConvertToDto;
        this.limitBorrowBooks = limitBorrowBooks;
    }

    public List<MemberDto> getMembers() {
        List<Member> members = memberRepository.findAll();
        return members.stream()
                .map(memberConvertToDto::convertToDto)
                .collect(Collectors.toList());
    }

    public MemberDto getMemberById(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new IdNotFoundException("The member does not exist by this id: " + id));

        return MemberDto.builder()
                .name(member.getName())
                .membershipDate(member.getMembershipDate())
                .build();
    }

    public void saveMember(String name) {
        Member member = Member.builder()
                .name(name)
                .membershipDate(LocalDate.now())
                .build();

        memberRepository.save(member);
    }

    public void updateMember(String name, Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new IdNotFoundException("The member does not exist by this id: " + id));
        member.setName(name);

        memberRepository.save(member);
    }

    public void deleteMember(Long id) {
        memberRepository.deleteById(id);
    }

    public void borrowBook(Long memberId, Long bookId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IdNotFoundException("The member does not exist by this id: " + memberId));
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new IdNotFoundException("The book does not exist by this id: " + bookId));
        Integer numberBook = memberRepository.getCountBookByMemberId(memberId);

        if (book.getAmount() == 0 || numberBook >= limitBorrowBooks) {
            throw new RuntimeException("This book is currently unavailable or you have limit");
        } else {
            book.setAmount(book.getAmount() - 1);
            book.getMembers().add(member);
            member.getBooks().add(book);
            bookRepository.save(book);
            memberRepository.save(member);
        }
    }

    public void returnBorrowBook(Long memberId, Long bookId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IdNotFoundException("The member does not exist by this id: " + memberId));
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new IdNotFoundException("The book does not exist by this id: " + bookId));

        book.setAmount(book.getAmount() + 1);
        book.getMembers().remove(member);
        member.getBooks().remove(book);
        bookRepository.save(book);
        memberRepository.save(member);
    }
}
