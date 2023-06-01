package dao;

import model.Produto;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class ProdutoDAO extends DAO {	
	public ProdutoDAO() {
		super();
		conectar();
	}
	
	
	public void finalize() {
		close();
	}
	
	private static final String ENDPOINT = "http://265baa66-8d5a-4147-8c31-de894b2a21cd.southcentralus.azurecontainer.io/score";
	private static final String API_KEY = "BEMyzMvsuabnc0yHL3hExgByHEcMtBhr";

	public String endpointSI(String cidade, int idade, String sexo) {
		
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder().uri(URI.create(ENDPOINT)).headers("Content-Type", "application/json", "Authorization", "Bearer " + API_KEY).POST(HttpRequest.BodyPublishers.ofString(sampleData(cidade, idade, sexo))).build();
		
		try {
			HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
			
			String classification = responseMapBody(response.body());
			
            return classification;
            //return classification;
		}catch (IOException | InterruptedException e) {
			e.printStackTrace();
			return "";
		}
	}
	
	private static String sampleData(String cidade, int idade, String sexo) {
        JSONArray array = new JSONArray();
        JSONObject item = new JSONObject();
        JSONObject mainItem = new JSONObject();
        item.put("categoria", "");
        item.put("sexo", sexo);
        item.put("cidade", cidade);
        item.put("idade", idade);

        array.put(item);

        return array.toString();
    }
	
	private String responseMapBody(String body) {
        
        body = body.replaceAll("\\\\", "");
        body = body.substring(1, body.length()-1);

        JSONObject jsonObject = new JSONObject(body);
        
        JSONArray objs = (JSONArray) jsonObject.get("result");
        
        String finalResult = "";

        for (Object _obj : objs) {
            JSONArray _obj2 = (JSONArray) _obj;
            String str = (String)_obj2.get(_obj2.length()-1);
            finalResult += str;
        }

        return finalResult;
    }
	
	
	public boolean insert(Produto produto) {
		boolean status = false;
		try {
			String sql = "INSERT INTO produto (descricao, preco, quantidade, nome, imagem) "
		               + "VALUES ('" + produto.getDescricao() + "', "
		               + produto.getPreco() + ", " + produto.getQuantidade() + ", '" + produto.getNome() + "', '" + produto.getImagem() + "', '" + produto.getCategory() + "');";
			PreparedStatement st = conexao.prepareStatement(sql);
			st.executeUpdate();
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}

	
	public Produto get(int id) {
		Produto produto = null;
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM produto WHERE id = "+id;
			ResultSet rs = st.executeQuery(sql);	
	        if(rs.next()){            
	        	 produto = new Produto(rs.getInt("id"), rs.getString("nome"), (float)rs.getDouble("preco"), 
	                				   rs.getInt("quantidade"),rs.getString("descricao"),rs.getString("imagem"), rs.getString("category"));
	        }
	        st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return produto;
	}
	
	
	public List<Produto> get() {
		return get("");
	}

	
	public List<Produto> getOrderByID() {
		return get("id");		
	}
	
	
	public List<Produto> getOrderByDescricao() {
		return get("descricao");		
	}
	
	
	public List<Produto> getOrderByPreco() {
		return get("preco");		
	}
	
	
	private List<Produto> get(String orderBy) {
		List<Produto> produtos = new ArrayList<Produto>();
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM produto" + ((orderBy.trim().length() == 0) ? "" : (" ORDER BY " + orderBy));
			ResultSet rs = st.executeQuery(sql);	           
	        while(rs.next()) {	            	
	        	Produto p = new Produto(rs.getInt("id"), rs.getString("nome"), (float)rs.getDouble("preco"), 
				rs.getInt("quantidade"),rs.getString("descricao"),rs.getString("imagem"), rs.getString("category"));
	            produtos.add(p);
	        }
	        st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return produtos;
	}
	
	
	public boolean update(Produto produto) {
		boolean status = false;
		try {  
			String sql = "UPDATE produto SET descricao = '" + produto.getDescricao() + "', "
					+ "preco = " + produto.getPreco() + ", " 
					+ "quantidade = " + produto.getQuantidade() + ", "
					+ "nome = '" + produto.getNome() + "', "
					+ "imagem = '" + produto.getImagem() + "', "
					+ "category = '" + produto.getCategory() + "', " 
					+ "WHERE id = " + produto.getId();
			PreparedStatement st = conexao.prepareStatement(sql);
			st.executeUpdate();
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}
	
	
	public Produto delete(int id) {
		boolean status = false;
		Produto product = null;
		try {
			product = get(id);  
			Statement st = conexao.createStatement();
			st.executeUpdate("DELETE FROM produto WHERE id = " + id);
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status ? product : null;
	}
}