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
import programacao.model.Usuario;

/**
 *
 * @author Koroch
 */
public class CadastroUsuario extends javax.swing.JFrame {
    private static List<Usuario> usuariosCadastro = new ArrayList<>();
    /**
     * Creates new form CadastroUsuario
     */
    public CadastroUsuario(List<Usuario> usuarios) {
        CadastroUsuario.usuariosCadastro = usuarios;
        initComponents();
        jRSim.setOpaque(false);
        jRNao.setOpaque(false);
        jRNao.setSelected(true);
    }

    public List<Usuario> getListaAtualizadaUsuarios() {
        return new ArrayList<>(usuariosCadastro);
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bGTemCarteira = new javax.swing.ButtonGroup();
        jLNome = new javax.swing.JLabel();
        jLFuncao = new javax.swing.JLabel();
        jLNomeWar = new javax.swing.JLabel();
        jLTemCarteira = new javax.swing.JLabel();
        jTFNome = new javax.swing.JTextField();
        jTFNomeWar = new javax.swing.JTextField();
        jTFFuncao = new javax.swing.JTextField();
        jRSim = new javax.swing.JRadioButton();
        jRNao = new javax.swing.JRadioButton();
        jBRemoverUser = new javax.swing.JButton();
        jBCriarUser = new javax.swing.JButton();
        jBAlterarUser = new javax.swing.JButton();
        jBBuscar = new javax.swing.JButton();
        jBLimpar = new javax.swing.JButton();
        jLFundo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setBackground(new java.awt.Color(7, 28, 71));
        setResizable(false);
        getContentPane().setLayout(null);

        jLNome.setForeground(new java.awt.Color(255, 255, 255));
        jLNome.setText("Nome do Colaborador:");
        getContentPane().add(jLNome);
        jLNome.setBounds(40, 20, 160, 16);

        jLFuncao.setForeground(new java.awt.Color(255, 255, 255));
        jLFuncao.setText("Função:");
        getContentPane().add(jLFuncao);
        jLFuncao.setBounds(40, 140, 90, 16);

        jLNomeWar.setForeground(new java.awt.Color(255, 255, 255));
        jLNomeWar.setText("Nome de Guerra:");
        getContentPane().add(jLNomeWar);
        jLNomeWar.setBounds(40, 80, 140, 16);

        jLTemCarteira.setForeground(new java.awt.Color(255, 255, 255));
        jLTemCarteira.setText("Tem carteira de Carro?");
        getContentPane().add(jLTemCarteira);
        jLTemCarteira.setBounds(40, 200, 160, 16);

        jTFNome.setToolTipText("Informe o nome do colaborador.");
        jTFNome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTFNomeActionPerformed(evt);
            }
        });
        getContentPane().add(jTFNome);
        jTFNome.setBounds(40, 40, 270, 30);

        jTFNomeWar.setBackground(new java.awt.Color(204, 204, 255));
        jTFNomeWar.setToolTipText("Coloque um Nome de Guerra único. Não poderá modificar depois!");
        getContentPane().add(jTFNomeWar);
        jTFNomeWar.setBounds(40, 100, 327, 30);

        jTFFuncao.setToolTipText("Qual será a função do colaborador?");
        getContentPane().add(jTFFuncao);
        jTFFuncao.setBounds(40, 160, 327, 30);

        bGTemCarteira.add(jRSim);
        jRSim.setForeground(new java.awt.Color(255, 255, 255));
        jRSim.setText("Sim");
        getContentPane().add(jRSim);
        jRSim.setBounds(40, 220, 60, 21);

        bGTemCarteira.add(jRNao);
        jRNao.setForeground(new java.awt.Color(255, 255, 255));
        jRNao.setText("Não");
        jRNao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRNaoActionPerformed(evt);
            }
        });
        getContentPane().add(jRNao);
        jRNao.setBounds(100, 220, 80, 21);

        jBRemoverUser.setBackground(new java.awt.Color(204, 204, 204));
        jBRemoverUser.setText("Remover");
        jBRemoverUser.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jBRemoverUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBRemoverUserActionPerformed(evt);
            }
        });
        getContentPane().add(jBRemoverUser);
        jBRemoverUser.setBounds(260, 260, 110, 30);

        jBCriarUser.setBackground(new java.awt.Color(204, 204, 204));
        jBCriarUser.setText("Adicionar");
        jBCriarUser.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jBCriarUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBCriarUserActionPerformed(evt);
            }
        });
        getContentPane().add(jBCriarUser);
        jBCriarUser.setBounds(40, 260, 110, 30);

        jBAlterarUser.setBackground(new java.awt.Color(204, 204, 204));
        jBAlterarUser.setText("Alterar");
        jBAlterarUser.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jBAlterarUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBAlterarUserActionPerformed(evt);
            }
        });
        getContentPane().add(jBAlterarUser);
        jBAlterarUser.setBounds(150, 260, 110, 30);

        jBBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/lupa.png"))); // NOI18N
        jBBuscar.setToolTipText("Clique aqui para procurar!");
        jBBuscar.setDefaultCapable(false);
        jBBuscar.setFocusPainted(false);
        jBBuscar.setFocusable(false);
        jBBuscar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jBBuscar.setIconTextGap(1);
        jBBuscar.setMaximumSize(new java.awt.Dimension(120, 120));
        jBBuscar.setMinimumSize(new java.awt.Dimension(120, 120));
        jBBuscar.setName(""); // NOI18N
        jBBuscar.setNextFocusableComponent(jRNao);
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
        jBBuscar.setBounds(320, 30, 40, 40);

        jBLimpar.setText("Limpar");
        jBLimpar.setToolTipText("Limpar todos dados dos campos.");
        jBLimpar.setFocusable(false);
        jBLimpar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBLimparActionPerformed(evt);
            }
        });
        getContentPane().add(jBLimpar);
        jBLimpar.setBounds(280, 210, 90, 40);

        jLFundo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/tela_de_fundo.jpeg"))); // NOI18N
        getContentPane().add(jLFundo);
        jLFundo.setBounds(-380, 0, 1300, 710);

        setSize(new java.awt.Dimension(413, 342));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jRNaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRNaoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jRNaoActionPerformed

    private void jTFNomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTFNomeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTFNomeActionPerformed

    private void jBRemoverUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBRemoverUserActionPerformed
        List<Usuario> aux = new ArrayList<>(usuariosCadastro);
        
        if(!validaNomeWar())
            return;
        
        for (Usuario x: aux) {
            if(x.getNomeDeGuerra().toUpperCase().equals(jTFNomeWar.getText().toUpperCase())){
                usuariosCadastro.remove(x);
                return;
            }
        }
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("usuarios.txt"))) {
            for (Usuario usuario: usuariosCadastro) {
                writer.write(usuario.toString());
                writer.newLine();
            }
            
            JOptionPane.showMessageDialog(null, "Colaborador deletado com sucesso!", "Deleção", JOptionPane.WARNING_MESSAGE);
            jTFNome.setText("");
            jTFNomeWar.setText("");
            jTFFuncao.setText("");
            jRNao.setSelected(true);
            
        } catch (IOException e ) {
            JOptionPane.showMessageDialog(null, "Erro ao salvar aquivo!");
        }
    }//GEN-LAST:event_jBRemoverUserActionPerformed

    private void jBCriarUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBCriarUserActionPerformed
        if(!validaCampos())
            return;
        if(!validaNome())
            return;
        if(!validaNomeWar())
            return;
        if(!validaFuncao())
            return;
        if(!validaNomeWarUnico())
            return;
        
        Usuario usuario = new Usuario(jTFNome.getText().toUpperCase(),jTFNomeWar.getText().toUpperCase(),jTFFuncao.getText().toUpperCase(),jRSim.isSelected());
        usuariosCadastro.add(usuario);
        System.out.println("User"+ usuario.toString());
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("usuarios.txt", true))) {
            writer.write(usuario.toString());
            writer.newLine();
            JOptionPane.showMessageDialog(null, "Conteúdo gravado com sucesso!");
            jTFNome.setText("");
            jTFNomeWar.setText("");
            jTFFuncao.setText("");
            jRNao.setSelected(true);
        } catch (IOException e ) {
            JOptionPane.showMessageDialog(null, "Erro ao salvar aquivo!");
        }
    }//GEN-LAST:event_jBCriarUserActionPerformed

    private void jBAlterarUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBAlterarUserActionPerformed
        if(!validaNome() || !validaFuncao())
            return;
        
        for (Usuario x: usuariosCadastro) {
            if(x.getNomeDeGuerra().toUpperCase().equals(jTFNomeWar.getText().toUpperCase())){
                x.setNome(jTFNome.getText().toUpperCase());
                x.setFuncao(jTFFuncao.getText().toUpperCase());
                x.setCarteiraDeCarro(jRSim.isSelected());
                System.out.println("x "+x);
                break;
            }
        }
        
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("usuarios.txt"))) {
            for (Usuario usuario: usuariosCadastro) {
                writer.write(usuario.toString());
                writer.newLine();
            }
            
            JOptionPane.showMessageDialog(null, "Colaborador alterado com sucesso!", "Alteração", JOptionPane.WARNING_MESSAGE);
            jTFNome.setText("");
            jTFNomeWar.setText("");
            jTFFuncao.setText("");
            jRNao.setSelected(true);
            
        } catch (IOException e ) {
            JOptionPane.showMessageDialog(null, "Erro ao salvar aquivo!");
        }
    }//GEN-LAST:event_jBAlterarUserActionPerformed

    private void jBBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBBuscarActionPerformed
        if(!validaNome())
            return;
        
        for (Usuario x: usuariosCadastro) {
            if(x.getNome().toUpperCase().contains(jTFNome.getText().toUpperCase())){
                jTFNome.setText(x.getNome());
                jTFNomeWar.setText(x.getNomeDeGuerra());
                jTFFuncao.setText(x.getFuncao());
                if(x.isCarteiraDeCarro()){
                    jRSim.setSelected(true);
                }else{
                    jRNao.setSelected(true);
                }
                return;
            }
        }
    }//GEN-LAST:event_jBBuscarActionPerformed

    private void jBBuscarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBBuscarMouseClicked
        
    }//GEN-LAST:event_jBBuscarMouseClicked

    private void jBLimparActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBLimparActionPerformed
        // TODO add your handling code here:
        jTFNome.setText("");
        jTFNomeWar.setText("");
        jTFFuncao.setText("");
        jRNao.setSelected(true);
    }//GEN-LAST:event_jBLimparActionPerformed

    
    public boolean isStringValid(String string) {
        return string.matches("^[a-zA-ZÀ-ÿ]+( [a-zA-ZÀ-ÿ]+)*$");
    }
    
    public boolean validaCampos(){
        if(jTFNome.getText().equals("") || jTFNomeWar.getText().equals("") || jTFFuncao.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Calma aí! Para adicionar, tem que preencher todos os campos!");
            return false;
        }
        return true;
    }
    
    public boolean validaNome(){
        if(!isStringValid(jTFNome.getText())){
            JOptionPane.showMessageDialog(null, "Ops! Preencha o nome corretamente!");
            return false;
        }
        return true;
    }
    public boolean validaNomeWar(){
        if(!isStringValid(jTFNomeWar.getText())){
            JOptionPane.showMessageDialog(null, "Ops! Preencha o nome de guerra corretamente!");
            return false;
        }
        return true;
    }
    public boolean validaFuncao(){
        if(!isStringValid(jTFFuncao.getText())){
            JOptionPane.showMessageDialog(null, "Ops! Preencha a função corretamente!");
            return false;
        }
        return true;
    }
    public boolean validaNomeWarUnico(){
        for (Usuario x: usuariosCadastro) {
            if(x.getNomeDeGuerra().trim().toUpperCase().equals(jTFNomeWar.getText().trim().toUpperCase())){
                JOptionPane.showMessageDialog(null, "Nome de Guerra já em uso! Crie com outro para não dar confusão!");
                return false;
            }
        }
        return true;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bGTemCarteira;
    private javax.swing.JButton jBAlterarUser;
    private javax.swing.JButton jBBuscar;
    private javax.swing.JButton jBCriarUser;
    private javax.swing.JButton jBLimpar;
    private javax.swing.JButton jBRemoverUser;
    private javax.swing.JLabel jLFuncao;
    private javax.swing.JLabel jLFundo;
    private javax.swing.JLabel jLNome;
    private javax.swing.JLabel jLNomeWar;
    private javax.swing.JLabel jLTemCarteira;
    private javax.swing.JRadioButton jRNao;
    private javax.swing.JRadioButton jRSim;
    private javax.swing.JTextField jTFFuncao;
    private javax.swing.JTextField jTFNome;
    private javax.swing.JTextField jTFNomeWar;
    // End of variables declaration//GEN-END:variables
}
