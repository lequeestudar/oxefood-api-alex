package br.com.ifpe.oxefood.modelo.venda;
import java.util.List;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VendaService {
    @Autowired
    private VendaRepository repository;

    @Transactional
    public Venda save(Venda venda) {
        venda.setHabilitado(Boolean.TRUE);
        return repository.save(venda);
    }

    public List<Venda> listarTodos() {
        return repository.findAll();
    }

    public Venda obterPorID(Long id) {
        return repository.findById(id).get();
    }

    @Transactional
    public void update(Long id, Venda vendaAlterada) {
        Venda venda = repository.findById(id).get();
        venda.setCliente(vendaAlterada.getCliente());
        venda.setProduto(vendaAlterada.getProduto());
        venda.setStatusVenda(vendaAlterada.getStatusVenda());
        venda.setDataVenda(vendaAlterada.getDataVenda());
        venda.setValorTotal(vendaAlterada.getValorTotal());
        venda.setObservacao(vendaAlterada.getObservacao());
        venda.setRetiradaEmLoja(vendaAlterada.getRetiradaEmLoja());

        repository.save(venda);
    }

    @Transactional
        public void delete(Long id) {
        Venda venda = repository.findById(id).get();
        venda.setHabilitado(Boolean.FALSE);
        repository.save(venda);
   }
}