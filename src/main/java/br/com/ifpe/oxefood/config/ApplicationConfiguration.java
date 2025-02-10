package br.com.ifpe.oxefood.config;

import org.springframework.context.annotation.Bean; // Importa a anotação que marca um método como produtor de um bean
import org.springframework.context.annotation.Configuration; // Importa a anotação para marcar a classe como uma configuração do Spring
import org.springframework.security.authentication.AuthenticationManager; // Importa o gerenciador de autenticação
import org.springframework.security.authentication.AuthenticationProvider; // Importa a interface para configurar a autenticação
import org.springframework.security.authentication.dao.DaoAuthenticationProvider; // Importa o provedor de autenticação baseado em DAO
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration; // Importa a configuração de autenticação
import org.springframework.security.core.userdetails.UserDetailsService; // Interface que define o serviço para carregar dados do usuário
import org.springframework.security.core.userdetails.UsernameNotFoundException; // Exceção lançada quando um usuário não é encontrado
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder; // Classe que implementa a criptografia de senhas usando BCrypt

import br.com.ifpe.oxefood.modelo.acesso.UsuarioRepository; // Importa o repositório que acessa dados de usuário

@Configuration // Marca a classe como uma classe de configuração para o Spring
public class ApplicationConfiguration {

    private final UsuarioRepository userRepository; // Repositório responsável por acessar os dados do usuário

    // Construtor que injeta o repositório de usuários
    public ApplicationConfiguration(UsuarioRepository userRepository) {
        
        this.userRepository = userRepository;
    }

    // Definição do bean para carregar os detalhes do usuário (UserDetailsService)
    @Bean
    UserDetailsService userDetailsService() {

        // Retorna uma implementação de UserDetailsService que utiliza o repositório para encontrar o usuário
        return username -> userRepository.findByUsername(username) // Busca o usuário pelo nome de usuário
            .orElseThrow(() -> new UsernameNotFoundException("User not found")); // Lança exceção se o usuário não for encontrado
    }

    // Define o bean do BCryptPasswordEncoder para codificação de senhas
    @Bean
    BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Cria e retorna um encoder BCrypt para criptografar senhas
    }

    // Define o bean do AuthenticationManager, necessário para a autenticação
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {

        // Retorna o AuthenticationManager a partir da configuração de autenticação fornecida pelo Spring
        return config.getAuthenticationManager();
    }

    // Define o bean do AuthenticationProvider, responsável por autenticar o usuário
    @Bean
    AuthenticationProvider authenticationProvider() {

        // Cria uma instância do DaoAuthenticationProvider
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        // Define o UserDetailsService que será usado para carregar os detalhes do usuário
        authProvider.setUserDetailsService(userDetailsService());

        // Define o PasswordEncoder para comparar senhas de forma segura
        authProvider.setPasswordEncoder(passwordEncoder());

        // Retorna o AuthenticationProvider configurado
        return authProvider;
    }

}
