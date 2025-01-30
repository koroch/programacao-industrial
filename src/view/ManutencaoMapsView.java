/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import programacao.model.Bloco;
import programacao.model.Carro;
import programacao.model.HistoricoKms;

/**
 *
 * @author Koroch
 */
public class ManutencaoMapsView extends javax.swing.JFrame {
    private List<HistoricoKms> historicoKms = new ArrayList<>();
    private List<String> linhasArquivoHistKms = new ArrayList<>();
    private static int qtdAlertasVermelho = 0;
    private static int qtdAlertasLaranja = 0;
    private List<Carro> carros;
    private List<Bloco> blocos;
    
    /**
     * Creates new form ManutencaoMapsView
     * @param carros
     * @param blocos
     */
    public ManutencaoMapsView(List<Carro> carros, List<Bloco> blocos) {
        initComponents();
        this.carros = carros;
        this.blocos= blocos;
        //executa();
    }
    
    private void calculaQtdAlertas(List<Carro> carros) {
        qtdAlertasVermelho = 0;
        qtdAlertasLaranja = 0;
        for (Carro carro : carros) {
            int kmUltimaTroca = carro.getKm_ultima_troca();
            int kmAtualizacao = carro.getKm_atual_maps();
            int kmProximaTroca = carro.getKm_ultima_troca()+(1000*carro.getMultiploProximaTroca());

            // Calcula o percentual considerando KM Última Troca como 0% e KM Próxima Troca como 100%
            double percentual = ((double) (kmAtualizacao - kmUltimaTroca) / (kmProximaTroca - kmUltimaTroca)) * 100;

            if (percentual >= 80 && percentual < 100) {
                incrementarAletasLaranja();
            } else if (percentual >= 100) {
                incrementarAletasVermelho();
            }
        }
    }

    public int getQtdAletasVermelho(List<Carro> carros) {
        calculaQtdAlertas(carros);
        return qtdAlertasVermelho;
    }
    
    public int getQtdAletasLaranja(List<Carro> carros) {
        calculaQtdAlertas(carros);
        return qtdAlertasLaranja;
    }

    // Método para incrementar qtdAletasVermelho
    private void incrementarAletasVermelho() {
        qtdAlertasVermelho++;
    }
    
    private void incrementarAletasLaranja() {
        qtdAlertasLaranja++;
    }

