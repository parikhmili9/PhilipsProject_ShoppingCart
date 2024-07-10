package server.model;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {
    private List<CartItem> items = new ArrayList<>();

    public List<CartItem> getItems(){
        return items;
    }

    public void addItem(CartItem item){
        for(CartItem cartItem : items){
            if(cartItem.getName().equals(item.getName()) && cartItem.getPrice() == item.getPrice()){
                cartItem.setQuantity(cartItem.getQuantity() + item.getQuantity());
                return;
            }
        }
        items.add(item);
    }
}
