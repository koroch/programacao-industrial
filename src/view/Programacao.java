package view;

import com.toedter.calendar.JDateChooser;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;
import programacao.model.Carro;
import programacao.model.Cliente;
import programacao.model.Estado;
import programacao.model.Usuario;
import javax.swing.text.MaskFormatter;
import org.apache.http.client.CookieStore;
import org.apache.http.impl.client.BasicCookieStore;
import programacao.model.Bloco;
import programacao.model.DemaisInfos;
import programacao.model.Hotel;
import utils.Authenticate;

/**
 *
 * @author Koroch
 */
public class Programacao extends javax.swing.JFrame {
    private String dataSalva = "";
    
    private List<String> linhasArquivoBlocoProgramacaoAux = new ArrayList<>();
    private List<String> linhasArquivoBlocoProgramacao = new ArrayList<>();
    private List<Bloco> blocos = new ArrayList<>();
    public static int ultimoIdBloco = 0;

    private DefaultListModel<String> modeloSelec = new DefaultListModel<>();
    private DefaultListModel<String> listModel = new DefaultListModel<>();

    private List<String> linhasArquivoUser = new ArrayList<>();
    private List<Usuario> usuarios = new ArrayList<>();
    public static int ultimoIdUser = 0;

    private List<String> linhasArquivoCarro = new ArrayList<>();
    private List<Carro> carros = new ArrayList<>();
    public static int ultimoIdCarro = 0;

    private List<String> linhasArquivoCliente = new ArrayList<>();
    private List<Cliente> clientes = new ArrayList<>();
    public static int ultimoIdCliente = 0;

    private List<String> linhasArquivoHotel = new ArrayList<>();
    private List<Hotel> hoteis = new ArrayList<>();
    public static int ultimoIdHotel = 0;

    private List<String> linhasArquivoDemaisInfos = new ArrayList<>();
    private List<DemaisInfos> demaisInfos = new ArrayList<>();
    public static int ultimoIdDemaisInfos = 0;
    
    private boolean olhoEstaClicado = false;
    private boolean olhoEstaCarroClicado = false;
    private boolean olhoEstaCarroExtraClicado = false;
    
    //private CookieStore cookieStore = new BasicCookieStore();
    private ManutencaoGPSView manutencao = null; 
    
    private GerenciamentoHorasEKms gerenciamentoHorasEKms = null;
    
    public Programacao() throws Exception {
        
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();

        // Obtém a altura da barra de tarefas
        int taskBarHeight = (int) toolkit.getScreenSize().getHeight() - (int) java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().getBounds().getHeight();

        // A largura da tela deve ser a total, sem subtração
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;

        // Ajusta o tamanho da janela para maximizar sem tampar a barra de tarefas
        this.setSize(screenWidth, screenHeight - taskBarHeight);  // Ajusta para a largura total e a altura visível
        this.setLocation(0, 0); 
        
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
            // Configurando propriedades do Nimbus para JComboBox
            UIManager.put("ComboBox.background", Color.WHITE); // Cor de fundo
            UIManager.put("ComboBox.foreground", Color.BLACK); // Cor do texto
            UIManager.put("ComboBox.selectionBackground", Color.LIGHT_GRAY); // Cor de fundo da seleção
            UIManager.put("ComboBox.selectionForeground", Color.BLACK); // Cor do texto da seleção
            UIManager.put("ComboBox[Disabled].background", Color.LIGHT_GRAY); // Cor de fundo quando desativado
            UIManager.put("ComboBox[MouseOver].background", Color.WHITE); // Cor de fundo quando mouse está sobre
            UIManager.put("ComboBox[Focused].border", BorderFactory.createLineBorder(Color.GRAY)); // Borda quando em foco
            UIManager.put("ComboBox[Selected].background", Color.LIGHT_GRAY); // Fundo quando selecionado
            UIManager.put("ComboBox.border", BorderFactory.createLineBorder(Color.GRAY)); // Borda
            UIManager.put("Button.background", Color.WHITE);
            UIManager.put("Button.foreground", Color.BLACK);
            UIManager.put("TextField.background", Color.WHITE);
            UIManager.put("TextField.foreground", Color.BLACK);
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException e) {
        }

        initComponents();
        Authenticate auth = new Authenticate();
        
        //CookieStore cookieStoreLocal = auth.authenticate();
        //this.cookieStore = cookieStoreLocal;
        
        jLbAlertVermelho.setVisible(false);
        jLbAlertaLaranja.setVisible(false);
        jLbVermelho.setVisible(false);
        jLbAlertLaranja.setVisible(false);
        
        maximizarJanela();

        // Alinhar horizontalmente o JPanel após o JFrame ser exibido
        this.addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentShown(java.awt.event.ComponentEvent evt) {
                alinharHorizontalmenteJPanel(); // Alinha após o JFrame ser exibido
            }

