package com.lecture11.assignment1.service;

import com.lecture11.assignment1.model.Title;
import com.lecture11.assignment1.model.composite.TitleId;

public interface TitleService {
    
    Title save(Title title);
    Title findById(TitleId id);
    void delete(TitleId id);
    Title update(Title title, TitleId id);
}
