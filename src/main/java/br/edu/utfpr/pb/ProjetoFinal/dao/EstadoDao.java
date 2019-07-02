package br.edu.utfpr.pb.ProjetoFinal.dao;

import br.edu.utfpr.pb.ProjetoFinal.model.Estado;

import javax.persistence.Query;

public class EstadoDao extends GenericDao<Estado, Long> {

    public EstadoDao() {
        super(Estado.class);
    }

    public Estado findByName(String nome) {
        Query query = em.createQuery("SELECT e FROM Estado e " +
                "WHERE e.nome = :nome");
        query.setParameter("nome", nome);
        return (Estado) query.getSingleResult();
    }
}
