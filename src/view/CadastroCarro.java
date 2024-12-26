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
import programacao.model.Carro;

/**
 *
 * @author Koroch
 */
public class CadastroCarro extends javax.swing.JFrame {
    private static List<Carro> carrosCadastro = new ArrayList<>();
    /**
     * Creates new form CadastroCarro
     * @param carros
     */
    public CadastroCarro(List<Carro> carros) {
        CadastroCarro.carrosCadastro = carros;
        initComponents();
    }

    public List<Carro> getListaAtualizadaCarros() {
        List<Carro> lista = new ArrayList<>(carrosCadastro);
        lista.sort((c1, c2) -> c1.getNome().compareTo(c2.getNome()));
        return lista;
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLMarca = new javax.swing.JLabel();
        jLNomeWar = new javax.swing.JLabel();
        jTFMarca = new javax.swing.JTextField();
        jBRemoverUser = new javax.swing.JButton();
        jBCriarUser = new javax.swing.JButton();
        jBAlterarUser = new javax.swing.JButton();
        jBBuscar = new javax.swing.JButton();
        jBLimpar = new javax.swing.JButton();
        jTFNome = new javax.swing.JTextField();
        jLNome = new javax.swing.JLabel();
        jFTFPlaca = new javax.swing.JFormattedTextField();
        jLImei = new javax.swing.JLabel();
        jFTFImei = new javax.swing.JFormattedTextField();
        jTFkmAtual = new javax.swing.JTextField();
        jLkmatual = new javax.swing.JLabel();
        jTFKmUltimaTroca = new javax.swing.JTextField();
        jLKmUltimaTroca = new javax.swing.JLabel();
        jFTFDataKmAtual = new javax.swing.JFormattedTextField();
        jLDataKmAtual = new javax.swing.JLabel();
        jLFundo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        getContentPane().setLayout(null);

        jLMarca.setForeground(new java.awt.Color(255, 255, 255));
        jLMarca.setText("Marca:");
        getContentPane().add(jLMarca);
        jLMarca.setBounds(40, 90, 120, 16);

        jLNomeWar.setForeground(new java.awt.Color(255, 255, 255));
        jLNomeWar.setText("Placa:");
        getContentPane().add(jLNomeWar);
        jLNomeWar.setBounds(40, 150, 140, 16);

        jTFMarca.setToolTipText("Informe a marca do carro!");
        jTFMarca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTFMarcaActionPerformed(evt);
            }
        });
        getContentPane().add(jTFMarca);
        jTFMarca.setBounds(40, 110, 330, 30);

        jBRemoverUser.setBackground(new java.awt.Color(204, 204, 204));
        jBRemoverUser.setText("Remover");
        jBRemoverUser.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jBRemoverUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBRemoverUserActionPerformed(evt);
            }
        });
        getContentPane().add(jBRemoverUser);
        jBRemoverUser.setBounds(260, 380, 110, 30);

        jBCriarUser.setBackground(new java.awt.Color(204, 204, 204));
        jBCriarUser.setText("Adicionar");
        jBCriarUser.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jBCriarUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBCriarUserActionPerformed(evt);
            }
        });
        getContentPane().add(jBCriarUser);
        jBCriarUser.setBounds(40, 380, 110, 30);

        jBAlterarUser.setBackground(new java.awt.Color(204, 204, 204));
        jBAlterarUser.setText("Alterar");
        jBAlterarUser.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jBAlterarUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBAlterarUserActionPerformed(evt);
            }
        });
        getContentPane().add(jBAlterarUser);
        jBAlterarUser.setBounds(150, 380, 110, 30);

        jBBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/lupa.png"))); // NOI18N
        jBBuscar.setToolTipText("Clique aqui para procurar pelo Nome ou Marca!");
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
        jBBuscar.setBounds(330, 40, 40, 40);

        jBLimpar.setText("Limpar");
        jBLimpar.setToolTipText("Limpar todos dados dos campos.");
        jBLimpar.setFocusable(false);
        jBLimpar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBLimparActionPerformed(evt);
            }
        });
        getContentPane().add(jBLimpar);
        jBLimpar.setBounds(280, 340, 90, 30);

        jTFNome.setToolTipText("Informe o nome ou como chamam o carro!");
        jTFNome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTFNomeActionPerformed(evt);
            }
        });
        getContentPane().add(jTFNome);
        jTFNome.setBounds(40, 50, 280, 30);

        jLNome.setForeground(new java.awt.Color(255, 255, 255));
        jLNome.setText("Nome:");
        getContentPane().add(jLNome);
        jLNome.setBounds(40, 30, 120, 16);

        jFTFPlaca.setBackground(new java.awt.Color(204, 204, 255));
        try {
            jFTFPlaca.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("UUU-#*##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        jFTFPlaca.setToolTipText("Coloque uma Placa única. Não poderá modificar depois!");
        getContentPane().add(jFTFPlaca);
        jFTFPlaca.setBounds(40, 170, 100, 30);

        jLImei.setForeground(new java.awt.Color(255, 255, 255));
        jLImei.setText("IMEI do Rastreador:");
        getContentPane().add(jLImei);
        jLImei.setBounds(160, 150, 140, 16);

        try {
            jFTFImei.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("###############")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        getContentPane().add(jFTFImei);
        jFTFImei.setBounds(160, 170, 210, 30);

        jTFkmAtual.setToolTipText("Informe a marca do carro!");
        jTFkmAtual.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTFkmAtualActionPerformed(evt);
            }
        });
        getContentPane().add(jTFkmAtual);
        jTFkmAtual.setBounds(40, 230, 160, 30);

        jLkmatual.setForeground(new java.awt.Color(255, 255, 255));
        jLkmatual.setText("KM Atual");
        getContentPane().add(jLkmatual);
        jLkmatual.setBounds(40, 210, 140, 16);

        jTFKmUltimaTroca.setToolTipText("Informe a marca do carro!");
        jTFKmUltimaTroca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTFKmUltimaTrocaActionPerformed(evt);
            }
        });
        getContentPane().add(jTFKmUltimaTroca);
        jTFKmUltimaTroca.setBounds(210, 230, 160, 30);

        jLKmUltimaTroca.setForeground(new java.awt.Color(255, 255, 255));
        jLKmUltimaTroca.setText("KM última troca");
        getContentPane().add(jLKmUltimaTroca);
        jLKmUltimaTroca.setBounds(210, 210, 140, 16);

        jFTFDataKmAtual.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(java.text.DateFormat.getDateInstance(java.text.DateFormat.SHORT))));
        getContentPane().add(jFTFDataKmAtual);
        jFTFDataKmAtual.setBounds(40, 290, 160, 30);

        jLDataKmAtual.setForeground(new java.awt.Color(255, 255, 255));
        jLDataKmAtual.setText("Data da KM Atual");
        getContentPane().add(jLDataKmAtual);
        jLDataKmAtual.setBounds(40, 270, 140, 16);

        jLFundo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/tela_de_fundo.jpeg"))); // NOI18N
        getContentPane().add(jLFundo);
        jLFundo.setBounds(-230, -210, 710, 700);

        setSize(new java.awt.Dimension(426, 466));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jTFMarcaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTFMarcaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTFMarcaActionPerformed

    private void jBRemoverUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBRemoverUserActionPerformed
        List<Carro> aux = new ArrayList<>(carrosCadastro);
        boolean exists = false;
        
        if(!validaPlaca())
            return;
        
        for (Carro x: aux) {
            if(x.getPlaca().toUpperCase().equals(jFTFPlaca.getText().toUpperCase())){
                carrosCadastro.remove(x);
                JOptionPane.showMessageDialog(null, "Carro removido com sucesso!");
                return;
            }
        }
        
        if(!exists){
            JOptionPane.showMessageDialog(null, "Não foi encontrado o carro para remover!");
            return;
        }
        
        if(carrosCadastro.isEmpty()){
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("carros.txt"))) {
                writer.write("");
                jTFNome.setText("");
                jTFMarca.setText("");
                jFTFPlaca.setText("");
                jFTFImei.setText("");
                jTFkmAtual.setText("");
                jFTFDataKmAtual.setText("");
                jTFKmUltimaTroca.setText("");
                return;
            } catch (IOException e ) {
                JOptionPane.showMessageDialog(null, "Erro ao salvar aquivo!");
            }
        }
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("carros.txt"))) {
            for (Carro carro: carrosCadastro) {
                writer.write(carro.toString());
                writer.newLine();
            }
            
            JOptionPane.showMessageDialog(null, "Carro deletado com sucesso!", "Deleção", JOptionPane.WARNING_MESSAGE);
            jTFNome.setText("");
            jTFMarca.setText("");
            jFTFPlaca.setText("");
            jFTFImei.setText("");
            jTFkmAtual.setText("");
            jFTFDataKmAtual.setText("");
            jTFKmUltimaTroca.setText("");
        } catch (IOException e ) {
            JOptionPane.showMessageDialog(null, "Erro ao salvar aquivo!");
        }
    }//GEN-LAST:event_jBRemoverUserActionPerformed

    private void jBCriarUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBCriarUserActionPerformed
        if(!validaMarca())
            return;
        if(!validaPlaca())
            return;
        if(!validaPlacaUnica())
            return;
        
        Carro carro = new Carro(jTFNome.getText().toUpperCase(),jTFMarca.getText().toUpperCase(),jFTFPlaca.getText().toUpperCase(), jFTFImei.getText(), Integer.parseInt(jTFkmAtual.getText()), jFTFDataKmAtual.getText(), Integer.parseInt(jTFKmUltimaTroca.getText()), jFTFDataKmAtual.getText());
        carrosCadastro.add(carro);
        System.out.println("User"+ carro.toString());
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("carros.txt", true))) {
            writer.write(carro.toString());
            writer.newLine();
            JOptionPane.showMessageDialog(null, "Carro gravado com sucesso!");
            jTFNome.setText("");
            jTFMarca.setText("");
            jFTFPlaca.setText("");
            jFTFImei.setText("");
            jTFkmAtual.setText("");
            jFTFDataKmAtual.setText("");
            jTFKmUltimaTroca.setText("");
        } catch (IOException e ) {
            JOptionPane.showMessageDialog(null, "Erro ao salvar aquivo!");
        }
    }//GEN-LAST:event_jBCriarUserActionPerformed

    private void jBAlterarUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBAlterarUserActionPerformed
        if(!validaPlaca())
            return;
        
        for (Carro x: carrosCadastro) {
            if(x.getPlaca().toUpperCase().equals(jFTFPlaca.getText().toUpperCase())){
                x.setNome(jTFNome.getText().toUpperCase());
                x.setMarca(jTFMarca.getText().toUpperCase());
                x.setImei_rastreador(jFTFImei.getText());
                x.setKm_atual(Integer.parseInt(jTFkmAtual.getText()));
                x.setData_km_atual(jFTFDataKmAtual.getText());
                x.setKm_ultima_troca(Integer.parseInt(jTFKmUltimaTroca.getText()));
                break;
            }
        }
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("carros.txt"))) {
            for (Carro carro: carrosCadastro) {
                writer.write(carro.toString());
                writer.newLine();
            }
            
            JOptionPane.showMessageDialog(null, "Carro alterado com sucesso!");
            jTFNome.setText("");
            jTFMarca.setText("");
            jFTFPlaca.setText("");
            jFTFImei.setText("");
            jTFkmAtual.setText("");
            jFTFDataKmAtual.setText("");
            jTFKmUltimaTroca.setText("");
        } catch (IOException e ) {
            JOptionPane.showMessageDialog(null, "Erro ao salvar aquivo!");
        }
    }//GEN-LAST:event_jBAlterarUserActionPerformed

    private void jBBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBBuscarActionPerformed
        if(jTFMarca.getText().equals("") && !jTFNome.getText().equals("")){
            for (Carro x: carrosCadastro) {
                if(x.getNome().toUpperCase().contains(jTFNome.getText().toUpperCase())){
                    jTFNome.setText(x.getNome());
                    jTFMarca.setText(x.getMarca());
                    jFTFPlaca.setText(x.getPlaca());
                    jFTFImei.setText(x.getImei_rastreador());
                    jTFkmAtual.setText(String.valueOf(x.getKm_atual()));
                    jFTFDataKmAtual.setText(x.getData_km_atual());
                    jTFKmUltimaTroca.setText(String.valueOf(x.getKm_ultima_troca()));
                    break;
                }
            }
        }
        
        if(!jTFMarca.getText().equals("") && jTFNome.getText().equals("")){
            for (Carro x: carrosCadastro) {
                if(x.getMarca().toUpperCase().contains(jTFMarca.getText().toUpperCase())){
                    jTFNome.setText(x.getNome());
                    jTFMarca.setText(x.getMarca());
                    jFTFPlaca.setText(x.getPlaca());
                    break;
                }
            }
        }
        
        if(jTFMarca.getText().equals("") && jTFNome.getText().equals("")){
            if(!validaNome())
            return;
        }
    }//GEN-LAST:event_jBBuscarActionPerformed

    private void jBBuscarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBBuscarMouseClicked
        
    }//GEN-LAST:event_jBBuscarMouseClicked

    private void jBLimparActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBLimparActionPerformed
        // TODO add your handling code here:
        jTFNome.setText("");
        jTFMarca.setText("");
        jFTFPlaca.setText("");
        jFTFImei.setText("");
        jTFkmAtual.setText("");
        jFTFDataKmAtual.setText("");
        jTFKmUltimaTroca.setText("");
    }//GEN-LAST:event_jBLimparActionPerformed

    private void jTFNomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTFNomeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTFNomeActionPerformed

    private void jTFkmAtualActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTFkmAtualActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTFkmAtualActionPerformed

    private void jTFKmUltimaTrocaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTFKmUltimaTrocaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTFKmUltimaTrocaActionPerformed

    public boolean isStringValid(String string) {
        return string.matches("^[a-zA-ZÀ-ÿ]+( [a-zA-ZÀ-ÿ]+)*$");
    }
    
    public boolean validaNome(){
        if(!isStringValid(jTFNome.getText())){
            JOptionPane.showMessageDialog(null, "Ops! Preencha o nome corretamente!");
            return false;
        }
        return true;
    }
        
    public boolean validaMarca(){
        if(!isStringValid(jTFMarca.getText())){
            JOptionPane.showMessageDialog(null, "Ops! Preencha a marca corretamente!");
            return false;
        }
        return true;
    }
    
    
    public boolean isPlacaValid(String string) {
        return string.matches("^[A-Z]{3}-\\d{4}$|^[A-Z]{3}-\\d[A-Z]\\d{2}$");
    }
    
    public boolean validaPlaca(){
        if(!isPlacaValid(jFTFPlaca.getText())){
            JOptionPane.showMessageDialog(null, "Ops! Preencha a placa corretamente, pois ela é o identificador único!");
            return false;
        }
        return true;
    }
    public boolean validaPlacaUnica(){
        for (Carro x: carrosCadastro) {
            if(x.getPlaca().trim().toUpperCase().equals(jFTFPlaca.getText().trim().toUpperCase())){
                JOptionPane.showMessageDialog(null, "Placa já em uso!");
                return false;
            }
        }
        return true;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBAlterarUser;
    private javax.swing.JButton jBBuscar;
    private javax.swing.JButton jBCriarUser;
    private javax.swing.JButton jBLimpar;
    private javax.swing.JButton jBRemoverUser;
    private javax.swing.JFormattedTextField jFTFDataKmAtual;
    private javax.swing.JFormattedTextField jFTFImei;
    private javax.swing.JFormattedTextField jFTFPlaca;
    private javax.swing.JLabel jLDataKmAtual;
    private javax.swing.JLabel jLFundo;
    private javax.swing.JLabel jLImei;
    private javax.swing.JLabel jLKmUltimaTroca;
    private javax.swing.JLabel jLMarca;
    private javax.swing.JLabel jLNome;
    private javax.swing.JLabel jLNomeWar;
    private javax.swing.JLabel jLkmatual;
    private javax.swing.JTextField jTFKmUltimaTroca;
    private javax.swing.JTextField jTFMarca;
    private javax.swing.JTextField jTFNome;
    private javax.swing.JTextField jTFkmAtual;
    // End of variables declaration//GEN-END:variables
}
