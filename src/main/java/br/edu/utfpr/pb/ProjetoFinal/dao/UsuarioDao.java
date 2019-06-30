package br.edu.utfpr.pb.ProjetoFinal.dao;

import br.edu.utfpr.pb.ProjetoFinal.model.Usuario;
import br.edu.utfpr.pb.ProjetoFinal.util.CriptografiaUtil;

import javax.persistence.Query;

public class UsuarioDao extends GenericDao<Usuario, Long> {

    public UsuarioDao() {
        super(Usuario.class);
    }

    public Usuario findByEmailNamedQuery(String email,
                                                 String senha){
        Query query = em.createNamedQuery(
                Usuario.FIND_BY_EMAIL);
        query.setParameter("email", email);
        Usuario usuario = (Usuario) query.getSingleResult();

        if (CriptografiaUtil.descriptografa(usuario.getSenha()).equals(senha)) {
            return usuario;
        }
        return null;
    }
}
