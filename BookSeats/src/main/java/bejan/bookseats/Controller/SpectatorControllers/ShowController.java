package bejan.bookseats.Controller.SpectatorControllers;

import bejan.bookseats.Controller.LoginController;
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

public class ShowController {
    public Button bLogout;
    public Label lbName;
    public ListView lvAllShows;
    public Button bViewAllShows;
    public Button bTodayShow;
    public Button bYourReservations;
    private Service service;

    public void setService(Service service) {
        this.service = service;
        lbName.setText(service.getLoggedUser().getName());
        loadShows();
    }

    public void initialize() {
        bLogout.setOnAction(event -> logout());
        bViewAllShows.setOnAction(event -> loadShows());
        bTodayShow.setOnAction(event -> openTodayShow());
        bYourReservations.setOnAction(event -> openYourReservations());
    }

    private void openYourReservations() {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/bejan/bookseats/SpectatorViews/your_reservations_view.fxml"));
        try {
            Scene scene = new Scene(fxmlLoader.load());
            YourReservationsController yourReservationsController = fxmlLoader.getController();
            yourReservationsController.setService(service);

            Stage stage = (Stage) lbName.getScene().getWindow();
            stage.setTitle("Your reservations");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.out.println("Your reservations's show page failed!");
            throw new RuntimeException(e);
        }
    }

    private void openTodayShow() {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/bejan/bookseats/SpectatorViews/today_show_view.fxml"));
        try {
            Scene scene = new Scene(fxmlLoader.load());
            TodayShowController todayShowController = fxmlLoader.getController();
            todayShowController.setService(service);

            Stage stage = (Stage) lbName.getScene().getWindow();
            stage.setTitle("Today's show");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.out.println("Open today's show page failed!");
            throw new RuntimeException(e);
        }
    }


    private void loadShows() {
        ObservableList<Pane> items = FXCollections.observableArrayList();

        List<Show> shows = service.getShows();
        lvAllShows.getItems().clear();
        shows.forEach(show ->{
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/bejan/bookseats/SpectatorViews/show_card.fxml"));
            try {
                Pane pane = fxmlLoader.load();
                ShowCardController showCardController = fxmlLoader.getController();
                showCardController.setShow(show);
                items.add(pane);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        lvAllShows.setItems(items);
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
