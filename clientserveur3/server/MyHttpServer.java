package clientserveur3.clientserveur3.server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class MyHttpServer {
    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        server.createContext("/accueil", new AccueilHandler());
        server.createContext("/calcul", new CalculHandler());
        server.createContext("/image", new ImageHandler());
        server.start();
    }
}
