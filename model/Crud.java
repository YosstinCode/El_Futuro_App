package model;

import model.models.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Locale;

public class Crud {
    private static Connection connection;

    public static Connection setConnection(Connection connection) {
        Crud.connection = connection;
        return connection;
    }
    //CREATE
    public static boolean register(String nombre, int cantidad, String categoria,double precio){
            //vaiables
            // boolean for detect if all was fine
            boolean success = false;
            //variable that prepare a query for execute in moment after
            PreparedStatement query = null;

            //SQL for execute
            String sql = "INSERT INTO productos(nombre,cantidad,categoria,precio) VALUES (?,?,?,?)";

            try {
                query = connection.prepareStatement(sql);
                //add values of the query
                query.setString(1,nombre);
                query.setInt(2,cantidad);
                query.setString(3,categoria);
                query.setDouble(4,precio);

                //execute query
                query.execute();

                //so True beacuse was fine
                success = true;
            } catch (SQLException e) {
                System.out.println("CONNECTION| "+e);
            }

            //return if was fine or not
            return success;
        }

    //READ

        //get all product
        public static ArrayList<Product> read(){

            ArrayList<Product> products = new ArrayList<>();

            ResultSet result = null;

            String sql = "SELECT * FROM productos;";
            try {
                result = connection.prepareStatement(sql).executeQuery();

                //Filling the array products with info from db
                while (result.next()) {
                    Product product = new Product(result.getInt(1),result.getString(2), result.getInt(3),
                            result.getString(4), result.getDouble(5));
                    products.add(product);
                }

            } catch (SQLException e) {
                System.out.println("CONNECTION| "+e);
            }
            finally {
                try {
                    if (result != null) {
                        result.close();
                    }
                } catch (SQLException e) {
                    System.out.println("CONNECTION| "+e);
                }
            }
            return products;
        }
        //get product according to filter
        public static ArrayList<Product> consult(String sql){
            ArrayList<Product> products = new ArrayList<>();

            ResultSet result = null;

            try {
                result = connection.prepareStatement(sql).executeQuery();

                //Filling the array products with info from db
                while (result.next()) {
                    Product product = new Product(result.getInt(1),result.getString(2), result.getInt(3),
                            result.getString(4), result.getDouble(5));
                    products.add(product);
                }

            } catch (SQLException e) {
                System.out.println("CONNECTION| "+e);
            }
            finally {
                try {
                    if (result != null) {
                        result.close();
                    }
                } catch (SQLException e) {
                    System.out.println("CONNECTION| "+e);
                }
            }
            return products;
        }

    //UPDATE
    public static boolean update(Product product){
        PreparedStatement query = null;
        String sql = String.format(Locale.ROOT,"UPDATE Productos SET nombre = '%s', Cantidad = %d, categoria = '%s', precio = %.2f WHERE Id = %d",
                product.getNombre(),product.getCantidad(),product.getCategoria(),product.getPrecio(), product.getId());
        try {
            query = connection.prepareStatement(sql);
            query.execute();
            return true;
        } catch (SQLException e) {
            System.out.println("CONNECTION| "+e);
            return false;
        }
    }
    //DELETE
    public static void delete(int id){
            //prepare Statement for data base
            PreparedStatement query = null;
            //code SQL
            String sql = String.format("DELETE FROM Productos WHERE Id=%d",id);
            try{
                //connect query and sql with database
                query = connection.prepareStatement(sql);
                //execute query and return result
                query.execute();
            } catch (SQLException e) {
                System.out.println("CONNECTION| "+e);
            }
        }
}