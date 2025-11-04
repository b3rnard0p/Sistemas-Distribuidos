package chatMulticast;

import java.awt.HeadlessException;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.Iterator;
import java.util.LinkedList;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import util.ComunicadorUDP;
import javax.swing.DefaultListModel;
import javax.swing.SwingUtilities;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.HashMap;

public class JFrame_chatMulticast extends javax.swing.JFrame {

    public JFrame_chatMulticast() {
        initComponents();
        listModel = new DefaultListModel<>();
        listModel.addElement("Todos");
        jList1.setModel(listModel);
        jList1.setSelectedIndex(0);
        userMap = new HashMap<>();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel_Chat = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea_Mensagens = new javax.swing.JTextArea();
        jTextField_textoDeEnvio = new javax.swing.JTextField();
        jButton_Enviar = new javax.swing.JButton();
        jLabel_Porta = new javax.swing.JLabel();
        jTextField_Porta = new javax.swing.JTextField();
        jLabel_ServidorIP = new javax.swing.JLabel();
        jButton_Sair = new javax.swing.JButton();
        jTextField_GrupoIP = new javax.swing.JTextField();
        jLabel_Nick = new javax.swing.JLabel();
        jTextField_Nick = new javax.swing.JTextField();
        jButton_Conectar = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTextArea_Mensagens.setEditable(false);
        jTextArea_Mensagens.setColumns(20);
        jTextArea_Mensagens.setRows(5);
        jTextArea_Mensagens.setFocusable(false);
        jScrollPane1.setViewportView(jTextArea_Mensagens);

        jTextField_textoDeEnvio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField_textoDeEnvioKeyPressed(evt);
            }
        });

        jButton_Enviar.setText("Enviar");
        jButton_Enviar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_EnviarActionPerformed(evt);
            }
        });

        jLabel_Porta.setText("Porta");

        jTextField_Porta.setText("3456");

        jLabel_ServidorIP.setText("Grupo IP:");

        jButton_Sair.setText("Sair");
        jButton_Sair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_SairActionPerformed(evt);
            }
        });

        jTextField_GrupoIP.setText("239.1.2.3");

        jLabel_Nick.setText("Nick:");

        jButton_Conectar.setText("Conectar");
        jButton_Conectar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_ConectarActionPerformed(evt);
            }
        });

        jScrollPane2.setViewportView(jList1);

        javax.swing.GroupLayout jPanel_ChatLayout = new javax.swing.GroupLayout(jPanel_Chat);
        jPanel_Chat.setLayout(jPanel_ChatLayout);
        jPanel_ChatLayout.setHorizontalGroup(
            jPanel_ChatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_ChatLayout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addComponent(jLabel_ServidorIP)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTextField_GrupoIP, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel_Porta)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTextField_Porta, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel_Nick)
                .addGap(18, 18, 18)
                .addComponent(jTextField_Nick, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(jButton_Conectar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
                .addComponent(jButton_Sair)
                .addGap(2, 2, 2))
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_ChatLayout.createSequentialGroup()
                .addGroup(jPanel_ChatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel_ChatLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 442, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jTextField_textoDeEnvio))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton_Enviar))
        );
        jPanel_ChatLayout.setVerticalGroup(
            jPanel_ChatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_ChatLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(13, 13, 13)
                .addGroup(jPanel_ChatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField_textoDeEnvio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton_Enviar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel_ChatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel_ServidorIP)
                    .addComponent(jTextField_GrupoIP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel_Porta)
                    .addComponent(jTextField_Porta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel_Nick)
                    .addComponent(jTextField_Nick, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton_Conectar)
                    .addComponent(jButton_Sair))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel_Chat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel_Chat, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    class UserInfo {
        String ip;
        int port;

        public UserInfo(String ip, int port) {
            this.ip = ip;
            this.port = port;
        }
    }

    class ThreadMulticastReceptora extends Thread {

        @Override
        public void run() {
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    DatagramPacket pacote = ComunicadorUDP.recebeMensagem(multicastSocket);
                    if (pacote == null) continue;
                    
                    String msgRecebida = new String(pacote.getData(), 0, pacote.getLength());
                    String senderIP = pacote.getAddress().getHostAddress();
 
                    //Quando a mensagem for de entrada
                    if (msgRecebida.startsWith("JOIN:")) {
                        try {
                            String[] parts = msgRecebida.split(":");
                            String nick = parts[1];
                            int port = Integer.parseInt(parts[2]);

                            // não permite adicionar a si mesmo na lista
                            if (!nick.equals(meuNick) && !userMap.containsKey(nick)) {
                                userMap.put(nick, new UserInfo(senderIP, port));
                                SwingUtilities.invokeLater(() -> listModel.addElement(nick));

                                //Garante que outros User salvem suas Infos
                                String msgResponse = "JOIN:" + meuNick + ":" + unicastSocket.getLocalPort();
                                DatagramPacket pacoteResp = ComunicadorUDP.montaMensagem(msgResponse, jTextField_GrupoIP.getText(), Integer.parseInt(jTextField_Porta.getText()));
                                multicastSocket.send(pacoteResp);
                            }
                        } catch (Exception e){}

                        //Remove da lista se estiver saindo
                    } else if (msgRecebida.startsWith("LEAVE:")) {
                        try {
                            String nick = msgRecebida.split(":")[1];
                            if (userMap.containsKey(nick)) {
                                userMap.remove(nick);
                                SwingUtilities.invokeLater(() -> listModel.removeElement(nick));
                            }
                        } catch (Exception e) {}

                        // A logica antiga da Thread
                    } else if (msgRecebida.startsWith("PUBLIC:")) {
                        try {
                            String[] parts = msgRecebida.split(":", 3);
                            String senderNick = parts[1];
                            String publicMsg = parts[2];
                            
                            if (!senderNick.equals(meuNick)) {
                                SwingUtilities.invokeLater(() -> jTextArea_Mensagens.append(senderNick + ": " + publicMsg + "\n"));
                            }
                        } catch (Exception e) {}
                        
                    } else if (msgRecebida.contains(" entrou na sala") || msgRecebida.contains(" saiu da sala")) {
                        SwingUtilities.invokeLater(() -> jTextArea_Mensagens.append(msgRecebida + "\n"));
                    }
                }
            } catch (IOException e) {
                if (multicastSocket != null && !multicastSocket.isClosed()) {
                    System.err.println("Erro no multicast: " + e.getMessage());
                } else {
                    System.out.println("Socket multicast fechado.");
                }
            }
        }
    }

    class ThreadUnicastReceptora extends Thread {

        @Override
        public void run() {
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    DatagramPacket pacote = ComunicadorUDP.recebeMensagem(unicastSocket);
                    if (pacote == null) continue;

                    String msgRecebida = new String(pacote.getData(), 0, pacote.getLength());

                    //Mesma logica da outra, mas é unicastSocket
                    if (msgRecebida.startsWith("PRIVATE:")) {
                        try {
                            String[] parts = msgRecebida.split(":", 3);
                            String senderNick = parts[1];
                            String privateMsg = parts[2];

                            String msgFormatada = "[Privado de " + senderNick + "]: " + privateMsg;
                            SwingUtilities.invokeLater(() -> jTextArea_Mensagens.append(msgFormatada + "\n"));

                        } catch (Exception e) {}
                    }
                }
            } catch (IOException e) {
                if (unicastSocket != null && !unicastSocket.isClosed()) {
                    System.err.println("Erro no unicast: " + e.getMessage());
                } else {
                    System.out.println("Socket unicast fechado.");
                }
            }
        }
    }
    
    private void sairDoSistema() throws IOException, NumberFormatException, NullPointerException {
        try {
            if (multicastSocket != null && !multicastSocket.isClosed() && meuNick != null) {
                String ipGrupo = jTextField_GrupoIP.getText();
                int portaMulticast = Integer.parseInt(jTextField_Porta.getText());

                String msgLeave = "LEAVE:" + meuNick;
                DatagramPacket pacoteLeave = ComunicadorUDP.montaMensagem(msgLeave, ipGrupo, portaMulticast);
                multicastSocket.send(pacoteLeave);

                String msgLog = meuNick + " saiu da sala";
                DatagramPacket pacoteLog = ComunicadorUDP.montaMensagem(msgLog, ipGrupo, portaMulticast);
                multicastSocket.send(pacoteLog);

                if (threadMulticast != null) threadMulticast.interrupt();
                if (threadUnicast != null) threadUnicast.interrupt();
                
                if (grupo != null) multicastSocket.leaveGroup(grupo);
                
                multicastSocket.close();
                unicastSocket.close();
            }
        } catch (IOException | NumberFormatException | NullPointerException e) {
            if (e.getClass().toString().equals("class java.lang.NullPointerException") && multicastSocket == null) {
                JOptionPane.showMessageDialog(this, "Você está saindo sem ter se conectado");
            } else if (!e.getMessage().equalsIgnoreCase("Socket closed")) {
                JOptionPane.showMessageDialog(this, e.getMessage());
            }
        } finally {
            System.exit(0);
        }
    }

    private void jButton_ConectarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_ConectarActionPerformed
       try {
            if (jTextField_Nick.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "Preencha seu nick");
                return;
            }
            
            meuNick = jTextField_Nick.getText();
            String ipGrupo = jTextField_GrupoIP.getText();
            int portaMulticast = Integer.parseInt(jTextField_Porta.getText());

            // 1. DEFINO O IP DO GRUPO
            grupo = InetAddress.getByName(ipGrupo);

            // 2. CRIO O SOCKET MULTICAST
            multicastSocket = new MulticastSocket(portaMulticast);
            
            // 3. CRIO O SOCKET UNICAST (em qualquer porta livre)
            unicastSocket = new DatagramSocket();
            int minhaPortaUnicast = unicastSocket.getLocalPort();
            
            // 4. ENTRA NO GRUPO MULTICAST
            multicastSocket.joinGroup(grupo);

            // 5. CRIO AS THREADS PARA RECEBER MENSAGENS
            threadMulticast = new ThreadMulticastReceptora();
            threadMulticast.start();
            
            threadUnicast = new ThreadUnicastReceptora();
            threadUnicast.start();

            JOptionPane.showMessageDialog(this, "Conectado com sucesso na porta Unicast: " + minhaPortaUnicast);
            jButton_Conectar.setEnabled(false);
            jTextField_Nick.setEnabled(false);
            jTextField_GrupoIP.setEnabled(false);
            jTextField_Porta.setEnabled(false);

            // 6. ANUNCIA MINHA PRESENÇA (via Multicast)
            // Formato: JOIN:Nick:PortaUnicast
            String msgJoin = "JOIN:" + meuNick + ":" + minhaPortaUnicast;
            DatagramPacket pacoteJoin = ComunicadorUDP.montaMensagem(msgJoin, ipGrupo, portaMulticast);
            multicastSocket.send(pacoteJoin);

            // 7. MANDA MENSAGEM DE "entrou na sala" (via Multicast)
            String msgLog = meuNick + " entrou na sala";
            DatagramPacket pacoteLog = ComunicadorUDP.montaMensagem(msgLog, ipGrupo, portaMulticast);
            multicastSocket.send(pacoteLog);
            
            jTextField_textoDeEnvio.requestFocus();
            
        } catch (HeadlessException | IOException | NumberFormatException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
            // Limpa em caso de falha
            if (multicastSocket != null) multicastSocket.close();
            if (unicastSocket != null) unicastSocket.close();
        }
    }//GEN-LAST:event_jButton_ConectarActionPerformed

    private void enviarMsg() throws IOException, NumberFormatException, NullPointerException {
            try {
                //Quem vou enviar
                String selectedUser = jList1.getSelectedValue();
                // O que vou enviar
                String textoParaEnvio = jTextField_textoDeEnvio.getText();

                if (selectedUser == null) {
                    JOptionPane.showMessageDialog(this, "Selecione 'Todos' ou um usuário.");
                    return;
                }

                DatagramPacket pacote;

                if (selectedUser.equals("Todos")) {
                    String msg = "PUBLIC:" + meuNick + ":" + textoParaEnvio;
                    pacote = ComunicadorUDP.montaMensagem(msg, jTextField_GrupoIP.getText(), Integer.parseInt(jTextField_Porta.getText()));
                    multicastSocket.send(pacote);

                    jTextArea_Mensagens.append(meuNick + ": " + textoParaEnvio + "\n");

                } else {
                    UserInfo recipientInfo = userMap.get(selectedUser);
                    if (recipientInfo == null) {
                        JOptionPane.showMessageDialog(this, "Erro: Usuário não encontrado no mapa.");
                        return;
                    }
                    String msg = "PRIVATE:" + meuNick + ":" + textoParaEnvio;
                    pacote = ComunicadorUDP.montaMensagem(msg, recipientInfo.ip, recipientInfo.port);

                    unicastSocket.send(pacote); 
                    jTextArea_Mensagens.append("[Para " + selectedUser + "]: " + textoParaEnvio + "\n");
                }

                jTextField_textoDeEnvio.setText("");

            } catch (IOException | NumberFormatException | NullPointerException e) {
                if (e.getClass().toString().equals("class java.lang.NullPointerException") && multicastSocket == null) {
                    JOptionPane.showMessageDialog(this, "Você precisa conectar antes de enviar msgs");
                } else {
                    JOptionPane.showMessageDialog(this, e.getMessage());
                }
            }
    }

    private void jButton_EnviarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_EnviarActionPerformed
        try {
            this.enviarMsg();
            jTextField_textoDeEnvio.requestFocus();
        } catch (IOException | NumberFormatException | NullPointerException e) {

        }
    }//GEN-LAST:event_jButton_EnviarActionPerformed

    private void jButton_SairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_SairActionPerformed
        try {
            this.sairDoSistema();
        } catch (IOException | NumberFormatException | NullPointerException e) {

        }
    }//GEN-LAST:event_jButton_SairActionPerformed

    private void jTextField_textoDeEnvioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_textoDeEnvioKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            try {
                this.enviarMsg();
            } catch (IOException | NumberFormatException | NullPointerException e) {

            }
        }

        if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            try {
                this.sairDoSistema();
            } catch (IOException | NumberFormatException | NullPointerException e) {

            }
        }
    }//GEN-LAST:event_jTextField_textoDeEnvioKeyPressed

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new JFrame_chatMulticast().setVisible(true);
            }
        });
    }

    InetAddress grupo;
    MulticastSocket multicastSocket;
    DatagramSocket unicastSocket;
    
    DefaultListModel<String> listModel;
    HashMap<String, UserInfo> userMap;
    
    ThreadMulticastReceptora threadMulticast;
    ThreadUnicastReceptora threadUnicast; 
    String meuNick; 
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton_Conectar;
    private javax.swing.JButton jButton_Enviar;
    private javax.swing.JButton jButton_Sair;
    private javax.swing.JLabel jLabel_Nick;
    private javax.swing.JLabel jLabel_Porta;
    private javax.swing.JLabel jLabel_ServidorIP;
    private javax.swing.JList<String> jList1;
    private javax.swing.JPanel jPanel_Chat;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTextArea_Mensagens;
    private javax.swing.JTextField jTextField_GrupoIP;
    private javax.swing.JTextField jTextField_Nick;
    private javax.swing.JTextField jTextField_Porta;
    private javax.swing.JTextField jTextField_textoDeEnvio;
    // End of variables declaration//GEN-END:variables
}
