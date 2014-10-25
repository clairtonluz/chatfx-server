import java.io.DataInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by clairton on 10/24/14.
 */
public class Tarefa implements Runnable {

    protected Socket clientSocket = null;
    protected String serverText = null;

    public Tarefa(Socket clientSocket, String serverText) {
        this.clientSocket = clientSocket;
        this.serverText = serverText;
    }

    public void run() {
        try {

            DataInputStream input = new DataInputStream(clientSocket.getInputStream());
            OutputStream output = clientSocket.getOutputStream();
            long time = System.currentTimeMillis();
            output.write((this.serverText).getBytes());

            String line = input.readLine();
            if (line != null) {
                output.write(line.getBytes());

                output.close();
                input.close();
                System.out.println("Server txt " + this.serverText);
                System.out.println("Request processed: " + time);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
