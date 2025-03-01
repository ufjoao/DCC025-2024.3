package banco.model;

import banco.exception.Validador;
import banco.persistence.Persistence;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
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
    }
    
    public Cliente() 
    {
        super("", "", 0); 
    }

    @Override
    public void setSenha(int senha) 
    {
        Validador.validarSenhaDaConta(senha, contas.get(0).getNumeroDaConta());
        this.senha = senha;
    }
    
    @Override
    public String getNome() {
        return nome;
    }

    public List<Conta> getContas() 
    {
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

    public void addContaRegistro(Conta conta) 
    {
        Persistence.addConta(conta);
    }

    public void criarConta(int numeroConta, int senha) 
    {
        this.contas.add(new Conta(numeroConta, senha, this));
    }

    public void realizarDeposito(float valor) 
    {
        contas.get(contas.indexOf(this)).deposito(valor);
    }

    public void realizarSaque(float valor) 
    {
        contas.get(contas.indexOf(this)).saque(valor);
    }

    public void consultarSaldo() 
    {
        contas.get(contas.indexOf(this)).consultaSaldo();
    }

    public void gerarExtrato() 
    {
        contas.get(contas.indexOf(this)).gerarExtrato();
    }

    public boolean verificaNumeroDaConta(int numeroDaConta) 
    {
        return contas.get(contas.indexOf(this)).verificaNumeroDaConta(numeroDaConta);
    }

    public void registraMovimentacao(String movimentacao) 
    {
        contas.get(contas.indexOf(this)).adicionarMovimentacao(movimentacao);
    }
}
