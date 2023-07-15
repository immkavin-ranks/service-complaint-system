/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kavin.live.servicecompaintsystem;

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import com.mongodb.client.gridfs.model.GridFSUploadOptions;
import org.bson.Document;


import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import org.apache.commons.io.IOUtils;


import java.util.Base64;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.bson.BsonObjectId;
import org.bson.types.ObjectId;


public class ServiceComplaintWindow extends JFrame {
    private JComboBox<String> serviceComboBox;
    private JButton fileComplaintButton;
    
    private JFrame waterComplaintWindow;
    private JFrame electricityComplaintWindow;
    private JFrame homeMaintenanceComplaintWindow;

    private JLabel imageLabel;
    private JLabel selectedImageLabel;
    private JTextField areaField;
    private JTextField pinField;
    private JTextField locationField;
    private JButton browseButton;
    private JButton submitButton;
    
    
    public ServiceComplaintWindow() {
        setTitle("Service Complaint");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        serviceComboBox = new JComboBox<>(new String[]{"Water Services", "Electricity Services", "Home Maintenance Services"});
        fileComplaintButton = new JButton("File Complaint");

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));

        panel.add(new JLabel("Select Service:"));
        panel.add(serviceComboBox);
        panel.add(new JLabel()); // Empty label for spacing
        panel.add(fileComplaintButton);

        add(panel);

        fileComplaintButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedService = (String) serviceComboBox.getSelectedItem();
                switch (selectedService) {
                    case "Water Services":
                        createWaterComplaintWindow();
                        break;
                    case "Electricity Services":
                        createElectricityComplaintWindow();
                        break;
                    case "Home Maintenance Services":
                        createHomeMaintenanceComplaintWindow();
                        break;
                }
            }
        });

        setVisible(true);
        setLocationRelativeTo(null);
    }

    private void createWaterComplaintWindow() {
        dispose(); // Close the current window

        waterComplaintWindow = new JFrame("Water Services Complaint");
        waterComplaintWindow.setSize(400, 250);
        waterComplaintWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 2));

        imageLabel = new JLabel("Upload Image:");
        selectedImageLabel = new JLabel("No image selected");
        areaField = new JTextField();
        pinField = new JTextField();
        locationField = new JTextField();
        browseButton = new JButton("Browse");
        submitButton = new JButton("Submit");

        panel.add(imageLabel);
        panel.add(selectedImageLabel);
        panel.add(new JLabel("Area:"));
        panel.add(areaField);
        panel.add(new JLabel("PIN Code:"));
        panel.add(pinField);
        panel.add(new JLabel("Location:"));
        panel.add(locationField);
        panel.add(new JLabel()); // Empty label for spacing
        panel.add(browseButton);
        panel.add(new JLabel()); // Empty label for spacing
        panel.add(submitButton);

        waterComplaintWindow.getContentPane().add(panel);
        waterComplaintWindow.setVisible(true);
        waterComplaintWindow.setLocationRelativeTo(null);

        browseButton.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Images", "jpg", "jpeg", "png"));
        int result = fileChooser.showOpenDialog(waterComplaintWindow);
        if (result == JFileChooser.APPROVE_OPTION) {
            selectedFile = fileChooser.getSelectedFile();
            selectedImageLabel.setText(selectedFile.getName());

            // Read the selected file and display it
            try {
                FileInputStream fileInputStream = new FileInputStream(selectedFile);
                Image image = ImageIO.read(fileInputStream);
                // Adjust the size of the imageLabel according to your requirements
                image = image.getScaledInstance(150, 150, Image.SCALE_SMOOTH);
                imageLabel.setIcon(new ImageIcon(image));
                fileInputStream.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
});

