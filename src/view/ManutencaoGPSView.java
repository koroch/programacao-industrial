/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view;

import com.google.gson.JsonObject;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import org.apache.http.client.CookieStore;
import org.apache.http.impl.client.BasicCookieStore;
import programacao.model.Carro;
import utils.FetchDataCarRastrek;

/**
 *
 * @author Koroch
 */
public class ManutencaoGPSView extends javax.swing.JFrame {
    private static int qtdAletasVermelho = 0;
    private static int qtdAletasLaranja = 0;
    private static CookieStore cookieStore = new BasicCookieStore();
    private List<Carro> carrosMenu;
    DefaultTableModel modelo = new DefaultTableModel() {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    /**
     * Creates new form ManutencaoView
     * @param carros
     * @param cookieStore
     */
    public ManutencaoGPSView(List<Carro> carros, CookieStore cookieStore) {
        initComponents();
        jLProgresso.setVisible(false);
        ManutencaoGPSView.cookieStore = cookieStore;
        this.carrosMenu = carros;
        preencherTabela(carros);
    }
    
    private void calculaQtdAletas(List<Carro> carros) {
        qtdAletasVermelho = 0;
        qtdAletasLaranja = 0;
        for (Carro carro : carros) {
            int kmUltimaTroca = carro.getKm_ultima_troca();
            int kmAtualizacao = carro.getKm_atual();
            int kmProximaTroca = carro.getKm_proxima_troca();

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
        calculaQtdAletas(carros);
        return qtdAletasVermelho;
    }
    
    public int getQtdAletasLaranja(List<Carro> carros) {
        calculaQtdAletas(carros);
        return qtdAletasLaranja;
    }

    // Método para incrementar qtdAletasVermelho
    private void incrementarAletasVermelho() {
        qtdAletasVermelho++;
    }
    
    private void incrementarAletasLaranja() {
        qtdAletasLaranja++;
    }

    public void executa() {
        jLProgresso.setVisible(true);
        new Thread(() -> {
            try {
                // Obter a lista atualizada de carros
                List<Carro> carros = new CadastroCarro(carrosMenu).getListaAtualizadaCarros();
                
                // Configurar ExecutorService
                ExecutorService executor = Executors.newFixedThreadPool(9); // 9 threads simultâneas
                AtomicInteger progresso = new AtomicInteger(0); // Contador para progresso

                // Data de hoje
                String dataHoje = new SimpleDateFormat("dd/MM/yyyy").format(new Date());

                // Submeter tarefas ao ExecutorService
                for (Carro carro : carros) {
                    executor.submit(() -> {
                        try {
                            //System.out.println("Início do processamento para carro: " + carro.getNome());
                            String dataInicio = incrementaUmDia(carro.getData_km_atual_automatica());
                            String primeiraData = carro.getData_km_atual_automatica();

                            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                            Date dataInicioDate = sdf.parse(dataInicio);
                            Date dataHojeDate = sdf.parse(dataHoje);
                            
                            if (dataInicioDate.compareTo(dataHojeDate) <= 0) {
                                FetchDataCarRastrek fetchDataCar = new FetchDataCarRastrek(ManutencaoGPSView.cookieStore);
                                JsonObject jsonObject = fetchDataCar.fetchData(primeiraData, dataHoje, carro.getImei_rastreador(), carros);
                            
                                JsonObject data = jsonObject.getAsJsonObject("data");
                    
                                // Verificar e acessar "emei"
                                String imei = data.has("imei") ? data.get("imei").getAsString() : "Imei não disponível";

                                // Verificar e acessar "license_plate"
                                String licensePlate = data.has("license_plate") ? data.get("license_plate").getAsString() : "Placa não disponível";

                                // Verificar "html" e "header"
                                if (data.has("html")) {
                                    JsonObject html = data.getAsJsonObject("html");

                                    if (html.has("header")) {
                                        JsonObject header = html.getAsJsonObject("header");

                                        // Verificar e acessar "trip_distance"
                                        String tripDistance = header.has("trip_distance") ? header.get("trip_distance").getAsString() : "Distância não disponível";
                                        int kmAtualizar = Integer.parseInt(tripDistance.split(" ")[0]);
                                        // Exibir valores
                                        atualiza(imei, carros, kmAtualizar, dataHoje);
                                        System.out.println("IMEI: " + imei);
                                        System.out.println("License Plate: " + licensePlate);
                                        System.out.println("Trip Distance: " + tripDistance);
                                    } else {
                                        System.err.println("O objeto 'header' não está presente.");
                                    }
                                } else {
                                    System.err.println("O objeto 'html' não está presente.");
                                }
                                
                            } 
                            
                         
                            System.out.println("Fim do processamento para carro: " + carro.getNome());

                            // Atualizar progresso
                            int concluido = progresso.incrementAndGet();
                            //System.out.println("Tarefa concluída (" + concluido + "/" + carros.size() + ")");
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
                    preencherTabela(carrosMenu);
                }

                
                jLProgresso.setVisible(false);
            } catch (InterruptedException e) {
                JOptionPane.showMessageDialog(null, "Erro em algumas das threads que consultam a api do Rastrek");
            }
        }).start(); // Inicia a execução em uma thread separada
    }
    
    private void preencherTabela(List<Carro> carros) {
        while (modelo.getRowCount() > 0) {
            modelo.removeRow(0);
        }
        // Definir as colunas
        modelo.setColumnIdentifiers(new String[]{"Carro", "Data de Cadastro", "Data Att Automática", "KM Atualizado", "KM Última Troca", "KM Próxima Troca", "% de Troca"});

        // Adicionar os dados dos carros como linhas
        for (Carro carro : carros) {
            modelo.addRow(new Object[]{
                carro.getNome(),
                carro.getData_km_atual(),
                carro.getData_km_atual_automatica(),
                carro.getKm_atual(),
                carro.getKm_ultima_troca(),
                carro.getKm_proxima_troca()
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
    
    private void atualiza(String imei, List<Carro> carros, int kmAtualizar, String dataConsulta){
         for (Carro x: carros) {
            if(x.getImei_rastreador().equals(imei)){
                x.setKm_atual(x.getKm_atual()+kmAtualizar);
                x.setData_km_atual_automatica(dataConsulta);
                break;
            }
        }
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("carros.txt"))) {
            for (Carro carro: carros) {
                writer.write(carro.toString());
                writer.newLine();
            }
        } catch (IOException e ) {
            JOptionPane.showMessageDialog(null, "Erro ao salvar aquivo!");
        }
    }
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jCB = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLProgresso = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTbManut = new javax.swing.JTable();
        jBHistorico = new javax.swing.JButton();
        jBExecutar = new javax.swing.JButton();
        jBTrocaDeOleo = new javax.swing.JButton();
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
        jLProgresso.setText("Aguarde...");
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

        jBHistorico.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jBHistorico.setText("Histórico");
        jBHistorico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBHistoricoActionPerformed(evt);
            }
        });
        jCB.add(jBHistorico);
        jBHistorico.setBounds(40, 360, 110, 40);

        jBExecutar.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jBExecutar.setText("Executar");
        jBExecutar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBExecutarActionPerformed(evt);
            }
        });
        jCB.add(jBExecutar);
        jBExecutar.setBounds(490, 360, 100, 50);

        jBTrocaDeOleo.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jBTrocaDeOleo.setText("Troca de Óleo");
        jBTrocaDeOleo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBTrocaDeOleoActionPerformed(evt);
            }
        });
        jCB.add(jBTrocaDeOleo);
        jBTrocaDeOleo.setBounds(160, 360, 110, 40);

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

    private void jBHistoricoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBHistoricoActionPerformed
        List<Carro> carros = new CadastroCarro(carrosMenu).getListaAtualizadaCarros();
        HistoricoManutView historicoManut = new HistoricoManutView(carros);  
        historicoManut.setVisible(true);
    }//GEN-LAST:event_jBHistoricoActionPerformed

    private void jBExecutarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBExecutarActionPerformed
        executa();
    }//GEN-LAST:event_jBExecutarActionPerformed

    private void jBTrocaDeOleoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBTrocaDeOleoActionPerformed
        TrocaDeOleoView troca = new TrocaDeOleoView(carrosMenu);
        troca.setVisible(true);
    }//GEN-LAST:event_jBTrocaDeOleoActionPerformed

   

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBExecutar;
    private javax.swing.JButton jBHistorico;
    private javax.swing.JButton jBTrocaDeOleo;
    private javax.swing.JPanel jCB;
    private javax.swing.JLabel jLFundo;
    private javax.swing.JLabel jLProgresso;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTbManut;
    // End of variables declaration//GEN-END:variables
}
