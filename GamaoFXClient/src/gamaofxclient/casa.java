package gamaofxclient;

import java.io.Serializable;
import java.util.ArrayList;

public class casa implements Serializable {
    ArrayList<peca> pecas;
    final int id;
    int posX, posY;
    String cor;
    boolean jogavel;

    /**
     * Construtor
     *
     * @param id numero associado á casa, representa a posição no array
     * @param posX posição X
     * @param posY posição Y
     * @param cor String da cor
     */
    public casa(int id, int posX, int posY, String cor) {
        pecas = new ArrayList<>();
        this.id = id;
        this.posX = posX;
        this.posY = posY;
        this.cor = cor;
        jogavel = true;

    }

    /**
     * Adiciona peça branca no array pecas
     */
    public void addpecabranca() {
        pecas.add(new peca("jog1", posX + 25, posY + correcaoposy()));
    }

    /**
     * Adiciona peça preta no array pecas
     */
    public void addpecapreta() {
        pecas.add(new peca("jog2", posX + 25, posY + correcaoposy()));
    }

    /**
     * Adiciona peça em vazio
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

    /**
     * Verifica se o array de peças está vazio
     *
     * @return true se estiver vazio
     */
    public boolean vazio() {
        return pecas.isEmpty();
    }

    /**
     * Define se é jogavel
     *
     * @param jogador jogador a ser verificado
     * @param jogavel boolean que identifica se é jogavel
     */
    public void setjogavel(String jogador, boolean jogavel) {
        if ("jog1".equals(jogador)) {
            this.jogavel = jogavel;

        }
        if ("jog2".equals(jogador)) {
            this.jogavel = !jogavel;

        }
    }
}
