package Screen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class Screen extends JFrame {
    JTextField num1;
    JTextField num2;
    JLabel title;
    JCheckBox jCheckBox;

    public Screen() {
        Font font = new Font("Arial", Font.BOLD, 40);
        setTitle("Aula 32");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        setLayout(null);

        // Botão Clique aqui
        JButton jButton = new JButton("Clique aqui");
        jButton.setBounds(150, 150, 500, 50);
        jButton.setFont(font);
        jButton.setForeground(Color.WHITE);
        jButton.setBackground(Color.BLACK);
        jButton.addActionListener(this::certo);
        add(jButton);

        // Botão Não clique
        JButton jButton2 = new JButton("Nao clique ");
        jButton2.setBounds(10, 10, 300, 80);
        jButton2.setFont(font);
        jButton2.setForeground(new Color(0, 255, 255));
        jButton2.setBackground(new Color(255, 0, 0));
        jButton2.addActionListener(this::errado);
        add(jButton2);

        // Campo de texto num1
        num1 = new JTextField();
        num1.setBounds(150, 100, 200, 50); // Ajuste da posição e tamanho
        num1.setFont(new Font("Arial", Font.ITALIC, 20));
        add(num1);

        // Campo de texto num2
        num2 = new JTextField();
        num2.setBounds(150, 200, 200, 50); // Ajuste da posição e tamanho
        num2.setFont(new Font("Arial", Font.ITALIC, 20));
        add(num2);

        // Título centralizado
        title = new JLabel("Title", SwingConstants.CENTER); // Alinha o texto ao centro do JLabel
        title.setFont(new Font("Arial", Font.BOLD, 25));
        title.setBounds((getWidth() - 400) / 2, 50, 400, 100); // Centraliza o JLabel na largura do JFrame
        add(title);


        // Label Numero 1
        JLabel jLabel = new JLabel("Numero 1:");
        jLabel.setBounds(50, 100, 100, 50); // Nova posição ajustada
        jLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        add(jLabel);

        // Label Numero 2
        JLabel jLabel2 = new JLabel("Numero 2:");
        jLabel2.setBounds(50, 200, 100, 50); // Nova posição ajustada
        jLabel2.setFont(new Font("Arial", Font.PLAIN, 20));
        add(jLabel2);

        //Check box
        jCheckBox = new JCheckBox("checar");
        jCheckBox.setBounds(10,250,150,150);
        jCheckBox.setSelected(true);//marca caixa
        jCheckBox.addActionListener(this::marca);
        add(jCheckBox);

        // Adicionando imagem para enfeite no canto superior direito
        String caminhoImagem = "C:\\Users\\SUPORTE\\IdeaProjects\\TrucoJavaSocketMagaiver\\untitled\\src\\main\\java\\Screen\\img\\img1.png";
        ImageIcon originalIcon = new ImageIcon(caminhoImagem);
        Image imagemRedimensionada = originalIcon.getImage().getScaledInstance(150, 100, Image.SCALE_SMOOTH);
        JLabel imagem1 = new JLabel(new ImageIcon(imagemRedimensionada));
        imagem1.setBounds(600, 20, 150, 100);
        add(imagem1);

        // Adicionando imagem para enfeite no canto inferior esquerdo
        JLabel imagem2 = new JLabel(new ImageIcon("\\img\\img2.png"));
        imagem2.setBounds(10, 350, 150, 100);
        add(imagem2);

        setVisible(true);
    }

    private void marca(ActionEvent actionEvent) {
        System.out.println(jCheckBox.isSelected());
    }

    private void errado(ActionEvent actionEvent) {
        JOptionPane.showMessageDialog(null, "Porque fez isso? ;-;", "Aviso", JOptionPane.WARNING_MESSAGE);
    }

    public void certo(ActionEvent e) {
        try {
            Integer numero1 = Integer.parseInt(num1.getText());
            Integer numero2 = Integer.parseInt(num2.getText());
            Integer soma = numero1 + numero2;

            title.setText("A soma é: " + soma);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Por favor, insira números válidos.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
