/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
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
import programacao.model.Carro;

/**
 *
 * @author Koroch
 */
public class ManutencaoView extends javax.swing.JFrame {
    private static int qtdAletasVermelho = 0;
    private static int qtdAletasLaranja = 0;
    
    private List<Carro> carrosMenu;
    /**
     * Creates new form ManutencaoView
     * @param carros
     */
    public ManutencaoView(List<Carro> carros) {
        initComponents();
        this.carrosMenu = carros;
        executa();
    }
    
    private void calculaQtdAletas(List<Carro> carros) {
        qtdAletasVermelho = 0;
        qtdAletasLaranja = 0;
        for (Carro carro : carros) {
            int kmUltimaTroca = carro.getKm_ultima_troca();
            int kmAtualizacao = carro.getKm_atual();
            int kmProximaTroca = carro.getKm_ultima_troca()+10000;

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

    private static final String AUTH_URL = "https://painel.rastrek.com.br/auth";
    private static final String QUERY_URL = "https://painel.rastrek.com.br/rastreamento/getHistorico";
    private static CookieStore cookieStore;

    private void executa() {
        new Thread(() -> {
            try {
                jLProgresso.setVisible(true);
                // Configurar o CookieStore
                cookieStore = new BasicCookieStore();

                // Autenticar
                authenticate();

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

                            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                            Date dataInicioDate = sdf.parse(dataInicio);
                            Date dataHojeDate = sdf.parse(dataHoje);

                            if (dataInicioDate.compareTo(dataHojeDate) <= 0) {
                                fetchData(dataInicio, dataHoje, carro.getImei_rastreador(), carros);
                            } else {
                                //System.out.println("Carro " + carro.getNome() + " já está atualizado!");
                            }

                            System.out.println("Fim do processamento para carro: " + carro.getNome());

                            // Atualizar progresso
                            int concluido = progresso.incrementAndGet();
                            //System.out.println("Tarefa concluída (" + concluido + "/" + carros.size() + ")");
                            jLProgresso.setText("Buscando dados atualizados... (" + concluido + "/" + carros.size() + ")");
                            jLProgresso.setVisible(true);
                        } catch (Exception e) {
                            JOptionPane.showMessageDialog(null, "Erro ao processar dados do carro: " + carro.getNome());
                            e.printStackTrace();
                        }
                    });
                }

                // Finalizar executor após todas as tarefas
                executor.shutdown();
                while (!executor.isTerminated()) {
                    Thread.sleep(500); // Aguarda até todas as threads terminarem
                }

                preencherTabela(carros);
                jLProgresso.setVisible(false);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Erro em algumas das threads que consultam a api do Rastrek");
                e.printStackTrace();
            }
        }).start(); // Inicia a execução em uma thread separada
    }
    
    private void preencherTabela(List<Carro> carros) {
        // Criar o modelo da tabela
        DefaultTableModel modelo = new DefaultTableModel();
        
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
                (carro.getKm_ultima_troca()+10000)
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

        System.out.println("velha data " +data);
        
        System.out.println("nova data " +dateFormat.format(calendar.getTime()));
        return dateFormat.format(calendar.getTime());
    }

    private static void authenticate() throws Exception {
        try (CloseableHttpClient httpClient = HttpClients.custom()
                .setDefaultCookieStore(cookieStore)
                .build()) {

            // Cria a requisição de autenticação
            HttpPost authRequest = new HttpPost(AUTH_URL);
            authRequest.setHeader("Authorization", "Bearer token");
            authRequest.setHeader("User-Agent", "Mozilla/5.0");

            // Configura o corpo da requisição como multipart/form-data
            HttpEntity authEntity = MultipartEntityBuilder.create()
                    .addTextBody("usuario", "user")
                    .addTextBody("senha", "senha")
                    .build();
            authRequest.setEntity(authEntity);

            // Executa a requisição
            HttpResponse response = httpClient.execute(authRequest);
            int statusCode = response.getStatusLine().getStatusCode();
            System.out.println("Código de resposta da autenticação: " + statusCode);

            if (statusCode == 200 || statusCode == 303) {
                System.out.println("Sessão iniciada com sucesso. Cookies armazenados:");
                cookieStore.getCookies().forEach(cookie -> {
                    System.out.println(cookie.getName() + "=" + cookie.getValue());
                });
            } else {
                System.err.println("Falha na autenticação.");
                System.err.println(EntityUtils.toString(response.getEntity()));
            }
        }
    }

    private void fetchData(String dataInicial, String dataFinal, String imeiCarro, List<Carro> carros) throws Exception {
        try (CloseableHttpClient httpClient = HttpClients.custom()
                .setDefaultCookieStore(cookieStore)
                .build()) {

            // Cria a requisição de consulta
            HttpPost queryRequest = new HttpPost(QUERY_URL);
            queryRequest.setHeader("User-Agent", "Mozilla/5.0");

            // Configura o corpo da requisição
            HttpEntity queryEntity = MultipartEntityBuilder.create()
                    .addTextBody("dataInicial", dataInicial+" 00:00")
                    .addTextBody("dataFinal", dataFinal+" 00:00")
                    .addTextBody("imei", imeiCarro)
                    .build();
            queryRequest.setEntity(queryEntity);

            // Executa a requisição
            HttpResponse response = httpClient.execute(queryRequest);
            int statusCode = response.getStatusLine().getStatusCode();
            System.out.println("Código de resposta da consulta: " + statusCode);

            String responseBody = EntityUtils.toString(response.getEntity());
            if (statusCode == 200) {
                JsonObject jsonObject = JsonParser.parseString(responseBody).getAsJsonObject();

                if (jsonObject.has("data")) {
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
                            atualiza(imei, carros, kmAtualizar, dataFinal);
                            System.out.println("IMEI: " + imei);
                            System.out.println("License Plate: " + licensePlate);
                            System.out.println("Trip Distance: " + tripDistance);
                        } else {
                            System.err.println("O objeto 'header' não está presente.");
                        }
                    } else {
                        System.err.println("O objeto 'html' não está presente.");
                    }
                } else {
                    System.err.println("O objeto 'data' não está presente.");
                }
            } else {
                System.err.println("Falha na consulta.");
                System.err.println(EntityUtils.toString(response.getEntity()));
            }
        }
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
        jTbManut.setEnabled(false);
        jTbManut.setFocusable(false);
        jTbManut.setRequestFocusEnabled(false);
        jTbManut.setRowSelectionAllowed(false);
        jScrollPane1.setViewportView(jTbManut);

        jCB.add(jScrollPane1);
        jScrollPane1.setBounds(40, 60, 1000, 270);

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

   

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jCB;
    private javax.swing.JLabel jLFundo;
    private javax.swing.JLabel jLProgresso;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTbManut;
    // End of variables declaration//GEN-END:variables
}
