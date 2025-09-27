package com.form;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.SwingConstants;

public class Operations implements ActionListener{
    JFrame frame;
    JLabel amountLabel = new JLabel("Amount requested", SwingConstants.CENTER);
    JLabel durationLabel = new JLabel("Duration (years)", SwingConstants.CENTER);
    JLabel results = new JLabel("Total to return", SwingConstants.CENTER);
    JTextField amountField = new JTextField();
    JTextField durationField = new JTextField();
    JTextField resultField = new JTextField();
    JButton calculate = new JButton("Calculate");

    public Operations(){
        createWindow();
        SetLocationAndSize();
        addcomponentsToFrame();
        AddActionEvent();
        setDefaultValues();
    }
    
    private void setDefaultValues() {
        amountField.setText("0");
        durationField.setText("0");
        resultField.setText("0");
    }
    
    private void AddActionEvent() {
        calculate.addActionListener(this);
    }
    
    private void addcomponentsToFrame() {
        frame.add(amountLabel);
        frame.add(durationLabel);
        frame.add(results);
        frame.add(amountField);
        frame.add(durationField);
        frame.add(resultField);
        frame.add(calculate);
    }
    
    private void SetLocationAndSize() {
        amountLabel.setBounds(10, 10, 150, 35);
        amountField.setBounds(170, 10, 120, 30);
        durationLabel.setBounds(10, 50, 150, 35);
        durationField.setBounds(170, 50, 120, 30);
        results.setBounds(10, 90, 150, 35);
        resultField.setBounds(170, 90, 120, 30);
        calculate.setBounds(100, 130, 100, 30);
        
        // Set white font and black border for labels
        amountLabel.setForeground(Color.WHITE);
        amountLabel.setBorder(new LineBorder(Color.BLACK, 2));
        
        durationLabel.setForeground(Color.WHITE);
        durationLabel.setBorder(new LineBorder(Color.BLACK, 2));
        
        results.setForeground(Color.WHITE);
        results.setBorder(new LineBorder(Color.BLACK, 2));
        
        // Center align the text in text fields
        amountField.setHorizontalAlignment(JTextField.CENTER);
        durationField.setHorizontalAlignment(JTextField.CENTER);
        resultField.setHorizontalAlignment(JTextField.CENTER);
        
        // Center align button text
        calculate.setHorizontalAlignment(SwingConstants.CENTER);
    }
    
    private void createWindow() {
        frame = new JFrame();
        frame.setTitle("Loan Calculator");
        frame.setBounds(10, 10, 400, 300);
        frame.getContentPane().setBackground(Color.blue);
        frame.getContentPane().setLayout(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
    }
    
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == calculate){
            
                double amount = Double.parseDouble(amountField.getText().replaceAll(",", ""));
                int duration = Integer.parseInt(durationField.getText());
                
               
                double interestRate = 0.006; 
                double totalReturn = amount * (1 + interestRate * duration);
              
                NumberFormat numberFormat = NumberFormat.getInstance();
                resultField.setText(numberFormat.format(totalReturn));
                
            }
        }
    }