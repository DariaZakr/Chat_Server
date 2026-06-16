import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

class ClientHandler extends Thread {
    private final Socket socket;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        PrintWriter out = null;

        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            out.println("Вы подключились к чату.");
            ChatServer.addClient(out);

            String clientAddress = socket.getInetAddress().toString();
            ChatServer.broadcast("К чату подключился новый клиент: " + clientAddress, out);

            String message;
            while ((message = in.readLine()) != null) {
                System.out.println("Получено сообщение: " + message);
                ChatServer.broadcast(message, out);
            }
        } catch (IOException e) {
            System.out.println("Ошибка при работе с клиентом: " + e.getMessage());
        } finally {
            closeConnection(out);
        }
    }

    private void closeConnection(PrintWriter out) {
        if (out != null) {
            ChatServer.removeClient(out);
            ChatServer.broadcast("Один из клиентов отключился.", out);
        }

        try {
            socket.close();
            System.out.println("Клиент отключился.");
        } catch (IOException e) {
            System.out.println("Не удалось закрыть соединение: " + e.getMessage());
        }
    }
}
