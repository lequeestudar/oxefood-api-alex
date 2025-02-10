package br.com.ifpe.oxefood.modelo.cliente;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    
    //busca aproximada por nome
    @Query(value = "SELECT c FROM Cliente c WHERE c.nome ilike %:nome% ORDER BY c.nome")
    List<Cliente> consultarPorNome(String nome);

    //busca exata por cpf
    @Query(value = "SELECT c FROM Cliente c WHERE c.cpf = :cpf")
    List<Cliente> consultarPorCpf(String cpf);

    //busca por nome e por cpf
    @Query(value = "SELECT c FROM Cliente c WHERE c.nome ilike %:nome% AND c.cpf = :cpf")
    List<Cliente> findByCpfAndNome(String nome, String cpf);

}