package com.midterm.group4.repository;

import com.midterm.group4.data.model.Invoice;
import com.midterm.group4.data.model.Customer;
import com.midterm.group4.data.repository.InvoiceRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.midterm.group4.data.repository.CustomerRepository;

import java.math.BigInteger;
import java.util.ArrayList;
import java.time.LocalDate;
import java.util.List;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class InvoiceRepositoryTest {

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private CustomerRepository customerRepository;

    private Invoice invoice;

    private Customer customer;

    @BeforeEach
    public void setup(){
        customer = new Customer();
        customer.setFirstName("Fahira");
        customer.setLastName("Adindiah");
        customer.setPhone("08189209381");
        customerRepository.save(customer);

        invoice = new Invoice();
        invoice.setCustomer(customer);
        invoice.setListOrderItem(new ArrayList<>());
        invoice.setTotalAmount(BigInteger.valueOf(0));
        invoice.setInvoiceDate(LocalDate.now());
        invoiceRepository.save(invoice);
    }

    @AfterEach
    public void teardown(){
        invoiceRepository.deleteAll();
        customerRepository.deleteAll();
    }

    @Test
    @DisplayName("Test 1: Get all invoices by customer name")
    public void getAllInvoice_withCustomerName_thenRetunPageInvoices(){
        Pageable pageable = PageRequest.of(0, 10);
        Page<Invoice> actual = invoiceRepository.findAllByCustomerName("Fahira", pageable);
        assertEquals(1, actual.getContent().size());
        assertEquals(actual.getContent().get(0).getCustomer().getCustomerId(), customer.getCustomerId());
    }

    @Test
    @DisplayName("Test 2: Get all invoices by invoice date (month)")
    public void getAllInvoice_withInvoiceMonth_thenRetunPageInvoices(){
        Pageable pageable = PageRequest.of(0, 10);
        Page<Invoice> actual = invoiceRepository.findAllByMonth(8, pageable);
        assertEquals(1, actual.getContent().size());
        assertEquals(actual.getContent().get(0).getInvoiceDate(), invoice.getInvoiceDate());
    }

    @Test
    @DisplayName("Test 3: Get total amount by date")
    public void getTotalAmount_byDate_thenReturnTotalAmount(){
        BigInteger totalAmount = invoiceRepository.findTotalAmountByDate(LocalDate.now());
        assertEquals(BigInteger.valueOf(0), totalAmount);
    }

    @Test
    @DisplayName("Test 4: Get invoices by month and year")
    public void getInvoices_byMonthAndYear_thenReturnListInvoices(){
        List<Invoice> invoices = invoiceRepository.findByMonthAndYear(LocalDate.now().getMonthValue(), LocalDate.now().getYear());
        assertEquals(1, invoices.size());
        assertEquals(invoice.getInvoiceDate().getMonthValue(), invoices.get(0).getInvoiceDate().getMonthValue());
        assertEquals(invoice.getInvoiceDate().getYear(), invoices.get(0).getInvoiceDate().getYear());
    }

    @Test
    @DisplayName("Test 5: Get invoices by customer and month and year")
    public void getInvoices_byCustomerAndMonthAndYear_thenReturnListInvoices(){
        List<Invoice> invoices = invoiceRepository.findByCustomerAndMonthAndYear(customer.getCustomerId(), LocalDate.now().getMonthValue(), LocalDate.now().getYear());
        assertEquals(1, invoices.size());
        assertEquals(invoice.getCustomer().getCustomerId(), invoices.get(0).getCustomer().getCustomerId());
    }

    @Test
    @DisplayName("Test 6: Get invoices by customer and year")
    public void getInvoices_byCustomerAndYear_thenReturnListInvoices(){
        List<Invoice> invoices = invoiceRepository.findByCustomerAndYear(customer.getCustomerId(), LocalDate.now().getYear());
        assertEquals(1, invoices.size());
        assertEquals(invoice.getCustomer().getCustomerId(), invoices.get(0).getCustomer().getCustomerId());
    }

    @Test
    @DisplayName("Test 7: Get invoices by year")
    public void getInvoices_byYear_thenReturnListInvoices(){
        List<Invoice> invoices = invoiceRepository.findByYear(LocalDate.now().getYear());
        assertEquals(1, invoices.size());
        assertEquals(invoice.getInvoiceDate().getYear(), invoices.get(0).getInvoiceDate().getYear());
    }

    @Test
    @DisplayName("Test 8: Get invoices by customer")
    public void getInvoices_byCustomer_thenReturnListInvoices(){
        List<Invoice> invoices = invoiceRepository.findByCustomer(customer.getCustomerId());
        assertEquals(1, invoices.size());
        assertEquals(invoice.getCustomer().getCustomerId(), invoices.get(0).getCustomer().getCustomerId());
    }

    @Test
    @DisplayName("Test 9: @PrePersist lifecycle method")
    public void prePersist_thenReturnNewCreateAndUpdateTime() {
        invoiceRepository.flush();

        assertNotNull(invoice.getInvoiceId());
        assertNotNull(invoice.getCreatedTime());
        assertNotNull(invoice.getUpdatedTime());

        assertEquals(invoice.getCreatedTime().toLocalDate().toString(), LocalDateTime.now().toLocalDate().toString());
        assertEquals(invoice.getUpdatedTime().toLocalDate().toString(), LocalDateTime.now().toLocalDate().toString());
    }

    @Test
    @DisplayName("Test 10: @PostUpdate lifecycle method")
    public void postUpdate_thenReturnNewUpdateTime() {
        invoice.setListOrderItem(new ArrayList<>());
        invoiceRepository.save(invoice);
        invoiceRepository.flush();

        assertNotNull(invoice.getUpdatedTime());
        assertEquals(invoice.getUpdatedTime().toLocalDate().toString(), LocalDateTime.now().toLocalDate().toString());
    }

    @Test
    @DisplayName("Test 11: Create invoice with null id")
    public void whenPersistInvoice_withNullInvoiceId_thenInvoiceIdShouldBeGenerated() {
        Invoice invalidInvoice = new Invoice();
        invalidInvoice.setInvoiceId(null);
        invalidInvoice.setListOrderItem(new ArrayList<>());
        invalidInvoice.setTotalAmount(BigInteger.valueOf(100));
        invalidInvoice.setInvoiceDate(LocalDate.now());
        invoiceRepository.save(invalidInvoice);

        Invoice savedInvoice = invoiceRepository.findById(invalidInvoice.getInvoiceId()).orElse(null);

        assertNotNull(savedInvoice);
        assertNotNull(savedInvoice.getInvoiceId());
        assertTrue(savedInvoice.getInvoiceId() != null);
        assertNotNull(savedInvoice.getCreatedTime());
        assertNotNull(savedInvoice.getUpdatedTime());
        assertTrue(savedInvoice.getCreatedTime().isBefore(LocalDateTime.now()));
        assertTrue(savedInvoice.getUpdatedTime().isBefore(LocalDateTime.now()));
    }
    


}
