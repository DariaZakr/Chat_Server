import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

class ClientHandler extends Thread {
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);


            ChatServer.addClient(out);


            String message;
            while ((message = in.readLine()) != null) {
                System.out.println("Получено сообщение: " + message);
                ChatServer.broadcast(message, out); // Рассылаем всем, кроме отправителя
            }
        } catch (IOException e) {
            System.out.println("Клиент отключился.");
        } finally {

            ChatServer.removeClient(out);
            try {
                socket.close();
            } catch (IOException e) {}
        }
    }
}
