package br.com.ifpe.oxefood.api.produto;

import org.hibernate.validator.constraints.Length; // Importa a anotação Length para validar o tamanho de strings

import br.com.ifpe.oxefood.modelo.produto.Produto; // Importa o modelo de Produto
import jakarta.validation.constraints.NotBlank; // Importa a anotação NotBlank para garantir que o campo não seja nulo ou vazio
import lombok.AllArgsConstructor; // Gera um construtor com todos os argumentos
import lombok.Builder; // Permite a criação de objetos usando o padrão Builder
import lombok.Data; // Gera automaticamente getters, setters, equals, hashCode e toString
import lombok.NoArgsConstructor; // Gera um construtor sem argumentos

@Data // Gera os métodos getters e setters automaticamente
@Builder // Permite a construção do objeto ProdutoRequest de forma fluída
@NoArgsConstructor // Gera o construtor sem argumentos
@AllArgsConstructor // Gera o construtor com todos os argumentos
public class ProdutoRequest {

    private Long idCategoria; // ID da categoria à qual o produto pertence

    @NotBlank(message = "O Código é de preenchimento obrigatório") // Valida que o código não seja vazio
    private String codigo; // Código do produto

    @NotBlank(message = "O Título é de preenchimento obrigatório") // Valida que o título não seja vazio
    @Length(max = 100, message = "O título deverá ter no máximo {max} caracteres") // Valida o comprimento do título
    private String titulo; // Título do produto

    @NotBlank(message = "A Descrição do produto é de preenchimento obrigatório") // Valida que a descrição não seja vazia
    private String descricao; // Descrição do produto

    private Double valorUnitario; // Preço unitário do produto

    private Integer tempoEntregaMinimo; // Tempo mínimo de entrega (em minutos)

    private Integer tempoEntregaMaximo; // Tempo máximo de entrega (em minutos)

    // Método que converte a requisição para o modelo Produto
    public Produto build() {
        return Produto.builder()
            .codigo(codigo) // Atribui o código do produto
            .titulo(titulo) // Atribui o título do produto
            .descricao(descricao) // Atribui a descrição do produto
            .valorUnitario(valorUnitario) // Atribui o valor unitário do produto
            .tempoEntregaMinimo(tempoEntregaMinimo) // Atribui o tempo mínimo de entrega
            .tempoEntregaMaximo(tempoEntregaMaximo) // Atribui o tempo máximo de entrega
            .build(); // Retorna um objeto Produto construído com os dados fornecidos
    }
}
