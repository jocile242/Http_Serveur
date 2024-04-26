package clientserveur3.clientserveur3.server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ImageHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        // Lire le fichier HTML à partir du disque
        String filePath = "C:/Users/stagiaire/IdeaProjects/Sockets_Serveurs/web/image.html";
        String response = new String(Files.readAllBytes(Paths.get(filePath)));

        // Envoyer la réponse
        exchange.sendResponseHeaders(200, response.getBytes().length);
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}

