/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MyUtil;



import Controller.ProductManagement;
import Model.Product;
import java.util.ArrayList;
import java.util.Scanner;
//dd/mm/yyy

public class Utils {
    public static boolean checkChange = false;// to require user save
    private static Scanner sc = new Scanner(System.in);//do use for other class

    public static boolean comfirmMsg(String msg){
        String answer = inputRequiredString(msg, "Y|N");
        if ( answer.equalsIgnoreCase("Y"))
            return true;
        else
            return false;
    }
    
    
    public static String inputRequiredString(String msg, String pattern) {
        String s;
        while (true) {
            System.out.print(msg);
            s = sc.nextLine();
            s = s.replaceAll("\\s", "").toUpperCase();
            if (s.matches(pattern) && s.isEmpty() == false) {
                break;
            }
            System.out.println("Must be follow the formating! ");
        }
        return s;

    }

    public static String inputString(String msg) {
        String s = null;
        while (true) {
            System.out.print(msg);
            s = sc.nextLine();
            if (s.isEmpty() == false) {
                break;
            }
            System.out.println("Not allowed empty! Enter again");
        }
        return standardizeString(s);

    }

    public static int inputInteger(String msg, int min, int max) {
        boolean check = true;
        int number = 0;
        do {
            try {
                System.out.print(msg);
                number = Integer.parseInt(sc.nextLine());
                if (number < min || number > max) {
                    System.out.println("Number must be between " + min + " and " + max);
                    check = false;
                } else {
                    check = true;
                }
            } catch (Exception e) {
                System.out.println("Input again! Must be number! ");
                check = false;
            }
        } while (check == false);
        return number;
    }

    public static String inputDate(String msg) {
        String date = null;
        while (true) {
            System.out.print(msg);
            date = sc.nextLine().replaceAll("\\s", "");
            if (checkVailidDate(date)) {
                return date;
            }
        }

    }
    
    public static String inputCode(String msg, String pattern, ArrayList<Product> list) {// xem lại
        ProductManagement p = new ProductManagement();
        String code;
        while (true) {
            System.out.print(msg);
            code = sc.nextLine().toUpperCase();
            if (code.isEmpty()) {
                System.out.println("Not allowed empty! Enter again");
            }

            if (code.matches(pattern) && p.searchProduct(code, list) == null)
                return code.replaceAll("\\s", "");
            if ( p.searchProduct(code, list) != null)
                System.out.println("Duplicate code!!!");
        }

    }
    

    //Check day,month,year
    private static boolean checkVailidDate(String date) {
        String pattern = "([0-2][0-9]|3[0-1])/(0[0-9]|1[0-2])/\\d{4}";
        if (date.trim().matches(pattern)) {
            String tmp[] = date.replaceAll("\\s", "").split("/");

            int day = Integer.parseInt(tmp[0]);
            int month = Integer.parseInt(tmp[1]);
            int year = Integer.parseInt(tmp[2]);

            if ((year % 4 != 0 && month == 2 && day > 28) || (year % 4 == 0 && month == 2 && day > 29)
                || (month == 4 || month == 6 || month == 9 || month == 11) && day > 30)
            {
                System.out.println("Not valid! ");
                return false;                
            }
            else 
                return true;
        }
        System.out.println("Must follow formating! EX: 01/09/2023");
        return false;
}


    public static boolean compareDate(String mfd, String exp) {
        String tmp1[] = mfd.replaceAll("\\s", "").split("/");// remove space
        String tmp2[] = exp.replaceAll("\\s", "").split("/");
 
        int dayMFD = Integer.parseInt(tmp1[0]);
        int monthMFD = Integer.parseInt(tmp1[1]);
        int yearMFD = Integer.parseInt(tmp1[2]); 
        
        int yearEXP = Integer.parseInt(tmp2[2]);
        int monthEXP = Integer.parseInt(tmp2[1]);
        int dayEXP = Integer.parseInt(tmp2[0]);

        if (yearEXP > yearMFD) {
            return true;
        } else if (yearEXP == yearMFD) {
            if (monthEXP > monthMFD) {
                return true;
            } else if (monthEXP == monthMFD) {
                if (dayEXP > dayMFD || dayEXP == dayMFD) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
    
    private static String standardizeString(String s ){
       s = s.trim().toLowerCase().replaceAll("\\s+", " "); //remove all consecutive spaces into 1 space
       String tmp[] =s.split(" ");
       for (int i = 0; i < tmp.length; i++) {
            char c= Character.toUpperCase(tmp[i].charAt(0));
            tmp[i]= c + tmp[i].substring(1);
        }
       String result = "";
       for (int i = 0; i < tmp.length; i++) {
            result += tmp[i] + " ";
       }
       return result.trim();        
    }

}
