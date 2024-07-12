package com.lacture9.assignment3.utils;

import java.util.List;
import java.time.LocalDate;
import org.springframework.stereotype.Component;
import java.io.IOException;
import com.itextpdf.html2pdf.HtmlConverter;
import com.lacture9.assignment3.model.Employee;
import com.lacture9.assignment3.service.EmployeeService;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;
import java.io.ByteArrayOutputStream;
import java.util.Optional;

@Component
public class DocumentUtils {

    private final SpringTemplateEngine templateEngine;
    private final EmployeeService employeeService;

    public DocumentUtils(SpringTemplateEngine templateEngine, EmployeeService employeeService) {
        this.templateEngine = templateEngine;
        this.employeeService = employeeService;
    }

    public byte[] generateEmployeeInfo(List<Employee> listEmployees) throws IOException {
        Context context = new Context();
        String customerName = listEmployees.get(0).getName();
        Optional<Integer> maxSalary = employeeService.findMaxSalary();
        Optional<Integer> minSalary = employeeService.findMinSalary();
        Double aveSalary = employeeService.findAverageSalary();
        int totalRecord = listEmployees.size();
        LocalDate currentDate = LocalDate.now();
        Optional<String> nameHighSal = employeeService.findEmployeeWithHighestSalary();
        Optional<String> nameLowSal = employeeService.findEmployeeWithLowestSalary();
    
        context.setVariable("customer", customerName);
        context.setVariable("maxSalary", maxSalary.orElse(0));
        context.setVariable("minSalary", minSalary.orElse(0));
        context.setVariable("aveSalary", aveSalary);
        context.setVariable("employees", listEmployees);
        context.setVariable("record", totalRecord);
        context.setVariable("currentDate", currentDate);
        context.setVariable("nameHighSal", nameHighSal.orElse(""));
        context.setVariable("nameLowSal", nameLowSal.orElse(""));
    
        String processedHtml = templateEngine.process("data/pdf-template", context);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        HtmlConverter.convertToPdf(processedHtml, stream);
        stream.flush();
        return stream.toByteArray();
    }    
}