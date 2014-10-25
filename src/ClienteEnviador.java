import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Vector;

/**
 * Created by clairton on 10/25/14.
 */
public class ClienteEnviador extends Thread {

    private Vector filaMensagens = new Vector();
    private ServerTransmisor serverTransmisor;
    private ClienteInfo clienteInfo;
    private PrintWriter output;

    public ClienteEnviador(ClienteInfo clienteInfo, ServerTransmisor serverTransmisor) throws IOException {
        this.clienteInfo = clienteInfo;
        this.serverTransmisor = serverTransmisor;
        Socket socket = clienteInfo.getSocket();
        output = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
    }

    public synchronized void enviarMensagem(String mensagem) {
        filaMensagens.add(mensagem);
        notify();
    }

    private synchronized String getProximaMensagemDaFila() throws InterruptedException {
        while (filaMensagens.size()==0) {
            wait();
        }
        String message = (String) filaMensagens.get(0);
        filaMensagens.removeElementAt(0);
        return message;
    }

    private void enviarMensagemParaCliente(String aMessage) {
        output.println(aMessage);
        output.flush();
    }

    public void run() {
        try {
            while (!isInterrupted()) {
                String message = getProximaMensagemDaFila();
                enviarMensagemParaCliente(message);
            }
        } catch (Exception e) {
            // Commuication problem
        }
        // Communication is broken. Interrupt both listener and sender threads
        clienteInfo.getClienteListener().interrupt();
        serverTransmisor.deleteClient(clienteInfo);
    }
}