package test;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpPrincipal;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;

public class MockHttpExchange extends HttpExchange {

    private final Headers requestHeaders;
    private final Headers responseHeaders;
    private final URI requestURI;
    private final String requestMethod;
    private final InputStream requestBodyStream;
    private final ByteArrayOutputStream responseBodyStream;
    private int responseCode;

    public MockHttpExchange(String method, String path, String requestBody) {
        this.requestHeaders = new Headers();
        this.responseHeaders = new Headers();
        this.requestURI = URI.create(path);
        this.requestMethod = method;
        this.requestBodyStream = new ByteArrayInputStream(requestBody.getBytes());
        this.responseBodyStream = new ByteArrayOutputStream();
    }

    @Override
    public Headers getRequestHeaders() {
        return requestHeaders;
    }

    @Override
    public Headers getResponseHeaders() {
        return responseHeaders;
    }

    @Override
    public URI getRequestURI() {
        return requestURI;
    }

    @Override
    public String getRequestMethod() {
        return requestMethod;
    }

    @Override
    public OutputStream getResponseBody() {
        return responseBodyStream;
    }

    @Override
    public InputStream getRequestBody() {
        return requestBodyStream;
    }

    @Override
    public void sendResponseHeaders(int statusCode, long contentLength) throws IOException {
        responseCode = statusCode;
    }

    @Override
    public int getResponseCode() {
        return responseCode;
    }

    // Implement other abstract methods as needed

    @Override
    public InetSocketAddress getRemoteAddress() {
        return new InetSocketAddress("localhost", 8080); // Dummy implementation
    }

    @Override
    public InetSocketAddress getLocalAddress() {
        return new InetSocketAddress("localhost", 8000); // Dummy implementation
    }

    @Override
    public String getProtocol() {
        return "HTTP/1.1"; // Dummy implementation
    }

    @Override
    public Object getAttribute(String name) {
        return null; // Dummy implementation
    }

    @Override
    public void setAttribute(String name, Object value) {
        // Dummy implementation
    }

    @Override
    public void setStreams(InputStream i, OutputStream o) {
        // Dummy implementation
    }

    @Override
    public HttpPrincipal getPrincipal() {
        return null; // Dummy implementation
    }

    @Override
    public HttpContext getHttpContext() {
        return null; // Dummy implementation
    }

    @Override
    public void close() {
        try {
            requestBodyStream.close();
            responseBodyStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
