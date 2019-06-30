package br.edu.utfpr.pb.ProjetoFinal.model;

import br.edu.utfpr.pb.ProjetoFinal.enumeration.ETipoPagamento;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "conta_pagar")
public class ContaPagar implements AbstractModel {

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

    @Column(name = "data_conta", nullable = false)
    private LocalDate dataConta;

    @NotNull(message = "O campo 'Vencimento' é de preechimento obrigatório.")
    @Column(name = "data_vencimento", nullable = false)
    private LocalDate dataVencimento;

    @NotNull(message = "O campo 'Tipo de Pagamento' deve ser selecionado.")
    @Column(name = "tipo_pagamento", nullable = false)
    private ETipoPagamento tipoPagamento;

    @DecimalMin(value = "0.01", message = "O valor deve ser maior que R$ 0.00.")
    @Column(name = "valor_conta", nullable = false)
    private BigDecimal valorConta;

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

    public LocalDate getDataConta() {
        return dataConta;
    }

    public void setDataConta(LocalDate dataConta) {
        this.dataConta = dataConta;
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

    public BigDecimal getValorConta() {
        return valorConta;
    }

    public void setValorConta(BigDecimal valorConta) {
        this.valorConta = valorConta;
    }
}
