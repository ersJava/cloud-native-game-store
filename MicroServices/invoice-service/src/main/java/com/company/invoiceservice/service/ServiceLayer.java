package com.company.invoiceservice.service;

import com.company.invoiceservice.dao.InvoiceDao;
import com.company.invoiceservice.dao.InvoiceItemDao;
import com.company.invoiceservice.exception.NotFoundException;
import com.company.invoiceservice.model.Invoice;
import com.company.invoiceservice.model.InvoiceItem;
import com.company.invoiceservice.viewmodel.InvoiceViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
public class ServiceLayer {

    private InvoiceDao invoiceDao;
    private InvoiceItemDao invoiceItemDao;

    @Autowired
    public ServiceLayer(InvoiceDao invoiceDao, InvoiceItemDao invoiceItemDao) {

        this.invoiceDao = invoiceDao;
        this.invoiceItemDao = invoiceItemDao;
    }

    // * * * * * * Invoice * * * * * * *
    private InvoiceViewModel buildViewModel(Invoice invoice) {

        List<InvoiceItem> itemList = invoiceItemDao.getItemsByInvoiceId(invoice.getInvoiceId());

        InvoiceViewModel viewModel = new InvoiceViewModel();
        viewModel.setInvoiceId(invoice.getInvoiceId());
        viewModel.setCustomerId(invoice.getCustomerId());
        viewModel.setPurchaseDate(invoice.getPurchaseDate());
        viewModel.setItemList(itemList);

        return viewModel;
    }

    @Transactional
    public InvoiceViewModel saveInvoice(InvoiceViewModel viewModel) {

        Invoice invoice = new Invoice();
        invoice.setCustomerId(viewModel.getCustomerId());
        invoice.setPurchaseDate(viewModel.getPurchaseDate());
        invoice = invoiceDao.addInvoice(invoice);
        viewModel.setInvoiceId(invoice.getInvoiceId());

        List<InvoiceItem> itemList = viewModel.getItemList();

        itemList.forEach(item->
                        {
                            item.setInvoiceId(viewModel.getInvoiceId());
                            invoiceItemDao.addInvoiceItem(item);
                        });
        itemList = invoiceItemDao.getItemsByInvoiceId(invoice.getInvoiceId());
        viewModel.setItemList(itemList);

        return viewModel;

    }

    public InvoiceViewModel findInvoice(int id) {

        Invoice invoice = invoiceDao.getInvoice(id);

        if(invoice == null)
            throw new NotFoundException(String.format("Invoice could not be retrieved for id %s", id));
        else
            return buildViewModel(invoice);
    }

    public List<InvoiceViewModel> findAllInvoices() {

        List<Invoice> invoiceList = invoiceDao.getAllInvoices();

        List<InvoiceViewModel> ivmList = new ArrayList<>();

        for (Invoice i : invoiceList) {
            InvoiceViewModel ivm = buildViewModel(i);
            ivmList.add(ivm);
        }
        return ivmList;
    }

    public void removeInvoice(int id) {

        Invoice invoice = invoiceDao.getInvoice(id);

        if(invoice == null)
            throw new NotFoundException(String.format("Invoice could not be retrieved for id %s", id));

        List<InvoiceItem> list = invoiceItemDao.getItemsByInvoiceId(invoice.getInvoiceId());
        list.forEach(item -> invoiceItemDao.deleteItem(item.getInvoiceItemId()));

        invoiceDao.deleteInvoice(id);
    }

    //  ---- Custom Method ----
    // Get an Invoice by Customer Id
    public List<InvoiceViewModel> findInvoicesByCustomerId(int customerId) {

        List<Invoice> list = invoiceDao.getInvoicesByCustomerId(customerId);

        List<InvoiceViewModel> ivmList = new ArrayList<>();

        if(list != null && list.size() == 0)
            throw new NotFoundException(String.format("No invoices in the system found with customer id %s", customerId));
        else
            for (Invoice i : list){
                InvoiceViewModel ivm = buildViewModel(i);
                ivmList.add(ivm);
            }

            return ivmList;
    }

}
