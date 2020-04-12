import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.ResourceBundle;

public class Lookup implements Initializable {

    //customer node declarations
    @FXML
    public Pane CustomerLookupPane;
    @FXML
    public TableView<Customer> customerviewtable;
    @FXML
    public TableColumn<Customer, Integer> customeridcolumn;
    @FXML
    public TableColumn<Customer, String> customernamecolumn;
    @FXML
    public TableColumn<Customer, String> customeractivecolumn;
    @FXML
    public TableColumn<Customer, String> customeraddresscolumn;
    @FXML
    public RadioButton customerIDratio;
    @FXML
    public RadioButton customernameRatio;
    @FXML
    public Button customersearchbutton;
    @FXML
    public TextField customerinputfield;
    @FXML
    public RadioButton customercontainsratio;
    @FXML
    public RadioButton customerexactratio;
    @FXML
    public Button customeraddbutton;
    @FXML
    public Button customermodifyselectedbutton;
    @FXML
    public Button customerdeletebutton;
    @FXML
    public Button customercancelbutton;


    //appointment node declarations
    @FXML
    public Pane AppointmentLookupPane;
    @FXML
    public TableView<Appointment> apptsearchtable;
    @FXML
    public TableColumn<Appointment, String> apptidcolumn;
    @FXML
    public TableColumn<Appointment, String> apptcustomernamecolumn;
    @FXML
    public TableColumn<Appointment, LocalDateTime> apptdatetimecolumn;
    @FXML
    public TableColumn<Appointment, String> apptconsultantcol;
    @FXML
    public TableColumn<Appointment, String> appttitlecolumn;
    @FXML
    public RadioButton apptapptidratio;
    @FXML
    public RadioButton apptcustomernameratio;
    @FXML
    public Button apptsearchbutton;
    @FXML
    public TextField apptinputfield;
    @FXML
    public RadioButton apptcontainsratio;
    @FXML
    public RadioButton apptexactratio;
    @FXML
    public Button apptnewbutton;
    @FXML
    public Button apptmodifybutton;
    @FXML
    public Button apptdeletebutton;
    @FXML
    public Button apptcancelbutton;
    @FXML
    public RadioButton apptconsultantnameratio;
    @FXML
    public RadioButton apptappttitleratio;


    //consultant node declarations
    @FXML
    public Pane ConsultantLookupPane;
    @FXML
    public TableView<User> consultantsearchtableview;
    @FXML
    public TableColumn<User, Integer> consultantidcolumn;
    @FXML
    public TableColumn<User, String> consultantnamecolumn;
    @FXML
    public TableColumn<User, Integer> consultantisactivecolumn;
    @FXML
    public TableColumn<User, LocalDateTime> consultantlastupdatedcolumn;
    @FXML
    public RadioButton consultantidratio;
    @FXML
    public RadioButton consultantnameratio;
    @FXML
    public TextField consultantsearchinputfield;
    @FXML
    public RadioButton consultantcontainsratio;
    @FXML
    public RadioButton consultantexactratio;
    @FXML
    public Button consultantcancelbutton;
    @FXML
    public Button consultantcopyIDbutton;
    @FXML
    public Button consultantsearchbutton;


    //other declarations
    @FXML
    public StackPane stackPane;
    @FXML
    public AnchorPane anchorPane;

