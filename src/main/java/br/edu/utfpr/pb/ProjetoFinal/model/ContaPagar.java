package br.edu.utfpr.pb.ProjetoFinal.model;

import br.edu.utfpr.pb.ProjetoFinal.enumeration.ETipoPagamento;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "conta-pagar")
public class ContaPagar implements AbstractModel{

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "O campo 'Detalhes' é de preenchimento obrigatório.")
    @Column(name = "detalhes", nullable = false)
    private String detalhesConta;

    @Column(name = "observacao")
    private String observacao;

    @NotNull(message = "O campo 'Vencimento' é de preechimento obrigatório.")
    @Column(name = "data-vencimento", nullable = false)
    private LocalDate dataVencimento;

    @NotNull(message = "O campo 'Tipo de Pagamento' deve ser selecionado.")
    @Column(name = "tipo-pagamento", nullable = false)
    private ETipoPagamento tipoPagamento;

    public ContaPagar() {
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDetalhesConta() {
        return detalhesConta;
    }

    public void setDetalhesConta(String detalhesConta) {
        this.detalhesConta = detalhesConta;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public LocalDate getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(LocalDate dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public ETipoPagamento getTipoPagamento() {
        return tipoPagamento;
    }

    public void setTipoPagamento(ETipoPagamento tipoPagamento) {
        this.tipoPagamento = tipoPagamento;
    }
}
