/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.locadora.locadoraveiculos.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author harpo
 */
public class ClienteRepositorio {
    private final List<Cliente> clientes = new ArrayList<>();
    
    public void incluir(Cliente cliente) {
        clientes.add(cliente);
    }
    
    public void atualizar(Cliente antigo, String nome, String sobrenome, String rg, String cpf, String endereco) {
        antigo.setNome(nome);
        antigo.setSobrenome(sobrenome);
        antigo.setRg(rg);
        antigo.setCpf(cpf);
        antigo.setEndereco(endereco);
    }
    
    public void excluir(Cliente cliente) {
        clientes.remove(cliente);
    }
    
    public List<Cliente> listar() {
        return clientes;
    }
    
    // busca os alunos com determinado nome, sobrenome ou cpf
    public List<Cliente> buscar(String termo) {
        String t = termo.toLowerCase();
        List<Cliente> r = new ArrayList<>();
        for(Cliente c: clientes){
            if(c.getNome().toLowerCase().contains(t) ||
                    c.getSobrenome().toLowerCase().contains(t) ||
                    c.getCpf().contains(t))
                r.add(c);
        }
        return r;
    }
    
    public boolean existeCpf(String cpf) {
        return existeCpf(cpf, null);
    }
    
    public boolean existeCpf(String cpf, Cliente ignora) {
        if(cpf == null) return false;
        
        for(Cliente c: clientes) {
            if(c == null || c.equals(ignora))
                continue;
            if(c.getCpf().equals(cpf))
                return true;
        }
        return false;
    }
}
