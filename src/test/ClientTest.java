package test;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.IOException;
import client.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ClientTest {
    private final InputStream systemIn = System.in;
    private final PrintStream systemOut = System.out;

    private ByteArrayInputStream testIn;
    private ByteArrayOutputStream testOut;

    @BeforeEach
    public void setUpOutput() {
        testOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(testOut));
    }

    @AfterEach
    public void restoreSystemInputOutput() {
        System.setIn(systemIn);
        System.setOut(systemOut);
    }

    @Test
    public void testClient_AddItemToCart_Success() throws IOException {
        String input = "1\nTest Item\n10.0\n1\n3\n";
        testIn = new ByteArrayInputStream(input.getBytes());
        System.setIn(testIn);

        // Run the client application (invoke main method)
        Client.main(new String[]{});

        // Verify output
        String expectedOutput = "\nAvailable Commands:\n" +
                                "1. Add item to cart\n" +
                                "2. View cart\n" +
                                "3. Exit\n" +
                                "Enter command number: Enter item name: " +
                                "Enter item price: Enter item quantity: " +
                                "Item added to cart.\n" +
                                "\nAvailable Commands:\n" +
                                "1. Add item to cart\n" +
                                "2. View cart\n" +
                                "3. Exit\n" +
                                "Enter command number: Exiting program.\n";

        assertEquals(expectedOutput, testOut.toString());
    }

    @Test
    public void testClient_AddItemToCart_InvalidInputs() throws IOException {
        String input = "1\nTest Item\ninvalid_price\n10.0\n1\n3\n";
        testIn = new ByteArrayInputStream(input.getBytes());
        System.setIn(testIn);
        Client.main(new String[]{});
        String expectedOutput = "\nAvailable Commands:\n" +
                                "1. Add item to cart\n" +
                                "2. View cart\n" +
                                "3. Exit\n" +
                                "Enter command number: Enter item name: " +
                                "Enter item price: Invalid input. Please enter a valid number.\n" +
                                "Enter item price: Enter item quantity: " +
                                "Item added to cart.\n" +
                                "\nAvailable Commands:\n" +
                                "1. Add item to cart\n" +
                                "2. View cart\n" +
                                "3. Exit\n" +
                                "Enter command number: Exiting program.\n";
        assertEquals(expectedOutput, testOut.toString());
    }

    @Test
    public void testClient_ViewCart_Empty() throws IOException {
        String input = "2\n3\n";
        testIn = new ByteArrayInputStream(input.getBytes());
        System.setIn(testIn);
        Client.main(new String[]{});
        String expectedOutput = "\nAvailable Commands:\n" +
                                "1. Add item to cart\n" +
                                "2. View cart\n" +
                                "3. Exit\n" +
                                "Enter command number: Cart Contents:\n" +
                                "\nAvailable Commands:\n" +
                                "1. Add item to cart\n" +
                                "2. View cart\n" +
                                "3. Exit\n" +
                                "Enter command number: Exiting program.\n";
        assertEquals(expectedOutput, testOut.toString());
    }

    @Test
    public void testClient_ViewCart_AfterAddingItems() throws IOException {
        String input = "1\nTest Item 1\n10.0\n1\n1\nTest Item 2\n20.0\n2\n2\n3\n";
        testIn = new ByteArrayInputStream(input.getBytes());
        System.setIn(testIn);
        Client.main(new String[]{});
        String expectedOutput = "\nAvailable Commands:\n" +
                                "1. Add item to cart\n" +
                                "2. View cart\n" +
                                "3. Exit\n" +
                                "Enter command number: Enter item name: " +
                                "Enter item price: Enter item quantity: " +
                                "Item added to cart.\n" +
                                "\nAvailable Commands:\n" +
                                "1. Add item to cart\n" +
                                "2. View cart\n" +
                                "3. Exit\n" +
                                "Enter command number: Enter item name: " +
                                "Enter item price: Enter item quantity: " +
                                "Item added to cart.\n" +
                                "\nAvailable Commands:\n" +
                                "1. Add item to cart\n" +
                                "2. View cart\n" +
                                "3. Exit\n" +
                                "Enter command number: Cart Contents:\n" +
                                "Name: Test Item 1, Price: 10.0, Quantity: 1\n" +
                                "Name: Test Item 2, Price: 20.0, Quantity: 2\n" +
                                "\nAvailable Commands:\n" +
                                "1. Add item to cart\n" +
                                "2. View cart\n" +
                                "3. Exit\n" +
                                "Enter command number: Exiting program.\n";
        assertEquals(expectedOutput, testOut.toString());
    }

    @Test
    public void testClient_ExitDirectly() throws IOException {
        String input = "3\n";
        testIn = new ByteArrayInputStream(input.getBytes());
        System.setIn(testIn);
        Client.main(new String[]{});
        String expectedOutput = "\nAvailable Commands:\n" +
                                "1. Add item to cart\n" +
                                "2. View cart\n" +
                                "3. Exit\n" +
                                "Enter command number: Exiting program.\n";
        assertEquals(expectedOutput, testOut.toString());
    }

    @Test
    public void testClient_InvalidCommandNumber() throws IOException {
        String input = "invalid_command\n3\n";
        testIn = new ByteArrayInputStream(input.getBytes());
        System.setIn(systemIn);
        Client.main(new String[]{});
        String expectedOutput = "\nAvailable Commands:\n" +
                                "1. Add item to cart\n" +
                                "2. View cart\n" +
                                "3. Exit\n" +
                                "Enter command number: Invalid input. Please enter a number.\n" +
                                "Enter command number: Exiting program.\n";
        assertEquals(expectedOutput, testOut.toString());
    }
}
