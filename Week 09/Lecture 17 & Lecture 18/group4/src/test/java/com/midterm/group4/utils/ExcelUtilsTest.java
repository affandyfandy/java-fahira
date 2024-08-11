package com.midterm.group4.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.List;
import java.util.UUID;
import java.util.ArrayList;

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
    }

    @Test
    public void testCreateWorkbookAndSheet() {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Invoices");

        assertNotNull(workbook);
        assertNotNull(sheet);
        assertEquals("Invoices", sheet.getSheetName());
    }

    @Test
    public void testCreateHeaderRow() {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Invoices");

        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Invoice ID");
        headerRow.createCell(1).setCellValue("Customer ID");
        headerRow.createCell(2).setCellValue("Customer Name");
        headerRow.createCell(3).setCellValue("Total Amount");
        headerRow.createCell(4).setCellValue("Products");

        assertNotNull(headerRow, "Header row should not be null");
        assertEquals("Invoice ID", headerRow.getCell(0).getStringCellValue(), "Cell 0 should contain 'Invoice ID'");
        assertEquals("Customer ID", headerRow.getCell(1).getStringCellValue(), "Cell 1 should contain 'Customer ID'");
        assertEquals("Customer Name", headerRow.getCell(2).getStringCellValue(), "Cell 2 should contain 'Customer Name'");
        assertEquals("Total Amount", headerRow.getCell(3).getStringCellValue(), "Cell 3 should contain 'Total Amount'");
        assertEquals("Products", headerRow.getCell(4).getStringCellValue(), "Cell 4 should contain 'Products'");
    }

    @Test
    public void testWriteToByteArrayOutputStream() throws IOException {
        // Create sample data
        Invoice invoice = new Invoice();
        invoice.setInvoiceId(UUID.randomUUID());
        Customer customer = new Customer();
        customer.setCustomerId(UUID.randomUUID());
        customer.setFirstName("John");
        customer.setLastName("Doe");
        invoice.setCustomer(customer);
        invoice.setTotalAmount(BigInteger.valueOf(5000));

        List<OrderItem> orderItems = new ArrayList<>();
        OrderItem orderItem = new OrderItem();
        Product product = new Product();
        product.setProductId(UUID.randomUUID());
        product.setPrice(BigInteger.valueOf(1000));
        orderItem.setProduct(product);
        orderItem.setQuantity(5);
        orderItems.add(orderItem);
        invoice.setListOrderItem(orderItems);

        // Create a workbook and sheet
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Invoices");

        // Create header row
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Invoice ID");
        headerRow.createCell(1).setCellValue("Customer ID");
        headerRow.createCell(2).setCellValue("Customer Name");
        headerRow.createCell(3).setCellValue("Total Amount");
        headerRow.createCell(4).setCellValue("Products");

        // Fill data rows
        int rowNum = 1;
        Row row = sheet.createRow(rowNum++);
        row.createCell(0).setCellValue(invoice.getInvoiceId().toString());
        row.createCell(1).setCellValue(invoice.getCustomer().getCustomerId().toString());
        row.createCell(2).setCellValue(invoice.getCustomer().getFirstName() + " " + invoice.getCustomer().getLastName());
        row.createCell(3).setCellValue(invoice.getTotalAmount().toString());

        StringBuilder products = new StringBuilder();
        invoice.getListOrderItem().forEach(item -> {
            BigInteger quantity = BigInteger.valueOf(item.getQuantity());
            BigInteger price = new BigInteger(item.getProduct().getPrice().toString());
            BigInteger amount = quantity.multiply(price);

            products.append("ID: ").append(item.getProduct().getProductId().toString())
                    .append(", Name: ").append(item.getProduct().getName())
                    .append(", Price: ").append(price.toString())
                    .append(", Quantity: ").append(quantity.toString())
                    .append(", Amount: ").append(amount.toString())
                    .append("; ");
        });
        row.createCell(4).setCellValue(products.toString());

        // Write to ByteArrayOutputStream
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        workbook.close();

        // Verify the output
        byte[] byteArray = outputStream.toByteArray();
        assertEquals(byteArray.length > 0, true, "Output stream should not be empty");

        // Optionally, you can read the content and verify further
        try (ByteArrayInputStream inputStream = new ByteArrayInputStream(byteArray);
             Workbook testWorkbook = new XSSFWorkbook(inputStream)) {
            Sheet testSheet = testWorkbook.getSheetAt(0);
            Row testHeaderRow = testSheet.getRow(0);
            assertEquals("Invoice ID", testHeaderRow.getCell(0).getStringCellValue());
            assertEquals("Customer ID", testHeaderRow.getCell(1).getStringCellValue());
            assertEquals("Customer Name", testHeaderRow.getCell(2).getStringCellValue());
            assertEquals("Total Amount", testHeaderRow.getCell(3).getStringCellValue());
            assertEquals("Products", testHeaderRow.getCell(4).getStringCellValue());

            Row testRow = testSheet.getRow(1);
            assertEquals(invoice.getInvoiceId().toString(), testRow.getCell(0).getStringCellValue());
            assertEquals(invoice.getCustomer().getCustomerId().toString(), testRow.getCell(1).getStringCellValue());
            assertEquals(invoice.getCustomer().getFirstName() + " " + invoice.getCustomer().getLastName(), testRow.getCell(2).getStringCellValue());
            assertEquals(invoice.getTotalAmount().toString(), testRow.getCell(3).getStringCellValue());
        }
    }

    @Test
    public void testExportInvoicesToExcel() throws IOException {
        // Create sample data
        Invoice invoice = new Invoice();
        invoice.setInvoiceId(UUID.randomUUID());
        Customer customer = new Customer();
        customer.setCustomerId(UUID.randomUUID());
        customer.setFirstName("Jane");
        customer.setLastName("Doe");
        invoice.setCustomer(customer);
        invoice.setTotalAmount(BigInteger.valueOf(10000));

        List<OrderItem> orderItems = new ArrayList<>();
        OrderItem orderItem = new OrderItem();
        Product product = new Product();
        product.setProductId(UUID.randomUUID());
        product.setName("Product X");
        product.setPrice(BigInteger.valueOf(200));
        product.setQuantity(50);
        orderItem.setProduct(product);
        orderItem.setQuantity(10);
        orderItem.setAmount(BigInteger.valueOf(2000));
        orderItems.add(orderItem);
        invoice.setListOrderItem(orderItems);

        List<Invoice> invoices = List.of(invoice);

        // Execute the method
        ByteArrayInputStream inputStream = excelUtils.exportInvoicesToExcel(invoices);

        // Verify the content of the ByteArrayInputStream
        try (Workbook workbook = new XSSFWorkbook(inputStream)) {
            Sheet sheet = workbook.getSheetAt(0);

            // Check header row
            Row headerRow = sheet.getRow(0);
            String[] expectedHeaders = {"Invoice ID", "Customer ID", "Customer Name", "Amount", "Product ID", "Product Name", "Product Price", "Product Quantity", "Product Amount"};
            for (int i = 0; i < expectedHeaders.length; i++) {
                assertEquals(expectedHeaders[i], headerRow.getCell(i).getStringCellValue());
            }

            // Check data row
            Row dataRow = sheet.getRow(1);
            assertEquals(invoice.getInvoiceId().toString(), dataRow.getCell(0).getStringCellValue());
            assertEquals(invoice.getCustomer().getCustomerId().toString(), dataRow.getCell(1).getStringCellValue());
            assertEquals(invoice.getCustomer().getFirstName() + " " + invoice.getCustomer().getLastName(), dataRow.getCell(2).getStringCellValue());
            assertEquals(invoice.getTotalAmount().toString(), dataRow.getCell(3).getStringCellValue());
            assertEquals(product.getProductId().toString(), dataRow.getCell(4).getStringCellValue());
            assertEquals(product.getName(), dataRow.getCell(5).getStringCellValue());
            assertEquals(product.getPrice().toString(), dataRow.getCell(6).getStringCellValue());
            assertEquals(orderItem.getQuantity(), (int) dataRow.getCell(7).getNumericCellValue());
            assertEquals(orderItem.getAmount().toString(), dataRow.getCell(8).getStringCellValue());
        }
    }
}
