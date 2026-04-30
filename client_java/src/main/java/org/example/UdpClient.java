package org.example;

import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class UdpClient {
    public static void main(String[] args) {
        String host = "100.65.183.62";
        int port = 12345;

        try (
                DatagramSocket socket = new DatagramSocket();
                Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8)
        ) {
            InetAddress serverAddress = InetAddress.getByName(host);

            System.out.println("Client UDP pornit. Scrie mesaje sau 'exit'.");

            while (true) {
                System.out.print("Client: ");
                String mesaj = scanner.nextLine();

                byte[] bufferTrimitere = mesaj.getBytes(StandardCharsets.UTF_8);

                DatagramPacket packetTrimitere = new DatagramPacket(
                        bufferTrimitere,
                        bufferTrimitere.length,
                        serverAddress,
                        port
                );

                socket.send(packetTrimitere);

                if (mesaj.equalsIgnoreCase("exit")) {
                    System.out.println("Client UDP închis.");
                    break;
                }

                byte[] bufferPrimire = new byte[1024];
                DatagramPacket packetPrimire = new DatagramPacket(
                        bufferPrimire,
                        bufferPrimire.length
                );

                socket.receive(packetPrimire);

                String raspuns = new String(
                        packetPrimire.getData(),
                        0,
                        packetPrimire.getLength(),
                        StandardCharsets.UTF_8
                );

                System.out.println("Server: " + raspuns);
            }

        } catch (Exception e) {
            System.out.println("Eroare UDP: " + e.getMessage());
        }
    }
}