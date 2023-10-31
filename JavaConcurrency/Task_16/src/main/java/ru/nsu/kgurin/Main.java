package ru.nsu.kgurin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter URL: ");
            URL url = new URL(scanner.nextLine());

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            int lineCount = 0;

            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                while (((line = reader.readLine()) != null)) {
                    if (lineCount % 25 == 0 && lineCount != 0) {
                        System.out.println("Press Enter to continue and 'q' for exit...");
                        if (System.in.read() == 'q') {
                            System.out.println("Exit");
                            System.exit(0);
                        }
                    }
                    System.out.println((lineCount + 1) + ": " + line);
                    lineCount++;
                }
                reader.close();
            } else {
                System.out.println("Failed to retrieve data from the server.");
            }
            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
