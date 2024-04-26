package clientserveur3.clientserveur3.server;

import clientserveur3.clientserveur3.calcul.*;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.context.IContext;
import org.thymeleaf.context.WebContext;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;

public class AccueilHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {

        String resourceName = "accueil.html";
        Map<String, Integer> parametersMap = new HashMap<>();
        String operation = "add";

        // Récupérer les paramètres et leur valeur
        String requestUri = exchange.getRequestURI().getQuery();
        if (requestUri != null) {
            String[] parameters = requestUri.split("&");
            for (String parameterString : parameters) {
                if (parameterString.startsWith("number")) {
                    String[] p = parameterString.split("=");
                    int number = 0;
                    try {
                        number = Integer.parseInt(p[1]);
                    } catch (Exception e) {
                    }
                    parametersMap.put(p[0], number);
                } else {
                    if (parameterString.startsWith("operation")) {
                        String[] p = parameterString.split("=");
                        operation = p[1];
                    }
                }
            }
        }

        // Lire le fichier HTML à partir de la ressource
        InputStream resourceStream = this.getClass().getResourceAsStream(resourceName);
        Scanner scanner = new Scanner(resourceStream, StandardCharsets.UTF_8);
        String html = scanner.useDelimiter("\\A").next();
        scanner.close();

        // Calculer le résultat
        ICalcul op = null;
        if (operation.equals("sub")) {
            op = new OperationMoins();
        } else if (operation.equals("mul")) {
            op = new OperationMult();
        } else if (operation.equals("div")) {
            op = new OperationDiv();
        } else {
            op = new OperationPlus();
        }

        Double result = 0.0;
        if (parametersMap.get("number1") != null && parametersMap.get("number2") != null) {
            result = op.calcul(parametersMap.get("number1"), parametersMap.get("number2"));
        }

        // Intégrer le résultat dans la page HTML
        TemplateEngine engine = new TemplateEngine();
        Context ctx = new Context();
        ctx.setVariable("result", result);
        String processedTemplate = engine.process(html, ctx);

        // Envoyer la réponse
        exchange.sendResponseHeaders(200, processedTemplate.getBytes().length);
        OutputStream os = exchange.getResponseBody();
        os.write(processedTemplate.getBytes());
        os.close();
    }
}

