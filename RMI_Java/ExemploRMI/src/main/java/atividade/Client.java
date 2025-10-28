package atividade;
import java.rmi.Naming;
import java.util.Scanner;

public class Client {
    public static void main(String args[]) {
        
        String lookupURL = "rmi://localhost/TrataNomes";
        Scanner scanner = new Scanner(System.in);

        try {
            ITrataNomes i = (ITrataNomes) Naming.lookup(lookupURL);
            System.out.println("Cliente RMI conectado...");
            System.out.print("Digite um nome completo: ");
            String nomeCompleto = scanner.nextLine();
            
            System.out.println("Enviando nome: " + nomeCompleto + "\n");

            String email = i.gerarEmail(nomeCompleto);
            String primeiroNome = i.pegarNome(nomeCompleto);
            String sobrenome = i.pegarSobrenome(nomeCompleto);

            System.out.println("--- Resultados do Servidor ---");
            System.out.println("Primeiro Nome: " + primeiroNome);
            System.out.println("Sobrenome: " + sobrenome);
            System.out.println("Email: " + email);

        } catch (Exception e) {
            System.out.println("Error no cliente: " + e);
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }
}