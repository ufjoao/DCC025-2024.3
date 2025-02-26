/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package banco.persistence;

import banco.model.Conta;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Persistence {

    private static final String ARQUIVO_CONTAS = "src/main/java/banco/persistence/contas.json";  // Caminho do arquivo
    private static final ObjectMapper objectMapper = new ObjectMapper()
            .enable(SerializationFeature.INDENT_OUTPUT); // Ativa a indentação automática

    // Carregar contas do arquivo JSON
    public static ArrayList<Conta> carregarContas() {
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

    // Método para salvar contas no arquivo JSON
    public static void salvarContas(ArrayList<Conta> contas) {
        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(ARQUIVO_CONTAS), contas);
        } catch (IOException e) {
            System.out.println("Erro ao salvar contas: " + e.getMessage());
        }
    }

    public static void addConta(Conta novaConta) {
        ArrayList<Conta> contas = carregarContas(); // Carrega todas as contas do JSON
        boolean contaExiste = false;
        for (Conta conta : contas) {
            if (conta.getNumeroDaConta() == novaConta.getNumeroDaConta()) {
                // Atualiza os dados da conta existente
                conta.setSaldo(novaConta.getSaldo());
                conta.setMovimentacoes(novaConta.getMovimentacoes());
                contaExiste = true;
                break;
            }
        }
        if (!contaExiste) {
            contas.add(novaConta); // Adiciona a conta apenas se não existir
        }
        salvarContas(contas); // Salva a lista atualizada no JSON
    }
}
