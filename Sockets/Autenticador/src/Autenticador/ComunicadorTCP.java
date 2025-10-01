package Autenticador;

import java.io.*;
import java.net.*;

public class ComunicadorTCP {
    
    public static void enviarMensagem(Socket socket, String mensagem) {
        try {
            PrintWriter saida = new PrintWriter(socket.getOutputStream(), true);
            saida.println(mensagem);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static String receberMensagem(Socket socket) {
        try {
            BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            return entrada.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static String solicitarFruta(String ip, int porta) {
        try (Socket socket = new Socket(ip, porta)) {
            // Enviar solicitação
            enviarMensagem(socket, "SOLICITAR_FRUTA");
            
            // Receber resposta
            String fruta = receberMensagem(socket);
            
            return fruta;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
