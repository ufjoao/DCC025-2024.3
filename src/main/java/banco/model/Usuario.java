/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package banco.model;

import banco.exception.Validador;
import banco.persistence.Persistence;
import com.google.gson.annotations.Expose;

/**
 *
 * @author rodri
 */
public abstract class Usuario {

    @Expose
    private int id;
    @Expose
    private String nome;
    @Expose
    private String cpf;
    @Expose
    private int senha;

    protected Usuario() {
    }

    public Usuario(String nome, String cpf, int senha) {
        this.id = Persistence.gerarIdAleatorio();
        this.nome = nome;
        this.cpf = cpf;
        this.senha = senha;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSenha() {
        return senha;
    }

    public abstract String getTipo();

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
