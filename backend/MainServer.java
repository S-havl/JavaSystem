import java.net.Socket;
import java.net.ServerSocket;
import java.io.InputStream;
import java.io.OutputStream;

public class MainServer {
    public static void main(String[] args) throws Exception{
        int lport = 8080;

        ServerSocket server = new ServerSocket(lport);
        System.out.println("Waiting for conections on port: " + lport);
        while (true) {
            Socket client = server.accept();
            System.out.println("Client conection accept.");

            InputStream in = client.getInputStream();
            OutputStream out = client.getOutputStream();

        }
    }
}
