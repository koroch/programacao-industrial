/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import programacao.model.Bloco;
import programacao.model.DemaisInfos;
import programacao.model.Usuario;
/**
 *
 * @author Koroch
 */
public class DemaisInfosView extends javax.swing.JFrame {    
    private List<DemaisInfos> demaisInfosCadastro = new ArrayList<>();
    private List<Bloco> blocosCadastro = new ArrayList<>();
    private List<String> linhasArquivoUser = new ArrayList<>();
    private List<Usuario> usuarios = new ArrayList<>();
    private DefaultListModel<String> dLMUsers = new DefaultListModel<>();
    private DefaultListModel<String> dLMInternosMec = new DefaultListModel<>();
    private DefaultListModel<String> dLMInternosElec = new DefaultListModel<>();
    private DefaultListModel<String> dLMFolgas = new DefaultListModel<>();
    private DefaultListModel<String> dLMFerias = new DefaultListModel<>();
    private String dataBuscada = "";
    /**
     * Creates new form DemaisInfos
     * @param demaisInfos
     */
   
    public DemaisInfosView(List<DemaisInfos> demaisInfos, List<Bloco> blocos) {
        this.demaisInfosCadastro = demaisInfos;
        this.blocosCadastro = blocos;
        
        initComponents();
        
        try (BufferedReader br = new BufferedReader(new FileReader("usuarios.txt"))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                linhasArquivoUser.add(linha);
            }
            
            linhasArquivoUser.forEach(elemento -> {
                String[] dados = elemento.split("\\|");
                int qtdArray = dados.length;
                usuarios.add(new Usuario(
                    Integer.parseInt(dados[0].trim()),
                    dados[1].trim(),
                    (qtdArray >= 3 ? dados[2].trim() : null),
                    (qtdArray >= 4 ? dados[3].trim() : null),
                    Boolean.parseBoolean((qtdArray >= 5 ? dados[4].trim() : "false"))
                ));
            });
            
            if(!usuarios.isEmpty()){
                String[] itensArray = usuarios.stream().map(Usuario::getNomeDeGuerra).sorted().toArray(String[]::new);
                for (String item : itensArray) {
                    dLMUsers.addElement(item);
                }
                jLColab.setModel(dLMUsers);
            }
            jDCDataProgramacao.addPropertyChangeListener("date", evt -> {
                atualizarListas();
                atualizarUsersDisponiveis();
                buscar();
            });
        } catch (IOException e ) {
            JOptionPane.showMessageDialog(null, "Erro no arquivo usuarios.txt, talvez ele ainda esteja vazio!");
        }
    }
    
    private List<Usuario> stringToUser(String dado, Usuario usuarioHelper){
        String[] dadosUsers = dado.replace("[", "").replace("]", "").split(",");
        List<Usuario> users = Arrays.stream(dadosUsers)
            .map(idStr -> Integer.parseInt(idStr)) // Converte cada String para int
            .map(id -> usuarioHelper.getById(id, usuarios)) // Busca o nome do usuário pelo ID
            .collect(Collectors.toList());
        return users;
    }
   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jDCDataProgramacao = new com.toedter.calendar.JDateChooser();
        jLProgramDia = new javax.swing.JLabel();
        jSPColaboradores = new javax.swing.JScrollPane();
        jLColab = new javax.swing.JList<>();
        jLColaboradores = new javax.swing.JLabel();
        jBSubInternoMec = new javax.swing.JButton();
        jBAddInternoMec = new javax.swing.JButton();
        jSPSelecionados = new javax.swing.JScrollPane();
        jLtInternosMecanica = new javax.swing.JList<>();
        jLInternosMecanica = new javax.swing.JLabel();
        jSPSelecionados1 = new javax.swing.JScrollPane();
        jLtInternosEletrica = new javax.swing.JList<>();
        jLinternosEletrica = new javax.swing.JLabel();
        jSPSelecionados2 = new javax.swing.JScrollPane();
        jLtFolgas = new javax.swing.JList<>();
        jLFolgas = new javax.swing.JLabel();
        jBAddInternoElec = new javax.swing.JButton();
        jBSubInternoElec = new javax.swing.JButton();
        jBSubFolga = new javax.swing.JButton();
        jBAddFolga = new javax.swing.JButton();
        jBCriar = new javax.swing.JButton();
        jBAlterar = new javax.swing.JButton();
        jBRemover = new javax.swing.JButton();
        jSPSelecionados3 = new javax.swing.JScrollPane();
        jLtFerias = new javax.swing.JList<>();
        jLFérias = new javax.swing.JLabel();
        jBAddFerias = new javax.swing.JButton();
        jBSubFerias = new javax.swing.JButton();
        jLFundo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        getContentPane().setLayout(null);

        jDCDataProgramacao.setDate(new java.util.Date(new java.util.Date().getTime() + 86400000L)
        );
        jDCDataProgramacao.setDateFormatString("dd/MM/yyyy");
        getContentPane().add(jDCDataProgramacao);
        jDCDataProgramacao.setBounds(160, 60, 420, 30);

        jLProgramDia.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLProgramDia.setForeground(new java.awt.Color(255, 255, 255));
        jLProgramDia.setText("Adicionar para a programação do dia:");
        jLProgramDia.setToolTipText("");
        getContentPane().add(jLProgramDia);
        jLProgramDia.setBounds(160, 40, 210, 16);

        jLColab.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jLColab.setToolTipText("");
        jSPColaboradores.setViewportView(jLColab);

        getContentPane().add(jSPColaboradores);
        jSPColaboradores.setBounds(160, 130, 180, 330);

        jLColaboradores.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        jLColaboradores.setForeground(new java.awt.Color(255, 255, 255));
        jLColaboradores.setText("Nome do Colaborador:");
        jLColaboradores.setToolTipText("");
        getContentPane().add(jLColaboradores);
        jLColaboradores.setBounds(160, 110, 130, 14);

        jBSubInternoMec.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        jBSubInternoMec.setText("<<");
        jBSubInternoMec.setToolTipText("");
        jBSubInternoMec.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jBSubInternoMec.setBorderPainted(false);
        jBSubInternoMec.setFocusable(false);
        jBSubInternoMec.setRequestFocusEnabled(false);
        jBSubInternoMec.setRolloverEnabled(false);
        jBSubInternoMec.setVerifyInputWhenFocusTarget(false);
        jBSubInternoMec.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBSubInternoMecActionPerformed(evt);
            }
        });
        getContentPane().add(jBSubInternoMec);
        jBSubInternoMec.setBounds(350, 160, 50, 30);

        jBAddInternoMec.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        jBAddInternoMec.setText(">>");
        jBAddInternoMec.setToolTipText("");
        jBAddInternoMec.setBorder(javax.swing.BorderFactory.createCompoundBorder());
        jBAddInternoMec.setFocusable(false);
        jBAddInternoMec.setRequestFocusEnabled(false);
        jBAddInternoMec.setRolloverEnabled(false);
        jBAddInternoMec.setVerifyInputWhenFocusTarget(false);
        jBAddInternoMec.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBAddInternoMecActionPerformed(evt);
            }
        });
        getContentPane().add(jBAddInternoMec);
        jBAddInternoMec.setBounds(350, 130, 50, 30);

        jLtInternosMecanica.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jLtInternosMecanica.setToolTipText("");
        jSPSelecionados.setViewportView(jLtInternosMecanica);

        getContentPane().add(jSPSelecionados);
        jSPSelecionados.setBounds(410, 130, 170, 60);

        jLInternosMecanica.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        jLInternosMecanica.setForeground(new java.awt.Color(255, 255, 255));
        jLInternosMecanica.setText("Internos - Mecânica:");
        jLInternosMecanica.setToolTipText("");
        getContentPane().add(jLInternosMecanica);
        jLInternosMecanica.setBounds(410, 110, 160, 14);

        jLtInternosEletrica.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jLtInternosEletrica.setToolTipText("");
        jSPSelecionados1.setViewportView(jLtInternosEletrica);

        getContentPane().add(jSPSelecionados1);
        jSPSelecionados1.setBounds(410, 220, 170, 60);

        jLinternosEletrica.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        jLinternosEletrica.setForeground(new java.awt.Color(255, 255, 255));
        jLinternosEletrica.setText("Internos - Elétrica:");
        jLinternosEletrica.setToolTipText("");
        getContentPane().add(jLinternosEletrica);
        jLinternosEletrica.setBounds(410, 200, 160, 14);

        jLtFolgas.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jLtFolgas.setToolTipText("");
        jSPSelecionados2.setViewportView(jLtFolgas);

        getContentPane().add(jSPSelecionados2);
        jSPSelecionados2.setBounds(410, 310, 170, 60);

        jLFolgas.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        jLFolgas.setForeground(new java.awt.Color(255, 255, 255));
        jLFolgas.setText("Folgas:");
        jLFolgas.setToolTipText("");
        getContentPane().add(jLFolgas);
        jLFolgas.setBounds(410, 290, 160, 14);

        jBAddInternoElec.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        jBAddInternoElec.setText(">>");
        jBAddInternoElec.setToolTipText("");
        jBAddInternoElec.setBorder(javax.swing.BorderFactory.createCompoundBorder());
        jBAddInternoElec.setFocusable(false);
        jBAddInternoElec.setRequestFocusEnabled(false);
        jBAddInternoElec.setRolloverEnabled(false);
        jBAddInternoElec.setVerifyInputWhenFocusTarget(false);
        jBAddInternoElec.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBAddInternoElecActionPerformed(evt);
            }
        });
        getContentPane().add(jBAddInternoElec);
        jBAddInternoElec.setBounds(350, 220, 50, 30);

        jBSubInternoElec.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        jBSubInternoElec.setText("<<");
        jBSubInternoElec.setToolTipText("");
        jBSubInternoElec.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jBSubInternoElec.setBorderPainted(false);
        jBSubInternoElec.setFocusable(false);
        jBSubInternoElec.setRequestFocusEnabled(false);
        jBSubInternoElec.setRolloverEnabled(false);
        jBSubInternoElec.setVerifyInputWhenFocusTarget(false);
        jBSubInternoElec.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBSubInternoElecActionPerformed(evt);
            }
        });
        getContentPane().add(jBSubInternoElec);
        jBSubInternoElec.setBounds(350, 250, 50, 30);

        jBSubFolga.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        jBSubFolga.setText("<<");
        jBSubFolga.setToolTipText("");
        jBSubFolga.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jBSubFolga.setBorderPainted(false);
        jBSubFolga.setFocusable(false);
        jBSubFolga.setRequestFocusEnabled(false);
        jBSubFolga.setRolloverEnabled(false);
        jBSubFolga.setVerifyInputWhenFocusTarget(false);
        jBSubFolga.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBSubFolgaActionPerformed(evt);
            }
        });
        getContentPane().add(jBSubFolga);
        jBSubFolga.setBounds(350, 340, 50, 30);

        jBAddFolga.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        jBAddFolga.setText(">>");
        jBAddFolga.setToolTipText("");
        jBAddFolga.setBorder(javax.swing.BorderFactory.createCompoundBorder());
        jBAddFolga.setFocusable(false);
        jBAddFolga.setRequestFocusEnabled(false);
        jBAddFolga.setRolloverEnabled(false);
        jBAddFolga.setVerifyInputWhenFocusTarget(false);
        jBAddFolga.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBAddFolgaActionPerformed(evt);
            }
        });
        getContentPane().add(jBAddFolga);
        jBAddFolga.setBounds(350, 310, 50, 30);

        jBCriar.setBackground(new java.awt.Color(204, 204, 204));
        jBCriar.setText("Criar");
        jBCriar.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jBCriar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBCriarActionPerformed1(evt);
            }
        });
        getContentPane().add(jBCriar);
        jBCriar.setBounds(160, 480, 140, 30);

        jBAlterar.setBackground(new java.awt.Color(204, 204, 204));
        jBAlterar.setText("Alterar");
        jBAlterar.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jBAlterar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBAlterarActionPerformed(evt);
            }
        });
        getContentPane().add(jBAlterar);
        jBAlterar.setBounds(300, 480, 140, 30);

        jBRemover.setBackground(new java.awt.Color(204, 204, 204));
        jBRemover.setText("Remover");
        jBRemover.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jBRemover.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBRemoverActionPerformed(evt);
            }
        });
        getContentPane().add(jBRemover);
        jBRemover.setBounds(440, 480, 140, 30);

        jLtFerias.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jLtFerias.setToolTipText("");
        jSPSelecionados3.setViewportView(jLtFerias);

        getContentPane().add(jSPSelecionados3);
        jSPSelecionados3.setBounds(410, 400, 170, 60);

        jLFérias.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        jLFérias.setForeground(new java.awt.Color(255, 255, 255));
        jLFérias.setText("Férias:");
        jLFérias.setToolTipText("");
        getContentPane().add(jLFérias);
        jLFérias.setBounds(410, 380, 160, 14);

        jBAddFerias.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        jBAddFerias.setText(">>");
        jBAddFerias.setToolTipText("");
        jBAddFerias.setBorder(javax.swing.BorderFactory.createCompoundBorder());
        jBAddFerias.setFocusable(false);
        jBAddFerias.setRequestFocusEnabled(false);
        jBAddFerias.setRolloverEnabled(false);
        jBAddFerias.setVerifyInputWhenFocusTarget(false);
        jBAddFerias.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBAddFeriasActionPerformed(evt);
            }
        });
        getContentPane().add(jBAddFerias);
        jBAddFerias.setBounds(350, 400, 50, 30);

        jBSubFerias.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        jBSubFerias.setText("<<");
        jBSubFerias.setToolTipText("");
        jBSubFerias.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jBSubFerias.setBorderPainted(false);
        jBSubFerias.setFocusable(false);
        jBSubFerias.setRequestFocusEnabled(false);
        jBSubFerias.setRolloverEnabled(false);
        jBSubFerias.setVerifyInputWhenFocusTarget(false);
        jBSubFerias.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBSubFeriasActionPerformed(evt);
            }
        });
        getContentPane().add(jBSubFerias);
        jBSubFerias.setBounds(350, 430, 50, 30);

        jLFundo.setBackground(new java.awt.Color(51, 51, 51));
        jLFundo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/tela_de_fundo.jpeg"))); // NOI18N
        jLFundo.setToolTipText("");
        getContentPane().add(jLFundo);
        jLFundo.setBounds(0, -210, 1360, 1120);

        setSize(new java.awt.Dimension(774, 572));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void atualizarUsersDisponiveis(){
        String dataProgramacao = jDCDataProgramacao.getDate()==null?"":new SimpleDateFormat("dd/MM/yyyy").format(jDCDataProgramacao.getDate());
        List<String> todosNomesUsuarios = new ArrayList<>();
        for (Bloco bloco : blocosCadastro) {
            if (bloco.getDataProgramacao().equals(dataProgramacao)) {
                // Adiciona todos os nomes de guerra da equipe do bloco na lista
                bloco.getEquipe().stream()
                    .map(Usuario::getNomeDeGuerra)
                    .forEach(todosNomesUsuarios::add);
            }
        }

        String[] nomesUsuariosEquipe = todosNomesUsuarios.toArray(new String[0]);
        if (nomesUsuariosEquipe != null) {
            for (String item : nomesUsuariosEquipe) {
                System.out.println("item: " + item);
                if (dLMUsers.contains(item)) {
                    dLMUsers.removeElement(item);
                }
            }
            jLColab.setModel(dLMUsers);
        }
    }
    
    private void atualizarRemove(DefaultListModel<String> dfList){
        String[] arrayItens = IntStream.range(0, dLMUsers.getSize())
            .mapToObj(dLMUsers::getElementAt)
            .toArray(String[]::new);
        for (String item : arrayItens) {
            if(IntStream.range(0, dfList.getSize()).mapToObj(dfList::getElementAt).anyMatch(nome -> nome.equals(item))){
                dLMUsers.removeElement(item);
            }
        }
        jLColab.setModel(dLMUsers);
    }
    
    private void jBSubInternoMecActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBSubInternoMecActionPerformed
        String itemSelecionado = jLtInternosMecanica.getSelectedValue();
        
        if (itemSelecionado != null) {
            dLMUsers.addElement(jLtInternosMecanica.getSelectedValue());
            dLMInternosMec.remove(jLtInternosMecanica.getSelectedIndex()); 
            atualizarRemove(dLMInternosMec);
        } else {
            JOptionPane.showMessageDialog(null, "Nenhum colaborador selecionado na listagem!");
        }
    }//GEN-LAST:event_jBSubInternoMecActionPerformed

    private void jBAddInternoMecActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBAddInternoMecActionPerformed
        String nomeSelecionado = jLColab.getSelectedValue();
        
        if (nomeSelecionado != null) {
            boolean isSaved = false;
            for (int i = 0; i < dLMInternosMec.size(); i++) {
                if(dLMInternosMec.elementAt(i).equals(nomeSelecionado)){
                    isSaved = true;
                    break;
                }
            }

            if(isSaved){
                JOptionPane.showMessageDialog(null, "O Colaborador já está selecionado!");
                return;
            }
            
            dLMInternosMec.addElement(nomeSelecionado);
            jLtInternosMecanica.setModel(dLMInternosMec);
            dLMUsers.remove(jLColab.getSelectedIndex());
            
        } else {
            JOptionPane.showMessageDialog(null, "Nenhum colaborador selecionado na listagem!");
        }
    }//GEN-LAST:event_jBAddInternoMecActionPerformed

    private void jBAddInternoElecActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBAddInternoElecActionPerformed
        String nomeSelecionado = jLColab.getSelectedValue();
        
        if (nomeSelecionado != null) {
            boolean isSaved = false;
            for (int i = 0; i < dLMInternosElec.size(); i++) {
                if(dLMInternosElec.elementAt(i).equals(nomeSelecionado)){
                    isSaved = true;
                    break;
                }
            }

            if(isSaved){
                JOptionPane.showMessageDialog(null, "O Colaborador já está selecionado!");
                return;
            }
            
            dLMInternosElec.addElement(nomeSelecionado);
            jLtInternosEletrica.setModel(dLMInternosElec);
            dLMUsers.remove(jLColab.getSelectedIndex());
            
        } else {
            JOptionPane.showMessageDialog(null, "Nenhum colaborador selecionado na listagem!");
        }
    }//GEN-LAST:event_jBAddInternoElecActionPerformed

    private void jBSubInternoElecActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBSubInternoElecActionPerformed
        String itemSelecionado = jLtInternosEletrica.getSelectedValue();
        
        if (itemSelecionado != null) {
            dLMUsers.addElement(jLtInternosEletrica.getSelectedValue());
            dLMInternosElec.remove(jLtInternosEletrica.getSelectedIndex()); 
            atualizarRemove(dLMInternosElec);
        } else {
            JOptionPane.showMessageDialog(null, "Nenhum colaborador selecionado na listagem!");
        }
    }//GEN-LAST:event_jBSubInternoElecActionPerformed

    private void jBSubFolgaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBSubFolgaActionPerformed
        String itemSelecionado = jLtFolgas.getSelectedValue();
        
        if (itemSelecionado != null) {
            dLMUsers.addElement(jLtFolgas.getSelectedValue());
            dLMFolgas.remove(jLtFolgas.getSelectedIndex()); 
            atualizarRemove(dLMFolgas);
        } else {
            JOptionPane.showMessageDialog(null, "Nenhum colaborador selecionado na listagem!");
        }
    }//GEN-LAST:event_jBSubFolgaActionPerformed

    private void jBAddFolgaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBAddFolgaActionPerformed
        String nomeSelecionado = jLColab.getSelectedValue();
        
        if (nomeSelecionado != null) {
            boolean isSaved = false;
            for (int i = 0; i < dLMFolgas.size(); i++) {
                if(dLMFolgas.elementAt(i).equals(nomeSelecionado)){
                    isSaved = true;
                    break;
                }
            }

            if(isSaved){
                JOptionPane.showMessageDialog(null, "O Colaborador já está selecionado!");
                return;
            }
            
            dLMFolgas.addElement(nomeSelecionado);
            jLtFolgas.setModel(dLMFolgas);
            dLMUsers.remove(jLColab.getSelectedIndex());
           
        } else {
            JOptionPane.showMessageDialog(null, "Nenhum colaborador selecionado na listagem!");
        }
    }//GEN-LAST:event_jBAddFolgaActionPerformed

    private void jBCriarActionPerformed1(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBCriarActionPerformed1
        for (DemaisInfos x : demaisInfosCadastro) {
            if(x.getDataProgramacao().contains(jDCDataProgramacao.getDate()==null?"":new SimpleDateFormat("dd/MM/yyyy").format(jDCDataProgramacao.getDate()))){
                JOptionPane.showMessageDialog(null, "Já há salvo um registro de folga, férias ou internos para este dia. Altere-o em vez de criar um novo!", "Atenção", JOptionPane.WARNING_MESSAGE);
                return;
            }
        }
        
        jLtInternosMecanica.setModel(dLMInternosMec);
        jLtInternosEletrica.setModel(dLMInternosElec);
        jLtFolgas.setModel(dLMFolgas);
        jLtFerias.setModel(dLMFerias);
                
        String dataProgramacao = jDCDataProgramacao.getDate()==null?"":new SimpleDateFormat("dd/MM/yyyy").format(jDCDataProgramacao.getDate());
        List<Usuario> internosMec = new ArrayList<>();
        if (dLMInternosMec.getSize() > 0) {
            ArrayList<String> arrayList = new ArrayList<>(
                IntStream.range(0, dLMInternosMec.getSize())
                    .mapToObj(dLMInternosMec::getElementAt)
                    .collect(Collectors.toList())
            );
            internosMec = (ArrayList<Usuario>) usuarios.stream()
                .filter(usuario -> arrayList.stream()
                    .anyMatch(nome -> nome.equals(usuario.getNomeDeGuerra()))
                )
                .collect(Collectors.toList());
        }
        List<Usuario> internosElec = new ArrayList<>();
        if (dLMInternosElec.getSize() > 0) {
            ArrayList<String> arrayList = new ArrayList<>(
                IntStream.range(0, dLMInternosElec.getSize())
                    .mapToObj(dLMInternosElec::getElementAt)
                    .collect(Collectors.toList())
            );
            internosElec = (ArrayList<Usuario>) usuarios.stream()
                .filter(usuario -> arrayList.stream()
                    .anyMatch(nome -> nome.equals(usuario.getNomeDeGuerra()))
                )
                .collect(Collectors.toList());
        }
        List<Usuario> folgas = new ArrayList<>();
        if (dLMFolgas.getSize() > 0) {
            ArrayList<String> arrayList = new ArrayList<>(
                IntStream.range(0, dLMFolgas.getSize())
                    .mapToObj(dLMFolgas::getElementAt)
                    .collect(Collectors.toList())
            );
            folgas = (ArrayList<Usuario>) usuarios.stream()
                .filter(usuario -> arrayList.stream()
                    .anyMatch(nome -> nome.equals(usuario.getNomeDeGuerra()))
                )
                .collect(Collectors.toList());
        }
        List<Usuario> ferias = new ArrayList<>();
        if (dLMFerias.getSize() > 0) {
            ArrayList<String> arrayList = new ArrayList<>(
                IntStream.range(0, dLMFerias.getSize())
                    .mapToObj(dLMFerias::getElementAt)
                    .collect(Collectors.toList())
            );
            ferias = (ArrayList<Usuario>) usuarios.stream()
                .filter(usuario -> arrayList.stream()
                    .anyMatch(nome -> nome.equals(usuario.getNomeDeGuerra()))
                )
                .collect(Collectors.toList());
        }
        DemaisInfos demaisInfosLocal = new DemaisInfos(dataProgramacao,internosMec,internosElec,folgas,ferias);
        String idsMecanicos =  demaisInfosLocal.getMecanicosInternos().stream().map(usuario -> String.valueOf(usuario.getId())).collect(Collectors.joining(","));
        String idsEletricistas =  demaisInfosLocal.getEletricistasInternos().stream().map(usuario -> String.valueOf(usuario.getId())).collect(Collectors.joining(","));
        String idFolgas =  demaisInfosLocal.getFolgas().stream().map(usuario -> String.valueOf(usuario.getId())).collect(Collectors.joining(","));
        String idFerias =  demaisInfosLocal.getFerias().stream().map(usuario -> String.valueOf(usuario.getId())).collect(Collectors.joining(","));
        String demaisInfosSalvar = demaisInfosLocal.getId()+"|"+dataProgramacao+"|"+idsMecanicos+"|"+idsEletricistas+"|"+idFolgas+"|"+idFerias;
        
        LocalDate dataProgramBr = jDCDataProgramacao.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        if(!dataProgramBr.isBefore(LocalDate.now())){
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("demaisInfos.txt", true))) {
                writer.write(demaisInfosSalvar);
                writer.newLine();
                JOptionPane.showMessageDialog(null, "Dados gravados com sucesso!");
            } catch (IOException e ) {
                JOptionPane.showMessageDialog(null, "Erro ao salvar aquivo demaisInfos!");
            }
        }else{
            JOptionPane.showMessageDialog(null, "A data não pode ser retroativa!");
            return;
        }
    }//GEN-LAST:event_jBCriarActionPerformed1

    private void jBAlterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBAlterarActionPerformed
        boolean exists = false;
        for (DemaisInfos x : demaisInfosCadastro) {
            if(x.getDataProgramacao().contains(jDCDataProgramacao.getDate()==null?"":new SimpleDateFormat("dd/MM/yyyy").format(jDCDataProgramacao.getDate()))){
                exists = true;

                String dataProgramacao = jDCDataProgramacao.getDate()==null?"":new SimpleDateFormat("dd/MM/yyyy").format(jDCDataProgramacao.getDate());
                List<Usuario> internosMec = new ArrayList<>();
                if (dLMInternosMec.getSize() > 0) {
                    List<String> internosElecString = new ArrayList<>(
                        IntStream.range(0, dLMInternosMec.getSize())
                            .mapToObj(dLMInternosMec::getElementAt)
                            .collect(Collectors.toList())
                    );
                    internosMec = (ArrayList<Usuario>) usuarios.stream()
                        .filter(usuario -> internosElecString.stream()
                            .anyMatch(nome -> nome.equals(usuario.getNomeDeGuerra()))
                        )
                        .collect(Collectors.toList());
                }
                List<Usuario> internosElec = new ArrayList<>();
                if (dLMInternosElec.getSize() > 0) {
                    List<String> internosElecString = new ArrayList<>(
                        IntStream.range(0, dLMInternosElec.getSize())
                            .mapToObj(dLMInternosElec::getElementAt)
                            .collect(Collectors.toList())
                    );
                    internosElec = (ArrayList<Usuario>) usuarios.stream()
                        .filter(usuario -> internosElecString.stream()
                            .anyMatch(nome -> nome.equals(usuario.getNomeDeGuerra()))
                        )
                        .collect(Collectors.toList());
                }
                List<Usuario> folgas = new ArrayList<>();
                if (dLMFolgas.getSize() > 0) {
                    List<String> folgasString = new ArrayList<>(
                        IntStream.range(0, dLMFolgas.getSize())
                            .mapToObj(dLMFolgas::getElementAt)
                            .collect(Collectors.toList())
                    );
                    folgas = (ArrayList<Usuario>) usuarios.stream()
                        .filter(usuario -> folgasString.stream()
                            .anyMatch(nome -> nome.equals(usuario.getNomeDeGuerra()))
                        )
                        .collect(Collectors.toList());
                }
                List<Usuario> ferias = new ArrayList<>();
                if (dLMFerias.getSize() > 0) {
                    List<String> feriasString = new ArrayList<>(
                        IntStream.range(0, dLMFerias.getSize())
                            .mapToObj(dLMFerias::getElementAt)
                            .collect(Collectors.toList())
                    );
                    ferias = (ArrayList<Usuario>) usuarios.stream()
                        .filter(usuario -> feriasString.stream()
                            .anyMatch(nome -> nome.equals(usuario.getNomeDeGuerra()))
                        )
                        .collect(Collectors.toList());
                }
                
                if(dLMInternosMec.getSize() > 0 || dLMInternosElec.getSize() > 0 || dLMFolgas.getSize() > 0 || dLMFerias.getSize() > 0){
                    x.setMecanicosInternos(internosMec);
                    x.setEletricistasInternos(internosElec);
                    x.setFolgas(folgas);
                    x.setFerias(ferias);
                }else{
                    JOptionPane.showMessageDialog(null, "Não é possível alterar deixando todas listas vazias! Sugiro excluir diretamente!");
                }
                break;
            }  
        }
        List<DemaisInfos> demaisAux = new ArrayList<>(demaisInfosCadastro);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("demaisInfos.txt"))) {
            for (DemaisInfos demais : demaisAux) {
                String idsMecanicos =  Optional.ofNullable(demais.getMecanicosInternos()).map(mecanicos -> mecanicos.stream().map(usuario -> String.valueOf(usuario.getId())).collect(Collectors.joining(","))).orElse("");
                System.out.println(idsMecanicos);
                String idsEletricistas = Optional.ofNullable(demais.getEletricistasInternos()).map(eletricistas -> eletricistas.stream().map(usuario -> String.valueOf(usuario.getId())).collect(Collectors.joining(","))).orElse("");
                System.out.println(idsEletricistas);
                String idFolgas =  Optional.ofNullable(demais.getFolgas()).map(folgas -> folgas.stream().map(usuario -> String.valueOf(usuario.getId())).collect(Collectors.joining(","))).orElse("");
                System.out.println(idFolgas);
                String idFerias =  Optional.ofNullable(demais.getFerias()).map(ferias -> ferias.stream().map(usuario -> String.valueOf(usuario.getId())).collect(Collectors.joining(","))).orElse("");
                System.out.println(idFerias);
                String demaisInfosSalvar = demais.getId()+"|"+demais.getDataProgramacao()+"|"+idsMecanicos+"|"+idsEletricistas+"|"+idFolgas+"|"+idFerias;

                writer.write(demaisInfosSalvar);
                writer.newLine();
            }
            JOptionPane.showMessageDialog(null, "Dados gravados com sucesso!");
        } catch (IOException e ) {
            JOptionPane.showMessageDialog(null, "Erro ao salvar aquivo!");
        }
        if(!exists) {
            JOptionPane.showMessageDialog(null, "Dados não encontrados para fazer alteração! Caso não tenha criado um para essa data, crie ele primeiro!");
            return;
        }
    }//GEN-LAST:event_jBAlterarActionPerformed

    private void jBRemoverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBRemoverActionPerformed
        String dataProgramacao = jDCDataProgramacao.getDate()==null?"":new SimpleDateFormat("dd/MM/yyyy").format(jDCDataProgramacao.getDate());
        DemaisInfos escolhido = findByDataProgramacao(dataProgramacao);
        System.out.println(escolhido);
        if(escolhido == null) {
            JOptionPane.showMessageDialog(null, "A busca pela data não encontrou nada!\nData disponível para criação de um novo bloco!");
            return;
        }
        demaisInfosCadastro.remove(escolhido);
        JOptionPane.showMessageDialog(null, "Dado deletado com sucesso!", "Deleção", JOptionPane.WARNING_MESSAGE);
        
        if(demaisInfosCadastro.isEmpty()){
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("demaisInfos.txt"))) {
                writer.write("");
                return;
            } catch (IOException e ) {
                JOptionPane.showMessageDialog(null, "Erro ao salvar aquivo!");
            }
        }
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("demaisInfos.txt"))) {
            for (DemaisInfos demaisInfo: demaisInfosCadastro) {
                String idsMecanicos =  Optional.ofNullable(demaisInfo.getMecanicosInternos()).map(mecanicos -> mecanicos.stream().map(usuario -> String.valueOf(usuario.getId())).collect(Collectors.joining(","))).orElse("");
                System.out.println(idsMecanicos);
                String idsEletricistas = Optional.ofNullable(demaisInfo.getEletricistasInternos()).map(eletricistas -> eletricistas.stream().map(usuario -> String.valueOf(usuario.getId())).collect(Collectors.joining(","))).orElse("");
                System.out.println(idsEletricistas);
                String idFolgas =  Optional.ofNullable(demaisInfo.getFolgas()).map(folgas -> folgas.stream().map(usuario -> String.valueOf(usuario.getId())).collect(Collectors.joining(","))).orElse("");
                System.out.println(idFolgas);
                String idFerias =  Optional.ofNullable(demaisInfo.getFerias()).map(ferias -> ferias.stream().map(usuario -> String.valueOf(usuario.getId())).collect(Collectors.joining(","))).orElse("");
                System.out.println(idFerias);
                String demaisInfosSalvar = demaisInfo.getId()+"|"+demaisInfo.getDataProgramacao()+"|"+idsMecanicos+"|"+idsEletricistas+"|"+idFolgas+"|"+idFerias;

                writer.write(demaisInfosSalvar);
                writer.newLine();
            }
        } catch (IOException e ) {
            JOptionPane.showMessageDialog(null, "Erro ao salvar aquivo!");
        }
        
        
    }//GEN-LAST:event_jBRemoverActionPerformed

    private DemaisInfos findByDataProgramacao(String dataProgramacao) {
        return demaisInfosCadastro.stream()
        .filter(demaisInfos -> dataProgramacao.equals(demaisInfos.getDataProgramacao()))
        .findFirst()
        .orElse(null);
    }
    
        private void buscar() {
        String dataProgramacao = jDCDataProgramacao.getDate()==null?"":new SimpleDateFormat("dd/MM/yyyy").format(jDCDataProgramacao.getDate()); 
        if(dataBuscada.equals(dataProgramacao)){
            JOptionPane.showMessageDialog(null, "Esta data é a mesma que você procurou anteriormente!");
            return;
        }
        
        this.dataBuscada = dataProgramacao;
        if(dataProgramacao.equals("")){
            JOptionPane.showMessageDialog(null, "Ops! Escolha a data para buscar!");
            return;
        }
        boolean exists = false;
        for (DemaisInfos x: demaisInfosCadastro) {
            if(x.getDataProgramacao().equals(dataProgramacao)){
                exists = true;
                dLMInternosMec.removeAllElements();
                if(x.getMecanicosInternos() != null){
                    x.getMecanicosInternos().forEach(membro -> {
                        dLMInternosMec.addElement(membro.getNomeDeGuerra());
                    });
                    jLtInternosMecanica.setModel(dLMInternosMec);
                }
                
                dLMInternosElec.removeAllElements();
                if(x.getEletricistasInternos() != null){
                    x.getEletricistasInternos().forEach(membro -> {
                        dLMInternosElec.addElement(membro.getNomeDeGuerra());
                    });
                    jLtInternosEletrica.setModel(dLMInternosElec);
                }
                
                dLMFolgas.removeAllElements();
                if(x.getFolgas() != null){
                    x.getFolgas().forEach(membro -> {
                        dLMFolgas.addElement(membro.getNomeDeGuerra());
                    });
                    jLtFolgas.setModel(dLMFolgas);
                }
                
                dLMFerias.removeAllElements();
                if(x.getFerias() != null){
                    x.getFerias().forEach(membro -> {
                        dLMFerias.addElement(membro.getNomeDeGuerra());
                    });
                    jLtFerias.setModel(dLMFerias);
                }
                atualizarUsers();
                return;
            }else{
                dLMInternosMec.removeAllElements();
                dLMInternosElec.removeAllElements();
                dLMFolgas.removeAllElements();
                dLMFerias.removeAllElements();
            }
        }
        if(!exists){
            JOptionPane.showMessageDialog(null, "Ops! Nada para ver nesta data!");
            return;
        }
    }
    private void atualizarListas(){
        dLMUsers.removeAllElements();
        String[] itensUsers = usuarios.stream().map(Usuario::getNomeDeGuerra).sorted().toArray(String[]::new);
        for (String usuario : itensUsers) {
            
            dLMUsers.addElement(usuario);
        }
        jLColab.setModel(dLMUsers);
        
        dLMInternosMec.removeAllElements();
        jLtInternosMecanica.setModel(dLMInternosMec);
        dLMInternosElec.removeAllElements();
        jLtInternosEletrica.setModel(dLMInternosElec);
        dLMFolgas.removeAllElements();
        jLtFolgas.setModel(dLMFolgas);
        dLMFerias.removeAllElements();
        jLtFerias.setModel(dLMFerias);
    }
    
    private void atualizarUsers(){
        CadastroUsuario cadastroUser = new CadastroUsuario(usuarios);
        if (!cadastroUser.getListaAtualizadaUsuarios().isEmpty()) {
            String[] itensArray = cadastroUser.getListaAtualizadaUsuarios()
                .stream()
                .map(Usuario::getNomeDeGuerra)
                .sorted()
                .toArray(String[]::new);

            // Limpa o modelo de dados da lista `dLMUsers`.
            dLMUsers.removeAllElements();

            // Lista de itens que não estão em nenhuma das listas de "internos"
            List<String> itensNaoUsados = new ArrayList<>();

            // Adiciona ao `dLMUsers` apenas os itens que não estão em nenhuma das outras listas.
            for (String item : itensArray) {
                boolean existsInAnyList = 
                    IntStream.range(0, dLMInternosMec.getSize()).mapToObj(dLMInternosMec::getElementAt).anyMatch(nome -> nome.equals(item)) ||
                    IntStream.range(0, dLMInternosElec.getSize()).mapToObj(dLMInternosElec::getElementAt).anyMatch(nome -> nome.equals(item)) ||
                    IntStream.range(0, dLMFolgas.getSize()).mapToObj(dLMFolgas::getElementAt).anyMatch(nome -> nome.equals(item)) ||
                    IntStream.range(0, dLMFerias.getSize()).mapToObj(dLMFerias::getElementAt).anyMatch(nome -> nome.equals(item));

                // Se o item não está em nenhuma lista, adiciona à lista de itens não usados.
                if (!existsInAnyList) {
                    itensNaoUsados.add(item);
                }
            }

            // Agora, adicionamos os itens não usados ao `dLMUsers`.
            for (String item : itensNaoUsados) {
                dLMUsers.addElement(item);
            }

            // Finalmente, atualiza o modelo da JList com a lista atualizada.
            jLColab.setModel(dLMUsers);
        }
    }
    
    private void jBAddFeriasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBAddFeriasActionPerformed
        String nomeSelecionado = jLColab.getSelectedValue();
        
        if (nomeSelecionado != null) {
            boolean isSaved = false;
            for (int i = 0; i < dLMFerias.size(); i++) {
                if(dLMFerias.elementAt(i).equals(nomeSelecionado)){
                    isSaved = true;
                    break;
                }
            }

            if(isSaved){
                JOptionPane.showMessageDialog(null, "O Colaborador já está selecionado!");
                return;
            }
            
            dLMFerias.addElement(nomeSelecionado);
            jLtFerias.setModel(dLMFerias);
            dLMUsers.remove(jLColab.getSelectedIndex());
            
        } else {
            JOptionPane.showMessageDialog(null, "Nenhum colaborador selecionado na listagem!");
        }
    }//GEN-LAST:event_jBAddFeriasActionPerformed

    private void jBSubFeriasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBSubFeriasActionPerformed
        String itemSelecionado = jLtFerias.getSelectedValue();
        
        if (itemSelecionado != null) {
            dLMUsers.addElement(jLtFerias.getSelectedValue());
            dLMFerias.remove(jLtFerias.getSelectedIndex()); 
            atualizarRemove(dLMFerias);
        } else {
            JOptionPane.showMessageDialog(null, "Nenhum colaborador selecionado na listagem!");
        }
    }//GEN-LAST:event_jBSubFeriasActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBAddFerias;
    private javax.swing.JButton jBAddFolga;
    private javax.swing.JButton jBAddInternoElec;
    private javax.swing.JButton jBAddInternoMec;
    private javax.swing.JButton jBAlterar;
    private javax.swing.JButton jBCriar;
    private javax.swing.JButton jBRemover;
    private javax.swing.JButton jBSubFerias;
    private javax.swing.JButton jBSubFolga;
    private javax.swing.JButton jBSubInternoElec;
    private javax.swing.JButton jBSubInternoMec;
    private com.toedter.calendar.JDateChooser jDCDataProgramacao;
    private javax.swing.JList<String> jLColab;
    private javax.swing.JLabel jLColaboradores;
    private javax.swing.JLabel jLFolgas;
    private javax.swing.JLabel jLFundo;
    private javax.swing.JLabel jLFérias;
    private javax.swing.JLabel jLInternosMecanica;
    private javax.swing.JLabel jLProgramDia;
    private javax.swing.JLabel jLinternosEletrica;
    private javax.swing.JList<String> jLtFerias;
    private javax.swing.JList<String> jLtFolgas;
    private javax.swing.JList<String> jLtInternosEletrica;
    private javax.swing.JList<String> jLtInternosMecanica;
    private javax.swing.JScrollPane jSPColaboradores;
    private javax.swing.JScrollPane jSPSelecionados;
    private javax.swing.JScrollPane jSPSelecionados1;
    private javax.swing.JScrollPane jSPSelecionados2;
    private javax.swing.JScrollPane jSPSelecionados3;
    // End of variables declaration//GEN-END:variables
}
