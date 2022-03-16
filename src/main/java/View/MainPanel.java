package View;

import Controller.Controller;

import javax.swing.*;

public class MainPanel extends JPanel {
    private NPanel nPanel;
    private CPanel cPanel;

    public MainPanel(int width, int height, Controller controller) {
        this.setLayout(null);
        this.setSize(width, height);

        nPanel = new NPanel(width, height, controller);
        add(nPanel);

        cPanel = new CPanel(width,height,controller);
        add(cPanel);
    }

    public CPanel getcPanel(){
        return cPanel;
    }

}