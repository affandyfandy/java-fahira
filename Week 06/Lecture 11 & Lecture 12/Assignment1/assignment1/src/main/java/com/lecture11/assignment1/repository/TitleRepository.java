package com.lecture11.assignment1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lecture11.assignment1.model.Title;
import com.lecture11.assignment1.model.composite.TitleId;

@Repository
public interface TitleRepository extends JpaRepository<Title, TitleId>{
    
}
