package br.com.ifpe.oxefood.api.entregador;

// Importações necessárias para validações e formatação
import java.time.LocalDate; // Para trabalhar com datas
import org.hibernate.validator.constraints.Length; // Para validação do comprimento de strings
import org.hibernate.validator.constraints.br.CPF; // Para validar o CPF

import com.fasterxml.jackson.annotation.JsonFormat; // Para formatar a data corretamente durante a serialização
import br.com.ifpe.oxefood.modelo.entregador.Entregador; // Modelo de dados do entregador
import jakarta.validation.constraints.NotBlank; // Validação para garantir que o campo não esteja em branco
import jakarta.validation.constraints.NotEmpty; // Validação para garantir que o campo não esteja vazio
import jakarta.validation.constraints.NotNull; // Validação para garantir que o campo não seja nulo
import jakarta.validation.constraints.Past; // Validação para garantir que a data seja passada
import lombok.AllArgsConstructor; // Gera um construtor com todos os parâmetros
import lombok.Builder; // Para implementar o padrão de projeto Builder
import lombok.Data; // Gera getters, setters, equals, hashCode, toString
import lombok.NoArgsConstructor; // Gera um construtor sem parâmetros

@Data // Anotação Lombok para criar automaticamente getters e setters
@Builder // Anotação Lombok para implementar o padrão de projeto Builder
@NoArgsConstructor // Gera um construtor sem parâmetros
@AllArgsConstructor // Gera um construtor com todos os parâmetros
public class EntregadorRequest {

    // Validações para o nome do entregador
    @NotNull(message = "O Nome é de preenchimento obrigatório")
    @NotEmpty(message = "O Nome é de preenchimento obrigatório")
    @Length(max = 100, message = "O Nome deverá ter no máximo {max} caracteres")
    private String nome;

    // Validação para o CPF (campo obrigatório e válido)
    @NotBlank(message = "O CPF é de preenchimento obrigatório")
    @CPF // Valida se o CPF é válido
    private String cpf;

    // Validação para o RG (campo obrigatório)
    @NotBlank(message = "O RG é de preenchimento obrigatório")
    private String rg;

    // Validação para a data de nascimento (campo obrigatório e deve ser uma data passada)
    @Past
    @JsonFormat(pattern = "dd/MM/yyyy") // Define o formato da data durante a serialização/deserialização
    private LocalDate dataNascimento;

    // Validação para o campo Fone Celular (tamanho mínimo e máximo de caracteres)
    @Length(min = 8, max = 20, message = "O campo Fone Celular tem que ter entre {min} e {max} caracteres")
    private String foneCelular;

    // Validação para o campo Fone Fixo (tamanho mínimo e máximo de caracteres)
    @Length(min = 8, max = 20, message = "O campo Fone Fixo tem que ter entre {min} e {max} caracteres")
    private String foneFixo;

    // Quantidade de entregas realizadas
    private Integer qtdEntregasRealizadas;

    // Valor do frete
    private Double valorFrete;

    // Endereço do entregador
    @NotBlank(message = "A rua é de preenchimento obrigatório")
    private String enderecoRua;

    // Complemento do endereço (campo opcional)
    private String enderecoComplemento;

    // Número da residência (campo obrigatório)
    @NotBlank(message = "O número da residência é de preenchimento obrigatório")
    private String enderecoNumero;

    // Bairro do entregador (campo obrigatório)
    @NotBlank(message = "O Bairro é de preenchimento obrigatório")
    private String enderecoBairro;

    // Cidade do entregador (campo obrigatório)
    @NotBlank(message = "A Cidade é de preenchimento obrigatório")
    private String enderecoCidade;

    // CEP do entregador (campo obrigatório)
    @NotBlank(message = "O CEP é de preenchimento obrigatório")
    private String enderecoCep;

    // Estado do entregador (campo obrigatório)
    @NotBlank(message = "O Estado é de preenchimento obrigatório")
    private String enderecoUf;

    // Indicador de se o entregador está ativo
    private Boolean ativo;

    // Método que converte os dados recebidos em um objeto Entregador
    public Entregador build() {
        return Entregador.builder()
                .nome(nome)
                .cpf(cpf)
                .rg(rg)
                .dataNascimento(dataNascimento)
                .foneCelular(foneCelular)
                .foneFixo(foneFixo)
                .qtdEntregasRealizadas(qtdEntregasRealizadas)
                .valorFrete(valorFrete)
                .enderecoRua(enderecoRua)
                .enderecoComplemento(enderecoComplemento)
                .enderecoNumero(enderecoNumero)
                .enderecoBairro(enderecoBairro)
                .enderecoCidade(enderecoCidade)
                .enderecoCep(enderecoCep)
                .enderecoUf(enderecoUf)
                .ativo(ativo)
                .build();
    }
}
