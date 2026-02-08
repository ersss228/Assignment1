package sports.club.management.system.controller;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import sports.club.management.system.config.DBConnection;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Minimal REST API server exposing JSON endpoints backed by the existing DB.
 * Endpoints:
 *  - GET  /api/sports   -> list sports
 *  - GET  /api/athletes -> list athletes
 *  - GET  /api/clubs    -> list sports clubs
 *  - POST /api/athletes -> create athlete from JSON {"name":"...","age":N}
 */
public class RestServer {

    public static void main(String[] args) throws IOException {
        int port = 8080;
        if (args != null && args.length > 0) {
            try { port = Integer.parseInt(args[0]); } catch (NumberFormatException ignored) { }
        }

        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);

        server.createContext("/api/sports", exchange -> {
            if ("GET".equalsIgnoreCase(exchange.getRequestMethod())) {
                handleListSports(exchange);
            } else {
                methodNotAllowed(exchange, "GET");
            }
        });

        server.createContext("/api/athletes", exchange -> {
            String method = exchange.getRequestMethod();
            if ("GET".equalsIgnoreCase(method)) {
                handleListAthletes(exchange);
            } else if ("POST".equalsIgnoreCase(method)) {
                handleCreateAthlete(exchange);
            } else {
                methodNotAllowed(exchange, "GET, POST");
            }
        });

        server.createContext("/api/clubs", exchange -> {
            if ("GET".equalsIgnoreCase(exchange.getRequestMethod())) {
                handleListClubs(exchange);
            } else {
                methodNotAllowed(exchange, "GET");
            }
        });

        server.createContext("/health", exchange -> sendJson(exchange, 200, "{\"status\":\"OK\"}"));

        server.setExecutor(null);
        server.start();
        System.out.println("REST server started on http://localhost:" + port);
        System.out.println("Endpoints: /api/sports, /api/athletes, /api/clubs, POST /api/athletes");
    }

    private static void handleListSports(HttpExchange exchange) throws IOException {
        StringBuilder json = new StringBuilder();
        json.append("[");
        boolean first = true;
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT id, name, isTeamSport FROM sport ORDER BY id");
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                if (!first) json.append(',');
                first = false;
                int id = rs.getInt("id");
                String name = rs.getString("name");
                boolean isTeam = rs.getBoolean("isTeamSport");
                json.append('{')
                        .append("\"id\":").append(id).append(',')
                        .append("\"name\":\"").append(escape(name)).append("\",")
                        .append("\"teamSport\":").append(isTeam)
                        .append('}');
            }
            json.append(']');
            sendJson(exchange, 200, json.toString());
        } catch (SQLException e) {
            sendError(exchange, 500, "DB_ERROR", e.getMessage());
        }
    }

    private static void handleListAthletes(HttpExchange exchange) throws IOException {
        StringBuilder json = new StringBuilder();
        json.append("[");
        boolean first = true;
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT id, name, age FROM athlete ORDER BY id");
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                if (!first) json.append(',');
                first = false;
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int age = rs.getInt("age");
                json.append('{')
                        .append("\"id\":").append(id).append(',')
                        .append("\"name\":\"").append(escape(name)).append("\",")
                        .append("\"age\":").append(age)
                        .append('}');
            }
            json.append(']');
            sendJson(exchange, 200, json.toString());
        } catch (SQLException e) {
            sendError(exchange, 500, "DB_ERROR", e.getMessage());
        }
    }

    private static void handleListClubs(HttpExchange exchange) throws IOException {
        StringBuilder json = new StringBuilder();
        json.append("[");
        boolean first = true;
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT id, name, numberofathletes FROM sportsclub ORDER BY id");
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                if (!first) json.append(',');
                first = false;
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int count = rs.getInt("numberofathletes");
                json.append('{')
                        .append("\"id\":").append(id).append(',')
                        .append("\"name\":\"").append(escape(name)).append("\",")
                        .append("\"numberOfAthletes\":").append(count)
                        .append('}');
            }
            json.append(']');
            sendJson(exchange, 200, json.toString());
        } catch (SQLException e) {
            sendError(exchange, 500, "DB_ERROR", e.getMessage());
        }
    }

    private static void handleCreateAthlete(HttpExchange exchange) throws IOException {
        String contentType = exchange.getRequestHeaders().getFirst("Content-Type");
        if (contentType == null || !contentType.toLowerCase().contains("application/json")) {
            sendError(exchange, 400, "BAD_REQUEST", "Content-Type must be application/json");
            return;
        }
        String body = readBody(exchange.getRequestBody());
        // extremely simple JSON parsing for {"name":"...","age":N}
        String name = null;
        Integer age = null;
        Pattern nameP = Pattern.compile("\"name\"\s*:\s*\"(.*?)\"");
        Pattern ageP = Pattern.compile("\"age\"\s*:\s*(\\d+)");
        Matcher m1 = nameP.matcher(body);
        if (m1.find()) name = m1.group(1);
        Matcher m2 = ageP.matcher(body);
        if (m2.find()) age = Integer.parseInt(m2.group(1));

        if (name == null || age == null) {
            sendError(exchange, 400, "BAD_REQUEST", "Expected JSON {\"name\":\"...\",\"age\":number}");
            return;
        }

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement("INSERT INTO athlete(name, age) VALUES (?, ?) RETURNING id");) {
            ps.setString(1, name);
            ps.setInt(2, age);
            try (ResultSet rs = ps.executeQuery()) {
                int id = -1;
                if (rs.next()) id = rs.getInt(1);
                String json = "{" +
                        "\"id\":" + id + "," +
                        "\"name\":\"" + escape(name) + "\"," +
                        "\"age\":" + age +
                        "}";
                sendJson(exchange, 201, json);
            }
        } catch (SQLException e) {
            sendError(exchange, 500, "DB_ERROR", e.getMessage());
        }
    }

    private static void sendJson(HttpExchange exchange, int status, String body) throws IOException {
        Headers h = exchange.getResponseHeaders();
        h.set("Content-Type", "application/json; charset=utf-8");
        byte[] bytes = body.getBytes(StandardCharsets.UTF_8);
        exchange.sendResponseHeaders(status, bytes.length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(bytes);
        }
    }

    private static void methodNotAllowed(HttpExchange exchange, String allowed) throws IOException {
        Headers h = exchange.getResponseHeaders();
        h.set("Allow", allowed);
        sendJson(exchange, 405, "{\"error\":\"METHOD_NOT_ALLOWED\",\"allowed\":\"" + escape(allowed) + "\"}");
    }

    private static void sendError(HttpExchange exchange, int status, String code, String message) throws IOException {
        String json = "{" +
                "\"error\":\"" + escape(code) + "\"," +
                "\"message\":\"" + escape(message == null ? "" : message) + "\"" +
                "}";
        sendJson(exchange, status, json);
    }

    private static String readBody(InputStream is) throws IOException {
        byte[] buf = is.readAllBytes();
        return new String(buf, StandardCharsets.UTF_8);
    }

    private static String escape(String s) {
        if (s == null) return "";
        return s.replace("\\", "\\\\").replace("\"", "\\\"").replace("\n", "\\n");
    }
}
