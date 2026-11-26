
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

    private JComboBox<Cliente> cbClientes;
    private JComboBox<Veiculo> cbVeiculos;
    private JTextField txtDias;
    private JTextField txtData;

    public TelaLocarVeiculo(
            Navegador nv,
            ClienteController clienteCtrl,
            VeiculoController veiculoCtrl,
            LocacaoController locacaoCtrl) {

        this.clienteCtrl = clienteCtrl;
        this.veiculoCtrl = veiculoCtrl;
        this.locacaoCtrl = locacaoCtrl;

        setLayout(new BorderLayout(10, 10));

        JLabel titulo = new JLabel("Locação de Veículos");
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        add(titulo, BorderLayout.NORTH);

        JPanel painel = new JPanel(new GridLayout(4, 2, 10, 15));
        painel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        cbClientes = new JComboBox<>();
        cbVeiculos = new JComboBox<>();
        txtDias = new JTextField();
        txtData = new JTextField();

        painel.add(new JLabel("Cliente:"));
        painel.add(cbClientes);

        painel.add(new JLabel("Veículo disponível:"));
        painel.add(cbVeiculos);

        painel.add(new JLabel("Quantidade de dias:"));
        painel.add(txtDias);

        painel.add(new JLabel("Data da locação (dd/MM/yyyy):"));
        painel.add(txtData);

        add(painel, BorderLayout.CENTER);

        JButton btnLocar = new JButton("Confirmar locação");
        JButton btnVoltar = new JButton("Voltar");

        JPanel botoes = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        botoes.add(btnVoltar);
        botoes.add(btnLocar);

        add(botoes, BorderLayout.SOUTH);

        configurarRenderizadores();

        btnLocar.addActionListener(e -> realizarLocacao());
        btnVoltar.addActionListener(e -> nv.irPara(TelaInicial.MENU));

        atualizarCombos();
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
        String dataTexto = txtData.getText().trim();

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
            txtData.setText("");

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
        atualizarCombos();
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
