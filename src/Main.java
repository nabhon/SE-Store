import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
/*
Programe name : SE Store
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
                        showProduct(productList);
                        break;
                    case 2:
                        fileWrite(productList);
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
        File productInput = new File("N:\\Java\\QueueArray\\SE-Store\\file\\PRODUCT.txt");
        Scanner fileReader = new Scanner(productInput);
        while (fileReader.hasNextLine()){
            String regex = "[\t]";
            String[] readList = fileReader.nextLine().split(regex);
            int id = Integer.parseInt(readList[0]);
            String name = readList[1];
            double price = Double.parseDouble(readList[2].replace("$",""));
            int quality = Integer.parseInt(readList[3]);
            productList.add(new Product(id,name,price,quality));
        }
    }

    public static void fileWrite(ArrayList<Product> productList) throws FileNotFoundException {
        File outputText = new File("N:\\Java\\QueueArray\\SE-Store\\file\\PRODUCTest.txt");
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
        System.out.println("1. Show Product");
        System.out.println("2. Exit");
        System.out.println("====================");
        System.out.print("Select (1-2) :\t");
    }

    //อ่านค่าจาก Array productList แล้วแสดงค่าออกมาเป็น List
    public static void showProduct(ArrayList<Product> productList){
        int productCount = 1;
        System.out.println("=========== SE STORE's Products ===========");
        System.out.printf("%-6s%-20s%-15s%-10s\n","#","Name","Price","Quantity");
        for (Product p: productList) {
            String productNumber = String.format("%04d",productCount++);
            String name = p.getName();
            String price = String.format("$%.2f",p.getPrice());
            int quantity = p.getQuality();
            System.out.printf("%-6s%-20s%-15s%-10d\n",productNumber,name,price,quantity);
        }
        System.out.println("===========================================");
    }
}
