package bejan.bookseats.Controller.ManagerControllers;

import bejan.bookseats.Domain.Show;
import bejan.bookseats.Utils.IDeleteShowFunc;
import bejan.bookseats.Utils.IUpdateShowFunc;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import org.hibernate.sql.Delete;

import java.util.function.Function;

public class AdminShowCardController {
    public Label lTitle;
    public Label lPrice;
    public Label lDate;
    public Label lLocation;
    public Label lTime;
    public Button bUpdate;
    public Button bDelete;
    private Show show;

    public void setShow(Show show) {
        lTitle.setText(show.getTitle());
        lPrice.setText("Price: " + show.getPrice().toString());
        lDate.setText("Date: " + show.getDate().toString());
        lLocation.setText("Location: " + show.getLocation());
        lTime.setText("Time: " + show.getTime().toString());

        this.show = show;
    }

    public void setDeleteFunction(IDeleteShowFunc deleteFunction) {
        bDelete.setOnAction(event -> deleteFunction.deleteShow(show));
    }

    public void setUpdateFunction(IUpdateShowFunc updateFunction) {
        bUpdate.setOnAction(event -> updateFunction.updateShow(show));
    }




}
