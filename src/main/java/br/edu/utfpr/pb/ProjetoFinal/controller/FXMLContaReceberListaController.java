package br.edu.utfpr.pb.ProjetoFinal.controller;

import br.edu.utfpr.pb.ProjetoFinal.dao.ContaReceberDao;
import br.edu.utfpr.pb.ProjetoFinal.model.ContaReceber;
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

import javax.persistence.Temporal;
import java.math.BigDecimal;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

/**
 * @author Gustavo Zaffani
 */
public class FXMLContaReceberListaController implements Initializable {

    @FXML
    private TableView<ContaReceber> tableData;
    @FXML
    private TableColumn<ContaReceber, Long> columnId;
    @FXML
    private TableColumn<ContaReceber, String> columnDescricao;
    @FXML
    private TableColumn<ContaReceber, BigDecimal> columnValor;
    @FXML
    private TableColumn<ContaReceber, LocalDate> columnDtConta;
    @FXML
    private TableColumn<ContaReceber, LocalDate> columnDtVenc;
    @FXML
    private TableColumn<ContaReceber, BigDecimal> columnVlrParcela;
    @FXML
    private TableColumn<ContaReceber, Boolean> columnPago;
    @FXML
    private Button buttonEdit;
    private ContaReceberDao contaReceberDao;
    private ObservableList<ContaReceber> list = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.contaReceberDao = new ContaReceberDao();
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
        this.columnValor.setCellValueFactory(
                new PropertyValueFactory<>("valorConta")
        );
        this.columnVlrParcela.setCellValueFactory(
                new PropertyValueFactory<>("valorParcela")
        );
        this.columnDtConta.setCellValueFactory(
                new PropertyValueFactory<>("dataConta")
        );
        this.columnDtVenc.setCellValueFactory(
                new PropertyValueFactory<>("dataVencimento")
        );
        this.columnPago.setCellValueFactory(
                new PropertyValueFactory<>("infoPago")
        );
    }

    private void loadData() {
        this.list.clear();
        this.list.addAll(this.contaReceberDao.findAll());

        list.forEach(lista -> {
            if (lista.getIsPago().equals(Boolean.TRUE)) {
                lista.setInfoPago("Recebido");
            } else lista.setInfoPago("Pendente");
        });
        tableData.setItems(list);
    }

    private void openForm(ContaReceber contaReceber,
                          ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(
                    this.getClass()
                            .getResource("/fxml/FXMLContaReceberCadastro.fxml"));
            AnchorPane pane = (AnchorPane) loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Cadastro de Contas a Receber");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(
                    ((Node) buttonEdit)
                            .getScene().getWindow());
            Scene scene = new Scene(pane);
            dialogStage.setScene(scene);

            FXMLContaReceberCadastroController controller =
                    loader.getController();
            controller.setContaReceber(contaReceber);
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
        ContaReceber contaReceber =
                tableData.getSelectionModel()
                        .getSelectedItem();
        this.openForm(contaReceber, event);
    }

    @FXML
    private void newRecord(ActionEvent event) {
        this.openForm(new ContaReceber(), event);
    }

    @FXML
    private void receber(ActionEvent event) {
        if (tableData.getSelectionModel()
                .getSelectedIndex() >= 0) {
            try {
                ContaReceber contaReceber = tableData
                        .getSelectionModel().getSelectedItem();
                contaReceber.setIsPago(Boolean.TRUE);
                contaReceberDao.save(contaReceber);
                loadData();
            } catch (Exception e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erro");
                alert.setHeaderText("Ocorreu um erro "
                        + " ao realizar o recebimento da conta!");
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


    @FXML
    private void delete(ActionEvent event) {
        if (tableData.getSelectionModel()
                .getSelectedIndex() >= 0) {
            try {
                ContaReceber contaReceber = tableData
                        .getSelectionModel().getSelectedItem();
                if (contaReceber.getVenda() != null) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erro");
                    alert.setHeaderText("Não foi possível remover o registro!");
                    alert.setContentText("Essa conta está vinculada a uma venda!");
                    alert.showAndWait();
                } else {
                    contaReceberDao.delete(contaReceber.getId());
                    tableData.getItems().remove(
                            tableData.getSelectionModel()
                                    .getSelectedIndex());
                }
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