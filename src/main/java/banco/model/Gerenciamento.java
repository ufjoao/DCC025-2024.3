package banco.model;

import java.util.ArrayList;
import java.util.Scanner;

public class Gerenciamento {

    private ArrayList<Cliente> clientes;
    private ArrayList<Caixa> caixas;
    private ArrayList<Gerente> gerentes;

    //agora Conta está atrelada ao Usuário
    //private ArrayList <Conta> contas;
    public Gerenciamento() {
        clientes = new ArrayList<Cliente>();
        caixas = new ArrayList<Caixa>();
        gerentes = new ArrayList<Gerente>();
    }

    public void transferencia() {
        Scanner info = new Scanner(System.in);

        System.out.println("Digite numero da conta de origem:");
        int origem = info.nextInt();
        info.nextLine();

        System.out.println("Digite numero da conta de destino:");
        int destino = info.nextInt();
        info.nextLine();

        for (int i = 0; i < clientes.size(); i++) {
            if (clientes.get(i).verificaNumeroDaConta(origem)) {
                for (int j = 0; j < clientes.size(); j++) {
                    if (clientes.get(j).verificaNumeroDaConta(destino)) {
                        System.out.println("Digite o valor da transferencia");
                        float valor = info.nextFloat();

                        System.out.println("Senha para a confirmacao da transferencia:");
                        String senha = info.nextLine();

                        if (clientes.get(i).verificaSenha(senha)) {
                            //se a senha for valida, entra e faz a transferencia
                            clientes.get(i).realizarSaque(valor); //saca da conta de origem e deposita na conta de destino
                            clientes.get(j).realizarDeposito(valor);

                            String movimentacao = "Transferencia \nValor: R$ " + valor;//string com os dados da transferencia, passada para o
                            clientes.get(i).registraMovimentacao(movimentacao);        //setMovimentacoes, que armazena Strings para quando for necessario

                            movimentacao = "Transferencia Recebida \nValor: R$ " + valor;
                            clientes.get(j).registraMovimentacao(movimentacao);
                        }
                    }
                }
            }
        }

    }

    //inserir os metodos das operacoes e o loop
    public void menu() {
        Scanner entrada = new Scanner(System.in);
        int verificador = 1;

        //cadastrar alguns usuarios automaticamente aqui
        while (verificador != 0)//loop de menu
        {
            //dar a opção de cadastrar usuários manualmente

            switch (verificador) {
//                case 1:
//                    //entrar com o nome
//                String nome = entrada.nextLine();
//                    //entrar com o documento
//                String documento = entrada.nextLine();
//                    //entrar com a senha
//                String senha = entrada.nextLine();
//
//                clientes.add(new Cliente(nome, documento, clientes.indexOf(this), senha));
//                
//                break;
                //dar a opção de logar e realizar as funções possíveis para cada tipo usuário

                //    case 2:
                //cliente tem Transferencia, consulta de saldo, consulta de extrato, investimentos de renda fixa e variável e solicitação de crédito
                //caixa tem atendimento de saque, processamento de depositos e transferencia
                //gerente tem apoio em movimentações, cadastrar uma nova opção de renda fixa, cadastrar renda variável e avaliação de crédito
            }
        }
    }

}
