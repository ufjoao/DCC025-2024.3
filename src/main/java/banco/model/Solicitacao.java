//João Alexandre dos Santos Nunes – 202235029
//João Antônio Fonseca e Almeida – 201935010
//Rodrigo da Silva Soares – 201765218AB

package banco.model;

import java.io.Serializable;

public class Solicitacao implements Serializable {

    private String tipo; // "Saque" ou "Deposito"
    private float valor;
    private Conta conta;
    private Cliente cliente;
    private boolean aprovado;

    // Construtor
    public Solicitacao(String tipo, float valor, Conta conta, Cliente cliente) {
        this.tipo = tipo;
        this.valor = valor;
        this.conta = conta;
        this.cliente = cliente;
        this.aprovado = false; // Inicialmente, a solicitação não é aprovada
    }

    // Getters e setters
    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }

    public Conta getConta() {
        return conta;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setConta(Conta conta) {
        this.conta = conta;
    }

    public boolean isAprovado() {
        return aprovado;
    }

    public void setAprovado(boolean aprovado) {
        this.aprovado = aprovado;
    }

    public void aprovar() {
        aprovado = true;
    }

    @Override
    public String toString() {
        return "Solicitação [Tipo=" + tipo + ", Valor=" + valor + ", Conta=" + conta.getNumeroDaConta() + ", Aprovado=" + aprovado + "]";
    }
}
