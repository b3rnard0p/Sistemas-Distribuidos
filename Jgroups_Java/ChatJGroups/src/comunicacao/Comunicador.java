package comunicacao;

import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import org.jgroups.Address;
import org.jgroups.JChannel;
import org.jgroups.Message;
import org.jgroups.ReceiverAdapter;
import org.jgroups.View;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;
import org.jgroups.Event;
import org.jgroups.PhysicalAddress;
import org.jgroups.stack.IpAddress;

public class Comunicador extends ReceiverAdapter {

    JChannel channel;
    List<Address> listaMembros;
    String frase;
    Message mensagem;
    JFrame_chatJGROUPS meuFrame;
    StringBuffer membrosStringBuffer;

    public void iniciar(JFrame_chatJGROUPS meuFrame) throws Exception {

        System.setProperty("java.net.preferIPv4Stack", "true");//desabilita ipv6, para que só sejam aceitas conexões via ipv4
/*
  * JGroups utiliza um JChannel como principal forma de conectar
  * a um cluster/grupo. É atraves dele que enviaremos e recebermos mensagens
  * bem como registrar os eventos callback quando acontecer alguma
  * mudança (por exemplo, entrada de um membro no grupo).
  *  * Neste caso, criamos uma instancia deste objeto, utilizando configurações default.
  */
        this.channel = new JChannel();
        /*
  * Definimos através do método setReceiver qual classe implementará
  * o método callback receive, que será chamado toda vez que alguém
  * enviar uma mensagem ao cluster/grupo. Neste caso, a própria classe
  * implementa o método receive mais abaixo.
  */
        this.channel.setReceiver(this);
        /*
  * O método connect faz com que este processo entre no cluster/grupo ChatCluster.
  * Não há a necessidade de se criar explicitamente um cluster, pois o método connect
  * cria o cluster caso este seja o primeiro membro a entrar nele.
  */
        this.meuFrame = meuFrame;

        this.channel.setName(meuFrame.getjTextField_apelido().getText());
        this.channel.connect(meuFrame.getTitle());
        this.channel.getState(null, 0); 
    }

    public void enviar(String frase, String participante) {
        try {
            if (participante == null) {
                /*
  * cria uma instancia da classe Message do JGrupos com a mensagem.
  * O primeiro parâmetro é o endereço do destinatário. Caso seja null, a mensagem é enviada para todos do grupo
  * O segundo parâmetro é a mensagem enviada através de um buffer de bytes (convertida automaticamente)
  */
                this.mensagem = new Message(null, frase);
            } else {
                for (int i = 0; i < this.listaMembros.size(); i++) {
                    if (participante.equals(listaMembros.get(i).toString())) {
                        System.out.println("Achouuuu");
                        this.mensagem = new Message(listaMembros.get(i), frase);
                        break;
                    }
                }
            }
            /*
 * envia a mensagem montada acima ao grupo
  */
            this.channel.send(this.mensagem);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(meuFrame, "Algo ocorreu de errrado ao enviar sua mensagem!!");
        }
    }

    public void finalizar() {
        this.channel.close();
    }

    /*
  * O método abaixo é callback, e é chamado toda vez que alguem
  * envia uma mensagem ao processo/grupo/canal. Esta mensagem é recebida no parâmetro
  * Message msg. Nessa implementação, mostramos na tela o originador
  * da mensagem em msg.getSrc() e a mensagem propriamente dita em
  * msg.getObject
  */
    @Override
    public void receive(Message msg) {
        Date dt = new Date();
        final String msgFormatada = "[" + dt.toString() + "] " + msg.getSrc() + " disse: "
                + msg.getObject().toString() + "\n";

        javax.swing.SwingUtilities.invokeLater(() -> {
            this.meuFrame.getjTextArea_mensagensGerais().append(msgFormatada);
        });
    }

