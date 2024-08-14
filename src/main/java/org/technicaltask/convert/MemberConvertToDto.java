package org.technicaltask.convert;

import org.springframework.stereotype.Component;
import org.technicaltask.dto.MemberDto;
import org.technicaltask.entity.Member;

@Component
public class MemberConvertToDto {

    public MemberDto convertToDto(Member member) {
        return MemberDto.builder()
                .name(member.getName())
                .membershipDate(member.getMembershipDate())
                .build();
    }
}
