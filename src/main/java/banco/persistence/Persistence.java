/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package banco.persistence;

import banco.model.Conta;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Persistence {

    private static final String ARQUIVO_CONTAS = "src/main/java/banco/persistence/contas.json";

    // Método para salvar contas no arquivo JSON
    public static void salvarContas(ArrayList<Conta> contas) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            // Escreve os dados no arquivo
            mapper.writeValue(new File(ARQUIVO_CONTAS), contas);
            System.out.println("Contas salvas com sucesso!");
        } catch (IOException e) {
            System.err.println("Erro ao salvar contas: " + e.getMessage());
        }
    }

    // Método para carregar contas do arquivo JSON
    public static ArrayList<Conta> carregarContas() {
        ObjectMapper objectMapper = new ObjectMapper();
        ArrayList<Conta> contas = new ArrayList<>();

        try {
            File arquivo = new File(ARQUIVO_CONTAS);
            if (arquivo.exists() && arquivo.length() > 0) {
                // Se o arquivo existir e não estiver vazio, carrega as contas
                contas = objectMapper.readValue(arquivo, objectMapper.getTypeFactory().constructCollectionType(ArrayList.class, Conta.class));
            }
        } catch (IOException e) {
            System.out.println("Erro ao carregar contas: " + e.getMessage());
        }

        return contas;
    }

    // Método para adicionar uma conta ao registro
    public static void addConta(Conta conta) {
        // Adiciona a conta à lista, salvando ela ao mesmo tempo
        ArrayList<Conta> contas = carregarContas(); // Carrega as contas
        contas.add(conta); // Adiciona a conta nova
        salvarContas(contas); // Salva novamente todas as contas
    }
}
