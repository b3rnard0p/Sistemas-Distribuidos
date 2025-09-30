import java.util.Random;
import javax.swing.JButton;
import javax.swing.JSeparator;

public class Movimenta {
    public static void irEsquerda(JButton botao) {
        int x = botao.getBounds().x - 20;
        if (x < 0) return;
        botao.setBounds(x, botao.getBounds().y, botao.getBounds().width, botao.getBounds().height);
    }
    
    public static void irCima(JButton botao, JSeparator separador) {
        int limiteY = separador.getBounds().y + separador.getBounds().height + 10; // 10px de margem
        int y = botao.getBounds().y - 20;
        if (y < limiteY) return;
        botao.setBounds(botao.getBounds().x, y, botao.getBounds().width, botao.getBounds().height);
    }
    
    public static void irDireita(JButton botao, int largura) {
        int x = botao.getBounds().x + 20;
        if (x > largura - botao.getBounds().width) return;
        botao.setBounds(x, botao.getBounds().y, botao.getBounds().width, botao.getBounds().height);
    }
    
    public static void irBaixo(JButton botao, int altura) {
        int y = botao.getBounds().y + 20;
        if (y > altura - botao.getBounds().height) return;
        botao.setBounds(botao.getBounds().x, y, botao.getBounds().width, botao.getBounds().height);
    }
    
    public static boolean pegou(JButton botao1, JButton botao2) {
        return botao1.getBounds().intersects(botao2.getBounds());
    }
    
    public static void posicionaAleatorio(JButton botao, int largura, int altura, JSeparator separador) {
        Random gerador = new Random();
        int limiteY = separador.getBounds().y + separador.getBounds().height + 10; // 10px de margem
        int alturaJogo = altura - limiteY;
        
        int x = gerador.nextInt(largura - botao.getBounds().width);
        int y = limiteY + gerador.nextInt(alturaJogo - botao.getBounds().height);
        botao.setBounds(x, y, botao.getBounds().width, botao.getBounds().height);   
    }
    
    // Verificar colisão com obstáculos
    public static boolean colidiu(JButton jogador, JButton obstaculo1, JButton obstaculo2, JButton obstaculo3) {
        return jogador.getBounds().intersects(obstaculo1.getBounds()) ||
               jogador.getBounds().intersects(obstaculo2.getBounds()) ||
               jogador.getBounds().intersects(obstaculo3.getBounds());
    }
    
    // Reposicionar obstáculos em posições aleatórias (apenas abaixo do separador)
    public static void reposicionarObstaculos(JButton obstaculo1, JButton obstaculo2, JButton obstaculo3, 
                                            JButton jogador1, JButton jogador2, JButton fruta, 
                                            int largura, int altura, JSeparator separador) {
        Random gerador = new Random();
        int limiteY = separador.getBounds().y + separador.getBounds().height + 10; // 10px de margem
        int alturaJogo = altura - limiteY;
        
        // Reposicionar obstáculo 1
        do {
            posicionaAleatorio(obstaculo1, largura, altura, separador);
        } while (pegou(obstaculo1, jogador1) || pegou(obstaculo1, jogador2) || pegou(obstaculo1, fruta));
        
        // Reposicionar obstáculo 2
        do {
            posicionaAleatorio(obstaculo2, largura, altura, separador);
        } while (pegou(obstaculo2, jogador1) || pegou(obstaculo2, jogador2) || pegou(obstaculo2, fruta) || pegou(obstaculo2, obstaculo1));
        
        // Reposicionar obstáculo 3
        do {
            posicionaAleatorio(obstaculo3, largura, altura, separador);
        } while (pegou(obstaculo3, jogador1) || pegou(obstaculo3, jogador2) || pegou(obstaculo3, fruta) || 
                 pegou(obstaculo3, obstaculo1) || pegou(obstaculo3, obstaculo2));
    }
}
