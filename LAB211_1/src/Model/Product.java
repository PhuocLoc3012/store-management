/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;
import java.io.Serializable;

/**
 *
 * @author Asus
 */
public class Product implements Serializable{
    protected String code;//protected
    protected String name;
    protected int quantity;


    public Product(String code, String name) {
        this.code = code;       
        this.name = name;
    }

    public Product() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
  
// ko viet them gi vi lop the hien la lop con . lớp cơ sở
    @Override
    public String toString() {
        return "| Code: " + code + "| Name: "+ name+ "  | Quantity: " + quantity;         
    }




    
}
