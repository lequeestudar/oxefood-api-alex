package br.com.ifpe.oxefood.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean; // Importa a anotação para definir um método como Bean gerenciado pelo Spring
import org.springframework.context.annotation.Configuration; // Anotação que define a classe como uma configuração do Spring
import org.springframework.http.HttpMethod; // Usado para definir os métodos HTTP (GET, POST, PUT, DELETE)
import org.springframework.security.authentication.AuthenticationProvider; // Interface para autenticação personalizada
import org.springframework.security.config.annotation.web.builders.HttpSecurity; // Classe para configurar regras de segurança HTTP
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity; // Habilita as configurações de segurança
import org.springframework.security.config.http.SessionCreationPolicy; // Define como as sessões devem ser gerenciadas
import org.springframework.security.web.SecurityFilterChain; // Interface para definir a cadeia de filtros de segurança
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter; // Filtro de autenticação padrão
import org.springframework.web.cors.CorsConfiguration; // Classe que configura as políticas CORS
import org.springframework.web.cors.CorsConfigurationSource; // Fonte de configuração para CORS
import org.springframework.web.cors.UrlBasedCorsConfigurationSource; // Classe que registra a configuração CORS para URLs específicas

import br.com.ifpe.oxefood.modelo.acesso.Perfil; // Importa os perfis de acesso definidos
import br.com.ifpe.oxefood.modelo.seguranca.JwtAuthenticationFilter; // Filtro de autenticação JWT personalizado

@Configuration // Anotação que define esta classe como uma classe de configuração
@EnableWebSecurity // Habilita a segurança web do Spring Security
public class SecurityConfiguration {

    private final AuthenticationProvider authenticationProvider; // Provê a autenticação personalizada
    private final JwtAuthenticationFilter jwtAuthenticationFilter; // Filtro JWT para verificar o token

    // Construtor que injeta as dependências necessárias
    public SecurityConfiguration(JwtAuthenticationFilter jwtAuthenticationFilter,
            AuthenticationProvider authenticationProvider) {
        this.authenticationProvider = authenticationProvider;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    // Configura os filtros de segurança, regras de autorização e CORS
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        // Configura as permissões de requisições HTTP, CORS e políticas de autenticação
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource())) // Configura CORS
                .csrf(c -> c.disable()) // Desabilita a proteção CSRF (não necessária para APIs REST)
                .authorizeHttpRequests(authorize -> authorize // Define as permissões para as requisições

                        // Permite o acesso público às rotas de registro de cliente, funcionário e autenticação
                        .requestMatchers(HttpMethod.POST, "/api/cliente").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/funcionario").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/auth").permitAll()
                        

                        // Permite que usuários com os perfis CLIENTE, FUNCIONARIO_ADMIN, FUNCIONARIO_USER consultem produtos
                        .requestMatchers(HttpMethod.GET, "/api/produto/").hasAnyAuthority(
                                Perfil.ROLE_CLIENTE,
                                Perfil.ROLE_FUNCIONARIO_ADMIN,
                                Perfil.ROLE_FUNCIONARIO_USER)

                        // Permite que usuários com os perfis CLIENTE, FUNCIONARIO_ADMIN, FUNCIONARIO_USER cadastrem produtos
                        .requestMatchers(HttpMethod.POST, "/api/produto").hasAnyAuthority(
                                Perfil.ROLE_CLIENTE,        
                                Perfil.ROLE_FUNCIONARIO_ADMIN,
                                Perfil.ROLE_FUNCIONARIO_USER)

                        // Permite que apenas usuários com perfil FUNCIONARIO_ADMIN ou FUNCIONARIO_USER alterem produtos
                        .requestMatchers(HttpMethod.PUT, "/api/produto/*").hasAnyAuthority(
                                Perfil.ROLE_FUNCIONARIO_ADMIN,
                                Perfil.ROLE_FUNCIONARIO_USER)

                        // Permite que apenas usuários com perfil FUNCIONARIO_ADMIN excluam produtos
                        .requestMatchers(HttpMethod.DELETE, "/api/produto/*").hasAnyAuthority(
                                Perfil.ROLE_FUNCIONARIO_ADMIN)

                        // Permite que usuários com os perfis CLIENTE, FUNCIONARIO_ADMIN, FUNCIONARIO_USER filtrem clientes
                        .requestMatchers(HttpMethod.POST, "/api/cliente/filtrar").hasAnyAuthority(
                                Perfil.ROLE_CLIENTE,
                                Perfil.ROLE_FUNCIONARIO_ADMIN,
                                Perfil.ROLE_FUNCIONARIO_USER)

                        // Permite o acesso público aos endpoints Swagger para documentação da API
                        .requestMatchers(HttpMethod.GET, "/api-docs/*").permitAll()
                        .requestMatchers(HttpMethod.GET, "/swagger-ui/*").permitAll()

                        // Requer autenticação para qualquer outra requisição
                        .anyRequest().authenticated()

                )
                // Define que a aplicação não usará sessão (stateless)
                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // Configura o provedor de autenticação personalizado
                .authenticationProvider(authenticationProvider)
                // Adiciona o filtro JWT antes do filtro de autenticação padrão
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        // Retorna a configuração de filtros de segurança
        return http.build();
    }

    // Método que define a configuração CORS
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration configuration = new CorsConfiguration();

        // Permite requisições apenas da URL "http://localhost:3000" (típico de desenvolvimento front-end)
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
        // Define os métodos HTTP permitidos
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
        // Permite os cabeçalhos Authorization e Content-Type
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type"));
        // Permite o envio de cookies (credenciais) com as requisições
        configuration.setAllowCredentials(true);

        // Registra a configuração CORS para todas as URLs
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        
        return source; // Retorna a configuração CORS
    }
}
