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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;


public class Controller {
    private Cart currentCart = new Cart();
    private Account loggedInAccount;
    private ConsoleTest consoleView;
    private LoginWindow loginWindow;
    private RegisterWindow registerWindow;
    private DBCInterface dbc;
    private ConsoleView cView;

    private ArrayList<String> cartList = new ArrayList<String>(); //LIST ON ALL CARTS..?

    public Controller() throws Exception {
        dbc = new DBCInterface();
        consoleView = new ConsoleTest();
        cView = new ConsoleView(this, dbc);
    }

    // ----- Call Backs ------ //
    public void loginWindow() {
        loginWindow = new LoginWindow(this);
    }

    public void registerWindow() {
        registerWindow = new RegisterWindow(this);
    }


    public void loginPressed(String email) {
        try {
            if (dbc.getAccount(email) != null) {
                String dbData = dbc.getAccount(email);
                String[] accountData = splitRow(dbData);
                tryLogin(accountData);
            } else {
                System.out.println("user does not exist!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList getCart() {
        return cartList;
    }

    public void registerAccountPressed() {
        // view.getStuff..()
        String full_name = "java has no name"; //view.getRegisterInputName();
        String email = registerWindow.getEmail();

        try {
            dbc.registerAccount(full_name, email, "Str", "Cty", "Cntry", "070-");

            registerWindow.dispose();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showProductsPressed() {
        String product_name = consoleView.getName();
        String product_type = consoleView.getType();
        String supplier_name = consoleView.getName();
        int minPrice = consoleView.getInteger();
        int maxPrice = consoleView.getInteger();

        if (loggedInAccount != null && loggedInAccount.isAdmin()) {
            consoleView.showMessage("Showing All Products");

            try {
                String[] products = dbc.getAllProducts(product_name, product_type, supplier_name, minPrice, maxPrice);

                for (String row : products) {
                    consoleView.showMessage(row);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            consoleView.showMessage("Showing Available Products");

            try {
                String[] products = dbc.getAvailableProducts(product_name, product_type, supplier_name, minPrice, maxPrice);
                for (String row : products) {
                    consoleView.showMessage(row);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public String[] showAllSelected(String name, String type, String supplier, float minPrice, float maxPrice) throws Exception {
        System.out.println("Returning all products..");
        return dbc.getAvailableProducts(name, type, supplier, minPrice, maxPrice);
        //System.out.println(Arrays.toString(dbc.getAvailableProducts(name, type, supplier, minPrice, maxPrice)));    //TODO Formattera ordentligt

    }

    public String[] showCurrentlyDiscountedProductsPressed() throws Exception {
        System.out.println("Returning all discounted products..");
        return dbc.getCurrentlyDiscountedProducts();

 /*       String product_name = consoleView.getName();
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
        }*/
    }
    // CUSTOMER

    public void addToCartPressed(int product_id, int quantity) throws Exception {
        System.out.println("Adding " + quantity + " of " + dbc.getProductByID(product_id) + " to cart...");
        currentCart.addToCart(product_id,quantity);

        if (currentCart.getIndexOfProductID(product_id) >= 0) { //TODO Denna felchecken kastar exceptions vid första item to add
            currentCart.addToCart(product_id, quantity);
            cartList.add(dbc.getProductByID(product_id));
            System.out.println(cartList.toString());
        }
        /*
        Lite småsaker ska städas upp här och felchecken måste kollas över (tror jag)
        Jag antar att getIndexOfProductID handlar om att checka att valt ID tillhör en produkt
        Kan vi felchecka detta på ett annat sätt? //Måns
         */
    }

    public String cartStrings() {
        return getCart().toString();
    }

    public boolean busyCart(){ ///"har vi en cart?"
        if (cartList.isEmpty()){
            return false;
        }
        return true;
    }

    /*
    public void removeFromCartPressed(){
        int product_id = consoleView.getID(); // view.getCartSelection
        cart.removeByProductID(product_id);

        showCart(cart.getCartStrings());
    }
     */
    public void showCartPressed() {
        OrderedItem[] orderedItems = currentCart.getOrderedItems();

        for (OrderedItem item : orderedItems) {
            int product_id = item.getProduct_id();
            try {
                String row = dbc.getProductByID(product_id);
                row += " -- Quantity: " + item.getQuantity();
                consoleView.showMessage(row);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void placeOrderPressed() {
        // if logged in.. THOUGHT: we should only see this button when we are logged in.... .. ?
        if (loggedInAccount != null) {
            OrderedItem[] orderedItems = currentCart.getOrderedItems();
            if (orderedItems.length > 0) {
                try {
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
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            consoleView.showError("Must be logged in to place order.");
        }
    }

    public void updateOrderQuantityPressed(int productId, int choice) throws Exception {
        int tot = 0;
        if (choice == 1) {
            System.out.println("How many orders would you like to add to this product?");
            int addBy = readInt();
            int curr = dbc.getQuantity(productId);
            tot = addBy + curr;
            System.out.println("Added " + addBy + " to the product. Updated total: " + tot);
        } else if (choice == 2) {
            System.out.println("How many orders would you like to remove from this product?");
            int removeBy = readInt();
            int curr = dbc.getQuantity(productId);
            tot = curr - removeBy;
            System.out.println("Removed " + removeBy + " from the product. Updated: " + tot);
        }
        dbc.updateQuantityOfProduct(productId, tot);
    }

    public int readInt() {
        Scanner scanner = new Scanner(System.in);
        int intOut = scanner.nextInt();
        scanner.nextLine();
        return intOut;
    }

    public boolean validateIdInput(int id) throws Exception {
        if (id > 0) {
            String s = dbc.getProductByID(id);
            if (!s.equals("")) {
                System.out.println("Loading product... " + s);
                return true;
            }
        }
        return false;
    }

    public void showMyOrdersPressed() {
        showOrders(loggedInAccount.getId());
    }


    // ADMIN
    public void addProductPressed() {
        String name = consoleView.getName(); //view.getProductName();
        String type = consoleView.getName(); //view.getProductType();
        int quantity = consoleView.getInteger(); //view.getQuantity();
        float baseprice = (float) consoleView.getInteger();
        int supplier_id = consoleView.getID();

        try {
            dbc.addProduct(name, type, quantity, baseprice, supplier_id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void removeProductPressed() {
        int product_id = consoleView.getID();

        try {
            dbc.removeProduct(product_id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setProductQuantityPressed() {
        int product_id = consoleView.getID();
        int quantity = consoleView.getInteger();

        try {
            dbc.setQuantity(product_id, quantity);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showUnconfirmedOrdersPressed() {
        try {
            String[] table = dbc.getUnconfirmedOrders();

            // view.setOrderList = table;
            // view.setCategorySelection = order..
            for (String row : table) {
                //consoleView.showMessage(row);
                System.out.println(row);
                //cView.showMessage(row);       //Todo flytta utskriften hit...varför i hela fan är cview null?

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void confirmOrderPressed() {
        int order_id = consoleView.getID();
        confirmOrder(order_id);
    }

    private void confirmOrder(int order_id) {
        confirmOrder(order_id, true);
    }

    private void confirmOrder(int order_id, boolean confirmStatus) {
        try {
            dbc.confirmOrder(order_id, confirmStatus);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showCustomerOrdersPressed() {
        int customer_id = consoleView.getID(); // view.getOrderSelected...()
        showOrders(customer_id);
    }

    public void addSupplierPressed() {
        String phone = consoleView.getPhone();
        String street = consoleView.getStreet();
        String city = consoleView.getCity();
        String country = consoleView.getCountry();
        String supplier_name = consoleView.getName();
        try {
            dbc.addSupplier(phone, street, city, country, supplier_name);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Account getLoggedInAccount() {
        return loggedInAccount;
    }

    public void showSuppliersPressed() {
        try {
            String[] suppliers = dbc.getSuppliers();
            for (String row : suppliers) {
                consoleView.showMessage(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addDiscountPressed() {
        String code = consoleView.getEmail();
        String reason = consoleView.getName();
        try {
            dbc.addDiscount(code, reason);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addDiscountedProductPressed() {
        String dicount_code = consoleView.getEmail();
        int product_id = consoleView.getID();
        int percentage = consoleView.getInteger();
        String startdate = consoleView.getDate();
        String enddate = consoleView.getDate();

        try {
            dbc.addDiscountedProduct(dicount_code, product_id, percentage, startdate, enddate);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showDiscountHistoryPressed() {
        try {
            String[] discountHistory = dbc.getDiscountedProductHistory();
            for (String row : discountHistory) {
                consoleView.showMessage(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showTopSellersPressed() {
        try {
            String yearAndMonth = consoleView.getDateTruncMonth();
            String[] topSellers = dbc.getTopSellers(yearAndMonth);

            for (String row : topSellers) {
                consoleView.showMessage(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ------ helpers ------- //
    /*
    private void showCart(String[] cartInfo){
        view.setListData(cartInfo);
    }
     */

    private void showOrders(int customer_id) {
        try {
            String[] table = dbc.getCustomersOrders(customer_id);

            for (String row : table) {
                //consoleView.showMessage(row);
                System.out.println(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showOrderInfo(int order_id) {
        try {
            String[] products = dbc.getOrderedProductsByOrder(order_id);

            int order_cost = 0;
            for (String s : products) {
                String[] productInfo = splitRow(s);
                int product_total_cost = Integer.parseInt(productInfo[11]);
                order_cost += product_total_cost;
            }

            consoleView.showMessage(String.format("Order Total Cost: %d", order_cost));

            //view.setListData(products);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public DBCInterface getDbc() {
        return dbc;
    }


    private void tryLogin(String[] accountData) {
        Account account = new Account();

        System.out.println(Arrays.toString(accountData));

        if (accountData.length > 1) {      //if true, user was found
            try {
                account.setId(Integer.parseInt(accountData[0]));
                account.setEmail(accountData[2]);
                account.setAdminStatus(Boolean.parseBoolean(accountData[7]));

            } catch (Exception e) {
                e.printStackTrace();
            }

            loginAccount(account);
        } else {
            System.out.println("User not found");
        }

    }


    private void loginAccount(Account account) {
        loggedInAccount = account;
        boolean loginStatus = false;
        boolean adminStatus = false;
        if (loggedInAccount != null) {
            loginStatus = true;
            adminStatus = loggedInAccount.isAdmin();
            //cView.showMessage("Login ok for " + loggedInAccount.getEmail());
            System.out.println("Login ok for " + loggedInAccount.getEmail());
        }

        // update views..
        // view.updateUserStatus(loginStatus, adminStatus);

        // update cart / orders / etc.
    }

    private String[] splitRow(String row) {
        return row.split("\\|"); //escape regex magic.. splits on '|'
    }

    public void logOutUser() {
        loggedInAccount = null;
    }
}
