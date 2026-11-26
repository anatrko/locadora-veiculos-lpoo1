/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.locadora.locadoraveiculos.controller;

import br.com.locadora.locadoraveiculos.enums.*;
import br.com.locadora.locadoraveiculos.model.Automovel;
import br.com.locadora.locadoraveiculos.model.Motocicleta;
import br.com.locadora.locadoraveiculos.model.Van;
import br.com.locadora.locadoraveiculos.model.Veiculo;
import br.com.locadora.locadoraveiculos.model.VeiculoRepositorio;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author harpo
 */
public class VeiculoController {
    private final VeiculoRepositorio veiculos;
    
    public VeiculoController(VeiculoRepositorio veiculoRepo) {
        this.veiculos = veiculoRepo;
    }
    
    /*
    incluir veiculo --- recebe de comboBox ('Tipo', Marca, Estado, Categoria, Modelo)
        --- nao faz muito sentido incluir como Estado.VENDIDO ou Estado.LOCADO (preencher combo sem estes eh uma opcao) ---
        --- recebe valorDeCompra textField valor monetario
        --- recebe placa -> verificar se no padrao XXX-0000
        instancia e armazena na memoria
    */
    public void incluir(TipoVeiculo tipo, Marca marca, Estado estado, Categoria categoria,
            Enum<?> modelo, String valorTxt, String placa, String anoTxt) 
            throws IllegalArgumentException {
        
        if(tipo == null || marca == null || estado == null || categoria == null 
                || modelo == null || valorTxt.isBlank() || placa.isBlank() || anoTxt.isBlank())
            throw new IllegalArgumentException("Preencha todos os campos!");
        
        if(!placa.trim().matches("^[A-Z]{3}-\\d{4}$"))
            throw new IllegalArgumentException("Placa inválida. Use o formato XXX-0000 .");
        
        if ( veiculos.existePlaca(placa) )
            throw new IllegalArgumentException("Placa encontrada no sistema. Não são permitidos dois veículos com a mesma placa.´d");
        
        double valor;
        int ano;
        
        try {
            valor = Double.parseDouble(valorTxt.trim().replace(".", "").replace(",", "."));
            ano = Integer.parseInt(anoTxt);
        } catch(NumberFormatException e) {
            throw new NumberFormatException("Valor de compra ou ano inválido!");
        }
        
        veiculos.incluir(criar(tipo, marca, estado, categoria, modelo, valor, placa, ano));
    }
    
    /*
    vender veiculo --- filtrar os veiculos -> seleciona um da tabela
        aciona veiculo.vender
    */
    public void vender(Veiculo veiculo) throws IllegalArgumentException {
        if(veiculo == null)
            throw new IllegalArgumentException("Veículo não selecionado. Selecione um veículo disponível para venda.");
        
            //valida se o veiculo esta disponivel
        if( veiculo.getEstado() != Estado.DISPONIVEL || veiculo.getEstado() != Estado.NOVO)
            throw new IllegalArgumentException("Veículo escolhido não está disponível para venda.");
        
        veiculo.vender();
    }
    
    /*
    filtrar os veiculos --- VeiculoRepositorio
        --- recebe de comboBox (filtros Tipo, Marca, Categoria) -> valida
    */
    public List<Veiculo> filtrar(TipoVeiculo tipo, Marca marca, Categoria categoria) {
        return Collections.unmodifiableList(veiculos.filtrar(tipo, marca, categoria));
    }
    
    public List<Veiculo> listarDisponiveis() {
        return Collections.unmodifiableList(veiculos.listarDisponiveis());
    }
    
    private Veiculo criar(TipoVeiculo tipo, Marca marca, Estado estado, Categoria categoria, 
            Enum<?> modelo, double valor, String placa, int ano) throws IllegalArgumentException {
        try {
            return switch(tipo) {
                    case AUTOMOVEL -> new Automovel(marca, estado, categoria, valor, placa, ano, (ModeloAutomovel) modelo);
                    case MOTOCICLETA -> new Motocicleta(marca, estado, categoria, valor, placa, ano, (ModeloMotocicleta) modelo);
                    case VAN -> new Van(marca, estado, categoria, valor, placa, ano, (ModeloVan) modelo);
                    default -> throw new IllegalArgumentException("Tipo de veiculo invalido!");
            };
        } catch(ClassCastException e) {
            throw new IllegalArgumentException("O modelo selecionado nao corresponde ao tipo do veiculo!");
        }
    }
}
