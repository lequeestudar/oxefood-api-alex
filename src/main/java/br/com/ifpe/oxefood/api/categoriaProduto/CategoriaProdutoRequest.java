package br.com.ifpe.oxefood.api.categoriaProduto;

// Importação da classe modelo CategoriaProduto para usá-la ao construir o objeto
import br.com.ifpe.oxefood.modelo.categoriaProduto.CategoriaProduto;

// Importação de anotações de validação e do Lombok
import jakarta.validation.constraints.NotBlank; // Valida que o campo não pode ser em branco
import lombok.AllArgsConstructor; // Gera um construtor com todos os parâmetros
import lombok.Builder; // Gera o padrão Builder para a classe
import lombok.Data; // Gera getters, setters, toString, equals, e hashCode
import lombok.NoArgsConstructor; // Gera o construtor sem parâmetros

// Anotações do Lombok para automação de código boilerplate
@Data // Gera os métodos getters, setters, toString(), equals() e hashCode() automaticamente
@Builder // Habilita o padrão de projeto Builder para a criação de instâncias dessa classe
@NoArgsConstructor // Gera o construtor sem parâmetros
@AllArgsConstructor // Gera o construtor com todos os parâmetros
public class CategoriaProdutoRequest {

    // Define que a descrição não pode ser em branco e personaliza a mensagem de erro
    @NotBlank(message = "A descrição é de preenchimento obrigatório")
    private String descricao; // Atributo que representa a descrição da categoria do produto

    // Método build que converte a requisição (CategoriaProdutoRequest) em um objeto CategoriaProduto
    public CategoriaProduto build() {

        // Usando o padrão Builder para construir e retornar uma nova instância de CategoriaProduto
        return CategoriaProduto.builder()
                .descricao(descricao) // Atribui a descrição à nova categoria de produto
                .build(); // Constrói o objeto CategoriaProduto
    }
}
