package bejan.bookseats.Controller.ManagerControllers;

import bejan.bookseats.Controller.LoginController;
import bejan.bookseats.Controller.SpectatorControllers.ShowCardController;
import bejan.bookseats.Domain.Show;
import bejan.bookseats.Main;
import bejan.bookseats.Service.Service;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class AdminShowController {
    public Button bLogout;
    public Label lbName;
    public Label lbPageTitle;
    public Pane pInfo;
    public ListView lvShows;
    public Button bAdd;

    private Service service;

    public void setService(Service service) {
        this.service = service;
        lbName.setText(service.getLoggedUser().getName());
        loadShows();
    }

    public void initialize() {
        bLogout.setOnAction(event -> logout());
        bAdd.setOnAction(event -> openNewShow());
    }

    private void openNewShow() {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/bejan/bookseats/ManagerViews/new_show_view.fxml"));
        try {
            Scene scene = new Scene(fxmlLoader.load());
            NewShowController newShowController = fxmlLoader.getController();
            newShowController.setService(service);

            Stage stage = (Stage) lbName.getScene().getWindow();
            stage.setTitle("Book'a seat");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.out.println("Open new show page failed!");
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

    private void loadShows() {
        ObservableList<Pane> items = FXCollections.observableArrayList();

        List<Show> shows = service.getShows();
        lvShows.getItems().clear();
        shows.forEach(show ->{
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/bejan/bookseats/ManagerViews/admin_show_card.fxml"));
            try {
                Pane pane = fxmlLoader.load();
                AdminShowCardController adminShowCardController = fxmlLoader.getController();
                adminShowCardController.setShow(show);
                adminShowCardController.setDeleteFunction(this::deleteShow);
                adminShowCardController.setUpdateFunction(this::updateShow);
                items.add(pane);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        lvShows.setItems(items);
    }

    private void updateShow(Show show) {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/bejan/bookseats/ManagerViews/update_show_view.fxml"));
        try {
            Scene scene = new Scene(fxmlLoader.load());
            UpdateShowController updateShowController = fxmlLoader.getController();
            updateShowController.setService(service);
            updateShowController.setShow(show);

            Stage stage = (Stage) lbName.getScene().getWindow();
            stage.setTitle("Book'a seat");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.out.println("Open update show page failed!");
            throw new RuntimeException(e);
        }
    }

    private void deleteShow(Show show) {
        service.deleteShow(show);
        loadShows();
    }
}
