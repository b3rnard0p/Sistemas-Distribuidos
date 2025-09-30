import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.JOptionPane;

/**
 *
 * @author alexandrezamberlan
 */
public class ServidorJogador1 extends javax.swing.JFrame {

    private int pontuacaoJ1 = 0;
    private int pontuacaoJ2 = 0;
    private static final int PONTUACAO_VITORIA = 10;
    private boolean jogoTerminado = false;
    private boolean colisaoJ1UltimoMovimento = false;
    private boolean colisaoJ2UltimoMovimento = false;

    /**
     * Creates new form ServidorJogador1
     */
    public ServidorJogador1() {
        initComponents();
        
        // Configurar obstáculos iniciais
        btnObstaculo1.setText("X");
        btnObstaculo1.setBackground(java.awt.Color.RED);
        btnObstaculo2.setText("X");
        btnObstaculo2.setBackground(java.awt.Color.RED);
        btnObstaculo3.setText("X");
        btnObstaculo3.setBackground(java.awt.Color.RED);
        
        // Inicializar placar
        txtPlacarServidor.setText("0");
        txtPlacarJogador.setText("0");
        txtPlacarServidor.setEditable(false);
        txtPlacarJogador.setEditable(false);
        
        // Posicionar fruta inicialmente
        Movimenta.posicionaAleatorio(jButtonFruta, 
                jPanelJogador1.getBounds().width, 
                jPanelJogador1.getBounds().height, jSeparator1);
        
        // Posicionar obstáculos inicialmente (apenas abaixo do separador)
        Movimenta.reposicionarObstaculos(btnObstaculo1, btnObstaculo2, btnObstaculo3,
                                       jButtonJogador1, jButtonJogador2, jButtonFruta,
                                       jPanelJogador1.getBounds().width, jPanelJogador1.getBounds().height, jSeparator1);

        new Thread() {
            @Override
            public void run() {
                try {
                    System.out.println("Servidor esperando o cliente conectar-se...porta 12345");
                    servidor = new ServerSocket(12345);

                    socket_jogador2 = servidor.accept();
                    
                    saida = new ObjectOutputStream(socket_jogador2.getOutputStream());
                    entrada = new ObjectInputStream(socket_jogador2.getInputStream());
                    System.out.println("Cliente conectado: " + socket_jogador2.getInetAddress().getHostAddress());

                    // Enviar estado inicial completo
                    enviarEstadoCompleto();
                    
                    while (true) {
                        c = ComunicadorTCP.receberComponente(entrada);
                        
                        if (c == null || jogoTerminado) continue;
                        
                        if (c.tipo == Componente.JOGADOR && c.numeroObstaculo == 2) {
                            // Processar comando de movimento do jogador 2
                            int comando = c.x; // O comando foi enviado no campo x
                            
                            switch (comando) {
                                case 37:
                                    Movimenta.irEsquerda(jButtonJogador2);
                                    break;
                                case 38:
                                    Movimenta.irCima(jButtonJogador2, jSeparator1);
                                    break;
                                case 39:
                                    Movimenta.irDireita(jButtonJogador2, jPanelJogador1.getBounds().width);
                                    break;
                                case 40:
                                    Movimenta.irBaixo(jButtonJogador2, jPanelJogador1.getBounds().height);
                                    break;
                            }
                            
                            // Verificar colisão do jogador 2 com obstáculos - APENAS DESCONTA PONTOS
                            if (Movimenta.colidiu(jButtonJogador2, btnObstaculo1, btnObstaculo2, btnObstaculo3)) {
                                if (!colisaoJ2UltimoMovimento) {
                                    colisaoJ2UltimoMovimento = true;
                                    if (pontuacaoJ2 > 0) {
                                        pontuacaoJ2--;
                                        atualizarPlacar();
                                        javax.swing.SwingUtilities.invokeLater(() -> {
                                            JOptionPane.showMessageDialog(ServidorJogador1.this, "Jogador 2 bateu em obstáculo! Perdeu 1 ponto.");
                                        });
                                    }
                                    Movimenta.reposicionarObstaculos(btnObstaculo1, btnObstaculo2, btnObstaculo3,
                                                                   jButtonJogador1, jButtonJogador2, jButtonFruta,
                                                                   jPanelJogador1.getBounds().width, jPanelJogador1.getBounds().height, jSeparator1);
                                }
                            } else {
                                colisaoJ2UltimoMovimento = false;
                            }
                            
                            // Verificar se jogador 2 pegou a fruta
                            if (Movimenta.pegou(jButtonJogador2, jButtonFruta)) {
                                pontuacaoJ2++;
                                atualizarPlacar();
                                
                                if (pontuacaoJ2 >= PONTUACAO_VITORIA) {
                                    gameOver("Jogador 2");
                                } else {
                                    Movimenta.posicionaAleatorio(jButtonFruta,
                                            jPanelJogador1.getBounds().width,
                                            jPanelJogador1.getBounds().height, jSeparator1);
                                    
                                    Movimenta.reposicionarObstaculos(btnObstaculo1, btnObstaculo2, btnObstaculo3,
                                                                   jButtonJogador1, jButtonJogador2, jButtonFruta,
                                                                   jPanelJogador1.getBounds().width, jPanelJogador1.getBounds().height, jSeparator1);
                                }
                            }
                            
                            // SEMPRE enviar TUDO após movimento do jogador 2
                            enviarEstadoCompleto();
                        }
                    }
                } catch (Exception e) {
                    System.out.println("Erro: " + e.getMessage());
                }
            }
        }.start();
    }
    
