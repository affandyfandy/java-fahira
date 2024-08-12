package com.assignment1.writer.service;

import java.util.List;
import com.assignment1.writer.data.entity.Writer;

public interface WriterService {
    Writer save(Writer writer);
    List<Writer> findAll();
    Writer findById(Integer id);
}
