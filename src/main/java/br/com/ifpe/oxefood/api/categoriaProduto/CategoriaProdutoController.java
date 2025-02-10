package br.com.ifpe.oxefood.api.categoriaProduto;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired; // Importa a anotação para injeção automática de dependências
import org.springframework.http.HttpStatus; // Importa o status HTTP para configurar respostas
import org.springframework.http.ResponseEntity; // Importa a classe para construir respostas HTTP com status
import org.springframework.web.bind.annotation.CrossOrigin; // Importa a anotação para habilitar requisições de diferentes origens
import org.springframework.web.bind.annotation.DeleteMapping; // Importa a anotação para tratar requisições DELETE
import org.springframework.web.bind.annotation.GetMapping; // Importa a anotação para tratar requisições GET
import org.springframework.web.bind.annotation.PathVariable; // Importa a anotação para capturar variáveis de caminho na URL
import org.springframework.web.bind.annotation.PostMapping; // Importa a anotação para tratar requisições POST
import org.springframework.web.bind.annotation.PutMapping; // Importa a anotação para tratar requisições PUT
import org.springframework.web.bind.annotation.RequestBody; // Importa a anotação para capturar o corpo da requisição
import org.springframework.web.bind.annotation.RequestMapping; // Importa a anotação para mapear requisições a um endpoint específico
import org.springframework.web.bind.annotation.RestController; // Define que essa classe é um controlador REST (API)

// Importação dos modelos e serviços necessários
import br.com.ifpe.oxefood.modelo.categoriaProduto.CategoriaProduto;
import br.com.ifpe.oxefood.modelo.categoriaProduto.CategoriaProdutoService;
import jakarta.validation.Valid; // Importa a anotação para garantir a validação dos dados no corpo da requisição
import io.swagger.v3.oas.annotations.Operation; // Importa a anotação para documentar a API com Swagger
import io.swagger.v3.oas.annotations.tags.Tag; // Importa a anotação para organizar a documentação da API

// Definindo o controlador REST para as categorias de produto
@RestController
@RequestMapping("/api/categoriaproduto") // Define o mapeamento da URL base para as requisições
@CrossOrigin // Permite que a API receba requisições de diferentes origens (cross-origin)
@Tag(name = "API Categoria Produto", description = "API responsável pelos serviços de categoria de produto no sistema") // Documentação do Swagger

public class CategoriaProdutoController {

    // Injeção automática do serviço que gerencia as operações de categoria de produto
    @Autowired
    private CategoriaProdutoService categoriaProdutoService;

    // Serviço responsável por salvar uma nova categoria de produto no sistema
    @Operation(summary = "Serviço responsável por salvar uma categoria de produto no sistema.", description = "/api/categoriaproduto")
    @PostMapping // Mapeia requisições POST para esse método
    public ResponseEntity<CategoriaProduto> save(@RequestBody @Valid CategoriaProdutoRequest request) {

        // Constrói a nova categoria de produto a partir da requisição
        CategoriaProduto categoriaProdutoNovo = request.build();
        
        // Salva a nova categoria de produto no banco de dados
        CategoriaProduto categoriaProduto = categoriaProdutoService.save(categoriaProdutoNovo);
        
        // Retorna a resposta com o código de status 201 (Criado) e o objeto da categoria de produto criada
        return new ResponseEntity<CategoriaProduto>(categoriaProduto, HttpStatus.CREATED);
    }

    // Serviço responsável por listar todas as categorias de produto cadastradas no sistema
    @Operation(summary = "Serviço responsável por pegar todas as categorias de produto cadastradas no sistema.", description = "api/categoriaproduto")
    @GetMapping // Mapeia requisições GET para esse método
    public List<CategoriaProduto> listarTodos() {
        return categoriaProdutoService.listarTodos(); // Retorna todas as categorias de produto
    }

    // Serviço responsável por pegar os dados de uma categoria de produto específica, identificada pelo ID
    @Operation(summary = "Serviço responsável por pegar os dados de uma categoria de produto em específico.", description = "api/categoriaproduto/id")
    @GetMapping("/{id}") // Mapeia requisições GET com o ID da categoria na URL
    public CategoriaProduto obterPorID(@PathVariable Long id) {
        return categoriaProdutoService.obterPorID(id); // Retorna a categoria de produto correspondente ao ID
    }

    // Serviço responsável por editar os dados de uma categoria de produto específica, identificada pelo ID
    @Operation(summary = "Serviço responsável por editar os dados de uma categoria de produto em específico.", description = "api/categoriaproduto/id")
    @PutMapping("/{id}") // Mapeia requisições PUT para editar dados de uma categoria específica
    public ResponseEntity<CategoriaProduto> update(@PathVariable("id") Long id,
            @RequestBody CategoriaProdutoRequest request) {

        // Atualiza a categoria de produto com o ID fornecido e os dados da requisição
        categoriaProdutoService.update(id, request.build());
        
        // Retorna uma resposta com código de sucesso (200 OK)
        return ResponseEntity.ok().build();
    }

    // Serviço responsável por deletar uma categoria de produto específica, identificada pelo ID
    @Operation(summary = "Serviço responsável por deletar os dados de uma categoria de produto em específico.", description = "api/categoriaproduto/id")
    @DeleteMapping("/{id}") // Mapeia requisições DELETE para remover a categoria com o ID fornecido
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        // Deleta a categoria de produto com o ID fornecido
        categoriaProdutoService.delete(id);
        
        // Retorna uma resposta com código de sucesso (200 OK) sem corpo
        return ResponseEntity.ok().build();
    }
}
