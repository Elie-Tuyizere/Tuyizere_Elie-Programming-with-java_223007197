package com.panel;

import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import com.util.DB;

public class RoomPanel extends JPanel implements ActionListener {

    JTextField txtRoomID = new JTextField();   // Locked (Auto Increment)
    JTextField txtRoomNumber = new JTextField();
    JComboBox<String> cmbRoomType = new JComboBox<>(new String[]{"Single", "Double", "Dorm"});
    JTextField txtCapacity = new JTextField();
    JComboBox<String> cmbStatus = new JComboBox<>(new String[]{"Available", "Occupied", "Maintenance"});
    JTextField txtUserID = new JTextField(); // optional, foreign key to User

    JButton addBtn = new JButton("Add"),
            updateBtn = new JButton("Update"),
            deleteBtn = new JButton("Delete"),
            loadBtn = new JButton("Load");

    JTable table;
    DefaultTableModel model;

    public RoomPanel() {
        setLayout(null);

        // Lock the RoomID field
        txtRoomID.setEditable(false);

        String[] labels = {"RoomID", "RoomNumber", "RoomType", "Capacity", "Status", "UserID", "CreatedAt"};
        model = new DefaultTableModel(labels, 0);
        table = new JTable(model);
        JScrollPane sp = new JScrollPane(table);
        sp.setBounds(20, 250, 800, 300);
        add(sp);

        int y = 20;
        addField("RoomID", txtRoomID, y); y += 30;
        addField("RoomNumber", txtRoomNumber, y); y += 30;
        addField("RoomType", cmbRoomType, y); y += 30;
        addField("Capacity", txtCapacity, y); y += 30;
        addField("Status", cmbStatus, y); y += 30;
        addField("UserID", txtUserID, y); y += 30;

        addButton();

        addBtn.addActionListener(this);
        updateBtn.addActionListener(this);
        deleteBtn.addActionListener(this);
        loadBtn.addActionListener(this);

        // Table row click -> load data into fields (old-style listener for compatibility)
        table.getSelectionModel().addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            @Override
            public void valueChanged(javax.swing.event.ListSelectionEvent event) {
                if (!event.getValueIsAdjusting() && table.getSelectedRow() != -1) {
                    int row = table.getSelectedRow();
                    txtRoomID.setText(model.getValueAt(row, 0).toString());
                    txtRoomNumber.setText(model.getValueAt(row, 1).toString());
                    cmbRoomType.setSelectedItem(model.getValueAt(row, 2).toString());
                    txtCapacity.setText(model.getValueAt(row, 3).toString());
                    cmbStatus.setSelectedItem(model.getValueAt(row, 4).toString());
                    txtUserID.setText(model.getValueAt(row, 5) != null ? model.getValueAt(row, 5).toString() : "");
                }
            }
        });

        loadRooms();
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
        l.setBounds(20, y, 80, 25);
        txt.setBounds(120, y, 150, 25);
        add(l);
        add(txt);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try (Connection con = DB.getConnection()) {
            if (e.getSource() == addBtn) {
                PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO Room (RoomNumber, RoomType, Capacity, Status, UserID) VALUES (?,?,?,?,?)"
                );
                ps.setString(1, txtRoomNumber.getText());
                ps.setString(2, cmbRoomType.getSelectedItem().toString());
                ps.setInt(3, Integer.parseInt(txtCapacity.getText()));
                ps.setString(4, cmbStatus.getSelectedItem().toString());
                if (txtUserID.getText().isEmpty()) {
                    ps.setNull(5, java.sql.Types.INTEGER);
                } else {
                    ps.setInt(5, Integer.parseInt(txtUserID.getText()));
                }
                ps.executeUpdate();
                JOptionPane.showMessageDialog(this, "Room Added!");
                loadRooms();

            } else if (e.getSource() == updateBtn) {
                if (txtRoomID.getText().isEmpty()) return;
                PreparedStatement ps = con.prepareStatement(
                    "UPDATE Room SET RoomNumber=?, RoomType=?, Capacity=?, Status=?, UserID=? WHERE RoomID=?"
                );
                ps.setString(1, txtRoomNumber.getText());
                ps.setString(2, cmbRoomType.getSelectedItem().toString());
                ps.setInt(3, Integer.parseInt(txtCapacity.getText()));
                ps.setString(4, cmbStatus.getSelectedItem().toString());
                if (txtUserID.getText().isEmpty()) {
                    ps.setNull(5, java.sql.Types.INTEGER);
                } else {
                    ps.setInt(5, Integer.parseInt(txtUserID.getText()));
                }
                ps.setInt(6, Integer.parseInt(txtRoomID.getText()));
                ps.executeUpdate();
                JOptionPane.showMessageDialog(this, "Room Updated!");
                loadRooms();

            } else if (e.getSource() == deleteBtn) {
                if (txtRoomID.getText().isEmpty()) return;
                PreparedStatement ps = con.prepareStatement("DELETE FROM Room WHERE RoomID=?");
                ps.setInt(1, Integer.parseInt(txtRoomID.getText()));
                ps.executeUpdate();
                JOptionPane.showMessageDialog(this, "Room Deleted!");
                loadRooms();

            } else if (e.getSource() == loadBtn) {
                loadRooms();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database Error: " + ex.getMessage());
        }
    }

    private void loadRooms() {
        try (Connection con = DB.getConnection()) {
            model.setRowCount(0);
            ResultSet rs = con.createStatement().executeQuery("SELECT * FROM Room");
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("RoomID"),
                    rs.getString("RoomNumber"),
                    rs.getString("RoomType"),
                    rs.getInt("Capacity"),
                    rs.getString("Status"),
                    rs.getObject("UserID"), // may be null
                    rs.getTimestamp("CreatedAt")
                });
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
