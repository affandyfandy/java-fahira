package com.midterm.group4.controller;

import java.util.Arrays;
import java.util.UUID;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.HashMap;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.math.BigInteger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.midterm.group4.data.model.Customer;
import com.midterm.group4.data.model.Invoice;
import com.midterm.group4.data.model.Product;
import com.midterm.group4.data.model.OrderItem;
import com.midterm.group4.dto.CustomerMapperImpl;
import com.midterm.group4.dto.InvoiceMapperImpl;
import com.midterm.group4.dto.OrderItemMapperImpl;
import com.midterm.group4.exception.ObjectNotFoundException;
import com.midterm.group4.service.impl.InvoiceServiceImpl;

@WebMvcTest(controllers = InvoiceController.class)
@Import({InvoiceMapperImpl.class, CustomerMapperImpl.class, OrderItemMapperImpl.class})
public class InvoiceControllerTest {
    
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private InvoiceServiceImpl invoiceService;

    private Invoice invoice;

    @BeforeEach
    public void setup(){
        UUID invoiceId = UUID.randomUUID();
        invoice = new Invoice();
        invoice.setInvoiceId(invoiceId);
        invoice.setCreatedTime(LocalDateTime.now());
        invoice.setUpdatedTime(LocalDateTime.now());
        invoice.setListOrderItem(Arrays.asList(
            new OrderItem() {{
                setOrderItemId(UUID.randomUUID());
                setQuantity(1);
                setAmount(BigInteger.valueOf(100));
            }}
        ));
    }

