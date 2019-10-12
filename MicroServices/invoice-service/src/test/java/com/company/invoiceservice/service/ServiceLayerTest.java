package com.company.invoiceservice.service;

import com.company.invoiceservice.dao.InvoiceDao;
import com.company.invoiceservice.dao.InvoiceDaoJdbcTemplateImpl;
import com.company.invoiceservice.dao.InvoiceItemDao;
import com.company.invoiceservice.dao.InvoiceItemJdbcTemplateImpl;
import com.company.invoiceservice.exception.NotFoundException;
import com.company.invoiceservice.model.Invoice;
import com.company.invoiceservice.model.InvoiceItem;
import com.company.invoiceservice.viewmodel.InvoiceItemViewModel;
import com.company.invoiceservice.viewmodel.InvoiceViewModel;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class ServiceLayerTest {

    private ServiceLayer servicelayer;

    private InvoiceDao invoiceDao;
    private InvoiceItemDao invoiceItemDao;

    @Before
    public void setUp() throws Exception {

        setUpInvoiceDaoMock();
        setUpInvoiceItemDaoMock();

        servicelayer = new ServiceLayer(invoiceDao, invoiceItemDao);
    }

    private void setUpInvoiceDaoMock() {

        invoiceDao = mock(InvoiceDaoJdbcTemplateImpl.class);

        //Invoice To Return
        Invoice invoice = new Invoice();
        invoice.setInvoiceId(1);
        invoice.setCustomerId(10);
        invoice.setPurchaseDate(LocalDate.of(2019, 10, 15));

        Invoice invoice1 = new Invoice();
        invoice1.setCustomerId(10);
        invoice1.setPurchaseDate(LocalDate.of(2019, 10, 15));

        // update Mock data
        Invoice updateInvoice = new Invoice();
        updateInvoice.setInvoiceId(2);
        updateInvoice.setCustomerId(20);
        updateInvoice.setPurchaseDate(LocalDate.of(2019, 10, 15));

        // Mock save
        doReturn(invoice).when(invoiceDao).addInvoice(invoice1);

        // Mock findAll
        List<Invoice> list = new ArrayList<>();
        list.add(invoice);
        doReturn(list).when(invoiceDao).getAllInvoices();

        // Mock findById
        doReturn(invoice).when(invoiceDao).getInvoice(1);

        // Mock update
        doNothing().when(invoiceDao).updateInvoice(updateInvoice);
        doReturn(updateInvoice).when(invoiceDao).getInvoice(2);

        // Mock delete
        doNothing().when(invoiceDao).deleteInvoice(55);
        doReturn(null).when(invoiceDao).getInvoice(55);

        // Custom Method

        // Mock findByCustomerId
        List<Invoice> list2 = new ArrayList<>();
        list2.add(invoice);
        doReturn(list2).when(invoiceDao).getInvoicesByCustomerId(10);

    }

    private void setUpInvoiceItemDaoMock() {

        invoiceItemDao = mock(InvoiceItemJdbcTemplateImpl.class);

        InvoiceItem invoiceItem = new InvoiceItem();
        invoiceItem.setInvoiceItemId(790);
        invoiceItem.setInvoiceId(1);
        invoiceItem.setInventoryId(40001);
        invoiceItem.setQuantity(3);
        invoiceItem.setUnitPrice(new BigDecimal("4.99"));

        InvoiceItem invoiceItem1 = new InvoiceItem();
        invoiceItem1.setInvoiceId(1);
        invoiceItem1.setInventoryId(40001);
        invoiceItem1.setQuantity(3);
        invoiceItem1.setUnitPrice(new BigDecimal("4.99"));

        // Mock addItem
        doReturn(invoiceItem).when(invoiceItemDao).addInvoiceItem(invoiceItem1);

        //A second Invoice Item for the List
        InvoiceItem invoiceItem2 = new InvoiceItem();
        invoiceItem2.setInvoiceItemId(970);
        invoiceItem2.setInvoiceId(1);
        invoiceItem2.setInventoryId(40002);
        invoiceItem2.setQuantity(3);
        invoiceItem2.setUnitPrice(new BigDecimal("4.99"));

        InvoiceItem invoiceItem3 = new InvoiceItem();
        invoiceItem3.setInvoiceItemId(791);
        invoiceItem3.setInvoiceId(2);
        invoiceItem3.setInventoryId(40001);
        invoiceItem3.setQuantity(3);
        invoiceItem3.setUnitPrice(new BigDecimal("4.99"));

        //A second Invoice Item for the List
        InvoiceItem invoiceItem4 = new InvoiceItem();
        invoiceItem4.setInvoiceItemId(971);
        invoiceItem4.setInvoiceId(2);
        invoiceItem4.setInventoryId(40002);
        invoiceItem4.setQuantity(3);
        invoiceItem4.setUnitPrice(new BigDecimal("4.99"));

        List<InvoiceItem> list = new ArrayList<>();
        list.add(invoiceItem);
        list.add(invoiceItem3);

//        List<InvoiceItem> list2 = new ArrayList<>();
//        list2.add(invoiceItem3);
//        list2.add(invoiceItem4);

        // Mock getItemByInvoiceId
        doReturn(list).when(invoiceItemDao).getItemsByInvoiceId(1);
//        doReturn(list2).when(invoiceItemDao).getItemsByInvoiceId(2);

       //  Mock data
//        InvoiceItem updateItem= new InvoiceItem();
//        updateItem.setInvoiceItemId(791);
//        updateItem.setInvoiceId(2);
//        updateItem.setInventoryId(40001);
//        updateItem.setQuantity(3);
//        updateItem.setUnitPrice(new BigDecimal("4.99"));

    }

    @Test
    public void saveFindInvoice() {

        InvoiceViewModel ivm = new InvoiceViewModel();
        ivm.setCustomerId(10);
        ivm.setPurchaseDate(LocalDate.of(2019, 10, 15));

        InvoiceItemViewModel item = new InvoiceItemViewModel();
        item.setInventoryId(40001);
        item.setQuantity(3);
        item.setUnitPrice(new BigDecimal("4.99"));

        List<InvoiceItemViewModel> list = new ArrayList<>();
        list.add(item);

        ivm.setItemList(list);

        ivm = servicelayer.saveInvoice(ivm);

        InvoiceViewModel fromService = servicelayer.findInvoice(ivm.getInvoiceId());

        assertEquals(fromService, ivm);

    }

    @Test(expected = NotFoundException.class)
    public void removeInvoice() {

        servicelayer.removeInvoice(55);

        InvoiceViewModel ivm = servicelayer.findInvoice(55);
    }

    @Test
    public void findAllInvoices() {

        List<InvoiceViewModel> list = servicelayer.findAllInvoices();
        assertEquals(1, list.size());

    }

    @Test
    public void getInvoiceItemByInventoryIdTest(){

        List<InvoiceItemViewModel> invoiceItems = servicelayer.getInvoiceItemByInventoryId(40001);

        assertEquals(invoiceItems.size(), 2);
    }

    @Test
    public void updateInvoice() {

        InvoiceViewModel ivmUpdate = new InvoiceViewModel();

        Invoice updateInvoice = new Invoice();
        updateInvoice.setInvoiceId(2);
        updateInvoice.setCustomerId(20);
        updateInvoice.setPurchaseDate(LocalDate.of(2019, 10, 15));
//
//        InvoiceItemViewModel iivmUpdate = new InvoiceItemViewModel();
//        iivmUpdate.setInvoiceItemId(791);
//        iivmUpdate.setInvoiceId(2);
//        iivmUpdate.setInventoryId(40001);
//        iivmUpdate.setQuantity(3);
//        iivmUpdate.setUnitPrice(new BigDecimal("4.99"));
//
//        List<InvoiceItemViewModel> invoiceItemViewModelList = new ArrayList<>();
//        invoiceItemViewModelList.add(iivmUpdate);
//
        ivmUpdate.setInvoiceId(updateInvoice.getInvoiceId());
        ivmUpdate.setCustomerId(updateInvoice.getCustomerId());
        ivmUpdate.setPurchaseDate(updateInvoice.getPurchaseDate());
//        ivmUpdate.setItemList(invoiceItemViewModelList);
//
        servicelayer.updateInvoice(ivmUpdate);

        InvoiceViewModel ivmAfterUpdate = servicelayer.findInvoice(ivmUpdate.getInvoiceId());

        assertEquals(ivmAfterUpdate, ivmUpdate);

    }

    @Test(expected = NotFoundException.class)
    public void findInvoicesByCustomerId() {

        List<InvoiceViewModel> list = servicelayer.findInvoicesByCustomerId(10);
        assertEquals(1, list.size());

        list = servicelayer.findInvoicesByCustomerId(999);
        assertEquals(list.size(), 0);
    }

}