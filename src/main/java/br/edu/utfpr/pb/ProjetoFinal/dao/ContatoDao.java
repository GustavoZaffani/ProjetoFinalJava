package br.edu.utfpr.pb.ProjetoFinal.dao;

import br.edu.utfpr.pb.ProjetoFinal.model.Contato;

public class ContatoDao extends GenericDao<Contato, Long> {

    public ContatoDao() {
        super(Contato.class);
    }
}
