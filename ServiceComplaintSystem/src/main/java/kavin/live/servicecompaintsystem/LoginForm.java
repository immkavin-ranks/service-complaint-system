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




import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import javax.swing.*;

class LoginForm extends JFrame {

  private JLabel usernameLabel, passwordLabel;
  private JTextField usernameField;
  private JPasswordField passwordField;
  private JButton loginButton, registerButton;

  public LoginForm() {
    setTitle("Login");
    setSize(300, 200);
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    setResizable(false); 
   
    usernameLabel = new JLabel("Username or ID:");
    usernameField = new JTextField();

    passwordLabel = new JLabel("Password:");
    passwordField = new JPasswordField();

    loginButton = new JButton("Login");
    registerButton = new JButton("Register");

    Panel panel = new Panel();
    panel.setLayout(new GridLayout(3, 2));

    panel.add(usernameLabel);
    panel.add(usernameField);
    panel.add(passwordLabel);
    panel.add(passwordField);
    panel.add(loginButton);
    panel.add(registerButton);

    add(panel);

    
    setVisible(true);
    setLocationRelativeTo(null);
    
    loginButton.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        String usernameOrId = usernameField.getText();
        String password = new String(passwordField.getPassword());

        // Create a MongoDB client and connect to the database
        try (MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017")) {
            // Select the database
            MongoDatabase database = mongoClient.getDatabase("serviceComplaint");

            // Select the collection
            MongoCollection<Document> collection = database.getCollection("users");

            // Create a query to find the user based on the username or ID
            Document query = new Document("$or",
        Arrays.asList(new Document("username", usernameOrId), new Document("id", usernameOrId)));

            // Find the user document that matches the username or ID
            Document userDocument = collection.find(query).first();

            if (userDocument != null) {
                // Retrieve the stored password
                String storedPassword = userDocument.getString("password");

                if (password.equals(storedPassword)) {
                    // Password matches, perform the login logic here

                    // Open the ServiceComplaintWindow
                    new ServiceComplaintWindow();
                    
                    // Close the LoginForm
                    dispose();
                } else {
                    // Password doesn't match
                    JOptionPane.showMessageDialog(LoginForm.this, "Invalid password");
                }
            } else {
                // User not found
                JOptionPane.showMessageDialog(LoginForm.this, "User not found");
            }
        }
    }
});
    
    registerButton.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        // Open the RegisterForm
        RegisterForm registerForm = new RegisterForm();
        
        // Close the LoginForm
        dispose();
    }
});


  }

  

  public static void main(String[] args) {
    new LoginForm();
  }
}

