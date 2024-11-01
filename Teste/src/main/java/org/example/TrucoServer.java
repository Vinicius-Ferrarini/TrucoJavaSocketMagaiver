package org.example;

import java.io.*;
import java.net.*;
import java.util.HashMap;

public class TrucoServer {
    private static final int PORT = 12345;
    private static HashMap<Integer, PrintWriter> clientes = new HashMap<>();

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(PORT);
        System.out.println("Servidor de Truco iniciado na porta " + PORT);

        int clientCount = 0;
        while (clientCount < 2) {
            Socket clientSocket = serverSocket.accept();
            clientCount++;
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            clientes.put(clientCount, out);
            System.out.println("Cliente " + clientCount + " conectado.");

            new Thread(new ClientHandler(clientCount, in)).start();
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
                    System.out.println("Jogada do Cliente " + clientId + ": " + input);
                    validarJogada(clientId, input);
                }
            } catch (IOException e) {
                System.err.println("Erro na comunicação com o cliente " + clientId);
            }
        }

        private void validarJogada(int clientId, String jogada) {
            // Lógica para validar a jogada do truco
            // Aqui você pode implementar as regras e enviar as respostas apropriadas aos clientes

            // Exemplo de resposta para os clientes
            for (int id : clientes.keySet()) {
                if (id != clientId) {
                    clientes.get(id).println("Cliente " + clientId + " jogou: " + jogada);
                }
            }
        }
    }
}
