package model;

public class Usuario {
    
	private int id;
	private String nome;
	private String username;
	private String password;
	private long cpf;
	private long telefone;
	private String email;
	private String cidade;
	private String estado;
	private String descricao;
	private String role;
	private char sexo;
	private int idade;
	
	public Usuario(int id, String nome, String username, String password, long cpf, long telefone, String email, String descricao, String cidade, String estado, String role, char sexo, int idade) {
		this.id = id;
		this.nome = nome;
		this.username = username;
		this.password = password;
		this.cpf = cpf;
		this.telefone = telefone;
		this.email = email;
		this.cidade = cidade;
		this.estado = estado;
		this.descricao = descricao;
		this.role = role;
		this.sexo = sexo;
		this.idade = idade;
	}
	
	public Usuario(int id, String nome, String username, long cpf, long telefone, String email, String descricao, String cidade, String estado, String role, char sexo, int idade) {
		this.id = id;
		this.nome = nome;
		this.username = username;
		this.cpf = cpf;
		this.telefone = telefone;
		this.email = email;
		this.cidade = cidade;
		this.estado = estado;
		this.descricao = descricao;
		this.role = role;
		this.sexo = sexo;
		this.idade = idade;
	}
	
	public Usuario() {
		this.id = -1;
		this.nome = null;
		this.username = null;
		this.password = null;
		this.cpf = 0;
		this.telefone = 0;
		this.email = null;
		this.cidade = null;
		this.estado = null;
		this.descricao = null;
		this.role = null;
		this.sexo = 'o';
		this.idade = 0;
	}
	
	public int getId() {
		return this.id;
	}
	
	public String getNome() {
		return this.nome;
	}
	
	public String getUsername() {
		return this.username;
	}
	
	public String getPassword() {
		return this.password;
	}
	
	public long getCPF() {
		return this.cpf;
	}
	
	public long getTelefone() {
		return this.telefone;
	}
	
	public String getEmail() {
		return this.email;
	}
	
	public String getDescricao() {
		return this.descricao;
	}
	
	public String getCidade() {
		return this.cidade;
	}
	public String getEstado() {
		return this.estado;
	}
	public String getRole() {
		return this.role;
	}
	public int getIdade() {
		return this.idade;
	}
	public char getSexo() {
		return this.sexo;
	}
}
