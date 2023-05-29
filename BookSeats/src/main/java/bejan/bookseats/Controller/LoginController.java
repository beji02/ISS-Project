package bejan.bookseats.Controller;

import bejan.bookseats.Controller.ManagerControllers.AdminShowController;
import bejan.bookseats.Controller.SpectatorControllers.RegisterController;
import bejan.bookseats.Controller.SpectatorControllers.ShowController;
import bejan.bookseats.Domain.User;
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

public class LoginController {
    public TextField tfUsername;
    public TextField tfPassword;
    public Button bLogin;
    public Hyperlink hlRegister;

    private Service service;

    public void setService(Service service) {
        this.service = service;
    }

    public void initialize() {
        bLogin.setOnAction(event -> login());
        hlRegister.setOnAction(event -> openRegister());
    }

    private void openRegister() {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/bejan/bookseats/SpectatorViews/register_view.fxml"));
        try {
            Scene scene = new Scene(fxmlLoader.load());
            RegisterController registerController = fxmlLoader.getController();
            registerController.setService(service);

            Stage stage = (Stage) bLogin.getScene().getWindow();
            stage.setTitle("Register");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.out.println("Open register failed!");
            throw new RuntimeException(e);
        }

    }

    public void login() {
        String username = tfUsername.getText();
        String password = tfPassword.getText();

        User user = service.login(username, password);
        if (user != null) {
            System.out.println("Login successful!");

            if(user.isAdmin()) {
                FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/bejan/bookseats/ManagerViews/admin_show_view.fxml"));
                try {
                    Scene scene = new Scene(fxmlLoader.load());
                    AdminShowController adminShowController = fxmlLoader.getController();
                    adminShowController.setService(service);

                    Stage stage = (Stage) bLogin.getScene().getWindow();
                    stage.setTitle("Shows");
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            else{
                FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/bejan/bookseats/SpectatorViews/show_view.fxml"));
                try {
                    Scene scene = new Scene(fxmlLoader.load());
                    ShowController showController = fxmlLoader.getController();
                    showController.setService(service);

                    Stage stage = (Stage) bLogin.getScene().getWindow();
                    stage.setTitle("Shows");
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }


        } else {
            System.out.println("Login failed!");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Login failed!");
            alert.setContentText("Username or password incorrect!");
            alert.showAndWait();
        }
    }
}
