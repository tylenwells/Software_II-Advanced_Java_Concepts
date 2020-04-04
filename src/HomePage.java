import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.TextAlignment;

import java.awt.*;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.ResourceBundle;
import java.util.function.Function;

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
    @FXML
    public Label WeeklyMondayLabel;
    @FXML
    public Label WeeklyTuesdayLabel;
    @FXML
    public Label WeeklySundayLabel;
    @FXML
    public Label WeeklyWednesdayLabel;
    @FXML
    public Label WeeklyThursdayLabel;
    @FXML
    public Label WeeklyFridayLabel;
    @FXML
    public Label WeeklySaturdayLabel;
    @FXML
    public ListView<String> SundayList;
    @FXML
    public ListView<String> MondayList;
    @FXML
    public ListView<String> TuesdayList;
    @FXML
    public ListView<String> WednesdayList;
    @FXML
    public ListView<String> ThursdayList;
    @FXML
    public ListView<String> FridayList;
    @FXML
    public ListView<String> SaturdayList;
    @FXML
    public GridPane WeeklyViewGridPane;

    //member variables
    final ToggleGroup calview = new ToggleGroup();
    String currentuser = null;
    Appointment[] weeklyApptArray = new Appointment[256];
    Appointment[] monthlyApptArray = new Appointment[256];
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        weeklyviewratio.setToggleGroup(calview);
        monthlyviewratio.setToggleGroup(calview);
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

        WeeklyViewGridPane.setDisable(false);
        fillWeeklyTable();
        WeeklyViewGridPane.setVisible(true);
        monthlyviewratio.setSelected(false);
        WeeklySundayLabel.setVisible(true);
        WeeklyMondayLabel.setVisible(true);
        WeeklyTuesdayLabel.setVisible(true);
        WeeklyWednesdayLabel.setVisible(true);
        WeeklyThursdayLabel.setVisible(true);
        WeeklyFridayLabel.setVisible(true);
        WeeklySaturdayLabel.setVisible(true);
        WeeklySundayLabel.setDisable(false);
        WeeklyMondayLabel.setDisable(false);
        WeeklyTuesdayLabel.setDisable(false);
        WeeklyWednesdayLabel.setDisable(false);
        WeeklyThursdayLabel.setDisable(false);
        WeeklyFridayLabel.setDisable(false);
        WeeklySaturdayLabel.setDisable(false);
    }

    public void monthlyviewratioselected(ActionEvent actionEvent) {
        WeeklyViewGridPane.setDisable(true);
        WeeklyViewGridPane.setVisible(false);
        weeklyviewratio.setSelected(false);
        monthlyviewratio.setSelected(true);
        WeeklySundayLabel.setVisible(false);
        WeeklyMondayLabel.setVisible(false);
        WeeklyTuesdayLabel.setVisible(false);
        WeeklyWednesdayLabel.setVisible(false);
        WeeklyThursdayLabel.setVisible(false);
        WeeklyFridayLabel.setVisible(false);
        WeeklySaturdayLabel.setVisible(false);
        WeeklySundayLabel.setDisable(true);
        WeeklyMondayLabel.setDisable(true);
        WeeklyTuesdayLabel.setDisable(true);
        WeeklyWednesdayLabel.setDisable(true);
        WeeklyThursdayLabel.setDisable(true);
        WeeklyFridayLabel.setDisable(true);
        WeeklySaturdayLabel.setDisable(true);
    }

    //other functions/methods

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
            e.printStackTrace();
        }
    }

