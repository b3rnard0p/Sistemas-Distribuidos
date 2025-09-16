package exemplo2_gerarEmail;

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

    private void btnIniciarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIniciarActionPerformed
    btnIniciar.setEnabled(false);
    btnIniciar.setText("Servidor Rodando...");

    Thread servidorThread = new Thread(new Runnable() {
        @Override
        public void run() {
            ArrayList<Pessoa> lista = new ArrayList<>();

            try {
                int portaServidor = 50000;
                ServerSocket servidor = new ServerSocket(portaServidor);

                final String msgInicial = "Servidor ouvindo na porta: " + portaServidor;
                SwingUtilities.invokeLater(() -> txtLista.setText(msgInicial));

                while (true) {
                    Socket cliente = servidor.accept();

                    ObjectInputStream entrada = new ObjectInputStream(cliente.getInputStream());
                    String nomePessoa = (String) entrada.readObject();

                    String vetorNome[] = nomePessoa.split(" ");
                    String email = vetorNome[0].toLowerCase() + "." + vetorNome[vetorNome.length - 1].toLowerCase() + "@ufn.edu.br";
                    Pessoa p = new Pessoa(nomePessoa.toUpperCase(), email);

                    boolean encontrado = false;
                    synchronized (lista) {
                        if (lista.contains(p)) {
                            encontrado = true;
                        } else {
                            lista.add(p);
                        }
                    }

                    ObjectOutputStream saida = new ObjectOutputStream(cliente.getOutputStream());
                    saida.writeObject(encontrado ? null : p);
                    saida.flush();
                    saida.close();
                    cliente.close();
                    atualizarListaNaUI(lista);
                }

            } catch (IOException | ClassNotFoundException e) {
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

    private void atualizarListaNaUI(ArrayList<Pessoa> lista) {
    StringBuilder textoDaLista = new StringBuilder();
    textoDaLista.append("--- Clientes na base ---\n");
    
    synchronized (lista) {
        for (Pessoa pessoa : lista) {
            textoDaLista.append(pessoa.toString()).append("\n");
        }
    }
    textoDaLista.append("------------------------\n");

    SwingUtilities.invokeLater(new Runnable() {
        @Override
        public void run() {
            txtLista.setText(textoDaLista.toString());
        }
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
