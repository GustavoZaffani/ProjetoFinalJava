package br.edu.utfpr.pb.ProjetoFinal.dao;

import br.edu.utfpr.pb.ProjetoFinal.model.VendaProduto;

public class VendaProdutoDao extends GenericDao<VendaProduto, Long> {

    public VendaProdutoDao() {
        super(VendaProduto.class);
    }
}
