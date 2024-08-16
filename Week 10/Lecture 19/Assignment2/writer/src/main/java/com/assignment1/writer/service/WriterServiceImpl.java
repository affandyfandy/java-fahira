package com.assignment1.writer.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.assignment1.writer.data.entity.Writer;
import com.assignment1.writer.data.repository.WriterRepository;
import com.assignment1.writer.exception.ObjectNotFoundException;

@Service
public class WriterServiceImpl implements WriterService {

    private final WriterRepository writerRepository;

    public WriterServiceImpl(WriterRepository writerRepository){
        this.writerRepository = writerRepository;
    }

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
    public Writer findById(Integer id){
        return writerRepository.findById(id)
            .orElseThrow(() -> new ObjectNotFoundException(String.format("Writer with Id %s is not found", id)));
    }

    @Override
    public Writer update(Integer id, Writer data) {
        Writer findWriter = findById(id);
        
        findWriter.setDob(data.getDob() != null ? data.getDob() : findWriter.getDob());
        findWriter.setLocation(data.getLocation() != null ? data.getLocation() : findWriter.getLocation());
        findWriter.setName(data.getName() != null ? data.getName() : findWriter.getName());

        return writerRepository.save(findWriter);
    }
    
}
