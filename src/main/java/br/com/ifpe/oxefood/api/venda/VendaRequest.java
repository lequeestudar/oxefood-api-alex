package br.com.ifpe.oxefood.api.venda;

import java.time.LocalDate; // Importa a classe LocalDate para trabalhar com datas sem horário
import com.fasterxml.jackson.annotation.JsonFormat; // Importa a anotação para formatar datas durante a serialização JSON
import br.com.ifpe.oxefood.modelo.venda.Venda; // Importa a classe Venda, que é o modelo de dados
import lombok.AllArgsConstructor; // Gera um construtor com todos os parâmetros
import lombok.Builder; // Gera um padrão Builder para a classe
import lombok.Data; // Gera métodos de acesso (getters, setters), toString, equals e hashCode
import lombok.NoArgsConstructor; // Gera um construtor sem parâmetros

@Data // Cria automaticamente os métodos getters, setters, toString, equals, e hashCode
@Builder // Permite criar objetos da classe de forma mais fluida, seguindo o padrão Builder
@NoArgsConstructor // Gera um construtor vazio (sem parâmetros)
@AllArgsConstructor // Gera um construtor com todos os parâmetros da classe
public class VendaRequest {

    private String cliente; // Nome do cliente que está realizando a compra

    private String produto; // Nome ou descrição do produto comprado
    
    private String statusVenda; // Status da venda (ex: "Pendente", "Finalizada", etc.)

    @JsonFormat(pattern = "dd/MM/yyyy") // Formatação da data ao serializar/deserializar para o formato "dd/MM/yyyy"
    private LocalDate dataVenda; // Data em que a venda foi realizada

    private Double valorTotal; // Valor total da venda

    private String observacao; // Observações adicionais sobre a venda (ex: promoções, observações especiais)

    private Boolean retiradaEmLoja; // Indica se o cliente vai retirar a compra na loja (true/false)

    // Método que converte o objeto VendaRequest em um objeto Venda
    public Venda build() {
        return Venda.builder() // Inicia a construção do objeto Venda com o padrão Builder
                .cliente(cliente) // Define o nome do cliente
                .produto(produto) // Define o produto comprado
                .statusVenda(statusVenda) // Define o status da venda
                .dataVenda(dataVenda) // Define a data da venda
                .valorTotal(valorTotal) // Define o valor total da venda
                .observacao(observacao) // Define a observação da venda
                .retiradaEmLoja(retiradaEmLoja) // Define se a retirada será na loja
                .build(); // Constrói e retorna o objeto Venda
    }
}
