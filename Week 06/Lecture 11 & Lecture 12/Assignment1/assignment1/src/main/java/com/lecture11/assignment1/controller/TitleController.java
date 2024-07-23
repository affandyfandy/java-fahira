package com.lecture11.assignment1.controller;

import org.springframework.web.bind.annotation.RestController;

import com.lecture11.assignment1.dto.TitleMapper;
import com.lecture11.assignment1.dto.response.TitleDto;
import com.lecture11.assignment1.model.Title;
import com.lecture11.assignment1.service.EmployeeService;
import com.lecture11.assignment1.service.TitleService;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping("/api/v1/title")
public class TitleController {

    @Autowired
    private TitleService titleService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private TitleMapper titleMapper;

    @PostMapping
    public ResponseEntity<TitleDto> saveTitle(@RequestBody TitleDto titleDto){
        Title title = titleMapper.toEntity(titleDto);
        title.setEmployee(employeeService.findById(titleDto.getEmployeeNo()));
        Title newTitle = titleService.save(title);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(titleMapper.toDto(newTitle));
    }

    @GetMapping
    public ResponseEntity<List<TitleDto>> getAllTitle() {
        List<TitleDto> listTitle = titleMapper.toListDto(titleService.findAll());
        return ResponseEntity.status(HttpStatus.OK).body(listTitle);
    }
}
    

