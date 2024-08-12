package com.assignment1.book.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class CreateBookDto {
    public String title;
    public LocalDate publishedDate;
    public String writerId;
}
