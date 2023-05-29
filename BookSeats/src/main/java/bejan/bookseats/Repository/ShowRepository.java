package bejan.bookseats.Repository;

import bejan.bookseats.Domain.Reservation;
import bejan.bookseats.Domain.Show;
import bejan.bookseats.Utils.Utils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;

public class ShowRepository {
    private SessionFactory sessionFactory;

    public ShowRepository() {
        sessionFactory = Utils.getInstanceOfSessionFactory();
        System.out.println("Show repo initialized.");
    }

    public List<Show> findAll() {
        // returns all shows
        List<Show> shows = null;
        try(Session session = sessionFactory.openSession()) {
            shows = session.createQuery("from Show", Show.class).list();
        }

        return shows;
    }

    public Show findOne(Date date) {
        // returns the show with the given date
        Show show = null;
        try(Session session = sessionFactory.openSession()) {
            show = session.createQuery("from Show where date = :date", Show.class)
                    .setParameter("date", date)
                    .uniqueResult();
        }

        return show;
    }

    public void addReservation(Reservation reservation) {
        // adds a reservation to the database
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(reservation);
            session.getTransaction().commit();
        }
    }

    public void addShow(Show show) {
        // adds a show to the database
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(show);
            session.getTransaction().commit();
        }
    }

    public void deleteOne(Show show) {
        // deletes a show from the database
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.delete(show);
            session.getTransaction().commit();
        }
    }

    public void modifyShow(Show show) {
        // modifies a show from the database
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.update(show);
            session.getTransaction().commit();
        }
    }
}
