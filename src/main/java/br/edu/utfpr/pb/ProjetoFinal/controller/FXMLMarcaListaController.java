package br.edu.utfpr.pb.ProjetoFinal.controller;

import br.edu.utfpr.pb.ProjetoFinal.dao.MarcaDao;
import br.edu.utfpr.pb.ProjetoFinal.model.Marca;
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
import java.util.ResourceBundle;

public class FXMLMarcaListaController implements Initializable {

    @FXML
    private TableView<Marca> tableData;
    @FXML
    private TableColumn<Marca, Long> columnId;
    @FXML
    private TableColumn<Marca, String> columnDescricao;
    @FXML
    private Button buttonEdit;
    private MarcaDao marcaDao;
    private ObservableList<Marca> list = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.marcaDao = new MarcaDao();
        this.tableData.getSelectionModel()
                .setSelectionMode(SelectionMode.SINGLE);
        setColumnProperties();
        loadData();
    }

    private void loadData() {
        this.list.clear();
        this.list.setAll(this.marcaDao.findAll());
        this.tableData.setItems(list);
    }

    private void setColumnProperties() {
        this.columnId.setCellValueFactory(
                new PropertyValueFactory<>("id")
        );
        this.columnDescricao.setCellValueFactory(
                new PropertyValueFactory<>("descricao")
        );
    }

    private void openForm(Marca marca,
                          ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(
                    this.getClass()
                            .getResource("/fxml/FXMLMarcaCadastro.fxml"));
            AnchorPane pane = (AnchorPane) loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Cadastro de Marca");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(
                    ((Node) buttonEdit)
                            .getScene().getWindow());
            Scene scene = new Scene(pane);
            dialogStage.setScene(scene);

            FXMLMarcaCadastroController controller =
                    loader.getController();
            controller.setMarca(marca);
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
        Marca marca =
                tableData.getSelectionModel()
                        .getSelectedItem();
        this.openForm(marca, event);
    }

    @FXML
    private void newRecord(ActionEvent event) {
        this.openForm(new Marca(), event);
    }

    @FXML
    private void delete(ActionEvent event) {
        if (tableData.getSelectionModel()
                .getSelectedIndex() >= 0) {
            try {
                Marca marca = tableData
                        .getSelectionModel().getSelectedItem();
                marcaDao.delete(marca.getId());
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
