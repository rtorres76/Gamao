package gamaofx;

import java.util.ArrayList;

//Tabuleiro que contem array de peças a ser enviado para o cliente
public class tabuleiro {
    ArrayList<casa> casas;
    dado dado1,dado2;

    //Construtor
    //Preenche as casas nas posições certas

    public tabuleiro() {
        casas = new ArrayList<>();
        dado1 = new dado();
        dado2 = new dado();
        for (int i = 0; i <= 25; i++) {
            if (i == 0){
                casas.add(new casa(i, 300, 0, ""));
                System.out.println(i);
            }
            else if (i <= 6) {
                casas.add(new casa(i, 650 - (i * 50), 0, "cima"));
                System.out.println(i);
            } else if (i > 6 && i <= 12) {
                casas.add(new casa(i, 650 - (i * 50) - 50, 0, "cima"));
                System.out.println(i);
            } else if (i > 12 && i <= 18) {
                casas.add(new casa(i, (i - 13) * 50, 220, "baixo"));
                System.out.println(i);
            } else if (i > 18 && i < 25) {
                casas.add(new casa(i, ((i - 13) * 50) + 50, 220, "baixo"));
                System.out.println(i);
            } else if (i == 25){
                casas.add(new casa(i, 300, 220, ""));
                System.out.println(i);
            }
        }
    }

    //Preenche cada casa do array com a disposição de peças de um jogo normal</p>
    //Invoca em cada uma das casas dentro do array, o metodo {@link gamaofx.casa#addpecabranca} e {@link gamaofx.casa#addpecapreta}
    public final void iniciapecas() {

        //pecas brancas
        for (int i = 0; i < 2; i++) {
            casas.get(24).addpecabranca();
        }
        for (int i = 0; i < 5; i++) {
            casas.get(13).addpecabranca();
        }
        for (int i = 0; i < 3; i++) {
            casas.get(8).addpecabranca();
        }
        for (int i = 0; i < 5; i++) {
            casas.get(6).addpecabranca();
        }

        //pecas pretas
        for (int i = 0; i < 2; i++) {
            casas.get(1).addpecapreta();
        }
        for (int i = 0; i < 5; i++) {
            casas.get(12).addpecapreta();
        }
        for (int i = 0; i < 3; i++) {
            casas.get(17).addpecapreta();
        }
        for (int i = 0; i < 5; i++) {
            casas.get(19).addpecapreta();
        }

    }
    //impressão de peças para simular o final do jogo


    //Preenche cada casa do array com a disposição de peças para testar as a fase final do jogo</p>
    //Invoca em cada uma das casas dentro do array, o metodo {@link gamaofx.casa#addpecabranca} e {@link gamaofx.casa#addpecapreta}
    public final void iniciapecastesteFinal() {

        for (int i = 0; i < 14; i++) {
            casas.get(24).addpecabranca();
        }
        casas.get(19).addpecabranca();

        //pecas pretas
        for (int i = 0; i < 14; i++) {
            casas.get(1).addpecapreta();
        }
        casas.get(7).addpecapreta();
    }

    //Testa se todas as peças do jogador encontram-se no ultimo quadrante</p>
    //@param jogador o jogador ao qual vai ser testado
    //@return true se todas as peças se encontrarem no ultimo parametro
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