package Principal;

import java.net.Socket;
import java.util.ArrayList;

public class ThreadRecebedora extends Thread {

    private Socket socket;
    private ArrayList<Pessoa> lista;
    private InterfaceServidor servidor;
    private Pessoa pessoaCriada;
    private boolean pessoaJaExiste;

    public ThreadRecebedora(Socket socket, ArrayList<Pessoa> lista, InterfaceServidor servidor) {
        this.socket = socket;
        this.lista = lista;
        this.servidor = servidor;
        this.pessoaCriada = null;
        this.pessoaJaExiste = false;
    }

    public void run() {
            String nomePessoa = Comunicador.recebeMensagem(socket);
            
            if (nomePessoa != null) {

                String[] vetorNome = nomePessoa.split(" ");
                String email = vetorNome[0].toLowerCase() + "." + 
                              vetorNome[vetorNome.length - 1].toLowerCase() + "@ufn.edu.br";
                Pessoa p = new Pessoa(nomePessoa.toUpperCase(), email);
                synchronized (lista) {
                    if (lista.contains(p)) {
                        pessoaJaExiste = true;
                    } else {
                        lista.add(p);
                        pessoaCriada = p;
                    }
                }
                
                ThreadEnviadora enviadora = new ThreadEnviadora(socket, pessoaCriada, pessoaJaExiste, servidor);
                enviadora.start();
                try {
                    enviadora.join();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }

    }

    public Pessoa getPessoaCriada() {
        return pessoaCriada;
    }

    public boolean isPessoaJaExiste() {
        return pessoaJaExiste;
    }
}