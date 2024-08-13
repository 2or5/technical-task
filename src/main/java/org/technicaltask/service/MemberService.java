package org.technicaltask.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.technicaltask.entity.Member;
import org.technicaltask.exception.IdNotFoundException;
import org.technicaltask.repository.MemberRepository;
import java.time.LocalDate;
import java.util.List;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public List<Member> getMembers() {
        return memberRepository.findAll();
    }

    public Member getMemberById(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new IdNotFoundException("The member does not exist by this id: " + id));
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
