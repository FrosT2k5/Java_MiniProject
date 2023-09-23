package org.shoppingapp;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import org.json.*;

public class ShopCart implements ActionListener {
    private ShopItem userItems[];
    private JTextArea amountText = new JTextArea(1,5);
    private int totalAmount = 0;
    private JButton submitOrderButton = new JButton("Submit");
    private JPanel cartPanel = new JPanel();
    private JLabel costLabel = new JLabel("Cost: ");


    public ShopCart(ShopItem[] items) {
        userItems = Arrays.copyOf(items, items.length);
        amountText.setEditable(false);
        cartPanel.add(costLabel);
        cartPanel.add(amountText);
        cartPanel.add(submitOrderButton);
        submitOrderButton.addActionListener(this);
        updateAmount();
    }

    private void calculateAmount() {
        int currentItemAmount,currentItemCost;
        totalAmount = 0;
        for (ShopItem item : userItems) {
            currentItemAmount = item.getAmount();
            currentItemCost = item.getCost();
            totalAmount += (currentItemAmount * currentItemCost);
        }
    }

    public void updateAmount() {
        calculateAmount();
        amountText.setText(String.valueOf(totalAmount));
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public JPanel getCartPanel() {
        return cartPanel;
    }

    public void bookOrder() {
        JSONObject orderJson = new JSONObject();
        JSONObject orderItemsJson = new JSONObject();
        String orderList = "";
        for (ShopItem item : userItems) {
            if (item.getAmount() > 0) {
                orderList += (item.getItemName() + ": " + item.getAmount()) + "\n";
                orderItemsJson.put(item.getItemName(),item.getAmount());
            }
        }
        orderList += "\nCost: "+totalAmount;
        orderList += "\nEnter your name to book order";

        String name = JOptionPane.showInputDialog(orderList);
        if (name == null){
            ;
        }
        else if (name.equals("")) {
            JOptionPane.showMessageDialog(null,"Please enter a name.");
        }

        else {
            System.out.println(name);
            orderJson.put("name", name);
            orderJson.put("items", orderItemsJson);
            System.out.println(orderJson);
            JOptionPane.showMessageDialog(null,"Order Booked Successfully.");
        }
    }


    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource() == submitOrderButton) {
            bookOrder();
        }
    }
}