    @Test
    @DisplayName("Test 1: Retrieve all invoices")
    public void testGetAllInvoices() throws Exception{
        Invoice invoice1 = new Invoice();
        Invoice invoice2 = new Invoice();

        Page<Invoice> pageInvoice = new PageImpl<>(Arrays.asList(invoice1, invoice2), PageRequest.of(0, 10), 2);

        Mockito.when(invoiceService.findAllSorted(Mockito.any(Integer.class), Mockito.any(Integer.class),
            Mockito.any(String.class), Mockito.any(String.class))).thenReturn(pageInvoice);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/invoice")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.length()").value(2));
    }

    @Test
    @DisplayName("Test 2: Filter invoices")
    public void testFilterInvoices() throws Exception{
        Customer cust = new Customer();
        cust.setCustomerId(UUID.randomUUID());

        Invoice invoice1 = new Invoice();
        invoice1.setInvoiceId(UUID.randomUUID());
        invoice1.setCustomer(cust);

        Invoice invoice2 = new Invoice();
        invoice2.setInvoiceId(UUID.randomUUID());
        invoice1.setCustomer(cust);

        Page<Invoice> pageInvoice = new PageImpl<>(Arrays.asList(invoice1, invoice2), PageRequest.of(0, 10), 2);

        Mockito.when(invoiceService.findAllFiltered(Mockito.any(Integer.class), Mockito.any(Integer.class),
            Mockito.any(String.class), Mockito.any(String.class), Mockito.any(UUID.class),
            Mockito.any(String.class), Mockito.any(String.class))).thenReturn(pageInvoice);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/invoice/filter")
            .param("customerId",cust.getCustomerId().toString()))
            .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("Test 3: Update invoice with valid data")
    public void testUpdateInvoiceValid() throws Exception {
        Mockito.when(invoiceService.update(Mockito.any(UUID.class), Mockito.any(Invoice.class), Mockito.anyList()))
                .thenReturn(invoice);

        String jsonPayload = "{"
                + "\"listOrderItem\": [{"
                + "\"orderItemId\": \"" + invoice.getListOrderItem().get(0).getOrderItemId() + "\","
                + "\"quantity\": 2,"
                + "\"amount\": 200"
                + "}]"
                + "}";

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/invoice/{id}", invoice.getInvoiceId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonPayload))
                .andExpect(MockMvcResultMatchers.status().isAccepted())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.invoiceId").value(invoice.getInvoiceId().toString()));
    }

    @Test
    @DisplayName("Test 4: Update invoice with invalid data")
    public void testUpdateInvoiceThrowException() throws Exception {
        Mockito.when(invoiceService.update(Mockito.any(UUID.class), Mockito.any(Invoice.class), Mockito.anyList()))
                .thenThrow(new ObjectNotFoundException("Invoice not found"));

        String jsonPayload = "{"
                + "\"listOrderItem\": [{"
                + "\"orderItemId\": \"" + UUID.randomUUID() + "\","
                + "\"quantity\": 2,"
                + "\"amount\": 200"
                + "}]"
                + "}";

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/invoice/{id}", invoice.getInvoiceId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonPayload))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @DisplayName("Test 5: Get report with date parameter")
    public void testGetReportByInvoiceDate() throws Exception {
        LocalDate date = LocalDate.of(2024, 8, 1);

        Map<String, Object> report = new HashMap<>();
        report.put("reportData", "someData");

        Mockito.when(invoiceService.generateReport(Mockito.eq(date), Mockito.anyInt(), Mockito.anyInt()))
               .thenReturn(report);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/invoice/report")
                .param("date", date.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.reportData").value("someData"));
    }

    @Test
    @DisplayName("Test 8: Search invoices by customer name - Success")
    public void testSearchInvoicesByCustomerName() throws Exception {
        String customerName = "Mario Maurer";
        Invoice invoice1 = new Invoice();
        Invoice invoice2 = new Invoice();
        List<Invoice> invoices = Arrays.asList(invoice1, invoice2);
        Page<Invoice> pageInvoice = new PageImpl<>(invoices, PageRequest.of(0, 10), 2);

        Mockito.when(invoiceService.findAllByCustomerName(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString()))
               .thenReturn(pageInvoice);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/invoice/search")
                .param("pageNo", "0")
                .param("pageSize", "10")
                .param("sortOrder", "asc")
                .param("sortBy", "totalAmount")
                .param("customerName", customerName)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.length()").value(2));
    }

    @Test
    @DisplayName("Test 9: Search invoices by customer name - Pagination")
    public void testSearchInvoicesByCustomerName_pagination() throws Exception {
        String customerName = "Mario Maurer";
        Invoice invoice = new Invoice();
        List<Invoice> invoices = Arrays.asList(invoice);
        Page<Invoice> pageInvoice = new PageImpl<>(invoices, PageRequest.of(1, 5), 1);

        Mockito.when(invoiceService.findAllByCustomerName(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString()))
               .thenReturn(pageInvoice);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/invoice/search")
                .param("pageNo", "1")
                .param("pageSize", "5")
                .param("sortOrder", "asc")
                .param("sortBy", "totalAmount")
                .param("customerName", customerName)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.length()").value(1));
    }

    @Test
    @DisplayName("Test 10: Create new invoice")
    public void testCreateNewInvoice() throws Exception {
        Invoice newInvoice = new Invoice();
        newInvoice.setInvoiceId(UUID.randomUUID());
        newInvoice.setCreatedTime(LocalDateTime.now());
        newInvoice.setUpdatedTime(LocalDateTime.now());
        newInvoice.setListOrderItem(Arrays.asList(
            new OrderItem() {{
                setOrderItemId(UUID.randomUUID());
                setQuantity(2);
                setAmount(BigInteger.valueOf(200));
            }}
        ));
        
        Mockito.when(invoiceService.createInvoice(Mockito.any(Invoice.class), Mockito.anyList()))
               .thenReturn(newInvoice);

        String jsonPayload = "{"
                + "\"listOrderItem\": [{"
                + "\"orderItemId\": \"" + newInvoice.getListOrderItem().get(0).getOrderItemId() + "\","
                + "\"quantity\": 2,"
                + "\"amount\": 200"
                + "}]"
                + "}";

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/invoice")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonPayload))
                .andExpect(MockMvcResultMatchers.status().isAccepted())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.invoiceId").value(newInvoice.getInvoiceId().toString()));
    }

    @Test
    @DisplayName("Test 11: Create new invoice with invalid data")
    public void testCreateNewInvoice_invalidData() throws Exception {
        Mockito.when(invoiceService.createInvoice(Mockito.any(Invoice.class), Mockito.anyList()))
               .thenThrow(new ObjectNotFoundException("Product not found"));

        String jsonPayload = "{"
                + "\"listOrderItem\": [{"
                + "\"orderItemId\": \"" + UUID.randomUUID() + "\","
                + "\"quantity\": 2,"
                + "\"amount\": 200"
                + "}]"
                + "}";

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/invoice")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonPayload))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @DisplayName("Test 12: Get invoice by existing invoice Id")
    public void testGetInvoiceByValidId() throws Exception{
        Mockito.when(invoiceService.findById(Mockito.any(UUID.class))).thenReturn(invoice);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/invoice/" + invoice.getInvoiceId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.invoiceId").value(invoice.getInvoiceId().toString()));
    }

    @Test
    @DisplayName("Test 13: Get invoice by non-existing invoice Id")
    public void testGetInvoiceByInvalidId() throws Exception {
        UUID nonExistingId = UUID.randomUUID();
        Mockito.when(invoiceService.findById(Mockito.any(UUID.class)))
            .thenThrow(new ObjectNotFoundException("Invoice not found with ID: " + nonExistingId));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/invoice/{id}", nonExistingId)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isNotFound())
            .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Invoice not found with ID: " + nonExistingId));
    }

    @Test
    @DisplayName("Test 14: Download invoice should return PDF byte array")
    public void testDownloadInvoiceSuccess() throws Exception {
        UUID invoiceId = invoice.getInvoiceId();
        byte[] pdfBytes = new byte[]{1, 2, 3};
        when(invoiceService.generateToPdf(invoiceId)).thenReturn(pdfBytes);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/invoice/{id}/export", invoiceId)
                .accept(MediaType.APPLICATION_PDF))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_PDF))
                .andExpect(MockMvcResultMatchers.header().string(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=invoice.pdf"))
                .andExpect(MockMvcResultMatchers.content().bytes(pdfBytes));

        verify(invoiceService).generateToPdf(invoiceId);
    }
}
