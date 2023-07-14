/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kavin.live.servicecompaintsystem;

/**
 *
 * @author SATHYA COMPUTERS
 */
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;



import javax.swing.*;
import java.awt.*;

public class RegisterForm extends JFrame {
    private JLabel usernameLabel, emailLabel, passwordLabel;
    private JTextField usernameField, emailField;
    private JPasswordField passwordField;
    private JButton registerButton, loginButton;

    public RegisterForm() {
        setTitle("Register");
        setSize(300, 200);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        usernameLabel = new JLabel("Username:");
        usernameField = new JTextField();

        emailLabel = new JLabel("Email:");
        emailField = new JTextField();

        passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField();

        registerButton = new JButton("Register");
        loginButton = new JButton("Login");
        
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2));

        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(emailLabel);
        panel.add(emailField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(loginButton); 
        panel.add(registerButton);

        add(panel);
        
        registerButton.addActionListener(e -> {
    String username = usernameField.getText();
    String email = emailField.getText();
    String password = new String(passwordField.getPassword());

    // Check if any field is empty
    if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Please fill in all the fields");
        return; // Stop the registration process
    }

    String generatedId = UniqueIdGenerator.generateUniqueId();

    // Create a MongoDB client and connect to the database
    try (MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017")) {
        // Select the database
        MongoDatabase database = mongoClient.getDatabase("serviceComplaint");

        // Select the collection
        MongoCollection<Document> collection = database.getCollection("users");

        // Create a document to store the user data
        Document document = new Document();
        document.append("id", generatedId);
        document.append("username", username);
        document.append("email", email);
        document.append("password", password);

        // Insert the document into the collection
        collection.insertOne(document);

        showIdDialog(generatedId);
    }

    // Close the RegisterForm
    dispose();

    // Open the LoginForm
    new LoginForm();
});

        
        loginButton.addActionListener(e -> {
    // Open the LoginForm
    LoginForm loginForm = new LoginForm();
    
    // Close the RegisterForm
    dispose();
});


        setVisible(true);
        setLocationRelativeTo(null);
    }
    
    private void showIdDialog(String id) {
        JOptionPane.showMessageDialog(this, "Registration successful! Your ID is: " + id);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new RegisterForm();
        });
    }
}
