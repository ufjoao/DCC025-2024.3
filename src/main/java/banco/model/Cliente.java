package banco.model;

import banco.persistence.Persistence;
import com.google.gson.annotations.Expose;
import java.util.ArrayList;
import java.util.List;

public class Cliente extends Usuario {

    @Expose
    private List<Conta> contas;
    @Expose
    private float limiteCredito = 0f;

    public Cliente(String nome, String cpf, int senha) {
        super(nome, cpf, senha);
        this.contas = new ArrayList<>();
    }

    public Cliente() {
        super("", "", 0); // Valor padrão para inicializar
        this.contas = new ArrayList<>();
    }

    @Override
    public String getTipo() {
        return "Cliente";
    }

    public void criarConta(int numeroConta) {
        Conta novaConta = new Conta(numeroConta, this.getId()); // Passa o cliente como referência
        this.contas.add(novaConta);
    }

    public List<Conta> getContas() {
        return contas;
    }

    public Conta retornaConta(int numeroDaConta) {
        for (int i = 0; i < contas.size(); i++) {
            if (verificaNumeroDaConta(numeroDaConta)) {
                return contas.get(i);
            }
        }

        System.out.println("Conta não encontrada");
        return null;
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

    public void realizarDeposito(float valor) {
        contas.get(contas.indexOf(this)).deposito(valor);
    }

    public void realizarSaque(float valor) {
        contas.get(contas.indexOf(this)).saque(valor);
    }

    public float consultarSaldo() {
        return contas.get(contas.indexOf(this)).consultaSaldo();
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

    public String gerarExtrato() {
        StringBuilder extratoCompleto = new StringBuilder("Extrato do Cliente:\n\n");

        for (Conta conta : contas) {
            extratoCompleto.append(conta.gerarExtrato()).append("\n------------------\n");
        }

        return extratoCompleto.toString();
    }

    public boolean verificaNumeroDaConta(int numeroDaConta) {
        return contas.get(contas.indexOf(this)).verificaNumeroDaConta(numeroDaConta);
    }

    public void registraMovimentacao(String movimentacao) {
        contas.get(contas.indexOf(this)).adicionarMovimentacao(movimentacao);
    }

    public float getLimiteCredito() {
        return limiteCredito;
    }

    public void adicionarLimiteCredito(float valor) {
        this.limiteCredito += valor;
    }
}
