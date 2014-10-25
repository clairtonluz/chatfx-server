/**
 * Created by clairton on 10/24/14.
 */
public class ChatServer {

    public static void main(String... args) {
        ServidorMultiThread server = new ServidorMultiThread(9000);
        new Thread(server).start();
//
//        try {
//            Thread.sleep(20 * 1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        System.out.println("Stopping Server");
//        server.stop();

    }
}
