package client;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.InputMismatchException;
import java.util.Scanner;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * This class handles the client side and sends HTTP POST/GET requests to the server based
 * on the inputs provided by the user of the application.
 */
public class Client {
    private static final String BASE_URL = "http://localhost:8000/api/cart";

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        
        // Continously prompt the user to inputs
        while (true) {
            System.out.println("\nAvailable Commands:");
            System.out.println("1. Add item to cart");
            System.out.println("2. View cart");
            System.out.println("3. Exit");

            System.out.print("Enter command number: ");
            int command = readIntInput(scanner);
            scanner.nextLine();

            switch (command) {
                case 1:
                    addItemToCart(scanner);
                    break;
                case 2:
                    viewCart();
                    break;
                case 3:
                    System.out.println("Exiting program.");
                    return;
                default:
                    System.out.println("Invalid command number. Please enter a valid command.");
                    break;
            }
        }
    }

    /**
     * Handles adding item to the cart, takes user inputs, creates a JSON object with those inputs and
     * sets up HTTP connection for POST request
     * @param scanner
     * @throws IOException
     */
    private static void addItemToCart(Scanner scanner) throws IOException {
        System.out.print("Enter item name: ");
        String name = scanner.nextLine();

        double price = readDoubleInput(scanner, "Enter item price: ");

        int quantity = readIntInput(scanner, "Enter item quantity: ");

         // Create a JSON object with the item details
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("price", price);
        json.put("quantity", quantity);

        // Set up the HTTP connection for the POST request
        URL url = new URL(BASE_URL + "/add");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true);

         // Write the JSON object to the output stream
        try (OutputStream os = conn.getOutputStream()) {
            byte[] input = json.toString().getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        // Get the response code
        int responseCode = conn.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            System.out.println("Item added to cart.");
        } else {
            System.out.println("Failed to add item to cart. Response code: " + responseCode);
        }

        conn.disconnect();
    }

    /**
     * Handles viweing items of the cart, sets up HTTP connection for GET request, reads the response
     * body and parses the JSON array to display the cart contents.
     * @throws IOException
     */
    private static void viewCart() throws IOException {
        // Set up the HTTP connection for the GET request
        URL url = new URL(BASE_URL + "/view");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        // Get the response code and handle the response
        int responseCode = conn.getResponseCode();
        if (responseCode == 200) {
            // Read the response body
            Scanner responseScanner = new Scanner(conn.getInputStream());
            String responseBody = responseScanner.useDelimiter("\\A").next();
            responseScanner.close();

            // Parse the JSON array and display cart contents
            JSONArray jsonArray = new JSONArray(responseBody);
            System.out.println("Cart Contents:");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject json = jsonArray.getJSONObject(i);
                System.out.println("Name: " + json.getString("name") + 
                                   ", Price: " + json.getDouble("price") + 
                                   ", Quantity: " + json.getInt("quantity"));
            }
        } else {
            System.out.println("Failed to retrieve cart. Response code: " + responseCode);
        }

        conn.disconnect();
    }

    // Helper method to read an integer input from the user, if the input is not integer then
    // prompt the user for another valid input.
    private static int readIntInput(Scanner scanner) {
        while (true) {
            try {
                return scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); // Clear the invalid input
            }
        }
    }

    // Helper method to read an double input from the user, if the input is not double then
    // prompt the user for another valid input.
    private static double readDoubleInput(Scanner scanner, String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return scanner.nextDouble();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.nextLine(); // Clear the invalid input
            }
        }
    }

    // Helper method to read an integer input from the user, if the input is not integer then
    // prompt the user for another valid input.
    private static int readIntInput(Scanner scanner, String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.nextLine(); // Clear the invalid input
            }
        }
    }
}
