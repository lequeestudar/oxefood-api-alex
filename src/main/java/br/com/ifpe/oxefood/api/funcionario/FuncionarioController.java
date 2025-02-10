package br.com.ifpe.oxefood.api.funcionario;

import io.swagger.v3.oas.annotations.Operation; // Anotações para gerar documentação Swagger
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List; // Lista de funcionários
import org.springframework.beans.factory.annotation.Autowired; // Injeção de dependências
import org.springframework.http.HttpStatus; // Status HTTP
import org.springframework.http.ResponseEntity; // Retorno das respostas HTTP
import org.springframework.web.bind.annotation.CrossOrigin; // Habilita CORS
import org.springframework.web.bind.annotation.DeleteMapping; // Mapeia requisições DELETE
import org.springframework.web.bind.annotation.GetMapping; // Mapeia requisições GET
import org.springframework.web.bind.annotation.PathVariable; // Mapeia variáveis na URL
import org.springframework.web.bind.annotation.PostMapping; // Mapeia requisições POST
import org.springframework.web.bind.annotation.PutMapping; // Mapeia requisições PUT
import org.springframework.web.bind.annotation.RequestBody; // Para mapear o corpo da requisição
import org.springframework.web.bind.annotation.RequestMapping; // Define a URL base do controlador
import org.springframework.web.bind.annotation.RestController; // Indica que a classe é um controlador REST
import br.com.ifpe.oxefood.modelo.acesso.Perfil; // Modelo para perfil de usuário
import br.com.ifpe.oxefood.modelo.acesso.Usuario; // Modelo de usuário
import br.com.ifpe.oxefood.modelo.funcionario.Funcionario; // Modelo de funcionário
import br.com.ifpe.oxefood.modelo.funcionario.FuncionarioService; // Serviço para manipulação de dados do funcionário
import br.com.ifpe.oxefood.modelo.funcionario.TipoFuncionario; // Enum para tipos de funcionários
import jakarta.validation.Valid; // Para validar os dados de entrada

@RestController
@RequestMapping("/api/funcionario") // Define o caminho da URL para acessar a API
@CrossOrigin // Habilita CORS (Cross-Origin Resource Sharing)
@Tag(name = "API Funcionario", description = "API responsável pelos serviços de funcionário no sistema") // Definição do nome e descrição para o Swagger

public class FuncionarioController {

    @Autowired
    private FuncionarioService funcionarioService; // Serviço que gerencia a lógica de negócios dos funcionários

    // Endpoint para salvar um novo funcionário no sistema
    @Operation(summary = "Serviço responsável por salvar um funcionário no sistema.", description = "api/funcionario")
    @PostMapping // Mapeia a requisição POST para criar um novo funcionário
    public ResponseEntity<Funcionario> save(@RequestBody @Valid FuncionarioRequest request) {
        // Constrói um novo objeto Funcionario a partir da requisição
        Funcionario funcionarioNovo = request.build();

        // Verifica o tipo do funcionário e atribui o perfil de usuário adequado
        if (funcionarioNovo.getTipo().equals(TipoFuncionario.ADMINISTRADOR)) {
            funcionarioNovo.getUsuario().getRoles().add(new Perfil(Perfil.ROLE_FUNCIONARIO_ADMIN));
        } else if (funcionarioNovo.getTipo().equals(TipoFuncionario.OPERADOR)) {
            funcionarioNovo.getUsuario().getRoles().add(new Perfil(Perfil.ROLE_FUNCIONARIO_USER));
        }

        // Chama o serviço para salvar o novo funcionário
        Funcionario funcionario = funcionarioService.save(funcionarioNovo);
        
        // Retorna uma resposta com status 201 (Created) e o objeto do funcionário salvo
        return new ResponseEntity<Funcionario>(funcionario, HttpStatus.CREATED);
    }

    // Endpoint para listar todos os funcionários cadastrados no sistema
    @Operation(summary = "Serviço responsável por listar todos os funcionários cadastrados no sistema.", description = "api/funcionario")
    @GetMapping // Mapeia a requisição GET para listar todos os funcionários
    public List<Funcionario> listarTodos() {
        // Chama o serviço para listar todos os funcionários
        return funcionarioService.listarTodos();
    }

    // Endpoint para obter dados de um funcionário específico, dado seu ID
    @Operation(summary = "Serviço responsável por pegar dados de um funcionário específico no sistema.", description = "api/funcionario/id")
    @GetMapping("/{id}") // Mapeia a requisição GET para obter um funcionário por ID
    public Funcionario obterPorID(@PathVariable Long id) {
        // Chama o serviço para obter o funcionário pelo ID
        return funcionarioService.obterPorID(id);
    }

    // Endpoint para editar os dados de um funcionário específico, dado seu ID
    @Operation(summary = "Serviço responsável por editar dados de um funcionário específico no sistema.", description = "api/funcionario/id")
    @PutMapping("/{id}") // Mapeia a requisição PUT para atualizar os dados do funcionário
    public ResponseEntity<Funcionario> update(@PathVariable("id") Long id, @RequestBody FuncionarioRequest request) {
        // Chama o serviço para atualizar os dados do funcionário com o ID fornecido
        funcionarioService.update(id, request.build());
        // Retorna uma resposta com status 200 (OK)
        return ResponseEntity.ok().build();
    }

    // Endpoint para deletar um funcionário específico, dado seu ID
    @Operation(summary = "Serviço responsável por deletar dados de um funcionário específico no sistema.", description = "api/funcionario/id")
    @DeleteMapping("/{id}") // Mapeia a requisição DELETE para deletar um funcionário por ID
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        // Chama o serviço para deletar o funcionário com o ID fornecido
        funcionarioService.delete(id);
        // Retorna uma resposta com status 200 (OK) indicando que a operação foi bem-sucedida
        return ResponseEntity.ok().build();
    }
    
}
