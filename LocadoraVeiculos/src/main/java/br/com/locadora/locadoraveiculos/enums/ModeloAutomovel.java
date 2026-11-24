/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package br.com.locadora.locadoraveiculos.enums;

/**
 *
 * @author harpo
 */
public enum ModeloAutomovel {
    BRASILIA("Brasilia"), GOL("Gol"), CELTA("Celta"), 
    PALIO("Palio"), PULSE("Pulse"), LANCER("Lancer"), ONIX("Onix");
    
    // permite as enumeracoes terem valores proprios
    private final String descricao;
    
    ModeloAutomovel(String descricao) {
        this.descricao = descricao;
    }
    
    // retorna o valor que sera mostrado no combo
    // mostra o valor String em vez da constante ENUM
    @Override
    public String toString() {
        return descricao;
    }
}
