/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
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

public class ServiceComplaintSystem extends JFrame {
    private JButton userButton;
    private JButton adminButton;

    public ServiceComplaintSystem() {
        setTitle("Service Complaint System");
        setSize(400, 300);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(new Color(255, 255, 255));

        JLabel titleLabel = new JLabel("Service Complaint System");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(titleLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(2, 1, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        userButton = new JButton("User");
        userButton.setFont(new Font("Arial", Font.PLAIN, 16));
        userButton.setBackground(new Color(51, 153, 255));
        userButton.setForeground(Color.WHITE);
        userButton.setFocusPainted(false);
        buttonPanel.add(userButton);

        adminButton = new JButton("Admin");
        adminButton.setFont(new Font("Arial", Font.PLAIN, 16));
        adminButton.setBackground(new Color(255, 102, 102));
        adminButton.setForeground(Color.WHITE);
        adminButton.setFocusPainted(false);
        buttonPanel.add(adminButton);

        panel.add(buttonPanel, BorderLayout.CENTER);

        add(panel);

        userButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openLoginForm();
            }
        });

        adminButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openControllerLoginForm();
            }
        });

        setVisible(true);
        setLocationRelativeTo(null); // Center the window on the screen
    }

    private void openLoginForm() {
        new LoginForm();
        dispose();
    }

    private void openControllerLoginForm() {
        new ControllerLoginForm();
        dispose();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ServiceComplaintSystem();
        });
    }
}

