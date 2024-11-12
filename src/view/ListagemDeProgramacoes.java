/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view;

import java.awt.Component;
import java.awt.Font;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import programacao.model.Bloco;
import programacao.model.DemaisInfos;
import programacao.model.Usuario;

/**
 *
 * @author Koroch
 */
public class ListagemDeProgramacoes extends javax.swing.JFrame {
    private List<Bloco> blocos = new ArrayList<>();
    private List<DemaisInfos> demaisInfos = new ArrayList<>();
    
    public ListagemDeProgramacoes(List<DemaisInfos> demaisInfos, int ultimoIdDemaisInfos, List<Bloco> blocos, int ultimoIdBlocos) {
        initComponents();
        this.blocos = blocos;
        this.demaisInfos = demaisInfos;
        preencheCB(blocos, demaisInfos);
        configurarModeloTabela();
    }
    @SuppressWarnings("unchecked")
    
    private void configurarModeloTabela() {
        DefaultTableModel model = (DefaultTableModel) jTProgramação.getModel();
        model.setColumnIdentifiers(new String[] { "Programação" });
        
        jTProgramação.getColumnModel().getColumn(0).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, 
                                                           boolean hasFocus, int row, int column) {
                // Obtém o componente padrão
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                // Verifica se a célula contém o texto específico para aplicar negrito
                if (value != null && value.toString().startsWith("Cliente:".toUpperCase()) || value != null && value.toString().startsWith("VISITA COMERCIAL")) {
                    c.setFont(c.getFont().deriveFont(Font.BOLD)); // Define o texto como negrito
                } else {
                    c.setFont(c.getFont().deriveFont(Font.PLAIN)); // Mantém o estilo normal
                }

                return c;
            }
        });
    }
    
    private void preencheCB(List<Bloco> blocos, List<DemaisInfos> demaisInfos){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        Set<String> datasUnicas = Stream.concat(
            demaisInfos.stream().map(DemaisInfos::getDataProgramacao),
            blocos.stream().map(Bloco::getDataProgramacao)
        )
        .filter(data -> data != null && !data.isEmpty())
        .map(data -> LocalDate.parse(data, formatter))
        .sorted()
        .map(formatter::format) 
        .collect(Collectors.toCollection(LinkedHashSet::new)); 

        datasUnicas.forEach(jCBDatasUnicas::addItem);
    }
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLProg = new javax.swing.JLabel();
        jCB = new javax.swing.JPanel();
        jLProgramDia = new javax.swing.JLabel();
        jCBDatasUnicas = new javax.swing.JComboBox<>();
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
        jLProg.setBounds(500, 10, 350, 25);

        jCB.setBackground(new java.awt.Color(7, 30, 74));
        jCB.setToolTipText("");
        jCB.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jCB.setFocusable(false);
        jCB.setName(""); // NOI18N
        jCB.setLayout(null);

        jLProgramDia.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLProgramDia.setForeground(new java.awt.Color(255, 255, 255));
        jLProgramDia.setText("Escolha a programação do dia desejado:");
        jLProgramDia.setToolTipText("");
        jCB.add(jLProgramDia);
        jLProgramDia.setBounds(320, 10, 280, 16);

        jCBDatasUnicas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCBDatasUnicasActionPerformed(evt);
            }
        });
        jCB.add(jCBDatasUnicas);
        jCBDatasUnicas.setBounds(320, 30, 570, 30);

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

    private void jCBDatasUnicasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCBDatasUnicasActionPerformed
        String dataEscolhida = String.valueOf(jCBDatasUnicas.getSelectedItem());
        populateTable(dataEscolhida);
    }//GEN-LAST:event_jCBDatasUnicasActionPerformed

    public void populateTable(String dataEscolhida) {
        DefaultTableModel model = (DefaultTableModel) jTProgramação.getModel();
        model.setRowCount(0); // Limpa as linhas antes de adicionar novas

        // Filtrando e adicionando dados de `blocos`
        blocos.stream()
            .filter(obj -> obj.getDataProgramacao().equals(dataEscolhida))
            .forEach(bloco -> {
                String carretao = "";
                if(!"N/A".equals(bloco.getCarretao()))    
                    carretao = " com " + bloco.getCarretao().toLowerCase();
                if(bloco.getFinalidade().equals("VISITA COMERCIAL")){
                    model.addRow(new Object[] { bloco.getFinalidade()});
                }else{
                    model.addRow(new Object[] { ("Cliente: " + (bloco.getCliente() != null ? bloco.getCliente().getNome() : "N/A")+ " -> " + bloco.getFinalidade()).toUpperCase()});
                }
                model.addRow(new Object[] { "Equipe: " + bloco.getEquipe().stream().map(Usuario::getNomeDeGuerra).collect(Collectors.joining(", ")) });
                if(bloco.getProjeto()!=null&&!bloco.getProjeto().equals("")&&!bloco.getProjeto().equals("null")&&!bloco.getProjeto().equals("N/A")){
                    model.addRow(new Object[] { "Projeto: " + bloco.getProjeto() });
                }
                model.addRow(new Object[] { "Responsável do Trabalho: " + (bloco.getResponsavelDoTrabalho() != null ? bloco.getResponsavelDoTrabalho().getNomeDeGuerra(): "N/A") });
                model.addRow(new Object[] { "Carro: " + (bloco.getCarro() != null ? bloco.getCarro().getNome() : "N/A") + carretao });
                model.addRow(new Object[] { "Data de Saída: " + bloco.getDataDeSaida() });
                model.addRow(new Object[] { "Data de Retorno: " + bloco.getDataDeRetorno() });
                model.addRow(new Object[] { "Horário de Saída: " + bloco.getHorarioDeSaida() });
                if(!("Horário de Trabalho: " + bloco.getHorarioDeTrabalhoInicio()+"H AS " + bloco.getHorarioDeTrabalhoFimMeioDia() +"Hs " + bloco.getHorarioDeTrabalhoInicioMeioDia() +"Hs AS " + bloco.getHorarioDeTrabalhoFim() +"Hs").equals("Horário de Trabalho: H AS Hs Hs AS Hs")){
                    model.addRow(new Object[] { "Horário de Trabalho: " + bloco.getHorarioDeTrabalhoInicio()+"H AS " + bloco.getHorarioDeTrabalhoFimMeioDia() +"Hs " + bloco.getHorarioDeTrabalhoInicioMeioDia() +"Hs AS " + bloco.getHorarioDeTrabalhoFim() +"Hs"});
                }
                if(bloco.getAlmoco()!=null&&!bloco.getAlmoco().equals("")&&!bloco.getAlmoco().equals("null")&&!bloco.getAlmoco().equals("N/A")){
                    model.addRow(new Object[] { "Almoço: " + bloco.getAlmoco() });
                }
                if(bloco.getJanta()!=null&&!bloco.getJanta().equals("")&&!bloco.getJanta().equals("null")&&!bloco.getJanta().equals("N/A")){
                    model.addRow(new Object[] { "Janta: " + bloco.getJanta() });
                }
                if(bloco.getHospedagem() != null){
                    model.addRow(new Object[] { "Hospedagem: " + (bloco.getHospedagem().getNomeComCidadeEEstado()) });
                }
                model.addRow(new Object[] { "" });
            });

        // Filtrando e adicionando dados de `demaisInfos`
        demaisInfos.stream()
            .filter(obj -> obj.getDataProgramacao().equals(dataEscolhida))
            .findFirst()
            .ifPresent(info -> {
                if(!Optional.ofNullable(info.getMecanicosInternos()).orElse(new ArrayList<>()).stream().map(Usuario::getNomeDeGuerra).collect(Collectors.joining(", ")).equals("")){
                    model.addRow(new Object[] { "Mecânicos Internos: " + Optional.ofNullable(info.getMecanicosInternos()).orElse(new ArrayList<>()).stream().map(Usuario::getNomeDeGuerra).collect(Collectors.joining(", ")) });
                }
                if(!Optional.ofNullable(info.getEletricistasInternos()).orElse(new ArrayList<>()).stream().map(Usuario::getNomeDeGuerra).collect(Collectors.joining(", ")).equals("")){
                    model.addRow(new Object[] { "Eletricistas Internos: " + Optional.ofNullable(info.getEletricistasInternos()).orElse(new ArrayList<>()).stream().map(Usuario::getNomeDeGuerra).collect(Collectors.joining(", ")) });
                }
                if(!Optional.ofNullable(info.getFolgas()).orElse(new ArrayList<>()).stream().map(Usuario::getNomeDeGuerra).collect(Collectors.joining(", ")).equals("")){
                    model.addRow(new Object[] { "Folgas: " + Optional.ofNullable(info.getFolgas()).orElse(new ArrayList<>()).stream().map(Usuario::getNomeDeGuerra).collect(Collectors.joining(", ")) });
                }
                if(!Optional.ofNullable(info.getFerias()).orElse(new ArrayList<>()).stream().map(Usuario::getNomeDeGuerra).collect(Collectors.joining(", ")).equals("")){
                    model.addRow(new Object[] { "Férias: " + Optional.ofNullable(info.getFerias()).orElse(new ArrayList<>()).stream().map(Usuario::getNomeDeGuerra).collect(Collectors.joining(", ")) });
                }
                model.addRow(new Object[] { "" });
            });
        
        
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jCB;
    private javax.swing.JComboBox<String> jCBDatasUnicas;
    private javax.swing.JLabel jLFundo;
    private javax.swing.JLabel jLProg;
    private javax.swing.JLabel jLProgramDia;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTProgramação;
    // End of variables declaration//GEN-END:variables
}
