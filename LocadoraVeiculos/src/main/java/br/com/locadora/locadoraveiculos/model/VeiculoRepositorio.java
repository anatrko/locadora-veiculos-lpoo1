/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.locadora.locadoraveiculos.model;

import br.com.locadora.locadoraveiculos.enums.Categoria;
import br.com.locadora.locadoraveiculos.enums.Estado;
import br.com.locadora.locadoraveiculos.enums.Marca;
import br.com.locadora.locadoraveiculos.enums.TipoVeiculo;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author harpo
 */
public class VeiculoRepositorio {
    private final List<Veiculo> veiculos = new ArrayList<>();
    
    public void incluir(Veiculo veiculo) {
        veiculos.add(veiculo);
    }
    
    public List<Veiculo> listarDisponiveis() {
        List<Veiculo> r = new ArrayList<>();
        for(Veiculo v: veiculos) {
            if(v != null && (v.getEstado() == Estado.DISPONIVEL || v.getEstado() == Estado.NOVO))
                r.add(v);
        }
        return r;
    }
    
    public List<Veiculo> listarLocados() {
        List<Veiculo> r = new ArrayList<>();
        for(Veiculo v: veiculos) {
            if(v != null && v.getEstado() == Estado.LOCADO)
                r.add(v);
        }
        return r;
    }
    
    // filtra em uma combinacao retornando veiculos DISPONIVEIS que atendam aos filtros
    // se filtro igual a null ele veiculo passa por ele, podendo ser barrado por outro filtro
    public List<Veiculo> filtrar(TipoVeiculo tipo, Marca marca, Categoria categoria) {
        List<Veiculo> filtrados = new ArrayList<>();
        for(Veiculo v: listarDisponiveis()) {
            boolean tipoOk = (tipo == null) || corresponde(v, tipo);
            boolean marcaOk = (marca == null) || v.getMarca() == marca;
            boolean categoriaOk = (categoria == null) || v.getCategoria() == categoria;
            
            if(tipoOk && marcaOk && categoriaOk)
                filtrados.add(v);
        }
        return filtrados;
    }
    
    private boolean corresponde(Veiculo v, TipoVeiculo tipo) {
        return switch (tipo) {
            case AUTOMOVEL -> v instanceof Automovel;
            case MOTOCICLETA -> v instanceof Motocicleta;
            case VAN -> v instanceof Van;
            default -> false;
        };
    }
    
    public boolean clientePossuiLocacao(Cliente cliente) {
        for(Veiculo v: veiculos)
            if(v.getLocacao() != null // como eh diferente de null soh se Estado.LOCADO, verficar estado eh redundante
                    && v.getLocacao().getCliente() == cliente)
                return true;
        return false;
    }
    
    public boolean existePlaca(String placa) {
        if(placa == null) return false;
        
        for(Veiculo v: veiculos) {
            if(v.getPlaca().equals(placa))
                return true;
        }
        return false;
    }
}
