/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.locadora.locadoraveiculos.controller;

import br.com.locadora.locadoraveiculos.enums.*;
import br.com.locadora.locadoraveiculos.model.Automovel;
import br.com.locadora.locadoraveiculos.model.Cliente;
import br.com.locadora.locadoraveiculos.model.Motocicleta;
import br.com.locadora.locadoraveiculos.model.Van;
import br.com.locadora.locadoraveiculos.model.Veiculo;
import br.com.locadora.locadoraveiculos.model.VeiculoRepositorio;
import java.util.Calendar;
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
    locar veiculo --- seleciona o cliente por nome/sobrenome/cpf (ClienteController) -> !null
        --- verifica se cliente ja possui locacao 
        --- filtrar os veiculos -> seleciona um da tabela
        --- recebe dias da locacao -> TextField validar e converter (int)
        --- recebe data da locacao -> TextField ou outro, validar (Calendar)
        aciona veiculo.locar(dias, data, cliente)
    */
    public void locar(Cliente cliente, Veiculo veiculo, String diasTxt, Calendar data)
            throws IllegalArgumentException {
        // valida as entradas
        if( cliente == null || veiculo == null)
            throw new IllegalArgumentException("Cliente ou veículo não selecionado. Selecione um clinte e um veículo para locar.");
        
            //valida se o veiculo esta disponivel
        if( veiculo.getEstado() != Estado.DISPONIVEL || veiculo.getEstado() != Estado.NOVO)
            throw new IllegalArgumentException("Veículo escolhido não está disponível para locacação.");
        
        Calendar dataComparacao = (Calendar) data.clone();
        zerarHorario(dataComparacao);
        Calendar hoje = Calendar.getInstance();
        zerarHorario(hoje);
        if( dataComparacao.before( hoje ))
            throw new IllegalArgumentException("Data da locacação inválida. Informe uma data a partir do dia atual.");
        
        int dias;
        try {
            dias = Integer.parseInt(diasTxt);
            if( dias <= 0)
                throw new IllegalArgumentException("Quantidade de dias inválida. Informe uma quantidade aceita: um ou mais dias.");
        } catch(NumberFormatException e) {
            throw new NumberFormatException("Informe a quantidade de dias corretamente.");
        }        
        
        // verifica se cliente selecionado ja possui locacao
        if( veiculos.clientePossuiLocacao(cliente) )
            throw new IllegalArgumentException("Esse cliente já possui uma locação. Clientes podem ter somente uma locação ativa.");
        
        // passa um clone do objeto Calendar para precaver do risco de
        // caso a instancia seja alterada na view, o objeto em memoria nao eh alterado
        veiculo.locar(dias, (Calendar) data.clone(), cliente);
    }
    
    /*
    devolver veiculo --- lista veiculos locados 
        --- seleciona o veiculo da tabela -> !null
        aciona veiculo.devolver
    */
    public void devolver(Veiculo veiculo) throws IllegalArgumentException {
        if(veiculo == null)
            throw new IllegalArgumentException("Locação não selecionada. Selecione um veículo locado para devolver.");
        
        if(veiculo.getEstado() != Estado.LOCADO)
            throw new IllegalArgumentException("Veículo não possui locação ativa. Selecione um veículo locado para devolver.");
        
        veiculo.devolver();
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
    
    /*
    listar locados
    */
    public List<Veiculo> listarLocados() {
        return Collections.unmodifiableList(veiculos.listarLocados());
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
    
    private void zerarHorario(Calendar cal) {
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
    }
}