    public ToggleGroup customerinputtypegroup = new ToggleGroup();
    public ToggleGroup customersearchmethodgroup = new ToggleGroup();
    public ToggleGroup consultantinputtypegroup = new ToggleGroup();
    public ToggleGroup consultantsearchmethodgroup = new ToggleGroup();
    public ToggleGroup apptinputtypegroup = new ToggleGroup();
    public ToggleGroup apptsearchmethodgroup = new ToggleGroup();
    public SimpleStringProperty customerinput = new SimpleStringProperty();
    public SimpleStringProperty consultantinput = new SimpleStringProperty();
    public SimpleStringProperty apptinput = new SimpleStringProperty();
    public ObservableList<Customer> customerObservableList = FXCollections.observableArrayList();
    public ObservableList<User> userObservableList = FXCollections.observableArrayList();
    public ObservableList<Appointment> appointmentObservableList = FXCollections.observableArrayList();
    public ObservableList<Customer> custbuff = FXCollections.observableArrayList();
    public ObservableList<User> userbuff = FXCollections.observableArrayList();
    public ObservableList<Appointment> apptbuff = FXCollections.observableArrayList();
    public int panetoLoad = 0;



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
        customeractivecolumn.setCellValueFactory(new PropertyValueFactory<>("activeString"));
        customeraddresscolumn.setCellValueFactory(new PropertyValueFactory<>("addressString"));
        apptsearchtable.setItems(appointmentObservableList);
        apptidcolumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        apptcustomernamecolumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        apptdatetimecolumn.setCellValueFactory(new PropertyValueFactory<>("start"));
        apptconsultantcol.setCellValueFactory(new PropertyValueFactory<>("consultantName"));
        appttitlecolumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        consultantsearchtableview.setItems(userObservableList);
        consultantidcolumn.setCellValueFactory(new PropertyValueFactory<>("userId"));
        consultantnamecolumn.setCellValueFactory(new PropertyValueFactory<>("userName"));
        consultantisactivecolumn.setCellValueFactory(new PropertyValueFactory<>("activeString"));
        consultantlastupdatedcolumn.setCellValueFactory(new PropertyValueFactory<>("lastUpdate"));
        loadPane(panetoLoad);

    }


//toggle different panes upon loading

    public void loadPane(int i) { //1 = Customer, 2 = Appt, 3 = Consultant/User
        switch (i) {
            case 1: {
                CustomerLookupPane.setDisable(false);
                CustomerLookupPane.setVisible(true);
                ConsultantLookupPane.setVisible(false);
                AppointmentLookupPane.setVisible(false);
                refreshCustomerTable();
                break;
            }
            case 2: {
                AppointmentLookupPane.setDisable(false);
                AppointmentLookupPane.setVisible(true);
                CustomerLookupPane.setVisible(false);
                ConsultantLookupPane.setVisible(false);
                refreshAppointmentTable();
                break;
            }
            case 3: {
                ConsultantLookupPane.setDisable(false);
                ConsultantLookupPane.setVisible(true);
                AppointmentLookupPane.setVisible(false);
                CustomerLookupPane.setVisible(false);
                refreshConsultantTable();
                break;
            }
        }

    }

