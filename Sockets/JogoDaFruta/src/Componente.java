import java.io.Serializable;
/**
 *
 * @author alexandrezamberlan
 */
public class Componente implements Serializable {
    public static final int FRUTA = 1, JOGADOR = 2, OBSTACULO = 3, PLACAR = 4, GAME_OVER = 5, ESTADO_COMPLETO = 6;
    public int x;
    public int y;
    public int largura;
    public int altura;
    public int tipo;
    public int pontuacaoJ1;
    public int pontuacaoJ2;
    public int numeroObstaculo; // para identificar qual obstáculo (1, 2 ou 3)
    public String vencedor; // para mensagem de game over

    // Para estado completo
    public int jogador1X, jogador1Y, jogador1W, jogador1H;
    public int jogador2X, jogador2Y, jogador2W, jogador2H;
    public int frutaX, frutaY, frutaW, frutaH;
    public int obs1X, obs1Y, obs1W, obs1H;
    public int obs2X, obs2Y, obs2W, obs2H;
    public int obs3X, obs3Y, obs3W, obs3H;

    public Componente(int x, int y, int largura, int altura) {
        this.x = x;
        this.y = y;
        this.largura = largura;
        this.altura = altura;
    }
    
    // Construtor para placar
    public Componente(int pontuacaoJ1, int pontuacaoJ2) {
        this.pontuacaoJ1 = pontuacaoJ1;
        this.pontuacaoJ2 = pontuacaoJ2;
        this.tipo = PLACAR;
    }
    
    // Construtor para estado completo
    public Componente() {
        this.tipo = ESTADO_COMPLETO;
    }
}
