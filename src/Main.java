import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
/*
Programe name : SE Store #2
Student ID : 66160351
Student name : Nabhon Karladee
Date : 14/8/24
Description : SE Store app for showing store menu
 */

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner input = new Scanner(System.in);
        ArrayList<Product> productList = new ArrayList<>();
        fileRead(productList);
        mainWhile:
        while (true){
            menu();
            try {
                int command = input.nextInt();
                switch (command){
                    case 1:
                        showSupplier(productList);
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
            }
        }
    }

    //อ่านไฟล์ Product.txt แล้วเก็บค่าลงใน Array productList
    public static void fileRead(ArrayList<Product> productList) throws FileNotFoundException {
        File productInput = new File("C:\\Java\\PSP\\SE Store\\file\\PRODUCT.txt");
        Scanner fileReader = new Scanner(productInput);
        while (fileReader.hasNextLine()){
            String regex = "\\t+";
            String[] readList = fileReader.nextLine().split(regex);
            int id = Integer.parseInt(readList[0]);
            String name = readList[1];
            double price = Double.parseDouble(readList[2]);
            int quality = Integer.parseInt(readList[3]);
            int suppID = Integer.parseInt(readList[5]);
            productList.add(new Product(id,name,price,quality,suppID));
        }
    }

    public static void showSupplier(ArrayList<Product> productList) throws FileNotFoundException {
        Scanner input = new Scanner(System.in);
        whileyaya:
        while (true) {
            System.out.println("===== SE STORE's Supplier =====");
            System.out.printf("%-15s%-20s%-20s%-30s%-30s%-20s\n","SupplierID","SupplierName","ContactName","Address","Phone","Email");
            File productInput = new File("C:\\Java\\PSP\\SE Store\\file\\Supplier.txt");
            Scanner fileReader = new Scanner(productInput);
            while (fileReader.hasNextLine()){
                String[] readList = fileReader.nextLine().split("\t");
                int id = Integer.parseInt(readList[0]);
                String name = readList[1];
                String contract = readList[2];
                String address = readList[3];
                String phone = readList[4];
                String email = readList[5];
                System.out.printf("%-15d%-20s%-20s%-30s%-30s%-20s\n",id,name,contract,address,phone,email);
            }
            System.out.println("====================");
            System.out.println("Type Supplier Number You want and Press Q to Exit");
            System.out.print("Select : ");
            String command = input.nextLine();
            switch (command){
                case "1" :
                    System.out.println("===== Fresh Foods Co. =====");
                    showProduct(productList,1);
                    break;
                case "2" :
                    System.out.println("===== Daily Dairy =====");
                    showProduct(productList,2);
                    break;
                case "3" :
                    System.out.println("===== Oceanic Seafood =====");
                    showProduct(productList,3);
                    break;
                case "4" :
                    System.out.println("===== Bakers Delight =====");
                    showProduct(productList,4);
                    break;
                case "5" :
                    System.out.println("===== Home Essentials =====");
                    showProduct(productList,5);
                    break;
                case "6" :
                    System.out.println("===== Tech Innovators =====");
                    showProduct(productList,6);
                    break;
                case "7" :
                    System.out.println("===== Beauty Bliss =====");
                    showProduct(productList,7);
                    break;
                case "8" :
                    System.out.println("===== School Supplies =====");
                    showProduct(productList,8);
                    break;
                case "Q","q" :
                    break whileyaya;
                default:
                    System.out.println("Input Incorrect");
                    System.out.println("Type Supplier Number You want and Press Q to Exit");
                    System.out.print("Select : ");
                    input.nextLine();
            }
        }
    }

    public static void fileWrite(ArrayList<Product> productList) throws FileNotFoundException {
        File outputText = new File("C:\\Java\\PSP\\SE Store\\file\\PRODUCTWrite.txt");
        PrintWriter filePrinter = new PrintWriter(outputText);
        for (Product P : productList) {
            String id = String.valueOf(P.getId());
            String name = P.getName();
            String price = "$"+String.format("%.2f",P.getPrice());
            String quantity = String.valueOf(P.getQuality());
            filePrinter.printf("%s\t%s\t%s\t%s\n",id,name,price,quantity);
        }
        filePrinter.close();
    }

    //แสดงหน้าต่าง Menu
    public static void menu(){
        System.out.println("===== SE STORE =====");
        System.out.println("1. Show Supplier");
        System.out.println("2. Exit");
        System.out.println("====================");
        System.out.print("Select (1-2) :\t");
    }

    //อ่านค่าจาก Array productList แล้วแสดงค่าออกมาเป็น List
    public static void showProduct(ArrayList<Product> productList,int suppID) throws FileNotFoundException {
        Scanner input = new Scanner(System.in);
        int productCount = 1;
        System.out.printf("%-6s%-20s%-15s%-10s\n","#","Name","Price","Quantity");
        for (Product p: productList) {
            if (suppID==p.getSuppID()) {
                String productNumber = String.format("%04d", p.getId());
                String name = p.getName();
                String price = String.format("$%.2f", p.getPrice());
                int quantity = p.getQuality();
                System.out.printf("%-6s%-20s%-15s%-10d\n", productNumber, name, price, quantity);
            }
        }
        System.out.println("===========================");
        whilebreak:
        while (true){
            System.out.print("Press Q to Exit : ");
            String command = input.next();
            switch (command){
                case "Q","q" :
                    break whilebreak;
                default :
                    System.out.println("Input Incorrect");
                    break;
            }
        }
    }
}
