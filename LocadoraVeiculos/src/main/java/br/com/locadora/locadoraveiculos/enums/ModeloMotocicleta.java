/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package br.com.locadora.locadoraveiculos.enums;

/**
 *
 * @author harpo
 */
public enum ModeloMotocicleta {
    CALIBRO("Calibro"), CG125("CG125"), CBR500("CBR500"),
    NX500("NX500"), SEIEMMEZZO("Seiemezzo"), SHADOW("Shadow"), R7("R7");
    
    // permite as enumeracoes terem valores proprios
    private final String descricao;
    
    ModeloMotocicleta(String descricao) {
        this.descricao = descricao;
    }
    
    // retorna o valor que sera mostrado no combo
    // mostra o valor String em vez da constante ENUM
    @Override
    public String toString() {
        return descricao;
    }
}
