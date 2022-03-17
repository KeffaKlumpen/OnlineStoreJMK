package View;

import Controller.Controller;

import java.util.Arrays;
import java.util.Scanner;


public class ConsoleView {
    private Controller controller;
    private DBCInterface db;
    public ConsoleView(Controller controller, DBCInterface db) throws Exception {
        this.controller = controller;
        this.db = db;
        mainMenu();
    }

    public int readInt(){
        Scanner scanner = new Scanner(System.in);
        int intOut = scanner.nextInt();
        scanner.nextLine();
        return intOut;
    }
    public String readString(){
        Scanner scanner = new Scanner(System.in);
        String strOut = scanner.nextLine();
        return strOut;
    }

    public void mainMenu() throws Exception {
        System.out.println("What do u want to do?");
        System.out.println("1: Log in");
        System.out.println("2: Continue as guest");
        System.out.println("3: Register account");
        System.out.println("4: debug");
        System.out.println("0: Quit");
        int choice = -1;
        while(choice != 0){
            //choice = readInt();
            choice = 4;
            switch (choice){
                case 1:
                    controller.loginPressed();
                    System.out.println("Current account: " + controller.getLoggedInAccount());
                    if (controller.getLoggedInAccount() != null) {
                        if (controller.getLoggedInAccount().isAdmin()){
                            adminMenu();
                        }else{
                            customerMenu();
                        }
                    } else {
                        System.out.println("User not found..");
                    }
                    break;
                case 2:
                    guestMenu();
                    break;
                case 3:
                    register();
                    break;
                case 4:
                    //Used for debugging currently
                    adminMenu();
                    //customerMenu();
                    //mainMenu();
                    break;
                default:
                    System.out.println("Invalid choice, try again");
                    break;
            }
        }
    }

    public void register() throws Exception {
        System.out.println("Enter name:");
        String name = readString();
        System.out.println("Enter email");
        String email = readString();
        System.out.println("Enter street");
        String street = readString();
        System.out.println("Enter city");
        String city = readString();
        System.out.println("Enter country");
        String country = readString();
        System.out.println("Enter phone number");
        String phoneNbr = readString();
        System.out.println("***************************");



        System.out.println("Press 1 to register as customer, 2 to register as admin");
        int choice = readInt();
        boolean isAdmin = (choice == 2);

        db.registerAccount(name, email, street, city, country, phoneNbr, isAdmin);
    }
    public void adminMenu() throws Exception {
        System.out.println("Do stuff as admin!\n");

        System.out.println("What do u want to do?");
        System.out.println("0: Log out");
        System.out.println("1: Set quantity                             (Klar!)");
        System.out.println("2: Get new orders                           (Klar!)");
        System.out.println("3: Confirm order                            (Klar!)");
        System.out.println("4: Remove unconfirmed order                 (Klar!)");
        System.out.println("5: Get account by e-mail                    (Klar!)");
        System.out.println("6: Update quantity of ordered product       (Ej klar)");             //todo
        System.out.println("7: View discounted products");        //Currently discounted?        //todo
        System.out.println("8: Add a new product to the store           (Klar!)");
        System.out.println("9: Delete an item that has yet to be sold");                         //todo

        int choice = readInt();

        switch (choice){
            case 0:
                controller.logOutUser();
                System.out.println("Logged out!");
                mainMenu();
                break;
            case 1:
                //Change quantity of product chosen by ID
                System.out.println("What product ID to change?");
                int inputID = readInt();
                System.out.println("Enter new quantity");
                int quantity = readInt();
                db.setQuantity(inputID, quantity);
                break;
            case 2:
                controller.showUnconfirmedOrdersPressed();
                break;
            case 3:
                //Print orders and enter ID to confirm
                System.out.println(Arrays.toString(db.getUnconfirmedOrders()));
                db.getUnconfirmedOrders();
                System.out.println("Enter ID of order to confirm");
                int orderNr = readInt();
                db.confirmOrder(orderNr, true);
                break;
            case 4:
                //Print all orders, then remove order by ID
                System.out.println(Arrays.toString(db.getUnconfirmedOrders()));
                int orderToRemove = readInt();
                db.removeOrder(orderToRemove);
                break;
            case 5:
                //Get account by email
                //Behövs getAccount(int account_id) användas?
                System.out.println("Enter email to search for: ");
                String targetMail = readString();
                System.out.println(db.getAccount(targetMail));
                break;
            case 6:     //TODO Testa mer. Funkar denna verkligen?
                //TODO tf_update_product_quantity() kastar Exception. Fix or delete trigger?
                //Se gärna över denna. Jag är liiite osäker på exakt vad dess syfte är //måns
                //Update quantity of ordered products
                System.out.println("Enter product ID:");
                int chosenID = readInt();
                System.out.println("Enter new quantity");
                int newQuantity = readInt();
                System.out.println("Enter order ID");
                int orderID = readInt();
                controller.updateOrderQuantityPressed(chosenID, newQuantity, orderID);
            case 7:
                //discount
                controller.showCurrentlyDiscountedProductsPressed();
            case 8:
                //add a new item to the store
                controller.addProductPressed();
                break;
            case 9:
                //remove a product that hasn't been sold yet
                controller.removeProductPressed();
                break;
            case 10:
                //placeOrder(), Behövs denna metoden? Se kommentar i DBCInterface
                break;

            default:
                break;
        }
    }

