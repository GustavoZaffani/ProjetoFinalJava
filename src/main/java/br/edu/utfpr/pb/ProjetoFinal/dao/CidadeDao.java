package br.edu.utfpr.pb.ProjetoFinal.dao;

import br.edu.utfpr.pb.ProjetoFinal.model.Cidade;
import br.edu.utfpr.pb.ProjetoFinal.model.Estado;

import javax.persistence.Query;
import java.util.List;

public class CidadeDao extends GenericDao<Cidade, Long> {

    public CidadeDao() {
        super(Cidade.class);
    }

    public List<Cidade> findCidadeByEstado(Long idEstado) {
        Query query = em.createQuery("SELECT c FROM Cidade c " +
                "WHERE c.estado.id = :estado");
        query.setParameter("estado", idEstado);
        return query.getResultList();
    }

}
