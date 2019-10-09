package com.company.productservice.dao;

import com.company.productservice.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ProductDaoJdbcTemplateImpl implements ProductDao {
    //Prepared Statements
    public static final String INSERT_PRODUCT =
            "insert into product (product_name, product_description, list_price, unit_cost) values (?, ?, ?, ?)";

    public static final String SELECT_PRODUCT_BY_ID =
            "select * from product where product_id = ?";

    public static final String SELECT_ALL_PRODUCTS=
            "select * from product";

    public static final String SELECT_ALL_PRODUCTS_BY_PRODUCT_NAME=
            "select * from product where product_name = ?";

    public static final String UPDATE_PRODUCT =
            "update product set product_name = ?, product_description = ?, list_price = ?, unit_cost = ? where product_id = ?";

    public static final String DELETE_PRODUCT =
            "delete from product where product_id = ?";

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public ProductDaoJdbcTemplateImpl(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    //ServiceLayer method newProduct
    @Override
    public Product createProduct(Product product) {
        jdbcTemplate.update(INSERT_PRODUCT,

                product.getProductName(),
                product.getProductDescription(),
                product.getListPrice(),
                product.getUnitCost()
                );

        int id = jdbcTemplate.queryForObject("select LAST_INSERT_ID()", Integer.class);
        product.setProductId(id);

        return product;
    }

    //ServiceLayer method getProduct
    @Override
    public Product readProduct(int productId) {
        try{
            return jdbcTemplate.queryForObject(SELECT_PRODUCT_BY_ID, this::mapRowToProduct, productId);
        }catch (EmptyResultDataAccessException e){
            return null;
        }
    }

    //ServiceLayer method getAllProducts
    @Override
    public List<Product> readAllProducts() {
        return jdbcTemplate.query(SELECT_ALL_PRODUCTS,this::mapRowToProduct);
    }

    @Override
    public void updateProduct(Product product) {
        jdbcTemplate.update(UPDATE_PRODUCT,
                product.getProductName(),
                product.getProductDescription(),
                product.getListPrice(),
                product.getUnitCost(),
                product.getProductId()
                );
    }

    //ServiceLayer method deleteProduct
    @Override
    public void deleteProduct(int productId) {
        jdbcTemplate.update(DELETE_PRODUCT, productId);
    }

    //ServiceLayer method getProductsByProductName
    @Override
    public List<Product> getProductsByProductName(String productName) {
        return jdbcTemplate.query(SELECT_ALL_PRODUCTS_BY_PRODUCT_NAME, this::mapRowToProduct, productName);
    }

    //Helper method
    private Product mapRowToProduct(ResultSet rs, int rowNum) throws SQLException {

        Product product = new Product();

        product.setProductId(rs.getInt("product_id"));
        product.setProductName(rs.getString("product_name"));
        product.setProductDescription(rs.getString("product_description"));
        product.setListPrice(rs.getBigDecimal("list_price"));
        product.setUnitCost(rs.getBigDecimal("unit_cost"));

        return product;
    }
}
