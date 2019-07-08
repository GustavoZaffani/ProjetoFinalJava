package br.edu.utfpr.pb.ProjetoFinal.controller;

import br.edu.utfpr.pb.ProjetoFinal.dao.CidadeDao;
import br.edu.utfpr.pb.ProjetoFinal.dao.EstadoDao;
import br.edu.utfpr.pb.ProjetoFinal.dao.FornecedorDao;
import br.edu.utfpr.pb.ProjetoFinal.model.Cidade;
import br.edu.utfpr.pb.ProjetoFinal.model.Estado;
import br.edu.utfpr.pb.ProjetoFinal.model.Fornecedor;
import br.edu.utfpr.pb.ProjetoFinal.util.ValidatorUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Gustavo Zaffani
 */
public class FXMLFornecedorCadastroController implements Initializable {

    @FXML
    private TextField textId;
    @FXML
    private TextField textRazaoSocial;
    @FXML
    private TextField textNomeFantasia;
    @FXML
    private TextField textCnpj;
    @FXML
    private TextField textIe;
    @FXML
    private DatePicker datePickerFundacao;
    @FXML
    private ComboBox comboEstado;
    @FXML
    private ComboBox comboCidade;
    @FXML
    private TextField textNumero;

    private FornecedorDao fornecedorDao;
    private EstadoDao estadoDao;
    private CidadeDao cidadeDao;
    private Stage stage;
    private Fornecedor fornecedor;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.fornecedorDao = new FornecedorDao();
        this.fornecedor = new Fornecedor();
        this.estadoDao = new EstadoDao();
        this.cidadeDao = new CidadeDao();
        ObservableList<Estado> estados =
                FXCollections.observableArrayList(
                        this.estadoDao.findAll()
                );
        this.comboEstado.setItems(estados);

        this.comboEstado.setOnAction(event -> {
            ObservableList<Cidade> cidades =
                    FXCollections.observableArrayList(
                            this.cidadeDao.findCidadeByEstado(
                                    ((Estado) this.comboEstado.getSelectionModel()
                                            .getSelectedItem()).getId())
                    );
            this.comboCidade.setItems(cidades);
        });
    }

    public void setDialogStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private void cancel() {
        this.stage.close();
    }

    @FXML
    private void save() {
        fornecedor.setRazaoSocial(textRazaoSocial.getText());
        fornecedor.setNomeFantasia(textNomeFantasia.getText());
        fornecedor.setIe(textIe.getText());
        fornecedor.setCnpj(textCnpj.getText());
        fornecedor.setNro(textNumero.getText());
        fornecedor.setDataFundacao(datePickerFundacao.getValue());
        fornecedor.setEstado(((Estado) comboEstado.getSelectionModel().getSelectedItem()));
        fornecedor.setCidade(((Cidade) comboCidade.getSelectionModel().getSelectedItem()));

        if (this.fornecedorDao.isValid(fornecedor) &&
                ValidatorUtil.isCNPJ(this.textCnpj.getText())) {
            this.fornecedorDao.save(fornecedor);
            this.stage.close();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Erro ao salvar registro");
            if (this.fornecedorDao.getErrors(fornecedor).equals("")) {
                alert.setContentText("O campo 'CNPJ' deve ser válido!");
            } else {
                alert.setContentText(this.fornecedorDao.getErrors(fornecedor));
            }
            alert.showAndWait();
        }
    }

    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
        if (fornecedor.getId() != null) {
            this.textId.setText(fornecedor.getId().toString());
            this.textRazaoSocial.setText(fornecedor.getRazaoSocial());
            this.textNomeFantasia.setText(fornecedor.getNomeFantasia());
            this.textCnpj.setText(fornecedor.getCnpj());
            this.textIe.setText(fornecedor.getIe());
            this.textNumero.setText(fornecedor.getNro());
            this.datePickerFundacao.setValue(fornecedor.getDataFundacao());
            this.comboCidade.setValue(fornecedor.getCidade());
            this.comboEstado.setValue(fornecedor.getEstado());
        }
    }
}
