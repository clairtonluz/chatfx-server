import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Created by clairton on 10/25/14.
 */
public class ClientListener extends Thread {

    private ServerTransmisor serverTransmisor;
    private ClienteInfo clienteInfo;
    private BufferedReader input;

    public ClientListener(ClienteInfo clienteInfo, ServerTransmisor serverTransmisor) throws IOException {
        this.clienteInfo = clienteInfo;
        this.serverTransmisor = serverTransmisor;
        Socket socket = clienteInfo.getSocket();
        input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    /**
     * Ler mensagens do cliente socket, redireciona para a fila do server transmisor
     * e o server transmisor envia a mensagem
     */
    public void run() {
        try {
            clienteInfo.setUsername(input.readLine());
            serverTransmisor.informeNovoUsuario(clienteInfo);
            while (!isInterrupted()) {
                String message = input.readLine();
                if (message == null)
                    break;
                serverTransmisor.transmitirMensagem(clienteInfo, message);
            }
        } catch (IOException ioex) {
            // Problem reading from socket (communication is broken)
        }
        // Communication is broken. Interrupt both listener and sender threads
        clienteInfo.getClienteEnviador().interrupt();
        serverTransmisor.deleteClient(clienteInfo);
    }
}