public void fillWeeklyTable() {

        SundayList.getItems().clear();
        MondayList.getItems().clear();
        TuesdayList.getItems().clear();
        WednesdayList.getItems().clear();
        ThursdayList.getItems().clear();
        FridayList.getItems().clear();
        SaturdayList.getItems().clear();

        SundayList.getItems().add(" ");
        MondayList.getItems().add(" ");
        TuesdayList.getItems().add(" ");
        WednesdayList.getItems().add(" ");
        ThursdayList.getItems().add(" ");
        FridayList.getItems().add(" ");
        SaturdayList.getItems().add(" ");

        String query = "SELECT appointmentId, customerId, appointment.userId, title, description, location, contact, type, url, start, end, appointment.createDate, appointment.createdBy, appointment.lastUpdate, appointment.lastUpdateBy from appointment INNER JOIN user u WHERE TIMESTAMPDIFF(day, now(), start) <= 7 AND TIMESTAMPDIFF(day, now(), start) >= -7  and u.userName = '" + currentuser + "'  ORDER BY start;";
        ResultSet r = getqueryResult(Main.getDBHelper().runQuery.apply(query));
        try{
            int index = 0;
            while(r.next()){
                String test = r.getObject(10).toString();
                weeklyApptArray[index] = new Appointment();
                weeklyApptArray[index].setId(r.getObject(1).toString());
                weeklyApptArray[index].setCustomerId(r.getObject(2).toString());
                weeklyApptArray[index].setUserId(r.getObject(3).toString());
                weeklyApptArray[index].setTitle(r.getObject(4).toString());
                weeklyApptArray[index].setDescription(r.getObject(5).toString());
                weeklyApptArray[index].setLocation(r.getObject(6).toString());
                weeklyApptArray[index].setContact(r.getObject(7).toString());
                weeklyApptArray[index].setType(r.getObject(8).toString());
                weeklyApptArray[index].setUrl(r.getObject(9).toString());
                weeklyApptArray[index].setCreatedBy(r.getObject(13).toString());
                weeklyApptArray[index].setLastUpdateBy(r.getObject(15).toString());
                weeklyApptArray[index].setStart(convertSQLUTCStrtoLocalTime.apply(r.getObject(10).toString().substring(0,19)));
                weeklyApptArray[index].setEnd(convertSQLUTCStrtoLocalTime.apply(r.getObject(11).toString().substring(0,19)));
                weeklyApptArray[index].setCreateDate(convertSQLUTCStrtoLocalTime.apply(r.getObject(12).toString().substring(0,19)));
                weeklyApptArray[index].setLastUpdate(convertSQLUTCStrtoLocalTime.apply(r.getObject(14).toString().substring(0,19)));
                index++;
            }
        }
        catch(SQLException s) {
            s.printStackTrace();
    }

        LocalDateTime Sunday = LocalDateTime.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY));
        LocalDateTime Monday = Sunday.with(TemporalAdjusters.next(DayOfWeek.MONDAY));
        LocalDateTime Tuesday = Sunday.with(TemporalAdjusters.next(DayOfWeek.TUESDAY));
        LocalDateTime Wednesday = Sunday.with(TemporalAdjusters.next(DayOfWeek.WEDNESDAY));
        LocalDateTime Thursday = Sunday.with(TemporalAdjusters.next(DayOfWeek.THURSDAY));
        LocalDateTime Friday = Sunday.with(TemporalAdjusters.next(DayOfWeek.FRIDAY));
        LocalDateTime Saturday = Sunday.with(TemporalAdjusters.next(DayOfWeek.SATURDAY));

        WeeklySundayLabel.textProperty().set("Sun (" + Sunday.format(DateTimeFormatter.ofPattern("MM/dd/yyyy")) + ")");
        WeeklyMondayLabel.textProperty().set("Mon (" + Monday.format(DateTimeFormatter.ofPattern("MM/dd/yyyy")) + ")");
        WeeklyTuesdayLabel.textProperty().set("Tue (" + Tuesday.format(DateTimeFormatter.ofPattern("MM/dd/yyyy")) + ")");
        WeeklyWednesdayLabel.textProperty().set("Wed (" + Wednesday.format(DateTimeFormatter.ofPattern("MM/dd/yyyy")) + ")");
        WeeklyThursdayLabel.textProperty().set("Thu (" + Thursday.format(DateTimeFormatter.ofPattern("MM/dd/yyyy")) + ")");
        WeeklyFridayLabel.textProperty().set("Fri (" + Friday.format(DateTimeFormatter.ofPattern("MM/dd/yyyy")) + ")");
        WeeklySaturdayLabel.textProperty().set("Sat (" + Saturday.format(DateTimeFormatter.ofPattern("MM/dd/yyyy")) + ")");

    //WeeklySundayLabel.setMinWidth(140.0) ;
    WeeklySundayLabel.textAlignmentProperty().set(TextAlignment.CENTER);
    WeeklyMondayLabel.setPrefWidth(140.0);
    WeeklyMondayLabel.textAlignmentProperty().set(TextAlignment.CENTER);
    WeeklyTuesdayLabel.setPrefWidth(140.0);
    WeeklyTuesdayLabel.textAlignmentProperty().set(TextAlignment.CENTER);
    WeeklyWednesdayLabel.setPrefWidth(140.0);
    WeeklyWednesdayLabel.textAlignmentProperty().set(TextAlignment.CENTER);
    WeeklyThursdayLabel.setPrefWidth(140.0);
    WeeklyThursdayLabel.textAlignmentProperty().set(TextAlignment.CENTER);
    WeeklyFridayLabel.setPrefWidth(140.0);
    WeeklyFridayLabel.textAlignmentProperty().set(TextAlignment.CENTER);
    WeeklySaturdayLabel.setPrefWidth(140.0);
    WeeklySaturdayLabel.textAlignmentProperty().set(TextAlignment.CENTER);



        for (Appointment a : weeklyApptArray) {
            if (a == null) {
            break;
            }
            if (a.getStart().format(DateTimeFormatter.ofPattern("MM/dd/yyyy")).equals(Sunday.format(DateTimeFormatter.ofPattern("MM/dd/yyyy")))){
                SundayList.getItems().add(a.getStart().format(DateTimeFormatter.ofPattern("HH:mm:")) + " " + a.getTitle());
            } else if (a.getStart().format(DateTimeFormatter.ofPattern("MM/dd/yyyy")).equals(Monday.format(DateTimeFormatter.ofPattern("MM/dd/yyyy")))){
                MondayList.getItems().add(a.getStart().format(DateTimeFormatter.ofPattern("HH:mm:")) + " " + a.getTitle());
            } else if (a.getStart().format(DateTimeFormatter.ofPattern("MM/dd/yyyy")).equals(Tuesday.format(DateTimeFormatter.ofPattern("MM/dd/yyyy")))){
                TuesdayList.getItems().add(a.getStart().format(DateTimeFormatter.ofPattern("HH:mm:")) + " " + a.getTitle());
            } else if (a.getStart().format(DateTimeFormatter.ofPattern("MM/dd/yyyy")).equals(Wednesday.format(DateTimeFormatter.ofPattern("MM/dd/yyyy")))){
                WednesdayList.getItems().add(a.getStart().format(DateTimeFormatter.ofPattern("HH:mm:")) + " " + a.getTitle());
            } else if (a.getStart().format(DateTimeFormatter.ofPattern("MM/dd/yyyy")).equals(Thursday.format(DateTimeFormatter.ofPattern("MM/dd/yyyy")))){
                ThursdayList.getItems().add(a.getStart().format(DateTimeFormatter.ofPattern("HH:mm:")) + " " + a.getTitle());
            } else if (a.getStart().format(DateTimeFormatter.ofPattern("MM/dd/yyyy")).equals(Friday.format(DateTimeFormatter.ofPattern("MM/dd/yyyy")))){
                FridayList.getItems().add(a.getStart().format(DateTimeFormatter.ofPattern("HH:mm:")) + " " + a.getTitle());
            } else if (a.getStart().format(DateTimeFormatter.ofPattern("MM/dd/yyyy")).equals(Saturday.format(DateTimeFormatter.ofPattern("MM/dd/yyyy")))){
                SaturdayList.getItems().add(a.getStart().format(DateTimeFormatter.ofPattern("HH:mm:")) + " " + a.getTitle());
            }
    }

};





    //helper functions that allow easier running of SQL Statements
    public ResultSet getqueryResult(ResultSet r) {
        return r;
    }

    public void dms(int i) { }


    Function<String, LocalDateTime> convertSQLUTCStrtoLocalTime = (String s1) -> {
        LocalDateTime ldt = LocalDateTime.parse(s1, dateTimeFormatter);
        LocalDateTime buffldt = ldt;
        ZonedDateTime zdt = ldt.atZone(ZoneId.of("+00:00"));
        ZonedDateTime buffzdt = buffldt.atZone(ZoneId.systemDefault());
        ZoneOffset lzo = buffzdt.getOffset();
        LocalDateTime localTime = zdt.plus(lzo.getTotalSeconds(), ChronoUnit.SECONDS).toLocalDateTime();
        return localTime;
    };
}


