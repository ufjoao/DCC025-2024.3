package banco.persistence;

import banco.model.*;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import java.io.*;
import java.lang.reflect.Type;
import java.util.*;

public class Persistence {

    private static final String ARQUIVO_CLIENTES = "clientes.json";
    private static final String ARQUIVO_CAIXAS = "caixas.json";
    private static final String ARQUIVO_GERENTES = "gerentes.json";

    // Método para adicionar ou atualizar um cliente
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

    // Método para adicionar ou atualizar um caixa
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

    // Salva os clientes no arquivo JSON
    public static void salvarClientes(List<Cliente> clientes) {
        try (FileWriter writer = new FileWriter("clientes.json")) {
            Gson gson = new Gson();
            gson.toJson(clientes, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void salvarCaixas(List<Caixa> caixas) {
        try (FileWriter writer = new FileWriter("caixas.json")) {
            Gson gson = new Gson();
            gson.toJson(caixas, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void salvarGerentes(List<Gerente> gerentes) {
        try (FileWriter writer = new FileWriter("gerentes.json")) {
            Gson gson = new Gson();
            gson.toJson(gerentes, writer);
        } catch (IOException e) {
            e.printStackTrace();
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

    public static void salvarCliente(Cliente cliente) {
        // Carregar todos os clientes
        List<Cliente> clientes = carregarClientes();

        // Encontrar o cliente pelo ID e atualizar
        for (int i = 0; i < clientes.size(); i++) {
            if (clientes.get(i).getId() == cliente.getId()) {
                // Atualiza o cliente na lista
                clientes.set(i, cliente);

                // Salva os clientes atualizados no arquivo
                salvarClientes(clientes);
                return; // Retorna após salvar o cliente
            }
        }

        // Se o cliente não for encontrado, podemos adicionar um novo
        clientes.add(cliente);
        salvarClientes(clientes); // Salva os dados no arquivo
    }

    // Carrega os clientes do arquivo JSON
    public static List<Cliente> carregarClientes() {
        try (FileReader reader = new FileReader("clientes.json")) {
            Gson gson = new Gson();
            Type tipoListaClientes = new TypeToken<List<Cliente>>() {
            }.getType();
            return gson.fromJson(reader, tipoListaClientes);
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
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

    // Método genérico para carregar dados
    private static <T> List<T> carregarDados(String filename, Type type, Gson gson) {
        try (FileReader reader = new FileReader(filename)) {
            return gson.fromJson(reader, type);
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    // Gera um ID aleatório para o usuário
    public static int gerarIdAleatorio() {
        Random rand = new Random();
        return rand.nextInt(1000000);  // Gera um ID entre 0 e 999999
    }

    public static Cliente buscarClientePorId(int id) {
        Gson gson = new Gson();

        try (FileReader reader = new FileReader("clientes.json")) {
            Type listType = new TypeToken<List<Cliente>>() {
            }.getType();
            List<Cliente> clientes = gson.fromJson(reader, listType);

            for (Cliente cliente : clientes) {
                if (cliente.getId() == id) {
                    return cliente;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null; // Retorna null se não encontrar o cliente
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
        throw new NoSuchElementException("Conta com número " + numeroConta + " não encontrada.");
    }
}
