import java.io.File;
import java.io.FileNotFoundException;
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
                        System.out.println("===== SE STORE =====\nThank you for using our service :3");
                        break mainWhile;
                    default:
                        throw new InputMismatchException();
                }
            } catch (InputMismatchException i){
                System.out.println("Input incorrect");
                input.nextLine();
            } catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
    }

    //อ่านไฟล์ Product.txt แล้วเก็บค่าลงใน Array productList
    public static void fileRead(ArrayList<Product> productList) throws FileNotFoundException {
        File productInput = new File("C:\\Java\\PSP\\SE Store\\file\\PRODUCT.txt");
        Scanner fileReader = new Scanner(productInput);
        while (fileReader.hasNextLine()){
            String regex = "[\t]";
            String[] readList = fileReader.nextLine().split(regex);
            int id = Integer.valueOf(readList[0]);
            String name = readList[1];
            double price = Double.valueOf(readList[2].replace("$",""));
            int quality = Integer.valueOf(readList[3]);
            productList.add(new Product(id,name,price,quality));
        }
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
            String price = String.format("%.2f",p.getPrice())+"$";
            System.out.printf("%-6d%-20s%-15s%-10d\n",productCount++,p.getName(),price,p.getQuality());
        }
        System.out.println("===========================================");
    }
}
