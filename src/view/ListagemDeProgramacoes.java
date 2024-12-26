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
import java.awt.Color;
import java.awt.Component;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
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
    private Date firstClickDate = null;
    private Date secondClickDate = null;
    
    public ListagemDeProgramacoes(List<DemaisInfos> demaisInfos, int ultimoIdDemaisInfos, List<Bloco> blocos, int ultimoIdBlocos) {
        initComponents();
        jLbCalendar.setVisible(false);
        jLbCalendar.setEnabled(false);
        jPCalendar.setVisible(false);
        jPCalendar.setEnabled(false);
        this.blocos = blocos;
        this.demaisInfos = demaisInfos;
        preencheCB(blocos, demaisInfos);
        configurarModeloTabela();
    }
    
    private void configurarModeloTabela() {
        DefaultTableModel model = (DefaultTableModel) jTProgramação.getModel();
        model.setColumnIdentifiers(new String[] { "Programação" });
        
        jTProgramação.getColumnModel().getColumn(0).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, 
                                                           boolean hasFocus, int row, int column) {
                // Obtém o componente padrão
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                if (isSelected) {
                    c.setBackground(table.getSelectionBackground());
                    c.setForeground(table.getSelectionForeground());
                } else {
                    c.setBackground(table.getBackground());
                    c.setForeground(table.getForeground());
                }
                
                // Verifica se a célula contém o texto específico para aplicar negrito
                if (value != null && value.toString().startsWith("PROGRAMAÇÃO DO DIA") || value != null && value.toString().startsWith("OBS:") || value != null && value.toString().startsWith("CLIENTE:") || (value != null && value.toString().startsWith("VISITA COMERCIAL")) || (value != null && value.toString().startsWith("MECÂNICOS INTERNOS:")) || (value != null && value.toString().startsWith("ELETRICISTAS INTERNOS:")) || (value != null && value.toString().startsWith("FOLGAS:")) || (value != null && value.toString().startsWith("FÉRIAS:"))) {
                        c.setFont(c.getFont().deriveFont(java.awt.Font.BOLD)); 
                } else {
                    c.setFont(c.getFont().deriveFont(java.awt.Font.PLAIN)); 
                }
                
                if (!jTFBuscar.getText().trim().equals("") && value != null 
                && value.toString().toUpperCase().contains(jTFBuscar.getText().trim().toUpperCase())) {
                c.setBackground(Color.BLUE);
                c.setForeground(Color.WHITE);
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

        addDatas(datasUnicas);
    }
    
    private void addDatas(Set<LocalDate> datasUnicas){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        for (int i = 0; i < jCBDatasUnicas.getItemCount(); i++) {
            String item = jCBDatasUnicas.getItemAt(i);
            if (!datasUnicas.contains(LocalDate.parse(item, formatter))) {
                jCBDatasUnicas.removeItem(item);
                i--; 
            }
        }

        datasUnicas.forEach(data -> {
            String formattedData = formatter.format(data);
            boolean existe = false;

            for (int i = 0; i < jCBDatasUnicas.getItemCount(); i++) {
                if (jCBDatasUnicas.getItemAt(i).equals(formattedData)) {
                    existe = true;
                    break;
                }
            }
            
            if (!existe) {
                jCBDatasUnicas.addItem(formattedData);
            }
        });
    }   
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLProg = new javax.swing.JLabel();
        jLbCalendar = new javax.swing.JLabel();
        jPCalendar = new javax.swing.JPanel();
        jCalendar1 = new com.toedter.calendar.JCalendar();
        jBtEnviarRange = new javax.swing.JButton();
        jBtReset = new javax.swing.JButton();
        jCB = new javax.swing.JPanel();
        jLProgramDia = new javax.swing.JLabel();
        jCBDatasUnicas = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTProgramação = new javax.swing.JTable();
        jBPDF = new javax.swing.JButton();
        JBXlsx = new javax.swing.JButton();
        jTFDiaSemana = new javax.swing.JTextField();
        jBCopiar = new javax.swing.JButton();
        jBBuscar = new javax.swing.JButton();
        jTFBuscar = new javax.swing.JTextField();
        jCBxIntervalo = new javax.swing.JCheckBox();
        jLFundo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        getContentPane().setLayout(null);

        jLProg.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLProg.setForeground(new java.awt.Color(255, 255, 255));
        jLProg.setText("Visualizar Programações");
        getContentPane().add(jLProg);
        jLProg.setBounds(500, 10, 350, 25);

        jLbCalendar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLbCalendar.setForeground(new java.awt.Color(255, 255, 255));
        jLbCalendar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLbCalendar.setToolTipText("");
        getContentPane().add(jLbCalendar);
        jLbCalendar.setBounds(410, 390, 380, 60);

        jPCalendar.setBackground(new java.awt.Color(0, 51, 102));
        jPCalendar.setLayout(null);

        jCalendar1.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jCalendar1PropertyChange(evt);
            }
        });
        jPCalendar.add(jCalendar1);
        jCalendar1.setBounds(10, 10, 360, 230);

        jBtEnviarRange.setText("CONFIRMAR INTERVALO");
        jBtEnviarRange.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtEnviarRangeActionPerformed(evt);
            }
        });
        jPCalendar.add(jBtEnviarRange);
        jBtEnviarRange.setBounds(10, 270, 240, 40);

        jBtReset.setText("RESET");
        jBtReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtResetActionPerformed(evt);
            }
        });
        jPCalendar.add(jBtReset);
        jBtReset.setBounds(260, 270, 110, 40);

        getContentPane().add(jPCalendar);
        jPCalendar.setBounds(410, 160, 380, 320);

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
        jLProgramDia.setBounds(190, 10, 280, 16);

        jCBDatasUnicas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCBDatasUnicasActionPerformed(evt);
            }
        });
        jCB.add(jCBDatasUnicas);
        jCBDatasUnicas.setBounds(190, 30, 290, 30);

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
        jScrollPane1.setBounds(170, 90, 880, 470);

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
        jTFDiaSemana.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTFDiaSemanaActionPerformed(evt);
            }
        });
        jCB.add(jTFDiaSemana);
        jTFDiaSemana.setBounds(510, 30, 170, 30);

        jBCopiar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/transf.png"))); // NOI18N
        jBCopiar.setPreferredSize(new java.awt.Dimension(52, 52));
        jBCopiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBCopiarActionPerformed(evt);
            }
        });
        jCB.add(jBCopiar);
        jBCopiar.setBounds(860, 570, 52, 52);

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
        jCB.add(jBBuscar);
        jBBuscar.setBounds(990, 20, 40, 40);

        jTFBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTFBuscarActionPerformed(evt);
            }
        });
        jCB.add(jTFBuscar);
        jTFBuscar.setBounds(710, 30, 270, 30);

        jCBxIntervalo.setForeground(new java.awt.Color(255, 255, 255));
        jCBxIntervalo.setText(" Intervalo de datas");
        jCBxIntervalo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCBxIntervaloActionPerformed(evt);
            }
        });
        jCB.add(jCBxIntervalo);
        jCBxIntervalo.setBounds(190, 60, 140, 20);

        getContentPane().add(jCB);
        jCB.setBounds(0, 40, 1210, 640);

        jLFundo.setBackground(new java.awt.Color(51, 51, 51));
        jLFundo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/tela_de_fundo.jpeg"))); // NOI18N
        jLFundo.setToolTipText("");
        getContentPane().add(jLFundo);
        jLFundo.setBounds(-40, -210, 1360, 1120);

        setSize(new java.awt.Dimension(1209, 712));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jCBDatasUnicasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCBDatasUnicasActionPerformed
        if(jCBDatasUnicas.getComponentCount() > 0){
            String dataEscolhida = String.valueOf(jCBDatasUnicas.getSelectedItem());
            String data = dataEscolhida;
            DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate dataDate = LocalDate.parse(data, formato);
            jTFDiaSemana.setText(dataDate.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.getDefault()).toUpperCase());
            DefaultTableModel model = (DefaultTableModel) jTProgramação.getModel();
            model.setRowCount(0); 
            populateTable(dataEscolhida, model);
        }
    }//GEN-LAST:event_jCBDatasUnicasActionPerformed

    private void jBPDFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBPDFActionPerformed
        try {
            Document documento = new Document();
            if(!jCBxIntervalo.isSelected()){
                PdfWriter.getInstance(documento, new FileOutputStream("files/Programação-"+jCBDatasUnicas.getSelectedItem().toString().replaceAll("/", ".")+".pdf"));
            }else{
                PdfWriter.getInstance(documento, new FileOutputStream("files/Programação-multi-datas.pdf"));
            }
            documento.open();

            PdfPTable tabela = new PdfPTable(jTProgramação.getColumnCount());
            tabela.setWidthPercentage(100);
            tabela.getDefaultCell().setBorderWidth(0);

            // Adicionar cabeçalho da tabela
            //PdfPCell celula1 = new PdfPCell(new Phrase("PROGRAMAÇÃO DO DIA " + jCBDatasUnicas.getSelectedItem().toString() + " - " +jTFDiaSemana.getText()));
//            celula1.setBorderWidth(0);
//            tabela.addCell(celula1);
//            PdfPCell celula2 = new PdfPCell(new Phrase(" "));
//            celula2.setBorderWidth(0);
//            tabela.addCell(celula2);
            
            // Adicionar linhas da tabela
            for (int i = 0; i < jTProgramação.getRowCount(); i++) {
                PdfPCell celula = new PdfPCell(new Phrase(String.valueOf(jTProgramação.getValueAt(i, 0))));
                celula.setBorderWidth(0);
                String valor = String.valueOf(jTProgramação.getValueAt(i, 0)).equals("")?" ":String.valueOf(jTProgramação.getValueAt(i, 0));
                if (valor.startsWith("MECÂNICOS INTERNOS:") || valor.startsWith("ELETRICISTAS INTERNOS:") || valor.startsWith("FOLGAS:") || valor.startsWith("FÉRIAS:") || valor.startsWith("PROGRAMAÇÃO DO DIA") || valor.startsWith("CLIENTE:") || valor.startsWith("OBS:") || valor.startsWith("VISITA COMERCIAL")) {
                    Font fonteNegrito = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
                    if(valor.startsWith("PROGRAMAÇÃO DO DIA")) {
                        celula.setPhrase(new Phrase("\n\n"+valor, fonteNegrito));
                    }else{
                         celula.setPhrase(new Phrase(valor, fonteNegrito));
                    }
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
        //cell0.setCellValue("PROGRAMAÇÃO DO DIA " + jCBDatasUnicas.getSelectedItem().toString() + " - " +jTFDiaSemana.getText());
        
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
        //texto.append(("*PROGRAMAÇÃO DO DIA " + jCBDatasUnicas.getSelectedItem().toString() + " - " +jTFDiaSemana.getText()+"*\n\n")); 
        texto.append("\n"); 
        for (int i = 0; i < jTProgramação.getRowCount(); i++) {
            Object valor = jTProgramação.getValueAt(i, 0);
            if (valor != null) {
                if(valor.toString().contains("PROGRAMAÇÃO DO DIA ") || valor.toString().contains("CLIENTE:") ||  valor.toString().startsWith("MECÂNICOS INTERNOS:") || valor.toString().startsWith("ELETRICISTAS INTERNOS:") || valor.toString().startsWith("FOLGAS:") || valor.toString().startsWith("FÉRIAS:")){
                    texto.append("*"+valor.toString().trim()+"*");
                    if(valor.toString().contains("CLIENTE:")){
                        texto.append("\n");
                    }
                }else{
                    texto.append(valor.toString());
                }
            } else {
                texto.append("");
            }
            texto.append("\n");
            if(valor.equals("")){
                texto.append("\n");
            }
        }

        StringSelection selection = new StringSelection(texto.toString());
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(selection, null);
        JOptionPane.showMessageDialog(null, "Texto copiado para área de transferência!");
    }//GEN-LAST:event_jBCopiarActionPerformed

    private void jBBuscarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBBuscarMouseClicked

    }//GEN-LAST:event_jBBuscarMouseClicked

    private void jBBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBBuscarActionPerformed
        buscarAcao();
    }//GEN-LAST:event_jBBuscarActionPerformed

    private void jTFBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTFBuscarActionPerformed
        buscarAcao();
    }//GEN-LAST:event_jTFBuscarActionPerformed

    private void jCalendar1PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jCalendar1PropertyChange
        Date selectedDate = jCalendar1.getDate();

        // Se for a primeira data a ser selecionada
        if (firstClickDate == null) {
            firstClickDate = selectedDate;
            jLbCalendar.setText("Selecione a segunda data.");
        } 
        // Se for a segunda data a ser selecionada
        else if (secondClickDate == null) {
            secondClickDate = selectedDate;

            // Verificar se as datas estão na ordem correta
            if (firstClickDate.after(secondClickDate)) {
                // Se a primeira data for maior que a segunda, invertê-las
                Date temp = firstClickDate;
                firstClickDate = secondClickDate;
                secondClickDate = temp;
            }

            // Exibir o intervalo selecionado
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            jLbCalendar.setText("Intervalo: " + sdf.format(firstClickDate) + " até " + sdf.format(secondClickDate));

            // Chamar o método para processar as datas
            processInterval(firstClickDate, secondClickDate);

            // Não chamar o reset aqui, para que as variáveis de data sejam preservadas
            // resetDates();  // Remover a chamada para resetDates()
        }
    }//GEN-LAST:event_jCalendar1PropertyChange

    private void processInterval(Date firstDate, Date secondDate) {
        if (firstDate == null || secondDate == null) {
            return;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    }

    // Método para resetar as variáveis, permitindo uma nova seleção
    private void resetDates() {
        // Resetando as variáveis para permitir a seleção de novas datas
        firstClickDate = null;
        secondClickDate = null;
        jLbCalendar.setText("Selecione a primeira data.");
    }
    
    private void jCBxIntervaloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCBxIntervaloActionPerformed
        if(jCBxIntervalo.isSelected()){
            jCBDatasUnicas.setEnabled(false);
            jTFDiaSemana.setText("");
            jTFDiaSemana.setEnabled(false);
            //jTProgramação.removeAll();
            DefaultTableModel model = (DefaultTableModel) jTProgramação.getModel();
            model.setRowCount(0);
            jPCalendar.setVisible(true);
            jPCalendar.setEnabled(true);
            jLbCalendar.setVisible(true);
            jLbCalendar.setEnabled(true);
        }else{
            jTFDiaSemana.setEnabled(true);
            jCBDatasUnicas.setEnabled(true);
            jPCalendar.setVisible(false);
            jPCalendar.setEnabled(false);
            jLbCalendar.setVisible(false);
            jLbCalendar.setEnabled(false);
        }
    }//GEN-LAST:event_jCBxIntervaloActionPerformed

    private void jBtEnviarRangeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtEnviarRangeActionPerformed
        jCBDatasUnicas.setEnabled(false);
        jPCalendar.setVisible(false);
        jPCalendar.setEnabled(false);
        jLbCalendar.setVisible(false);
        jLbCalendar.setEnabled(false);
        
        if (firstClickDate != null && secondClickDate != null) {
            if (firstClickDate.after(secondClickDate)) {
                Date temp = firstClickDate;
                firstClickDate = secondClickDate;
                secondClickDate = temp;
            }

            // Formatar as datas no padrão dd/MM/yyyy
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

            // Calendar para iterar pelas datas
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(firstClickDate);
            
            DefaultTableModel model = (DefaultTableModel) jTProgramação.getModel();
            model.setRowCount(0); // Limpa as linhas antes de adicionar novas
            // Loop para percorrer todas as datas entre firstClickDate e secondClickDate
            while (!calendar.getTime().after(secondClickDate)) {
                // Formatar a data atual e passá-la para o populateTable
                String dataFormatada = sdf.format(calendar.getTime());
                populateTable(dataFormatada,model);  // Passando a data formatada individualmente

                // Avançar para o próximo dia
                calendar.add(Calendar.DAY_OF_MONTH, 1);
            }
        } else {
            // Caso as datas não estejam selecionadas corretamente
            JOptionPane.showMessageDialog(this, "Selecione corretamente o intervalo de datas.");
        }
    }//GEN-LAST:event_jBtEnviarRangeActionPerformed

    private void jBtResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtResetActionPerformed
        resetDates();
    }//GEN-LAST:event_jBtResetActionPerformed

    private void jTFDiaSemanaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTFDiaSemanaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTFDiaSemanaActionPerformed

    
    public void buscarAcao(){
        List<String> stringBusca = new ArrayList<>();

        // Agrupar os blocos por data
        Map<String, StringBuilder> blocosPorData = new HashMap<>();

        // Iterar sobre os blocos para concatenar os dados por data e coletar as datas
        blocos.stream()
            .forEach(bloco -> {
                String dataProgramacao = bloco.getDataProgramacao();
                StringBuilder sb = blocosPorData.computeIfAbsent(dataProgramacao, k -> new StringBuilder());

                sb.append(("PROGRAMAÇÃO DO DIA " + jCBDatasUnicas.getSelectedItem().toString() + " - " +jTFDiaSemana.getText())).append(" "); 
                
                sb.append("Data Programação: ").append(dataProgramacao).append(" ");

                String carretao = "";
                if (!"N/A".equals(bloco.getCarretao()))    
                    carretao = " com " + bloco.getCarretao().toLowerCase();

                if (bloco.getFinalidade().equals("VISITA COMERCIAL")) {
                    sb.append("Finalidade: ").append(bloco.getFinalidade()).append(" ");
                } else {
                    String finalidadeRef = bloco.getFinalidade().toUpperCase().equals("OCULTAR") ? "" : " - \"" + bloco.getFinalidade().toUpperCase() + "\"";
                    sb.append("CLIENTE: ").append(bloco.getCliente() != null ? bloco.getCliente().getNome() : "N/A").append(finalidadeRef).append(" ");
                }

                sb.append("Equipe: ").append(bloco.getEquipe().stream().map(Usuario::getNomeDeGuerra).collect(Collectors.joining(", "))).append(" ");

                if (bloco.getProjeto() != null && !bloco.getProjeto().isEmpty() && !"N/A".equals(bloco.getProjeto())) {
                    sb.append("Projeto: ").append(bloco.getProjeto()).append(" ");
                }

                if (!bloco.getFinalidade().equals("VISITA COMERCIAL")) {
                    sb.append("Responsável do Trabalho: ").append(bloco.getResponsavelDoTrabalho() != null ? bloco.getResponsavelDoTrabalho().getNomeDeGuerra() : "N/A").append(" ");
                }

                
                if(bloco.getCarroExtra() != null){
                    if(bloco.getCarroExtra().equals("null")){
                        sb.append("Carro: ").append(bloco.getCarro() != null ? bloco.getCarro().getNome() : "N/A").append(carretao).append(" ");
                    }else{
                        sb.append("Carro: ").append(bloco.getCarro() != null ? bloco.getCarro().getNome() : "N/A").append(carretao).append(", Carro Extra: ").append(bloco.getCarroExtra()).append(" ");
                    }
                }else{
                    sb.append("Carro: ").append(bloco.getCarro() != null ? bloco.getCarro().getNome() : "N/A").append(carretao).append(" ");
                }
                sb.append("Data de Saída: ").append(bloco.getDataDeSaida()).append(" ");
                sb.append("Data de Retorno: ").append(bloco.getDataDeRetorno()).append(" ");
                sb.append("Horário de Saída: ").append(bloco.getHorarioDeSaida()).append("H ");

                if (!"Horário de Trabalho: H AS Hs Hs AS Hs".equals("Horário de Trabalho: " + bloco.getHorarioDeTrabalhoInicio() + "H AS " + bloco.getHorarioDeTrabalhoFimMeioDia() + "Hs " + bloco.getHorarioDeTrabalhoInicioMeioDia() + "Hs AS " + bloco.getHorarioDeTrabalhoFim() + "Hs")) {
                    sb.append("Horário de Trabalho: ").append(bloco.getHorarioDeTrabalhoInicio()).append("H AS ")
                      .append(bloco.getHorarioDeTrabalhoFimMeioDia()).append("Hs ")
                      .append(bloco.getHorarioDeTrabalhoInicioMeioDia()).append("Hs AS ")
                      .append(bloco.getHorarioDeTrabalhoFim()).append("Hs ");
                }

                if (bloco.getAlmoco() != null && !bloco.getAlmoco().equals("N/A")) {
                    sb.append("Almoço: ").append(bloco.getAlmoco()).append(" ");
                }

                if (bloco.getJanta() != null && !bloco.getJanta().equals("N/A")) {
                    sb.append("Janta: ").append(bloco.getJanta()).append(" ");
                }

                if (bloco.getHospedagem() != null) {
                    sb.append("Hospedagem: ").append(bloco.getHospedagem().getNomeComCidadeEEstado()).append(" - ").append(bloco.getHospedagem().getEndereco()).append(" ");
                }

                if (bloco.getObservacoes() != null && !bloco.getObservacoes().equals("N/A")) {
                    sb.append("OBS: ").append(bloco.getObservacoes().toUpperCase()).append(" ");
                }
            });

        // Agora, para cada data em blocosPorData, vamos adicionar as informações de `demaisInfos`
        blocosPorData.forEach((data, sb) -> {
            demaisInfos.stream()
                .filter(info -> data.equals(info.getDataProgramacao()))  // Verifica se a data bate com a de `demaisInfos`
                .findFirst()
                .ifPresent(info -> {
                    sb.append("Data Programação: ").append(info.getDataProgramacao()).append(" ");

                    if (!Optional.ofNullable(info.getMecanicosInternos()).orElse(new ArrayList<>()).isEmpty()) {
                        sb.append("MECÂNICOS INTERNOS: ").append(info.getMecanicosInternos().stream().map(Usuario::getNomeDeGuerra).collect(Collectors.joining(", "))).append(" ");
                    }

                    if (!Optional.ofNullable(info.getEletricistasInternos()).orElse(new ArrayList<>()).isEmpty()) {
                        sb.append("ELETRICISTAS INTERNOS: ").append(info.getEletricistasInternos().stream().map(Usuario::getNomeDeGuerra).collect(Collectors.joining(", "))).append(" ");
                    }

                    if (!Optional.ofNullable(info.getFolgas()).orElse(new ArrayList<>()).isEmpty()) {
                        sb.append("FOLGAS: ").append(info.getFolgas().stream().map(Usuario::getNomeDeGuerra).collect(Collectors.joining(", "))).append(" ");
                    }

                    if (!Optional.ofNullable(info.getFerias()).orElse(new ArrayList<>()).isEmpty()) {
                        sb.append("FÉRIAS: ").append(info.getFerias().stream().map(Usuario::getNomeDeGuerra).collect(Collectors.joining(", "))).append(" ");
                    }
                });
            stringBusca.add(sb.toString().trim());
        });

        Set<String> datasDeBusca = new HashSet<>();

        for (String stringb : stringBusca) {
            if (stringb.contains(jTFBuscar.getText().toUpperCase())) {
                blocos.stream()
                    .filter(bloco -> stringb.contains("Data Programação: " + bloco.getDataProgramacao()))
                    .forEach(bloco -> datasDeBusca.add(bloco.getDataProgramacao()));
                demaisInfos.stream()
                    .filter(info -> stringb.contains("Data Programação: " + info.getDataProgramacao()))
                    .forEach(info -> datasDeBusca.add(info.getDataProgramacao()));
            }
        }

        if(datasDeBusca.isEmpty()){
            JOptionPane.showMessageDialog(null, "Nada encontrado. Listagem não atualizada!");
            return;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        Set<LocalDate> localDates = datasDeBusca.stream()
            .map(date -> LocalDate.parse(date, formatter)) // Converte cada String para LocalDate
            .collect(Collectors.toCollection(HashSet::new));

        addDatas(localDates);
        configurarModeloTabela();
    }
    
    public void populateTable(String dataEscolhida, DefaultTableModel model) {
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate dataDate = LocalDate.parse(dataEscolhida, formato);
        
        boolean existeProgramacao = false;
        for (Bloco bloco : blocos) {
            if(bloco.getDataProgramacao().equals(dataEscolhida)){
                existeProgramacao = true;
            }
        }
        if(existeProgramacao){
            model.addRow(new Object[] { "" });
            model.addRow(new Object[] { "PROGRAMAÇÃO DO DIA " + dataEscolhida + " - " + dataDate.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.getDefault()).toUpperCase() });
            model.addRow(new Object[] { "" });
        }
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
                if(bloco.getCarroExtra() != null){
                    if(bloco.getCarroExtra().equals("null")){
                        model.addRow(new Object[] { "Carro: " + (bloco.getCarro() != null ? bloco.getCarro().getNome() : "N/A") + carretao });
                    }else{
                        model.addRow(new Object[] { "Carro: " + (bloco.getCarro() != null ? bloco.getCarro().getNome() : "N/A") + carretao + ", Carro Extra: " + bloco.getCarroExtra()});
                    }
                }else{
                    model.addRow(new Object[] { "Carro: " + (bloco.getCarro() != null ? bloco.getCarro().getNome() : "N/A") + carretao });
                }
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
                    model.addRow(new Object[] { "Hospedagem: " + (bloco.getHospedagem().getNomeComCidadeEEstado()) + " - " + (bloco.getHospedagem().getEndereco()) });
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
                    model.addRow(new Object[] { "MECÂNICOS INTERNOS: " });
                    model.addRow(new Object[] { Optional.ofNullable(info.getMecanicosInternos()).orElse(new ArrayList<>()).stream().map(Usuario::getNomeDeGuerra).collect(Collectors.joining(", ")) + "\n" });
                }
                if(!Optional.ofNullable(info.getEletricistasInternos()).orElse(new ArrayList<>()).stream().map(Usuario::getNomeDeGuerra).collect(Collectors.joining(", ")).equals("")){
                    model.addRow(new Object[] { "ELETRICISTAS INTERNOS: " });
                    model.addRow(new Object[] { Optional.ofNullable(info.getEletricistasInternos()).orElse(new ArrayList<>()).stream().map(Usuario::getNomeDeGuerra).collect(Collectors.joining(", "))  + "\n" });
                }
                if(!Optional.ofNullable(info.getFolgas()).orElse(new ArrayList<>()).stream().map(Usuario::getNomeDeGuerra).collect(Collectors.joining(", ")).equals("")){
                    model.addRow(new Object[] { "FOLGAS: " });
                    model.addRow(new Object[] { Optional.ofNullable(info.getFolgas()).orElse(new ArrayList<>()).stream().map(Usuario::getNomeDeGuerra).collect(Collectors.joining(", "))  + "\n" });
                }
                if(!Optional.ofNullable(info.getFerias()).orElse(new ArrayList<>()).stream().map(Usuario::getNomeDeGuerra).collect(Collectors.joining(", ")).equals("")){
                    model.addRow(new Object[] { "FÉRIAS: " });
                    model.addRow(new Object[] { Optional.ofNullable(info.getFerias()).orElse(new ArrayList<>()).stream().map(Usuario::getNomeDeGuerra).collect(Collectors.joining(", "))  + "\n" });
                }
                model.addRow(new Object[] { "" });
                
            });
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton JBXlsx;
    private javax.swing.JButton jBBuscar;
    private javax.swing.JButton jBCopiar;
    private javax.swing.JButton jBPDF;
    private javax.swing.JButton jBtEnviarRange;
    private javax.swing.JButton jBtReset;
    private javax.swing.JPanel jCB;
    private javax.swing.JComboBox<String> jCBDatasUnicas;
    private javax.swing.JCheckBox jCBxIntervalo;
    private com.toedter.calendar.JCalendar jCalendar1;
    private javax.swing.JLabel jLFundo;
    private javax.swing.JLabel jLProg;
    private javax.swing.JLabel jLProgramDia;
    private javax.swing.JLabel jLbCalendar;
    private javax.swing.JPanel jPCalendar;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTFBuscar;
    private javax.swing.JTextField jTFDiaSemana;
    private javax.swing.JTable jTProgramação;
    // End of variables declaration//GEN-END:variables
}
