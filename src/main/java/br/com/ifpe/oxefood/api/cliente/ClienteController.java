package br.com.ifpe.oxefood.api.cliente;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired; // Para injeção automática de dependências
import org.springframework.http.HttpStatus; // Para definir status HTTP nas respostas
import org.springframework.http.ResponseEntity; // Para construir respostas HTTP com status
import org.springframework.web.bind.annotation.CrossOrigin; // Permite chamadas de origens diferentes (CORS)
import org.springframework.web.bind.annotation.DeleteMapping; // Para mapeamento de requisição DELETE
import org.springframework.web.bind.annotation.GetMapping; // Para mapeamento de requisição GET
import org.springframework.web.bind.annotation.PathVariable; // Para capturar variáveis no caminho da URL
import org.springframework.web.bind.annotation.PostMapping; // Para mapeamento de requisição POST
import org.springframework.web.bind.annotation.PutMapping; // Para mapeamento de requisição PUT
import org.springframework.web.bind.annotation.RequestBody; // Para capturar o corpo da requisição
import org.springframework.web.bind.annotation.RequestMapping; // Para definir o caminho base para os endpoints
import org.springframework.web.bind.annotation.RequestParam; // Para capturar parâmetros de requisição
import org.springframework.web.bind.annotation.RestController; // Indica que a classe é um controlador REST

// Anotações de documentação da API usando Swagger
import io.swagger.v3.oas.annotations.Operation; // Para descrever cada operação na API
import io.swagger.v3.oas.annotations.tags.Tag; // Para adicionar tags à documentação da API

// Importação dos serviços e modelos necessários
import br.com.ifpe.oxefood.modelo.acesso.UsuarioService; // Para acessar o serviço do usuário
import br.com.ifpe.oxefood.modelo.cliente.Cliente; // Modelo de dados de cliente
import br.com.ifpe.oxefood.modelo.cliente.ClienteService; // Serviço para operações de cliente
import br.com.ifpe.oxefood.modelo.cliente.EnderecoCliente; // Modelo de dados de endereço de cliente
import br.com.ifpe.oxefood.modelo.produto.Produto; // Modelo de produto (não usado diretamente aqui, mas pode ser necessário)

import jakarta.servlet.http.HttpServletRequest; // Para acessar a requisição HTTP (por exemplo, para obter o usuário logado)
import jakarta.validation.Valid; // Para validar os dados recebidos nas requisições

// Definindo o controlador REST para os endpoints de clientes
@RestController
@RequestMapping("/api/cliente") // Caminho base para todas as operações de cliente
@CrossOrigin // Permite requisições de diferentes origens
@Tag(
    name = "API Cliente", // Nome da API
    description = "API responsável pelos serviços de cliente no sistema" // Descrição da API para documentação
)

public class ClienteController {

    // Injeção automática dos serviços necessários
    @Autowired
    private ClienteService clienteService;

    @Autowired
    private UsuarioService usuarioService;

    // Endpoint para salvar um novo cliente
    @Operation(
        summary = "Serviço responsável por salvar um cliente no sistema.", 
        description = "api/cliente"
    ) 
    @PostMapping // Mapeia requisições POST para esse método
    public ResponseEntity<Cliente> save(@RequestBody @Valid ClienteRequest clienteRequest, HttpServletRequest request) {
        // Cria o cliente no sistema, associando o usuário logado
        Cliente cliente = clienteService.save(clienteRequest.build(), usuarioService.obterUsuarioLogado(request));
        // Retorna o cliente criado com o status HTTP 201 (Criado)
        return new ResponseEntity<Cliente>(cliente, HttpStatus.CREATED);
    }

    // Endpoint para listar todos os clientes
    @Operation(
        summary = "Serviço responsável por pegar os dados de todos os clientes no sistema.", 
        description = "api/cliente"
    )
    @GetMapping // Mapeia requisições GET para esse método
    public List<Cliente> listarTodos() {
        return clienteService.listarTodos(); // Retorna todos os clientes
    }

    // Endpoint para obter os dados de um cliente específico
    @Operation(
        summary = "Serviço responsável por pegar os dados de um cliente no sistema.", 
        description = "api/cliente/id"
    ) 
    @GetMapping("/{id}") // Mapeia requisições GET para esse método, usando o ID do cliente
    public Cliente obterPorID(@PathVariable Long id) {
        return clienteService.obterPorID(id); // Retorna o cliente com o ID fornecido
    }

