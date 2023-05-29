package bejan.bookseats.Controller.SpectatorControllers;

import bejan.bookseats.Controller.LoginController;
import bejan.bookseats.Domain.Show;
import bejan.bookseats.Domain.User;
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
import java.util.ArrayList;
import java.util.List;

public class YourReservationsController {
    public Button bLogout;
    public Label lbName;
    public Label lbPageTitle;
    public Pane pInfo;
    public ListView lvReservations;
    public Button bViewAllShows;
    public Button bTodayShow;
    public Button bYourReservations;
    private Service service;

    public void setService(Service service) {
        this.service = service;
        lbName.setText(service.getLoggedUser().getName());
        loadReservations();
    }

    private void loadReservations() {
        ObservableList<Pane> items = FXCollections.observableArrayList();

        User user = service.getLoggedUser();

        List<Show> uniqueShows = new ArrayList<>();
        user.getOwnReservations().forEach(reservation -> {
                if(!uniqueShows.contains(reservation.getShow()))
                    uniqueShows.add(reservation.getShow());
        });

        uniqueShows.forEach(show->{
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/bejan/bookseats/SpectatorViews/reservation_card.fxml"));
            try {
                Pane pane = fxmlLoader.load();
                ReservationCardController reservationCardController = fxmlLoader.getController();
                reservationCardController.setReservation(show, user);
                items.add(pane);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        lvReservations.setItems(items);
    }

    public void initialize() {
        bLogout.setOnAction(event -> logout());
        bViewAllShows.setOnAction(event -> openAllShows());
        bTodayShow.setOnAction(event -> openTodayShow());
        bYourReservations.setOnAction(event -> loadReservations());
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

    private void openAllShows() {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/bejan/bookseats/SpectatorViews/show_view.fxml"));
        try {
            Scene scene = new Scene(fxmlLoader.load());
            ShowController showController = fxmlLoader.getController();
            showController.setService(service);

            Stage stage = (Stage) bLogout.getScene().getWindow();
            stage.setTitle("Book'a seat");
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
