package com.lecture11.assignment1.service;

import java.util.List;
import com.lecture11.assignment1.model.Title;

public interface TitleService {
    
    Title save(Title title);
    List<Title> findAll();
}
