package br.edu.utfpr.pb.ProjetoFinal.dao;

import br.edu.utfpr.pb.ProjetoFinal.model.Cidade;

import java.util.List;

public class CidadeDao extends GenericDao<Cidade, Long> {

    public CidadeDao() {
        super(Cidade.class);
    }

}
