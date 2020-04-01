import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class HomePage implements Initializable {

    @FXML
    public Button logoutbutton;
    @FXML
    public Button customerbutton;
    @FXML
    public Button apptbutton;
    @FXML
    public Button consultantbutton;
    @FXML
    public Button adminbutton;
    @FXML
    public RadioButton monthlyapptbytyperatio;
    @FXML
    public RadioButton schedulespecificconsultantratio;
    @FXML
    public RadioButton apptsspecificcustomerratio;
    @FXML
    public TextField consultantIDfield;
    @FXML
    public TextField customerIDfield;
    @FXML
    public Label customeridlabel;
    @FXML
    public Label consultantidlabel;
    @FXML
    public Button generatereportbutton;
    @FXML
    public TableView WeeklyTableView;
    @FXML
    public TableColumn WeeklySunColumn;
    @FXML
    public TableColumn WeeklyMonColumn;
    @FXML
    public TableColumn WeeklyTueColumn;
    @FXML
    public TableColumn WeeklyWedColumn;
    @FXML
    public TableColumn WeeklyThuColumn;
    @FXML
    public TableColumn WeeklyFriColumn;
    @FXML
    public TableColumn WeeklySatColumn;
    @FXML
    public TableView MonthlyTableView;
    @FXML
    public TableColumn MonSunColumn;
    @FXML
    public TableColumn MonthlyMonColumn;
    @FXML
    public TableColumn MonthlyTueColumn;
    @FXML
    public TableColumn MonthlyWedColumn;
    @FXML
    public TableColumn MonThuColumn;
    @FXML
    public TableColumn MonFriCol;
    @FXML
    public TableColumn MonSatColumn;
    @FXML
    public RadioButton weeklyviewratio;
    @FXML
    public RadioButton monthlyviewratio;

    //member variables
    String currentuser = null;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

//interface action methods

    public void logoutbuttonpressed(ActionEvent actionEvent) {
    }

    public void customerbuttonpressed(ActionEvent actionEvent) {
    }

    public void apptbuttonpressed(ActionEvent actionEvent) {
    }

    public void consultantbuttonpressed(ActionEvent actionEvent) {
    }

    public void adminbuttonpressed(ActionEvent actionEvent) {
    }

    public void monthlyapptbytyperatioselected(ActionEvent actionEvent) {
    }

    public void schedulespecificconsultantratioselected(ActionEvent actionEvent) {
    }

    public void apptsspecificcustomerratioselected(ActionEvent actionEvent) {
    }

    public void generatereportbuttonpressed(ActionEvent actionEvent) {
    }

    public void weeklyviewratioselected(ActionEvent actionEvent) {
    }

    public void monthlyviewratioselected(ActionEvent actionEvent) {
    }

    //other methods

    public void initdata(String username){
        currentuser = username;
        checkforlt15minappt();
    }

    public void checkforlt15minappt() {
        ResultSet r = getqueryResult(Main.getDBHelper().runQuery.apply("SELECT * FROM appointment INNER JOIN user u WHERE TIMESTAMPDIFF(MINUTE, NOW(), start) <= 15 and TIMESTAMPDIFF(MINUTE, NOW(), start) > 0 and u.userName = '" + currentuser + "';"));
        try{
                while(r.next()) {
                    Alert alertlt15appt = new Alert(Alert.AlertType.INFORMATION);
                    alertlt15appt.setHeaderText("Upcoming Appointment");
                    alertlt15appt.setTitle("FYI: Appointment Alert");
                    alertlt15appt.setContentText("You have an appointment in 15 minutes or less. \n\nPlease take a look at the details on your calendar.");
                    alertlt15appt.showAndWait();
                }
        }
        catch (Exception e) {
            System.out.println(e.fillInStackTrace());
        }
    }

    public ResultSet getqueryResult(ResultSet r) {
        return r;
    }

    public void dms(int i) { }

}
