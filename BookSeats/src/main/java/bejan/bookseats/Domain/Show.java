package bejan.bookseats.Domain;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;
import java.util.List;
import java.util.Objects;

@Entity
@Table(
        name = "shows",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"date"})
        }
)
public class Show {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private String description;
    private Float price;
    private Date date;
    private Time time;
    private String artists;
    private String location;

    @OneToMany(mappedBy = "show", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Reservation> reservations;

    public Show() {
    }

    public Show(Integer id, String title, String description, Float price, Date date, Time time, String artists, String location) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.price = price;
        this.date = date;
        this.time = time;
        this.artists = artists;
        this.location = location;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String name) {
        this.title = name;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    public void addReservation(Reservation reservation) {
        reservations.add(reservation);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public static Integer getNoSeats() {
        return 38;
    }

    public Integer getNoOccupiedSeats() {
        return reservations.size();
    }

    public Integer getNoAvailableSeats() {
        return Show.getNoSeats() - getNoOccupiedSeats();
    }
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Show{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", noSeats=" + getNoSeats() +
                ", date=" + date +
                ", time=" + time +
                ", artists='" + artists + '\'' +
                ", location='" + location + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Show show)) return false;

        return Objects.equals(id, show.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    public static Integer getNoRows() {
        return 6;
    }

    public static Integer getNoCols() {
        return 9;
    }

    public String getArtists() {
        return artists;
    }

    public void setArtists(String artists) {
        this.artists = artists;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
