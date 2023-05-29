package bejan.bookseats;

import bejan.bookseats.Controller.ManagerControllers.AdminShowController;
import bejan.bookseats.Controller.SpectatorControllers.ShowController;
import bejan.bookseats.Repository.ShowRepository;
import bejan.bookseats.Repository.UserRepository;
import bejan.bookseats.Service.Service;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
//        UserRepository userRepository = new UserRepository();
//        User user = userRepository.add("mail", "username", "name", "password");
//        System.out.println(user);
//        User user2 = userRepository.findOne("username", "password");
//        System.out.println(user2);
//        User user3 = userRepository.add("mail", "username", "name", "password");
//        System.out.println(user3);


//        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/BookSeats", "postgres", "postgres");
//             PreparedStatement statement = connection.prepareStatement("select * from users");
//        ) {
//            try(ResultSet rs = statement.executeQuery();) {
//                while(rs.next()) {
//                    int id = rs.getInt("id");
//                    String name = rs.getString("name");
//                    System.out.println(id + " " + name);
//                }
//            }
//
//
//        }
//        catch(Exception e) {
//            e.printStackTrace();
//        }

//        EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu");
//        EntityManager em = emf.createEntityManager();
//
//        User user = em.find(User.class, 1);
//        System.out.println(user);
        //UserRepository userRepository = new UserRepository();
        UserRepository userRepository = new UserRepository();
        ShowRepository showRepository = new ShowRepository();

        Platform.setImplicitExit(true);
        stage.setOnCloseRequest((ae) -> {
            Platform.exit();
            System.exit(0);
        });

//        Service service = new Service(userRepository, showRepository);
//        service.login("deiubejan", "paine123");
//
//        //System.out.println(service.getShows());
//
//
//
//        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/bejan/bookseats/SpectatorViews/show_view.fxml"));
//        Scene scene = new Scene(fxmlLoader.load());
//        ShowController showController = fxmlLoader.getController();
//        showController.setService(service);
//        stage.setTitle("Book'a Seat");
//        stage.setScene(scene);
//        stage.show();

        Service service = new Service(userRepository, showRepository);
        service.login("deiu", "deiu");

        //System.out.println(service.getShows());



        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/bejan/bookseats/ManagerViews/admin_show_view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        AdminShowController adminShowController = fxmlLoader.getController();
        adminShowController.setService(service);
        stage.setTitle("Book'a Seat");
        stage.setScene(scene);
        stage.show();



//        Platform.setImplicitExit(true);
//        stage.setOnCloseRequest((ae) -> {
//            Platform.exit();
//            System.exit(0);
//        });
//
//        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("login_view.fxml"));
//        Scene scene = new Scene(fxmlLoader.load());
//        LoginController loginController = fxmlLoader.getController();
//        loginController.setService(service);
//        stage.setTitle("Login");
//        stage.setScene(scene);
//        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}