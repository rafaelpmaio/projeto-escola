package br.com.praticajava.escola.usuarios;

public abstract class Usuario {
	
	protected String nome;
	protected String email;
	protected int senha;
	
	public String getNome() {
		return nome;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}

	public int getSenha() {
		return senha;
	}

}
