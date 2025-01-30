package view;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import org.apache.http.client.CookieStore;
import org.apache.http.impl.client.BasicCookieStore;
import programacao.model.Bloco;
import programacao.model.Carro;
import programacao.model.Cliente;
import programacao.model.HistoricoKms;
import utils.FetchDataCarRastrek;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.HashMap;
import java.util.stream.Collectors;
import javax.swing.table.DefaultTableModel;
import programacao.model.DadosAcumuladosPorCarro;
import programacao.model.DadosAcumuladosPorProjeto;
import programacao.model.DadosProjeto;
import programacao.model.Usuario;
import utils.SheetDBProvider;

/**
 *
 * @author Koroch
 */
public class GerenciamentoHorasEKms extends javax.swing.JFrame {
    private final List<HistoricoKms> historicoKms = new ArrayList<>();
    private final List<String> linhasArquivoHistKms = new ArrayList<>();
    private List<Carro> carrosManut = new ArrayList<>();
    private List<Bloco> blocos = new ArrayList<>();
    private List<Cliente> clientes = new ArrayList<>();
    private CookieStore cookieStore = new BasicCookieStore();
    
    public static final String EMPRESA = "SGA Soluções em Automação, R. Djanir Hausen de Oliveira, nº 31 - Pavilhão 1 - Distrito Industrial, Venâncio Aires - RS, 95800-000";
    DefaultTableModel modeloProjeto = new DefaultTableModel() {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    DefaultTableModel modeloCarro = new DefaultTableModel() {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    
    public GerenciamentoHorasEKms(List<Carro> carros, List<Bloco> blocos, List<Cliente> clientes, CookieStore cookieStore) {
        initComponents();
        this.carrosManut = carros;
        this.blocos = blocos;
        this.cookieStore = cookieStore;
        this.clientes = clientes;
    }
    
    private void atualizaHistoricoKms(){
        linhasArquivoHistKms.clear();
        historicoKms.clear();
        try (BufferedReader br = new BufferedReader(new FileReader("historicoKms.txt"))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                linhasArquivoHistKms.add(linha);
            }

            linhasArquivoHistKms.forEach(elemento -> {
                String[] dados = elemento.split("\\|");
                int qtdArray = dados.length;
                Cliente cliente = (qtdArray >= 5 ? dados[4].trim() : null) != null ? new Cliente().getById(Integer.parseInt(dados[4].trim()), clientes):null;
                historicoKms.add(new HistoricoKms(
                        dados[0].trim(),
                        (qtdArray >= 2 ? dados[1].trim() : null),
                        (qtdArray >= 3 ? dados[2].trim() != null ? Math.round(Double.parseDouble(dados[2].trim()) * 100.0) / 100.0 : 0 : 0),
                        (qtdArray >= 4 ? dados[3].trim() : null),
                        cliente,
                        (qtdArray >= 6 ? Integer.parseInt(dados[5].trim()) : 0),                        
                        (qtdArray >= 7 ? Integer.parseInt(dados[6].trim()) : 0),
                        (qtdArray >= 8 ? Double.parseDouble(dados[7].trim()) : 0)
                ));
            });
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Erro no arquivo historicoKms.txt, talvez ele ainda esteja vazio!");
        }
    }
    
    private void executarProcessamentoEmSegundoPlano() {
        SwingWorker<Void, Void> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() {
                salvaQuilimetragem(blocos);
                return null;
            }
            @Override
            protected void done() {
                jLProgresso.setVisible(false);
            }
        };

        worker.execute();
    }
    
