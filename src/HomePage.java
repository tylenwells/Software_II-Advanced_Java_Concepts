import com.mysql.cj.Query;
import com.mysql.cj.xdevapi.SqlStatement;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.Function;

@SuppressWarnings("unchecked")
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
    @FXML
    public ListView<String> mon36;
    @FXML
    public ListView<String> mon37;
    @FXML
    public ListView<String> mon01;
    @FXML
    public ListView<String> mon02;
    @FXML
    public ListView<String> mon03;
    @FXML
    public ListView<String> mon04;
    @FXML
    public ListView<String> mon05;
    @FXML
    public ListView<String> mon06;
    @FXML
    public ListView<String> mon07;
    @FXML
    public ListView<String> mon08;
    @FXML
    public ListView<String> mon09;
    @FXML
    public ListView<String> mon10;
    @FXML
    public ListView<String> mon11;
    @FXML
    public ListView<String> mon12;
    @FXML
    public ListView<String> mon13;
    @FXML
    public ListView<String> mon14;
    @FXML
    public ListView<String> mon15;
    @FXML
    public ListView<String> mon16;
    @FXML
    public ListView<String> mon17;
    @FXML
    public ListView<String> mon18;
    @FXML
    public ListView<String> mon19;
    @FXML
    public ListView<String> mon20;
    @FXML
    public ListView<String> mon21;
    @FXML
    public ListView<String> mon22;
    @FXML
    public ListView<String> mon23;
    @FXML
    public ListView<String> mon24;
    @FXML
    public ListView<String> mon25;
    @FXML
    public ListView<String> mon26;
    @FXML
    public ListView<String> mon27;
    @FXML
    public ListView<String> mon28;
    @FXML
    public ListView<String> mon29;
    @FXML
    public ListView<String> mon30;
    @FXML
    public ListView<String> mon31;
    @FXML
    public ListView<String> mon32;
    @FXML
    public ListView<String> mon33;
    @FXML
    public ListView<String> mon34;
    @FXML
    public ListView<String> mon35;
    @FXML
    public GridPane MonthlyViewGridPane;
    @FXML
    public Label MonthlySundayLabel;
    @FXML
    public Label MonthlyMondayLabel;
    @FXML
    public Label MonthlyTuesdayLabel;
    @FXML
    public Label MonthlyThursdayLabel;
    @FXML
    public Label MonthlyWednesdayLabel;
    @FXML
    public Label MonthlyFridayLabel;
    @FXML
    public Label MonthlySaturdayLabel;
    @FXML
    public Label MonViewMonthYearLabel;

    //member variables
    final ToggleGroup calview = new ToggleGroup();
    final ToggleGroup reportselection = new ToggleGroup();


    String currentuser = null;
    Appointment[] weeklyApptArray = new Appointment[256];
    Appointment[] monthlyApptArray = new Appointment[256];
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    SimpleStringProperty consultantid = new SimpleStringProperty();
    SimpleStringProperty customerid = new SimpleStringProperty();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        weeklyviewratio.setToggleGroup(calview);
        monthlyviewratio.setToggleGroup(calview);
        monthlyapptbytyperatio.setToggleGroup(reportselection);
        schedulespecificconsultantratio.setToggleGroup(reportselection);
        apptsspecificcustomerratio.setToggleGroup(reportselection);
        consultantIDfield.textProperty().bindBidirectional(consultantid);
        customerIDfield.textProperty().bindBidirectional(customerid);
    }

