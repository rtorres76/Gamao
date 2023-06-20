package backgammonfx;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

/**
 * <h1>BackGammonFX (Servidor)</h1>
 * <p>
 * O programa BackgammonFX é uma aplicação que permite jogar gamão atraves de
 * uma conexão servidor cliente.
 * </p>
 *
 * @author Joao_Pires
 * @version 1.0
 * @since 22-06-2021
 */
public class FXMLDocumentController implements Initializable {

    //---------------------------------FXML-----------------------------------------
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
    packet p;
    //rectangulos que representam as casas
    ArrayList<Rectangle> Rects;
    //Circulos que representam as peças
    @FXML
    static Circle[][] Circs;
    //Servidor
    Server servidor;
    //Animação de transição
    TranslateTransition moves;
    //Posição (X e Y) id(posição no array casas), e h(posição no array peças) FINAL  
    int finX, finY, finID, finH;
    //Posição (X e Y) id(posição no array casas), e h(posição no array peças) INICIAL     
    int iniX, iniY, iniID, iniH;
    //Atributos das casas dos jogadores
    int posjogX = 655, posjogY = 220, posadvX = 655, posadvY = 0;
    //posições possiveis (posicao inicial + dado1) (posicao inicial + dado2)
    int posID1, posID2;
    //fase do jogo
    int phase;
    //representa se é possivel começar a retirar peças do jogo
    boolean fimjogo;
    //"nome" do jogador e do adversário
    String jogador = "jog1", adversario = "jog2";
    //id da casa do jogador e do adversario
    int jogid = 26, advid = 27;

