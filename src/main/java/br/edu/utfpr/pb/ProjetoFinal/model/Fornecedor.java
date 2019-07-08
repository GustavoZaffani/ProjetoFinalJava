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
@Table(name = "fornecedor")
public class Fornecedor implements AbstractModel {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotEmpty(message = "O campo 'Razão Social' é de preenchimento obrigatório.")
    @Column(name = "razao_social", length = 50, nullable = false)
    private String razaoSocial;

    @NotEmpty(message = "O campo 'Nome Fantasia' é de preenchimento obrigatório.")
    @Column(name = "nome_fantasia", length = 50, nullable = false)
    private String nomeFantasia;

    @NotEmpty(message = "O campo 'CNPJ' é de preenchimento obrigatório.")
    @Column(name = "cnpj", length = 14, nullable = false)
    private String cnpj;

    @NotEmpty(message = "O campo 'Inscrição Estadual' é de preenchimento obrigatório.")
    @Column(name = "ie", length = 14, nullable = false)
    private String ie;

    @NotNull(message = "O campo 'Data de Fundação' é de preenchimento obrigatório.")
    @Column(name = "data_fundacao", nullable = false)
    private LocalDate dataFundacao;

    @NotNull(message = "O campo 'Estado' deve ser selecionado.")
    @ManyToOne
    @JoinColumn(name = "estado_id", referencedColumnName = "id")
    private Estado estado;

    @NotNull(message = "O campo 'Cidade' deve ser selecionado.")
    @ManyToOne
    @JoinColumn(name = "cidade_id", referencedColumnName = "id")
    private Cidade cidade;

    @NotEmpty(message = "O campo 'Número' é de preenchimento obrigatório'")
    @Column(name = "nro", nullable = false)
    private String nro;

    public String toString() {
        return this.getNomeFantasia();
    }
}