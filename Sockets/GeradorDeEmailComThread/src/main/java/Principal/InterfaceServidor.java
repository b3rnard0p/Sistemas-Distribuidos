package Principal;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import javax.swing.SwingUtilities;

public class InterfaceServidor extends javax.swing.JFrame {

    public InterfaceServidor() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTextField1 = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtLista = new javax.swing.JTextArea();
        btnIniciar = new javax.swing.JButton();

        jTextField1.setText("jTextField1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        txtLista.setColumns(20);
        txtLista.setRows(5);
        jScrollPane1.setViewportView(txtLista);

        btnIniciar.setText("Iniciar");
        btnIniciar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIniciarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(155, 155, 155)
                .addComponent(btnIniciar)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 388, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnIniciar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 291, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variáveis do servidor para gerenciar múltiplos clientes
    private ArrayList<Pessoa> lista = new ArrayList<>();  // Lista compartilhada de pessoas cadastradas
    private ServerSocket servidor;  // Socket do servidor para aceitar conexões

    private void btnIniciarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIniciarActionPerformed
    btnIniciar.setEnabled(false);
    btnIniciar.setText("Servidor Rodando...");

    Thread servidorThread = new Thread(new Runnable() {
        @Override
        public void run() {
            try {
                int portaServidor = 50000;
                servidor = new ServerSocket(portaServidor);

                final String msgInicial = "Servidor ouvindo na porta: " + portaServidor;
                SwingUtilities.invokeLater(() -> txtLista.setText(msgInicial));

                while (true) {
                    Socket cliente = servidor.accept();
                    
                    SwingUtilities.invokeLater(() -> {
                        txtLista.append("Cliente conectado: " + cliente.getInetAddress() + "\n");
                    });
                    Thread clienteThread = new Thread(() -> processarCliente(cliente));
                    clienteThread.start();
                }

            } catch (IOException e) {
                String msgErro = "Erro no servidor: " + e.getMessage();
                SwingUtilities.invokeLater(() -> txtLista.setText(msgErro));

                 java.awt.EventQueue.invokeLater(() -> {
                     btnIniciar.setEnabled(true);
                     btnIniciar.setText("Iniciar Servidor");
                 });
            }
        }
    });
    servidorThread.start();
    }//GEN-LAST:event_btnIniciarActionPerformed

    private void processarCliente(Socket cliente) {
        try {
            ThreadRecebedora recebedora = new ThreadRecebedora(cliente, lista, this);
            recebedora.start();
            
        } catch (Exception e) {
            String msgErro = "Erro ao processar cliente: " + e.getMessage();
            SwingUtilities.invokeLater(() -> txtLista.append(msgErro + "\n"));
        }
    }

    public void atualizarListaNaUI() {
        StringBuilder textoDaLista = new StringBuilder();
        textoDaLista.append("--- Clientes na base ---\n");
        
        synchronized (lista) {
            for (Pessoa pessoa : lista) {
                textoDaLista.append(pessoa.toString()).append("\n");
            }
        }
        textoDaLista.append("------------------------\n");

        SwingUtilities.invokeLater(() -> {
            txtLista.setText(textoDaLista.toString());
        });
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new InterfaceServidor().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnIniciar;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextArea txtLista;
    // End of variables declaration//GEN-END:variables
}
