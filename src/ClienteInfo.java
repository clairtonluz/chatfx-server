import java.net.Socket;

/**
 * Created by clairton on 10/25/14.
 */
public class ClienteInfo {

    private Socket socket = null;
    private ClientListener clienteListener = null;
    private ClienteEnviador clienteEnviador = null;

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public ClientListener getClienteListener() {
        return clienteListener;
    }

    public void setClienteListener(ClientListener clienteListener) {
        this.clienteListener = clienteListener;
    }

    public ClienteEnviador getClienteEnviador() {
        return clienteEnviador;
    }

    public void setClienteEnviador(ClienteEnviador clienteEnviador) {
        this.clienteEnviador = clienteEnviador;
    }
}