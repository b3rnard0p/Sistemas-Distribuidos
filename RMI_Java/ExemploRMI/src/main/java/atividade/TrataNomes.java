package atividade;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class TrataNomes extends UnicastRemoteObject implements ITrataNomes {

    public TrataNomes() throws RemoteException {
        super(); 
    }

    @Override
    public String gerarEmail(String nome) throws RemoteException {
        if (nome == null || nome.trim().isEmpty()) {
            return "Nome inválido";
        }
        String[] partes = nome.trim().split("\\s+");
        if (partes.length == 0) {
            return "Nome inválido";
        }

        String primeiroNome = partes[0].toLowerCase();
        String ultimoNome = partes[partes.length - 1].toLowerCase();

        return primeiroNome + "." + ultimoNome + "@ufn.edu.br";
    }

    @Override
    public String pegarSobrenome(String nome) throws RemoteException {
        if (nome == null || nome.trim().isEmpty()) {
            return "Nome inválido";
        }
        String[] partes = nome.trim().split("\\s+");
        if (partes.length == 0) {
            return "Nome inválido";
        }

        return partes[partes.length - 1];
    }

    @Override
    public String pegarNome(String nome) throws RemoteException {
        if (nome == null || nome.trim().isEmpty()) {
            return "Nome inválido";
        }
        String[] partes = nome.trim().split("\\s+");
        if (partes.length == 0) {
            return "Nome inválido";
        }

        return partes[0]; 
    }
}