//interface action methods

    public void logoutbuttonpressed(ActionEvent actionEvent) {
        boolean check = false;
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Logout");
        alert.setContentText("Are you sure you want to log out and return to the login screen?");
        alert.getButtonTypes().set(0, ButtonType.YES);
        alert.getButtonTypes().set(1, ButtonType.NO);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.YES) check = true;
        if (check){
            Stage stage = (Stage) logoutbutton.getScene().getWindow();
            stage.close();
        }
    }

    public void customerbuttonpressed(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Lookup.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
            Lookup lookup = loader.getController();
            lookup.initdata(currentuser);
            lookup.loadPane(1);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void apptbuttonpressed(ActionEvent actionEvent) {
        try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Lookup.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
        Lookup lookup = loader.getController();
        lookup.initdata(currentuser);
        lookup.loadPane(2);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void consultantbuttonpressed(ActionEvent actionEvent) {
        try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Lookup.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
        Lookup lookup = loader.getController();
        lookup.initdata(currentuser);
        lookup.loadPane(3);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void adminbuttonpressed(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminPage.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public void monthlyapptbytyperatioselected(ActionEvent actionEvent) {
        consultantidlabel.setVisible(false);
        customeridlabel.setVisible(false);
        consultantIDfield.setVisible(false);
        customerIDfield.setVisible(false);
    }

    public void schedulespecificconsultantratioselected(ActionEvent actionEvent) {
        consultantidlabel.setVisible(true);
        consultantidlabel.setDisable(false);
        customeridlabel.setVisible(false);
        consultantIDfield.setVisible(true);
        consultantIDfield.setDisable(false);
        customerIDfield.setVisible(false);
    }

    public void apptsspecificcustomerratioselected(ActionEvent actionEvent) {
        consultantidlabel.setVisible(false);
        customeridlabel.setVisible(true);
        customerIDfield.setDisable(false);
        consultantIDfield.setVisible(false);
        customerIDfield.setVisible(true);
        customeridlabel.setDisable(false);
    }

    public void generatereportbuttonpressed(ActionEvent actionEvent) {
        if (monthlyapptbytyperatio.isSelected()){
            //compile and save report of all appts for the month separated by type

           Integer numtypes;
           String numtypesquery = "SELECT DISTINCT type FROM appointment WHERE TIMESTAMPDIFF(day, now(), start) <= 31 AND TIMESTAMPDIFF(day, now(), start) >= -31";
           ArrayList<String> results = new ArrayList<String>();
           ResultSet r = getqueryResult(Main.getDBHelper().runQuery.apply(numtypesquery));
        try{
          while (r.next()){
                  results.add(r.getObject(1).toString());
          }
        }
        catch (Exception e) {
                e.printStackTrace();
            }

        String[] preparray = new String[results.size()];
        for (String s : results) {
            int index = results.indexOf(s);
            String apptquery = "SELECT * FROM appointment WHERE TIMESTAMPDIFF(day, now(), start) <= 31 AND TIMESTAMPDIFF(day, now(), start) >= -31 AND type = '" + s + "';";
            ResultSet r2 = getqueryResult(Main.getDBHelper().runQuery.apply(apptquery));
            s = s.concat(System.lineSeparator() + "__________________________________" + System.lineSeparator());
            int hitcount = 1;
            try {
                while (r2.next()) {
                    LocalDateTime dt;
                    String hci = Integer.toString(hitcount);
                    dt = convertSQLUTCStrtoLocalTime.apply(r2.getObject(10).toString());
                    s = s.concat(hci + ".\t" + r2.getObject(4) + "\t" + dt.format(DateTimeFormatter.ISO_LOCAL_DATE) + " " + dt.format(DateTimeFormatter.ISO_LOCAL_TIME) + System.lineSeparator());
                    hitcount++;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            s = s.concat(System.lineSeparator() + System.lineSeparator() + System.lineSeparator());
            results.set(index, s);
        }

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Report");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text Files", "*.txt"),
                new FileChooser.ExtensionFilter("All Files", "*.*"));
        File selectedFile = fileChooser.showSaveDialog(null);

        if (selectedFile != null) {
            try {
                Files.write(Paths.get(selectedFile.getCanonicalPath()), ("Report of monthly appointments, separated by appointment type:" + System.lineSeparator() + System.lineSeparator()).getBytes());
                for (String s : results) {
                    Files.write(Paths.get(selectedFile.getCanonicalPath()), (s.getBytes()), StandardOpenOption.APPEND);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

        else if (schedulespecificconsultantratio.isSelected()) {
            if (consultantid.get().isEmpty() || consultantid.get() == null) {
                Alert alertlt15appt = new Alert(Alert.AlertType.ERROR);
                alertlt15appt.setHeaderText("Empty ID field");
                alertlt15appt.setTitle("Error: Empty ID Field");
                alertlt15appt.setContentText("You must enter an ID to generate and save this report. \n\nPlease enter an ID into the ID field and retry!");
                alertlt15appt.showAndWait();
                return;
            } else {
                String id = consultantid.get();
                String query = "SELECT * FROM appointment INNER JOIN user u WHERE TIMESTAMPDIFF(DAY, NOW(), start) >= 0 and u.userId = '" + id + "' ORDER BY start;";
                ResultSet r = getqueryResult(Main.getDBHelper().runQuery.apply(query));
                ArrayList<String> resultarraylist = new ArrayList<String>();
                boolean check = false;
                String towrite = "";
                try {
                    int hitcount = 1;
                    while (r.next()) {
                        check = true;
                        String hitcountstring = Integer.toString(hitcount);
                        resultarraylist.add(hitcountstring + ".\t" + r.getObject(4).toString() + "\t" + r.getObject(10) + System.lineSeparator());
                        hitcount++;
                    }
                    if (!check) {
                        Alert alertlt15appt = new Alert(Alert.AlertType.ERROR);
                        alertlt15appt.setHeaderText("No appointments found.");
                        alertlt15appt.setTitle("Error: Nothing to Report");
                        alertlt15appt.setContentText("No appointments were found for the user ID " + id + ". \n\nReport will not be generated, please check Consultant ID!");
                        alertlt15appt.showAndWait();
                        return;
                    } else {
                        for (int i = 0; i < resultarraylist.size(); i++) {
                            towrite = towrite.concat(resultarraylist.get(i));
                        }
                    }
                    towrite = "Listed schedule for user ID " + id + ", arranged chronologically" + System.lineSeparator() + "Please note this report only includes appointments which start in the present day or in the future." + System.lineSeparator() + "_________________________________________________" + System.lineSeparator() + towrite;

                    FileChooser fileChooser = new FileChooser();
                    fileChooser.setTitle("Save Report");
                    fileChooser.getExtensionFilters().addAll(
                            new FileChooser.ExtensionFilter("Text Files", "*.txt"),
                            new FileChooser.ExtensionFilter("All Files", "*.*"));
                    File selectedFile = fileChooser.showSaveDialog(null);

                    if (selectedFile != null) {
                        try {
                            Files.write(Paths.get(selectedFile.getCanonicalPath()), towrite.getBytes());

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                catch(SQLException e){
                        e.printStackTrace();
                    }
            }


        }

        else if (apptsspecificcustomerratio.isSelected()) {
            if (customerid.get().isEmpty() || customerid.get() == null) {
                Alert alertlt15appt = new Alert(Alert.AlertType.ERROR);
                alertlt15appt.setHeaderText("Empty ID field");
                alertlt15appt.setTitle("Error: Empty ID Field");
                alertlt15appt.setContentText("You must enter an ID to generate and save this report. \n\nPlease enter an ID into the ID field and retry!");
                alertlt15appt.showAndWait();
                return;
            } else {
                String id = customerid.get();
                String query = "SELECT * FROM appointment INNER JOIN customer c WHERE TIMESTAMPDIFF(DAY, NOW(), start) >= 0 and c.customerId = '" + id + "' ORDER BY start;";
                ResultSet r = getqueryResult(Main.getDBHelper().runQuery.apply(query));
                ArrayList<String> resultarraylist = new ArrayList<String>();
                boolean check = false;
                String towrite = "";
                try {
                    int hitcount = 1;
                    while (r.next()) {
                        check = true;
                        String hitcountstring = Integer.toString(hitcount);
                        resultarraylist.add(hitcountstring + ".\t" + r.getObject(4).toString() + "\t" + r.getObject(10) + System.lineSeparator());
                        hitcount++;
                    }
                    if (!check) {
                        Alert alertlt15appt = new Alert(Alert.AlertType.ERROR);
                        alertlt15appt.setHeaderText("No appointments found.");
                        alertlt15appt.setTitle("Error: Nothing to Report");
                        alertlt15appt.setContentText("No appointments were found for the customer ID " + id + ". \n\nReport will not be generated, please check Customer ID!");
                        alertlt15appt.showAndWait();
                        return;
                    } else {
                        for (int i = 0; i < resultarraylist.size(); i++) {
                            towrite = towrite.concat(resultarraylist.get(i));
                        }
                    }
                    towrite = "Listed schedule for customer ID \"" + id + "\" , arranged chronologically" + System.lineSeparator() + "Please note this report only includes appointments which start in the present day or in the future." + System.lineSeparator() + "_________________________________________________" + System.lineSeparator() + towrite;

                    FileChooser fileChooser = new FileChooser();
                    fileChooser.setTitle("Save Report");
                    fileChooser.getExtensionFilters().addAll(
                            new FileChooser.ExtensionFilter("Text Files", "*.txt"),
                            new FileChooser.ExtensionFilter("All Files", "*.*"));
                    File selectedFile = fileChooser.showSaveDialog(null);

                    if (selectedFile != null) {
                        try {
                            Files.write(Paths.get(selectedFile.getCanonicalPath()), towrite.getBytes());

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                catch(SQLException e){
                    e.printStackTrace();
                }
            }
        }
    }

    public void weeklyviewratioselected(ActionEvent actionEvent) {

        WeeklyViewGridPane.setDisable(false);
        MonthlyViewGridPane.setDisable(true);
        fillWeeklyTable();
        MonViewMonthYearLabel.setVisible(false);
        MonthlyViewGridPane.setVisible(false);
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
        MonthlySundayLabel.setVisible(false);
        MonthlyMondayLabel.setVisible(false);
        MonthlyTuesdayLabel.setVisible(false);
        MonthlyWednesdayLabel.setVisible(false);
        MonthlyThursdayLabel.setVisible(false);
        MonthlyFridayLabel.setVisible(false);
        MonthlySaturdayLabel.setVisible(false);
        MonthlySundayLabel.setDisable(true);
        MonthlyMondayLabel.setDisable(true);
        MonthlyTuesdayLabel.setDisable(true);
        MonthlyWednesdayLabel.setDisable(true);
        MonthlyThursdayLabel.setDisable(true);
        MonthlyFridayLabel.setDisable(true);
        MonthlySaturdayLabel.setDisable(true);

    }

    public void monthlyviewratioselected(ActionEvent actionEvent) {
        WeeklyViewGridPane.setDisable(true);
        MonthlyViewGridPane.setDisable(false);
        fillMonthlyTable();
        MonViewMonthYearLabel.setVisible(true);
        WeeklyViewGridPane.setVisible(false);
        MonthlyViewGridPane.setVisible(true);
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
        MonthlySundayLabel.setVisible(true);
        MonthlyMondayLabel.setVisible(true);
        MonthlyTuesdayLabel.setVisible(true);
        MonthlyWednesdayLabel.setVisible(true);
        MonthlyThursdayLabel.setVisible(true);
        MonthlyFridayLabel.setVisible(true);
        MonthlySaturdayLabel.setVisible(true);
        MonthlySundayLabel.setDisable(false);
        MonthlyMondayLabel.setDisable(false);
        MonthlyTuesdayLabel.setDisable(false);
        MonthlyWednesdayLabel.setDisable(false);
        MonthlyThursdayLabel.setDisable(false);
        MonthlyFridayLabel.setDisable(false);
        MonthlySaturdayLabel.setDisable(false);

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

    public void fillMonthlyTable() {
        markmonlistsVisable();
        ListView<String>[] lva = new ListView[37];
        LocalDateTime fd = LocalDateTime.now().with(TemporalAdjusters.firstDayOfMonth());
        Integer daysinmonth = LocalDateTime.now().with(TemporalAdjusters.lastDayOfMonth()).getDayOfMonth();
        int offset = 0;
        switch (fd.getDayOfWeek()){ //Switch statement based upon the day of the week the first day of the month falls on
            case SUNDAY: ;
                switch(daysinmonth){
                    case 28: //disable 29-37
                        mon29.setVisible(false);
                        mon31.setVisible(false);
                        mon32.setVisible(false);
                        mon33.setVisible(false);
                        mon34.setVisible(false);
                        mon35.setVisible(false);
                        mon36.setVisible(false);
                        mon37.setVisible(false);
                        mon30.setVisible(false);
                        break;
                    case 29: //disable 30-37
                        mon30.setVisible(false);
                        mon31.setVisible(false);
                        mon32.setVisible(false);
                        mon33.setVisible(false);
                        mon34.setVisible(false);
                        mon35.setVisible(false);
                        mon36.setVisible(false);
                        mon37.setVisible(false);
                        break;
                    case 30: //disable 31-37
                        mon31.setVisible(false);
                        mon32.setVisible(false);
                        mon33.setVisible(false);
                        mon34.setVisible(false);
                        mon35.setVisible(false);
                        mon36.setVisible(false);
                        mon37.setVisible(false);
                        break;
                    case 31: //disable 32-37
                        mon32.setVisible(false);
                        mon33.setVisible(false);
                        mon34.setVisible(false);
                        mon35.setVisible(false);
                        mon36.setVisible(false);
                        mon37.setVisible(false);
                        break;
                    default: //ide is mad if i don't include this
                        break;
                } //switch statements to hide the unused lists outside of the scope of the month
                break;
            case MONDAY:
                offset = 1;
                switch(daysinmonth){
                    case 28: //disable 1, 30-37
                        mon01.setVisible(false);
                        mon30.setVisible(false);
                        mon31.setVisible(false);
                        mon32.setVisible(false);
                        mon33.setVisible(false);
                        mon34.setVisible(false);
                        mon35.setVisible(false);
                        mon36.setVisible(false);
                        mon37.setVisible(false);
                        break;
                    case 29: //disable 1, 31-37
                        mon01.setVisible(false);
                        mon31.setVisible(false);
                        mon32.setVisible(false);
                        mon33.setVisible(false);
                        mon34.setVisible(false);
                        mon35.setVisible(false);
                        mon36.setVisible(false);
                        mon37.setVisible(false);
                        break;
                    case 30: //disable 1, 32-37
                        mon01.setVisible(false);
                        mon32.setVisible(false);
                        mon33.setVisible(false);
                        mon34.setVisible(false);
                        mon35.setVisible(false);
                        mon36.setVisible(false);
                        mon37.setVisible(false);
                        break;
                    case 31: //disable 1, 33-37
                        mon01.setVisible(false);
                        mon33.setVisible(false);
                        mon34.setVisible(false);
                        mon35.setVisible(false);
                        mon36.setVisible(false);
                        mon37.setVisible(false);
                        break;
                    default: //ide is mad if I don't include this.
                        break;
                }
                break;
            case TUESDAY:
                offset = 2;
                switch(daysinmonth){
                    case 28: //disable 1,2,31-37
                        mon01.setVisible(false);
                        mon02.setVisible(false);
                        mon31.setVisible(false);
                        mon32.setVisible(false);
                        mon33.setVisible(false);
                        mon34.setVisible(false);
                        mon35.setVisible(false);
                        mon36.setVisible(false);
                        mon37.setVisible(false);
                        break;
                    case 29: //disable 1,2,32-37
                        mon01.setVisible(false);
                        mon02.setVisible(false);
                        mon32.setVisible(false);
                        mon33.setVisible(false);
                        mon34.setVisible(false);
                        mon35.setVisible(false);
                        mon36.setVisible(false);
                        mon37.setVisible(false);
                        break;
                    case 30: //disable 1,2,33-37
                        mon01.setVisible(false);
                        mon02.setVisible(false);
                        mon33.setVisible(false);
                        mon34.setVisible(false);
                        mon35.setVisible(false);
                        mon36.setVisible(false);
                        mon37.setVisible(false);
                        break;
                    case 31://disable 1,2,34-37
                        mon01.setVisible(false);
                        mon02.setVisible(false);
                        mon34.setVisible(false);
                        mon35.setVisible(false);
                        mon36.setVisible(false);
                        mon37.setVisible(false);
                        break;
                    default: //IDE gets mad if I don't include this
                        break;
                }
                break;
            case WEDNESDAY:
                offset = 3;
                switch(daysinmonth){
                    case 28: //disable 1-3, 32-37
                        mon01.setVisible(false);
                        mon02.setVisible(false);
                        mon03.setVisible(false);
                        mon32.setVisible(false);
                        mon33.setVisible(false);
                        mon34.setVisible(false);
                        mon35.setVisible(false);
                        mon36.setVisible(false);
                        mon37.setVisible(false);
                        break;
                    case 29: //disable 1-3, 33-37
                        mon01.setVisible(false);
                        mon02.setVisible(false);
                        mon03.setVisible(false);
                        mon33.setVisible(false);
                        mon34.setVisible(false);
                        mon35.setVisible(false);
                        mon36.setVisible(false);
                        mon37.setVisible(false);
                        break;
                    case 30: //disable 1-3, 34-37
                        mon01.setVisible(false);
                        mon02.setVisible(false);
                        mon03.setVisible(false);
                        mon34.setVisible(false);
                        mon35.setVisible(false);
                        mon36.setVisible(false);
                        mon37.setVisible(false);
                        break;
                    case 31: //disable 1-3, 35-37
                        mon01.setVisible(false);
                        mon02.setVisible(false);
                        mon03.setVisible(false);
                        mon35.setVisible(false);
                        mon36.setVisible(false);
                        mon37.setVisible(false);
                        break;
                    default: //IDE is mad if I don't include this.
                        break;
                }
                break;
            case THURSDAY:
                offset = 4;
                switch(daysinmonth){
                    case 28:// disable 1-4, 33-37
                        mon01.setVisible(false);
                        mon02.setVisible(false);
                        mon03.setVisible(false);
                        mon04.setVisible(false);
                        mon33.setVisible(false);
                        mon34.setVisible(false);
                        mon35.setVisible(false);
                        mon36.setVisible(false);
                        mon37.setVisible(false);
                        break;
                    case 29: //disable 1-4, 34-37
                        mon01.setVisible(false);
                        mon02.setVisible(false);
                        mon03.setVisible(false);
                        mon04.setVisible(false);
                        mon34.setVisible(false);
                        mon35.setVisible(false);
                        mon36.setVisible(false);
                        mon37.setVisible(false);
                        break;
                    case 30: //disable 1-4, 35-37
                        mon01.setVisible(false);
                        mon02.setVisible(false);
                        mon03.setVisible(false);
                        mon04.setVisible(false);
                        mon35.setVisible(false);
                        mon36.setVisible(false);
                        mon37.setVisible(false);
                        break;
                    case 31: //disable 1-4, 36-37
                        mon01.setVisible(false);
                        mon02.setVisible(false);
                        mon03.setVisible(false);
                        mon04.setVisible(false);
                        mon36.setVisible(false);
                        mon37.setVisible(false);
                        break;
                    default: //IDE is mad if I don't include this.
                        break;
                }
                break;
            case FRIDAY:
                offset = 5;
                switch(daysinmonth){
                    case 28: //disable 1-5, 34-37
                        mon01.setVisible(false);
                        mon02.setVisible(false);
                        mon03.setVisible(false);
                        mon04.setVisible(false);
                        mon05.setVisible(false);
                        mon34.setVisible(false);
                        mon35.setVisible(false);
                        mon36.setVisible(false);
                        mon37.setVisible(false);
                        break;
                    case 29: //disable 1-5, 35-37
                        mon01.setVisible(false);
                        mon02.setVisible(false);
                        mon03.setVisible(false);
                        mon04.setVisible(false);
                        mon05.setVisible(false);
                        mon35.setVisible(false);
                        mon36.setVisible(false);
                        mon37.setVisible(false);
                        break;
                    case 30: //disable 1-5, 36-37
                        mon01.setVisible(false);
                        mon02.setVisible(false);
                        mon03.setVisible(false);
                        mon04.setVisible(false);
                        mon05.setVisible(false);
                        mon36.setVisible(false);
                        mon37.setVisible(false);
                        break;
                    case 31: //disable 1-5, 37
                        mon01.setVisible(false);
                        mon02.setVisible(false);
                        mon03.setVisible(false);
                        mon04.setVisible(false);
                        mon05.setVisible(false);
                        mon37.setVisible(false);
                        break;
                    default: //IDE is mad if I don't include this.
                        break;
                }
                break;
            case SATURDAY:
                offset = 6;
                switch(daysinmonth){
                    case 28: //disable 1-6, 35-37
                        mon01.setVisible(false);
                        mon02.setVisible(false);
                        mon03.setVisible(false);
                        mon04.setVisible(false);
                        mon05.setVisible(false);
                        mon06.setVisible(false);
                        mon35.setVisible(false);
                        mon36.setVisible(false);
                        mon37.setVisible(false);
                        break;
                    case 29: //disable 1-6, 36-37
                        mon01.setVisible(false);
                        mon02.setVisible(false);
                        mon03.setVisible(false);
                        mon04.setVisible(false);
                        mon05.setVisible(false);
                        mon06.setVisible(false);
                        mon36.setVisible(false);
                        mon37.setVisible(false);
                        break;
                    case 30: //disable 1-6, 37
                        mon01.setVisible(false);
                        mon02.setVisible(false);
                        mon03.setVisible(false);
                        mon04.setVisible(false);
                        mon05.setVisible(false);
                        mon06.setVisible(false);
                        mon37.setVisible(false);
                        break;
                    case 31: //disable 1-6
                        mon01.setVisible(false);
                        mon02.setVisible(false);
                        mon03.setVisible(false);
                        mon04.setVisible(false);
                        mon05.setVisible(false);
                        mon06.setVisible(false);
                        break;
                    default: //IDE is mad if I don't include this.
                        break;
                }
                break;
            default:
                break;
        }

        for(int i = LocalDateTime.now().with(TemporalAdjusters.lastDayOfMonth()).getDayOfMonth(); i > 0; i--){
            int offseti = i + offset;
            if (Integer.toString(offseti).length() == 1) {
                String buff = "0".concat(Integer.toString(offseti));;
                lva[i] =(ListView<String>) MonthlyViewGridPane.lookup("#mon" + buff);
            }else lva[i] = (ListView<String>) MonthlyViewGridPane.lookup("#mon" + Integer.toString(offseti));
        }

        initmonLists(lva);

        MonViewMonthYearLabel.textProperty().set(LocalDateTime.now().getMonth().name()  + " " + Integer.toString(LocalDateTime.now().getYear())); //set month and year label text value

        String query = "SELECT appointmentId, customerId, appointment.userId, title, description, location, contact, type, url, start, end, appointment.createDate, appointment.createdBy, appointment.lastUpdate, appointment.lastUpdateBy from appointment INNER JOIN user u WHERE TIMESTAMPDIFF(day, now(), start) <= 31 AND TIMESTAMPDIFF(day, now(), start) >= -31  and u.userName = '" + currentuser + "'  ORDER BY start;";
        ResultSet r = getqueryResult(Main.getDBHelper().runQuery.apply(query));

        try{
            int index = 0;
            while(r.next()){
                String test = r.getObject(10).toString();
                monthlyApptArray[index] = new Appointment();
                monthlyApptArray[index].setId(r.getObject(1).toString());
                monthlyApptArray[index].setCustomerId(r.getObject(2).toString());
                monthlyApptArray[index].setUserId(r.getObject(3).toString());
                monthlyApptArray[index].setTitle(r.getObject(4).toString());
                monthlyApptArray[index].setDescription(r.getObject(5).toString());
                monthlyApptArray[index].setLocation(r.getObject(6).toString());
                monthlyApptArray[index].setContact(r.getObject(7).toString());
                monthlyApptArray[index].setType(r.getObject(8).toString());
                monthlyApptArray[index].setUrl(r.getObject(9).toString());
                monthlyApptArray[index].setCreatedBy(r.getObject(13).toString());
                monthlyApptArray[index].setLastUpdateBy(r.getObject(15).toString());
                monthlyApptArray[index].setStart(convertSQLUTCStrtoLocalTime.apply(r.getObject(10).toString()));
                monthlyApptArray[index].setEnd(convertSQLUTCStrtoLocalTime.apply(r.getObject(11).toString()));
                monthlyApptArray[index].setCreateDate(convertSQLUTCStrtoLocalTime.apply(r.getObject(12).toString()));
                monthlyApptArray[index].setLastUpdate(convertSQLUTCStrtoLocalTime.apply(r.getObject(14).toString()));
                index++;
            }
        }
        catch(SQLException s) {
            s.printStackTrace();
        }

        for (Appointment a: monthlyApptArray) {
            if (a == null){
                break;
            }
            if (LocalDateTime.now().getMonth() == a.getStart().getMonth()  && LocalDateTime.now().getYear() == a.getStart().getYear()){
                lva[a.getStart().getDayOfMonth()].getItems().add(a.getStart().format(DateTimeFormatter.ofPattern("HH:mm:")) + " " + a.getTitle());
            }
        }
    }

    public void markmonlistsVisable() {
        mon01.setVisible(true);
        mon02.setVisible(true);
        mon03.setVisible(true);
        mon04.setVisible(true);
        mon05.setVisible(true);
        mon06.setVisible(true);
        mon07.setVisible(true);
        mon08.setVisible(true);
        mon09.setVisible(true);
        mon10.setVisible(true);
        mon11.setVisible(true);
        mon12.setVisible(true);
        mon13.setVisible(true);
        mon14.setVisible(true);
        mon15.setVisible(true);
        mon16.setVisible(true);
        mon17.setVisible(true);
        mon18.setVisible(true);
        mon19.setVisible(true);
        mon20.setVisible(true);
        mon21.setVisible(true);
        mon22.setVisible(true);
        mon23.setVisible(true);
        mon24.setVisible(true);
        mon25.setVisible(true);
        mon26.setVisible(true);
        mon27.setVisible(true);
        mon28.setVisible(true);
        mon29.setVisible(true);
        mon30.setVisible(true);
        mon31.setVisible(true);
        mon32.setVisible(true);
        mon33.setVisible(true);
        mon34.setVisible(true);
        mon35.setVisible(true);
        mon36.setVisible(true);
        mon37.setVisible(true);
    }

    public void initmonLists(ListView<String>[] lva){ //clears and set date on lists
        for(ListView<String> list : lva){
            try {
                list.getItems().clear();
            }
            catch (NullPointerException ignored){}

        }
        for (int i = lva.length; i>0; i--){
            try{
                lva[i].getItems().add(Integer.toString(i));
                lva[i].cellFactoryProperty().set(list -> {
                    ListCell<String> cell = new ListCell<String>() {
                        @Override
                        protected void updateItem(String item, boolean empty) {
                            super.updateItem(item, empty);
                            if (empty || item == null) {
                                setGraphic(null);
                                setStyle(null);
                            } else {
                                if (item.equalsIgnoreCase(list.getItems().get(0))) {
                                    setStyle("alignment: LEFT;");
                                }
                                setText(item);
                            }
                        }
                    };
                    return cell;
                });
                }
            catch (IndexOutOfBoundsException | NullPointerException ignored){}
        }
    }




    //helper functions that allow easier running of SQL Statements
    public ResultSet getqueryResult(ResultSet r) {
        return r;
    }

    public void dms(int i) { }


    Function<String, LocalDateTime> convertSQLUTCStrtoLocalTime = (String s1) -> {
        s1 = s1.substring(0,19);
        LocalDateTime ldt = LocalDateTime.parse(s1, dateTimeFormatter);
        LocalDateTime buffldt = ldt;
        ZonedDateTime zdt = ldt.atZone(ZoneId.of("+00:00"));
        ZonedDateTime buffzdt = buffldt.atZone(ZoneId.systemDefault());
        ZoneOffset lzo = buffzdt.getOffset();
        LocalDateTime localTime = zdt.plus(lzo.getTotalSeconds(), ChronoUnit.SECONDS).toLocalDateTime();
        return localTime;
    };
}


