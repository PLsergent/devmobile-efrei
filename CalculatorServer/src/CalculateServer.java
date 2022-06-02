import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class CalculateServer implements Runnable {
    private Socket sock;

    public CalculateServer(Socket s) {
        sock = s;
    }

    @Override
    public void run() {
        try {
            DataInputStream dis = new DataInputStream(sock.getInputStream());
            DataOutputStream dos = new DataOutputStream(sock.getOutputStream());

            // read operation text
            String operation = dis.readUTF();

            String result = CalculateFactory.calculateOperation(operation);

            // send back result
            dos.writeUTF(result);

            dis.close();
            dos.close();
            sock.close();

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {

        // Example of a distant calculator
        ServerSocket ssock = new ServerSocket(9876);

        while (true) { // infinite loop
            Socket comm = ssock.accept();
            System.out.println("connection established");

            new Thread(new CalculateServer(comm)).start();
        }
    }
}

