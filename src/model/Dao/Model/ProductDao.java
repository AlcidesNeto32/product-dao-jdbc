package model.Dao.Model;

import model.entities.Product;

import java.util.List;

public interface ProductDao {
    void insertProduct(Product product);
    void updateProduct(int id,Product product);
    void deleteProduct(int id);
    Product findById (int id);
    List<Product> showAllProducts();
}
