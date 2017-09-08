package exception;

public class LoginRuntimeException extends RuntimeException {

	private static final long serialVersionUID = 2377673151685906964L;

	public LoginRuntimeException() {
		super("Usuário inválido. Faça login para continuar!");
	}

	public LoginRuntimeException(String message) {
		super(message);
	}
}
