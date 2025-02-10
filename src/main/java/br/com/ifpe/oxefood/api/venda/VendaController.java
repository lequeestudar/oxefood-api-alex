package br.com.ifpe.oxefood.api.venda;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired; // Importa a anotação para injeção de dependências
import org.springframework.http.HttpStatus; // Importa os códigos de status HTTP
import org.springframework.http.ResponseEntity; // Importa a classe para construir respostas HTTP
import org.springframework.web.bind.annotation.CrossOrigin; // Importa a anotação para permitir o acesso de diferentes origens
import org.springframework.web.bind.annotation.DeleteMapping; // Importa a anotação para realizar a operação de DELETE
import org.springframework.web.bind.annotation.GetMapping; // Importa a anotação para realizar a operação de GET
import org.springframework.web.bind.annotation.PathVariable; // Importa a anotação para extrair valores da URL
import org.springframework.web.bind.annotation.PostMapping; // Importa a anotação para realizar a operação de POST
import org.springframework.web.bind.annotation.PutMapping; // Importa a anotação para realizar a operação de PUT
import org.springframework.web.bind.annotation.RequestBody; // Importa a anotação para mapear o corpo da requisição
import org.springframework.web.bind.annotation.RequestMapping; // Importa a anotação para definir a URL base
import org.springframework.web.bind.annotation.RestController; // Importa a anotação para marcar a classe como um controlador REST
import br.com.ifpe.oxefood.modelo.venda.Venda; // Importa o modelo de Venda
import br.com.ifpe.oxefood.modelo.venda.VendaService; // Importa o serviço responsável pela lógica de vendas

@RestController // Marca a classe como um controlador REST
@RequestMapping("/api/venda") // Define a URL base da API
@CrossOrigin // Permite o acesso à API de diferentes origens (CORS)
public class VendaController {

    @Autowired // Injeção de dependência para o serviço de vendas
    private VendaService vendaService;

    // Método responsável por salvar uma nova venda
    @PostMapping
    public ResponseEntity<Venda> save(@RequestBody VendaRequest request) {
        // Converte a requisição (DTO) em um objeto Venda e salva no banco de dados
        Venda venda = vendaService.save(request.build());
        // Retorna a venda salva com status HTTP 201 (Criado)
        return new ResponseEntity<Venda>(venda, HttpStatus.CREATED);
    }

    // Método responsável por listar todas as vendas
    @GetMapping
    public List<Venda> listarTodos() {
        // Retorna a lista de todas as vendas
        return vendaService.listarTodos();
    }

    // Método responsável por obter uma venda específica pelo ID
    @GetMapping("/{id}")
    public Venda obterPorID(@PathVariable Long id) {
        // Retorna a venda com o ID fornecido
        return vendaService.obterPorID(id);
    }

    // Método responsável por atualizar os dados de uma venda
    @PutMapping("/{id}")
    public ResponseEntity<Venda> update(@PathVariable("id") Long id, @RequestBody VendaRequest request) {
        // Atualiza a venda com o ID fornecido com os novos dados
        vendaService.update(id, request.build());
        // Retorna uma resposta HTTP 200 (OK) após a atualização
        return ResponseEntity.ok().build();
    }

    // Método responsável por deletar uma venda
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        // Deleta a venda com o ID fornecido
        vendaService.delete(id);
        // Retorna uma resposta HTTP 200 (OK) após a deleção
        return ResponseEntity.ok().build();
    }
}
