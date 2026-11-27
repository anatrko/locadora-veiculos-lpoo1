package br.com.locadora.locadoraveiculos.controller;

import br.com.locadora.locadoraveiculos.model.Cliente;
import br.com.locadora.locadoraveiculos.model.ClienteRepositorio;
import br.com.locadora.locadoraveiculos.model.VeiculoRepositorio;
import java.util.Collections;
import java.util.List;

public class ClienteController {
    private final ClienteRepositorio clientes;
    private final VeiculoRepositorio veiculos;
    
    public ClienteController(ClienteRepositorio clienteRepo, VeiculoRepositorio veiculoRepo) {
       this.clientes = clienteRepo;
       this.veiculos = veiculoRepo;
    }
    
    public void incluir(String nome, String sobrenome, String rg, String cpf, String endereco) 
            throws IllegalArgumentException {
        nome = nome.trim(); 
        sobrenome = sobrenome.trim();
        rg = rg.trim();
        endereco = endereco.trim();
        
        // verificacao e conversoes
        if( nome.isBlank() || sobrenome.isBlank() || rg.isBlank() || cpf.isBlank() || endereco.isBlank() )
            throw new IllegalArgumentException("Preencha todos os campos!");
        
        if(!cpf.matches("^\\d{3}.\\d{3}.\\d{3}-\\d{2}$"))
            throw new IllegalArgumentException("Formato do CPF incorreto!");
        cpf = cpf.replaceAll("\\D", ""); // limpa a formatacao para armazenar sem a mascara
        
        if( clientes.existeCpf(cpf) )
            throw new IllegalArgumentException("CPF encontrado no sistema. Não são permitido dois clientes com mesmo CPF.");
        
        if( !verificaNome(nome) )
            throw new IllegalArgumentException("Preencha o nome corretamente! "
                        + "Deve possuir mais de tres caracteres e sem numeros");
        
        if( !verificaSobrenome(sobrenome) )
            throw new IllegalArgumentException("Preencha o sobrenome corretamente!"
                    + "Nao deve possuir numeros nem caracteres especiais");
        
        /* caso implementar verificacao do rg ou cpf --- faze-lo aqui  ---*/
        
        // instancia um novo cliente e inclui-o na lista
        Cliente novo = new Cliente(nome, sobrenome, rg, cpf, endereco);
        
        clientes.incluir(novo);
    }
    
    public void atualizar(Cliente cliente, String nome, String sobrenome, String rg, String cpf, String endereco)
            throws IllegalArgumentException {
        
        if( cliente == null )
            throw new IllegalArgumentException("Nenhum cliente selecionado para atualizar. Selecione um clinte na tabela.");
        
        if( nome.isBlank() || sobrenome.isBlank() || rg.isBlank() || cpf.isBlank() || endereco.isBlank() )
            throw new IllegalArgumentException("Preencha todos os campos!");
        
        if(!cpf.matches("^\\d{3}.\\d{3}.\\d{3}-\\d{2}$"))
            throw new IllegalArgumentException("Formato do CPF incorreto!");
        cpf = cpf.replaceAll("\\D", ""); // limpa a formatacao para armazenar sem a mascara
        
        if( clientes.existeCpf(cpf, cliente) ) // procura um cpf igual, ignorando o cliente indicado
            throw new IllegalArgumentException("CPF encontrado no sistema. Não são permitidos dois clientes com mesmo CPF.");
        
        if( !verificaNome(nome.trim()) )
            throw new IllegalArgumentException("Preencha o nome corretamente! "
                        + "Deve possuir mais de tres caracteres e sem numeros");
        
        if( !verificaSobrenome(sobrenome.trim()) )
            throw new IllegalArgumentException("Preencha o sobrenome corretamente!"
                    + "Nao deve possuir numeros nem caracteres especiais");
        
        // ao atribuir uma nova instancia de cliente ao cliente antigo
        //  a instancia antiga continua sendo apontada por uma locacao ativa, o que gera inconsistencias
        clientes.atualizar(cliente, nome.trim(), sobrenome.trim(), rg.trim(), cpf.trim(), endereco.trim());
    }
    
    public void excluir(Cliente cliente) throws IllegalArgumentException {
        if( cliente == null )
            throw new IllegalArgumentException("Nenhum cliente selecionado para excluir. Selecione um clinte na tabela.");
        
        if( veiculos.clientePossuiLocacao(cliente) )
            throw new IllegalArgumentException("Não é possível excluir este cliente, pois ele possui um veículo alugado!");
        
        clientes.excluir(cliente);
    }
    

    private boolean verificaNome(String nome) {
        String regex = "^[\\p{L}]+(?:[ ]+[\\p{L}]+)*$";
        return nome.length() >= 3 && nome.matches(regex);
    }

    private boolean verificaSobrenome(String sobrenome) {
        String regex = "^[\\p{L}]+(?:[ ]+[\\p{L}]+)*$";
        return sobrenome.length() >= 3 && sobrenome.matches(regex);
    }

    
    public List<Cliente> getClientes() {
        return clientes.listar();
    }
    
   public List<Cliente> buscarPorNome(String termo) {
        if (termo == null || termo.isBlank()) return clientes.listar();
        return Collections.unmodifiableList(clientes.buscarPorNome(termo));
    }

    public List<Cliente> buscarPorCpf(String cpf) {
        if (cpf == null) return clientes.listar();
        String digitos = cpf.replaceAll("\\D", "");
        if (digitos.isEmpty()) return clientes.listar();
        return Collections.unmodifiableList(clientes.buscarPorCpf(digitos));
    }
}
