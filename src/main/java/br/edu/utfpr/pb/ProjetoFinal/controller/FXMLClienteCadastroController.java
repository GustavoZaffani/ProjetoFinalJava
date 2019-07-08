package br.edu.utfpr.pb.ProjetoFinal.controller;

import br.edu.utfpr.pb.ProjetoFinal.dao.CidadeDao;
import br.edu.utfpr.pb.ProjetoFinal.dao.ClienteDao;
import br.edu.utfpr.pb.ProjetoFinal.dao.EstadoDao;
import br.edu.utfpr.pb.ProjetoFinal.enumeration.EOperadora;
import br.edu.utfpr.pb.ProjetoFinal.enumeration.ETipoContato;
import br.edu.utfpr.pb.ProjetoFinal.model.Cidade;
import br.edu.utfpr.pb.ProjetoFinal.model.Cliente;
import br.edu.utfpr.pb.ProjetoFinal.model.Contato;
import br.edu.utfpr.pb.ProjetoFinal.model.Estado;
import br.edu.utfpr.pb.ProjetoFinal.util.ValidatorUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author Gustavo Zaffani
 */
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
    private ImageView imageFoto;
    @FXML
    private Button buttonFoto;
    @FXML
    private TextField textTelefone;
    @FXML
    private ComboBox comboOperadora;
    @FXML
    private ComboBox comboTipo;
    @FXML
    private TableView<Contato> tableData;
    @FXML
    private TableColumn<Contato, Long> columnCod;
    @FXML
    private TableColumn<Contato, String> columnTelefone;
    @FXML
    private TableColumn<Contato, ETipoContato> columnTipo;
    @FXML
    private TableColumn<Contato, EOperadora> columnOperadora;

    private ClienteDao clienteDao;
    private EstadoDao estadoDao;
    private CidadeDao cidadeDao;
    private Stage stage;
    private Cliente cliente;
    private List<Contato> contatos;
    private ObservableList<Contato> list = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.clienteDao = new ClienteDao();
        this.cliente = new Cliente();
        this.estadoDao = new EstadoDao();
        this.cidadeDao = new CidadeDao();
        this.contatos = new ArrayList<>();
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

        ObservableList<EOperadora> operadoras =
                FXCollections.observableArrayList(
                        EOperadora.OI, EOperadora.CLARO, EOperadora.TIM, EOperadora.VIVO
                );
        this.comboOperadora.setItems(operadoras);

        ObservableList<ETipoContato> tipoContatos =
                FXCollections.observableArrayList(
                        ETipoContato.CELULAR, ETipoContato.COMERCIAL, ETipoContato.RESIDENCIAL
                );
        this.comboTipo.setItems(tipoContatos);

        setColumnProperties();
        loadData();
    }

    private void setColumnProperties() {
        this.columnCod.setCellValueFactory(
                new PropertyValueFactory<>("id")
        );
        this.columnOperadora.setCellValueFactory(
                new PropertyValueFactory<>("operadora")
        );
        this.columnTipo.setCellValueFactory(
                new PropertyValueFactory<>("tipoContato")
        );
        this.columnTelefone.setCellValueFactory(
                new PropertyValueFactory<>("telefone")
        );
    }

    private void loadData() {
        this.list.clear();
        this.list.addAll(this.contatos);
        tableData.setItems(list);
    }

    @FXML
    private void insertContato() {
        if (this.comboTipo.getValue() != null
                && this.textTelefone.getText() != null) {
            Contato contato = new Contato();
            contato.setCliente(this.cliente);
            contato.setTelefone(this.textTelefone.getText());
            contato.setOperadora((EOperadora) this.comboOperadora.getSelectionModel().getSelectedItem());
            contato.setTipoContato((ETipoContato) this.comboTipo.getSelectionModel().getSelectedItem());
            contatos.add(contato);
        }
        loadData();
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
        cliente.setContatos(this.contatos);
        if (this.clienteDao.isValid(cliente) &&
                ValidatorUtil.isCPF(textCpf.getText())) {
            this.clienteDao.save(cliente);
            this.stage.close();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Erro ao salvar registro");

            if (this.clienteDao.getErrors(cliente).equals("")) {
                alert.setContentText("O campo 'Cpf' deve ser válido!");
            } else {
                alert.setContentText(this.clienteDao.getErrors(cliente));
            }
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
            this.contatos = cliente.getContatos();
            try {
                if (cliente.getFoto() != null) {
                    Image imagem = new Image(new ByteArrayInputStream(cliente.getFoto()));
                    imageFoto.setImage(imagem);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        loadData();
    }

    @FXML
    private void loadImage() {
        try {
            final FileChooser fileChooser = new FileChooser();
            File file = fileChooser.showOpenDialog(stage);
            if (file != null) {
                Image imagem = new Image(file.toURI().toString());
                imageFoto.setImage(imagem);
                cliente.setFoto(Files.readAllBytes(file.toPath()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
