import org.shoppingapp.*;

public class Main {

    public static void main(String[] args) {
        MainFrame frame = new MainFrame();
        ShopItem items[] = new ShopItem[7];
        ShopCart cart;

        items[0] = new ShopItem("Burger","burger.png",100);
        items[1] = new ShopItem("Burger and Colddrink","burgeranddrink.png",150);
        items[2] = new ShopItem("Coffee","coffee.png",50);
        items[3] = new ShopItem("Colddrink","colddrink.png",50);
        items[4] = new ShopItem("Fries","fries.png",120);
        items[5] = new ShopItem("Pizza","pizza.png",100);
        items[6] = new ShopItem("Sandwich","sandwitch.png",80);

        for (ShopItem item : items) {
            frame.addItem(item);
        }

        cart = new ShopCart(items);
        ShopItem.setCart(cart);
        frame.addItem(cart.getCartPanel());
        frame.setVisible(true);
    }
}
