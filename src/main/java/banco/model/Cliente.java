//João Alexandre dos Santos Nunes – 202235029
//João Antônio Fonseca e Almeida – 201935010
//Rodrigo da Silva Soares – 201765218AB

package banco.model;

import banco.persistence.Persistence;
import com.google.gson.annotations.Expose;
import java.util.ArrayList;
import java.util.List;

import java.io.Serializable;

public class Cliente extends Usuario implements Serializable {
    private static final long serialVersionUID = 1L;

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
    for (Conta conta : contas) {
        if (conta.getNumeroDaConta() == numeroDaConta) {
            return conta; // Retorna a conta encontrada
        }
    }

    System.out.println("Conta não encontrada");
    return null; // Caso não encontre a conta
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
    for (Conta conta : contas) {
        if (conta.getNumeroDaConta() == numeroDaConta) {
            return true; // Encontrou a conta
        }
    }
    return false; // Conta não encontrada
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
        if (this.movimentacoes == null) {
            this.movimentacoes = new ArrayList<>();
        }
        this.movimentacoes.add(movimentacao);
    }
}
