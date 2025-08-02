/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.motorph.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

/**
 *
 * @author TheCatWhoDoesntMeow —Yamoyam, Jahaziel D.
 * Comments are made for studying and reviewing 
 */
public class LoginFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JCheckBox showPasswordCheckbox;

    public LoginFrame() {
        setTitle("MotorPH HR - Login");
        setSize(350, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window

        // Panel for layout
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Username label kag field
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Username:"), gbc);

        gbc.gridx = 1;
        usernameField = new JTextField(15);
        panel.add(usernameField, gbc);

        // Password label and field
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Password:"), gbc);

        gbc.gridx = 1;
        passwordField = new JPasswordField(15);
        panel.add(passwordField, gbc);

        // Checkbox para makita ang password
        gbc.gridx = 1;
        gbc.gridy = 2;
        showPasswordCheckbox = new JCheckBox("Show Password");
        showPasswordCheckbox.addActionListener(e -> togglePasswordVisibility());
        panel.add(showPasswordCheckbox, gbc);

        // Login button
        gbc.gridx = 1;
        gbc.gridy = 3;
        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(new LoginAction());
        panel.add(loginButton, gbc);

        add(panel);
    }
    //method or action for my Show Password
    private void togglePasswordVisibility() {
        if (showPasswordCheckbox.isSelected()) {
            passwordField.setEchoChar((char) 0); // Show password
        } else {
            passwordField.setEchoChar('•'); // Hide password
        }
    }

    private class LoginAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword()).trim();

            // Input validations
            if (username.isEmpty()) {
                JOptionPane.showMessageDialog(LoginFrame.this,
                        "Username is required!",
                        "Input Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (password.isEmpty()) {
                JOptionPane.showMessageDialog(LoginFrame.this,
                        "Password is required!",
                        "Input Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Authentication check
            if (authenticate(username, password)) {
                JOptionPane.showMessageDialog(LoginFrame.this,
                        "Login Successful!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE);
                dispose(); // Close login frame
                new MainFrame().setVisible(true); 
            } else {
                JOptionPane.showMessageDialog(LoginFrame.this,
                        "Incorrect Password!",
                        "Login Failed",
                        JOptionPane.ERROR_MESSAGE);
            }
        }

        private boolean authenticate(String username, String password) {
            try (BufferedReader br = new BufferedReader(new FileReader("data/loginCredentials.sql"))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] creds = line.split(",");
                    if (creds.length >= 2 && creds[0].equals(username) && creds[1].equals(password)) {
                        return true;
                    }
                }
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(LoginFrame.this,
                        "Error reading user database!",
                        "File Error",
                        JOptionPane.ERROR_MESSAGE);
            }
            return false;
        }
    }

    // For testing
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new LoginFrame().setVisible(true);
        });
    }
}
