//João Alexandre dos Santos Nunes – 202235029
//João Antônio Fonseca e Almeida – 201935010
//Rodrigo da Silva Soares – 201765218AB
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package banco.exception;

import banco.model.Conta;
import java.util.ArrayList;

/**
 *
 * @author joao
 */
public class Validador 
{
    public static boolean nomeValido(String nome)
    {
        if(nome==null || nome.length()<2)
            return false;
        if(nome.matches(".*\\d.*"))
            return false;
        if(!nome.matches("^[a-zA-ZÀ-ÿ\\s]+$"))
            return false;
        if(temRepeticaoConsecutiva(nome))
            return false;
        
        return true;
    }
    
    // Validação do nome
    public static void validarNome(String nome) 
    {
        if (nome == null || nome.length() < 2) 
        {
            throw new ValidacaoException("Nome deve ter pelo menos 2 caracteres.");
        }
        if (nome.matches(".*\\d.*")) 
        {
            throw new ValidacaoException("Nome não pode conter números.");
        }
        if (!nome.matches("^[a-zA-ZÀ-ÿ\\s]+$")) 
        {
            throw new ValidacaoException("Nome não pode conter caracteres especiais (exceto acentuação).");
        }
        if (temRepeticaoConsecutiva(nome)) 
        {
            throw new ValidacaoException("Nome não pode ter 3 ou mais caracteres repetidos consecutivos.");
        }
    }

    // Verifica se o nome contém 3 ou mais caracteres repetidos consecutivos
    private static boolean temRepeticaoConsecutiva(String nome) 
    {
        for (int i = 0; i < nome.length() - 2; i++) 
        {
            if (nome.charAt(i) == nome.charAt(i + 1) && nome.charAt(i + 1) == nome.charAt(i + 2)) 
            {
                return true;
            }
        }
        return false;
    }

    // Função para validar o CPF completo
    public static boolean cpfValido(String cpf) 
    {
        // Verifica se todos os dígitos do CPF são iguais
        if (cpf.matches("(\\d)\\1{10}")) 
        {
            return false;
        }
        
        if (cpf.length() != 11) 
        {
            return false;
        }

        // Verifica se os dígitos calculados coincidem com os do CPF
        return true;
    }

    // Validação do CPF
    public static void validarCpf(String cpf) {
        if (cpf == null || cpf.length() != 11 || !cpf.matches("\\d{11}")) {
            throw new ValidacaoException("CPF inválido. Deve ter 11 dígitos numéricos.");
        }
        if (temNumerosEmSequencia(cpf)) {
            throw new ValidacaoException("CPF não pode conter números em sequência.");
        }
        if (temCaractereRepetidoCPF(cpf)) {
            throw new ValidacaoException("CPF não pode ter mais de 3 caracteres repetidos consecutivos.");
        }
        if (!cpfValido(cpf)) {
            throw new ValidacaoException("CPF inválido. Verifique os dígitos de verificação.");
        }
    }

    // Verifica se o CPF tem números em sequência (ex: 12345678900)
    private static boolean temNumerosEmSequencia(String cpf) {
        for (int i = 0; i < cpf.length() - 1; i++) {
            if (cpf.charAt(i) + 1 != cpf.charAt(i + 1)) {
                return false;
            }
        }
        return true;
    }

    // Verifica se o CPF tem mais de 3 caracteres repetidos consecutivos (ex: 11111111111)
    private static boolean temCaractereRepetidoCPF(String cpf) {
        for (int i = 0; i < cpf.length() - 3; i++) {
            if (cpf.charAt(i) == cpf.charAt(i + 1) && cpf.charAt(i + 1) == cpf.charAt(i + 2) && cpf.charAt(i + 2) == cpf.charAt(i + 3)) {
                return true;
            }
        }
        return false;
    }

    
    public static boolean senhaValida(char[] senha) {
    String senhaStr = new String(senha);
    
    if (senhaStr.length() != 6)
        return false;
    if (temSequencia(senhaStr))
        return false;
    if (temTodosCaracteresIguais(senhaStr))  // Adicionando verificação de numeros iguais
        return false;
    
    return true;
}

// Método para verificar se todos os caracteres são iguais
private static boolean temTodosCaracteresIguais(String senha) 
{
    return senha.matches("(\\d)\\1{5}");
}


    
// Validação da senha (como inteiro)
    public static void validarSenha(int senha) {
        // Convertendo a senha e o número da conta para String para facilitar as comparações
        String senhaStr = String.format("%06d", senha);  // Garante que a senha tenha 6 dígitos

        // Verificar se a senha tem exatamente 6 dígitos
        if (senhaStr.length() != 6) {
            throw new ValidacaoException("A senha deve conter exatamente 6 dígitos numéricos.");
        }

        // Verificar se a senha tem números em sequência (ex: 123456)
        if (temSequencia(senhaStr)) {
            throw new ValidacaoException("A senha não pode conter números em sequência.");
        }
    }

