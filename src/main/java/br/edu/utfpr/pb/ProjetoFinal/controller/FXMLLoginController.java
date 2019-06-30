package br.edu.utfpr.pb.ProjetoFinal.controller;

import br.edu.utfpr.pb.ProjetoFinal.dao.UsuarioDao;
import br.edu.utfpr.pb.ProjetoFinal.model.Usuario;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Gustavo Zaffani
 */
public class FXMLLoginController implements Initializable {

    @FXML
    private TextField txtUsuario;
    @FXML
    private PasswordField txtSenha;
    @FXML
    private Button btnEntrar;
    private UsuarioDao usuarioDao;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.usuarioDao = new UsuarioDao();
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                txtUsuario.requestFocus();
            }
        });
        btnEntrar.setDefaultButton(true);
    }

    @FXML
    private void login() {
        try {
            Usuario usuario = this.usuarioDao.findByEmailNamedQuery(
                    txtUsuario.getText(), txtSenha.getText());
            if (usuario != null) {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(
                        this.getClass()
                                .getResource("/fxml/FXMLPrincipal.fxml"));
                VBox root = (VBox) loader.load();
                Scene scene = new Scene(root);
                scene.getStylesheets().add("/styles/style.css");
                Stage stage = new Stage();
                stage.setTitle("Projeto Final Java - OO24S");
                stage.setScene(scene);
                stage.setMaximized(true);
                stage.setResizable(true);

                FXMLPrincipalController controller = loader.getController();
                controller.setUsuarioAutenticado(usuario);

                stage.show();

                Stage stageLogin = (Stage) btnEntrar.getScene().getWindow();
                stageLogin.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Usuário e/ou senha inválidos!");
            alert.setContentText("Por favor, tente novamente!");
            alert.showAndWait();
        }
    }
}
