import java.io.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
/*
Program name : SE Store
Student ID : 66160351
Student name : Nabhon Karladee
Date : 14/8/24
Last Update : 18/10/24
Description : SE Store app
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
            System.out.println("===== SE STORE =====");
            System.out.println("1. Login");
            System.out.println("2. Exit");
            System.out.println("====================");
            System.out.print("Select (1-2) : ");
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

    //Show user data and menu
    public static void getUserData(Member currentLogin,ArrayList<Product> productList,ArrayList<Supplier> supplierList,ArrayList<Member> memberList){
        Scanner input = new Scanner(System.in);
        ArrayList<Product> orderList = new ArrayList<>();
        String displayName = currentLogin.getMemberLastName().substring(0,1).toUpperCase()+". "+currentLogin.getMemberName()+" ("+currentLogin.getMemberStatus()+")";
        int indexOfAt = currentLogin.getEmail().indexOf("@");
        String email = currentLogin.getEmail().substring(0,2)+"***"+currentLogin.getEmail().substring(indexOfAt,indexOfAt+3)+"***";
        String phone = currentLogin.getPhone().substring(0,3)+"-"+currentLogin.getPhone().substring(3,6)+"-"+currentLogin.getPhone().substring(6,10);
        String point = String.format("%.0f",currentLogin.getMemberPoint());
        whilestop:
        while (true) {
            System.out.println("===== SE STORE =====");
            System.out.println("Hello, "+displayName);
            System.out.println("Email : "+email);
            System.out.println("Phone : "+phone);
            System.out.println("You have "+point+" Point");
            if (currentLogin.isStaff()) {
                System.out.println("====================");
                System.out.println("1. Show Supplier");
                System.out.println("2. Add Supplier");
                System.out.println("3. Edit Supplier");
                System.out.println("4. Edit Products");
                System.out.println("5. Logout");
                System.out.println("====================");
                System.out.print("Select (1-5) : ");
                try {
                    int command = input.nextInt();
                    switch (command){
                        case 1:
                            showSupplier(productList,supplierList,currentLogin);
                            break;
                        case 2:
                            addSupplier(supplierList);
                            break;
                        case 3:
                            editSupplier(supplierList);
                            break;
                        case 4:
                            editProduct(productList);
                            break;
                        case 5:
                            break whilestop;
                        default:
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
                System.out.println("2. Order Product");
                System.out.println("3. Checkout");
                System.out.println("4. Logout");
                System.out.println("====================");
                System.out.print("Select (1-2) : ");
                try {
                    int command = input.nextInt();
                    switch (command){
                        case 1:
                            showSupplier(productList,supplierList,currentLogin);
                            break;
                        case 2:
                            orderProduct(productList,orderList);
                            FileHandle.saveOrder(orderList,currentLogin.getMemberID());
                            break;
                        case 3:
                            checkOut(productList,memberList);
                            break;
                        case 4:
                            break whilestop;
                        default:
                            throw new InputMismatchException();
                    }
                } catch (InputMismatchException | IOException E){
                    System.out.println("Input incorrect");
                    input.nextLine();
                }
            }

        }
    }

    public static void checkOut(ArrayList<Product> productList,ArrayList<Member> memberList) throws IOException {
        File suppInput = new File("file/CART.txt");
        Scanner fileReader = new Scanner(suppInput);
        System.out.println("============= Checkout =============");
        System.out.printf("%-10s%-20s%-25s%-10s\n", "Buyer", "Name", "Price($)", "Quantity");
        while (fileReader.hasNextLine()){
            String[] readList = fileReader.nextLine().split("\t+");
            int memID = Integer.parseInt(readList[0]);
            int productID = Integer.parseInt(readList[1]);
            int quantity = Integer.parseInt(readList[2]);
            System.out.print(showCheckOutProduct(memID,productID,quantity,memberList,productList));
        }
        System.out.println("====================================");
        Scanner input = new Scanner(System.in);
        whileBreak:
        while (true){
            System.out.print("Confirm Order (Y/N) : ");
            String confirm = input.next();
            switch (confirm){
                case "y","Y":
                    checkOutProduct(productList);
                    break;
                case "n","N":
                    System.out.println("Checkout canceled");
                    break whileBreak;
                default:
                    System.out.println("Input invalid try again");
            }
        }
    }

    public static void checkOutProduct(ArrayList<Product> productList) throws IOException {
        File suppInput = new File("file/CART.txt");
        Scanner fileReader = new Scanner(suppInput);
        while (fileReader.hasNextLine()){
            String[] readList = fileReader.nextLine().split("\t+");
            int productID = Integer.parseInt(readList[1]);
            int quantity = Integer.parseInt(readList[2]);
            for (Product P:productList) {
                if (P.getId()==productID){
                    int newQuantity = P.getQuality()-quantity;
                    P.setQuality(newQuantity);
                    break;
                }
            }
        }
        FileWriter file = new FileWriter("file/SUPPLIER.txt");
        PrintWriter outputFile = new PrintWriter(file);
        outputFile.close();
    }

    public static String showCheckOutProduct(int memID,int productID,int quantity,ArrayList<Member> memberList,ArrayList<Product> productList){
        String showMemName = "";
        String showProductName = "";
        String showQuantity = String.valueOf(quantity);
        double discountValue = 1;
        double price = 0;
        for (Member M:memberList) {
            if (M.getMemberID()==memID){
                showMemName = String.valueOf(M.getMemberName());
                discountValue = M.getDiscount();
                break;
            }
        }
        for (Product P:productList) {
            if (P.getId()==productID){
                showProductName = String.valueOf(P.getName());
                price = P.getPrice();
                break;
            }
        }
        String priceShow = String.format("%.2f",(price*discountValue));
        String oldPrice = String.format("%.2f",price);
        priceShow = String.format("%s (%s)",priceShow,oldPrice);
        return String.format("%-10s%-20s%-25s%-10s\n",showMemName,showProductName,priceShow,showQuantity);
    }

    public static void orderProduct(ArrayList<Product> productList,ArrayList<Product> orderList) throws IOException {
        Scanner input = new Scanner(System.in);
        System.out.println("============= SE STORE's Products =============");
        System.out.printf("%-10s%-20s%-25s%-10s\n", "#", "Name", "Price(฿)", "Quantity");
        int number = 1;
        for (Product p : productList) {
            String productNumber = String.valueOf(number++);
            String name = p.getName();
            String price;
            int quantity = p.getQuality();
            price = String.format("%.2f", p.getPrice() * 34);
            System.out.printf("%-10s%-20s%-25s%-10d\n", productNumber, name, price, quantity);
        }
        System.out.println("===============================================");
        System.out.println("Enter the product number followed by the quantity.");
        System.out.println("1. How to Order");
        System.out.println("2. List Products");
        System.out.println("3. Show Cart");
        System.out.println("Q. Exit");
        whileyaya:
        while (true) {
            while (true) {
                try{
                    System.out.print("Enter : ");
                    String[] command = input.nextLine().split(" ");
                    boolean removeCheck = false;
                    //ถ้า User กรอกมาตัวเดียวให้เช็คว่าตรง 1 2 3 Q ไหมถ้าไม่ก็ Error
                    if (command.length==1){
                        if (command[0].equalsIgnoreCase("q")){
                            break whileyaya;
                        } else if (command[0].equalsIgnoreCase("1")){
                            System.out.println("""
                                How to Order:
                                To Add Product:
                                \t\tEnter the product number followed by the quantity.
                                \t\tExample: 1 50 (Adds 50 chips)
                                To Adjust Quantity:
                                \t\t+ to add more items: 1 +50 (Adds 50 more chips)
                                \t\t- to reduce items: 1 -50 (Removes 50 chips)""");
                            break;
                        } else if (command[0].equalsIgnoreCase("2")){
                            System.out.println("============= SE STORE's Products =============");
                            System.out.printf("%-10s%-20s%-25s%-10s\n", "#", "Name", "Price(฿)", "Quantity");
                            number = 1;
                            for (Product p : productList) {
                                String productNumber = String.valueOf(number++);
                                String name = p.getName();
                                String price;
                                int quantity = p.getQuality();
                                price = String.format("%.2f", p.getPrice() * 34);
                                System.out.printf("%-10s%-20s%-25s%-10d\n", productNumber, name, price, quantity);
                            }
                            System.out.println("===============================================");
                            break ;
                        } else if (command[0].equalsIgnoreCase("3")){
                            System.out.println("====== Cart ======");
                            for (Product P:orderList) {
                                System.out.printf("%-15s%-7d%n",P.getName(),P.getQuality());
                            }
                            System.out.println("==================");
                            break;
                        } else {
                            throw new InputMismatchException("Input incorrect");
                        }
                    } else if (command.length!=2){
                        throw new InputMismatchException("Input incorrect(please enter in format 1 50)");
                    }
                    if (!isDigit(command[0])||!isDigit(command[1])){
                        throw new RuntimeException("input incorrect(input number only)");
                    }
                    int indexOfProduct = Integer.parseInt(command[0])-1;
                    if (indexOfProduct<0||indexOfProduct>productList.size()-1){
                        throw new IndexOutOfBoundsException("Input incorrect(Index out of bound)");
                    }
                    int indexOfOrder = findOrderIndex(orderList,productList.get(indexOfProduct).getName());
                    int value = Integer.parseInt(command[1]);
                    int newValue;
                    Product listProduct = productList.get(indexOfProduct);
                    //กรณีที่มี + หรือ -
                    if (command[1].charAt(0)=='+'||command[1].charAt(0)=='-'){
                        //ถ้าไม่มี Product อยู่และใส่จำนวนเกินจำนวนที่มีในคลังให้ Error
                        if (indexOfOrder!=-1&&orderList.get(indexOfOrder).getQuality()+value>listProduct.getQuality()){
                            throw new RuntimeException("input incorrect(cannot add more than available quantity)");
                        } else if (value>listProduct.getQuality()){
                            throw new RuntimeException("input incorrect(cannot add more than available quantity)");
                        }
                        //ถ้ามีค่าอยู่แล้วให้เอาค่า +- เข้าไป +
                        //ถ้าไม่มีค่าอยู่ให้สร้าง Product ใหม่เข้าไปใน Cart
                        if (indexOfOrder!=-1){
                            newValue = orderList.get(indexOfOrder).getQuality()+value;
                            orderList.get(indexOfOrder).setQuality(newValue);
                        } else {
                            orderList.add(new Product(listProduct.getId(), listProduct.getName(), listProduct.getPrice(), value, listProduct.getSuppID()));
                            indexOfOrder = findOrderIndex(orderList,productList.get(indexOfProduct).getName());
                        }
                        //ถ้าค่าหลังจาก +- เสร็จแล้วเป็น 0 หรือน้อยกว่าให้ลบทิ้ง
                        //กรณีที่ไม่มี +-
                    } else {
                        //ถ้าค่าที่กรอกมากกว่า Product ที่มีอยู่ให้ Error
                        if (value>listProduct.getQuality()){
                            throw new RuntimeException("input incorrect(cannot add more than available quantity)");
                        }
                        //ถ้ามี Product แล้วให้แก้ไขค่าใหม่ลงไปถ้าไม่มีให้สร้าง Product ใหม่ในตะกร้า
                        if (indexOfOrder!=-1){
                            orderList.get(indexOfOrder).setQuality(value);
                        } else {
                            orderList.add(new Product(listProduct.getId(), listProduct.getName(), listProduct.getPrice(), value, listProduct.getSuppID()));
                            indexOfOrder = findOrderIndex(orderList,productList.get(indexOfProduct).getName());
                        }
                    }
                    //ถ้าค่าหลังจาก +- เสร็จแล้วเป็น 0 หรือน้อยกว่าให้ลบทิ้ง
                    if (orderList.get(indexOfOrder).getQuality()<=0){
                        System.out.println(orderList.get(indexOfOrder).getName() + " removed from cart");
                        orderList.remove(indexOfOrder);
                        removeCheck = true;
                    }
                    if (!removeCheck) {
                        System.out.println(orderList.get(indexOfOrder).getName() + " (" + orderList.get(indexOfOrder).getQuality() + ")" + " in cart");
                    }
                } catch (Exception e){
                    System.out.println(e.getMessage());
                    System.out.println("Please try again or enter 'Q' to exit");
                }

            }
        }
    }

    public static int findOrderIndex(ArrayList<Product> orderList,String name){
        for (int i = 0; i < orderList.size(); i++) {
            if (orderList.get(i).getName().equals(name)){
                return i;
            }
        }
        return -1;
    }

    //Print supplier from supplierList
    public static void showSupplier(ArrayList<Product> productList,ArrayList<Supplier> supplierList,Member currentLogin) {
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
                    showProduct(productList,supplierList.get(Integer.parseInt(command)-1),currentLogin);
                    break;
                } else {
                    System.out.println("Input Incorrect");
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
            String phone = phoneNum.substring(0,3)+"-"+phoneNum.substring(3,6)+"-"+phoneNum.substring(6,10);
            String address = buildingNum+" "+streetName+", "+cityName;
            Supplier newSupplier = new Supplier(String.valueOf(supplierList.size()+1),name,contractName,address,phone,email);
            supplierList.add(newSupplier);
            FileHandle.saveSupplier(supplierList);
            System.out.println("Success - New Supplier has been created!");
        } else {
            System.out.println("Error! - Your Information are Incorrect!");
        }
        System.out.println("======================");
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
                System.out.println("2. Show Name By DESC");
                System.out.println("3. Show Price By ASC");
                System.out.println("4. Show Price By DESC");
                System.out.print("Press Q to Exit : ");
                String command = input.next();
                switch (command){
                    case "1":
                        Sort.sortByNameASC(productShow);
                        break whileinside;
                    case "2":
                        Sort.sortByNameDESC(productShow);
                        break whileinside;
                    case "3":
                        Sort.sortByPriceASC(productShow);
                        break whileinside;
                    case "4":
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

    public static boolean isDouble(String digit){
        try {
            Double.parseDouble(digit);
            return true;
        }catch (Exception E){
            return false;
        }
    }

    public static void editProduct(ArrayList<Product> productList) throws IOException {
        Scanner input = new Scanner(System.in);
        whileyaya:
        while (true) {
            System.out.println("===== SE STORE's Products =====");
            System.out.printf("%-10s%-20s%-25s%-10s\n","#","Name","Price(฿)","Quantity");
            int number = 1;
            for (Product p : productList) {
                String productNumber = String.valueOf(number++);
                String name = p.getName();
                String price;
                int quantity = p.getQuality();
                price = String.format("%.2f",p.getPrice()*34);
                System.out.printf("%-10s%-20s%-25s%-10d\n", productNumber, name, price, quantity);
            }
            System.out.println("====================");
            while (true) {
                System.out.println("Type Product Number, You want to edit or Press Q to Exit");
                System.out.print("Select : ");
                String command = input.nextLine();
                if (command.equalsIgnoreCase("Q")) {
                    break whileyaya;
                } else if (isDigit(command)&&(Integer.parseInt(command)>0&&Integer.parseInt(command)<=productList.size()-1)) {
                    Product editProduct = productList.get(Integer.parseInt(command)-1);
                    boolean valid = true;
                    String name,price,format,digit;
                    System.out.println("==== Edit info of "+editProduct.getName()+" ====");
                    System.out.println("Type new info or Hyphen (-) for none edit.");
                    System.out.print("Name : ");
                    name = input.nextLine();
                    System.out.print("Price ($ or ฿) : ");
                    price = input.nextLine();
                    if (!price.equals("-")){
                        format = price.substring(0,1);
                        digit = price.substring(1);
                        if (!format.equals("$")&&!format.equals("฿")){
                            valid = false;
                        }
                        if (!isDouble(digit)){
                            valid = false;
                        }
                        if (valid){
                            double editPrice;
                            if (format.equals("$")){
                                editPrice = Double.parseDouble(digit);
                            } else {
                                editPrice = Double.parseDouble(digit) / 34;
                            }
                            digit = String.format("%.2f",editPrice);
                            editProduct.setPrice(Double.parseDouble(digit));
                        } else {
                            System.out.println("Error! - Your Information are Incorrect!");
                            break whileyaya;
                        }
                    }
                    if (!name.equals("-")){
                        editProduct.setName(name);
                    }
                    System.out.println("Success - "+editProduct.getName()+" has been updated!");
                    FileHandle.saveProduct(productList);
                    break whileyaya;
                } else {
                    System.out.println("Input Incorrect");
                }
            }
        }
    }

    //Method แสดง Supplier สำหรับเลือกแก้ Supplier
    public static void editSupplier(ArrayList<Supplier> supplierList) throws IOException {
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
                    Supplier supplier = supplierList.get(Integer.parseInt(command)-1);
                    boolean valid = true;
                    System.out.println("==== Edit info of "+supplier.getSuppName()+" ====");
                    System.out.println("Type new info or Hyphen (-) for none edit.");
                    System.out.print("Supplier Name : ");
                    String name = input.nextLine();
                    System.out.print("Contact Name : ");
                    String contractName = input.nextLine();
                    System.out.print("Phone : ");
                    String phone = input.nextLine();
                    System.out.print("Email : ");
                    String email = input.nextLine();
                    if (name.length()<=2&&!name.equals("-")){
                        valid = false;
                    }
                    if (contractName.length()<=4&&!contractName.equals("-")){
                        valid = false;
                    }
                    if (phone.length()!=10&&!phone.equals("-")){
                        valid = false;
                    }
                    if ((email.length()<=2||!email.contains("@"))&&!email.equals("-")){
                        valid = false;
                    }
                    if (valid){
                        if (!name.equals("-")){
                            supplier.setSuppName(name);
                        }
                        if (!contractName.equals("-")){
                            supplier.setContractName(contractName);
                        }
                        if (!phone.equals("-")){
                            String phoneNum = phone.substring(0,3)+"-"+phone.substring(3,6)+"-"+phone.substring(6,10);
                            supplier.setPhone(phoneNum);
                        }
                        if (!email.equals("-")){
                            supplier.setEmail(email);
                        }
                        System.out.println("Success - Supplier has been updated!");
                        FileHandle.saveSupplier(supplierList);
                    } else {
                        System.out.println("Failed - Please try again!");
                    }
                    break whileyaya;
                } else {
                    System.out.println("Input Incorrect");
                }
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

    //Check if user input for supplier is within index of supplierList
    public static boolean findID(String id,int supplierListSize){
        int checker = Integer.parseInt(id);
        return checker <= supplierListSize && checker > 0;
    }

}