    /*
  * O método abaixo é callback, e é chamado toda vez que uma nova
  * instancia entra no grupo, ou se alguma instancia sai do grupo.
  * Ele recebe uma View como parâmetro. Este objeto possui informações
  * sobre todos os membros do grupo.
  * Na nossa implementação, quando damos um print no objeto new_view
  * ele mostra, respectivamente:
  * [Criador do grupo | ID da View] [Membros do grupo]
  *  * Cada View possui uma ID, que a identifica. 
  * O ID da View é um Relógio de Lamport que marca a ocorrência de eventos.
  */
    @Override
    public void viewAccepted(View view_atual) {
        List<Address> novaListaMembros = view_atual.getMembers();

        if (this.listaMembros != null) {
            for (Address membroAntigo : this.listaMembros) {
                if (!novaListaMembros.contains(membroAntigo)) {
                    final String msgSaida = "--- " + membroAntigo.toString() + " saiu do grupo ---\n";
                    javax.swing.SwingUtilities.invokeLater(() -> {
                        this.meuFrame.getjTextArea_mensagensGerais().append(msgSaida);
                    });
                }
            }
        }

        this.listaMembros = novaListaMembros;
        this.membrosStringBuffer = new StringBuffer();

        final List<String> nomesParaComboBox = new ArrayList<>();

        for (int i = 0; i < this.listaMembros.size(); i++) {
            Address logical_addr = this.listaMembros.get(i);
            String logical_name = logical_addr.toString();
            nomesParaComboBox.add(logical_name);

            String ip_display = "";
            try {
                PhysicalAddress physical_addr = (PhysicalAddress) channel.down(new Event(Event.GET_PHYSICAL_ADDRESS, logical_addr));
                if (physical_addr instanceof IpAddress) {
                    IpAddress ip_addr = (IpAddress) physical_addr;
                    ip_display = " (" + ip_addr.getIpAddress().getHostAddress() + ")";
                } else if (physical_addr != null) {
                    ip_display = " (" + physical_addr.toString() + ")";
                }
            } catch (Exception e) {
            }

            membrosStringBuffer.append(logical_name + ip_display + "\n");
        }

        final String textoListaMembros = membrosStringBuffer.toString();
        javax.swing.SwingUtilities.invokeLater(() -> {
            this.meuFrame.getjTextArea_listaMembros().setText(textoListaMembros);

            this.meuFrame.getjComboBox_listaParticipantesGrupo().removeAllItems();
            this.meuFrame.getjComboBox_listaParticipantesGrupo().addItem("Selecione o participante");
            for (String nome : nomesParaComboBox) {
                this.meuFrame.getjComboBox_listaParticipantesGrupo().addItem(nome);
            }
        });
    }

    /*
  * Este método callback é chamado toda vez que um membro é 
  * suspeito de ter falhado, porém ainda não foi excluído
  * do grupo. Esse método só é executado no coordenador do grupo.
  */
    @Override
    public void suspect(Address mbr) {
        JOptionPane.showMessageDialog(meuFrame, "PROCESSO SUSPEITO DE FALHA: " + mbr);
    }

    @Override
    public void getState(OutputStream output) throws Exception {
        final AtomicReference<String> estado = new AtomicReference<>();

        try {
            javax.swing.SwingUtilities.invokeAndWait(() -> {
                estado.set(meuFrame.getjTextArea_mensagensGerais().getText());
            });
        } catch (Exception e) {
            throw new RuntimeException("Falha ao ler o estado da GUI (JTextArea)", e);
        }
        try (ObjectOutputStream oos = new ObjectOutputStream(output)) {
            oos.writeObject(estado.get());
            oos.flush();
        }
    }

    @Override
    public void setState(InputStream input) throws Exception {
        final String estado;
        try (ObjectInputStream ois = new ObjectInputStream(input)) {
            estado = (String) ois.readObject();
        }
        javax.swing.SwingUtilities.invokeLater(() -> {
            meuFrame.getjTextArea_mensagensGerais().setText(estado);
        });
    }
}
