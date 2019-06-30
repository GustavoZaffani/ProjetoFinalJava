package br.edu.utfpr.pb.ProjetoFinal.controller;

import br.edu.utfpr.pb.ProjetoFinal.dao.MarcaDao;
import br.edu.utfpr.pb.ProjetoFinal.model.Marca;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class FXMLMarcaCadastroController implements Initializable {

    @FXML
    private TextField textId;
    @FXML
    private TextField textDescricao;
    private MarcaDao marcaDao;
    private Stage stage;
    private Marca marca;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.marcaDao = new MarcaDao();
        this.marca = new Marca();
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
        marca.setDescricao(textDescricao.getText());
        this.marcaDao.save(marca);
        this.stage.close();
    }

    public void setMarca(Marca marca) {
        this.marca = marca;
        if (marca.getId() != null) {
            this.textId.setText(marca.getId().toString());
            this.textDescricao.setText(marca.getDescricao());
        }
    }
}
