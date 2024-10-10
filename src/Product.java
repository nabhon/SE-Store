public class Product {
    private int id;
    private String name;
    private double price;
    private int quality;
    private int suppID;
    Product() {
        this.id = 0;
        this.name = "Unknown";
        this.price = 0;
        this.quality = 0;
        this.suppID = 0;
    }

    Product(int id,String name,double price,int quality,int suppID){
        this.id = id;
        this.name = name;
        this.price = price;
        this.quality = quality;
        this.suppID = suppID;
    }

    public int findValue(){
        return name.charAt(0);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuality() {
        return quality;
    }

    public void setQuality(int quality) {
        this.quality = quality;
    }public int getSuppID(){
        return this.suppID;
    }

    @Override
    public String toString() {
        String price = String.format("$%.2f",getPrice());
        return String.format("%d\t%s\t%s\t%d\t%d",getId(),getName(),price,getQuality(),getSuppID());
    }
}
