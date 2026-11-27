
package br.com.locadora.locadoraveiculos.view;

import br.com.locadora.locadoraveiculos.controller.ClienteController;
import br.com.locadora.locadoraveiculos.controller.VeiculoController;
import br.com.locadora.locadoraveiculos.controller.LocacaoController;
import br.com.locadora.locadoraveiculos.model.*;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.swing.*;

public class TelaLocarVeiculo extends JPanel implements Atualizavel {

    private final ClienteController clienteCtrl;
    private final VeiculoController veiculoCtrl;
    private final LocacaoController locacaoCtrl;
    
    private static final String BUSCA_NOME = "buscar por nome/sobrenome";
    private static final String BUSCA_CPF = "buscar por cpf";
    
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

        containerCampoPesquisa.add(panelNomeSobrenome, BUSCA_NOME);
        containerCampoPesquisa.add(panelCpf, BUSCA_CPF);
        
        btnVoltar.addActionListener(e -> nv.irPara(TelaInicial.MENU));
    }

    private void configurarRenderizadores() {

        cbClientes.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(
                    JList<?> list, Object value, int index,
                    boolean isSelected, boolean cellHasFocus) {

                super.getListCellRendererComponent(
                        list, value, index, isSelected, cellHasFocus);

                if (value instanceof Cliente) {
                    Cliente c = (Cliente) value;
                    setText(c.getNome() + " " + c.getSobrenome()
                            + " - CPF: " + c.getCpf());
                }

                return this;
            }
        });

        cbVeiculos.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(
                    JList<?> list, Object value, int index,
                    boolean isSelected, boolean cellHasFocus) {

                super.getListCellRendererComponent(
                        list, value, index, isSelected, cellHasFocus);

                if (value instanceof Veiculo) {
                    Veiculo v = (Veiculo) value;
                    setText(v.getMarca() + " - " + v.getPlaca());
                }

                return this;
            }
        });
    }

    private void atualizarCombos() {

        cbClientes.removeAllItems();
        cbVeiculos.removeAllItems();

        List<Cliente> clientes = clienteCtrl.getClientes();

        for (Cliente c : clientes) {
            cbClientes.addItem(c);
        }

        List<Veiculo> veiculos = veiculoCtrl.listarDisponiveis();

        for (Veiculo v : veiculos) {
            cbVeiculos.addItem(v);
        }
    }

    private void realizarLocacao() {

        Cliente cliente = (Cliente) cbClientes.getSelectedItem();
        Veiculo veiculo = (Veiculo) cbVeiculos.getSelectedItem();

        String dias = txtDias.getText().trim();
        String dataTexto = txtData_old.getText().trim();

        try {
            SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
            formato.setLenient(false);

            Date dataConvertida = formato.parse(dataTexto);

            Calendar data = Calendar.getInstance();
            data.setTime(dataConvertida);

            locacaoCtrl.locar(cliente, veiculo, dias, data);

            JOptionPane.showMessageDialog(this,
                    "Locação realizada com sucesso!");

            txtDias.setText("");
            txtData_old.setText("");

            atualizarCombos();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    e.getMessage() == null
                    ? "Confira os dados informados."
                    : e.getMessage(),
                    "Erro na locação",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelNomeSobrenome = new javax.swing.JPanel();
        txtNomeSobrenome = new javax.swing.JFormattedTextField();
        panelCpf = new javax.swing.JPanel();
        txtCpf = new javax.swing.JFormattedTextField();
        titulo = new javax.swing.JLabel();
        containerBotoes1 = new javax.swing.JPanel();
        btnLocar = new javax.swing.JButton();
        btnVoltar1 = new javax.swing.JButton();
        containerConteudo = new javax.swing.JPanel();
        containerForm = new javax.swing.JPanel();
        labelBusca = new javax.swing.JLabel();
        cmbTipoTermo = new javax.swing.JComboBox<>();
        containerCampoPesquisa = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblBuscaCliente = new javax.swing.JTable();
        labeFiltroVeiculo = new javax.swing.JLabel();
        gridCombosBusca = new javax.swing.JPanel();
        Tipo = new javax.swing.JLabel();
        Marca = new javax.swing.JLabel();
        Categoria = new javax.swing.JLabel();
        cmbTipoVeiculo = new javax.swing.JComboBox<>();
        cmbMarca = new javax.swing.JComboBox<>();
        cmbCategoria = new javax.swing.JComboBox<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblVeiculos = new javax.swing.JTable();
        QtdadeDias = new javax.swing.JLabel();
        txtQtdadeDias = new javax.swing.JTextField();
        Data = new javax.swing.JLabel();
        txtData = new javax.swing.JFormattedTextField();

        txtNomeSobrenome.addActionListener(this::txtNomeSobrenomeActionPerformed);

        javax.swing.GroupLayout panelNomeSobrenomeLayout = new javax.swing.GroupLayout(panelNomeSobrenome);
        panelNomeSobrenome.setLayout(panelNomeSobrenomeLayout);
        panelNomeSobrenomeLayout.setHorizontalGroup(
            panelNomeSobrenomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtNomeSobrenome, javax.swing.GroupLayout.DEFAULT_SIZE, 332, Short.MAX_VALUE)
        );
        panelNomeSobrenomeLayout.setVerticalGroup(
            panelNomeSobrenomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelNomeSobrenomeLayout.createSequentialGroup()
                .addComponent(txtNomeSobrenome)
                .addContainerGap())
        );

        try {
            txtCpf.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("###.###.###-##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txtCpf.addActionListener(this::txtCpfActionPerformed);

        javax.swing.GroupLayout panelCpfLayout = new javax.swing.GroupLayout(panelCpf);
        panelCpf.setLayout(panelCpfLayout);
        panelCpfLayout.setHorizontalGroup(
            panelCpfLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtCpf, javax.swing.GroupLayout.DEFAULT_SIZE, 332, Short.MAX_VALUE)
        );
        panelCpfLayout.setVerticalGroup(
            panelCpfLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCpfLayout.createSequentialGroup()
                .addComponent(txtCpf)
                .addContainerGap())
        );

        titulo.setText("Locação de Veículos");

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

        Tipo.setFont(new java.awt.Font("Inter", 0, 14)); // NOI18N
        Tipo.setLabelFor(cmbTipoVeiculo);
        Tipo.setText("Tipo");
        gridCombosBusca.add(Tipo);

        Marca.setFont(new java.awt.Font("Inter", 0, 14)); // NOI18N
        Marca.setLabelFor(cmbMarca);
        Marca.setText("Marca");
        gridCombosBusca.add(Marca);

        Categoria.setFont(new java.awt.Font("Inter", 0, 14)); // NOI18N
        Categoria.setText("Categoria");
        gridCombosBusca.add(Categoria);

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

        QtdadeDias.setFont(new java.awt.Font("Inter", 0, 14)); // NOI18N
        QtdadeDias.setLabelFor(txtQtdadeDias);
        QtdadeDias.setText("Quantidade de dias");

        Data.setFont(new java.awt.Font("Inter", 0, 14)); // NOI18N
        Data.setLabelFor(txtData);
        Data.setText("Data de início da locação (dd/MM/aaaa)");

        txtData.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(java.text.DateFormat.getDateInstance(java.text.DateFormat.SHORT))));

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
                        .addComponent(QtdadeDias)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtQtdadeDias, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(containerFormLayout.createSequentialGroup()
                        .addComponent(Data)
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
                    .addComponent(QtdadeDias)
                    .addComponent(txtQtdadeDias, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(containerFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Data)
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

    private void txtNomeSobrenomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomeSobrenomeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNomeSobrenomeActionPerformed

    private void txtCpfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCpfActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCpfActionPerformed

    private void btnLocarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLocarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnLocarActionPerformed

    @Override
    public void aoExibir() {
        atualizarCombos();
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Categoria;
    private javax.swing.JLabel Data;
    private javax.swing.JLabel Marca;
    private javax.swing.JLabel QtdadeDias;
    private javax.swing.JLabel Tipo;
    private javax.swing.JButton btnLocar;
    private javax.swing.JButton btnMatricular;
    private javax.swing.JButton btnVoltar;
    private javax.swing.JButton btnVoltar1;
    private javax.swing.JComboBox<String> cmbCategoria;
    private javax.swing.JComboBox<String> cmbMarca;
    private javax.swing.JComboBox<String> cmbTipoTermo;
    private javax.swing.JComboBox<String> cmbTipoVeiculo;
    private javax.swing.JPanel containerBotoes;
    private javax.swing.JPanel containerBotoes1;
    private javax.swing.JPanel containerCampoPesquisa;
    private javax.swing.JPanel containerConteudo;
    private javax.swing.JPanel containerForm;
    private javax.swing.JPanel gridCombosBusca;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel labeFiltroVeiculo;
    private javax.swing.JLabel labelBusca;
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
}
