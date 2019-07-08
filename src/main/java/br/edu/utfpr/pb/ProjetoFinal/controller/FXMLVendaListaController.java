package br.edu.utfpr.pb.ProjetoFinal.controller;

import br.edu.utfpr.pb.ProjetoFinal.dao.VendaDao;
import br.edu.utfpr.pb.ProjetoFinal.model.Venda;
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
public class FXMLVendaListaController implements Initializable {

    @FXML
    private TableView<Venda> tableData;
    @FXML
    private TableColumn<Venda, Long> columnId;
    @FXML
    private TableColumn<Venda, String> columnDescricao;
    @FXML
    private TableColumn<Venda, Double> columnValorTotal;
    @FXML
    private TableColumn<Venda, LocalDate> columnDtVenda;
    @FXML
    private Button buttonEdit;
    private VendaDao vendaDao;
    private ObservableList<Venda> list = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.vendaDao = new VendaDao();
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
                new PropertyValueFactory<>("valorTotal")
        );
        this.columnDtVenda.setCellValueFactory(
                new PropertyValueFactory<>("dataVenda")
        );
    }

    private void loadData() {
        this.list.clear();
        this.list.addAll(this.vendaDao.findAll());
        tableData.setItems(list);
    }

    private void openForm(Venda venda,
                          ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(
                    this.getClass()
                            .getResource("/fxml/FXMLVendaCadastro.fxml"));
            AnchorPane pane = (AnchorPane) loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Carrinho");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(
                    ((Node) buttonEdit)
                            .getScene().getWindow());
            Scene scene = new Scene(pane);
            dialogStage.setScene(scene);

            FXMLVendaCadastroController controller =
                    loader.getController();
            controller.setVenda(venda);
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
        Venda venda =
                tableData.getSelectionModel()
                        .getSelectedItem();
        this.openForm(venda, event);
    }

    @FXML
    private void newRecord(ActionEvent event) {
        this.openForm(new Venda(), event);
    }

    @FXML
    private void delete(ActionEvent event) {
        if (tableData.getSelectionModel()
                .getSelectedIndex() >= 0) {
            try {
                Venda venda = tableData
                        .getSelectionModel().getSelectedItem();
                vendaDao.delete(venda.getId());
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