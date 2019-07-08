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

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Table(name = "usuario")
@NamedQueries({
        @NamedQuery(name = "Usuario.findByEmail",
                query = "Select u from Usuario u "
                        + " where u.email=:email")
})
public class Usuario implements AbstractModel, Serializable {

    private static final long serialVersionUID = 1L;
    public static final String FIND_BY_EMAIL = "Usuario.findByEmail";

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public byte[] getSenha() {
        return senha;
    }

    public void setSenha(byte[] senha) {
        this.senha = senha;
    }

    public Boolean getAtivo() {
        return isAtivo;
    }

    public void setAtivo(Boolean ativo) {
        isAtivo = ativo;
    }

    public Boolean getAdministrador() {
        return isAdministrador;
    }

    public void setAdministrador(Boolean administrador) {
        isAdministrador = administrador;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }
}