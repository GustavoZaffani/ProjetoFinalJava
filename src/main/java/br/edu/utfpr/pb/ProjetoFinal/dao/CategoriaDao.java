package br.edu.utfpr.pb.ProjetoFinal.dao;

import br.edu.utfpr.pb.ProjetoFinal.model.Categoria;

public class CategoriaDao extends GenericDao<Categoria, Long> {

    public CategoriaDao() {
        super(Categoria.class);
    }
}
