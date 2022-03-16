/*
  Author: Joel Eriksson Sinclair
  Id: ai7892
  Study program: Sys 21h
*/

package View;

import java.util.Scanner;

public class ConsoleTest {

    public void showError(String msg){
        System.out.println("ERROR: " + msg);
    }
    public void showMessage(String msg){
        System.out.println(msg);
    }

    public String getRegisterInputName(){
        return "Name From View";
    }

    public String getEmail(){
        System.out.println("Input email: ");
        Scanner sc = new Scanner(System.in);
        return sc.nextLine();
    }

    public String getType(){
        System.out.println("Input product type: ");
        Scanner sc = new Scanner(System.in);
        return sc.nextLine();
    }

    public int getID(){
        System.out.println("Input id: ");
        Scanner sc = new Scanner(System.in);
        return Integer.parseInt(sc.nextLine());
    }

    public int getInteger(){
        System.out.println("Input integer: ");
        Scanner sc = new Scanner(System.in);
        return Integer.parseInt(sc.nextLine());
    }

    public String getDate(){
        System.out.println("Input date (Format: yyyy-MM-dd): ");
        Scanner sc = new Scanner(System.in);
        return sc.nextLine();
    }
    public String getDateTruncMonth(){
        System.out.println("Input date (Format: yyyy-MM): ");
        Scanner sc = new Scanner(System.in);
        return sc.nextLine();
    }

    public String getName(){
        System.out.println("Input full name: ");
        Scanner sc = new Scanner(System.in);
        return sc.nextLine();
    }

    public String getStreet(){
        System.out.println("Input street: ");
        Scanner sc = new Scanner(System.in);
        return sc.nextLine();
    }

    public String getCity(){
        System.out.println("Input city: ");
        Scanner sc = new Scanner(System.in);
        return sc.nextLine();
    }

    public String getCountry(){
        System.out.println("Input country: ");
        Scanner sc = new Scanner(System.in);
        return sc.nextLine();
    }

    public String getPhone(){
        System.out.println("Input phone number: ");
        Scanner sc = new Scanner(System.in);
        return sc.nextLine();
    }

    public boolean getBoolean(){
        System.out.println("Input y to return true, else false");
        Scanner sc = new Scanner(System.in);
        String str = sc.nextLine();
        return str.equals("Y") || str.equals("y");
    }
}
