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
public class DailyUsingProduct extends Product{
    String unit;
    String size;//small, medium, large

    public DailyUsingProduct() {
    }

    public DailyUsingProduct(String code, String name, String unit, String size) {
        super(code, name);
        this.unit = unit;
        this.size = size;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return "Daily Using Product " + super.toString() + "| Unit: " + unit + "| Size: " + size +"|";
    }

}
