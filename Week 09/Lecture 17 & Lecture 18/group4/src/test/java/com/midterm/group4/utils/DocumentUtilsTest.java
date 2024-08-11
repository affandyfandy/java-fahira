package com.midterm.group4.utils;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

import java.math.BigInteger;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import java.util.Collections;
import org.mockito.MockitoAnnotations;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.context.Context;

import com.itextpdf.html2pdf.HtmlConverter;
import java.io.IOException;
import com.midterm.group4.data.model.Customer;
import com.midterm.group4.data.model.Invoice;
import com.midterm.group4.data.model.OrderItem;
import com.midterm.group4.data.model.Product;

import java.io.ByteArrayOutputStream;

public class DocumentUtilsTest {
    
    @Mock
    private SpringTemplateEngine templateEngine;

    @InjectMocks
    private DocumentUtils documentUtils;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }
}
