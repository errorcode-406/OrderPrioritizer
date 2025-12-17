package com.orderprioritizer;

import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
            UIManager.put("Button.arc", 15);          // tombol rounded
            UIManager.put("Component.focusWidth", 2); // fokus border
            UIManager.put("Table.rowHeight", 30);    // tinggi row table
            UIManager.put("Table.showGrid", true);
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Order Prioritizer");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1100, 650);
            frame.setLocationRelativeTo(null); // center window

            GUI root = new GUI();
            frame.setContentPane(root);

            frame.setVisible(true);
        });
    }
}
