//João Alexandre dos Santos Nunes – 202235029
//João Antônio Fonseca e Almeida – 201935010
//Rodrigo da Silva Soares – 201765218AB

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package banco.model;

/**
 *
 * @author rodri
 */
public class Caixa extends Usuario {

    public Caixa (String nome, String cpf, int senha) {
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
