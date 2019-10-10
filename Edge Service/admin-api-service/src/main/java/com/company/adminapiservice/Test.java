package com.company.adminapiservice;

import com.company.adminapiservice.viewmodel.InvoiceItem;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Test {

    InvoiceItem invoiceItem2 = new InvoiceItem();

    public static void main(String[] args){

        InvoiceItem invoiceItem = new InvoiceItem();
        invoiceItem.setInvoiceId(1);
        invoiceItem.setInventoryId(2);
        invoiceItem.setQuantity(3);

        //otro
        InvoiceItem invoiceItem3 = new InvoiceItem();
        invoiceItem3.setInvoiceId(1);
        invoiceItem3.setInventoryId(2);
        invoiceItem3.setQuantity(1);

        //another
        InvoiceItem invoiceItem7 = new InvoiceItem();
        invoiceItem7.setInvoiceId(1);
        invoiceItem7.setInventoryId(2);
        invoiceItem7.setQuantity(1);

        //another
        InvoiceItem invoiceItem2 = new InvoiceItem();
        invoiceItem2.setInvoiceId(1);
        invoiceItem2.setInventoryId(3);
        invoiceItem2.setQuantity(1);

        //another
        InvoiceItem invoiceItem4 = new InvoiceItem();
        invoiceItem4.setInvoiceId(1);
        invoiceItem4.setInventoryId(3);
        invoiceItem4.setQuantity(1);

        //another
        InvoiceItem invoiceItem5 = new InvoiceItem();
        invoiceItem5.setInvoiceId(1);
        invoiceItem5.setInventoryId(4);
        invoiceItem5.setQuantity(1);

        //another
        InvoiceItem invoiceItem6 = new InvoiceItem();
        invoiceItem6.setInvoiceId(1);
        invoiceItem6.setInventoryId(5);
        invoiceItem6.setQuantity(1);

        //another
        InvoiceItem invoiceItem8 = new InvoiceItem();
        invoiceItem8.setInvoiceId(1);
        invoiceItem8.setInventoryId(5);
        invoiceItem8.setQuantity(3);

        //another
        InvoiceItem invoiceItem9 = new InvoiceItem();
        invoiceItem9.setInvoiceId(1);
        invoiceItem9.setInventoryId(5);
        invoiceItem9.setQuantity(3);


        List<InvoiceItem> itemList = new ArrayList<>();
        itemList.add(invoiceItem);
        itemList.add(invoiceItem2);
        itemList.add(invoiceItem3);
        itemList.add(invoiceItem4);
        itemList.add(invoiceItem5);
        itemList.add(invoiceItem6);
        itemList.add(invoiceItem7);
        itemList.add(invoiceItem8);
        itemList.add(invoiceItem9);

        List<InvoiceItem> anotherList = new ArrayList<>();
        anotherList.add(invoiceItem);
        anotherList.add(invoiceItem2);
        anotherList.add(invoiceItem9);

        Test n = new Test();
        //List<InvoiceItem> filtered = n.filterInvoiceItemList(anotherList);

        List<InvoiceItem> invoiceItemsList = n.filterInvoiceItemList(itemList);

        System.out.println("It works?");

    }

    public List<InvoiceItem> filterInvoiceItemList(List<InvoiceItem> itemList){

        //List with uniques inventoryId
        Set<Integer> inventoryId = new HashSet<>();

        for(int i = 0; i < itemList.size(); i++){

            InvoiceItem iItem = itemList.get(i);
            Integer inId = iItem.getInventoryId();

            if(inventoryId.add(inId) == false){

                int quantity = iItem.getQuantity();

                for(int x = 0; x < itemList.size(); x++){

                    InvoiceItem i2 = itemList.get(x);

                    if(i2.getInventoryId() == inId){

                        i2.setQuantity(i2.getQuantity() + quantity);
                        itemList.remove(i);

                        //To break the loop
                        x = itemList.size();
                    }
                }
                i = -1;
                inventoryId.clear();
            }
        }
        return itemList;
    }
}

 //   Set<Integer> inventoryId = new HashSet<>();
//
//        for(int i = 0; i < itemList.size(); i++){
//            InvoiceItem ii = itemList.get(i);
//            Integer inId = ii.getInventoryId();
//
//            if(inventoryId.add(inId) == false){
//                int quantity = ii.getQuantity();
//                for(int x = 0; x < itemList.size(); x++){
//                    InvoiceItem i2 = itemList.get(x);
//                    if(i2.getInventoryId() == inId){
//                        i2.setQuantity(i2.getQuantity() + quantity);
//                        itemList.remove(i);
//                        x = itemList.size();
//                    }
//                }
//                i = -1;
//                inventoryId.clear();
//            }
//        }
