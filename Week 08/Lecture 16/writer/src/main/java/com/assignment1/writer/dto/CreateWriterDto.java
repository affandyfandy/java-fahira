package com.assignment1.writer.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class CreateWriterDto {
    public String name;
    public LocalDate dob;
    public String location;
}
