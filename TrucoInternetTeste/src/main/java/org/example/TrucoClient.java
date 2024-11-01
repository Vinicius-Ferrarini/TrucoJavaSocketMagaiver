package org.example;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class TrucoClient {
    private static final String HOST = "localhost";
    private static final int PORT = 12345;

    public static void main(String[] args) {
        try (Socket socket = new Socket(HOST, PORT);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
             Scanner scanner = new Scanner(System.in)) {

            System.out.println("Conectado ao servidor. Digite suas jogadas (ou 'sair' para encerrar):");

            while (true) {
                // Ler a jogada do usuário
                System.out.print("Sua jogada: ");
                String jogada = scanner.nextLine();

                // Envia a jogada ao servidor
                out.writeObject(jogada);
                out.flush();

                // Verifica se o jogador quer encerrar
                if (jogada.equalsIgnoreCase("sair")) {
                    System.out.println("Encerrando o jogo...");
                    break;
                }

                // Recebe o estado atualizado do jogo do servidor
                Object estadoJogo = in.readObject();
                System.out.println("Estado do jogo: " + estadoJogo);
            }

        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erro de comunicação: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
