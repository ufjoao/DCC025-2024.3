/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package banco.model;

import banco.exception.Validador;

/**
 *
 * @author rodri
 */
public class Usuario {

    private String nome;
    private String cpf;
    int senha;

    public Usuario(String nome, String cpf, int senha) {
        Validador.validarNome(nome);
        Validador.validarCpf(cpf);
        Validador.validarSenha(senha);

        this.nome = nome;
        this.cpf = cpf;
        this.senha = senha;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        Validador.validarNome(nome);
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setSenha(int senha) {
        Validador.validarSenha(senha);
        this.senha = senha;
    }

    public boolean verificaSenha(int senha) {
        return senha == this.senha;
    }
}
