package View;

import Controller.Controller;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginWindow extends JFrame implements ActionListener {

    private JPanel panel;
    private Controller controller;
    private JLabel emailLabel;

    JButton loginButton;
    JButton registerButton;

    public LoginWindow(Controller controller) {
        panel = new JPanel();
        setSize(300, 200);
        this.setContentPane(panel);
        this.setTitle("Login");
        this.setVisible(true);
        this.setResizable(false);
        this.controller = controller;

        panel.setLayout(null);
        setUp();
    }

    public void setUp() {
        emailLabel = new JLabel("Email");
        emailLabel.setVisible(true);
        emailLabel.setBounds(10, 20, 80, 25);
        panel.add(emailLabel);

        JTextField userText = new JTextField();
        userText.setVisible(true);
        userText.setBounds(100, 20, 165, 25);
        panel.add(userText);

        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setVisible(true);
        passwordLabel.setBounds(10, 50, 80, 25);
        panel.add(passwordLabel);

        JPasswordField passwordText = new JPasswordField();
        passwordText.setVisible(true);
        passwordText.setBounds(100, 50, 165, 25);
        panel.add(passwordText);

        loginButton = new JButton("Logga in");
        loginButton.setVisible(true);
        loginButton.setBounds(30, 100, 90, 25);
        panel.add(loginButton);
        loginButton.addActionListener(this);

        registerButton = new JButton("Registrera");
        registerButton.setVisible(true);
        registerButton.setBounds(140, 100, 110, 25);
        panel.add(registerButton);
        registerButton.addActionListener(this);
    }

    public String getEmail(){
        return emailLabel.getText();
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton){}
        else if (e.getSource() == registerButton) {
            controller.registerWindow();
        }
    }


}
