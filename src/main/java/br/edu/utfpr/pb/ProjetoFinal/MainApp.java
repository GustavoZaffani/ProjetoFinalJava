package br.edu.utfpr.pb.ProjetoFinal;

import br.edu.utfpr.pb.ProjetoFinal.dao.MarcaDao;
import br.edu.utfpr.pb.ProjetoFinal.model.Marca;
import javafx.application.Application;
import javafx.stage.Stage;

public class MainApp extends Application {

    public void start(Stage primaryStage) throws Exception {
        Marca marca = new Marca();
        marca.setDescricao("Teste insert");
        new MarcaDao().save(marca);
        System.exit(0);
    }
}