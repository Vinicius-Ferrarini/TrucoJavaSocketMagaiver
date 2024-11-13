package org.example;

import org.example.repository.Baralho;
import org.example.repository.Cartas;

import javax.swing.*;
import java.io.*;
import java.net.*;
import java.util.*;

public class TrucoServer {
    private static final int PORT = 12345;
    private static Map<Integer, PrintWriter> clientes = new HashMap<>();
    private static Map<Integer, List<Cartas>> maos = new HashMap<>();
    private static Baralho baralho = new Baralho();
    private static int currentPlayerTurn = 1;
    private static int clientCount = 0;
    private static int totalPlayers = 2; // Padrão inicial de 2 jogadores

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);

            // Caixa de diálogo para escolher o número de jogadores
            String[] options = {"2 jogadores", "4 jogadores"};
            int choice = JOptionPane.showOptionDialog(null, "Escolha o número de jogadores:", "Configuração de Jogo",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

            // Configura o total de jogadores com base na escolha
            totalPlayers = (choice == 1) ? 4 : 2;

            JOptionPane.showMessageDialog(null, "Servidor de Truco iniciado para " + totalPlayers + " jogadores. Aguardando conexões...");

            // Loop para aguardar a conexão de todos os jogadores
            while (clientCount < totalPlayers) {
                Socket clientSocket = serverSocket.accept();
                clientCount++;
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                clientes.put(clientCount, out);
                JOptionPane.showMessageDialog(null, "Cliente " + clientCount + " conectado.");

                // Inicia uma nova thread para gerenciar a conexão de cada cliente
                new Thread(new ClientHandler(clientCount, in)).start();
            }

            // Distribui cartas quando todos os jogadores estão conectados
            distribuirCartas();
            notificarVezDoJogador(currentPlayerTurn);

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Erro ao iniciar o servidor: " + e.getMessage());
        }
    }

    private static void distribuirCartas() {
        baralho.embaralhar();

        // Envia uma mão de cartas para cada cliente
        for (int id : clientes.keySet()) {
            List<Cartas> mao = baralho.distribuirCartas(3);
            maos.put(id, mao);
            StringBuilder maoStr = new StringBuilder();

            for (Cartas carta : mao) {
                maoStr.append(carta).append(", ");
            }

            // Envia as cartas para o cliente específico
            clientes.get(id).println("Suas cartas: " + maoStr.toString().trim());
            clientes.get(id).println("Aguarde o outro jogador.");
        }
    }

    private static void notificarVezDoJogador(int playerId) {
        for (int id : clientes.keySet()) {
            if (id == playerId) {
                clientes.get(id).println("Sua vez de jogar.");
            } else {
                clientes.get(id).println("Aguarde o outro jogador.");
            }
        }
    }

    private static class ClientHandler implements Runnable {
        private int clientId;
        private BufferedReader in;

        public ClientHandler(int clientId, BufferedReader in) {
            this.clientId = clientId;
            this.in = in;
        }

        @Override
        public void run() {
            try {
                String input;
                while ((input = in.readLine()) != null) {
                    if (clientId == currentPlayerTurn) {
                        validarJogada(clientId, input);
                        alternarTurno();
                    } else {
                        clientes.get(clientId).println("Aguarde sua vez.");
                    }
                }
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Erro na comunicação com o cliente " + clientId);
            }
        }

        private void validarJogada(int clientId, String jogada) {
            for (int id : clientes.keySet()) {
                if (id != clientId) {
                    clientes.get(id).println("Cliente " + clientId + " jogou: " + jogada);
                }
            }
        }

        private void alternarTurno() {
            // Alterna o turno entre os jogadores
            currentPlayerTurn = (currentPlayerTurn % totalPlayers) + 1;
            notificarVezDoJogador(currentPlayerTurn);
        }
    }
}
