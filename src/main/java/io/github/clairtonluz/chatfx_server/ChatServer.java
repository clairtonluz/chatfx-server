package io.github.clairtonluz.chatfx_server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by clairton on 10/25/14.
 */
public class ChatServer {

        public static final int LISTENING_PORT = 8080;

        public static void main(String[] args) {
            ServerSocket serverSocket = null;
            try {
                serverSocket = new ServerSocket(LISTENING_PORT);
                System.out.println("ChatServer rodando na porta " + LISTENING_PORT);
            } catch (IOException e) {
                System.err.println("ChatServer não pode iniciar na porta " + LISTENING_PORT);
                e.printStackTrace();
                System.exit(0);
            }
            ServerTransmisor serverTransmisor = new ServerTransmisor();
            serverTransmisor.start();

            // Aceita e gerência as conexões
            while (true) {
                try {
                    Socket socket = serverSocket.accept();
                    ClienteInfo clientInfo = new ClienteInfo();
                    clientInfo.setSocket(socket);
                    ClientListener clientListener = new ClientListener(clientInfo, serverTransmisor);
                    ClienteEnviador clientSender = new ClienteEnviador(clientInfo, serverTransmisor);
                    clientInfo.setClienteListener(clientListener);
                    clientInfo.setClienteEnviador(clientSender);
                    clientListener.start();
                    clientSender.start();
                    serverTransmisor.addCliente(clientInfo);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
}
