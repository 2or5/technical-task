package org.technicaltask.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.technicaltask.convert.MemberConvertToDto;
import org.technicaltask.dto.MemberDto;
import org.technicaltask.entity.Member;
import org.technicaltask.exception.IdNotFoundException;
import org.technicaltask.repository.MemberRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final MemberConvertToDto memberConvertToDto;

    @Autowired
    public MemberService(MemberRepository memberRepository, MemberConvertToDto memberConvertToDto) {
        this.memberRepository = memberRepository;
        this.memberConvertToDto = memberConvertToDto;
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
}
