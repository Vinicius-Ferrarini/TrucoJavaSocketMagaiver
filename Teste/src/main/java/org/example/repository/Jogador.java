package org.example.repository;

import java.util.List;

public class Jogador {
    private int id;
    private List<Cartas> mao;
    private Integer pontuacao;  //partida atual
    private Integer time;
    private boolean vez;
    private int pontosTotais;

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

    public void receberCartas(List<Cartas> cartas){
        this.mao = cartas;
    }

    public void mostrarMao(){
        System.out.println("Mao do jogador: " + mao);
    }
}
