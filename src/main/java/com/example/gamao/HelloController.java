package com.example.gamao;

import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HelloController {

    //Pane criado no screenbuilder que irá conter todos os componentes
    @FXML
    Pane pane;
    //Imagens que irão ser demonstradas no menu inicial
    @FXML
    Image black, white, tabinicio, tabfim;
    //Local onde as imagens irão aparecer
    @FXML
    ImageView myImageView, myImageView2;
    //Botão para cancelar a jogada
    Button cancelar;
    //Informação no centro do ecrã sobre o jogo
    Label ronda;
    Button bdados, bpecas;
    //Tabuleiro do jogo
    tabuleiro tab1;
    //Jogador e adversario
    jogador jog, adv;
    //Instancia da classe packet apenas serve para testar conexão
    //packet p;
    //rectangulos que representam as casas
    ArrayList<Rectangle> Rects;
    //Circulos que representam as peças
    @FXML
    static Circle[][] Circs;
    //Servidor
    //Server servidor;
    //Animação de transição
    TranslateTransition moves;
    //Posição (X e Y) id(posição no array casas), e h(posição no array peças) FINAL
    int finX, finY, finID, finH;
    //Posição (X e Y) id(posição no array casas), e h(posição no array peças) INICIAL
    int iniX, iniY, iniID, iniH;
    //Atributos das casas dos jogadores
    int posID1, posID2;
    //fase do jogo
    int phase;
    //representa se é possivel começar a retirar peças do jogo
    boolean fimjogo;
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
    //Metodo ativado a partir de botão na tabela inicial
    //Muda a imagem do scene builder para tabinicio
    //Invoca o metodo do {@link backgammonfx.tabuleiro#iniciapecas()}
    public void selecionartabinicio() {
        myImageView2.setImage(tabinicio);
        tab1 = new tabuleiro();
        tab1.iniciapecas();
    }

    //Metodo ativado a partir de botão na tabela inicial</p>
    //Muda a imagem do scene builder para tabfim</p>
    //Invoca o metodo do
    //{@link backgammonfx.tabuleiro#iniciapecastesteFinal()}</p>
    public void selecionartabfim() {
        myImageView2.setImage(tabfim);
        tab1 = new tabuleiro();
        tab1.iniciapecastesteFinal();
    }

    //inicia/reinicia jogo
    //Cria jogador e adversário
    //Dá reset aos atributos para valores iniciais
    //Muda phase para 1
    //invoca {@link backgammonfx.FXMLDocumentController#imprime()}
    //invoca {@link backgammonfx.FXMLDocumentController#imprimeronda()}
    //invoca {@link backgammonfx.FXMLDocumentController#imprimebotao()}
    public void iniciarjogo() {

        jog = new jogador(jogid, jogador, posjogX, posjogY);
        adv = new jogador(advid, adversario, posadvX, posadvY);

        fimjogo = false;

        //--------------------------IMPRIMIR----------------------------------------------
        phase = 1;
        System.out.println("Fase de jogo:" + phase);
        pane.getChildren().clear();
        imprime();
        imprimeronda();
        imprimebotao();
    }


    //Metodo associado a cada retangulo
    //Se phase == 2 invoca
    //{@link backgammonfx.FXMLDocumentController#click1()}
    //Se phase == 3 invoca
    //{@link backgammonfx.FXMLDocumentController#click2()}
    //@param event evento associado ao click da peça, a partir do metodo
    //event.getsource() é possivel retornar o id da peça
    public void pressed(MouseEvent event) {
        if (phase == 2) {
            Rectangle b = (Rectangle) event.getSource();
            iniID = Integer.parseInt(b.getId());
            if (tab1.casas.get(iniID).jogavel && iniID < 25 && iniID > 0) {
                click1();
            }

        } else if (phase == 3) {
            Rectangle b = (Rectangle) event.getSource();
            finID = Integer.parseInt(b.getId());

            if ((finID < 25 && finID != 0) || fimjogo) {
                click2();
            }
        }

    }
     //Anima a movimentação das peças
     //@param diferencaX vetor X do movimento da peça
     //@param diferencaY vetor Y do movimento da peça Ambos os parametros
     //resultam da diferença entre a posiçao inicial e final da peça
    public void movepeca(int diferencaX, int diferencaY) {
        //Instantiating TranslateTransition class
        TranslateTransition translate = new TranslateTransition();
        //shifting the X coordinate of the centre of the circle by 400
        translate.setByX(diferencaX);
        translate.setByY(diferencaY);
        //setting the duration for the Translate transition
        translate.setDuration(Duration.millis(500));
        //setting cycle count for the Translate transition
        translate.setCycleCount(1);
        //the transition will set to be auto reversed by setting this to true
        translate.setAutoReverse(true);
        //setting Circle as the node onto which the transition will be applied
        translate.setNode(Circs[iniID][iniH]);
        //playing the transition
        translate.play();
        translate.setOnFinished(e -> imprime());

        System.out.println("ANIMACAO ACABOU");
    }

    //Metodo que anima a label ronda para simular uma mão a abanar os dados no
    //final invoca {@link backgammonfx.FXMLDocumentController#imprimedados()}
    public void animadados() {
        //Instantiating TranslateTransition class
        TranslateTransition translate = new TranslateTransition();
        //shifting the X coordinate of the centre of the circle by 400
        translate.setByX(20);
        translate.setByY(20);
        //setting the duration for the Translate transition
        translate.setDuration(Duration.millis(50));
        //setting cycle count for the Translate transition
        translate.setCycleCount(10);
        //the transition will set to be auto reversed by setting this to true
        translate.setAutoReverse(true);
        //setting Circle as the node onto which the transition will be applied
        translate.setNode(ronda);//-------------------------------------------------------------------------------------------------------
        //playing the transition
        translate.play();

        translate.setOnFinished(e -> imprimedados());

        System.out.println("ANIMACAO ACABOU");
    }

    //----------------------------IMPRIME----------------------------------------
     //Metodo que imprime retangulos e peças Invoca
     //{@link backgammonfx.FXMLDocumentController#imprimedados()} Invoca
     //{@link backgammonfx.FXMLDocumentController#imprimedados()}
    public void imprime() {
        imprimeretangulos();
        imprimepecas();
    }

     //Imprime os retangulos associados á casa do jogador
     //@param id id do retangulo associado ao jogador
     //@param jog jogador ao qual a casa será imprimida
    public void imprimeretangulosjog(int id, jogador jog) {

        Rects.add(id, new Rectangle(50, 180));
        Rects.get(id).setLayoutX(jog.posX);
        Rects.get(id).setLayoutY(jog.posY);

        if (jogador.equals(jog.jogador)) {
            if (fimjogo) {
                Rects.get(id).setFill(Color.RED);
            } else {
                Rects.get(id).setFill(Color.BLACK);
            }
            Rects.get(id).setStroke(Color.GOLD);
            Rects.get(id).setStrokeWidth(3);
        } else {
            Rects.get(id).setFill(Color.BLACK);
            Rects.get(id).setStroke(Color.GOLD);
            Rects.get(id).setStrokeWidth(3);
        }
        Rects.get(id).setId(String.valueOf(id));

        Rects.get(id).setOnMousePressed(event -> pressed(event));
    }

     //Imprime os circulos associados ás peças de jogador
     //@param id id da casa associado ao jogador
     //@param jog jogador ao qual as peças seram imprimidas

    public void imprimepecasjog(int id, jogador jog) {
        for (int h = 0; h < jog.pecas.size(); h++) {

            Circs[id][h] = new Circle(jog.pecas.get(h).posX, jog.pecas.get(h).posY, 18);

            if ("jog1".compareTo(jog.jogador) == 0) {
                Circs[id][h].setFill(Color.WHITE);
                Circs[id][h].setStroke(Color.BLACK);
            }
            if ("jog2".compareTo(jog.jogador) == 0) {
                Circs[id][h].setFill(Color.BLACK);
                Circs[id][h].setStroke(Color.WHITE);
            }
            Circs[id][h].setId(id + "" + h);

            pane.getChildren().add(Circs[id][h]);
        }

    }

    //Guarda no array Rects[], retangulos com os atributos da classe casa
    //No final invoca
    //{@link backgammonfx.FXMLDocumentController#imprimeretangulosjog} com os
    //atributos do jogador
    //E invoca {@link backgammonfx.FXMLDocumentController#imprimeretangulosjog}
    //novamente com os atributos do adversario
    public void imprimeretangulos() {
        Rects = new ArrayList<>();
        Rects.clear();
        pane.getChildren().removeIf(node -> node instanceof Rectangle);
        pane.getChildren().removeAll(Rects);
        for (int i = 0; i <= 25; i++) {

            Rects.add(i, new Rectangle(50, 180));
            Rects.get(i).setLayoutX(tab1.casas.get(i).posX);
            Rects.get(i).setLayoutY(tab1.casas.get(i).posY);

            if ("cima".equals(tab1.casas.get(i).cor)) {
                Rects.get(i).setFill(Color.AQUA);
                Rects.get(i).setStroke(Color.BLACK);
            }
            if ("baixo".equals(tab1.casas.get(i).cor)) {
                Rects.get(i).setFill(Color.CHOCOLATE);
                Rects.get(i).setStroke(Color.BLACK);
            }
            if ("".equals(tab1.casas.get(i).cor)) {
                Rects.get(i).setOpacity(0.10);
            }
            Rects.get(i).setId(String.valueOf(i));

            Rects.get(i).setOnMousePressed(event -> pressed(event));
        }
        //unica alteração entre projeto do cliente e do servidor
        //jog1 tem de ser primeiro
        if (jog.id < adv.id) {
            imprimeretangulosjog(jog.id, jog);
            imprimeretangulosjog(adv.id, adv);
        } else {
            imprimeretangulosjog(adv.id, adv);
            imprimeretangulosjog(jog.id, jog);
        }
        pane.getChildren().addAll(Rects);
        if (tab1.dado1.rect != null) {
            tab1.dado1.redraw(pane);
            tab1.dado2.redraw(pane);
        }

    }
//necessario repetir "for" para garantir que as peças estejam no topo da hierarquia

     //Guarda no array duplo Circs[][], circulos com os atributos da classe
     //peca
     //No final invoca
     //{@link backgammonfx.FXMLDocumentController#imprimepecasjog} com os
     //atributos do jogador
     //E invoca {@link backgammonfx.FXMLDocumentController#imprimepecasjog}
     //novamente com os atributos do adversario
    @FXML
    public void imprimepecas() {
        Circs = new Circle[30][16];
        pane.getChildren().removeIf(node -> node instanceof Circle);
        for (int i = 0; i <= 25; i++) {
            for (int h = 0; h < tab1.casas.get(i).pecas.size(); h++) {

                Circs[i][h] = new Circle(tab1.casas.get(i).pecas.get(h).posX, tab1.casas.get(i).pecas.get(h).posY, 18);

                if ("jog1".compareTo(tab1.casas.get(i).pecas.get(h).jogador) == 0) {
                    Circs[i][h].setFill(Color.WHITE);
                    Circs[i][h].setStroke(Color.BLACK);
                    tab1.casas.get(i).setjogavel(jogador, true);
                }
                if ("jog2".compareTo(tab1.casas.get(i).pecas.get(h).jogador) == 0) {
                    Circs[i][h].setFill(Color.BLACK);
                    Circs[i][h].setStroke(Color.WHITE);
                    tab1.casas.get(i).setjogavel(jogador, false);
                }
                Circs[i][h].setId(i + "" + h);

                pane.getChildren().add(Circs[i][h]);

            }
        }
        imprimepecasjog(jog.id, jog);
        imprimepecasjog(adv.id, adv);
        //IMPRIMIR CIRCULOS DE CADA JOGADOR ---------------------------------------------------------------------------------------------------------

    }

    //Cria e imprime o texto que aparece no centro do ecra
    public void imprimeronda() {
        ronda = new Label("Rode os dados");
        ronda.setLayoutX(250);
        ronda.setLayoutY(187);
        ronda.setScaleX(2);
        ronda.setScaleY(2);
        pane.getChildren().add(ronda);

    }

    //Cria e imprime o botão associado a rodar dados e passar jogada
    public void imprimebotao() {
        bdados = new Button("Rodar dados");
        bdados.setLayoutX(450);
        bdados.setLayoutY(187);
        bdados.setOnMouseClicked(event -> animadados());
        cancelar = new Button("Cancelar");
        cancelar.setLayoutX(550);
        cancelar.setLayoutY(187);
        cancelar.setOnMouseClicked(event -> cancelarjog());
        pane.getChildren().add(bdados);
        pane.getChildren().add(cancelar);

    }
}