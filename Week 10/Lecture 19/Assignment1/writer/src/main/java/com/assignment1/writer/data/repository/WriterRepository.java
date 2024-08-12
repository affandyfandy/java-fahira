package com.assignment1.writer.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.assignment1.writer.data.entity.Writer;

public interface WriterRepository extends JpaRepository<Writer, Integer> {
    
}
