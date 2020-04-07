import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class AdminPage implements Initializable {

    @FXML
    public CheckBox enableduseraccessloggingcheckbox;
    @FXML
    public TextField filepathtextfield;
    @FXML
    public Label logfilepathlabel;
    @FXML
    public Button editfilepathbutton;
    @FXML
    public Button savebutton;
    @FXML
    public Button cancelbutton;

    SimpleStringProperty logpath = new SimpleStringProperty();
    Integer enableduseraccesslogging;
    Integer client_pk = null;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        enableduseraccessloggingcheckbox.setAllowIndeterminate(false);
        filepathtextfield.textProperty().bindBidirectional(logpath);
        enableduseraccesslogging = checkStoredSettings(Main.getDBHelper().runQuery.apply("SELECT * FROM clientsettings;"));
        if (enableduseraccesslogging == 1)  {
            enableduseraccessloggingcheckbox.setSelected(true);
            logfilepathlabel.setDisable(false);
            filepathtextfield.setDisable(false);
            editfilepathbutton.setDisable(false);
        }
    }


    public int checkStoredSettings(ResultSet r){
        boolean check = false;
        boolean audit = false;
        try {
            while(r.next()){

                if (r.getObject(1).toString().equals(InetAddress.getLocalHost().getHostName())){
                    check = true;
                    client_pk = (Integer) r.getObject(2);
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


    public void setLogpath(String logpath) {
        this.logpath.set(logpath);
    }

    public void dms(int i) { }


    public void enableuseraccessloggingcheckboxchanged(ActionEvent actionEvent) {
    if (enableduseraccessloggingcheckbox.isSelected()) {
        enableduseraccesslogging = 1;
        logfilepathlabel.setDisable(false);
        filepathtextfield.setDisable(false);
        editfilepathbutton.setDisable(false);

    }
    else if (!enableduseraccessloggingcheckbox.isSelected()){
        enableduseraccesslogging = 0;
        logfilepathlabel.setDisable(true);
        filepathtextfield.setDisable(true);
        editfilepathbutton.setDisable(true);

    }
    }

    public void editfilepathbuttonpressed(ActionEvent actionEvent) {
        FileChooser fc = new FileChooser();
        try{
        String logfilepath = fc.showSaveDialog(null).getCanonicalPath();
       setLogpath(logfilepath);
        }
        catch (IOException e){
            e.printStackTrace(); //chose not to set logpath to null in the case the pre-existing path was to be kept. this should prevent user path data loss.
        }
    }

    public void savebuttonpressed(ActionEvent actionEvent) {
        if (enableduseraccesslogging == 1) {
            try {
                String q1 = "UPDATE clientsettings SET hostname = '" + InetAddress.getLocalHost().getHostName() + "', logonauditenabled = 1, logpath = '" + logpath.get() + "' WHERE client_pk = " + client_pk + ";";
                String q1buff = q1.replaceAll("\\\\", "\\\\\\\\");
                dms(Main.getDBHelper().runDMS.apply(q1buff));
            }
            catch (UnknownHostException h){
                h.printStackTrace();
            }
        }
        else if (enableduseraccesslogging == 0){
            try {
                String q1 = "UPDATE clientsettings SET hostname = '" + InetAddress.getLocalHost().getHostName() + "', logonauditenabled = 0, logpath = '" + logpath.get() + "' WHERE client_pk = " + client_pk + ";";
                dms(Main.getDBHelper().runDMS.apply(q1));
        } catch (UnknownHostException h){
                h.printStackTrace();
            }
    }
       Stage stage = (Stage) savebutton.getScene().getWindow();
        stage.close();
}

    public void cancelbuttonpressed(ActionEvent actionEvent) {
        if (cancelCheck()) {
            Stage stage = (Stage) cancelbutton.getScene().getWindow();
            stage.close();
        }
    }

    public boolean cancelCheck() {
        boolean check = false;
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Cancel");
        alert.setContentText("Are you sure you want to cancel and discard changes?");
        alert.getButtonTypes().set(0, ButtonType.YES);
        alert.getButtonTypes().set(1, ButtonType.NO);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.YES) check = true;
        return check;
    }
}
