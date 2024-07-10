package client;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.InputMismatchException;
import java.util.Scanner;
import org.json.JSONArray;
import org.json.JSONObject;

public class Client {
    private static final String BASE_URL = "http://localhost:8000/api/cart";

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        
        while (true) {
            System.out.println("\nAvailable Commands:");
            System.out.println("1. Add item to cart");
            System.out.println("2. View cart");
            System.out.println("3. Exit");

            System.out.print("Enter command number: ");
            int command = readIntInput(scanner);
            scanner.nextLine(); // consume newline

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

    private static void addItemToCart(Scanner scanner) throws IOException {
        System.out.print("Enter item name: ");
        String name = scanner.nextLine();

        double price = readDoubleInput(scanner, "Enter item price: ");

        int quantity = readIntInput(scanner, "Enter item quantity: ");

        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("price", price);
        json.put("quantity", quantity);

        URL url = new URL(BASE_URL + "/add");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true);

        try (OutputStream os = conn.getOutputStream()) {
            byte[] input = json.toString().getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        int responseCode = conn.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            System.out.println("Item added to cart.");
        } else {
            System.out.println("Failed to add item to cart. Response code: " + responseCode);
        }

        conn.disconnect();
    }

    private static void viewCart() throws IOException {
        URL url = new URL(BASE_URL + "/view");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        int responseCode = conn.getResponseCode();
        if (responseCode == 200) {
            Scanner responseScanner = new Scanner(conn.getInputStream());
            String responseBody = responseScanner.useDelimiter("\\A").next();
            responseScanner.close();

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
