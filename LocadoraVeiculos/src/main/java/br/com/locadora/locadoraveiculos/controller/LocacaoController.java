/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.locadora.locadoraveiculos.controller;

import br.com.locadora.locadoraveiculos.enums.Estado;
import br.com.locadora.locadoraveiculos.model.Cliente;
import br.com.locadora.locadoraveiculos.model.Veiculo;
import br.com.locadora.locadoraveiculos.model.VeiculoRepositorio;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author harpo
 */
public class LocacaoController {
    private final VeiculoRepositorio veiculos;

    public LocacaoController(VeiculoRepositorio veiculos) {
        this.veiculos = veiculos;
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
            throw new IllegalArgumentException("Cliente ou veículo não selecionado. Selecione um cliente e um veículo para locar.");
        
            //valida se o veiculo esta disponivel
        if( veiculo.getEstado() != Estado.DISPONIVEL && veiculo.getEstado() != Estado.NOVO)
            throw new IllegalArgumentException("Veículo escolhido não está disponível para locacação.");
        
        Calendar dataComparacao = (Calendar) data.clone();
        zerarHorario(dataComparacao);
        Calendar hoje = Calendar.getInstance();
        zerarHorario(hoje);
        if( dataComparacao.before( hoje ))
            throw new IllegalArgumentException("Data da locacação inválida. Informe uma data a partir do dia atual.");
        
        int dias;
        try {
            dias = Integer.parseInt(diasTxt.trim());
        } catch(NumberFormatException e) {
            throw new NumberFormatException("Informe a quantidade de dias corretamente.");
        }
        if( dias <= 0 || dias > 60)
            throw new IllegalArgumentException("Quantidade de dias inválida. Informe uma quantidade aceita: de 1 a 60 dias");
        
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
    listar locados
    */
    public List<Veiculo> listarLocados() {
        return Collections.unmodifiableList(veiculos.listarLocados());
    }
    
    private void zerarHorario(Calendar cal) {
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
    }
}
