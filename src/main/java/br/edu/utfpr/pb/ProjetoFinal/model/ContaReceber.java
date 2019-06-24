package br.edu.utfpr.pb.ProjetoFinal.model;

import javax.persistence.*;

@Entity
@Table(name = "conta-receber")
public class ContaReceber implements AbstractModel {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "detalhes")
    private String detalhesConta;

    @Column(name = "observacao")
    private String observacao;

    public ContaReceber() {
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
