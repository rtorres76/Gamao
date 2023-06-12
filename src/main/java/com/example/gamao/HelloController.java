package com.example.gamao;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class HelloController implements Initializable {

    //Imagens que irão ser demonstradas no menu inicial
    @FXML
    Image black, white, tabinicio, tabfim;

    //Tabuleiro do jogo
    tabuleiro tab1;

    //Local onde as imagens irão aparecer
    @FXML
    ImageView myImageView;

    //"nome" do jogador e do adversário
    String jogador = "jog1", adversario = "jog2";

    //Atributos das casas dos jogadores
    int posjogX = 655, posjogY = 220, posadvX = 655, posadvY = 0;

    //id da casa do jogador e do adversario
    int jogid = 26, advid = 27;

    public void initialize(URL url, ResourceBundle rb) {

        //--Inicio Jogo

        white = new Image(getClass().getResourceAsStream("d.jpg"));
        black = new Image(getClass().getResourceAsStream("black.jpg"));
        tabinicio = new Image(getClass().getResourceAsStream("tabuleiroinicio.jpg"));
        tabfim = new Image(getClass().getResourceAsStream("tabuleirofinal.jpg"));
        tab1 = new tabuleiro();
        tab1.iniciapecas();
    }

    /**
     //-- Muda a imagem do imageview Muda o jogador para pecas pretas (topo)
     */
    public void selecionarpretas() {
        myImageView.setImage(black);
        jogador = "jog2";
        adversario = "jog1";
        posjogX = 655;
        posjogY = 0;
        posadvX = 655;
        posadvY = 220;
        jogid = 27;
        advid = 26;
    }

    /**
     //-- Muda a imagem do imageview Muda jogador para peças brancas (baixo)
     */
    public void selecionarbrancas() {
        myImageView.setImage(white);
        jogador = "jog1";
        adversario = "jog2";
        posjogX = 655;
        posjogY = 220;
        posadvX = 655;
        posadvY = 0;
        jogid = 26;
        advid = 27;
    }


}