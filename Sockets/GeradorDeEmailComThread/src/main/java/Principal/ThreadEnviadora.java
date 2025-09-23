package Principal;

import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ThreadEnviadora extends Thread {

    private Socket socket;
    private Pessoa pessoaParaEnviar;
    private boolean pessoaJaExiste;
    private InterfaceServidor servidor;

    public ThreadEnviadora(Socket socket, Pessoa pessoaParaEnviar, boolean pessoaJaExiste, InterfaceServidor servidor) {
        this.socket = socket;
        this.pessoaParaEnviar = pessoaParaEnviar;
        this.pessoaJaExiste = pessoaJaExiste;
        this.servidor = servidor;
    }

    public void run() {
        try {
            if (pessoaJaExiste) {
                Comunicador.enviaMensagem(socket, null);
            } else {
                Comunicador.enviaObjeto(socket, pessoaParaEnviar);
            }

            socket.close();
            
            servidor.atualizarListaNaUI();
        } catch (IOException ex) {
            Logger.getLogger(ThreadEnviadora.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}