//João Alexandre dos Santos Nunes – 202235029
//João Antônio Fonseca e Almeida – 201935010
//Rodrigo da Silva Soares – 201765218AB

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package banco.model;

import banco.persistence.Persistence;
import java.util.ArrayList;

/**
 *
 * @author Alexandre
 */
public class saqueMilhao {
    private Cliente cliente;
    private Conta conta;
    private float valor;
    
    public saqueMilhao(Cliente cliente, Conta conta, float valor) {
        this.cliente = cliente;
        this.conta = conta;
        this.valor = valor;
    }
    
    public String getNomeCliente() {
        return cliente.getNome();
    }
    
    public int getNumeroConta() {
        return conta.getNumeroDaConta();
    }
    
     public float getSaldoCliente() {
        return conta.getSaldo();
    }
    
    public float getValor() {
        return valor;
    }
    
    public void executarSaque() {
        conta.saque(valor);
        System.out.println("Saque realizado com sucesso!");
    }
}
