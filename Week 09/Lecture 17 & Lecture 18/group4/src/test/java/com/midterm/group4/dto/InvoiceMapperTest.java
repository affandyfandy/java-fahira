package com.midterm.group4.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.math.BigInteger;
import java.util.UUID;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;

import com.midterm.group4.data.model.Customer;
import com.midterm.group4.data.model.Invoice;
import com.midterm.group4.data.model.Product;
import com.midterm.group4.dto.request.CreateInvoiceDTO;
import com.midterm.group4.dto.request.CreateOrderItemDTO;
import com.midterm.group4.dto.response.ReadCustomerOrderDTO;
import com.midterm.group4.dto.response.ReadInvoiceDTO;
import com.midterm.group4.dto.response.ReadInvoiceOrderDTO;

public class InvoiceMapperTest {
    
    @InjectMocks
    private InvoiceMapper invoiceMapper = Mappers.getMapper(InvoiceMapper.class);

    @Mock
    private CustomerMapper customerMapper;

    @Mock
    private ProductMapper productMapper;

    @BeforeEach
    public void setUp() {
         MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testToReadInvoiceOrderDto() {
        Customer customer = new Customer();
        customer.setCustomerId(UUID.randomUUID());
        customer.setFirstName("John");
        customer.setLastName("Doe");

        Product product = new Product();
        product.setProductId(UUID.randomUUID());
        product.setPrice(BigInteger.valueOf(100));
        product.setQuantity(10);
        product.setActive(true);

        Invoice invoice = new Invoice();
        invoice.setInvoiceId(UUID.randomUUID());
        invoice.setCustomer(customer);
        invoice.setTotalAmount(BigInteger.valueOf(500));
        invoice.setInvoiceDate(LocalDate.now());
        invoice.setListOrderItem(new ArrayList<>());

        ReadInvoiceOrderDTO dto = invoiceMapper.toReadInvoiceOrderDto(invoice);

        assertEquals(invoice.getInvoiceId(), dto.getInvoiceId());
        assertEquals(invoice.getTotalAmount(), dto.getTotalAmount());
        assertEquals(invoice.getInvoiceDate(), dto.getInvoiceDate());
    }

    @Test
    public void testToReadInvoiceDto() {
        Customer customer = new Customer();
        customer.setCustomerId(UUID.randomUUID());
        customer.setFirstName("John");
        customer.setLastName("Doe");

        Product product = new Product();
        product.setProductId(UUID.randomUUID());
        product.setPrice(BigInteger.valueOf(100));
        product.setQuantity(10);
        product.setActive(true);

        Invoice invoice = new Invoice();
        invoice.setInvoiceId(UUID.randomUUID());
        invoice.setCustomer(customer);
        invoice.setTotalAmount(BigInteger.valueOf(500));
        invoice.setInvoiceDate(LocalDate.now());
        invoice.setListOrderItem(new ArrayList<>());

        // Mock CustomerMapper
        ReadCustomerOrderDTO customerDto = new ReadCustomerOrderDTO();
        customerDto.setCustomerId(customer.getCustomerId());
        when(customerMapper.toReadCustomerOrderDto(customer)).thenReturn(customerDto);

        ReadInvoiceDTO dto = invoiceMapper.toReadInvoiceDto(invoice);

        assertEquals(invoice.getInvoiceId(), dto.getInvoiceId());
        assertEquals(customer.getCustomerId(), dto.getCustomer().getCustomerId());
        assertEquals(invoice.getInvoiceDate(), dto.getInvoiceDate());
    }

    @Test
    public void testToListReadInvoiceDto() {
        Customer customer = new Customer();
        customer.setCustomerId(UUID.randomUUID());
        customer.setFirstName("John");
        customer.setLastName("Doe");

        Product product = new Product();
        product.setProductId(UUID.randomUUID());
        product.setPrice(BigInteger.valueOf(100));
        product.setQuantity(10);
        product.setActive(true);

        Invoice invoice1 = new Invoice();
        invoice1.setInvoiceId(UUID.randomUUID());
        invoice1.setCustomer(customer);
        invoice1.setTotalAmount(BigInteger.valueOf(500));
        invoice1.setInvoiceDate(LocalDate.now());
        invoice1.setListOrderItem(new ArrayList<>());

        Invoice invoice2 = new Invoice();
        invoice2.setInvoiceId(UUID.randomUUID());
        invoice2.setCustomer(customer);
        invoice2.setTotalAmount(BigInteger.valueOf(300));
        invoice2.setInvoiceDate(LocalDate.now());
        invoice2.setListOrderItem(new ArrayList<>());

        List<Invoice> invoices = new ArrayList<>();
        invoices.add(invoice1);
        invoices.add(invoice2);

        List<ReadInvoiceDTO> dtos = invoiceMapper.toListReadInvoiceDto(invoices);

        assertEquals(2, dtos.size());
        assertEquals(invoice1.getInvoiceId(), dtos.get(0).getInvoiceId());
        assertEquals(invoice2.getInvoiceId(), dtos.get(1).getInvoiceId());
    }

    @Test
    public void testToEntity() {
        CreateInvoiceDTO createInvoiceDTO = new CreateInvoiceDTO();
        createInvoiceDTO.setCustomerId(UUID.randomUUID());
        List<CreateOrderItemDTO> orderItem = Arrays.asList(new CreateOrderItemDTO());
        createInvoiceDTO.setListOrderItem(orderItem);
        Invoice invoice = invoiceMapper.toEntity(createInvoiceDTO);
        assertEquals(createInvoiceDTO.getCustomerId(), invoice.getCustomer().getCustomerId());
    }
}
