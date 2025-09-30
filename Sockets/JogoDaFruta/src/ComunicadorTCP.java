import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ComunicadorTCP {
    
    public static void enviarComponente(ObjectOutputStream saida, Componente componente) {
        try {
            saida.flush();
            saida.writeObject(componente);
        } catch (Exception e) {
            System.out.println("Erro ao enviar componente: " + e.getMessage());
        }
    }
    
    public static Componente receberComponente(ObjectInputStream entrada) {
        try {
            return (Componente) entrada.readObject();
        } catch (Exception e) {
            System.out.println("Erro ao receber componente: " + e.getMessage());
            return null;
        }
    }
    
    public static Componente criarComponenteJogador(int x, int y, int largura, int altura) {
        Componente c = new Componente(x, y, largura, altura);
        c.tipo = Componente.JOGADOR;
        return c;
    }
    
    public static Componente criarComponenteFruta(int x, int y, int largura, int altura) {
        Componente c = new Componente(x, y, largura, altura);
        c.tipo = Componente.FRUTA;
        return c;
    }
    
    public static Componente criarComponenteObstaculo(int x, int y, int largura, int altura, int numeroObstaculo) {
        Componente c = new Componente(x, y, largura, altura);
        c.tipo = Componente.OBSTACULO;
        c.numeroObstaculo = numeroObstaculo;
        return c;
    }
    
    public static Componente criarComponentePlacar(int pontuacaoJ1, int pontuacaoJ2) {
        return new Componente(pontuacaoJ1, pontuacaoJ2);
    }
    
    public static Componente criarComponenteGameOver(String vencedor) {
        Componente c = new Componente(0, 0, 0, 0);
        c.tipo = Componente.GAME_OVER;
        c.vencedor = vencedor;
        return c;
    }
    
    public static Componente criarEstadoCompleto(
            int j1X, int j1Y, int j1W, int j1H,
            int j2X, int j2Y, int j2W, int j2H,
            int fX, int fY, int fW, int fH,
            int o1X, int o1Y, int o1W, int o1H,
            int o2X, int o2Y, int o2W, int o2H,
            int o3X, int o3Y, int o3W, int o3H,
            int pontos1, int pontos2) {
        
        Componente c = new Componente();
        c.tipo = Componente.ESTADO_COMPLETO;
        
        // Jogadores
        c.jogador1X = j1X; c.jogador1Y = j1Y; c.jogador1W = j1W; c.jogador1H = j1H;
        c.jogador2X = j2X; c.jogador2Y = j2Y; c.jogador2W = j2W; c.jogador2H = j2H;
        
        // Fruta
        c.frutaX = fX; c.frutaY = fY; c.frutaW = fW; c.frutaH = fH;
        
        // Obstáculos
        c.obs1X = o1X; c.obs1Y = o1Y; c.obs1W = o1W; c.obs1H = o1H;
        c.obs2X = o2X; c.obs2Y = o2Y; c.obs2W = o2W; c.obs2H = o2H;
        c.obs3X = o3X; c.obs3Y = o3Y; c.obs3W = o3W; c.obs3H = o3H;
        
        // Placar
        c.pontuacaoJ1 = pontos1;
        c.pontuacaoJ2 = pontos2;
        
        return c;
    }
}
