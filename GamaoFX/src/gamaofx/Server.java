package gamaofx;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class Server {
    Socket s;
    ServerSocket ss;
    ObjectInputStream din;
    ObjectOutputStream dout;
    packet p;
    jogador jog;
    static final int PORT = 3192;
    ArrayList<casa> casas;

    //Construtor
    //dá inicio ao servidor e pede ao cliente um packet para testar a ligação
    //invoca {@link gamaofx.Server#StartServer}
    //invoca {@link gamaofx.Server#receber}
    //@throws Exception caso o cliente não responda ou não consiga encontrar a classe
    public Server() throws Exception {
        StartServer();
        receber();
    }

    //-----------------------START SERVIDOR--------------------------

    //Inicia servidor
    //@throws ClassNotFoundException caso não encontre a classe que é pedida
    public void StartServer() throws ClassNotFoundException {
        try {

            ss = new ServerSocket(PORT);
            s = ss.accept();//establishes connection

            dout = new ObjectOutputStream(s.getOutputStream());
            din = new ObjectInputStream(s.getInputStream());
            System.out.println("Server up and running");

        } catch (IOException ex) {
            System.out.println(ex);
        }
    }

    //-----------------------CLOSE SERVIDOR--------------------------

    //Fecha servidor
    //@throws ClassNotFoundException caso não encontre a classe que é pedida
    public void CloseServer() throws ClassNotFoundException {
        try {
            s.close();
        } catch (IOException ex) {
            System.out.println(ex);

        }
    }

    //---------------------------------RECEBER TESTE-----------------------------

    //Espera receber packet de teste
    //@throws IOException caso não conecte com o cliente
    //@throws ClassNotFoundException caso não encontre a classe recebida
    public void receber() throws IOException, ClassNotFoundException {

        System.out.println("A RECEBER");
        p = (packet) din.readObject();
        System.out.println("Mensagem recebida:" + p.message);

    }

    //------------------------ENVIAR CASAS---------------------------------------

    //Envia casas para o cliente
    //@param tab1 tabuleiro que contem as peças
    //@throws IOException caso não conecte com o cliente
    //@throws ClassNotFoundException caso não encontre a classe recebida
    public void enviarPecas(tabuleiro tab1) throws IOException, ClassNotFoundException {

        System.out.println("A ENVIAR CASAS");
        dout.writeObject(tab1.casas);
        System.out.println("Mensagem enviada");

    }
    //-------------------------RECEBER CASAS-------------------------------------

    //Recebe as casas do cliente
    //@return retorna as casas
    //@throws IOException caso não conecte com o cliente
    //@throws ClassNotFoundException caso não encontre a classe recebida
    public ArrayList<casa> receberpecas() throws IOException, ClassNotFoundException {

        System.out.println("A RECEBER casas");
        casas = (ArrayList<casa>) din.readObject();
        System.out.println("Mensagem recebida:");
        return casas;
    }

    //------------------------ENVIAR JOG---------------------------------------

    //Envia o jogador para o cliente
    //@param jog o jogador a enviar
    //@throws IOException caso não conecte com o cliente
    //@throws ClassNotFoundException caso não encontre a classe recebida
    public void enviarJog(jogador jog) throws IOException, ClassNotFoundException {

        System.out.println("A ENVIAR:" + jog.jogador);
        dout.writeObject(jog);
        System.out.println("Mensagem enviada");

    }
    //-------------------------RECEBER JOG-------------------------------------

    //Recebe o jogador do cliente
    //@return o jogador recebido
    //@throws IOException caso não conecte com o cliente
    //@throws ClassNotFoundException caso não encontre a classe recebida
    public jogador receberJog() throws IOException, ClassNotFoundException {

        System.out.println("A RECEBER jogador");
        jog = (jogador) din.readObject();
        System.out.println("Mensagem recebida:" + jog.jogador);
        return jog;
    }
}
