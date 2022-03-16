/*
  Author: Joel Eriksson Sinclair
  Id: ai7892
  Study program: Sys 21h
*/

package Model;

public class OrderedItem {
    private int product_id;
    private int quantity;

    public OrderedItem(int product_id, int quantity){
        this.product_id = product_id;
        this.quantity = quantity;
    }

    public int getProduct_id() {
        return product_id;
    }
    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("OrderedItem{");
        sb.append("product_id=").append(product_id);
        sb.append(", quantity=").append(quantity);
        sb.append('}');
        return sb.toString();
    }
}
