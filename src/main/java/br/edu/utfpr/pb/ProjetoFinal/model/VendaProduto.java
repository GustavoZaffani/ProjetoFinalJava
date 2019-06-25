package br.edu.utfpr.pb.ProjetoFinal.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

// TODO necessário realizar as alterações
@Entity
@Data
@Table(name = "venda_produto")
public class VendaProduto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int quantidade;

    @Column(nullable = false)
    private Double valor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "produto_id", referencedColumnName = "id", nullable = false)
    private Produto produto;

    @ManyToOne
    @JoinColumn(name = "venda_id", referencedColumnName = "id")
    private Venda venda;

    @Override
    public String toString() {
        return "VendaProduto{" + "id=" + id + ", quantidade=" + quantidade + ", valor=" + valor + ", produto=" + produto + ", venda=" + venda + '}';
    }
}
