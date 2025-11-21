package model.Dao;

import model.entities.Product;

import java.util.List;

public interface ProductDao {
    void insertProduct(Product product);
    void updateProduct(int id);
    void deleteProduct(int id);
    Product findById (int id);
    List<Product> showAllProducts();
}
