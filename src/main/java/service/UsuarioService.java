package service;

import java.io.File;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import dao.UsuarioDAO;
import model.Usuario;

import spark.Request;
import spark.Response;


public class UsuarioService {
	
	private boolean authMiddleware(Request request) {
		Set<String> headers = request.headers();
		if(headers.contains("Authorization")) {
			String authToken = request.headers("Authorization");
			return true;
		} else {
			makeJson(ERROR_401);
			return false;
		}
	}
	
	
	private String getMd5(String input)
    {
        try {
 
            // Static getInstance method is called with hashing MD5
            MessageDigest md = MessageDigest.getInstance("MD5");
 
            // digest() method is called to calculate message digest
            // of an input digest() return array of byte
            byte[] messageDigest = md.digest(input.getBytes());
 
            // Convert byte array into signum representation
            BigInteger no = new BigInteger(1, messageDigest);
 
            // Convert message digest into hex value
            String hashtext = no.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        }
 
        // For specifying wrong message digest algorithms
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
	
	private UsuarioDAO usuarioDAO = new UsuarioDAO();
	private String json;
	private final int GET_USER = 1;
	private final int INSERT_USER = 2;
	private final int DELETE_USER = 3;
	private final int UPDATE_USER = 4;
	private final int LIST_USER = 6;
	private final int ERROR_404 = 7;
	private final int ERROR_401 = 8;
	
	private void makeJson(int erro) {
		List<Usuario> users = new ArrayList<Usuario>();
		makeJson(erro, users);
	}
	
	private void makeJson(int tipo, List<Usuario> usuarios) {
		String nomeTemplate = "userJson.json";
		json = "";
		try {
			Scanner entrada = new Scanner(new File(nomeTemplate));
			while(entrada.hasNext()) {
				json += (entrada.nextLine() + "\n");
			}
		} catch (Exception e){
			System.out.println(e.getMessage());
		}
		
		String msg = "";
		String userString = "";
		if(tipo == ERROR_401) {
			msg = "Não autorizado.";
			json = json.replaceFirst("%MESSAGE%", "\"" + msg + "\"");
			json = json.replaceFirst("%USERINFO%", userString);
		}
		else if(tipo == ERROR_404) {
			msg = "Não encontrado.";
			json = json.replaceFirst("%MESSAGE%", "\"" + msg + "\"");
			json = json.replaceFirst("%USERINFO%", userString);
		} else {
			Usuario usuario = usuarios.get(0);
			switch(tipo) {
			case GET_USER:
				System.out.println("teste");
				msg = "Usuario de id " + usuario.getId() + " encontrado.";
				userString += "{";
				userString += "\"id\": " + usuario.getId() + ",";
				userString += "\"username\": \"" + usuario.getUsername() + "\",";
				userString += "\"nome\": \"" + usuario.getNome() + "\",";
				userString += "\"cpf\": " + usuario.getCPF() + ",";
				userString += "\"email\": \"" + usuario.getEmail() + "\",";
				userString += "\"telefone\": " + usuario.getTelefone() + ",";
				userString += "\"descricao\": \"" + usuario.getDescricao() + "\",";
				userString += "\"cidade\": \"" + usuario.getCidade() + "\",";
				userString += "\"estado\": \"" + usuario.getEstado() + "\",";
				userString += "\"role\": \"" + usuario.getRole() + "\",";
				userString += "\"sexo\": \"" + usuario.getSexo() + "\",";
				userString += "\"idade\": \"" + usuario.getIdade() + "\"";
				userString += "}";
				break;
			case INSERT_USER:
				userString += "{";
				msg = "Usuario " + usuario.getUsername() + " inserido.";
				userString += "\"id\": " + usuario.getId() + ",";
				userString += "\"username\": \"" + usuario.getUsername() + "\"";
				userString += "}";
				break;
			case DELETE_USER:
				userString += "{";
				msg = "Usuario de id " + usuario.getId() + " deletado.";
				userString += "\"username\": \"" + usuario.getUsername() + "\"";
				userString += "}";
				break;
			case UPDATE_USER:
				msg = "Usuario de id " + usuario.getId() + " atualizado.";
				userString += "{";
				userString += "\"id\": " + usuario.getId() + ",";
				userString += "\"username\": \"" + usuario.getUsername() + "\",";
				userString += "\"nome\": \"" + usuario.getNome() + "\",";
				userString += "\"cpf\": " + usuario.getCPF() + ",";
				userString += "\"email\": \"" + usuario.getEmail() + "\",";
				userString += "\"telefone\": " + usuario.getTelefone() + ",";
				userString += "\"descricao\": \"" + usuario.getDescricao() + "\",";
				userString += "\"cidade\": \"" + usuario.getCidade() + "\",";
				userString += "\"estado\": \"" + usuario.getEstado() + "\",";
				userString += "\"role\": \"" + usuario.getRole() + "\",";
				userString += "\"sexo\": \"" + usuario.getSexo() + "\",";
				userString += "\"idade\": \"" + usuario.getIdade() + "\"";
				userString += "}";
				break;
			case LIST_USER:
				msg = "Usuarios existestes.";
				for(int i = 0; i < usuarios.size(); i++) {
					usuario = usuarios.get(i);
					userString += "{";
					userString += "\"id\": " + usuario.getId() + ",";
					userString += "\"username\": \"" + usuario.getUsername() + "\",";
					userString += "\"nome\": \"" + usuario.getNome() + "\",";
					userString += "\"cpf\": " + usuario.getCPF() + ",";
					userString += "\"email\": \"" + usuario.getEmail() + "\",";
					userString += "\"telefone\": " + usuario.getTelefone() + ",";
					userString += "\"descricao\": \"" + usuario.getDescricao() + "\",";
					userString += "\"cidade\": \"" + usuario.getCidade() + "\",";
					userString += "\"estado\": \"" + usuario.getEstado() + "\",";
					userString += "\"role\": \"" + usuario.getRole() + "\",";
					userString += "\"sexo\": \"" + usuario.getSexo() + "\",";
					userString += "\"idade\": \"" + usuario.getIdade() + "\"";
					userString += "},";
				}
				userString = userString.substring(0, userString.length() - 1);
			}
			json = json.replaceFirst("%MESSAGE%", "\"" + msg + "\"");
			json = json.replaceFirst("%USERINFO%", userString);
		}
	}
	
    public Object get(Request request, Response response) {
    	
    	if(!authMiddleware(request)) {
    		response.status(401);
    		response.type("application/json");
    		return json;
    	}
    	
    	int id = Integer.parseInt(request.params(":id"));
    	Usuario usuario = (Usuario) usuarioDAO.get(id);

    	if(usuario != null) {
    		response.status(200);
    		response.type("application/json");
    		List<Usuario> users = new ArrayList<Usuario>();
    		users.add(usuario);
    		makeJson(GET_USER, users);
    	}else {
    		response.status(404);
    		makeJson(ERROR_404);
    	}
    	
    	return json;
    }
    
    public Object insert(Request request, Response response) {
    	
    	if(!authMiddleware(request)) {
    		response.status(401);
    		response.type("application/json");
    		return json;
    	}
    	
    	String body = request.body();
    	String name = "";
    	String username = "";
    	String password = "";
    	String cpf = "";
    	String email = "";
    	String telefone = "";
    	String descricao = "";
    	String cidade = "";
    	String estado = "";
    	String role = "";
    	String sexo = "";
    	String idade = "";
    	
    	
    	String cursor = body.split("\"nome\":\"")[1];
    	int k = 0;
    	while(cursor.charAt(k) != '\"') {
    		name += cursor.charAt(k);
			k++;
    	}
		k = 0;
    	cursor = body.split("\"username\":\"")[1];
    	while(cursor.charAt(k) != '\"') {
    		username += cursor.charAt(k);
			k++;
    	}
		k=0;
    	cursor = body.split("\"password\":\"")[1];
    	while(cursor.charAt(k) != '\"') {
    		password += cursor.charAt(k);
			k++;
    	}
		k=0;
    	cursor = body.split("\"cpf\":")[1];
    	while(cursor.charAt(k) != ',') {
    		cpf += cursor.charAt(k);
			k++;
    	}
		k=0;
    	cursor = body.split("\"email\":\"")[1];
    	while(cursor.charAt(k) != '\"') {
    		email += cursor.charAt(k);
			k++;
    	}
		k=0;
    	cursor = body.split("\"telefone\":")[1];
    	while(cursor.charAt(k) != ',') {
    		telefone += cursor.charAt(k);
			k++;
    	}
		k=0;
    	cursor = body.split("\"descricao\":\"")[1];
    	while(cursor.charAt(k) != '\"') {
    		descricao += cursor.charAt(k);
			k++;
    	}
		k=0;
    	cursor = body.split("\"cidade\":\"")[1];
    	while(cursor.charAt(k) != '\"') {
    		cidade += cursor.charAt(k);
			k++;
    	}
		k=0;
    	cursor = body.split("\"estado\":\"")[1];
    	while(cursor.charAt(k) != '\"') {
    		estado += cursor.charAt(k);
			k++;
    	}
    	k=0;
    	cursor = body.split("\"role\":\"")[1];
    	while(cursor.charAt(k) != '\"') {
    		role += cursor.charAt(k);
			k++;
    	}
    	k=0;
    	cursor = body.split("\"sexo\":\"")[1];
    	while(cursor.charAt(k) != '\"') {
    		sexo += cursor.charAt(k);
			k++;
    	}
    	k=0;
    	cursor = body.split("\"idade\":\"")[1];
    	while(cursor.charAt(k) != '\"') {
    		idade += cursor.charAt(k);
			k++;
    	}
    	
    	password = getMd5(password);
    	
    	Usuario usuario = new Usuario(-1, name, username, password, Long.parseLong(cpf), Long.parseLong(telefone), email, descricao, cidade, estado, role, sexo.charAt(0), Integer.parseInt(idade));
    	
    	if(usuarioDAO.insert(usuario) == true) {
    		response.status(201);
    		response.type("application/json");
    		List<Usuario> users = new ArrayList<Usuario>();
    		users.add(usuario);
    		makeJson(INSERT_USER, users);
    	}else {
    		response.status(404);
    		makeJson(ERROR_404);
    	}
    	
    	return json;
    }
    
    public Object update(Request request, Response response) {
    	
    	if(!authMiddleware(request)) {
    		response.status(401);
    		response.type("application/json");
    		return json;
    	}
    	
    	String body = request.body();
    	String name = "";
    	String username = "";
    	String cpf = "";
    	String email = "";
    	String telefone = "";
    	String descricao = "";
    	String cidade = "";
    	String estado = "";
    	String role = "";
    	String sexo = "";
    	String idade = "";
    	
    	String cursor = body.split("\"nome\":\"")[1];
    	int k = 0;
    	while(cursor.charAt(k) != '\"') {
    		name += cursor.charAt(k);
			k++;
    	}
		k = 0;
    	cursor = body.split("\"username\":\"")[1];
    	while(cursor.charAt(k) != '\"') {
    		username += cursor.charAt(k);
			k++;
    	}
		k=0;
    	cursor = body.split("\"cpf\":")[1];
    	while(cursor.charAt(k) != ',') {
    		cpf += cursor.charAt(k);
			k++;
    	}
		k=0;
    	cursor = body.split("\"email\":\"")[1];
    	while(cursor.charAt(k) != '\"') {
    		email += cursor.charAt(k);
			k++;
    	}
		k=0;
    	cursor = body.split("\"telefone\":")[1];
    	while(cursor.charAt(k) != ',') {
    		telefone += cursor.charAt(k);
			k++;
    	}
		k=0;
    	cursor = body.split("\"descricao\":\"")[1];
    	while(cursor.charAt(k) != '\"') {
    		descricao += cursor.charAt(k);
			k++;
    	}
		k=0;
    	cursor = body.split("\"cidade\":\"")[1];
    	while(cursor.charAt(k) != '\"') {
    		cidade += cursor.charAt(k);
			k++;
    	}
		k=0;
    	cursor = body.split("\"estado\":\"")[1];
    	while(cursor.charAt(k) != '\"') {
    		estado += cursor.charAt(k);
			k++;
    	}
    	k=0;
    	cursor = body.split("\"role\":\"")[1];
    	while(cursor.charAt(k) != '\"') {
    		role += cursor.charAt(k);
			k++;
    	}
    	k=0;
    	cursor = body.split("\"sexo\":\"")[1];
    	while(cursor.charAt(k) != '\"') {
    		sexo += cursor.charAt(k);
			k++;
    	}
    	k=0;
    	cursor = body.split("\"idade\":\"")[1];
    	while(cursor.charAt(k) != '\"') {
    		idade += cursor.charAt(k);
			k++;
    	}
    	
    	Usuario usuario = new Usuario(-1, name, username, Long.parseLong(cpf), Integer.parseInt(telefone), email, descricao, cidade, estado, role, sexo.charAt(0), Integer.parseInt(idade));
    	
    	if(usuarioDAO.update(usuario) == true) {
    		response.status(201);
    		response.type("application/json");
    		List<Usuario> users = new ArrayList<Usuario>();
    		users.add(usuario);
    		makeJson(UPDATE_USER, users);
    	}else {
    		response.status(404);
    		makeJson(ERROR_404);
    	}
    	
    	return json;
    }
    
    public Object login(Request request, Response response) {
    	
    	String body = request.body();
    	System.out.println(body);
    	String username = "";
    	String password = "";
    	String cursor = body.split("\"username\":\"")[1];
    	int k = 0;
    	while(cursor.charAt(k) != '\"') {
    		username += cursor.charAt(k);
			k++;
    	}
		k = 0;
		cursor = body.split("\"password\":\"")[1];
    	while(cursor.charAt(k) != '\"') {
    		password += cursor.charAt(k);
			k++;
    	}
		k = 0;
		
		password = getMd5(password);
		
		Usuario user = (Usuario) usuarioDAO.login(username, password);
		if(user != null) {
			response.status(200);
    		response.type("application/json");
    		List<Usuario> users = new ArrayList<Usuario>();
    		users.add(user);
    		makeJson(GET_USER, users);
		} else {
    		response.status(404);
    		makeJson(ERROR_404);
    	}
    	
    	return json;
    }
    
    public Object delete(Request request, Response response) {
    	if(!authMiddleware(request)) {
    		response.status(401);
    		response.type("application/json");
    		return json;
    	}
    	
    	int id = Integer.parseInt(request.params(":id"));
    	Usuario result = usuarioDAO.delete(id);
    	
    	if(result != null) {
    		response.status(200);
    		response.type("application/json");
    		List<Usuario> users = new ArrayList<Usuario>();
    		users.add(result);
    		makeJson(DELETE_USER, users);
    	}else {
    		response.status(404);
    		makeJson(ERROR_404);
    	}
    	
    	return json;
    }
    
    public Object list(Request request, Response response) {
    	if(!authMiddleware(request)) {
    		response.status(401);
    		response.type("application/json");
    		return json;
    	}
    	
    	List<Usuario> result = usuarioDAO.list();
    	
    	if(result != null) {
    		response.status(200);
    		response.type("application/json");
    		makeJson(LIST_USER, result);
    	}else {
    		response.status(404);
    		makeJson(ERROR_404);
    	}
    	
    	return json;
    }
}
