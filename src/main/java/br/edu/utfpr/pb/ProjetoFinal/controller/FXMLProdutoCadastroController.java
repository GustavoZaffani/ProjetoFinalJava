package br.edu.utfpr.pb.ProjetoFinal.controller;

import br.edu.utfpr.pb.ProjetoFinal.dao.CategoriaDao;
import br.edu.utfpr.pb.ProjetoFinal.dao.MarcaDao;
import br.edu.utfpr.pb.ProjetoFinal.dao.ProdutoDao;
import br.edu.utfpr.pb.ProjetoFinal.model.Categoria;
import br.edu.utfpr.pb.ProjetoFinal.model.Marca;
import br.edu.utfpr.pb.ProjetoFinal.model.Produto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Gustavo Zaffani
 */
public class FXMLProdutoCadastroController implements Initializable {

    @FXML
    private TextField textId;
    @FXML
    private TextField textNome;
    @FXML
    private TextField textPrecoCusto;
    @FXML
    private TextField textPrecoVenda;
    @FXML
    private ComboBox comboMarca;
    @FXML
    private ComboBox comboCategoria;
    @FXML
    private TextArea textAreaObservacao;

    private Produto produto;
    private MarcaDao marcaDao;
    private CategoriaDao categoriaDao;
    private ProdutoDao produtoDao;
    private Stage dialogStage;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.produtoDao = new ProdutoDao();
        this.categoriaDao = new CategoriaDao();
        this.marcaDao = new MarcaDao();
        this.produto = new Produto();

        ObservableList<Marca> marcas =
                FXCollections.observableArrayList(
                        this.marcaDao.findAll()
                );
        this.comboMarca.setItems(marcas);

        ObservableList<Categoria> categorias =
                FXCollections.observableArrayList(
                        this.categoriaDao.findAll()
                );
        this.comboCategoria.setItems(categorias);
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
        if (produto.getId() != null) {
            this.textId.setText(produto.getId().toString());
            this.textNome.setText(produto.getNome());
            this.textPrecoCusto.setText(produto.getPrecoCusto().toString());
            this.textPrecoVenda.setText(produto.getPrecoVenda().toString());
            this.textAreaObservacao.setText(produto.getObservacao());
            this.comboMarca.setValue(produto.getMarca());
            this.comboCategoria.setValue(produto.getCategoria());
        }
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    @FXML
    private void cancel() {
        this.dialogStage.close();
    }

    @FXML
    private void save() {
        produto.setNome(textNome.getText());
        produto.setPrecoCusto(Double.valueOf(textPrecoCusto.getText()));
        produto.setPrecoVenda(Double.valueOf(textPrecoVenda.getText()));
        produto.setObservacao(textAreaObservacao.getText());
        produto.setMarca((Marca) comboMarca.getSelectionModel().getSelectedItem());
        produto.setCategoria((Categoria) comboCategoria.getSelectionModel().getSelectedItem());
        if (this.produtoDao.isValid(produto)) {
            this.produtoDao.save(produto);
            this.dialogStage.close();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Erro ao salvar registro");
            alert.setContentText(this.produtoDao.getErrors(produto));
            alert.showAndWait();
        }
    }
}