package com.form;

import java.awt.BorderLayout;
import javax.swing.*;


import com.panel.BookingPanel;
import com.panel.PaymentPanel;
import com.panel.RoomPanel;
import com.panel.UserPanel;

public class HostelManagementSystem extends JFrame {

    public HostelManagementSystem(String role, int userid) {
        JTabbedPane tabs = new JTabbedPane();
        setTitle("Bookstore Management System");
        setSize(900, 600);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
        

        try {
            if (role.equalsIgnoreCase("admin")) {
                tabs.add("Users", new UserPanel(role, userid));
                tabs.add("Rooms", new RoomPanel());
                tabs.add("Booking Room", new BookingPanel());
                tabs.add("Payment", new PaymentPanel());
            } 
            else if (role.equalsIgnoreCase("staff")) {
                tabs.add("Orders", new BookingPanel());
            } 
            else if (role.equalsIgnoreCase("customer")) {
                tabs.add("My Orders", new BookingPanel());
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error initializing panel: " + ex.getMessage());
        }

        add(tabs, BorderLayout.CENTER);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    // Optional main for testing
    public static void main(String[] args) {
        new HostelManagementSystem("admin", 1); // test with admin role
    }
}
