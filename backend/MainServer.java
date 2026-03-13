import java.net.Socket;
import java.net.ServerSocket;
import java.io.OutputStream;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class MainServer {
    public static void main(String[] args) throws Exception{
        int lport = 8080;

        ServerSocket server = new ServerSocket(lport);
        System.out.println("Waiting for connections on port: " + lport);
        while (true) {
            Socket client = server.accept();
            System.out.println("Client connection accepted.");

            BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));

            OutputStream out = client.getOutputStream();

            String firstLine = reader.readLine();
            System.out.println(firstLine);

            String[] parts = firstLine.split(" ");

            String method = parts[0];
            String path = parts[1];
            String version = parts[2];

            String line;

            while ((line = reader.readLine()) != null && !line.isEmpty()) {
                System.out.println(line);
            }

            String contentType = "text/html";

            if (path.endsWith(".css")) {
                contentType = "text/css";
            }

            else if (path.endsWith(".js")) {
                contentType = "application/javascript";
            }

            if (path.equals("/")) {
                path = "/index.html";
            }

            String filePath = "../frontend" + path;

            String body = Files.readString(Path.of(filePath));

            String response =
                "HTTP/1.1 200 OK\r\n" +
                "Content-Type: " + contentType + "\r\n" +
                "Content-Length: " + body.getBytes(StandardCharsets.UTF_8).length + "\r\n" +
                "Connection: close\r\n" +
                "\r\n" +
                body;

            System.out.println("Serving file: " + filePath);

            out.write(response.getBytes(StandardCharsets.UTF_8));
            out.flush();

            client.close();

        }

    }
}
