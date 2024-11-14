/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import programacao.model.Cliente;
import programacao.model.Estado;

/**
 *
 * @author Koroch
 */
public class CadastroCliente extends javax.swing.JFrame {
    private static List<Cliente> clientesCadastro = new ArrayList<>();
    /**
     * Creates new form CadastroCarro
     * @param clientes
     */
    public CadastroCliente(List<Cliente> clientes) {
        CadastroCliente.clientesCadastro = clientes;
        initComponents();
        for (Estado estado : Estado.values()) {
            jCBEstado.addItem(String.valueOf(estado));
        }
    }
    
    public List<Cliente> getListaAtualizadaClientes() {
        return new ArrayList<>(clientesCadastro);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLEstado = new javax.swing.JLabel();
        jTFCidade = new javax.swing.JTextField();
        jBRemoverEmp = new javax.swing.JButton();
        jBAlterarEmp = new javax.swing.JButton();
        jBBuscar = new javax.swing.JButton();
        jBLimpar = new javax.swing.JButton();
        jTFNome = new javax.swing.JTextField();
        jLNome = new javax.swing.JLabel();
        jCBEstado = new javax.swing.JComboBox<>();
        jLCidade = new javax.swing.JLabel();
        jBCriarEmp = new javax.swing.JButton();
        jLFundo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        getContentPane().setLayout(null);

        jLEstado.setForeground(new java.awt.Color(255, 255, 255));
        jLEstado.setText("Estado:");
        getContentPane().add(jLEstado);
        jLEstado.setBounds(280, 130, 90, 16);

        jTFCidade.setToolTipText("Informe o nome do colaborador.");
        jTFCidade.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTFCidadeActionPerformed(evt);
            }
        });
        getContentPane().add(jTFCidade);
        jTFCidade.setBounds(40, 150, 230, 30);

        jBRemoverEmp.setBackground(new java.awt.Color(204, 204, 204));
        jBRemoverEmp.setText("Remover");
        jBRemoverEmp.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jBRemoverEmp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBRemoverEmpActionPerformed(evt);
            }
        });
        getContentPane().add(jBRemoverEmp);
        jBRemoverEmp.setBounds(260, 250, 110, 30);

        jBAlterarEmp.setBackground(new java.awt.Color(204, 204, 204));
        jBAlterarEmp.setText("Alterar");
        jBAlterarEmp.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jBAlterarEmp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBAlterarEmpActionPerformed(evt);
            }
        });
        getContentPane().add(jBAlterarEmp);
        jBAlterarEmp.setBounds(150, 250, 110, 30);

        jBBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/lupa.png"))); // NOI18N
        jBBuscar.setToolTipText("Clique aqui para procurar!");
        jBBuscar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jBBuscar.setDefaultCapable(false);
        jBBuscar.setFocusPainted(false);
        jBBuscar.setFocusable(false);
        jBBuscar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jBBuscar.setIconTextGap(1);
        jBBuscar.setMaximumSize(new java.awt.Dimension(120, 120));
        jBBuscar.setMinimumSize(new java.awt.Dimension(120, 120));
        jBBuscar.setName(""); // NOI18N
        jBBuscar.setPreferredSize(new java.awt.Dimension(120, 120));
        jBBuscar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jBBuscarMouseClicked(evt);
            }
        });
        jBBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBBuscarActionPerformed(evt);
            }
        });
        getContentPane().add(jBBuscar);
        jBBuscar.setBounds(330, 80, 40, 40);

        jBLimpar.setText("Limpar");
        jBLimpar.setToolTipText("Limpar todos dados dos campos.");
        jBLimpar.setFocusable(false);
        jBLimpar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBLimparActionPerformed(evt);
            }
        });
        getContentPane().add(jBLimpar);
        jBLimpar.setBounds(280, 210, 90, 30);

        jTFNome.setToolTipText("Informe o nome da empresa");
        jTFNome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTFNomeActionPerformed(evt);
            }
        });
        getContentPane().add(jTFNome);
        jTFNome.setBounds(40, 90, 280, 30);

        jLNome.setForeground(new java.awt.Color(255, 255, 255));
        jLNome.setText("Nome da empresa:");
        getContentPane().add(jLNome);
        jLNome.setBounds(40, 70, 120, 16);

        getContentPane().add(jCBEstado);
        jCBEstado.setBounds(280, 150, 90, 30);

        jLCidade.setForeground(new java.awt.Color(255, 255, 255));
        jLCidade.setText("Cidade:");
        getContentPane().add(jLCidade);
        jLCidade.setBounds(40, 130, 90, 16);

        jBCriarEmp.setBackground(new java.awt.Color(204, 204, 204));
        jBCriarEmp.setText("Adicionar");
        jBCriarEmp.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jBCriarEmp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBCriarEmpActionPerformed1(evt);
            }
        });
        getContentPane().add(jBCriarEmp);
        jBCriarEmp.setBounds(40, 250, 110, 30);

        jLFundo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/tela_de_fundo.jpeg"))); // NOI18N
        getContentPane().add(jLFundo);
        jLFundo.setBounds(-180, -150, 620, 490);

        setSize(new java.awt.Dimension(426, 334));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jTFCidadeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTFCidadeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTFCidadeActionPerformed

    private void jBRemoverEmpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBRemoverEmpActionPerformed
        List<Cliente> aux = new ArrayList<>(clientesCadastro);
        boolean exists = false;
        
        if(!validaNome())
            return;
        
        for (Cliente x: aux) {
            if(x.getNome().toUpperCase().equals(jTFNome.getText().toUpperCase())){
                clientesCadastro.remove(x);
                JOptionPane.showMessageDialog(null, "Cliente removido com sucesso!");
                return;
            }
        }
        
        if(!exists){
            JOptionPane.showMessageDialog(null, "Não foi encontrado o cliente para remover!");
            return;
        }
        
        if(clientesCadastro.isEmpty()){
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("clientes.txt"))) {
                writer.write("");
                jTFNome.setText("");
                jTFCidade.setText("");
                jCBEstado.setSelectedIndex(0);
                return;
            } catch (IOException e ) {
                JOptionPane.showMessageDialog(null, "Erro ao salvar aquivo!");
            }
        }
        
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("clientes.txt"))) {
            for (Cliente cliente: clientesCadastro) {
                writer.write(cliente.toString());
                writer.newLine();
            }
            JOptionPane.showMessageDialog(null, "Cliente deletado com sucesso!", "Deleção", JOptionPane.WARNING_MESSAGE);
            jTFNome.setText("");
            jTFCidade.setText("");
            jCBEstado.setSelectedIndex(0);
            
        } catch (IOException e ) {
            JOptionPane.showMessageDialog(null, "Erro ao salvar aquivo!");
        }
    }//GEN-LAST:event_jBRemoverEmpActionPerformed

    private void jBCriarEmpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBCriarEmpActionPerformed
        
    }//GEN-LAST:event_jBCriarEmpActionPerformed

    private void jBAlterarEmpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBAlterarEmpActionPerformed
        if(!validaNome())
            return;
        
        for (Cliente x: clientesCadastro) {
            if(x.getNome().toUpperCase().equals(jTFNome.getText().toUpperCase())){
                x.setNome(jTFNome.getText().toUpperCase());
                x.setCidade(jTFCidade.getText().toUpperCase());
                x.setEstado(Estado.valueOf((String)jCBEstado.getSelectedItem()));
                break;
            }
        }
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("clientes.txt"))) {
            for (Cliente carro: clientesCadastro) {
                writer.write(carro.toString());
                writer.newLine();
            }
            
            JOptionPane.showMessageDialog(null, "Cliente alterado com sucesso!", "Alteração", JOptionPane.WARNING_MESSAGE);
            jTFNome.setText("");
            jTFCidade.setText("");
            jCBEstado.setSelectedIndex(0);
            
        } catch (IOException e ) {
            JOptionPane.showMessageDialog(null, "Erro ao salvar aquivo!");
        }
    }//GEN-LAST:event_jBAlterarEmpActionPerformed

    private void jBBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBBuscarActionPerformed
        if(jTFNome.getText().equals("")){
            if(!validaNome())
            return;
        }
        for (Cliente x: clientesCadastro) {
            if(x.getNome().toUpperCase().contains(jTFNome.getText().toUpperCase())){
                jTFNome.setText(x.getNome());
                jTFCidade.setText(x.getCidade());
                jCBEstado.setSelectedItem(String.valueOf(x.getEstado()));
                break;
            }
        }
    }//GEN-LAST:event_jBBuscarActionPerformed

    private void jBBuscarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBBuscarMouseClicked
        
    }//GEN-LAST:event_jBBuscarMouseClicked

    private void jBLimparActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBLimparActionPerformed
        // TODO add your handling code here:
        jTFNome.setText("");
        jTFCidade.setText("");
        jCBEstado.setSelectedIndex(0);
    }//GEN-LAST:event_jBLimparActionPerformed

    private void jTFNomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTFNomeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTFNomeActionPerformed

    private void jBCriarEmpActionPerformed1(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBCriarEmpActionPerformed1
        // TODO add your handling code here:
        if(!validaNome())
            return;
        if(!validaCidade())
            return;
        if(!validaNomeUnico())
            return;
        
        Cliente cliente = new Cliente(jTFNome.getText().toUpperCase(),jTFCidade.getText().toUpperCase(),Estado.valueOf((String)jCBEstado.getSelectedItem()));
        clientesCadastro.add(cliente);
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("clientes.txt", true))) {
            writer.write(cliente.toString());
            writer.newLine();
            JOptionPane.showMessageDialog(null, "Cliente gravado com sucesso!");
            jTFNome.setText("");
            jTFCidade.setText("");
            jCBEstado.setSelectedIndex(0);
        } catch (IOException e ) {
            JOptionPane.showMessageDialog(null, "Erro ao salvar aquivo!");
        }
    }//GEN-LAST:event_jBCriarEmpActionPerformed1
    
    public boolean isStringValid(String string) {
        return string.matches("^[0-9a-zA-ZÀ-ÿ]+( [0-9a-zA-ZÀ-ÿ]+)*$");
    }
    
    public boolean validaNome(){
        if(!isStringValid(jTFNome.getText())){
            JOptionPane.showMessageDialog(null, "Ops! Preencha o nome corretamente, pois ele é o identificador único!");
            return false;
        }
        return true;
    }
        
    public boolean validaCidade(){
        if(!isStringValid(jTFCidade.getText())){
            JOptionPane.showMessageDialog(null, "Ops! Preencha a Cidade corretamente!");
            return false;
        }
        return true;
    }
    
    public boolean validaNomeUnico(){
        for (Cliente x: clientesCadastro) {
            if(x.getNome().trim().toUpperCase().equals(jTFNome.getText().trim().toUpperCase())){
                JOptionPane.showMessageDialog(null, "Nome já em uso! Crie com outro nome para não confundir!");
                return false;
            }
        }
        return true;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBAlterarEmp;
    private javax.swing.JButton jBBuscar;
    private javax.swing.JButton jBCriarEmp;
    private javax.swing.JButton jBLimpar;
    private javax.swing.JButton jBRemoverEmp;
    private javax.swing.JComboBox<String> jCBEstado;
    private javax.swing.JLabel jLCidade;
    private javax.swing.JLabel jLEstado;
    private javax.swing.JLabel jLFundo;
    private javax.swing.JLabel jLNome;
    private javax.swing.JTextField jTFCidade;
    private javax.swing.JTextField jTFNome;
    // End of variables declaration//GEN-END:variables
}
