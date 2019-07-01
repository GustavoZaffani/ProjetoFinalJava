package br.edu.utfpr.pb.ProjetoFinal.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
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

    @NotNull(message = "O campo 'Data de Nascimento' é de preenchimento obrigatório.")
    @Column(name = "data_nascimento", nullable = false)
    private LocalDate dataNascimento;

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

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public Cidade getCidade() {
        return cidade;
    }

    public void setCidade(Cidade cidade) {
        this.cidade = cidade;
    }

    public Integer getNro() {
        return nro;
    }

    public void setNro(Integer nro) {
        this.nro = nro;
    }

    public List<Contato> getContatos() {
        return contatos;
    }

    public void setContatos(List<Contato> contatos) {
        this.contatos = contatos;
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