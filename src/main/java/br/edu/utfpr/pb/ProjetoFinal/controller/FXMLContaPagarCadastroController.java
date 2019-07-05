package br.edu.utfpr.pb.ProjetoFinal.controller;

import br.edu.utfpr.pb.ProjetoFinal.dao.ContaReceberDao;
import br.edu.utfpr.pb.ProjetoFinal.model.ContaReceber;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
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

    private ContaReceber contaReceber;
    private ContaReceberDao contaReceberDao;
    private Stage dialogStage;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.contaReceberDao = new ContaReceberDao();
        this.contaReceber = new ContaReceber();
    }

    public void setContaReceber(ContaReceber contaReceber) {
        this.contaReceber = contaReceber;
        if (contaReceber.getId() != null) {
            this.textId.setText(contaReceber.getId().toString());
            this.textDescricao.setText(contaReceber.getDescricao());
            this.datePickerConta.setValue(contaReceber.getDataConta());
            this.datePickerVencimento.setValue(contaReceber.getDataVencimento());
            this.textAreaObservacao.setText(contaReceber.getObservacao());
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