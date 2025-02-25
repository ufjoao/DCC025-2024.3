package banco.model;

import java.util.ArrayList;
import java.util.List;

public class Cliente {

    private String nome;
    private String cpf;
    private String senha;
    private List<Conta> contas; // Um cliente pode ter várias contas

    // Construtor
    public Cliente(String nome, String cpf, String senha) {
        if (nome == null || nome.length() < 2) {
            throw new IllegalArgumentException("Nome deve ter pelo menos 2 caracteres.");
        }
        if (!cpfValido(cpf)) {
            throw new IllegalArgumentException("CPF inválido.");
        }
        if (senha == null || senha.length() < 6) {
            throw new IllegalArgumentException("Senha deve ter pelo menos 6 caracteres.");
        }

        this.nome = nome;
        this.cpf = cpf;
        this.senha = senha;
        this.contas = new ArrayList<>();
    }

    // Getters e Setters
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        if (nome == null || nome.length() < 2) {
            throw new IllegalArgumentException("Nome deve ter pelo menos 2 caracteres.");
        }
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        if (senha == null || senha.length() < 6) {
            throw new IllegalArgumentException("Senha deve ter pelo menos 6 caracteres.");
        }
        this.senha = senha;
    }

    public List<Conta> getContas() {
        return contas;
    }

    public void adicionarConta(Conta conta) {
        if (conta == null) {
            throw new IllegalArgumentException("Conta não pode ser nula.");
        }
        contas.add(conta);
    }

    // Método auxiliar para validar CPF (simplificado)
    private boolean cpfValido(String cpf) {
        return cpf != null && cpf.matches("\\d{11}"); // Apenas verifica se tem 11 dígitos numéricos
    }
}
