package com.lecture11.assignment1.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lecture11.assignment1.model.Title;
import com.lecture11.assignment1.model.composite.TitleId;
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
    public Title findById(TitleId id) {
        return titleRepository.findById(id).get();
    }

    @Override
    @Transactional
    public void delete(TitleId id) {
        titleRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Title update(Title title, TitleId id) {
        var findTitle = titleRepository.findById(id);
        if (findTitle.isPresent()){
            Title theTitle = findTitle.get();
            theTitle.setToDate(title.getToDate());
            titleRepository.save(theTitle);
        }
        return findTitle.get();
    }
    
}
