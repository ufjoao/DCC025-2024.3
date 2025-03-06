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
}
