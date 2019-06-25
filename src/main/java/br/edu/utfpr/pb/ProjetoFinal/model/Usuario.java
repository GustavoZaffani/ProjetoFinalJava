package br.edu.utfpr.pb.ProjetoFinal.model;

import br.edu.utfpr.pb.ProjetoFinal.util.BooleanConverter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

// TODO necessário realizar as alterações
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Table(name = "usuario")
@NamedQueries({
        @NamedQuery(name = "Usuario.findByEmailAndSenha",
                query = "Select u from Usuario u "
                        + " where u.email=:email AND u.senha=:senha")
})
public class Usuario implements AbstractModel, Serializable {

    private static final long serialVersionUID = 1L;
    public static final String FIND_BY_EMAIL_AND_SENHA = "Usuario.findByEmailAndSenha";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "O campo 'Nome' é obrigatório!")
    @Column(name = "nome", length = 100, nullable = false)
    private String nome;

    @NotEmpty(message = "O campo 'cpf' é obrigatório!")
    @Column(name = "cpf", length = 11, nullable = false)
    private String cpf;

    @Column(name = "email", length = 100, nullable = false)
    private String email;

    @NotEmpty(message = "O campo 'Senha' é de preenchimento obrigatório.")
    @Lob
    @Column(name = "senha", nullable = false)
    private byte[] senha;

    @Convert(converter = BooleanConverter.class)
    @Column(name = "isAtivo", columnDefinition = "char(1) default 'T'")
    private Boolean isAtivo;

    @Convert(converter = BooleanConverter.class)
    @Column(name = "isAdm", columnDefinition = "char(1) default 'F'")
    private Boolean isAdministrador;

    @NotNull(message = "O campo 'Data de Nascimento' é obrigatório!")
    @Column(name = "data_nascimento", nullable = false)
    private LocalDate dataNascimento;

    @Lob
    @Column(name = "foto")
    private byte[] foto;
}