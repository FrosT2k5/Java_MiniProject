package org.shoppingapp;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URI;
import java.util.Arrays;
import org.json.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ShopCart implements ActionListener {
    private ShopItem userItems[];
    private JTextArea amountText = new JTextArea(1,5);
    private int totalAmount = 0;
    private JButton submitOrderButton = new JButton("Submit");
    private JPanel cartPanel = new JPanel();
    private JLabel costLabel = new JLabel("Cost: ");
    private final String cartUri = "http://127.0.0.1:8000/";


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
        String orderNo = "0";
        JSONObject orderJson = new JSONObject();
        JSONObject orderItemsJson = new JSONObject();
        HttpClient client = HttpClient.newHttpClient();
        HttpResponse response;
        JSONObject responseJson;

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
            orderJson.put("name", name);
            orderJson.put("items", orderItemsJson.toString());

            HttpRequest request = HttpRequest.newBuilder(
                            URI.create(cartUri+"bookorder"))
                    .header("Content-Type","application/json")
                    .version(HttpClient.Version.HTTP_1_1)
                    .POST(HttpRequest.BodyPublishers.ofString(orderJson.toString()))
                    .build();
            try {
                response = client.send(request, HttpResponse.BodyHandlers.ofString());
                responseJson = new JSONObject(response.body().toString());
                orderNo = responseJson.get("orderno").toString();
                System.out.println(orderNo);
            }
            catch (Exception e) {
                System.out.println(e.getStackTrace());
            }


            JOptionPane.showMessageDialog(null,"Order Booked Successfully. \nOrder No: "+orderNo);
        }
    }


    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource() == submitOrderButton) {
            bookOrder();
        }
    }
}
