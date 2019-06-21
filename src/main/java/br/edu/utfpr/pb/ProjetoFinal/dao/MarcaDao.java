package br.edu.utfpr.pb.ProjetoFinal.dao;

import br.edu.utfpr.pb.ProjetoFinal.model.Marca;

public class MarcaDao extends GenericDao<Marca, Long> {

    public MarcaDao() {
        super(Marca.class);
    }
}
