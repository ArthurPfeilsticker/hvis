package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Usuario;

public class UsuarioDAO extends DAO{
	
	public UsuarioDAO() {
		super();
		conectar();
	}
	
	public void finalize() {
		close();
	}
	
	public boolean insert(Usuario usuario) {
		boolean status = false;
		try {
			String sql = "INSERT INTO usuario (nome, username, password, cpf, telefone, email, cidade, estado, descricao, role, sexo, idade) "
				+ "VALUES ('" + usuario.getNome() + "', '"
				+ usuario.getUsername() + "', '" + usuario.getPassword() + "', " + usuario.getCPF()
				+ ", " + usuario.getTelefone() + ", '" + usuario.getEmail() + "', '" + usuario.getCidade()
				+ "', '" + usuario.getEstado() + "', '" + usuario.getDescricao() + "', '" + usuario.getRole() + "', '" + usuario.getSexo() + "', '" + usuario.getIdade() +  "');";
			PreparedStatement st = conexao.prepareStatement(sql);
			st.executeUpdate();
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}
	
	public Usuario get(int id) {
		Usuario usuario = null;

		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM usuario WHERE id = "+id;
			ResultSet rs = st.executeQuery(sql);
			if(rs.next()) {
				usuario = new Usuario(rs.getInt("id"), rs.getString("nome"), rs.getString("username"), rs.getLong("cpf"), rs.getLong("telefone"), rs.getString("email"), rs.getString("descricao"), rs.getString("cidade"), rs.getString("estado"), rs.getString("role"), rs.getString("sexo").charAt(0), rs.getInt("idade"));
;			}
			st.close();
		}catch (Exception e){
			System.err.println(e.getMessage());
		}
		return usuario;
	}
	//
	public boolean update(Usuario usuario) {
		boolean status = false;
		try {  
			String sql = "UPDATE usuario SET nome = '" + usuario.getNome() + "', "
				+ "username = " + usuario.getUsername() + ", " 
				+ "cpf = " + usuario.getCPF() + ","
				+ "telefone = " + usuario.getTelefone() + ", "
				+ "email = " + usuario.getEmail() + ", "
				+ "descricao = " + usuario.getDescricao() + ", "
				+ "cidade = " + usuario.getCidade() + ", "
				+ "estado = " + usuario.getEstado() + ", "
				+ "role = " + usuario.getRole() + ", "
				+ "sexo = " + usuario.getSexo() + ", "
				+ "idade = " + usuario.getIdade() + ", "
				+ " WHERE id = " + usuario.getId();
			PreparedStatement st = conexao.prepareStatement(sql);
			st.executeUpdate();
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}
	
	public Usuario delete(int id) {
		boolean status = false;
		Usuario user = null;
		try {  
			user = get(id);
			Statement st = conexao.createStatement();
			st.executeUpdate("DELETE FROM usuario WHERE id = " + id);
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status ? user : null;
	}
	
	public Usuario login(String username, String password) {
		Usuario usuario = null;
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM usuario WHERE username = '" + username + "' AND password = '" + password + "';";
			ResultSet rs = st.executeQuery(sql);
			if(rs.next()) {
				usuario = new Usuario(rs.getInt("id"), rs.getString("nome"), rs.getString("username"), rs.getLong("cpf"), rs.getLong("telefone"), rs.getString("email"), rs.getString("descricao"), rs.getString("cidade"), rs.getString("estado"), rs.getString("role"), rs.getString("sexo").charAt(0), rs.getInt("idade"));
;			}
			st.close();
		}catch (Exception e){
			System.err.println(e.getMessage());
		}
		return usuario;
	}
	
	public List<Usuario> list(){
		List<Usuario> users = new ArrayList<Usuario>();
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM usuario;";
			ResultSet rs = st.executeQuery(sql);
			while(rs.next()) {
				users.add(new Usuario(rs.getInt("id"), rs.getString("nome"), rs.getString("username"), rs.getLong("cpf"), rs.getLong("telefone"), rs.getString("email"), rs.getString("descricao"), rs.getString("cidade"), rs.getString("estado"), rs.getString("role"), rs.getString("sexo").charAt(0), rs.getInt("idade")));
;			}
			st.close();
		}catch (Exception e){
			System.err.println(e.getMessage());
		}
		return users;
	}
}
