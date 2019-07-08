package br.edu.utfpr.pb.ProjetoFinal.dao;

import br.edu.utfpr.pb.ProjetoFinal.model.Fornecedor;

public class FornecedorDao extends GenericDao<Fornecedor, Long> {

    public FornecedorDao() {
        super(Fornecedor.class);
    }
}
