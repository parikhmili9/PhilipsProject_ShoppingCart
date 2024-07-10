package server.model;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a Shopping cart and has methods to add an item to the cart and view all
 * items of the cart.
 */
public class ShoppingCart {
    private List<CartItem> items = new ArrayList<>();

    /**
     * This method returns a list of all the items present in the cart.
     * @return list of all CartItem objects
     */
    public List<CartItem> getItems(){
        return items;
    }

    /**
     * This method checks if the item is already present in the cart, if yes then it increases the
     * quantity of the existing item and if no then it adds a new item to the cart.
     * @param item - object of the CartItem class
     */
    public void addItem(CartItem item){
        if (item == null) {
            throw new IllegalArgumentException("Item cannot be null");
        }
        if (item.getPrice() < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }
        if (item.getQuantity() < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative");
        }

        for(CartItem cartItem : items){
            if(cartItem.getName().equals(item.getName()) && cartItem.getPrice() == item.getPrice()){
                cartItem.setQuantity(cartItem.getQuantity() + item.getQuantity());
                return;
            }
        }
        items.add(item);
    }

    public void clearCart() {
        items.clear();
    }
}
