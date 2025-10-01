package Autenticador;

import javax.swing.JOptionPane;

public class ClienteInterface extends javax.swing.JFrame {

    public ClienteInterface() {
        initComponents();
        
        txtIPServer.setText("localhost");
        txtPortaServer.setText("1234");
        txtFruta.setEditable(false);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtIPServer = new javax.swing.JTextField();
        txtPortaServer = new javax.swing.JTextField();
        btnConectar = new javax.swing.JButton();
        txtFruta = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Cliente");

        jLabel2.setText("IP:");

        jLabel3.setText("Porta:");

        btnConectar.setText("Conectar");
        btnConectar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConectarActionPerformed(evt);
            }
        });

        txtFruta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFrutaActionPerformed(evt);
            }
        });

        jLabel4.setText("Fruta Atual");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(118, 118, 118))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(53, 53, 53)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtIPServer))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtPortaServer, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnConectar))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(txtFruta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(51, 51, 51)))
                .addContainerGap(53, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtIPServer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtPortaServer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnConectar))
                .addGap(51, 51, 51)
                .addComponent(jLabel4)
                .addGap(18, 18, 18)
                .addComponent(txtFruta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(17, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnConectarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConectarActionPerformed
        String ip = txtIPServer.getText().trim();
        String portaText = txtPortaServer.getText().trim();
        
        if (ip.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Digite o IP do servidor!");
            return;
        }
        
        if (portaText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Digite a porta do servidor!");
            return;
        }
        
        try {
            int porta = Integer.parseInt(portaText);
            btnConectar.setEnabled(false);
            btnConectar.setText("Conectando...");

            new Thread(() -> {
                String fruta = ComunicadorTCP.solicitarFruta(ip, porta);
                
                javax.swing.SwingUtilities.invokeLater(() -> {
                    if (fruta != null) {
                        txtFruta.setText(fruta);
                        JOptionPane.showMessageDialog(this, "Fruta recebida: " + fruta);
                    } else {
                        JOptionPane.showMessageDialog(this, "Erro ao conectar com o servidor!");
                    }
                    
                    btnConectar.setEnabled(true);
                    btnConectar.setText("Conectar");
                });
            }).start();
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Porta deve ser um número!");
            btnConectar.setEnabled(true);
            btnConectar.setText("Conectar");
        }
    }//GEN-LAST:event_btnConectarActionPerformed

    private void txtFrutaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFrutaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFrutaActionPerformed

    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ClienteInterface().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnConectar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JTextField txtFruta;
    private javax.swing.JTextField txtIPServer;
    private javax.swing.JTextField txtPortaServer;
    // End of variables declaration//GEN-END:variables
}
