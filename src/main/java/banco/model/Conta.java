/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package banco.model;

import banco.exception.Validador;
import java.util.ArrayList;

/**
 *
 * @author rodri
 */
public class Conta {

    private int numeroDaConta;
    private String agencia;
    private float saldo;
    private int senha;
    private ArrayList<String> movimentacoes;
    private static ArrayList<Integer> contasRegistradas;

    public Conta(int numero, String agencia, int senha) {
        Validador.validarNumeroConta(numero);
        this.numeroDaConta = numero;
        this.agencia = "0001";
        this.saldo = 0;
        Validador.validarSenhaDaConta(senha, numero);
        this.senha = senha;
        this.movimentacoes = new ArrayList<>();
        contasRegistradas.add(numero);
    }

    public boolean verificarSenha(int senhaInformada) {
        return this.senha == senhaInformada;
    }

    public String getAgencia() {
        return agencia;
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

    public void setMovimentacoes(String movimentacao) {
        //vai armazenar uma string com o tipo de movimentação e o valor
        movimentacoes.add(movimentacao);
    }

    boolean verificaNumeroDaConta(int numeroDaConta) {
        return this.numeroDaConta == numeroDaConta;
    }

    public void deposito(float valor) {
        Validador.validarDeposito(valor);
        saldo += valor;
        movimentacoes.add("Depósito de R$ " + valor);
    }

    void saque(float valor) {
        Validador.validarSaque(valor, saldo);
        saldo -= valor;
    }

    public void transferir(Conta destino, float valor, int senha) {
        Validador.validarSenha(senha);
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
