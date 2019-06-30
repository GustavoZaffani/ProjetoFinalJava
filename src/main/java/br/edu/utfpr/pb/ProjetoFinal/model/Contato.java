package br.edu.utfpr.pb.ProjetoFinal.model;

import br.edu.utfpr.pb.ProjetoFinal.enumeration.EOperadora;
import br.edu.utfpr.pb.ProjetoFinal.enumeration.ETipoContato;
import br.edu.utfpr.pb.ProjetoFinal.util.TipoContatoConverter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
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

    @Convert(converter = TipoContatoConverter.class)
    private ETipoContato tipoContato;

    @ManyToOne
    @JoinColumn(name = "cliente_id", referencedColumnName = "id")
    private Cliente cliente;

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

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
}
