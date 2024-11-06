package org.example;

import org.example.repository.Baralho;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;

public class TrucoClientGUI {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 12345;

    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private JTextArea messageArea;
    private String playerName;


    public TrucoClientGUI(String playerName) {
        this.playerName = playerName;
        setupConnection();
        setupGUI();
    }

    private void setupConnection() {
        try {
            socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // Thread para ler mensagens do servidor e exibir na área de mensagens
            new Thread(() -> {
                try {
                    String serverMessage;
                    while ((serverMessage = in.readLine()) != null) {
                        messageArea.append(serverMessage + "\n");
                    }
                } catch (IOException e) {
                    messageArea.append("Erro ao ler mensagens do servidor.\n");
                }
            }).start();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Erro ao conectar com o servidor.");
        }
    }

    private void setupGUI() {
        JFrame frame = new JFrame("Truco - " + playerName);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new BorderLayout());

        // Área de mensagens
        messageArea = new JTextArea();
        messageArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(messageArea);
        frame.add(scrollPane, BorderLayout.CENTER);

        // Painel de botões para jogar cartas
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        frame.add(buttonPanel, BorderLayout.SOUTH);

        frame.setVisible(true);

        // Inicia a thread para ouvir mensagens do servidor
        new Thread(() -> {
            try {
                String serverMessage;
                while ((serverMessage = in.readLine()) != null) {
                    if (serverMessage.startsWith("Suas cartas: ")) {
                        // Extrai as cartas do servidor
                        String cartasString = serverMessage.substring(13);
                        String[] cartas = cartasString.split(", ");
                        exibirCartas(cartas, buttonPanel);
                    } else {
                        messageArea.append(serverMessage + "\n");
                    }
                }
            } catch (IOException e) {
                messageArea.append("Erro ao ler mensagens do servidor.\n");
            }
        }).start();
    }

    // Metodo que exibe as cartas na interface
    private void exibirCartas(String[] cartas, JPanel buttonPanel) {
        buttonPanel.removeAll();  // Remove os botões antigos

        // Cria um botão para cada carta
        for (String carta : cartas) {
            JButton cartaButton = new JButton(carta);
            cartaButton.addActionListener(new JogadaActionListener(carta));
            buttonPanel.add(cartaButton);
        }

        // Atualiza a interface gráfica
        buttonPanel.revalidate();
        buttonPanel.repaint();
    }



    private class JogadaActionListener implements ActionListener {
        private String jogada;

        public JogadaActionListener(String jogada) {
            this.jogada = jogada;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            out.println(playerName + " jogou " + jogada);
            messageArea.append("Você jogou: " + jogada + "\n");
        }
    }

    public static void main(String[] args) {
        String playerName = JOptionPane.showInputDialog("Digite seu nome:");
        if (playerName != null && !playerName.trim().isEmpty()) {
            new TrucoClientGUI(playerName);
        }
    }
}
