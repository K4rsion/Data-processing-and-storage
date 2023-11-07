package ru.nsu.kgurin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicBoolean;

public class Server {
    public static void main(String[] args) throws IOException {
        int port = 4040;
        AtomicBoolean isClose = new AtomicBoolean(false);
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("Server is listening on port " + port + "...");
        Socket clientSocket = serverSocket.accept();
        Scanner scanner = new Scanner(System.in);
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        System.out.println("Connected to the client. Start typing messages:");

        CompletableFuture.runAsync(() -> {
            try {
                String message;
                while (!isClose.get()) {
                    message = in.readLine();
                    if (message == null || message.equals("close")) {
                        clientSocket.close();
                        serverSocket.close();
                        isClose.set(true);
                        System.out.println("Client disconnected. Press Enter to disconnect");
                        break;
                    }
                    System.out.println(message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        while (!isClose.get()) {
            String message = scanner.nextLine();
            if (message == null || message.equals("close")) {
                clientSocket.close();
                serverSocket.close();
                isClose.set(true);
                break;
            }
            out.println(message);
        }
    }
}
