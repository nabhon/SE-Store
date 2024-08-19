public class Product {
    private int id;
    private String name;
    private double price;
    private int quality;
    Product() {
        this.id = 0;
        this.name = "Unknown";
        this.price = 0;
        this.quality = 0;
    }

    Product(int id,String name,double price,int quality){
        this.id = id;
        this.name = name;
        this.price = price;
        this.quality = quality;
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

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQuality() {
        return quality;
    }

    public void setQuality(int quality) {
        this.quality = quality;
    }
}
