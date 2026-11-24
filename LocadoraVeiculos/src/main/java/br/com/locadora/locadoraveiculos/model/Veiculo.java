/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.locadora.locadoraveiculos.model;

import br.com.locadora.locadoraveiculos.enums.Categoria;
import br.com.locadora.locadoraveiculos.enums.Estado;
import br.com.locadora.locadoraveiculos.enums.Marca;
import java.util.Calendar;

/**
 *
 * @author harpo
 */
public abstract class Veiculo implements VeiculoI{
    private final Marca marca;
    private Estado estado;
    private Locacao locacao;
    private final Categoria categoria;
    private final double valorDeCompra;
    private final String placa;
    private final int ano;

    public Veiculo(Marca marca, Estado estado, Categoria categoria, double valorDeCompra, String placa, int ano) {
        this.marca = marca;
        this.estado = estado;
        this.categoria = categoria;
        this.valorDeCompra = valorDeCompra;
        this.placa = placa;
        this.ano = ano;
        this.locacao = null;
    }

    @Override
    public void locar(int dias, Calendar data, Cliente cliente) {
        this.estado = Estado.LOCADO;
        this.locacao = new Locacao(dias, this.getValorDiariaLocacao(), data, cliente);
    }

    @Override
    public void vender() {
        this.estado = Estado.VENDIDO;
    }

    @Override
    public void devolver() {
        this.locacao = null;
        this.estado = Estado.DISPONIVEL;
    }

    @Override
    public Estado getEstado() { return this.estado; }
    @Override
    public Marca getMarca() { return this.marca; }
    @Override
    public Categoria getCategoria() { return this.categoria; }
    @Override
    public Locacao getLocacao() { return this.locacao; }
    @Override
    public String getPlaca() { return this.placa; }
    @Override
    public int getAno() { return this.ano; }
    
    @Override
    public double getValorParaVenda() {
        int anoAtual = Calendar.getInstance().get(Calendar.YEAR);
        int idadeVeiculoEmAnos = anoAtual - this.ano;
        
        double valorParaVenda = valorDeCompra - idadeVeiculoEmAnos*0.15*valorDeCompra;
        // se menor que 10% do valorDeCompra -> valorParaVenda eh 10% do valorDeCompra
        if(valorParaVenda < 0.1*valorDeCompra || valorParaVenda < 0)
            valorParaVenda = 0.1*valorDeCompra;
        return valorParaVenda;
    }
}
