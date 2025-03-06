/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package banco.model;

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
}
