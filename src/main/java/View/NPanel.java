package View;

import Controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NPanel extends JPanel implements ActionListener {

    private JButton login;
    private JButton reg;

    private JButton inventory;
    private JButton orders;

    private JButton seProdukter;
    private JButton topOrders;
    private JButton rabatter;
    private JButton addCart;
    private JButton seeCart;
    private JButton historik;

    private int width;
    private int height;

    private Controller controller;

    public NPanel(int width, int height, Controller controller){
        this.controller = controller;
        this.setLayout(null);
        this.width = width;
        this.height = height;
        setLocation(0,0);
        this.setSize(width, height);
        setUp();
    }

    public void setUp(){
        login = new JButton("Login");
        login.setVisible(true);
        login.setBounds(1,1,100,60);
        login.setSize(70,35);
        this.add(login);
        login.addActionListener(this);

        reg = new JButton("Admin?");
        reg.setVisible(true);
        reg.setBounds(71,1,100,60);
        reg.setSize(100,35);
        this.add(reg);
        reg.addActionListener(this);

        inventory = new JButton("Varor");
        inventory.setVisible(false);
        inventory.setBounds(171,1,100,60);
        inventory.setSize(80,35);
        this.add(inventory);
        inventory.addActionListener(this);

        orders = new JButton("Beställningar");
        orders.setVisible(false);
        orders.setBounds(251,1,100,60);
        orders.setSize(110,35);
        this.add(orders);
        orders.addActionListener(this);

        seProdukter = new JButton("Produkter");
        seProdukter.setVisible(true);
        seProdukter.setBounds(361,1,100,60);
        seProdukter.setSize(100,35);
        this.add(seProdukter);
        seProdukter.addActionListener(this);

        topOrders = new JButton("Högsta månadsbeställda");
        topOrders.setVisible(true);
        topOrders.setBounds(461,1,100,60);
        topOrders.setSize(190,35);
        this.add(topOrders);
        topOrders.addActionListener(this);

        rabatter = new JButton("Rabatter");
        rabatter.setVisible(true);
        rabatter.setBounds(651,1,100,60);
        rabatter.setSize(90,35);
        this.add(rabatter);
        rabatter.addActionListener(this);

        addCart = new JButton("Lägg till");
        addCart.setVisible(true);
        addCart.setBounds(741,1,100,60);
        addCart.setSize(80,35);
        this.add(addCart);
        addCart.addActionListener(this);

        seeCart = new JButton("Se korg");
        seeCart.setVisible(true);
        seeCart.setBounds(821,1,100,60);
        seeCart.setSize(80,35);
        this.add(seeCart);
        seeCart.addActionListener(this);

        historik = new JButton("Historik");
        historik.setVisible(true);
        historik.setBounds(901,1,100,60);
        historik.setSize(90,35);
        this.add(historik);
        historik.addActionListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==login){
            controller.loginWindow();
        }
        else if (e.getSource()==reg){
            inventory.setVisible(true);
            orders.setVisible(true);
        }
        /*
        else if (e.getSource()==inventory){
            controller.showCartPressed();
        }
         */
        /*
        else if (e.getSource()==orders){
            controller.showMyOrdersPressed();
        }
        */
        else if (e.getSource()==seProdukter){
            controller.showProductsPressed();
        }
        else if (e.getSource()==topOrders){
            controller.showTopSellersPressed();
        }
        else if (e.getSource()==rabatter){
            controller.showDiscountHistoryPressed();
        }
        /*else if (e.getSource()==addCart){
            controller.addToCartPressed();
        }*/
        else if (e.getSource()==seeCart){
            controller.showCartPressed();
        }
        else if (e.getSource()==historik){
            controller.showMyOrdersPressed();
        }
    }


}
