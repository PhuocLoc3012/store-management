/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.DailyUsingProduct;
import Model.LongShelfLifeProduct;
import Model.Order;
import Model.Product;
import MyUtil.Utils;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;


/**
 *
 * @author Asus
 */
public class ProductManagement {

    private int number = 1;//inceasing numer (start with 1)
    private ArrayList<Order> listOrder = new ArrayList<>();
    private ArrayList<Product> listProduct = new ArrayList<>();
    private String fileProduct = "product.dat"; // store product list
    private String fileOrder ="wareHouse.dat"; // store import/export information 
    
    public ProductManagement() {        

    }

    public void setListProduct(ArrayList<Product> listProduct) {
        this.listProduct = listProduct;
    }
    
    public ArrayList<Product> getListProduct() {
        return listProduct;
    }    
    
    public String getFileProduct() {
        return fileProduct;
    }

    
    
    private String increasingNumber(){  
        String result;
        while(true)
        {
            result = "";            
            String s = String.valueOf(number); // convert number into string
            for (int i = 0; i < 7 - s.length(); i++) {
               result += "0";
            }
            result = result + s;// append 
            if ( searchListOrder(result) == false) // check duplicate
                break; // if not found-> not duplicate
            number++;// if duplicate code then number++
        };
        return result;
        
    }//only use in this class
    private String currentDate(){
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");  
        String currentDate= formatter.format(date);  
        return currentDate;
    }//only use in this class    


    // Search the product's code into ListItem(Order) to delete if the product absent
    public boolean searchListItem(String code){
        for (Order order : listOrder) {
            if (order.getListItem().containsKey(code))
                return true;            
        }

        return false;
       
    }    
    
    
    

    //1. MANAGE PRODUCT
    
    //Manage product
    public void manageProduct(){
        Menu m = new Menu();
        int choice;
        do{
        m.menuManageProduct();
        choice = m.getChoice(1, 5);
        switch(choice){
            case 1:  
                choiceAddProduct();
                break;
            case 2:
                updateChoice();
                break;  
            case 3:
                deleteProduct(Utils.inputRequiredString("Enter the product's code that you want to delete (PXXXX): ", "P\\d{4}"));
                break;

            case 4:
                System.out.println("____________________________________________________________________________________________________________________");
                System.out.println(" =====================================================LIST PRODUCTS=================================================");                 
                showProduct(listProduct);
                break;
            case 5:
                break;
        }
    } while(choice < 5);        
    }    
    // (Main) add product function witch switch case
    public void choiceAddProduct(){
        Menu m = new Menu();
        boolean answer= true;
        do {
        m.menuAddProduct();
        int choice =m.getChoice(1, 3);
        switch(choice){
            case 1:
                while (answer ){
                   addProduct(choice);
                   Utils.checkChange = true;
                   answer = Utils.comfirmMsg("Do you continue add daily using product? (Y/N): ");   
                }
                break;
            case 2:
                answer = true;
                while ( answer ){
                    addProduct(choice);
                    Utils.checkChange = true;
                    answer = Utils.comfirmMsg("Do you continue add long shelf life product? (Y/N): ");  
                }                                
                break;
            case 3:
                answer = true;  
            }
        } while ( answer == false);       
    }
    
    //Check duplicate return product   
    public  Product searchProduct(String code, ArrayList<Product> list) {
        for (Product p : list) {
            if (p.getCode().equalsIgnoreCase(code)) {
                return p;
            }
        }
        return null;
    }    
    
