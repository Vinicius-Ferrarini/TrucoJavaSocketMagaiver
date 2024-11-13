package org.example.repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Baralho {
    private List<Cartas> cartas;

    public Baralho() {
        inicializarBaralho();
    }

    // Inicializa ou reinicia o baralho com todas as cartas
    private void inicializarBaralho() {
        cartas = new ArrayList<>();
        String[] naipes = {"Ouros", "Espadas", "Copas", "Paus"};
        String[] valores = {"4", "5", "6", "7", "Q", "J", "K", "A", "2", "3"};

        for (String valor : valores) {
            for (String naipe : naipes) {
                cartas.add(new Cartas(valor, naipe));
            }
        }
    }

    // Metodo para embaralhar o baralho
    public void embaralhar() {
        Collections.shuffle(cartas);
    }

    // Metodo para distribuir uma quantidade específica de cartas
    public List<Cartas> distribuirCartas(int quantidade) {
        List<Cartas> mao = new ArrayList<>();
        for (int i = 0; i < quantidade; i++) {
            if (!cartas.isEmpty()) {
                mao.add(cartas.remove(0));
            }
        }
        return mao;
    }

    // Metodo para resetar o baralho para uma nova partida
    public void resetarBaralho() {
        inicializarBaralho();
        embaralhar();
    }

    // Metodo para remover uma carta específica do baralho (caso seja necessário)
    public void removerCarta(Cartas carta) {
        cartas.remove(carta);
    }

    // Metodo para verificar quantas cartas restam no baralho
    public int obterTamanho() {
        return cartas.size();
    }

    // Metodo para obter o estado atual do baralho (para depuração ou visualização)
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Cartas no baralho:\n");
        for (Cartas carta : cartas) {
            sb.append(carta).append("\n");
        }
        return sb.toString();
    }
}
