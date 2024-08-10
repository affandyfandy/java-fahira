package com.midterm.group4.service;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;
import java.util.Arrays;
import java.util.List;
import java.time.LocalDateTime;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.midterm.group4.data.model.Customer;
import com.midterm.group4.data.model.Invoice;
import com.midterm.group4.data.model.OrderItem;
import com.midterm.group4.data.model.Product;
import com.midterm.group4.data.repository.CustomerRepository;
import com.midterm.group4.data.repository.InvoiceRepository;
import com.midterm.group4.data.repository.OrderItemRepository;
import com.midterm.group4.data.repository.ProductRepository;
import com.midterm.group4.exception.ObjectNotFoundException;
import com.midterm.group4.service.impl.InvoiceServiceImpl;
import com.midterm.group4.utils.DocumentUtils;

@ExtendWith(SpringExtension.class)
public class InvoiceServiceTest {

    @Mock
    private InvoiceRepository invoiceRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private OrderItemRepository orderItemRepository;

    @Mock
    private ProductService productService;

    @Mock
    private DocumentUtils documentUtils;

    @InjectMocks
    private InvoiceServiceImpl service;


    private Customer customer;
    private Product product;
    private OrderItem orderItem;
    private static Invoice invoice;

    @BeforeAll
    public static void setupInvoice() {
        invoice = new Invoice();
        invoice.setListOrderItem(new ArrayList<>());
    }

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        customer = new Customer();
        customer.setCustomerId(UUID.randomUUID());
        customer.setFirstName("Mario");
        customer.setLastName("Maurer");
        customer.setPhone("088282731234");
        customer.setActive(true);

        product = new Product();
        product.setProductId(UUID.randomUUID());
        product.setName("Stanley Tumbler");
        product.setPrice(BigInteger.valueOf(100));
        product.setQuantity(80);
        product.setActive(true);