    //Add 2 types product
    public void addProduct(int choice){
        String code;
        String name;
        int quantity;
        String unit;
        String size;
        String manufacturingDate;
        String expirationDate;        
        
        System.out.println("Enter new product detail: ");
        code = Utils.inputCode("Enter code product, must be PXXXX (X is digit): ", "P\\d{4}",listProduct);//loadFromFiles
        name = Utils.inputString("Enter product's name: ");

        if ( choice == 1){//Daily using product
            unit = Utils.inputString("Enter unit of product: ");
            size = Utils.inputRequiredString("Enter the size(S/M/L): ", "S|M|L"); 
            listProduct.add(new DailyUsingProduct(code, name,unit, size));
            System.out.println("Add product successfully!");            
        }
        else //Long shelf life product
        {
            do 
            {
                manufacturingDate = Utils.inputDate("Enter manufacturing date of product (dd/mm/yyyy): ");

                if ( Utils.compareDate(manufacturingDate, currentDate())) {
                    break;
                }
                System.out.println("The day is not valid! The manufacturing date must be smaller than the current date. Enter again!!!");
            } while (true);
            do
            {
                expirationDate = Utils.inputDate("Enter expiration date of product (dd/mm/yyyy): ");
                if (Utils.compareDate(manufacturingDate, expirationDate) && Utils.compareDate(currentDate(),  expirationDate))
                    break;
                System.out.println("The day is not valid! The exparition date must be larger than manufacturing date and current date. Enter again!!!");
   
            } while(true);
            
        listProduct.add(new LongShelfLifeProduct(code, name, manufacturingDate, expirationDate));
        System.out.println("Add product successfully!");            
        }
    }
    
    
    //Update product with choices (main)
    public void updateChoice(){       
        int choice = 0;
        boolean answer = true;
        String newMFD;
        String newEXP;
        do{
        Menu m = new Menu();            
        String code = Utils.inputRequiredString("Enter product code that you want to update (PXXXX): ", "P\\d{4}");
        Product p = searchProduct(code, listProduct);
        if ( p == null)
        {
            System.out.println("Product not exist!");
            return;
        }
        else
        {
            System.out.println(p);
            Utils.checkChange = true;
            if ( p instanceof DailyUsingProduct )
            {
                while(true){
                    m.menuUpdateDailyProduct();
                    choice = m.getChoice(1, 5);           
                    switch ( choice){
                        case 1:
                            p.setName(Utils.inputString("Enter new product's name: "));                         
                            break;
                        case 2:
                           ((DailyUsingProduct)p).setUnit(Utils.inputString("Enter new unit of this product: "));
                           break;
                        case 3:
                           ((DailyUsingProduct)p).setSize(Utils.inputRequiredString("Enter new size of this product: ", "S|M|L"));
                           break;
                        case 4:
                            updateAllInfor(code);
                            break;
                        case 5:
                            return;
                    }
                    answer = Utils.comfirmMsg("Do you want to continue update others information this product? (Y/N): ");
                    if (answer == false)
                        break;
                }                
            }
            if ( p instanceof LongShelfLifeProduct )
            {
                while(true){                
                    m.menuUpdateLongProduct();
                    choice = m.getChoice(1, 5);
                    switch ( choice){
                        case 1:
                            p.setName(Utils.inputString("Enter new product name: ")); 
                            break;
                        case 2:
                            while(true){
                              newMFD = Utils.inputDate("Enter new manufacturing name (dd/MM/yyyy): ");
                              if ( Utils.compareDate(newMFD, currentDate())&& Utils.compareDate(newMFD, ((LongShelfLifeProduct) p).getEnpirationDate()) )
                              {
                                  ((LongShelfLifeProduct) p).setManufacturingDate(newMFD);
                                  break;
                              }
                              else
                              {
                                  System.out.println("The manufacturing date is not valid and must be smaller the current date!");
                              }
                            }
                           break;
                        case 3:
                            while (true){
                                newEXP = Utils.inputDate("Enter new expiration date (dd/MM/yyyy): ");
                                if ( Utils.compareDate(((LongShelfLifeProduct) p).getManufacturingDate(), newEXP)&&Utils.compareDate(currentDate(),  newEXP)){
                                    ((LongShelfLifeProduct) p).setEnpirationDate(newEXP);
                                    break;
                                }
                                else
                                {
                                    System.out.println("The expiration date is not valid must be larger than the manufacturing date " +((LongShelfLifeProduct) p).getManufacturingDate()+")!");
                                }
                            }
                           break;
                        case 4:
                            updateAllInfor(code);
                            break;  
                        case 5:
                            return;
                    }
                    answer = Utils.comfirmMsg("Do you want to continue update others information this product? (Y/N): ");
                    if (answer == false)
                        break;
                }
            }
        }
        System.out.println("Update successfully");
        System.out.println(p);
        answer = Utils.comfirmMsg("Do you want to continue update others product? (Y/N): ");
        } while ( answer );
    } 
    
