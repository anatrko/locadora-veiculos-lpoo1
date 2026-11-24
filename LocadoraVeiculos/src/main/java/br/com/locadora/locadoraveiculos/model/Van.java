/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.locadora.locadoraveiculos.model;

import br.com.locadora.locadoraveiculos.enums.Categoria;
import br.com.locadora.locadoraveiculos.enums.Estado;
import br.com.locadora.locadoraveiculos.enums.Marca;
import br.com.locadora.locadoraveiculos.enums.ModeloVan;

/**
 *
 * @author harpo
 */
public class Van extends Veiculo {
    private final ModeloVan modelo;

    public Van(Marca marca, Estado estado, Categoria categoria, double valorDeCompra, 
            String placa, int ano, ModeloVan modelo) {
        super(marca, estado, categoria, valorDeCompra, placa, ano);
        this.modelo = modelo;
    }

    public ModeloVan getModelo() { return modelo; }

    @Override
    public double getValorDiariaLocacao() {
        switch( this.getCategoria() ) {
            case(Categoria.POPULAR) -> { return 200f; }
            case(Categoria.INTERMEDIARIO) -> { return 400f; }
            case(Categoria.LUXO) -> { return 600f; }
        }
        return 0;
    }
    
}
