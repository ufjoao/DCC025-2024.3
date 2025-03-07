package banco.model;

import banco.persistence.Persistence;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.swing.JOptionPane;

public class Gerenciamento {
    //trabalhar com um ArrayList de usuarios
    //iniciar no construtor e acessar todas as funções de conta atraves dos clientes e caixas
    //com isso, talvez, seja necessário alterar a Persistence também

    private List<Cliente> clientes;
    private List<Caixa> caixas;
    private List<Gerente> gerentes;
    private List<Investimento> investimentos;
    private List<movimentacaoMilhao> transferenciasPendentes;
    private List<saqueMilhao> saquesPendentes;
    private ArrayList<SolicitacaoCredito> solicitacoesCredito;

    private Conta fundoDeInvestimentos;

    private static Scanner scanner = new Scanner(System.in);

    public Gerenciamento() {
        clientes = Persistence.carregarClientes();
        caixas = Persistence.carregarCaixas();
        gerentes = Persistence.carregarGerentes();
        investimentos = new ArrayList<>();
        transferenciasPendentes = new ArrayList();
        saquesPendentes = new ArrayList<>();
        solicitacoesCredito = new ArrayList<>();

        //conta do próprio bando, somente para receber as transferências de investimento
        fundoDeInvestimentos = new Conta(000000);
    }