    private void enviarEstadoCompleto() {
        if (saida == null) return;
        
        c = ComunicadorTCP.criarEstadoCompleto(
            jButtonJogador1.getBounds().x, jButtonJogador1.getBounds().y, 
            jButtonJogador1.getBounds().width, jButtonJogador1.getBounds().height,
            
            jButtonJogador2.getBounds().x, jButtonJogador2.getBounds().y, 
            jButtonJogador2.getBounds().width, jButtonJogador2.getBounds().height,
            
            jButtonFruta.getBounds().x, jButtonFruta.getBounds().y, 
            jButtonFruta.getBounds().width, jButtonFruta.getBounds().height,
            
            btnObstaculo1.getBounds().x, btnObstaculo1.getBounds().y, 
            btnObstaculo1.getBounds().width, btnObstaculo1.getBounds().height,
            
            btnObstaculo2.getBounds().x, btnObstaculo2.getBounds().y, 
            btnObstaculo2.getBounds().width, btnObstaculo2.getBounds().height,
            
            btnObstaculo3.getBounds().x, btnObstaculo3.getBounds().y, 
            btnObstaculo3.getBounds().width, btnObstaculo3.getBounds().height,
            
            pontuacaoJ1, pontuacaoJ2
        );
        
        ComunicadorTCP.enviarComponente(saida, c);
    }
    
    private void atualizarPlacar() {
        txtPlacarServidor.setText(String.valueOf(pontuacaoJ1));
        txtPlacarJogador.setText(String.valueOf(pontuacaoJ2));
        // Não enviar separadamente - será enviado no estado completo
    }
    