        orderItem = new OrderItem();
        orderItem.setOrderItemId(UUID.randomUUID());
        orderItem.setProduct(product);
        orderItem.setQuantity(30);
        orderItem.setAmount(BigInteger.valueOf(3000));
    }

    @AfterEach
    public void cleanup(){
        customer = null;
        product = null;
        orderItem = null;
    }

    @Test
    @DisplayName("Test 1: Retrieve all invoices sorted")
    public void retrieveAllInvoiceSorted_thenReturnPageInvoice(){
        Sort sort = Sort.by(Sort.Direction.ASC, "invoiceDate");
        Pageable pageable = PageRequest.of(0, 10, sort);
        Page<Invoice> invoicePage = new PageImpl<>(Arrays.asList(invoice), pageable, 1);
        when(invoiceRepository.findAll(pageable)).thenReturn(invoicePage);
        Page<Invoice> actual = service.findAllSorted(0, 10, "invoiceDate", "asc");
        assertEquals(invoicePage, actual);
    }

    @Test
    @DisplayName("Test 2: Retrieve invoices by invoice date (month)")
    public void retrieveAllInvoices_withGivenMonth_thenReturnPageInvoice(){
        Sort sort = Sort.by(Sort.Direction.ASC, "invoiceDate");
        Pageable pageable = PageRequest.of(0, 10, sort);
        Page<Invoice> invoicePage = new PageImpl<>(Arrays.asList(invoice), pageable, 1);
        when(invoiceRepository.findAllByMonth(any(Integer.class), any(Pageable.class))).thenReturn(invoicePage);
        Page<Invoice> actual = service.findAllByMonth(0, 10, 8, "asc");
        assertEquals(invoicePage, actual);
    }

    @Test
    @DisplayName("Test 3: Get invoice by existing invoice id")
    public void getInvoice_withExistingId_thenReturnInvoice(){
        when(invoiceRepository.findById(invoice.getInvoiceId())).thenReturn(Optional.of(invoice));
        Invoice actual = service.findById(invoice.getInvoiceId());
        assertEquals(invoice, actual);
    }

    @Test
    @DisplayName("Test 4: Get invoice by non existing invoice id")
    public void getInvoice_withNonExistingId_thenReturnExeption(){
        UUID nonExistingId = UUID.randomUUID();
        when(invoiceRepository.findById(nonExistingId)).thenReturn(Optional.empty());
        assertThrows(ObjectNotFoundException.class, () -> {
            service.findById(nonExistingId);
        });
    }

    @Test
    @DisplayName("Test 5: Create new invoice with valid data")
    public void createNewInvoice_withValidData_thenReturnInvoice(){
        invoice.setCustomer(customer);
        List<OrderItem> listOrderItem = Arrays.asList(orderItem);
        when(invoiceRepository.save(any(Invoice.class))).thenReturn(invoice);
        Invoice actual = service.createInvoice(invoice, listOrderItem);
        assertEquals(invoice, actual);
    }

    @Test
    @DisplayName("Test 6: Create new invoice with non existing customer")
    public void createNewInvoice_withNonExistingCustomer_thenReturnException(){
        invoice.setCustomer(customer);
        List<OrderItem> listOrderItem = Arrays.asList(orderItem);

        when(customerRepository.findById(UUID.randomUUID())).thenReturn(Optional.empty());

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            service.createInvoice(invoice, listOrderItem);
        });
        assertEquals("Customer doesn't exist", thrown.getMessage());
    }

    @Test
    @DisplayName("Test 7: Create new invoice with inactive customer")
    public void createNewInvoice_withInactiveCustomer_thenReturnException(){
        Customer newCustomer = new Customer();
        newCustomer.setActive(false);
        newCustomer.setCustomerId(UUID.randomUUID());
        invoice.setCustomer(newCustomer);

        List<OrderItem> listOrderItem = Arrays.asList(orderItem);

        when(customerRepository.findById(newCustomer.getCustomerId())).thenReturn(Optional.of(newCustomer));

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            service.createInvoice(invoice, listOrderItem);
        });
        assertEquals("Customer is inactive", thrown.getMessage());
    }

    @Test
    @DisplayName("Test 8: Create new invoice with inactive product")
    public void createNewInvoice_withInactiveProduct_thenReturnException() {
        Product inactiveProduct = new Product();
        inactiveProduct.setActive(false);
        inactiveProduct.setProductId(UUID.randomUUID());
    
        orderItem.setProduct(inactiveProduct);
        invoice.setCustomer(customer);
        
        List<OrderItem> listOrderItem = Arrays.asList(orderItem);

        when(customerRepository.findById(customer.getCustomerId())).thenReturn(Optional.of(customer));
        when(productService.findById(inactiveProduct.getProductId())).thenReturn(inactiveProduct);

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            service.createInvoice(invoice, listOrderItem);
        });
        assertEquals("Product is inactive", thrown.getMessage());
    }

    @Test
    @DisplayName("Test 9: Create new invoice with insufficient product quantity")
    public void createNewInvoice_withInsufficientQty_thenReturnException() {
        orderItem.setQuantity(200);
        invoice.setCustomer(customer);

        List<OrderItem> listOrderItem = Arrays.asList(orderItem);

        when(customerRepository.findById(customer.getCustomerId())).thenReturn(Optional.of(customer));
        when(productService.findById(product.getProductId())).thenReturn(product);

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            service.createInvoice(invoice, listOrderItem);
        });
        assertEquals("Insuffient product quantity", thrown.getMessage());
    }


    @Test
    @DisplayName("Test 10: Retrive all invoices by customer name sort asc")
    public void retrieveInvoice_withCustomerName_thenReturnPageInvoiceASC(){
        String customerName = "Mario";
        int pageNo = 0;
        int pageSize = 10;
        String sortBy = "date";
        String sortOrder = "asc";

        Invoice invoice1 = new Invoice();
        Invoice invoice2 = new Invoice();
        List<Invoice> invoices = Arrays.asList(invoice1, invoice2);
        Page<Invoice> invoicePage = new PageImpl<>(invoices, PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.ASC, sortBy)), invoices.size());

        when(invoiceRepository.findAllByCustomerName(customerName, PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.ASC, sortBy))))
                .thenReturn(invoicePage);

        Page<Invoice> result = service.findAllByCustomerName(pageNo, pageSize, sortBy, sortOrder, customerName);

        assertEquals(2, result.getTotalElements());
        assertEquals(invoice1, result.getContent().get(0));
        assertEquals(invoice2, result.getContent().get(1));
        verify(invoiceRepository, times(1))
            .findAllByCustomerName(customerName, PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.ASC, sortBy)));
    }

    @Test
    @DisplayName("Test 11: Retrive total amount per day")
    public void getTotalAmount_withDateDay_thenReturnBigInteger(){
        LocalDate date = LocalDate.of(2024, 8, 6);
        BigInteger expectedTotalAmount = BigInteger.valueOf(300);

        when(invoiceRepository.findTotalAmountByDate(date)).thenReturn(expectedTotalAmount);

        BigInteger actualTotalAmount = service.getTotalAmountPerDay(date);
        assertEquals(expectedTotalAmount, actualTotalAmount);
        verify(invoiceRepository).findTotalAmountByDate(date);
    }

    @Test
    @DisplayName("Test 12: Retrive total amount per month")
    public void getTotalAmount_withDateMonth_thenReturnBigInteger(){
        BigInteger expectedTotalAmount = BigInteger.valueOf(10000);

        when(invoiceRepository.findTotalAmountByMonth(8, 2024)).thenReturn(expectedTotalAmount);

        BigInteger actualTotalAmount = service.getTotalAmountPerMonth(8, 2024);
        assertEquals(expectedTotalAmount, actualTotalAmount);
        verify(invoiceRepository).findTotalAmountByMonth(8, 2024);
    }

    @Test
    @DisplayName("Test 13: Retrive total amount per year")
    public void getTotalAmount_withDateYear_thenReturnBigInteger(){
        BigInteger expectedTotalAmount = BigInteger.valueOf(100000);

        when(invoiceRepository.findTotalAmountByYear(2024)).thenReturn(expectedTotalAmount);

        BigInteger actualTotalAmount = service.getTotalAmountPerYear(2024);
        assertEquals(expectedTotalAmount, actualTotalAmount);
        verify(invoiceRepository).findTotalAmountByYear(2024);
    }

    @Test
    @DisplayName("Test 14: Generate PDF with non existing invoice Id")
    public void generateToPdf_withNonExistingInvoice_thenReturnException() throws IOException {
        UUID invoiceId = UUID.randomUUID();

        when(invoiceRepository.findById(invoiceId)).thenReturn(Optional.empty());

        ObjectNotFoundException thrown = assertThrows(ObjectNotFoundException.class, () -> {
            service.generateToPdf(invoiceId);
        });

        assertEquals("Invoice not found with ID: " + invoiceId, thrown.getMessage());

        verify(invoiceRepository, times(1)).findById(invoiceId);
        verify(documentUtils, never()).generateByteInvoice(any(Invoice.class));
    }

    @Test
    @DisplayName("Test 15: Retrieve all invoice by filter (all params)")
    public void findAllFiltered_withAllFilters_thenReturnPageInvoice() {
        String invoiceDate = "2024-08-06";
        String month = "8";
        Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "invoiceDate"));
        Page<Invoice> expectedPage = new PageImpl<>(List.of(new Invoice(), new Invoice()));

        when(invoiceRepository.findAllFiltered(customer.getCustomerId(), invoiceDate, month, pageable)).thenReturn(expectedPage);

        Page<Invoice> result = service.findAllFiltered(0, 10, "invoiceDate", "asc", customer.getCustomerId(), invoiceDate, month);

        assertEquals(expectedPage, result);
        verify(invoiceRepository).findAllFiltered(customer.getCustomerId(), invoiceDate, month, pageable);
    }

    @Test
    @DisplayName("Test 16: Retrieve all invoice by filter (customer & invoice date)")
    public void findAllFiltered_withCustomerAndDate_thenRetunPageInvoice() {
        String invoiceDate = "2024-08-06";
        Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "invoiceDate"));
        Page<Invoice> expectedPage = new PageImpl<>(List.of(new Invoice(), new Invoice()));

        when(invoiceRepository.findAllByCustomerIdAndInvoiceDate(customer.getCustomerId(), invoiceDate, pageable)).thenReturn(expectedPage);

        Page<Invoice> result = service.findAllFiltered(0, 10, "invoiceDate", "asc", customer.getCustomerId(), invoiceDate, null);

        assertEquals(expectedPage, result);
        verify(invoiceRepository).findAllByCustomerIdAndInvoiceDate(customer.getCustomerId(), invoiceDate, pageable);
    }

    @Test
    @DisplayName("Test 17: Retrieve all invoice by filter (customer & month)")
    public void findAllFiltered_withCustomerAndMonth_thenReturnPageInvoice() {
        String month = "8";
        Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "invoiceDate"));
        Page<Invoice> expectedPage = new PageImpl<>(List.of(new Invoice(), new Invoice()));

        when(invoiceRepository.findAllByCustomerIdAndMonth(customer.getCustomerId(), month, pageable)).thenReturn(expectedPage);

        Page<Invoice> result = service.findAllFiltered(0, 10, "invoiceDate", "asc", customer.getCustomerId(), null, month);

        assertEquals(expectedPage, result);
        verify(invoiceRepository).findAllByCustomerIdAndMonth(customer.getCustomerId(), month, pageable);
    }

    @Test
    @DisplayName("Test 18: Retrieve all invoice by filter (customer Id)")
    public void findAllFiltered_withCustomerOnly_thenReturnPageInvoice() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "invoiceDate"));
        Page<Invoice> expectedPage = new PageImpl<>(List.of(new Invoice(), new Invoice()));

        when(invoiceRepository.findAllByCustomerId(customer.getCustomerId(), pageable)).thenReturn(expectedPage);

        Page<Invoice> result = service.findAllFiltered(0, 10, "invoiceDate", "asc", customer.getCustomerId(), null, null);

        assertEquals(expectedPage, result);
        verify(invoiceRepository).findAllByCustomerId(customer.getCustomerId(), pageable);
    }

    @Test
    @DisplayName("Test 19: Retrieve all invoice by filter (month)")
    public void findAllFiltered_withMonthOnly_thenReturnPageInvoice() {
        String month = "8";
        Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "invoiceDate"));
        Page<Invoice> expectedPage = new PageImpl<>(List.of(new Invoice(), new Invoice()));

        when(invoiceRepository.findAllByMonth(Integer.parseInt(month), pageable)).thenReturn(expectedPage);

        Page<Invoice> result = service.findAllFiltered(0, 10, "invoiceDate", "asc", null, null, month);

        assertEquals(expectedPage, result);
        verify(invoiceRepository).findAllByMonth(Integer.parseInt(month), pageable);
    }

    @Test
    @DisplayName("Test 20: Retrieve all invoice by filter (date)")
    public void findAllFiltered_withDateOnly_thenReturnPageInvoice() {
        String invoiceDate = "2024-08-06";
        Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "invoiceDate"));
        Page<Invoice> expectedPage = new PageImpl<>(List.of(new Invoice(), new Invoice()));

        when(invoiceRepository.findAllByInvoiceDate(invoiceDate, pageable)).thenReturn(expectedPage);

        Page<Invoice> result = service.findAllFiltered(0, 10, "invoiceDate", "asc", null, invoiceDate, null);

        assertEquals(expectedPage, result);
        verify(invoiceRepository).findAllByInvoiceDate(invoiceDate, pageable);
    }

    @Test
    @DisplayName("Test 21: Update invoice detail with valid data")
    public void updateInvoice_thenReturnInvoice() {
        UUID invoiceId = invoice.getInvoiceId();
        invoice.setCreatedTime(LocalDateTime.now());
        invoice.setUpdatedTime(LocalDateTime.now());

        when(invoiceRepository.findById(invoiceId)).thenReturn(Optional.of(invoice));
        when(productService.findById(product.getProductId())).thenReturn(product);
        when(productRepository.save(any(Product.class))).thenReturn(product);
        when(invoiceRepository.save(any(Invoice.class))).thenReturn(invoice);

        List<OrderItem> newOrderItems = Arrays.asList(orderItem);

        Invoice updatedInvoice = service.update(invoiceId, invoice, newOrderItems);

        assertNotNull(updatedInvoice);
        assertEquals(1, updatedInvoice.getListOrderItem().size());
        assertEquals(BigInteger.valueOf(3000), updatedInvoice.getListOrderItem().get(0).getAmount());
        verify(invoiceRepository, times(1)).findById(invoiceId);
        verify(productRepository, times(1)).save(any(Product.class));
        verify(invoiceRepository, times(1)).save(any(Invoice.class));
    }

    @Test
    @DisplayName("Test 22: Update invoice after 10 minutes being created")
    public void updateAfter10Minutes_thenReturnException() {
        invoice.setCreatedTime(LocalDateTime.now().minusMinutes(15)); 
        when(invoiceRepository.findById(invoice.getInvoiceId())).thenReturn(Optional.of(invoice));

        List<OrderItem> newOrderItems = Arrays.asList(orderItem);

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            service.update(invoice.getInvoiceId(), invoice, newOrderItems);
        });

        assertEquals("Invoice can't be edited: over 10 minutes from being created", thrown.getMessage());
        verify(invoiceRepository, times(1)).findById(invoice.getInvoiceId());
        verify(productRepository, never()).save(any(Product.class));
        verify(invoiceRepository, never()).save(any(Invoice.class));
    }

    @Test
    @DisplayName("Test 23: Update with non existing product")
    public void updateInvoice_withNonExistingProduct_thenReturnException() {
        UUID randomId = UUID.randomUUID();
        invoice.setCreatedTime(LocalDateTime.now());
        invoice.setUpdatedTime(LocalDateTime.now());

        when(invoiceRepository.findById(invoice.getInvoiceId())).thenReturn(Optional.of(invoice));
        when(productService.findById(randomId)).thenReturn(null);

        List<OrderItem> newOrderItems = Arrays.asList(orderItem);
        newOrderItems.get(0).getProduct().setProductId(randomId);

        ObjectNotFoundException thrown = assertThrows(ObjectNotFoundException.class, () -> {
            service.update(invoice.getInvoiceId(), invoice, newOrderItems);
        });

        assertEquals("Product not found", thrown.getMessage());
        verify(invoiceRepository, times(1)).findById(invoice.getInvoiceId());
        verify(productService, times(1)).findById(randomId);
        verify(productRepository, never()).save(any(Product.class));
        verify(invoiceRepository, never()).save(any(Invoice.class));
    }

    @Test
    @DisplayName("Test 24: Update with inactive product")
    public void updateInvoice_withInactiveProduct_thenReturnException() {
        product.setActive(false);
        invoice.setCreatedTime(LocalDateTime.now());
        invoice.setUpdatedTime(LocalDateTime.now());

        when(invoiceRepository.findById(invoice.getInvoiceId())).thenReturn(Optional.of(invoice));
        when(productService.findById(product.getProductId())).thenReturn(product);

        List<OrderItem> newOrderItems = Arrays.asList(orderItem);

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            service.update(invoice.getInvoiceId(), invoice, newOrderItems);
        });

        assertEquals("Product is not active", thrown.getMessage());
        verify(invoiceRepository, times(1)).findById(invoice.getInvoiceId());
        verify(productService, times(1)).findById(product.getProductId());
        verify(productRepository, never()).save(any(Product.class));
        verify(invoiceRepository, never()).save(any(Invoice.class));
    }

    @Test
    @DisplayName("Test 25: Update with insufficient product quantity")
    public void updateInvoice_withInsufficientProductQty_thenReturnException() {
        product.setQuantity(5);
        invoice.setCreatedTime(LocalDateTime.now());
        invoice.setUpdatedTime(LocalDateTime.now());

        when(invoiceRepository.findById(invoice.getInvoiceId())).thenReturn(Optional.of(invoice));
        when(productService.findById(product.getProductId())).thenReturn(product);

        List<OrderItem> newOrderItems = Arrays.asList(orderItem);
        newOrderItems.get(0).setQuantity(10);

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            service.update(invoice.getInvoiceId(), invoice, newOrderItems);
        });

        assertEquals("Insufficient product quantity", thrown.getMessage());
        verify(invoiceRepository, times(1)).findById(invoice.getInvoiceId());
        verify(productService, times(1)).findById(product.getProductId());
        verify(productRepository, never()).save(any(Product.class));
        verify(invoiceRepository, never()).save(any(Invoice.class));
    }

    @Test
    @DisplayName("Test 26: Generate invoice report data with all parameters")
    public void generateInvoiceReportData_withAllParameters_thenReturnCorrectData() {
        UUID customerId = UUID.randomUUID();
        Integer month = 5;
        Integer year = 2024;
        List<Invoice> expectedInvoices = List.of(new Invoice(), new Invoice());

        when(invoiceRepository.findByCustomerAndMonthAndYear(customerId, month, year)).thenReturn(expectedInvoices);

        List<Invoice> result = service.generateInvoiceReportData(customerId, month, year);

        assertEquals(expectedInvoices, result);
        verify(invoiceRepository).findByCustomerAndMonthAndYear(customerId, month, year);
    }

    @Test
    @DisplayName("Test 27: Generate invoice report with customerId and month")
    public void generateInvoiceReportData_withCustomerIdAndMonth_thenReturnCorrectData() {
        UUID customerId = UUID.randomUUID();
        Integer month = 5;
        List<Invoice> expectedInvoices = List.of(new Invoice(), new Invoice());

        when(invoiceRepository.findByCustomerAndMonth(customerId, month)).thenReturn(expectedInvoices);

        List<Invoice> result = service.generateInvoiceReportData(customerId, month, null);

        assertEquals(expectedInvoices, result);
        verify(invoiceRepository).findByCustomerAndMonth(customerId, month);
    }

    @Test
    @DisplayName("Test 28: Generate invoice report with customerId and year")
    public void generateInvoiceReportData_withCustomerIdAndYear_thenReturnCorrectData() {
        UUID customerId = UUID.randomUUID();
        Integer year = 2024;
        List<Invoice> expectedInvoices = List.of(new Invoice(), new Invoice());

        when(invoiceRepository.findByCustomerAndYear(customerId, year)).thenReturn(expectedInvoices);

        List<Invoice> result = service.generateInvoiceReportData(customerId, null, year);

        assertEquals(expectedInvoices, result);
        verify(invoiceRepository).findByCustomerAndYear(customerId, year);
    }

    @Test
    @DisplayName("Test 29: Generate invoice report with month and year")
    public void generateInvoiceReportData_withMonthAndYear_thenReturnCorrectData() {
        Integer month = 5;
        Integer year = 2024;
        List<Invoice> expectedInvoices = List.of(new Invoice(), new Invoice());

        when(invoiceRepository.findByMonthAndYear(month, year)).thenReturn(expectedInvoices);

        List<Invoice> result = service.generateInvoiceReportData(null, month, year);

        assertEquals(expectedInvoices, result);
        verify(invoiceRepository).findByMonthAndYear(month, year);
    }

    @Test
    @DisplayName("Test 30: Generate invoice report with customerId only")
    public void generateInvoiceReportData_withCustomerIdOnly_thenReturnCorrectData() {
        UUID customerId = UUID.randomUUID();
        List<Invoice> expectedInvoices = List.of(new Invoice(), new Invoice());

        when(invoiceRepository.findByCustomer(customerId)).thenReturn(expectedInvoices);

        List<Invoice> result = service.generateInvoiceReportData(customerId, null, null);

        assertEquals(expectedInvoices, result);
        verify(invoiceRepository).findByCustomer(customerId);
    }

    @Test
    @DisplayName("Test 31: Generate invoice report with year only")
    public void generateInvoiceReportData_withYearOnly_thenReturnCorrectData() {
        Integer year = 2024;
        List<Invoice> expectedInvoices = List.of(new Invoice(), new Invoice());

        when(invoiceRepository.findByYear(year)).thenReturn(expectedInvoices);

        List<Invoice> result = service.generateInvoiceReportData(null, null, year);

        assertEquals(expectedInvoices, result);
        verify(invoiceRepository).findByYear(year);
    }

    @Test
    @DisplayName("Test 32: Generate invoice report with no parameters")
    public void generateInvoiceReportData_withNoParameters_thenReturnAllInvoices() {
        List<Invoice> expectedInvoices = List.of(new Invoice(), new Invoice());

        when(invoiceRepository.findAll()).thenReturn(expectedInvoices);

        List<Invoice> result = service.generateInvoiceReportData(null, null, null);

        assertEquals(expectedInvoices, result);
        verify(invoiceRepository).findAll();
    }

    @Test
    @DisplayName("Test 33: Get sold products should return list of product names")
    public void getSoldProducts_thenReturnSoldProducts() {
        List<String> expectedProducts = List.of("Product1", "Product2", "Product3");

        when(orderItemRepository.findSoldProducts()).thenReturn(expectedProducts);

        List<String> result = service.getSoldProducts();

        assertEquals(expectedProducts, result);
        verify(orderItemRepository).findSoldProducts();
    }

    @Test
    @DisplayName("Test 34: Generate PDF should return byte array of PDF")
    public void generateToPdf_thenReturnPdfByteArray() throws IOException {
        UUID invoiceId = invoice.getInvoiceId();
        byte[] expectedPdfBytes = new byte[]{1, 2, 3};

        when(invoiceRepository.findById(invoiceId)).thenReturn(java.util.Optional.of(invoice));
        when(documentUtils.generateByteInvoice(invoice)).thenReturn(expectedPdfBytes);

        byte[] result = service.generateToPdf(invoiceId);

        assertArrayEquals(expectedPdfBytes, result);
        verify(invoiceRepository).findById(invoiceId);
        verify(documentUtils).generateByteInvoice(invoice);
    }

}