    private void preencherTabelaProjeto() {
        while (modeloProjeto.getRowCount() > 0) {
            modeloProjeto.removeRow(0);
        }

        modeloProjeto.setColumnIdentifiers(new String[]{"Projeto", "Cliente", "Horas Engenharia", "Horas Execução", "KM"});

        Date dataInicio = jDCDataInicioPeriodo.getDate();
        Date dataFim = jDCDataFimPeriodo.getDate();
        LocalDate inicioPeriodo = dataInicio.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate fimPeriodo = dataFim.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        List<HistoricoKms> dadosFiltrados = historicoKms.stream()
            .filter(historicoKm -> {
                LocalDate dataProgramacao = LocalDate.parse(
                    historicoKm.getDataProgramacao(),
                    DateTimeFormatter.ofPattern("dd/MM/yyyy")
                );
                return (dataProgramacao.isEqual(inicioPeriodo) || dataProgramacao.isAfter(inicioPeriodo)) &&
                       (dataProgramacao.isEqual(fimPeriodo) || dataProgramacao.isBefore(fimPeriodo));
            })
            .collect(Collectors.toList());

        // Usar chave combinada para diferenciar cliente e projeto nulo
        Map<String, DadosAcumuladosPorProjeto> acumuladosPorProjeto = new HashMap<>();
        for (HistoricoKms historicoKm : dadosFiltrados) {
            String projeto = (historicoKm.getNumeroProjeto() != null && !historicoKm.getNumeroProjeto().equals("null")) 
                ? historicoKm.getNumeroProjeto() 
                : "Sem Projeto";
            String cliente = historicoKm.getCliente().getNome();

            // Criar chave única combinando projeto e cliente
            String chave = projeto + " - " + cliente;
            
            DadosAcumuladosPorProjeto dadosProjeto = acumuladosPorProjeto
                    .computeIfAbsent(chave, k -> new DadosAcumuladosPorProjeto(projeto));
            dadosProjeto.setNomeCliente(cliente);
            dadosProjeto.adicionarKm(historicoKm.getKmPercorrido());
            dadosProjeto.adicionarHorasExecucao(historicoKm.getHorasPorPessoa() * historicoKm.getQuantidadeEquipeExecucao());
            dadosProjeto.adicionarHorasEngenharia(historicoKm.getHorasPorPessoa() * historicoKm.getQuantidadeEquipeEngenharia());
        }

        // Ordenar os dados acumulados por projeto
        List<DadosAcumuladosPorProjeto> dadosOrdenadosPorProjeto = new ArrayList<>(acumuladosPorProjeto.values());
        dadosOrdenadosPorProjeto.sort(Comparator.comparing(DadosAcumuladosPorProjeto::getNumeroProjeto));

        // Preencher a tabela com os dados ordenados
        for (DadosAcumuladosPorProjeto dadosProjeto : dadosOrdenadosPorProjeto) {
            modeloProjeto.addRow(new Object[]{
                dadosProjeto.getNumeroProjeto(),
                dadosProjeto.getNomeCliente(),
                String.format("%.1f", dadosProjeto.getTotalHorasEngenharia()),
                String.format("%.1f", dadosProjeto.getTotalHorasExecucao()),
                String.format("%.1f", dadosProjeto.getTotalKmPercorrido())
            });
        }

        jTbHrKmProjeto.setModel(modeloProjeto);
        jTbHrKmProjeto.setFont(jTbHrKmProjeto.getFont().deriveFont(Font.BOLD));
        jTbHrKmProjeto.setRowHeight(25); // Altura das linhas para melhor visualização
    }

    private void preencherTabelaCarro() {
        while (modeloCarro.getRowCount() > 0) {
            modeloCarro.removeRow(0);
        }
        modeloCarro.setColumnIdentifiers(new String[]{"Carro", "KM"});

        Date dataInicio = jDCDataInicioPeriodo.getDate();
        Date dataFim = jDCDataFimPeriodo.getDate();
        LocalDate inicioPeriodo = dataInicio.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate fimPeriodo = dataFim.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        List<HistoricoKms> dadosFiltrados = historicoKms.stream()
            .filter(historicoKm -> {
                LocalDate dataProgramacao = LocalDate.parse(
                    historicoKm.getDataProgramacao(),
                    DateTimeFormatter.ofPattern("dd/MM/yyyy")
                );
                return (dataProgramacao.isEqual(inicioPeriodo) || dataProgramacao.isAfter(inicioPeriodo)) &&
                       (dataProgramacao.isEqual(fimPeriodo) || dataProgramacao.isBefore(fimPeriodo));
            })
            .collect(Collectors.toList());

        Map<String, DadosAcumuladosPorCarro> acumuladosPorCarro = new HashMap<>();
        for (HistoricoKms historicoKm : dadosFiltrados) {
            String placaCarro = historicoKm.getPlacaCarro();

            DadosAcumuladosPorCarro dadosCarro = acumuladosPorCarro
                    .computeIfAbsent(placaCarro, DadosAcumuladosPorCarro::new);
            dadosCarro.adicionarKm(historicoKm.getKmPercorrido());
        }
        
        List<DadosAcumuladosPorCarro> dadosOrdenadosPorCarro = new ArrayList<>(acumuladosPorCarro.values());
        dadosOrdenadosPorCarro.sort(Comparator.comparing(DadosAcumuladosPorCarro::getPlacaCarro));

        // Preencher a tabela com os dados ordenados
        for (DadosAcumuladosPorCarro historicoKm : dadosOrdenadosPorCarro) {
            modeloCarro.addRow(new Object[]{
                historicoKm.getPlacaCarro(),
                String.format("%.1f", historicoKm.getTotalKmPercorrido())
            });
        }

        jTbHrKmCarro.setModel(modeloCarro);
        jTbHrKmCarro.setFont(jTbHrKmCarro.getFont().deriveFont(Font.BOLD));
        jTbHrKmCarro.setRowHeight(25); 
    }
    

