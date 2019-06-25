package br.edu.utfpr.pb.ProjetoFinal;

import br.edu.utfpr.pb.ProjetoFinal.dao.UsuarioDao;
import br.edu.utfpr.pb.ProjetoFinal.model.Usuario;
import br.edu.utfpr.pb.ProjetoFinal.util.CriptografiaUtil;
import javafx.application.Application;
import javafx.stage.Stage;

import java.time.LocalDate;

public class MainApp extends Application {

    public void start(Stage primaryStage) {
        Usuario usuario = new Usuario();
        usuario.setNome("Usuário teste");
        usuario.setCpf("12345678");
        usuario.setEmail("teste@teste.com");
        usuario.setDataNascimento(LocalDate.of(2019, 10, 10));
        usuario.setSenha(CriptografiaUtil.criptografa("#$@#%$%REREREdde43"));
        new UsuarioDao().save(usuario);

        Usuario user = new UsuarioDao().findById(1L);
        System.out.println("Senha banco: " + user.getSenha());
        System.out.println("Senha banco desc: " + CriptografiaUtil.descriptografa(user.getSenha()));

        System.exit(0);
    }
}