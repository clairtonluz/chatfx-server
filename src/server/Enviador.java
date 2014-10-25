package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

/**
 * Created by clairton on 10/25/14.
 */
public class Enviador extends Thread {

    private PrintWriter output;

    public Enviador(PrintWriter output) {
        this.output = output;
    }

    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            while (!isInterrupted()) {
                String message = in.readLine();
                output.println(message);
                output.flush();
            }
        } catch (IOException ioe) {
            // Communication is broken
        }
    }
}