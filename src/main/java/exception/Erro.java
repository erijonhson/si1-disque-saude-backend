package exception;

public class Erro {

	private String causa;

	public Erro(String causa) {
		this.setCausa(causa);
	}

	public String getCausa() {
		return causa;
	}

	public void setCausa(String causa) {
		this.causa = causa;
	}

}
