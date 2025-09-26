/** Group members

TUYIZERE Elie     223007197
HABUMUGISHA Eric  223009063
NIKUBWAYO Leandre 223015716
**/
package com.form;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.*;
import com.util.DB;

public class LoginForm extends JFrame implements ActionListener {

    JTextField usertxt = new JTextField("Enter Username");  
    JPasswordField passtxt = new JPasswordField();
    JComboBox<String> roleCmb = new JComboBox<>(new String[]{"Admin", "Staff", "Customer"});
    JButton loginbtn = new JButton("Login");
    JButton cancelbtn = new JButton("Cancel");

    public LoginForm() {
        setTitle("Login Form");
        setBounds(100, 100, 300, 250); 
        setLayout(null);

        usertxt.setBounds(50, 30, 200, 25);
        passtxt.setBounds(50, 70, 200, 25);
        roleCmb.setBounds(50, 110, 200, 25); 

        loginbtn.setBounds(30, 160, 100, 30);
        cancelbtn.setBounds(150, 160, 100, 30);

        add(usertxt);
        add(passtxt);
        add(roleCmb);
        add(loginbtn);
        add(cancelbtn);

        loginbtn.addActionListener(this);
        cancelbtn.addActionListener(this);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == cancelbtn) {
            dispose();
            return;
        }

        try (Connection con = DB.getConnection()) {
            String selectedRole = roleCmb.getSelectedItem().toString();
            String sql = "SELECT * FROM user WHERE username=? AND PasswordHash=? AND role=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, usertxt.getText().trim());
            ps.setString(2, new String(passtxt.getPassword()).trim());
            ps.setString(3, selectedRole);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int userid = rs.getInt("userid");
                dispose(); // close login window
                new HostelManagementSystem(selectedRole, userid); // open main BMS window based on selected role
            } else {
                JOptionPane.showMessageDialog(this, "Invalid login or role!", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, ex.getMessage(), "DB Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        new LoginForm();
    }
}
