//package org.technicaltask.service;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.technicaltask.entity.Member;
//import org.technicaltask.exception.IdNotFoundException;
//import org.technicaltask.repository.MemberRepository;
//import java.time.LocalDate;
//import java.util.List;
//import java.util.Optional;
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertThrows;
//import static org.mockito.Mockito.*;
//
//@RunWith(SpringRunner.class)
//public class MemberServiceTest {
//
//    @Mock
//    private MemberRepository memberRepository;
//
//    @InjectMocks
//    private MemberService memberService;
//
//    @Test
//    public void getMembersTest() {
//        List<Member> members = List.of(
//                new Member(1L, "test name", LocalDate.now()),
//                new Member(2L, "test name 2", LocalDate.now()));
//
//        when(memberRepository.findAll()).thenReturn(members);
//        assertEquals(members, memberService.getMembers());
//        verify(memberRepository, times(1)).findAll();
//    }
//
//    @Test
//    public void getMemberByIdTest() {
//        var member = new Member(1L, "test name", LocalDate.now());
//
//        when(memberRepository.findById(1L)).thenReturn(Optional.of(member));
//        assertEquals(member, memberService.getMemberById(1L));
//        verify(memberRepository, times(1)).findById(1L);
//    }
//
//    @Test
//    public void getMemberByIdWithInvalidIdTest() {
//        assertThrows(IdNotFoundException.class,
//                () -> memberService.getMemberById(100L));
//        verify(memberRepository, times(1)).findById(100L);
//    }
//
//    @Test
//    public void saveMemberTest() {
//        var member = Member.builder()
//                .name("test name")
//                .membershipDate(LocalDate.now())
//                .build();
//
//        when(memberRepository.save(member)).thenReturn(any());
//        memberService.saveMember("test name");
//        verify(memberRepository, times(1)).save(member);
//    }
//
//    @Test
//    public void updateMemberTest() {
//        var member = new Member(1L, "Test name", LocalDate.now());
//
//        when(memberRepository.findById(1L)).thenReturn(Optional.of(member));
//        memberService.updateMember("Test name", 1L);
//        verify(memberRepository, times(1)).save(member);
//    }
//
//    @Test
//    public void deleteMemberTest() {
//        memberService.deleteMember(1L);
//        verify(memberRepository, times(1)).deleteById(1L);
//    }
//}
