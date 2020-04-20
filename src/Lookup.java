import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.Consumer;

public class Lookup implements Initializable {

    @FXML
    public AnchorPane anchorPane;

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


    public ToggleGroup customerinputtypegroup = new ToggleGroup();
    public ToggleGroup customersearchmethodgroup = new ToggleGroup();
    public ToggleGroup consultantinputtypegroup = new ToggleGroup();
    public ToggleGroup consultantsearchmethodgroup = new ToggleGroup();
    public ToggleGroup apptinputtypegroup = new ToggleGroup();
    public ToggleGroup apptsearchmethodgroup = new ToggleGroup();
    public SimpleStringProperty customerinput = new SimpleStringProperty("");
    public SimpleStringProperty consultantinput = new SimpleStringProperty("");
    public SimpleStringProperty apptinput = new SimpleStringProperty("");
    public ObservableList<Customer> customerObservableList = FXCollections.observableArrayList();
    public ObservableList<User> userObservableList = FXCollections.observableArrayList();
    public ObservableList<Appointment> appointmentObservableList = FXCollections.observableArrayList();
    public ObservableList<Customer> custbuff = FXCollections.observableArrayList();
    public ObservableList<User> userbuff = FXCollections.observableArrayList();
    public ObservableList<Appointment> apptbuff = FXCollections.observableArrayList();
    public int panetoLoad = 0;
    public String username = "";


