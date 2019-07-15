package br.edu.utfpr.pb.ProjetoFinal.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "compra")
public class Compra implements AbstractModel {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotEmpty(message = "O campo 'Descrição' é de preenchimento obrigatório.")
    @Column(name = "descricao", nullable = false)
    private String descricao;

    @NotNull(message = "O campo 'Data de Compra' deve ser selecionado.")
    @Column(name = "data_compra", nullable = false)
    private LocalDate dataCompra;

    @OneToOne(mappedBy = "compra", cascade = CascadeType.ALL)
    private ContaPagar contaPagar;

    @NotNull(message = "O campo 'Fornecedor' deve ser escolhido.")
    @ManyToOne
    @JoinColumn(name = "cliente_id", referencedColumnName = "id")
    private Fornecedor fornecedor;

    @NotNull(message = "Deve ser escolhido ao menos 1 produto.")
    @OneToMany(mappedBy = "compra",
            cascade = {CascadeType.ALL, CascadeType.REMOVE})
    private List<CompraProduto> compraProdutos;

    @Transient
    private Double vlrTotal;

    public Double getVlrTotal() {
        return compraProdutos.stream().mapToDouble(vp -> vp.getValor() *
                vp.getQtde()).sum();
    }

}
