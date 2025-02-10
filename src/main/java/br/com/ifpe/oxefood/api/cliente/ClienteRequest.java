package br.com.ifpe.oxefood.api.cliente;
// Importação de classes para validação e manipulação de dados
import java.time.LocalDate; // Para manipulação de datas, como data de nascimento
import java.util.Arrays; // Para converter uma lista de perfis em array
import org.hibernate.validator.constraints.Length; // Para validação de comprimento de strings
import org.hibernate.validator.constraints.br.CPF; // Para validar o CPF
import jakarta.validation.constraints.Email; // Para validar o formato de e-mail
import jakarta.validation.constraints.NotBlank; // Para validar que o campo não está vazio
import jakarta.validation.constraints.NotNull; // Para validar que o campo não é nulo
import jakarta.validation.constraints.Past; // Para validar se a data está no passado
import jakarta.validation.constraints.NotEmpty; // Para garantir que o campo não está vazio
import com.fasterxml.jackson.annotation.JsonFormat; // Para formatar a data no formato desejado
import br.com.ifpe.oxefood.modelo.acesso.Perfil; // Importação do modelo de perfil de usuário
import br.com.ifpe.oxefood.modelo.acesso.Usuario; // Importação do modelo de usuário
import br.com.ifpe.oxefood.modelo.cliente.Cliente; // Importação do modelo de cliente
import lombok.AllArgsConstructor; // Anotação Lombok para criar um construtor com todos os campos
import lombok.Builder; // Anotação Lombok para criar um padrão Builder
import lombok.Data; // Anotação Lombok para gerar os métodos getters, setters, equals, hashcode, etc.
import lombok.NoArgsConstructor; // Anotação Lombok para criar um construtor sem parâmetros

@Data // Gera os métodos getters, setters, equals, hashCode, toString automaticamente
@Builder // Gera o padrão Builder para facilitar a construção do objeto
@NoArgsConstructor // Gera um construtor sem parâmetros
@AllArgsConstructor // Gera um construtor com todos os parâmetros
public class ClienteRequest {

    // Validação para garantir que o e-mail não seja em branco e tenha o formato correto
    @NotBlank(message = "O e-mail é de preenchimento obrigatório")
    @Email
    private String email;

    // Validação para garantir que a senha não seja em branco
    @NotBlank(message = "A senha é de preenchimento obrigatório")
    private String password;

    // Validações para garantir que o nome do cliente não seja nulo nem vazio
    @NotNull(message = "O Nome é de preenchimento obrigatório")
    @NotEmpty(message = "O Nome é de preenchimento obrigatório")
    @Length(max = 100, message = "O Nome deverá ter no máximo {max} caracteres")
    private String nome;
    
    // Valida a data de nascimento para garantir que seja uma data no passado
    @Past
    @JsonFormat(pattern = "dd/MM/yyyy") // Define o formato de exibição da data
    private LocalDate dataNascimento;

    // Validação para garantir que o CPF não seja em branco e tenha o formato correto
    @NotBlank(message = "O CPF é de preenchimento obrigatório")
    @CPF
    private String cpf;

    // Validação para o comprimento do campo de celular (entre 8 e 20 caracteres)
    @Length(min = 8, max = 20, message = "O campo Fone Celular tem que ter entre {min} e {max} caracteres")
    private String foneCelular;

    // Validação para o comprimento do campo de telefone fixo (entre 8 e 20 caracteres)
    @Length(min = 8, max = 20, message = "O campo Fone Fixo tem que ter entre {min} e {max} caracteres")
    private String foneFixo;

    // Método que cria um objeto Usuario a partir das informações do cliente
    // Este usuário terá o e-mail como username, a senha fornecida e o perfil 'ROLE_CLIENTE'
    public Usuario buildUsuario() {
       return Usuario.builder()
           .username(email) // Usa o e-mail como nome de usuário
           .password(password) // A senha fornecida
           .roles(Arrays.asList(new Perfil(Perfil.ROLE_CLIENTE))) // Atribui o perfil de cliente
           .build(); // Cria o objeto Usuario
   }

    // Método que cria um objeto Cliente com as informações fornecidas

    public Cliente build() {

        return Cliente.builder()
                .usuario(buildUsuario()) // Associa o usuário ao cliente
                .nome(nome) // Nome do cliente
                .dataNascimento(dataNascimento) // Data de nascimento do cliente
                .cpf(cpf) // CPF do cliente
                .foneCelular(foneCelular) // Número de celular do cliente
                .foneFixo(foneFixo) // Número de telefone fixo do cliente
                .build(); // Cria o objeto Cliente
    }
}
