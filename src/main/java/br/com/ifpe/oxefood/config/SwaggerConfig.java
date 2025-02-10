package br.com.ifpe.oxefood.config;

import org.springdoc.core.models.GroupedOpenApi; // Importa a classe que facilita a criação de grupos de APIs
import org.springframework.context.annotation.Bean; // Importa a anotação Bean para indicar métodos de configuração
import org.springframework.context.annotation.Configuration; // Anotação que marca esta classe como uma classe de configuração
import io.swagger.v3.oas.models.OpenAPI; // Importa a classe principal para configurar o OpenAPI (Swagger)
import io.swagger.v3.oas.models.info.Contact; // Importa a classe para definir o contato na documentação
import io.swagger.v3.oas.models.info.Info; // Importa a classe para definir informações gerais sobre a API

@Configuration // Marca a classe como uma classe de configuração do Spring
public class SwaggerConfig {

    // Definindo a configuração principal do OpenAPI
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI() // Cria uma nova instância do OpenAPI
                .info(new Info() // Define as informações da API (como título, versão, descrição)
                        .title("OxeFood API") // Título da API
                        .version("1.0") // Versão da API
                        .description("API do OxeFood") // Descrição da API
                        .contact(new Contact() // Define as informações de contato do responsável pela API
                                .name("Wilson Barbosa") // Nome do responsável
                                .email("wbsj@discente.ifpe.edu.br"))); // E-mail de contato
    }

    // Definindo um grupo para a documentação da API
    @Bean
    public GroupedOpenApi customApi() {
        return GroupedOpenApi.builder() // Cria um novo grupo de OpenAPI
                .group("api") // Nome do grupo (serve para organizar as APIs)
                .pathsToMatch("/api/**") // Especifica os caminhos que serão incluídos na documentação
                .pathsToExclude("/error", "/actuator/**") // Exclui alguns caminhos, como os de erro e de monitoramento do actuator
                .build(); // Conclui a construção do grupo de APIs
    }
}
