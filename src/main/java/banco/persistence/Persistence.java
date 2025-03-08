//João Alexandre dos Santos Nunes – 202235029
//João Antônio Fonseca e Almeida – 201935010
//Rodrigo da Silva Soares – 201765218AB

package banco.persistence;

import banco.model.*;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import java.io.*;
import java.lang.reflect.Type;
import java.util.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.*;
import java.lang.reflect.Type;
import java.util.List;

public class Persistence {

    private static final String ARQUIVO_CLIENTES = "clientes.json";
    private static final String ARQUIVO_CAIXAS = "caixas.json";
    private static final String ARQUIVO_GERENTES = "gerentes.json";

    // Método para adicionar ou atualizar um cliente
    public static List<Solicitacao> solicitacoes = new ArrayList<>();

    // Método para adicionar uma solicitação
    public static void adicionarSolicitacao(Solicitacao solicitacao) {
        solicitacoes.add(solicitacao);
        salvarSolicitacoesJson(); // Salva a lista atualizada no arquivo
    }

    public static void verificarSolicitacoes() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("solicitacoes.dat"))) {
            List<Solicitacao> solicitacoesCarregadas = (List<Solicitacao>) in.readObject();
            for (Solicitacao solicitacao : solicitacoesCarregadas) {
                System.out.println(solicitacao);
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void verificarConteudoArquivo() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("solicitacoes.dat"))) {
            List<Solicitacao> solicitacoes = (List<Solicitacao>) ois.readObject();
            // Verifica o que está sendo carregado
            for (Solicitacao solicitacao : solicitacoes) {
                System.out.println(solicitacao);  // Imprime as solicitações no console
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void exibirSolicitacoesPendentes() {
        carregarSolicitacoesJson();  // Carrega as solicitações antes de exibir
        // Exibe as solicitações na interface
    }

    // Método para buscar uma solicitação de um tipo específico (Saque ou Depósito)
    public static Solicitacao buscarSolicitacaoPorTipo(String tipo) {
        for (Solicitacao solicitacao : solicitacoes) {
            if (solicitacao.getTipo().equalsIgnoreCase(tipo) && !solicitacao.isAprovado()) {
                return solicitacao;
            }
        }
        return null; // Caso não encontre uma solicitação pendente
    }

// Método para salvar solicitações em um arquivo JSON
    public static void salvarSolicitacoesJson() {
        try (Writer writer = new FileWriter("solicitacoes.json")) {
            Gson gson = new Gson();
            gson.toJson(solicitacoes, writer);  // Converte a lista para JSON e escreve no arquivo
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Método para zerar todas as solicitações
    public static void zerarSolicitacoes() {
        solicitacoes.clear(); // Limpa a lista de solicitações
        salvarSolicitacoesJson(); // Salva o estado vazio no arquivo
        System.out.println("Todas as solicitações foram removidas.");
    }

    // Método para carregar as solicitações a partir do arquivo
// Método para carregar solicitações a partir de um arquivo JSON
    public static void carregarSolicitacoesJson() {
        try (Reader reader = new FileReader("solicitacoes.json")) {
            Gson gson = new Gson();
            Type solicitacaoListType = new TypeToken<List<Solicitacao>>() {
            }.getType();
            solicitacoes = gson.fromJson(reader, solicitacaoListType);  // Carrega as solicitações do arquivo JSON
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Solicitacao> obterSolicitacoesPendentes() {
        return solicitacoes; // ou algum método de recuperação de dados da persistência
    }

    public static void addCliente(Cliente cliente) {
        List<Cliente> clientes = carregarClientes();

        // Verifica se o cliente já existe no arquivo
        boolean clienteExistente = false;
        for (int i = 0; i < clientes.size(); i++) {
            if (clientes.get(i).getId() == cliente.getId()) {
                clientes.set(i, cliente); // Atualiza o cliente
                clienteExistente = true;
                break;
            }
        }

        // Se o cliente não existir, adiciona o novo cliente
        if (!clienteExistente) {
            clientes.add(cliente);
        }

        // Salva os dados atualizados no arquivo
        salvarClientes(clientes);
    }

    // Salva os clientes no arquivo JSON
    public static void salvarClientes(List<Cliente> clientes) {
        try (FileWriter writer = new FileWriter(ARQUIVO_CLIENTES)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create(); // Para JSON formatado
            gson.toJson(clientes, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void salvarCliente(Cliente cliente) {
        List<Cliente> clientes = carregarClientes();
        for (int i = 0; i < clientes.size(); i++) {
            if (clientes.get(i).getId() == cliente.getId()) {
                clientes.set(i, cliente);
                salvarClientes(clientes);
                return;
            }
        }
        clientes.add(cliente);
        salvarClientes(clientes);
    }

    // Carrega os clientes do arquivo JSON
    public static List<Cliente> carregarClientes() {
        try (FileReader reader = new FileReader(ARQUIVO_CLIENTES)) {
            Gson gson = new Gson();
            Type listType = new TypeToken<List<Cliente>>() {
            }.getType();
            return gson.fromJson(reader, listType);
        } catch (FileNotFoundException e) {
            return new ArrayList<>(); // Retorna lista vazia se o arquivo não existir
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public static void addCaixa(Caixa caixa) {
        List<Caixa> caixas = carregarCaixas();

        boolean caixaExistente = false;
        for (int i = 0; i < caixas.size(); i++) {
            if (caixas.get(i).getId() == caixa.getId()) {
                caixas.set(i, caixa); // Atualiza o caixa
                caixaExistente = true;
                break;
            }
        }

        if (!caixaExistente) {
            caixas.add(caixa);
        }

        salvarCaixas(caixas);
    }

    public static void salvarCaixas(List<Caixa> caixas) {
        try (FileWriter writer = new FileWriter("caixas.json")) {
            Gson gson = new Gson();
            gson.toJson(caixas, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Caixa> carregarCaixas() {
        try (FileReader reader = new FileReader("caixas.json")) {
            Gson gson = new Gson();
            Type tipoListaCaixas = new TypeToken<List<Caixa>>() {
            }.getType();
            return gson.fromJson(reader, tipoListaCaixas);
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public static void addGerente(Gerente gerente) {
        List<Gerente> gerentes = carregarGerentes();

        boolean gerenteExistente = false;
        for (int i = 0; i < gerentes.size(); i++) {
            if (gerentes.get(i).getId() == gerente.getId()) {
                gerentes.set(i, gerente); // Atualiza o gerente
                gerenteExistente = true;
                break;
            }
        }

        if (!gerenteExistente) {
            gerentes.add(gerente);
        }

        salvarGerentes(gerentes);
    }

    public static void salvarGerentes(List<Gerente> gerentes) {
        try (FileWriter writer = new FileWriter("gerentes.json")) {
            Gson gson = new Gson();
            gson.toJson(gerentes, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Gerente> carregarGerentes() {
        try (FileReader reader = new FileReader("gerentes.json")) {
            Gson gson = new Gson();
            Type tipoListaGerentes = new TypeToken<List<Gerente>>() {
            }.getType();
            return gson.fromJson(reader, tipoListaGerentes);
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public static void salvarConta(Conta conta) {
        // Busca o cliente dono da conta
        Cliente dono = buscarClientePorId(conta.getDono().getId());

        if (dono != null) {
            // Atualiza a conta dentro do cliente
            for (int i = 0; i < dono.getContas().size(); i++) {
                if (dono.getContas().get(i).getNumeroDaConta() == conta.getNumeroDaConta()) {
                    // Atualiza a conta no cliente
                    dono.getContas().set(i, conta);
                    // Salva o cliente com a conta modificada
                    salvarCliente(dono);  // Chama o método para reescrever os dados do cliente
                    return; // Retorna após salvar
                }
            }
        }

        System.out.println("Conta ou cliente não encontrados.");
    }

    // Gera um ID aleatório para o usuário
    public static int gerarIdAleatorio() {
        Random rand = new Random();
        return rand.nextInt(1000000);  // Gera um ID entre 0 e 999999
    }

    public static Cliente buscarClientePorId(int id) {
        List<Cliente> clientes = carregarClientes();
        for (Cliente cliente : clientes) {
            if (cliente.getId() == id) {
                return cliente;
            }
        }
        return null;
    }

    public static Caixa buscarCaixaPorId(int id) {
        Gson gson = new Gson();

        try (FileReader reader = new FileReader("clientes.json")) {
            Type listType = new TypeToken<List<Caixa>>() {
            }.getType();
            List<Caixa> caixas = gson.fromJson(reader, listType);

            for (Caixa caixa : caixas) {
                if (caixa.getId() == id) {
                    return caixa;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null; // Retorna null se não encontrar o cliente
    }

    public static Gerente buscarGerentePorId(int id) {
        Gson gson = new Gson();

        try (FileReader reader = new FileReader("clientes.json")) {
            Type listType = new TypeToken<List<Gerente>>() {
            }.getType();
            List<Gerente> gerentes = gson.fromJson(reader, listType);

            for (Gerente gerente : gerentes) {
                if (gerente.getId() == id) {
                    return gerente;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null; // Retorna null se não encontrar o cliente
    }

    public static Conta buscarContaPorNumero(int numeroConta) {
        List<Cliente> clientes = carregarClientes();
        for (Cliente cliente : clientes) {
            for (Conta conta : cliente.getContas()) {
                if (conta.getNumeroDaConta() == numeroConta) {
                    return conta;
                }
            }
        }
        return null;
    }
}
