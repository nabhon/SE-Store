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
        ArrayList<Supplier> supplierList = new ArrayList<>();
        fileRead(productList,supplierList);
        mainWhile:
        while (true){
            menu();
            try {
                int command = input.nextInt();
                switch (command){
                    case 1:
                        showSupplier(productList,supplierList);
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


    public static void showSupplier(ArrayList<Product> productList,ArrayList<Supplier> supplierList) {
        Scanner input = new Scanner(System.in);
        whileyaya:
        while (true) {
            System.out.println("===== SE STORE's Supplier =====");
            System.out.printf("%-15s%-20s%-20s%-30s%-30s%-20s\n","SupplierID","SupplierName","ContactName","Address","Phone","Email");
            for (Supplier S:supplierList) {
                System.out.printf("%-15s%-20s%-20s%-30s%-30s%-20s\n",S.getSuppID(),S.getSuppName(),S.getContractName(),S.getAddress(),S.getPhone(),S.getEmail());
            }
            System.out.println("====================");
            while (true) {
                System.out.println("Type Supplier Number You want and Press Q to Exit");
                System.out.print("Select : ");
                String command = input.nextLine();
                if (command.equalsIgnoreCase("Q")) {
                    break whileyaya;
                } else if (findID(command, supplierList)) {
                    showProduct(productList, Integer.parseInt(command));
                    break;
                } else {
                    System.out.println("Input Incorrect");
                }
            }
        }
    }

    public static boolean findID(String id,ArrayList<Supplier> supplierList){
        for (Supplier S:supplierList) {
            if (S.getSuppID().equalsIgnoreCase(id)){
                return true;
            }
        }
        return false;
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
    public static void showProduct(ArrayList<Product> productList,int suppID) {
        Scanner input = new Scanner(System.in);
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

    //อ่านไฟล์ Product.txt แล้วเก็บค่าลงใน Array productList
    public static void fileRead(ArrayList<Product> productList,ArrayList<Supplier> supplierList) throws FileNotFoundException {
        File productInput = new File("file/PRODUCT.txt");
        Scanner fileReader = new Scanner(productInput);
        while (fileReader.hasNextLine()){
            String regex = "\t+";
            String[] readList = fileReader.nextLine().split(regex);
            int id = Integer.parseInt(readList[0]);
            String name = readList[1];
            double price = Double.parseDouble(readList[2]);
            int quality = Integer.parseInt(readList[3]);
            int suppID = Integer.parseInt(readList[5]);
            productList.add(new Product(id,name,price,quality,suppID));
        }
        File suppInput = new File("file/Supplier.txt");
        fileReader = new Scanner(suppInput);
        while (fileReader.hasNextLine()){
            String[] readList = fileReader.nextLine().split("\t+");
            String id = readList[0];
            String name = readList[1];
            String contract = readList[2];
            String address = readList[3];
            String phone = readList[4];
            String email = readList[5];
            supplierList.add(new Supplier(id,name,contract,address,phone,email));
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

}
