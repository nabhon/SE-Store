import java.util.ArrayList;

public class Sort {
    public static void sortByNameASC(ArrayList<Product> productShow){
        Product temp;
        for (int i = 1; i < productShow.size()-1; i++) {
            for (int j = productShow.size()-1 ; j >= i  ; j--) {
                if ((productShow.get(j).findValue())<productShow.get(j-1).findValue()){
                    temp = productShow.get(j);
                    productShow.set(j,productShow.get(j-1));
                    productShow.set(j-1,temp);
                }
            }
        }
    }

    public static void sortByPriceDESC(ArrayList<Product> productShow){
        Product temp;
        for (int i = 1; i < productShow.size()-1; i++) {
            for (int j = productShow.size()-1 ; j >= i  ; j--) {
                if ((productShow.get(j).getPrice())>productShow.get(j-1).getPrice()){
                    temp = productShow.get(j);
                    productShow.set(j,productShow.get(j-1));
                    productShow.set(j-1,temp);
                }
            }
        }
    }
}
