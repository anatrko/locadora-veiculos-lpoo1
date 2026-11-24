/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.locadora.locadoraveiculos.model;

import br.com.locadora.locadoraveiculos.enums.Categoria;
import br.com.locadora.locadoraveiculos.enums.Estado;
import br.com.locadora.locadoraveiculos.enums.Marca;
import br.com.locadora.locadoraveiculos.enums.ModeloAutomovel;

/**
 *
 * @author harpo
 */
public class Automovel extends Veiculo {
    private final ModeloAutomovel modelo;

    public Automovel(Marca marca, Estado estado, Categoria categoria, double valorDeCompra, 
            String placa, int ano, ModeloAutomovel modelo) {
        super(marca, estado, categoria, valorDeCompra, placa, ano);
        this.modelo = modelo;
    }

    public ModeloAutomovel getModelo() { return modelo; }

    @Override
    public double getValorDiariaLocacao() {
        switch( this.getCategoria() ) {
            case(Categoria.POPULAR) -> { return 100f; }
            case(Categoria.INTERMEDIARIO) -> { return 300f; }
            case(Categoria.LUXO) -> { return 450f; }
        }
        return 0;
    }
}
