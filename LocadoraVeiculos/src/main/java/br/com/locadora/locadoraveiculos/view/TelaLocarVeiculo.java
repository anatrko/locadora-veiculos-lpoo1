
package br.com.locadora.locadoraveiculos.view;

import br.com.locadora.locadoraveiculos.controller.ClienteController;
import br.com.locadora.locadoraveiculos.controller.VeiculoController;
import br.com.locadora.locadoraveiculos.controller.LocacaoController;
import br.com.locadora.locadoraveiculos.enums.Categoria;
import br.com.locadora.locadoraveiculos.enums.Marca;
import br.com.locadora.locadoraveiculos.enums.TipoVeiculo;
import br.com.locadora.locadoraveiculos.model.*;

import java.awt.*;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.JTextComponent;
import javax.swing.text.MaskFormatter;

public class TelaLocarVeiculo extends JPanel implements Atualizavel {

    private final ClienteController clienteCtrl;
    private final VeiculoController veiculoCtrl;
    private final LocacaoController locacaoCtrl;
    
    private static final String BUSCA_NOME = "buscar por nome/sobrenome";
    private static final String BUSCA_CPF = "buscar por cpf";
    
    private final ModeloTabelaClientes modeloCliente = new ModeloTabelaClientes();
    private final ModeloTabelaVeiculos modeloVeiculo = new ModeloTabelaVeiculos();
    
    // ---- tirar dps
    private JComboBox<Cliente> cbClientes;
    private JComboBox<Veiculo> cbVeiculos;
    private JTextField txtDias;
    private JTextField txtData_old;
    //------------

    public TelaLocarVeiculo(
            Navegador nv,
            ClienteController clienteCtrl,
            VeiculoController veiculoCtrl,
            LocacaoController locacaoCtrl) {

        this.clienteCtrl = clienteCtrl;
        this.veiculoCtrl = veiculoCtrl;
        this.locacaoCtrl = locacaoCtrl;
        
        initComponents();
        
        tblBuscaCliente.setModel(modeloCliente);
        tblBuscaCliente.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblVeiculos.setModel(modeloVeiculo);
        tblVeiculos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        configurarBuscaCliente();
        configurarFiltrosVeiculo();
        configurarCampoData();
        
        btnVoltar1.addActionListener(e -> nv.irPara(TelaInicial.MENU));
    }
    
    // ---------- busca de cliente ----------
    private void configurarBuscaCliente() {
        containerCampoPesquisa.add(panelNomeSobrenome, BUSCA_NOME);
        containerCampoPesquisa.add(panelCpf, BUSCA_CPF);

        cmbTipoTermo.setModel(new DefaultComboBoxModel<>(new String[]{"Nome/Sobrenome", "CPF"}));
        cmbTipoTermo.addActionListener(e -> {
            CardLayout cl = (CardLayout) containerCampoPesquisa.getLayout();
            cl.show(containerCampoPesquisa,
                    cmbTipoTermo.getSelectedIndex() == 0 ? BUSCA_NOME : BUSCA_CPF);
            buscarClientes();
        });

        aoDigitar(txtNomeSobrenome, this::buscarClientes);
        aoDigitar(txtCpf, this::buscarClientes);
    }

    private void aoDigitar(JTextComponent campo, Runnable acao) {
        campo.getDocument().addDocumentListener(new DocumentListener() {
            @Override public void insertUpdate(DocumentEvent e) { acao.run(); }
            @Override public void removeUpdate(DocumentEvent e) { acao.run(); }
            @Override public void changedUpdate(DocumentEvent e) { acao.run(); }
        });
    }

    private void buscarClientes() {
        String termo = (cmbTipoTermo.getSelectedIndex() == 0)
                ? txtNomeSobrenome.getText().trim()
                : txtCpf.getText().replaceAll("\\D", "");   // só os dígitos da máscara

        modeloCliente.setClientes(termo.isEmpty()
                ? clienteCtrl.getClientes()
                : clienteCtrl.buscar(termo));
    }
    
