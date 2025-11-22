package model.Dao.DaoImplementation;

import db.DB;
import db.DBException;
import model.Dao.Model.ProductDao;
import model.entities.Product;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProductDaoImpl implements ProductDao {
    Connection connection = null;

    public ProductDaoImpl(Connection connection) {
        this.connection = connection;
    }

    public ProductDaoImpl() {
    }

    @Override
    public void insertProduct(Product product) {
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement("insert into product " +
                    "(name,price,quantity,department) " +
                    "values " +
                    "(?,?,?,?) ", Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, product.getName());
            preparedStatement.setDouble(2, product.getPrice());
            preparedStatement.setInt(3, product.getQuantity());
            preparedStatement.setString(4, product.getDepartment());


            if (preparedStatement.executeUpdate() > 0) {
                JOptionPane.showMessageDialog(null, "Done! product: " + product.getName() + " added!"
                        , "Register Products", JOptionPane.PLAIN_MESSAGE);
                ResultSet resultSet = preparedStatement.getGeneratedKeys();
                while (resultSet.next()) {
                    //set the product id
                    product.setId(resultSet.getInt(1));
                }
                DB.closeResultSet(resultSet);
            } else {
                JOptionPane.showMessageDialog(null, "[ERROR] an error unexpected happen!", "Register products", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            throw new DBException(e.getMessage());
        } finally {
            DB.closePreparedStatement(preparedStatement);
        }
    }

    @Override
    public void updateProduct(int id, Product product) {
        PreparedStatement preparedStatement = null;

        try{
            preparedStatement = connection.prepareStatement("update product " +
                    "set " +
                    "name = ? , price = ? , quantity = ? , department = ? " +
                    "where id = ? ", Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1,product.getName());
            preparedStatement.setDouble(2,product.getPrice());
            preparedStatement.setInt(3,product.getQuantity());
            preparedStatement.setString(4,product.getDepartment());
            preparedStatement.setInt(5,id);

            if (preparedStatement.executeUpdate() > 0){
                JOptionPane.showMessageDialog(null,"Done! product " + product.getName() + " updated!","Update product",JOptionPane.PLAIN_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null,"[ERROR] this product does not exist!" , "Update product", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e){
             throw new DBException(e.getMessage());
        } finally {
            DB.closePreparedStatement(preparedStatement);
        }
    }

    @Override
    public void deleteProduct(int id) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement("delete from product " +
                    "where id = ? ");
            preparedStatement.setInt(1, id);
            if (preparedStatement.executeUpdate() > 0) {
                JOptionPane.showMessageDialog(null, "Done! product deleted!", "Delete product", JOptionPane.PLAIN_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "[ERROR] product does not exist!", "Delete product", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            throw new DBException(e.getMessage());
        } finally {
            DB.closePreparedStatement(preparedStatement);
        }
    }

    @Override
    public Product findById(int id) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = connection.prepareStatement("select * from product " +
                    " where id = ? ");
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Product product = new Product();
                product.setId(resultSet.getInt("id"));
                product.setName(resultSet.getString("name"));
                product.setPrice(resultSet.getDouble("price"));
                product.setQuantity(resultSet.getInt("quantity"));
                product.setDepartment(resultSet.getString("department"));
                return product;
            } else {
                JOptionPane.showMessageDialog(null, "[ERROR] this product does not exist!", "Find product by id", JOptionPane.PLAIN_MESSAGE);
                return null;
            }

        } catch (SQLException e) {
            throw new DBException(e.getMessage());
        } finally {
            DB.closePreparedStatement(preparedStatement);
        }
    }

    @Override
    public List<Product> showAllProducts() {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement("Select * from product ");
            resultSet = preparedStatement.executeQuery();
            List<Product> productList = new ArrayList<>();
            Product product = new Product();

            if (resultSet.next()) {
                product.setId(resultSet.getInt("id"));
                product.setName(resultSet.getString("name"));
                product.setPrice(resultSet.getDouble("price"));
                product.setQuantity(resultSet.getInt("quantity"));
                product.setDepartment(resultSet.getString("quantity"));
                productList.add(product);
                product = new Product();
                while (resultSet.next()) {
                    product.setId(resultSet.getInt("id"));
                    product.setName(resultSet.getString("name"));
                    product.setPrice(resultSet.getDouble("price"));
                    product.setQuantity(resultSet.getInt("quantity"));
                    product.setDepartment(resultSet.getString("quantity"));
                    productList.add(product);
                    product = new Product();
                }
                DB.closeResultSet(resultSet);
                return productList;
            } else {
                JOptionPane.showMessageDialog(null, "[ERROR] there are not products registered!", "Show storage", JOptionPane.ERROR_MESSAGE);
                return Collections.EMPTY_LIST;
            }
        } catch (SQLException e) {
            throw new DBException(e.getMessage());
        } finally {
            DB.closePreparedStatement(preparedStatement);
        }
    }
}
