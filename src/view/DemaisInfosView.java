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
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import programacao.model.DemaisInfos;
import programacao.model.Usuario;
/**
 *
 * @author Koroch
 */
public class DemaisInfosView extends javax.swing.JFrame {    
    private List<String> linhasArquivoUser = new ArrayList<>();
    private List<Usuario> usuarios = new ArrayList<>();
    private DefaultListModel<String> dLMUsers = new DefaultListModel<>();
    private DefaultListModel<String> dLMInternosMec = new DefaultListModel<>();
    private DefaultListModel<String> dLMInternosElec = new DefaultListModel<>();
    private DefaultListModel<String> dLMFolgas = new DefaultListModel<>();
    private DefaultListModel<String> dLMFerias = new DefaultListModel<>();
    
    /**
     * Creates new form DemaisInfos
     * @param demaisInfos
     * @param ultimoIdDemaisInfos
     */
    public DemaisInfosView(List<DemaisInfos> demaisInfos, int ultimoIdDemaisInfos) {
        initComponents();
        try (BufferedReader br = new BufferedReader(new FileReader("usuarios.txt"))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                linhasArquivoUser.add(linha);
            }
            
            linhasArquivoUser.forEach(elemento -> {
                String[] dados = elemento.split("\\|");
                usuarios.add(new Usuario(
                    Integer.parseInt(dados[0].trim()),
                    dados[1].trim(),
                    dados[2].trim(),
                    dados[3].trim(),
                    Boolean.parseBoolean(dados[4].trim())
                ));
            });
            
            if(!usuarios.isEmpty()){
                String[] itensArray = usuarios.stream().map(Usuario::getNomeDeGuerra).sorted().toArray(String[]::new);
                for (String item : itensArray) {
                    dLMUsers.addElement(item);
                }
                jLColab.setModel(dLMUsers);
            }
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
        jBLimpar = new javax.swing.JButton();
        jBBuscar = new javax.swing.JButton();
        jSPSelecionados3 = new javax.swing.JScrollPane();
        jLtFerias = new javax.swing.JList<>();
        jLFérias = new javax.swing.JLabel();
        jBAddFerias = new javax.swing.JButton();
        jBSubFerias = new javax.swing.JButton();
        jLFundo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        getContentPane().setLayout(null);

        jDCDataProgramacao.setDate(new java.util.Date());
        jDCDataProgramacao.setDateFormatString("dd/MM/yyyy");
        getContentPane().add(jDCDataProgramacao);
        jDCDataProgramacao.setBounds(160, 60, 350, 30);

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

        jBSubInternoMec.setFont(new java.awt.Font("Segoe UI", 1, 8)); // NOI18N
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

        jBAddInternoMec.setFont(new java.awt.Font("Segoe UI", 1, 8)); // NOI18N
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

        jBAddInternoElec.setFont(new java.awt.Font("Segoe UI", 1, 8)); // NOI18N
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

        jBSubInternoElec.setFont(new java.awt.Font("Segoe UI", 1, 8)); // NOI18N
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

        jBSubFolga.setFont(new java.awt.Font("Segoe UI", 1, 8)); // NOI18N
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

        jBAddFolga.setFont(new java.awt.Font("Segoe UI", 1, 8)); // NOI18N
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
        jBCriar.setText("Adicionar");
        jBCriar.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jBCriar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBCriarActionPerformed1(evt);
            }
        });
        getContentPane().add(jBCriar);
        jBCriar.setBounds(160, 480, 110, 30);

        jBAlterar.setBackground(new java.awt.Color(204, 204, 204));
        jBAlterar.setText("Alterar");
        jBAlterar.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jBAlterar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBAlterarActionPerformed(evt);
            }
        });
        getContentPane().add(jBAlterar);
        jBAlterar.setBounds(270, 480, 110, 30);

        jBRemover.setBackground(new java.awt.Color(204, 204, 204));
        jBRemover.setText("Remover");
        jBRemover.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jBRemover.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBRemoverActionPerformed(evt);
            }
        });
        getContentPane().add(jBRemover);
        jBRemover.setBounds(380, 480, 110, 30);

        jBLimpar.setBackground(new java.awt.Color(102, 102, 102));
        jBLimpar.setText("Limpar");
        jBLimpar.setToolTipText("Limpar todos dados dos campos.");
        jBLimpar.setFocusable(false);
        jBLimpar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBLimparActionPerformed(evt);
            }
        });
        getContentPane().add(jBLimpar);
        jBLimpar.setBounds(510, 480, 70, 30);

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
        jBBuscar.setBounds(530, 50, 50, 40);

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

        jBAddFerias.setFont(new java.awt.Font("Segoe UI", 1, 8)); // NOI18N
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

        jBSubFerias.setFont(new java.awt.Font("Segoe UI", 1, 8)); // NOI18N
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

    private void atualizarRemove(DefaultListModel<String> dfList){
        CadastroUsuario cadastroUser = new CadastroUsuario(usuarios);
        if(!cadastroUser.getListaAtualizadaUsuarios().isEmpty()){
            String[] itensArray = cadastroUser.getListaAtualizadaUsuarios().stream().map(Usuario::getNomeDeGuerra).sorted().toArray(String[]::new);
            List<String> itensNaoUsados = new ArrayList<>();
            dLMUsers.removeAllElements();
            for (String item : itensArray) {
                if(!IntStream.range(0, dfList.getSize())
                    .mapToObj(dfList::getElementAt)
                    .anyMatch(nome -> nome.equals(item))){
                        itensNaoUsados.add(item);
                }
            }
            
            
            for (String item : itensNaoUsados) {
                dLMUsers.addElement(item);
            }
            jLColab.setModel(dLMUsers);
        }
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
            if(!isSaved){
                dLMInternosMec.addElement(nomeSelecionado);
                jLtInternosMecanica.setModel(dLMInternosMec);
                dLMUsers.remove(jLColab.getSelectedIndex());
            }
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
            if(!isSaved){
                dLMInternosElec.addElement(nomeSelecionado);
                jLtInternosEletrica.setModel(dLMInternosElec);
                dLMUsers.remove(jLColab.getSelectedIndex());
            }
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
            if(!isSaved){
                dLMFolgas.addElement(nomeSelecionado);
                jLtFolgas.setModel(dLMFolgas);
                dLMUsers.remove(jLColab.getSelectedIndex());
            }
        } else {
            JOptionPane.showMessageDialog(null, "Nenhum colaborador selecionado na listagem!");
        }
    }//GEN-LAST:event_jBAddFolgaActionPerformed

    private void jBCriarActionPerformed1(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBCriarActionPerformed1
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
        
    }//GEN-LAST:event_jBAlterarActionPerformed

    private void jBRemoverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBRemoverActionPerformed
        
       
    }//GEN-LAST:event_jBRemoverActionPerformed

    private void jBLimparActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBLimparActionPerformed
        
    }//GEN-LAST:event_jBLimparActionPerformed

    private void jBBuscarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBBuscarMouseClicked

    }//GEN-LAST:event_jBBuscarMouseClicked

    private void jBBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBBuscarActionPerformed
        
    }//GEN-LAST:event_jBBuscarActionPerformed

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
            if(!isSaved){
                dLMFerias.addElement(nomeSelecionado);
                jLtFerias.setModel(dLMFerias);
                dLMUsers.remove(jLColab.getSelectedIndex());
            }
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
    private javax.swing.JButton jBBuscar;
    private javax.swing.JButton jBCriar;
    private javax.swing.JButton jBLimpar;
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
