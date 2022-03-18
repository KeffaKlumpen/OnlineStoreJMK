package View;

import Controller.Controller;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterWindow extends JFrame implements ActionListener {

    private JPanel panel;
    private Controller controller;

    private JTextField emailTextField;
    JButton loginButton;
    JButton registerButton;

    public RegisterWindow(Controller controller) {
        panel = new JPanel();
        setSize(300, 400);
        this.setContentPane(panel);
        this.setTitle("Register");
        this.setVisible(true);
        this.setResizable(false);
        this.controller = controller;

        panel.setLayout(null);
        setUp();
    }

    public void setUp() {
        JLabel emailLabel = new JLabel("Email");
        emailLabel.setVisible(true);
        emailLabel.setBounds(10, 20, 80, 25);
        panel.add(emailLabel);

        emailTextField = new JTextField();
        emailTextField.setVisible(true);
        emailTextField.setBounds(100, 20, 165, 25);
        panel.add(emailTextField);

        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setVisible(true);
        passwordLabel.setBounds(10, 50, 80, 25);
        panel.add(passwordLabel);

        JPasswordField passwordText = new JPasswordField();
        passwordText.setVisible(true);
        passwordText.setBounds(100, 50, 165, 25);
        panel.add(passwordText);

        JLabel firstName = new JLabel("First name");
        firstName.setVisible(true);
        firstName.setBounds(10, 80, 80, 25);
        panel.add(firstName);

        JTextField firstNameT = new JTextField();
        firstNameT.setVisible(true);
        firstNameT.setBounds(100, 80, 165, 25);
        panel.add(firstNameT);

        JLabel lastName = new JLabel("Last name");
        lastName.setVisible(true);
        lastName.setBounds(10, 110, 80, 25);
        panel.add(lastName);

        JTextField lastNameT = new JTextField();
        lastNameT.setVisible(true);
        lastNameT.setBounds(100, 110, 165, 25);
        panel.add(lastNameT);

        JLabel email = new JLabel("Email");
        email.setVisible(true);
        email.setBounds(10, 140, 80, 25);
        panel.add(email);

        JTextField emailT = new JTextField();
        emailT.setVisible(true);
        emailT.setBounds(100, 140, 165, 25);
        panel.add(emailT);

        JLabel address = new JLabel("Address");
        address.setVisible(true);
        address.setBounds(10, 170, 80, 25);
        panel.add(address);

        JTextField addressT = new JTextField();
        addressT.setVisible(true);
        addressT.setBounds(100, 170, 165, 25);
        panel.add(addressT);

        JLabel city = new JLabel("City");
        city.setVisible(true);
        city.setBounds(10, 200, 80, 25);
        panel.add(city);

        JTextField cityT = new JTextField();
        cityT.setVisible(true);
        cityT.setBounds(100, 200, 165, 25);
        panel.add(cityT);

        JLabel country = new JLabel("Country");
        country.setVisible(true);
        country.setBounds(10, 230, 80, 25);
        panel.add(country);

        JTextField countryT = new JTextField();
        countryT.setVisible(true);
        countryT.setBounds(100, 230, 165, 25);
        panel.add(countryT);

        JLabel phonenumber = new JLabel("Phone number");
        phonenumber.setVisible(true);
        phonenumber.setBounds(10, 260, 100, 25);
        panel.add(phonenumber);

        JTextField phonenumberT = new JTextField();
        phonenumberT.setVisible(true);
        phonenumberT.setBounds(100, 260, 165, 25);
        panel.add(phonenumberT);

        registerButton = new JButton("Registrera som kund");
        registerButton.setVisible(true);
        registerButton.setBounds(30, 310, 170, 25);
        panel.add(registerButton);
        registerButton.addActionListener(this);
    }

    public String getEmail() {
        return emailTextField.getText();
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            //controller.loginPressed();
        } else if (e.getSource() == registerButton) {
            controller.registerAccountPressed();
        }
    }


}