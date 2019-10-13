package com.company.invoiceservice.service;

import com.company.invoiceservice.dao.InvoiceDao;
import com.company.invoiceservice.dao.InvoiceItemDao;
import com.company.invoiceservice.exception.NotFoundException;
import com.company.invoiceservice.model.Invoice;
import com.company.invoiceservice.model.InvoiceItem;
import com.company.invoiceservice.viewmodel.InvoiceItemViewModel;
import com.company.invoiceservice.viewmodel.InvoiceViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ServiceLayer {

    private InvoiceDao invoiceDao;
    private InvoiceItemDao invoiceItemDao;

    @Autowired
    public ServiceLayer(InvoiceDao invoiceDao, InvoiceItemDao invoiceItemDao) {

        this.invoiceDao = invoiceDao;
        this.invoiceItemDao = invoiceItemDao;
    }


    private InvoiceItemViewModel buildItemViewModel(InvoiceItem item) {

        InvoiceItemViewModel ivm = new InvoiceItemViewModel();
        ivm.setInvoiceItemId(item.getInvoiceItemId());
        ivm.setInvoiceId(item.getInvoiceId());
        ivm.setInventoryId(item.getInventoryId());
        ivm.setQuantity(item.getQuantity());
        ivm.setUnitPrice(item.getUnitPrice());

        return ivm;
    }

    // * * * * * * Invoice * * * * * * *
    private InvoiceViewModel buildInvoiceViewModel(Invoice invoice) {

        List<InvoiceItem> itemList = invoiceItemDao.getItemsByInvoiceId(invoice.getInvoiceId());

        List<InvoiceItemViewModel> ivmList = new ArrayList<>();

        for(InvoiceItem i : itemList){
            InvoiceItemViewModel ivm = buildItemViewModel(i);
            ivmList.add(ivm);
        }

        InvoiceViewModel viewModel = new InvoiceViewModel();
        viewModel.setInvoiceId(invoice.getInvoiceId());
        viewModel.setCustomerId(invoice.getCustomerId());
        viewModel.setPurchaseDate(invoice.getPurchaseDate());
        viewModel.setItemList(ivmList);

        return viewModel;
    }

    @Transactional
    public InvoiceViewModel saveInvoice(InvoiceViewModel viewModel) {

        Invoice invoice = new Invoice();
        invoice.setCustomerId(viewModel.getCustomerId());
        invoice.setPurchaseDate(viewModel.getPurchaseDate());
        invoice = invoiceDao.addInvoice(invoice);
        viewModel.setInvoiceId(invoice.getInvoiceId());

        List<InvoiceItemViewModel> itemList = viewModel.getItemList();

        itemList.stream().forEach(item->
        {
            InvoiceItem invoiceItem = new InvoiceItem();
            invoiceItem.setInvoiceId(viewModel.getInvoiceId());
            invoiceItem.setInventoryId(item.getInventoryId());
            invoiceItem.setQuantity(item.getQuantity());
            invoiceItem.setUnitPrice(item.getUnitPrice());
            invoiceItem= invoiceItemDao.addInvoiceItem(invoiceItem);
            item.setInvoiceItemId(invoiceItem.getInvoiceItemId());
            item.setInvoiceId(viewModel.getInvoiceId());
        });

        viewModel.setItemList(itemList);

        return viewModel;
    }

    public InvoiceViewModel findInvoice(Integer id) {

        Invoice invoice = invoiceDao.getInvoice(id);

        if(invoice == null)
            throw new NotFoundException(String.format("Invoice could not be retrieved for id %s", id));
        else
            return buildInvoiceViewModel(invoice);
    }

    public List<InvoiceViewModel> findAllInvoices() {

        List<Invoice> invoiceList = invoiceDao.getAllInvoices();

        if(invoiceList.size() == 0){
            throw new NotFoundException("No invoices found, the Database is empty!!");
        }

        List<InvoiceViewModel> ivmList = new ArrayList<>();

        for (Invoice i : invoiceList) {

            InvoiceViewModel ivm = buildInvoiceViewModel(i);

            ivmList.add(ivm);
        }


        return ivmList;
    }

    @Transactional
    public void updateInvoice(InvoiceViewModel viewModel) {

        Invoice invoice = new Invoice();
        invoice.setInvoiceId(viewModel.getInvoiceId());
        invoice.setCustomerId(viewModel.getCustomerId());
        invoice.setPurchaseDate(viewModel.getPurchaseDate());

        invoiceDao.updateInvoice(invoice);

        List<InvoiceItem> itemList = invoiceItemDao.getItemsByInvoiceId(invoice.getInvoiceId());
        itemList.forEach(item -> invoiceItemDao.deleteItem(item.getInvoiceItemId()));

        List<InvoiceItemViewModel> items = viewModel.getItemList();
        items.stream().forEach(item -> {

            InvoiceItem invoiceItem = new InvoiceItem();
            invoiceItem.setInvoiceId(viewModel.getInvoiceId());
            invoiceItem.setInventoryId(item.getInventoryId());
            invoiceItem.setQuantity(item.getQuantity());
            invoiceItem.setUnitPrice(item.getUnitPrice());
            invoiceItem = invoiceItemDao.addInvoiceItem(invoiceItem);
            item.setInvoiceItemId(invoiceItem.getInvoiceItemId());
            item.setInvoiceId(viewModel.getInvoiceId());
        });

        viewModel.setItemList(items);

    }

    public void removeInvoice(Integer id) {

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

                InvoiceViewModel ivm = buildInvoiceViewModel(i);

                ivmList.add(ivm);
            }
            return ivmList;
    }


    public List<InvoiceItemViewModel> getInvoiceItemByInventoryId(int inventoryId){


        //Getting all the invoices
        List<InvoiceViewModel> allInvoices =  findAllInvoices();

        List<List<InvoiceItemViewModel>> allInvoiceItemsList = new ArrayList<>();

        List<InvoiceItemViewModel> invoiceItems = new ArrayList<>();

        allInvoices.stream().forEach(invoiceViewModel -> allInvoiceItemsList.add(invoiceViewModel.getItemList()));


        for (List<InvoiceItemViewModel> iiList: allInvoiceItemsList) {

            for (InvoiceItemViewModel ii: iiList) {
              
                invoiceItems.add(ii);
            }
        }

        //Getting the invoice Items related to one InventoryId
        List<InvoiceItemViewModel> invoiceItemsForInventoryId = invoiceItems.stream().filter(invoiceItem -> invoiceItem.getInventoryId() == inventoryId).collect(Collectors.toList());


        if(invoiceItemsForInventoryId.size() == 0){
            throw new NotFoundException("No Invoice Items for the specified inventoryId");
        }

        return invoiceItemsForInventoryId;
    }
}
