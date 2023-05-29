package bejan.bookseats.Domain;


import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
public class User {
    @Id
    private String username;
    private String mail;
    private String name;
    private String password;

    private Boolean isAdmin;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Reservation> ownReservations;

    public User(String username, String mail, String name, String password) {
        this.mail = mail;
        this.username = username;
        this.name = name;
        this.password = password;
    }

    public User() {

    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMail() {
        return mail;
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "User{" +
                "mail='" + mail + '\'' +
                ", username='" + username + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public Boolean isAdmin() {
        return isAdmin;
    }

    public List<Reservation> getOwnReservations() {
        return ownReservations;
    }

    public void setOwnReservations(List<Reservation> ownReservations) {
        this.ownReservations = ownReservations;
    }

    public Boolean getAdmin() {
        return isAdmin;
    }

    public void setAdmin(Boolean admin) {
        isAdmin = admin;
    }
}
