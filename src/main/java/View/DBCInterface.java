/*
  Author: Joel Eriksson Sinclair
  Id: ai7892
  Study program: Sys 21h
*/

package View;

import javax.swing.plaf.nimbus.State;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;

public class DBCInterface {
    public Connection getDataBaseConnection(){
        String url = "jdbc:postgresql://pgserver.mau.se:5432/onlinestorejmk";
        String user = "ai7892";
        String password = "sndhw9t4";

        Connection con;

        try{
            con = DriverManager.getConnection(url, user, password);

            return con;
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
    public boolean testDataBaseConnection(){
        String url = "jdbc:postgresql://pgserver.mau.se:5432/onlinestorejmk";
        String user = "ai7892";
        String password = "sndhw9t4";

        Connection con;

        try{
            con = DriverManager.getConnection(url, user, password);

            return true;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    //// API ////

    //TODO Läs och räkna ut slutpris efter discount och fixa snyggare utskrift //Måns
    public String[] getAllAvailableProducts() throws SQLException {
        Connection con = getDataBaseConnection();
        Statement stmt = con.createStatement();
        String QUERY = String.format("Select * from product where quantity > 0");

        ResultSet rs = stmt.executeQuery(QUERY);
        ArrayList<String> table = new ArrayList<>();

        while (rs.next()){
            String row = "";

            // product
            row += String.format("%d|", rs.getInt("id"));           // product_id
            row += String.format("%s|", rs.getString("name"));      // product_name
            row += String.format("%s|", rs.getString("type"));      // product_type
            row += String.format("%d|", rs.getInt("quantity"));
            row += String.format("%f|", rs.getFloat("baseprice"));

            // discounted_product & discount
            //row += String.format("%d|", rs.getInt("percentage"));
            //row += String.format("%s|", rs.getString("discount_code"));
            //row += String.format("%f|", rs.getFloat("discount_amount"));
            //row += String.format("%f|", rs.getFloat("final_price"));

            // supplier
            row += String.format("%d|", rs.getInt("supplier_id"));
            //row += String.format("%s", rs.getString("supplier_name"));        //nyckla skiten?

            table.add(row);
        }



        return table.toArray(new String[0]);
    }
    // Product
    public String[] getAvailableProducts(String product_name, String product_type, String supplier_name, double minPrice, double maxPrice) throws Exception{
        Connection con = getDataBaseConnection();
        Statement stmt = con.createStatement();
        String QUERY = String.format(Locale.US, "SELECT * FROM f_available_products('%s', '%s', '%s', %f, %f);",
                product_name, product_type, supplier_name, minPrice, maxPrice);

        ResultSet rs = stmt.executeQuery(QUERY);

        ArrayList<String> table = new ArrayList<>();

        while (rs.next()){
            String row = "";

            // product
            row += String.format("%d|", rs.getInt("id"));           // product_id
            row += String.format("%s|", rs.getString("name"));      // product_name
            row += String.format("%s|", rs.getString("type"));      // product_type
            row += String.format("%d|", rs.getInt("quantity"));
            row += String.format("%f|", rs.getFloat("baseprice"));

            // discounted_product & discount
            row += String.format("%d|", rs.getInt("percentage"));
            row += String.format("%s|", rs.getString("discount_code"));
            row += String.format("%f|", rs.getFloat("discount_amount"));
            row += String.format("%f|", rs.getFloat("final_price"));

            // supplier
            row += String.format("%d|", rs.getInt("supplier_id"));
            row += String.format("%s", rs.getString("supplier_name"));
            table.add("\n");
            table.add(row);
        }

        return table.toArray(new String[0]);        //TODO Formatering, fixa fina tabeller
    }
    public String[] getCurrentlyDiscountedProducts(String product_name, String product_type, String supplier_name, double minPrice, double maxPrice) throws Exception{
        Connection con = getDataBaseConnection();
        Statement stmt = con.createStatement();
        //String QUERY = String.format("SELECT * FROM f_currently_discounted_products('%s', '%s', '%s', %f, %f);",
         //       product_name, product_type, supplier_name, minPrice, maxPrice);
        String QUERY = "f_currently_discounted_products('name', 'type', 'supp name', 0, 10)";
        ResultSet rs = stmt.executeQuery(QUERY);

        ArrayList<String> table = new ArrayList<>();

        while (rs.next()){
            String row = "";

            // product
            row += String.format("%d|", rs.getInt("id"));           // product_id
            row += String.format("%s|", rs.getString("name"));      // product_name
            row += String.format("%s|", rs.getString("type"));      // product_type
            row += String.format("%d|", rs.getInt("quantity"));
            row += String.format("%f|", rs.getFloat("baseprice"));

            // discounted_product & discount
            row += String.format("%d|", rs.getInt("percentage"));
            row += String.format("%s|", rs.getString("discount_code"));
            row += String.format("%f|", rs.getFloat("discount_amount"));
            row += String.format("%f|", rs.getFloat("final_price"));

            // supplier
            row += String.format("%d|", rs.getInt("supplier_id"));
            row += String.format("%s", rs.getString("supplier_name"));

            table.add(row);
        }

        return table.toArray(new String[0]);
    }
    public String getProductByID(int product_id) throws Exception {
        Connection con = getDataBaseConnection();
        Statement stmt = con.createStatement();
        String QUERY = String.format("SELECT * FROM f_get_product(%d);", product_id);
        ResultSet rs = stmt.executeQuery(QUERY);

        String product_data = "";

        if(rs.next()){
            // product
            product_data += String.format("%d|", rs.getInt("id"));           // product_id
            product_data += String.format("%s|", rs.getString("name"));      // product_name
            product_data += String.format("%s|", rs.getString("type"));      // product_type
            product_data += String.format("%d|", rs.getInt("quantity"));
            product_data += String.format("%f|", rs.getFloat("baseprice"));

            // discounted_product & discount
            product_data += String.format("%d|", rs.getInt("percentage"));
            product_data += String.format("%s|", rs.getString("discount_code"));
            product_data += String.format("%f|", rs.getFloat("discount_amount"));
            product_data += String.format("%f|", rs.getFloat("final_price"));

            // supplier
            product_data += String.format("%d|", rs.getInt("supplier_id"));
            product_data += String.format("%s", rs.getString("supplier_name"));
        }

        return product_data;
    }

    //--- ADMIN
    public String[] getAllProducts(String product_name, String product_type, String supplier_name, double minPrice, double maxPrice) throws Exception{
        Connection con = getDataBaseConnection();
        Statement stmt = con.createStatement();
        String QUERY = String.format("SELECT * FROM f_all_products('%s', '%s', '%s', %f, %f);",
                product_name, product_type, supplier_name, minPrice, maxPrice);
        ResultSet rs = stmt.executeQuery(QUERY);

        ArrayList<String> table = new ArrayList<>();

        while (rs.next()){
            String row = "";

            // product
            row += String.format("%d|", rs.getInt("id"));           // product_id
            row += String.format("%s|", rs.getString("name"));      // product_name
            row += String.format("%s|", rs.getString("type"));      // product_type
            row += String.format("%d|", rs.getInt("quantity"));
            row += String.format("%f|", rs.getFloat("baseprice"));

            // discounted_product & discount
            row += String.format("%d|", rs.getInt("percentage"));
            row += String.format("%s|", rs.getString("discount_code"));
            row += String.format("%f|", rs.getFloat("discount_amount"));
            row += String.format("%f|", rs.getFloat("final_price"));

            // supplier
            row += String.format("%d|", rs.getInt("supplier_id"));
            row += String.format("%s", rs.getString("supplier_name"));

            table.add(row);
        }

        return table.toArray(new String[0]);
    }
    public String[] getDiscountedProductHistory() throws Exception{
        Connection con = getDataBaseConnection();
        Statement stmt = con.createStatement();
        String QUERY = String.format("SELECT * FROM discounted_product;");
        ResultSet rs = stmt.executeQuery(QUERY);

        ArrayList<String> table = new ArrayList<>();

        while (rs.next()){
            String row = "";

            row += String.format("%d|", rs.getInt("id"));
            row += String.format("%s|", rs.getString("discount_code"));
            row += String.format("%d|", rs.getInt("product_id"));
            row += String.format("%f|", rs.getInt("percentage"));
            row += String.format("%s|", rs.getString("startdate"));
            row += String.format("%s", rs.getString("enddate"));

            table.add(row);
        }

        return table.toArray(new String[0]);
    }

    // Get the most ordered products during the specified month.
    // Returns up to 3 rows
    // params yearAndMonth Formatted as "yyyy-MM"
    public String[] getTopSellers(String yearAndMonth) throws Exception{
        Connection con = getDataBaseConnection();
        Statement stmnt = con.createStatement();
        String QUERY = String.format("select * FROM f_top_ordered_by_month('%s');", yearAndMonth);
        ResultSet rs = stmnt.executeQuery(QUERY);

        ArrayList<String> table = new ArrayList<>();

        while (rs.next()){
            String row = "";

            row += String.format("%s|", rs.getDate("order_month").toString());
            row += String.format("%d|", rs.getInt("product_id"));
            row += String.format("%d", rs.getInt("total_ordered"));

            table.add(row);
        }

        return table.toArray(new String[0]);
    }

    public void addProduct(String name, String type, int quantity, float baseprice, int supplier_id) throws Exception{
        Connection con = getDataBaseConnection();
        Statement stmt = con.createStatement();
        String QUERY = String.format(Locale.US, "INSERT INTO product (name, type, quantity, baseprice, supplier_id) " +
                "VALUES ('%s', '%s', '%d', '%f', '%d');", name, type, quantity, baseprice, supplier_id);
        System.out.println("addProduct(...) === " + QUERY);
        stmt.execute(QUERY);
    }
    public boolean removeProduct(int product_id) throws Exception{
        Connection con = getDataBaseConnection();
        Statement stmt = con.createStatement();
        String QUERY = String.format("select * from f_remove_product(%d)", product_id);
        ResultSet rs = stmt.executeQuery(QUERY);
        if(rs.next()){
            return rs.getBoolean(1);
        }

        return false;
    }
    public void setQuantity(int id, int quantity) throws Exception{
        Connection con = getDataBaseConnection();
        Statement stmt = con.createStatement();
        String QUERY = String.format("select setQuantity(%d, %d);", id, quantity);
        stmt.executeQuery(QUERY);
    }

    // Supplier
    //--- ADMIN
    public String[] getSuppliers() throws Exception{
        Connection con = getDataBaseConnection();
        Statement stmt = con.createStatement();
        String QUERY = "SELECT * FROM supplier;";
        ResultSet rs = stmt.executeQuery(QUERY);

        ArrayList<String> table = new ArrayList<>();

        while (rs.next()){
            String row = "";

            row += String.format("%d|", rs.getInt("id"));
            row += String.format("%s|", rs.getString("phone"));
            row += String.format("%s|", rs.getString("address"));
            row += String.format("%s|", rs.getString("city"));
            row += String.format("%s|", rs.getString("country"));
            row += String.format("%s|", rs.getString("supplier_name"));

            table.add(row);
        }

        return table.toArray(new String[0]);
    }
    public void addSupplier(String phone, String street, String city, String country, String supplier_name) throws Exception{
        Connection con = getDataBaseConnection();
        Statement stmt = con.createStatement();
        String QUERY = String.format("INSERT INTO supplier (phone, address, city, country, supplier_name) " +
                "VALUES ('%s', '%s', '%s', '%s', '%s');", phone, street, city, country, supplier_name);
        stmt.execute(QUERY);
    }

    // Discount
    //--- ADMIN
    public void addDiscount(String code, String reason) throws Exception{
        Connection con = getDataBaseConnection();
        Statement stmt = con.createStatement();
        String QUERY = String.format("INSERT INTO discount (code, reason) VALUES ('%s', '%s');", code, reason);
        stmt.execute(QUERY);
    }
    // @param percentage An Integer in the range of 0-100
    public void addDiscountedProduct(String discount_code, int product_id, int percentage, String startdate, String enddate) throws Exception{
        Connection con = getDataBaseConnection();
        Statement stmt = con.createStatement();
        String QUERY = String.format("INSERT INTO discounted_product (discount_code, product_id, percentage, startdate, enddate) " +
                "VALUES ('%s', %d, %d, '%s', '%s');", discount_code, product_id, percentage, startdate, enddate);
        stmt.execute(QUERY);
    }

    // Order
    // NOTE: Currently we get Order.totalCost by getting finalPrice from getOrderedProductsByOrder,
    // and then adding them together. Maybe this should be included in getCustomersOrder() view?
    public String[] getCustomersOrders(int customer_id) throws Exception{
        Connection con = getDataBaseConnection();
        Statement stmt = con.createStatement();
        String QUERY = String.format("SELECT * FROM orders where customer_id = %d;", customer_id);
        ResultSet rs = stmt.executeQuery(QUERY);

        ArrayList<String> table = new ArrayList<>();

        while (rs.next()){
            String row = "";

            row += String.format("%d|", rs.getInt("id"));
            row += String.format("%s|", rs.getDate("order_date").toString());
            row += String.format("%b|", rs.getBoolean("confirmed"));
            row += String.format("%d", rs.getInt("customer_id"));

            table.add(row);
        }

        return table.toArray(new String[0]);
    }
    public String[] getOrderedProductsByOrder(int order_id) throws Exception{
        Connection con = getDataBaseConnection();
        Statement stmt = con.createStatement();
        String QUERY = String.format("SELECT * FROM f_ordered_products(%d);", order_id);
        ResultSet rs = stmt.executeQuery(QUERY);

        ArrayList<String> table = new ArrayList<>();

        while (rs.next()){
            String row = "";

            // ordered_product
            row += String.format("%d|", rs.getInt("order_id"));
            row += String.format("%d|", rs.getInt("ordered_quantity"));
            row += String.format("%f|", rs.getFloat("total_price"));

            // product
            row += String.format("%d|", rs.getInt("id"));           // product_id
            row += String.format("%s|", rs.getString("name"));      // product_name
            row += String.format("%s|", rs.getString("type"));      // product_type
            row += String.format("%d|", rs.getInt("quantity"));
            row += String.format("%f|", rs.getFloat("baseprice"));

            // discounted_product & discount
            row += String.format("%d|", rs.getInt("percentage"));
            row += String.format("%s|", rs.getString("discount_code"));
            row += String.format("%f|", rs.getFloat("discount_amount"));
            row += String.format("%f|", rs.getFloat("final_price"));

            // supplier
            row += String.format("%d|", rs.getInt("supplier_id"));
            row += String.format("%s", rs.getString("supplier_name"));

            table.add(row);
        }

        return table.toArray(new String[0]);
    }

    /*
        TODO: Can we make SQL take in an array of both product_id and quantity?
         Or do we have to keep this mess?
     */
    // Place order manually inserts into orders and ordered_product
    // ordered_product events deal with updating quantity on the affected products.
    public void placeOrder(int customer_id, int[][] productsAndQuantity) throws Exception {
        Connection con = getDataBaseConnection();
        con.setAutoCommit(false);
        Statement stmt = con.createStatement();
        // Insert into orders and get the id from this new row.
        String QUERY = String.format("INSERT into orders (order_date, confirmed, customer_id)" +
                " VALUES ('%s', %b, %d) returning orders.id;", getCurrentDate(), false, customer_id);
        ResultSet rs = stmt.executeQuery(QUERY);
        int order_id = -1;
        if(rs.next()){
            order_id = rs.getInt(1);
        }

        // Build a query to insert multiple rows into ordered_products, match with the order_id
        QUERY = "INSERT into ordered_product VALUES ";

        stmt = con.createStatement();
        for (int i = 0; i < productsAndQuantity.length; i++) {
            int product_id = productsAndQuantity[i][0];
            int quantity = productsAndQuantity[i][1];

            QUERY += String.format("(%d,%d,%d)", order_id, product_id, quantity);

            if(i >= productsAndQuantity.length - 1)
                QUERY += ";";
            else
                QUERY += ",";
        }

        stmt.execute(QUERY);

        // Only commit when all goes well!
        con.commit();
    }

    public void updateQuantityOfOrderedProduct(int product_id, int newQuantity, int order_id) throws Exception {
        Connection con = getDataBaseConnection();
        Statement stmt = con.createStatement();
        String QUERY = String.format("UPDATE ordered_product SET quantity = %d WHERE product_id = %d AND order_id = %d;", newQuantity, product_id, order_id);
        stmt.execute(QUERY);
    }
    public void getTotalOrderCost(int order_id){
        // SQL function, count discounts!
        // DONT NEED...? Can get from getOrderedProductsByOrder()
    }

    //--- ADMIN
    public String[] getUnconfirmedOrders() throws Exception{
        Connection con = getDataBaseConnection();
        Statement stmt = con.createStatement();
        String QUERY = "SELECT * FROM orders where confirmed = false ORDER BY id;";
        ResultSet rs = stmt.executeQuery(QUERY);

        ArrayList<String> table = new ArrayList<>();

        while (rs.next()){
            String row = "";

            row += String.format("%d|", rs.getInt("id"));
            row += String.format("%s|", rs.getDate("order_date").toString());
            row += String.format("%b|", rs.getBoolean("confirmed"));
            row += String.format("%d", rs.getInt("customer_id"));

            table.add(row);
        }

        return table.toArray(new String[0]);
    }

    public void confirmOrder(int order_id) throws Exception {
        confirmOrder(order_id, true);
    }
    public void confirmOrder(int order_id, boolean confirmStatus) throws Exception{
        Connection con = getDataBaseConnection();
        Statement stmt = con.createStatement();
            String QUERY = String.format("UPDATE orders SET confirmed = %b WHERE id = %d;", confirmStatus, order_id);
        stmt.execute(QUERY);
    }
    // TODO: Untested java
    public void removeOrder(int order_id) throws Exception{
        Connection con = getDataBaseConnection();
        Statement stmt = con.createStatement();
        String QUERY = String.format("DELETE from orders where id = %d;", order_id);
        stmt.executeQuery(QUERY);
    }

    // Account
    public boolean registerAccount(String fullName, String email, String street, String city, String country, String phone) throws Exception{
        return registerAccount(fullName, email, street, city, country, phone, false);
    }
    public boolean registerAccount(String fullName, String email, String street, String city, String country, String phone, boolean admin_status) throws Exception{
        Connection con = getDataBaseConnection();
        String QUERY = String.format("select registerAccount('%s', '%s', '%s', '%s', '%s', '%s', %b);",
                fullName, email, street, city, country, phone, admin_status);
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(QUERY);

        if(rs.next()){
            return rs.getBoolean(1);
        }
        return false;
    }

    public String getAccount(String accountEmail) throws Exception{
        Connection con = getDataBaseConnection();
        String QUERY = String.format("SELECT * FROM account WHERE \"email\" = '%s'", accountEmail);
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(QUERY);

        StringBuilder accountRow = new StringBuilder();
        while (rs.next()){
            String row = "";

            accountRow.append(String.format("%d|", rs.getInt("id")));
            accountRow.append(String.format("%s|", rs.getString("fullName")));
            accountRow.append(String.format("%s|", rs.getString("email")));
            accountRow.append(String.format("%s|", rs.getString("address")));
            accountRow.append(String.format("%s|", rs.getString("city")));
            accountRow.append(String.format("%s|", rs.getString("country")));
            accountRow.append(String.format("%s|", rs.getString("phone")));
            accountRow.append(String.format("%s", rs.getBoolean("admin_status")));
        }

        return accountRow.toString();
    }
    public String getAccount(int account_id) throws Exception{
        Connection con = getDataBaseConnection();
        String QUERY = String.format("SELECT * FROM account WHERE \"id\" = '%s'", account_id);
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(QUERY);

        StringBuilder accountRow = new StringBuilder();
        while (rs.next()){
            String row = "";

            accountRow.append(String.format("%d|", rs.getInt("id")));
            accountRow.append(String.format("%s|", rs.getString("fullName")));
            accountRow.append(String.format("%s|", rs.getString("email")));
            accountRow.append(String.format("%s|", rs.getString("address")));
            accountRow.append(String.format("%s|", rs.getString("city")));
            accountRow.append(String.format("%s|", rs.getString("country")));
            accountRow.append(String.format("%s|", rs.getString("phone")));
            accountRow.append(String.format("%s", rs.getBoolean("admin_status")));
        }

        return accountRow.toString();
    }

    private String getCurrentDate(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("uuuu-MM-dd");
        LocalDate localDate = LocalDate.now();
        return dtf.format(localDate);
    }
}
