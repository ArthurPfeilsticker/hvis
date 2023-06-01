package model;

public class Produto {
	private int id;
	private String nome;
	private String descricao;
	private float preco;
	private int quantidade;
	private String imagem;
	private String category;
	
	public Produto() {
		id = -1;
		descricao = "";
		preco = 0.00F;
		quantidade = 0;
		nome = "";
		imagem = "";
		category = "";
	}

	public Produto(int id, String nome, float preco, int quantidade, String descricao, String imagem, String category) {
		setId(id);
		setDescricao(descricao);
		setPreco(preco);
		setQuantidade(quantidade);
		setNome(nome);
		setImagem(imagem);
		setCategory(category);
	}		
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	
	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public float getPreco() {
		return preco;
	}

	public void setPreco(float preco) {
		this.preco = preco;
	}

	public int getQuantidade() {
		return quantidade;
	}
	
	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}
	
	public String getNome() {
		return this.nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setImagem(String imagem) {
		this.imagem = imagem;
	}
	
	public String getImagem() {
		return imagem;
	}
	
	public void setCategory(String category) {
		this.category = category;
	}
	
	public String getCategory() {
		return category;
	}

	/**
	 * Método sobreposto da classe Object. É executado quando um objeto precisa
	 * ser exibido na forma de String.
	 */
	@Override
	public String toString() {
		return "Produto: " + descricao + "   Preço: R$" + preco + "   Quantidade.: " + quantidade;
	}
	
	@Override
	public boolean equals(Object obj) {
		return (this.getId() == ((Produto) obj).getId());
	}	
}