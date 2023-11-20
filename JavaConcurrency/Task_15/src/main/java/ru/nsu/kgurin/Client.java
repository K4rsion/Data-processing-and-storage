package ru.nsu.kgurin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicBoolean;

public class Client {
    public static void main(String[] args) throws IOException {
        int port = 4000;
        AtomicBoolean isClose = new AtomicBoolean(false);
        Socket socket = new Socket("localhost", port);
        Scanner scanner = new Scanner(System.in);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        System.out.println("Connected to the server. Start typing messages:");

        CompletableFuture.runAsync(() -> {
            try {
                String message;
                while (!isClose.get()) {
                    message = in.readLine();
                    if (message == null || message.equals("close")) {
                        socket.close();
                        isClose.set(true);
                        System.out.println("Server disconnected. Press Enter to disconnect");
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
                socket.close();
                isClose.set(true);
                break;
            }
            out.println(message);
        }

    }
}
