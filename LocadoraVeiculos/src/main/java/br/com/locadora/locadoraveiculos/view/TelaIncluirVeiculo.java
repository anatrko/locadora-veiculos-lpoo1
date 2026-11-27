
package br.com.locadora.locadoraveiculos.view;

import br.com.locadora.locadoraveiculos.controller.VeiculoController;
import br.com.locadora.locadoraveiculos.enums.*;

import java.awt.*;
import java.text.DecimalFormat;
import javax.swing.*;
import javax.swing.text.MaskFormatter;
import javax.swing.text.NumberFormatter;

public class TelaIncluirVeiculo extends JPanel implements Atualizavel {

    private final VeiculoController veiculoCtrl;

    private final JComboBox<TipoVeiculo> cbTipo;
    private final JComboBox<Marca> cbMarca;
    private final JComboBox<Estado> cbEstado;
    private final JComboBox<Categoria> cbCategoria;
    private final JComboBox<Object> cbModelo;

    private final JFormattedTextField txtValor;
    private final JFormattedTextField txtPlaca;
    private final JFormattedTextField txtAno;

    public TelaIncluirVeiculo(Navegador nv, VeiculoController veiculoCtrl) {
        this.veiculoCtrl = veiculoCtrl;
        
        setLayout(new BorderLayout(10, 10));

        JLabel titulo = new JLabel("Cadastro de Veículos");
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        add(titulo, BorderLayout.NORTH);

        JPanel painel = new JPanel(new GridLayout(8, 2, 10, 12));
        painel.setBorder(BorderFactory.createEmptyBorder(25, 30, 25, 30));

        cbTipo = new JComboBox<>(TipoVeiculo.values());
        cbMarca = new JComboBox<>(Marca.values());

        cbEstado = new JComboBox<>(new Estado[]{
            Estado.NOVO, Estado.DISPONIVEL
        });

        cbCategoria = new JComboBox<>(Categoria.values());
        cbModelo = new JComboBox<>();

        txtValor = criarCampoMonetario();
        txtPlaca = criarCampoFormatado("UUU-####");
        txtAno = criarCampoFormatado("####");

        painel.add(new JLabel("Tipo de veículo:"));
        painel.add(cbTipo);

        painel.add(new JLabel("Marca:"));
        painel.add(cbMarca);

        painel.add(new JLabel("Estado:"));
        painel.add(cbEstado);

        painel.add(new JLabel("Categoria:"));
        painel.add(cbCategoria);

        painel.add(new JLabel("Modelo:"));
        painel.add(cbModelo);

        painel.add(new JLabel("Valor de compra (R$):"));
        painel.add(txtValor);

        painel.add(new JLabel("Placa (ABC-1234):"));
        painel.add(txtPlaca);

        painel.add(new JLabel("Ano:"));
        painel.add(txtAno);

        add(painel, BorderLayout.CENTER);

        JButton btnCadastrar = new JButton("Cadastrar veículo");
        JButton btnVoltar = new JButton("Voltar");

        JPanel botoes = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        botoes.add(btnVoltar);
        botoes.add(btnCadastrar);

        add(botoes, BorderLayout.SOUTH);

        cbTipo.addActionListener(e -> atualizarModelos());

        btnCadastrar.addActionListener(e -> cadastrarVeiculo());
        btnVoltar.addActionListener(e -> nv.irPara(TelaInicial.MENU));

        atualizarModelos();
    }

    private void atualizarModelos() {
        cbModelo.removeAllItems();

        TipoVeiculo tipo = (TipoVeiculo) cbTipo.getSelectedItem();

        if (tipo == null) {
            return;
        }

        switch (tipo) {
            case AUTOMOVEL:
                for (ModeloAutomovel modelo : ModeloAutomovel.values()) {
                    cbModelo.addItem(modelo);
                }
                break;

            case MOTOCICLETA:
                for (ModeloMotocicleta modelo : ModeloMotocicleta.values()) {
                    cbModelo.addItem(modelo);
                }
                break;

            case VAN:
                for (ModeloVan modelo : ModeloVan.values()) {
                    cbModelo.addItem(modelo);
                }
                break;
        }
    }

    private void cadastrarVeiculo() {
        try {
            veiculoCtrl.incluir(
                (TipoVeiculo) cbTipo.getSelectedItem(),
                (Marca) cbMarca.getSelectedItem(),
                (Estado) cbEstado.getSelectedItem(),
                (Categoria) cbCategoria.getSelectedItem(),
                (Enum<?>) cbModelo.getSelectedItem(),
                ((Number) txtValor.getValue()).doubleValue(),
                txtPlaca.getText(),
                txtAno.getText().replace("_", "")
            );

            JOptionPane.showMessageDialog(this,
                    "Veículo cadastrado com sucesso!");

            limparCampos();
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this,
                    e.getMessage(),
                    "Erro no cadastro",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limparCampos() {
        txtValor.setText("0.00");
        txtPlaca.setText("");
        txtAno.setText("");

        cbTipo.setSelectedIndex(0);
        cbMarca.setSelectedIndex(0);
        cbEstado.setSelectedIndex(0);
        cbCategoria.setSelectedIndex(0);

        atualizarModelos();
    }
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    @Override
    public void aoExibir() {
        limparCampos();
    }
    
    private JFormattedTextField criarCampoFormatado(String mascara) {
        try {
            MaskFormatter formatador = new MaskFormatter(mascara);
            formatador.setPlaceholderCharacter('_');
            return new JFormattedTextField(formatador);
        } catch(java.text.ParseException e){
            // Como mascara eh fixa interna, ParseException nao ocorre
            // Se ocorrer retorna campo sem mascara
            return new JFormattedTextField();
        }
    }
    
    private JFormattedTextField criarCampoMonetario() {
        DecimalFormat formato = new DecimalFormat("#,##0.00");
        NumberFormatter formatador = new NumberFormatter(formato);
        formatador.setValueClass(Double.class);
        formatador.setMinimum(0.0);
        formatador.setAllowsInvalid(false);
        
        JFormattedTextField campo = new JFormattedTextField(formatador);
        campo.setValue(0.00);
        return campo;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}