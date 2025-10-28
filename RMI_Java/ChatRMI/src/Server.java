import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import javax.swing.JOptionPane;

public class Server {
    
    String HOST_URL = "rmi://10.104.12.4/Chat";
            
    public Server(){
        try {
            LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
            Chat objetoRemoto = new Chat();
            Naming.bind(HOST_URL, objetoRemoto);
            JOptionPane.showMessageDialog(null,"Servidor em execução e aguardando conexões");
        } catch (MalformedURLException | AlreadyBoundException | RemoteException ex) {
            JOptionPane.showMessageDialog(null,"Alguma exceção gerada....");
        }
    }
    
    public static void main(String args[]){
        new Server();
    }
}
