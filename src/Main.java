import java.io.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
/*
Programe name : SE Store #4
Student ID : 66160351
Student name : Nabhon Karladee
Date : 14/8/24
Last Update : 9/9/24
Description : SE Store app for showing store menu
 */

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner input = new Scanner(System.in);
        ArrayList<Product> productList = new ArrayList<>();
        ArrayList<Supplier> supplierList = new ArrayList<>();
        ArrayList<Member> memberList = new ArrayList<>();
        FileHandle.fileRead(productList,supplierList,memberList);
        mainWhile:
        while (true){
            menu();
            try {
                int command = input.nextInt();
                switch (command){
                    case 1:
                        LoginMenu.loginMenu(memberList,productList,supplierList);
                        break;
                    case 2:
                        System.out.println("===== SE STORE =====\nThank you for using our service :3");
                        break mainWhile;
                    default:
                        throw new InputMismatchException();
                }
            } catch (InputMismatchException i){
                System.out.println("Input incorrect");
                input.nextLine();
            } catch (Exception ignored){
            }
        }
    }

    //Check if String X is a digit
    public static boolean isDigit(String id){
        try {
            Integer.parseInt(id);
            return true;
        } catch (Exception e){
            return false;
        }
    }

    //Print menu
    public static void menu(){
        System.out.println("===== SE STORE =====");
        System.out.println("1. Login");
        System.out.println("2. Exit");
        System.out.println("====================");
        System.out.print("Select (1-2) : ");
    }

    //Method for login menu

    //Show user data and menu
    public static void getUserData(Member currentLogin,ArrayList<Product> productList,ArrayList<Supplier> supplierList){
        Scanner input = new Scanner(System.in);
        String displayName = currentLogin.getMemberLastName().substring(0,1).toUpperCase()+". "+currentLogin.getMemberName()+" ("+currentLogin.getMemberStatus()+")";
        int indexOfAt = currentLogin.getEmail().indexOf("@");
        String email = currentLogin.getEmail().substring(0,2)+"***"+currentLogin.getEmail().substring(indexOfAt,indexOfAt+3)+"***";
        String phone = currentLogin.getPhone().substring(0,3)+"-"+currentLogin.getPhone().substring(3,6)+"-"+currentLogin.getPhone().substring(6,10);
        String point = String.format("%.0f",currentLogin.getMemberPoint());
        System.out.println("===== SE STORE =====");
        System.out.println("Hello, "+displayName);
        System.out.println("Email : "+email);
        System.out.println("Phone : "+phone);
        System.out.println("You have "+point+" Point");
        while (true) {
            if (currentLogin.isStaff()) {
                System.out.println("====================");
                System.out.println("1. Show Supplier");
                System.out.println("2. Add Supplier");
                System.out.println("3. Logout");
                System.out.println("====================");
                System.out.print("Select (1-3) : ");
                try {
                    int command = input.nextInt();
                    if (command==1){
                        showSupplier(productList,supplierList,currentLogin);
                    } else if(command==2){
                        FileHandle.addSupplier(supplierList);
                    } else if (command==3) {
                        break;
                    } else {
                        throw new InputMismatchException();
                    }
                } catch (InputMismatchException E){
                    System.out.println("Input incorrect");
                    input.nextLine();
                } catch (IOException I){
                    System.out.println("IOException Error @getUserData");
                }
            } else {
                System.out.println("====================");
                System.out.println("1. Show Supplier");
                System.out.println("2. Logout");
                System.out.println("====================");
                System.out.print("Select (1-2) : ");
                try {
                    int command = input.nextInt();
                    if (command==1){
                        showSupplier(productList,supplierList,currentLogin);
                    } else if(command==2){
                        break;
                    } else {
                        throw new InputMismatchException();
                    }
                } catch (InputMismatchException E){
                    System.out.println("Input incorrect");
                    input.nextLine();
                }
            }

        }
    }

    //Print supplier from supplierList
    public static void showSupplier(ArrayList<Product> productList,ArrayList<Supplier> supplierList,Member currectLogin) {
        Scanner input = new Scanner(System.in);
        whileyaya:
        while (true) {
            System.out.println("===== SE STORE's Supplier =====");
            System.out.printf("%-10s%-25s%-20s%-30s%-30s%-20s\n","#","SupplierName","ContactName","Address","Phone","Email");
            int number = 1;
            for (Supplier S:supplierList) {
                System.out.printf("%-10s%-25s%-20s%-30s%-30s%-20s\n",number++,S.getSuppName(),S.getContractName(),S.getAddress(),S.getPhone(),S.getEmail());
            }
            System.out.println("====================");
            while (true) {
                System.out.println("Type Supplier Number You want or Press Q to Exit");
                System.out.print("Select : ");
                String command = input.nextLine();
                if (command.equalsIgnoreCase("Q")) {
                    break whileyaya;
                } else if (isDigit(command)&&findID(command,supplierList.size())) {
                    showProduct(productList,supplierList.get(Integer.parseInt(command)-1),currectLogin);
                    break;
                } else {
                    System.out.println("Input Incorrect");
                }
            }
        }
    }

    //Print product from productList that has ID equal to supplier ID
    public static void showProduct(ArrayList<Product> productList,Supplier selectSupplier,Member currentLogin) {
        Scanner input = new Scanner(System.in);
        ArrayList<Product> productShow = new ArrayList<>();
        int suppID = Integer.parseInt(selectSupplier.getSuppID());
        for (Product p: productList) {
            if (suppID==p.getSuppID()) {
                productShow.add(p);
            }
        }
        whilebreak:
        while (true){
            System.out.println("===== "+selectSupplier.getSuppName()+" =====");
            System.out.printf("%-10s%-20s%-25s%-10s\n","#","Name","Price(฿)","Quantity");
            int number = 1;
            for (Product p : productShow) {
                String productNumber = String.valueOf(number++);
                String name = p.getName();
                String price;
                int quantity = p.getQuality();
                if (currentLogin.getDiscount()!=1){
                    price = String.format("(%.2f)",p.getPrice()*34);
                    String discountPrice = String.format("%.2f",p.getPrice()*34*currentLogin.getDiscount());
                    System.out.printf("%-10s%-20s%-7s%-10s%8s%-10d\n", productNumber, name,discountPrice,price,"", quantity);
                } else {
                    price = String.format("%.2f",p.getPrice()*34);
                    System.out.printf("%-10s%-20s%-25s%-10d\n", productNumber, name, price, quantity);
                }
            }
            System.out.println("===========================");
            whileinside:
            while (true){
                System.out.println("1. Show Name By ASC");
                System.out.println("2. Show Price By DESC");
                System.out.print("Press Q to Exit : ");
                String command = input.next();
                switch (command){
                    case "1":
                        Sort.sortByNameASC(productShow);
                        break whileinside;
                    case "2":
                        Sort.sortByPriceDESC(productShow);
                        break whileinside;
                    case "Q","q" :
                        break whilebreak;
                    default :
                        System.out.println("Input Incorrect");
                        break;
                }
            }
        }
    }

    //Check if user input for supplier is within supplierList
    public static boolean findID(String id,int supplierListSize){
        int checker = Integer.parseInt(id);
        return checker <= supplierListSize && checker > 0;
    }


}