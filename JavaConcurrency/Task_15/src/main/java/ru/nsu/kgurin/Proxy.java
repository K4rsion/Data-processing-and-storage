package ru.nsu.kgurin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicBoolean;

public class Proxy {

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("Type proxy port: ");
            int proxyPort = scanner.nextInt();
            System.out.print("Type server port: ");
            int serverPort = scanner.nextInt();

            System.out.println("Proxy is listening on port " + proxyPort + "...");
            ServerSocket proxySocket = new ServerSocket(proxyPort);

            Socket clientSocket = proxySocket.accept();

            CompletableFuture.runAsync(() -> handleClient(clientSocket, serverPort))
                    .thenRun(() -> {
                        try {
                            proxySocket.close();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });
        }
    }

    private static void handleClient(Socket clientSocket, int serverPort) {
        try {

            AtomicBoolean isClose = new AtomicBoolean(false);
            Socket serverSocket = new Socket("localhost", serverPort);

            CompletableFuture<Void> clientToServer = CompletableFuture.runAsync(() -> {
                try {
                    BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    PrintWriter out = new PrintWriter(serverSocket.getOutputStream(), true);
                    String message;
                    while (!isClose.get()) {
                        try {
                            message = in.readLine();
                            if (message == null || message.equals("close")) {
                                serverSocket.close();
                                clientSocket.close();
                                isClose.set(true);
                            }
                            out.println(message);
                        } catch (SocketException ex) {
                            System.out.println("Socket closed");
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            CompletableFuture<Void> serverToClient = CompletableFuture.runAsync(() -> {
                try {
                    BufferedReader in = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
                    PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                    String message;
                    while (!isClose.get()) {
                        try {
                            message = in.readLine();
                            if (message == null || message.equals("close")) {
                                serverSocket.close();
                                clientSocket.close();
                                isClose.set(true);
                            }
                            out.println(message);
                        } catch (SocketException ex) {
                            System.out.println("Socket closed");
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            CompletableFuture.allOf(clientToServer, serverToClient).join();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
