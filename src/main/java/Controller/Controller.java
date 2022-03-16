/*
  Author: Joel Eriksson Sinclair
  Id: ai7892
  Study program: Sys 21h
*/

package Controller;

import Model.Account;
import Model.Cart;
import Model.OrderedItem;
import Model.Product;
import View.*;

public class Controller {
    private Cart cart;
    private Account loggedInAccount;
    private ConsoleTest consoleView;
    private LoginWindow loginWindow;
    private RegisterWindow registerWindow;
    private DBCInterface dbc;

    public Controller(){
        dbc = new DBCInterface();
        consoleView = new ConsoleTest();
    }

    // ----- Call Backs ------ //
    public void loginWindow(){
         loginWindow = new LoginWindow(this);
    }
    public void registerWindow(){
        registerWindow = new RegisterWindow(this);
    }

    public void loginPressed(){
        String email = loginWindow.getEmail();
        try{
            String dbData = dbc.getAccount(email);
            String[] accountData = splitRow(dbData);

            consoleView.showMessage("account retrieved.");

            tryLogin(accountData);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void registerAccountPressed(){
        // view.getStuff..()
        String full_name = "java has no name"; //view.getRegisterInputName();
        String email = registerWindow.getEmail();

        try{
            dbc.registerAccount(full_name, email, "Str", "Cty", "Cntry", "070-");

            registerWindow.dispose();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void showProductsPressed(){
        String product_name = consoleView.getName();
        String product_type = consoleView.getType();
        String supplier_name = consoleView.getName();
        int minPrice = consoleView.getInteger();
        int maxPrice = consoleView.getInteger();

        if(loggedInAccount != null && loggedInAccount.isAdmin()){
            consoleView.showMessage("Showing All Products");

            try {
                String[] products = dbc.getAllProducts(product_name, product_type, supplier_name, minPrice, maxPrice);

                for (String row: products) {
                    consoleView.showMessage(row);
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
        else {
            consoleView.showMessage("Showing Available Products");

            try {
                String[] products = dbc.getAvailableProducts(product_name, product_type, supplier_name, minPrice, maxPrice);
                for (String row: products) {
                    consoleView.showMessage(row);
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    public void showCurrentlyDiscountedProductsPressed(){
        String product_name = consoleView.getName();
        String product_type = consoleView.getName();
        String supplier_name = consoleView.getName();
        int minPrice = consoleView.getInteger();
        int maxPrice = consoleView.getInteger();

        try {
            String[] products = dbc.getCurrentlyDiscountedProducts(product_name, product_type, supplier_name, minPrice, maxPrice);
            for (String row: products) {
                consoleView.showMessage(row);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    // CUSTOMER
    public void addToCartPressed(){
        int product_id = consoleView.getID(); // view.getProductListSelection..
        int quantity = consoleView.getInteger();
        if(cart.getIndexOfProductID(product_id) < 0){
            cart.addToCart(product_id, quantity);
        }
    }
    /*
    public void removeFromCartPressed(){
        int product_id = consoleView.getID(); // view.getCartSelection
        cart.removeByProductID(product_id);

        showCart(cart.getCartStrings());
    }
     */
    public void showCartPressed(){
        OrderedItem[] orderedItems = cart.getOrderedItems();
        for (OrderedItem item: orderedItems) {
            int product_id = item.getProduct_id();
            try {
                String row = dbc.getProductByID(product_id);
                row += " -- Quantity: " + item.getQuantity();
                consoleView.showMessage(row);
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public void placeOrderPressed(){
        // if logged in.. THOUGHT: we should only see this button when we are logged in.... .. ?
        if(loggedInAccount != null){
            OrderedItem[] orderedItems = cart.getOrderedItems();
            if(orderedItems.length > 0){
                try{
                    // SQL transaction..
                    // insert into orders AND insert into ordered_product
                    // account_id = loggedInAccount.getId()
                    // order_id = auto incremented
                    int[][] idAndQuantity = new int[orderedItems.length][2];
                    for (int i = 0; i < orderedItems.length; i++) {
                        idAndQuantity[i][0] = orderedItems[i].getProduct_id();
                        idAndQuantity[i][1] = orderedItems[i].getQuantity();
                    }
                    dbc.placeOrder(loggedInAccount.getId(), idAndQuantity);
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        else {
            consoleView.showError("Must be logged in to place order.");
        }
    }
    public void updateOrderQuantityPressed(){
        int product_id = consoleView.getID(); // selected product in list..
        int quantity = consoleView.getInteger();
        int order_id = consoleView.getID(); // currently viewed order..
        try{
            dbc.updateQuantityOfOrderedProduct(product_id, quantity, order_id);
            showOrderInfo(order_id);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    public void abortOrderPressed(){
        try {
            int order_id = consoleView.getID();
            dbc.removeOrder(order_id);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void showMyOrdersPressed(){
        showOrders(loggedInAccount.getId());
    }

    // ADMIN
    public void addProductPressed(){
        String name = consoleView.getName(); //view.getProductName();
        String type = consoleView.getName(); //view.getProductType();
        int quantity = consoleView.getInteger(); //view.getQuantity();
        float baseprice = (float)consoleView.getInteger();
        int supplier_id = consoleView.getID();

        try {
            dbc.addProduct(name, type, quantity, baseprice, supplier_id);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    public void removeProductPressed(){
        int product_id = consoleView.getID();

        try {
            dbc.removeProduct(product_id);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    public void setProductQuantityPressed(){
        int product_id = consoleView.getID();
        int quantity = consoleView.getInteger();

        try {
            dbc.setQuantity(product_id, quantity);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void showUnconfirmedOrdersPressed(){
        try{
            String[] table = dbc.getUnconfirmedOrders();

            // view.setOrderList = table;
            // view.setCategorySelection = order..
            for (String row: table) {
                consoleView.showMessage(row);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    public void confirmOrderPressed(){
        int order_id = consoleView.getID();
        confirmOrder(order_id);
    }
    private void confirmOrder(int order_id){
        confirmOrder(order_id, true);
    }
    private void confirmOrder(int order_id, boolean confirmStatus){
        try{
            dbc.confirmOrder(order_id, confirmStatus);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void showCustomerOrdersPressed(){
        int customer_id = consoleView.getID(); // view.getOrderSelected...()
        showOrders(customer_id);
    }

    public void addSupplierPressed(){
        String phone = consoleView.getPhone();
        String street = consoleView.getStreet();
        String city = consoleView.getCity();
        String country = consoleView.getCountry();
        String supplier_name = consoleView.getName();
        try {
            dbc.addSupplier(phone, street, city, country, supplier_name);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    public void showSuppliersPressed(){
        try{
            String[] suppliers = dbc.getSuppliers();
            for (String row: suppliers) {
                consoleView.showMessage(row);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void addDiscountPressed(){
        String code = consoleView.getEmail();
        String reason = consoleView.getName();
        try {
            dbc.addDiscount(code, reason);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    public void addDiscountedProductPressed(){
        String dicount_code = consoleView.getEmail();
        int product_id = consoleView.getID();
        int percentage = consoleView.getInteger();
        String startdate = consoleView.getDate();
        String enddate = consoleView.getDate();

        try {
            dbc.addDiscountedProduct(dicount_code, product_id, percentage, startdate, enddate);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    public void showDiscountHistoryPressed(){
        try{
            String[] discountHistory = dbc.getDiscountedProductHistory();
            for (String row: discountHistory) {
                consoleView.showMessage(row);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void showTopSellersPressed(){
        try{
            String yearAndMonth = consoleView.getDateTruncMonth();
            String[] topSellers = dbc.getTopSellers(yearAndMonth);

            for (String row: topSellers) {
                consoleView.showMessage(row);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    // ------ helpers ------- //
    /*
    private void showCart(String[] cartInfo){
        view.setListData(cartInfo);
    }
     */

    private void showOrders(int customer_id){
        try {
            String[] table = dbc.getCustomersOrders(customer_id);

            for (String row: table) {
                consoleView.showMessage(row);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private void showOrderInfo(int order_id){
        try {
            String[] products = dbc.getOrderedProductsByOrder(order_id);

            int order_cost = 0;
            for (String s: products) {
                String[] productInfo = splitRow(s);
                int product_total_cost = Integer.parseInt(productInfo[11]);
                order_cost += product_total_cost;
            }

            consoleView.showMessage(String.format("Order Total Cost: %d", order_cost));

            //view.setListData(products);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }


    private void tryLogin(String[] accountData){
        Account account = new Account();
        try {
            account.setId(Integer.parseInt(accountData[0]));
            account.setEmail(accountData[2]);
            account.setAdminStatus(Boolean.parseBoolean(accountData[7]));
        }
        catch (Exception e){
            e.printStackTrace();
        }

        loginAccount(account);
    }


    private void loginAccount(Account account){
        loggedInAccount = account;
        boolean loginStatus = false;
        boolean adminStatus = false;
        if(loggedInAccount != null)
        {
            loginStatus = true;
            adminStatus = loggedInAccount.isAdmin();
            consoleView.showMessage("Login ok for " + loggedInAccount.getEmail());
        }

        // update views..
        // view.updateUserStatus(loginStatus, adminStatus);

        // update cart / orders / etc.
    }

    private String[] splitRow(String row){
        return row.split("\\|"); //escape regex magic.. splits on '|'
    }
}
