package com.assignment1.web.dto.response;

import lombok.Data;

@Data
public class ReadBookDto {
    public Integer bookId;
    public String title;
    public String publishedDate;
    public Integer writerId;
}
