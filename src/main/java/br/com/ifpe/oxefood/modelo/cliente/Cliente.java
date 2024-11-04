package br.com.ifpe.oxefood.modelo.cliente;

import java.time.LocalDate;

import br.com.ifpe.oxefood.util.entity.EntidadeAuditavel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Cliente extends EntidadeAuditavel  {
  
   private String nome;

   private LocalDate dataNascimento;

   private String cpf;

   private String foneCelular;

   private String foneFixo;

}
