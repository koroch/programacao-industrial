/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view;
import java.awt.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import programacao.model.Historico;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import programacao.model.Carro;

/**
 *
 * @author Koroch
 */
public class HistoricoManutView extends javax.swing.JFrame {
    private List<Carro> carrosManut = new ArrayList<>();
    private List<Historico> historicos = new ArrayList<>();
    private List<String> linhasArquivoHist = new ArrayList<>();
    
    /**
     * Creates new form ManutencaoView
     * @param carros
     */
    public HistoricoManutView(List<Carro> carros) {
        initComponents();
        preencheCB(carros);
        this.carrosManut = carros;
        try (BufferedReader br = new BufferedReader(new FileReader("historicos.txt"))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                linhasArquivoHist.add(linha);
            }

            linhasArquivoHist.forEach(elemento -> {
                String[] dados = elemento.split("\\|");
                int qtdArray = dados.length;
                historicos.add(new Historico(
                        dados[0].trim(),
                        Integer.parseInt(dados[1].trim()),
                        (qtdArray >= 3 ? dados[2].trim() : null),
                        (qtdArray >= 4 ? dados[3].trim() : null)
                ));
            });
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Erro no arquivo historicos.txt, talvez ele ainda esteja vazio!");
        }
        preencherTabela(carros);
        
