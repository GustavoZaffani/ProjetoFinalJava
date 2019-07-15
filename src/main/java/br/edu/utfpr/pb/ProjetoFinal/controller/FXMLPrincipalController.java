package br.edu.utfpr.pb.ProjetoFinal.controller;

import br.edu.utfpr.pb.ProjetoFinal.db.DatabaseConnection;
import br.edu.utfpr.pb.ProjetoFinal.model.Usuario;
import br.edu.utfpr.pb.ProjetoFinal.util.GenerateReport;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import net.sf.jasperreports.view.JasperViewer;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * @author Gustavo Zaffani
 */
public class FXMLPrincipalController implements Initializable {

    @FXML
    private VBox boxPrincipal;
    @FXML
    private TitledPane paneRelatorios;
    @FXML
    private Menu menuRelatorios;
    @FXML
    private Button btnUsuario;
    @FXML
    private MenuItem menuUsuario;

    private Usuario usuarioAutenticado;

    public void setUsuarioAutenticado(Usuario usuario) {
        this.usuarioAutenticado = usuario;
        if (usuarioAutenticado.getIsAdministrador().equals(Boolean.FALSE)) {
            this.paneRelatorios.setVisible(false);
            this.menuRelatorios.setVisible(false);
            this.menuUsuario.setVisible(false);
            this.btnUsuario.setVisible(false);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    public void setDataPane(Node node) {
        boxPrincipal.getChildren().setAll(node);
    }

    public VBox openVBox(String url) throws IOException {
        VBox v = (VBox) FXMLLoader.load(
                this.getClass().getResource(url));
        FadeTransition ft = new FadeTransition(
                Duration.millis(1000));
        ft.setNode(v);
        ft.setFromValue(0.1);
        ft.setToValue(1);
        ft.setCycleCount(1);
        ft.setAutoReverse(false);
        ft.play();
        return v;
    }

    @FXML
    public void loadCategoria(ActionEvent event) throws IOException {
        setDataPane(openVBox(
                "/fxml/FXMLCategoriaLista.fxml"
        ));
    }

    @FXML
    public void loadProduto(ActionEvent event) throws IOException {
        setDataPane(openVBox(
                "/fxml/FXMLProdutoLista.fxml"
        ));
    }

    @FXML
    public void loadUsuario(ActionEvent event) throws IOException {
        setDataPane(openVBox(
                "/fxml/FXMLUsuarioLista.fxml"
        ));
    }

    @FXML
    public void loadMarca(ActionEvent event) throws IOException {
        setDataPane(openVBox(
                "/fxml/FXMLMarcaLista.fxml"
        ));
    }

    @FXML
    public void loadCliente(ActionEvent event) throws IOException {
        setDataPane(openVBox(
                "/fxml/FXMLClienteLista.fxml"
        ));
    }

    @FXML
    public void loadFornecedor(ActionEvent event) throws IOException {
        setDataPane(openVBox(
                "/fxml/FXMLFornecedorLista.fxml"
        ));
    }

    @FXML
    public void loadContaReceber(ActionEvent event) throws IOException {
        setDataPane(openVBox(
                "/fxml/FXMLContaReceberLista.fxml"
        ));
    }

    @FXML
    public void loadContaPagar(ActionEvent event) throws IOException {
        setDataPane(openVBox(
                "/fxml/FXMLContaPagarLista.fxml"
        ));
    }

    @FXML
    public void loadVendas(ActionEvent event) throws IOException {
        setDataPane(openVBox(
                "/fxml/FXMLVendaLista.fxml"
        ));
    }

    @FXML
    public void loadCompras(ActionEvent event) throws IOException {
        setDataPane(openVBox(
                "/fxml/FXMLCompraLista.fxml"
        ));
    }

    @FXML
    private void showReportProduto(ActionEvent event) {
        GenerateReport generateReport = new GenerateReport();
        InputStream file = this.getClass().getResourceAsStream("/report/produtos.jasper");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("TITULO", "Lista de Produtos");

        DatabaseConnection conn = DatabaseConnection.getInstance();
        try {
            JasperViewer viewer = generateReport.getReport(
                    conn.getConnection(), parameters, file);
            viewer.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Falha ao exibir relatório!");
            alert.setContentText("Falha ao exibir relatório!");
            alert.showAndWait();
        }
    }

    @FXML
    private void showReportCliente(ActionEvent event) {
        GenerateReport generateReport = new GenerateReport();
        InputStream file = this.getClass().getResourceAsStream("/report/clientes.jasper");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("TITULO", "Lista de Clientes");

        DatabaseConnection conn = DatabaseConnection.getInstance();
        try {
            JasperViewer viewer = generateReport.getReport(
                    conn.getConnection(), parameters, file);
            viewer.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Falha ao exibir relatório!");
            alert.setContentText("Falha ao exibir relatório!");
            alert.showAndWait();
        }
    }

    @FXML
    private void showReportFornecedor(ActionEvent event) {
        GenerateReport generateReport = new GenerateReport();
        InputStream file = this.getClass().getResourceAsStream("/report/fornecedores.jasper");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("TITULO", "Lista de Fornecedores");

        DatabaseConnection conn = DatabaseConnection.getInstance();
        try {
            JasperViewer viewer = generateReport.getReport(
                    conn.getConnection(), parameters, file);
            viewer.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Falha ao exibir relatório!");
            alert.setContentText("Falha ao exibir relatório!");
            alert.showAndWait();
        }
    }

    @FXML
    private void showReportReceberVencidas(ActionEvent event) {
        GenerateReport generateReport = new GenerateReport();
        InputStream file = this.getClass().getResourceAsStream("/report/receberVencidas.jasper");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("TITULO", "Contas a Receber - Vencidas");

        DatabaseConnection conn = DatabaseConnection.getInstance();
        try {
            JasperViewer viewer = generateReport.getReport(
                    conn.getConnection(), parameters, file);
            viewer.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Falha ao exibir relatório!");
            alert.setContentText("Falha ao exibir relatório!");
            alert.showAndWait();
        }
    }
}