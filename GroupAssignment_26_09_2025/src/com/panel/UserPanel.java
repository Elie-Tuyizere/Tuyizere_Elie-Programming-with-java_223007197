package com.panel;

import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import com.util.DB;

public class UserPanel extends JPanel implements ActionListener {

    JTextField idtxt = new JTextField();
    JTextField nametxt = new JTextField();
    JTextField emailtxt = new JTextField();
    JComboBox<String> roleCmb = new JComboBox<>(new String[]{"Admin", "TUYIZERE", "Student"});
    JPasswordField passtxt = new JPasswordField();

    JButton addBtn = new JButton("Add"), 
            updateBtn = new JButton("Update"), 
            deleteBtn = new JButton("Delete"),
            loadBtn = new JButton("Load");

    JTable table;
    DefaultTableModel model;
    String currentRole;
    int currentUserId;

    public UserPanel(String role, int userid) {
        this.currentRole = role;
        this.currentUserId = userid;

        setLayout(null);
        String[] labels = { "ID", "Username", "PasswordHash", "Email", "Role" };
        model = new DefaultTableModel(labels, 0);
        table = new JTable(model);
        JScrollPane sp = new JScrollPane(table);
        sp.setBounds(20, 200, 800, 300);
        add(sp);

        int y = 20;
        addField("ID", idtxt, y); y+=30;
        addField("Username", nametxt, y); y+=30;
        addField("Password", passtxt, y); y+=30;
        addField("Email", emailtxt, y); y+=30;
        addField("Role", roleCmb, y); y+=30;

        addButton();

        addBtn.addActionListener(this);
        updateBtn.addActionListener(this);
        deleteBtn.addActionListener(this);
        loadBtn.addActionListener(this);

        table.getSelectionModel().addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            @Override
            public void valueChanged(javax.swing.event.ListSelectionEvent event) {
                if (!event.getValueIsAdjusting() && table.getSelectedRow() != -1) {
                    int row = table.getSelectedRow();
                    idtxt.setText(model.getValueAt(row, 0).toString());
                    nametxt.setText(model.getValueAt(row, 1).toString());
                    passtxt.setText(model.getValueAt(row, 2).toString());
                    emailtxt.setText(model.getValueAt(row, 3).toString());
                    roleCmb.setSelectedItem(model.getValueAt(row, 4).toString());
                }
            }
        });

        // Only Admin can add/update/delete users
        if(!currentRole.equalsIgnoreCase("Admin")) {
            addBtn.setEnabled(false);
            updateBtn.setEnabled(false);
            deleteBtn.setEnabled(false);
        }

        loadUsers();
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
        txt.setBounds(100, y, 150, 25);
        add(l);
        add(txt);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try (Connection con = DB.getConnection()) {
            if (e.getSource() == addBtn) {
                PreparedStatement ps = con.prepareStatement(
                        "INSERT INTO User (Username, PasswordHash, Email, Role) VALUES (?, ?, ?, ?)");
                ps.setString(1, nametxt.getText());
                ps.setString(2, new String(passtxt.getPassword())); // TODO: Hash this!
                ps.setString(3, emailtxt.getText());
                ps.setString(4, roleCmb.getSelectedItem().toString());
                ps.executeUpdate();
                JOptionPane.showMessageDialog(this, "User Added!");
                loadUsers();
            } else if (e.getSource() == updateBtn) {
                if (idtxt.getText().isEmpty()) return;
                PreparedStatement ps = con.prepareStatement(
                        "UPDATE User SET Username=?, PasswordHash=?, Email=?, Role=? WHERE UserID=?");
                ps.setString(1, nametxt.getText());
                ps.setString(2, new String(passtxt.getPassword())); 
                ps.setString(3, emailtxt.getText());
                ps.setString(4, roleCmb.getSelectedItem().toString());
                ps.setInt(5, Integer.parseInt(idtxt.getText()));
                ps.executeUpdate();
                JOptionPane.showMessageDialog(this, "User Updated!");
                loadUsers();
            } else if (e.getSource() == deleteBtn) {
                if (idtxt.getText().isEmpty()) return;
                int confirm = JOptionPane.showConfirmDialog(this, 
                    "Deleting this user will also remove linked records. Continue?", 
                    "Confirm Delete", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    PreparedStatement ps = con.prepareStatement("DELETE FROM User WHERE UserID=?");
                    ps.setInt(1, Integer.parseInt(idtxt.getText()));
                    ps.executeUpdate();
                    JOptionPane.showMessageDialog(this, "User Deleted!");
                    loadUsers();
                }
            } else if (e.getSource() == loadBtn) {
                loadUsers();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database Error: " + ex.getMessage());
        }
    }

    private void loadUsers() {
        try (Connection con = DB.getConnection()) {
            model.setRowCount(0);
            ResultSet rs = con.createStatement().executeQuery("SELECT * FROM User");
            while (rs.next()) {
                model.addRow(new Object[] {
                    rs.getInt("UserID"),
                    rs.getString("Username"),
                    rs.getString("PasswordHash"),
                    rs.getString("Email"),
                    rs.getString("Role")
                });
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database Error: " + ex.getMessage());
        }
    }
}
