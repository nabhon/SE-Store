import java.util.ArrayList;

public class Sort {

    public static void sortByNameASC(ArrayList<Product> productShow){
        for (int i = 0; i < productShow.size(); i++) {
            Product temp = productShow.get(i);
            int minIndex = i;
            for (int j = i+1 ; j < productShow.size()  ; j++) {
                if (productShow.get(minIndex).findValue()>productShow.get(j).findValue()){
                    minIndex = j;
                }
            }
            productShow.set(i,productShow.get(minIndex));
            productShow.set(minIndex,temp);
        }
    }

    public static void sortByNameDESC(ArrayList<Product> productShow){
        for (int i = 0; i < productShow.size(); i++) {
            Product temp = productShow.get(i);
            int maxIndex = i;
            for (int j = i+1 ; j < productShow.size()  ; j++) {
                if (productShow.get(maxIndex).findValue()<productShow.get(j).findValue()){
                    maxIndex = j;
                }
            }
            productShow.set(i,productShow.get(maxIndex));
            productShow.set(maxIndex,temp);
        }
    }

    public static void sortByPriceASC(ArrayList<Product> productShow){
        for (int i = 0; i < productShow.size(); i++) {
            Product temp = productShow.get(i);
            int minIndex = i;
            for (int j = i+1 ; j < productShow.size() ; j++) {
                if (productShow.get(minIndex).getPrice()>productShow.get(j).getPrice()){
                    minIndex = j;
                }
            }
            productShow.set(i,productShow.get(minIndex));
            productShow.set(minIndex,temp);
        }
    }

    public static void sortByPriceDESC(ArrayList<Product> productShow){
        for (int i = 0; i < productShow.size(); i++) {
            Product temp = productShow.get(i);
            int maxIndex = i;
            for (int j = i+1 ; j < productShow.size()  ; j++) {
                if (productShow.get(maxIndex).getPrice()<productShow.get(j).getPrice()){
                    maxIndex = j;
                }
            }
            productShow.set(i,productShow.get(maxIndex));
            productShow.set(maxIndex,temp);
        }
    }
}
