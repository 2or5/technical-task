package org.technicaltask.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "books")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
