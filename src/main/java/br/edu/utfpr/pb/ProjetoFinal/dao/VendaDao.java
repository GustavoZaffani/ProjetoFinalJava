package br.edu.utfpr.pb.ProjetoFinal.dao;

import br.edu.utfpr.pb.ProjetoFinal.model.Venda;

public class VendaDao extends GenericDao<Venda, Long> {

    public VendaDao() {
        super(Venda.class);
    }
}
