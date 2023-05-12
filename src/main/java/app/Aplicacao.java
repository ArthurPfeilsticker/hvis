package app;

import static spark.Spark.*;
import service.UsuarioService;
import service.ProdutoService;
import spark.Filter;
import spark.Request;
import spark.Response;
import spark.Spark;


public class Aplicacao {
	
	private static UsuarioService usuarioService = new UsuarioService();
	private static ProdutoService produtoService = new ProdutoService();
	
    public static void main(String[] args) {
        port(4568);
        
        staticFiles.location("/public");
        
     // Cria o filtro CORS manualmente
        Filter filter = new Filter() {
            @Override
            public void handle(Request request, Response response) throws Exception {
                response.header("Access-Control-Allow-Origin", request.headers("Origin"));
                response.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
                response.header("Access-Control-Allow-Headers", "Content-Type, Authorization");
                response.header("Access-Control-Allow-Credentials", "true");
                response.type("application/json");
            }

	
        };

        // Adiciona o filtro CORS às rotas do Spark
        options("/*", (request, response) -> {
            String accessControlRequestHeaders = request.headers("Access-Control-Request-Headers");
            if (accessControlRequestHeaders != null) {
                response.header("Access-Control-Allow-Headers", accessControlRequestHeaders);
            }

            String accessControlRequestMethod = request.headers("Access-Control-Request-Method");
            if (accessControlRequestMethod != null) {
                response.header("Access-Control-Allow-Methods", accessControlRequestMethod);
            }

            return "OK";
        });
        Spark.before(filter);
        
        get("/usuario", (request, response) -> usuarioService.list(request, response));
        
        post("/usuario", (request, response) -> usuarioService.insert(request, response));
        
        get("/usuario/:id", (request, response) -> usuarioService.get(request, response));
         
        post("/login", (request, response) -> usuarioService.login(request, response));
        
        put("/usuario/:id", (request, response) -> usuarioService.update(request, response));
        
        delete("/usuario/:id", (request, response) -> usuarioService.delete(request, response));
        
        get("/produto", (request, response) -> produtoService.list(request, response));
        
        post("/produto", (request, response) -> produtoService.insert(request, response));
        
        get("/produto/:id", (request, response) -> produtoService.get(request, response));
        
        delete("/produto/:id", (request, response) -> produtoService.delete(request, response));

    }
}