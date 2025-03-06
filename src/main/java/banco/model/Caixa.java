/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package banco.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 * @author rodri
 */
public class Caixa extends Usuario {

    public Caixa(@JsonProperty("nome") String nome,
            @JsonProperty("cpf") String cpf,
            @JsonProperty("senha") int senha) {
        super(nome, cpf, senha);
    }

    public void realizarDeposito(Conta conta, float valor) {
        conta.deposito(valor);
        System.out.println("Depósito de R$" + valor + " realizado para " + conta.getDono().getNome());
    }

    public void realizarSaque(Conta conta, float valor) {
        conta.saque(valor);
        System.out.println("Saque de R$" + valor + " realizado para " + conta.getDono().getNome());
    }

    @Override
    public String getTipo() {
        return "Caixa";
    }
}