    private void executa() {
        new Thread(() -> {
            try {
                jLProgresso.setVisible(true);
                
                ExecutorService executor = Executors.newFixedThreadPool(9); // 9 threads simultâneas
                AtomicInteger progresso = new AtomicInteger(0); // Contador para progresso

                // Data de hoje
                String dataHoje = new SimpleDateFormat("dd/MM/yyyy").format(new Date());

                // Submeter tarefas ao ExecutorService
                for (Carro carro : carros) {
                    executor.submit(() -> {
                        try {
                            String dataInicio = incrementaUmDia(carro.getData_km_atual_automatica_maps());
                            String primeiraData = carro.getData_km_atual_automatica_maps();

                            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                            Date dataInicioDate = sdf.parse(dataInicio);
                            Date dataHojeDate = sdf.parse(dataHoje);
                            
                            if (dataInicioDate.compareTo(dataHojeDate) <= 0) {
                                
                                blocos.forEach(bloco -> {
                                    System.out.println(bloco.getDataProgramacao());
                                });
                                
                            }

                            int concluido = progresso.incrementAndGet();
                            jLProgresso.setText("Buscando dados atualizados... (" + concluido + "/" + carros.size() + ")");
                            jLProgresso.setVisible(true);
                        } catch (Exception e) {
                            JOptionPane.showMessageDialog(null, "Erro ao processar dados do carro: " + carro.getNome());
                        }
                    });
                }

                // Finalizar executor após todas as tarefas
                executor.shutdown();
                while (!executor.isTerminated()) {
                    Thread.sleep(500); // Aguarda até todas as threads terminarem
                    preencherTabela(carros);
                }

                jLProgresso.setVisible(false);
            } catch (InterruptedException e) {
                JOptionPane.showMessageDialog(null, "Erro em algumas das threads que consultam no maps");
            }
        }).start(); // Inicia a execução em uma thread separada
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
        modelo.setColumnIdentifiers(new String[]{"Carro", "Data de Cadastro", "Data Att Automática", "KM Atualizado", "KM Última Troca", "KM Próxima Troca", "% de Troca"});

        // Adicionar os dados dos carros como linhas
        for (Carro carro : carros) {
            modelo.addRow(new Object[]{
                carro.getNome(),
                carro.getData_km_atual(),
                carro.getData_km_atual_automatica_maps(),
                carro.getKm_atual(),
                carro.getKm_ultima_troca(),
                (carro.getKm_ultima_troca()+(1000*carro.getMultiploProximaTroca()))
            });
        }

        // Atribuir o modelo à JTable
        jTbManut.setModel(modelo);
        
        jTbManut.setFont(jTbManut.getFont().deriveFont(Font.BOLD));
        jTbManut.setRowHeight(25); // Altura das linhas para melhor visualização

        // Renderer personalizado
        jTbManut.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                // Configuração de fonte padrão
                c.setForeground(Color.BLACK);
                c.setBackground(Color.WHITE);

                // Lógica para definir a cor da linha inteira
                try {
                    int kmUltimaTroca = Integer.parseInt(table.getValueAt(row, 4).toString());
                    int kmAtualizacao = Integer.parseInt(table.getValueAt(row, 3).toString());
                    int kmProximaTroca = Integer.parseInt(table.getValueAt(row, 5).toString());

                    // Calcula o percentual considerando KM Última Troca como 0% e KM Próxima Troca como 100%
                    double percentual = ((double) (kmAtualizacao - kmUltimaTroca) / (kmProximaTroca - kmUltimaTroca)) * 100;

                    // Grava o percentual na coluna 6 (Percentual)
                    table.setValueAt(String.format("%.2f%%", percentual), row, 6);

                    // Define a cor da linha com base no percentual
                    if (percentual < 80) {
                        c.setBackground(new Color(0, 100, 0)); // Verde escuro
                        c.setForeground(Color.WHITE); // Texto preto
                    } else if (percentual >= 80 && percentual < 100) {
                        c.setBackground(new Color(245, 109, 5)); // Laranja
                        c.setForeground(Color.WHITE); // Texto branco
                        incrementarAletasLaranja();
                    } else if (percentual >= 100) {
                        c.setBackground(Color.RED); // Vermelho
                        c.setForeground(Color.WHITE); // Texto branco
                        incrementarAletasVermelho();
                    }
                } catch (NumberFormatException | NullPointerException e) {
                    // Ignora se o valor não puder ser convertido para inteiro ou for nulo
                }

                // Mantém a seleção com prioridade
                if (isSelected) {
                    c.setBackground(table.getSelectionBackground());
                    c.setForeground(table.getSelectionForeground());
                }

                return c;
            }
        });
    }

    private String incrementaUmDia(String data) throws Exception {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date parsedDate = dateFormat.parse(data);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(parsedDate);
        calendar.add(Calendar.DAY_OF_MONTH, 1); // Adiciona 1 dia
        return dateFormat.format(calendar.getTime());
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jCB = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLProgresso = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTbManut = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
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
        jLabel2.setText("Controle de Trocas de Óleo e Manutenção");
        jCB.add(jLabel2);
        jLabel2.setBounds(400, 20, 300, 20);

        jLProgresso.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLProgresso.setForeground(new java.awt.Color(0, 0, 102));
        jLProgresso.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLProgresso.setText("Ops, aguarde! Em breve...");
        jCB.add(jLProgresso);
        jLProgresso.setBounds(310, 120, 460, 20);

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
        jTbManut.setFocusable(false);
        jTbManut.setOpaque(false);
        jTbManut.setRequestFocusEnabled(false);
        jScrollPane1.setViewportView(jTbManut);

        jCB.add(jScrollPane1);
        jScrollPane1.setBounds(40, 60, 1000, 270);

        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton1.setText("Histórico");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jCB.add(jButton1);
        jButton1.setBounds(40, 360, 110, 40);

        getContentPane().add(jCB);
        jCB.setBounds(0, 110, 1120, 430);

        jLFundo.setBackground(new java.awt.Color(51, 51, 51));
        jLFundo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/tela_de_fundo.jpeg"))); // NOI18N
        jLFundo.setVerifyInputWhenFocusTarget(false);
        getContentPane().add(jLFundo);
        jLFundo.setBounds(-140, -10, 1250, 730);

        setSize(new java.awt.Dimension(1100, 670));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        HistoricoManutView historicoManut = new HistoricoManutView(carros);  
        historicoManut.setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

   

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JPanel jCB;
    private javax.swing.JLabel jLFundo;
    private javax.swing.JLabel jLProgresso;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTbManut;
    // End of variables declaration//GEN-END:variables
}
