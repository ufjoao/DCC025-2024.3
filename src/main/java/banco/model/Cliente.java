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
    private List<Movimentacao> movimentacoes;
    public Cliente(String nome, String cpf, int senha) {
        super(nome, cpf, senha);
        this.contas = new ArrayList<>();
    }

    public Cliente() {
        super("", "", 0); // Valor padrão para inicializar
        this.contas = new ArrayList<>();
        this.movimentacoes = new ArrayList<>();
    }

    @Override
    public String getTipo() {
        return "Cliente";
    }

    public void criarConta(int numeroDaConta) {
        // Cria a conta e associa o cliente como dono
        Conta novaConta = new Conta(numeroDaConta, this.getId());  // 'this' é o cliente
        this.adicionarConta(novaConta);  // Adiciona a conta à lista de contas do cliente
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
        if (this.contas == null) {
            this.contas = new ArrayList<>();
        }
        this.contas.add(conta);  // Adiciona a conta à lista de contas do cliente
    }

    public float consultarSaldo(Conta conta) {
        return conta.consultaSaldo();
    }

    public float retornaSaldo(Conta conta) {
        return conta.getSaldo();
    }

    
    public String gerarExtrato() {
        StringBuilder extratoCompleto = new StringBuilder("Extrato do Cliente:\n\n");

        if (movimentacoes.isEmpty()) {
            return "Conta sem movimentações.";
        }

        for (Movimentacao movimentacao : movimentacoes) {
            extratoCompleto.append(movimentacao).append("\n");
        }

        return extratoCompleto.toString();
    }

    public boolean verificaNumeroDaConta(int numeroDaConta) {
        return contas.get(contas.indexOf(this)).verificaNumeroDaConta(numeroDaConta);
    }

    public void registrarMovimentacao(String descricao, float valor) {
        Movimentacao movimentacao = new Movimentacao(descricao, valor);
        movimentacoes.add(movimentacao);
    }

    public float getLimiteCredito() {
        return limiteCredito;
    }

    public void adicionarLimiteCredito(float valor) {
        this.limiteCredito += valor;
    }
    
        public void adicionarMovimentacao(Movimentacao movimentacao) {
        movimentacoes.add(movimentacao);
    }

}
