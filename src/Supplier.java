public class Supplier {
    private String suppID;
    private String suppName;
    private String contractName;
    private String address;
    private String phone;
    private String email;

    public Supplier(String suppID, String suppName, String contractName, String address, String phone, String email) {
        this.suppID = suppID;
        this.suppName = suppName;
        this.contractName = contractName;
        this.address = address;
        this.phone = phone;
        this.email = email;
    }

    public String getSuppID() {
        return suppID;
    }

    public void setSuppID(String suppID) {
        this.suppID = suppID;
    }

    public void setSuppName(String suppName) {
        this.suppName = suppName;
    }

    public void setContractName(String contractName) {
        this.contractName = contractName;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSuppName() {
        return suppName;
    }

    public String getContractName() {
        return contractName;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return String.format("%s\t%s\t%s\t%s\t%s\t%s",getSuppID(),getSuppName(),getContractName(),getAddress(),getPhone(),getEmail());
    }
}
