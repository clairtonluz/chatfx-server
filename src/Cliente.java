import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;

public class Cliente {
    int port = 9000;
    //		String hostname = "192.168.0.5";
    String hostname = "localhost";
    private Socket clientSocket;

    public void enviar(String mensagem) {

        try {
            this.clientSocket = new Socket(hostname, port);
            DataInputStream in = new DataInputStream(clientSocket.getInputStream());
            PrintStream out = new PrintStream(clientSocket.getOutputStream());
            DataInputStream userInput = new DataInputStream(System.in);

            if ((mensagem != null) || !mensagem.isEmpty()) {
                out.println(mensagem);
            }

        } catch (IOException e) {
            System.err.println(e);
        }
    }

}
