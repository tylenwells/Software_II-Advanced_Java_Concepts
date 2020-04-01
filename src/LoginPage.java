import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.FileAttribute;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Locale;
import java.util.ResourceBundle;

public class LoginPage implements Initializable {

    @FXML
    public Label loginbannertext;
    @FXML
    public TextField usernamefield;
    @FXML
    public Label usernametext;
    @FXML
    public Label passwordtext;
    @FXML
    public Button loginbutton;
    @FXML
    public Button enlangbutton;
    @FXML
    public Button jplangbutton;
    @FXML
    public PasswordField passwordfield;


    private String logpath = null;
    private short lang = 0; //0=default, 1=eng, 2=jp
    private Locale locale = Locale.getDefault();

    private SimpleStringProperty username = new SimpleStringProperty();
    private SimpleStringProperty password = new SimpleStringProperty();


    public void loginbuttonpressed(ActionEvent actionEvent) {
        String query = "SELECT COUNT(1) FROM user WHERE userName = '" + username.get() + "' AND password = '" + password.get() + "' LIMIT 1;";
       checkLogin(Main.getDBHelper().runQuery.apply(query)); // runs query, returns resultset and checks it for valid login state. Displays error if incorrect.
    }

    public void enlangbuttonpressed(ActionEvent actionEvent) {
        usernametext.textProperty().set("Username:");
        passwordtext.textProperty().set("Password:");
        loginbannertext.textProperty().set("Scheduling Application Login");
        loginbutton.textProperty().set("Login");
        lang = 1;
    }

    public void jplangbuttonpressed(ActionEvent actionEvent) {
usernametext.textProperty().set("ユーザー名:");
passwordtext.textProperty().set("パスワード:");
loginbannertext.textProperty().set("予定ソフトログイン");
loginbutton.textProperty().set("ログインする");
lang = 2;
    }

    public void bindInputFields(){
        usernamefield.textProperty().bindBidirectional(username);
        passwordfield.textProperty().bindBidirectional(password);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.bindInputFields();
        if (locale.getLanguage() == "ja") {
            jplangbutton.fire();
        } else {
          enlangbutton.fire();
        }
    }

    public void checkLogin(ResultSet r) {
      try {
          r.next();
          if (r.getObject(1).toString().equals("1")) {
              FXMLLoader loader = new FXMLLoader(getClass().getResource("HomePage.fxml"));
              Parent root = loader.load();
              Stage stage = new Stage();
              stage.setScene(new Scene(root));
              stage.show();
              HomePage homePage = loader.getController();
              homePage.initdata(username.get());
              int config = checkStoredSettings(Main.getDBHelper().runQuery.apply("SELECT * FROM clientsettings;"));
              if (config == 1) {
                  try {
                      Files.write(Paths.get(logpath), ("\nUser: " + username.get() + " logged in at " + Date.valueOf(LocalDate.now()) + " " + Time.valueOf(LocalTime.now()) + ".").getBytes(), StandardOpenOption.APPEND);
                  }
                  catch (IOException ie) {
                      try {
                          Files.createFile(Paths.get(logpath));
                          Files.write(Paths.get(logpath), ("\nUser: " + username.get() + " logged in at " + Date.valueOf(LocalDate.now()) + " " + Time.valueOf(LocalTime.now()) + ".").getBytes(), StandardOpenOption.APPEND);
                      }
                      catch (IOException i) {
                          System.out.println("Failed to write to or create file.");
                          Alert alertlogfailed = new Alert(Alert.AlertType.WARNING);
                          alertlogfailed.setHeaderText("Warning: logging failed.");
                          alertlogfailed.setTitle("Unable to log login timestamp.");
                          alertlogfailed.setContentText("Application was unable to log timestamp. \n\nPlease ensure " + logpath + " file is not corrupt.");
                          alertlogfailed.showAndWait();
                          ;
                      }
                  }
              }

          }
          else {
              if (lang == 1) {
                  Alert alertbadlogin = new Alert(Alert.AlertType.ERROR);
                  alertbadlogin.setHeaderText("Error: Bad Login Info");
                  alertbadlogin.setTitle("Invalid Username/Password");
                  alertbadlogin.setContentText("Username and Password entered did either are not correct or do not exist. \n\nPlease correct and retry!");
                  alertbadlogin.showAndWait();
              } else if (lang == 2) {
                  Alert alertbadlogin = new Alert(Alert.AlertType.ERROR);
                  alertbadlogin.setHeaderText("エラー: ログイン情報");
                  alertbadlogin.setTitle("ログインじょうほうが見つかれませんでした");
                  alertbadlogin.setContentText("ユーザー名かパスワードが間違っています。 \n\nもう一回入れて直してください！");
                  alertbadlogin.showAndWait();
              }
          }

      }
      catch (SQLException | IOException ex)
      {
          System.out.println("Exception while executing statement. Terminating program... " + ex.getMessage());
      }
    }

    public int checkStoredSettings(ResultSet r){
        boolean check = false;
        boolean audit = false;
        try {
            while(r.next()){

                if (r.getObject(1).toString().equals(InetAddress.getLocalHost().getHostName())){
                    check = true;
                    String debugvar = r.getObject(3).toString();
                    if (r.getObject(3).toString().contains("true")) {
                        audit = true;
                        setLogpath(r.getObject(4).toString());
                    }
                }
            }
        }
        catch (SQLException | UnknownHostException ex)
        {
            System.out.println("Exception while executing statement. Terminating program... " + ex.getMessage());
            return -1;
        }
        try{
            if (!check) {
            String q1 = "INSERT INTO clientsettings (hostname) VALUES ('" + InetAddress.getLocalHost().getHostName() + "');";
            dms(Main.getDBHelper().runDMS.apply(q1));
            return 0;
            }
            if (!audit){
                return 0;
            } else {
                return 1;
            }
        }
        catch (UnknownHostException e){
            System.out.println("Unknown Host Exception. Terminating Program..." + e.getMessage());
            return -1;
        }
}

    public void dms(int i) { }

    public void setLogpath(String logpath) {
        this.logpath = logpath;
    }
}
