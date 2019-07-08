package br.edu.utfpr.pb.ProjetoFinal.controller;

import br.edu.utfpr.pb.ProjetoFinal.dao.ContaReceberDao;
import br.edu.utfpr.pb.ProjetoFinal.enumeration.ETipoPagamento;
import br.edu.utfpr.pb.ProjetoFinal.model.ContaReceber;
import br.edu.utfpr.pb.ProjetoFinal.model.Venda;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
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
public class FXMLContaReceberCadastroController implements Initializable {

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
    private ComboBox comboTipoPag;
    @FXML
    private TextField textNroParcelas;
    @FXML
    private TextField textValorParcela;
    @FXML
    private TextField textVenda;

    private ContaReceber contaReceber;
    private ContaReceberDao contaReceberDao;
    private Stage dialogStage;
    private Venda venda;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.contaReceberDao = new ContaReceberDao();
        this.contaReceber = new ContaReceber();

        ObservableList<ETipoPagamento> tiposPagamento =
                FXCollections.observableArrayList(
                        ETipoPagamento.A, ETipoPagamento.P
                );

        this.comboTipoPag.setOnAction(event -> {
            if (this.comboTipoPag.getValue().equals(ETipoPagamento.A)) {
                this.textNroParcelas.setDisable(true);
                this.textNroParcelas.setText("1");
            } else {
                this.textNroParcelas.setDisable(false);
            }
        });
        this.comboTipoPag.setItems(tiposPagamento);
        this.setDefaultValues();
    }

    private void setDefaultValues() {
        this.textValor.setText(BigDecimal.ZERO.toString());
        this.datePickerConta.setValue(LocalDate.now());
        this.datePickerVencimento.setValue(LocalDate.now());
        this.textValorParcela.setText(BigDecimal.ZERO.toString());
    }

    public void setContaReceber(ContaReceber contaReceber) {
        if (contaReceber.getVenda() != null) {
            this.venda = contaReceber.getVenda();
            this.textValor.setText(this.venda.getValorTotal().toString());
            this.textValor.setEditable(false);
            this.textVenda.setText(this.venda.getDescricao());
        }
        this.contaReceber = contaReceber;
        if (contaReceber.getId() != null) {
            this.textId.setText(contaReceber.getId().toString());
            this.textDescricao.setText(contaReceber.getDescricao());
            this.datePickerConta.setValue(contaReceber.getDataConta());
            this.datePickerVencimento.setValue(contaReceber.getDataVencimento());
            this.textAreaObservacao.setText(contaReceber.getObservacao());
            this.comboTipoPag.setValue(contaReceber.getTipoPagamento());
            this.textValor.setText(contaReceber.getValorConta().toString());
            this.textNroParcelas.setText(contaReceber.getNroParcelas().toString());
            this.textValorParcela.setText(contaReceber.getValorParcela().toString());
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
        contaReceber.setDescricao(textDescricao.getText());
        contaReceber.setDataConta(datePickerConta.getValue());
        contaReceber.setDataVencimento(datePickerVencimento.getValue());
        contaReceber.setObservacao(textAreaObservacao.getText());
        contaReceber.setValorConta(new BigDecimal(textValor.getText()));
        contaReceber.setTipoPagamento((ETipoPagamento) comboTipoPag.getSelectionModel().getSelectedItem());
        contaReceber.setValorParcela(new BigDecimal(textValorParcela.getText()));
        contaReceber.setNroParcelas(new Integer(textNroParcelas.getText()));

        if (this.venda != null) {
            List<ContaReceber> contaReceberList = new ArrayList<>();
            contaReceberList.add(this.contaReceber);
            this.venda.setContasAReceber(contaReceberList);
            this.dialogStage.close();
        } else {
            if (this.contaReceberDao.isValid(contaReceber)) {
                this.contaReceberDao.save(contaReceber);
                this.dialogStage.close();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erro");
                alert.setHeaderText("Erro ao salvar registro");
                alert.setContentText(this.contaReceberDao.getErrors(contaReceber));
                alert.showAndWait();
            }
        }


    }


}