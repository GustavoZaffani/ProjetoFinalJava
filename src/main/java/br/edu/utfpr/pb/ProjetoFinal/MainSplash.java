package br.edu.utfpr.pb.ProjetoFinal;

import br.edu.utfpr.pb.ProjetoFinal.dao.UsuarioDao;
import br.edu.utfpr.pb.ProjetoFinal.model.Usuario;
import br.edu.utfpr.pb.ProjetoFinal.util.CriptografiaUtil;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.IOException;
import java.time.LocalDate;

public class MainSplash extends Application {

    private Pane splashLayout;
    private ProgressBar loadProgress;
    private Label progressText;
    private static final int SPLASH_WIDTH = 850;
    private static final int SPLASH_HEIGHT = 450;

    public static void main(String[] args) throws Exception {
        launch(args);
    }

    @Override
    public void init() {
        Usuario usuario = new Usuario();
        usuario.setNome("Gustavo");
        usuario.setCpf("1234");
        usuario.setAdministrador(true);
        usuario.setAtivo(true);
        usuario.setDataNascimento(LocalDate.of(1999,07,18));
        usuario.setEmail("admin");
        usuario.setSenha(CriptografiaUtil.criptografa("admin"));
        System.out.println("crypt: " + usuario.getSenha());
        new UsuarioDao().save(usuario);
        ImageView splash = new ImageView(new Image(
                getClass().getResource("/images/imgLogo.jpg").toExternalForm()
        ));
        loadProgress = new ProgressBar();
        loadProgress.setPrefWidth(SPLASH_WIDTH - 20);
        loadProgress.setPrefHeight(25.0);
        loadProgress.setStyle("-fx-accent: gray;");
        progressText = new Label("Sistema ERP - LZSoftware");
        splashLayout = new VBox();
        splashLayout.getChildren().addAll(splash, loadProgress, progressText);
        progressText.setAlignment(Pos.CENTER);
        splashLayout.setStyle(
                "-fx-padding: 5; "
                        + "-fx-background-color: white; "
                        + "-fx-border-width:5; "
                        + "-fx-border-color: "
                        + "linear-gradient("
                        + "to bottom, "
                        + "black, "
                        + "derive(gray, 50%)"
                        + ");"
        );
        splashLayout.setEffect(new DropShadow());
    }

    @Override
    public void start(final Stage initStage) throws Exception {
        final Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                updateMessage("Conectando ao banco de dados . . .");
                UsuarioDao usuarioDao = new UsuarioDao();
                updateMessage("Iniciando o sistema.");
                return null;
            }
        };

        showSplash(
                initStage,
                task,
                () -> showMainStage()
        );
        new Thread(task).start();
    }

    private void showMainStage() {
        try {
            Parent root = FXMLLoader.load(
                    this.getClass()
                            .getResource("/fxml/FXMLLogin.fxml")
            );
            Scene scene = new Scene(root);
            scene.getStylesheets().add("/styles/bootstrap.css");
            Stage stage = new Stage();
            stage.centerOnScreen();
            stage.setTitle("Aula de JavaFX - OO24S");
            stage.setScene(scene);
            //stage.setMaximized(true);
            //stage.setResizable(true);

            Screen screen = Screen.getPrimary();
            Rectangle2D bounds = screen.getBounds();
            double sw = bounds.getWidth();
            double sh = bounds.getHeight();
            stage.setX((sw - 400) / 2);
            stage.setY((sh - 300) / 2);

            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void showSplash(final Stage initStage, Task<?> task,
                            InitCompletionHandler initCompletionHandler) {
        progressText.textProperty().bind(task.messageProperty());
        loadProgress.progressProperty().bind(task.progressProperty());
        task.stateProperty().addListener((observableValue, oldState, newState) -> {
            if (newState == Worker.State.SUCCEEDED) {
                loadProgress.progressProperty().unbind();
                loadProgress.setProgress(1);
                initStage.toFront();
                FadeTransition fadeSplash = new FadeTransition(Duration.seconds(1.2), splashLayout);
                fadeSplash.setFromValue(1.0);
                fadeSplash.setToValue(0.0);
                fadeSplash.setOnFinished(actionEvent -> initStage.hide());
                fadeSplash.play();
                initCompletionHandler.complete();
            }
        });

        Scene splashScene = new Scene(splashLayout, Color.TRANSPARENT);
        final Rectangle2D bounds = Screen.getPrimary().getBounds();
        initStage.setScene(splashScene);
        initStage.setX(bounds.getMinX() + bounds.getWidth() / 2 - SPLASH_WIDTH / 2);
        initStage.setY(bounds.getMinY() + bounds.getHeight() / 2 - SPLASH_HEIGHT / 2);
        initStage.initStyle(StageStyle.TRANSPARENT);
        initStage.setAlwaysOnTop(true);
        initStage.show();
    }

    public interface InitCompletionHandler {

        void complete();
    }
}