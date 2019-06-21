package br.edu.utfpr.pb.ProjetoFinal.dao;

import br.edu.utfpr.pb.ProjetoFinal.model.Cliente;

public class ClienteDao extends GenericDao<Cliente, Long> {

    public ClienteDao() {
        super(Cliente.class);
    }
}
