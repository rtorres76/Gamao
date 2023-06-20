/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backgammonfx;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * <p>
 *A classe dado representa os dados a serem imprimidos para o ecra
 * </p>
 *
 * @author Joao_Pires
 * @version 1.0
 * @since 22-06-2021
 */
public class dado {

    int face;
    boolean uso;
    Rectangle rect;
    Label num;

    /**
     *Construtor
     */
    public dado() {
        resetdado();
    }

    /**
     *Roda o dado, guarda um valor aleatorio entre 1 e 6
     * e cria um retangulo com esse valor no meio
     * no fim adiciona ao pane para ser impresso
     * @param pane pane onde será adicionado o dado
     * @param pos posição onde o dado será imprimido
     */
    public void rodadado(Pane pane, int pos) {
        uso = false;
        face = (int) Math.floor(Math.random() * 6) + 1;
        rect = new Rectangle(30, 30);
        rect.setFill(Color.WHITESMOKE);
        rect.setStroke(Color.BLACK);
        num = new Label("" + face);
        rect.setLayoutX(50 + 40 * pos);
        rect.setLayoutY(185);
        num.setLayoutX(50 + 40 * pos + 10);
        num.setLayoutY(185 + 6);
        num.setScaleX(3);
        num.setScaleY(3);
        pane.getChildren().add(rect);
        pane.getChildren().add(num);

    }

    /**
     *Retira e adiciona novamente ao pane
     * @param pane pane onde será retirado e adicionado
     */
    public void redraw(Pane pane) {
        pane.getChildren().remove(rect);
        pane.getChildren().remove(num);        
        pane.getChildren().add(rect);
        pane.getChildren().add(num);
    }

    /**
     * Usa o dado
     *Torna o boolean uso = true
     *Troca a cor do dado para cinzento para informar que foi usado
     */
    public void usadado() {
        uso = true;
        rect.setFill(Color.GREY);
    }
    
    /**
     *Reseta os atributos do dado
     */
    public void resetdado(){
        face = 0;
    rect = null;
    num = null;
    uso = false;
    }

}
