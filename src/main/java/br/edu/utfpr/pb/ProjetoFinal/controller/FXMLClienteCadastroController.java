package br.edu.utfpr.pb.ProjetoFinal.controller;

import br.edu.utfpr.pb.ProjetoFinal.dao.CidadeDao;
import br.edu.utfpr.pb.ProjetoFinal.dao.ClienteDao;
import br.edu.utfpr.pb.ProjetoFinal.dao.EstadoDao;
import br.edu.utfpr.pb.ProjetoFinal.model.Cidade;
import br.edu.utfpr.pb.ProjetoFinal.model.Cliente;
import br.edu.utfpr.pb.ProjetoFinal.model.Estado;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.util.ResourceBundle;

public class FXMLClienteCadastroController implements Initializable {

    @FXML
    private TextField textId;
    @FXML
    private TextField textNome;
    @FXML
    private TextField textCpf;
    @FXML
    private DatePicker datePickerNascimento;
    @FXML
    private ComboBox comboEstado;
    @FXML
    private ComboBox comboCidade;
    @FXML
    private TextField textNumero;
    @FXML
    private ImageView image;
    @FXML
    private Button buttonFoto;
    @FXML
    private Button buttonRemoveFoto;

    private ClienteDao clienteDao;
    private EstadoDao estadoDao;
    private CidadeDao cidadeDao;
    private Stage stage;
    private Cliente cliente;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.clienteDao = new ClienteDao();
        this.cliente = new Cliente();
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
        cliente.setNome(textNome.getText());
        cliente.setCpf(textCpf.getText());
        cliente.setNro(textNumero.getText());
        cliente.setDataNascimento(datePickerNascimento.getValue());
        cliente.setEstado(((Estado) comboEstado.getSelectionModel().getSelectedItem()));
        cliente.setCidade(((Cidade) comboCidade.getSelectionModel().getSelectedItem()));

        if (this.clienteDao.isValid(cliente)) {
            this.clienteDao.save(cliente);
            this.stage.close();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Erro ao salvar registro");
            alert.setContentText(this.clienteDao.getErrors(cliente));
            alert.showAndWait();
        }
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
        if (cliente.getId() != null) {
            this.textId.setText(cliente.getId().toString());
            this.textNome.setText(cliente.getNome());
            this.textCpf.setText(cliente.getCpf());
            this.textNumero.setText(cliente.getNro());
            this.datePickerNascimento.setValue(cliente.getDataNascimento());
            this.comboCidade.setValue(cliente.getCidade());
            this.comboEstado.setValue(cliente.getEstado());
            try {
                if (cliente.getFoto() != null) {
                    Image imagem = new Image(new ByteArrayInputStream(cliente.getFoto()));
                    image.setImage(imagem);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void loadImage() {
        try {
            final FileChooser fileChooser = new FileChooser();
            File file = fileChooser.showOpenDialog(stage);
            if (file != null) {
                Image imagem = new Image(file.toURI().toString());
                image.setImage(imagem);
                cliente.setFoto(Files.readAllBytes(file.toPath()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
