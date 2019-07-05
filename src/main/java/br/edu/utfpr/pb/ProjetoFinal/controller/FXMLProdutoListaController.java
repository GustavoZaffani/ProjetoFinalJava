/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.edu.utfpr.pb.ProjetoFinal.controller;

import br.edu.utfpr.pb.ProjetoFinal.dao.ProdutoDao;
import br.edu.utfpr.pb.ProjetoFinal.dao.UsuarioDao;
import br.edu.utfpr.pb.ProjetoFinal.model.Produto;
import br.edu.utfpr.pb.ProjetoFinal.model.Usuario;
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

import java.math.BigDecimal;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Gustavo Zaffani
 */
public class FXMLProdutoListaController implements Initializable {

    @FXML
    private TableView<Produto> tableData;
    @FXML
    private TableColumn<Produto, Long> columnId;
    @FXML
    private TableColumn<Produto, String> columnNome;
    @FXML
    private TableColumn<Produto, BigDecimal> columnValor;
    @FXML
    private Button buttonEdit;
    private ProdutoDao produtoDao;
    private ObservableList<Produto> list =
            FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.produtoDao = new ProdutoDao();
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
        this.columnNome.setCellValueFactory(
                new PropertyValueFactory<>("nome")
        );
        this.columnValor.setCellValueFactory(
                new PropertyValueFactory<>("precoVenda")
        );
    }

    private void loadData() {
        this.list.clear();
        this.list.addAll(this.produtoDao.findAll());
        tableData.setItems(list);
    }

    private void openForm(Produto produto,
                          ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(
                    this.getClass()
                            .getResource("/fxml/FXMLProdutoCadastro.fxml"));
            AnchorPane pane = (AnchorPane) loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Cadastro de Produto");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(
                    ((Node) buttonEdit)
                            .getScene().getWindow());
            Scene scene = new Scene(pane);
            dialogStage.setScene(scene);

            FXMLProdutoCadastroController controller =
                    loader.getController();
            controller.setProduto(produto);
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
        Produto produto =
                tableData.getSelectionModel()
                        .getSelectedItem();
        this.openForm(produto, event);
    }

    @FXML
    private void newRecord(ActionEvent event) {
        this.openForm(new Produto(), event);
    }

    @FXML
    private void delete(ActionEvent event) {
        if (tableData.getSelectionModel()
                .getSelectedIndex() >= 0) {
            try {
                Produto produto = tableData
                        .getSelectionModel().getSelectedItem();
                produtoDao.delete(produto.getId());
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