    //Update all imformation, sub function
    public void updateAllInfor(String code){
        Product p = searchProduct(code, listProduct);
        String newMFD;
        String newEXP;        
        if ( p instanceof DailyUsingProduct)
        {
            p.setName(Utils.inputString("Enter new product name: "));  
            ((DailyUsingProduct)p).setUnit(Utils.inputString("Enter new unit of product: "));
            ((DailyUsingProduct)p).setSize(Utils.inputRequiredString("Enter new size of product: ", "S|M|L"));
        }
        else
        {
            p.setName(Utils.inputString("Enter new product name: "));  
           do 
            {
                newMFD = Utils.inputDate("Enter new manufacturing date of product (dd/mm/yyyy): ");

                if ( Utils.compareDate(newMFD, currentDate())) {
                    break;
                }
                System.out.println("The day is not valid! The manufacturing date must be smaller the current date. Enter again!!!");
            } while (true);
                
            do
            {
                newEXP = Utils.inputDate("Enter new expiration date of product (dd/mm/yyyy): ");
                if (Utils.compareDate(newMFD, newEXP) && Utils.compareDate(newEXP,  currentDate())==false)
                    break;
                System.out.println("The day is not valid! Please checking the exparition date must be larger than manufacturing date and current date. Enter again!!!");
   
            } while(true);
             ((LongShelfLifeProduct)p).setManufacturingDate(newMFD);
            ((LongShelfLifeProduct)p).setEnpirationDate(newEXP);
        }
    }
   
    //sub function
    public void deleteProduct(String code) {
        Product p = searchProduct(code, listProduct);
        if (p == null) {
            System.out.println("Product does not exist!");
        }
        else
        {
            if(searchListItem(code) == false)// there is absent in listItem (list Order)
            {
                System.out.println(p);
                boolean cofirm = Utils.comfirmMsg("Do you want to delete this product? (Y/N): ");
                if ( cofirm == true){
                    listProduct.remove(p);
                    Utils.checkChange = true;  // changed
                    System.out.println("Delete product successfully!");
                }
                else
                {
                    System.out.println("Delete product failed!");                    
                }                
            }
            else
            {
                 System.out.println("Delete product failed! This product have generated in receipt.");

            }

        }
    }

    //display all product on listProduct
    public void showProduct(ArrayList<Product> list) {

        System.out.format("|%-7s|%-20s|%10s|%-10s|%-6s|%-15s|%-15s|\n",
                " Code ","        Name       ", " Quantity ","   Unit   ", " Size ", "       MFD   ","       EXP   ");    
        for (Product p : list) {
            if ( p instanceof DailyUsingProduct){
                System.out.format("|%-7s|%-20s|%10d|%-10s|%-6s|%-15s|%-15s|\n",
                        " "+p.getCode(), " "+p.getName(),p.getQuantity(), " "+((DailyUsingProduct) p).getUnit(),
                  "   "+((DailyUsingProduct) p).getSize(), "          ","          " );
            }
            if ( p instanceof LongShelfLifeProduct){
                System.out.format("|%-7s|%-20s|%10d|%-10s|%-6s|%-15s|%-15s|\n",
                        " "+ p.getCode()," "+ p.getName(),p.getQuantity()
                        ,"      " ,"      ","   " +
                        ((LongShelfLifeProduct) p).getManufacturingDate(),"   " 
                        +((LongShelfLifeProduct) p).getEnpirationDate() );                
            }
        }
        System.out.println("____________________________________________________________________________________________________________________");

    }

    

    
    
    //2. MANAGE WARE HOUSE
    
    //check order code duplicate or not to increasing code
    public boolean searchListOrder(String codeOrder){
        for (Order order : listOrder) {
            if ( codeOrder.equalsIgnoreCase(order.getCodeOrder()))
                return true;
        }
        return false;
    }
    
