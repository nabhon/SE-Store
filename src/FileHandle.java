import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class FileHandle {
    //อ่านไฟล์ Text ข้อมูล แล้วเก็บค่าลงใน Array productList
    public static void fileRead(ArrayList<Product> productList,ArrayList<Supplier> supplierList,ArrayList<Member> memberList) throws FileNotFoundException {
        File productInput = new File("file/PRODUCT.txt");
        Scanner fileReader = new Scanner(productInput);
        while (fileReader.hasNextLine()){
            String regex = "\t+";
            String[] readList = fileReader.nextLine().split(regex);
            int id = Integer.parseInt(readList[0]);
            String name = readList[1];
            double price = Double.parseDouble(readList[2].replace("$",""));
            int quality = Integer.parseInt(readList[3]);
            int suppID = Integer.parseInt(readList[4]);
            productList.add(new Product(id,name,price,quality,suppID));
        }
        File suppInput = new File("file/SUPPLIER.txt");
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
        File memberInput = new File("file/MEMBER.txt");
        fileReader = new Scanner(memberInput);
        while (fileReader.hasNextLine()){
            String regex = "\t+";
            String[] readList = fileReader.nextLine().split(regex);
            int memberID = Integer.parseInt(readList[0]);
            String name = readList[1];
            String lastName = readList[2];
            String email = readList[3];
            String password = readList[4];
            String phone = readList[5];
            double point = Double.parseDouble(readList[6]);
            memberList.add(new Member(memberID,name,lastName,email,password,phone,point));
        }
    }

    public static void saveSupplier(ArrayList<Supplier> supplierList) throws IOException {
        FileWriter file = new FileWriter("file/SUPPLIER.txt");
        PrintWriter outputFile = new PrintWriter(file);
        for (Supplier S:supplierList) {
            outputFile.println(S);
        }
        outputFile.close();
    }

    public static void saveProduct(ArrayList<Product> productList) throws IOException {
        FileWriter file = new FileWriter("file/PRODUCT.txt");
        PrintWriter outputFile = new PrintWriter(file);
        for (Product P:productList) {
            outputFile.println(P);
        }
        outputFile.close();
    }

    public static void saveOrder(ArrayList<Product> orderList,int memberID) throws IOException {
        FileWriter file = new FileWriter("file/CART.txt");
        PrintWriter outputFile = new PrintWriter(file);
        for (Product O:orderList) {
            String print = String.format("%d\t%d\t%d",memberID,O.getId(),O.getQuality());
            outputFile.println(print);
        }
        outputFile.close();
    }
}
