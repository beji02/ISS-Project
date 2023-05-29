package bejan.bookseats.Controller.SpectatorControllers;

import bejan.bookseats.Controller.LoginController;
import bejan.bookseats.Domain.Show;
import bejan.bookseats.Main;
import bejan.bookseats.Service.Service;
import bejan.bookseats.Utils.Pair;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ChooseSeatsController {
    public Button bLogout;
    public Label lbName;
    public Label lbPageTitle;
    public Pane pInfo;
    public Button bBook;
    public Button bViewAllShows;
    public Button bTodayShow;
    public Button bYourReservations;
    public Label lSelected;

    private Service service;
    private Integer noSeats;

    private List<Pair<Integer, Integer>> selectedSeats;

    public void initialize() {
        bLogout.setOnAction(event -> logout());
        bViewAllShows.setOnAction(event -> openAllShows());
        bTodayShow.setOnAction(event -> openTodayShow());
        bYourReservations.setOnAction(event -> openYourReservations());
        bBook.setDisable(true);
        bBook.setOnAction(event -> bookSeats());
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
        try {
            service.bookChosenSeats(service.getTodayShow(), selectedSeats);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText("Success");
            alert.setContentText("Seats booked successfully!");
            alert.showAndWait();
            openTodayShow();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    private void loadSeats(Show show) {
        List<Pair<Integer, Integer>> occupiedSeats = service.getAllOccupiedSeats(show);
        List<Pair<Integer, Integer>> emptySeats = service.getAllEmptySeats(show);

        System.out.println(occupiedSeats);
        System.out.println(emptySeats);

        occupiedSeats.forEach(pair -> createSeat(pair.getFirst(), pair.getSecond(), false));
        emptySeats.forEach(pair -> createSeat(pair.getFirst(), pair.getSecond(), true));
    }

    public void createSeat(Integer i, Integer j, Boolean available) {
        Integer size = 40;
        Integer padding = 5;
        Integer displacement = 10;

        Button b = new Button();

        b.setPrefSize(size, size);
        b.setText(i + " " + j);
        b.setLayoutX((size + padding) * (j-1)+displacement);
        b.setLayoutY((size + padding) * (i-1)+displacement);

        if(available) {
            b.setStyle("-fx-background-color: #00ff00");
            b.setOnAction(event -> {
                if(selectedSeats.contains(new Pair<>(i, j))) {
                    b.setStyle("-fx-background-color: #00ff00");
                    selectedSeats.remove(new Pair<>(i, j));
                    lSelected.setText("Selected seats: " + selectedSeats.size() + "/" + noSeats);
                    bBook.setDisable(true);
                }
                else if(selectedSeats.size() < noSeats) {
                    b.setStyle("-fx-background-color: #ffff00");
                    selectedSeats.add(new Pair<>(i, j));
                    lSelected.setText("Selected seats: " + selectedSeats.size() + "/" + noSeats);
                    if(selectedSeats.size() == noSeats) {
                        bBook.setDisable(false);
                    }
                }
            });
        } else {
            b.setDisable(true);
        }

        pInfo.getChildren().add(b);
    }

    public void setService(Service service) {
        this.service = service;
        lbName.setText(service.getLoggedUser().getName());
        loadSeats(service.getTodayShow());
    }


    public void setNoSeats(Integer noSeats) {
        this.noSeats = noSeats;
        selectedSeats = new ArrayList<>();
        lSelected.setText("Selected seats: 0/" + noSeats);
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
