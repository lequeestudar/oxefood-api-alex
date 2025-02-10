package br.com.ifpe.oxefood.api.entregador;
// Importações necessárias para a classe
import java.util.List; // Para trabalhar com listas de entregadores
import io.swagger.v3.oas.annotations.Operation; // Para gerar documentação da API com Swagger
import io.swagger.v3.oas.annotations.tags.Tag; // Para adicionar tags na documentação do Swagger

import org.springframework.beans.factory.annotation.Autowired; // Para injeção de dependência
import org.springframework.http.HttpStatus; // Para definir os status HTTP das respostas
import org.springframework.http.ResponseEntity; // Para enviar as respostas com status HTTP
import org.springframework.web.bind.annotation.*; // Para manipulação dos métodos HTTP

import br.com.ifpe.oxefood.modelo.entregador.Entregador; // Modelo de dados do entregador
import br.com.ifpe.oxefood.modelo.entregador.EntregadorService; // Serviço responsável pela lógica de negócios do entregador
import jakarta.validation.Valid; // Para garantir que os dados de entrada estão validados

@RestController // Define que esta classe é um controlador REST
@RequestMapping("/api/entregador") // Define o caminho base para os endpoints dessa classe
@CrossOrigin // Permite requisições de origens externas (CORS)
@Tag( // Adiciona uma tag para a documentação Swagger
    name = "API Entregador", 
    description = "API responsável pelos serviços de entregador no sistema"
)

public class EntregadorController {

    @Autowired // Injeta a dependência do serviço de entregador
    private EntregadorService entregadorService;

    // Serviço responsável por salvar um entregador no sistema
    @Operation(
        summary = "Serviço responsável por salvar um entregador no sistema.",
        description = "api/entregador"
    )
    @PostMapping // Define o método HTTP POST para este endpoint
    public ResponseEntity<Entregador> save(@RequestBody @Valid EntregadorRequest request) {
        // Constrói e salva o entregador usando o serviço
        Entregador entregador = entregadorService.save(request.build());
        // Retorna uma resposta HTTP 201 (Criado) com os dados do entregador
        return new ResponseEntity<Entregador>(entregador, HttpStatus.CREATED);
    }

    // Serviço responsável por pegar os dados de todos os entregadores do sistema
    @Operation(
        summary = "Serviço responsável por pegar os dados de todos os entregadores do sistema.",
        description = "api/entregador"
    )
    @GetMapping // Define o método HTTP GET para este endpoint
    public List<Entregador> listarTodos() {
        // Retorna a lista de todos os entregadores
        return entregadorService.listarTodos();
    }

    // Serviço responsável por pegar os dados de um entregador específico
    @Operation(
        summary = "Serviço responsável por pegar os dados de um entregador específico",
        description = "api/entregador/id"
    )
    @GetMapping("/{id}") // Define o método HTTP GET para este endpoint com um parâmetro de caminho (id)
    public Entregador obterPorID(@PathVariable long id) {
        // Retorna os dados do entregador com o id informado
        return entregadorService.obterPorID(id);
    }

    // Serviço responsável por editar os dados de um entregador específico
    @Operation(
        summary = "Serviço responsável por editar os dados de um entregador específico",
        description = "api/entregador/id"
    )
    @PutMapping("/{id}") // Define o método HTTP PUT para este endpoint com um parâmetro de caminho (id)
    public ResponseEntity<Entregador> update(@PathVariable("id") Long id, @RequestBody EntregadorRequest request) {
        // Atualiza os dados do entregador com o id informado
        entregadorService.update(id, request.build());
        // Retorna uma resposta HTTP 200 (OK) após a atualização
        return ResponseEntity.ok().build();
    }

    // Serviço responsável por deletar os dados de um entregador específico
    @Operation(
        summary = "Serviço responsável por deletar os dados de um entregador específico",
        description = "api/entregador/id"
    )
    @DeleteMapping("/{id}") // Define o método HTTP DELETE para este endpoint com um parâmetro de caminho (id)
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        // Deleta o entregador com o id informado
        entregadorService.delete(id);
        // Retorna uma resposta HTTP 200 (OK) após a exclusão
        return ResponseEntity.ok().build();
    }
}