    public void printDiscountMenu(){
        System.out.println("1: Add discount (With code and reason");
        System.out.println("2: todo");
    }
    public void guestMenu(){
        System.out.println("What do u want to do");
        System.out.println("1: View top sellers by year and month");
        System.out.println("2: ");
    }

    public void customerMenu() throws Exception {
        int choice = -1;
        System.out.println("Do stuff as a customer\n");

        System.out.println("What do u want to do?");
        System.out.println("0: Log out                              (Klar!");
        System.out.println("1: View all available products          (Typ klar?)");
        System.out.println("2: View all discounted products");
        System.out.println("3: Search products by ID                (Klar!)");
        System.out.println("4: View cart");
        System.out.println("5: Select item to add to cart");        //TODO
        System.out.println("6: Finish shopping");
        System.out.println("8: Admin menu");

        choice = readInt();

        while (choice != -1) {
            switch (choice){
                case 0:
                    controller.logOutUser();
                    System.out.println("Logged out!");
                    mainMenu();
                    break;
                case 1:
                    System.out.println("Trying to get available products...");
                    System.out.println(Arrays.toString(controller.showAllSelected("", "", "", Float.MIN_VALUE, Float.MAX_VALUE)));
                    //db.getAvailableProducts("", "", "", Integer.MIN_VALUE, Integer.MAX_VALUE);    //Denna funkar ej, "function now found" - Troligtvis pga fel datatyp som parameter (unknown säger java)
                    //System.out.println(Arrays.toString(db.getAllAvailableProducts()));      //Denna funkar men känns fel
                    break;
                case 2:     //TODO
                    db.getCurrentlyDiscountedProducts("a", "a", "a" , Integer.MIN_VALUE, Integer.MAX_VALUE);
                    break;
                case 3:
                    System.out.println("Enter ID to search for");
                    int inputID = readInt();
                    System.out.print(db.getProductByID(inputID));
                    break;
                case 4:     //print cart
                    if (controller.getCart() != null){
                        controller.getCart().getCartStrings();
                    }else{
                        System.out.println("Cart is empty!");
                    }
                    break;
                case 5:     //Add to cart
                    //print cart
                    //cart.add(id, quantity)
                    System.out.println(Arrays.toString(controller.showAllSelected("", "", "", Float.MIN_VALUE, Float.MAX_VALUE)));
                    System.out.println("Enter ID of product to add");
                    int idToAdd = readInt();
                    System.out.println("Enter desired quantity");
                    int chosenQuantity = readInt();
                    controller.addToCartPressed(idToAdd, chosenQuantity);
                    //controller.getCart().addToCart(idToAdd, chosenQuantity);
                    //System.out.println(Arrays.toString(controller.getCart().getCartStrings()));
                case 6:
                    //TODO check out
                    break;
                case 8:
                    adminMenu();
                    break;
            }
            choice = readInt();
        }

    }

    public void showMessage(String s) {
        System.out.println(s);
    }
}