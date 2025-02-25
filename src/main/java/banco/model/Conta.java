/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package banco.model;

import java.util.ArrayList;

/**
 *
 * @author rodri
 */
public class Conta 
{
    private int numeroDaConta;
    private int id;//indice que representa a conta em questão no ArrayList da classe Gerenciamento
    private float saldo;
    private ArrayList<String> movimentacoes;
    
    public Conta(int numeroDaConta, int id)
    {
        this.numeroDaConta = numeroDaConta;
        this.id = id;
        movimentacoes = new ArrayList(null);
        saldo = 0;
    }
    
    public float getSaldo()
    {
        return saldo;
    }
    
    public void setSaldo(float saldo)
    {
        this.saldo = saldo;
    }
    
    public void setMovimentacoes(String movimentacao)
    {
        //vai armaazenar uma string com o tipo de movimentação e o valor
        movimentacoes.add(movimentacao);
    }
    
    boolean verificaNumeroDaConta(int numeroDaConta)
    {
        return this.numeroDaConta == numeroDaConta;
    }
    
    void deposito(float valor)
    {
        saldo += valor;
    }
    
    void saque(float valor)
    {
        saldo -= valor;
    }
    
    void consultaSaldo()
    {
        System.out.println("Saldo da Conta\n");
        System.out.println("Saldo: R$" + saldo);
    }
    
    void gerarExtrato()
    {
        System.out.println("Extrato da Conta\n");
        
        if(movimentacoes != null)
        {
            System.out.println("Ultimas movimentacoes da conta:");
            
            for(int i=0; i<movimentacoes.size(); i++)
            {
                System.out.println(movimentacoes.get(i));
            }
        }
        else
            System.out.println("Conta sem movimentacoes");
    }
}
