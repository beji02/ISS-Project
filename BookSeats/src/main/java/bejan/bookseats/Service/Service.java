package bejan.bookseats.Service;

import bejan.bookseats.Domain.Reservation;
import bejan.bookseats.Domain.Show;
import bejan.bookseats.Domain.User;
import bejan.bookseats.Repository.ShowRepository;
import bejan.bookseats.Repository.UserRepository;
import bejan.bookseats.Utils.Pair;

import javax.persistence.EntityNotFoundException;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Service {
    private UserRepository userRepository;
    private ShowRepository showRepository;
    private String loggedUserUsername = null;

    public Service(UserRepository userRepository, ShowRepository showRepository) {
        this.userRepository = userRepository;
        this.showRepository = showRepository;
    }

    public User login(String username, String password) {
        User loggedUser = userRepository.findOne(username, password);
        loggedUserUsername = loggedUser.getUsername();
        return loggedUser;
    }

    public User register(String username, String mail, String name, String password) {
        return userRepository.add(username, mail, name, password);
    }

    public User getLoggedUser() {
        return userRepository.findUser(loggedUserUsername);
    }

    public void logout() {
        loggedUserUsername = null;
    }

    public Show getTodayShow() {
        // get date of today without time
        Date today = Date.valueOf(LocalDateTime.now().toLocalDate());
        Show show = showRepository.findOne(today);
        if (show == null)
            throw new EntityNotFoundException("No show today.");
        return show;
    }

    public List<Show> getShows() {
        return showRepository.findAll();
    }

    public void bookSeats(Show show, Integer noSeats) {
        if (show.getNoAvailableSeats() < noSeats)
            throw new IllegalArgumentException("Not enough seats.");

        List<Pair<Integer, Integer>> emptySeats = getAllEmptySeats(show);

        for (int i = 0; i < noSeats; i++) {
            Integer row = emptySeats.get(i).getFirst();
            Integer col = emptySeats.get(i).getSecond();

            Reservation reservation = new Reservation(row, col, show, getLoggedUser());

            showRepository.addReservation(reservation);
        }
    }

    public void bookChosenSeats(Show show, List<Pair<Integer, Integer>> chosenSeats) {
        if (show.getNoAvailableSeats() < chosenSeats.size())
            throw new IllegalArgumentException("Not enough seats.");

        List<Pair<Integer, Integer>> emptySeats = getAllEmptySeats(show);

        for (Pair<Integer, Integer> seat : chosenSeats) {
            if(!emptySeats.contains(seat))
                throw new IllegalArgumentException("Seat already taken.");
            Reservation reservation = new Reservation(seat.getFirst(), seat.getSecond(), show, getLoggedUser());

            showRepository.addReservation(reservation);
        }
    }

    public List<Pair<Integer, Integer>> getAllOccupiedSeats(Show show) {
        List<Reservation> reservations = show.getReservations();
        List<Pair<Integer, Integer>> occupiedSeats = new ArrayList<>();
        reservations.forEach(reservation -> occupiedSeats.add(new Pair<>(reservation.getRow(), reservation.getCol())));

        return occupiedSeats;
    }

    public List<Pair<Integer, Integer>> getAllEmptySeats(Show show) {
        List<Reservation> reservations = show.getReservations();
        List<Pair<Integer, Integer>> occupiedSeats = getAllOccupiedSeats(show);
        List<Pair<Integer, Integer>> emptySeats = new ArrayList<>();

        for (int i = 1; i <= Show.getNoRows(); i++) {
            for (int j = 1; j <= Show.getNoCols(); j++) {
                if (Math.abs(i - 1) + Math.abs(j - 5) > 3) {
                    if(!occupiedSeats.contains(new Pair<>(i, j))){
                        emptySeats.add(new Pair<>(i, j));
                    }
                }
            }
        }

        return emptySeats;
    }

    public List<Reservation> getUserReservations() {
        return getLoggedUser().getOwnReservations();
    }

    public void addShow(String title, String artists, String date, String time, String price, String description, String location) {
        Float priceFloat = Float.parseFloat(price);
        Time timeTime = Time.valueOf(time);
        Date dateDate = Date.valueOf(date);
        Show show = new Show(-1, title, description, priceFloat, dateDate, timeTime, artists, location);

        showRepository.addShow(show);
    }

    public void deleteShow(Show show) {
        showRepository.deleteOne(show);
    }

    public void updateShow(Integer id, String title, String artists, String date, String time, String price, String description, String location) {
        Float priceFloat = Float.parseFloat(price);
        Time timeTime = Time.valueOf(time);
        Date dateDate = Date.valueOf(date);
        Show show = new Show(id, title, description, priceFloat, dateDate, timeTime, artists, location);

        showRepository.modifyShow(show);
    }
}
