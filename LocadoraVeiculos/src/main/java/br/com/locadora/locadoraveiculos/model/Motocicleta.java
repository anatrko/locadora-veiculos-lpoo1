/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.locadora.locadoraveiculos.model;

import br.com.locadora.locadoraveiculos.enums.Categoria;
import br.com.locadora.locadoraveiculos.enums.Estado;
import br.com.locadora.locadoraveiculos.enums.Marca;
import br.com.locadora.locadoraveiculos.enums.ModeloMotocicleta;

/**
 *
 * @author harpo
 */
public class Motocicleta extends Veiculo {
    private final ModeloMotocicleta modelo;

    public Motocicleta(Marca marca, Estado estado, Categoria categoria, double valorDeCompra, 
            String placa, int ano, ModeloMotocicleta modelo) {
        super(marca, estado, categoria, valorDeCompra, placa, ano);
        this.modelo = modelo;
    }

    public ModeloMotocicleta getModelo() { return modelo; }

    @Override
    public double getValorDiariaLocacao() {
        switch( this.getCategoria() ) {
            case(Categoria.POPULAR) -> { return 70f; }
            case(Categoria.INTERMEDIARIO) -> { return 200f; }
            case(Categoria.LUXO) -> { return 350f; }
        }
        return 0;
    }
}
