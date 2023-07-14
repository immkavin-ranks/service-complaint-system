/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kavin.live.servicecompaintsystem;

/**
 *
 * @author SATHYA COMPUTERS
 */
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ServicePanelWindow extends JFrame {
    private JTable dataTable;

    public ServicePanelWindow() {
        setTitle("Service Panel Window");
        setSize(600, 400);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        dataTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(dataTable);

        add(scrollPane);

        // Connect to MongoDB and retrieve data from the complaints collection
        try (MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017")) {
            MongoDatabase database = mongoClient.getDatabase("serviceComplaint");
            MongoCollection<Document> collection = database.getCollection("complaints");

            FindIterable<Document> iterable = collection.find();
            DefaultTableModel model = new DefaultTableModel();
            //model.addColumn("Complaint ID");
            model.addColumn("Service");
            model.addColumn("Area");
            model.addColumn("PIN Code");
            model.addColumn("Location");

            for (Document document : iterable) {
                //String complaintId = document.getString("complaintId");
                String service = document.getString("service");
                String area = document.getString("area");
                String pinCode = document.getString("pinCode");
                String location = document.getString("location");
                                            //complaintId ,
                model.addRow(new Object[]{service, area, pinCode, location});
            }

            dataTable.setModel(model);
        }

        setVisible(true);
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ServicePanelWindow();
        });
    }
}
