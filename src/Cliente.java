import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;

public class Cliente {

	public static void main(String[] args) {
		int port = 1024;
//		String hostname = "192.168.0.5";
		String hostname = "localhost";
		try (Socket cliente = new Socket(hostname, port)) {
			DataInputStream in = new DataInputStream(cliente.getInputStream());
			PrintStream out = new PrintStream(cliente.getOutputStream());
			DataInputStream userInput = new DataInputStream(System.in);
			String line;

			while (true) {
				System.out.print(">");
				line = userInput.readLine();
				if ((line == null) || line.equals("exit"))
					break;
				out.println(line);
				line = in.readLine();
				System.out.println(line);
			}
		} catch (IOException e) {
			System.err.println(e);
		}
	}
}
