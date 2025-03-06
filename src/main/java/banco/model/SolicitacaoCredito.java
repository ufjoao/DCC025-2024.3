/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package banco.model;

/**
 *
 * @author rodri
 */
public class SolicitacaoCredito
{
    private Cliente cliente;
    private double valorSolicitado;

    public SolicitacaoCredito(Cliente cliente, double valorSolicitado) 
    {
        this.cliente = cliente;
        this.valorSolicitado = valorSolicitado;
    }

    public Cliente getCliente() 
    {
        return cliente;
    }

    public double getValorSolicitado() 
    {
        return valorSolicitado;
    }

    @Override
    public String toString() 
    {
        return "Cliente: " + cliente.getNome() + ", Valor solicitado: R$" + valorSolicitado;
    }
}
