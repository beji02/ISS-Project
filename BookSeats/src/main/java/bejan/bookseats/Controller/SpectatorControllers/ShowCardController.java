package bejan.bookseats.Controller.SpectatorControllers;

import bejan.bookseats.Domain.Show;
import javafx.scene.control.Label;

public class ShowCardController {
    public Label lTitle;
    public Label lDescription;
    public Label lDateTime;
    public Label lSeats;

    public void setShow(Show show) {
        lTitle.setText(show.getTitle());
        lDescription.setText(show.getDescription());
        lDateTime.setText(show.getDate().toString() + " " + show.getTime().toString());
        lSeats.setText("Seats: " + show.getNoOccupiedSeats().toString()+"/"+show.getNoSeats().toString());
    }
}
