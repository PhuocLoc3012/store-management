/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;
;
import MyUtil.Utils;
import java.util.Scanner;

/**
 *
 * @author Asus
 */
public class Menu {


    public void bigMenu(){
        System.out.println("  _____________________________________");
        System.out.println(" |                                     |");
        System.out.println(" |STORE MANAGEMENT AT CONVENIENCE STORE|");     
        System.out.println(" | ____________________________________|");
        System.out.println(" |      1. Manage products             |");
        System.out.println(" |      2. Manage Warehouse            |");
        System.out.println(" |      3. Report                      |");
        System.out.println(" |      4. Store data to files         |");
        System.out.println(" |      5. Close the application       |");
        System.out.println(" |_____________________________________|");
    }
    public void menuManageProduct(){
        System.out.println("    ________MANAGE PRODUCTS________");
        System.out.println("    1. Add a product.");
        System.out.println("    2. Update product information.");
        System.out.println("    3. Delete product.");
        System.out.println("    4. Show all product.");
        System.out.println("    5. Back to main menu.");
        System.out.println("    _______________________________");
    }

    public void menuAddProduct(){
        System.out.println("       ___________ADD A PRODUCT__________");
        System.out.println("       1. Add a daily using product.    ");
        System.out.println("       2. Add a long shelf life product.");
        System.out.println("       3. Exit.                         ");
        System.out.println("       __________________________________");
    }
    
    public void menuManageWarehouse(){
        System.out.println("     ______MANAGE WAREHOUSE______");
        System.out.println("     1. Create an import receipt.");
        System.out.println("     2. Create an export receipt.");
        System.out.println("     3. Back to main menu.       ");
        System.out.println("     ____________________________");
    }   
    
    public void menuUpdateDailyProduct(){
        System.out.println("       __________UPDATE PRODUCT_______");
        System.out.println("        1. Update name            ");
        System.out.println("        2. Update unit            ");
        System.out.println("        3. Update size            ");
        System.out.println("        4. Update all information ");
        System.out.println("        5. Exit");
        System.out.println("        ______________________________");
    }
    
    public void menuUpdateLongProduct(){
        System.out.println("       __________UPDATE PRODUCT_______");
        System.out.println("        1. Update name");
        System.out.println("        2. Update manufacturing date");
        System.out.println("        3. Update expiration date."); 
        System.out.println("        4. Update all information");
        System.out.println("        5. Exit");
        System.out.println("        ______________________________");    
    }
    public void menuReport(){
        System.out.println("    ______________________REPORT_____________________");
        System.out.println("    1. Products that have expired.");
        System.out.println("    2. The products that the store is selling.");
        System.out.println("    3. Products that are running out of stock.");
        System.out.println("    4. Import/export receipt of a product");
        System.out.println("    5. Back to main menu.");
        System.out.println("    _________________________________________________");
    }

    public  int getChoice(int min, int max){
        Scanner sc = new Scanner(System.in);
        return Utils.inputInteger("Your choice: ", min, max);        
    }
    


}
