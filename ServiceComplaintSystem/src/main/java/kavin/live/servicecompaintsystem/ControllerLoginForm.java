/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kavin.live.servicecompaintsystem;

/**
 *
 * @author SATHYA COMPUTERS
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControllerLoginForm extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;

    public ControllerLoginForm() {
        setTitle("Login");
        setSize(300, 200);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        JLabel usernameLabel = new JLabel("Username:");
        JLabel passwordLabel = new JLabel("Password:");
        usernameField = new JTextField();
        passwordField = new JPasswordField();
        loginButton = new JButton("Login");

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));
        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(new JLabel()); // Empty label for spacing
        panel.add(loginButton);

        add(panel);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                // Check if username and password match the credentials
                if (username.equals("admin") && password.equals("admin")) {
                    // Open the Service Panel Window
                    openServicePanelWindow();
                } else {
                    JOptionPane.showMessageDialog(ControllerLoginForm.this, "Invalid username or password");
                }
            }
        });

        setVisible(true);
        setLocationRelativeTo(null);
    }

    private void openServicePanelWindow() {
        ServicePanelWindow servicePanelWindow = new ServicePanelWindow();
        dispose(); // Close the Login Form
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ControllerLoginForm();
        });
    }
}
