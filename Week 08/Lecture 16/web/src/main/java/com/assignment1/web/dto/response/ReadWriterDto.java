package com.assignment1.web.dto.response;

import java.time.LocalDate;

import lombok.Data;

@Data
public class ReadWriterDto {
    public Integer writerId;
    public String name;
    public LocalDate dob;
    public String location;
}
