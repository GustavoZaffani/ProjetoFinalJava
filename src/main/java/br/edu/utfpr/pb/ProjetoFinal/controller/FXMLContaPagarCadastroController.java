package br.edu.utfpr.pb.ProjetoFinal.controller;

import br.edu.utfpr.pb.ProjetoFinal.dao.ContaPagarDao;
import br.edu.utfpr.pb.ProjetoFinal.enumeration.ETipoPagamento;
import br.edu.utfpr.pb.ProjetoFinal.model.Compra;
import br.edu.utfpr.pb.ProjetoFinal.model.ContaPagar;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

/**
 * @author Gustavo Zaffani
 */
public class FXMLContaPagarCadastroController implements Initializable {

    @FXML
    private TextField textId;
    @FXML
    private TextField textDescricao;
    @FXML
    private DatePicker datePickerConta;
    @FXML
    private DatePicker datePickerVencimento;
    @FXML
    private TextArea textAreaObservacao;
    @FXML
    private TextField textValor;
    @FXML
    private TextField textCompra;
    @FXML
    private ComboBox comboTipoPag;

    private ContaPagar contaPagar;
    private ContaPagarDao contaPagarDao;
    private Stage dialogStage;
    private Compra compra;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.contaPagarDao = new ContaPagarDao();
        this.contaPagar = new ContaPagar();

        ObservableList<ETipoPagamento> tiposPagamento =
                FXCollections.observableArrayList(
                        ETipoPagamento.A, ETipoPagamento.P
                );
        this.comboTipoPag.setItems(tiposPagamento);
        this.setDefaultValues();
    }

    private void setDefaultValues() {
        this.textValor.setText(BigDecimal.ZERO.toString());
        this.datePickerConta.setValue(LocalDate.now());
        this.datePickerVencimento.setValue(LocalDate.now());
    }

    public void setContaPagar(ContaPagar contaPagar) {
        if (contaPagar.getCompra() != null) {
            this.compra = contaPagar.getCompra();
            this.textCompra.setText(this.compra.getDescricao());
            this.textValor.setText(this.compra.getValorTotal().toString());
            this.textValor.setEditable(false);
        }
        this.contaPagar = contaPagar;
        if (contaPagar.getId() != null) {
            this.textId.setText(contaPagar.getId().toString());
            this.textDescricao.setText(contaPagar.getDescricao());
            this.datePickerConta.setValue(contaPagar.getDataConta());
            this.datePickerVencimento.setValue(contaPagar.getDataVencimento());
            this.textAreaObservacao.setText(contaPagar.getObservacao());
            this.comboTipoPag.setValue(contaPagar.getTipoPagamento());
            this.textValor.setText(contaPagar.getValorConta().toString());
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
        contaPagar.setDescricao(textDescricao.getText());
        contaPagar.setDataConta(datePickerConta.getValue());
        contaPagar.setDataVencimento(datePickerVencimento.getValue());
        contaPagar.setObservacao(textAreaObservacao.getText());
        contaPagar.setValorConta(new BigDecimal(textValor.getText()));
        contaPagar.setTipoPagamento((ETipoPagamento) comboTipoPag.getSelectionModel().getSelectedItem());

        if (this.compra != null) {
            this.compra.setContaPagar(this.contaPagar);
            this.dialogStage.close();
        } else {
            if (this.contaPagarDao.isValid(contaPagar)) {
                this.contaPagarDao.save(contaPagar);
                this.dialogStage.close();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erro");
                alert.setHeaderText("Erro ao salvar registro");
                alert.setContentText(this.contaPagarDao.getErrors(contaPagar));
                alert.showAndWait();
            }
        }
    }
}