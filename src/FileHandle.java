import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class FileHandle {
    //Add new supplier to the file
    public static void addSupplier(ArrayList<Supplier> supplierList) throws IOException {
        Scanner input = new Scanner(System.in);
        boolean checkValid = true;
        System.out.println("=====Add Supplier=====");
        System.out.print("Supplier Name : ");
        String name = input.nextLine();
        System.out.print("Contract Name : ");
        String contractName = input.nextLine();
        System.out.print("Building Number : ");
        String buildingNum = input.nextLine();
        System.out.print("Street Name : ");
        String streetName = input.nextLine();
        System.out.print("City : ");
        String cityName = input.nextLine();
        System.out.print("Phone : ");
        String phoneNum = input.nextLine();
        System.out.print("Email : ");
        String email = input.nextLine();
        if (name.length()<=2){
            checkValid = false;
        }
        if (contractName.length()<=4){
            checkValid = false;
        }
        if (buildingNum.length()==0){
            checkValid = false;
        }
        if (streetName.length()<=2){
            checkValid = false;
        }
        if (cityName.length()<=2){
            checkValid = false;
        }
        if (phoneNum.length()!=10){
            checkValid = false;
        }
        if (email.length()<=2||!email.contains("@")){
            checkValid = false;
        }
        if (checkValid){
            String phone = phoneNum.substring(0,3)+"-"+phoneNum.substring(3,6)+"-"+phoneNum.substring(6,10);
            String address = buildingNum+" "+streetName+", "+cityName;
            Supplier newSupplier = new Supplier(String.valueOf(supplierList.size()+1),name,contractName,address,phone,email);
            supplierList.add(newSupplier);
            supplierWrite(newSupplier);
            System.out.println("Success - New Supplier has been created!");
        } else {
            System.out.println("Error! - Your Information are Incorrect!");
        }
        System.out.println("======================");
    }

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

    //Method to Write new supplier to Supplier file as it get create
    public static void supplierWrite(Supplier supplier) throws IOException {
        FileWriter file = new FileWriter("file/SUPPLIER.txt",true);
        PrintWriter outputFile = new PrintWriter(file);
        String printText = String.format("%s\t%s\t%s\t%s\t%s\t%s",supplier.getSuppID(),supplier.getSuppName(),supplier.getContractName(),supplier.getAddress(),supplier.getPhone(),supplier.getEmail());
        outputFile.println(printText);
        outputFile.close();
    }

    public static void saveSupplier(ArrayList<Supplier> supplierList) throws IOException {
        FileWriter file = new FileWriter("file/SUPPLIER.txt");
        PrintWriter outputFile = new PrintWriter(file);
        for (Supplier S:supplierList) {
            String printText = String.format("%s\t%s\t%s\t%s\t%s\t%s",S.getSuppID(),S.getSuppName(),S.getContractName(),S.getAddress(),S.getPhone(),S.getEmail());
            outputFile.println(printText);
        }
        outputFile.close();
    }

    public static void saveProduct(ArrayList<Product> productList) throws IOException {
        FileWriter file = new FileWriter("file/PRODUCT.txt");
        PrintWriter outputFile = new PrintWriter(file);
        for (Product P:productList) {
            String price = String.format("$%.2f",P.getPrice());
            String printText = String.format("%d\t%s\t%s\t%d\t%d",P.getId(),P.getName(),price,P.getQuality(),P.getSuppID());
            outputFile.println(printText);
        }
        outputFile.close();
    }
}