    // Endpoint para atualizar os dados de um cliente
    @Operation(
        summary = "Serviço responsável por editar um cliente no sistema.", 
        description = "api/cliente/id"
    )
    @PutMapping("/{id}") // Mapeia requisições PUT para esse método, usando o ID do cliente
    public ResponseEntity<Cliente> update(@PathVariable("id") Long id,
            @RequestBody @Valid ClienteRequest clienteRequest, HttpServletRequest request) {
        // Atualiza o cliente com os dados fornecidos
        clienteService.update(id, clienteRequest.build(), usuarioService.obterUsuarioLogado(request));
        return ResponseEntity.ok().build(); // Retorna uma resposta com status HTTP 200 (OK)
    }

    // Endpoint para excluir um cliente
    @Operation(
        summary = "Serviço responsável por deletar um cliente no sistema.", 
        description = "api/cliente/id"
    )
    @DeleteMapping("/{id}") // Mapeia requisições DELETE para esse método, usando o ID do cliente
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        // Exclui o cliente com o ID fornecido
        clienteService.delete(id);
        return ResponseEntity.ok().build(); // Retorna uma resposta com status HTTP 200 (OK)
    }

    // Endpoint para obter todos os endereços de um cliente
    @Operation(
        summary = "Serviço responsável por pegar todos os endereços de um cliente no sistema.", 
        description = "api/cliente/endereco/clienteid"
    )
    @GetMapping("/endereco/{clienteId}") // Mapeia requisições GET para esse método, usando o ID do cliente
    public EnderecoCliente obterEnderecoPorID(@PathVariable("clienteId") Long clienteId) {
        return clienteService.obterEnderecoPorID(clienteId); // Retorna os endereços do cliente com o ID fornecido
    }

    // Endpoint para cadastrar um novo endereço vinculado a um cliente
    @Operation(
        summary = "Serviço responsável por cadastrar endereço vinculado a um cliente no sistema.", 
        description = "api/cliente/endereco/clienteid"
    )
    @PostMapping("/endereco/{clienteId}") // Mapeia requisições POST para esse método, usando o ID do cliente
    public ResponseEntity<EnderecoCliente> adicionarEnderecoCliente(@PathVariable("clienteId") Long clienteId,
            @RequestBody @Valid EnderecoClienteRequest request) {
        // Cria o endereço para o cliente com o ID fornecido
        EnderecoCliente endereco = clienteService.adicionarEnderecoCliente(clienteId, request.build());
        return new ResponseEntity<EnderecoCliente>(endereco, HttpStatus.CREATED); // Retorna o endereço criado com status HTTP 201 (Criado)
    }

    // Endpoint para atualizar um endereço de cliente
    @Operation(
        summary = "Serviço responsável por editar um endereço específico", 
        description = "api/cliente/endereco/enderecoid"
    )
    @PutMapping("/endereco/{enderecoId}") // Mapeia requisições PUT para esse método, usando o ID do endereço
    public ResponseEntity<EnderecoCliente> atualizarEnderecoCliente(@PathVariable("enderecoId") Long enderecoId,
            @RequestBody EnderecoClienteRequest request) {
        // Atualiza o endereço do cliente com o ID fornecido
        EnderecoCliente endereco = clienteService.atualizarEnderecoCliente(enderecoId, request.build());
        return new ResponseEntity<EnderecoCliente>(endereco, HttpStatus.OK); // Retorna o endereço atualizado com status HTTP 200 (OK)
    }

    // Endpoint para excluir um endereço de cliente
    @Operation(
        summary = "Serviço responsável por delatar um endereço específico", 
        description = "api/cliente/endereco/enderecoid"
    )
    @DeleteMapping("/endereco/{enderecoId}") // Mapeia requisições DELETE para esse método, usando o ID do endereço
    public ResponseEntity<Void> removerEnderecoCliente(@PathVariable("enderecoId") Long enderecoId) {
        // Exclui o endereço com o ID fornecido
        clienteService.removerEnderecoCliente(enderecoId);
        return ResponseEntity.noContent().build(); // Retorna uma resposta sem conteúdo, mas com status HTTP 204 (No Content)
    }

    // Endpoint para filtrar clientes por nome ou CPF
    @Operation(
        summary = "Serviço responsável por filtrar dados por cliente ou cpf", 
        description = "api/cliente/filtrar/"
    )
    @PostMapping("/filtrar") // Mapeia requisições POST para esse método
    public List<Cliente> filtrar(
            @RequestParam(value = "nome", required = false) String nome, // Parâmetro de nome opcional
            @RequestParam(value = "cpf", required = false) String cpf) { // Parâmetro de CPF opcional

        return clienteService.filtrar(nome, cpf); // Retorna os clientes filtrados conforme os parâmetros fornecidos
    }
    
}