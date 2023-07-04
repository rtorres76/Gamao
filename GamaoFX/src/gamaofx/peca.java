package gamaofx;

import java.io.Serializable;

public class peca implements Serializable {

    String jogador;

    int posicaoX, posicaoY;

/**
    //-- CONSTRUTOR
    //--  @param jogador jogador ao qual a peça pertence
    //--  @param posX posição X da peça
    //--  @param posY posição Y da peça
 */

public peca(String jogador, int posX, int posY) {

    this.posicaoX = posX;
    this.posicaoY = posY;
    this.jogador = jogador;

    }
}