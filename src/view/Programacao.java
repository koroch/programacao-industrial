/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view;

import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;
import programacao.model.Carro;
import programacao.model.Cliente;
import programacao.model.Estado;
import programacao.model.Usuario;
import javax.swing.text.MaskFormatter;
import programacao.model.Bloco;
import programacao.model.DemaisInfos;
import programacao.model.Hotel;
/**
 *
 * @author Koroch
 */
public class Programacao extends javax.swing.JFrame {
    private List<String> linhasArquivoBlocoProgramacao = new ArrayList<>();
    private List<Bloco> blocos = new ArrayList<>();
    public static int ultimoIdBloco = 0;
    
    private DefaultListModel<String> modeloSelec = new DefaultListModel<>();
    private DefaultListModel<String> listModel = new DefaultListModel<>();
    public static ImageIcon notificacaoIco = new ImageIcon("../images/notification.png");
    
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
    
    public Programacao() {
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        initComponents();
        
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
                usuarios.add(new Usuario(
                    Integer.parseInt(dados[0].trim()),
                    dados[1].trim(),
                    dados[2].trim(),
                    dados[3].trim(),
                    Boolean.parseBoolean(dados[4].trim())
                ));
            });
            
            if(!usuarios.isEmpty()){
                ultimoIdUser = usuarios.getLast().getId();
                String[] itensArray = usuarios.stream().map(Usuario::getNomeDeGuerra).sorted().toArray(String[]::new);
                for (String item : itensArray) {
                    listModel.addElement(item);
                }
                jLColab.setModel(listModel);
            }
        } catch (IOException e ) {
            JOptionPane.showMessageDialog(null, "Erro no arquivo usuarios.txt, talvez ele ainda esteja vazio!");
        }
        
        try (BufferedReader br = new BufferedReader(new FileReader("carros.txt"))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                linhasArquivoCarro.add(linha);
            }
            
            linhasArquivoCarro.forEach(elemento -> {
                String[] dados = elemento.split("\\|");
                carros.add(new Carro(
                    Integer.parseInt(dados[0].trim()),
                    dados[1].trim(),
                    dados[2].trim(),
                    dados[3].trim()
                ));
                
            });
            
            if(!carros.isEmpty()){
                ultimoIdCarro = carros.getLast().getId();
                carros.forEach(x -> {
                    jCBCarro.addItem(x.getNome());
                });
            }
        } catch (IOException e ) {
            JOptionPane.showMessageDialog(null, "Erro no arquivo carros.txt, talvez ele ainda esteja vazio!");
        }
        
        try (BufferedReader br = new BufferedReader(new FileReader("clientes.txt"))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                linhasArquivoCliente.add(linha);
            }
            
            linhasArquivoCliente.forEach(elemento -> {
                String[] dados = elemento.split("\\|");
                clientes.add(new Cliente(
                    Integer.parseInt(dados[0].trim()),
                    dados[1].trim(),
                    dados[2].trim(),
                    Estado.valueOf(dados[3].trim())
                ));
                
            });
            
            if(!clientes.isEmpty()){
                ultimoIdCliente = clientes.getLast().getId();
                clientes.forEach(x -> {
                    jCBCliente.addItem(x.getNome());
                });
            }
        } catch (IOException e ) {
            JOptionPane.showMessageDialog(null, "Erro no arquivo clientes.txt, talvez ele ainda esteja vazio!");
        }
        
        try (BufferedReader br = new BufferedReader(new FileReader("hoteis.txt"))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                linhasArquivoHotel.add(linha);
            }
            
            linhasArquivoHotel.forEach(elemento -> {
                String[] dados = elemento.split("\\|");
                hoteis.add(new Hotel(
                    Integer.parseInt(dados[0].trim()),
                    dados[1].trim(),
                    dados[2].trim(),
                    Estado.valueOf(dados[3].trim()),
                    dados[4].trim()
                ));
                
            });
            
            if(!hoteis.isEmpty()){
                ultimoIdHotel = hoteis.getLast().getId();
                hoteis.forEach(x -> {
                    jCBHospedagem.addItem(x.getNomeComCidadeEEstado());
                });
            }
        } catch (IOException e ) {
            JOptionPane.showMessageDialog(null, "Erro no arquivo hoteis.txt, talvez ele ainda esteja vazio!");
        }
        
        try (BufferedReader br = new BufferedReader(new FileReader("blocos.txt"))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                linhasArquivoBlocoProgramacao.add(linha);
            }
            
            linhasArquivoBlocoProgramacao.forEach(elemento -> {
                String[] dados = elemento.split("\\|");

                String[] dados4Array = dados[5].trim().replace("[", "").replace("]", "").split(",");
                Usuario usuarioHelper = new Usuario();
                List<Usuario> listaDaEquipe = Arrays.stream(dados4Array)
                                  .map(idStr -> Integer.parseInt(idStr)) // Converte cada String para int
                                  .map(id -> usuarioHelper.getById(id, usuarios)) // Busca o nome do usuário pelo ID
                                  .collect(Collectors.toList());
                
                blocos.add(new Bloco(
                    Integer.parseInt(dados[0].trim()),
                    dados[1].trim(), //data
                    dados[2].trim(), //proj
                    dados[3].trim().equals("null")? null : new Cliente().getById(Integer.parseInt(dados[3].trim()), clientes), //client conv
                    dados[4].trim(), // finalidade
                    listaDaEquipe, //equipe
                    usuarioHelper.getById(Integer.parseInt(dados[6].trim()), usuarios), //user resp
                    dados[7].trim().equals("null")? null : new Carro().getById(Integer.parseInt(dados[7].trim()), carros), //carro
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
                    dados[18].trim().equals("null")? null : new Hotel().getById(Integer.parseInt(dados[18].trim()), hoteis) //hotel
                ));
            });
            
            if(!blocos.isEmpty()){
                ultimoIdBloco = blocos.getLast().getId();
            }
        } catch (IOException e ) {
            JOptionPane.showMessageDialog(null, "Erro no arquivo blocos.txt, talvez ele ainda esteja vazio!");
        }
        
        try (BufferedReader br = new BufferedReader(new FileReader("demaisInfos.txt"))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                linhasArquivoDemaisInfos.add(linha);
            }
            
            linhasArquivoDemaisInfos.forEach(elemento -> {
                String[] dados = elemento.split("\\|");
                
                Usuario usuarioHelper = new Usuario();
                
                demaisInfos.add(new DemaisInfos(
                    Integer.parseInt(dados[0].trim()),
                    dados[1].trim(),
                    stringToUser(dados[2].trim(), usuarioHelper),
                    stringToUser(dados[3].trim(), usuarioHelper),
                    stringToUser(dados[4].trim(), usuarioHelper),
                    stringToUser(dados[5].trim(), usuarioHelper)
                ));
            });
            
            if(!demaisInfos.isEmpty()){
                ultimoIdDemaisInfos = demaisInfos.getLast().getId();
            }
        } catch (IOException e ) {
            JOptionPane.showMessageDialog(null, "Erro no arquivo demaisInfos.txt, talvez ele ainda esteja vazio!");
        }
    }
    
    private List<Usuario> stringToUser(String dado, Usuario usuarioHelper){
        if(dado == null || dado.equals("")){
            return null;
        }
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

        jLProg = new javax.swing.JLabel();
        jCB = new javax.swing.JPanel();
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
        jCBResponsavel = new javax.swing.JComboBox<>();
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
        jLFundo = new javax.swing.JLabel();
        jMenuBar = new javax.swing.JMenuBar();
        jMCadastros = new javax.swing.JMenu();
        jMIUsers = new javax.swing.JMenuItem();
        jMICarros = new javax.swing.JMenuItem();
        jMICliente = new javax.swing.JMenuItem();
        jMIHoteis = new javax.swing.JMenuItem();
        jMProgramacoes = new javax.swing.JMenu();
        jMIListaProgramacoes = new javax.swing.JMenuItem();
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

        jCB.setBackground(new java.awt.Color(7, 30, 74));
        jCB.setToolTipText("");
        jCB.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jCB.setFocusable(false);
        jCB.setName(""); // NOI18N
        jCB.setLayout(null);

        jLNumero.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        jLNumero.setForeground(new java.awt.Color(255, 255, 255));
        jLNumero.setText("Número do Projeto:");
        jLNumero.setToolTipText("");
        jCB.add(jLNumero);
        jLNumero.setBounds(820, 10, 110, 14);

        jFTFNumero.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("####.##"))));
        jFTFNumero.setToolTipText("Informe o número do projeto");
        jFTFNumero.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jFTFNumeroFocusGained(evt);
            }
        });
        jCB.add(jFTFNumero);
        jFTFNumero.setBounds(820, 30, 180, 30);

        jTFAlmoco.setToolTipText("Se haver, informe o local de almoço!");
        jCB.add(jTFAlmoco);
        jTFAlmoco.setBounds(500, 290, 500, 30);

        jLAlmoco.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        jLAlmoco.setForeground(new java.awt.Color(255, 255, 255));
        jLAlmoco.setText("Local de almoço:");
        jLAlmoco.setToolTipText("");
        jCB.add(jLAlmoco);
        jLAlmoco.setBounds(410, 300, 80, 16);

        jLHospedagem.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        jLHospedagem.setForeground(new java.awt.Color(255, 255, 255));
        jLHospedagem.setText("Hospedagem: ");
        jLHospedagem.setToolTipText("");
        jCB.add(jLHospedagem);
        jLHospedagem.setBounds(410, 260, 90, 16);

        jLCliente.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        jLCliente.setForeground(new java.awt.Color(255, 255, 255));
        jLCliente.setText("Cliente:");
        jLCliente.setToolTipText("");
        jCB.add(jLCliente);
        jLCliente.setBounds(610, 10, 110, 14);

        jCBHospedagem.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "N/A" }));
        jCBHospedagem.setToolTipText("Selecione o Hotel!");
        jCBHospedagem.setBorder(null);
        jCBHospedagem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCBHospedagemActionPerformed(evt);
            }
        });
        jCB.add(jCBHospedagem);
        jCBHospedagem.setBounds(500, 250, 500, 30);

        jLColaboradores.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        jLColaboradores.setForeground(new java.awt.Color(255, 255, 255));
        jLColaboradores.setText("Nome do Colaborador:");
        jLColaboradores.setToolTipText("");
        jCB.add(jLColaboradores);
        jLColaboradores.setBounds(160, 80, 130, 14);

        jLColab.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jLColab.setToolTipText("");
        jSPColaboradores.setViewportView(jLColab);

        jCB.add(jSPColaboradores);
        jSPColaboradores.setBounds(160, 100, 180, 260);

        jLSelecionados.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        jLSelecionados.setForeground(new java.awt.Color(255, 255, 255));
        jLSelecionados.setText("Colaboradores Selecionados:");
        jLSelecionados.setToolTipText("");
        jCB.add(jLSelecionados);
        jLSelecionados.setBounds(410, 80, 160, 14);

        jLSelec.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jLSelec.setToolTipText("");
        jSPSelecionados.setViewportView(jLSelec);

        jCB.add(jSPSelecionados);
        jSPSelecionados.setBounds(410, 100, 170, 120);

        jBSub.setFont(new java.awt.Font("Segoe UI", 1, 8)); // NOI18N
        jBSub.setText("<<");
        jBSub.setToolTipText("");
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
        jCB.add(jBSub);
        jBSub.setBounds(350, 160, 50, 30);

        jBAdd.setFont(new java.awt.Font("Segoe UI", 1, 8)); // NOI18N
        jBAdd.setText(">>");
        jBAdd.setToolTipText("");
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
        jCB.add(jBAdd);
        jBAdd.setBounds(350, 120, 50, 30);

        jLResponsavel.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        jLResponsavel.setForeground(new java.awt.Color(255, 255, 255));
        jLResponsavel.setText("Selecione o Responsável da Obra:");
        jLResponsavel.setToolTipText("");
        jCB.add(jLResponsavel);
        jLResponsavel.setBounds(610, 100, 160, 14);

        jCBResponsavel.setToolTipText("Selecione o Responsável dentre os que vão para Obra!");
        jCB.add(jCBResponsavel);
        jCBResponsavel.setBounds(610, 120, 180, 30);

        jLCarro.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        jLCarro.setForeground(new java.awt.Color(255, 255, 255));
        jLCarro.setText("Selecione o Carro:");
        jLCarro.setToolTipText("");
        jCB.add(jLCarro);
        jLCarro.setBounds(610, 170, 160, 14);

        jCBCarro.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "N/A" }));
        jCBCarro.setToolTipText("Selecione o carro!");
        jCB.add(jCBCarro);
        jCBCarro.setBounds(610, 190, 180, 30);

        jBAddBloco.setBackground(new java.awt.Color(204, 204, 204));
        jBAddBloco.setText("Adicionar");
        jBAddBloco.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jBAddBloco.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBAddBlocoActionPerformed(evt);
            }
        });
        jCB.add(jBAddBloco);
        jBAddBloco.setBounds(250, 510, 110, 30);

        jBAlterarBloco.setBackground(new java.awt.Color(204, 204, 204));
        jBAlterarBloco.setText("Alterar");
        jBAlterarBloco.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jBAlterarBloco.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBAlterarBlocoActionPerformed(evt);
            }
        });
        jCB.add(jBAlterarBloco);
        jBAlterarBloco.setBounds(380, 510, 110, 30);

        jBRemover.setBackground(new java.awt.Color(204, 204, 204));
        jBRemover.setText("Remover");
        jBRemover.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jBRemover.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBRemoverActionPerformed(evt);
            }
        });
        jCB.add(jBRemover);
        jBRemover.setBounds(510, 510, 110, 30);

        jBLimpar.setBackground(new java.awt.Color(204, 204, 204));
        jBLimpar.setText("❌ Limpar Campos");
        jBLimpar.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jBLimpar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBLimparActionPerformed(evt);
            }
        });
        jCB.add(jBLimpar);
        jBLimpar.setBounds(640, 510, 130, 30);

        jLDataSaida.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        jLDataSaida.setForeground(new java.awt.Color(255, 255, 255));
        jLDataSaida.setText("Data de Saída:");
        jLDataSaida.setToolTipText("");
        jCB.add(jLDataSaida);
        jLDataSaida.setBounds(250, 410, 90, 16);

        jLDataRetorno.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        jLDataRetorno.setForeground(new java.awt.Color(255, 255, 255));
        jLDataRetorno.setText("Data de Retorno: ");
        jLDataRetorno.setToolTipText("");
        jCB.add(jLDataRetorno);
        jLDataRetorno.setBounds(250, 450, 110, 16);

        jLHoraSaida.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        jLHoraSaida.setForeground(new java.awt.Color(255, 255, 255));
        jLHoraSaida.setText("Hora de Saída:");
        jLHoraSaida.setToolTipText("");
        jCB.add(jLHoraSaida);
        jLHoraSaida.setBounds(490, 410, 90, 16);

        jFTFHoraSaida.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(java.text.DateFormat.getTimeInstance(java.text.DateFormat.SHORT))));
        jFTFHoraSaida.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jFTFHoraSaida.setName(""); // NOI18N
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
        jCB.add(jFTFHoraSaida);
        jFTFHoraSaida.setBounds(570, 400, 50, 30);

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
        jFTFHoraFimTarde.setName(""); // NOI18N
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

        jCB.add(jPanel1);
        jPanel1.setBounds(630, 380, 290, 110);

        jLJanta.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        jLJanta.setForeground(new java.awt.Color(255, 255, 255));
        jLJanta.setText("Local de janta:");
        jLJanta.setToolTipText("");
        jCB.add(jLJanta);
        jLJanta.setBounds(410, 340, 80, 16);

        jTFJanta.setToolTipText("Se haver, informe o local de janta!");
        jCB.add(jTFJanta);
        jTFJanta.setBounds(500, 330, 500, 30);

        jCBCliente.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "N/A" }));
        jCBCliente.setToolTipText("Escolha o cliente!");
        jCBCliente.setBorder(null);
        jCB.add(jCBCliente);
        jCBCliente.setBounds(610, 30, 180, 30);

        jDCDataRetorno.setDate(new java.util.Date(new java.util.Date().getTime() + 86400000L));
        jDCDataRetorno.setDateFormatString("dd/MM/yyyy");
        jCB.add(jDCDataRetorno);
        jDCDataRetorno.setBounds(340, 440, 130, 30);

        jDCDataSaida.setDate(new java.util.Date(new java.util.Date().getTime() + 86400000L));
        jDCDataSaida.setDateFormatString("dd/MM/yyyy");
        jCB.add(jDCDataSaida);
        jDCDataSaida.setBounds(340, 400, 130, 30);

        jDCDataProgramacao.setDate(new java.util.Date(new java.util.Date().getTime() + 86400000L));
        jDCDataProgramacao.setDateFormatString("dd/MM/yyyy");
        jCB.add(jDCDataProgramacao);
        jDCDataProgramacao.setBounds(160, 30, 180, 30);

        jLProgramDia.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        jLProgramDia.setForeground(new java.awt.Color(255, 255, 255));
        jLProgramDia.setText("Adicionar para a programação do dia:");
        jLProgramDia.setToolTipText("");
        jCB.add(jLProgramDia);
        jLProgramDia.setBounds(160, 10, 210, 14);

        jCBFinalidade.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "SELECIONE", "ADEQUAÇÃO NR-12", "ELÉTRICA", "MECÂNICA", "LEVANTAMENTO TÉCNICO", "SPDA", "PASSAGEM DE TRABALHO", "VISITA COMERCIAL" }));
        jCB.add(jCBFinalidade);
        jCBFinalidade.setBounds(370, 30, 210, 30);

        jLFinalidade.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        jLFinalidade.setForeground(new java.awt.Color(255, 255, 255));
        jLFinalidade.setText("Finalidade:");
        jLFinalidade.setToolTipText("");
        jCB.add(jLFinalidade);
        jLFinalidade.setBounds(370, 10, 110, 14);

        jCBCarretao.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "N/A", "CARRETÃO GRANDE", "CARRETÂO PEQUENO" }));
        jCBCarretao.setToolTipText("Selecione o carretão, caso precise!");
        jCB.add(jCBCarretao);
        jCBCarretao.setBounds(820, 190, 180, 30);

        jLCarretao.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        jLCarretao.setForeground(new java.awt.Color(255, 255, 255));
        jLCarretao.setText("Selecione o Carretão:");
        jLCarretao.setToolTipText("");
        jCB.add(jLCarretao);
        jLCarretao.setBounds(820, 170, 160, 14);

        jBDemaisInfos.setBackground(new java.awt.Color(204, 204, 204));
        jBDemaisInfos.setText("Adicionar Folgas e Internos");
        jBDemaisInfos.setToolTipText("Definir Folga, Férias e Internos");
        jBDemaisInfos.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jBDemaisInfos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBDemaisInfosActionPerformed(evt);
            }
        });
        jCB.add(jBDemaisInfos);
        jBDemaisInfos.setBounds(790, 510, 160, 30);

        getContentPane().add(jCB);
        jCB.setBounds(0, 40, 1200, 570);

        jLFundo.setBackground(new java.awt.Color(51, 51, 51));
        jLFundo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/tela_de_fundo.jpeg"))); // NOI18N
        jLFundo.setToolTipText("");
        getContentPane().add(jLFundo);
        jLFundo.setBounds(-50, -200, 1360, 1120);

        jMenuBar.setBackground(new java.awt.Color(153, 153, 153));
        jMenuBar.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jMenuBar.setAutoscrolls(true);
        jMenuBar.setBorderPainted(false);
        jMenuBar.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jMenuBar.setPreferredSize(new java.awt.Dimension(70, 50));

        jMCadastros.setText("Cadastros");
        jMCadastros.setAutoscrolls(true);
        jMCadastros.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMCadastros.setRolloverEnabled(false);
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

        jMenuBar.add(jMProgramacoes);

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

        setSize(new java.awt.Dimension(1209, 725));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jMCadastrosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMCadastrosActionPerformed
        // TODO add your handling code here:
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
        if(!cadastroUser.getListaAtualizadaUsuarios().isEmpty()){
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
        }
    }
    
    private void atualizarListas(){
        CadastroCliente cadastroCliente = new CadastroCliente(clientes);
        if(!cadastroCliente.getListaAtualizadaClientes().isEmpty()){
            ultimoIdCliente = cadastroCliente.getListaAtualizadaClientes().getLast().getId();
            jCBCliente.removeAllItems();
            jCBCliente.addItem("N/A");
            cadastroCliente.getListaAtualizadaClientes().forEach(x -> {
                jCBCliente.addItem(x.getNome());
            });
        }
        
        CadastroHotel cadastroHotel = new CadastroHotel(hoteis);
        if(!cadastroHotel.getListaAtualizadaHoteis().isEmpty()){
            ultimoIdHotel = cadastroCliente.getListaAtualizadaClientes().getLast().getId();
            jCBHospedagem.removeAllItems();
            jCBHospedagem.addItem("N/A");
            cadastroHotel.getListaAtualizadaHoteis().forEach(x -> {
                jCBHospedagem.addItem(x.getNomeComCidadeEEstado());
            });
        }
        
        CadastroCarro cadastroCarro = new CadastroCarro(carros);
        if(!cadastroCarro.getListaAtualizadaCarros().isEmpty()){
            ultimoIdCliente = cadastroCarro.getListaAtualizadaCarros().getLast().getId();
            jCBCarro.removeAllItems();
            jCBCarro.addItem("N/A");
            cadastroCarro.getListaAtualizadaCarros().forEach(x -> {
                jCBCarro.addItem(x.getNome());
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
                
                Usuario usuarioHelper = new Usuario();
                
                demaisInfos.add(new DemaisInfos(
                    Integer.parseInt(dados[0].trim()),
                    dados[1].trim(),
                    stringToUser(dados[2].trim(), usuarioHelper),
                    stringToUser(dados[3].trim(), usuarioHelper),
                    stringToUser(dados[4].trim(), usuarioHelper),
                    stringToUser(dados[5].trim(), usuarioHelper)
                ));
            });
            
            if(!demaisInfos.isEmpty()){
                ultimoIdDemaisInfos = demaisInfos.getLast().getId();
            }
        } catch (IOException e ) {
            JOptionPane.showMessageDialog(null, "Erro no arquivo demaisInfos.txt, talvez ele ainda esteja vazio!");
        }
        
        atualizarRemove();
    }
    
    private void jBAddBlocoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBAddBlocoActionPerformed
        String dataProgramacao = jDCDataProgramacao.getDate()==null?"":new SimpleDateFormat("dd/MM/yyyy").format(jDCDataProgramacao.getDate()); 
        String projeto = jFTFNumero.getText().equals("") ? null : jFTFNumero.getText().split("\\.")[1].equals("0") ? jFTFNumero.getText().split("\\.")[0] : jFTFNumero.getText();
        Cliente cliente = new Cliente().getByNameEmpresa(String.valueOf(jCBCliente.getSelectedItem()), clientes);
        String finalidade = String.valueOf(jCBFinalidade.getSelectedItem());
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
        if(jCBResponsavel.getSelectedItem() == null){
            JOptionPane.showMessageDialog(null, "Selecione e Adicione ao menos um membro a equipe!", "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }
        Usuario responsavel = new Usuario().getByNameDeGuerra(String.valueOf(jCBResponsavel.getSelectedItem()), usuarios);
        
        checagemCarteira();
                
        Carro carro = new Carro().getByName(String.valueOf(jCBCarro.getSelectedItem()), carros);
        String carretao = String.valueOf(jCBCarretao.getSelectedItem());
        Hotel hotel = new Hotel().getByNameHotel(String.valueOf(jCBHospedagem.getSelectedItem()).split(",")[0], hoteis);
        String almoco = jTFAlmoco.getText().equals("")?null:jTFAlmoco.getText();
        String janta = jTFJanta.getText().equals("")?null:jTFJanta.getText();
        String dataSaida = jDCDataSaida.getDate()==null?"":new SimpleDateFormat("dd/MM/yyyy").format(jDCDataSaida.getDate()); 
        String dataRetorno = jDCDataRetorno.getDate()==null?"":new SimpleDateFormat("dd/MM/yyyy").format(jDCDataRetorno.getDate());
        String horaSaida = jFTFHoraSaida.getText();
        String horaManhaInicio = jFTFHoraInicioManha.getText();
        String horaManhaFim = jFTFHoraFimManha.getText();
        String horaTardeInicio = jFTFHoraInicioTarde.getText();
        String horaTardeFim = jFTFHoraFimTarde.getText();
        if(dataProgramacao.equals("") || dataSaida.equals("") || dataRetorno.equals("") || horaSaida.equals("")){
            JOptionPane.showMessageDialog(null, "Preencha corretamente todas datas e hora de saída!", "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        Bloco bloco = new Bloco(dataProgramacao, projeto, cliente, finalidade, equipe, responsavel, carro, carretao, dataSaida, dataRetorno, horaSaida, horaManhaInicio, horaManhaFim, horaTardeInicio, horaTardeFim, almoco, janta, hotel);
                
        blocos.add(bloco);
        String nomesColabs =  bloco.getEquipe().stream().map(usuario -> String.valueOf(usuario.getId())).collect(Collectors.joining(","));
        String blocoSalvar = bloco.getId() +"|"+ dataProgramacao +"|"+ projeto +"|"+ (cliente==null?null:cliente.getId()) +"|"+ finalidade +"|"+ nomesColabs +"|"+ (responsavel==null?null:responsavel.getId()) +"|"+ (carro==null?null:carro.getId()) +"|"+ carretao +"|"+ dataSaida +"|"+ dataRetorno +"|"+ horaSaida +"|"+ horaManhaInicio +"|"+ horaManhaFim +"|"+ horaTardeInicio +"|"+ horaTardeFim +"|"+ almoco +"|"+ janta +"|"+ (hotel==null?null:hotel.getId());
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("blocos.txt", true))) {
            writer.write(blocoSalvar);
            writer.newLine();
            JOptionPane.showMessageDialog(null, "Bloco de programação gravado com sucesso!");
            limpaCampos();
        } catch (IOException e ) {
            JOptionPane.showMessageDialog(null, "Erro ao salvar aquivo!");
        }

        
    }//GEN-LAST:event_jBAddBlocoActionPerformed

    private void jBAlterarBlocoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBAlterarBlocoActionPerformed

        limpaCampos();
    }//GEN-LAST:event_jBAlterarBlocoActionPerformed

    private void jBRemoverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBRemoverActionPerformed
        
        limpaCampos();
    }//GEN-LAST:event_jBRemoverActionPerformed

    private void jBLimparActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBLimparActionPerformed
        limpaCampos();
        jDCDataProgramacao.setDate(null);
    }//GEN-LAST:event_jBLimparActionPerformed

    private void limpaCampos(){
        modeloSelec.removeAllElements();
        jCBResponsavel.removeAllItems();
        listModel.removeAllElements();
        atualizarListas();
        jFTFNumero.setValue(null);
        jDCDataSaida.setDate(null);
        jDCDataRetorno.setDate(null);
        jFTFHoraSaida.setValue(null);
        jFTFHoraInicioManha.setValue(null);
        jFTFHoraFimManha.setValue(null);
        jFTFHoraInicioTarde.setValue(null);
        jFTFHoraFimTarde.setValue(null);
        jTFAlmoco.setText("");
        jTFJanta.setText("");
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

    private void jBAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBAddActionPerformed
        String nomeSelecionado = jLColab.getSelectedValue();
        
        if (nomeSelecionado != null) {
            boolean isSaved = false;
            for (int i = 0; i < modeloSelec.size(); i++) {
                if(modeloSelec.elementAt(i).equals(nomeSelecionado)){
                    isSaved = true;
                    break;
                }
            }

            if(isSaved){
                JOptionPane.showMessageDialog(null, "O Colaborador já está selecionado!");
                return;
            }
            if(modeloSelec.size()>=5){
                JOptionPane.showMessageDialog(null, "Não tem mais espaço no carro!");
                return;
            }
            if(!isSaved && modeloSelec.size()<5){
                modeloSelec.addElement(nomeSelecionado);
                jCBResponsavel.addItem(nomeSelecionado);
                jLSelec.setModel(modeloSelec);
                listModel.remove(jLColab.getSelectedIndex());
            }
            
            if(modeloSelec.size()==5){
                checagemCarteira();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Nenhum colaborador selecionado na listagem!");
        }
    }//GEN-LAST:event_jBAddActionPerformed

    private boolean checagemCarteira() {
        List<Usuario> usuariosFiltrados = usuarios.stream()
                .filter(user -> {
                    return modeloSelec.contains(user.getNomeDeGuerra());
                })
                .collect(Collectors.toList());
            boolean hasCarteira = usuariosFiltrados.stream().anyMatch(Usuario::isCarteiraDeCarro);
            if(!hasCarteira){
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
    }//GEN-LAST:event_jBSubActionPerformed

    private void jFTFHoraInicioManhaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jFTFHoraInicioManhaFocusGained
        atualizaHora(jFTFHoraInicioManha);
    }//GEN-LAST:event_jFTFHoraInicioManhaFocusGained

    private void jFTFHoraInicioManhaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jFTFHoraInicioManhaFocusLost
        if(jFTFHoraInicioManha.getText().equals("")){
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
        if(jFTFHoraSaida.getText().equals("")){
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
        if(jFTFHoraInicioTarde.getText().equals("")){
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
        if(jFTFHoraFimManha.getText().equals("")){
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
        if(jFTFHoraFimTarde.getText().equals("")){
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
        DemaisInfosView demaisInfosViw = new DemaisInfosView(demaisInfos, ultimoIdDemaisInfos);
        demaisInfosViw.setVisible(true);
        
        demaisInfosViw.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                atualizarListas();
            }
        });
    }//GEN-LAST:event_jBDemaisInfosActionPerformed

    private void atualizaData(JFormattedTextField dataCampo){
        if(dataCampo.getText().equals("")){
            try {
                MaskFormatter data = new MaskFormatter("##/##/####");
                data.setPlaceholderCharacter('_'); // Placeholder para caracteres não digitados
                data.setValueContainsLiteralCharacters(false); // Ignora caracteres literais
                data.install(dataCampo);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }
    
    private void atualizaHora(JFormattedTextField horaCampo){
        if(horaCampo.getText().equals("")){
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
    
    private void atualizaProjeto(JFormattedTextField projetoCampo){
        if(projetoCampo.getText().equals("")){
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
    private javax.swing.JButton jBAdd;
    private javax.swing.JButton jBAddBloco;
    private javax.swing.JButton jBAlterarBloco;
    private javax.swing.JButton jBDemaisInfos;
    private javax.swing.JButton jBLimpar;
    private javax.swing.JButton jBRemover;
    private javax.swing.JButton jBSub;
    private javax.swing.JPanel jCB;
    private javax.swing.JComboBox<String> jCBCarretao;
    private javax.swing.JComboBox<String> jCBCarro;
    public javax.swing.JComboBox<String> jCBCliente;
    private javax.swing.JComboBox<String> jCBFinalidade;
    public javax.swing.JComboBox<String> jCBHospedagem;
    private javax.swing.JComboBox<String> jCBResponsavel;
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
    private javax.swing.JLabel jLProg;
    private javax.swing.JLabel jLProgramDia;
    private javax.swing.JLabel jLResponsavel;
    private javax.swing.JList<String> jLSelec;
    private javax.swing.JLabel jLSelecionados;
    private javax.swing.JMenu jMCadastros;
    private javax.swing.JMenuItem jMICarros;
    private javax.swing.JMenuItem jMICliente;
    private javax.swing.JMenuItem jMIHoteis;
    private javax.swing.JMenuItem jMIListaProgramacoes;
    private javax.swing.JMenuItem jMIUsers;
    private javax.swing.JMenuItem jMIVerDetalhes;
    private javax.swing.JMenu jMProgramacoes;
    private javax.swing.JMenu jMSobre;
    private javax.swing.JMenuBar jMenuBar;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jSPColaboradores;
    private javax.swing.JScrollPane jSPSelecionados;
    private javax.swing.JTextField jTFAlmoco;
    private javax.swing.JTextField jTFJanta;
    // End of variables declaration//GEN-END:variables
}
