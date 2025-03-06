package banco.model;

import banco.persistence.Persistence;
import com.google.gson.annotations.Expose;
import java.util.ArrayList;
import java.util.List;

public class Cliente extends Usuario {

    @Expose
    private List<Conta> contas;

    public Cliente(String nome, String cpf, int senha) {
        super(nome, cpf, senha);
        this.contas = new ArrayList<>();
    }

    public Cliente() {
        super("", "", 0); // Valor padrão para inicializar
    }

    @Override
    public String getTipo() {
        return "Cliente";
    }

    public List<Conta> getContas() {
        return contas;
    }

    public void setContas(List<Conta> contas) {
        this.contas = contas;
    }

    public void adicionarConta(Conta conta) {
        if (conta == null) {
            throw new IllegalArgumentException("Conta não pode ser nula.");
        }
        contas.add(conta);
    }

    public void addContaRegistro(Conta conta) {
        Persistence.addCliente(this);
    }

    public void criarConta(int numeroConta) {
        this.contas.add(new Conta(numeroConta, this));
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

    public float retornaSaldo(int numeroDaConta) {
        for (int i = 0; i < contas.size(); i++) {
            if (verificaNumeroDaConta(numeroDaConta)) {
                return contas.get(i).getSaldo();
            }
        }
        System.out.println("Conta não encontrada.");
        return -1.f;
    }

    public void gerarExtrato() {
        contas.get(contas.indexOf(this)).gerarExtrato();
    }

    public boolean verificaNumeroDaConta(int numeroDaConta) {
        return contas.get(contas.indexOf(this)).verificaNumeroDaConta(numeroDaConta);
    }

    public void registraMovimentacao(String movimentacao) {
        contas.get(contas.indexOf(this)).adicionarMovimentacao(movimentacao);
    }
}