submitButton.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        // Get the complaint details from the input fields
        String area = areaField.getText();
        String pinCode = pinField.getText();
        String location = locationField.getText();
        String selectedImageName = selectedImageLabel.getText();
        
        // Use the instance variable selectedFile here
        if (selectedFile != null) {
            // Perform complaint submission logic for home maintenance services
            // Read the selected file and store its contents as base64 string
            String base64Image = null;
            try {
                FileInputStream fileInputStream = new FileInputStream(selectedFile);
                byte[] imageBytes = IOUtils.toByteArray(fileInputStream);
                base64Image = Base64.getEncoder().encodeToString(imageBytes);
                fileInputStream.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            
            // Create a MongoDB client and connect to the database
            try (MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017")) {
                // Select the database
                MongoDatabase database = mongoClient.getDatabase("serviceComplaint");

                // Select the collection
                MongoCollection<Document> collection = database.getCollection("complaints");

                // Create a document to store the complaint data
                Document document = new Document();
                document.append("service", "Water Services");
                document.append("area", area);
                document.append("pinCode", pinCode);
                document.append("location", location);
                document.append("image", base64Image);

                // Insert the document into the collection
                collection.insertOne(document);

                // Show a confirmation dialog
                JOptionPane.showMessageDialog(waterComplaintWindow, "Water Services Complaint Submitted");

                // Close the home maintenance complaint window
                waterComplaintWindow.dispose();
            }
        }
    }
});
    }
    
    private void createElectricityComplaintWindow() {
        dispose(); // Close the current window

        electricityComplaintWindow = new JFrame("Electricity Services Complaint");
        electricityComplaintWindow.setSize(400, 250);
        electricityComplaintWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 2));

        imageLabel = new JLabel("Upload Image:");
        selectedImageLabel = new JLabel("No image selected");
        areaField = new JTextField();
        pinField = new JTextField();
        locationField = new JTextField();
        browseButton = new JButton("Browse");
        submitButton = new JButton("Submit");

        panel.add(imageLabel);
        panel.add(selectedImageLabel);
        panel.add(new JLabel("Area:"));
        panel.add(areaField);
        panel.add(new JLabel("PIN Code:"));
        panel.add(pinField);
        panel.add(new JLabel("Location:"));
        panel.add(locationField);
        panel.add(new JLabel()); // Empty label for spacing
        panel.add(browseButton);
        panel.add(new JLabel()); // Empty label for spacing
        panel.add(submitButton);

        electricityComplaintWindow.getContentPane().add(panel);
        electricityComplaintWindow.setVisible(true);
        electricityComplaintWindow.setLocationRelativeTo(null);

        browseButton.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Images", "jpg", "jpeg", "png"));
        int result = fileChooser.showOpenDialog(electricityComplaintWindow);
        if (result == JFileChooser.APPROVE_OPTION) {
            selectedFile = fileChooser.getSelectedFile();
            selectedImageLabel.setText(selectedFile.getName());

            // Read the selected file and display it
            try {
                FileInputStream fileInputStream = new FileInputStream(selectedFile);
                Image image = ImageIO.read(fileInputStream);
                // Adjust the size of the imageLabel according to your requirements
                image = image.getScaledInstance(150, 150, Image.SCALE_SMOOTH);
                imageLabel.setIcon(new ImageIcon(image));
                fileInputStream.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
});

