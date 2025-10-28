package atividade;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server {
    String HOST_URL = "rmi://localhost/TrataNomes";

    public Server() {
        try {
            LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
            TrataNomes objetoRemoto = new TrataNomes();

            System.out.println("Servidor TrataNomes pronto para atender clientes....");
            Naming.bind(HOST_URL, objetoRemoto);
            
        } catch (Exception e) {
            System.out.println("Error no servidor: " + e);
            e.printStackTrace();
        }
    }
    public static void main(String args[]) {
        new Server();
    }
}