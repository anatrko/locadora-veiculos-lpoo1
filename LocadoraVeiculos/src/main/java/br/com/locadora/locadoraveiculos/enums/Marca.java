/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.locadora.locadoraveiculos.enums;

/**
 *
 * @author harpo
 */
public enum Marca {
    FIAT("Fiat"), GM("GM"), HONDA("Honda"), MERCEDES("Mercedes"), 
    MITSUBISHI("Mitsubishi"), MORINI("Morini"), VW("VW"), YAMAHA("Yamaha");
    
    // permite as enumeracoes terem valores proprios
    private final String descricao;
    
    Marca(String descricao) {
        this.descricao = descricao;
    }
    
    // retorna o valor que sera mostrado no combo
    // mostra o valor String em vez da constante ENUM
    @Override
    public String toString() {
        return descricao;
    }
}