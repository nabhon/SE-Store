import java.util.ArrayList;

public class Sort {

    public static void sortByNameASC(ArrayList<Product> productShow){
        Product temp,min;
        int minIndex;
        for (int i = 0; i < productShow.size(); i++) {
            temp = productShow.get(i);
            min = productShow.get(i);
            minIndex = i;
            for (int j = i+1 ; j < productShow.size()  ; j++) {
                if (min.findValue()>productShow.get(j).findValue()){
                    min = productShow.get(j);
                    minIndex = j;
                }
            }
            productShow.set(i,min);
            productShow.set(minIndex,temp);
        }
    }

    public static void sortByNameDESC(ArrayList<Product> productShow){
        Product temp,max;
        int maxIndex;
        for (int i = 0; i < productShow.size(); i++) {
            temp = productShow.get(i);
            max = productShow.get(i);
            maxIndex = i;
            for (int j = i+1 ; j < productShow.size()  ; j++) {
                if (max.findValue()<productShow.get(j).findValue()){
                    max = productShow.get(j);
                    maxIndex = j;
                }
            }
            productShow.set(i,max);
            productShow.set(maxIndex,temp);
        }
    }

    public static void sortByPriceASC(ArrayList<Product> productShow){
        Product temp,min;
        int minIndex;
        for (int i = 0; i < productShow.size(); i++) {
            temp = productShow.get(i);
            min = productShow.get(i);
            minIndex = i;
            for (int j = i+1 ; j < productShow.size() ; j++) {
                if (min.getPrice()>productShow.get(j).getPrice()){
                    min = productShow.get(j);
                    minIndex = j;
                }
            }
            productShow.set(i,min);
            productShow.set(minIndex,temp);
        }
    }

    public static void sortByPriceDESC(ArrayList<Product> productShow){
        Product temp,max;
        int maxIndex;
        for (int i = 0; i < productShow.size(); i++) {
            temp = productShow.get(i);
            max = productShow.get(i);
            maxIndex = i;
            for (int j = i+1 ; j < productShow.size()  ; j++) {
                if (max.getPrice()<productShow.get(j).getPrice()){
                    max = productShow.get(j);
                    maxIndex = j;
                }
            }
            productShow.set(i,max);
            productShow.set(maxIndex,temp);
        }
    }
}
