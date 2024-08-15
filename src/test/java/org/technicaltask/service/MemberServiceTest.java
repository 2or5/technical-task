package org.technicaltask.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.technicaltask.convert.MemberConvertToDto;
import org.technicaltask.dto.MemberDto;
import org.technicaltask.entity.Book;
import org.technicaltask.entity.Member;
import org.technicaltask.exception.IdNotFoundException;
import org.technicaltask.repository.BookRepository;
import org.technicaltask.repository.MemberRepository;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@TestPropertySource(locations = "classpath:application-test.properties")
public class MemberServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private MemberConvertToDto memberConvertToDto;

    @Mock
    private BookRepository bookRepository;

    private MemberService memberService;


    @Value("${limit.borrow.books}")
    private Integer limitBorrowBooks;

    @Before
    public void setUp() {
        limitBorrowBooks = 5; // або використовуйте @Value("${limit.borrow.books}")
        memberService = new MemberService(memberRepository, bookRepository, memberConvertToDto, limitBorrowBooks);
    }

    @Test
    public void getMembersTest() {
        List<Member> members = List.of(
                new Member(2L, "test name 2", LocalDate.now(), new ArrayList<>()));
        List<MemberDto> memberDtoList = List.of(
                MemberDto.builder().name("test name 2").membershipDate(LocalDate.now()).build());

        when(memberRepository.findAll()).thenReturn(members);
        when(memberConvertToDto.convertToDto(members.get(0))).thenReturn(memberDtoList.get(0));

        List<MemberDto> result = memberService.getMembers();

        assertEquals(memberDtoList, result);
        verify(memberRepository, times(1)).findAll();
        verify(memberConvertToDto, times(1)).convertToDto(members.get(0));
    }

    @Test
    public void getMemberByIdTest() {
        var memberDto = MemberDto.builder()
                .name("test name")
                .membershipDate(LocalDate.now())
                .build();
        var member = new Member(1L, "test name", LocalDate.now(), new ArrayList<>());

        when(memberRepository.findById(1L)).thenReturn(Optional.of(member));
        assertEquals(memberDto, memberService.getMemberById(1L));
        verify(memberRepository, times(1)).findById(1L);
    }

    @Test
    public void getMemberByIdWithInvalidIdTest() {
        assertThrows(IdNotFoundException.class,
                () -> memberService.getMemberById(100L));
        verify(memberRepository, times(1)).findById(100L);
    }

    @Test
    public void saveMemberTest() {
        var member = Member.builder()
                .name("test name")
                .membershipDate(LocalDate.now())
                .build();

        when(memberRepository.save(member)).thenReturn(any());
        memberService.saveMember("test name");
        verify(memberRepository, times(1)).save(member);
    }

    @Test
    public void updateMemberTest() {
        var member = new Member(1L, "Test name", LocalDate.now(), new ArrayList<>());

        when(memberRepository.findById(1L)).thenReturn(Optional.of(member));
        memberService.updateMember("Test name", 1L);
        verify(memberRepository, times(1)).save(member);
    }

    @Test
    public void deleteMemberTest() {
        var member = new Member(1L, "Test", LocalDate.now(), new ArrayList<>());

        when(memberRepository.findById(1L)).thenReturn(Optional.of(member));
        memberService.deleteMember(1L);
        verify(memberRepository, times(1)).findById(1L);
        verify(memberRepository, times(1)).deleteById(1L);
    }

    @Test
    public void deleteMemberWithBookTest() {
        var book = new Book();
        var books = new ArrayList<Book>();
        books.add(book);
        var member = new Member(1L, "Test", LocalDate.now(), books);

        when(memberRepository.findById(1L)).thenReturn(Optional.of(member));
        assertThrows(IllegalStateException.class, () -> memberService.deleteMember(1L));
        verify(memberRepository, times(1)).findById(1L);
        verify(memberRepository, never()).deleteById(1L);
    }

    @Test
    public void borrowBookTest() {
        var member = new Member(1L, "Test name", LocalDate.now(), new ArrayList<>());
        var book = new Book(1L, "Test", "Test Test", 2, new ArrayList<>());

        when(memberRepository.findById(1L)).thenReturn(Optional.of(member));
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(memberRepository.getCountBookByMemberId(1L)).thenReturn(1);

        memberService.borrowBook(1L, 1L);

        verify(memberRepository, times(1)).save(member);
        verify(bookRepository, times(1)).save(book);
    }

    @Test
    public void borrowBookWithLimitTest() {
        var member = new Member(1L, "Test name", LocalDate.now(), new ArrayList<>());
        var book = new Book(1L, "Test", "Test Test", 2, new ArrayList<>());

        when(memberRepository.findById(1L)).thenReturn(Optional.of(member));
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(memberRepository.getCountBookByMemberId(1L)).thenReturn(10);

        assertThrows(RuntimeException.class,
                () -> memberService.borrowBook(1L, 1L));
    }

    @Test
    public void returnBorrowBookTest() {
        var member = new Member(1L, "Test name", LocalDate.now(), new ArrayList<>());
        var book = new Book(1L, "Test", "Test Test", 2, new ArrayList<>());

        when(memberRepository.findById(1L)).thenReturn(Optional.of(member));
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(memberRepository.getCountBookByMemberId(1L)).thenReturn(1);

        memberService.returnBorrowBook(1L, 1L);

        verify(memberRepository, times(1)).save(member);
        verify(bookRepository, times(1)).save(book);
    }
}
