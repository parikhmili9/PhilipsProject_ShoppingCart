package server;

import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;

import server.handler.ShoppingCartHandler;

/**
 * This Server class sets up an HTTP server using Java's built-in HttpServer class on port 8000. 
 * This class handles incoming HTTP requests and directs them to the appropriate handler.
 */
public class Server {
    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        
        // creates a new context, or route, for the server. The context is /api/cart, 
        // and any HTTP requests to this path will be handled by the ShoppingCartHandler class.
        server.createContext("/api/cart", new ShoppingCartHandler());
        server.setExecutor(null); // creates a default executor
        server.start();
        System.out.println("Server started on port 8000");
    }
}
