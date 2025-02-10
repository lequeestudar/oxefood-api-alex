package br.com.ifpe.oxefood.api.funcionario;

import java.time.LocalDate; // Importa LocalDate para trabalhar com datas (data de nascimento do funcionário)

import com.fasterxml.jackson.annotation.JsonFormat; // Importa a anotação JsonFormat para controlar a formatação das datas na serialização/deserialização

import br.com.ifpe.oxefood.modelo.acesso.Usuario; // Importa o modelo de Usuario (relacionado ao login do funcionário)
import br.com.ifpe.oxefood.modelo.funcionario.Funcionario; // Importa o modelo Funcionario, que será preenchido com os dados da requisição
import br.com.ifpe.oxefood.modelo.funcionario.TipoFuncionario; // Importa o enum TipoFuncionario (por exemplo, ADMINISTRADOR ou OPERADOR)

import jakarta.persistence.EnumType; // Para configurar o tipo enum no banco de dados
import jakarta.persistence.Enumerated; // Anotação para persistir o tipo Enum no banco de dados
import jakarta.validation.constraints.NotBlank; // Validação que garante que o campo não estará em branco
import jakarta.validation.constraints.NotNull; // Validação que garante que o campo não estará nulo
import lombok.AllArgsConstructor; // Gera um construtor com todos os parâmetros
import lombok.Builder; // Permite criar o objeto usando o padrão builder
import lombok.Data; // Gera automaticamente os métodos getters, setters, equals, hashCode e toString
import lombok.NoArgsConstructor; // Gera o construtor sem parâmetros

@Data // Gera os métodos getter, setter, equals, hashCode e toString
@Builder // Permite criar um objeto FuncionarioRequest utilizando o padrão builder
@NoArgsConstructor // Gera um construtor sem parâmetros
@AllArgsConstructor // Gera um construtor com todos os parâmetros
public class FuncionarioRequest {

    // Campo para o tipo de funcionário, como ADMINISTRADOR ou OPERADOR
    @NotNull // Garante que o tipo não seja nulo
    @Enumerated(EnumType.STRING) // Armazena o tipo de funcionário como uma string no banco de dados
    private TipoFuncionario tipo;

    // Campo para o e-mail do funcionário (obrigatório)
    @NotBlank // Garante que o e-mail não seja em branco
    private String email;

    // Campo para a senha do funcionário (obrigatório)
    @NotBlank // Garante que a senha não seja em branco
    private String password;

    // Campo para o nome do funcionário (obrigatório)
    @NotBlank // Garante que o nome não seja em branco
    private String nome;

    // Campo para o CPF do funcionário (opcional)
    private String cpf;

    // Campo para o RG do funcionário (opcional)
    private String rg;

    // Campo para a data de nascimento do funcionário, com formato de data personalizado
    @JsonFormat(pattern = "dd/MM/yyyy") // Define o padrão de formatação da data durante a serialização/deserialização
    private LocalDate dataNascimento;

    // Campos para os números de telefone (celular e fixo)
    private String foneCelular;
    
    private String foneFixo;

    // Campo para o salário do funcionário
    private Double salario;

    // Campos para o endereço do funcionário
    private String enderecoRua;

    private String enderecoNumero;

    private String enderecoBairro;

    private String enderecoCidade;

    private String enderecoCep;

    private String enderecoUf;

    private String enderecoComplemento;

    // Método que cria um objeto Funcionario a partir dos dados fornecidos na requisição
    public Funcionario build() {

        // Usando o padrão Builder para construir o objeto Funcionario
        return Funcionario.builder()
                .usuario(buildUsuario()) // Associa um objeto Usuario ao funcionário (usuário é baseado no e-mail e senha)
                .tipo(tipo) // Define o tipo de funcionário
                .nome(nome) // Define o nome do funcionário
                .cpf(cpf) // Define o CPF do funcionário
                .rg(rg) // Define o RG do funcionário
                .dataNascimento(dataNascimento) // Define a data de nascimento
                .foneCelular(foneCelular) // Define o celular
                .foneFixo(foneFixo) // Define o telefone fixo
                .salario(salario) // Define o salário
                .enderecoRua(enderecoRua) // Define a rua do endereço
                .enderecoNumero(enderecoNumero) // Define o número do endereço
                .enderecoBairro(enderecoBairro) // Define o bairro
                .enderecoCidade(enderecoCidade) // Define a cidade
                .enderecoCep(enderecoCep) // Define o CEP
                .enderecoUf(enderecoUf) // Define o estado
                .enderecoComplemento(enderecoComplemento) // Define o complemento do endereço
                .build(); // Retorna o objeto Funcionario com todos os dados preenchidos
    }

    // Método que cria o objeto Usuario com base no e-mail e senha fornecidos
    public Usuario buildUsuario() {

        // Usando o padrão Builder para construir o objeto Usuario
        return Usuario.builder()
                .username(email) // O nome de usuário será o e-mail
                .password(password) // Define a senha
                .build(); // Retorna o objeto Usuario
    }

}
