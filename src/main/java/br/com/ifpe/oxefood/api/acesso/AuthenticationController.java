package br.com.ifpe.oxefood.api.acesso;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.HashMap;
import java.util.Map;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.ifpe.oxefood.modelo.acesso.Usuario;
import br.com.ifpe.oxefood.modelo.acesso.UsuarioService;
import br.com.ifpe.oxefood.modelo.seguranca.JwtService;

// Define a classe como um controlador REST para autenticação
@RestController
@RequestMapping("/api/auth") // Mapeia o endpoint base para as requisições de autenticação
@CrossOrigin // Permite chamadas de diferentes origens (cross-origin)
@Tag( // Usado pelo Swagger para documentar a API
    name = "API Autenticação", 
    description = "API responsável pela autenticação de usuários no sistema"
)

public class AuthenticationController {

    // Declaração dos serviços necessários para a autenticação e geração de JWT
    private final JwtService jwtService;

    private UsuarioService usuarioService;

    // Construtor para injetar as dependências de JwtService e UsuarioService
    public AuthenticationController(JwtService jwtService, UsuarioService usuarioService) {
        this.jwtService = jwtService; // Atribui o serviço de JWT
        this.usuarioService = usuarioService; // Atribui o serviço de autenticação de usuário
    }

    // Anotação do Swagger que descreve o serviço de login para documentação
    @Operation(
        summary = "Serviço responsável por logar um usuario no sistema.", // Resumo da operação
        description = "api/auth" // Descrição adicional da operação (geralmente usada em documentação)
    )
    @PostMapping // Define que esse método responde a requisições POST
    public Map<Object, Object> signin(@RequestBody AuthenticationRequest data) {
    
        // Chama o serviço para autenticar o usuário com as credenciais fornecidas
        Usuario authenticatedUser = usuarioService.authenticate(data.getUsername(), data.getPassword());

        // Gera o token JWT para o usuário autenticado
        String jwtToken = jwtService.generateToken(authenticatedUser);

        // Cria o mapa de resposta que será retornado
        Map<Object, Object> loginResponse = new HashMap<>();
        loginResponse.put("username", authenticatedUser.getUsername()); // Adiciona o nome de usuário na resposta
        loginResponse.put("token", jwtToken); // Adiciona o token JWT na resposta
        loginResponse.put("tokenExpiresIn", jwtService.getExpirationTime()); // Adiciona o tempo de expiração do token

        return loginResponse; // Retorna a resposta com o nome de usuário, token e tempo de expiração
    }    
}
