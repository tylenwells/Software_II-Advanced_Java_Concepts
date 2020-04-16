import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.function.Consumer;

public class FindPage implements Initializable {

    @FXML
    public Pane FindConsultantPane;
    @FXML
    public TableView<User> consultanttable;
    @FXML
    public TableColumn<User, String> consultantidcolumn;
    @FXML
    public TableColumn<User, String> consultantnamecolumn;
    @FXML
    public RadioButton consultantidratio;
    @FXML
    public RadioButton consultantnameratio;
    @FXML
    public Button consultantsearchbutton;
    @FXML
    public TextField consultantsearchinputfield;
    @FXML
    public RadioButton consultantcontainsratio;
    @FXML
    public RadioButton consultantexactratio;
    @FXML
    public Button consultantcancelbutton;
    @FXML
    public Button userselectedconsultantbutton;
    @FXML
    public Pane FindCustomerPane;
    @FXML
    public TableView<Customer> customertable;
    @FXML
    public TableColumn<Customer, String> customeridcolumn;
    @FXML
    public TableColumn<Customer, String> customernamecolumn;
    @FXML
    public RadioButton customeridratio;
    @FXML
    public RadioButton customernameratio;
    @FXML
    public Button customersearchbutton;
    @FXML
    public TextField customersearchinputfield;
    @FXML
    public RadioButton customercontainsratio;
    @FXML
    public RadioButton customerexactratio;
    @FXML
    public Button customercancelbutton;
    @FXML
    public Button useselectedcustomerbutton;

    public ToggleGroup customerinputtypegroup = new ToggleGroup();
    public ToggleGroup customersearchmethodgroup = new ToggleGroup();
    public ToggleGroup consultantinputtypegroup = new ToggleGroup();
    public ToggleGroup consultantsearchmethodgroup = new ToggleGroup();
    public SimpleStringProperty customerinput = new SimpleStringProperty("");
    public SimpleStringProperty consultantinput = new SimpleStringProperty("");
    public ObservableList<Customer> customerObservableList = FXCollections.observableArrayList();
    public ObservableList<User> userObservableList = FXCollections.observableArrayList();
    public ObservableList<Customer> custbuff = FXCollections.observableArrayList();
    public ObservableList<User> userbuff = FXCollections.observableArrayList();
    public int panetoLoad = 0;
    private Consumer<User> onConsultantComplete;
    private Consumer<Customer> onCustomerComplete;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        customernameratio.setToggleGroup(customerinputtypegroup);
        customeridratio.setToggleGroup(customerinputtypegroup);
        customercontainsratio.setToggleGroup(customersearchmethodgroup);
        customerexactratio.setToggleGroup(customersearchmethodgroup);
        consultantnameratio.setToggleGroup(consultantinputtypegroup);
        consultantidratio.setToggleGroup(consultantinputtypegroup);
        consultantcontainsratio.setToggleGroup(consultantsearchmethodgroup);
        consultantexactratio.setToggleGroup(consultantsearchmethodgroup);
        customertable.setItems(customerObservableList);
        customeridcolumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        customernamecolumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        consultanttable.setItems(userObservableList);
        consultantidcolumn.setCellValueFactory(new PropertyValueFactory<>("userId"));
        consultantnamecolumn.setCellValueFactory(new PropertyValueFactory<>("userName"));
        loadpane(panetoLoad);
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

    public void consultantcancelbuttonpressed(ActionEvent actionEvent) {
        Stage stage = (Stage) consultantcancelbutton.getScene().getWindow();
        stage.close();
    }

    public void userselectedconsultantbuttonpressed(ActionEvent actionEvent){
        if (consultanttable.getSelectionModel().getSelectedItem() != null){
            onConsultantComplete.accept(consultanttable.getSelectionModel().getSelectedItem());
            Stage stage = (Stage) userselectedconsultantbutton.getScene().getWindow();
            stage.close();
        }
    }

    public void customersearchbuttonpressed(ActionEvent actionEvent) {
        //write code to filter table

        if (customeridratio.isSelected()){ //if searching via customerID
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
        else if (customernameratio.isSelected()){ //if searching via customer name
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

    public void customercancelbuttonpressed(ActionEvent actionEvent) {
        Stage stage = (Stage) customercancelbutton.getScene().getWindow();
        stage.close();
    }

    public void useselectedcustomerbuttonpressed(ActionEvent actionEvent) {
            if (customertable.getSelectionModel().getSelectedItem() != null){
                onCustomerComplete.accept(customertable.getSelectionModel().getSelectedItem());
                Stage stage = (Stage) userselectedconsultantbutton.getScene().getWindow();
                stage.close();
            }
    }

    public void loadpane(int i){//1 = Customer, 2 = Consultant/User
        switch (i) {
            case 1: {
                FindCustomerPane.setDisable(false);
                FindCustomerPane.setVisible(true);
                FindConsultantPane.setVisible(false);
                refreshCustomerTable();
                break;
            }
            case 2: {
                FindConsultantPane.setDisable(false);
                FindConsultantPane.setVisible(true);
                FindCustomerPane.setVisible(false);
                refreshConsultantTable();
                break;
            }

            }
        }

    private void refreshConsultantTable() {
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
            this.consultanttable.setItems(this.userObservableList);

            this.userbuff.setAll(userObservableList);
        }
    }

    private void refreshCustomerTable() {
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
        this.customertable.setItems(this.customerObservableList);
        this.custbuff.setAll(customerObservableList);
    }

    public void initconsultantdata(Consumer<User> userConsumer){
        this.onConsultantComplete = userConsumer;
    }
    public void initcustomerdata(Consumer<Customer> customerConsumer){
        this.onCustomerComplete = customerConsumer;
    }

}

