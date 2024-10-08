package org.technicaltask.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class BookDtoResponse {

    private String title;

    private String author;

    private Integer amount;
}
