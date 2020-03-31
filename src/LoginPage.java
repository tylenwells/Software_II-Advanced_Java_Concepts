import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.sql.Connection;
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


    private short lang = 0; //0=default, 1=eng, 2=jp
    private Locale locale = Locale.getDefault();

    private SimpleStringProperty username = new SimpleStringProperty();
    private SimpleStringProperty password = new SimpleStringProperty();


    public void loginbuttonpressed(ActionEvent actionEvent) {
        boolean logincheck = Main.getDBHelper().checkLogin(username.get(), password.get());
        if (logincheck == true) {
            //open next page with proper info
        } else {
            Alert alertbadlogin = new Alert(Alert.AlertType.ERROR);
            alertbadlogin.setHeaderText("Error: Bad Login Info");
            alertbadlogin.setTitle("Invalid Username/Password");
            alertbadlogin.setContentText("Username and Password entered did either are not correct or do not exist.\" \n\nPlease correct and retry!");
            alertbadlogin.showAndWait();
        }
    }

    public void enlangbuttonpressed(ActionEvent actionEvent) {
        usernametext.textProperty().set("Username:");
        passwordtext.textProperty().set("Password:");
        loginbannertext.textProperty().set("Scheduling Application Login");
        loginbutton.textProperty().set("Login");

    }

    public void jplangbuttonpressed(ActionEvent actionEvent) {
usernametext.textProperty().set("ユーザー名:");
passwordtext.textProperty().set("パスワード:");
loginbannertext.textProperty().set("予定ソフトログイン");
loginbutton.textProperty().set("ログインする");

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
}