    public void addOrder(){ 
        Menu m = new Menu();        
        do{
            m.menuManageWarehouse();
            int quantity_Import_Export;
            String codeOrder="";// increasing
            String typeOrder = null;       
            String orderDate;
            int choice = m.getChoice(1, 3);
            switch (choice) {
                case 1:
                    typeOrder ="Import";
                    break;
                case 2:
                    typeOrder ="Export";
                    break;
                default:
                    return;
            }
            orderDate = currentDate();// system time
            Order order = new Order(codeOrder, typeOrder, orderDate); 
            showProduct(listProduct);// display list for user see product's code
            boolean confirm = true;// continue or not
            do{
                String codeProduct = Utils.inputRequiredString("Enter product's code to " + typeOrder +": ", "P\\d{4}");
                Product p = searchProduct(codeProduct, listProduct);// search code
                if (p != null)// found product
                 {
                    if ( typeOrder.equalsIgnoreCase("Import"))
                    {
                        quantity_Import_Export = Utils.inputInteger("Enter the number of product you want to import: ", 1, 999999);
                        order.setCodeOrder(increasingNumber());
                        p.setQuantity(quantity_Import_Export + p.getQuantity());
                        System.out.println(typeOrder + " product successfully!"); 
                        order.getListItem().put(p.getCode(), quantity_Import_Export);                    

                    }
                    else //export receipt
                    {
                        if ( p.getQuantity() == 0)//check quantity
                        {
                            System.out.println("Export this product failed! Out of stock!");
                        }
                        else
                        {
                            quantity_Import_Export = Utils.inputInteger("Enter the number of product you want to export: ", 1, p.getQuantity());
                            if ( p.getQuantity() < quantity_Import_Export)// not able to export
                                System.out.println("Create an export receipt failed!");
                            else 
                            {
                                order.setCodeOrder(increasingNumber());
                                p.setQuantity(p.getQuantity() - quantity_Import_Export);
                                System.out.println(typeOrder + " product successfully!"); 
                                order.getListItem().put(p.getCode(), quantity_Import_Export);
                            }
                        }
                    }       
                }
              else // not found product
              {
                 System.out.println("Product not exist!");
              }
              confirm = Utils.comfirmMsg("Are you continuing to "+ typeOrder + " others products (Y/N)?: ");
          } while(confirm); 

          listOrder.add(order);
          System.out.println(order); 
          Utils.checkChange = true; //not save
        }while(true);
    }

    

    
    //3. REPORT
    
    public void report(){
        Menu m = new Menu();
        while (true){
        m.menuReport();
        int choice = m.getChoice(1, 5);
        switch(choice){
            case 1:
                printExpriedProduct();
                break;
            case 2:
                printSellingProduct();
                break;
            case 3:
                printOutOfStockProduct();
                break;
            case 4:
                printInforOrder();
                break;
            case 5:
                return;
        }
        } 
    }
    
    // Imformation import/export receipt of product
    public void printInforOrder(){
        int count = 0; // check empty list
        String code = Utils.inputRequiredString("Enter product code (PXXXX): ", "P\\d{4}");
        if ( searchProduct(code, listProduct)!= null){
            System.out.println("======================================Import/Export receipt of the product================================");
            for (Order order : listOrder) {
                if(order.getListItem().containsKey(code)){
                    System.out.println("| Receipt code: " + order.getCodeOrder() 
                            +" | Type: " + order.getType() +" |"
                            + " Order date: " + order.getOrderDate()
                            +" | " + "Product code: " + code+" | " + order.getType() + " quantity: " + order.getListItem().get(code) +"|");
                    count++;
                }
            }
            if ( count == 0)
            {
                System.out.println("This product does not have any import/export receipts!");                
            }
        }
        else
        {
            System.out.println("Product does not exist!");
        }
    }    
    public void printExpriedProduct(){
        int count= 0;//check size of list
        System.out.println(" ================================================List of expired products============================================"); 
        for (Product p : listProduct) {
            if (p instanceof LongShelfLifeProduct)
            {
                if (Utils.compareDate(((LongShelfLifeProduct)p).getEnpirationDate(), currentDate()))
                {
                    System.out.println(p);
                    count++;// when have a product then increasing
                }
            }
        }
        if ( count ==0){ // empty list
            System.out.println("No have expired products!");
        }
    }
    
