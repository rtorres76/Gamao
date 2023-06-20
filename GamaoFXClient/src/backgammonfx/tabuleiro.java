/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backgammonfx;

import java.util.ArrayList;

/**
 * <p>
 * Tabuleiro que contem array de casas a ser enviado para o servidor
 * </p>
 *
 * @author Joao_Pires
 * @version 1.0
 * @since 22-06-2021
 */
public class tabuleiro {

    ArrayList<casa> casas;
    dado dado1, dado2;

/**
 * Construtor
 * inicia casas dado1 e dado2
 */
    public tabuleiro() {
        casas = new ArrayList<>();
        dado1 = new dado();
        dado2 = new dado();
    }
    
    /**
     *<p>Testa se todas as peças do jogador encontram-se no ultimo quadrante</p>
     * @param jogador o jogador ao qual vai ser testado
     * @return true se todas as peças se encontrarem no ultimo parametro
     */
    public boolean fimdejogo(String jogador) {      
        int total = 0;
        //percorre casas 24 a 19 se for o jogador 1
        if ("jog1".equals(jogador)) {
            for (int i = 24; i >= 19; i--) {
                //se não está vazio
                if (!casas.get(i).vazio()) {
                    //se as peças pertencem ao jogador 1
                    if (casas.get(i).pecas.get(0).jogador.compareTo(jogador) == 0) {
                        total += casas.get(i).pecas.size();
                                System.out.println("TOTAL " + total);
                    }
                }
            }
        }
        //percorre casas 0 a 6 se for o jogador 2
        else if ("jog2".equals(jogador)) {
            for (int i = 1; i <= 6; i++) {
                //se está vazio
                if (!casas.get(i).vazio()) {
                    //se as peças pertencem ao jogador 2
                    if (casas.get(i).pecas.get(0).jogador.compareTo(jogador) == 0) {
                        total += casas.get(i).pecas.size();
                                System.out.println("TOTAL " + total);
                    }
                }
            }
        }
        
        return total >= 15;
    }



}
