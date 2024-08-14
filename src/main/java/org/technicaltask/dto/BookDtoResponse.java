package org.technicaltask.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class BookDtoResponse {

    private String bookName;

    private Long borrowedAmount;
}
