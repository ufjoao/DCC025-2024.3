package banco.model;

import banco.exception.Validador;

import java.util.ArrayList;
import java.util.List;

public class Cliente extends Usuario {

    private String nome;
    private String cpf;
    private int senha;
    private ArrayList<Conta> contas; // Um cliente pode ter várias contas

    // Construtor
    public Cliente(String nome, String cpf, int senha) {
        super(nome, cpf, senha);
    }

    @Override
    public void setSenha(int senha) {
        Validador.validarSenhaDaConta(senha, contas.get(0).getNumeroDaConta());
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

    public void criarConta(int numeroConta, int senha) {
        this.contas.add(new Conta(numeroConta, "0001", senha));
    }

    public void realizarDeposito(float valor) {
        contas.get(contas.indexOf(this)).deposito(valor);
    }

    public void realizarSaque(float valor) {
        contas.get(contas.indexOf(this)).saque(valor);
    }

    public void consultarSaldo() {
        contas.get(contas.indexOf(this)).consultaSaldo();
    }

    public void gerarExtrato() {
        contas.get(contas.indexOf(this)).gerarExtrato();
    }

    public boolean verificaNumeroDaConta(int numeroDaConta) {
        return contas.get(contas.indexOf(this)).verificaNumeroDaConta(numeroDaConta);
    }

    public void registraMovimentacao(String movimentacao) {
        contas.get(contas.indexOf(this)).setMovimentacoes(movimentacao);
    }
}
