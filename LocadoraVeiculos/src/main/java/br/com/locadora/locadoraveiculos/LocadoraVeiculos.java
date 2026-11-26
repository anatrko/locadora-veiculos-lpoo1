/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package br.com.locadora.locadoraveiculos;

import br.com.locadora.locadoraveiculos.controller.*;
import br.com.locadora.locadoraveiculos.model.ClienteRepositorio;
import br.com.locadora.locadoraveiculos.model.VeiculoRepositorio;
import br.com.locadora.locadoraveiculos.view.TelaInicial;
import javax.swing.SwingUtilities;

/**
 *
 * @author anaca
 */
public class LocadoraVeiculos {

    public static void main(String[] args) {
        VeiculoRepositorio veiculoRepo = new VeiculoRepositorio();
        ClienteRepositorio clienteRepo = new ClienteRepositorio();
        
        VeiculoController veiculoCtrl = new VeiculoController(veiculoRepo);
        LocacaoController locacaoCtrl = new LocacaoController(veiculoRepo);
        ClienteController clienteCtrl = new ClienteController(clienteRepo, veiculoRepo);
        
        SwingUtilities.invokeLater(() 
                -> new TelaInicial(clienteCtrl, veiculoCtrl, locacaoCtrl).setVisible(true));
    }
}
