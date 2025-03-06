/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package banco.model;

import java.util.Random;

/**
 *
 * @author rodri
 */
 public class Investimento 
 {
    private String tipoInvestimento;
    private double taxaRendimento;

    public Investimento(String tipoInvestimento, double taxaRendimento) 
    {
        this.tipoInvestimento = tipoInvestimento;
        this.taxaRendimento = taxaRendimento;
    }

    public String getTipoInvestimento() 
    {
        return tipoInvestimento;
    }

    public double getTaxaRendimento() 
    {
        return taxaRendimento;
    }
    
    public void imprimeInvestimento()
    {
        System.out.println("Tipo de investimento: " + tipoInvestimento);
        System.out.println("Taxa de Rencimento: " + taxaRendimento);
    }
     // Método para realizar o investimento
    public double realizarInvestimento(double valor) 
    {
        if (this.tipoInvestimento.equals("Renda Fixa")) 
        {
            return realizarInvestimentoRendaFixa(valor);
        } else if (this.tipoInvestimento.equals("Renda Variável")) 
        {
            return realizarInvestimentoRendaVariavel(valor);
        } 
        else 
        {
            throw new IllegalArgumentException("Tipo de investimento desconhecido");
        }
    }

    private double realizarInvestimentoRendaFixa(double valor) 
    {
        // Aplica a taxa de rendimento fixa
        double rendimento = valor * (taxaRendimento / 100);
        double valorFinal = valor + rendimento;
        System.out.println("Investimento em Renda Fixa: R$" + valor + " com rendimento de R$" + rendimento);
        return valorFinal;
    }

    private double realizarInvestimentoRendaVariavel(double valor) 
    {
        Random rand = new Random();
        // Variação entre -15% e taxaRendimento
        double variacao = (rand.nextFloat() * (taxaRendimento - (-0.15))) + (-0.15);
        double rendimento = valor * variacao;
        double valorFinal = valor + rendimento;
        System.out.println("Investimento em Renda Variável: R$" + valor + " com variação de " + (variacao * 100) + "%.");
        return valorFinal;
    }

}
