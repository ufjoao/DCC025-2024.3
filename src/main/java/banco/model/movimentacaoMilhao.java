/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package banco.model;

/**
 *
 * @author Alexandre
 */

import banco.persistence.Persistence;
import java.util.ArrayList;

public class movimentacaoMilhao {
    private Cliente cliente;
    private Conta origem;
    private Conta destino;
    private float valor;
    
    public movimentacaoMilhao(Cliente cliente, Conta origem, Conta destino, float valor) {
        this.cliente = cliente;
        this.origem = origem;
        this.destino = destino;
        this.valor = valor;
    }
    
    public String getNomeCliente() {
        return cliente.getNome();
    }
    
    public int getNumeroOrigem() {
        return origem.getNumeroDaConta();
    }
    
    public int getNumeroDestino() {
        return destino.getNumeroDaConta();
    }
    
    public String getNomeDestino() {
        return destino.getDono().getNome();
    }
    
    public float getSaldoCliente() {
        return origem.getSaldo();
    }
    
    public float getValor() {
        return valor;
    }
    
    public void executarTransferencia() {
        origem.saque(valor);
        origem.adicionarMovimentacao("Transferência enviada para " + getNomeDestino() + " no valor de: " + valor);
        destino.deposito(valor);
        destino.adicionarMovimentacao("Transferência recebida de " + getNomeCliente() + " no valor de: " + valor);

        
        System.out.println("Transferência realizada com sucesso!"); 
    }            
}