//customer node functions

    public void customeridratiopressed(ActionEvent actionEvent) {
    }

    public void customernameratiopressed(ActionEvent actionEvent) {
    }

    public void customersearchbuttonpressed(ActionEvent actionEvent) {
        //write code to filter table

        if (customerIDratio.isSelected()){ //if searching via customerID
            if (customercontainsratio.isSelected()){ //and selected contains
                if (customerinput.get().length() == 0) {
                    refreshCustomerTable();
                } else {
                    this.customerObservableList.setAll(custbuff);
                    ObservableList<Customer> temp = FXCollections.observableArrayList();
                    boolean check = false;
                    for (Customer c : customerObservableList){
                        if (c.getCustomerId().toString().toLowerCase().contains(customerinput.get().toLowerCase())){
                            temp.add(c);
                            check = true;
                        }
                    }
                    if (!check) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setHeaderText("Error: Customer DNE");
                        alert.setTitle("Non-existent customer");
                        alert.setContentText("Unable to find a customer using the criteria specified.");
                        alert.showAndWait();
                        refreshCustomerTable();
                        return;
                    }
                    this.customerObservableList.setAll(temp);
                }

                }
            else if (customerexactratio.isSelected()){ //and selected exact
            if (customerinput.get().length() == 0) {
                refreshCustomerTable();
            } else {
                this.customerObservableList.setAll(custbuff);
                ObservableList<Customer> temp = FXCollections.observableArrayList();
                boolean check = false;
                for (Customer c : customerObservableList){
                    if (c.getCustomerId().toString().equalsIgnoreCase(customerinput.get())){
                        temp.add(c);
                        check = true;
                    }
                }
                if (!check) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText("Error: Customer DNE");
                    alert.setTitle("Non-existent customer");
                    alert.setContentText("Unable to find a customer using the criteria specified.");
                    alert.showAndWait();
                    refreshCustomerTable();
                    return;
                }
                this.customerObservableList.setAll(temp);
            }
        }
        }
        else if (customernameRatio.isSelected()){ //if searching via customer name
            if (customercontainsratio.isSelected()){ //and selected contains
                if (customerinput.get().length() == 0) {
                    refreshCustomerTable();
                } else {
                    this.customerObservableList.setAll(custbuff);
                    ObservableList<Customer> temp = FXCollections.observableArrayList();
                    boolean check = false;
                    for (Customer c : customerObservableList){
                        if (c.getCustomerName().toLowerCase().contains(customerinput.get().toLowerCase())){
                            temp.add(c);
                            check = true;
                        }
                    }
                    if (!check) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setHeaderText("Error: Customer DNE");
                        alert.setTitle("Non-existent customer");
                        alert.setContentText("Unable to find a customer using the criteria specified.");
                        alert.showAndWait();
                        refreshCustomerTable();
                        return;
                    }
                    this.customerObservableList.setAll(temp);
                }
            }
            else if (customerexactratio.isSelected()){ //and selected exact
                if (customerinput.get().length() == 0) {
                    refreshCustomerTable();
                } else {
                    this.customerObservableList.setAll(custbuff);
                    ObservableList<Customer> temp = FXCollections.observableArrayList();
                    boolean check = false;
                    for (Customer c : customerObservableList){
                        if (c.getCustomerName().equalsIgnoreCase(customerinput.get())){
                            temp.add(c);
                            check = true;
                        }
                    }
                    if (!check) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setHeaderText("Error: Customer DNE");
                        alert.setTitle("Non-existent customer");
                        alert.setContentText("Unable to find a customer using the criteria specified.");
                        alert.showAndWait();
                        refreshCustomerTable();
                        return;
                    }
                    this.customerObservableList.setAll(temp);
                }
            }
        }
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
    if (apptapptidratio.isSelected()){
        if (apptcontainsratio.isSelected()) {
            if (apptinput.get().length() == 0) {
                refreshAppointmentTable();
            } else {
                this.appointmentObservableList.setAll(apptbuff);
                ObservableList<Appointment> temp = FXCollections.observableArrayList();
                boolean check = false;
                for (Appointment c : appointmentObservableList) {
                    if (c.getId().toLowerCase().contains(apptinput.get().toLowerCase())) {
                        temp.add(c);
                        check = true;
                    }
                }
                if (!check) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText("Error: Appointment DNE");
                    alert.setTitle("Non-existent appointment");
                    alert.setContentText("Unable to find an appointment using the criteria specified.");
                    alert.showAndWait();
                    refreshCustomerTable();
                    return;
                }
                this.appointmentObservableList.setAll(temp);
            }
        }
        else if (apptexactratio.isSelected()){
            if (apptinput.get().length() == 0) {
                refreshCustomerTable();
            } else {
                this.appointmentObservableList.setAll(apptbuff);
                ObservableList<Appointment> temp = FXCollections.observableArrayList();
                boolean check = false;
                for (Appointment c : appointmentObservableList) {
                    if (c.getId().equalsIgnoreCase(apptinput.get())) {
                        temp.add(c);
                        check = true;
                    }
                }
                if (!check) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText("Error: Appointment DNE");
                    alert.setTitle("Non-existent appointment");
                    alert.setContentText("Unable to find an appointment using the criteria specified.");
                    alert.showAndWait();
                    refreshCustomerTable();
                    return;
                }
                this.appointmentObservableList.setAll(temp);
            }
        }
    }
    else if (apptcustomernameratio.isSelected()){
        if (apptcontainsratio.isSelected()) {
            if (apptinput.get().length() == 0) {
                refreshAppointmentTable();
            } else {
                this.appointmentObservableList.setAll(apptbuff);
                ObservableList<Appointment> temp = FXCollections.observableArrayList();
                boolean check = false;
                for (Appointment c : appointmentObservableList) {
                    if (c.getCustomerName().toLowerCase().contains(apptinput.get().toLowerCase())) {
                        temp.add(c);
                        check = true;
                    }
                }
                if (!check) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText("Error: Appointment DNE");
                    alert.setTitle("Non-existent appointment");
                    alert.setContentText("Unable to find an appointment using the criteria specified.");
                    alert.showAndWait();
                    refreshCustomerTable();
                    return;
                }
                this.appointmentObservableList.setAll(temp);
            }
        }
        else if (apptexactratio.isSelected()){
            if (apptinput.get().length() == 0) {
                refreshCustomerTable();
            } else {
                this.appointmentObservableList.setAll(apptbuff);
                ObservableList<Appointment> temp = FXCollections.observableArrayList();
                boolean check = false;
                for (Appointment c : appointmentObservableList) {
                    if (c.getCustomerName().equalsIgnoreCase(apptinput.get())) {
                        temp.add(c);
                        check = true;
                    }
                }
                if (!check) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText("Error: Appointment DNE");
                    alert.setTitle("Non-existent appointment");
                    alert.setContentText("Unable to find an appointment using the criteria specified.");
                    alert.showAndWait();
                    refreshCustomerTable();
                    return;
                }
                this.appointmentObservableList.setAll(temp);
            }
        }
    }
    else if (apptconsultantnameratio.isSelected()) {
        if (apptcontainsratio.isSelected()) {
            if (apptinput.get().length() == 0) {
                refreshAppointmentTable();
            } else {
                this.appointmentObservableList.setAll(apptbuff);
                ObservableList<Appointment> temp = FXCollections.observableArrayList();
                boolean check = false;
                for (Appointment c : appointmentObservableList) {
                    if (c.getConsultantName().toLowerCase().contains(apptinput.get().toLowerCase())) {
                        temp.add(c);
                        check = true;
                    }
                }
                if (!check) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText("Error: Appointment DNE");
                    alert.setTitle("Non-existent appointment");
                    alert.setContentText("Unable to find an appointment using the criteria specified.");
                    alert.showAndWait();
                    refreshCustomerTable();
                    return;
                }
                this.appointmentObservableList.setAll(temp);
            }
        }
        else if (apptexactratio.isSelected()){
            if (apptinput.get().length() == 0) {
                refreshCustomerTable();
            } else {
                this.appointmentObservableList.setAll(apptbuff);
                ObservableList<Appointment> temp = FXCollections.observableArrayList();
                boolean check = false;
                for (Appointment c : appointmentObservableList) {
                    if (c.getConsultantName().equalsIgnoreCase(apptinput.get())) {
                        temp.add(c);
                        check = true;
                    }
                }
                if (!check) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText("Error: Appointment DNE");
                    alert.setTitle("Non-existent appointment");
                    alert.setContentText("Unable to find an appointment using the criteria specified.");
                    alert.showAndWait();
                    refreshCustomerTable();
                    return;
                }
                this.appointmentObservableList.setAll(temp);
            }
        }
    }
    else if (apptappttitleratio.isSelected()){
        if (apptcontainsratio.isSelected()) {
            if (apptinput.get().length() == 0) {
                refreshAppointmentTable();
            } else {
                this.appointmentObservableList.setAll(apptbuff);
                ObservableList<Appointment> temp = FXCollections.observableArrayList();
                boolean check = false;
                for (Appointment c : appointmentObservableList) {
                    if (c.getTitle().toLowerCase().contains(apptinput.get().toLowerCase())) {
                        temp.add(c);
                        check = true;
                    }
                }
                if (!check) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText("Error: Appointment DNE");
                    alert.setTitle("Non-existent appointment");
                    alert.setContentText("Unable to find an appointment using the criteria specified.");
                    alert.showAndWait();
                    refreshCustomerTable();
                    return;
                }
                this.appointmentObservableList.setAll(temp);
            }
        }
        else if (apptexactratio.isSelected()){
            if (apptinput.get().length() == 0) {
                refreshCustomerTable();
            } else {
                this.appointmentObservableList.setAll(apptbuff);
                ObservableList<Appointment> temp = FXCollections.observableArrayList();
                boolean check = false;
                for (Appointment c : appointmentObservableList) {
                    if (c.getTitle().equalsIgnoreCase(apptinput.get())) {
                        temp.add(c);
                        check = true;
                    }
                }
                if (!check) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText("Error: Appointment DNE");
                    alert.setTitle("Non-existent appointment");
                    alert.setContentText("Unable to find an appointment using the criteria specified.");
                    alert.showAndWait();
                    refreshCustomerTable();
                    return;
                }
                this.appointmentObservableList.setAll(temp);
            }
        }
    }


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

    public void consultantsearchbuttonpressed(ActionEvent actionEvent) {

    }


