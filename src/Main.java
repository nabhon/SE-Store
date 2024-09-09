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
        fileRead(productList,supplierList,memberList);
        mainWhile:
        while (true){
            menu();
            try {
                int command = input.nextInt();
                switch (command){
                    case 1:
                        //showSupplier(productList,supplierList);
                        loginMenu(memberList,productList,supplierList);
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


    public static void showSupplier(ArrayList<Product> productList,ArrayList<Supplier> supplierList) {
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
                System.out.println("Type Supplier Number You want and Press Q to Exit");
                System.out.print("Select : ");
                String command = input.nextLine();
                if (command.equalsIgnoreCase("Q")) {
                    break whileyaya;
                } else if (isDigit(command)&&findID(command, supplierList)) {
                    System.out.println("===== "+supplierList.get(Integer.parseInt(command)-1).getSuppName()+" =====");
                    showProduct(productList, Integer.parseInt(supplierList.get(Integer.parseInt(command)-1).getSuppID()));
                    break;
                } else {
                    System.out.println("Input Incorrect");
                }
            }
        }
    }

    public static boolean findID(String id,ArrayList<Supplier> supplierList){
        int checker = Integer.parseInt(id);
        if (checker<=supplierList.size()&&checker>0){
                return true;
        }
        return false;
    }

    public static boolean isDigit(String id){
        try {
            Integer.parseInt(id);
            return true;
        } catch (Exception e){
            return false;
        }
    }

    //แสดงหน้าต่าง Menu
    public static void menu(){
        System.out.println("===== SE STORE =====");
        System.out.println("1. Login");
        System.out.println("2. Exit");
        System.out.println("====================");
        System.out.print("Select (1-2) : ");
    }

    public static void loginMenu(ArrayList<Member> memberList,ArrayList<Product> productList,ArrayList<Supplier> supplierList) throws IOException {
        Scanner input = new Scanner(System.in);
        int loginFail = 0;
        while (true) {
            System.out.println("===== Login =====");
            System.out.print("Email : ");
            String email = input.next();
            System.out.print("Password : ");
            String password = input.next();
            int loginID = findMember(memberList, email, password);
            if (loginID == -1) {
                loginFail++;
                System.out.println("Error! - Email or Password is Incorrect ("+loginFail+")");
                if (loginFail==3){
                    System.out.println("Sorry, Please try again later :(");
                    break;
                }
            } else {
                if (checkIDValid(memberList,loginID)&&isStaff(memberList,loginID)){
                    getStaffData(memberList,productList,supplierList,loginID);
                    break;
                } else if (checkIDValid(memberList,loginID)){
                    getUserData(memberList,productList,supplierList,loginID);
                    break;
                } else {
                    System.out.println("Error! - Your Account are Expired!");
                    break;
                }
            }
        }
    }

    public static boolean isStaff(ArrayList<Member> memberList,int loginID){
        for (Member m:memberList) {
            if (m.getMemberID()==loginID){
                return m.isStaff();
            }
        }
        return false;
    }

    //Method to find LoginID of a member
    public static int findMember(ArrayList<Member> memberList,String email,String password){
        for (Member m:memberList) {
            String checkEmail = m.getEmail();
            String checkPassword = m.getPassword().substring(9,11)+m.getPassword().substring(13,17);
            if (email.equals(checkEmail)&&password.equals(checkPassword)){
                return m.getMemberID();
            }
        }
        return -1;
    }

    //Show user data and menu
    public static void getUserData(ArrayList<Member> memberList,ArrayList<Product> productList,ArrayList<Supplier> supplierList,int loginID){
        Scanner input = new Scanner(System.in);
        for (Member m: memberList) {
            if (m.getMemberID()==loginID){
                String displayName = m.getMemberLastName().substring(0,1).toUpperCase()+". "+m.getMemberName()+" ("+m.getMemberStatus()+")";
                int indexOfAt = m.getEmail().indexOf("@");
                String email = m.getEmail().substring(0,2)+"***"+m.getEmail().substring(indexOfAt,indexOfAt+3)+"***";
                String phone = m.getPhone().substring(0,3)+"-"+m.getPhone().substring(3,6)+"-"+m.getPhone().substring(6,10);
                String point = String.format("%.0f",m.getMemberPoint());
                System.out.println("===== SE STORE =====");
                System.out.println("Hello, "+displayName);
                System.out.println("Email : "+email);
                System.out.println("Phone : "+phone);
                System.out.println("You have "+point+" Point");
                while (true) {
                System.out.println("====================");
                System.out.println("1. Show Supplier");
                System.out.println("2. Logout");
                System.out.println("====================");
                    System.out.print("Select (1-2) : ");
                    try {
                        int command = input.nextInt();
                        if (command==1){
                            showSupplier(productList,supplierList);
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
    }

    //Show staff data and menu
    public static void getStaffData(ArrayList<Member> memberList,ArrayList<Product> productList,ArrayList<Supplier> supplierList,int loginID) throws IOException{
        Scanner input = new Scanner(System.in);
        for (Member m: memberList) {
            if (m.getMemberID()==loginID){
                String displayName = m.getMemberLastName().substring(0,1).toUpperCase()+". "+m.getMemberName()+" ("+m.getMemberStatus()+")";
                int indexOfAt = m.getEmail().indexOf("@");
                String email = m.getEmail().substring(0,2)+"***"+m.getEmail().substring(indexOfAt,indexOfAt+3)+"***";
                String phone = m.getPhone().substring(0,3)+"-"+m.getPhone().substring(3,6)+"-"+m.getPhone().substring(6,10);
                String point = String.format("%.0f",m.getMemberPoint());
                System.out.println("===== SE STORE =====");
                System.out.println("Hello, "+displayName);
                System.out.println("Email : "+email);
                System.out.println("Phone : "+phone);
                System.out.println("You have "+point+" Point");
                while (true) {
                    System.out.println("====================");
                    System.out.println("1. Show Supplier");
                    System.out.println("2. Add Supplier");
                    System.out.println("3. Logout");
                    System.out.println("====================");
                    System.out.print("Select (1-3) : ");
                    try {
                        int command = input.nextInt();
                        if (command==1){
                            showSupplier(productList,supplierList);
                        } else if(command==2){
                            addSupplier(supplierList);
                        } else if (command==3) {
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
    }

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
            String address = buildingNum+" "+streetName+", "+cityName;
            Supplier newSupplier = new Supplier(String.valueOf(supplierList.size()+1),name,contractName,address,phoneNum,email);
            supplierList.add(newSupplier);
            supplierWrite(newSupplier);
            System.out.println("Success - New Supplier has been created!");
        } else {
            System.out.println("Error! - Your Information are Incorrect!");
        }
        System.out.println("======================");
    }

    //Check if account in expired or not
    public static boolean checkIDValid(ArrayList<Member> memberList,int loginID){
        for (Member m: memberList) {
            if (m.getMemberID()==loginID){
                int checkIndex = Integer.parseInt(m.getPassword().substring(2,3));
                return checkIndex == 1;
            }
        }
        return false;
    }

    //อ่านค่าจาก Array productList แล้วแสดงค่าออกมาเป็น List
    public static void showProduct(ArrayList<Product> productList,int suppID) {
        Scanner input = new Scanner(System.in);
        System.out.printf("%-10s%-20s%-15s%-10s\n","#","Name","Price(฿)","Quantity");
        int number = 1;
        for (Product p: productList) {
            if (suppID==p.getSuppID()) {
                String productNumber = String.valueOf(number++);
                String name = p.getName();
                String price = String.format("%.2f", p.getPrice()*34);
                int quantity = p.getQuality();
                System.out.printf("%-10s%-20s%-15s%-10d\n", productNumber, name, price, quantity);
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

}
