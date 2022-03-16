/*
  Author: Joel Eriksson Sinclair
  ID: ai7892
  Study program: Sys 21h
*/

package Model;

public class Product {
    private int id;
    private String name;
    private String type;
    private int quantity;
    private double baseprice;

    private int percentage;
    private String discount_code;
    private double discount_amount;
    private double final_price;

    private int supplier_id;
    private String supplier_name;

    public Product(int id, String name, String type, int quantity, double baseprice,
                   int percentage, String discount_code, double discount_amount, double final_price,
                   int supplier_id, String supplier_name) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.quantity = quantity;
        this.baseprice = baseprice;
        this.percentage = percentage;
        this.discount_code = discount_code;
        this.discount_amount = discount_amount;
        this.final_price = final_price;
        this.supplier_id = supplier_id;
        this.supplier_name = supplier_name;
    }

    public static Product productFromDataRow(String dataRow){
        String[] data = dataRow.split("\\|");
        int id = Integer.parseInt(data[0]);
        String name = data[1];
        String type = data[2];
        int quantity = Integer.parseInt(data[3]);
        double baseprice = Double.parseDouble(data[4]);

        int percentage = Integer.parseInt(data[5]);
        String discount_code = data[6];
        double discount_amount = Double.parseDouble(data[7]);
        double final_price = Double.parseDouble(data[8]);

        int supplier_id = Integer.parseInt(data[9]);
        String supplier_name = data[10];

        return new Product(id, name, type, quantity, baseprice,
                percentage, discount_code, discount_amount, final_price,
                supplier_id, supplier_name);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Product{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", type='").append(type).append('\'');
        sb.append(", quantity=").append(quantity);
        sb.append(", baseprice=").append(baseprice);
        sb.append(", percentage=").append(percentage);
        sb.append(", discount_code='").append(discount_code).append('\'');
        sb.append(", discount_amount=").append(discount_amount);
        sb.append(", final_price=").append(final_price);
        sb.append(", supplier_id=").append(supplier_id);
        sb.append(", supplier_name='").append(supplier_name).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
