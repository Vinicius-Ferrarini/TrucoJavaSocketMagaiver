package org.example.repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Baralho {
    private List<Cartas> cartas;

    public Baralho(){
        cartas =new ArrayList<>();
        String[] naipes  = {"Ouros" , "Espadas" , "Copas" , "Paus"};
        String[] valores = {"4","5","6","7","Q","J","K","A","2","3"};

        //cria baralho
        for(String naipe : naipes){
            for (String valor : valores){
                cartas.add(new Cartas(naipe,valor));
            }
        }
    }

    public void embaralhar() {
        Collections.shuffle(cartas);
    }

    public List<Cartas> distribuirCartas(int quantidade){
        List<Cartas> mao = new ArrayList<>();
        for (int i = 0; i < quantidade; i++) {
            if (!cartas.isEmpty()){
                mao.add(cartas.remove(0));
            }
        }
        return mao;
    }

}