    private void cadastrarUsuario() {
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

        switch (tipoUsuario) {
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

    public void realizarTransferencia(int numeroDaContaOrigem, float valor, int numeroDaContaDestino) {
        Conta contaOrigem = Persistence.buscarContaPorNumero(numeroDaContaOrigem);
        Conta contaDestino = Persistence.buscarContaPorNumero(numeroDaContaDestino);

        if (contaOrigem == null || contaDestino == null) {
            return;
        }

//        if (clienteOrigem.getSenha() != senha) {
//            throw new SecurityException("Senha incorreta.");
//        }
        if (contaOrigem.getSaldo() >= valor) {
            contaOrigem.saque(valor);
            contaDestino.deposito(valor);
//            clienteOrigem.registraMovimentacao("Transferência Enviada: R$ " + valor + " para " + clienteDestino.getNome(), numeroDaContaOrigem);
//            clienteDestino.registraMovimentacao("Transferência Recebida: R$ " + valor + " de " + clienteOrigem.getNome(), numeroDaContaDestino);
            Persistence.salvarConta(contaOrigem);
            Persistence.salvarConta(contaDestino);
        } else {
            JOptionPane.showMessageDialog(null, "Transferência não realizada! Saldo insuficiente.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private int encontrarConta(int numeroConta) {
        for (int i = 0; i < clientes.size(); i++) {
            if (clientes.get(i).verificaNumeroDaConta(numeroConta)) {
                //retorna o indice que armazena o cliente que é dono daquela conta em especifico
                return i;
            }
        }
        return -1; //Retorna -1 caso a conta não seja encontrada
    }

    // Método para realizar um saque
    public void realizarSaque(Cliente cliente, float valor, int numeroDaConta) {
        for (Conta conta : cliente.getContas()) {
            if (conta.getNumeroDaConta() == numeroDaConta) {
                conta.saque(valor); // Método já deve atualizar o saldo
                Persistence.salvarConta(conta); // Salvar apenas a conta
                return;
            }
        }
        throw new IllegalArgumentException("Conta não encontrada para depósito.");
    }

    public void realizarDeposito(Cliente cliente, float valor, int numeroConta) {
        for (Conta conta : cliente.getContas()) {
            if (conta.getNumeroDaConta() == numeroConta) {
                conta.deposito(valor); // Método já deve atualizar o saldo
                Persistence.salvarConta(conta); // Salvar apenas a conta
                return;
            }
        }
        throw new IllegalArgumentException("Conta não encontrada para depósito.");
    }

    public List<Cliente> getClientes() {
        clientes = Persistence.carregarClientes();
        return clientes;
    }

    public void imprimirExtrato(int i) {
        clientes.get(i).gerarExtrato();
    }

    public void solicitarCredito(Cliente cliente) {
        scanner = new Scanner(System.in);

        // Solicitar o valor de crédito desejado
        System.out.println("Digite o valor desejado para crédito:");
        double valorCredito = scanner.nextDouble();

        // Adicionar a solicitação ao ArrayList de solicitações
        solicitacoesCredito.add(new SolicitacaoCredito(cliente, valorCredito));
        System.out.println("Solicitação de crédito de R$" + valorCredito + " registrada com sucesso.");

        // Opcionalmente, você pode adicionar lógica para processar ou aprovar o crédito aqui.
    }

    // Método para aprovar crédito para um cliente
    public void aprovarCredito(Gerente gerente, Cliente cliente, float valor, int senha, int numeroDaConta) {
        // Verificar se a senha do gerente está correta
        if (gerente.verificaSenha(senha)) {
            if (cliente.retornaSaldo(Persistence.buscarContaPorNumero(numeroDaConta)) >= valor) {
                System.out.println("Crédito aprovado para o cliente " + cliente.getNome() + " no valor de R$ " + valor);
                // Aumenta o limite de crédito
                cliente.adicionarLimiteCredito(valor);
                System.out.println("Limite de crédito do cliente " + cliente.getNome() + " foi aumentado para R$ " + cliente.getLimiteCredito());
            } else {
                System.out.println("Saldo insuficiente para aprovar o crédito.");
            }
        } else {
            System.out.println("Senha incorreta. Aprovação de crédito não realizada.");
        }
    }

    // Método para cadastrar um novo investimento
    public void cadastrarInvestimento(Gerente gerente, String tipoInvestimento, double taxaRendimento, int senha) {
        // Verificar se a senha do gerente está correta
        if (gerente.verificaSenha(senha)) {
            // Lógica para adicionar o investimento
            Investimento novoInvestimento = new Investimento(tipoInvestimento, taxaRendimento);
            investimentos.add(novoInvestimento);
            System.out.println("Investimento de tipo " + tipoInvestimento + " com taxa " + taxaRendimento + "% cadastrado com sucesso!");
        } else {
            System.out.println("Senha incorreta. Investimento não cadastrado.");
        }
    }

    // Outros métodos para outras operações podem ser adicionados aqui
    public void apoioMovimentacao(Gerente gerente, int senha) {
        Scanner teclado = new Scanner(System.in);
        //List<movimentacaoMilhao> transferenciasPendentes = new ArrayList<>();
        if (gerente.verificaSenha(senha)) {
            for (int i = 0; i < transferenciasPendentes.size(); i++) {
                movimentacaoMilhao atual = transferenciasPendentes.get(i);
                System.out.println("O cliente " + atual.getNomeCliente() + " deseja transferir " + atual.getValor() + " reais da sua conta " + atual.getNumeroOrigem() + " para a conta " + atual.getNumeroDestino() + " do cliente " + atual.getNomeDestino() + "\n");
                System.out.println("O cliente possui " + atual.getSaldoCliente() + " reais na respectiva conta\n");
                while (true) {
                    System.out.println("Você deseja permitir a transação? (sim/não)");
                    String resposta = teclado.nextLine().trim().toLowerCase();

                    if (resposta.equals("sim")) {
                        atual.executarTransferencia();
                        System.out.println("Permissão Concedida");
                        break;
                    } else if (resposta.equals("não")) {
                        System.out.println("Permissão Negada");
                        break;
                    } else {
                        System.out.println("Resposta inválida. Por favor, responda com 'sim' ou 'não'.");
                    }
                }
            }
        }
    }

    public void apoioSaque(Gerente gerente, int senha) {
        Scanner teclado = new Scanner(System.in);
        //List<saqueMilhao> saquesPendentes = new ArrayList<>();
        if (gerente.verificaSenha(senha)) {
            for (int i = 0; i < saquesPendentes.size(); i++) {
                saqueMilhao atual = saquesPendentes.get(i);
                System.out.println("O cliente " + atual.getNomeCliente() + " deseja sacar " + atual.getValor() + " reais da sua conta " + atual.getNumeroConta() + "\n");
                System.out.println("O cliente possui " + atual.getSaldoCliente() + " reais na respectiva conta\n");
                while (true) {
                    System.out.println("Você deseja permitir o saque? (sim/não)");
                    String resposta = teclado.nextLine().trim().toLowerCase();

                    if (resposta.equals("sim")) {
                        atual.executarSaque();
                        System.out.println("Permissão Concedida");
                        break;
                    } else if (resposta.equals("não")) {
                        System.out.println("Permissão Negada");
                        break;
                    } else {
                        System.out.println("Resposta inválida. Por favor, responda com 'sim' ou 'não'.");
                    }
                }
            }
        }
    }

    public void listarInvestimentos() {
        for (int i = 0; i < investimentos.size(); i++) {
            System.out.println("( " + i + 1 + ")");
            investimentos.get(i).imprimeInvestimento();
        }
    }

    public void realizaInvestimento(Cliente cliente) {
        System.out.println("Digite o valor a ser investido: ");
        Float valorDoInvestimento = scanner.nextFloat();
        scanner.nextLine();

        System.out.println("Confirme o numero da sua conta: ");
        int numeroDaConta = scanner.nextInt();

        listarInvestimentos();
        System.out.println("Digite o codigo do investimento que deseja realizar: ");
        int i = scanner.nextInt();
        scanner.nextLine();

        realizarSaque(cliente, valorDoInvestimento, numeroDaConta);
        fundoDeInvestimentos.deposito(valorDoInvestimento);

        investimentos.get(i - 1).realizarInvestimento(valorDoInvestimento);
    }
}
