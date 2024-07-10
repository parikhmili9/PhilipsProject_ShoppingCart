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

/**
 * This class handles adding items to the cart and retrieving items from the cart.
 * It is an HTTP Server to handle the API requests.
 */
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

    /**
     * This method handles the HTTP GET requests to view the contents of the shopping cart
     * @param exchange
     * @throws IOException
     */
    private void handleViewCart(HttpExchange exchange) throws IOException {
        // Create a JSON array to hold the cart items
        JSONArray jsonArray = new JSONArray();
        // Iterate through the items in the shopping cart
        for (CartItem item : shoppingCart.getItems()) {
            // Create a JSON object for each item
            JSONObject json = new JSONObject();
            json.put("name", item.getName());
            json.put("price", item.getPrice());
            json.put("quantity", item.getQuantity());
            // Add the JSON object to the JSON array
            jsonArray.put(json);
        }
         // Convert the JSON array to a string
        String response = jsonArray.toString();
        // Send a 200 OK response with the JSON array as the body
        exchange.sendResponseHeaders(200, response.getBytes().length);
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    /**
     * This method handles the HTTP POST requests to add items to the shopping cart.
     * @param exchange
     * @throws IOException
     */
    private void handleAddItem(HttpExchange exchange) throws IOException {
        try {
            // Read the request body
            String requestBody = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
            // Parse the request body as a JSON object
            JSONObject json = new JSONObject(requestBody);
            // Extract item details from the JSON body
            String name = json.getString("name");
            double price = json.getDouble("price");
            int quantity = json.getInt("quantity");
            // Create a new CartItem object with the extracted details
            CartItem item = new CartItem(name, price, quantity);
            // Add the item to the shopping cart
            shoppingCart.addItem(item);
            // Send a 200 OK response
            exchange.sendResponseHeaders(200, -1);
        } catch (Exception e) {
            e.printStackTrace();
            // Send a 400 Bad Request response if an error occurs
            exchange.sendResponseHeaders(400, -1);
        }
    }
}
