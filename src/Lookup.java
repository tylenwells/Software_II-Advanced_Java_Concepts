import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class Lookup implements Initializable {

//customer node declarations
    public Pane CustomerLookupPane;
    public TableView<Customer> customerviewtable;
    public TableColumn<Customer, Integer> customeridcolumn;
    public TableColumn<Customer, String> customernamecolumn;
    public TableColumn<Customer, Integer> customeractivecolumn;
    public TableColumn<Customer, String> customeraddresscolumn; //need to figure out how to parse this
    public RadioButton customerIDratio;
    public RadioButton customernameRatio;
    public Button customersearchbutton;
    public TextField customerinputfield;
    public RadioButton customercontainsratio;
    public RadioButton customerexactratio;
    public Button customeraddbutton;
    public Button customermodifyselectedbutton;
    public Button customerdeletebutton;
    public Button customercancelbutton;


//appointment node declarations
    public Pane AppointmentLookupPane;
    public TableView<Appointment> apptsearchtable;
    public TableColumn<Appointment, Integer> apptidcolumn;
    public TableColumn<Appointment, String> apptcustomernamecolumn;
    public TableColumn<Appointment, LocalDateTime> apptdatetimecolumn;
    public TableColumn<Appointment, String> apptconsultantcol;
    public TableColumn<Appointment, String> appttitlecolumn;
    public RadioButton apptapptidratio;
    public RadioButton apptcustomernameratio;
    public Button apptsearchbutton;
    public TextField apptinputfield;
    public RadioButton apptcontainsratio;
    public RadioButton apptexactratio;
    public Button apptnewbutton;
    public Button apptmodifybutton;
    public Button apptdeletebutton;
    public Button apptcancelbutton;
    public RadioButton apptconsultantnameratio;
    public RadioButton apptappttitleratio;


//consultant node declarations
    public Pane ConsultantLookupPane;
    public TableView<User> consultantsearchtableview;
    public TableColumn<User, Integer> consultantidcolumn;
    public TableColumn<User, String> consultantnamecolumn;
    public TableColumn<User, Integer> consultantisactivecolumn;
    public TableColumn<User, LocalDateTime> consultantlastupdatedcolumn;
    public RadioButton consultantidratio;
    public RadioButton consultantnameratio;
    public TextField consultantsearchinputfield;
    public RadioButton consultantcontainsratio;
    public RadioButton consultantexactratio;
    public Button consultantcancelbutton;
    public Button consultantcopyIDbutton;


//other declarations
    public ToggleGroup customerinputtypegroup = new ToggleGroup();
    public ToggleGroup customersearchmethodgroup = new ToggleGroup();
    public ToggleGroup consultantinputtypegroup = new ToggleGroup();
    public ToggleGroup consultantsearchmethodgroup = new ToggleGroup();
    public ToggleGroup apptinputtypegroup = new ToggleGroup();
    public ToggleGroup apptsearchmethodgroup = new ToggleGroup();
    public SimpleStringProperty customerinput = new SimpleStringProperty();
    public SimpleStringProperty consultantinput = new SimpleStringProperty();
    public SimpleStringProperty apptinput = new SimpleStringProperty();
    public ObservableList<Customer> customerObservableList;
    public ObservableList<User> userObservableList;
    public ObservableList<Appointment> appointmentObservableList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        customernameRatio.setToggleGroup(customerinputtypegroup);
        customerIDratio.setToggleGroup(customerinputtypegroup);
        customercontainsratio.setToggleGroup(customersearchmethodgroup);
        customerexactratio.setToggleGroup(customersearchmethodgroup);
        apptapptidratio.setToggleGroup(apptinputtypegroup);
        apptappttitleratio.setToggleGroup(apptinputtypegroup);
        apptcustomernameratio.setToggleGroup(apptinputtypegroup);
        apptconsultantnameratio.setToggleGroup(apptinputtypegroup);
        apptcontainsratio.setToggleGroup(apptsearchmethodgroup);
        apptexactratio.setToggleGroup(apptsearchmethodgroup);
        consultantidratio.setToggleGroup(consultantinputtypegroup);
        consultantnameratio.setToggleGroup(consultantinputtypegroup);
        consultantexactratio.setToggleGroup(consultantsearchmethodgroup);
        consultantcontainsratio.setToggleGroup(consultantsearchmethodgroup);
        consultantsearchinputfield.textProperty().bindBidirectional(consultantinput);
        customerinputfield.textProperty().bindBidirectional(customerinput);
        apptinputfield.textProperty().bindBidirectional(apptinput);
        customerviewtable.setItems(customerObservableList);
        customeridcolumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        customernamecolumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        customeractivecolumn.setCellValueFactory(new PropertyValueFactory<>());
    }

//customer node functions

    public void customeridratiopressed(ActionEvent actionEvent) {
    }

    public void customernameratiopressed(ActionEvent actionEvent) {
    }

    public void customersearchbuttonpressed(ActionEvent actionEvent) {
    }

    public void customercontainsratiopressed(ActionEvent actionEvent) {
    }

    public void customerexactratiopressed(ActionEvent actionEvent) {
    }

    public void customeraddbuttonpressed(ActionEvent actionEvent) {
    }

    public void customermodifyselectedbuttonpressed(ActionEvent actionEvent) {
    }

    public void customerdeletebuttonpressed(ActionEvent actionEvent) {
    }

    public void customercancelbuttonpressed(ActionEvent actionEvent) {
    }


//appointment node functions

    public void apptapptidratiopressed(ActionEvent actionEvent) {
    }

    public void apptcustomernameratiopressed(ActionEvent actionEvent) {
    }

    public void apptsearchbuttonpressed(ActionEvent actionEvent) {
    }

    public void apptcontainsratiopressed(ActionEvent actionEvent) {
    }

    public void apptexactratioselected(ActionEvent actionEvent) {
    }

    public void apptnewbuttonpressed(ActionEvent actionEvent) {
    }

    public void apptmodifybuttonpressed(ActionEvent actionEvent) {
    }

    public void apptdeletebuttonpressed(ActionEvent actionEvent) {
    }

    public void apptcancelbuttonpressed(ActionEvent actionEvent) {
    }

    public void apptconsultantnameratiopressed(ActionEvent actionEvent) {
    }

    public void apptappttitleratiopressed(ActionEvent actionEvent) {
    }




//consultant node functions

    public void consultantidratiopressed(ActionEvent actionEvent) {
    }

    public void consultantnameratiopressed(ActionEvent actionEvent) {
    }

    public void consultantcontainsratiopressed(ActionEvent actionEvent) {
    }

    public void consultantexactratiopressed(ActionEvent actionEvent) {
    }

    public void consultantcancelbuttonpressed(ActionEvent actionEvent) {
    }

    public void consultantcopyidbuttonpressed(ActionEvent actionEvent) {
    }
}