    private void salvaQuilimetragem(List<Bloco> blocos){
        Set<Bloco> blocosUnicos = new HashSet<>(blocos);
        String dataHoje = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
        
        ExecutorService executor = Executors.newFixedThreadPool(30); // Limita a 30 threads simultâneas
        AtomicInteger progresso = new AtomicInteger(0); // Controla o progresso
        int total = blocosUnicos.size();

        blocosUnicos.forEach(bloco -> executor.submit(() -> {
            try {
                String primeiraData = bloco.getDataProgramacao();
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                Date dataInicioDate = sdf.parse(primeiraData);
                Date dataHojeDate = sdf.parse(decrementaUmDia(dataHoje));
                boolean isCalculado = false;
                String placa = bloco.getCarro() != null ? bloco.getCarro().getPlaca() : "null";

                for (HistoricoKms histkms : historicoKms) {
                    if (bloco.getDataProgramacao().equals(histkms.getDataProgramacao())
                            && placa.equals(histkms.getPlacaCarro())
                            && (bloco.getProjeto().equals(histkms.getNumeroProjeto())
                            || bloco.getProjeto().equals("") 
                            || bloco.getProjeto().equals("null")
                            || bloco.getProjeto() == null)) {
                        isCalculado = true;
                        break;
                    }
                }
                
                if (dataInicioDate.compareTo(dataHojeDate) <= 0 && !isCalculado) {
                    double horasComMinutosTrabalhadas = 0;
                    FetchDataCarRastrek dataCar = new FetchDataCarRastrek(cookieStore);
                    Gson gson = new Gson();
                        
                    if(bloco.getCarro()!=null){
                        String dataString = sdf.format(sdf.parse(incrementaUmDia(primeiraData)));
                        JsonObject jsonObject = dataCar.fetchData(primeiraData, dataString, bloco.getCarro().getImei_rastreador(), carrosManut);

                        JsonObject data = jsonObject.getAsJsonObject("data");
                        JsonArray positionsArray = data.getAsJsonArray("positions");

                        // Converter JsonArray para List<Map<String, Object>>
                        java.lang.reflect.Type listType = new TypeToken<List<Map<String, Object>>>(){}.getType();
                        List<Map<String, Object>> positionsList = gson.fromJson(positionsArray, listType);
                        List<Map<String, Object>> movingPositions = positionsList.stream()
                            .filter(pos -> (Boolean) pos.get("moving"))
                            .toList();

                        String dataSaida[] = bloco.getDataDeSaida().split("/");
                        String horaSaida[] = bloco.getHorarioDeSaida().split(":");
                        String dataRetorno[] = bloco.getDataDeRetorno().split("/");
                        if(bloco.getDataProgramacao().equals(bloco.getDataDeSaida()) || bloco.getDataProgramacao().equals(bloco.getDataDeRetorno())){
                            String horaRetorno[];
                            LocalDateTime dataEhoraSaida, dataEhoraRetorno;
                            if(!movingPositions.isEmpty()){
                                Map<String, Object> ultimaPosition = movingPositions.get(movingPositions.size()-1);
                                String dado = (String) ultimaPosition.get("date");
                                String dataFormatada[] = dado.split(" ");
                                horaRetorno = dataFormatada[1].split(":");
                                if(!bloco.getDataProgramacao().equals(bloco.getDataDeSaida())){
                                    dataEhoraRetorno = LocalDateTime.of(Integer.parseInt(dataRetorno[2]),Integer.parseInt(dataRetorno[1]),Integer.parseInt(dataRetorno[0]),Integer.parseInt(horaRetorno[0]),Integer.parseInt(horaRetorno[1]));
                                    dataEhoraSaida = LocalDateTime.of(Integer.parseInt(dataRetorno[2]),Integer.parseInt(dataRetorno[1]),Integer.parseInt(dataRetorno[0]),Integer.parseInt(horaSaida[0]),Integer.parseInt(horaSaida[1]));
                                }else if(!bloco.getDataProgramacao().equals(bloco.getDataDeRetorno())){
                                    dataEhoraSaida = LocalDateTime.of(Integer.parseInt(dataSaida[2]),Integer.parseInt(dataSaida[1]),Integer.parseInt(dataSaida[0]),Integer.parseInt(horaSaida[0]),Integer.parseInt(horaSaida[1]));
                                    dataEhoraRetorno = LocalDateTime.of(Integer.parseInt(dataSaida[2]),Integer.parseInt(dataSaida[1]),Integer.parseInt(dataSaida[0]),Integer.parseInt(horaRetorno[0]),Integer.parseInt(horaRetorno[1]));
                                }
                                dataEhoraSaida = LocalDateTime.of(Integer.parseInt(dataSaida[2]),Integer.parseInt(dataSaida[1]),Integer.parseInt(dataSaida[0]),Integer.parseInt(horaSaida[0]),Integer.parseInt(horaSaida[1]));
                                dataEhoraRetorno = LocalDateTime.of(Integer.parseInt(dataRetorno[2]),Integer.parseInt(dataRetorno[1]),Integer.parseInt(dataRetorno[0]),Integer.parseInt(horaRetorno[0]),Integer.parseInt(horaRetorno[1]));
                            }else{
                                horaRetorno = bloco.getHorarioDeTrabalhoFim().split(":");
                                dataEhoraSaida = LocalDateTime.of(Integer.parseInt(dataSaida[2]),Integer.parseInt(dataSaida[1]),Integer.parseInt(dataSaida[0]),Integer.parseInt(horaSaida[0]),Integer.parseInt(horaSaida[1]));
                                dataEhoraRetorno = LocalDateTime.of(Integer.parseInt(dataRetorno[2]),Integer.parseInt(dataRetorno[1]),Integer.parseInt(dataRetorno[0]),Integer.parseInt(horaRetorno[0]),Integer.parseInt(horaRetorno[1]));
                                //System.out.println("Tentando: "+bloco.getCliente().getBuscaEndereco()+", Dados mais recentes: " + " "+bloco.getCarro().getPlaca()+", IMEI: "+bloco.getCarro().getImei_rastreador());
                            }
                            long diferencaTotalMinutos = Duration.between(dataEhoraSaida, dataEhoraRetorno).toMinutes();
                            long horas = diferencaTotalMinutos / 60;
                            long minutos = diferencaTotalMinutos % 60;
                            double horasComMinutos = horas + (minutos / 60.0);
                            horasComMinutos -= 1;
                            horasComMinutosTrabalhadas = Math.round(horasComMinutos * 100.0) / 100.0;
                            if(horasComMinutosTrabalhadas <= 0){
                                horaRetorno = bloco.getHorarioDeTrabalhoFim().split(":");
                                dataEhoraSaida = LocalDateTime.of(Integer.parseInt(dataSaida[2]),Integer.parseInt(dataSaida[1]),Integer.parseInt(dataSaida[0]),Integer.parseInt(horaSaida[0]),Integer.parseInt(horaSaida[1]));
                                dataEhoraRetorno = LocalDateTime.of(Integer.parseInt(dataRetorno[2]),Integer.parseInt(dataRetorno[1]),Integer.parseInt(dataRetorno[0]),Integer.parseInt(horaRetorno[0]),Integer.parseInt(horaRetorno[1]));
                                diferencaTotalMinutos = Duration.between(dataEhoraSaida, dataEhoraRetorno).toMinutes();
                                horas = diferencaTotalMinutos / 60;
                                minutos = diferencaTotalMinutos % 60;
                                horasComMinutos = horas + (minutos / 60.0);
                                horasComMinutos -= 1;
                                horasComMinutosTrabalhadas = Math.round(horasComMinutos * 100.0) / 100.0;
                            }
                        }
                    }else{
                        horasComMinutosTrabalhadas = 8.8;
                    }
                    
                    if(horasComMinutosTrabalhadas >= 8 && horasComMinutosTrabalhadas <= 8.8){
                        horasComMinutosTrabalhadas = 8.8;
                    }
                    
                    if(bloco.getCliente() != null){
                        double quilometragem = 0;
                        if(bloco.getDataProgramacao().equals(bloco.getDataDeSaida()) && bloco.getDataProgramacao().equals(bloco.getDataDeRetorno())){
                            quilometragem = bloco.getCliente().getDistanciaEmKm()*2;
                        }else if(bloco.getDataProgramacao().equals(bloco.getDataDeSaida()) || bloco.getDataProgramacao().equals(bloco.getDataDeRetorno())){
                            quilometragem = bloco.getCliente().getDistanciaEmKm();
                        }
                        
                        int quantidadeExecucao = (int) bloco.getEquipe().stream().filter((Usuario user) -> user.getFuncao().equals("EXECUÇÃO")).count();
                        int quantidadeEngenharia = (int) bloco.getEquipe().stream().filter((Usuario user) -> user.getFuncao().equals("ADMINISTRATIVO")).count();
                        
                        if(bloco.getCliente().getDistanciaEmKm() > 0){
                            HistoricoKms hist = new HistoricoKms(
                                primeiraData,
                                bloco.getCarro() != null ? bloco.getCarro().getPlaca() : null,
                                quilometragem,
                                bloco.getProjeto().equals("") ? null : bloco.getProjeto(),
                                bloco.getCliente(),
                                quantidadeEngenharia, //engenharia
                                quantidadeExecucao, //execução
                                horasComMinutosTrabalhadas
                            );

                            synchronized (this) {
                                try (BufferedWriter writer = new BufferedWriter(new FileWriter("historicoKms.txt", true))) {
                                    writer.write(hist.toString());
                                    writer.newLine();
                                }
                            }
                            
                            if(!bloco.getCarroExtra().equals("null") && !bloco.getCarroExtra().equals("N/A") && bloco.getCarroExtra()!=null){
                                HistoricoKms hist2 = new HistoricoKms(
                                    primeiraData,
                                    bloco.getCarroExtra().split(" ")[1],
                                    quilometragem,
                                    bloco.getProjeto().equals("") ? null : bloco.getProjeto(),
                                    bloco.getCliente(),
                                    0,
                                    0,
                                    0
                                );

                                synchronized (this) {
                                    try (BufferedWriter writer = new BufferedWriter(new FileWriter("historicoKms.txt", true))) {
                                        writer.write(hist2.toString());
                                        writer.newLine();
                                    }
                                }
                            }
                        }
                    }
                }else{
                    System.out.println("Ignorado");
                }

                int concluido = progresso.incrementAndGet();
                SwingUtilities.invokeLater(() -> {
                    jLProgresso.setText("Buscando dados atualizados... (" + concluido + "/" + total + ")");
                    jLProgresso.setVisible(true);
                });
            } catch (Exception ex) {
                Logger.getLogger(GerenciamentoHorasEKms.class.getName()).log(Level.SEVERE, null, ex);
            }
        }));

        executor.shutdown();
        while (!executor.isTerminated()) {
            jDCDataInicioPeriodo.setEnabled(false);
            jDCDataFimPeriodo.setEnabled(false);
            jBBuscar.setEnabled(false);
            jBPlanilhaOnline.setEnabled(false);
            try {
                Thread.sleep(500); // Aguarda até todas as threads terminarem
            } catch (InterruptedException ex) {
                Logger.getLogger(GerenciamentoHorasEKms.class.getName()).log(Level.SEVERE, null, ex);
            }
            preencherTabelaProjeto();
            preencherTabelaCarro();
        }

        // Aguarda todas as threads terminarem
        new Thread(() -> {
            try {
                executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
                jBBuscar.setEnabled(true);
                jBPlanilhaOnline.setEnabled(true);
                jDCDataInicioPeriodo.setEnabled(true);
                jDCDataFimPeriodo.setEnabled(true);
                //SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(this, "Processamento concluído!"));
            } catch (InterruptedException e) {
                Logger.getLogger(GerenciamentoHorasEKms.class.getName()).log(Level.SEVERE, null, e);
            }
        }).start();
    }
                
