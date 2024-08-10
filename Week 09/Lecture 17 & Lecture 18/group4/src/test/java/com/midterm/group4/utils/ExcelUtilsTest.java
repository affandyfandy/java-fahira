package com.midterm.group4.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.midterm.group4.data.model.Customer;
import com.midterm.group4.data.model.Invoice;
import com.midterm.group4.data.model.OrderItem;
import com.midterm.group4.data.model.Product;

public class ExcelUtilsTest {
    @InjectMocks
    private ExcelUtils excelUtils;

    @Mock
    private Invoice invoice;

    @Mock
    private Customer customer;

    @Mock
    private OrderItem orderItem;

    @Mock
    private Product product;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        when(invoice.getInvoiceId()).thenReturn(UUID.randomUUID());
        when(invoice.getTotalAmount()).thenReturn(BigInteger.valueOf(100));
        when(invoice.getInvoiceDate()).thenReturn(LocalDate.now());
        when(invoice.getCreatedTime()).thenReturn(LocalDateTime.now());
        when(invoice.getUpdatedTime()).thenReturn(LocalDateTime.now());

        when(customer.getCustomerId()).thenReturn(UUID.randomUUID());
        when(customer.getFirstName()).thenReturn("John");
        when(customer.getLastName()).thenReturn("Doe");
        when(invoice.getCustomer()).thenReturn(customer);

        when(orderItem.getQuantity()).thenReturn(2);
        when(orderItem.getAmount()).thenReturn(BigInteger.valueOf(200));
        when(orderItem.getProduct()).thenReturn(product);

        when(product.getProductId()).thenReturn(UUID.randomUUID());
        when(product.getName()).thenReturn("Sample Product");
        when(product.getPrice()).thenReturn(BigInteger.valueOf(100));
        
        when(invoice.getListOrderItem()).thenReturn(Arrays.asList(orderItem));
    }

    @Test
    void exportInvoice_shouldExportDataToExcel() throws IOException {
        // Arrange
        List<Invoice> invoices = Arrays.asList(invoice);

        // Act
        ByteArrayOutputStream outputStream = excelUtils.exportInvoice(invoices);

        // Assert
        try (Workbook workbook = new XSSFWorkbook(new ByteArrayInputStream(outputStream.toByteArray()))) {
            Sheet sheet = workbook.getSheetAt(0);
            Row headerRow = sheet.getRow(0);
            assertEquals("Invoice ID", headerRow.getCell(0).getStringCellValue());
            assertEquals("Customer ID", headerRow.getCell(1).getStringCellValue());
            assertEquals("Customer Name", headerRow.getCell(2).getStringCellValue());
            assertEquals("Total Amount", headerRow.getCell(3).getStringCellValue());
            assertEquals("Products", headerRow.getCell(4).getStringCellValue());

            Row dataRow = sheet.getRow(1);
            assertEquals(invoice.getInvoiceId().toString(), dataRow.getCell(0).getStringCellValue());
            assertEquals(customer.getCustomerId().toString(), dataRow.getCell(1).getStringCellValue());
            assertEquals("John Doe", dataRow.getCell(2).getStringCellValue());
            assertEquals("100", dataRow.getCell(3).getStringCellValue());
            assertTrue(dataRow.getCell(4).getStringCellValue().contains("ID: " + orderItem.getProduct().getProductId().toString()));
        }
    }
}
