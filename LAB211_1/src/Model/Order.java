/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;



import java.io.Serializable;
import java.util.HashMap;

/**
 *
 * @author Asus
 */
public class Order implements Serializable{
    
    private String codeOrder;
    private String type; 
    private String orderDate;    
    private HashMap<String, Integer> listItem = new HashMap<>();
    //Key is Product code, Value is quantity
    
    public Order(){
        
    }
    
    public Order(String codeOrder, String type, String orderDate) {
        this.codeOrder = codeOrder;
        this.type = type;
        this.orderDate = orderDate;
    }

    public String getCodeOrder() {
        return codeOrder;
    }

    public void setCodeOrder(String codeOrder) {
        this.codeOrder = codeOrder;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public HashMap<String, Integer> getListItem() {
        return listItem;
    }

    public void setListItem(HashMap<String, Integer> listItem) {
        this.listItem = listItem;
    }

    @Override
    public String toString() {
        return "Receipt Code: " + codeOrder + "| Type: "+ type + "| orderDate: " 
                + orderDate +" | Product code: " + getListItem().keySet() +" | Quantity: "+ getListItem().values() +" |";
    }

    public Object setListItem() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
}
