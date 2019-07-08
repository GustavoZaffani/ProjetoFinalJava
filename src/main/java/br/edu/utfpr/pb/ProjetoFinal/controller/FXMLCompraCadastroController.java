package br.edu.utfpr.pb.ProjetoFinal.controller;

import br.edu.utfpr.pb.ProjetoFinal.dao.CompraDao;
import br.edu.utfpr.pb.ProjetoFinal.dao.FornecedorDao;
import br.edu.utfpr.pb.ProjetoFinal.dao.ProdutoDao;
import br.edu.utfpr.pb.ProjetoFinal.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author Gustavo Zaffani
 */
public class FXMLCompraCadastroController implements Initializable {

    @FXML
    private TextField textId;
    @FXML
    private TextField textDescricao;
    @FXML
    private DatePicker datePickerCompra;
    @FXML
    private ComboBox comboFornecedor;
    @FXML
    private ComboBox comboProduto;
    @FXML
    private TextField textQtde;
    @FXML
    private TextField textPreco;
    @FXML
    private TextField textVlrTotal;
    @FXML
    private TableView<CompraProduto> tableData;
    @FXML
    private TableColumn<CompraProduto, Produto> columnDescricao;
    @FXML
    private TableColumn<CompraProduto, Integer> columnQtde;
    @FXML
    private TableColumn<CompraProduto, Double> columnVlrUnit;
    @FXML
    private TableColumn<CompraProduto, Double> columnVlrTotal;

    private Compra compra;
    private CompraDao compraDao;
    private ProdutoDao produtoDao;
    private FornecedorDao fornecedorDao;
    private Stage dialogStage;
    private List<CompraProduto> compraProdutoList;
    private ObservableList<CompraProduto> list = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.compraDao = new CompraDao();
        this.produtoDao = new ProdutoDao();
        this.fornecedorDao = new FornecedorDao();
        this.compra = new Compra();
        this.compraProdutoList = new ArrayList<CompraProduto>();

        ObservableList<Produto> produtos =
                FXCollections.observableArrayList(
                        this.produtoDao.findAll()
                );
        this.comboProduto.setItems(produtos);

        ObservableList<Fornecedor> fornecedor =
                FXCollections.observableArrayList(
                        this.fornecedorDao.findAll()
                );
        this.comboFornecedor.setItems(fornecedor);

        this.comboProduto.setOnAction(event -> {
            this.textPreco.setText(
                    ((Produto) this.comboProduto.getSelectionModel()
                            .getSelectedItem()).getPrecoCusto().toString()
            );
        });

        this.tableData.getSelectionModel()
                .setSelectionMode(
                        SelectionMode.SINGLE);
        setColumnProperties();
        this.setDefaultValues();
    }

    private void setDefaultValues() {
        this.textPreco.setText("0.00");
        this.textVlrTotal.setText("0.00");
        this.datePickerCompra.setValue(LocalDate.now());
    }

    public void setCompra(Compra compra) {
        this.compra = compra;
        if (compra.getId() != null) {
            this.textId.setText(compra.getId().toString());
            this.textDescricao.setText(compra.getDescricao());
            this.datePickerCompra.setValue(compra.getDataCompra());
            this.comboFornecedor.setValue(compra.getFornecedor());
            this.textVlrTotal.setText(compra.getValorTotal().toString());
            this.compraProdutoList = compra.getCompraProdutos();
        }
        loadData();
    }

    @FXML
    private void inserirProduto() {
        if (this.comboProduto.getSelectionModel().getSelectedItem() != null
                && this.textQtde != null) {
            CompraProduto compraProduto = new CompraProduto();
            compraProduto.setProduto((Produto) this.comboProduto.getSelectionModel().getSelectedItem());
            compraProduto.setQtde(new Integer(this.textQtde.getText()));
            compraProduto.setValor(((Produto) this.comboProduto.getSelectionModel().getSelectedItem()).getPrecoCusto());
            compraProduto.setCompra(this.compra);
            this.compraProdutoList.add(compraProduto);
        }
        this.compra.setCompraProdutos(this.compraProdutoList);
        this.textVlrTotal.setText(compra.getValorTotal().toString());
        loadData();
    }

    private void setColumnProperties() {
        this.columnDescricao.setCellValueFactory(
                new PropertyValueFactory<>("produto")
        );
        this.columnVlrUnit.setCellValueFactory(
                new PropertyValueFactory<>("valor")
        );
        this.columnQtde.setCellValueFactory(
                new PropertyValueFactory<>("qtde")
        );
        this.columnVlrTotal.setCellValueFactory(
                new PropertyValueFactory<>("vlrTotal")
        );
    }

    private void loadData() {
        this.list.clear();
        this.list.addAll(this.compraProdutoList);
        tableData.setItems(list);
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    @FXML
    private void cancel() {
        this.dialogStage.close();
    }

    @FXML
    private void save(ActionEvent event) {
        compra.setDescricao(textDescricao.getText());
        compra.setDataCompra(datePickerCompra.getValue());
        compra.setFornecedor((Fornecedor) comboFornecedor.getSelectionModel().getSelectedItem());
        compra.setCompraProdutos(compraProdutoList);
        this.openPagamento(new ContaPagar(), event);
        if (this.compraDao.isValid(compra)) {
            this.compraDao.save(compra);
            this.dialogStage.close();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Erro ao salvar registro");
            alert.setContentText(this.compraDao.getErrors(compra));
            alert.showAndWait();
        }
    }

    private void openPagamento(ContaPagar contaPagar,
                               ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(
                    this.getClass()
                            .getResource("/fxml/FXMLContaPagarCadastro.fxml"));
            AnchorPane pane = (AnchorPane) loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Pagamento");
            dialogStage.initModality(Modality.WINDOW_MODAL);

            Scene scene = new Scene(pane);
            dialogStage.setScene(scene);
            FXMLContaPagarCadastroController controller =
                    loader.getController();
            contaPagar.setCompra(this.compra);
            controller.setContaPagar(contaPagar);
            controller.setDialogStage(dialogStage);
            dialogStage.showAndWait();

        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Ocorreu um erro ao abrir "
                    + "a janela de cadastro!");
            alert.setContentText("Por favor, tente realizar "
                    + "a operação novamente!");
            alert.showAndWait();
        }
        loadData();
    }

}