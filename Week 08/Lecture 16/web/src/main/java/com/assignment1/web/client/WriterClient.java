package com.assignment1.web.client;

import java.util.concurrent.locks.ReadWriteLock;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.assignment1.web.dto.request.CreateBookDto;
import com.assignment1.web.dto.request.CreateWriterDto;
import com.assignment1.web.dto.response.ReadBookDto;
import com.assignment1.web.dto.response.ReadWriterDto;

@FeignClient(name = "writerClient", url = "${writer.api.url}")
public interface WriterClient {

    @PostMapping
    ReadWriterDto save(@RequestBody CreateWriterDto dto);
}