//misc functions


    private void refreshCustomerTable(){
        //query customer table -> if successful clear customerobservablelist -> loop through and add all customers from query to customerobservablelist -> table.setitems to customerobservablelist
        ResultSet r = Main.getDBHelper().runQuery.apply("SELECT customerId from customer ORDER BY customerId;");
        int check = 0;
        try {
            while (r.next()) {
                if (check == 0) { //check to ensure this runs only once, and only if query successfully returns results
                    this.customerObservableList.clear();
                }
                check = 1;

                this.customerObservableList.add(new Customer(Main.getDBHelper().runQuery.apply("SELECT * from customer WHERE customerId = '" + r.getObject(1) + "';")));

            }
        }
        catch (SQLException se){
            se.printStackTrace();
        }
        this.customerviewtable.setItems(this.customerObservableList);
        this.custbuff.setAll(customerObservableList);
    }

    private void refreshAppointmentTable(){
        //query appt table -> if successful clear appointmentobservablelist -> loop through and add all appt from query to apptobservablelist -> table.setitems to apptobservablelist
        {
            ResultSet r = Main.getDBHelper().runQuery.apply("SELECT appointmentId from appointment ORDER BY appointmentId;");
            int check = 0;
            try {
                while (r.next()) {
                    if (check == 0) { //check to ensure this runs only once, and only if query successfully returns results
                        this.appointmentObservableList.clear();
                    }
                    check = 1;

                    this.appointmentObservableList.add(new Appointment(Main.getDBHelper().runQuery.apply("SELECT * from appointment WHERE appointmentId = '" + r.getObject(1) + "';")));

                }
            }
            catch (SQLException se){
                se.printStackTrace();
            }
            this.apptsearchtable.setItems(this.appointmentObservableList);

            this.apptbuff.setAll(appointmentObservableList);
        }
    }

    private void refreshConsultantTable(){
        //same as above but user
        {
            ResultSet r = Main.getDBHelper().runQuery.apply("SELECT userId from user ORDER BY userId;");
            int check = 0;
            try {
                while (r.next()) {
                    if (check == 0) { //check to ensure this runs only once, and only if query successfully returns results
                        this.userObservableList.clear();
                    }
                    check = 1;

                    this.userObservableList.add(new User(Main.getDBHelper().runQuery.apply("SELECT * from user WHERE userId = '" + r.getObject(1) + "';")));

                }
            }
            catch (SQLException se){
                se.printStackTrace();
            }
            this.consultantsearchtableview.setItems(this.userObservableList);

            this.userbuff.setAll(userObservableList);
        }
    }

    public void setpanetoLoad(int i){
        this.panetoLoad = i;
    }


}