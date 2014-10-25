import java.net.Socket;
import java.util.Vector;

/**
 * Created by clairton on 10/25/14.
 */
public class ServerTransmisor extends Thread {
    private Vector mMessageQueue = new Vector();
    private Vector listClientes = new Vector();

    public synchronized void addCliente(ClienteInfo clienteInfo) {
        listClientes.add(clienteInfo);
    }

    public synchronized void deleteClient(ClienteInfo aClientInfo) {
        int clientIndex = listClientes.indexOf(aClientInfo);
        if (clientIndex != -1) {
            listClientes.removeElementAt(clientIndex);
        }
    }

    public synchronized void transmitirMensagem(ClienteInfo clienteInfo, String mensagem) {
        Socket socket = clienteInfo.getSocket();
        String hostOrigem = socket.getInetAddress().getHostAddress();
        String portaOrigem = "" + socket.getPort();
        mensagem = hostOrigem + ":" + portaOrigem + " : " + mensagem;
        mMessageQueue.add(mensagem);
        notify();
    }

    private synchronized String getProximaMensagemDaFila() throws InterruptedException {
        while (mMessageQueue.size()==0) {
            wait();
        }
        String message = (String) mMessageQueue.get(0);
        mMessageQueue.removeElementAt(0);
        return message;
    }

    private synchronized void enviarMensagemParaTodosClientes(String aMessage) {
        for (int i=0; i< listClientes.size(); i++) {
            ClienteInfo clienteInfo = (ClienteInfo) listClientes.get(i);
            clienteInfo.getClienteEnviador().enviarMensagem(aMessage);
        }
    }

    /**
     * Infinitamente ler as mensagens da fila e envia para todos
     * os clientes conectados
     */
    public void run() {
        try {
            while (true) {
                String message = getProximaMensagemDaFila();
                enviarMensagemParaTodosClientes(message);
            }
        } catch (InterruptedException ie) {
            // Thread interrupted. Stop its execution
        }
    }
}