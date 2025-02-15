/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.ufjf.dcc.dcc025.banco;

import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author rodri
 */
public class Gerenciamento 
{
    private ArrayList <Cliente> clientes;
    private ArrayList <Caixa> caixas;
    private ArrayList <Gerente> gerentes;
    private ArrayList <Conta> contas;
    
    public Gerenciamento()
    {
        clientes = new ArrayList();
        caixas = new ArrayList();
        gerentes = new ArrayList();
        contas = new ArrayList();
    }
    
    public void transferencia()
    {
        Scanner info = new Scanner(System.in);
        
        System.out.println("Digite numero da conta de origem:");
        int origem = info.nextInt();
        info.nextLine();
        
        System.out.println("Digite numero da conta de destino:");
        int destino = info.nextInt();
        info.nextLine();
        
        for(int i=0; i<contas.size(); i++)
        {
            if(contas.get(i).verificaNumeroDaConta(origem))
            {
                for(int j=0; j<contas.size(); j++)
                {
                    if(contas.get(j).verificaNumeroDaConta(destino))
                    {
                        System.out.println("Digite o valor da transferencia");
                        float valor = info.nextFloat();
                        
                        System.out.println("Senha para a confirmacao da transferencia:");
                        String senha = info.nextLine();
                        
                        if(senha.equals(clientes.getSenha()))
                        {
                            contas.get(i).saque(valor);
                            contas.get(j).deposito(valor);

                            String movimentacao = "Transferencia \nValor: R$ " + valor;
                            contas.get(i).setMovimentacoes(movimentacao);
                        }
                    }
                }
            }
        }
        
    }
    
    //inserir os metodos das operacoes e o loop
    
}