submitButton.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        // Get the complaint details from the input fields
        String area = areaField.getText();
        String pinCode = pinField.getText();
        String location = locationField.getText();
        String selectedImageName = selectedImageLabel.getText();
        
        // Use the instance variable selectedFile here
        if (selectedFile != null) {
            // Perform complaint submission logic for home maintenance services
            // Read the selected file and store its contents as base64 string
            String base64Image = null;
            try {
                FileInputStream fileInputStream = new FileInputStream(selectedFile);
                byte[] imageBytes = IOUtils.toByteArray(fileInputStream);
                base64Image = Base64.getEncoder().encodeToString(imageBytes);
                fileInputStream.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            
            // Create a MongoDB client and connect to the database
            try (MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017")) {
                // Select the database
                MongoDatabase database = mongoClient.getDatabase("serviceComplaint");

                // Select the collection
                MongoCollection<Document> collection = database.getCollection("complaints");

                // Create a document to store the complaint data
                Document document = new Document();
                document.append("service", "Electricity Services");
                document.append("area", area);
                document.append("pinCode", pinCode);
                document.append("location", location);
                document.append("image", base64Image);

                // Insert the document into the collection
                collection.insertOne(document);

                // Show a confirmation dialog
                JOptionPane.showMessageDialog(electricityComplaintWindow, "Electricity Services Complaint Submitted");

                // Close the home maintenance complaint window
                electricityComplaintWindow.dispose();
            }
        }
    }
});
    }
    private File selectedFile;
    private void createHomeMaintenanceComplaintWindow() {
        dispose(); // Close the current window

        homeMaintenanceComplaintWindow = new JFrame("Home Maintenance Services Complaint");
        homeMaintenanceComplaintWindow.setSize(400, 250);
        homeMaintenanceComplaintWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 2));

        imageLabel = new JLabel("Upload Image:");
        selectedImageLabel = new JLabel("No image selected");
        areaField = new JTextField();
        pinField = new JTextField();
        locationField = new JTextField();
        browseButton = new JButton("Browse");
        submitButton = new JButton("Submit");

        panel.add(imageLabel);
        panel.add(selectedImageLabel);
        panel.add(new JLabel("Area:"));
        panel.add(areaField);
        panel.add(new JLabel("PIN Code:"));
        panel.add(pinField);
        panel.add(new JLabel("Location:"));
        panel.add(locationField);
        panel.add(new JLabel()); // Empty label for spacing
        panel.add(browseButton);
        panel.add(new JLabel()); // Empty label for spacing
        panel.add(submitButton);

        homeMaintenanceComplaintWindow.getContentPane().add(panel);
        homeMaintenanceComplaintWindow.setVisible(true);
        homeMaintenanceComplaintWindow.setLocationRelativeTo(null);
        

        browseButton.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Images", "jpg", "jpeg", "png"));
        int result = fileChooser.showOpenDialog(homeMaintenanceComplaintWindow);
        if (result == JFileChooser.APPROVE_OPTION) {
            selectedFile = fileChooser.getSelectedFile();
            selectedImageLabel.setText(selectedFile.getName());

            // Read the selected file and display it
            try {
                FileInputStream fileInputStream = new FileInputStream(selectedFile);
                Image image = ImageIO.read(fileInputStream);
                // Adjust the size of the imageLabel according to your requirements
                image = image.getScaledInstance(150, 150, Image.SCALE_SMOOTH);
                imageLabel.setIcon(new ImageIcon(image));
                fileInputStream.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
});

submitButton.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        // Get the complaint details from the input fields
        String area = areaField.getText();
        String pinCode = pinField.getText();
        String location = locationField.getText();
        String selectedImageName = selectedImageLabel.getText();
        
        // Use the instance variable selectedFile here
        if (selectedFile != null) {
            // Perform complaint submission logic for home maintenance services
            // Read the selected file and store its contents as base64 string
            String base64Image = null;
            try {
                FileInputStream fileInputStream = new FileInputStream(selectedFile);
                byte[] imageBytes = IOUtils.toByteArray(fileInputStream);
                base64Image = Base64.getEncoder().encodeToString(imageBytes);
                fileInputStream.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            
            // Create a MongoDB client and connect to the database
            try (MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017")) {
                // Select the database
                MongoDatabase database = mongoClient.getDatabase("serviceComplaint");

                // Select the collection
                MongoCollection<Document> collection = database.getCollection("complaints");

                // Create a document to store the complaint data
                Document document = new Document();
                document.append("service", "Home Maintenance Services");
                document.append("area", area);
                document.append("pinCode", pinCode);
                document.append("location", location);
                document.append("image", base64Image);

                // Insert the document into the collection
                collection.insertOne(document);

                // Show a confirmation dialog
                JOptionPane.showMessageDialog(homeMaintenanceComplaintWindow, "Home Maintenance Services Complaint Submitted");
                
                // Close the home maintenance complaint window
                homeMaintenanceComplaintWindow.dispose();
            }
        }
    }
});
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ServiceComplaintWindow();
        });
    }
}
