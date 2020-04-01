import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;

public class Main extends Application {



    public static void main(String[] args) {launch(args);}

    private static JDBCHelper jdbcHelper = new JDBCHelper();
    private Connection connection = null;
    private Stage primaryStage;
    static LoginPage main;


    @Override
    public void start(Stage primaryStage) {
        jdbcHelper.initConnection();
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Scheduling Application");
        initializeLogin();
    }

    private void initializeLogin() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("LoginPage.fxml"));
            AnchorPane rootLayout = loader.load();
            main = loader.getController();

            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static JDBCHelper getDBHelper(){
        return jdbcHelper;
    }
}
