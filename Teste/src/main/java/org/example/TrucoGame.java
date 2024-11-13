package org.example;

import org.example.repository.Cartas;
import org.example.repository.Jogador;

import java.util.*;

public class TrucoGame {
    private List<Jogador> jogadores;  // Lista de jogadores no jogo
    private List<Cartas> baralho;  // O baralho que será usado para distribuir cartas
    private int turnoAtual;  // O índice do jogador que está jogando
    private int valorRodada;  // O valor atual da rodada

    // Construtor da classe TrucoGame
    public TrucoGame() {
        this.jogadores = new ArrayList<>();
        this.baralho = new ArrayList<>();
        this.turnoAtual = 0;  // Começa com o jogador 1
        this.valorRodada = 1;  // Valor inicial da rodada
        inicializarBaralho();  // Inicializa o baralho
    }

    // Método para adicionar jogadores ao jogo
    public void adicionarJogador(Jogador jogador) {
        jogadores.add(jogador);
    }

    // Método para inicializar o baralho
    private void inicializarBaralho() {
        // Aqui você pode adicionar cartas ao baralho (baseado nas cartas existentes)
        String[] naipes = {"Ouros", "Espadas", "Copas", "Paus"};
        String[] valores = {"4", "5", "6", "7", "Q", "J", "K", "A", "2", "3"};

        for (String naipe : naipes) {
            for (String valor : valores) {
                baralho.add(new Cartas(valor, naipe));
            }
        }
    }

    // Método para embaralhar as cartas
    public void embaralharBaralho() {
        Collections.shuffle(baralho);
    }

    // Método para distribuir cartas para os jogadores
    public void distribuirCartas() {
        for (Jogador jogador : jogadores) {
            List<Cartas> mao = new ArrayList<>();
            for (int i = 0; i < 3; i++) {
                mao.add(baralho.remove(0));  // Distribui 3 cartas para cada jogador
            }
            jogador.receberCartas(mao);  // Atualiza a mão do jogador
        }
    }

    // Método para alternar o turno entre os jogadores
    public void alternarTurno() {
        turnoAtual = (turnoAtual + 1) % jogadores.size();  // Alterna entre 0 e 1 para dois jogadores, por exemplo
    }

    // Método para chamar Truco
    public void chamarTruco(Jogador jogador) {
        System.out.println("Jogador " + jogador.getId() + " chamou truco!");
        valorRodada *= 3;  // Aumenta o valor da rodada (isso é um exemplo simples)
    }

    // Método para jogar uma carta
    public void jogarCarta(Jogador jogador, Cartas carta) {
        System.out.println("Jogador " + jogador.getId() + " jogou a carta: " + carta);
    }

    // Exibe informações do jogo
    public void exibirInformacoes() {
        System.out.println("Jogo iniciado. Turno atual: Jogador " + (turnoAtual + 1));
        System.out.println("Valor da rodada: " + valorRodada);
    }

    // Getters e Setters
    public int getTurnoAtual() {
        return turnoAtual;
    }

    public void setTurnoAtual(int turnoAtual) {
        this.turnoAtual = turnoAtual;
    }

    public int getValorRodada() {
        return valorRodada;
    }

    public void setValorRodada(int valorRodada) {
        this.valorRodada = valorRodada;
    }

    public List<Jogador> getJogadores() {
        return jogadores;
    }

    public List<Cartas> getBaralho() {
        return baralho;
    }
}
