package test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import server.model.*;

public class ShoppingCartTest {

    private ShoppingCart shoppingCart;

    @BeforeEach
    public void setUp() {
        shoppingCart = new ShoppingCart();
        shoppingCart.addItem(new CartItem("Item 1", 10.0, 1));
        shoppingCart.addItem(new CartItem("Item 2", 15.0, 2));
    }

    @Test
    public void testGetItems() {
        List<CartItem> items = shoppingCart.getItems();
        assertNotNull(items);
        assertEquals(2, items.size());
    }

    @Test
    public void testAddItem_NewItem() {
        shoppingCart.addItem(new CartItem("New Item", 20.0, 1));
        List<CartItem> items = shoppingCart.getItems();
        assertEquals(3, items.size());
    }

    @Test
    public void testAddItem_ExistingItem() {
        shoppingCart.addItem(new CartItem("Item 1", 10.0, 2)); // Increase quantity of existing item
        List<CartItem> items = shoppingCart.getItems();
        assertEquals(2, items.size()); // Should still have 2 unique items
        assertEquals(3, items.get(0).getQuantity()); // Quantity of Item 1 should be 3 now
    }

    @Test
    public void testAddItem_ExistingItemWithDifferentPrice() {
        shoppingCart.addItem(new CartItem("Item 1", 9.0, 2)); 
        List<CartItem> items = shoppingCart.getItems();
        assertEquals(3, items.size()); // Should still have 3 unique items
    }

    @Test
    public void testAddItem_NullItem() {
        assertThrows(IllegalArgumentException.class, () -> shoppingCart.addItem(null));
    }

    @Test
    public void testAddItem_NegativePrice() {
        assertThrows(IllegalArgumentException.class, () -> shoppingCart.addItem(new CartItem("Item 3", -5.0, 1)));
    }

    @Test
    public void testAddItem_NegativeQuantity() {
        assertThrows(IllegalArgumentException.class, () -> shoppingCart.addItem(new CartItem("Item 3", 5.0, -1)));
    }

    @Test
    public void testGetItems_EmptyCart() {
        shoppingCart = new ShoppingCart();
        List<CartItem> items = shoppingCart.getItems();
        assertNotNull(items);
        assertEquals(0, items.size());
    }

    @Test
    public void testClearCart() {
        shoppingCart.clearCart();
        List<CartItem> items = shoppingCart.getItems();
        assertEquals(0, items.size());
    }
}
