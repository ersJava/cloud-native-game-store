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

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class InvoiceDaoJdbcTemplateImplTest {

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
    public void addGetDeleteInvoice() {

        Invoice invoice = new Invoice();
        invoice.setCustomerId(10);
        invoice.setPurchaseDate(LocalDate.of(2019, 10, 15));

        invoice = invoiceDao.addInvoice(invoice);

        Invoice fromService = invoiceDao.getInvoice(invoice.getInvoiceId());
        assertEquals(fromService, invoice);

        invoiceDao.deleteInvoice(invoice.getInvoiceId());
        fromService = invoiceDao.getInvoice(invoice.getInvoiceId());
        assertNull(fromService);
    }

    @Test
    public void getAllInvoices() {

        Invoice invoice = new Invoice();
        invoice.setCustomerId(10);
        invoice.setPurchaseDate(LocalDate.of(2019, 10, 15));

        invoiceDao.addInvoice(invoice);

        List<Invoice> list = invoiceDao.getAllInvoices();
        assertEquals(list.size(), 1);

    }

    @Test
    public void getInvoicesByCustomerId() {

        Invoice invoice = new Invoice();
        invoice.setCustomerId(10);
        invoice.setPurchaseDate(LocalDate.of(2019, 10, 15));
        invoiceDao.addInvoice(invoice);

        List<Invoice> list = invoiceDao.getInvoicesByCustomerId(10);
        assertEquals(1, list.size());

    }
}