    private void gameOver(String vencedor) {
        jogoTerminado = true;
        JOptionPane.showMessageDialog(this, vencedor + " venceu com " + PONTUACAO_VITORIA + " pontos!");
        
        // Enviar mensagem de game over para o cliente
        c = ComunicadorTCP.criarComponenteGameOver(vencedor);
        ComunicadorTCP.enviarComponente(saida, c);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanelJogador1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtPlacarServidor = new javax.swing.JTextField();
        txtPlacarJogador = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        jButtonJogador1 = new javax.swing.JButton();
        jButtonFruta = new javax.swing.JButton();
        jButtonJogador2 = new javax.swing.JButton();
        btnObstaculo1 = new javax.swing.JButton();
        btnObstaculo2 = new javax.swing.JButton();
        btnObstaculo3 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Jogador 1 - SERVIDOR");

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel1.setText("x");

        jButtonJogador1.setText("j1");
        jButtonJogador1.setFocusable(false);

        jButtonFruta.setBackground(new java.awt.Color(255, 204, 0));
        jButtonFruta.setText("@");
        jButtonFruta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jButtonFrutaKeyPressed(evt);
            }
        });

        jButtonJogador2.setText("j2");
        jButtonJogador2.setFocusable(false);

        javax.swing.GroupLayout jPanelJogador1Layout = new javax.swing.GroupLayout(jPanelJogador1);
        jPanelJogador1.setLayout(jPanelJogador1Layout);
        jPanelJogador1Layout.setHorizontalGroup(
            jPanelJogador1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelJogador1Layout.createSequentialGroup()
                .addComponent(jSeparator1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanelJogador1Layout.createSequentialGroup()
                .addGroup(jPanelJogador1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelJogador1Layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addGroup(jPanelJogador1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelJogador1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnObstaculo3, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(67, 67, 67)
                                .addGroup(jPanelJogador1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnObstaculo2, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButtonJogador2)))
                            .addGroup(jPanelJogador1Layout.createSequentialGroup()
                                .addGroup(jPanelJogador1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jButtonJogador1)
                                    .addGroup(jPanelJogador1Layout.createSequentialGroup()
                                        .addGap(57, 57, 57)
                                        .addComponent(btnObstaculo1, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanelJogador1Layout.createSequentialGroup()
                                        .addGap(144, 144, 144)
                                        .addComponent(jButtonFruta)))
                                .addGap(174, 174, 174))))
                    .addGroup(jPanelJogador1Layout.createSequentialGroup()
                        .addGap(137, 137, 137)
                        .addComponent(txtPlacarServidor, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtPlacarJogador, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(19, Short.MAX_VALUE))
        );
        jPanelJogador1Layout.setVerticalGroup(
            jPanelJogador1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelJogador1Layout.createSequentialGroup()
                .addGap(76, 76, 76)
                .addGroup(jPanelJogador1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtPlacarServidor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtPlacarJogador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 84, Short.MAX_VALUE)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonJogador1)
                .addGroup(jPanelJogador1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelJogador1Layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(btnObstaculo1, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanelJogador1Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(btnObstaculo2, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(jPanelJogador1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelJogador1Layout.createSequentialGroup()
                        .addGap(103, 103, 103)
                        .addComponent(jButtonJogador2))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelJogador1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonFruta)
                        .addGap(37, 37, 37)
                        .addComponent(btnObstaculo3, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(9, 9, 9)))
                .addGap(15, 15, 15))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelJogador1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelJogador1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonFrutaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jButtonFrutaKeyPressed
        if (jogoTerminado) return;
        
        switch (evt.getKeyCode()) {
            case 37:
                Movimenta.irEsquerda(jButtonJogador1);
                break;
            case 38:
                Movimenta.irCima(jButtonJogador1, jSeparator1);
                break;
            case 39:
                Movimenta.irDireita(jButtonJogador1, jPanelJogador1.getBounds().width);
                break;
            case 40:
                Movimenta.irBaixo(jButtonJogador1, jPanelJogador1.getBounds().height);
                break;
        }
        
        // Verificar colisão do jogador 1 com obstáculos
        if (Movimenta.colidiu(jButtonJogador1, btnObstaculo1, btnObstaculo2, btnObstaculo3)) {
            if (!colisaoJ1UltimoMovimento) {
                colisaoJ1UltimoMovimento = true;
                if (pontuacaoJ1 > 0) {
                    pontuacaoJ1--;
                    atualizarPlacar();
                    JOptionPane.showMessageDialog(this, "Jogador 1 bateu em obstáculo! Perdeu 1 ponto.");
                }
                Movimenta.reposicionarObstaculos(btnObstaculo1, btnObstaculo2, btnObstaculo3,
                                               jButtonJogador1, jButtonJogador2, jButtonFruta,
                                               jPanelJogador1.getBounds().width, jPanelJogador1.getBounds().height, jSeparator1);
            }
        } else {
            colisaoJ1UltimoMovimento = false;
        }
        
        if (Movimenta.pegou(jButtonJogador1, jButtonFruta)) {
            pontuacaoJ1++;
            atualizarPlacar();
            
            if (pontuacaoJ1 >= PONTUACAO_VITORIA) {
                gameOver("Jogador 1");
                return;
            }
            
            Movimenta.posicionaAleatorio(jButtonFruta,
                    jPanelJogador1.getBounds().width,
                    jPanelJogador1.getBounds().height, jSeparator1);
                    
            Movimenta.reposicionarObstaculos(btnObstaculo1, btnObstaculo2, btnObstaculo3,
                                           jButtonJogador1, jButtonJogador2, jButtonFruta,
                                           jPanelJogador1.getBounds().width, jPanelJogador1.getBounds().height, jSeparator1);
        }
        
        // SEMPRE enviar TUDO após movimento do jogador 1
        if (saida != null) {
            enviarEstadoCompleto();
        }
    }//GEN-LAST:event_jButtonFrutaKeyPressed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ServidorJogador1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ServidorJogador1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ServidorJogador1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ServidorJogador1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ServidorJogador1().setVisible(true);
            }
        });
    }
    
    ServerSocket servidor;
    Socket socket_jogador2;
    ObjectOutputStream saida;
    ObjectInputStream entrada;
    Componente c;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnObstaculo1;
    private javax.swing.JButton btnObstaculo2;
    private javax.swing.JButton btnObstaculo3;
    private javax.swing.JButton jButtonFruta;
    private javax.swing.JButton jButtonJogador1;
    private javax.swing.JButton jButtonJogador2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanelJogador1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextField txtPlacarJogador;
    private javax.swing.JTextField txtPlacarServidor;
    // End of variables declaration//GEN-END:variables
}
