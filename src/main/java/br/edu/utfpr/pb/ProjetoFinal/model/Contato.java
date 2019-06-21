package br.edu.utfpr.pb.ProjetoFinal.model;

import br.edu.utfpr.pb.ProjetoFinal.enumeration.EOperadora;
import br.edu.utfpr.pb.ProjetoFinal.enumeration.ETipoContato;
import br.edu.utfpr.pb.ProjetoFinal.util.TipoContatoConverter;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "contato")
public class Contato implements AbstractModel {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 25, nullable = false)
    private String telefone;

    @Enumerated(EnumType.STRING)
    private EOperadora operadora;

    //@Enumerated(EnumType.ORDINAL)
    @Convert(converter = TipoContatoConverter.class)
    private ETipoContato tipoContato;

    @ManyToOne
    @JoinColumn(name = "cliente_id", referencedColumnName = "id")
    private Cliente cliente;

    public Contato() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public EOperadora getOperadora() {
        return operadora;
    }

    public void setOperadora(EOperadora operadora) {
        this.operadora = operadora;
    }

    public ETipoContato getTipoContato() {
        return tipoContato;
    }

    public void setTipoContato(ETipoContato tipoContato) {
        this.tipoContato = tipoContato;
    }

    public Cliente getUsuario() {
        return cliente;
    }

    public void setUsuario(Cliente cliente) {
        this.cliente = cliente;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Contato other = (Contato) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
}
