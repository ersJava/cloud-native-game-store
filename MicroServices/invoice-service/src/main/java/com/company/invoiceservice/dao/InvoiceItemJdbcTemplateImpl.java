package com.company.invoiceservice.dao;

import com.company.invoiceservice.model.InvoiceItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class InvoiceItemJdbcTemplateImpl implements InvoiceItemDao {

    private static final String INSERT_ITEM_SQL =
            "insert into invoice_item (invoice_id, inventory_id, quantity, unit_price) values (?, ?, ?, ?)";

    private static final String SELECT_ITEM_SQL =
            "select * from invoice_item where invoice_item_id = ?";

    private static final String SELECT_ALL_ITEMS_SQL =
            "select * from invoice_item";

    private static final String UPDATE_ITEM_SQL =
            "update invoice_item set invoice_id = ?, inventory_id = ?, quantity = ?, unit_price = ? where invoice_item_id = ?";

    private static final String DELETE_ITEM_SQL =
            "delete from invoice_item where invoice_item_id =?";

    private static final String SELECT_ITEMS_BY_INVOICE_ID_SQL =
            "select * from invoice_item where invoice_id = ?";

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public InvoiceItemJdbcTemplateImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private InvoiceItem mapRowToInvoiceItem(ResultSet rs, int rowNum) throws SQLException {

        InvoiceItem invoiceItem = new InvoiceItem();
        invoiceItem.setInvoiceItemId(rs.getInt("invoice_item_id"));
        invoiceItem.setInvoiceId(rs.getInt("invoice_id"));
        invoiceItem.setInventoryId(rs.getInt("inventory_id"));
        invoiceItem.setQuantity(rs.getInt("quantity"));
        invoiceItem.setUnitPrice(rs.getBigDecimal("unit_price"));

        return invoiceItem;
    }


    @Override
    public InvoiceItem addInvoiceItem(InvoiceItem item) {

        jdbcTemplate.update(INSERT_ITEM_SQL,
                item.getInvoiceId(),
                item.getInventoryId(),
                item.getQuantity(),
                item.getUnitPrice());
        int id = jdbcTemplate.queryForObject("select last_insert_id()", Integer.class);
        item.setInvoiceItemId(id);

        return item;
    }

    @Override
    public InvoiceItem getInvoiceItem(int id) {

        try {
            return jdbcTemplate.queryForObject(SELECT_ITEM_SQL, this::mapRowToInvoiceItem, id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<InvoiceItem> getAllItems() {

        return jdbcTemplate.query(SELECT_ALL_ITEMS_SQL, this::mapRowToInvoiceItem);
    }

    @Override
    public void updateItem(InvoiceItem invoiceItem) {

        jdbcTemplate.update(UPDATE_ITEM_SQL,
                invoiceItem.getInvoiceId(),
                invoiceItem.getInventoryId(),
                invoiceItem.getQuantity(),
                invoiceItem.getUnitPrice(),
                invoiceItem.getInvoiceItemId());

    }

    @Override
    public void deleteItem(int id) {

        jdbcTemplate.update(DELETE_ITEM_SQL, id);
    }

    @Override
    public List<InvoiceItem> getItemsByInvoiceId(int invoiceId) {

        return jdbcTemplate.query(SELECT_ITEMS_BY_INVOICE_ID_SQL, this::mapRowToInvoiceItem, invoiceId);

    }
}
