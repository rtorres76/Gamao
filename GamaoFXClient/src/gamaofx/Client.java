package gamaofx;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
public class Client {
    Socket s;
    ObjectInputStream din;
    ObjectOutputStream dout;
    packet p;
    jogador jog;
    ArrayList<casa> casas;
    static final int PORT = 3192;

    /**
     *Construtor
     * dá inicio ao cliente e envia servidor um packet para testar a ligação
     * invoca {@link gamaofx.Client#StartServer}
     * invoca {@link gamaofx.Client#enviar}
     * @throws Exception caso o servidor não responda ou não consiga encontrar a classe
     */
    public Client() throws Exception {
        StartServer();
        enviar();
    }
    //-----------------------START SERVIDOR--------------------------

    /**
     * Inicia cliente
     * @throws ClassNotFoundException caso não encontre a classe que é pedida
     */
    public void StartServer() throws ClassNotFoundException {
        try {

            s = new Socket("localhost", PORT);
            dout = new ObjectOutputStream(s.getOutputStream());
            din = new ObjectInputStream(s.getInputStream());
            System.out.println("CLIENT up and running");

        } catch (IOException ex) {
            System.out.println(ex);
        }
    }
    //-----------------------CLOSE SERVIDOR--------------------------

    /**
     * Fecha cliente
     * @throws ClassNotFoundException caso não encontre a classe que é pedida
     */
    public void CloseServer() throws ClassNotFoundException {
        try {
            s.close();
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }

    //-------------------------ENVIAR TESTE-------------------------------------

    /**
     * Envia packet de teste de ligação
     * @throws IOException caso não conecte com o servidor
     * @throws ClassNotFoundException caso não encontre a classe recebida
     */
    public void enviar() throws IOException, ClassNotFoundException {

        System.out.println("A ENVIAR");
        p = new packet("ADEUS MASTER");
        dout.writeObject(p);
        System.out.println("Mensagem enviada");

    }
    //-------------------------RECEBER CASAS-------------------------------------

    /**
     * Recebe as casas do servidor
     * @return retorna as casas
     * @throws IOException caso não conecte com o servidor
     * @throws ClassNotFoundException caso não encontre a classe recebida
     */
    public ArrayList<casa> receberpecas() throws IOException, ClassNotFoundException {


        System.out.println("A RECEBER casas");
        casas = (ArrayList<casa>) din.readObject();
        System.out.println("Mensagem recebida:");
        return casas;
    }
    //------------------------ENVIAR CASAS---------------------------------------

    /**
     *Envia casas para o servidor
     * @param tab1 tabuleiro que contem as casas
     * @throws IOException caso não conecte com o cliente
     * @throws ClassNotFoundException caso não encontre a classe recebida
     */
    public void enviarPecas(tabuleiro tab1) throws IOException, ClassNotFoundException {

        System.out.println("A ENVIAR CASAS");
        dout.writeObject(tab1.casas);
        System.out.println("Mensagem enviada");

    }
    //------------------------ENVIAR JOG---------------------------------------

    /**
     *Envia o jogador para o servidor
     * @param jog o jogador a enviar
     * @throws IOException caso não conecte com o servidor
     * @throws ClassNotFoundException caso não encontre a classe recebida
     */
    public void enviarJog(jogador jog) throws IOException, ClassNotFoundException {

        System.out.println("A ENVIAR:" + jog.jogador);
        dout.writeObject(jog);
        System.out.println("Mensagem enviada");

    }
    //-------------------------RECEBER JOG-------------------------------------

    /**
     *Recebe jogador do servidor
     * @throws IOException caso não conecte com o servidor
     * @throws ClassNotFoundException caso não encontre a classe recebida
     * @return jogador recebido
     */
    public jogador receberJog() throws IOException, ClassNotFoundException {

        System.out.println("A RECEBER jogador");
        jog = (jogador) din.readObject();
        System.out.println("Mensagem recebida:" + jog.jogador);
        return jog;
    }
}