    // ---------- filtros de veículo ----------
    private void configurarFiltrosVeiculo() {
        popularCombo(cmbTipoVeiculo, TipoVeiculo.values());
        popularCombo(cmbMarca, Marca.values());
        popularCombo(cmbCategoria, Categoria.values());

        cmbTipoVeiculo.addActionListener(e -> carregarVeiculos());
        cmbMarca.addActionListener(e -> carregarVeiculos());
        cmbCategoria.addActionListener(e -> carregarVeiculos());
    }
    
    // primeiro item = null, exibido como "Todos" (significa "sem filtro")
    @SuppressWarnings({"unchecked", "rawtypes"})
    private void popularCombo(JComboBox combo, Object[] valores) {
        DefaultComboBoxModel modelo = new DefaultComboBoxModel();
        modelo.addElement(null);
        for (Object v : valores) modelo.addElement(v);
        combo.setModel(modelo);
        combo.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value,
                    int index, boolean selected, boolean focus) {
                return super.getListCellRendererComponent(list,
                        value == null ? "Todos" : value, index, selected, focus);
            }
        });
    }
    
    private void carregarVeiculos() {
        TipoVeiculo tipo = (TipoVeiculo) cmbTipoVeiculo.getSelectedItem();
        Marca marca = (Marca) cmbMarca.getSelectedItem();
        Categoria categoria = (Categoria) cmbCategoria.getSelectedItem();
        modeloVeiculo.setVeiculos(veiculoCtrl.filtrar(tipo, marca, categoria));
    }
    
    // ---------- data ----------
    private void configurarCampoData() {
        try {
            txtData.setFormatterFactory(
                    new DefaultFormatterFactory(new MaskFormatter("##/##/####")));
        } catch (ParseException ex) {
            throw new IllegalStateException(ex);
        }
    }

    private Calendar lerData() throws ParseException {
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        formato.setLenient(false);
        Date d = formato.parse(txtData.getText().trim());
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        return c;
    }
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelNomeSobrenome = new javax.swing.JPanel();
        txtNomeSobrenome = new javax.swing.JFormattedTextField();
        panelCpf = new javax.swing.JPanel();
        txtCpf = new javax.swing.JFormattedTextField();
        titulo = new javax.swing.JLabel();
        containerConteudo = new javax.swing.JPanel();
        containerForm = new javax.swing.JPanel();
        labelBusca = new javax.swing.JLabel();
        cmbTipoTermo = new javax.swing.JComboBox<>();
        containerCampoPesquisa = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblBuscaCliente = new javax.swing.JTable();
        labeFiltroVeiculo = new javax.swing.JLabel();
        gridCombosBusca = new javax.swing.JPanel();
        labelTipoVeiculo = new javax.swing.JLabel();
        labelMarca = new javax.swing.JLabel();
        labelCategoria = new javax.swing.JLabel();
        cmbTipoVeiculo = new javax.swing.JComboBox<>();
        cmbMarca = new javax.swing.JComboBox<>();
        cmbCategoria = new javax.swing.JComboBox<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblVeiculos = new javax.swing.JTable();
        labelQtdadeDias = new javax.swing.JLabel();
        txtQtdadeDias = new javax.swing.JTextField();
        labelData = new javax.swing.JLabel();
        txtData = new javax.swing.JFormattedTextField();
        containerBotoes1 = new javax.swing.JPanel();
        btnLocar = new javax.swing.JButton();
        btnVoltar1 = new javax.swing.JButton();

        javax.swing.GroupLayout panelNomeSobrenomeLayout = new javax.swing.GroupLayout(panelNomeSobrenome);
        panelNomeSobrenome.setLayout(panelNomeSobrenomeLayout);
        panelNomeSobrenomeLayout.setHorizontalGroup(
            panelNomeSobrenomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelNomeSobrenomeLayout.createSequentialGroup()
                .addContainerGap(41, Short.MAX_VALUE)
                .addComponent(txtNomeSobrenome, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(41, Short.MAX_VALUE))
        );
        panelNomeSobrenomeLayout.setVerticalGroup(
            panelNomeSobrenomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtNomeSobrenome)
        );

        try {
            txtCpf.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("###.###.###-##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        javax.swing.GroupLayout panelCpfLayout = new javax.swing.GroupLayout(panelCpf);
        panelCpf.setLayout(panelCpfLayout);
        panelCpfLayout.setHorizontalGroup(
            panelCpfLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCpfLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txtCpf, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelCpfLayout.setVerticalGroup(
            panelCpfLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtCpf)
        );

        titulo.setText("Locação de Veículos");

        containerForm.setToolTipText("");

        labelBusca.setFont(new java.awt.Font("Inter", 0, 14)); // NOI18N
        labelBusca.setText("Pesquisar por:");

        cmbTipoTermo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        containerCampoPesquisa.setLayout(new java.awt.CardLayout());

        tblBuscaCliente.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tblBuscaCliente);

        labeFiltroVeiculo.setFont(new java.awt.Font("Inter", 0, 14)); // NOI18N
        labeFiltroVeiculo.setText("Filtrar Veiculos por");

        gridCombosBusca.setLayout(new java.awt.GridLayout(2, 3, 20, 0));

        labelTipoVeiculo.setFont(new java.awt.Font("Inter", 0, 14)); // NOI18N
        labelTipoVeiculo.setLabelFor(cmbTipoVeiculo);
        labelTipoVeiculo.setText("Tipo");
        gridCombosBusca.add(labelTipoVeiculo);

        labelMarca.setFont(new java.awt.Font("Inter", 0, 14)); // NOI18N
        labelMarca.setLabelFor(cmbMarca);
        labelMarca.setText("Marca");
        gridCombosBusca.add(labelMarca);

        labelCategoria.setFont(new java.awt.Font("Inter", 0, 14)); // NOI18N
        labelCategoria.setText("Categoria");
        gridCombosBusca.add(labelCategoria);

        cmbTipoVeiculo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        gridCombosBusca.add(cmbTipoVeiculo);

        cmbMarca.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        gridCombosBusca.add(cmbMarca);

        cmbCategoria.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        gridCombosBusca.add(cmbCategoria);

        tblVeiculos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(tblVeiculos);

        labelQtdadeDias.setFont(new java.awt.Font("Inter", 0, 14)); // NOI18N
        labelQtdadeDias.setLabelFor(txtQtdadeDias);
        labelQtdadeDias.setText("Quantidade de dias");

        labelData.setFont(new java.awt.Font("Inter", 0, 14)); // NOI18N
        labelData.setLabelFor(txtData);
        labelData.setText("Data de início da locação (dd/MM/aaaa)");

        javax.swing.GroupLayout containerFormLayout = new javax.swing.GroupLayout(containerForm);
        containerForm.setLayout(containerFormLayout);
        containerFormLayout.setHorizontalGroup(
            containerFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(containerCampoPesquisa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(containerFormLayout.createSequentialGroup()
                .addGroup(containerFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(containerFormLayout.createSequentialGroup()
                        .addComponent(labelBusca)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmbTipoTermo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(containerFormLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(containerFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(containerFormLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 385, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, containerFormLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(gridCombosBusca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(containerFormLayout.createSequentialGroup()
                .addGroup(containerFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labeFiltroVeiculo)
                    .addGroup(containerFormLayout.createSequentialGroup()
                        .addComponent(labelQtdadeDias)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtQtdadeDias, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(containerFormLayout.createSequentialGroup()
                        .addComponent(labelData)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtData, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        containerFormLayout.setVerticalGroup(
            containerFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(containerFormLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(containerFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelBusca)
                    .addComponent(cmbTipoTermo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(containerCampoPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(labeFiltroVeiculo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(gridCombosBusca, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(containerFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelQtdadeDias)
                    .addComponent(txtQtdadeDias, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(containerFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelData)
                    .addComponent(txtData, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout containerConteudoLayout = new javax.swing.GroupLayout(containerConteudo);
        containerConteudo.setLayout(containerConteudoLayout);
        containerConteudoLayout.setHorizontalGroup(
            containerConteudoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, containerConteudoLayout.createSequentialGroup()
                .addContainerGap(48, Short.MAX_VALUE)
                .addComponent(containerForm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(47, Short.MAX_VALUE))
        );
        containerConteudoLayout.setVerticalGroup(
            containerConteudoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(containerConteudoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(containerForm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnLocar.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        btnLocar.setText("Locar");
        btnLocar.setToolTipText("");
        btnLocar.addActionListener(this::btnLocarActionPerformed);

        btnVoltar1.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        btnVoltar1.setText("Voltar");

        javax.swing.GroupLayout containerBotoes1Layout = new javax.swing.GroupLayout(containerBotoes1);
        containerBotoes1.setLayout(containerBotoes1Layout);
        containerBotoes1Layout.setHorizontalGroup(
            containerBotoes1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, containerBotoes1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnVoltar1, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(105, 105, 105)
                .addComponent(btnLocar, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        containerBotoes1Layout.setVerticalGroup(
            containerBotoes1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, containerBotoes1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(containerBotoes1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnVoltar1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLocar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(containerConteudo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(titulo)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(containerBotoes1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(titulo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(containerConteudo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(containerBotoes1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents
    
    private void btnLocarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLocarActionPerformed
        Calendar data;
        try {
            data = lerData();
        } catch (ParseException ex) {
            erro("Informe a data no formato dd/MM/aaaa.");
            return;
        }

        try {
            locacaoCtrl.locar(clienteSelecionado(), veiculoSelecionado(),
                    txtQtdadeDias.getText().trim(), data);

            JOptionPane.showMessageDialog(this, "Locação realizada com sucesso!");
            limparFormulario();
            carregarVeiculos();   // o veículo locado deixa de aparecar na tabela
        } catch (IllegalArgumentException | IllegalStateException ex) {
            erro(ex.getMessage());
        }
    }//GEN-LAST:event_btnLocarActionPerformed

    private Cliente clienteSelecionado() {
        int linha = tblBuscaCliente.getSelectedRow();
        return linha < 0 ? null
                : modeloCliente.getCliente(tblBuscaCliente.convertRowIndexToModel(linha));
    }

    private Veiculo veiculoSelecionado() {
        int linha = tblVeiculos.getSelectedRow();
        return linha < 0 ? null
                : modeloVeiculo.getVeiculo(tblVeiculos.convertRowIndexToModel(linha));
    }
    
    private void limparFormulario() {
        txtQtdadeDias.setText("");
        txtData.setText("");
        tblBuscaCliente.clearSelection();
        tblVeiculos.clearSelection();
    }

    private void erro(String mensagem) {
        JOptionPane.showMessageDialog(this,
                mensagem == null ? "Confira os dados informados." : mensagem,
                "Erro na locação", JOptionPane.ERROR_MESSAGE);
    }
    
    @Override
    public void aoExibir() {
        cmbTipoVeiculo.setSelectedIndex(0);
        cmbMarca.setSelectedIndex(0);
        cmbCategoria.setSelectedIndex(0);
        cmbTipoTermo.setSelectedIndex(0);
        txtNomeSobrenome.setText("");
        txtCpf.setText("");
        limparFormulario();
        buscarClientes();
        carregarVeiculos();
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLocar;
    private javax.swing.JButton btnVoltar1;
    private javax.swing.JComboBox<String> cmbCategoria;
    private javax.swing.JComboBox<String> cmbMarca;
    private javax.swing.JComboBox<String> cmbTipoTermo;
    private javax.swing.JComboBox<String> cmbTipoVeiculo;
    private javax.swing.JPanel containerBotoes1;
    private javax.swing.JPanel containerCampoPesquisa;
    private javax.swing.JPanel containerConteudo;
    private javax.swing.JPanel containerForm;
    private javax.swing.JPanel gridCombosBusca;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel labeFiltroVeiculo;
    private javax.swing.JLabel labelBusca;
    private javax.swing.JLabel labelCategoria;
    private javax.swing.JLabel labelData;
    private javax.swing.JLabel labelMarca;
    private javax.swing.JLabel labelQtdadeDias;
    private javax.swing.JLabel labelTipoVeiculo;
    private javax.swing.JPanel panelCpf;
    private javax.swing.JPanel panelNomeSobrenome;
    private javax.swing.JTable tblBuscaCliente;
    private javax.swing.JTable tblVeiculos;
    private javax.swing.JLabel titulo;
    private javax.swing.JFormattedTextField txtCpf;
    private javax.swing.JFormattedTextField txtData;
    private javax.swing.JFormattedTextField txtNomeSobrenome;
    private javax.swing.JTextField txtQtdadeDias;
    // End of variables declaration//GEN-END:variables

    private class ModeloTabelaClientes extends AbstractTableModel {
        private final String[] colunas = {"Nome", "Sobrenome", "RG", "CPF"};
        private List<Cliente> clientes = new ArrayList<>();

        public void setClientes(List<Cliente> lista) {
            this.clientes = new ArrayList<>(lista);
            fireTableDataChanged();
        }

        public Cliente getCliente(int linha) {
            if (linha < 0 || linha >= clientes.size()) return null;
            return clientes.get(linha);
        }

        @Override public int getRowCount() { return clientes.size(); }
        @Override public int getColumnCount() { return colunas.length; }
        @Override public String getColumnName(int c) { return colunas[c]; }

        @Override
        public Object getValueAt(int linha, int coluna) {
            Cliente c = clientes.get(linha);
            return switch (coluna) {
                case 0 -> c.getNome();
                case 1 -> c.getSobrenome();
                case 2 -> c.getRg();
                case 3 -> c.getCpf();
                default -> null;
            };
        }
    }

    private class ModeloTabelaVeiculos extends AbstractTableModel {
        private final String[] colunas = {"Placa", "Marca", "Modelo", "Ano", "Preço da diária"};
        private final DecimalFormat moeda = new DecimalFormat("'R$'#,##0.00",
                new DecimalFormatSymbols(Locale.forLanguageTag("pt-BR")));
        private List<Veiculo> veiculos = new ArrayList<>();

        public void setVeiculos(List<Veiculo> lista) {
            this.veiculos = new ArrayList<>(lista);
            fireTableDataChanged();
        }

        public Veiculo getVeiculo(int linha) {
            if (linha < 0 || linha >= veiculos.size()) return null;
            return veiculos.get(linha);
        }

        @Override public int getRowCount() { return veiculos.size(); }
        @Override public int getColumnCount() { return colunas.length; }
        @Override public String getColumnName(int c) { return colunas[c]; }

        @Override
        public Object getValueAt(int linha, int coluna) {
            Veiculo v = veiculos.get(linha);
            return switch (coluna) {
                case 0 -> v.getPlaca();
                case 1 -> v.getMarca();
                case 2 -> modeloDe(v);
                case 3 -> String.format("%04d", v.getAno());
                case 4 -> moeda.format(v.getValorDiariaLocacao());  // polimorfismo
                default -> null;
            };
        }

        // getModelo() só existe nas subclasses, então este é o único instanceof
        private String modeloDe(Veiculo v) {
            if (v instanceof Automovel auto )  return String.valueOf(auto.getModelo());
            if (v instanceof Motocicleta moto) return String.valueOf(moto.getModelo());
            if (v instanceof Van van)         return String.valueOf(van.getModelo());
            return "";
        }
    }
}