        jTbManut.getSelectionModel().addListSelectionListener(event -> {
            // Verifica se a mudança é realmente uma nova seleção (e não ajuste interno)
            if (!event.getValueIsAdjusting()) {
                int linhaSelecionada = jTbManut.getSelectedRow(); // Obtém a linha selecionada
                if (linhaSelecionada != -1) { // Verifica se há uma linha selecionada
                    String carro = jTbManut.getValueAt(linhaSelecionada, 0).toString();
                    String dataKm = jTbManut.getValueAt(linhaSelecionada, 1).toString();
                    String quilometragem = jTbManut.getValueAt(linhaSelecionada, 2).toString();
                    String observacao = jTbManut.getValueAt(linhaSelecionada, 3).toString();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

                    jCBCarro.setSelectedItem(carro);
                    try {
                        jDCDataKM.setDate(dateFormat.parse(dataKm));
                    } catch (ParseException ex) {
                        Logger.getLogger(HistoricoManutView.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    System.out.println(jDCDataKM.getDate());
                    jFTFKMAtt.setText(quilometragem);
                    jTFObs.setText(observacao);
                }
            }
        });
    }
    
    private void preencheCB(List<Carro> carros){
        jCBCarro.removeAllItems();
        carros.stream()
          .map(Carro::getNome)
          .sorted()
          .forEach(jCBCarro::addItem);
    }
    
    private void preencherTabela(List<Carro> carros) {
        // Criar o modelo da tabela
        DefaultTableModel modelo = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        // Definir as colunas
        modelo.setColumnIdentifiers(new String[]{"Carro", "Data da KM", "Quilometragem", "Observação"});
        jTbManut.setModel(modelo);
        jTbManut.setFont(jTbManut.getFont().deriveFont(Font.BOLD));
        jTbManut.setRowHeight(25); // Altura das linhas para melhor visualização
        
        List<Object[]> dadosTabela = new ArrayList<>();
        for (Historico historico : historicos) {
            for (Carro carro : carros) {
                if (carro.getPlaca().equals(historico.getPlacaCarro())) {
                    String obs = (historico.getObservacao() != null) && !historico.getObservacao().equals("null") ? historico.getObservacao() : "";
                    dadosTabela.add(new Object[]{
                        carro.getNome(),
                        historico.getDataAtt(),
                        historico.getKmAtt(),
                        obs
                    });
                    break; // Encontrou o carro correspondente, pode sair do loop
                }
            }
        }
        
        dadosTabela.sort((a, b) -> {
            String nomeCarroA = (String) a[0];
            String nomeCarroB = (String) b[0];
            int nomeComparison = nomeCarroA.compareToIgnoreCase(nomeCarroB);

            if (nomeComparison != 0) {
                return nomeComparison; // Ordenação por nome do carro
            }

            // Subordenação por Data da KM
            try {
                java.util.Date dataA = new java.text.SimpleDateFormat("dd/MM/yyyy").parse((String) a[1]);
                java.util.Date dataB = new java.text.SimpleDateFormat("dd/MM/yyyy").parse((String) b[1]);
                return dataA.compareTo(dataB);
            } catch (java.text.ParseException e) {
                e.printStackTrace();
                return 0; // Se houver erro ao converter, considere iguais
            }
        });

        // Adicionar os dados ordenados ao modelo da tabela
        for (Object[] linha : dadosTabela) {
            modelo.addRow(linha);
        }
        
        TableColumnModel columnModel = jTbManut.getColumnModel();
        columnModel.getColumn(1).setPreferredWidth(30);
        columnModel.getColumn(2).setPreferredWidth(30);
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jCB = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTbManut = new javax.swing.JTable();
        jLbKMAtt = new javax.swing.JLabel();
        jLbDataKM = new javax.swing.JLabel();
        jLbObs = new javax.swing.JLabel();
        jTFObs = new javax.swing.JTextField();
        JLbCarro = new javax.swing.JLabel();
        jCBCarro = new javax.swing.JComboBox<>();
        jDCDataKM = new com.toedter.calendar.JDateChooser();
        jFTFKMAtt = new javax.swing.JFormattedTextField();
        jBOk = new javax.swing.JButton();
        jBDelete = new javax.swing.JButton();
        jBLimpaSelec = new javax.swing.JButton();
        jLFundo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        getContentPane().setLayout(null);

        jCB.setBackground(new java.awt.Color(7, 30, 74));
        jCB.setToolTipText("");
        jCB.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jCB.setFocusable(false);
        jCB.setName(""); // NOI18N
        jCB.setLayout(null);

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Histórico das Trocas");
        jCB.add(jLabel2);
        jLabel2.setBounds(400, 20, 300, 20);

        jTbManut.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Carro", "Data de Cadastro", "Data Att Automática", "KM Atualizado", "KM Última Troca", "KM Próxima Troca", "% de Troca"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTbManut.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jTbManut.setFocusable(false);
        jTbManut.setOpaque(false);
        jTbManut.setRequestFocusEnabled(false);
        jTbManut.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jTbManut.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jTbManut.setShowGrid(false);
        jTbManut.setUpdateSelectionOnSort(false);
        jTbManut.setVerifyInputWhenFocusTarget(false);
        jScrollPane1.setViewportView(jTbManut);

        jCB.add(jScrollPane1);
        jScrollPane1.setBounds(40, 60, 1000, 400);

        jLbKMAtt.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        jLbKMAtt.setForeground(new java.awt.Color(255, 255, 255));
        jLbKMAtt.setText("KM Atualizada:");
        jLbKMAtt.setToolTipText("");
        jCB.add(jLbKMAtt);
        jLbKMAtt.setBounds(250, 480, 80, 14);

        jLbDataKM.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        jLbDataKM.setForeground(new java.awt.Color(255, 255, 255));
        jLbDataKM.setText("Data da KM:");
        jLbDataKM.setToolTipText("");
        jCB.add(jLbDataKM);
        jLbDataKM.setBounds(440, 480, 90, 14);

        jLbObs.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        jLbObs.setForeground(new java.awt.Color(255, 255, 255));
        jLbObs.setText("Observação:");
        jLbObs.setToolTipText("");
        jCB.add(jLbObs);
        jLbObs.setBounds(660, 480, 90, 14);

        jTFObs.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTFObsMouseClicked(evt);
            }
        });
        jCB.add(jTFObs);
        jTFObs.setBounds(730, 470, 300, 30);

        JLbCarro.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        JLbCarro.setForeground(new java.awt.Color(255, 255, 255));
        JLbCarro.setText("Carro:");
        JLbCarro.setToolTipText("");
        jCB.add(JLbCarro);
        JLbCarro.setBounds(50, 480, 40, 14);

        jCBCarro.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jCBCarroMouseClicked(evt);
            }
        });
        jCB.add(jCBCarro);
        jCBCarro.setBounds(90, 470, 150, 30);

        jDCDataKM.setToolTipText("Dia/Mês/Ano");
        jDCDataKM.setDate(new java.util.Date(new java.util.Date().getTime()));
        jDCDataKM.setDateFormatString("dd/MM/yyyy");
        jDCDataKM.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jDCDataKMFocusGained(evt);
            }
        });
        jDCDataKM.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jDCDataKMMouseClicked(evt);
            }
        });
        jDCDataKM.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jDCDataKMPropertyChange(evt);
            }
        });
        jDCDataKM.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jDCDataKMKeyPressed(evt);
            }
        });
        jCB.add(jDCDataKM);
        jDCDataKM.setBounds(510, 470, 140, 30);

        jFTFKMAtt.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("###############"))));
        jFTFKMAtt.setToolTipText("");
        jFTFKMAtt.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jFTFKMAttMouseClicked(evt);
            }
        });
        jCB.add(jFTFKMAtt);
        jFTFKMAtt.setBounds(330, 470, 100, 30);

        jBOk.setText("OK");
        jBOk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBOkActionPerformed(evt);
            }
        });
        jCB.add(jBOk);
        jBOk.setBounds(860, 510, 80, 30);

        jBDelete.setBackground(new java.awt.Color(255, 102, 102));
        jBDelete.setText("Deletar");
        jBDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBDeleteActionPerformed(evt);
            }
        });
        jCB.add(jBDelete);
        jBDelete.setBounds(950, 510, 80, 30);

        jBLimpaSelec.setBackground(new java.awt.Color(204, 204, 204));
        jBLimpaSelec.setText("Limpar Seleção");
        jBLimpaSelec.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBLimpaSelecActionPerformed(evt);
            }
        });
        jCB.add(jBLimpaSelec);
        jBLimpaSelec.setBounds(730, 510, 120, 30);

        getContentPane().add(jCB);
        jCB.setBounds(0, 50, 1120, 550);

        jLFundo.setBackground(new java.awt.Color(51, 51, 51));
        jLFundo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/tela_de_fundo.jpeg"))); // NOI18N
        jLFundo.setVerifyInputWhenFocusTarget(false);
        getContentPane().add(jLFundo);
        jLFundo.setBounds(-140, -10, 1250, 730);

        setSize(new java.awt.Dimension(1100, 676));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jDCDataKMPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jDCDataKMPropertyChange
        
    }//GEN-LAST:event_jDCDataKMPropertyChange

    private void jBOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBOkActionPerformed
        Carro car = null;
        boolean exists = false;
        for (Carro x: carrosManut) {
            if(x.getNome().toUpperCase().equals(jCBCarro.getSelectedItem().toString())){
                car=x;
                break;
            }
        }
        if(car!=null){
            for(Historico hist: historicos){
                if(car.getPlaca().toUpperCase().equals(hist.getPlacaCarro()) && Integer.parseInt(jFTFKMAtt.getText().trim()) == hist.getKmAtt()){
                    exists = true;
                    hist.setDataAtt(jDCDataKM.getDate()==null?"":new SimpleDateFormat("dd/MM/yyyy").format(jDCDataKM.getDate()));
                    hist.setKmAtt(Integer.parseInt(jFTFKMAtt.getText().trim()));
                    hist.setObservacao(jTFObs.getText().trim());
                    break;
                }
            } 
            
            if(!exists){
                for (Carro x: carrosManut) {
                    if(x.getNome().toUpperCase().equals(jCBCarro.getSelectedItem().toString())){
                        Historico hist = new Historico(x.getPlaca(), Integer.parseInt(jFTFKMAtt.getText()), jDCDataKM.getDate()==null?"":new SimpleDateFormat("dd/MM/yyyy").format(jDCDataKM.getDate()), jTFObs.getText());
                        historicos.add(hist);
                        limparCampos();
                        break;
                    }
                }
            }
            
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("historicos.txt"))) {
                for (Historico hist: historicos) {
                    writer.write(hist.toString());
                    writer.newLine();
                }
                JOptionPane.showMessageDialog(null, "Histórico atualizado com sucesso!");
                limparCampos();
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Erro ao salvar aquivo!");
            }
        }  
    }//GEN-LAST:event_jBOkActionPerformed

    private void jBDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBDeleteActionPerformed
        List<Historico> aux = new ArrayList<>(historicos);
        Carro car = null;
        boolean exists = false;
        for (Carro x: carrosManut) {
            if(x.getNome().toUpperCase().equals(jCBCarro.getSelectedItem().toString())){
                car=x;
                break;
            }
        }
        if(car!=null){
            System.out.println("Tenho carro");
            if(jFTFKMAtt.getText().trim().equals("")){
                JOptionPane.showMessageDialog(null, "Selecione o carro e informe a quilometragem para fazer a remoção do histórico correto!");
                return;
            }
            for (Historico x: aux) {
                if(x.getPlacaCarro().toUpperCase().equals(car.getPlaca()) && x.getKmAtt() == Integer.parseInt(jFTFKMAtt.getText())){
                    historicos.remove(x);
                    exists=true;
                    break;
                }
            }

            if(!exists){
                JOptionPane.showMessageDialog(null, "Não foi encontrado o histórico para remover!\nSelecione o carro e informe a quilometragem para fazer a remoção do histórico correto!");
                return;
            }

            if(historicos.isEmpty()){
                try (BufferedWriter writer = new BufferedWriter(new FileWriter("historicos.txt"))) {
                    writer.write("");
                    limparCampos();
                    return;
                } catch (IOException e ) {
                    JOptionPane.showMessageDialog(null, "Erro ao salvar aquivo!");
                }
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter("historicos.txt"))) {
                for (Historico hist: historicos) {
                    writer.write(hist.toString());
                    writer.newLine();
                }

                JOptionPane.showMessageDialog(null, "Histórico deletado com sucesso!", "Deleção", JOptionPane.WARNING_MESSAGE);
                limparCampos();
            } catch (IOException e ) {
                JOptionPane.showMessageDialog(null, "Erro ao salvar aquivo!");
            }
        }
    }//GEN-LAST:event_jBDeleteActionPerformed

    private void removeSelecao(){
        if (jTbManut.getSelectedRow() == -1) { // Nenhuma linha selecionada
            return;
        }
        jDCDataKM.setDate(null);
        jTbManut.clearSelection();
    }
    
    private void jCBCarroMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jCBCarroMouseClicked

    }//GEN-LAST:event_jCBCarroMouseClicked

    private void jFTFKMAttMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jFTFKMAttMouseClicked

    }//GEN-LAST:event_jFTFKMAttMouseClicked

    private void jDCDataKMMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jDCDataKMMouseClicked

    }//GEN-LAST:event_jDCDataKMMouseClicked

    private void jTFObsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTFObsMouseClicked

    }//GEN-LAST:event_jTFObsMouseClicked

    private void jDCDataKMFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jDCDataKMFocusGained
        
    }//GEN-LAST:event_jDCDataKMFocusGained

    private void jDCDataKMKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jDCDataKMKeyPressed

    }//GEN-LAST:event_jDCDataKMKeyPressed

    private void jBLimpaSelecActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBLimpaSelecActionPerformed
        removeSelecao();
    }//GEN-LAST:event_jBLimpaSelecActionPerformed

    private void limparCampos(){
        jTFObs.setText("");
        jFTFKMAtt.setText("");
        jDCDataKM.setDate(new java.util.Date(new java.util.Date().getTime()));
        jCBCarro.setSelectedIndex(0);
    }
   

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel JLbCarro;
    private javax.swing.JButton jBDelete;
    private javax.swing.JButton jBLimpaSelec;
    private javax.swing.JButton jBOk;
    private javax.swing.JPanel jCB;
    private javax.swing.JComboBox<String> jCBCarro;
    private com.toedter.calendar.JDateChooser jDCDataKM;
    private javax.swing.JFormattedTextField jFTFKMAtt;
    private javax.swing.JLabel jLFundo;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLbDataKM;
    private javax.swing.JLabel jLbKMAtt;
    private javax.swing.JLabel jLbObs;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTFObs;
    private javax.swing.JTable jTbManut;
    // End of variables declaration//GEN-END:variables
}