    /**
     * <p>
     * Inicializa o codigo, equivalente a um main
     * </p>
     */
    @FXML
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        phase = 0;
        //---------------------------------Server start recebe msg de teste-----------------------------------------
        try {
            servidor = new Server();
        } catch (Exception ex) {
            System.out.println(ex);
        }

//-------------------------------inicio do jogo---------------------------------
        white = new Image(getClass().getResourceAsStream("d.jpg"));
        black = new Image(getClass().getResourceAsStream("black.jpg"));
        tabinicio = new Image(getClass().getResourceAsStream("tabuleiroinicio.jpg"));
        tabfim = new Image(getClass().getResourceAsStream("tabuleirofinal.jpg"));
        tab1 = new tabuleiro();
        tab1.iniciapecas();
    }

    /**
     * Muda a imagem do imageview Muda o jogador para pecas pretas (topo)
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
     * muda a imagem do imageview Muda jogador para peças brancas (baixo)
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

    /**
     * <p>
     * Metodo ativado a partir de botão na tabela inicial</p>
     * <p>
     * Muda a imagem do scene builder para tabinicio</p>
     * <p>
     * - Invoca o metodo do {@link backgammonfx.tabuleiro#iniciapecas()}</p>
     */
    public void selecionartabinicio() {
        myImageView2.setImage(tabinicio);
        tab1 = new tabuleiro();
        tab1.iniciapecas();
    }

    /**
     * <p>
     * Metodo ativado a partir de botão na tabela inicial</p>
     * <p>
     * Muda a imagem do scene builder para tabfim</p>
     * <p>
     * - Invoca o metodo do
     * {@link backgammonfx.tabuleiro#iniciapecastesteFinal()}</p>
     */
    public void selecionartabfim() {
        myImageView2.setImage(tabfim);
        tab1 = new tabuleiro();
        tab1.iniciapecastesteFinal();
    }

    /**
     * <p>
     * inicia/reinicia jogo</p>
     * <p>
     * Cria jogador e adversário</p>
     * <p>
     * Dá reset aos atributos para valores iniciais</p>
     * <p>
     * Muda phase para 1</p>
     * <p>
     * invoca {@link backgammonfx.FXMLDocumentController#imprime()}</p>
     * <p>
     * invoca {@link backgammonfx.FXMLDocumentController#imprimeronda()}</p>
     * <p>
     * invoca {@link backgammonfx.FXMLDocumentController#imprimebotao()}</p>
     */
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

    /**
     * <p>
     * Metodo associado a cada retangulo</p>
     * <p>
     * Se phase == 2 invoca
     * {@link backgammonfx.FXMLDocumentController#click1()}</p>
     * <p>
     * Se phase == 3 invoca
     * {@link backgammonfx.FXMLDocumentController#click2()}</p>
     *
     * @param event evento associado ao click da peça, a partir do metodo
     * event.getsource() é possivel retornar o id da peça
     */
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

    /**
     * Anima a movimentação das peças
     *
     * @param diferencaX vetor X do movimento da peça
     * @param diferencaY vetor Y do movimento da peça Ambos os parametros
     * resultam da diferença entre a posiçao inicial e final da peça
     */
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

    /**
     * Metodo que anima a label ronda para simular uma mão a abanar os dados no
     * final invoca {@link backgammonfx.FXMLDocumentController#imprimedados()}
     */
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
    /**
     * Metodo que imprime retangulos e peças Invoca
     * {@link backgammonfx.FXMLDocumentController#imprimedados()} Invoca
     * {@link backgammonfx.FXMLDocumentController#imprimedados()}
     *
     */
    public void imprime() {
        imprimeretangulos();
        imprimepecas();
    }

    /**
     * Imprime os retangulos associados á casa do jogador
     *
     * @param id id do retangulo associado ao jogador
     * @param jog jogador ao qual a casa será imprimida
     */
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

    /**
     * Imprime os circulos associados ás peças de jogador
     *
     * @param id id da casa associado ao jogador
     * @param jog jogador ao qual as peças seram imprimidas
     */
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

    /**
     * <p>
     * Guarda no array Rects[], retangulos com os atributos da classe casa</p>
     * <p>
     * No final invoca
     * {@link backgammonfx.FXMLDocumentController#imprimeretangulosjog} com os
     * atributos do jogador</p>
     * <p>
     * E invoca {@link backgammonfx.FXMLDocumentController#imprimeretangulosjog}
     * novamente com os atributos do adversario</p>
     *
     */
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

    /**
     * <p>
     * Guarda no array duplo Circs[][], circulos com os atributos da classe
     * peca</p>
     * <p>
     * No final invoca
     * {@link backgammonfx.FXMLDocumentController#imprimepecasjog} com os
     * atributos do jogador</p>
     * <p>
     * E invoca {@link backgammonfx.FXMLDocumentController#imprimepecasjog}
     * novamente com os atributos do adversario</p>
     *
     */
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

    /**
     * Cria e imprime o texto que aparece no centro do ecra
     */
    public void imprimeronda() {
        ronda = new Label("Rode os dados");
        ronda.setLayoutX(250);
        ronda.setLayoutY(187);
        ronda.setScaleX(2);
        ronda.setScaleY(2);
        pane.getChildren().add(ronda);

    }

    /**
     * Cria e imprime o botão associado a rodar dados e passar jogada
     */
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

    /**
     * Metodo que cria e imprime dados para o ecra invoca
     * {@link backgammonfx.dado#rodadado} para dado 1 invoca
     * {@link backgammonfx.dado#rodadado} para dado 2
     */
    public void imprimedados() {

        tab1.dado1.rodadado(pane, 1);
        tab1.dado2.rodadado(pane, 2);
        ronda.setText("Selecione casa origem");
        bdados.setText("Passar Jogada");
        bdados.setDisable(false);

        bdados.setOnMouseClicked(event1 -> passarjogada());
        phase = 2;
        System.out.println("Fase de jogo:" + phase);
    }

    //--------------------------------------------JOGADAS-----------------------------------------------
    /**
     * verifica se posicao de destino encontra se vazia e caso nao esteja vazia
     * se contem peças do oponente
     *
     * @param posID posição que irá ser verificada
     * @return boolean que representa se é clicavel ou não
     */
    public boolean clicavel(int posID) {
        try {
            if (!tab1.casas.get(posID).pecas.isEmpty()) {
                if (jogador.compareTo(tab1.casas.get(posID).pecas.get(0).jogador) == 0) {
                    return true;
                } else if (tab1.casas.get(posID).pecas.size() == 1) {
                    System.out.println("SIZE :" + tab1.casas.get(posID).pecas.size());
                    return true;
                } else {
                    return false;
                }
            } else {
                return true;
            }
        } catch (Exception ex) {
            System.out.println(ex);
            return false;
        }

    }

    /**
     * Metodo associado ao primeiro click (selecionar a peça a mover) preve onde
     * a peça poderá se mover (a partir da soma com o dado 1 e dado 2) e muda a
     * cor das casas assim como bloqueia as casas para onde não se pode mover
     */
    public void click1() {

        iniH = tab1.casas.get(iniID).pecas.size() - 1;
        iniX = tab1.casas.get(iniID).pecas.get(iniH).posX;
        iniY = tab1.casas.get(iniID).pecas.get(iniH).posY;
        phase = 3;
        System.out.println("Fase de jogo:" + phase);
        ronda.setText("Selecione casa destino");

        //Caso tenha casas no meio fica automaticamente selecionado o centro;
        if ("jog1".equals(jogador)) {
            if (!tab1.casas.get(0).vazio()) {
                ronda.setText("Tem de jogar a casa do meio");
                iniID = 0;
                iniH = tab1.casas.get(iniID).pecas.size() - 1;
                iniX = tab1.casas.get(iniID).pecas.get(iniH).posX;
                iniY = tab1.casas.get(iniID).pecas.get(iniH).posY;
            }

            posID1 = iniID + tab1.dado1.face;
            posID2 = iniID + tab1.dado2.face;

        } else {
            if (!tab1.casas.get(25).vazio()) {
                ronda.setText("Tem de jogar a casa do meio");
                iniID = 25;
                iniH = tab1.casas.get(iniID).pecas.size() - 1;
                iniX = tab1.casas.get(iniID).pecas.get(iniH).posX;
                iniY = tab1.casas.get(iniID).pecas.get(iniH).posY;
            }
            posID1 = iniID - tab1.dado1.face;
            posID2 = iniID - tab1.dado2.face;

        }

        //correção automatica de foreach feita pelo netbeans (functional operation ?)
        Rects.forEach((f) -> {
            f.setDisable(true);

        });
        //desbloqueio da ultima posicao
        Rects.get(iniID).setFill(Color.RED);
        if (fimjogo) {
            if (tab1.dado1.uso == false && posID1 >= 25 || posID1 <= 0) {
                Rects.get(jog.id).setDisable(false);
                Rects.get(jog.id).setFill(Color.DARKSEAGREEN);
                System.out.println("id do jog ativado" + jog.id);
            }
            if (tab1.dado2.uso == false && posID2 >= 25 || posID2 <= 0) {
                Rects.get(jog.id).setDisable(false);
                Rects.get(jog.id).setFill(Color.DARKSEAGREEN);
                System.out.println("id do jog ativado" + jog.id);
            }
        }

        if (tab1.dado1.uso == false && clicavel(posID1)) {
            Rects.get(posID1).setFill(Color.DARKSEAGREEN);
            Rects.get(posID1).setDisable(false);
        }
        if (tab1.dado2.uso == false && clicavel(posID2)) {
            Rects.get(posID2).setFill(Color.DARKSEAGREEN);
            Rects.get(posID2).setDisable(false);
        }

    }

    /**
     * Metodo associado ao segundo click (seleção de onde a peça irá mover) caso
     * a posição final seja uma casa de um jogador invoca
     * {@link backgammonfx.FXMLDocumentController#clickfimjogo()} caso a posição
     * final seja outra invoca
     * {@link backgammonfx.FXMLDocumentController#clicknormal()}
     */
    public void click2() {
        System.out.print(finID);

        if (finID == 0 || finID == 25) {
            //nao é aceite            

        } else if (finID == 26 || finID == 27) {
            if (finID == jog.id) {
                clickfimjogo();
                movepeca(finX - iniX, finY - iniY);
            }
        } else {
            clicknormal();
            movepeca(finX - iniX, finY - iniY);
        }


        /*  for (Rectangle f : Rects) {
                f.setDisable(true);
            }*/
    }

    /**
     * Click associado a peça sair para a casa do jogador no final invoca
     * {@link backgammonfx.FXMLDocumentController#condicaovitoria}
     */
    public void clickfimjogo() {
        //Adicionar peça para acertar a posição da animação final    
        jog.addpecablank();
        //verificar o  H (id da ultima peça dentro da casa)
        finH = jog.pecas.size() - 1;
        finX = jog.pecas.get(finH).posX;
        finY = jog.pecas.get(finH).posY;
        //Remove peça blank
        jog.rempeca();
        if ("jog1".compareTo(jogador) == 0) {
            jog.addpecabranca();
        }
        if ("jog2".compareTo(jogador) == 0) {
            jog.addpecapreta();
        }
        //Remove peça do inicio
        tab1.casas.get(iniID).rempeca();
        Rects.get(finID).setFill(Color.BLUE);
        //verificar que dado escolheu

        //caso a pos de destino seja 27 as peças estao a moverse na direcao contraria entao é necessario verificaçao de dados diferente
        if (finID == 27) {
            posID1 = posID1 * -1 + 27;
            posID2 = posID2 * -1 + 27;
        }
        if (finID == 26) {
            posID1 += 1;
            posID2 += 1;
        }
//função para usar o menor dos dois dados
        if (posID1 <= posID2) {
            if (posID1 >= finID && tab1.dado1.uso == false) {
                tab1.dado1.usadado();
                phase = 2;
                System.out.println("Fase de jogo:" + phase);

            } else if (posID2 >= finID) {
                tab1.dado2.usadado();
                phase = 2;
                System.out.println("Fase de jogo:" + phase);
            }
        } else if (posID1 >= posID2) {
            if (posID2 >= finID && tab1.dado2.uso == false) {
                tab1.dado2.usadado();
                phase = 2;
                System.out.println("Fase de jogo:" + phase);
            } else if (posID1 >= finID) {
                tab1.dado1.usadado();
                phase = 2;
                System.out.println("Fase de jogo:" + phase);
            }
        }

        if (tab1.dado1.uso && tab1.dado2.uso) {
            phase = 4;
            System.out.println("Fase de jogo:" + phase);
            ronda.setText("Passe jogada");
        } else {
            ronda.setText("Selecione casa origem");
        }

        condicaovitoria(jog);

    }

    /**
     * Click associado a peça a mover para casa
     */
    public void clicknormal() {
        //Adicionar peça para acertar a posição da animação final    
        tab1.casas.get(finID).addpecablank();
        //verificar o  H (id da ultima peça dentro da casa)
        finH = tab1.casas.get(finID).pecas.size() - 1;
        finX = tab1.casas.get(finID).pecas.get(finH).posX;
        finY = tab1.casas.get(finID).pecas.get(finH).posY;
        //Remove peça blank
        tab1.casas.get(finID).rempeca();
        comivel();
        if ("jog1".compareTo(jogador) == 0) {
            tab1.casas.get(finID).addpecabranca();

        }
        if ("jog2".compareTo(jogador) == 0) {
            tab1.casas.get(finID).addpecapreta();
        }

        //Remove peça do inicio
        tab1.casas.get(iniID).rempeca();
        //verifica se o jogo se encontra na fase final (apos estar na fase final nao verifica mais)
        if (fimjogo == false) {
            fimjogo = tab1.fimdejogo(jogador);
        }
        Rects.get(finID).setFill(Color.BLUE);
        //verificar que dado escolheu (a segunda condição é para caso os dois dados sejam iguais)
        if (finID == posID1 && tab1.dado1.uso == false) {
            tab1.dado1.usadado();
            phase = 2;
            System.out.println("Fase de jogo:" + phase);
        } else if (finID == posID2) {
            tab1.dado2.usadado();
            phase = 2;
            System.out.println("Fase de jogo:" + phase);
        }

        if (tab1.dado1.uso && tab1.dado2.uso) {
            phase = 4;
            System.out.println("Fase de jogo:" + phase);
            ronda.setText("Passe jogada");
        } else {
            ronda.setText("Selecione casa origem");
        }
    }

    /**
     * Metodo invocado a partir de botão cancelar cancela a posição escolhida
     * com o click1 e retorna phase para 2
     */
    public void cancelarjog() {
        if (phase == 3) {
            imprime();
            phase = 2;
            System.out.println("Fase de jogo:" + phase);
        }
    }
    //---------------------------------server-----------------------------------------

    /**
     * Metodo que passa a jogada para o outro jogador envia o tabuleiro e os
     * jogadores ao cliente
     */
    public void passarjogada() {
        bdados.setDisable(true);
        //envia pecas
        try {
            servidor.enviarPecas(tab1);
        } catch (IOException | ClassNotFoundException ex) {
            System.out.println(ex);
        }
        //envia jog      
        try {
            servidor.enviarJog(jog);
        } catch (IOException | ClassNotFoundException ex) {
            System.out.println(ex);
        }
        //envia adv        
        try {
            servidor.enviarJog(adv);
        } catch (IOException | ClassNotFoundException ex) {
            System.out.println(ex);
        }

        phase = 5;
        System.out.println("Fase de jogo:" + phase);

        receberjogada();
    }

    /**
     * Recebe a jogada do jogador adversario invoca
     * {@link backgammonfx.Server#receberpecas} invoca
     * {@link backgammonfx.Server#receberJog()} para jogador 1 invoca
     * {@link backgammonfx.Server#receberJog()} para jogador 2
     */
    public void receberjogada() {
        //recebe pecas
        try {
            tab1.casas = servidor.receberpecas();
        } catch (IOException | ClassNotFoundException ex) {
            System.out.println(ex);
        }
        //recebe jog (adv)
        try {
            adv = servidor.receberJog();
        } catch (IOException | ClassNotFoundException ex) {
            System.out.println(ex);
        }
        //recebe adv (jog) 
        try {
            jog = servidor.receberJog();
        } catch (IOException | ClassNotFoundException ex) {
            System.out.println(ex);

        }

        pane.getChildren().clear();
        imprime();
        imprimeronda();
        ronda.setText("Jogada recebida");
        imprimebotao();
        bdados.setDisable(false);
        bdados.setOnMouseClicked(event -> animadados());
        phase = 1;
        System.out.println("Fase de jogo:" + phase);
        tab1.dado1.resetdado();
        tab1.dado2.resetdado();

    }
    //---------------------------------server-----------------------------------------

    /**
     * Verifica se um espaço contem uma peça adversaria e caso tenha invoca
     * {@link backgammonfx.casa#addpecabranca()} ou
     * {@link backgammonfx.casa#addpecapreta()} para inserir a peça no centro e
     * em seguida invoca {@link backgammonfx.casa#rempeca()} para remover a peça
     */
    public void comivel() {
        //Só há uma situação em que a peça pousa numa peça adversária (clicavel) que é quando esta é comivel
        if (!tab1.casas.get(finID).pecas.isEmpty()) {
            if (tab1.casas.get(finID).pecas.get(finH - 1).jogador.compareTo(jogador) != 0) {
                //finH é a posicao da peça em branco que foi removida
                if ("jog1".equals(jogador)) {
                    tab1.casas.get(25).addpecapreta();
                }
                if ("jog2".equals(jogador)) {
                    tab1.casas.get(0).addpecabranca();
                }
                //remove a peça do adversario
                tab1.casas.get(finID).rempeca();
            }
        }
    }

    /**
     * verifica se todas as peças brancas se encontram na casa do jogador
     *
     * @param jog jogador ao qual irá ser verificado
     */
    public void condicaovitoria(jogador jog) {
        if (jog.pecas.size() >= 15) {
            ronda.setTextFill(Color.GOLD);
            ronda.setScaleX(5);
            ronda.setScaleY(5);
            ronda.setLayoutX(100);
            ronda.setLayoutY(20);
            ronda.setRotate(0.2);
            ronda.setText(jog.jogador + "GANHOU !!!!");

            animavitoria();
            Rects.forEach((f) -> {
                f.setDisable(true);
                System.out.print(f.getId());
            });
            bdados.setText("Guardar pontuação");
            bdados.setOnMouseClicked(event -> guardascore(event));
            cancelar.setDisable(true);

            phase = 6;
            System.out.println("Fase de jogo:" + phase);
            pane.getChildren().remove(ronda);
            pane.getChildren().add(ronda);
        }

    }

    /**
     * Animação do texto no centro do ecra para vitoria do jogador
     */
    public void animavitoria() {
        //Instantiating TranslateTransition class   
        TranslateTransition translate = new TranslateTransition();
        //shifting the X coordinate of the centre of the circle by 400   
        translate.setByX(200);
        //setting the duration for the Translate transition   
        translate.setDuration(Duration.millis(500));
        //setting cycle count for the Translate transition   
        translate.setCycleCount(100);
        //the transition will set to be auto reversed by setting this to true   
        translate.setAutoReverse(true);
        //setting Circle as the node onto which the transition will be applied  
        translate.setNode(ronda);
        //playing the transition   
        translate.play();
    }

    /**
     * @param event evento associado ao click do mouse, não é utilizado
     */
    public void guardascore(MouseEvent event) {
        pane.getChildren().clear();
        try {
            servidor.CloseServer();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
