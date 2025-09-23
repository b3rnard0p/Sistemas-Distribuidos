package Principal;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Comunicador {

    public static String recebeMensagem(Socket s) {
        try {
            ObjectInputStream leitor = new ObjectInputStream(s.getInputStream());
            String mensagem = (String)leitor.readObject();
            return mensagem;
        } catch (Exception e) {
            return null;
        }
    }

    public static void enviaMensagem(Socket s, String mensagem) {
        try {
            ObjectOutputStream escritor = new ObjectOutputStream(s.getOutputStream());
            escritor.writeObject(mensagem);
            escritor.flush();
        } catch (Exception e) {
        }
    }
    
    public static void enviaObjeto(Socket s, Object objeto) {
        try {
            ObjectOutputStream escritor = new ObjectOutputStream(s.getOutputStream());
            escritor.writeObject(objeto);
            escritor.flush();
        } catch (Exception e) {
        }
    }
}
