/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Component;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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
                if (value != null && value.toString().startsWith("OBS:") || value != null && value.toString().startsWith("CLIENTE:") || (value != null && value.toString().startsWith("VISITA COMERCIAL"))) {
                        c.setFont(c.getFont().deriveFont(java.awt.Font.BOLD)); 
                } else {
                    c.setFont(c.getFont().deriveFont(java.awt.Font.PLAIN)); 
                }

                return c;
            }
        });
    }
    
    private void preencheCB(List<Bloco> blocos, List<DemaisInfos> demaisInfos){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    
        Set<LocalDate> datasUnicas = Stream.concat(
            demaisInfos.stream().map(DemaisInfos::getDataProgramacao),
            blocos.stream().map(Bloco::getDataProgramacao)
        )
        .filter(data -> data != null && !data.isEmpty())
        .map(data -> LocalDate.parse(data, formatter))
        .sorted(Comparator.reverseOrder()) 
        .collect(Collectors.toCollection(LinkedHashSet::new));

        datasUnicas.forEach(data -> jCBDatasUnicas.addItem(formatter.format(data)));
    }
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLProg = new javax.swing.JLabel();
        jCB = new javax.swing.JPanel();
        jLProgramDia = new javax.swing.JLabel();
        jCBDatasUnicas = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTProgramação = new javax.swing.JTable();
        jBPDF = new javax.swing.JButton();
        JBXlsx = new javax.swing.JButton();
        jTFDiaSemana = new javax.swing.JTextField();
        jBCopiar = new javax.swing.JButton();
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
        jLProgramDia.setBounds(340, 10, 280, 16);

        jCBDatasUnicas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCBDatasUnicasActionPerformed(evt);
            }
        });
        jCB.add(jCBDatasUnicas);
        jCBDatasUnicas.setBounds(340, 30, 290, 30);

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
        jTProgramação.setEnabled(false);
        jScrollPane1.setViewportView(jTProgramação);

        jCB.add(jScrollPane1);
        jScrollPane1.setBounds(160, 70, 880, 490);

        jBPDF.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/pdf.png"))); // NOI18N
        jBPDF.setMaximumSize(new java.awt.Dimension(52, 52));
        jBPDF.setMinimumSize(new java.awt.Dimension(52, 52));
        jBPDF.setPreferredSize(new java.awt.Dimension(52, 52));
        jBPDF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBPDFActionPerformed(evt);
            }
        });
        jCB.add(jBPDF);
        jBPDF.setBounds(980, 570, 52, 52);

        JBXlsx.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/xlsx.PNG"))); // NOI18N
        JBXlsx.setMaximumSize(new java.awt.Dimension(52, 52));
        JBXlsx.setMinimumSize(new java.awt.Dimension(52, 52));
        JBXlsx.setPreferredSize(new java.awt.Dimension(52, 52));
        JBXlsx.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JBXlsxActionPerformed(evt);
            }
        });
        jCB.add(JBXlsx);
        JBXlsx.setBounds(920, 570, 52, 52);

        jTFDiaSemana.setEditable(false);
        jTFDiaSemana.setBackground(new java.awt.Color(0, 0, 51));
        jTFDiaSemana.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jTFDiaSemana.setForeground(new java.awt.Color(255, 255, 255));
        jTFDiaSemana.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTFDiaSemana.setAutoscrolls(false);
        jTFDiaSemana.setFocusable(false);
        jTFDiaSemana.setRequestFocusEnabled(false);
        jTFDiaSemana.setVerifyInputWhenFocusTarget(false);
        jCB.add(jTFDiaSemana);
        jTFDiaSemana.setBounds(640, 30, 170, 30);

        jBCopiar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/transf.png"))); // NOI18N
        jBCopiar.setPreferredSize(new java.awt.Dimension(52, 52));
        jBCopiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBCopiarActionPerformed(evt);
            }
        });
        jCB.add(jBCopiar);
        jBCopiar.setBounds(860, 570, 52, 52);

        getContentPane().add(jCB);
        jCB.setBounds(0, 40, 1200, 630);

        jLFundo.setBackground(new java.awt.Color(51, 51, 51));
        jLFundo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/tela_de_fundo.jpeg"))); // NOI18N
        jLFundo.setToolTipText("");
        getContentPane().add(jLFundo);
        jLFundo.setBounds(-40, -210, 1360, 1120);

        setSize(new java.awt.Dimension(1209, 712));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jCBDatasUnicasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCBDatasUnicasActionPerformed
        String dataEscolhida = String.valueOf(jCBDatasUnicas.getSelectedItem());
        String data = dataEscolhida;
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate dataDate = LocalDate.parse(data, formato);
        jTFDiaSemana.setText(dataDate.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.getDefault()).toUpperCase());
        populateTable(dataEscolhida);
    }//GEN-LAST:event_jCBDatasUnicasActionPerformed

    private void jBPDFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBPDFActionPerformed
        try {
            Document documento = new Document();
            PdfWriter.getInstance(documento, new FileOutputStream("files/Programação-"+jCBDatasUnicas.getSelectedItem().toString().replaceAll("/", ".")+".pdf"));
            documento.open();

            PdfPTable tabela = new PdfPTable(jTProgramação.getColumnCount());
            tabela.setWidthPercentage(100);
            tabela.getDefaultCell().setBorderWidth(0);

            // Adicionar cabeçalho da tabela
            PdfPCell celula1 = new PdfPCell(new Phrase("PROGRAMAÇÃO DO DIA " + jCBDatasUnicas.getSelectedItem().toString() + " - " +jTFDiaSemana.getText()));
            celula1.setBorderWidth(0);
            tabela.addCell(celula1);
            PdfPCell celula2 = new PdfPCell(new Phrase(" "));
            celula2.setBorderWidth(0);
            tabela.addCell(celula2);
            
            // Adicionar linhas da tabela
            for (int i = 0; i < jTProgramação.getRowCount(); i++) {
                PdfPCell celula = new PdfPCell(new Phrase(String.valueOf(jTProgramação.getValueAt(i, 0))));
                celula.setBorderWidth(0);
                String valor = String.valueOf(jTProgramação.getValueAt(i, 0)).equals("")?" ":String.valueOf(jTProgramação.getValueAt(i, 0));
                if (valor.startsWith("CLIENTE:") || valor.startsWith("OBS:") || valor.startsWith("VISITA COMERCIAL")) {
                    Font fonteNegrito = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
                    celula.setPhrase(new Phrase(valor, fonteNegrito));
                } else {
                    celula.setPhrase(new Phrase(valor));
                }
                
                tabela.addCell(celula);
            }

            documento.add(tabela);
            documento.close();
            JOptionPane.showMessageDialog(null, "PDF gerado com sucesso!");
        } catch (DocumentException | IOException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao gerar PDF: " + ex.getMessage());
        }
    }//GEN-LAST:event_jBPDFActionPerformed

    private void JBXlsxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JBXlsxActionPerformed
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Planilha");

        // Adicionar cabeçalho da tabela
        Row row = sheet.createRow(0);
        Cell cell0 = row.createCell(0);
        cell0.setCellValue("PROGRAMAÇÃO DO DIA " + jCBDatasUnicas.getSelectedItem().toString() + " - " +jTFDiaSemana.getText());
        
        // Adicionar linhas da tabela
        for (int i = 1; i <= jTProgramação.getRowCount(); i++) {
            row = sheet.createRow(i+1);
            Cell cell = row.createCell(0);
            String valor = String.valueOf(jTProgramação.getValueAt(i-1, 0)).equals("")?" ":String.valueOf(jTProgramação.getValueAt(i-1, 0));
            cell.setCellValue(valor);
        }

        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream("files/Programação-"+jCBDatasUnicas.getSelectedItem().toString().replaceAll("/", ".")+".xlsx");
            workbook.write(fileOutputStream);
            fileOutputStream.close();
            workbook.close();
            JOptionPane.showMessageDialog(null, "Planilha gerada com sucesso!");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao gerar Xlsx: " + ex.getMessage());
        }
    
    }//GEN-LAST:event_JBXlsxActionPerformed

    private void jBCopiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBCopiarActionPerformed
        StringBuilder texto = new StringBuilder();
        texto.append(("*PROGRAMAÇÃO DO DIA " + jCBDatasUnicas.getSelectedItem().toString() + " - " +jTFDiaSemana.getText()+"*\n\n")); 
        
        for (int i = 0; i < jTProgramação.getRowCount(); i++) {
            Object valor = jTProgramação.getValueAt(i, 0);
            if (valor != null) {
                texto.append(valor.toString());
            } else {
                texto.append("");
            }
            texto.append("\n"); 
        }

        StringSelection selection = new StringSelection(texto.toString());
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(selection, null);
        JOptionPane.showMessageDialog(null, "Texto copiado para área de transferência!");
    }//GEN-LAST:event_jBCopiarActionPerformed

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
                    String finalidadeRef = bloco.getFinalidade().toUpperCase().equals("OCULTAR") ? "" : " - \"" + bloco.getFinalidade().toUpperCase() + "\"";
                    model.addRow(new Object[] { "CLIENTE: " + (bloco.getCliente() != null ? bloco.getCliente().getNome() : "N/A")+ finalidadeRef});
                }
                model.addRow(new Object[] { "Equipe: " + bloco.getEquipe().stream().map(Usuario::getNomeDeGuerra).collect(Collectors.joining(", ")) });
                if(bloco.getProjeto()!=null&&!bloco.getProjeto().equals("")&&!bloco.getProjeto().equals("null")&&!bloco.getProjeto().equals("N/A")){
                    model.addRow(new Object[] { "Projeto: " + bloco.getProjeto() });
                }
                if(!bloco.getFinalidade().equals("VISITA COMERCIAL")){
                    model.addRow(new Object[] { "Responsável do Trabalho: " + (bloco.getResponsavelDoTrabalho() != null ? bloco.getResponsavelDoTrabalho().getNomeDeGuerra(): "N/A") });
                }
                model.addRow(new Object[] { "Carro: " + (bloco.getCarro() != null ? bloco.getCarro().getNome() : "N/A") + carretao });
                model.addRow(new Object[] { "Data de Saída: " + bloco.getDataDeSaida() });
                model.addRow(new Object[] { "Data de Retorno: " + bloco.getDataDeRetorno() });
                model.addRow(new Object[] { "Horário de Saída: " + bloco.getHorarioDeSaida() + "H"});
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
                if(bloco.getObservacoes()!=null&&!bloco.getObservacoes().equals("")&&!bloco.getObservacoes().equals("null")&&!bloco.getObservacoes().equals("N/A")){
                    model.addRow(new Object[] { "OBS: " + bloco.getObservacoes().toUpperCase() });
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
    private javax.swing.JButton JBXlsx;
    private javax.swing.JButton jBCopiar;
    private javax.swing.JButton jBPDF;
    private javax.swing.JPanel jCB;
    private javax.swing.JComboBox<String> jCBDatasUnicas;
    private javax.swing.JLabel jLFundo;
    private javax.swing.JLabel jLProg;
    private javax.swing.JLabel jLProgramDia;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTFDiaSemana;
    private javax.swing.JTable jTProgramação;
    // End of variables declaration//GEN-END:variables
}
