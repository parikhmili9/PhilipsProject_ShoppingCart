package test;

import com.sun.net.httpserver.HttpExchange;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import server.handler.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ShoppingCartHandlerTest {

    private ShoppingCartHandler shoppingCartHandler;

    @BeforeEach
    public void setUp() {
        shoppingCartHandler = new ShoppingCartHandler();
    }

    @Test
    public void testHandleAddItem_Success() throws IOException {
        String requestBody = "{ \"name\": \"Test Item\", \"price\": 10.0, \"quantity\": 1 }";
        HttpExchange exchange = new MockHttpExchange("POST", "/api/cart/add", requestBody);
        shoppingCartHandler.handle(exchange);
        assertEquals(200, exchange.getResponseCode());
    }

    @Test
    public void testHandleAddItem_InvalidJSON() throws IOException {
        String requestBody = "{ \"name\": \"Test Item\", \"price\": \"invalid\", \"quantity\": 1 }";
        HttpExchange exchange = new MockHttpExchange("POST", "/api/cart/add", requestBody);
        shoppingCartHandler.handle(exchange);
        assertEquals(400, exchange.getResponseCode());
    }

    @Test
    public void testHandleAddItem_MissingFields() throws IOException {
        String requestBody = "{ \"name\": \"Test Item\" }";
        HttpExchange exchange = new MockHttpExchange("POST", "/api/cart/add", requestBody);
        shoppingCartHandler.handle(exchange);
        assertEquals(400, exchange.getResponseCode());
    }

    @Test
    public void testHandleAddItem_EmptyBody() throws IOException {
        HttpExchange exchange = new MockHttpExchange("POST", "/api/cart/add", "");
        shoppingCartHandler.handle(exchange);
        assertEquals(400, exchange.getResponseCode());
    }

    @Test
    public void testHandleAddItem_InvalidMethod() throws IOException {
        String requestBody = "{ \"name\": \"Test Item\", \"price\": 10.0, \"quantity\": 1 }";
        HttpExchange exchange = new MockHttpExchange("GET", "/api/cart/add", requestBody);
        shoppingCartHandler.handle(exchange);
        assertEquals(404, exchange.getResponseCode());
    }

    @Test
    public void testHandleViewCart_Success() throws IOException {
        HttpExchange exchange = new MockHttpExchange("GET", "/api/cart/view", "");
        shoppingCartHandler.handle(exchange);
        assertEquals(200, exchange.getResponseCode());
    }

    @Test
    public void testHandleViewCart_ViewAfterAddSuccess() throws IOException {
        // Add an item first to ensure the cart is not empty
        String requestBody = "{ \"name\": \"Test Item\", \"price\": 10.0, \"quantity\": 1 }";
        HttpExchange addItemExchange = new MockHttpExchange("POST", "/api/cart/add", requestBody);
        shoppingCartHandler.handle(addItemExchange);
        HttpExchange viewCartExchange = new MockHttpExchange("GET", "/api/cart/view", "");
        shoppingCartHandler.handle(viewCartExchange);
        assertEquals(200, viewCartExchange.getResponseCode());
    }

    @Test
    public void testHandleInvalidPath() throws IOException {
        HttpExchange exchange = new MockHttpExchange("GET", "/api/cart/invalid", "");
        shoppingCartHandler.handle(exchange);
        assertEquals(404, exchange.getResponseCode());
    }

    @Test
    public void testHandleUnknownMethod() throws IOException {
        HttpExchange exchange = new MockHttpExchange("DELETE", "/api/cart/view", "");
        shoppingCartHandler.handle(exchange);
        assertEquals(404, exchange.getResponseCode());
    }
}
