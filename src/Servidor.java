import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {

    public static void main(String[] args) {
        ServerSocket server;
        PrintStream out;
        DataInputStream in;
        try {
            server = new ServerSocket(1024);
            System.out.println("Porta 1024 liberado para conexao\n");
            try {
                while (true) {
                    Socket theConnection = server.accept();
                    System.out
                            .println("\nCliente " + theConnection + "Conectou");
//                    out = new PrintStream(theConnection.getOutputStream());
                    in = new DataInputStream(
                            theConnection.getInputStream());

                    StringBuffer line = new StringBuffer(in.readLine());

                    System.out.print(line);
                }
            } catch (IOException e) {
                server.close();
                System.err.println(e);
            }
        } catch (IOException e) {
            System.err.println(e);
        }

    }
}
