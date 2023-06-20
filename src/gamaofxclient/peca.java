package gamaofxclient;

import java.io.Serializable;
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
