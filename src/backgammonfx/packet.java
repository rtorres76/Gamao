/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backgammonfx;

import java.io.Serializable;

/**
 * A classe packet apenas contem uma string message para testar a ligação entre
 * o servidor e o cliente
 *
 */
public class packet implements Serializable {

    String message;
/**
 *Construtor
     * @param message mensagem a ser enviada
 */
    public packet(String message) {
        this.message = message;
    }

}