    public void printSellingProduct(){
        System.out.println(" =========================================List of products the store is selling====================================");         
        ArrayList<Product> listSelling = new ArrayList<>();
        for (Product p : listProduct) {
            if ( p instanceof DailyUsingProduct)
            {
                if (p.getQuantity() > 0)
                    listSelling.add(p);
            }
            if ( p instanceof LongShelfLifeProduct)
            {
                if ( Utils.compareDate(((LongShelfLifeProduct)p).getManufacturingDate(), ((LongShelfLifeProduct)p).getEnpirationDate())
                && Utils.compareDate(currentDate(), ((LongShelfLifeProduct)p).getEnpirationDate())&& p.getQuantity() > 0)
                    listSelling.add(p);
            }
        }
        showProduct(listSelling);
        
    }
    public void printOutOfStockProduct(){
        ArrayList<Product> listOutStockProduct = new ArrayList<>();
        System.out.println(" ==========================================List product running out of stock=======================================");        
        for (Product p : listProduct) {
            if ( p.getQuantity() <= 3)
               listOutStockProduct.add(p);
        }
        if ( listOutStockProduct.size() == 0){
            System.out.println("No have product running out of stock!");
        }
        Collections.sort(listOutStockProduct, new Comparator<Product>() {
            @Override
            public int compare(Product o1, Product o2) {
                return o1.getQuantity() - o2.getQuantity();
            }
        });
        showProduct(listOutStockProduct);
    }
    
    
    //4. STORE TO FILE
    
    public void storeData(){
        storeToFileProduct();
        storeToFileWareHouse();
        Utils.checkChange = false;       
    }    
    public void storeToFileProduct(){//String fileName
        try {
            
            if (listProduct == null || listProduct.isEmpty()) {
                System.out.println("Empty list! No data to save!");
                return;
            }
            FileOutputStream f = new FileOutputStream(fileProduct);
            ObjectOutputStream of = new ObjectOutputStream(f);
            of.writeObject(listProduct);

            f.close();
            of.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        System.out.println("Save successfully!");

    }
    public void storeToFileWareHouse(){//String fileName
        try {  

            if (listOrder == null || listOrder.isEmpty()) {
                System.out.println("Empty list! No data to save!");                
                return;
            }
            FileOutputStream f = new FileOutputStream(fileOrder);
            ObjectOutputStream of = new ObjectOutputStream(f);
            of.writeObject(listOrder);


            of.close();
            f.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    
    public void loadFromFilesProduct() {
        try {
            FileInputStream f = new FileInputStream(fileProduct);
            ObjectInputStream of = new ObjectInputStream(f);
            listProduct= (ArrayList<Product>) of.readObject();
            
            of.close();
            f.close();            
            
        } catch (Exception e) {
            System.out.println("");
        }
        
    }
    
    public void loadFromFilesWareHouse() {

        try {

            FileInputStream f = new FileInputStream(fileOrder);
            ObjectInputStream of = new ObjectInputStream(f);
            listOrder= (ArrayList<Order>) of.readObject();

            f.close();
            of.close();

        } catch (Exception e) {
            System.out.println("");
        }
        
    }

    
    //5. CLOSE APP
    
     //Close the application
    public boolean closeApp(){
        boolean answer = true;
        while (true){
            if ( Utils.checkChange){
                answer =Utils.comfirmMsg("You haven't saved the data yet, right?"+"\n"+
                        "Do you want to save the data before closing? (Y/N): ");
                if ( answer == true)
                { // YES->save
                    storeData();
                    System.out.println("Good bye! See you later !");                       
                    return true;
                }
                System.out.println("Good bye! See you later !");                 
                return true;
            }    
            else  //not change data
            {
                answer = Utils.comfirmMsg("Do you want to close the application? (Y/N): ");            
                 if ( answer == true){
                    System.out.println("Good bye! See you later !");
                    return true;                            
                 }
                return false;
            }  
        }
    } 
    

    
    
}

