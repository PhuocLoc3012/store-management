/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controller.Menu;
import Controller.ProductManagement;


/**
 *
 * @author Asus
 */
public class Main {
    public static void main(String[] args) {
        Menu m = new Menu();
        ProductManagement p = new ProductManagement();
        p.loadFromFilesProduct();
        p.loadFromFilesWareHouse();
        int choice = 0;
        do {
            m.bigMenu();
            choice = m.getChoice(1, 5);
            switch(choice)
            {
                case 1:
                    p.manageProduct();
                    break;
                case 2: 
                    p.addOrder();
                    break;
                case 3:
                    p.report();
                   break;
                case 4:
                    p.storeData();
                    break;
                case 5:
                    if(p.closeApp())
                        return;
                     
            }
            
        } while (choice >= 1 && choice <= 5);

    }    
}
