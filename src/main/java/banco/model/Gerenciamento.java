package banco.model;

import banco.persistence.Persistence;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Gerenciamento 
{
    //trabalhar com um ArrayList de usuarios
    //iniciar no construtor e acessar todas as funções de conta atraves dos clientes e caixas
    //com isso, talvez, seja necessário alterar a Persistence também
    
    private ArrayList<Cliente> clientes = new ArrayList<>();
    private ArrayList<Caixa> caixas = new ArrayList<>();
    private ArrayList<Gerente> gerentes = new ArrayList<>();

    public Gerenciamento()
    {
        clientes = new ArrayList<>();  
        caixas = new ArrayList<>();
        gerentes = new ArrayList<>();
    }
    
    public void cadastrarUsuario()
    {
        //os três valores abaixo receberão as entradas do teclado, caso possível
        //os valores inseridos são apenas para testes.
        //A ideia do método é fazer toda a leitura do teclado ou receber da tela quando for chamado, pensei em não passar nenhum parâmetro.
        String nome = null;
        String cpf = "12345678901";
        int senha = 14725836;
        
        clientes.add(new Cliente(nome, cpf, senha));
    }
    

    // Método para realizar uma transferência
    //
    public void realizarTransferencia(int numeroDaContaOrigem, int numeroDaContaDestino, float valor, int senha) 
    {
        //depois vamos substituir todos os prints e entradas para pegar da tela
        Scanner info = new Scanner(System.in);
        
        System.out.println("Digite numero da conta de origem:");
        int origem = info.nextInt();
        info.nextLine();
        
        System.out.println("Digite numero da conta de destino:");
        int destino = info.nextInt();
        info.nextLine();
        
        for(int i=0; i<clientes.size(); i++)
        {
            if(clientes.get(i).verificaNumeroDaConta(origem))
            {
                for(int j=0; j<clientes.size(); j++)
                {
                    if(clientes.get(j).verificaNumeroDaConta(destino))
                    {    
                        System.out.println("Senha para a confirmacao da transferencia:");
                        int senhaConfirmacao = info.nextInt();
                        info.nextLine();
                        
                        //antes utilizávamos a verificação de senha do objeto clientes, presente na classe.
                        //Porém, como o método será acessada por clientes e caixas, a verificação de senha interna do método
                        //vai verificar a senha inserida a seguir com a senha informada na chamada do método.
                        //antes da chamada, no menu, também teremos uma verificação.
                        if(senha == senhaConfirmacao)
                        {
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

    private int encontrarConta(int numeroConta) 
    {
        for (int i=0; i<clientes.size(); i++) 
        {
            if (clientes.get(i).verificaNumeroDaConta(numeroConta)) 
            {
                //retorna o indice que armazena o cliente que é dono daquela conta em especifico
                return i;
            }
        }
        return -1; //Retorna -1 caso a conta não seja encontrada
    }

    // Método para realizar um saque
    public void realizarSaque(int i, float valor, int senha, int numeroDaConta) 
    {

            if (clientes.get(i).retornaSaldo(numeroDaConta) >= valor) 
            {
                clientes.get(i).realizarSaque(valor);
                System.out.println("Saque realizado com sucesso!");
                //clientes.get(i).registraMovimentacao("Saque no valor de R$" + valor);
            } 
            else 
            {
                System.out.println("Saldo insuficiente.");
            }
    }

    // Método para realizar um depósito
    public void realizarDeposito(int i, float valor) 
    {
        clientes.get(i).realizarDeposito(valor);
        //seria interessante fazer mais uma verificação de senha?
        System.out.println("Depósito realizado com sucesso!");
        //clientes.get(i).registraMovimentacao("Depósito no valor de R$" + valor);
    }

    public void imprimirExtrato(int i)
    {
        clientes.get(i).gerarExtrato();
    }

    // Método para o gerente aprovar crédito
    public void aprovarCredito(Gerente gerente, Cliente cliente, float valor, int senha) 
    {
        if (gerente.verificaSenha(senha)) 
        {
            // Aqui a lógica de aprovação pode envolver verificação de histórico de crédito
            System.out.println("Crédito aprovado para o cliente " + cliente.getNome());
        } 
        else 
        {
            System.out.println("Senha incorreta. Aprovação de crédito não realizada.");
        }
    }

    // Método para cadastrar uma nova opção de investimento (pode ser realizado pelo gerente)
    public void cadastrarInvestimento(Gerente gerente, String tipoInvestimento, double taxaRendimento, int senha) 
    {
        if (gerente.verificaSenha(senha)) 
        {
            // Lógica para cadastrar novo investimento no sistema
            System.out.println("Investimento de tipo " + tipoInvestimento + " com taxa " + taxaRendimento + "% cadastrado.");
        } 
        else 
        {
            System.out.println("Senha incorreta. Investimento não cadastrado.");
        }
    }

    // Outros métodos para outras operações podem ser adicionados aqui

    public void apoioMovimentacao(Gerente gerente, int senha) {
        Scanner teclado = new Scanner(System.in);
        List<movimentacaoMilhao> transferenciasPendentes = new ArrayList<>();
        if(gerente.verificaSenha(senha)) {
           for (int i = 0; i < transferenciasPendentes.size(); i++) {
              movimentacaoMilhao atual = transferenciasPendentes.get(i);
              System.out.println("O cliente " + atual.getNomeCliente() + " deseja transferir " + atual.getValor() + " reais da sua conta " + atual.getNumeroOrigem() + " para a conta " + atual.getNumeroDestino() + " do cliente " + atual.getNomeDestino()+ "\n");
              System.out.println("O cliente possui " + atual.getSaldoCliente() + " reais na respectiva conta\n");
              while (true) {
                 System.out.println("Você deseja permitir a transação? (sim/não)");
                 String resposta = teclado.nextLine().trim().toLowerCase();

                 if(resposta.equals("sim")) {
                     atual.executarTransferencia();
                     System.out.println("Permissão Concedida");
                     break;
                 } 
                 else if(resposta.equals("não")) {
                     System.out.println("Permissão Negada");
                     break;
                 } 
                 else  {
                     System.out.println("Resposta inválida. Por favor, responda com 'sim' ou 'não'.");
                 }
              } 
           }
        }
    }
    
    public void apoioSaque(Gerente gerente, int senha) {
        Scanner teclado = new Scanner(System.in);
        List<saqueMilhao> saquesPendentes = new ArrayList<>();
        if(gerente.verificaSenha(senha)) {
           for (int i = 0; i < saquesPendentes.size(); i++) {
              saqueMilhao atual = saquesPendentes.get(i);
              System.out.println("O cliente " + atual.getNomeCliente() + " deseja sacar " + atual.getValor() + " reais da sua conta " + atual.getNumeroConta() + "\n");
              System.out.println("O cliente possui " + atual.getSaldoCliente() + " reais na respectiva conta\n");
              while (true) {
                 System.out.println("Você deseja permitir o saque? (sim/não)");
                 String resposta = teclado.nextLine().trim().toLowerCase();

                 if(resposta.equals("sim")) {
                     atual.executarSaque();
                     System.out.println("Permissão Concedida");
                     break;
                 } 
                 else if(resposta.equals("não")) {
                     System.out.println("Permissão Negada");
                     break;
                 } 
                 else  {
                     System.out.println("Resposta inválida. Por favor, responda com 'sim' ou 'não'.");
                 }
              }
           }
        }
    }
}
    
    
    








