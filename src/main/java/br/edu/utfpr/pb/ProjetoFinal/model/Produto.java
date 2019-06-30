package br.edu.utfpr.pb.ProjetoFinal.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Data
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "produto")
public class Produto implements AbstractModel {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "O campo 'Nome' é de preenchimento obrigatório.")
    @Column(name = "nome", length = 50, nullable = false)
    private String nome;

    @NotEmpty(message = "O campo 'Descrição' é de preenchimento obrigatório.")
    @Column(name = "descricao", length = 500, nullable = false)
    private String descricao;

    @DecimalMin(value = "0.01", message = "O valor deve ser maior que R$ 0.00.")
    @Column(name = "valor", nullable = false)
    private Double valor;

    @NotNull(message = "O campo 'Marca' deve ser selecionado.")
    @ManyToOne
    @JoinColumn(name = "marca_id", referencedColumnName = "id")
    private Marca marca;

    @NotNull(message = "O campo 'Categoria' deve ser selecionado.")
    @ManyToOne
    @JoinColumn(name = "categoria_id", referencedColumnName = "id")
    private Categoria categoria;

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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Marca getMarca() {
        return marca;
    }

    public void setMarca(Marca marca) {
        this.marca = marca;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
}
