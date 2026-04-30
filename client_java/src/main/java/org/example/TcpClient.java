package org.example;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class TcpClient {
    public static void main(String[] args) {
        String host = "100.65.183.62";
        int port = 12345;

        try (
                Socket socket = new Socket(host, port);
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8)
                );
                BufferedWriter out = new BufferedWriter(
                        new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8)
                );
                Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8)
        ) {
            System.out.println("Conectat la server TCP. Scrie mesaje sau 'exit'.");

            while (true) {
                System.out.print("Client: ");
                String mesaj = scanner.nextLine();

                out.write(mesaj);
                out.newLine();
                out.flush();

                if (mesaj.equalsIgnoreCase("exit")) {
                    System.out.println("Conexiune închisă.");
                    break;
                }

                String raspuns = in.readLine();
                if (raspuns == null) {
                    System.out.println("Serverul a închis conexiunea.");
                    break;
                }

                System.out.println("Server: " + raspuns);
            }

        } catch (IOException e) {
            System.out.println("Eroare TCP: " + e.getMessage());
        }
    }
}