package com.panel;

import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import com.util.DB;

public class PaymentPanel extends JPanel implements ActionListener {

    JTextField txtPaymentID = new JTextField();   // Auto Increment, Locked
    JTextField txtReferenceNo = new JTextField();
    JTextField txtAmount = new JTextField();
    JTextField txtDate = new JTextField(); // Format: yyyy-MM-dd
    JComboBox<String> cmbMethod = new JComboBox<>(new String[]{"Cash","Card","Online","MobileMoney"});
    JComboBox<String> cmbStatus = new JComboBox<>(new String[]{"Pending","Paid","Failed"});
    JTextField txtBookingID = new JTextField(); // Foreign key

    JButton addBtn = new JButton("Add"),
            updateBtn = new JButton("Update"),
            deleteBtn = new JButton("Delete"),
            loadBtn = new JButton("Load");

    JTable table;
    DefaultTableModel model;

    public PaymentPanel() {
        setLayout(null);

        // Lock the PaymentID field
        txtPaymentID.setEditable(false);

        String[] labels = {"PaymentID", "ReferenceNo", "Amount", "Date", "Method", "Status", "BookingID"};
        model = new DefaultTableModel(labels, 0);
        table = new JTable(model);
        JScrollPane sp = new JScrollPane(table);
        sp.setBounds(20, 250, 800, 300);
        add(sp);

        int y = 20;
        addField("PaymentID", txtPaymentID, y); y += 30;
        addField("ReferenceNo", txtReferenceNo, y); y += 30;
        addField("Amount", txtAmount, y); y += 30;
        addField("Date (yyyy-MM-dd)", txtDate, y); y += 30;
        addField("Method", cmbMethod, y); y += 30;
        addField("Status", cmbStatus, y); y += 30;
        addField("BookingID", txtBookingID, y); y += 30;

        addButton();

        addBtn.addActionListener(this);
        updateBtn.addActionListener(this);
        deleteBtn.addActionListener(this);
        loadBtn.addActionListener(this);

        // Table row click -> load data into fields
        table.getSelectionModel().addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            @Override
            public void valueChanged(javax.swing.event.ListSelectionEvent event) {
                if (!event.getValueIsAdjusting() && table.getSelectedRow() != -1) {
                    int row = table.getSelectedRow();
                    txtPaymentID.setText(model.getValueAt(row, 0).toString());
                    txtReferenceNo.setText(model.getValueAt(row, 1).toString());
                    txtAmount.setText(model.getValueAt(row, 2).toString());
                    txtDate.setText(model.getValueAt(row, 3).toString());
                    cmbMethod.setSelectedItem(model.getValueAt(row, 4).toString());
                    cmbStatus.setSelectedItem(model.getValueAt(row, 5).toString());
                    txtBookingID.setText(model.getValueAt(row, 6).toString());
                }
            }
        });

        loadPayments();
    }

    private void addButton() {
        addBtn.setBounds(300, 20, 100, 30);
        updateBtn.setBounds(300, 60, 100, 30);
        deleteBtn.setBounds(300, 100, 100, 30);
        loadBtn.setBounds(300, 140, 100, 30);

        add(addBtn);
        add(updateBtn);
        add(deleteBtn);
        add(loadBtn);
    }

    private void addField(String lbl, JComponent txt, int y) {
        JLabel l = new JLabel(lbl);
        l.setBounds(20, y, 120, 25);
        txt.setBounds(150, y, 150, 25);
        add(l);
        add(txt);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try (Connection con = DB.getConnection()) {
            if (e.getSource() == addBtn) {
                PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO Payment (ReferenceNo, Amount, Date, Method, Status, BookingID) VALUES (?,?,?,?,?,?)"
                );
                ps.setString(1, txtReferenceNo.getText());
                ps.setBigDecimal(2, new java.math.BigDecimal(txtAmount.getText()));
                ps.setDate(3, Date.valueOf(txtDate.getText())); // yyyy-MM-dd
                ps.setString(4, cmbMethod.getSelectedItem().toString());
                ps.setString(5, cmbStatus.getSelectedItem().toString());
                ps.setInt(6, Integer.parseInt(txtBookingID.getText()));
                ps.executeUpdate();
                JOptionPane.showMessageDialog(this, "Payment Added!");
                loadPayments();

            } else if (e.getSource() == updateBtn) {
                if (txtPaymentID.getText().isEmpty()) return;
                PreparedStatement ps = con.prepareStatement(
                    "UPDATE Payment SET ReferenceNo=?, Amount=?, Date=?, Method=?, Status=?, BookingID=? WHERE PaymentID=?"
                );
                ps.setString(1, txtReferenceNo.getText());
                ps.setBigDecimal(2, new java.math.BigDecimal(txtAmount.getText()));
                ps.setDate(3, Date.valueOf(txtDate.getText()));
                ps.setString(4, cmbMethod.getSelectedItem().toString());
                ps.setString(5, cmbStatus.getSelectedItem().toString());
                ps.setInt(6, Integer.parseInt(txtBookingID.getText()));
                ps.setInt(7, Integer.parseInt(txtPaymentID.getText()));
                ps.executeUpdate();
                JOptionPane.showMessageDialog(this, "Payment Updated!");
                loadPayments();

            } else if (e.getSource() == deleteBtn) {
                if (txtPaymentID.getText().isEmpty()) return;
                PreparedStatement ps = con.prepareStatement("DELETE FROM Payment WHERE PaymentID=?");
                ps.setInt(1, Integer.parseInt(txtPaymentID.getText()));
                ps.executeUpdate();
                JOptionPane.showMessageDialog(this, "Payment Deleted!");
                loadPayments();

            } else if (e.getSource() == loadBtn) {
                loadPayments();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database Error: " + ex.getMessage());
        }
    }

    private void loadPayments() {
        try (Connection con = DB.getConnection()) {
            model.setRowCount(0);
            ResultSet rs = con.createStatement().executeQuery("SELECT * FROM Payment");
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("PaymentID"),
                    rs.getString("ReferenceNo"),
                    rs.getBigDecimal("Amount"),
                    rs.getDate("Date"),
                    rs.getString("Method"),
                    rs.getString("Status"),
                    rs.getInt("BookingID")
                });
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
