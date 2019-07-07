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
@Table(name = "conta_receber")
public class ContaReceber implements AbstractModel {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "O campo 'Descrição' deve ser preenchido.")
    @Column(name = "descricao", nullable = false)
    private String descricao;

    @NotNull(message = "O campo 'Data de Vencimento' deve ser selecionado.")
    @Column(name = "data_vencimento", nullable = false)
    private LocalDate dataVencimento;

    @NotNull(message = "O campo 'Data da Conta' deve ser selecionado.")
    @Column(name = "data_conta", nullable = false)
    private LocalDate dataConta;

    @Column(name = "observacao")
    private String observacao;

    @NotNull(message = "O campo 'Tipo de Pagamento' deve ser selecionado.")
    @Column(name = "tipo_pagamento", nullable = false)
    private ETipoPagamento tipoPagamento;

    @DecimalMin(value = "0.01", message = "O valor deve ser maior que R$ 0.00.")
    @Column(name = "valor_conta")
    private BigDecimal valorConta;

    @ManyToOne
    @JoinColumn(name = "venda_id", referencedColumnName = "id")
    private Venda venda;

    @Column(name = "nro_parcelas")
    private Integer nroParcelas;

    @Column(name = "valor_parcela", nullable = false)
    private BigDecimal valorParcela;
}
