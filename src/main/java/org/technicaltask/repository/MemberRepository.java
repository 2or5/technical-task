package org.technicaltask.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.technicaltask.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
