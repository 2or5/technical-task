package org.technicaltask.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BookDto {

    @NotBlank(message = "Title is required")
    @Size(min = 3, message = "Title must be at least 3 characters")
    @Pattern(regexp = "^[A-ZА-ЩЬЮЯ][a-zа-яієї]*.*$",
            message = "Title should start with a capital letter")
    private String title;

    @NotBlank(message = "Author is required")
    @Pattern(
            regexp = "^[A-ZА-ЩЬЮЯ][a-zа-яієї]+\\s[A-ZА-ЩЬЮЯ][a-zа-яієї]+$",
            message = "Author should contain two words with capitalized first letters")
    private String author;

    private Integer amount;
}
