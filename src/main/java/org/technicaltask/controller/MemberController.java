package org.technicaltask.controller;

import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(summary = "Get list of members",
            description = "This endpoint allows you to get all members.")
    @GetMapping
    public List<MemberDto> getMembers() {
        return memberService.getMembers();
    }

    @Operation(summary = "Get member by id",
            description = "This endpoint allows you to get member by id.")
    @GetMapping("/{id}")
    public MemberDto getMemberById(@PathVariable Long id) {
        return memberService.getMemberById(id);
    }

    @Operation(summary = "Save member",
            description = "This endpoint allows you to save member.")
    @PostMapping
    public void saveMember(@RequestParam String name) {
        memberService.saveMember(name);
    }

    @Operation(summary = "Edit member",
            description = "This endpoint allows you to edit member by id.")
    @PutMapping("/{id}")
    public void updateMember(@RequestParam String name, @PathVariable Long id) {
        memberService.updateMember(name, id);
    }

    @Operation(summary = "Delete member",
            description = "This endpoint allows you to delete member by id.")
    @DeleteMapping("/{id}")
    public void deleteMember(@PathVariable Long id) {
        memberService.deleteMember(id);
    }

    @Operation(summary = "Borrow book",
            description = "This endpoint allows you to borrow book.")
    @PostMapping("/borrowBook")
    public void borrowBook(@RequestParam Long memberId, @RequestParam Long bookId) {
        memberService.borrowBook(memberId, bookId);
    }

    @Operation(summary = "Return borrowed book",
            description = "This endpoint allows you to return borrowed book.")
    @PostMapping("/returnBorrowBook")
    public void returnBorrowBook(@RequestParam Long memberId, @RequestParam Long bookId) {
        memberService.returnBorrowBook(memberId, bookId);
    }
}
