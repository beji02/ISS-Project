package bejan.bookseats.Controller.ManagerControllers;

import bejan.bookseats.Controller.LoginController;
import bejan.bookseats.Main;
import bejan.bookseats.Service.Service;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class NewShowController {
    public Button bLogout;
    public Label lbName;
    public Label lbPageTitle;
    public Pane pInfo;
    public TextField tfTitle;
    public TextField tfArtists;
    public DatePicker dpDate;
    public TextField tfTime;
    public TextField tfPrice;
    public TextArea taDescription;
    public TextField tfLocation;
    public Button bAdd;
    public Button bCancel;

    private Service service;

    public void setService(Service service) {
        this.service = service;
        lbName.setText(service.getLoggedUser().getName());
    }

    public void initialize() {
        bLogout.setOnAction(event -> logout());
        bCancel.setOnAction(event -> openAdminShow());
        bAdd.setOnAction(event -> addShow());
    }

    private void addShow() {
        String title = tfTitle.getText();
        String artists = tfArtists.getText();
        String date = dpDate.getValue().toString();
        String time = tfTime.getText();
        String price = tfPrice.getText();
        String description = taDescription.getText();
        String location = tfLocation.getText();

        if (title.isEmpty() || artists.isEmpty() || date.isEmpty() || time.isEmpty() || price.isEmpty() || description.isEmpty() || location.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error adding show!");
            alert.setContentText("All fields must be completed!");
            alert.showAndWait();
            return;
        }

        try {
            service.addShow(title, artists, date, time, price, description, location);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText("Show added successfully!");
            alert.showAndWait();
            openAdminShow();
        } catch (RuntimeException rex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error adding show!");
            alert.setContentText(rex.getMessage());
            alert.showAndWait();
        }
    }

    private void openAdminShow() {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/bejan/bookseats/ManagerViews/admin_show_view.fxml"));
        try {
            Scene scene = new Scene(fxmlLoader.load());
            AdminShowController adminShowController = fxmlLoader.getController();
            adminShowController.setService(service);

            Stage stage = (Stage) bLogout.getScene().getWindow();
            stage.setTitle("Shows");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void logout() {
        service.logout();
        System.out.println("Logout successful!");
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("login_view.fxml"));
        try {
            Scene scene = new Scene(fxmlLoader.load());
            LoginController loginController = fxmlLoader.getController();
            loginController.setService(service);

            Stage stage = (Stage) lbName.getScene().getWindow();
            stage.setTitle("Login");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.out.println("Open login failed!");
            throw new RuntimeException(e);
        }
    }
}
