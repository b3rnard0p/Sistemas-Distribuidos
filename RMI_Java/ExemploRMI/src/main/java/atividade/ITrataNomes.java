package atividade;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ITrataNomes extends Remote {

    public String gerarEmail(String nome) throws RemoteException;

    public String pegarSobrenome(String nome) throws RemoteException;

    public String pegarNome(String nome) throws RemoteException;
}