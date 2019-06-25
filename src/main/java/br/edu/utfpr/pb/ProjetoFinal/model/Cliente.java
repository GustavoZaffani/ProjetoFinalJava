package br.edu.utfpr.pb.ProjetoFinal.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Data
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cliente")
public class Cliente implements AbstractModel {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotEmpty(message = "O campo 'Nome' é de preenchimento obrigatório.")
    @Column(name = "nome", length = 50, nullable = false)
    private String nome;

    @NotEmpty(message = "O campo 'CPF' é de preenchimento obrigatório.")
    @Column(name = "cpf", length = 11, nullable = false)
    private String cpf;

    @NotNull(message = "O campo 'Estado' deve ser selecionado.")
    @ManyToOne
    @JoinColumn(name = "estado_id", referencedColumnName = "id")
    private Estado estado;

    @NotNull(message = "O campo 'Cidade' deve ser selecionado.")
    @ManyToOne
    @JoinColumn(name = "cidade_id", referencedColumnName = "id")
    private Cidade cidade;

    @NotBlank(message = "O campo 'Número' é de preenchimento obrigatório'")
    @Column(name = "nro", nullable = false)
    private Integer nro;

    @OneToMany(mappedBy = "cliente", orphanRemoval = true,
            cascade = CascadeType.ALL)
    private List<Contato> contatos;
}