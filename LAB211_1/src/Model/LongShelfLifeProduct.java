/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;



/**
 *
 * @author Asus
 */
public class LongShelfLifeProduct extends Product{
    private String manufacturingDate;
    private String expirationDate;

    public LongShelfLifeProduct() {
    }

    public LongShelfLifeProduct(String code, String name, String manufacturingDate, String expirationDate) {
        super(code, name);
        this.manufacturingDate = manufacturingDate;
        this.expirationDate = expirationDate;
    }

    public String getManufacturingDate() {
        return manufacturingDate;
    }

    public void setManufacturingDate(String manufacturingDate) {
        this.manufacturingDate = manufacturingDate;
    }

    public String getEnpirationDate() {
        return expirationDate;
    }

    public void setEnpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    @Override
    public String toString() {
        return "Long shelf life product " + super.toString() 
              + "| Manufacturing date: " + manufacturingDate
              + "| Expiration Date: " + expirationDate +"|" ;
    }

    
}
