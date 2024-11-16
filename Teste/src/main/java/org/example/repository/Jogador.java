package org.example.repository;

import java.util.List;

public class Jogador {
    private int id;
    private List<Cartas> mao;
    private Integer pontuacao;  // Partida atual
    private Integer time;
    private boolean vez;
    private int pontosTotais; // Pontos acumulados de várias partidas
    private int rodadasGanhas; // Rodadas ganhas na partida atual

    public Jogador(int id) {
        this.id = id;
        this.pontuacao = 0;
        this.pontosTotais = 0;
        this.rodadasGanhas = 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Cartas> getMao() {
        return mao;
    }

    public void setMao(List<Cartas> mao) {
        this.mao = mao;
    }

    public Integer getPontuacao() {
        return pontuacao;
    }

    public void setPontuacao(Integer pontuacao) {
        this.pontuacao = pontuacao;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public boolean isVez() {
        return vez;
    }

    public void setVez(boolean vez) {
        this.vez = vez;
    }

    public int getPontosTotais() {
        return pontosTotais;
    }

    public void setPontosTotais(int pontosTotais) {
        this.pontosTotais = pontosTotais;
    }

    public int getRodadasGanhas() {
        return rodadasGanhas;
    }

    public void setRodadasGanhas(int rodadasGanhas) {
        this.rodadasGanhas = rodadasGanhas;
    }

    // Adiciona uma nova mão de cartas ao jogador
    public void receberCartas(List<Cartas> cartas) {
        this.mao = cartas;
    }

    // Exibe as cartas na mão do jogador
    public void mostrarMao() {
        System.out.println("Mão do jogador " + id + ": " + mao);
    }

    // Incrementa a contagem de rodadas ganhas
    public void ganharRodada() {
        this.rodadasGanhas++;
    }

    // Reseta as rodadas ganhas, usado no início de uma nova partida
    public void resetarRodadasGanhas() {
        this.rodadasGanhas = 0;
    }

    // Atualiza a pontuação total do jogador com base nas rodadas ganhas
    public void atualizarPontuacaoPartida() {
        if (rodadasGanhas >= 2) {
            this.pontosTotais += 1; // Considerando que vencer uma partida dá 1 ponto ao jogador
            System.out.println("Jogador " + id + " venceu a partida! Pontuação total: " + pontosTotais);
        }
    }

    // Reseta as pontuações para uma nova partida
    public void resetarPontuacao() {
        this.pontuacao = 0;
        this.rodadasGanhas = 0;
    }
}
