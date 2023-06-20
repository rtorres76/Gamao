package gamaofxclient;

import java.io.Serializable;

public class packet implements Serializable{
    String message;
    /**
     *Construtor
     * @param message mensagem a ser enviada
     */
    public packet(String message) {
        this.message = message;
    }
}
