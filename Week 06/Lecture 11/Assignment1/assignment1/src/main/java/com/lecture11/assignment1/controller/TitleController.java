package com.lecture11.assignment1.controller;

import org.springframework.web.bind.annotation.RestController;

import com.lecture11.assignment1.model.Title;
import com.lecture11.assignment1.model.composite.TitleId;
import com.lecture11.assignment1.service.TitleService;
import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping("/api/v1/title")
public class TitleController {

    @Autowired
    private TitleService titleService;

    @PostMapping
    public ResponseEntity<Title> saveTitle(@RequestBody Title title){
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(titleService.save(title));
    }

    @GetMapping("/employeeId={employeeId}&fromDate={date}&title={title}")
    public ResponseEntity<Title> getTitleById(@PathVariable int employeeId, @PathVariable String date, @PathVariable String title) {
        LocalDate fromDate = LocalDate.parse(date);
        TitleId id = new TitleId(employeeId, title, fromDate);
        return ResponseEntity.status(HttpStatus.OK).body(titleService.findById(id));
    }

    @PutMapping("/employeeId={employeeId}&fromDate={date}&title={title}")
    public ResponseEntity<Title> updateTitle(@PathVariable int employeeId, @PathVariable String date,
                                            @PathVariable String title, @RequestBody Title newTitle) {
        LocalDate fromDate = LocalDate.parse(date);
        TitleId id = new TitleId(employeeId, title, fromDate);
        return ResponseEntity.status(HttpStatus.OK).body(titleService.update(newTitle, id));
    }

    @DeleteMapping("/employeeId={employeeId}&fromDate={date}&title={title}")
    public ResponseEntity<String> deleteTitle(@PathVariable int employeeId, @PathVariable String date, @PathVariable String title) {
        LocalDate fromDate = LocalDate.parse(date);
        TitleId id = new TitleId(employeeId, title, fromDate);
        titleService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body("Delete successful!");
    }
}
    

