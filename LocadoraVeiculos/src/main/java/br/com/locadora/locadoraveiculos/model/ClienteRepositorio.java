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
    
    // -- dividido a funcao de busca ---
    // busca por nome e/ou sobrenome (ignora maiúsculas e acentos)
    public List<Cliente> buscarPorNome(String termo) {
        String t = normalizar(termo);
        List<Cliente> r = new ArrayList<>();
        for (Cliente c : clientes) {
            String nome = normalizar(c.getNome());
            String sobrenome = normalizar(c.getSobrenome());
            String completo = nome + " " + sobrenome;
            if (completo.contains(t)) {
                r.add(c);
            }
        }
        return r;
    }

    // busca por CPF (termo só com dígitos)
    public List<Cliente> buscarPorCpf(String cpf) {
        List<Cliente> r = new ArrayList<>();
        for (Cliente c : clientes) {
            if (c.getCpf().contains(cpf)) {
                r.add(c);
            }
        }
        return r;
    }

    private String normalizar(String s) {
        String semAcento = java.text.Normalizer.normalize(s, java.text.Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "");
        return semAcento.toLowerCase().trim();
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
