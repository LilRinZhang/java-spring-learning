package com.example.shopping.repository;

// import java.sql.Connection;
// import java.sql.PreparedStatement;
// import java.sql.ResultSet;
// import java.sql.SQLException;

// import javax.sql.DataSource;

import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.shopping.entity.Product;

@Repository
public class JdbcProductRepository implements ProductRepository {
    // private final DataSource dataSource;

    // public JdbcProductRepository(DataSource dataSource) {
    // this.dataSource = dataSource;
    // }

    private final JdbcTemplate jdbcTemplate;

    public JdbcProductRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Product selectById(String id) {
        Product product = jdbcTemplate.queryForObject("SELECT * FROM t_product WHERE id=?",
                new DataClassRowMapper<>(Product.class), id);
        return product;
        // try (
        // Connection con = dataSource.getConnection();
        // PreparedStatement stmt
        // = con.prepareStatement("SELECT * FROM t_product WHERE id=?");
        // ) {
        // stmt.setString(1, id);
        // ResultSet rs = stmt.executeQuery();
        // if (!rs.next()) {
        // rs.close();
        // return null;
        // }
        // Product product = new Product();
        // product.setId(rs.getString("id"));
        // product.setName(rs.getString("name"));
        // product.setPrice(rs.getInt("price"));
        // product.setStock(rs.getInt("stock"));
        // rs.close();
        // return product;
        // } catch (SQLException ex) {
        // throw new RuntimeException("SELECTに失敗", ex);
        // }
    }

    @Override
    public boolean update(Product product) {
        int result = jdbcTemplate.update("UPDATE t_product SET name=?, price=?, stock=? WHERE id=?", product.getName(),
                product.getPrice(), product.getStock(), product.getId());
        return !(result == 0);
        // try (
        // Connection con = dataSource.getConnection();
        // PreparedStatement stmt
        // = con.prepareStatement("UPDATE t_product SET name=?, price=?, stock=? WHERE
        // id=?");
        // ) {
        // stmt.setString(1, product.getName());
        // stmt.setInt(2, product.getPrice());
        // stmt.setInt(3, product.getStock());
        // stmt.setString(4, product.getId());
        // int count = stmt.executeUpdate();
        // if (count == 0) {
        // return false;
        // }
        // return true;
        // } catch (SQLException ex) {
        // throw new RuntimeException("UPDATEに失敗", ex);
        // }
    }
}
