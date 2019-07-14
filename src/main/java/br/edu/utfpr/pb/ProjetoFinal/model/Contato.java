package br.edu.utfpr.pb.ProjetoFinal.model;

import br.edu.utfpr.pb.ProjetoFinal.enumeration.EOperadora;
import br.edu.utfpr.pb.ProjetoFinal.enumeration.ETipoContato;
import br.edu.utfpr.pb.ProjetoFinal.util.TipoContatoConverter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Data
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "contato")
public class Contato implements AbstractModel {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "O campo 'Telefone' é de preenchimento obrigatório.")
    @Column(length = 25, nullable = false)
    private String telefone;

    @NotNull(message = "O campo 'Operadora' deve ser selecionado.")
    @Enumerated(EnumType.STRING)
    private EOperadora operadora;

    @NotNull(message = "O campo 'Tipo de Contato' deve ser selecionado.")
    @Convert(converter = TipoContatoConverter.class)
    private ETipoContato tipoContato;

    @ManyToOne
    @JoinColumn(name = "cliente_id", referencedColumnName = "id")
    private Cliente cliente;
}
