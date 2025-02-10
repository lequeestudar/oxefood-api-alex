package br.com.ifpe.oxefood.api.cliente;
// Importação das classes do Lombok para geração automática de código
import br.com.ifpe.oxefood.modelo.cliente.EnderecoCliente; // Importa o modelo de EnderecoCliente
import lombok.AllArgsConstructor; // Gera um construtor com todos os campos da classe
import lombok.Builder; // Gera o padrão Builder, facilitando a construção de objetos dessa classe
import lombok.Data; // Gera os métodos getters, setters, equals, hashCode, toString automaticamente
import lombok.NoArgsConstructor; // Gera um construtor sem parâmetros

@Data // Cria automaticamente os métodos necessários como getters, setters, etc.
@Builder // Gera o padrão Builder para construção de instâncias dessa classe
@NoArgsConstructor // Gera o construtor sem parâmetros
@AllArgsConstructor // Gera o construtor com todos os parâmetros
public class EnderecoClienteRequest {

   // Atributos que representam as informações do endereço do cliente
   private String rua; // Nome da rua do endereço
   private String numero; // Número do endereço (ex: número da casa ou apartamento)
   private String bairro; // Bairro onde o cliente reside
   private String cep; // Código Postal do endereço
   private String cidade; // Cidade onde o cliente reside
   private String estado; // Estado (UF) onde o cliente reside
   private String complemento; // Complemento do endereço (ex: apto, bloco, etc.)

   // Método que cria um objeto EnderecoCliente a partir dos dados fornecidos
   public EnderecoCliente build() {
       // Utiliza o padrão Builder para construir um objeto EnderecoCliente
       return EnderecoCliente.builder()
               .rua(rua) // Atribui o valor da rua
               .numero(numero) // Atribui o valor do número
               .bairro(bairro) // Atribui o valor do bairro
               .cep(cep) // Atribui o valor do CEP
               .cidade(cidade) // Atribui o valor da cidade
               .estado(estado) // Atribui o valor do estado
               .complemento(complemento) // Atribui o valor do complemento
               .build(); // Cria e retorna o objeto EnderecoCliente
   }

}
