package br.edu.utfpr.pb.ProjetoFinal.controller;

import br.edu.utfpr.pb.ProjetoFinal.dao.CompraDao;
import br.edu.utfpr.pb.ProjetoFinal.model.Compra;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

/**
 * @author Gustavo Zaffani
 */
public class FXMLCompraListaController implements Initializable {

    @FXML
    private TableView<Compra> tableData;
    @FXML
    private TableColumn<Compra, Long> columnId;
    @FXML
    private TableColumn<Compra, String> columnDescricao;
    @FXML
    private TableColumn<Compra, Double> columnValorTotal;
    @FXML
    private TableColumn<Compra, LocalDate> columnDtCompra;
    @FXML
    private Button buttonEdit;
    private CompraDao compraDao;
    private ObservableList<Compra> list = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.compraDao = new CompraDao();
        this.tableData.getSelectionModel()
                .setSelectionMode(
                        SelectionMode.SINGLE);
        setColumnProperties();
        loadData();
    }

    private void setColumnProperties() {
        this.columnId.setCellValueFactory(
                new PropertyValueFactory<>("id")
        );
        this.columnDescricao.setCellValueFactory(
                new PropertyValueFactory<>("descricao")
        );
        this.columnValorTotal.setCellValueFactory(
                new PropertyValueFactory<>("vlrTotal")
        );
        this.columnDtCompra.setCellValueFactory(
                new PropertyValueFactory<>("dataCompra")
        );
    }

    private void loadData() {
        this.list.clear();
        this.list.addAll(this.compraDao.findAll());
        tableData.setItems(list);
    }

    private void openForm(Compra compra,
                          ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(
                    this.getClass()
                            .getResource("/fxml/FXMLCompraCadastro.fxml"));
            AnchorPane pane = (AnchorPane) loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Compra de Produtos");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(
                    ((Node) buttonEdit)
                            .getScene().getWindow());
            Scene scene = new Scene(pane);
            dialogStage.setScene(scene);

            FXMLCompraCadastroController controller =
                    loader.getController();
            controller.setCompra(compra);
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

    @FXML
    private void edit(ActionEvent event) {
        Compra compra =
                tableData.getSelectionModel()
                        .getSelectedItem();
        this.openForm(compra, event);
    }

    @FXML
    private void newRecord(ActionEvent event) {
        this.openForm(new Compra(), event);
    }

    @FXML
    private void delete(ActionEvent event) {
        if (tableData.getSelectionModel()
                .getSelectedIndex() >= 0) {
            try {
                Compra compra = tableData
                        .getSelectionModel().getSelectedItem();
                compraDao.delete(compra.getId());
                tableData.getItems().remove(
                        tableData.getSelectionModel()
                                .getSelectedIndex());
            } catch (Exception e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erro");
                alert.setHeaderText("Ocorreu um erro "
                        + " ao remover o registro!");
                alert.setContentText("Por favor, tente realizar "
                        + "a operação novamente!");
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Nenhum registro "
                    + "selecionado");
            alert.setContentText("Por favor, "
                    + "selecione um registro "
                    + "na tabela!");
            alert.showAndWait();
        }
    }
}