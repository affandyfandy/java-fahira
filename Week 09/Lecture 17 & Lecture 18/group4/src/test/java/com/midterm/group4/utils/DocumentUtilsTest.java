package com.midterm.group4.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.io.IOException;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import com.midterm.group4.data.model.Invoice;

class DocumentUtilsTest {

    @InjectMocks
    private DocumentUtils documentUtils;

    @Mock
    private SpringTemplateEngine templateEngine;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Mock the SpringTemplateEngine processing
        when(templateEngine.process(anyString(), any(Context.class)))
            .thenReturn("<html><body><p>Mocked PDF Content</p></body></html>");
    }

    @Test
    void generateByteInvoice_shouldReturnPdfByteArray() throws IOException {
        // Arrange
        Invoice invoice = new Invoice();
        invoice.setInvoiceId(UUID.randomUUID());
        invoice.setTotalAmount(BigInteger.valueOf(100));
        invoice.setInvoiceDate(LocalDate.now());
        invoice.setCreatedTime(LocalDateTime.now());
        invoice.setUpdatedTime(LocalDateTime.now());

        // Act
        byte[] pdfBytes = documentUtils.generateByteInvoice(invoice);

        // Assert
        assertNotNull(pdfBytes);
        assertTrue(pdfBytes.length > 0);
    }
}
