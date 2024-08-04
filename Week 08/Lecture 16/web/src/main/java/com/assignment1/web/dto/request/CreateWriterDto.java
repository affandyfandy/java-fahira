package com.assignment1.web.dto.request;

import java.time.LocalDate;

import lombok.Data;

@Data
public class CreateWriterDto {
    public String name;
    public LocalDate dob;
    public String location;
}
