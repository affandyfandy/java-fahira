package com.assignment1.web.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.assignment1.web.dto.request.CreateBookDto;
import com.assignment1.web.dto.request.CreateWriterDto;
import com.assignment1.web.dto.response.ReadBookDto;
import com.assignment1.web.dto.response.ReadWriterDto;

@FeignClient(name = "bookClient", url = "${book.api.url}")
public interface BookClient {

    @GetMapping("/{id}")
    ReadBookDto findByBookId(@PathVariable("id") Integer id);
}
