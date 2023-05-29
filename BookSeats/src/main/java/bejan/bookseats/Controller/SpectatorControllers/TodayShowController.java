package bejan.bookseats.Controller.SpectatorControllers;

import bejan.bookseats.Controller.LoginController;
import bejan.bookseats.Domain.Show;
import bejan.bookseats.Main;
import bejan.bookseats.Service.Service;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;

public class TodayShowController {
    public Button bLogout;
    public Label lbName;
    public Label lbPageTitle;
    public Label lTitle;
    public Label lArtsti;
    public Label lDescription;
    public Label lGeneralInfo;
    public Button bBook;
    public Spinner spSeats;
    public Button bChoose;
    public Label lbAvailable;
    public Pane pInfo;
    public Button bViewAllShows;
    public Button bTodayShow;
    public Button bYourReservations;

    private Service service;

    public void setService(Service service) {
        this.service = service;
        lbName.setText(service.getLoggedUser().getName());
        loadTodayShow();
    }

    private void loadTodayShow() {
        try {
            Show show = service.getTodayShow();
            lTitle.setText(show.getTitle());
            lArtsti.setText("Artists: " + show.getArtists());
            lDescription.setText("Description:\n" + show.getDescription());
            lGeneralInfo.setText("Date: " + show.getDate().toString() + "\nTime: " + show.getTime().toString() + "\nLocation: " + show.getLocation());
            lbAvailable.setText("(available seats: " + (Show.getNoSeats() - show.getNoOccupiedSeats()) + "/" + Show.getNoSeats() + ")");
            bBook.setDisable(show.getNoAvailableSeats() == 0);
            bChoose.setDisable(show.getNoAvailableSeats() == 0);
            if(show.getNoAvailableSeats() == 0)
                spSeats.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 0));
            else
                spSeats.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, show.getNoAvailableSeats()));
        } catch (EntityNotFoundException e) {
            pInfo.getChildren().clear();
            Label lNoShow = new Label("No show today!");
            pInfo.getChildren().add(lNoShow);
        }
    }

    public void initialize() {
        bLogout.setOnAction(event -> logout());
        bViewAllShows.setOnAction(event -> openAllShows());
        bTodayShow.setOnAction(event -> loadTodayShow());
        bYourReservations.setOnAction(event -> openYourReservations());
        bBook.setOnAction(event -> bookSeats());
        bChoose.setOnAction(event -> openChooseSeats((Integer) spSeats.getValue()));
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

    private void bookSeats() {
        service.bookSeats(service.getTodayShow(), (Integer) spSeats.getValue());
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText("Success");
        alert.setContentText("You have successfully booked " + spSeats.getValue() + " seats!");
        alert.showAndWait();
        loadTodayShow();
    }

    private void openChooseSeats(Integer noSeats) {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/bejan/bookseats/SpectatorViews/choose_seats_view.fxml"));
        try {
            Scene scene = new Scene(fxmlLoader.load());
            ChooseSeatsController chooseSeatsController = fxmlLoader.getController();
            chooseSeatsController.setService(service);
            chooseSeatsController.setNoSeats(noSeats);

            Stage stage = (Stage) bLogout.getScene().getWindow();
            stage.setTitle("Book'a seat");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
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