    private String decrementaUmDia(String data) throws Exception {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date parsedDate = dateFormat.parse(data);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(parsedDate);
        calendar.add(Calendar.DAY_OF_MONTH, -1); // subtrai 1 dia
        return dateFormat.format(calendar.getTime());
    }            
    
    private String incrementaUmDia(String data) throws Exception {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date parsedDate = dateFormat.parse(data);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(parsedDate);
        calendar.add(Calendar.DAY_OF_MONTH, +1); // subtrai 1 dia
        return dateFormat.format(calendar.getTime());
    }    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jCB = new javax.swing.JPanel();
        jLProgresso = new javax.swing.JLabel();
        jDCDataFimPeriodo = new com.toedter.calendar.JDateChooser();
        jSPHrKmProjeto = new javax.swing.JScrollPane();
        jTbHrKmProjeto = new javax.swing.JTable();
        jSPHrKmCarro = new javax.swing.JScrollPane();
        jTbHrKmCarro = new javax.swing.JTable();
        jLName = new javax.swing.JLabel();
        jDCDataInicioPeriodo = new com.toedter.calendar.JDateChooser();
        jBBuscar = new javax.swing.JButton();
        jBPlanilhaOnline = new javax.swing.JButton();
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

        jLProgresso.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLProgresso.setForeground(new java.awt.Color(0, 153, 153));
        jLProgresso.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLProgresso.setText("Clique em buscar para iniciar!");
        jCB.add(jLProgresso);
        jLProgresso.setBounds(420, 230, 300, 100);

