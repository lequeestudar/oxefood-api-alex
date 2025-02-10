package br.com.ifpe.oxefood.api.acesso;

import java.io.Serializable; // Importa a interface Serializable para permitir que objetos dessa classe possam ser serializados

import jakarta.validation.constraints.NotBlank; // Importa a anotação para garantir que os campos não sejam nulos ou em branco
import lombok.AllArgsConstructor; // Importa a anotação para gerar o construtor com todos os parâmetros
import lombok.Builder; // Importa a anotação para gerar um padrão Builder para a classe
import lombok.Data; // Importa a anotação para gerar automaticamente os métodos getter, setter, toString, equals e hashCode
import lombok.NoArgsConstructor; // Importa a anotação para gerar o construtor sem parâmetros

@Data // Gera automaticamente getters, setters, toString, equals e hashCode
@Builder // Permite a criação de objetos dessa classe utilizando o padrão Builder
@NoArgsConstructor // Gera o construtor sem parâmetros
@AllArgsConstructor // Gera o construtor com todos os parâmetros
public class AuthenticationRequest implements Serializable { // A classe implementa Serializable para possibilitar a serialização

    @NotBlank // Valida que o campo 'username' não pode ser nulo ou em branco
    private String username; // Atributo para armazenar o nome de usuário

    @NotBlank // Valida que o campo 'password' não pode ser nulo ou em branco
    private String password; // Atributo para armazenar a senha do usuário
    
}
