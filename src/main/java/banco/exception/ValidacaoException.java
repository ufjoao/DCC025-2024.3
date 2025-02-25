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
