package View;

import Controller.Controller;

import javax.swing.*;

public class CPanel extends JPanel{
    private JList<String> mainList;

    private DefaultListModel<String> listModel;

    private JList<Object> secondList;
    private JLabel productCountText;
    private JLabel totalCostText;
    private JButton reset;
    private JButton checkout;

    private int width;
    private int height;

    private Controller controller;

    public CPanel(int width, int height, Controller controller) {
        this.controller = controller;
        this.setLayout(null);
        this.width = width;
        this.height = height;
        this.setSize(width, height);
        setLocation(0, 0);

        setUp();
    }

    public void setUp(){
        listModel = new DefaultListModel<>();
        mainList = new JList<>(listModel);
        //mainList.setBounds(10,45,721,400);
        //this.add(MainList); //wrap in scrollPane

        JScrollPane scrollPane = new JScrollPane(mainList);
        scrollPane.setBounds(10,45,721,400);
        this.add(scrollPane);

        productCountText = new JLabel("");
        productCountText.setVisible(true);
        productCountText.setBounds(742,40,100,40);
        this.add(productCountText);

        secondList = new JList<>();
        secondList.setBounds(742,85,240,285);
        this.add(secondList);

        totalCostText = new JLabel("TOT COST GOES HERE");
        totalCostText.setVisible(false);
        totalCostText.setBounds(770, 371, 160, 35);
        this.add(totalCostText);

        checkout = new JButton("Checkout");
        checkout.setVisible(false);
        checkout.setBounds(755,410,105,35);
        this.add(checkout);

        reset = new JButton("Reset");
        reset.setVisible(false);
        reset.setBounds(879,410,80,35);
        this.add(reset);
        //checkout.addActionListener(this);
    }


    public void setProductCount(int productCount){this.productCountText.setText("Products: " + productCount);}
    //public void setTotalCostText(double totalCost){this.totalCostText.setText("TOT: " + totalCost + ":-");}

    public int getSelectedIndex(){return mainList.getSelectedIndex();}

    public DefaultListModel<String> getListModel() {
        return listModel;
    }

    /*
    public void updateListToInventory(){MainList.setListData(inventoryExamples);}
    public void updateListToOrders(){
        MainList.setListData(orderExamples);
    }
    public void updateListToProducts(){
        MainList.setListData(produktExamples);
    }
    public void updateListToTopOrders(){
        MainList.setListData(topOrdersExamples);
    }
    public void updateListToRabatter(){
        MainList.setListData(rabattExamples);
    }

    public void updateListToCart(){
        totalCostText.setVisible(true);
        reset.setVisible(true);
        checkout.setVisible(true);
        secondList.setListData(cartExamples);
    }
    public void updateListToHistory(){
        secondList.setListData(historyExamples);
    }
     */

    public JList<String> getMainList() {
        return mainList;
    }
    public void setMainList(JList<String> mainList) {
        this.mainList = mainList;
    }

    public JList<Object> getSecondList() {
        return secondList;
    }
    public void setSecondList(JList<Object> secondList) {
        this.secondList = secondList;
    }

    public JLabel getProductCountText() {
        return productCountText;
    }
    public void setProductCountText(JLabel productCountText) {
        this.productCountText = productCountText;
    }

    public JLabel getTotalCostText() {
        return totalCostText;
    }
    public void setTotalCostText(JLabel totalCostText) {
        this.totalCostText = totalCostText;
    }
}
