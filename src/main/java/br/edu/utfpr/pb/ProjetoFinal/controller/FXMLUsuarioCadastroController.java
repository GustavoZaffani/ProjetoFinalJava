package br.edu.utfpr.pb.ProjetoFinal.controller;

import br.edu.utfpr.pb.ProjetoFinal.dao.UsuarioDao;
import br.edu.utfpr.pb.ProjetoFinal.model.Usuario;
import br.edu.utfpr.pb.ProjetoFinal.util.CriptografiaUtil;
import br.edu.utfpr.pb.ProjetoFinal.util.ValidatorUtil;
import javafx.event.ActionEvent;
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

/**
 * @author Gustavo Zaffani
 */
public class FXMLUsuarioCadastroController implements Initializable {

    @FXML
    private TextField textId;
    @FXML
    private TextField textNome;
    @FXML
    private TextField textCpf;
    @FXML
    private TextField textUsuario;
    @FXML
    private PasswordField textSenha;
    @FXML
    private DatePicker dateDataNascimento;
    @FXML
    private CheckBox checkAtivo;
    @FXML
    private CheckBox checkAdm;
    @FXML
    private ImageView imageFoto;
    @FXML
    private Button buttonFoto;
    private Usuario usuario;
    private UsuarioDao usuarioDao;
    private Stage dialogStage;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.usuarioDao = new UsuarioDao();
        this.usuario = new Usuario();
        this.buttonFoto.setOnAction((final ActionEvent e) -> {
            loadImage();
        });
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
        if (usuario.getId() != null) {
            textId.setText(usuario.getId().toString());
            textNome.setText(usuario.getNome());
            textCpf.setText(usuario.getCpf());
            textUsuario.setText(usuario.getUsuario());
            textSenha.setText(CriptografiaUtil.descriptografa(usuario.getSenha()));
            dateDataNascimento.setValue(usuario.getDataNascimento());
            checkAtivo.setSelected(usuario.getAtivo());
            checkAdm.setSelected(usuario.getAdministrador());
            try {
                if (usuario.getFoto() != null) {
                    Image image = new Image(new ByteArrayInputStream(usuario.getFoto()));
                    imageFoto.setImage(image);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    private void loadImage() {
        try {
            final FileChooser fileChooser = new FileChooser();
            File file = fileChooser.showOpenDialog(dialogStage);
            if (file != null) {
                Image image = new Image(file.toURI().toString());
                imageFoto.setImage(image);
                usuario.setFoto(Files.readAllBytes(file.toPath()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void cancel() {
        this.dialogStage.close();
    }

    @FXML
    private void save() {
        usuario.setNome(textNome.getText());
        usuario.setCpf(textCpf.getText());
        usuario.setUsuario(textUsuario.getText());
        usuario.setSenha(CriptografiaUtil.criptografa(textSenha.getText()));
        usuario.setDataNascimento(dateDataNascimento.getValue());
        usuario.setAtivo(checkAtivo.isSelected());
        usuario.setAdministrador(checkAdm.isSelected());

        if (this.usuarioDao.isValid(usuario) &&
                ValidatorUtil.isCPF(this.usuario.getCpf())) {
            this.usuarioDao.save(usuario);
            this.dialogStage.close();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Erro ao salvar registro");

            if (this.usuarioDao.getErrors(usuario).equals("")) {
                alert.setContentText("O campo 'CPF' deve ser válido!");
            } else {
                alert.setContentText(this.usuarioDao.getErrors(usuario));
            }
            alert.showAndWait();
        }
    }
}
