/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import programacao.model.Bloco;
import programacao.model.DemaisInfos;

/**
 *
 * @author Koroch
 */
public class ListagemDeProgramacoes extends javax.swing.JFrame {
    
    public ListagemDeProgramacoes(List<DemaisInfos> demaisInfos, int ultimoIdDemaisInfos, List<Bloco> blocos, int ultimoIdBlocos) {
        
        initComponents();
        Set<String> datasUnicas = Stream.concat(demaisInfos.stream().map(DemaisInfos::getDataProgramacao), blocos.stream().map(Bloco::getDataProgramacao)).collect(Collectors.toSet());
        System.out.println(datasUnicas);
        
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLProg = new javax.swing.JLabel();
        jCB = new javax.swing.JPanel();
        jBAddBloco = new javax.swing.JButton();
        jLProgramDia = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTProgramação = new javax.swing.JTable();
        jLFundo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        getContentPane().setLayout(null);

        jLProg.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLProg.setForeground(new java.awt.Color(255, 255, 255));
        jLProg.setText("Visualizar Programações");
        getContentPane().add(jLProg);
        jLProg.setBounds(440, 10, 350, 25);

        jCB.setBackground(new java.awt.Color(7, 30, 74));
        jCB.setToolTipText("");
        jCB.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jCB.setFocusable(false);
        jCB.setName(""); // NOI18N
        jCB.setLayout(null);

        jBAddBloco.setBackground(new java.awt.Color(204, 204, 204));
        jBAddBloco.setText("Ver");
        jBAddBloco.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jBAddBloco.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBAddBlocoActionPerformed(evt);
            }
        });
        jCB.add(jBAddBloco);
        jBAddBloco.setBounds(780, 30, 110, 30);

        jLProgramDia.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLProgramDia.setForeground(new java.awt.Color(255, 255, 255));
        jLProgramDia.setText("Escolha a programação do dia desejado:");
        jLProgramDia.setToolTipText("");
        jCB.add(jLProgramDia);
        jLProgramDia.setBounds(320, 10, 280, 16);

        jCB.add(jComboBox1);
        jComboBox1.setBounds(320, 30, 440, 30);

        jTProgramação.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null}
            },
            new String [] {
                "Programação"
            }
        ));
        jScrollPane1.setViewportView(jTProgramação);

        jCB.add(jScrollPane1);
        jScrollPane1.setBounds(160, 70, 880, 520);

        getContentPane().add(jCB);
        jCB.setBounds(0, 40, 1200, 610);

        jLFundo.setBackground(new java.awt.Color(51, 51, 51));
        jLFundo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/tela_de_fundo.jpeg"))); // NOI18N
        jLFundo.setToolTipText("");
        getContentPane().add(jLFundo);
        jLFundo.setBounds(-50, -200, 1360, 1120);

        setSize(new java.awt.Dimension(1209, 722));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jBAddBlocoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBAddBlocoActionPerformed
        
    }//GEN-LAST:event_jBAddBlocoActionPerformed

    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBAddBloco;
    private javax.swing.JPanel jCB;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLFundo;
    private javax.swing.JLabel jLProg;
    private javax.swing.JLabel jLProgramDia;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTProgramação;
    // End of variables declaration//GEN-END:variables
}
