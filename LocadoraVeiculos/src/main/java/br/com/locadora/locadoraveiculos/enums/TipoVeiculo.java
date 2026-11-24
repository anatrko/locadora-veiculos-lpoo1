/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package br.com.locadora.locadoraveiculos.enums;

/**
 *  Classe {@code TipoAutomovel} auxiliar, para os filtros de tipo nas telas de:
 * incluir (combo 'categoria'), locar e vender veiculo
 * 
 * @author harpo
 */
public enum TipoVeiculo {
    AUTOMOVEL("Automovel"), 
    MOTOCICLETA("Motocicleta"), 
    VAN("Van");
    
    // permite as enumeracoes terem valores proprios
    private final String descricao;
    
    TipoVeiculo(String descricao) {
        this.descricao = descricao;
    }
    
    // retorna o valor que sera mostrado no combo
    // "Automovel" em vez de "AUTOMOVEL"
    @Override
    public String toString() {
        return descricao;
    }
}
