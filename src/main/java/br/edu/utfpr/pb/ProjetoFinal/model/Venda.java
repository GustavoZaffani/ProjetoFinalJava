package br.edu.utfpr.pb.ProjetoFinal.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

// TODO necessário realizar as alterações
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Table(name = "venda")
public class Venda implements Serializable, AbstractModel {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "descricao", length = 60, nullable = false)
    private String descricao;

    @Column(name = "data_venda", nullable = false)
    private LocalDate dataVenda;

    @ManyToOne
    @JoinColumn(name = "cliente_id", referencedColumnName = "id")
    private Cliente cliente;

    @OneToMany(mappedBy = "venda",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    private List<VendaProduto> vendaProdutos;

    @OneToMany(mappedBy = "venda",
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<ContaReceber> contasAReceber;

    @Transient
    private Double valorTotal;

    public Double getValorTotal() {
        return null;
    }
}
