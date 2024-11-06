/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view;

import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.ArrayList;
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

/**
 *
 * @author Koroch
 */
public class Programacao extends javax.swing.JFrame {
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
                String[] dados = elemento.split(",");
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
                //listModel.remove(1);
            }
        } catch (IOException e ) {
            JOptionPane.showMessageDialog(null, "Erro ao salvar aquivo Usuarios!");
        }
        
        try (BufferedReader br = new BufferedReader(new FileReader("carros.txt"))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                linhasArquivoCarro.add(linha);
            }
            
            linhasArquivoCarro.forEach(elemento -> {
                String[] dados = elemento.split(",");
                carros.add(new Carro(
                    Integer.parseInt(dados[0].trim()),
                    dados[1].trim(),
                    dados[2].trim(),
                    dados[3].trim()
                ));
                
            });
            
            if(!carros.isEmpty()){
                ultimoIdCarro = carros.getLast().getId();
                System.out.println(ultimoIdCarro);
                carros.forEach(x -> {
                    jCBCarro.addItem(x.getNome());
                });
            }
        } catch (IOException e ) {
            JOptionPane.showMessageDialog(null, "Erro ao salvar aquivo Carros!");
        }
        
        try (BufferedReader br = new BufferedReader(new FileReader("clientes.txt"))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                linhasArquivoCliente.add(linha);
            }
            
            linhasArquivoCliente.forEach(elemento -> {
                String[] dados = elemento.split(",");
                clientes.add(new Cliente(
                    Integer.parseInt(dados[0].trim()),
                    dados[1].trim(),
                    dados[2].trim(),
                    Estado.valueOf(dados[3].trim())
                ));
                
            });
            
            if(!clientes.isEmpty()){
                ultimoIdCliente = clientes.getLast().getId();
                System.out.println(ultimoIdCliente);
                clientes.forEach(x -> {
                    jCBCliente.addItem(x.getNomeEmpresa());
                });
            }
        } catch (IOException e ) {
            JOptionPane.showMessageDialog(null, "Erro ao salvar aquivo Clientes!");
        }
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
        jTFHospedagem = new javax.swing.JTextField();
        jLHospedagem = new javax.swing.JLabel();
        jLCliente = new javax.swing.JLabel();
        jCBCliente = new javax.swing.JComboBox<>();
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
        jFTFDataSaida = new javax.swing.JFormattedTextField();
        jLDataRetorno = new javax.swing.JLabel();
        jFTFDataRetorno = new javax.swing.JFormattedTextField();
        jFTFHoraInicioManha = new javax.swing.JFormattedTextField();
        jLHoraSaida = new javax.swing.JLabel();
        jFTFHoraSaida = new javax.swing.JFormattedTextField();
        jFTFHoraInicioTarde = new javax.swing.JFormattedTextField();
        jFTFHoraFimManha = new javax.swing.JFormattedTextField();
        jFTFHoraFimTarde = new javax.swing.JFormattedTextField();
        jPanel1 = new javax.swing.JPanel();
        jLHoraInicioManha = new javax.swing.JLabel();
        jLHorarioDeTrabalho = new javax.swing.JLabel();
        jLHoraFimManha = new javax.swing.JLabel();
        jLHoraInicioTarde = new javax.swing.JLabel();
        jLHoraFimTarde = new javax.swing.JLabel();
        jLFundo = new javax.swing.JLabel();
        jMenuBar = new javax.swing.JMenuBar();
        jMCadastros = new javax.swing.JMenu();
        jMIUsers = new javax.swing.JMenuItem();
        jMICarros = new javax.swing.JMenuItem();
        jMICliente = new javax.swing.JMenuItem();
        jMSobre = new javax.swing.JMenu();
        jMIVerDetalhes = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        getContentPane().setLayout(null);

        jLProg.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLProg.setForeground(new java.awt.Color(255, 255, 255));
        jLProg.setText("Adicionar um item para a Programação");
        getContentPane().add(jLProg);
        jLProg.setBounds(250, 10, 350, 25);

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
        jLNumero.setBounds(120, 10, 110, 14);

        jFTFNumero.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("####.##"))));
        jFTFNumero.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jFTFNumeroFocusGained(evt);
            }
        });
        jCB.add(jFTFNumero);
        jFTFNumero.setBounds(120, 30, 160, 30);
        jCB.add(jTFAlmoco);
        jTFAlmoco.setBounds(390, 240, 310, 30);

        jLAlmoco.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        jLAlmoco.setForeground(new java.awt.Color(255, 255, 255));
        jLAlmoco.setText("Local de almoço:");
        jLAlmoco.setToolTipText("");
        jCB.add(jLAlmoco);
        jLAlmoco.setBounds(300, 250, 110, 16);
        jCB.add(jTFHospedagem);
        jTFHospedagem.setBounds(390, 290, 310, 30);

        jLHospedagem.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        jLHospedagem.setForeground(new java.awt.Color(255, 255, 255));
        jLHospedagem.setText("Hospedagem: ");
        jLHospedagem.setToolTipText("");
        jCB.add(jLHospedagem);
        jLHospedagem.setBounds(310, 300, 120, 16);

        jLCliente.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        jLCliente.setForeground(new java.awt.Color(255, 255, 255));
        jLCliente.setText("Cliente:");
        jLCliente.setToolTipText("");
        jCB.add(jLCliente);
        jLCliente.setBounds(320, 10, 110, 14);

        jCBCliente.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "INTERNO" }));
        jCBCliente.setBorder(null);
        jCB.add(jCBCliente);
        jCBCliente.setBounds(320, 30, 160, 30);

        jLColaboradores.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        jLColaboradores.setForeground(new java.awt.Color(255, 255, 255));
        jLColaboradores.setText("Nome do Colaborador:");
        jLColaboradores.setToolTipText("");
        jCB.add(jLColaboradores);
        jLColaboradores.setBounds(120, 80, 130, 14);

        jLColab.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jSPColaboradores.setViewportView(jLColab);

        jCB.add(jSPColaboradores);
        jSPColaboradores.setBounds(120, 100, 160, 230);

        jLSelecionados.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        jLSelecionados.setForeground(new java.awt.Color(255, 255, 255));
        jLSelecionados.setText("Colaboradores Selecionados:");
        jLSelecionados.setToolTipText("");
        jCB.add(jLSelecionados);
        jLSelecionados.setBounds(350, 80, 160, 14);

        jLSelec.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jSPSelecionados.setViewportView(jLSelec);

        jCB.add(jSPSelecionados);
        jSPSelecionados.setBounds(350, 100, 160, 120);

        jBSub.setFont(new java.awt.Font("Segoe UI", 0, 8)); // NOI18N
        jBSub.setText("<<");
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
        jBSub.setBounds(290, 160, 50, 30);

        jBAdd.setFont(new java.awt.Font("Segoe UI", 0, 8)); // NOI18N
        jBAdd.setText(">>");
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
        jBAdd.setBounds(290, 120, 50, 30);

        jLResponsavel.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        jLResponsavel.setForeground(new java.awt.Color(255, 255, 255));
        jLResponsavel.setText("Selecione o Responsável da Obra:");
        jLResponsavel.setToolTipText("");
        jCB.add(jLResponsavel);
        jLResponsavel.setBounds(540, 80, 160, 14);

        jCB.add(jCBResponsavel);
        jCBResponsavel.setBounds(540, 100, 160, 30);

        jLCarro.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        jLCarro.setForeground(new java.awt.Color(255, 255, 255));
        jLCarro.setText("Selecione o Carro:");
        jLCarro.setToolTipText("");
        jCB.add(jLCarro);
        jLCarro.setBounds(540, 150, 160, 14);

        jCBCarro.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "N/A" }));
        jCB.add(jCBCarro);
        jCBCarro.setBounds(540, 170, 160, 30);

        jBAddBloco.setBackground(new java.awt.Color(204, 204, 204));
        jBAddBloco.setText("Adicionar");
        jBAddBloco.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jBAddBloco.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBAddBlocoActionPerformed(evt);
            }
        });
        jCB.add(jBAddBloco);
        jBAddBloco.setBounds(180, 460, 110, 30);

        jBAlterarBloco.setBackground(new java.awt.Color(204, 204, 204));
        jBAlterarBloco.setText("Alterar");
        jBAlterarBloco.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jBAlterarBloco.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBAlterarBlocoActionPerformed(evt);
            }
        });
        jCB.add(jBAlterarBloco);
        jBAlterarBloco.setBounds(300, 460, 110, 30);

        jBRemover.setBackground(new java.awt.Color(204, 204, 204));
        jBRemover.setText("Remover");
        jBRemover.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jBRemover.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBRemoverActionPerformed(evt);
            }
        });
        jCB.add(jBRemover);
        jBRemover.setBounds(420, 460, 110, 30);

        jBLimpar.setBackground(new java.awt.Color(204, 204, 204));
        jBLimpar.setText("❌ Limpar Campos");
        jBLimpar.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jBLimpar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBLimparActionPerformed(evt);
            }
        });
        jCB.add(jBLimpar);
        jBLimpar.setBounds(540, 460, 130, 30);

        jLDataSaida.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        jLDataSaida.setForeground(new java.awt.Color(255, 255, 255));
        jLDataSaida.setText("Data de Saída:");
        jLDataSaida.setToolTipText("");
        jCB.add(jLDataSaida);
        jLDataSaida.setBounds(80, 360, 90, 16);

        jFTFDataSaida.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(new java.text.SimpleDateFormat("dd/MM/yyyy"))));
        jFTFDataSaida.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jFTFDataSaida.setName(""); // NOI18N
        jFTFDataSaida.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jFTFDataSaidaFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jFTFDataSaidaFocusLost(evt);
            }
        });
        jFTFDataSaida.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
            }
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
                jFTFDataSaidaInputMethodTextChanged(evt);
            }
        });
        jFTFDataSaida.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jFTFDataSaidaActionPerformed(evt);
            }
        });
        jCB.add(jFTFDataSaida);
        jFTFDataSaida.setBounds(160, 350, 80, 30);

        jLDataRetorno.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        jLDataRetorno.setForeground(new java.awt.Color(255, 255, 255));
        jLDataRetorno.setText("Data de Retorno: ");
        jLDataRetorno.setToolTipText("");
        jCB.add(jLDataRetorno);
        jLDataRetorno.setBounds(70, 400, 120, 16);

        jFTFDataRetorno.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(new java.text.SimpleDateFormat("dd/MM/yyyy"))));
        jFTFDataRetorno.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jFTFDataRetorno.setName(""); // NOI18N
        jFTFDataRetorno.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jFTFDataRetornoFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jFTFDataRetornoFocusLost(evt);
            }
        });
        jFTFDataRetorno.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
            }
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
                jFTFDataRetornoInputMethodTextChanged(evt);
            }
        });
        jFTFDataRetorno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jFTFDataRetornoActionPerformed(evt);
            }
        });
        jCB.add(jFTFDataRetorno);
        jFTFDataRetorno.setBounds(160, 390, 80, 30);

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
        jCB.add(jFTFHoraInicioManha);
        jFTFHoraInicioManha.setBounds(530, 360, 50, 30);

        jLHoraSaida.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        jLHoraSaida.setForeground(new java.awt.Color(255, 255, 255));
        jLHoraSaida.setText("Hora de Saída:");
        jLHoraSaida.setToolTipText("");
        jCB.add(jLHoraSaida);
        jLHoraSaida.setBounds(260, 360, 90, 16);

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
        jFTFHoraSaida.setBounds(340, 350, 50, 30);

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
        jCB.add(jFTFHoraInicioTarde);
        jFTFHoraInicioTarde.setBounds(530, 400, 50, 30);

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
        jCB.add(jFTFHoraFimManha);
        jFTFHoraFimManha.setBounds(670, 360, 50, 30);

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
        jCB.add(jFTFHoraFimTarde);
        jFTFHoraFimTarde.setBounds(670, 400, 50, 30);

        jPanel1.setBackground(new java.awt.Color(0, 22, 90));
        jPanel1.setLayout(null);

        jLHoraInicioManha.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        jLHoraInicioManha.setForeground(new java.awt.Color(255, 255, 255));
        jLHoraInicioManha.setText("Início - Manhã:");
        jLHoraInicioManha.setToolTipText("");
        jPanel1.add(jLHoraInicioManha);
        jLHoraInicioManha.setBounds(30, 30, 90, 14);

        jLHorarioDeTrabalho.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        jLHorarioDeTrabalho.setForeground(new java.awt.Color(255, 255, 255));
        jLHorarioDeTrabalho.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLHorarioDeTrabalho.setText("Horário de Trabalho");
        jLHorarioDeTrabalho.setToolTipText("");
        jPanel1.add(jLHorarioDeTrabalho);
        jLHorarioDeTrabalho.setBounds(90, 0, 140, 16);

        jLHoraFimManha.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        jLHoraFimManha.setForeground(new java.awt.Color(255, 255, 255));
        jLHoraFimManha.setText("Fim - Manhã:");
        jLHoraFimManha.setToolTipText("");
        jPanel1.add(jLHoraFimManha);
        jLHoraFimManha.setBounds(180, 30, 70, 16);

        jLHoraInicioTarde.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        jLHoraInicioTarde.setForeground(new java.awt.Color(255, 255, 255));
        jLHoraInicioTarde.setText(" Início - Tarde: ");
        jLHoraInicioTarde.setToolTipText("");
        jPanel1.add(jLHoraInicioTarde);
        jLHoraInicioTarde.setBounds(30, 70, 90, 16);

        jLHoraFimTarde.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        jLHoraFimTarde.setForeground(new java.awt.Color(255, 255, 255));
        jLHoraFimTarde.setText("  Fim - Tarde:");
        jLHoraFimTarde.setToolTipText("");
        jPanel1.add(jLHoraFimTarde);
        jLHoraFimTarde.setBounds(180, 70, 80, 16);

        jCB.add(jPanel1);
        jPanel1.setBounds(420, 340, 320, 110);

        getContentPane().add(jCB);
        jCB.setBounds(0, 40, 840, 500);

        jLFundo.setBackground(new java.awt.Color(51, 51, 51));
        jLFundo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/tela_de_fundo.jpeg"))); // NOI18N
        getContentPane().add(jLFundo);
        jLFundo.setBounds(-390, -210, 1250, 1120);

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

        jMenuBar.add(jMCadastros);

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

        setSize(new java.awt.Dimension(856, 697));
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
            //listModel.remove(1);
        }
    }
    
    private void atualizarListas(){
        CadastroCliente cadastroCliente = new CadastroCliente(clientes);
        if(!cadastroCliente.getListaAtualizadaClientes().isEmpty()){
            ultimoIdCliente = cadastroCliente.getListaAtualizadaClientes().getLast().getId();
            jCBCliente.removeAllItems();
            jCBCliente.addItem("INTERNO");
            cadastroCliente.getListaAtualizadaClientes().forEach(x -> {
                jCBCliente.addItem(x.getNomeEmpresa());
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
        atualizarRemove();
    }
    
    private void jBAddBlocoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBAddBlocoActionPerformed
//        String projeto = jFTFNumero.getText().split("\\.")[1].equals("0") ? jFTFNumero.getText().split("\\.")[0] : jFTFNumero.getText();
//        Cliente cliente = new Cliente().getByNameEmpresa(String.valueOf(jCBCliente.getSelectedItem()), clientes);
        List<String> colabsDoCarro = new ArrayList<>();

        if (modeloSelec.getSize() > 0) {
            ArrayList<String> arrayList = new ArrayList<>(
                IntStream.range(0, modeloSelec.getSize())
                    .mapToObj(modeloSelec::getElementAt)
                    .collect(Collectors.toList())
            );
            ArrayList<Usuario> usuariosEncontrados = (ArrayList<Usuario>) usuarios.stream()
                .filter(usuario -> arrayList.stream()
                    .anyMatch(nome -> nome.equals(usuario.getNomeDeGuerra()))
                )
                .collect(Collectors.toList());
        }
        
    }//GEN-LAST:event_jBAddBlocoActionPerformed

    private void jBAlterarBlocoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBAlterarBlocoActionPerformed
        
    }//GEN-LAST:event_jBAlterarBlocoActionPerformed

    private void jBRemoverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBRemoverActionPerformed

    }//GEN-LAST:event_jBRemoverActionPerformed

    private void jBLimparActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBLimparActionPerformed
        modeloSelec.removeAllElements();
        jCBResponsavel.removeAllItems();
        listModel.removeAllElements();
        atualizarListas();
        jFTFNumero.setValue(null);
        jFTFDataSaida.setValue(null);
        jFTFDataRetorno.setValue(null);
        jFTFHoraSaida.setValue(null);
        jFTFHoraInicioManha.setValue(null);
        jFTFHoraFimManha.setValue(null);
        jFTFHoraInicioTarde.setValue(null);
        jFTFHoraFimTarde.setValue(null);
        jTFAlmoco.setText("");
        jTFHospedagem.setText("");
    }//GEN-LAST:event_jBLimparActionPerformed

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
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Nenhum colaborador selecionado na listagem!");
        }
    }//GEN-LAST:event_jBAddActionPerformed

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

    private void jFTFDataSaidaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jFTFDataSaidaActionPerformed
      
    }//GEN-LAST:event_jFTFDataSaidaActionPerformed

    private void jFTFDataSaidaInputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_jFTFDataSaidaInputMethodTextChanged
        
    }//GEN-LAST:event_jFTFDataSaidaInputMethodTextChanged

    private void jFTFDataSaidaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jFTFDataSaidaFocusLost

    }//GEN-LAST:event_jFTFDataSaidaFocusLost

    private void jFTFDataSaidaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jFTFDataSaidaFocusGained
        jFTFDataSaida.setValue(null);
        atualizaData(jFTFDataSaida);
    }//GEN-LAST:event_jFTFDataSaidaFocusGained

    private void jFTFDataRetornoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jFTFDataRetornoFocusGained
        // TODO add your handling code here:
        jFTFDataRetorno.setValue(null);
        atualizaData(jFTFDataRetorno);
    }//GEN-LAST:event_jFTFDataRetornoFocusGained

    private void jFTFDataRetornoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jFTFDataRetornoFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_jFTFDataRetornoFocusLost

    private void jFTFDataRetornoInputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_jFTFDataRetornoInputMethodTextChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_jFTFDataRetornoInputMethodTextChanged

    private void jFTFDataRetornoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jFTFDataRetornoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jFTFDataRetornoActionPerformed

    private void jFTFHoraInicioManhaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jFTFHoraInicioManhaFocusGained
        // TODO add your handling code here:
        jFTFHoraInicioManha.setValue(null);
        atualizaHora(jFTFHoraInicioManha);
    }//GEN-LAST:event_jFTFHoraInicioManhaFocusGained

    private void jFTFHoraInicioManhaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jFTFHoraInicioManhaFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_jFTFHoraInicioManhaFocusLost

    private void jFTFHoraInicioManhaInputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_jFTFHoraInicioManhaInputMethodTextChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_jFTFHoraInicioManhaInputMethodTextChanged

    private void jFTFHoraInicioManhaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jFTFHoraInicioManhaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jFTFHoraInicioManhaActionPerformed

    private void jFTFHoraSaidaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jFTFHoraSaidaFocusGained
        jFTFHoraSaida.setValue(null);
        atualizaHora(jFTFHoraSaida);
    }//GEN-LAST:event_jFTFHoraSaidaFocusGained

    private void jFTFHoraSaidaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jFTFHoraSaidaFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_jFTFHoraSaidaFocusLost

    private void jFTFHoraSaidaInputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_jFTFHoraSaidaInputMethodTextChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_jFTFHoraSaidaInputMethodTextChanged

    private void jFTFHoraSaidaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jFTFHoraSaidaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jFTFHoraSaidaActionPerformed

    private void jFTFHoraInicioTardeFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jFTFHoraInicioTardeFocusGained
        jFTFHoraInicioTarde.setValue(null);
        atualizaHora(jFTFHoraInicioTarde);
    }//GEN-LAST:event_jFTFHoraInicioTardeFocusGained

    private void jFTFHoraInicioTardeFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jFTFHoraInicioTardeFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_jFTFHoraInicioTardeFocusLost

    private void jFTFHoraInicioTardeInputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_jFTFHoraInicioTardeInputMethodTextChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_jFTFHoraInicioTardeInputMethodTextChanged

    private void jFTFHoraInicioTardeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jFTFHoraInicioTardeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jFTFHoraInicioTardeActionPerformed

    private void jFTFHoraFimManhaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jFTFHoraFimManhaFocusGained
        jFTFHoraFimManha.setValue(null);
        atualizaHora(jFTFHoraFimManha);
    }//GEN-LAST:event_jFTFHoraFimManhaFocusGained

    private void jFTFHoraFimManhaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jFTFHoraFimManhaFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_jFTFHoraFimManhaFocusLost

    private void jFTFHoraFimManhaInputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_jFTFHoraFimManhaInputMethodTextChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_jFTFHoraFimManhaInputMethodTextChanged

    private void jFTFHoraFimManhaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jFTFHoraFimManhaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jFTFHoraFimManhaActionPerformed

    private void jFTFHoraFimTardeFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jFTFHoraFimTardeFocusGained
        jFTFHoraFimTarde.setValue(null);
        atualizaHora(jFTFHoraFimTarde);
    }//GEN-LAST:event_jFTFHoraFimTardeFocusGained

    private void jFTFHoraFimTardeFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jFTFHoraFimTardeFocusLost
        // TODO add your handling code here:
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
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Programacao().setVisible(true);
                
            }
        });
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBAdd;
    private javax.swing.JButton jBAddBloco;
    private javax.swing.JButton jBAlterarBloco;
    private javax.swing.JButton jBLimpar;
    private javax.swing.JButton jBRemover;
    private javax.swing.JButton jBSub;
    private javax.swing.JPanel jCB;
    private javax.swing.JComboBox<String> jCBCarro;
    public javax.swing.JComboBox<String> jCBCliente;
    private javax.swing.JComboBox<String> jCBResponsavel;
    private javax.swing.JFormattedTextField jFTFDataRetorno;
    private javax.swing.JFormattedTextField jFTFDataSaida;
    private javax.swing.JFormattedTextField jFTFHoraFimManha;
    private javax.swing.JFormattedTextField jFTFHoraFimTarde;
    private javax.swing.JFormattedTextField jFTFHoraInicioManha;
    private javax.swing.JFormattedTextField jFTFHoraInicioTarde;
    private javax.swing.JFormattedTextField jFTFHoraSaida;
    private javax.swing.JFormattedTextField jFTFNumero;
    private javax.swing.JLabel jLAlmoco;
    private javax.swing.JLabel jLCarro;
    private javax.swing.JLabel jLCliente;
    private javax.swing.JList<String> jLColab;
    private javax.swing.JLabel jLColaboradores;
    private javax.swing.JLabel jLDataRetorno;
    private javax.swing.JLabel jLDataSaida;
    private javax.swing.JLabel jLFundo;
    private javax.swing.JLabel jLHoraFimManha;
    private javax.swing.JLabel jLHoraFimTarde;
    private javax.swing.JLabel jLHoraInicioManha;
    private javax.swing.JLabel jLHoraInicioTarde;
    private javax.swing.JLabel jLHoraSaida;
    private javax.swing.JLabel jLHorarioDeTrabalho;
    private javax.swing.JLabel jLHospedagem;
    private javax.swing.JLabel jLNumero;
    private javax.swing.JLabel jLProg;
    private javax.swing.JLabel jLResponsavel;
    private javax.swing.JList<String> jLSelec;
    private javax.swing.JLabel jLSelecionados;
    private javax.swing.JMenu jMCadastros;
    private javax.swing.JMenuItem jMICarros;
    private javax.swing.JMenuItem jMICliente;
    private javax.swing.JMenuItem jMIUsers;
    private javax.swing.JMenuItem jMIVerDetalhes;
    private javax.swing.JMenu jMSobre;
    private javax.swing.JMenuBar jMenuBar;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jSPColaboradores;
    private javax.swing.JScrollPane jSPSelecionados;
    private javax.swing.JTextField jTFAlmoco;
    private javax.swing.JTextField jTFHospedagem;
    // End of variables declaration//GEN-END:variables
}
