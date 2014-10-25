package io.github.clairtonluz.chatfx_server;

import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Vector;

/**
 * Created by clairton on 10/25/14.
 */
public class ServerTransmisor extends Thread {
    private Vector mMessageQueue = new Vector();
    private Vector listClientes = new Vector();
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
    private static final String MENSAGEM_DO_SISTEMA = "[SYSTEM]";

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
        mensagem = String.format("[%s] %s : %s", LocalDateTime.now().format(formatter), clienteInfo.getUsername(), mensagem);
        mMessageQueue.add(mensagem);
        notify();
    }

    public synchronized void informeNovoUsuario(ClienteInfo clienteInfo) {
        Socket socket = clienteInfo.getSocket();
        String mensagem = String.format("%s[%s] %s entrou no chat", MENSAGEM_DO_SISTEMA , LocalDateTime.now().format(formatter), clienteInfo.getUsername());
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