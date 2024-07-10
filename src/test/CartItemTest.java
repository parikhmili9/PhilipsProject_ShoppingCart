package test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import server.model.*;

public class CartItemTest {
    
    private CartItem cartItem;

    @BeforeEach
    public void setUp() {
        cartItem = new CartItem("Test Item", 10.0, 2);
    }

    @Test
    public void testGetters() {
        assertEquals("Test Item", cartItem.getName());
        assertEquals(10.0, cartItem.getPrice(), 0.001);
        assertEquals(2, cartItem.getQuantity());
    }

    @Test
    public void testSetters() {
        cartItem.setName("Updated Item");
        cartItem.setPrice(15.0);
        cartItem.setQuantity(3);

        assertEquals("Updated Item", cartItem.getName());
        assertEquals(15.0, cartItem.getPrice(), 0.001);
        assertEquals(3, cartItem.getQuantity());
    }

    @Test
    public void testConstructor() {
        assertEquals("Test Item", cartItem.getName());
        assertEquals(10.0, cartItem.getPrice(), 0.001);
        assertEquals(2, cartItem.getQuantity());
    }
}
