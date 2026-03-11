import java.net.Socket;
import java.net.ServerSocket;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class MainServer {
    public static void main(String[] args) throws Exception{
        int lport = 8080;

        ServerSocket server = new ServerSocket(lport);
        System.out.println("Waiting for connections on port: " + lport);
        while (true) {
            Socket client = server.accept();
            System.out.println("Client connection accepted.");

            InputStream in = client.getInputStream();
            OutputStream out = client.getOutputStream();

            byte [] buffer = new byte[1024];

            int n = in.read(buffer);
            System.out.print(new String (buffer, 0, n));

            BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));

            String body = """
            <html>
            <head>
            <title>My server </title>
            </head>

            <body>

            <h1>My web server</h1>

            <p>This server is developed in Java.</p>

            <ul>
            <li>Supports sockets</li>
            <li>Respond HTTP</li>
            <li>Serves HTML</li>
            </ul>

            </body>
            </html>
            """;

            String response =
                "HTTP/1.1 200 OK\r\n" +
                "Content-Type: text/html\r\n" +
                "Content-Length: " + body.getBytes(StandardCharsets.UTF_8).length + "\r\n" +
                "Connection: close\r\n" +
                "\r\n" +
                body;

            out.write(response.getBytes(StandardCharsets.UTF_8));
            out.flush();

            client.close();

        }

    }
}
