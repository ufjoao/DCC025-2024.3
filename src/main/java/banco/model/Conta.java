/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package banco.model;

import banco.exception.Validador;
import banco.persistence.Persistence;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;

/**
 *
 * @author rodri
 */
public class Conta {

    @Expose
    private int numeroDaConta;
    @Expose
    private float saldo;
    @Expose
    private ArrayList<String> movimentacoes;
    @SerializedName("cliente")
    private int clienteId;
    private static ArrayList<Conta> contasRegistradas = new ArrayList<>();

    public Conta() {
        this.movimentacoes = new ArrayList<>();
    }
    
    public Conta(int numeroDaConta)
    {
        this.numeroDaConta = numeroDaConta;
        saldo = 0f;
        this.movimentacoes = new ArrayList<>();
    }

    public Conta(int numero, int dono) {
        Validador.validarNumeroConta(numero);
        this.numeroDaConta = numero;
        this.saldo = 0;
        this.movimentacoes = new ArrayList<>();
        this.clienteId = dono;
    }

    public Cliente getDono() {
        return Persistence.buscarClientePorId(clienteId);
    }
    
    public int getClienteId() {
    return clienteId;
}

public void setClienteId(int clienteId) {
    this.clienteId = clienteId;
}

    public float getSaldo() {
        return saldo;
    }

    public void setSaldo(float saldo) {
        this.saldo = saldo;
    }

    public int getNumeroDaConta() {
        return numeroDaConta;
    }

    public ArrayList<String> getMovimentacoes() {
        return movimentacoes;
    }

    public void adicionarMovimentacao(String movimentacao) {
        if (movimentacao != null && !movimentacao.isEmpty()) {
            this.movimentacoes.add(movimentacao);
        } else {
            throw new IllegalArgumentException("Movimentação não pode ser nula ou vazia.");
        }
    }

    public void setMovimentacoes(ArrayList<String> movimentacoesTotais) {
        movimentacoes = movimentacoesTotais;
    }

    boolean verificaNumeroDaConta(int numeroDaConta) {
        return this.numeroDaConta == numeroDaConta;
    }

    public void deposito(float valor) {
        Validador.validarDeposito(valor);
        saldo += valor;
    }

    void saque(float valor) {
        Validador.validarSaque(valor, saldo);
        saldo -= valor;
    }

    public void transferir(Conta destino, float valor, int senha) {
        Validador.validarSenhaDaConta(senha, numeroDaConta);
        Validador.validarTransferencia(valor, destino, contasRegistradas);
        Validador.validarSaque(valor, saldo);
        this.saque(valor);
        Validador.validarDeposito(valor);
        destino.deposito(valor);
        movimentacoes.add("Transferência de R$ " + valor + " para conta " + destino.getNumeroDaConta());
    }

    void consultaSaldo() {
        System.out.println("Saldo da Conta\n");
        System.out.println("Saldo: R$" + saldo);
    }

    void gerarExtrato() {
        System.out.println("Extrato da Conta\n");

        if (movimentacoes != null) {
            System.out.println("Ultimas movimentacoes da conta:");

            for (int i = 0; i < movimentacoes.size(); i++) {
                System.out.println(movimentacoes.get(i));
            }
        } else {
            System.out.println("Conta sem movimentacoes");
        }
    }
}
