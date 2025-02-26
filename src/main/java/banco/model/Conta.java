/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package banco.model;

import banco.exception.Validador;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.util.ArrayList;

/**
 *
 * @author rodri
 */
public class Conta {

    @JsonProperty("numeroDaConta")
    private int numeroDaConta;
    private String agencia;
    @JsonProperty("saldo")
    private float saldo;
    private int senha;
    @JsonProperty("movimentacoes")
    private ArrayList<String> movimentacoes;
    @JsonProperty("dono")
    private Cliente dono;
    private static ArrayList<Conta> contasRegistradas = new ArrayList<>();

    public Conta() {
        this.movimentacoes = new ArrayList<>();
    }

    public Conta(int numero, int senha, Cliente dono) {
        Validador.validarNumeroConta(numero);
        this.numeroDaConta = numero;
        this.agencia = "0001";
        this.saldo = 0;
        Validador.validarSenhaDaConta(senha, numero);
        this.senha = senha;
        this.movimentacoes = new ArrayList<>();
        this.dono = dono;
        addContaRegistro();
    }

    private void addContaRegistro() {
        dono.addContaRegistro(this);
    }
    
    public Cliente getDono(){
        return dono;
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

     public void adicionarMovimentacao(String movimentacao) {
        if (movimentacao != null && !movimentacao.isEmpty()) {
            this.movimentacoes.add(movimentacao);
        } else {
            throw new IllegalArgumentException("Movimentação não pode ser nula ou vazia.");
        }
    }
     
     public void setMovimentacoes(ArrayList<String> movimentacoesTotais){
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
