/*
  Author: Joel Eriksson Sinclair
  Id: ai7892
  Study program: Sys 21h
*/

package Model;

import java.util.ArrayList;

public class Cart {
    private OrderedItem[] orderedItems;
    private int nbrOfOrderedItems = 0;

    public Cart(){
        orderedItems = new OrderedItem[0];
    }

    // api
    public void removeAtIndex(int index){
        if(index >= 0 && index <= nbrOfOrderedItems){
            shiftToLeft(index);
            nbrOfOrderedItems--;
        }
    }
    public void removeByProductID(int product_id){
        int indexToRemove = -1;
        for (int i = 0; i < orderedItems.length; i++) {
            if(orderedItems[i].getProduct_id() == product_id){
                indexToRemove = i;
                break;
            }
        }
        if(indexToRemove >= 0){
            removeAtIndex(indexToRemove);
        }
    }

    // Return -1 if it does not exist
    public int getIndexOfProductID(int product_id){
        int indexOfProduct = -1;
        for (int i = 0; i < orderedItems.length; i++) {
            if(orderedItems[i].getProduct_id() == product_id){
                indexOfProduct = i;
                break;
            }
        }
        return indexOfProduct;
    }
    public void addToCart(int product_id, int quantity){
        if(nbrOfOrderedItems >= orderedItems.length){
            increaseArraySize();
        }

        orderedItems[nbrOfOrderedItems] = new OrderedItem(product_id, quantity);
        nbrOfOrderedItems++;
    }
    public OrderedItem getOrderAtIndex(int index){
        return orderedItems[index];
    }

    // helpers
    private void shiftToLeft(int index){
        for (int i = index; i < nbrOfOrderedItems; i++) {
            if(i + 1 < orderedItems.length){
                orderedItems[i] = orderedItems[i+1];
            }
            else {
                orderedItems[i] = null;
            }
        }
    }
    private void increaseArraySize(){
        OrderedItem[] newArray = new OrderedItem[orderedItems.length + 10];

        System.arraycopy(orderedItems, 0, newArray, 0, orderedItems.length);

        orderedItems = newArray;
    }

    // get&set
    public OrderedItem[] getOrderedItems(){
        return orderedItems;
    }
    public String[] getCartStrings(){
        ArrayList<String> strings = new ArrayList<>();
        for (OrderedItem item: orderedItems) {
            strings.add(item.toString());                   //VARFÖR ÄR DU NULL ITEMJÄVEL
        }
        return strings.toArray(new String[0]);
    }

}
