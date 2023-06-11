package com.example.gamao;

import java.io.Serializable;
import java.util.ArrayList;

public class jogador implements Serializable {

    ArrayList<peca> pecas;
    int posX;
    int posY;
    int id;
    String jogador;



    /**
     //--Construtor--//
     @param id id da casa do jogador
     @param jogador nome do jogador
     @param posX posição X da casa do jogador
     @param posY posição Y da casa do jogador
     */

    public jogador(int id, String jogador, int posX, int posY) {
        pecas = new ArrayList<>();
        this.id = id;
        this.jogador = jogador;
        this.posX = posX;
        this.posY = posY;
    }

    /**
     //--Adiciona peça branca á casa do jogador
     */
    public void addpecabranca() {

        //--

    }

    /**
     //--Adiciona peça preta á casa do jogador
     */
    public void addpecapreta() {

        //--

    }

    /**
     //--Adiciona peça em branco
     */
    public void addpecablank() {


        //--

    }

    /**
     Corrige a posição da peca de acordo com a posição do array para não se
     sobreporem

     @return retorna a correção da posição
     */
    public int correcaoposy() {


        //--

        return 0;
    }

    /**
     //--Remove peça do array
     */
    public void rempeca() {

        //--

    }


}