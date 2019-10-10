package com.company.invoiceservice.dao;

import com.company.invoiceservice.model.Invoice;
import com.company.invoiceservice.model.InvoiceItem;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class InvoiceItemJdbcTemplateImplTest {

    @Autowired
    InvoiceItemDao invoiceItemDao;

    @Autowired
    InvoiceDao invoiceDao;

    @Before
    public void setUp() throws Exception {

        List<InvoiceItem> itemList = invoiceItemDao.getAllItems();
        itemList.forEach(invoiceItem -> invoiceItemDao.deleteItem(invoiceItem.getInvoiceItemId()));

        List<Invoice> invoiceList = invoiceDao.getAllInvoices();
        invoiceList.forEach(invoice -> invoiceDao.deleteInvoice(invoice.getInvoiceId()));

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void addGetDeleteInvoiceItem() {

        Invoice invoice = new Invoice();
        invoice.setCustomerId(10);
        invoice.setPurchaseDate(LocalDate.of(2019, 10, 15));
        invoiceDao.addInvoice(invoice);

        InvoiceItem invoiceItem = new InvoiceItem();
        invoiceItem.setInvoiceId(invoice.getInvoiceId());
        invoiceItem.setInventoryId(40001);
        invoiceItem.setQuantity(3);
        invoiceItem.setUnitPrice(new BigDecimal("4.99"));
        invoiceItemDao.addInvoiceItem(invoiceItem);

        InvoiceItem fromService = invoiceItemDao.getInvoiceItem(invoiceItem.getInvoiceItemId());

        assertEquals(fromService, invoiceItem);

        invoiceItemDao.deleteItem(invoiceItem.getInvoiceItemId());

        fromService = invoiceItemDao.getInvoiceItem(invoiceItem.getInvoiceItemId());

        assertNull(fromService);

    }

    @Test
    public void getAllItems() {

        Invoice invoice = new Invoice();
        invoice.setCustomerId(10);
        invoice.setPurchaseDate(LocalDate.of(2019, 10, 15));
        invoiceDao.addInvoice(invoice);

        InvoiceItem invoiceItem = new InvoiceItem();
        invoiceItem.setInvoiceId(invoice.getInvoiceId());
        invoiceItem.setInventoryId(40001);
        invoiceItem.setQuantity(3);
        invoiceItem.setUnitPrice(new BigDecimal("4.99"));
        invoiceItemDao.addInvoiceItem(invoiceItem);

        List<InvoiceItem> list = invoiceItemDao.getAllItems();
        assertEquals(list.size(), 1);

    }

    @Test
    public void getItemByInvoiceId() {

        Invoice invoice = new Invoice();
        invoice.setCustomerId(10);
        invoice.setPurchaseDate(LocalDate.of(2019, 10, 15));
        invoiceDao.addInvoice(invoice);

        InvoiceItem invoiceItem = new InvoiceItem();
        invoiceItem.setInvoiceId(invoice.getInvoiceId());
        invoiceItem.setInventoryId(40001);
        invoiceItem.setQuantity(3);
        invoiceItem.setUnitPrice(new BigDecimal("4.99"));
        invoiceItemDao.addInvoiceItem(invoiceItem);

        List<InvoiceItem> list = invoiceItemDao.getItemsByInvoiceId(invoice.getInvoiceId());
        assertEquals(list.size(), 1);
    }
}