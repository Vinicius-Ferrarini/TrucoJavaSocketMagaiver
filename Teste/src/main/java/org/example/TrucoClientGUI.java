package org.example;

import org.example.repository.Cartas;

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
    private JPanel buttonPanel;

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
                        if (serverMessage.equals("Sua vez de jogar.")) {
                            habilitarCartas(true);
                        } else if (serverMessage.equals("Aguarde o outro jogador.")) {
                            habilitarCartas(false);
                        } else if (serverMessage.startsWith("Suas cartas: ")) {
                            // Extrai as cartas do servidor e exibe
                            String cartasString = serverMessage.substring(13);
                            String[] cartas = cartasString.split(", ");
                            exibirCartas(cartas);
                        } else {
                            messageArea.append(serverMessage + "\n");
                        }
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

        messageArea = new JTextArea();
        messageArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(messageArea);
        frame.add(scrollPane, BorderLayout.CENTER);

        buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout()); // Garantir que os botões usem FlowLayout
        frame.add(buttonPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    private void exibirCartas(String[] cartas) {
        // Limpa qualquer carta anterior
        buttonPanel.removeAll();

        // Adiciona os botões das cartas recebidas
        for (String cartaStr : cartas) {
            // Divide a string de carta em valor e naipe
            String[] cartaInfo = cartaStr.split(" de ");
            if (cartaInfo.length == 2) {
                Cartas carta = new Cartas(cartaInfo[0], cartaInfo[1]);

                // Cria um botão para a carta
                JButton cartaButton = new JButton(carta.toString());
                cartaButton.addActionListener(new JogadaActionListener(carta, cartaButton));

                // Adiciona o botão ao painel
                buttonPanel.add(cartaButton);
            }
        }

        // Atualiza o painel e o layout
        buttonPanel.revalidate();  // Garantir que o layout do painel seja reavaliado
        buttonPanel.repaint();     // Redesenha o painel para refletir as mudanças

        // Desabilita os botões até a vez do jogador
        habilitarCartas(false);
    }

    private void habilitarCartas(boolean habilitar) {
        for (Component component : buttonPanel.getComponents()) {
            component.setEnabled(habilitar);
        }
    }

    private class JogadaActionListener implements ActionListener {
        private Cartas jogada;
        private JButton cartaButton;

        public JogadaActionListener(Cartas jogada, JButton cartaButton) {
            this.jogada = jogada;
            this.cartaButton = cartaButton;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            out.println(playerName + " jogou " + jogada);
            messageArea.append("Você jogou: " + jogada + "\n");

            // Remove o botão da carta jogada
            buttonPanel.remove(cartaButton);
            buttonPanel.revalidate();
            buttonPanel.repaint();

            // Desabilita as cartas após jogar
            habilitarCartas(false);
        }
    }

    public static void main(String[] args) {
        String playerName = JOptionPane.showInputDialog("Digite seu nome:");
        if (playerName != null && !playerName.trim().isEmpty()) {
            new TrucoClientGUI(playerName);
        }
    }
}
