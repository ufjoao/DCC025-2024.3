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
        clientes = new ArrayList<Cliente>();
        caixas = new ArrayList<Caixa>();
        gerentes = new ArrayList<Gerente>();
        contas = new ArrayList<Conta>();
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
                        
                        if(clientes.get(i).verificaSenha(senha))
                        {
                            //se a senha for valida, entra e faz a transferencia
                            contas.get(i).saque(valor); //saca da conta de origem e deposita na conta de destino
                            contas.get(j).deposito(valor);

                            String movimentacao = "Transferencia \nValor: R$ " + valor;//string com os dados da transferencia, passada para o
                            contas.get(i).setMovimentacoes(movimentacao);              //setMovimentacoes, que armazena Strings para quando for necessario
                                                                                       //gerar um extrato
                        }
                    }
                }
            }
        }
        
    }
    
    //inserir os metodos das operacoes e o loop
    
    public void menu()
    {
        int verificador = 1;
        
        //cadastrar alguns usuarios automaticamente aqui
        
        while(verificador != 0)//loop de menu
        {
            //dar a opção de cadastrar usuários manualmente
            
            //dar a opção de logar e realizar as funções possíveis para cada tipo usuário
            
           //cliente tem Transferencia, consulta de saldo, consulta de extrato, investimentos de renda fixa e variável e solicitação de crédito
           //caixa tem atendimento de saque, processamento de depositos e transferencia
           //gerente tem apoio em movimentações, cadastrar uma nova opção de renda fixa, cadastrar renda variável e avaliação de crédito
        }
    }
    
}
