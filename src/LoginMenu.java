import java.util.ArrayList;
import java.util.Scanner;

public class LoginMenu {
    public static void loginMenu(ArrayList<Member> memberList, ArrayList<Product> productList, ArrayList<Supplier> supplierList) {
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
                Member currentLogin = getMember(loginID,memberList);
                if (checkIDValid(memberList,loginID)){
                    Main.getUserData(currentLogin,productList,supplierList);
                } else {
                    System.out.println("Error! - Your Account are Expired!");
                }
                break;
            }
        }
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

    //Method to find loginID of a member
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

    //Get a Member from memberList using loginID to find
    public static Member getMember(int loginID,ArrayList<Member> memberList){
        for (Member m:memberList) {
            if (m.getMemberID()==loginID){
                return m;
            }
        }
        return new Member();
    }

}
