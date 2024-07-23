package com.lecture11.assignment1.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.lecture11.assignment1.model.Title;
import com.lecture11.assignment1.repository.TitleRepository;
import com.lecture11.assignment1.service.TitleService;
import jakarta.transaction.Transactional;

@Service
public class TitleServiceImpl implements TitleService{

    @Autowired
    TitleRepository titleRepository;

    @Override
    @Transactional
    public Title save(Title title) {
        titleRepository.save(title);
        return title;
    }

    @Override
    @Transactional
    public List<Title> findAll() {
        return titleRepository.findAll();
    }
}
