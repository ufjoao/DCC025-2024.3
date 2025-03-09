//João Alexandre dos Santos Nunes – 202235029
//João Antônio Fonseca e Almeida – 201935010
//Rodrigo da Silva Soares – 201765218AB


package banco.view;

import banco.model.Caixa;
import banco.model.Cliente;
import banco.model.Conta;
import banco.model.Gerenciamento;
import banco.model.Gerente;
import banco.model.Solicitacao;
import banco.persistence.Persistence;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;


public class TelaUsuario extends JFrame {

    private String tipoUsuario;  // Cliente, Caixa ou Gerente
    private int id;
    private Gerenciamento gerenciamento;

    public TelaUsuario(String tipoUsuario, int id) {
        this.tipoUsuario = tipoUsuario;
        this.id = id;
        this.gerenciamento = new Gerenciamento();

        setTitle(tipoUsuario + " - Banco");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Painel de boas-vindas
        JPanel painelBoasVindas = new JPanel();
        painelBoasVindas.add(new JLabel("Bem-vindo, " + tipoUsuario + "!"));
        add(painelBoasVindas, BorderLayout.NORTH);

        // Painel de ações (botões)
        JPanel painelAcoes = new JPanel();
        painelAcoes.setLayout(new GridLayout(0, 2, 10, 10));
        add(painelAcoes, BorderLayout.CENTER); // Layout dinâmico com espaçamento
        // Adiciona os botões conforme o tipo de usuário
        switch (tipoUsuario) {
            case "Cliente" -> {
                Conta contaAtual = Persistence.buscarContaPorNumero(id);
                Cliente atual = contaAtual.getDono();
                adicionarBotao(painelAcoes, "Consultar Saldo", e -> consultarSaldo(atual, contaAtual));
                adicionarBotao(painelAcoes, "Extrato", e -> consultarExtrato(atual));
                adicionarBotao(painelAcoes, "Solicitar Depósito", e -> solicitarDeposito(atual, id));
                adicionarBotao(painelAcoes, "Solicitar Saque", e -> solicitarSaque(atual, id));
                adicionarBotao(painelAcoes, "Transferir", e -> realizarTransferencia(atual, id));
            }
            case "Caixa" -> {
                Persistence.carregarSolicitacoesJson();
                Caixa atual = Persistence.buscarCaixaPorId(id);
                adicionarBotao(painelAcoes, "Aprovar Depósitos", e -> aprovarDepositos());
                adicionarBotao(painelAcoes, "Aprovar Saques", e -> aprovarSaques());
                adicionarBotao(painelAcoes, "Transferências", e -> JOptionPane.showMessageDialog(this, "Função de transferência."));
            }
            case "Gerente" -> {
                Gerente atual = Persistence.buscarGerentePorId(id);
                adicionarBotao(painelAcoes, "Gerenciar Usuários", e -> JOptionPane.showMessageDialog(this, "Gerenciamento de usuários."));
                adicionarBotao(painelAcoes, "Aprovar Crédito", e -> JOptionPane.showMessageDialog(this, "Aprovação de crédito."));
                adicionarBotao(painelAcoes, "Relatórios Financeiros", e -> JOptionPane.showMessageDialog(this, "Relatórios financeiros."));
            }
            default -> {

            }
        }
        setVisible(true);
    }

    private void adicionarBotao(JPanel painel, String texto, ActionListener acao) {
        JButton botao = new JButton(texto);
        botao.addActionListener(acao);
        painel.add(botao);
    }
    
