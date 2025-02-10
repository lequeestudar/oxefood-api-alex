package br.com.ifpe.oxefood.api.produto;

import java.util.List; // Importa a classe List para trabalhar com listas de produtos

import org.springframework.beans.factory.annotation.Autowired; // Importa a anotação Autowired para injeção de dependências
import org.springframework.http.HttpStatus; // Importa os status HTTP
import org.springframework.http.ResponseEntity; // Importa a classe ResponseEntity para respostas HTTP com status
import org.springframework.web.bind.annotation.CrossOrigin; // Habilita CORS (Cross-Origin Resource Sharing)
import org.springframework.web.bind.annotation.DeleteMapping; // Mapeia o método HTTP DELETE
import org.springframework.web.bind.annotation.GetMapping; // Mapeia o método HTTP GET
import org.springframework.web.bind.annotation.PathVariable; // Acessa o valor das variáveis da URL (ex: /{id})
import org.springframework.web.bind.annotation.PostMapping; // Mapeia o método HTTP POST
import org.springframework.web.bind.annotation.PutMapping; // Mapeia o método HTTP PUT
import org.springframework.web.bind.annotation.RequestBody; // Permite obter o corpo da requisição
import org.springframework.web.bind.annotation.RequestMapping; // Define a URL base para o controlador
import org.springframework.web.bind.annotation.RequestParam; // Permite obter parâmetros de requisição
import org.springframework.web.bind.annotation.RestController; // Marca a classe como um controlador REST

import br.com.ifpe.oxefood.modelo.categoriaProduto.CategoriaProdutoService; // Importa o serviço de categoria de produto
import br.com.ifpe.oxefood.modelo.produto.Produto; // Importa o modelo de produto
import br.com.ifpe.oxefood.modelo.produto.ProdutoService; // Importa o serviço de produto

import jakarta.validation.Valid; // Valida as requisições para garantir que os dados estão corretos
import io.swagger.v3.oas.annotations.Operation; // Anotações para documentação da API usando Swagger
import io.swagger.v3.oas.annotations.tags.Tag; // Anotação para categorização na documentação Swagger

// Define a classe como um controlador REST
@RestController
@RequestMapping("/api/produto") // Define a URL base para os endpoints da API de produtos
@CrossOrigin // Habilita o compartilhamento de recursos entre origens diferentes
@Tag(name = "API Produto", description = "API responsável pelos serviços de produto no sistema") // Descrição da API para Swagger
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService; // Serviço para manipulação de produtos no banco de dados

    @Autowired
    private CategoriaProdutoService categoriaProdutoService; // Serviço para manipulação de categorias de produtos

    // Serviço para salvar um novo produto
    @Operation(summary = "Serviço responsável por salvar um produto no sistema.", description = "api/produto") // Documentação da operação para Swagger
    @PostMapping // Define que este método responde a requisições POST
    public ResponseEntity<Produto> save(@RequestBody @Valid ProdutoRequest request) {
        // Constrói um novo produto a partir da requisição recebida
        Produto produtoNovo = request.build();
        // Associa a categoria ao produto usando o ID da categoria informado
        produtoNovo.setCategoria(categoriaProdutoService.obterPorID(request.getIdCategoria()));
        // Salva o novo produto no banco de dados
        Produto produto = produtoService.save(produtoNovo);

        // Retorna o produto salvo com status HTTP 201 (Created)
        return new ResponseEntity<Produto>(produto, HttpStatus.CREATED);
    }

    // Serviço para listar todos os produtos cadastrados no sistema
    @Operation(summary = "Serviço responsável por pegar dados de todos os produtos no sistema.", description = "api/produto")
    @GetMapping // Define que este método responde a requisições GET
    public List<Produto> listarTodos() {
        // Retorna todos os produtos cadastrados
        return produtoService.listarTodos();
    }

    // Serviço para obter um produto específico a partir de seu ID
    @Operation(summary = "Serviço responsável por pegar dados de um produto específico", description = "api/produto/id")
    @GetMapping("/{id}") // Mapeia o GET com um ID de produto na URL
    public Produto obterPorID(@PathVariable Long id) {
        // Retorna o produto encontrado pelo ID
        return produtoService.obterPorID(id);
    }

    // Serviço para atualizar as informações de um produto específico
    @Operation(summary = "Serviço responsável por editar dados de um produto específico", description = "api/produto/id")
    @PutMapping("/{id}") // Define que este método responde a requisições PUT com um ID de produto na URL
    public ResponseEntity<Produto> update(@PathVariable("id") Long id, @RequestBody ProdutoRequest request) {
        // Constrói o produto a partir da requisição
        Produto produto = request.build();
        // Associa a categoria ao produto
        produto.setCategoria(categoriaProdutoService.obterPorID(request.getIdCategoria()));
        // Atualiza o produto no banco de dados
        produtoService.update(id, produto);

        // Retorna resposta de sucesso com status HTTP 200 (OK)
        return ResponseEntity.ok().build();
    }

    // Serviço para deletar um produto específico
    @Operation(summary = "Serviço responsável por deletar um produto específico", description = "api/produto/id")
    @DeleteMapping("/{id}") // Define que este método responde a requisições DELETE com um ID de produto na URL
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        // Deleta o produto com o ID fornecido
        produtoService.delete(id);

        // Retorna uma resposta vazia com status HTTP 200 (OK)
        return ResponseEntity.ok().build();
    }

    // Serviço para filtrar produtos com base em parâmetros opcionais
    @Operation(summary = "Serviço responsável por filtrar dados de um produto", description = "api/produto/filtrar")
    @PostMapping("/filtrar") // Define que este método responde a requisições POST
    public List<Produto> filtrar(
            @RequestParam(value = "codigo", required = false) String codigo, // Parâmetro opcional para filtrar pelo código do produto
            @RequestParam(value = "titulo", required = false) String titulo, // Parâmetro opcional para filtrar pelo título do produto
            @RequestParam(value = "idCategoria", required = false) Long idCategoria) { // Parâmetro opcional para filtrar pela categoria do produto

        // Retorna a lista de produtos que atendem aos critérios de filtragem
        return produtoService.filtrar(codigo, titulo, idCategoria);
    }
}
