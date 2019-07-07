package br.edu.utfpr.pb.ProjetoFinal.controller;

import br.edu.utfpr.pb.ProjetoFinal.dao.*;
import br.edu.utfpr.pb.ProjetoFinal.enumeration.ETipoPagamento;
import br.edu.utfpr.pb.ProjetoFinal.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author Gustavo Zaffani
 */
public class FXMLVendaCadastroController implements Initializable {

    @FXML
    private TextField textId;
    @FXML
    private TextField textDescricao;
    @FXML
    private DatePicker datePickerVenda;
    @FXML
    private ComboBox comboCliente;
    @FXML
    private ComboBox comboProduto;
    @FXML
    private TextField textQtde;
    @FXML
    private TextField textPreco;
    @FXML
    private TextField textVlrTotal;
    @FXML
    private TableView<VendaProduto> tableData;
    @FXML
    private TableColumn<VendaProduto, Long> columnId;
    @FXML
    private TableColumn<VendaProduto, Produto> columnDescricao;
    @FXML
    private TableColumn<VendaProduto, Integer> columnQtde;
    @FXML
    private TableColumn<VendaProduto, BigDecimal> columnVlrUnit;
    @FXML
    private TableColumn<VendaProduto, BigDecimal> columnVlrTotal;

    private Venda venda;
    private VendaDao vendaDao;
    private ProdutoDao produtoDao;
    private ClienteDao clienteDao;
    private Stage dialogStage;
    private List<VendaProduto> vendaProdutoList;
    private ObservableList<VendaProduto> list = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.vendaDao = new VendaDao();
        this.produtoDao = new ProdutoDao();
        this.clienteDao = new ClienteDao();
        this.venda = new Venda();
        this.vendaProdutoList = new ArrayList<VendaProduto>();

        ObservableList<Produto> produtos =
                FXCollections.observableArrayList(
                        this.produtoDao.findAll()
                );
        this.comboProduto.setItems(produtos);

        ObservableList<Cliente> clientes =
                FXCollections.observableArrayList(
                        this.clienteDao.findAll()
                );
        this.comboCliente.setItems(clientes);

        this.comboProduto.setOnAction(event -> {
            this.textPreco.setText(
                    ((Produto)this.comboProduto.getSelectionModel()
                            .getSelectedItem()).getPrecoVenda().toString()
            );
        });

        this.tableData.getSelectionModel()
                .setSelectionMode(
                        SelectionMode.SINGLE);
        setColumnProperties();
        this.setDefaultValues();
    }

    private void setDefaultValues() {
        this.textPreco.setText(BigDecimal.ZERO.toString());
        this.textVlrTotal.setText(BigDecimal.ZERO.toString());
        this.datePickerVenda.setValue(LocalDate.now());
    }

    public void setVenda(Venda venda) {
        this.venda = venda;
        if (venda.getId() != null) {
            this.textId.setText(venda.getId().toString());
            this.textDescricao.setText(venda.getDescricao());
            this.datePickerVenda.setValue(venda.getDataVenda());
            this.comboCliente.setValue(venda.getCliente());
            this.vendaProdutoList = venda.getVendaProdutos();
        }
        loadData();
    }

    @FXML
    private void inserirProduto() {
        if (this.comboProduto.getSelectionModel().getSelectedItem() != null
                && this.textQtde != null) {
            VendaProduto vendaProduto = new VendaProduto();
            vendaProduto.setProduto((Produto) this.comboProduto.getSelectionModel().getSelectedItem());
            vendaProduto.setQuantidade(new Integer (this.textQtde.getText()));
            vendaProduto.setValor(((Produto) this.comboProduto.getSelectionModel().getSelectedItem()).getPrecoVenda());
            vendaProduto.setVenda(this.venda);
            this.vendaProdutoList.add(vendaProduto);
        }
        this.textVlrTotal.setText(venda.getValorTotal().toString());
        loadData();
    }

    private void setColumnProperties() {
        this.columnId.setCellValueFactory(
                new PropertyValueFactory<>("produto.id")
        );
        this.columnDescricao.setCellValueFactory(
                new PropertyValueFactory<>("produto")
        );
        this.columnVlrUnit.setCellValueFactory(
                new PropertyValueFactory<>("valor")
        );
        this.columnQtde.setCellValueFactory(
                new PropertyValueFactory<>("quantidade")
        );
    }

    private void loadData() {
        this.list.clear();
        this.list.addAll(this.vendaProdutoList);
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
    private void save() {
        venda.setDescricao(textDescricao.getText());
        venda.setDataVenda(datePickerVenda.getValue());
        venda.setCliente((Cliente)comboCliente.getSelectionModel().getSelectedItem());
        venda.setVendaProdutos(vendaProdutoList);
        if (this.vendaDao.isValid(venda)) {
            this.vendaDao.save(venda);
            this.dialogStage.close();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Erro ao salvar registro");
            alert.setContentText(this.vendaDao.getErrors(venda));
            alert.showAndWait();
        }
    }


}