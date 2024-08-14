package org.technicaltask.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.technicaltask.dto.MemberDto;
import org.technicaltask.service.MemberService;
import java.util.List;

@RestController
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping
    public List<MemberDto> getMembers() {
        return memberService.getMembers();
    }

    @GetMapping("/{id}")
    public MemberDto getMemberById(@PathVariable Long id) {
        return memberService.getMemberById(id);
    }

    @PostMapping
    public void saveMember(@RequestParam String name) {
        memberService.saveMember(name);
    }

    @PutMapping("/{id}")
    public void updateMember(@RequestParam String name, @PathVariable Long id) {
        memberService.updateMember(name, id);
    }

    @DeleteMapping("/{id}")
    public void deleteMember(@PathVariable Long id) {
        memberService.deleteMember(id);
    }

    @PostMapping("/borrowBook")
    public void borrowBook(@RequestParam Long memberId, @RequestParam Long bookId) {
        memberService.borrowBook(memberId, bookId);
    }

    @PostMapping("/returnBorrowBook")
    public void returnBorrowBook(@RequestParam Long memberId, @RequestParam Long bookId) {
        memberService.returnBorrowBook(memberId, bookId);
    }
}
