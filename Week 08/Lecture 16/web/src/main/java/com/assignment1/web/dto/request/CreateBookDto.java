package com.assignment1.web.dto.request;

import java.time.LocalDate;

import lombok.Data;

@Data
public class CreateBookDto {
    public String title;
    public LocalDate publishedDate;
    public Integer writerId;
}
