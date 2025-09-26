package com.panel;

import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import com.util.DB;

public class BookingPanel extends JPanel implements ActionListener {

    JTextField txtBookingID = new JTextField();
    JTextField txtBookingDate = new JTextField(); // format: yyyy-MM-dd
    JComboBox<String> cmbStatus = new JComboBox<>(new String[]{"Pending", "Confirmed", "Cancelled"});
    JTextField txtAmount = new JTextField();
    JTextArea txtNotes = new JTextArea();
    JTextField txtRoomID = new JTextField();

    JButton addBtn = new JButton("Add"),
            updateBtn = new JButton("Update"),
            deleteBtn = new JButton("Delete"),
            loadBtn = new JButton("Load");

    JTable table;
    DefaultTableModel model;

    public BookingPanel() {
        setLayout(null);
        txtBookingID.setEditable(false);

        String[] labels = {"BookingID", "BookingDate", "Status", "Amount", "Notes", "RoomID"};
        model = new DefaultTableModel(labels, 0);
        table = new JTable(model);
        JScrollPane sp = new JScrollPane(table);
        sp.setBounds(20, 250, 800, 300);
        add(sp);

        int y = 20;
        addField("BookingID", txtBookingID, y); y+=30;
        addField("BookingDate", txtBookingDate, y); y+=30;
        addField("Status", cmbStatus, y); y+=30;
        addField("Amount", txtAmount, y); y+=30;

        JLabel lnotes = new JLabel("Notes");
        lnotes.setBounds(20, y, 80, 25);
        add(lnotes);
        JScrollPane noteSp = new JScrollPane(txtNotes);
        noteSp.setBounds(120, y, 150, 60);
        add(noteSp);
        y+=70;

        addField("RoomID", txtRoomID, y); y+=30;

        addButton();

        addBtn.addActionListener(this);
        updateBtn.addActionListener(this);
        deleteBtn.addActionListener(this);
        loadBtn.addActionListener(this);

      /**  table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && table.getSelectedRow() != -1) {
                int row = table.getSelectedRow();
                txtBookingID.setText(model.getValueAt(row,0).toString());
                txtBookingDate.setText(model.getValueAt(row,1).toString());
                cmbStatus.setSelectedItem(model.getValueAt(row,2).toString());
                txtAmount.setText(model.getValueAt(row,3).toString());
                txtNotes.setText(model.getValueAt(row,4)!=null?model.getValueAt(row,4).toString():"");
                txtRoomID.setText(model.getValueAt(row,5).toString());
            }
        });
**/
        loadBookings();
    }

    private void addButton() {
        addBtn.setBounds(300,20,100,30);
        updateBtn.setBounds(300,60,100,30);
        deleteBtn.setBounds(300,100,100,30);
        loadBtn.setBounds(300,140,100,30);
        add(addBtn); add(updateBtn); add(deleteBtn); add(loadBtn);
    }

    private void addField(String lbl, JComponent txt, int y) {
        JLabel l = new JLabel(lbl);
        l.setBounds(20,y,80,25);
        txt.setBounds(120,y,150,25);
        add(l); add(txt);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try(Connection con = DB.getConnection()) {
            if (e.getSource()==addBtn) {
                PreparedStatement ps = con.prepareStatement(
                  "INSERT INTO Booking (BookingDate, Status, Amount, Notes, RoomID) VALUES (?,?,?,?,?)"
                );
                ps.setDate(1, Date.valueOf(txtBookingDate.getText()));
                ps.setString(2, cmbStatus.getSelectedItem().toString());
                ps.setBigDecimal(3, new java.math.BigDecimal(txtAmount.getText()));
                ps.setString(4, txtNotes.getText());
                ps.setInt(5, Integer.parseInt(txtRoomID.getText()));
                ps.executeUpdate();
                JOptionPane.showMessageDialog(this,"Booking Added!");
                loadBookings();
            } else if (e.getSource()==updateBtn) {
                PreparedStatement ps = con.prepareStatement(
                  "UPDATE Booking SET BookingDate=?, Status=?, Amount=?, Notes=?, RoomID=? WHERE BookingID=?"
                );
                ps.setDate(1, Date.valueOf(txtBookingDate.getText()));
                ps.setString(2, cmbStatus.getSelectedItem().toString());
                ps.setBigDecimal(3, new java.math.BigDecimal(txtAmount.getText()));
                ps.setString(4, txtNotes.getText());
                ps.setInt(5, Integer.parseInt(txtRoomID.getText()));
                ps.setInt(6, Integer.parseInt(txtBookingID.getText()));
                ps.executeUpdate();
                JOptionPane.showMessageDialog(this,"Booking Updated!");
                loadBookings();
            } else if (e.getSource()==deleteBtn) {
                PreparedStatement ps = con.prepareStatement("DELETE FROM Booking WHERE BookingID=?");
                ps.setInt(1, Integer.parseInt(txtBookingID.getText()));
                ps.executeUpdate();
                JOptionPane.showMessageDialog(this,"Booking Deleted!");
                loadBookings();
            } else if (e.getSource()==loadBtn) {
                loadBookings();
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    private void loadBookings() {
        try(Connection con = DB.getConnection()) {
            model.setRowCount(0);
            ResultSet rs = con.createStatement().executeQuery("SELECT * FROM Booking");
            while(rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("BookingID"),
                    rs.getDate("BookingDate"),
                    rs.getString("Status"),
                    rs.getBigDecimal("Amount"),
                    rs.getString("Notes"),
                    rs.getInt("RoomID")
                });
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
}
