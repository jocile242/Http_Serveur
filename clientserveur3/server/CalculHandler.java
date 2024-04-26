package clientserveur3.clientserveur3.server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import clientserveur3.clientserveur3.calcul.ICalcul;
import clientserveur3.clientserveur3.calcul.OperationPlus;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class CalculHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        URI requestURI = exchange.getRequestURI();
        String query = requestURI.getQuery();
        Map<String, String> params = queryToMap(query);

        String num1Str = params.get("number1");
        String num2Str = params.get("number2");

        if (num1Str != null && num2Str != null) {
            try {
                Integer num1 = Integer.parseInt(num1Str);
                Integer num2 = Integer.parseInt(num2Str);

                ICalcul calcul = new OperationPlus();
                Double result = calcul.calcul(num1, num2);

                // Lire le fichier HTML à partir du disque
                String filePath = "C:/Users/stagiaire/IdeaProjects/Sockets_Serveurs/web/calcul.html";
                String response = new String(Files.readAllBytes(Paths.get(filePath)));

                // Remplacer le placeholder #Somme par le résultat du calcul
                response = response.replace("#Somme", result.toString());

                // Envoyer la réponse
                exchange.sendResponseHeaders(200, response.getBytes().length);
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
    }

    private Map<String, String> queryToMap(String query) {
        Map<String, String> result = new HashMap<>();
        for (String param : query.split("&")) {
            String[] entry = param.split("=");
            if (entry.length > 1) {
                result.put(entry[0], entry[1]);
            } else {
                result.put(entry[0], "");
            }
        }
        return result;
    }
}

