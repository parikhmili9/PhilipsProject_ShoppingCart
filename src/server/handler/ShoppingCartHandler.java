package server.handler;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

import org.json.JSONArray;
import org.json.JSONObject;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import server.model.CartItem;
import server.model.ShoppingCart;

public class ShoppingCartHandler implements HttpHandler{
    private final ShoppingCart shoppingCart = new ShoppingCart();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();
        String path = exchange.getRequestURI().getPath();

        if ("POST".equalsIgnoreCase(method) && path.equals("/api/cart/add")){
            handleAddItem(exchange);
        } else if ("GET".equalsIgnoreCase(method) && path.equals("/api/cart/view")){
            handleViewCart(exchange);
        } else {
            exchange.sendResponseHeaders(404, -1);
        }
    }

    private void handleViewCart(HttpExchange exchange) throws IOException {
        JSONArray jsonArray = new JSONArray();

        for (CartItem item : shoppingCart.getItems()) {
            JSONObject json = new JSONObject();
            json.put("name", item.getName());
            json.put("price", item.getPrice());
            json.put("quantity", item.getQuantity());
            jsonArray.put(json);
        }

        String response = jsonArray.toString();
        exchange.sendResponseHeaders(200, response.getBytes().length);
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    private void handleAddItem(HttpExchange exchange) throws IOException {
        try {
            String requestBody = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
            JSONObject json = new JSONObject(requestBody);

            String name = json.getString("name");
            double price = json.getDouble("price");
            int quantity = json.getInt("quantity");

            CartItem item = new CartItem(name, price, quantity);
            shoppingCart.addItem(item);

            exchange.sendResponseHeaders(200, -1);
        } catch (Exception e) {
            e.printStackTrace();
            exchange.sendResponseHeaders(400, -1);
        }
    }
    
}
