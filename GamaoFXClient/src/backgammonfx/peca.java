/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backgammonfx;

import java.io.Serializable;

/**
 * <p>
 * Classe peça define as posições e a que jogador pretencem
 * </p>
 *
 * @author Joao_Pires
 * @version 1.0
 * @since 22-06-2021
 */
public class peca implements Serializable{

    String jogador;
    int posX, posY;

    /**
     *Construtor
     * @param jogador jogador ao qual a peça pertence
     * @param posX posição X da peça
     * @param posY posição Y da peça
     */
    public peca(String jogador, int posX, int posY) {
        
        this.posX = posX;
        this.posY = posY;
        this.jogador = jogador;

    }
}