        jDCDataFimPeriodo.setToolTipText("Dia/Mês/Ano");
        jDCDataFimPeriodo.setDate(new java.util.Date(new java.util.Date().getTime()));
        jDCDataFimPeriodo.setDateFormatString("dd/MM/yyyy");
        jDCDataFimPeriodo.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jDCDataFimPeriodoPropertyChange(evt);
            }
        });
        jCB.add(jDCDataFimPeriodo);
        jDCDataFimPeriodo.setBounds(550, 40, 160, 30);

        jTbHrKmProjeto.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Projeto", "Cliente", "Horas Engenharia", "Horas Execução", "Km"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTbHrKmProjeto.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jTbHrKmProjeto.setFocusable(false);
        jTbHrKmProjeto.setOpaque(false);
        jTbHrKmProjeto.setRequestFocusEnabled(false);
        jTbHrKmProjeto.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jTbHrKmProjeto.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jTbHrKmProjeto.setShowGrid(false);
        jTbHrKmProjeto.setUpdateSelectionOnSort(false);
        jTbHrKmProjeto.setVerifyInputWhenFocusTarget(false);
        jSPHrKmProjeto.setViewportView(jTbHrKmProjeto);

        jCB.add(jSPHrKmProjeto);
        jSPHrKmProjeto.setBounds(20, 90, 760, 360);

        jTbHrKmCarro.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Carro", "Km"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTbHrKmCarro.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jTbHrKmCarro.setFocusable(false);
        jTbHrKmCarro.setOpaque(false);
        jTbHrKmCarro.setRequestFocusEnabled(false);
        jTbHrKmCarro.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jTbHrKmCarro.setShowGrid(false);
        jTbHrKmCarro.setUpdateSelectionOnSort(false);
        jTbHrKmCarro.setVerifyInputWhenFocusTarget(false);
        jSPHrKmCarro.setViewportView(jTbHrKmCarro);

        jCB.add(jSPHrKmCarro);
        jSPHrKmCarro.setBounds(810, 90, 250, 360);

        jLName.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLName.setForeground(new java.awt.Color(255, 255, 255));
        jLName.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLName.setText("Gerenciamento de Horas e Quilometragem");
        jCB.add(jLName);
        jLName.setBounds(410, 0, 300, 20);

        jDCDataInicioPeriodo.setToolTipText("Dia/Mês/Ano");
        jDCDataInicioPeriodo.setDate(new java.util.Date(new java.util.Date().getTime()));
        jDCDataInicioPeriodo.setDateFormatString("dd/MM/yyyy");
        jDCDataInicioPeriodo.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jDCDataInicioPeriodoPropertyChange(evt);
            }
        });
        jCB.add(jDCDataInicioPeriodo);
        jDCDataInicioPeriodo.setBounds(380, 40, 160, 30);

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
        jBBuscar.setBounds(730, 30, 40, 40);

        jBPlanilhaOnline.setBackground(new java.awt.Color(0, 204, 51));
        jBPlanilhaOnline.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jBPlanilhaOnline.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/planilha.png"))); // NOI18N
        jBPlanilhaOnline.setText("Enviar Atualização");
        jBPlanilhaOnline.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBPlanilhaOnlineActionPerformed(evt);
            }
        });
        jCB.add(jBPlanilhaOnline);
        jBPlanilhaOnline.setBounds(310, 460, 180, 50);

        getContentPane().add(jCB);
        jCB.setBounds(0, 50, 1120, 560);

        jLFundo.setBackground(new java.awt.Color(51, 51, 51));
        jLFundo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/tela_de_fundo.jpeg"))); // NOI18N
        jLFundo.setVerifyInputWhenFocusTarget(false);
        getContentPane().add(jLFundo);
        jLFundo.setBounds(-140, -10, 1250, 730);

        setSize(new java.awt.Dimension(1100, 676));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jDCDataFimPeriodoPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jDCDataFimPeriodoPropertyChange

    }//GEN-LAST:event_jDCDataFimPeriodoPropertyChange

    private void jDCDataInicioPeriodoPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jDCDataInicioPeriodoPropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_jDCDataInicioPeriodoPropertyChange

    private void jBBuscarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBBuscarMouseClicked

    }//GEN-LAST:event_jBBuscarMouseClicked

    private void jBBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBBuscarActionPerformed
        Date dataInicio = jDCDataInicioPeriodo.getDate();
        Date dataFim = jDCDataFimPeriodo.getDate();
        if (dataInicio == null || dataFim == null) {
            JOptionPane.showMessageDialog(this, "Por favor, selecione o período de datas.");
            return;
        }
        atualizaHistoricoKms();
        executarProcessamentoEmSegundoPlano();
    }//GEN-LAST:event_jBBuscarActionPerformed

    private void jBPlanilhaOnlineActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBPlanilhaOnlineActionPerformed
        if (jTbHrKmProjeto.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Tabela vazia! Busque com um período de datas válido e que tenha programações primeiro");
            return;
        }
        
        Object[] options = {"Enviar", "Cancelar"};
        int result = JOptionPane.showOptionDialog(
            null,
            "Deseja enviar os dados que estão na tabela para a planilha online?", // Mensagem
            "Confirmação", // Título da janela
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            options,
            options[0] // Botão padrão
        );
        
        if (result == JOptionPane.NO_OPTION || result == JOptionPane.CLOSED_OPTION) {
            return;
        }else{
            SheetDBProvider sheetDBProvider = new SheetDBProvider();
            try {
                for (int i = 0; i < jTbHrKmProjeto.getRowCount(); i++) {
                    String projeto = jTbHrKmProjeto.getValueAt(i, 0) != null ? (String) jTbHrKmProjeto.getValueAt(i, 0):null;
                    if(projeto != null && !projeto.equals("Sem Projeto") && !projeto.equals("1902")){
                        int horasEngenharia = Double.valueOf(((String)jTbHrKmProjeto.getValueAt(i, 2)).replace(",", ".")).intValue();
                        int horasExecucao = Double.valueOf(((String)jTbHrKmProjeto.getValueAt(i, 3)).replace(",", ".")).intValue();
                        int km = Double.valueOf(((String)jTbHrKmProjeto.getValueAt(i, 4)).replace(",", ".")).intValue();
                        DadosProjeto dadosProj = new DadosProjeto(projeto, horasEngenharia, horasExecucao, km);
                        String resultado = sheetDBProvider.saveData(dadosProj);
                        System.out.println(resultado);
                    }
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(
                    null,
                    "Erro: "+e.getMessage(), // Mensagem
                    "Sucesso", // Título da janela
                    JOptionPane.ERROR
                );
                return;
            }
            JOptionPane.showMessageDialog(
                null,
                "Enviado com sucesso!", // Mensagem
                "Sucesso", // Título da janela
                JOptionPane.INFORMATION_MESSAGE // Tipo de mensagem (ícone de informação)
            );
        }
    }//GEN-LAST:event_jBPlanilhaOnlineActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBBuscar;
    private javax.swing.JButton jBPlanilhaOnline;
    private javax.swing.JPanel jCB;
    private com.toedter.calendar.JDateChooser jDCDataFimPeriodo;
    private com.toedter.calendar.JDateChooser jDCDataInicioPeriodo;
    private javax.swing.JLabel jLFundo;
    private javax.swing.JLabel jLName;
    private javax.swing.JLabel jLProgresso;
    private javax.swing.JScrollPane jSPHrKmCarro;
    private javax.swing.JScrollPane jSPHrKmProjeto;
    private javax.swing.JTable jTbHrKmCarro;
    private javax.swing.JTable jTbHrKmProjeto;
    // End of variables declaration//GEN-END:variables
}
