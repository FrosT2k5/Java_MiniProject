package org.shoppingapp;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ShopItem extends JPanel implements ActionListener {
    private String name;
    private String logoPath;
    private int cost;
    private ImageIcon logo;
    private JLabel label = new JLabel();
    private JTextField txt = new JTextField();
    private JButton plusButton = new JButton("+");
    private JButton minusButton = new JButton("-");
    private int amount = 0;
    private JPanel namePanel = new JPanel();
    private JPanel counterPanel = new JPanel();
    private static ShopCart currentCart;
    public ShopItem(String itemName, String itemLogoPath, int itemCost) {
        // Set the private fields
        name = itemName;
        logoPath = itemLogoPath;
        cost = itemCost;

        label.setText(name+"  : ₹"+cost);
        logo = new ImageIcon(getClass().getResource("/"+logoPath));
        label.setIcon(logo);
        label.setHorizontalTextPosition(JLabel.RIGHT);
        label.setIconTextGap(10);
        plusButton.addActionListener(this);
        minusButton.addActionListener(this);

        txt.setText("0");
        txt.addActionListener(this);
        txt.setColumns(3);

        this.setPreferredSize(new Dimension(600, 50));
        this.setLayout(new BoxLayout(this,BoxLayout.X_AXIS));

        namePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        counterPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        namePanel.setBorder(new EmptyBorder(0,20,0,0));
        counterPanel.setBorder(new EmptyBorder(10,10,10,20));

        namePanel.add(label);
        counterPanel.add(minusButton);
        counterPanel.add(txt);
        counterPanel.add(plusButton);

        this.add(namePanel);
        this.add(counterPanel);
        this.setVisible(true);
    }

    public ShopItem () {
    }

    public int getAmount() {
        if (amount > 0) {
            return amount;
        }
        else {
            return 0;
        }
    }

    public String getItemName() {
        return name;
    }

    public static void setCart(ShopCart shopCart) {
        currentCart = shopCart;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {

        if (actionEvent.getSource() == minusButton) {
            if (amount <= 0) {
                amount = 0;
            }
            else {
                amount -= 1;
            }
            currentCart.updateAmount();
        }

        if (actionEvent.getSource() == plusButton) {
            if (amount < 0) {
                amount = 0;
            }
            else {
                amount += 1;
            }
            currentCart.updateAmount();
        }

        if (actionEvent.getSource() == txt) {
           amount = Integer.parseInt(txt.getText());
           currentCart.updateAmount();
        }

        txt.setText(Integer.toString(amount));
    }

    public int getCost() {
        return cost;
    }
}
