import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class ChatServer {
    private static final int PORT = 1234; // порт сервера
    private static Set<PrintWriter> clientWriters = Collections.synchronizedSet(new HashSet<>());

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(PORT);
        System.out.println("Сервер запущен на порту " + PORT);

        while (true) {
            Socket clientSocket = serverSocket.accept();
            System.out.println("Новый клиент подключился");
            new ClientHandler(clientSocket).start();
        }
    }


    public static void broadcast(String message) {
        synchronized (clientWriters) {
            for (PrintWriter writer : clientWriters) {
                writer.println(message);
            }
        }
    }


    public static void addClient(PrintWriter writer) {
        clientWriters.add(writer);
    }

    public static void removeClient(PrintWriter writer) {
        clientWriters.remove(writer);
    }
}