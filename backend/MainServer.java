import java.net.Socket;
import java.net.ServerSocket;
import java.io.InputStream;
import java.io.OutputStream;

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
            int n;
            while ((n = in.read(buffer)) != -1) {
                System.out.print(new String(buffer, 0, n));
            }

            client.close();

        }

    }
}
