package br.edu.utfpr.pb.ProjetoFinal.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "compra")
public class Compra implements AbstractModel {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "data_compra", nullable = false)
    private LocalDate dataCompra;

    @OneToOne
    private ContaPagar contaPagar;

    @OneToMany(mappedBy = "compra",
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<CompraProduto> compraProdutos;
}
