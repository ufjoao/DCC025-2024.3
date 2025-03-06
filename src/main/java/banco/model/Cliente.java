package banco.model;

import banco.persistence.Persistence;
import com.google.gson.annotations.Expose;
import java.util.ArrayList;
import java.util.List;

public class Cliente extends Usuario 
{

    private String nome;
    private String cpf;
    private int senha;
    private ArrayList<Conta> contas = new ArrayList<>(); // Um cliente pode ter várias contas

    @JsonCreator
    public Cliente(@JsonProperty("nome") String nome,
                   @JsonProperty("cpf") String cpf,
                   @JsonProperty("senha") int senha) 
    {
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

    public void adicionarConta(Conta conta) 
    {
        if (conta == null) 
        {
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
    
    public float getLimiteCredito() 
    {
        return limiteCredito;
    }
    
    public void adicionarLimiteCredito(float valor) 
    {
        this.limiteCredito += valor;
    }
}
