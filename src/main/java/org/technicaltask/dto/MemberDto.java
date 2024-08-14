package org.technicaltask.dto;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDate;

@Data
@Builder
public class MemberDto {

    private String name;

    private LocalDate membershipDate;
}
