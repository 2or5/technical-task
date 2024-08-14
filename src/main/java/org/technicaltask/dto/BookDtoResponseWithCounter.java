package org.technicaltask.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class BookDtoResponseWithCounter {

    private String title;

    private Long amount;
}
