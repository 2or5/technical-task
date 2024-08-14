package org.technicaltask.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.technicaltask.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {

    @Query(nativeQuery = true, value = "SELECT COUNT(*) FROM member_books WHERE member_id = :memberId")
    Integer getCountBookByMemberId(Long memberId);
}