            @Override
            public void componentResized(java.awt.event.ComponentEvent evt) {
                alinharHorizontalmenteJPanel(); // Recalcula ao redimensionar
            }
        });
        // Listener para o redimensionamento da janela
        this.addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent evt) {
                ajustarImagemDeFundo(); // Ajuste ao redimensionar a janela
            }
        });
        this.setIconImage(new ImageIcon(getClass().getResource("/images/icon.png")).getImage());
        
        ImageIcon icone = new ImageIcon(getClass().getResource("/images/olho-fechado.png"));
        Image imagemRedimensionada = icone.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        jBOlho.setIcon(new ImageIcon(imagemRedimensionada));
        jBOlhoCarro.setIcon(new ImageIcon(imagemRedimensionada));
        jBOlhoCarroExtra.setIcon(new ImageIcon(imagemRedimensionada));
        
        jBAddRespOutro.setEnabled(false);
        jBAddRespOutro.setVisible(false);
        jTFRespOutro.setEnabled(false);
        jTFRespOutro.setVisible(false);
        jCBCarroExtra.setEnabled(false);
        jCBCarroExtra.setVisible(false);
        jCBCarroExtra.removeAllItems();
        jBOlhoCarroExtra.setVisible(false);
        jLCarro1.setVisible(false);
        dataSalva = jDCDataProgramacao.getDate() == null ? "" : new SimpleDateFormat("dd/MM/yyyy").format(jDCDataProgramacao.getDate());
        
        DecimalFormatSymbols symbols = DecimalFormatSymbols.getInstance(Locale.US);
        symbols.setGroupingSeparator('\0'); // Remove agrupamento de milhares

        // Define o formato para aceitar até 4 dígitos inteiros e até 2 dígitos decimais opcionalmente
        DecimalFormat decimalFormat = new DecimalFormat("####.##", symbols);
        decimalFormat.setMinimumIntegerDigits(0);
        decimalFormat.setMaximumIntegerDigits(4);
        decimalFormat.setMinimumFractionDigits(1);
        decimalFormat.setMaximumFractionDigits(2);

        NumberFormatter numberFormatter = new NumberFormatter(decimalFormat);
        numberFormatter.setAllowsInvalid(false); // Desabilita entradas inválidas
        numberFormatter.setValueClass(Double.class); // Classe do valor
        numberFormatter.setMinimum(0.00); // Valor mínimo
        numberFormatter.setMaximum(9999.99);
        jFTFNumero.setFormatterFactory(new DefaultFormatterFactory(numberFormatter));  // Máximo de 2 dígitos decimais

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

            if (!usuarios.isEmpty()) {
                ultimoIdUser = usuarios.getLast().getId();
                List<String> itensArray = listaDisponivelDoDia(jDCDataProgramacao);
//                String[] itensArray = usuarios.stream().map(Usuario::getNomeDeGuerra).sorted().toArray(String[]::new);
                for (String item : itensArray) {
                    listModel.addElement(item);
                }
                jLColab.setModel(listModel);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Erro no arquivo usuarios.txt, talvez ele ainda esteja vazio!");
        }

        try (BufferedReader br = new BufferedReader(new FileReader("carros.txt"))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                linhasArquivoCarro.add(linha);
            }

            linhasArquivoCarro.forEach(elemento -> {
                String[] dados = elemento.split("\\|");
                int qtdArray = dados.length;
                carros.add(new Carro(
                        Integer.parseInt(dados[0].trim()),
                        dados[1].trim(),
                        (qtdArray >= 3 ? dados[2].trim() : null),
                        (qtdArray >= 4 ? dados[3].trim() : null),
                        (qtdArray >= 5 ? dados[4].trim() : null),
                        (qtdArray >= 6 && dados[5].trim() != null && !dados[5].trim().isEmpty() ? Integer.parseInt(dados[5].trim()) : null),
                        (qtdArray >= 7 ? dados[6].trim() : null),
                        (qtdArray >= 8 && dados[7].trim() != null && !dados[7].trim().isEmpty() ? Integer.parseInt(dados[7].trim()) : null),
                        (qtdArray >= 9 ? dados[8].trim() : null),
                        (qtdArray >= 10 ? Integer.parseInt(dados[9].trim()) : null),
                        (qtdArray >= 11 && dados[10].trim() != null && !dados[5].trim().isEmpty() ? Integer.parseInt(dados[5].trim()) : 0),
                        (qtdArray >= 12 ? dados[11].trim() : null)
                ));
            });

            if (!carros.isEmpty()) {
                ultimoIdCarro = carros.getLast().getId();
                List<String> itensArray = listaDeCarrosDisponivelDoDia(jDCDataProgramacao);
                
                for (String item : itensArray) {
                    jCBCarro.addItem(item);
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Erro no arquivo carros.txt, talvez ele ainda esteja vazio!");
        }

        try (BufferedReader br = new BufferedReader(new FileReader("clientes.txt"))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                linhasArquivoCliente.add(linha);
            }

            linhasArquivoCliente.forEach(elemento -> {
                String[] dados = elemento.split("\\|");
                int qtdArray = dados.length;
                clientes.add(new Cliente(
                        Integer.parseInt(dados[0].trim()),
                        dados[1].trim(),
                        dados[2].trim(),
                        Estado.valueOf(dados[3].trim()),
                        (qtdArray >= 5? Double.parseDouble(dados[4].trim()) : null)
                ));

            });

            if (!clientes.isEmpty()) {
                ultimoIdCliente = clientes.getLast().getId();
                 String[] itensArray = clientes.stream().map(Cliente::getNome).sorted().toArray(String[]::new);
                for (String item : itensArray) {
                    jCBCliente.addItem(item);
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Erro no arquivo clientes.txt, talvez ele ainda esteja vazio!");
        }

        try (BufferedReader br = new BufferedReader(new FileReader("hoteis.txt"))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                linhasArquivoHotel.add(linha);
            }

            linhasArquivoHotel.forEach(elemento -> {
                String[] dados = elemento.split("\\|");
                int qtdArray = dados.length;
                hoteis.add(new Hotel(
                        Integer.parseInt(dados[0].trim()),
                        dados[1].trim(),
                        dados[2].trim(),
                        Estado.valueOf(dados[3].trim()),
                        (qtdArray >= 5 ? dados[4].trim() : null)
                ));

            });

            if (!hoteis.isEmpty()) {
                ultimoIdHotel = hoteis.getLast().getId();
                hoteis.forEach(x -> {
                    jCBHospedagem.addItem(x.getNomeComCidadeEEstado());
                });
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Erro no arquivo hoteis.txt, talvez ele ainda esteja vazio!");
        }

        try (BufferedReader br = new BufferedReader(new FileReader("blocos.txt"))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                linhasArquivoBlocoProgramacao.add(linha);
            }

            linhasArquivoBlocoProgramacao.forEach(elemento -> {
                String[] dados = elemento.split("\\|");

                if (dados.length >= 6) {
                    String[] dados4Array = dados[5].trim().replace("[", "").replace("]", "").split(",");
                    Usuario usuarioHelper = new Usuario();
                    List<Usuario> listaDaEquipe = Arrays.stream(dados4Array)
                            .map(idStr -> Integer.valueOf(idStr)) // Converte cada String para int
                            .map(id -> usuarioHelper.getById(id, usuarios)) // Busca o nome do usuário pelo ID
                            .collect(Collectors.toList());
                    int qtdArray = dados.length;
                    blocos.add(new Bloco(
                            Integer.parseInt(dados[0].trim()),
                            dados[1].trim(), //data
                            dados[2].trim(), //proj
                            dados[3].trim().equals("null") ? null : new Cliente().getById(Integer.parseInt(dados[3].trim()), clientes), //client conv
                            dados[4].trim(), // finalidade
                            listaDaEquipe, //equipe
                            dados[6].trim().equals("null") ? null : usuarioHelper.getById(Integer.parseInt(dados[6].trim()), usuarios), //user resp
                            dados[7].trim().equals("null") ? null : new Carro().getById(Integer.parseInt(dados[7].trim()), carros), //carro
                            dados[8].trim(), //carretao
                            dados[9].trim(), //data
                            dados[10].trim(), //data
                            dados[11].trim(), //hora
                            dados[12].trim(), //hora
                            dados[13].trim(), //hora
                            dados[14].trim(), //hora
                            dados[15].trim(), //hora
                            dados[16].trim(), //alm
                            dados[17].trim(), //jan
                            dados[18].trim().equals("null") ? null : new Hotel().getById(Integer.parseInt(dados[18].trim()), hoteis), //hotel
                            (qtdArray >= 20 ? dados[19].trim() : null),
                            (qtdArray >= 21 ? dados[20].trim() : null)
                    ));
                }
            });

            if (!blocos.isEmpty()) {
                ultimoIdBloco = blocos.getLast().getId();
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Erro no arquivo blocos.txt, talvez ele ainda esteja vazio!");
        }
        atualizarDemaisInfos();
        //manutencao = new ManutencaoGPSView(carros);
        //manutencao.executa();
        agendarChamada();
        //gerenciamentoHorasEKms = new GerenciamentoHorasEKms(carros, blocos, clientes);
    }
    
    private void agendarChamada() {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        // Agendar a execução da função a cada 30 segundos
        scheduler.scheduleAtFixedRate(() -> {
            if(manutencao.getQtdAletasVermelho(carros) > 0){
                jLbAlertVermelho.setText("GPS - Atenção, faça troca de óleo!");
                jLbAlertVermelho.setVisible(true);
                jLbVermelho.setVisible(true);
            } else {
                jLbAlertVermelho.setVisible(false);
                jLbVermelho.setVisible(false);
            }
            
            if( manutencao.getQtdAletasLaranja(carros) > 0) {
                jLbAlertLaranja.setText("GPS - Revise o óleo dos carros!");
                jLbAlertLaranja.setVisible(true);
                jLbAlertaLaranja.setVisible(true);
            } else {
                jLbAlertaLaranja.setVisible(false);
                jLbAlertLaranja.setVisible(false);
            }
            
            System.out.println("Vermelho: "+manutencao.getQtdAletasVermelho(carros));
            System.out.println("Laranja: "+manutencao.getQtdAletasLaranja(carros));
        }, 0, 30, TimeUnit.SECONDS);
    }
    
    private void maximizarJanela() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Insets screenInsets = Toolkit.getDefaultToolkit().getScreenInsets(
            GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration()
        );

        // Calcula a largura e altura disponíveis (sem a barra de tarefas)
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height - screenInsets.bottom;

        // Ajusta o tamanho do JFrame
        this.setSize(screenWidth, screenHeight);
        this.setLocation(0, 0); // Posiciona no canto superior esquerdo
    }
    
    private void alinharHorizontalmenteJPanel() {
        
            int frameWidth = this.getContentPane().getWidth();

            int panelWidth = jPFundo.getWidth();
            int currentY = jPFundo.getY(); // Mantém a posição vertical atual

            // Calcula a posição horizontal para centralizar
            int x = (frameWidth - panelWidth) / 2;

            jPFundo.setBounds(x, currentY, panelWidth, jPFundo.getHeight()); // Atualiza apenas a posição horizontal
            jPFundo.revalidate();
            jPFundo.repaint();  
    }

    private void ajustarImagemDeFundo() {
        if (this.getWidth() > 0 && this.getHeight() > 0) {
            // Obtém o ícone configurado manualmente
            ImageIcon originalIcon = (ImageIcon) jLFundo.getIcon();
            if (originalIcon == null) {
                System.out.println("Erro: Nenhum ícone configurado no JLabel.");
                return;
            }

            // Redimensiona a imagem conforme o tamanho da janela
            Image scaledImage = originalIcon.getImage().getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_SMOOTH);

            // Atualiza o ícone do JLabel
            jLFundo.setIcon(new ImageIcon(scaledImage));

            // Ajusta o tamanho do JLabel para cobrir toda a área do JFrame
            jLFundo.setBounds(0, 0, this.getWidth(), this.getHeight());
            jLFundo.repaint();
        }
    }
    
    private void atualizarDemaisInfos() {
        try (BufferedReader br = new BufferedReader(new FileReader("demaisInfos.txt"))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                linhasArquivoDemaisInfos.add(linha);
            }

            linhasArquivoDemaisInfos.forEach(elemento -> {
                String[] dados = elemento.split("\\|");
                Usuario usuarioHelper = new Usuario();

                int qtdArray = dados.length;
                if (qtdArray > 1) {
                    demaisInfos.add(new DemaisInfos(
                            Integer.parseInt(dados[0].trim()),
                            dados[1].trim(),
                            (qtdArray >= 3 ? stringToUser(dados[2].trim(), usuarioHelper) : null),
                            (qtdArray >= 4 ? stringToUser(dados[3].trim(), usuarioHelper) : null),
                            (qtdArray >= 5 ? stringToUser(dados[4].trim(), usuarioHelper) : null),
                            (qtdArray >= 6 ? stringToUser(dados[5].trim(), usuarioHelper) : null)
                    ));
                }
            });

            if (!demaisInfos.isEmpty()) {
                ultimoIdDemaisInfos = demaisInfos.getLast().getId();
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Erro no arquivo demaisInfos.txt, talvez ele ainda esteja vazio!");
        }
    }

    private List<Usuario> stringToUser(String dado, Usuario usuarioHelper) {
        if (dado == null || dado.equals("")) {
            return null;
        }
        String[] dadosUsers = dado.replace("[", "").replace("]", "").split(",");
        List<Usuario> users = Arrays.stream(dadosUsers)
                .map(idStr -> Integer.valueOf(idStr)) // Converte cada String para int
                .map(id -> usuarioHelper.getById(id, usuarios)) // Busca o nome do usuário pelo ID
                .collect(Collectors.toList());
        return users;
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLProg = new javax.swing.JLabel();
        jPFundo = new javax.swing.JPanel();
        jLNumero = new javax.swing.JLabel();
        jFTFNumero = new javax.swing.JFormattedTextField();
        jTFAlmoco = new javax.swing.JTextField();
        jLAlmoco = new javax.swing.JLabel();
        jLHospedagem = new javax.swing.JLabel();
        jLCliente = new javax.swing.JLabel();
        jCBHospedagem = new javax.swing.JComboBox<>();
        jLColaboradores = new javax.swing.JLabel();
        jSPColaboradores = new javax.swing.JScrollPane();
        jLColab = new javax.swing.JList<>();
        jLSelecionados = new javax.swing.JLabel();
        jSPSelecionados = new javax.swing.JScrollPane();
        jLSelec = new javax.swing.JList<>();
        jBSub = new javax.swing.JButton();
        jBAdd = new javax.swing.JButton();
        jLResponsavel = new javax.swing.JLabel();
        jLCarro = new javax.swing.JLabel();
        jCBCarro = new javax.swing.JComboBox<>();
        jBAddBloco = new javax.swing.JButton();
        jBAlterarBloco = new javax.swing.JButton();
        jBRemover = new javax.swing.JButton();
        jBLimpar = new javax.swing.JButton();
        jLDataSaida = new javax.swing.JLabel();
        jLDataRetorno = new javax.swing.JLabel();
        jLHoraSaida = new javax.swing.JLabel();
        jFTFHoraSaida = new javax.swing.JFormattedTextField();
        jPanel1 = new javax.swing.JPanel();
        jLHoraInicioManha = new javax.swing.JLabel();
        jLHorarioDeTrabalho = new javax.swing.JLabel();
        jLHoraFimManha = new javax.swing.JLabel();
        jLHoraInicioTarde = new javax.swing.JLabel();
        jLHoraFimTarde = new javax.swing.JLabel();
        jFTFHoraInicioTarde = new javax.swing.JFormattedTextField();
        jFTFHoraInicioManha = new javax.swing.JFormattedTextField();
        jFTFHoraFimTarde = new javax.swing.JFormattedTextField();
        jFTFHoraFimManha = new javax.swing.JFormattedTextField();
        jLJanta = new javax.swing.JLabel();
        jTFJanta = new javax.swing.JTextField();
        jCBCliente = new javax.swing.JComboBox<>();
        jDCDataRetorno = new com.toedter.calendar.JDateChooser();
        jDCDataSaida = new com.toedter.calendar.JDateChooser();
        jDCDataProgramacao = new com.toedter.calendar.JDateChooser();
        jLProgramDia = new javax.swing.JLabel();
        jCBFinalidade = new javax.swing.JComboBox<>();
        jLFinalidade = new javax.swing.JLabel();
        jCBCarretao = new javax.swing.JComboBox<>();
        jLCarretao = new javax.swing.JLabel();
        jBDemaisInfos = new javax.swing.JButton();
        jBBuscar = new javax.swing.JButton();
        jLObservacoes = new javax.swing.JLabel();
        jTFObservacoes = new javax.swing.JTextField();
        jCBResponsavel = new javax.swing.JComboBox<>();
        jTFRespOutro = new javax.swing.JTextField();
        jBAddRespOutro = new javax.swing.JButton();
        jBOlho = new javax.swing.JButton();
        jCBRetornaMesmoDia = new javax.swing.JCheckBox();
        jCBCarroExtra = new javax.swing.JComboBox<>();
        jLbAlertaLaranja = new javax.swing.JLabel();
        jLbVermelho = new javax.swing.JLabel();
        jLbAlertVermelho = new javax.swing.JLabel();
        jLbAlertLaranja = new javax.swing.JLabel();
        jLCarro1 = new javax.swing.JLabel();
        jBOlhoCarro = new javax.swing.JButton();
        jBOlhoCarroExtra = new javax.swing.JButton();
        jLFundo = new javax.swing.JLabel();
        jMenuBar = new javax.swing.JMenuBar();
        jMCadastros = new javax.swing.JMenu();
        jMIUsers = new javax.swing.JMenuItem();
        jMICarros = new javax.swing.JMenuItem();
        jMICliente = new javax.swing.JMenuItem();
        jMIHoteis = new javax.swing.JMenuItem();
        jMProgramacoes = new javax.swing.JMenu();
        jMIListaProgramacoes = new javax.swing.JMenuItem();
        jMIFolgasInternos = new javax.swing.JMenuItem();
        Manutencao = new javax.swing.JMenu();
        jMenuItemManutencao = new javax.swing.JMenuItem();
        jMIAcViajensLancadas = new javax.swing.JMenuItem();
        jMGerenciamento = new javax.swing.JMenu();
        jMIHrsKM = new javax.swing.JMenuItem();
        jMSobre = new javax.swing.JMenu();
        jMIVerDetalhes = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        getContentPane().setLayout(null);

        jLProg.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLProg.setForeground(new java.awt.Color(255, 255, 255));
        jLProg.setText("Adicionar um item para a Programação");
        getContentPane().add(jLProg);
        jLProg.setBounds(440, 10, 350, 25);

        jPFundo.setBackground(new java.awt.Color(7, 30, 74));
        jPFundo.setToolTipText("");
        jPFundo.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jPFundo.setFocusable(false);
        jPFundo.setName(""); // NOI18N
        jPFundo.setLayout(null);

        jLNumero.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        jLNumero.setForeground(new java.awt.Color(255, 255, 255));
        jLNumero.setText("Número do Projeto:");
        jLNumero.setToolTipText("");
        jPFundo.add(jLNumero);
        jLNumero.setBounds(730, 20, 110, 14);

        jFTFNumero.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("####.##"))));
        jFTFNumero.setToolTipText("Informe o número do projeto");
        jFTFNumero.setNextFocusableComponent(jCBCliente);
        jFTFNumero.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jFTFNumeroFocusGained(evt);
            }
        });
        jPFundo.add(jFTFNumero);
        jFTFNumero.setBounds(730, 40, 170, 30);

        jTFAlmoco.setToolTipText("Se houver, informe o local de almoço!");
        jTFAlmoco.setNextFocusableComponent(jTFJanta);
        jPFundo.add(jTFAlmoco);
        jTFAlmoco.setBounds(500, 300, 500, 30);

        jLAlmoco.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        jLAlmoco.setForeground(new java.awt.Color(255, 255, 255));
        jLAlmoco.setText("Local de almoço:");
        jLAlmoco.setToolTipText("");
        jPFundo.add(jLAlmoco);
        jLAlmoco.setBounds(410, 310, 80, 16);

        jLHospedagem.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        jLHospedagem.setForeground(new java.awt.Color(255, 255, 255));
        jLHospedagem.setText("Hospedagem: ");
        jLHospedagem.setToolTipText("");
        jPFundo.add(jLHospedagem);
        jLHospedagem.setBounds(410, 270, 90, 16);

        jLCliente.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        jLCliente.setForeground(new java.awt.Color(255, 255, 255));
        jLCliente.setText("Cliente:");
        jLCliente.setToolTipText("");
        jPFundo.add(jLCliente);
        jLCliente.setBounds(610, 90, 110, 14);

        jCBHospedagem.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "N/A" }));
        jCBHospedagem.setToolTipText("Selecione o Hotel, caso precise");
        jCBHospedagem.setBorder(null);
        jCBHospedagem.setNextFocusableComponent(jTFAlmoco);
        jCBHospedagem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCBHospedagemActionPerformed(evt);
            }
        });
        jPFundo.add(jCBHospedagem);
        jCBHospedagem.setBounds(500, 260, 500, 30);

        jLColaboradores.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        jLColaboradores.setForeground(new java.awt.Color(255, 255, 255));
        jLColaboradores.setText("Nome do Colaborador:");
        jLColaboradores.setToolTipText("");
        jPFundo.add(jLColaboradores);
        jLColaboradores.setBounds(160, 90, 130, 14);

        jLColab.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jLColab.setToolTipText("");
        jSPColaboradores.setViewportView(jLColab);

        jPFundo.add(jSPColaboradores);
        jSPColaboradores.setBounds(160, 110, 180, 310);

        jLSelecionados.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        jLSelecionados.setForeground(new java.awt.Color(255, 255, 255));
        jLSelecionados.setText("Colaboradores Escolhidos:");
        jLSelecionados.setToolTipText("");
        jPFundo.add(jLSelecionados);
        jLSelecionados.setBounds(410, 90, 160, 14);

        jLSelec.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jLSelec.setToolTipText("");
        jSPSelecionados.setViewportView(jLSelec);

        jPFundo.add(jSPSelecionados);
        jSPSelecionados.setBounds(410, 110, 170, 120);

        jBSub.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        jBSub.setText("<<");
        jBSub.setToolTipText("Remove o colaborado");
        jBSub.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jBSub.setBorderPainted(false);
        jBSub.setFocusable(false);
        jBSub.setRequestFocusEnabled(false);
        jBSub.setRolloverEnabled(false);
        jBSub.setVerifyInputWhenFocusTarget(false);
        jBSub.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBSubActionPerformed(evt);
            }
        });
        jPFundo.add(jBSub);
        jBSub.setBounds(350, 180, 50, 30);

        jBAdd.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        jBAdd.setText(">>");
        jBAdd.setToolTipText("Adiciona o colaborado");
        jBAdd.setBorder(javax.swing.BorderFactory.createCompoundBorder());
        jBAdd.setFocusable(false);
        jBAdd.setRequestFocusEnabled(false);
        jBAdd.setRolloverEnabled(false);
        jBAdd.setVerifyInputWhenFocusTarget(false);
        jBAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBAddActionPerformed(evt);
            }
        });
        jPFundo.add(jBAdd);
        jBAdd.setBounds(350, 130, 50, 30);

        jLResponsavel.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        jLResponsavel.setForeground(new java.awt.Color(255, 255, 255));
        jLResponsavel.setText("Selecione o Responsável da Obra:");
        jLResponsavel.setToolTipText("");
        jPFundo.add(jLResponsavel);
        jLResponsavel.setBounds(610, 150, 160, 14);

        jLCarro.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        jLCarro.setForeground(new java.awt.Color(255, 255, 255));
        jLCarro.setText("Selecione o Carro:");
        jLCarro.setToolTipText("");
        jPFundo.add(jLCarro);
        jLCarro.setBounds(820, 90, 130, 14);

        jCBCarro.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "N/A" }));
        jCBCarro.setToolTipText("Selecione o carro, caso precise");
        jCBCarro.setNextFocusableComponent(jCBCarretao);
        jCBCarro.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jCBCarroMouseClicked(evt);
            }
        });
        jPFundo.add(jCBCarro);
        jCBCarro.setBounds(820, 110, 180, 30);

        jBAddBloco.setBackground(new java.awt.Color(204, 204, 204));
        jBAddBloco.setText("Criar");
        jBAddBloco.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jBAddBloco.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBAddBlocoActionPerformed(evt);
            }
        });
        jPFundo.add(jBAddBloco);
        jBAddBloco.setBounds(240, 540, 110, 30);

        jBAlterarBloco.setBackground(new java.awt.Color(204, 204, 204));
        jBAlterarBloco.setText("Alterar");
        jBAlterarBloco.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jBAlterarBloco.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBAlterarBlocoActionPerformed(evt);
            }
        });
        jPFundo.add(jBAlterarBloco);
        jBAlterarBloco.setBounds(370, 540, 110, 30);

        jBRemover.setBackground(new java.awt.Color(204, 204, 204));
        jBRemover.setText("Remover");
        jBRemover.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jBRemover.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBRemoverActionPerformed(evt);
            }
        });
        jPFundo.add(jBRemover);
        jBRemover.setBounds(500, 540, 110, 30);

        jBLimpar.setBackground(new java.awt.Color(204, 204, 204));
        jBLimpar.setText("❌ Limpar Campos");
        jBLimpar.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jBLimpar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBLimparActionPerformed(evt);
            }
        });
        jPFundo.add(jBLimpar);
        jBLimpar.setBounds(630, 540, 130, 30);

        jLDataSaida.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        jLDataSaida.setForeground(new java.awt.Color(255, 255, 255));
        jLDataSaida.setText("Data de Saída:");
        jLDataSaida.setToolTipText("");
        jPFundo.add(jLDataSaida);
        jLDataSaida.setBounds(250, 450, 90, 16);

        jLDataRetorno.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        jLDataRetorno.setForeground(new java.awt.Color(255, 255, 255));
        jLDataRetorno.setText("Data de Retorno: ");
        jLDataRetorno.setToolTipText("");
        jPFundo.add(jLDataRetorno);
        jLDataRetorno.setBounds(250, 490, 110, 16);

        jLHoraSaida.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        jLHoraSaida.setForeground(new java.awt.Color(255, 255, 255));
        jLHoraSaida.setText("Hora de Saída:");
        jLHoraSaida.setToolTipText("");
        jPFundo.add(jLHoraSaida);
        jLHoraSaida.setBounds(490, 450, 90, 16);

        jFTFHoraSaida.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(java.text.DateFormat.getTimeInstance(java.text.DateFormat.SHORT))));
        jFTFHoraSaida.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jFTFHoraSaida.setToolTipText("Hora:Minuto");
        jFTFHoraSaida.setName(""); // NOI18N
        jFTFHoraSaida.setNextFocusableComponent(jPanel1);
        jFTFHoraSaida.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jFTFHoraSaidaFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jFTFHoraSaidaFocusLost(evt);
            }
        });
        jFTFHoraSaida.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
            }
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
                jFTFHoraSaidaInputMethodTextChanged(evt);
            }
        });
        jFTFHoraSaida.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jFTFHoraSaidaActionPerformed(evt);
            }
        });
        jPFundo.add(jFTFHoraSaida);
        jFTFHoraSaida.setBounds(570, 440, 50, 30);

        jPanel1.setBackground(new java.awt.Color(0, 22, 90));
        jPanel1.setToolTipText("Defina o horário de trabalho, se for o caso.");
        jPanel1.setLayout(null);

        jLHoraInicioManha.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        jLHoraInicioManha.setForeground(new java.awt.Color(255, 255, 255));
        jLHoraInicioManha.setText("Início - Manhã:");
        jLHoraInicioManha.setToolTipText("");
        jPanel1.add(jLHoraInicioManha);
        jLHoraInicioManha.setBounds(10, 30, 90, 14);

        jLHorarioDeTrabalho.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        jLHorarioDeTrabalho.setForeground(new java.awt.Color(255, 255, 255));
        jLHorarioDeTrabalho.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLHorarioDeTrabalho.setText("Horário de Trabalho");
        jLHorarioDeTrabalho.setToolTipText("");
        jPanel1.add(jLHorarioDeTrabalho);
        jLHorarioDeTrabalho.setBounds(80, 0, 140, 16);

        jLHoraFimManha.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        jLHoraFimManha.setForeground(new java.awt.Color(255, 255, 255));
        jLHoraFimManha.setText("Fim - Manhã:");
        jLHoraFimManha.setToolTipText("");
        jPanel1.add(jLHoraFimManha);
        jLHoraFimManha.setBounds(160, 30, 70, 16);

        jLHoraInicioTarde.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        jLHoraInicioTarde.setForeground(new java.awt.Color(255, 255, 255));
        jLHoraInicioTarde.setText(" Início - Tarde: ");
        jLHoraInicioTarde.setToolTipText("");
        jPanel1.add(jLHoraInicioTarde);
        jLHoraInicioTarde.setBounds(10, 70, 90, 16);

        jLHoraFimTarde.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        jLHoraFimTarde.setForeground(new java.awt.Color(255, 255, 255));
        jLHoraFimTarde.setText("  Fim - Tarde:");
        jLHoraFimTarde.setToolTipText("");
        jPanel1.add(jLHoraFimTarde);
        jLHoraFimTarde.setBounds(160, 70, 80, 16);

        jFTFHoraInicioTarde.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(java.text.DateFormat.getTimeInstance(java.text.DateFormat.SHORT))));
        jFTFHoraInicioTarde.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jFTFHoraInicioTarde.setText("13:00");
        jFTFHoraInicioTarde.setToolTipText("Hora:Minuto");
        jFTFHoraInicioTarde.setName(""); // NOI18N
        jFTFHoraInicioTarde.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jFTFHoraInicioTardeFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jFTFHoraInicioTardeFocusLost(evt);
            }
        });
        jFTFHoraInicioTarde.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
            }
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
                jFTFHoraInicioTardeInputMethodTextChanged(evt);
            }
        });
        jFTFHoraInicioTarde.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jFTFHoraInicioTardeActionPerformed(evt);
            }
        });
        jPanel1.add(jFTFHoraInicioTarde);
        jFTFHoraInicioTarde.setBounds(90, 60, 50, 30);

        jFTFHoraInicioManha.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(java.text.DateFormat.getTimeInstance(java.text.DateFormat.SHORT))));
        jFTFHoraInicioManha.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jFTFHoraInicioManha.setText("07:30");
        jFTFHoraInicioManha.setToolTipText("Hora:Minuto");
        jFTFHoraInicioManha.setName(""); // NOI18N
        jFTFHoraInicioManha.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jFTFHoraInicioManhaFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jFTFHoraInicioManhaFocusLost(evt);
            }
        });
        jFTFHoraInicioManha.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
            }
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
                jFTFHoraInicioManhaInputMethodTextChanged(evt);
            }
        });
        jFTFHoraInicioManha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jFTFHoraInicioManhaActionPerformed(evt);
            }
        });
        jPanel1.add(jFTFHoraInicioManha);
        jFTFHoraInicioManha.setBounds(90, 20, 50, 30);

        jFTFHoraFimTarde.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(java.text.DateFormat.getTimeInstance(java.text.DateFormat.SHORT))));
        jFTFHoraFimTarde.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jFTFHoraFimTarde.setText("17:18");
        jFTFHoraFimTarde.setToolTipText("Hora:Minuto");
        jFTFHoraFimTarde.setName(""); // NOI18N
        jFTFHoraFimTarde.setNextFocusableComponent(jBAddBloco);
        jFTFHoraFimTarde.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jFTFHoraFimTardeFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jFTFHoraFimTardeFocusLost(evt);
            }
        });
        jFTFHoraFimTarde.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
            }
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
                jFTFHoraFimTardeInputMethodTextChanged(evt);
            }
        });
        jFTFHoraFimTarde.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jFTFHoraFimTardeActionPerformed(evt);
            }
        });
        jPanel1.add(jFTFHoraFimTarde);
        jFTFHoraFimTarde.setBounds(230, 60, 50, 30);

        jFTFHoraFimManha.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(java.text.DateFormat.getTimeInstance(java.text.DateFormat.SHORT))));
        jFTFHoraFimManha.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jFTFHoraFimManha.setText("12:00");
        jFTFHoraFimManha.setToolTipText("Hora:Minuto");
        jFTFHoraFimManha.setName(""); // NOI18N
        jFTFHoraFimManha.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jFTFHoraFimManhaFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jFTFHoraFimManhaFocusLost(evt);
            }
        });
        jFTFHoraFimManha.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
            }
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
                jFTFHoraFimManhaInputMethodTextChanged(evt);
            }
        });
        jFTFHoraFimManha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jFTFHoraFimManhaActionPerformed(evt);
            }
        });
        jPanel1.add(jFTFHoraFimManha);
        jFTFHoraFimManha.setBounds(230, 20, 50, 30);

        jPFundo.add(jPanel1);
        jPanel1.setBounds(660, 420, 290, 110);

        jLJanta.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        jLJanta.setForeground(new java.awt.Color(255, 255, 255));
        jLJanta.setText("Local de janta:");
        jLJanta.setToolTipText("");
        jPFundo.add(jLJanta);
        jLJanta.setBounds(410, 350, 80, 16);

        jTFJanta.setToolTipText("Se houver, informe o local de almoço!");
        jTFJanta.setNextFocusableComponent(jFTFHoraSaida);
        jPFundo.add(jTFJanta);
        jTFJanta.setBounds(500, 340, 500, 30);

        jCBCliente.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "N/A" }));
        jCBCliente.setToolTipText("Selecione o cliente. Caso não encontre, crie um para cada Fábrica!");
        jCBCliente.setBorder(null);
        jCBCliente.setNextFocusableComponent(jCBResponsavel);
        jPFundo.add(jCBCliente);
        jCBCliente.setBounds(610, 110, 180, 30);

        jDCDataRetorno.setToolTipText("Dia/Mês/Ano");
        jDCDataRetorno.setDate(new java.util.Date(new java.util.Date().getTime() + 86400000L));
        jDCDataRetorno.setDateFormatString("dd/MM/yyyy");
        jDCDataRetorno.setNextFocusableComponent(jFTFHoraSaida);
        jPFundo.add(jDCDataRetorno);
        jDCDataRetorno.setBounds(340, 480, 130, 30);

        jDCDataSaida.setToolTipText("Dia/Mês/Ano");
        jDCDataSaida.setDate(new java.util.Date(new java.util.Date().getTime() + 86400000L));
        jDCDataSaida.setDateFormatString("dd/MM/yyyy");
        jDCDataSaida.setNextFocusableComponent(jDCDataRetorno);
        jPFundo.add(jDCDataSaida);
        jDCDataSaida.setBounds(340, 440, 130, 30);

        jDCDataProgramacao.setToolTipText("Dia/Mês/Ano");
        jDCDataProgramacao.setDate(new java.util.Date(new java.util.Date().getTime() + 86400000L));
        jDCDataProgramacao.setDateFormatString("dd/MM/yyyy");
        jDCDataProgramacao.setNextFocusableComponent(jCBFinalidade);
        jDCDataProgramacao.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jDCDataProgramacaoPropertyChange(evt);
            }
        });
        jPFundo.add(jDCDataProgramacao);
        jDCDataProgramacao.setBounds(160, 40, 180, 30);

        jLProgramDia.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        jLProgramDia.setForeground(new java.awt.Color(255, 255, 255));
        jLProgramDia.setText("Adicionar para a programação do dia:");
        jLProgramDia.setToolTipText("");
        jPFundo.add(jLProgramDia);
        jLProgramDia.setBounds(160, 20, 210, 14);

        jCBFinalidade.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "SELECIONE", "OCULTAR", "H.H.", "ADEQUAÇÃO NR-12", "ELÉTRICA", "MECÂNICA", "LEVANTAMENTO TÉCNICO", "SPDA", "PASSAGEM DE TRABALHO", "VISITA COMERCIAL", "VISITA TÉCNICA" }));
        jCBFinalidade.setToolTipText("Selecione a finalidade do bloco");
        jCBFinalidade.setNextFocusableComponent(jFTFNumero);
        jPFundo.add(jCBFinalidade);
        jCBFinalidade.setBounds(410, 40, 250, 30);

        jLFinalidade.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        jLFinalidade.setForeground(new java.awt.Color(255, 255, 255));
        jLFinalidade.setText("Finalidade:");
        jLFinalidade.setToolTipText("");
        jPFundo.add(jLFinalidade);
        jLFinalidade.setBounds(410, 20, 110, 14);

        jCBCarretao.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "N/A", "CARRETÃO GRANDE", "CARRETÃO PEQUENO" }));
        jCBCarretao.setToolTipText("Selecione o carretão, caso precise");
        jCBCarretao.setNextFocusableComponent(jCBHospedagem);
        jCBCarretao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCBCarretaoActionPerformed(evt);
            }
        });
        jPFundo.add(jCBCarretao);
        jCBCarretao.setBounds(820, 170, 180, 30);

        jLCarretao.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        jLCarretao.setForeground(new java.awt.Color(255, 255, 255));
        jLCarretao.setText("Selecione o Carretão:");
        jLCarretao.setToolTipText("");
        jPFundo.add(jLCarretao);
        jLCarretao.setBounds(820, 150, 160, 14);

        jBDemaisInfos.setBackground(new java.awt.Color(204, 204, 204));
        jBDemaisInfos.setText("Adicionar Folgas e Internos");
        jBDemaisInfos.setToolTipText("Definir Folga, Férias e Internos");
        jBDemaisInfos.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jBDemaisInfos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBDemaisInfosActionPerformed(evt);
            }
        });
        jPFundo.add(jBDemaisInfos);
        jBDemaisInfos.setBounds(780, 540, 170, 30);

        jBBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/lupa.png"))); // NOI18N
        jBBuscar.setToolTipText("Clique aqui para procurar!");
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
        jPFundo.add(jBBuscar);
        jBBuscar.setBounds(950, 20, 50, 50);

        jLObservacoes.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        jLObservacoes.setForeground(new java.awt.Color(255, 255, 255));
        jLObservacoes.setText("Observações:");
        jLObservacoes.setToolTipText("");
        jPFundo.add(jLObservacoes);
        jLObservacoes.setBounds(410, 390, 80, 16);

        jTFObservacoes.setToolTipText("Se houver, informe o local de almoço!");
        jTFObservacoes.setNextFocusableComponent(jFTFHoraSaida);
        jPFundo.add(jTFObservacoes);
        jTFObservacoes.setBounds(500, 380, 500, 30);

        jCBResponsavel.setToolTipText("Selecione o Responsável dentre os que vão para Obra!");
        jCBResponsavel.setNextFocusableComponent(jCBCarro);
        jCBResponsavel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCBResponsavelActionPerformed(evt);
            }
        });
        jPFundo.add(jCBResponsavel);
        jCBResponsavel.setBounds(610, 170, 180, 30);

        jTFRespOutro.setEditable(false);
        jTFRespOutro.setBackground(new java.awt.Color(204, 204, 204));
        jTFRespOutro.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTFRespOutro.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jTFRespOutro.setFocusable(false);
        jTFRespOutro.setRequestFocusEnabled(false);
        jPFundo.add(jTFRespOutro);
        jTFRespOutro.setBounds(650, 210, 140, 20);

        jBAddRespOutro.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        jBAddRespOutro.setText(">>");
        jBAddRespOutro.setToolTipText("Adiciona o colaborado");
        jBAddRespOutro.setBorder(javax.swing.BorderFactory.createCompoundBorder());
        jBAddRespOutro.setFocusPainted(false);
        jBAddRespOutro.setFocusable(false);
        jBAddRespOutro.setRequestFocusEnabled(false);
        jBAddRespOutro.setRolloverEnabled(false);
        jBAddRespOutro.setVerifyInputWhenFocusTarget(false);
        jBAddRespOutro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBAddRespOutroActionPerformed(evt);
            }
        });
        jPFundo.add(jBAddRespOutro);
        jBAddRespOutro.setBounds(610, 210, 30, 20);

        jBOlho.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        jBOlho.setToolTipText("Ver todos colaboradores");
        jBOlho.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jBOlho.setBorderPainted(false);
        jBOlho.setFocusable(false);
        jBOlho.setRequestFocusEnabled(false);
        jBOlho.setRolloverEnabled(false);
        jBOlho.setVerifyInputWhenFocusTarget(false);
        jBOlho.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBOlhoActionPerformed(evt);
            }
        });
        jPFundo.add(jBOlho);
        jBOlho.setBounds(350, 280, 50, 30);

        jCBRetornaMesmoDia.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jCBRetornaMesmoDia.setForeground(new java.awt.Color(255, 255, 255));
        jCBRetornaMesmoDia.setSelected(true);
        jCBRetornaMesmoDia.setText(" Retornam no mesmo dia");
        jPFundo.add(jCBRetornaMesmoDia);
        jCBRetornaMesmoDia.setBounds(480, 480, 170, 30);

        jCBCarroExtra.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jCBCarroExtraMouseClicked(evt);
            }
        });
        jCBCarroExtra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCBCarroExtraActionPerformed(evt);
            }
        });
        jPFundo.add(jCBCarroExtra);
        jCBCarroExtra.setBounds(820, 220, 180, 30);

        jLbAlertaLaranja.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/laranja.png"))); // NOI18N
        jLbAlertaLaranja.setMaximumSize(new java.awt.Dimension(50, 50));
        jLbAlertaLaranja.setMinimumSize(new java.awt.Dimension(50, 50));
        jLbAlertaLaranja.setPreferredSize(new java.awt.Dimension(50, 50));
        jLbAlertaLaranja.setRequestFocusEnabled(false);
        jPFundo.add(jLbAlertaLaranja);
        jLbAlertaLaranja.setBounds(1070, 120, 40, 50);

        jLbVermelho.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/vermelho.png"))); // NOI18N
        jPFundo.add(jLbVermelho);
        jLbVermelho.setBounds(1070, 40, 40, 50);

        jLbAlertVermelho.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLbAlertVermelho.setForeground(new java.awt.Color(255, 102, 0));
        jLbAlertVermelho.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLbAlertVermelho.setText("  ");
        jPFundo.add(jLbAlertVermelho);
        jLbAlertVermelho.setBounds(970, 90, 210, 16);

        jLbAlertLaranja.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLbAlertLaranja.setForeground(new java.awt.Color(255, 102, 0));
        jLbAlertLaranja.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLbAlertLaranja.setText("  ");
        jPFundo.add(jLbAlertLaranja);
        jLbAlertLaranja.setBounds(1000, 170, 180, 16);

        jLCarro1.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        jLCarro1.setForeground(new java.awt.Color(255, 255, 255));
        jLCarro1.setText("Selecione o Carro Extra:");
        jLCarro1.setToolTipText("");
        jPFundo.add(jLCarro1);
        jLCarro1.setBounds(820, 200, 160, 14);

        jBOlhoCarro.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        jBOlhoCarro.setToolTipText("Ver todos carros");
        jBOlhoCarro.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jBOlhoCarro.setBorderPainted(false);
        jBOlhoCarro.setFocusable(false);
        jBOlhoCarro.setRequestFocusEnabled(false);
        jBOlhoCarro.setRolloverEnabled(false);
        jBOlhoCarro.setVerifyInputWhenFocusTarget(false);
        jBOlhoCarro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBOlhoCarroActionPerformed(evt);
            }
        });
        jPFundo.add(jBOlhoCarro);
        jBOlhoCarro.setBounds(960, 90, 40, 20);

        jBOlhoCarroExtra.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        jBOlhoCarroExtra.setToolTipText("Ver todos carros");
        jBOlhoCarroExtra.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jBOlhoCarroExtra.setBorderPainted(false);
        jBOlhoCarroExtra.setFocusable(false);
        jBOlhoCarroExtra.setRequestFocusEnabled(false);
        jBOlhoCarroExtra.setRolloverEnabled(false);
        jBOlhoCarroExtra.setVerifyInputWhenFocusTarget(false);
        jBOlhoCarroExtra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBOlhoCarroExtraActionPerformed(evt);
            }
        });
        jPFundo.add(jBOlhoCarroExtra);
        jBOlhoCarroExtra.setBounds(960, 200, 40, 20);

        getContentPane().add(jPFundo);
        jPFundo.setBounds(0, 40, 1180, 580);

        jLFundo.setBackground(new java.awt.Color(51, 51, 51));
        jLFundo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/tela_de_fundo.jpeg"))); // NOI18N
        jLFundo.setToolTipText("");
        getContentPane().add(jLFundo);
        jLFundo.setBounds(0, -200, 1360, 1120);

        jMenuBar.setBackground(new java.awt.Color(153, 153, 153));
        jMenuBar.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jMenuBar.setAutoscrolls(true);
        jMenuBar.setBorderPainted(false);
        jMenuBar.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jMenuBar.setPreferredSize(new java.awt.Dimension(70, 50));

        jMCadastros.setText("Cadastros");
        jMCadastros.setAutoscrolls(true);
        jMCadastros.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMCadastros.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMCadastrosActionPerformed(evt);
            }
        });

        jMIUsers.setText("Cadastro de Colaboradores");
        jMIUsers.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMIUsersActionPerformed(evt);
            }
        });
        jMCadastros.add(jMIUsers);

        jMICarros.setText("Cadastro de Carros");
        jMICarros.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMICarrosActionPerformed(evt);
            }
        });
        jMCadastros.add(jMICarros);

        jMICliente.setText("Cadastro de Clientes");
        jMICliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMIClienteActionPerformed(evt);
            }
        });
        jMCadastros.add(jMICliente);

        jMIHoteis.setText("Cadastro de Hoteis");
        jMIHoteis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMIHoteisActionPerformed(evt);
            }
        });
        jMCadastros.add(jMIHoteis);

        jMenuBar.add(jMCadastros);

        jMProgramacoes.setText("Programações");

        jMIListaProgramacoes.setText("Ver Programações");
        jMIListaProgramacoes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMIListaProgramacoesActionPerformed(evt);
            }
        });
        jMProgramacoes.add(jMIListaProgramacoes);

        jMIFolgasInternos.setText("Adicionar Folgas, Férias e Internos");
        jMIFolgasInternos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMIFolgasInternosActionPerformed(evt);
            }
        });
        jMProgramacoes.add(jMIFolgasInternos);

        jMenuBar.add(jMProgramacoes);

        Manutencao.setText("Manutenção");
        Manutencao.setToolTipText("");

        jMenuItemManutencao.setText("Acompanhamento Por GPS");
        jMenuItemManutencao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemManutencaoActionPerformed(evt);
            }
        });
        Manutencao.add(jMenuItemManutencao);

        jMIAcViajensLancadas.setText("Acompanhamento Por Viajens Lançadas");
        jMIAcViajensLancadas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMIAcViajensLancadasActionPerformed(evt);
            }
        });
        Manutencao.add(jMIAcViajensLancadas);

        jMenuBar.add(Manutencao);

        jMGerenciamento.setText("Gerenciamento");

        jMIHrsKM.setText("Horas e KM");
        jMIHrsKM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMIHrsKMActionPerformed(evt);
            }
        });
        jMGerenciamento.add(jMIHrsKM);

        jMenuBar.add(jMGerenciamento);

        jMSobre.setText("Sobre");

        jMIVerDetalhes.setText("Ver Detalhes");
        jMIVerDetalhes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMIVerDetalhesActionPerformed(evt);
            }
        });
        jMSobre.add(jMIVerDetalhes);

        jMenuBar.add(jMSobre);

        setJMenuBar(jMenuBar);

        setSize(new java.awt.Dimension(1195, 773));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jMCadastrosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMCadastrosActionPerformed
    }//GEN-LAST:event_jMCadastrosActionPerformed

    private void jMIUsersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMIUsersActionPerformed
        CadastroUsuario cadastroUser = new CadastroUsuario(usuarios);
        cadastroUser.setVisible(true);

        cadastroUser.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                atualizarListas();
            }
        });
    }//GEN-LAST:event_jMIUsersActionPerformed

    private void jMICarrosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMICarrosActionPerformed
        CadastroCarro cadastroCarro = new CadastroCarro(carros);
        cadastroCarro.setVisible(true);

        cadastroCarro.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                atualizarListas();
            }
        });
    }//GEN-LAST:event_jMICarrosActionPerformed

    private void jMIClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMIClienteActionPerformed
        CadastroCliente cadastroCliente = new CadastroCliente(clientes);
        cadastroCliente.setVisible(true);

        cadastroCliente.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                atualizarListas();
            }
        });
    }//GEN-LAST:event_jMIClienteActionPerformed

    private void atualizarRemove(){
        CadastroUsuario cadastroUser = new CadastroUsuario(usuarios);
        if (!cadastroUser.getListaAtualizadaUsuarios().isEmpty()) {
            ultimoIdUser = cadastroUser.getListaAtualizadaUsuarios().getLast().getId();
            List<String> itensNaoUsados = listaDisponivelDoDia(jDCDataProgramacao);
            
            List<String> equipeSelecionada = new ArrayList<>();
                if (modeloSelec.getSize() > 0) {
                    equipeSelecionada = new ArrayList<>(
                            IntStream.range(0, modeloSelec.getSize())
                                    .mapToObj(modeloSelec::getElementAt)
                                    .collect(Collectors.toList())
                    );
                }
            
            listModel.removeAllElements();
            for (String item : itensNaoUsados) {
                boolean jaSelecionado = false;
                for (String selecionado : equipeSelecionada) {
                    if(item.equals(selecionado)){
                        jaSelecionado = true;
                        break;
                    }
                }
                if(!jaSelecionado){
                    listModel.addElement(item);
                }
            }
            jLColab.setModel(listModel);
        }
    }

    private void atualizarListas() {
        CadastroCliente cadastroCliente = new CadastroCliente(clientes);
        if (!cadastroCliente.getListaAtualizadaClientes().isEmpty()) {
            ultimoIdCliente = cadastroCliente.getListaAtualizadaClientes().getLast().getId();
            jCBCliente.removeAllItems();
            jCBCliente.addItem("N/A");
            cadastroCliente.getListaAtualizadaClientes().forEach(x -> {
                jCBCliente.addItem(x.getNome());
            });
        }

        CadastroHotel cadastroHotel = new CadastroHotel(hoteis);
        if (!cadastroHotel.getListaAtualizadaHoteis().isEmpty()) {
            ultimoIdHotel = cadastroHotel.getListaAtualizadaHoteis().getLast().getId();
            jCBHospedagem.removeAllItems();
            jCBHospedagem.addItem("N/A");
            cadastroHotel.getListaAtualizadaHoteis().forEach(x -> {
                jCBHospedagem.addItem(x.getNomeComCidadeEEstado());
            });
        }

        CadastroCarro cadastroCarro = new CadastroCarro(carros);
        if (!cadastroCarro.getListaAtualizadaCarros().isEmpty()) {
            ultimoIdCarro = cadastroCarro.getListaAtualizadaCarros().getLast().getId();
            jCBCarro.removeAllItems();
            jCBCarro.addItem("N/A");
            listaDeCarrosDisponivelDoDia(jDCDataProgramacao).forEach(x -> {
                jCBCarro.addItem(x);
            });
        }

        linhasArquivoDemaisInfos.clear();
        try (BufferedReader br = new BufferedReader(new FileReader("demaisInfos.txt"))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                linhasArquivoDemaisInfos.add(linha);
            }

            linhasArquivoDemaisInfos.forEach(elemento -> {
                String[] dados = elemento.split("\\|");
                int qtdArray = dados.length;
                Usuario usuarioHelper = new Usuario();

                demaisInfos.add(new DemaisInfos(
                        Integer.parseInt(dados[0].trim()),
                        dados[1].trim(),
                        (qtdArray >= 3 ? stringToUser(dados[2].trim(), usuarioHelper) : null),
                        (qtdArray >= 4 ? stringToUser(dados[3].trim(), usuarioHelper) : null),
                        (qtdArray >= 5 ? stringToUser(dados[4].trim(), usuarioHelper) : null),
                        (qtdArray >= 6 ? stringToUser(dados[5].trim(), usuarioHelper) : null)
                ));
            });
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Erro no arquivo demaisInfos.txt, talvez ele ainda esteja vazio!");
        }

        atualizarRemove();
    }
    private List<String> listaDeCarrosDisponivelDoDia(JDateChooser data) {
        String dataProgram = data.getDate() == null ? "" : new SimpleDateFormat("dd/MM/yyyy").format(data.getDate());
        CadastroCarro cadastroCarro = new CadastroCarro(carros);
        List<Carro> carrossAtt = cadastroCarro.getListaAtualizadaCarros();
        List<Bloco> blocosAtt = new ArrayList<>();
        
        linhasArquivoBlocoProgramacaoAux.clear();
        try (BufferedReader br = new BufferedReader(new FileReader("blocos.txt"))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                linhasArquivoBlocoProgramacaoAux.add(linha);
            }

            linhasArquivoBlocoProgramacaoAux.forEach(elemento -> {
                String[] dados = elemento.split("\\|");

                if (dados.length >= 6) {
                    String[] dados4Array = dados[5].trim().replace("[", "").replace("]", "").split(",");
                    Usuario usuarioHelper = new Usuario();
                    List<Usuario> listaDaEquipe = Arrays.stream(dados4Array)
                            .map(idStr -> Integer.valueOf(idStr)) // Converte cada String para int
                            .map(id -> usuarioHelper.getById(id, usuarios)) // Busca o nome do usuário pelo ID
                            .collect(Collectors.toList());
                    int qtdArray = dados.length;
                    blocosAtt.add(new Bloco(
                            Integer.parseInt(dados[0].trim()),
                            dados[1].trim(), //data
                            dados[2].trim(), //proj
                            dados[3].trim().equals("null") ? null : new Cliente().getById(Integer.parseInt(dados[3].trim()), clientes), //client conv
                            dados[4].trim(), // finalidade
                            listaDaEquipe, //equipe
                            dados[6].trim().equals("null") ? null : usuarioHelper.getById(Integer.parseInt(dados[6].trim()), usuarios), //user resp
                            dados[7].trim().equals("null") ? null : new Carro().getById(Integer.parseInt(dados[7].trim()), carros), //carro
                            dados[8].trim(), //carretao
                            dados[9].trim(), //data
                            dados[10].trim(), //data
                            dados[11].trim(), //hora
                            dados[12].trim(), //hora
                            dados[13].trim(), //hora
                            dados[14].trim(), //hora
                            dados[15].trim(), //hora
                            dados[16].trim(), //alm
                            dados[17].trim(), //jan
                            dados[18].trim().equals("null") ? null : new Hotel().getById(Integer.parseInt(dados[18].trim()), hoteis), //hotel
                            (qtdArray >= 20 ? dados[19].trim() : null),
                            (qtdArray >= 21 ? dados[20].trim() : null)
                    ));
                }
            });
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Erro no arquivo blocos.txt, talvez ele ainda esteja vazio!");
        }
        
        List<String> carrosExistenteBlocosAtt = blocosAtt.stream()
            .filter(x -> x != null && x.getDataProgramacao() != null && x.getDataProgramacao().equals(dataProgram))
            .flatMap(x -> x.getCarro() != null ? Stream.of(x.getCarro()) : Stream.empty())
            .map(Carro::getNome)
            .collect(Collectors.toList());

        return (List<String>) carrossAtt.stream()
                .filter(u -> !carrosExistenteBlocosAtt.contains(u.getNome())
                        && !carrosExistenteBlocosAtt.contains(u.getNome()))
                .map(Carro::getNome)
                .sorted()
                .collect(Collectors.toList());
    }
    
    private List<String> listaDisponivelDoDia(JDateChooser data) {
        String dataProgram = data.getDate() == null ? "" : new SimpleDateFormat("dd/MM/yyyy").format(data.getDate());
        CadastroUsuario cadastroUsuario = new CadastroUsuario(usuarios);
        List<Usuario> usersAtt = cadastroUsuario.getListaAtualizadaUsuarios();
        List<DemaisInfos> demaisAtt = new ArrayList<>();
        List<Bloco> blocosAtt = new ArrayList<>();
        
        linhasArquivoDemaisInfos.clear();
        try (BufferedReader br = new BufferedReader(new FileReader("demaisInfos.txt"))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                linhasArquivoDemaisInfos.add(linha);
            }

            linhasArquivoDemaisInfos.forEach(elemento -> {
                String[] dados = elemento.split("\\|");
                int qtdArray = dados.length;
                Usuario usuarioHelper = new Usuario();

                demaisAtt.add(new DemaisInfos(
                        Integer.parseInt(dados[0].trim()),
                        dados[1].trim(),
                        (qtdArray >= 3 ? stringToUser(dados[2].trim(), usuarioHelper) : null),
                        (qtdArray >= 4 ? stringToUser(dados[3].trim(), usuarioHelper) : null),
                        (qtdArray >= 5 ? stringToUser(dados[4].trim(), usuarioHelper) : null),
                        (qtdArray >= 6 ? stringToUser(dados[5].trim(), usuarioHelper) : null)
                ));
            });          
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Erro no arquivo demaisInfos.txt, talvez ele ainda esteja vazio!");
        }

        linhasArquivoBlocoProgramacaoAux.clear();
        try (BufferedReader br = new BufferedReader(new FileReader("blocos.txt"))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                linhasArquivoBlocoProgramacaoAux.add(linha);
            }

            linhasArquivoBlocoProgramacaoAux.forEach(elemento -> {
                String[] dados = elemento.split("\\|");

                if (dados.length >= 6) {
                    String[] dados4Array = dados[5].trim().replace("[", "").replace("]", "").split(",");
                    Usuario usuarioHelper = new Usuario();
                    List<Usuario> listaDaEquipe = Arrays.stream(dados4Array)
                            .map(idStr -> Integer.valueOf(idStr)) // Converte cada String para int
                            .map(id -> usuarioHelper.getById(id, usuarios)) // Busca o nome do usuário pelo ID
                            .collect(Collectors.toList());
                    int qtdArray = dados.length;
                    blocosAtt.add(new Bloco(
                            Integer.parseInt(dados[0].trim()),
                            dados[1].trim(), //data
                            dados[2].trim(), //proj
                            dados[3].trim().equals("null") ? null : new Cliente().getById(Integer.parseInt(dados[3].trim()), clientes), //client conv
                            dados[4].trim(), // finalidade
                            listaDaEquipe, //equipe
                            dados[6].trim().equals("null") ? null : usuarioHelper.getById(Integer.parseInt(dados[6].trim()), usuarios), //user resp
                            dados[7].trim().equals("null") ? null : new Carro().getById(Integer.parseInt(dados[7].trim()), carros), //carro
                            dados[8].trim(), //carretao
                            dados[9].trim(), //data
                            dados[10].trim(), //data
                            dados[11].trim(), //hora
                            dados[12].trim(), //hora
                            dados[13].trim(), //hora
                            dados[14].trim(), //hora
                            dados[15].trim(), //hora
                            dados[16].trim(), //alm
                            dados[17].trim(), //jan
                            dados[18].trim().equals("null") ? null : new Hotel().getById(Integer.parseInt(dados[18].trim()), hoteis), //hotel
                            (qtdArray >= 20 ? dados[19].trim() : null),
                            (qtdArray >= 21 ? dados[20].trim() : null)
                    ));
                }
            });
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Erro no arquivo blocos.txt, talvez ele ainda esteja vazio!");
        }
        
        List<String> nomesExistenteDemaisAtt = (List<String>) demaisAtt.stream()
            .filter(x -> x != null && x.getDataProgramacao() != null && x.getDataProgramacao().equals(dataProgram))
            .flatMap(x -> Stream.of(
                x.getEletricistasInternos() != null ? x.getEletricistasInternos() : Collections.emptyList(),
                x.getMecanicosInternos() != null ? x.getMecanicosInternos() : Collections.emptyList(),
                x.getFerias() != null ? x.getFerias() : Collections.emptyList(),
                x.getFolgas() != null ? x.getFolgas() : Collections.emptyList()
            ).flatMap(Collection::stream))
            .map(u -> ((Usuario) u).getNomeDeGuerra())
            .collect(Collectors.toList());

        List<String> nomesExistenteBlocosAtt = (List<String>) blocosAtt.stream()
            .filter(x -> x != null && x.getDataProgramacao() != null && x.getDataProgramacao().equals(dataProgram))
            .flatMap(x -> x.getEquipe() != null ? x.getEquipe().stream() : Stream.empty())
            .map(Usuario::getNomeDeGuerra)
            .collect(Collectors.toList());

        List<String> nomesNaoExistente = (List<String>) usersAtt.stream()
            .filter(u -> !nomesExistenteDemaisAtt.contains(u.getNomeDeGuerra()) 
                && !nomesExistenteBlocosAtt.contains(u.getNomeDeGuerra())
                && (u.getFuncao().equals("EXECUÇÃO")) || (u.getFuncao().equals("EXECUCAO")))
            .map(Usuario::getNomeDeGuerra)
            .sorted()
            .collect(Collectors.toList());
        
        return nomesNaoExistente;
    }
    

    private boolean comparaData(JDateChooser data) {
        if (data.getDate() != null && data.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().isBefore(LocalDate.now())) {
            JOptionPane.showMessageDialog(null, "Não pode selecionar datas retroativas, tanto na data da Programação, quando na realização da obra!", "Atenção", JOptionPane.WARNING_MESSAGE);
            return true;
        }
        return false;
    }

    private boolean comparaIdaMaiorQueProgramacao(JDateChooser data, JDateChooser programacao) {
        if (data.getDate() != null && data.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().isAfter(programacao.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate())) {
            JOptionPane.showMessageDialog(null, "A data de Ida não pode ser maior que a data escolhida na Programação! Revise as datas!", "Atenção", JOptionPane.WARNING_MESSAGE);
            return true;
        }
        return false;
    }

    private boolean comparaDatasDeIdaEVolta(JDateChooser dataIda, JDateChooser volta) {
        if (dataIda.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().isAfter(volta.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()) || volta.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().isBefore(dataIda.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate())) {
            JOptionPane.showMessageDialog(null, "Espaço de dias inválido!\nColoque na Ida a data da 'Programação Inicial' e para data de Retorno uma data para retornar que seja maior oi igual a data da Programação Inicial!", "Atenção", JOptionPane.WARNING_MESSAGE);
            return true;
        }
        return false;
    }
    
    private boolean comparaMesmoDiaComProgramacao(JDateChooser dataIda, JDateChooser volta, JDateChooser dataProgramacao) {
        boolean mesmaData = dataIda.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().isEqual(volta.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        if (mesmaData && !dataIda.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().isEqual(dataProgramacao.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate())) {
            JOptionPane.showMessageDialog(null, "A data de Saída e Retorno não batem com a data da Programação!", "Atenção", JOptionPane.WARNING_MESSAGE);
            return true;
        }
        return false;
    }

    private void jBAddBlocoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBAddBlocoActionPerformed
        // Obtenha as datas inicial e final
        Date programacaoData = jDCDataProgramacao.getDate();
        Date dataInicial = jDCDataSaida.getDate();
        Date dataFinal = jDCDataRetorno.getDate();
        
        // Converta para LocalDate
        java.time.LocalDate localDateProg = programacaoData.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        java.time.LocalDate localDateSaida = dataInicial.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        java.time.LocalDate localDateRetorno = dataFinal.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        // Calcule a diferença em dias
        long diferencaDias = ChronoUnit.DAYS.between(localDateSaida, localDateRetorno);
 
        String[] options = {"Sim", "Não, cancelar!"};
        
        listaDisponivelDoDia(jDCDataProgramacao);
        listaDeCarrosDisponivelDoDia(jDCDataProgramacao);
        
        if (String.valueOf(jCBFinalidade.getSelectedItem()).equals("SELECIONE")) {
            JOptionPane.showMessageDialog(null, "Você precisa selecionar uma Finalidade!", "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (String.valueOf(jCBCarro.getSelectedItem()).equals("N/A")) {
            JOptionPane.showMessageDialog(null, "Você precisa selecionar um carro!", "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String texto = jFTFNumero.getText();
        String projeto = (texto == null || texto.isEmpty()) ? "" :
            texto.split("\\.").length > 1 && texto.split("\\.")[1].equals("0") ? 
            texto.split("\\.")[0] : 
            texto;
        if(jFTFNumero.getText().equals(".0")){
            JOptionPane.showMessageDialog(null, "Número de Projeto inválido!", "Atenção", JOptionPane.WARNING_MESSAGE);
            jFTFNumero.setValue(null);
            return;
        }
        

        if ((jDCDataProgramacao.getDate() != null && String.valueOf(jCBFinalidade.getSelectedItem()).contains("VISITA ") && !String.valueOf(jCBCarro.getSelectedItem()).equals("N/A"))
            || (jDCDataProgramacao.getDate() != null && !String.valueOf(jCBFinalidade.getSelectedItem()).contains("VISITA ") && !jFTFNumero.getText().equals(""))
            || ((("OCULTAR".equals(String.valueOf(jCBFinalidade.getSelectedItem())) || "H.H.".equals(String.valueOf(jCBFinalidade.getSelectedItem()))) || "PASSAGEM DE TRABALHO".equals(String.valueOf(jCBFinalidade.getSelectedItem()))) && (projeto.equals("") || projeto.isEmpty()))) {
            
            if(!String.valueOf(jCBFinalidade.getSelectedItem()).contains("VISITA ") && jCBCliente.getSelectedItem().equals("N/A")){
                JOptionPane.showMessageDialog(null, "Selecione um cliente válido ou mude a finalidade para VISITA COMERCIAL!", "Atenção", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            if(diferencaDias >= 15){
                int escolha = JOptionPane.showOptionDialog(
                    null, // Parent component (null para centralizar na tela)
                    "O intervalo de datas é muito grande! \nTem certeza que quer criar programações do dia "+ new SimpleDateFormat("dd/MM/yyyy").format(jDCDataSaida.getDate())+ " até o dia " +new SimpleDateFormat("dd/MM/yyyy").format(jDCDataRetorno.getDate())+ "?", // Mensagem
                    "Atenção!", // Título
                    JOptionPane.DEFAULT_OPTION, // Tipo de opção
                    JOptionPane.INFORMATION_MESSAGE, // Ícone
                    null, // Nenhum ícone personalizado
                    options, // Botões personalizados
                    options[0] // Botão padrão selecionado
                );

                if(escolha != 0){
                    return;
                }
            }
            
            for (Bloco x : blocos) {
                if ((x.getDataProgramacao().contains(jDCDataProgramacao.getDate() == null ? "" : new SimpleDateFormat("dd/MM/yyyy").format(jDCDataProgramacao.getDate())) && x.getFinalidade().contains("VISITA ") && x.getCarro().getNome().contains(String.valueOf(jCBCarro.getSelectedItem()))) || x.getDataProgramacao().contains(jDCDataProgramacao.getDate() == null ? "" : new SimpleDateFormat("dd/MM/yyyy").format(jDCDataProgramacao.getDate())) && (x.getFinalidade().equals(String.valueOf(jCBFinalidade.getSelectedItem())) && !x.getFinalidade().contains("VISITA ")) && x.getProjeto().equals(projeto)) {
                    JOptionPane.showMessageDialog(null, "Já há salvo um bloco com esses dados principais. Altere-o em vez de criar um novo!", "Atenção", JOptionPane.WARNING_MESSAGE);
                    return;
                }
            }

            // Crie um calendário para iterar sobre as datas
            Calendar calendario = Calendar.getInstance();
            calendario.setTime(dataInicial);
            calendario.set(Calendar.HOUR_OF_DAY, 0);
            calendario.set(Calendar.MINUTE, 0);
            calendario.set(Calendar.SECOND, 0);
            calendario.set(Calendar.MILLISECOND, 0);

            Calendar calendarioFinal = Calendar.getInstance();
            calendarioFinal.setTime(dataFinal);
            calendarioFinal.set(Calendar.HOUR_OF_DAY, 0);
            calendarioFinal.set(Calendar.MINUTE, 0);
            calendarioFinal.set(Calendar.SECOND, 0);
            calendarioFinal.set(Calendar.MILLISECOND, 0);
            
            Cliente cliente = new Cliente().getByNameEmpresa(String.valueOf(jCBCliente.getSelectedItem()), clientes);
            if(cliente != null){
                if((!jCBRetornaMesmoDia.isSelected())&&(jCBHospedagem.getSelectedItem().toString().equals("N/A")&&(cliente.getDistanciaEmKm()>70) && (ChronoUnit.DAYS.between(localDateProg, localDateRetorno) >= 1))){
                    JOptionPane.showMessageDialog(null, "Sargento, selecione o Hotel! Se não tiver, cadastre ele, por favor!");
                    return;
                }
            }
            
            while (calendario.getTime().before(calendarioFinal.getTime()) || calendario.getTime().equals(calendarioFinal.getTime())) {
                String dataProgramacao = new SimpleDateFormat("dd/MM/yyyy").format(calendario.getTime());
                if (comparaData(jDCDataProgramacao)) {
                    return;
                }
                String finalidade = String.valueOf(jCBFinalidade.getSelectedItem());
                if (cliente == null && !finalidade.contains("VISITA ")) {
                    JOptionPane.showMessageDialog(null, "Selecione um cliente! Caso seja Visita Comercial, mude a 'Finalidade'!", "Atenção", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                List<Usuario> equipe = new ArrayList<>();

                if (modeloSelec.getSize() > 0) {
                    ArrayList<String> arrayList = new ArrayList<>(
                            IntStream.range(0, modeloSelec.getSize())
                                    .mapToObj(modeloSelec::getElementAt)
                                    .collect(Collectors.toList())
                    );
                    equipe = (ArrayList<Usuario>) usuarios.stream()
                            .filter(usuario -> arrayList.stream()
                            .anyMatch(nome -> nome.equals(usuario.getNomeDeGuerra()))
                            )
                            .collect(Collectors.toList());
                }
                if (jCBResponsavel.getSelectedItem() == null) {
                    JOptionPane.showMessageDialog(null, "Selecione e Adicione ao menos um membro a equipe!", "Atenção", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                if(String.valueOf(jCBResponsavel.getSelectedItem()).equals("OUTRO") && jTFRespOutro.getText().equals("")){
                    JOptionPane.showMessageDialog(null, "Selecione um responsável na lista principal!", "Atenção", JOptionPane.WARNING_MESSAGE);
                    return;
                } 

                String nomeResponsavel = String.valueOf(jCBResponsavel.getSelectedItem()).equals("OUTRO") ? jTFRespOutro.getText() : String.valueOf(jCBResponsavel.getSelectedItem());
                Usuario responsavel = new Usuario().getByNameDeGuerra(nomeResponsavel, usuarios);

                responsavel = finalidade.contains("VISITA ") ? null : responsavel; 

                checagemCarteira();

                Carro carro = new Carro().getByName(String.valueOf(jCBCarro.getSelectedItem()), carros);

                String carretao = String.valueOf(jCBCarretao.getSelectedItem());
                Hotel hotel = new Hotel().getByNameHotel(String.valueOf(jCBHospedagem.getSelectedItem()).split(",")[0], hoteis);
                String almoco = jTFAlmoco.getText().equals("") ? null : jTFAlmoco.getText().trim().toUpperCase();
                String janta = jTFJanta.getText().equals("") ? null : jTFJanta.getText().trim().toUpperCase();
                String dataSaida = jDCDataSaida.getDate() == null ? "" : new SimpleDateFormat("dd/MM/yyyy").format(jDCDataSaida.getDate());
                if (comparaData(jDCDataSaida)) {
                    return;
                }
                if (comparaIdaMaiorQueProgramacao(jDCDataSaida, jDCDataProgramacao)) {
                    return;
                }
                String dataRetorno = jDCDataRetorno.getDate() == null ? "" : new SimpleDateFormat("dd/MM/yyyy").format(jDCDataRetorno.getDate());
                if (comparaData(jDCDataRetorno)) {
                    return;
                }
                if (comparaDatasDeIdaEVolta(jDCDataSaida, jDCDataRetorno)) {
                    return;
                }
                if (comparaMesmoDiaComProgramacao(jDCDataSaida, jDCDataRetorno, jDCDataProgramacao)) {
                    return;
                }
                String horaSaida = jFTFHoraSaida.getText();
                if (dataProgramacao.trim().equals("") || dataSaida.trim().equals("") || dataRetorno.trim().equals("") || horaSaida.trim().equals("")) {
                    JOptionPane.showMessageDialog(null, "Preencha corretamente todas datas e hora de saída!", "Atenção", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                String horaManhaInicio = jFTFHoraInicioManha.getText();
                String horaManhaFim = jFTFHoraFimManha.getText();
                String horaTardeInicio = jFTFHoraInicioTarde.getText();
                String horaTardeFim = jFTFHoraFimTarde.getText();
                if (horaManhaInicio.equals("") || horaManhaFim.equals("") || horaTardeInicio.equals("") || horaTardeFim.equals("")) {
                    JOptionPane.showMessageDialog(null, "Informe o horário de trabalho corretamente!", "Atenção", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                String carroExtra = String.valueOf(jCBCarroExtra.getSelectedItem()) != null? String.valueOf(jCBCarroExtra.getSelectedItem()) : null;
                String observacoes = jTFObservacoes.getText().equals("") ? null : jTFObservacoes.getText().trim().toUpperCase();
                String nomesColabs = "", blocoSalvar = "";
                boolean voltaMesmoDia = jCBRetornaMesmoDia.isSelected();
                Bloco bloco = null;
                if(voltaMesmoDia){
                    bloco = new Bloco(dataProgramacao, projeto, cliente, finalidade, equipe, responsavel, carro, carretao, dataProgramacao, dataProgramacao, horaSaida, horaManhaInicio, horaManhaFim, horaTardeInicio, horaTardeFim, almoco, janta, hotel, observacoes, carroExtra);
                    blocos.add(bloco);
                    nomesColabs = bloco.getEquipe().stream().map(usuario -> String.valueOf(usuario.getId())).collect(Collectors.joining(","));
                    blocoSalvar = bloco.getId() + "|" + dataProgramacao + "|" + projeto + "|" + (cliente == null ? null : cliente.getId()) + "|" + finalidade + "|" + nomesColabs + "|" + (responsavel == null ? null : responsavel.getId()) + "|" + (carro == null ? null : carro.getId()) + "|" + carretao + "|" + dataProgramacao + "|" + dataProgramacao + "|" + horaSaida + "|" + horaManhaInicio + "|" + horaManhaFim + "|" + horaTardeInicio + "|" + horaTardeFim + "|" + almoco + "|" + janta + "|" + (hotel == null ? null : hotel.getId()) + "|" + observacoes + "|" + carroExtra;
                }else{
                    bloco = new Bloco(dataProgramacao, projeto, cliente, finalidade, equipe, responsavel, carro, carretao, dataSaida, dataRetorno, horaSaida, horaManhaInicio, horaManhaFim, horaTardeInicio, horaTardeFim, almoco, janta, hotel, observacoes, carroExtra);
                    blocos.add(bloco);
                    nomesColabs = bloco.getEquipe().stream().map(usuario -> String.valueOf(usuario.getId())).collect(Collectors.joining(","));
                    blocoSalvar = bloco.getId() + "|" + dataProgramacao + "|" + projeto + "|" + (cliente == null ? null : cliente.getId()) + "|" + finalidade + "|" + nomesColabs + "|" + (responsavel == null ? null : responsavel.getId()) + "|" + (carro == null ? null : carro.getId()) + "|" + carretao + "|" + dataSaida + "|" + dataRetorno + "|" + horaSaida + "|" + horaManhaInicio + "|" + horaManhaFim + "|" + horaTardeInicio + "|" + horaTardeFim + "|" + almoco + "|" + janta + "|" + (hotel == null ? null : hotel.getId()) + "|" + observacoes + "|" + carroExtra;
                }

                
                try (BufferedWriter writer = new BufferedWriter(new FileWriter("blocos.txt", true))) {
                    writer.write(blocoSalvar);
                    writer.newLine();
                    JOptionPane.showMessageDialog(null, "Bloco de programação gravado com sucesso para a programação do dia "+dataProgramacao+"!");
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(null, "Erro ao salvar aquivo!");
                }
                // Avance para o próximo dia
                calendario.add(Calendar.DAY_OF_YEAR, 1);
            }
            
            limpaCampos();
            return;
        }
        JOptionPane.showMessageDialog(null, "Não é possível adicionar, dados insuficiêntes!\nVISITA COMERCIAL precisa de DATA, Finalidade VISITA COMERCIAL e CARRO\nPara as demais finalidades, precisa informar a DATA, a FINALIDADE e o NÚMERO DO PROJETO!");
    }//GEN-LAST:event_jBAddBlocoActionPerformed

    private void jBAlterarBlocoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBAlterarBlocoActionPerformed
        String[] options = {"Só Neste", "Em Todos", "Cancelar"};
        
        if(!jDCDataSaida.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().isEqual(jDCDataRetorno.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate())){
            int escolha = JOptionPane.showOptionDialog(
                null, // Parent component (null para centralizar na tela)
                "Deseja que a alteração reflita em todos registros semelhantes ou somente neste?", // Mensagem
                "Menu de Comandos", // Título
                JOptionPane.DEFAULT_OPTION, // Tipo de opção
                JOptionPane.INFORMATION_MESSAGE, // Ícone
                null, // Nenhum ícone personalizado
                options, // Botões personalizados
                options[0] // Botão padrão selecionado
            );
    
            switch (escolha) {
                case 0 -> alteracaoPorData();
                case 1 -> alteracaoEmTodasDatas(jDCDataSaida, jDCDataRetorno);
                default -> {
                    return;
                }
            }
            return;
        }
        alteracaoPorData();
    }//GEN-LAST:event_jBAlterarBlocoActionPerformed

    private void alteracaoEmTodasDatas(JDateChooser dataSaidaProgram, JDateChooser dataRetornoProgram) {
        boolean exists = false;
        boolean isVisitaComercial = validaVisitaComercial();
        if (!isVisitaComercial && !validaOutros()) {
            JOptionPane.showMessageDialog(null, "Dados insuficientes para Atualizar!\nSe for atualização de uma VISITA COMERCIAL ou PASSAGEM DE TRABALHO, preencha a DATA, a FINALIDADE 'VISITA COMERCIAL' e o CARRO.\nPara as demais finalidades, precisa informar a DATA, a FINALIDADE e o NÚMERO DO PROJETO!");
            return;
        }

        String projeto = jFTFNumero.getText().equals("") ? null : jFTFNumero.getText().split("\\.")[1].equals("0") ? jFTFNumero.getText().split("\\.")[0] : jFTFNumero.getText();
        LocalDate dataSaida = dataSaidaProgram.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate dataRetorno = dataRetornoProgram.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        for (LocalDate dataAtual = dataSaida; !dataAtual.isAfter(dataRetorno); dataAtual = dataAtual.plusDays(1)) {
            String dataAtualStr = dataAtual.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

            for (Bloco x : blocos) {
                if ((x.getDataProgramacao().contains(dataAtualStr) && (x.getFinalidade().contains("VISITA ") || x.getFinalidade().equals("PASSAGEM DE TRABALHO")) && x.getCarro().getNome().contains(String.valueOf(jCBCarro.getSelectedItem()))) ||
                    (x.getDataProgramacao().contains(dataAtualStr) && x.getFinalidade().equals(String.valueOf(jCBFinalidade.getSelectedItem())) && x.getProjeto().equals(projeto))) {

                    exists = true;
                    List<Usuario> equipe = new ArrayList<>();
                    if (modeloSelec.getSize() > 0) {
                        ArrayList<String> arrayList = new ArrayList<>(
                                IntStream.range(0, modeloSelec.getSize())
                                        .mapToObj(modeloSelec::getElementAt)
                                        .collect(Collectors.toList())
                        );
                        equipe = (ArrayList<Usuario>) usuarios.stream()
                                .filter(usuario -> arrayList.stream()
                                .anyMatch(nome -> nome.equals(usuario.getNomeDeGuerra()))
                                )
                                .collect(Collectors.toList());
                    }

                    Cliente cliente = new Cliente().getByNameEmpresa(String.valueOf(jCBCliente.getSelectedItem()), clientes);
                    x.setCliente(cliente);
                    String finalidade = String.valueOf(jCBFinalidade.getSelectedItem());
                    if (cliente == null && !finalidade.contains("VISITA ")) {
                        JOptionPane.showMessageDialog(null, "Selecione um cliente! Caso seja Visita Comercial, mude a 'Finalidade'!", "Atenção", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    Usuario responsavel = null;
                    if (!equipe.isEmpty()) {
                        x.setEquipe(equipe);
                        String nomeResponsavel = String.valueOf(jCBResponsavel.getSelectedItem()).equals("OUTRO") ? jTFRespOutro.getText() : String.valueOf(jCBResponsavel.getSelectedItem());
                        responsavel = new Usuario().getByNameDeGuerra(nomeResponsavel, usuarios);
                        responsavel = finalidade.contains("VISITA ") ? null : responsavel;
                        x.setResponsavelDoTrabalho(responsavel);

                    } else {
                        JOptionPane.showMessageDialog(null, "Equipe não atualizada, a equipe tem que ser composta pelo menos por um integrante!", "Atenção", JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    Carro carro = new Carro().getByName(String.valueOf(jCBCarro.getSelectedItem()), carros);
                    x.setCarro(carro);
                    String carretao = String.valueOf(jCBCarretao.getSelectedItem());
                    x.setCarretao(carretao);
                    Hotel hotel = new Hotel().getByNameHotel(String.valueOf(jCBHospedagem.getSelectedItem()).split(",")[0], hoteis);
                    x.setHospedagem(hotel);
                    String almoco = jTFAlmoco.getText().equals("") ? null : jTFAlmoco.getText().trim().toUpperCase();
                    x.setAlmoco(almoco);
                    String janta = jTFJanta.getText().equals("") ? null : jTFJanta.getText().trim().toUpperCase();
                    x.setJanta(janta);

                    String dataSaidaStr = new SimpleDateFormat("dd/MM/yyyy").format(dataSaidaProgram.getDate());
                    String dataRetornoStr = new SimpleDateFormat("dd/MM/yyyy").format(dataRetornoProgram.getDate());
                    String horaSaida = jFTFHoraSaida.getText();
                    if (dataSaidaStr.equals("") || dataRetornoStr.equals("") || horaSaida.equals("")) {
                        JOptionPane.showMessageDialog(null, "Preencha corretamente todas datas e hora de saída!\nLembrando que a data do bloco não é alterada! Se precisar, delete o bloco e crie outro!", "Atenção", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    x.setDataDeSaida(dataSaidaStr);
                    x.setDataDeRetorno(dataRetornoStr);
                    
                    x.setHorarioDeSaida(horaSaida);

                    String horaManhaInicio = jFTFHoraInicioManha.getText();
                    String horaManhaFim = jFTFHoraFimManha.getText();
                    String horaTardeInicio = jFTFHoraInicioTarde.getText();
                    String horaTardeFim = jFTFHoraFimTarde.getText();
                    if (horaManhaInicio.equals("") || horaManhaFim.equals("") || horaTardeInicio.equals("") || horaTardeFim.equals("")) {
                        JOptionPane.showMessageDialog(null, "Informe o horário de trabalho corretamente!", "Atenção", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    x.setHorarioDeTrabalhoInicio(horaManhaInicio);
                    x.setHorarioDeTrabalhoFimMeioDia(horaManhaFim);
                    x.setHorarioDeTrabalhoInicioMeioDia(horaTardeInicio);
                    x.setHorarioDeTrabalhoFim(horaTardeFim);

                    String observacoes = jTFObservacoes.getText().equals("") ? null : jTFObservacoes.getText().trim().toUpperCase();
                    x.setObservacoes(observacoes);
                }
            }
        }

        if (exists) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("blocos.txt"))) {
                for (Bloco bloco : blocos) {
                    String nomesColabsBl = bloco.getEquipe().stream()
                        .map(usuario -> String.valueOf(usuario.getId()))
                        .collect(Collectors.joining(","));

                    String blocoSalvarBl = bloco.getId() + "|" + bloco.getDataProgramacao() + "|" 
                        + bloco.getProjeto() + "|" 
                        + (bloco.getCliente() == null ? null : bloco.getCliente().getId()) + "|" 
                        + bloco.getFinalidade() + "|" + nomesColabsBl + "|" 
                        + (bloco.getResponsavelDoTrabalho() == null ? null : bloco.getResponsavelDoTrabalho().getId()) + "|" 
                        + (bloco.getCarro() == null ? null : bloco.getCarro().getId()) + "|" 
                        + bloco.getCarretao() + "|" + bloco.getDataDeSaida() + "|" 
                        + bloco.getDataDeRetorno() + "|" + bloco.getHorarioDeSaida() + "|" 
                        + bloco.getHorarioDeTrabalhoInicio() + "|" + bloco.getHorarioDeTrabalhoFimMeioDia() + "|" 
                        + bloco.getHorarioDeTrabalhoInicioMeioDia() + "|" + bloco.getHorarioDeTrabalhoFim() + "|" 
                        + bloco.getAlmoco() + "|" + bloco.getJanta() + "|" 
                        + (bloco.getHospedagem() == null || bloco.getHospedagem().equals("null") ? null : bloco.getHospedagem().getId()) + "|" 
                        + bloco.getObservacoes() + "|"
                        + bloco.getCarroExtra();

                    writer.write(blocoSalvarBl);    // Escreve o bloco no arquivo
                    writer.newLine();              // Adiciona uma nova linha
                }

                JOptionPane.showMessageDialog(null, "Bloco de programação gravado com sucesso!");
                limpaCampos(); // Função para limpar os campos de entrada após a gravação
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Erro ao salvar o arquivo!", "Erro", JOptionPane.ERROR_MESSAGE);
            }
    
        }
    }
    
    private void alteracaoPorData(){
        boolean exists = false;
        boolean isVisitaComercial = validaVisitaComercial();
        if (!isVisitaComercial && !validaOutros()) {
            JOptionPane.showMessageDialog(null, "Dados insuficientes para Atualizar!\nSe for atualização de uma VISITA COMPERCIAL ou PASSAGEM DE TRABALHO, preencha a DATA, a FINALIDADE 'VISITA COMERCIAL' e o CARRO.\nPara as demais finalidades, precisa informar a DATA, a FINALIDADE e o NÚMERO DO PROJETO!");
            return;
        }
        String projeto = jFTFNumero.getText().equals("") ? null : jFTFNumero.getText().split("\\.")[1].equals("0") ? jFTFNumero.getText().split("\\.")[0] : jFTFNumero.getText();
        for (Bloco x : blocos) {
            if ((x.getDataProgramacao().contains(jDCDataProgramacao.getDate() == null ? "" : new SimpleDateFormat("dd/MM/yyyy").format(jDCDataProgramacao.getDate())) && (x.getFinalidade().contains("VISITA ")||x.getFinalidade().equals("PASSAGEM DE TRABALHO")||(x.getFinalidade().equals("OCULTAR") || x.getFinalidade().equals("H.H."))) && x.getCarro().getNome().contains(String.valueOf(jCBCarro.getSelectedItem()))) || x.getDataProgramacao().contains(jDCDataProgramacao.getDate() == null ? "" : new SimpleDateFormat("dd/MM/yyyy").format(jDCDataProgramacao.getDate())) && x.getFinalidade().equals(String.valueOf(jCBFinalidade.getSelectedItem())) && x.getProjeto().equals(projeto)) {
                exists = true;
                List<Usuario> equipe = new ArrayList<>();
                if (modeloSelec.getSize() > 0) {
                    ArrayList<String> arrayList = new ArrayList<>(
                            IntStream.range(0, modeloSelec.getSize())
                                    .mapToObj(modeloSelec::getElementAt)
                                    .collect(Collectors.toList())
                    );
                    equipe = (ArrayList<Usuario>) usuarios.stream()
                            .filter(usuario -> arrayList.stream()
                            .anyMatch(nome -> nome.equals(usuario.getNomeDeGuerra()))
                            )
                            .collect(Collectors.toList());
                }
                
                Cliente cliente = new Cliente().getByNameEmpresa(String.valueOf(jCBCliente.getSelectedItem()), clientes);
                x.setCliente(cliente);
                String finalidade = String.valueOf(jCBFinalidade.getSelectedItem());
                if (cliente == null && !finalidade.contains("VISITA ")) {
                    JOptionPane.showMessageDialog(null, "Selecione um cliente! Caso seja Visita Comercial, mude a 'Finalidade'!", "Atenção", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                Usuario responsavel = null;
                if (!equipe.isEmpty()) {
                    x.setEquipe(equipe);
                    String nomeResponsavel = String.valueOf(jCBResponsavel.getSelectedItem()).equals("OUTRO") ? jTFRespOutro.getText() : String.valueOf(jCBResponsavel.getSelectedItem());
                    responsavel = new Usuario().getByNameDeGuerra(nomeResponsavel, usuarios);
                    responsavel = finalidade.contains("VISITA ") ? null : responsavel; 
                    x.setResponsavelDoTrabalho(responsavel);

                } else {
                    JOptionPane.showMessageDialog(null, "Equipe não atualizada, a equipe tem que ser composta pelo menos por um integrante!", "Atenção", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                
                Carro carro = new Carro().getByName(String.valueOf(jCBCarro.getSelectedItem()), carros);
                x.setCarro(carro);
                String carretao = String.valueOf(jCBCarretao.getSelectedItem());
                x.setCarretao(carretao);
                Hotel hotel = new Hotel().getByNameHotel(String.valueOf(jCBHospedagem.getSelectedItem()).split(",")[0], hoteis);
                x.setHospedagem(hotel);
                String almoco = jTFAlmoco.getText().equals("") ? null : jTFAlmoco.getText().trim().toUpperCase();
                x.setAlmoco(almoco);
                String janta = jTFJanta.getText().equals("") ? null : jTFJanta.getText().trim().toUpperCase();
                x.setJanta(janta);

                String dataSaida = jDCDataSaida.getDate() == null ? "" : new SimpleDateFormat("dd/MM/yyyy").format(jDCDataSaida.getDate());
                String dataRetorno = jDCDataRetorno.getDate() == null ? "" : new SimpleDateFormat("dd/MM/yyyy").format(jDCDataRetorno.getDate());
                String horaSaida = jFTFHoraSaida.getText();
                if (dataSaida.equals("") || dataRetorno.equals("") || horaSaida.equals("")) {
                    JOptionPane.showMessageDialog(null, "Preencha corretamente todas datas e hora de saída!\nLembrando que a data do bloco não é alterada! Se precisar, delete o bloco e crie outro!", "Atenção", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                x.setDataDeSaida(dataSaida);
                x.setDataDeRetorno(dataRetorno);
                
                x.setHorarioDeSaida(horaSaida);

                String horaManhaInicio = jFTFHoraInicioManha.getText();
                String horaManhaFim = jFTFHoraFimManha.getText();
                String horaTardeInicio = jFTFHoraInicioTarde.getText();
                String horaTardeFim = jFTFHoraFimTarde.getText();
                if (horaManhaInicio.equals("") || horaManhaFim.equals("") || horaTardeInicio.equals("") || horaTardeFim.equals("")) {
                    JOptionPane.showMessageDialog(null, "Informe o horário de trabalho corretamente!", "Atenção", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                x.setHorarioDeTrabalhoInicio(horaManhaInicio);
                x.setHorarioDeTrabalhoFimMeioDia(horaManhaFim);
                x.setHorarioDeTrabalhoInicioMeioDia(horaTardeInicio);
                x.setHorarioDeTrabalhoFim(horaTardeFim);
                x.setCarroExtra(String.valueOf(jCBCarroExtra.getSelectedItem()));
                
                String observacoes = jTFObservacoes.getText().equals("") ? null : jTFObservacoes.getText().trim().toUpperCase();
                x.setObservacoes(observacoes);
                
                try (BufferedWriter writer = new BufferedWriter(new FileWriter("blocos.txt"))) {
                    for (Bloco bloco : blocos) {
                        String nomesColabsBl = bloco.getEquipe().stream().map(usuario -> String.valueOf(usuario.getId())).collect(Collectors.joining(","));
                        String blocoSalvarBl = bloco.getId() + "|" + bloco.getDataProgramacao() + "|" 
                            + bloco.getProjeto() + "|" 
                            + (bloco.getCliente() == null ? null : bloco.getCliente().getId()) + "|" 
                            + bloco.getFinalidade() + "|" + nomesColabsBl + "|" 
                            + (bloco.getResponsavelDoTrabalho() == null ? null : bloco.getResponsavelDoTrabalho().getId()) + "|" 
                            + (bloco.getCarro() == null ? null : bloco.getCarro().getId()) + "|" 
                            + bloco.getCarretao() + "|" + bloco.getDataDeSaida() + "|" 
                            + bloco.getDataDeRetorno() + "|" + bloco.getHorarioDeSaida() + "|" 
                            + bloco.getHorarioDeTrabalhoInicio() + "|" + bloco.getHorarioDeTrabalhoFimMeioDia() + "|" 
                            + bloco.getHorarioDeTrabalhoInicioMeioDia() + "|" + bloco.getHorarioDeTrabalhoFim() + "|" 
                            + bloco.getAlmoco() + "|" + bloco.getJanta() + "|" 
                            + (bloco.getHospedagem() == null || bloco.getHospedagem().equals("null") ? null : bloco.getHospedagem().getId()) + "|" 
                            + bloco.getObservacoes() + "|"
                            + bloco.getCarroExtra();
                        writer.write(blocoSalvarBl);    // Escreve a linha no arquivo
                        writer.newLine();       // Adiciona uma nova linha
                    }
                    JOptionPane.showMessageDialog(null, "Bloco de programação gravado com sucesso!");
                    limpaCampos();
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(null, "Erro ao salvar aquivo!");
                }

                break;
            }

        }
        if (!exists) {
            JOptionPane.showMessageDialog(null, "Bloco não encontrado para fazer alteração! Caso ele ainda não exista, crie ele!");
            limpaCampos();
            return;
        }
    }
    
    private void jBRemoverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBRemoverActionPerformed
        boolean isVisitaComercial = validaVisitaComercial();
        boolean exists = false;
        if (!isVisitaComercial && !validaOutros() && !validaSemProjeto()) {
            JOptionPane.showMessageDialog(null, "Dados insuficientes para Remover!\nSe for remoção de uma VISITA COMPERCIAL, preencha a DATA, a FINALIDADE 'VISITA COMERCIAL' e o CARRO.\nPara as demais finalidades, precisa informar a DATA, a FINALIDADE e o NÚMERO DO PROJETO! Ou DATA, a FINALIDADE e CARRO!");
            return;
        }
        String projeto = jFTFNumero.getText().equals("") ? null : jFTFNumero.getText().split("\\.")[1].equals("0") ? jFTFNumero.getText().split("\\.")[0] : jFTFNumero.getText();
        List<Bloco> auxBlocos = new ArrayList<>(blocos);
        for (Bloco x : auxBlocos) {
            if ((x.getDataProgramacao().contains(jDCDataProgramacao.getDate() == null ? "" : new SimpleDateFormat("dd/MM/yyyy").format(jDCDataProgramacao.getDate())) && x.getFinalidade().contains("VISITA ") && x.getCarro().getNome().contains(String.valueOf(jCBCarro.getSelectedItem()))) || x.getDataProgramacao().contains(jDCDataProgramacao.getDate() == null ? "" : new SimpleDateFormat("dd/MM/yyyy").format(jDCDataProgramacao.getDate())) && x.getFinalidade().equals(String.valueOf(jCBFinalidade.getSelectedItem())) && x.getProjeto().equals(projeto) || x.getDataProgramacao().contains(jDCDataProgramacao.getDate() == null ? "" : new SimpleDateFormat("dd/MM/yyyy").format(jDCDataProgramacao.getDate())) && x.getFinalidade().equals(String.valueOf(jCBFinalidade.getSelectedItem())) && x.getProjeto().equals("") && x.getCarro().getNome().equals(String.valueOf(jCBCarro.getSelectedItem()))) {
                blocos.remove(x);
                JOptionPane.showMessageDialog(null, "Bloco de programação removido com sucesso!");
                exists = true;
                break;
            }
        }

        if (!exists) {
            JOptionPane.showMessageDialog(null, "Não foi encontrado o bloco para remover!");
            return;
        }
        if (blocos.isEmpty()) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("blocos.txt"))) {
                writer.write("");
                limpaCampos();
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Erro ao deletar a programação!");
            }
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("blocos.txt", false))) {
    // Loop para escrever os blocos
            for (Bloco bloco : blocos) {
                String nomesColabs = bloco.getEquipe().stream().map(usuario -> String.valueOf(usuario.getId())).collect(Collectors.joining(","));
                String blocoSalvar = bloco.getId() + "|" + bloco.getDataProgramacao() + "|"
                    + bloco.getProjeto() + "|"
                    + (bloco.getCliente() == null ? null : bloco.getCliente().getId()) + "|"
                    + bloco.getFinalidade() + "|" + nomesColabs + "|"
                    + (bloco.getResponsavelDoTrabalho() == null ? null : bloco.getResponsavelDoTrabalho().getId()) + "|"
                    + (bloco.getCarro() == null ? null : bloco.getCarro().getId()) + "|"
                    + bloco.getCarretao() + "|" + bloco.getDataDeSaida() + "|"
                    + bloco.getDataDeRetorno() + "|" + bloco.getHorarioDeSaida() + "|"
                    + bloco.getHorarioDeTrabalhoInicio() + "|" + bloco.getHorarioDeTrabalhoFimMeioDia() + "|"
                    + bloco.getHorarioDeTrabalhoInicioMeioDia() + "|" + bloco.getHorarioDeTrabalhoFim() + "|"
                    + bloco.getAlmoco() + "|" + bloco.getJanta() + "|"
                    + (bloco.getHospedagem() == null || bloco.getHospedagem().equals("null") ? null : bloco.getHospedagem().getId()) + "|"
                    + bloco.getObservacoes() + "|"
                    + bloco.getCarroExtra();

                writer.write(blocoSalvar);  // Escrever a linha no arquivo
                writer.newLine();           // Adicionar uma nova linha após cada bloco
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Erro ao salvar arquivo!");
        }
        limpaCampos();
    }//GEN-LAST:event_jBRemoverActionPerformed

    private void jBLimparActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBLimparActionPerformed
        limpaCampos();
    }//GEN-LAST:event_jBLimparActionPerformed

    private void limpaCampos() {
        jCBCliente.setSelectedIndex(0);
        jCBCarro.setSelectedIndex(0);
        jCBFinalidade.setSelectedIndex(0);
        jCBCarretao.setSelectedIndex(0);
        modeloSelec.removeAllElements();
        listModel.removeAllElements();
        atualizarListas();
        jFTFNumero.setValue(null);
        jDCDataSaida.setDate(new java.util.Date(new java.util.Date().getTime() + 86400000L));
        jDCDataRetorno.setDate(new java.util.Date(new java.util.Date().getTime() + 86400000L));
        jDCDataProgramacao.setDate(new java.util.Date(new java.util.Date().getTime() + 86400000L));
        jFTFHoraSaida.setValue(null);
        jFTFHoraInicioManha.setText("07:30");
        jFTFHoraFimManha.setText("12:00");
        jFTFHoraInicioTarde.setText("13:00");
        jFTFHoraFimTarde.setText("17:18");
        jTFAlmoco.setText("");
        jTFJanta.setText("");
        jTFObservacoes.setText("");
        jBAddRespOutro.setEnabled(false);
        jBAddRespOutro.setVisible(false);
        jTFRespOutro.setEnabled(false);
        jTFRespOutro.setVisible(false);
        jTFRespOutro.setText("");
        jCBResponsavel.removeAllItems();
        jBAddRespOutro.setEnabled(false);
        jBAddRespOutro.setVisible(false);
        jTFRespOutro.setEnabled(false);
        jTFRespOutro.setVisible(false);
        jCBCarroExtra.setEnabled(false);
        jCBCarroExtra.setVisible(false);
        jCBCarroExtra.removeAllItems();
        jBOlhoCarroExtra.setVisible(false);
        jLCarro1.setVisible(false);
        jTFRespOutro.setText("");
        jCBResponsavel.removeAll();
    }

    private void jMIVerDetalhesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMIVerDetalhesActionPerformed
        Sobre sobre = new Sobre();
        sobre.setVisible(true);

        sobre.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                atualizarListas();
            }
        });
    }//GEN-LAST:event_jMIVerDetalhesActionPerformed

    private void habilitaCarroExtra(){
        jCBCarroExtra.setEnabled(true);
        jCBCarroExtra.setVisible(true);
        jBOlhoCarroExtra.setVisible(true);
        jLCarro1.setVisible(true);
        jCBCarroExtra.removeAllItems();
        for (int i = 0; i < carros.size(); i++) {
            jCBCarroExtra.addItem(carros.get(i).getNome());
        }
    }
    
    private void jBAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBAddActionPerformed
        String nomeSelecionado = jLColab.getSelectedValue();
        
        if (nomeSelecionado != null) {
            boolean isSaved = false;
            for (int i = 0; i < modeloSelec.size(); i++) {
                if (modeloSelec.elementAt(i).equals(nomeSelecionado)) {
                    isSaved = true;
                    break;
                }
            }

            if (isSaved) {
                JOptionPane.showMessageDialog(null, "O Colaborador já está selecionado!");
                return;
            }
            if (modeloSelec.size() > 10) {
                JOptionPane.showMessageDialog(null, "Atingiu o limite de colaboradores na obra!");
                return;
            }
            if (!isSaved && modeloSelec.size() <= 10) {
                modeloSelec.addElement(nomeSelecionado);
                jCBResponsavel.addItem(nomeSelecionado);
                jLSelec.setModel(modeloSelec);
                listModel.remove(jLColab.getSelectedIndex());
            }

            if (modeloSelec.size() == 5) {
                checagemCarteira();
            }
            
        } else {
            JOptionPane.showMessageDialog(null, "Nenhum colaborador selecionado na listagem!");
        }
        
        if (modeloSelec.size() >= 6 && !String.valueOf(jCBCarro.getSelectedItem()).equals("S10 PRATA ASN-8E19")) {
            habilitaCarroExtra();
        } else if (modeloSelec.size() >= 7 && String.valueOf(jCBCarro.getSelectedItem()).equals("S10 PRATA ASN-8E19")){
            habilitaCarroExtra();
        } else {
            jCBCarroExtra.setEnabled(false);
            jCBCarroExtra.setVisible(false);
            jCBCarroExtra.removeAllItems();
            jLCarro1.setVisible(false);
            jBOlhoCarroExtra.setVisible(olhoEstaClicado);
        }
    }//GEN-LAST:event_jBAddActionPerformed

    private boolean checagemCarteira() {
        List<Usuario> usuariosFiltrados = usuarios.stream()
                .filter(user -> {
                    return modeloSelec.contains(user.getNomeDeGuerra());
                })
                .collect(Collectors.toList());
        boolean hasCarteira = usuariosFiltrados.stream().anyMatch(Usuario::isCarteiraDeCarro);
        if (!hasCarteira) {
            JLabel mensagem = new JLabel("Nenhum dos colaboradores selecionados possuem carteira B!");
            mensagem.setForeground(Color.RED);
            JOptionPane.showMessageDialog(
                    null,
                    mensagem,
                    "ATENÇÃO!",
                    JOptionPane.WARNING_MESSAGE
            );
            return false;
        }
        return true;
    }

    private void jBSubActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBSubActionPerformed
        String itemSelecionado = jLSelec.getSelectedValue();
        if (itemSelecionado != null) {
            jCBResponsavel.removeItem(itemSelecionado);
            listModel.addElement(jLSelec.getSelectedValue());
            modeloSelec.remove(jLSelec.getSelectedIndex());
            atualizarRemove();
        } else {
            JOptionPane.showMessageDialog(null, "Nenhum colaborador selecionado na listagem!");
        }
        
        if (modeloSelec.size() >= 6 && !String.valueOf(jCBCarro.getSelectedItem()).equals("S10 PRATA ASN-8E19")) {
            habilitaCarroExtra();
        } else if (modeloSelec.size() >= 7 && String.valueOf(jCBCarro.getSelectedItem()).equals("S10 PRATA ASN-8E19")){
            habilitaCarroExtra();
        } else {
            jCBCarroExtra.setEnabled(false);
            jCBCarroExtra.setVisible(false);
            jCBCarroExtra.removeAllItems();
            jLCarro1.setVisible(false);
        }
    }//GEN-LAST:event_jBSubActionPerformed

    private void jFTFHoraInicioManhaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jFTFHoraInicioManhaFocusGained
        atualizaHora(jFTFHoraInicioManha);
    }//GEN-LAST:event_jFTFHoraInicioManhaFocusGained

    private void jFTFHoraInicioManhaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jFTFHoraInicioManhaFocusLost
        if (jFTFHoraInicioManha.getText().equals("")) {
            jFTFHoraInicioManha.setValue(null);
        }
    }//GEN-LAST:event_jFTFHoraInicioManhaFocusLost

    private void jFTFHoraInicioManhaInputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_jFTFHoraInicioManhaInputMethodTextChanged
    }//GEN-LAST:event_jFTFHoraInicioManhaInputMethodTextChanged

    private void jFTFHoraInicioManhaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jFTFHoraInicioManhaActionPerformed
    }//GEN-LAST:event_jFTFHoraInicioManhaActionPerformed

    private void jFTFHoraSaidaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jFTFHoraSaidaFocusGained
        atualizaHora(jFTFHoraSaida);
    }//GEN-LAST:event_jFTFHoraSaidaFocusGained

    private void jFTFHoraSaidaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jFTFHoraSaidaFocusLost
        if (jFTFHoraSaida.getText().equals("")) {
            jFTFHoraSaida.setValue(null);
        }
    }//GEN-LAST:event_jFTFHoraSaidaFocusLost

    private void jFTFHoraSaidaInputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_jFTFHoraSaidaInputMethodTextChanged
    }//GEN-LAST:event_jFTFHoraSaidaInputMethodTextChanged

    private void jFTFHoraSaidaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jFTFHoraSaidaActionPerformed
    }//GEN-LAST:event_jFTFHoraSaidaActionPerformed

    private void jFTFHoraInicioTardeFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jFTFHoraInicioTardeFocusGained
        atualizaHora(jFTFHoraInicioTarde);
    }//GEN-LAST:event_jFTFHoraInicioTardeFocusGained

    private void jFTFHoraInicioTardeFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jFTFHoraInicioTardeFocusLost
        if (jFTFHoraInicioTarde.getText().equals("")) {
            jFTFHoraInicioTarde.setValue(null);
        }
    }//GEN-LAST:event_jFTFHoraInicioTardeFocusLost

    private void jFTFHoraInicioTardeInputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_jFTFHoraInicioTardeInputMethodTextChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_jFTFHoraInicioTardeInputMethodTextChanged

    private void jFTFHoraInicioTardeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jFTFHoraInicioTardeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jFTFHoraInicioTardeActionPerformed

    private void jFTFHoraFimManhaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jFTFHoraFimManhaFocusGained
        atualizaHora(jFTFHoraFimManha);
    }//GEN-LAST:event_jFTFHoraFimManhaFocusGained

    private void jFTFHoraFimManhaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jFTFHoraFimManhaFocusLost
        if (jFTFHoraFimManha.getText().equals("")) {
            jFTFHoraFimManha.setValue(null);
        }
    }//GEN-LAST:event_jFTFHoraFimManhaFocusLost

    private void jFTFHoraFimManhaInputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_jFTFHoraFimManhaInputMethodTextChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_jFTFHoraFimManhaInputMethodTextChanged

    private void jFTFHoraFimManhaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jFTFHoraFimManhaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jFTFHoraFimManhaActionPerformed

    private void jFTFHoraFimTardeFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jFTFHoraFimTardeFocusGained
        atualizaHora(jFTFHoraFimTarde);
    }//GEN-LAST:event_jFTFHoraFimTardeFocusGained

    private void jFTFHoraFimTardeFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jFTFHoraFimTardeFocusLost
        if (jFTFHoraFimTarde.getText().equals("")) {
            jFTFHoraFimTarde.setValue(null);
        }
    }//GEN-LAST:event_jFTFHoraFimTardeFocusLost

    private void jFTFHoraFimTardeInputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_jFTFHoraFimTardeInputMethodTextChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_jFTFHoraFimTardeInputMethodTextChanged

    private void jFTFHoraFimTardeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jFTFHoraFimTardeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jFTFHoraFimTardeActionPerformed

    private void jFTFNumeroFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jFTFNumeroFocusGained
        atualizaProjeto(jFTFNumero);
    }//GEN-LAST:event_jFTFNumeroFocusGained

    private void jCBHospedagemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCBHospedagemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCBHospedagemActionPerformed

    private void jMIHoteisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMIHoteisActionPerformed
        CadastroHotel cadastroHotel = new CadastroHotel(hoteis);
        cadastroHotel.setVisible(true);

        cadastroHotel.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                atualizarListas();
            }
        });
    }//GEN-LAST:event_jMIHoteisActionPerformed

    private void jMIListaProgramacoesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMIListaProgramacoesActionPerformed
        ListagemDeProgramacoes listagemDeProgramacoes = new ListagemDeProgramacoes(demaisInfos, ultimoIdDemaisInfos, blocos, ultimoIdBloco);
        listagemDeProgramacoes.setVisible(true);
    }//GEN-LAST:event_jMIListaProgramacoesActionPerformed

    private void jBDemaisInfosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBDemaisInfosActionPerformed
        DemaisInfosView demaisInfosViw = new DemaisInfosView(demaisInfos, blocos);
        demaisInfosViw.setVisible(true);

        demaisInfosViw.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                atualizarListas();
            }
        });
    }//GEN-LAST:event_jBDemaisInfosActionPerformed

    private void jMIFolgasInternosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMIFolgasInternosActionPerformed
        DemaisInfosView demaisInfosViw = new DemaisInfosView(demaisInfos, blocos);
        demaisInfosViw.setVisible(true);

        demaisInfosViw.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                atualizarListas();
            }
        });
    }//GEN-LAST:event_jMIFolgasInternosActionPerformed

    private void jBBuscarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBBuscarMouseClicked

    }//GEN-LAST:event_jBBuscarMouseClicked

    private boolean validaSemProjeto() {
        return !String.valueOf(jCBFinalidade.getSelectedItem()).contains("VISITA ") && !String.valueOf(jCBFinalidade.getSelectedItem()).equals("SELECIONE") && jFTFNumero.getText().equals("") && !jCBCarro.getSelectedItem().toString().equals("N/A") && jCBCarro.getItemCount()!=0;
    }
    
    private boolean validaVisitaComercial() {
        return (String.valueOf(jCBFinalidade.getSelectedItem()).contains("VISITA ") || String.valueOf(jCBFinalidade.getSelectedItem()).equals("PASSAGEM DE TRABALHO")) && !String.valueOf(jCBFinalidade.getSelectedItem()).equals("SELECIONE") && !String.valueOf(jCBCarro.getSelectedItem()).equals("N/A");
    }

    private boolean validaOutros() {
        return (!String.valueOf(jCBFinalidade.getSelectedItem()).contains("VISITA ") && !String.valueOf(jCBFinalidade.getSelectedItem()).equals("SELECIONE") && !jFTFNumero.getText().equals("") && !jFTFNumero.getText().equals(".0"))|| ((String.valueOf(jCBFinalidade.getSelectedItem()).equals("PASSAGEM DE TRABALHO") || (String.valueOf(jCBFinalidade.getSelectedItem()).equals("OCULTAR") || String.valueOf(jCBFinalidade.getSelectedItem()).equals("H.H."))) && !jFTFNumero.getText().equals(".0"));
    }

    private void jBBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBBuscarActionPerformed
        boolean isVisitaComercial = validaVisitaComercial();
        
        if (isVisitaComercial) {
            for (Bloco x : blocos) {
                if (x.getDataProgramacao().contains(jDCDataProgramacao.getDate() == null ? "" : new SimpleDateFormat("dd/MM/yyyy").format(jDCDataProgramacao.getDate())) && x.getFinalidade().contains("VISITA ") && x.getCarro().getNome().contains(String.valueOf(jCBCarro.getSelectedItem()))) {
                    recuperaDados(x);
                    return;
                }
            }
        } else if (!isVisitaComercial && !jFTFNumero.getText().equals("")) {
            String projeto = jFTFNumero.getText().equals("") ? "" : jFTFNumero.getText().split("\\.")[1].equals("0") ? jFTFNumero.getText().split("\\.")[0] : jFTFNumero.getText();
            for (Bloco x : blocos) {
                if (x.getDataProgramacao().contains(jDCDataProgramacao.getDate() == null ? "" : new SimpleDateFormat("dd/MM/yyyy").format(jDCDataProgramacao.getDate())) && x.getFinalidade().equals(String.valueOf(jCBFinalidade.getSelectedItem())) && x.getProjeto().equals(projeto)) {
                    recuperaDados(x);
                    return;
                }
            }
        } else if (!isVisitaComercial && ((String.valueOf(jCBFinalidade.getSelectedItem()).equals("OCULTAR")||String.valueOf(jCBFinalidade.getSelectedItem()).equals("H.H.")) || String.valueOf(jCBFinalidade.getSelectedItem()).equals("PASSAGEM DE TRABALHO")) && !String.valueOf(jCBCliente.getSelectedItem()).equals("SELECIONE")) {
            for (Bloco x : blocos) {
                if (x.getDataProgramacao().contains(jDCDataProgramacao.getDate() == null ? "" : new SimpleDateFormat("dd/MM/yyyy").format(jDCDataProgramacao.getDate())) && (x.getFinalidade().equals("H.H.") || x.getFinalidade().equals("OCULTAR")||x.getFinalidade().equals("PASSAGEM DE TRABALHO")) && x.getCliente().getNome().equals(String.valueOf(jCBCliente.getSelectedItem()))) {
                    recuperaDados(x);
                    return;
                }
            }
        }else{
            JOptionPane.showMessageDialog(null, "Dados insuficientes para a busca!\nSe for VISITA COMPERCIAL, preencha a DATA, a FINALIDADE 'VISITA COMERCIAL' e o CARRO.\nFinalidade 'OCULTAR' precisa de pelo menos DATA, FINALIDADE e 'CLIENTE'\nPara as demais finalidades, precisa informar a DATA, a FINALIDADE e o NÚMERO DO PROJETO!");
            return;
        }
        JOptionPane.showMessageDialog(null, "Nada encontrado! Verifique se o bloco já foi criado!");
        return;
    }//GEN-LAST:event_jBBuscarActionPerformed

    private void jBAddRespOutroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBAddRespOutroActionPerformed
        String nomeSelecionado = jLColab.getSelectedValue();
        if (nomeSelecionado != null) {
            jTFRespOutro.setText(nomeSelecionado);
            listModel.remove(jLColab.getSelectedIndex());
        } else {
            JOptionPane.showMessageDialog(null, "Nenhum colaborador selecionado na listagem geral!");
        }        
    }//GEN-LAST:event_jBAddRespOutroActionPerformed

    private void jCBResponsavelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCBResponsavelActionPerformed
        boolean hasOther = false;
        for (int i = 0; i < jCBResponsavel.getItemCount(); i++) {
            if(jCBResponsavel.getItemAt(i).equals("OUTRO")){
                hasOther = true;
                break;
            }
        }
        
        if(!hasOther && jCBResponsavel.getItemCount() >= 1){
            jCBResponsavel.addItem("OUTRO");
        }
        
        if(jCBResponsavel.getSelectedItem() != null){
            if(jCBResponsavel.getSelectedItem().equals("OUTRO")){
                jBAddRespOutro.setEnabled(true);
                jBAddRespOutro.setVisible(true);
                jTFRespOutro.setEnabled(true);
                jTFRespOutro.setVisible(true);
            }else{
                jBAddRespOutro.setEnabled(false);
                jBAddRespOutro.setVisible(false);
                jTFRespOutro.setEnabled(false);
                jTFRespOutro.setVisible(false);
                jTFRespOutro.setText("");
            }
        }
    }//GEN-LAST:event_jCBResponsavelActionPerformed

    private void jDCDataProgramacaoPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jDCDataProgramacaoPropertyChange
        if (evt.getSource() instanceof JDateChooser && evt.getPropertyName().equals("date") && evt.getNewValue() instanceof Date) {
            Date novaData = (Date) evt.getNewValue();
            SimpleDateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy");
            String dataFormatada = formatoData.format(novaData);

            if (!dataSalva.equals(dataFormatada)) {
                listModel.removeAllElements();
                List<String> itensArray = listaDisponivelDoDia(jDCDataProgramacao);
                for (String item : itensArray) {
                    listModel.addElement(item);
                }
                jLColab.setModel(listModel);
            }
            dataSalva = dataFormatada;
            atualizarListas();
            jDCDataSaida.setDate(jDCDataProgramacao.getDate());
            jDCDataRetorno.setDate(jDCDataProgramacao.getDate());
        }
    }//GEN-LAST:event_jDCDataProgramacaoPropertyChange

    private void jBOlhoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBOlhoActionPerformed
        if(!olhoEstaClicado){
            ImageIcon icone = new ImageIcon(getClass().getResource("/images/olho-aberto.png"));
            Image imagemRedimensionada = icone.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
            jBOlho.setIcon(new ImageIcon(imagemRedimensionada));
            olhoEstaClicado = true;
            
            CadastroUsuario cadastroUser = new CadastroUsuario(usuarios);;
            ultimoIdUser = cadastroUser.getListaAtualizadaUsuarios().getLast().getId();
            String[] itensArray = cadastroUser.getListaAtualizadaUsuarios().stream().map(Usuario::getNomeDeGuerra).sorted().toArray(String[]::new);
            List<String> itensNaoUsados = new ArrayList<>();
            listModel.removeAllElements();
            for (String item : itensArray) {
                if(!IntStream.range(0, modeloSelec.getSize())
                    .mapToObj(modeloSelec::getElementAt)
                    .anyMatch(nome -> nome.equals(item))){
                        itensNaoUsados.add(item);
                }
            }
            
            
            for (String item : itensNaoUsados) {
                listModel.addElement(item);
            }
            jLColab.setModel(listModel);
            
        } else {
            ImageIcon icone = new ImageIcon(getClass().getResource("/images/olho-fechado.png"));
            Image imagemRedimensionada = icone.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
            jBOlho.setIcon(new ImageIcon(imagemRedimensionada));
            olhoEstaClicado = false;
            List<String> itensArray = listaDisponivelDoDia(jDCDataProgramacao);
            listModel.removeAllElements();
            for (String item : itensArray) {
                listModel.addElement(item);
            }
            jLColab.setModel(listModel);
        }
    }//GEN-LAST:event_jBOlhoActionPerformed

    private void jCBCarroMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jCBCarroMouseClicked
        if(modeloSelec.size() >= 6) {
            habilitaCarroExtra();
        }
    }//GEN-LAST:event_jCBCarroMouseClicked

    private void jCBCarroExtraMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jCBCarroExtraMouseClicked
        if(modeloSelec.size() >= 6) {
            habilitaCarroExtra();
        }
    }//GEN-LAST:event_jCBCarroExtraMouseClicked

    private void jMenuItemManutencaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemManutencaoActionPerformed
        JOptionPane.showMessageDialog(null, "Desativado!");
    }//GEN-LAST:event_jMenuItemManutencaoActionPerformed

    private void jMIHrsKMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMIHrsKMActionPerformed
        //gerenciamentoHorasEKms.setVisible(true);
        JOptionPane.showMessageDialog(null, "Desativado!");
    }//GEN-LAST:event_jMIHrsKMActionPerformed

    private void jMIAcViajensLancadasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMIAcViajensLancadasActionPerformed
        JOptionPane.showMessageDialog(null, "Desativado!");
    }//GEN-LAST:event_jMIAcViajensLancadasActionPerformed

    private void jBOlhoCarroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBOlhoCarroActionPerformed
        if(!olhoEstaCarroClicado){
            ImageIcon icone = new ImageIcon(getClass().getResource("/images/olho-aberto.png"));
            Image imagemRedimensionada = icone.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
            jBOlhoCarro.setIcon(new ImageIcon(imagemRedimensionada));
            olhoEstaCarroClicado = true;
            
            CadastroCarro cadastroCar = new CadastroCarro(carros);
            ultimoIdCarro = cadastroCar.getListaAtualizadaCarros().getLast().getId();
            String[] itensArray = cadastroCar.getListaAtualizadaCarros().stream().map(Carro::getNome).sorted().toArray(String[]::new);
            List<String> itensNaoUsados = new ArrayList<>();
            jCBCarro.removeAllItems();
            for (String item : itensArray) {
                if(!IntStream.range(0, jCBCarro.getItemCount())
                    .mapToObj(jCBCarro::getItemAt)
                    .anyMatch(nome -> nome.equals(item))){
                        itensNaoUsados.add(item);
                }
            }
            
            for (String item : itensNaoUsados) {
                jCBCarro.addItem(item);
            }
            
        } else {
            ImageIcon icone = new ImageIcon(getClass().getResource("/images/olho-fechado.png"));
            Image imagemRedimensionada = icone.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
            jBOlhoCarro.setIcon(new ImageIcon(imagemRedimensionada));
            olhoEstaCarroClicado = false;
            List<String> itensArray = listaDeCarrosDisponivelDoDia(jDCDataProgramacao);
            jCBCarro.removeAllItems();
            jCBCarro.addItem("N/A");
            for (String item : itensArray) {
                jCBCarro.addItem(item);
            }
        }
    }//GEN-LAST:event_jBOlhoCarroActionPerformed

    private void jCBCarretaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCBCarretaoActionPerformed

    }//GEN-LAST:event_jCBCarretaoActionPerformed

    private void jCBCarroExtraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCBCarroExtraActionPerformed

    }//GEN-LAST:event_jCBCarroExtraActionPerformed

    private void jBOlhoCarroExtraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBOlhoCarroExtraActionPerformed
        if(!olhoEstaCarroExtraClicado){
            ImageIcon icone = new ImageIcon(getClass().getResource("/images/olho-aberto.png"));
            Image imagemRedimensionada = icone.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
            jBOlhoCarroExtra.setIcon(new ImageIcon(imagemRedimensionada));
            olhoEstaCarroExtraClicado = true;
            
            CadastroCarro cadastroCar = new CadastroCarro(carros);
            ultimoIdCarro = cadastroCar.getListaAtualizadaCarros().getLast().getId();
            String[] itensArray = cadastroCar.getListaAtualizadaCarros().stream().map(Carro::getNome).sorted().toArray(String[]::new);
            List<String> itensNaoUsados = new ArrayList<>();
            jCBCarroExtra.removeAllItems();
            for (String item : itensArray) {
                if(!IntStream.range(0, jCBCarroExtra.getItemCount())
                    .mapToObj(jCBCarroExtra::getItemAt)
                    .anyMatch(nome -> nome.equals(item))){
                        itensNaoUsados.add(item);
                }
            }
            
            for (String item : itensNaoUsados) {
                jCBCarroExtra.addItem(item);
            }
            
        } else {
            ImageIcon icone = new ImageIcon(getClass().getResource("/images/olho-fechado.png"));
            Image imagemRedimensionada = icone.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
            jBOlhoCarroExtra.setIcon(new ImageIcon(imagemRedimensionada));
            olhoEstaCarroExtraClicado = false;
            List<String> itensArray = listaDeCarrosDisponivelDoDia(jDCDataProgramacao);
            jCBCarroExtra.removeAllItems();
            jCBCarroExtra.addItem("N/A");
            for (String item : itensArray) {
                jCBCarroExtra.addItem(item);
            }
        }
    }//GEN-LAST:event_jBOlhoCarroExtraActionPerformed

    private void recuperaDados(Bloco x) {
        try {
            if(x.getCarroExtra()!=null && !x.getCarroExtra().equals("") && !x.getCarroExtra().equals("null")){
                jCBCarroExtra.setVisible(true);
                jCBCarroExtra.setEnabled(true);
                jLCarro1.setVisible(true);
                        
                boolean encontrado = false;

                for (int i = 0; i < jCBCarroExtra.getItemCount(); i++) {
                    if (jCBCarroExtra.getItemAt(i).equals(x.getCarroExtra())) {
                        encontrado = true;
                        break;
                    }
                }

                if (encontrado) {
                    jCBCarroExtra.setSelectedItem(x.getCarroExtra());
                } else {
                    jCBCarroExtra.addItem(x.getCarroExtra());
                    jCBCarroExtra.setSelectedItem(x.getCarroExtra());
                }
            }else{
                jCBCarroExtra.setVisible(false);
                jCBCarroExtra.setEnabled(false);
                jCBCarroExtra.removeAllItems();
                jLCarro1.setVisible(false);
                jBOlhoCarroExtra.setVisible(false);
            }
            
            modeloSelec.removeAllElements();
            jCBResponsavel.removeAllItems();
            x.getEquipe().forEach(membro -> {
                String nomeDeGuerra = membro.getNomeDeGuerra();
                modeloSelec.addElement(nomeDeGuerra);
                jCBResponsavel.addItem(nomeDeGuerra);
            });
            jLSelec.setModel(modeloSelec);
            atualizarRemove();
            jDCDataProgramacao.setDate(new SimpleDateFormat("dd/MM/yyyy").parse(x.getDataProgramacao()));
            jFTFNumero.setText(x.getProjeto());
            jCBCliente.setSelectedItem(x.getCliente() != null ? x.getCliente().getNome() : "N/A");
            jCBFinalidade.setSelectedItem(x.getFinalidade());
            boolean achouResp = false;
            
            List<Usuario> equipe = new ArrayList<>();
            if (modeloSelec.getSize() > 0) {
                ArrayList<String> arrayList = new ArrayList<>(
                        IntStream.range(0, modeloSelec.getSize())
                                .mapToObj(modeloSelec::getElementAt)
                                .collect(Collectors.toList())
                );
                equipe = (ArrayList<Usuario>) usuarios.stream()
                        .filter(usuario -> arrayList.stream()
                        .anyMatch(nome -> nome.equals(usuario.getNomeDeGuerra()))
                        )
                        .collect(Collectors.toList());
            }
            for (Usuario item : equipe) {
                if (item.getNomeDeGuerra().equals(x.getResponsavelDoTrabalho().getNomeDeGuerra())) {
                    achouResp = true;
                    jCBResponsavel.setSelectedItem(x.getResponsavelDoTrabalho().getNomeDeGuerra());
                    break;
                }
            }
            if(!achouResp){
                jCBResponsavel.setSelectedItem("OUTRO");
                jBAddRespOutro.setEnabled(true);
                jBAddRespOutro.setVisible(true);
                jTFRespOutro.setEnabled(true);
                jTFRespOutro.setVisible(true);
                jTFRespOutro.setText(x.getResponsavelDoTrabalho().getNomeDeGuerra());
            }
            
            if(x.getCliente().getDistanciaEmKm()<=70){
                jCBRetornaMesmoDia.setSelected(true);
            }else if((x.getCliente().getDistanciaEmKm()>70) && (x.getDataProgramacao().equals(x.getDataDeSaida()) && x.getDataProgramacao().equals(x.getDataDeRetorno()))){
                jCBRetornaMesmoDia.setSelected(true);
            }else{
                jCBRetornaMesmoDia.setSelected(false);
            }
            
            jCBCarretao.setSelectedItem(x.getCarretao());
            jCBHospedagem.setSelectedItem(x.getHospedagem() != null ? x.getHospedagem().getNomeComCidadeEEstado() : "");
            String almoco = x.getAlmoco() == null ? "" : x.getAlmoco();
            jTFAlmoco.setText(almoco.equals("null") ? "" : almoco);
            String janta = x.getJanta() == null ? "" : x.getJanta();
            jTFJanta.setText(janta.equals("null") ? "" : janta);
            jDCDataSaida.setDate(new SimpleDateFormat("dd/MM/yyyy").parse(x.getDataDeSaida()));
            jDCDataRetorno.setDate(new SimpleDateFormat("dd/MM/yyyy").parse(x.getDataDeRetorno()));
            jFTFHoraSaida.setText(x.getHorarioDeSaida());
            jFTFHoraInicioManha.setText(x.getHorarioDeTrabalhoInicio());
            jFTFHoraFimManha.setText(x.getHorarioDeTrabalhoFimMeioDia());
            jFTFHoraInicioTarde.setText(x.getHorarioDeTrabalhoInicioMeioDia());
            jFTFHoraFimTarde.setText(x.getHorarioDeTrabalhoFim());
            String observacoes = (x.getObservacoes() == null || "null".equals(x.getObservacoes())) ? "" : x.getObservacoes();
            jTFObservacoes.setText(observacoes.equals("null") ? "" : observacoes);
            
            String valor = x.getCarro().getNome(); // O valor que você deseja verificar e selecionar
            boolean encontrado = false;

            for (int i = 0; i < jCBCarro.getItemCount(); i++) {
                if (jCBCarro.getItemAt(i).equals(valor)) {
                    encontrado = true;
                    break;
                }
            }

            if (encontrado) {
                jCBCarro.setSelectedItem(valor);
            } else {
                jCBCarro.addItem(valor);
                jCBCarro.setSelectedItem(valor);
            }
            
        } catch (ParseException ex) {
            Logger.getLogger(Programacao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void atualizaHora(JFormattedTextField horaCampo) {
        if (horaCampo.getText().equals("")) {
            try {
                MaskFormatter data = new MaskFormatter("##:##");
                data.setPlaceholderCharacter('_'); // Placeholder para caracteres não digitados
                data.setValueContainsLiteralCharacters(false); // Ignora caracteres literais
                data.install(horaCampo);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    private void atualizaProjeto(JFormattedTextField projetoCampo) {
        if (projetoCampo.getText().equals("")) {
            try {
                MaskFormatter dado = new MaskFormatter("####.##");
                dado.setPlaceholderCharacter('_'); // Placeholder para caracteres não digitados
                dado.setValueContainsLiteralCharacters(false); // Ignora caracteres literais
                dado.install(projetoCampo);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu Manutencao;
    private javax.swing.JButton jBAdd;
    private javax.swing.JButton jBAddBloco;
    private javax.swing.JButton jBAddRespOutro;
    private javax.swing.JButton jBAlterarBloco;
    private javax.swing.JButton jBBuscar;
    private javax.swing.JButton jBDemaisInfos;
    private javax.swing.JButton jBLimpar;
    private javax.swing.JButton jBOlho;
    private javax.swing.JButton jBOlhoCarro;
    private javax.swing.JButton jBOlhoCarroExtra;
    private javax.swing.JButton jBRemover;
    private javax.swing.JButton jBSub;
    private javax.swing.JComboBox<String> jCBCarretao;
    private javax.swing.JComboBox<String> jCBCarro;
    private javax.swing.JComboBox<String> jCBCarroExtra;
    public javax.swing.JComboBox<String> jCBCliente;
    private javax.swing.JComboBox<String> jCBFinalidade;
    public javax.swing.JComboBox<String> jCBHospedagem;
    private javax.swing.JComboBox<String> jCBResponsavel;
    private javax.swing.JCheckBox jCBRetornaMesmoDia;
    private com.toedter.calendar.JDateChooser jDCDataProgramacao;
    private com.toedter.calendar.JDateChooser jDCDataRetorno;
    private com.toedter.calendar.JDateChooser jDCDataSaida;
    private javax.swing.JFormattedTextField jFTFHoraFimManha;
    private javax.swing.JFormattedTextField jFTFHoraFimTarde;
    private javax.swing.JFormattedTextField jFTFHoraInicioManha;
    private javax.swing.JFormattedTextField jFTFHoraInicioTarde;
    private javax.swing.JFormattedTextField jFTFHoraSaida;
    private javax.swing.JFormattedTextField jFTFNumero;
    private javax.swing.JLabel jLAlmoco;
    private javax.swing.JLabel jLCarretao;
    private javax.swing.JLabel jLCarro;
    private javax.swing.JLabel jLCarro1;
    private javax.swing.JLabel jLCliente;
    private javax.swing.JList<String> jLColab;
    private javax.swing.JLabel jLColaboradores;
    private javax.swing.JLabel jLDataRetorno;
    private javax.swing.JLabel jLDataSaida;
    private javax.swing.JLabel jLFinalidade;
    private javax.swing.JLabel jLFundo;
    private javax.swing.JLabel jLHoraFimManha;
    private javax.swing.JLabel jLHoraFimTarde;
    private javax.swing.JLabel jLHoraInicioManha;
    private javax.swing.JLabel jLHoraInicioTarde;
    private javax.swing.JLabel jLHoraSaida;
    private javax.swing.JLabel jLHorarioDeTrabalho;
    private javax.swing.JLabel jLHospedagem;
    private javax.swing.JLabel jLJanta;
    private javax.swing.JLabel jLNumero;
    private javax.swing.JLabel jLObservacoes;
    private javax.swing.JLabel jLProg;
    private javax.swing.JLabel jLProgramDia;
    private javax.swing.JLabel jLResponsavel;
    private javax.swing.JList<String> jLSelec;
    private javax.swing.JLabel jLSelecionados;
    private javax.swing.JLabel jLbAlertLaranja;
    private javax.swing.JLabel jLbAlertVermelho;
    private javax.swing.JLabel jLbAlertaLaranja;
    private javax.swing.JLabel jLbVermelho;
    private javax.swing.JMenu jMCadastros;
    private javax.swing.JMenu jMGerenciamento;
    private javax.swing.JMenuItem jMIAcViajensLancadas;
    private javax.swing.JMenuItem jMICarros;
    private javax.swing.JMenuItem jMICliente;
    private javax.swing.JMenuItem jMIFolgasInternos;
    private javax.swing.JMenuItem jMIHoteis;
    private javax.swing.JMenuItem jMIHrsKM;
    private javax.swing.JMenuItem jMIListaProgramacoes;
    private javax.swing.JMenuItem jMIUsers;
    private javax.swing.JMenuItem jMIVerDetalhes;
    private javax.swing.JMenu jMProgramacoes;
    private javax.swing.JMenu jMSobre;
    private javax.swing.JMenuBar jMenuBar;
    private javax.swing.JMenuItem jMenuItemManutencao;
    private javax.swing.JPanel jPFundo;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jSPColaboradores;
    private javax.swing.JScrollPane jSPSelecionados;
    private javax.swing.JTextField jTFAlmoco;
    private javax.swing.JTextField jTFJanta;
    private javax.swing.JTextField jTFObservacoes;
    private javax.swing.JTextField jTFRespOutro;
    // End of variables declaration//GEN-END:variables
}
