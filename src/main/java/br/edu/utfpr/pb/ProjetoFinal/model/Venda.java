package br.edu.utfpr.pb.ProjetoFinal.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

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

    @NotEmpty(message = "O campo 'Descrição' é de preenchimento obrigatório!")
    @Column(name = "descricao", length = 60, nullable = false)
    private String descricao;

    @NotNull(message = "O campo 'Data da Venda' é de preenchimento obrigatório!")
    @Column(name = "data_venda", nullable = false)
    private LocalDate dataVenda;

    @NotNull(message = "O campo 'Cliente' deve ser selecionado!")
    @ManyToOne
    @JoinColumn(name = "cliente_id", referencedColumnName = "id")
    private Cliente cliente;

    @NotNull(message = "Deve ser escolhido ao menos 1 produto.")
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
        return vendaProdutos.stream().mapToDouble(vp -> vp.getValor() *
                vp.getQuantidade()).sum();
    }
}
