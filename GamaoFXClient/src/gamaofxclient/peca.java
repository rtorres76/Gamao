package gamaofxclient;

import java.io.Serializable;
public class peca implements Serializable{
    String jogador;
    int posicaoX, posicaoY;

    /**
     *Construtor
     * @param jogador jogador ao qual a peça pertence
     * @param posicaoX posição X da peça
     * @param posicaoY posição Y da peça
     */
    public peca(String jogador, int posicaoX, int posicaoY) {

        this.posicaoX = posicaoX;
        this.posicaoY = posicaoY;
        this.jogador = jogador;

    }
}
