package org.example;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;

public class TrucoServer {
    private static final int PORT = 12345;
    private static ConcurrentHashMap<String, Integer> gameState = new ConcurrentHashMap<>(); // Estado do jogo

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Servidor iniciado na porta " + PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Novo jogador conectado: " + clientSocket.getInetAddress());
                new Thread(new ClientHandler(clientSocket)).start();
            }

        } catch (IOException e) {
            System.err.println("Erro ao iniciar o servidor: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Classe responsável por lidar com cada cliente conectado
    private static class ClientHandler implements Runnable {
        private Socket clientSocket;

        public ClientHandler(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }

        @Override
        public void run() {
            try (ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());
                 ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream())) {

                while (true) {
                    // Recebe a jogada do cliente
                    String jogada = (String) in.readObject();
                    if (jogada == null || jogada.equalsIgnoreCase("sair")) {
                        System.out.println("Jogador desconectado: " + clientSocket.getInetAddress());
                        break;
                    }

                    // Processa a jogada e atualiza o estado do jogo
                    processarJogada(jogada);

                    // Envia o estado atualizado do jogo para o cliente
                    out.writeObject(gameState);
                    out.flush();
                }

            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Erro na comunicação com o cliente: " + e.getMessage());
            }
        }

        private void processarJogada(String jogada) {
            // Processa a jogada e atualiza o estado do jogo (exemplo: contagem de pontos)
            System.out.println("Processando jogada: " + jogada);
            gameState.put("pontos", gameState.getOrDefault("pontos", 0) + 1); // Exemplo de atualização
        }
    }
}
