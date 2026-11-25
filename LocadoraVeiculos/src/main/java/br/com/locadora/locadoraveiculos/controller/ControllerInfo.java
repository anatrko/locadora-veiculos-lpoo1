package br.com.locadora.locadoraveiculos.controller;

import br.com.locadora.locadoraveiculos.model.Cliente;
import br.com.locadora.locadoraveiculos.model.Veiculo;
import java.util.ArrayList;
import java.util.List;

public class ControllerInfo {

    private static final List<Cliente> clientes = new ArrayList<>();
    private static final List<Veiculo> veiculos = new ArrayList<>();

    public static List<Cliente> getClientes() {
        return clientes;
    }

    public static List<Veiculo> getVeiculos() {
        return veiculos;
    }
}
