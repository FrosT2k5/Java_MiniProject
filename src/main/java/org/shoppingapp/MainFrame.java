package org.shoppingapp;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    public MainFrame() {
        this.setLayout(new BoxLayout(this.getContentPane(),BoxLayout.Y_AXIS));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(500,550);
        this.setTitle("Fast-Food Restaurant App");
        this.setIconImage(new ImageIcon(getClass().getResource("/logo.png")).getImage());
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public void addItem(JPanel s) {
        this.add(s);
    }
}
