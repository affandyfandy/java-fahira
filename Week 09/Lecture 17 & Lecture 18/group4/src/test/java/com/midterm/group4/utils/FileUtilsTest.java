package com.midterm.group4.utils;

import java.math.BigInteger;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import com.midterm.group4.data.model.Product;

public class FileUtilsTest {

    private static final MultipartFile createMockMultipartFile() throws IOException {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Products");

            Row header = sheet.createRow(0);
            header.createCell(0).setCellValue("Name");
            header.createCell(1).setCellValue("Price");
            header.createCell(2).setCellValue("Quantity");
            header.createCell(3).setCellValue("Active");

            Row row1 = sheet.createRow(1);
            row1.createCell(0).setCellValue("Book");
            row1.createCell(1).setCellValue(10.5);
            row1.createCell(2).setCellValue(20);
            row1.createCell(3).setCellValue(true);

            Row row2 = sheet.createRow(2);
            row2.createCell(0).setCellValue("Computer");
            row2.createCell(1).setCellValue(20.0);
            row2.createCell(2).setCellValue(30);
            row2.createCell(3).setCellValue(false);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            workbook.write(baos);
            return new MockMultipartFile("products.xlsx", "products.xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", new ByteArrayInputStream(baos.toByteArray()));
        }
    }

    
    @Test
    @DisplayName("Test 1: Read product from excel file")
    public void testReadProductFromExcel_validFile() throws IOException {
        MultipartFile file = createMockMultipartFile();

        List<Product> products = FileUtils.readProductFromExcel(file);

        assertEquals(2, products.size());

        Product product1 = products.get(0);
        assertEquals("Book", product1.getName());
        assertEquals(BigInteger.valueOf(10), product1.getPrice());
        assertEquals(20, product1.getQuantity());
        assertTrue(product1.isActive());

        Product product2 = products.get(1);
        assertEquals("Computer", product2.getName());
        assertEquals(BigInteger.valueOf(20), product2.getPrice());
        assertEquals(30, product2.getQuantity());
        assertFalse(product2.isActive());
    }

    @Test
    @DisplayName("Test 2: Read product from invalid excel file")
    public void testReadProductFromExcel_invalidData() throws IOException {
        String content = "Name,Price,Quantity,Active\n" +
                         "Product1,invalid,20,true\n";
        MultipartFile file = new MockMultipartFile("products.xlsx", new ByteArrayInputStream(content.getBytes()));

        IOException thrown = assertThrows(IOException.class, () -> {
            FileUtils.readProductFromExcel(file);
        });

        assertEquals("Error reading Excel file", thrown.getMessage());
    }

}
