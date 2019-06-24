package br.edu.utfpr.pb.ProjetoFinal.model;

import br.edu.utfpr.pb.ProjetoFinal.util.BooleanConverter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

// TODO necessário realizar as alterações
@Entity
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

    private String teste;
    @NotEmpty(message = "O campo 'Nome' é obrigatório!")
    @Column(name = "nome", length = 100, nullable = false)
    private String nome;

    @NotEmpty(message = "O campo 'cpf' é obrigatório!")
    @Column(name = "cpf", length = 11, nullable = false)
    private String cpf;

    @Column(name = "email", length = 100, nullable = false)
    private String email;

    @NotEmpty(message = "O campo 'Senha' é de preenchimento obrigatório.")
    @Column(name = "senha", length = 512, nullable = false)
    private String senha;

    @Convert(converter = BooleanConverter.class)
    @Column(name = "isAtivo", columnDefinition = "char(1) default 'T'")
    private Boolean isAtivo;

    @NotNull(message = "O campo 'Data de Nascimento' é obrigatório!")
    @Column(name = "data-nascimento", nullable = false)
    private LocalDate dataNascimento;

    @Lob
    @Column()
    private byte[] foto;

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

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

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Boolean getAtivo() {
        return isAtivo;
    }

    public void setAtivo(Boolean ativo) {
        this.isAtivo = ativo;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Usuario other = (Usuario) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }


}
