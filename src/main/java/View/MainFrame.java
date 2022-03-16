package View;

import Controller.Controller;

import javax.swing.*;
import java.util.ArrayList;

public class MainFrame extends JFrame {
    private Controller controller;
    private MainPanel mainPanel;

    public MainFrame(int width, int height, Controller controller) {
        super("JMK");
        this.controller = controller;
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setSize(width, height);
        this.mainPanel = new MainPanel(width, height, controller);
        this.setContentPane(mainPanel);
        this.setVisible(true);
    }

    public int getSelectedIndex(){return mainPanel.getcPanel().getSelectedIndex();}

    public void setProductCount(int productCount){mainPanel.getcPanel().setProductCount(productCount);}

    public void setListData(String[] listData){
        DefaultListModel<String> listModel = mainPanel.getcPanel().getListModel();

        listModel.clear();

        for (String menuItem : listData) {
            listModel.addElement(menuItem);
        }

        mainPanel.getcPanel().getMainList().setSelectedIndex(0);
    }

    public void updateUserStatus(boolean loginStatus, boolean adminStatus){
        // show / hide..
    }

    /*
    public void updateListToInventory() {
        mainPanel.getcPanel().updateListToInventory();
    }
    public void updateListToOrders() {
        mainPanel.getcPanel().updateListToOrders();
    }
    public void updateListToProducts() {
        mainPanel.getcPanel().updateListToProducts();
    }
    public void updateListToTopOrders() {
        mainPanel.getcPanel().updateListToTopOrders();
    }
    public void updateListToRabatter() {
        mainPanel.getcPanel().updateListToRabatter();
    }

    public void updateListToCart() {
        mainPanel.getcPanel().updateListToCart();
    }
    public void updateListToHistory() {
        mainPanel.getcPanel().updateListToHistory();
    }
     */
}
