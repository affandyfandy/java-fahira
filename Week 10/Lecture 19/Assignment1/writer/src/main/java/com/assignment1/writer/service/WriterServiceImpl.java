package com.assignment1.writer.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.assignment1.writer.data.entity.Writer;
import com.assignment1.writer.data.repository.WriterRepository;

@Service
public class WriterServiceImpl implements WriterService {

    @Autowired
    private WriterRepository writerRepository;

    @Override
    @Transactional
    public Writer save(Writer writer) {
        return writerRepository.save(writer);
    }

    @Override
    @Transactional
    public List<Writer> findAll() {
        return writerRepository.findAll();
    }

    @Override
    @Transactional
    public Writer findById(Integer id){
        Optional<Writer> optWriter = writerRepository.findById(id);
        return optWriter.get();
    }
    
}
