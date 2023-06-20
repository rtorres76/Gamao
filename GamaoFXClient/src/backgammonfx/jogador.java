/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backgammonfx;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * <p>
 * Classe jogador define os parametros de cada um dos jogadores
 * </p>
 *
 * @author Joao_Pires
 * @version 1.0
 * @since 22-06-2021
 */
public class jogador implements Serializable{

    ArrayList<peca> pecas;
    int posX;
    int posY;
    int id;
    String jogador;

    /**
     *Construtor
     * @param id id da casa do jogador
     * @param jogador nome do jogador
     * @param posX posição X da casa do jogador
     * @param posY posição Y da casa do jogador
     */
    public jogador(int id, String jogador, int posX, int posY) {
        pecas = new ArrayList<>();
        this.id = id;
        this.jogador = jogador;
        this.posX = posX;
        this.posY = posY;
    }  

    /**
     *Adiciona peça branca á casa do jogador
     */
    public void addpecabranca() {

        pecas.add(new peca("jog1", posX + 25, posY + correcaoposy()));
    }

    /**
     *Adiciona peça preta á casa do jogador
     */
    public void addpecapreta() {
        pecas.add(new peca("jog2", posX + 25, posY + correcaoposy()));
    }

    /**
     *Adiciona peça em branco
     */
    public void addpecablank() {

        pecas.add(new peca("", posX + 25, posY + correcaoposy()));
    }

    /**
     * Corrige a posição da peca de acordo com a posição do array para não se
     * sobreporem
     *
     * @return retorna a correção da posição
     */
    public int correcaoposy() {
        int posycorr = pecas.size() * 10;
        if (id > 12) {
            posycorr *= -1;
            posycorr += 160;
        } else {
            posycorr += 20;
        }
        return posycorr;
    }

    /**
     * remove peça do array
     */
    public void rempeca() {
        pecas.remove(pecas.size() - 1);
    }
}
