package bejan.bookseats.Controller.SpectatorControllers;

import bejan.bookseats.Controller.LoginController;
import bejan.bookseats.Main;
import bejan.bookseats.Service.Service;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class RegisterController {
    public TextField tfEmail;
    public TextField tfName;
    public TextField tfUsername;
    public TextField tfPassword;
    public Button bRegister;
    public Hyperlink hlLogin;
    private Service service;

    public void setService(Service service) {
        this.service = service;
    }

    public void initialize() {
        bRegister.setOnAction(event -> register());
        hlLogin.setOnAction(event -> openLogin());
    }

    private void openLogin() {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("login_view.fxml"));
        try {
            Scene scene = new Scene(fxmlLoader.load());
            LoginController loginController = fxmlLoader.getController();
            loginController.setService(service);

            Stage stage = (Stage) bRegister.getScene().getWindow();
            stage.setTitle("Login");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.out.println("Open login failed!");
            throw new RuntimeException(e);
        }
    }

    private void register() {
        if(service.register(tfUsername.getText(), tfEmail.getText(), tfName.getText(), tfPassword.getText()) != null) {
            System.out.println("Register successful!");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Register");
            alert.setHeaderText(null);
            alert.setContentText("Register successful!");
            alert.showAndWait();
        } else {
            System.out.println("Register failed!");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Register");
            alert.setHeaderText(null);
            alert.setContentText("Register failed! Username already exists!");
            alert.showAndWait();
        }
    }
}
