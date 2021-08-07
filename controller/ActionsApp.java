package controller;

import model.Crud;
import model.models.Product;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

public class ActionsApp {
    //CREATE
    public static String[] registerProducts(String nombre,String cantidad,String categoria,String precio){

        //count errors for verify if create or not create product
        int countErrors = 0;

        //vector where save text error for labels
        String[] errorsText = new String[5];

        //set position four with "" to avoid nullpointer
        errorsText[4] = "";

        //boolean vefify register or not register product
        boolean successRegister;


        //validation for type of the data
        errorsText[0] = (nombre.isBlank())? "¡Tienes que ingresar un nombre!" : "";
        errorsText[1] = (cantidad.isBlank())? "¡Tienes que ingresar un cantidad!" : "";
        errorsText[1] = (isNotNumeric(cantidad,"int"))? "¡La cantidad tiene que ser un numero!" : "";
        errorsText[2] = (categoria.isBlank())? "¡Tienes que ingresar un categoria!" : "";
        errorsText[3] = (precio.isBlank())? "¡Tienes que ingresar un precio!" : "";
        errorsText[3] = (isNotNumeric(precio,"double"))? "¡El precio tiene que ser un numero!" : "";

        //create product
        for(String error: errorsText) {
            if (!error.equals("")) {
                countErrors++;
                break;
            }
        }

        //verify that not have errors
        if(countErrors == 0) {
            //create product
            Product product = new Product(nombre, Integer.parseInt(cantidad), categoria, Double.parseDouble(precio));
            // pass to model for register product in the database
            successRegister = Crud.register(product.getNombre(), product.getCantidad(), product.getCategoria(), product.getPrecio());
            errorsText[4] = (successRegister)? "¡El producto ha sido registrado con exito!" : "Lo sentimos ha ocurrido un fallo.";
        }

        return errorsText;

    }
    //UPDATE
    public static String[] updateProducts(String id,String nombre,String cantidad,String categoria,String precio){

        //count errors for verify if create or not create product
        int countErrors = 0;

        //vector where save text error for labels
        String[] errorsText = new String[6];

        //set position four with "" to avoid nullpointer
        errorsText[4] = "";

        //boolean vefify register or not register product
        boolean successRegister;


        //validation for type of the data

        errorsText[0] = (nombre.isBlank())? "¡Tienes que ingresar un nombre!" : "";
        errorsText[1] = (cantidad.isBlank())? "¡Tienes que ingresar un cantidad!" : "";
        errorsText[1] = (isNotNumeric(cantidad,"int"))? "¡La cantidad tiene que ser un numero!" : "";
        errorsText[2] = (categoria.isBlank())? "¡Tienes que ingresar un categoria!" : "";
        errorsText[3] = (precio.isBlank())? "¡Tienes que ingresar un precio!" : "";
        errorsText[3] = (isNotNumeric(precio,"double"))? "¡El precio tiene que ser un numero!" : "";

        //id is position five because was last in be added
        errorsText[5] = (nombre.isBlank())?"¡Tienes que ingresar un id!": "";


        //create product
        for(String error: errorsText) {
            if (!error.equals("")) {
                countErrors++;
                break;
            }
        }

        //verify that not have errors
        if(countErrors == 0) {
            //create product
            Product product = new Product(Integer.parseInt(id),nombre, Integer.parseInt(cantidad), categoria, Double.parseDouble(precio));
            // pass to model for update product in the database
            successRegister =Crud.update(product);
            errorsText[4] = (successRegister)? "¡El producto ha sido Actualizado!" : "Lo sentimos ha ocurrido un fallo.";
        }


        return errorsText;
    }
    //READ
    public static ArrayList<Product> listProduct(){
       return Crud.read();
    }
    public static ArrayList<Product> consultProducts(String id, String nombre,String categoria,String cantidad,String precio){
        boolean whereExist=false;
        String sql = "SELECT * FROM productos";
        //verify if id exist for change variable whereExist
        if(!id.isBlank() && !isNotNumeric(id,"int")){
            sql += " WHERE id = " + Integer.parseInt(id);
            whereExist = true;
        }


        if(whereExist && !nombre.isBlank()){
            sql += String.format(" AND nombre = '%s'",nombre);
        } else if(!whereExist && !nombre.isBlank()){
            sql += String.format(" WHERE nombre = '%s'",nombre);
            whereExist = true;
        }

        if(whereExist && !categoria.isBlank()){
            sql += String.format(" AND categoria = '%s'",categoria);
        }else if(!whereExist && !categoria.isBlank()){
            sql += String.format(" WHERE categoria = '%s'",categoria);
            whereExist = true;
        }

        if(whereExist && !cantidad.isBlank() && !isNotNumeric(cantidad,"int")){
            sql += String.format(" AND cantidad = %d",Integer.parseInt(cantidad));
        }else if(!whereExist && !cantidad.isBlank() && !isNotNumeric(cantidad,"int")){
            sql += String.format(" WHERE cantidad = %d",Integer.parseInt(cantidad));
            whereExist = true;
        }

        if(whereExist && !precio.isBlank() && !isNotNumeric(precio,"double")){
            sql += String.format(Locale.ROOT," AND precio = %f",Double.parseDouble(precio));
        } else if(!whereExist && !precio.isBlank() && !isNotNumeric(precio,"double")){
            sql += String.format(Locale.ROOT," WHERE precio = %f",Double.parseDouble(precio));
            whereExist = true;
        }

        return Crud.consult(sql+";");
    }
    //DELETE
    public static String deleteProducts(int id){
        Crud.delete(id);
        return "¡Su producto ha sido eliminado!";
    }

    //validations
    public static boolean isNotNumeric(String string, String type){

        if(type.equalsIgnoreCase("int")){

            try {
                Integer.parseInt(string);
            } catch (NumberFormatException nfe){
                return true;
            }
        }else{

            try {
                Double.parseDouble(string);
            } catch (NumberFormatException nfe){
                return true;
            }
        }

        return false;
    }

}