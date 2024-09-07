public class Member {
    private int memberID;
    private  String memberName;
    private String memberLastName;
    private String email;
    private String password;
    private String phone;
    private double memberPoint;

    public Member(int memberID, String memberName, String memberLastName, String email, String password, String phone, double memberPoint) {
        this.memberID = memberID;
        this.memberName = memberName;
        this.memberLastName = memberLastName;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.memberPoint = memberPoint;
    }

    public int getMemberID() {
        return memberID;
    }

    public void setMemberID(int memberID) {
        this.memberID = memberID;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getMemberLastName() {
        return memberLastName;
    }

    public void setMemberLastName(String memberLastName) {
        this.memberLastName = memberLastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public double getMemberPoint() {
        return memberPoint;
    }

    public void setMemberPoint(double memberPoint) {
        this.memberPoint = memberPoint;
    }
}
