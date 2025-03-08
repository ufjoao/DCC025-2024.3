//João Alexandre dos Santos Nunes – 202235029
//João Antônio Fonseca e Almeida – 201935010
//Rodrigo da Silva Soares – 201765218AB
package banco.exception;

public class ValidacaoException extends RuntimeException {

    // Construtor que recebe uma mensagem de erro
    public ValidacaoException(String mensagem) {
        super(mensagem);
    }

    // Construtor que recebe uma mensagem de erro e a causa da exceção
    public ValidacaoException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