    final Clipboard clipboard = Clipboard.getSystemClipboard();
    final ClipboardContent content = new ClipboardContent();



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
                if (customerinput == null || customerinput.get().length() == 0) {
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
            if (customerinput == null || customerinput.get().length() == 0) {
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
                if (customerinput == null || customerinput.get().length() == 0) {
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
                if (customerinput == null || customerinput.get().length() == 0) {
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
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AddEdit.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            AddEdit addEdit = loader.getController();
            Customer c = new Customer();
            addEdit.initdata(c,true, username);
            addEdit.loadPane(1);
            stage.showAndWait();
            refreshCustomerTable();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void customermodifyselectedbuttonpressed(ActionEvent actionEvent) {
        if (customerviewtable.getSelectionModel().getSelectedItem() != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("AddEdit.fxml"));
                Parent root = loader.load();
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                AddEdit addEdit = loader.getController();
                addEdit.initdata(customerviewtable.getSelectionModel().getSelectedItem(), false, username);
                addEdit.loadPane(1);
                stage.showAndWait();
                refreshCustomerTable();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void customerdeletebuttonpressed(ActionEvent actionEvent) {
        boolean check = false;
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Delete Customer");
        alert.setContentText("Are you sure you want to delete the selected customer?" + System.lineSeparator() + System.lineSeparator() + "Please note: This cannot be undone and will remove the selected customer from the database.");
        alert.getButtonTypes().set(0, ButtonType.YES);
        alert.getButtonTypes().set(1, ButtonType.NO);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.YES) check = true;
        if (check){
        boolean depcheck = false;
        Integer depcount = 0;
        String selectedcustId = customerviewtable.getSelectionModel().getSelectedItem().getCustomerId().toString();
        ResultSet r = getqueryResult(Main.getDBHelper().runQuery.apply("SELECT COUNT(appointmentId) from appointment WHERE customerId = '" + selectedcustId + "';"));
      try {
          while (r.next()) {
              if (r.getInt(1) != 0) {
                  depcheck = true;
                  depcount = r.getInt(1);
              }
          }
      }
      catch (SQLException e){
          e.printStackTrace();
      }

        if (depcheck){
            boolean check2 = false;
            Alert alert2 = new Alert(Alert.AlertType.CONFIRMATION);
            alert2.setTitle("Confirmation");
            alert2.setHeaderText("Delete Customer");
            alert2.setContentText("Customer has " + depcount.toString() + " appointments." + System.lineSeparator() + System.lineSeparator() + "Please note: Deleting this customer will also delete all associated appointments. Do you wish to proceed?");
            alert2.getButtonTypes().set(0, ButtonType.YES);
            alert2.getButtonTypes().set(1, ButtonType.NO);
            Optional<ButtonType> result2 = alert2.showAndWait();
            if (result2.get() == ButtonType.YES) check2 = true;
            if (check2){
                dms(Main.getDBHelper().runDMS.apply("DELETE FROM appointment WHERE customerId = '" + selectedcustId + "';"));
                dms(Main.getDBHelper().runDMS.apply("DELETE FROM customer WHERE customerId = '" + selectedcustId + "';"));
            }
        }
        else {
            dms(Main.getDBHelper().runDMS.apply("DELETE FROM customer WHERE customerId = '" + selectedcustId + "';"));
        }
        }
        refreshCustomerTable();
    }

    public void customercancelbuttonpressed(ActionEvent actionEvent) {
        Stage stage = (Stage) customercancelbutton.getScene().getWindow();
        stage.close();
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
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AddEdit.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            AddEdit addEdit = loader.getController();
            Appointment a = new Appointment();
            addEdit.initdata(a,true, username);
            addEdit.loadPane(2);
            stage.showAndWait();
            refreshAppointmentTable();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void apptmodifybuttonpressed(ActionEvent actionEvent) {
        if (apptsearchtable.getSelectionModel().getSelectedItem() != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("AddEdit.fxml"));
                Parent root = loader.load();
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                AddEdit addEdit = loader.getController();
                addEdit.initdata(apptsearchtable.getSelectionModel().getSelectedItem(), false, username);
                addEdit.loadPane(2);
                stage.showAndWait();
                refreshAppointmentTable();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void apptdeletebuttonpressed(ActionEvent actionEvent) {
        boolean check = false;
        if (apptsearchtable.getSelectionModel().getSelectedItem() != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Delete Appointment");
            alert.setContentText("Are you sure you want to delete the selected appointment?" + System.lineSeparator() + System.lineSeparator() + "Please note: This cannot be undone and will remove the selected appointment from the database.");
            alert.getButtonTypes().set(0, ButtonType.YES);
            alert.getButtonTypes().set(1, ButtonType.NO);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.YES) check = true;
            if (check) {
                dms(Main.getDBHelper().runDMS.apply("DELETE FROM appointment WHERE appointmentId = '" + apptsearchtable.getSelectionModel().getSelectedItem().getId() +"';"));
            }
            refreshAppointmentTable();
        }
    }

    public void apptcancelbuttonpressed(ActionEvent actionEvent) {
        Stage stage = (Stage) apptcancelbutton.getScene().getWindow();
        stage.close();
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
        Stage stage = (Stage) consultantcancelbutton.getScene().getWindow();
        stage.close();
    }

    public void consultantcopyidbuttonpressed(ActionEvent actionEvent) {
        content.putString(this.consultantsearchtableview.getSelectionModel().getSelectedItem().getUserId().toString());
        clipboard.setContent(content);
    }

    public void consultantsearchbuttonpressed(ActionEvent actionEvent) {
        if (consultantidratio.isSelected()){ //if searching via consultantId
            if (consultantcontainsratio.isSelected()){ //and selected contains
                if (consultantinput.get().length() == 0) {
                    refreshCustomerTable();
                } else {
                    this.userObservableList.setAll(userbuff);
                    ObservableList<User> temp = FXCollections.observableArrayList();
                    boolean check = false;
                    for (User c : userObservableList){
                        if (c.getUserId().toString().toLowerCase().contains(consultantinput.get().toLowerCase())){
                            temp.add(c);
                            check = true;
                        }
                    }
                    if (!check) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setHeaderText("Error: Consultant DNE");
                        alert.setTitle("Non-existent consultant");
                        alert.setContentText("Unable to find a consultant using the criteria specified.");
                        alert.showAndWait();
                        refreshConsultantTable();
                        return;
                    }
                    this.userObservableList.setAll(temp);
                }

            }
            else if (consultantexactratio.isSelected()){ //and selected exact
                if (consultantinput.get().length() == 0) {
                    refreshCustomerTable();
                } else {
                    this.userObservableList.setAll(userbuff);
                    ObservableList<User> temp = FXCollections.observableArrayList();
                    boolean check = false;
                    for (User c : userObservableList){
                        if (c.getUserId().toString().equalsIgnoreCase(consultantinput.get())){
                            temp.add(c);
                            check = true;
                        }
                    }
                    if (!check) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setHeaderText("Error: Consultant DNE");
                        alert.setTitle("Non-existent consultant");
                        alert.setContentText("Unable to find a consultant using the criteria specified.");
                        alert.showAndWait();
                        refreshConsultantTable();
                        return;
                    }
                    this.userObservableList.setAll(temp);
                }
            }
        }
        else if (consultantnameratio.isSelected()){ //if searching via customer name
            if (consultantcontainsratio.isSelected()){ //and selected contains
                if (consultantinput.get().length() == 0) {
                    refreshCustomerTable();
                } else {
                    this.userObservableList.setAll(userbuff);
                    ObservableList<User> temp = FXCollections.observableArrayList();
                    boolean check = false;
                    for (User c : userObservableList){
                        if (c.getUserName().toLowerCase().contains(consultantinput.get().toLowerCase())){
                            temp.add(c);
                            check = true;
                        }
                    }
                    if (!check) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setHeaderText("Error: Consultant DNE");
                        alert.setTitle("Non-existent consultant");
                        alert.setContentText("Unable to find a consultant using the criteria specified.");
                        alert.showAndWait();
                        refreshConsultantTable();
                        return;
                    }
                    this.userObservableList.setAll(temp);
                }

            }
            else if (consultantexactratio.isSelected()){ //and selected exact
                if (consultantinput.get().length() == 0) {
                    refreshCustomerTable();
                } else {
                    this.userObservableList.setAll(userbuff);
                    ObservableList<User> temp = FXCollections.observableArrayList();
                    boolean check = false;
                    for (User c : userObservableList){
                        if (c.getUserName().toString().equalsIgnoreCase(consultantinput.get())){
                            temp.add(c);
                            check = true;
                        }
                    }
                    if (!check) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setHeaderText("Error: Consultant DNE");
                        alert.setTitle("Non-existent consultant");
                        alert.setContentText("Unable to find a consultant using the criteria specified.");
                        alert.showAndWait();
                        refreshConsultantTable();
                        return;
                    }
                    this.userObservableList.setAll(temp);
                }
            }
        }
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

    public void initdata(String username){
        this.username = username;
    }

    public void dms(int i) { }

    public ResultSet getqueryResult(ResultSet r) {
        return r;
    }



}