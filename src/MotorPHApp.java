/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */

/**
 *
 * @author yamoyamjahaziel / TheCatWhoDoesntMeow
 * Milestone II
 */

import javax.swing.*;

public class MotorPHApp {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginFrame loginFrame = new LoginFrame ();
            loginFrame.setVisible(true);
        });
    }
    
}