    public static boolean numeroContaValido(int senha)
    {
        String senhaStr = String.format("%06d", senha);
        if(senhaStr.length() != 6)
            return false;
        if(temSequencia(senhaStr))
            return false;
        
        return true;
    }
    
// Validação do número da conta (como inteiro)
    public static void validarNumeroConta(int senha) {
        // Convertendo a senha e o número da conta para String para facilitar as comparações
        String senhaStr = String.format("%06d", senha);  // Garante que a senha tenha 6 dígitos

        // Verificar se a senha tem exatamente 6 dígitos
        if (senhaStr.length() != 6) {
            throw new ValidacaoException("O número da conta deve conter exatamente 6 dígitos numéricos.");
        }

        // Verificar se a senha tem números em sequência (ex: 123456)
        if (temSequencia(senhaStr)) {
            throw new ValidacaoException("O número da conta não pode conter números em sequência.");
        }
    }

    public static void validarSenhaDaConta(int senha, int numeroConta) {
        // Convertendo a senha e o número da conta para String para facilitar as comparações
        String senhaStr = String.format("%06d", senha);  // Garante que a senha tenha 6 dígitos
        String contaStr = String.format("%06d", numeroConta);  // Garante que a conta tenha 6 dígitos

        // Verificar se a senha tem exatamente 6 dígitos
        if (senhaStr.length() != 6) {
            throw new ValidacaoException("A senha deve conter exatamente 6 dígitos numéricos.");
        }

        // Verificar se a senha tem números em sequência (ex: 123456)
        if (temSequencia(senhaStr)) {
            throw new ValidacaoException("A senha não pode conter números em sequência.");
        }

        // Verificar se a senha tem números repetidos (ex: 111111)
        if (temCaractereRepetido(senhaStr)) {
            throw new ValidacaoException("A senha não pode conter números repetidos.");
        }

        // Verificar se a senha é igual aos 6 primeiros dígitos da conta
        if (senhaStr.equals(contaStr)) {
            throw new ValidacaoException("A senha não pode ser igual aos 6 primeiros dígitos da conta.");
        }
    }

    // Verifica se a senha tem números em sequência (ex: 123456)
//    private static boolean temSequencia(String senha) {
//        for (int i = 0; i < senha.length() - 1; i++) {
//            if (senha.charAt(i) + 1 != senha.charAt(i + 1)) {
//                return false;
//            }
//        }
//        return true;
//    }
    private static boolean temSequencia(String senha) {
    int crescente = 1;
    int decrescente = 1;
    
    for (int i = 0; i < senha.length() - 1; i++) {
        if (senha.charAt(i) + 1 == senha.charAt(i + 1)) {
            crescente++;
        } else {
            crescente = 1;
        }

        if (senha.charAt(i) - 1 == senha.charAt(i + 1)) {
            decrescente++;
        } else {
            decrescente = 1;
        }

        if (crescente >= 3 || decrescente >= 3) {
            return true; // Senha contém uma sequência de 3 ou mais números
        }
    }
    
    return false;
}


    // Verifica se a senha tem números repetidos (ex: 111111)
    private static boolean temCaractereRepetido(String senha) {
        for (int i = 0; i < senha.length() - 1; i++) {
            if (senha.charAt(i) == senha.charAt(i + 1)) {
                return true;
            }
        }
        return false;
    }

    // Valida se um valor de depósito é válido
    public static void validarDeposito(double valor) {
        if (valor <= 0) {
            throw new ValidacaoException("O valor do depósito deve ser maior que zero.");
        }
    }

    // Valida se um saque pode ser realizado
    public static void validarSaque(double valor, double saldo) {
        if (valor <= 0) {
            throw new ValidacaoException("O valor do saque deve ser maior que zero.");
        }
        if (valor > saldo) {
            throw new ValidacaoException("Saldo insuficiente para realizar o saque.");
        }
    }

    // Valida se uma transferência pode ser realizada
    public static void validarTransferencia(float valor, Conta destino, ArrayList<Conta> contasCadastradas) {
        if (valor <= 0) {
            throw new ValidacaoException("O valor da transferência deve ser maior que zero.");
        }
        if (destino == null) {
            throw new ValidacaoException("Conta de destino inválida.");
        }
        boolean contaExiste = false;
        for (Conta conta : contasCadastradas) {
            if (conta.getNumeroDaConta() == destino.getNumeroDaConta()) {
                contaExiste = true;
                break;  // Para o loop ao encontrar a conta
            }
        }

        if (!contaExiste) {
            throw new ValidacaoException("Conta de destino não encontrada.");
        }
    }
}
