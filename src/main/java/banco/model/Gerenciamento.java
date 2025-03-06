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

    private static Scanner scanner = new Scanner(System.in);
    
    public Gerenciamento()
    {
        clientes = new ArrayList<>();  
        caixas = new ArrayList<>();
        gerentes = new ArrayList<>();
    }
    
//    public void cadastrarUsuario()
//    {
//        //os três valores abaixo receberão as entradas do teclado, caso possível
//        //os valores inseridos são apenas para testes.
//        //A ideia do método é fazer toda a leitura do teclado ou receber da tela quando for chamado, pensei em não passar nenhum parâmetro.
//        String nome = null;
//        String cpf = "12345678901";
//        int senha = 14725836;
//        
//        clientes.add(new Cliente(nome, cpf, senha));
//    }
    
    private void cadastrarUsuario() 
    {
        System.out.println("Escolha o tipo de usuário a cadastrar:");
        System.out.println("1 - Cliente");
        System.out.println("2 - Caixa");
        System.out.println("3 - Gerente");
        System.out.print("Escolha uma opção: ");
        
        int tipoUsuario = scanner.nextInt();
        scanner.nextLine();
        
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("CPF: ");
        String cpf = scanner.nextLine();
        System.out.print("Senha: ");
        int senha = scanner.nextInt();
        
        switch (tipoUsuario) 
        {
            case 1:
                clientes.add(new Cliente(nome, cpf, senha));
                System.out.println("Cliente cadastrado com sucesso!");
                break;
            case 2:
                caixas.add(new Caixa(nome, cpf, senha));
                System.out.println("Caixa cadastrado com sucesso!");
                break;
            case 3:
                gerentes.add(new Gerente(nome, cpf, senha));
                System.out.println("Gerente cadastrado com sucesso!");
                break;
            default:
                System.out.println("Opção inválida.");
        }
    }
    

    // Método para realizar uma transferência
    //
    public void realizarTransferencia(int numeroDaContaOrigem, float valor, int senha) 
    {
        //depois vamos substituir todos os prints e entradas para pegar da tela
        Scanner info = new Scanner(System.in);
        
        int origem = numeroDaContaOrigem;
        
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
    public void realizarSaque(Cliente cliente, float valor, int senha, int numeroDaConta) 
    {

            if (cliente.retornaSaldo(numeroDaConta) >= valor) 
            {
                cliente.realizarSaque(valor);
                System.out.println("Saque realizado com sucesso!");
                //clientes.get(i).registraMovimentacao("Saque no valor de R$" + valor);
            } 
            else 
            {
                System.out.println("Saldo insuficiente.");
            }
    }

    // Método para realizar um depósito
    public void realizarDeposito(Cliente cliente, float valor) 
    {
        cliente.realizarDeposito(valor);
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

    //fiz um único método, pois os clientes e os caixas têm acesso às exatas mesmas funções
    private void acessarClienteCaixa() 
    {
        System.out.print("Digite seu CPF: ");
        String cpf = scanner.nextLine();
        System.out.print("Digite sua senha: ");
        int senha = scanner.nextInt();
        
        Cliente cliente = encontrarCliente(cpf, senha);
        
        if (cliente != null) 
        {
            boolean continuarCliente = true;
            
            while (continuarCliente) 
            {
                System.out.println("\n==== Menu Cliente ====");
                System.out.println("1 - Realizar Transferência");
                System.out.println("2 - Realizar Saque");
                System.out.println("3 - Realizar Depósito");
                System.out.println("4 - Sair");
                System.out.print("Escolha uma opção: ");
                
                int opcao = scanner.nextInt();
                switch(opcao) 
                {
                    case 1:
                        System.out.print("Digite o número da conta de origem: ");
                        int numeroDaContaOrigem = scanner.nextInt();
                        scanner.nextLine();
                        
                        System.out.print("Digite valor de transferência: ");
                        Float valor = scanner.nextFloat();
                        scanner.nextLine();
                        
                        realizarTransferencia(numeroDaContaOrigem, valor, senha);
                        break;
                    case 2:
                        System.out.print("Digite o número da conta: ");
                        int numeroDaConta = scanner.nextInt();
                        scanner.nextLine();
                        
                        System.out.print("Digite valor do saque: ");
                        Float valorSaque = scanner.nextFloat();
                        scanner.nextLine();
                        
                        realizarSaque(cliente, valorSaque, senha, numeroDaConta );
                        break;
                    case 3:
                        System.out.print("Digite valor do depósito: ");
                        Float valorDeposito = scanner.nextFloat();
                        scanner.nextLine();
                        realizarDeposito(cliente, valorDeposito);
                        break;
                    case 4:
                        continuarCliente = false;
                        System.out.println("Saindo...");
                        break;
                    default:
                        System.out.println("Opção inválida.");
                }
            }
        } 
        else 
        {
            System.out.println("Cliente não encontrado ou dados incorretos.");
        }
    }
    
//    private void acessarGerente() 
//    {
//        System.out.print("Digite seu CPF: ");
//        String cpf = scanner.nextLine();
//        System.out.print("Digite sua senha: ");
//        int senha = scanner.nextInt();
//        
//        Gerente gerente = encontrarGerente(cpf, senha);
//        
//        if (gerente != null) 
//        {
//            boolean continuarGerente = true;
//            
//            while (continuarGerente) 
//            {
//                System.out.println("\n==== Menu Gerente ====");
//                System.out.println("1 - Aprovar Crédito");
//                System.out.println("2 - Cadastrar Investimento");
//                System.out.println("3 - Apoio Movimentação");
//                System.out.println("4 - Apoio Saque");
//                System.out.println("5 - Sair");
//                System.out.print("Escolha uma opção: ");
//                
//                int opcao = scanner.nextInt();
//                switch (opcao) 
//                {
//                    case 1:
//                        aprovarCredito(gerente, cliente, valor, senha);
//                        break;
//                    case 2:
//                        cadastrarInvestimento();
//                        break;
//                    case 3:
//                        apoioMovimentacao();
//                        break;
//                    case 4:
//                        apoioSaque();
//                        break;
//                    case 5:
//                        continuarGerente = false;
//                        System.out.println("Saindo...");
//                        break;
//                    default:
//                        System.out.println("Opção inválida.");
//                }
//            }
//        } 
//        else 
//        {
//            System.out.println("Gerente não encontrado ou dados incorretos.");
//        }
//    }
    
    // Métodos de busca para encontrar usuário pelo CPF e senha
    private Cliente encontrarCliente(String cpf, int senha) 
    {
        for (Cliente cliente : clientes) 
        {
            if (cliente.getCpf().equals(cpf) && cliente.verificaSenha(senha)) 
            {
                return cliente;
            }
        }
        return null;
    }
    
    private Caixa encontrarCaixa(String cpf, int senha) 
    {
        for (Caixa caixa : caixas) 
        {
            if (caixa.getCpf().equals(cpf) && caixa.verificaSenha(senha)) 
            {
                return caixa;
            }
        }
        return null;
    }

    private Gerente encontrarGerente(String cpf, int senha) 
    {
        for (Gerente gerente : gerentes) 
        {
            if (gerente.getCpf().equals(cpf) && gerente.verificaSenha(senha)) 
            {
                return gerente;
            }
        }
        return null;
    }
//    public void menu() 
//    {
//        boolean continuar = true;
//        
//        while (continuar) 
//        {
//            System.out.println("=================================");
//            System.out.println("Bem-vindo ao Sistema Bancário!");
//            System.out.println("1 - Cadastrar novo usuário");
//            System.out.println("2 - Acessar como cliente");
//            System.out.println("3 - Acessar como caixa");
//            System.out.println("4 - Acessar como gerente");
//            System.out.println("5 - Sair");
//            System.out.print("Escolha uma opção: ");
//            
//            int opcao = scanner.nextInt();
//            scanner.nextLine();  // Consumir a nova linha que fica após a escolha
//            
//            switch (opcao) 
//            {
//                case 1:
//                    cadastrarUsuario();
//                    break;
//                case 2:
//                    acessarClienteCaixa();
//                    break;
//                case 3:
//                    acessarClienteCaixa();
//                    break;
//                case 4:
//                    acessarGerente();
//                    break;
//                case 5:
//                    continuar = false;
//                    System.out.println("Saindo do sistema...");
//                    break;
//                default:
//                    System.out.println("Opção inválida. Tente novamente.");
//            }
//        }
//    }

}
    
    
    