    private void solicitarDeposito(Cliente cliente, int id) {
        // Solicita a senha e cria a solicitação de depósito
        JPasswordField senhaField = new JPasswordField(20);
        JPanel panel = new JPanel();
        panel.add(new JLabel("Digite a senha:"));
        panel.add(senhaField);

        int option = JOptionPane.showConfirmDialog(this, panel, "Autenticação", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (option != JOptionPane.OK_OPTION) {
            JOptionPane.showMessageDialog(this, "Operação cancelada.");
            return;
        }

        int senhaDigitada = Integer.parseInt(new String(senhaField.getPassword()));  // A senha é do tipo int
        if (senhaDigitada != cliente.getSenha()) {
            JOptionPane.showMessageDialog(this, "Senha incorreta!");
            return;
        }

        Conta contaAtual = Persistence.buscarContaPorNumero(id);
        if (contaAtual == null) {
            JOptionPane.showMessageDialog(this, "Conta não encontrada!");
            return;
        }

        String valorStr = JOptionPane.showInputDialog("Digite o valor do depósito:");
        if (valorStr != null) {
            try {
                float valor = Float.parseFloat(valorStr);
                if (valor <= 0) {
                    JOptionPane.showMessageDialog(this, "O valor do depósito deve ser positivo!");
                    return;
                }

                Solicitacao solicitacao = new Solicitacao("Deposito",valor , contaAtual, cliente);
                Persistence.adicionarSolicitacao(solicitacao); // Salva a solicitação
                JOptionPane.showMessageDialog(this, "Solicitação de depósito realizada com sucesso!");
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Valor inválido!");
            }
        }
    }

    private void solicitarSaque(Cliente cliente, int id) {
        // Solicita a senha e cria a solicitação de saque
        JPasswordField senhaField = new JPasswordField(20);
        JPanel panel = new JPanel();
        panel.add(new JLabel("Digite a senha:"));
        panel.add(senhaField);

        int option = JOptionPane.showConfirmDialog(this, panel, "Autenticação", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (option != JOptionPane.OK_OPTION) {
            JOptionPane.showMessageDialog(this, "Operação cancelada.");
            return;
        }

        int senhaDigitada = Integer.parseInt(new String(senhaField.getPassword()));  // A senha é do tipo int
        if (senhaDigitada != cliente.getSenha()) {
            JOptionPane.showMessageDialog(this, "Senha incorreta!");
            return;
        }

        Conta contaAtual = Persistence.buscarContaPorNumero(id);
        if (contaAtual == null) {
            JOptionPane.showMessageDialog(this, "Conta não encontrada!");
            return;
        }

        String valorStr = JOptionPane.showInputDialog("Digite o valor do saque:");
        if (valorStr != null) {
            try {
                float valor = Float.parseFloat(valorStr);
                if (valor <= 0) {
                    JOptionPane.showMessageDialog(this, "O valor do saque deve ser positivo!");
                    return;
                }

                Solicitacao solicitacao = new Solicitacao("Saque",valor , contaAtual, cliente);
                Persistence.adicionarSolicitacao(solicitacao); // Salva a solicitação
                JOptionPane.showMessageDialog(this, "Solicitação de saque realizada com sucesso!");
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Valor inválido!");
            }
        }
    }

    private void aprovarDepositos() {
        // Função para o caixa aprovar os depósitos
Solicitacao solicitacao = Persistence.buscarSolicitacaoPorTipo("Deposito");
if (solicitacao != null) {
    try {
        int senhaDigitada = Integer.parseInt(JOptionPane.showInputDialog("Digite a senha do cliente para aprovação:"));
        if (senhaDigitada == solicitacao.getCliente().getSenha()) {
            solicitacao.aprovar();
            Persistence.salvarConta(solicitacao.getConta());
            JOptionPane.showMessageDialog(this, "Depósito aprovado com sucesso!");
        } else {
            JOptionPane.showMessageDialog(this, "Senha incorreta!");
        }
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "Por favor, digite um número válido para a senha.");
    }
}
    }

    private void aprovarSaques() {
        // Função para o caixa aprovar os saques
        Solicitacao solicitacao = Persistence.buscarSolicitacaoPorTipo("Saque");
        if (solicitacao != null) {
            int senhaDigitada = Integer.parseInt(JOptionPane.showInputDialog("Digite a senha do cliente para aprovação:"));
            if (senhaDigitada == solicitacao.getCliente().getSenha()) {
                solicitacao.aprovar();
                Persistence.salvarConta(solicitacao.getConta());
                JOptionPane.showMessageDialog(this, "Saque aprovado com sucesso!");
            } else {
                JOptionPane.showMessageDialog(this, "Senha incorreta!");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Não há solicitações de saque.");
        }
    }

    private void consultarSaldo(Cliente cliente, Conta conta) {
        // Cria um painel para pedir a senha
        JPasswordField senhaField = new JPasswordField(20);
        JPanel panel = new JPanel();
        panel.add(new JLabel("Digite a senha:"));
        panel.add(senhaField);

        // Exibe o painel de senha
        int option = JOptionPane.showConfirmDialog(this, panel, "Autenticação", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (option != JOptionPane.OK_OPTION) {
            JOptionPane.showMessageDialog(this, "Operação cancelada.");
            return;
        }

        // Verifica a senha do cliente
        int senhaDigitada = Integer.parseInt(new String(senhaField.getPassword()));  // A senha é do tipo int, como você mencionou
        if (senhaDigitada != cliente.getSenha()) {
            JOptionPane.showMessageDialog(this, "Senha incorreta!");
            return;
        }

        // Após a senha estar correta, continua com a lógica de consulta de saldo
        conta = Persistence.buscarContaPorNumero(conta.getNumeroDaConta());
        if (conta == null) {
            JOptionPane.showMessageDialog(this, "Conta não encontrada!");
            return;
        }

        float saldoAtualizado = conta.getSaldo(); // Método correto para obter saldo atualizado
        JOptionPane.showMessageDialog(this, "Saldo Atualizado: R$ " + saldoAtualizado);
    }

    private void consultarExtrato(Cliente cliente) {
        // Cria um painel para pedir a senha
        JPasswordField senhaField = new JPasswordField(20);
        JPanel panel = new JPanel();
        panel.add(new JLabel("Digite a senha:"));
        panel.add(senhaField);

        // Exibe o painel de senha
        int option = JOptionPane.showConfirmDialog(this, panel, "Autenticação", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (option != JOptionPane.OK_OPTION) {
            JOptionPane.showMessageDialog(this, "Operação cancelada.");
            return;
        }

        // Verifica a senha do cliente
        int senhaDigitada = Integer.parseInt(new String(senhaField.getPassword()));  // A senha é do tipo int, como você mencionou
        if (senhaDigitada != cliente.getSenha()) {
            JOptionPane.showMessageDialog(this, "Senha incorreta!");
            return;
        }
        cliente = Persistence.buscarClientePorId(cliente.getId());
        String extrato = cliente.gerarExtrato();
        Persistence.salvarCliente(cliente);
        JOptionPane.showMessageDialog(this, extrato, "Extrato", JOptionPane.INFORMATION_MESSAGE);
    }

    public void realizarDeposito(Cliente cliente, int id) {
        // Cria um painel para pedir a senha
        JPasswordField senhaField = new JPasswordField(20);
        JPanel panel = new JPanel();
        panel.add(new JLabel("Digite a senha:"));
        panel.add(senhaField);

        // Exibe o painel de senha
        int option = JOptionPane.showConfirmDialog(this, panel, "Autenticação", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (option != JOptionPane.OK_OPTION) {
            JOptionPane.showMessageDialog(this, "Operação cancelada.");
            return;
        }

        // Verifica a senha do cliente
        int senhaDigitada = Integer.parseInt(new String(senhaField.getPassword()));  // A senha é do tipo int, como você mencionou
        if (senhaDigitada != cliente.getSenha()) {
            JOptionPane.showMessageDialog(this, "Senha incorreta!");
            return;
        }
        Conta contaAtual = Persistence.buscarContaPorNumero(id);
        cliente = Persistence.buscarClientePorId(cliente.getId());
        if (contaAtual == null) {
            JOptionPane.showMessageDialog(this, "Conta não encontrada!");
            return;
        }

        String valorStr = JOptionPane.showInputDialog("Digite o valor do depósito:");
        if (valorStr != null) {
            try {
                // Converte o valor para float apenas uma vez
                float valor = Float.parseFloat(valorStr);

                // Verifica se o valor é válido
                if (valor <= 0) {
                    JOptionPane.showMessageDialog(this, "O valor do depósito deve ser positivo!");
                    return;
                }
                gerenciamento.realizarDeposito(cliente, valor, id);
                Persistence.salvarCliente(cliente);
                JOptionPane.showMessageDialog(this, "Depósito realizado com sucesso!");
            } catch (NumberFormatException e) {
                // Caso o valor seja inválido, mostra a mensagem de erro
                JOptionPane.showMessageDialog(this, "Valor inválido!");
            }
        }
    }
    
    

    private void realizarSaque(Cliente cliente, int numeroDaConta) {
        // Cria um painel para pedir a senha
        JPasswordField senhaField = new JPasswordField(20);
        JPanel panel = new JPanel();
        panel.add(new JLabel("Digite a senha:"));
        panel.add(senhaField);

        // Exibe o painel de senha
        int option = JOptionPane.showConfirmDialog(this, panel, "Autenticação", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (option != JOptionPane.OK_OPTION) {
            JOptionPane.showMessageDialog(this, "Operação cancelada.");
            return;
        }

        // Verifica a senha do cliente
        int senhaDigitada = Integer.parseInt(new String(senhaField.getPassword()));  // A senha é do tipo int, como você mencionou
        if (senhaDigitada != cliente.getSenha()) {
            JOptionPane.showMessageDialog(this, "Senha incorreta!");
            return;
        }
        Conta conta = Persistence.buscarContaPorNumero(numeroDaConta);
        cliente = Persistence.buscarClientePorId(cliente.getId());
        String valorStr = JOptionPane.showInputDialog("Digite o valor do saque:");
        if (valorStr != null) {
            try {
                float valor = Float.parseFloat(valorStr);
                if (valor > conta.getSaldo()) {
                    JOptionPane.showMessageDialog(this, "O valor do Saque é superior ao Saldo!");
                    return;
                }

                // Busca a conta atualizada
                conta = Persistence.buscarContaPorNumero(numeroDaConta);
                if (conta == null) {
                    JOptionPane.showMessageDialog(this, "Conta não encontrada!");
                    return;
                }

                gerenciamento.realizarSaque(cliente, valor, id);

                // Atualiza os dados do cliente e salva no JSON
                Persistence.salvarCliente(cliente);

                // Atualiza os dados carregados na interface
                cliente = Persistence.buscarClientePorId(cliente.getId());

                // Exibe a confirmação do depósito
                JOptionPane.showMessageDialog(this, "Saque realizado com sucesso!");

            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Valor inválido!");
            }
        }
    }

    private void realizarTransferencia(Cliente cliente, int numeroDaConta) {
        // Cria um painel para pedir a senha
        JPasswordField senhaField = new JPasswordField(20);
        JPanel panel = new JPanel();
        panel.add(new JLabel("Digite a senha:"));
        panel.add(senhaField);

        // Exibe o painel de senha
        int option = JOptionPane.showConfirmDialog(this, panel, "Autenticação", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (option != JOptionPane.OK_OPTION) {
            JOptionPane.showMessageDialog(this, "Operação cancelada.");
            return;
        }

        // Verifica a senha do cliente
        int senhaDigitada = Integer.parseInt(new String(senhaField.getPassword()));  // A senha é do tipo int, como você mencionou
        if (senhaDigitada != cliente.getSenha()) {
            JOptionPane.showMessageDialog(this, "Senha incorreta!");
            return;
        }
        Conta conta = Persistence.buscarContaPorNumero(numeroDaConta);
        String destinoStr = JOptionPane.showInputDialog("Digite o número da conta de destino:");
        String valorStr = JOptionPane.showInputDialog("Digite o valor da transferência:");
        if (destinoStr != null && valorStr != null) {
            int destino = Integer.parseInt(destinoStr);
            float valor = Float.parseFloat(valorStr);
            if (valor > 0) {
                Conta contaDestino = Persistence.buscarContaPorNumero(destino);
                if (conta == null || contaDestino == null) {
                    JOptionPane.showMessageDialog(this, "Conta não encontrada!");
                    return;
                }
                gerenciamento.realizarTransferencia(numeroDaConta, valor, destino);
                JOptionPane.showMessageDialog(this, "Transferência realizada com sucesso!");
            }
        }
    }
}
