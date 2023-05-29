package bejan.bookseats.Controller.SpectatorControllers;

import bejan.bookseats.Domain.Reservation;
import bejan.bookseats.Domain.Show;
import bejan.bookseats.Domain.User;
import javafx.scene.control.Label;

import java.util.ArrayList;
import java.util.List;

public class ReservationCardController {
    public Label lTitle;
    public Label lGeneralInfo;
    public Label lSeats;

    public void setReservation(Show show, User user) {
        List<Reservation> reservations = new ArrayList<>();
        for (Reservation reservation : user.getOwnReservations()) {
            if (reservation.getShow().equals(show)) {
                reservations.add(reservation);
            }
        }

        lTitle.setText("Show: " + show.getTitle());
        lGeneralInfo.setText("Date: " + show.getDate().toString() + "\nTime: " + show.getTime().toString() + "\nLocation: " + show.getLocation());

        String seats = "Seats: ";
        for (Reservation reservation : reservations) {
            seats += "(" + reservation.getRow() + ", " + reservation.getCol() + ") ";
        }

        lSeats.setText(seats);
    }
}
