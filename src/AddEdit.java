import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.TemporalField;
import java.util.ResourceBundle;

@SuppressWarnings("ALL")
public class AddEdit implements Initializable {

    @FXML
    public Pane AddEditCustomerPane;
    @FXML
    public Label addcustomerlabel;
    @FXML
    public Label modifycustomerlabel;
    @FXML
    public TextField customernamefield;
    @FXML
    public TextField customeraddress1field;
    @FXML
    public TextField customeraddress2field;
    @FXML
    public TextField customercityfield;
    @FXML
    public TextField customercountryfield;
    @FXML
    public TextField customerpostalcodefield;
    @FXML
    public TextField customerphonenumberfield;
    @FXML
    public Button customercancelbutton;
    @FXML
    public Button customersavebutton;
    @FXML
    public Pane AddEditAppointmentPane;
    @FXML
    public Label addapptlabel;
    @FXML
    public Label modifyapptlabel;
    @FXML
    public TextField appttitlefield;
    @FXML
    public TextField apptcustomerfield;
    @FXML
    public TextField apptconsultantfield;
    @FXML
    public Button apptcancelbutton;
    @FXML
    public Button apptsavebutton;
    @FXML
    public TextArea apptdescriptiontextbox;
    @FXML
    public TextField apptlocationfield;
    @FXML
    public TextField apptcontactfield;
    @FXML
    public TextField appturlfield;
    @FXML
    public DatePicker apptstartdatepicker;
    @FXML
    public DatePicker apptenddatepicker;
    @FXML
    public TextField apptstarttimefield;
    @FXML
    public TextField apptendtimefield;
    @FXML
    public Button apptcustomerfindbutton;
    @FXML
    public Button apptconsultantfindbutton;
    @FXML
    public TextField appttypefield;

    //variables, etc.

    boolean create = false;
    String username = "";

    String customerid = "";
    String customerAddressId = "";
    SimpleStringProperty customername = new SimpleStringProperty();
    SimpleStringProperty customeraddress1 = new SimpleStringProperty();
    SimpleStringProperty customeraddress2 = new SimpleStringProperty();
    SimpleStringProperty customercity = new SimpleStringProperty();
    SimpleStringProperty customercountry = new SimpleStringProperty();
    SimpleStringProperty customerpostalcode = new SimpleStringProperty();
    SimpleStringProperty customerphonenumber = new SimpleStringProperty();

    String apptid = "";
    SimpleStringProperty appttitle = new SimpleStringProperty();
    SimpleStringProperty apptcustomername = new SimpleStringProperty();
    SimpleStringProperty apptconsultantname = new SimpleStringProperty();
    SimpleStringProperty apptdescription = new SimpleStringProperty();
    SimpleStringProperty appttype = new SimpleStringProperty();
    SimpleStringProperty apptlocation = new SimpleStringProperty();
    SimpleStringProperty apptcontact = new SimpleStringProperty();
    SimpleStringProperty appturl = new SimpleStringProperty();
    SimpleStringProperty apptstarttime = new SimpleStringProperty();
    SimpleStringProperty apptendtime = new SimpleStringProperty();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        customernamefield.textProperty().bindBidirectional(customername);
        customeraddress1field.textProperty().bindBidirectional(customeraddress1);
        customeraddress2field.textProperty().bindBidirectional(customeraddress2);
        customercityfield.textProperty().bindBidirectional(customercity);
        customercountryfield.textProperty().bindBidirectional(customercountry);
        customerpostalcodefield.textProperty().bindBidirectional(customerpostalcode);
        customerphonenumberfield.textProperty().bindBidirectional(customerphonenumber);


        appttitlefield.textProperty().bindBidirectional(appttitle);
        apptcustomerfield.textProperty().bindBidirectional(apptcustomername);
        apptconsultantfield.textProperty().bindBidirectional(apptconsultantname);
        apptdescriptiontextbox.textProperty().bindBidirectional(apptdescription);
        appttypefield.textProperty().bindBidirectional(appttype);
        apptlocationfield.textProperty().bindBidirectional(apptlocation);
        apptcontactfield.textProperty().bindBidirectional(apptcontact);
        appturlfield.textProperty().bindBidirectional(appturl);
        apptstarttimefield.textProperty().bindBidirectional(apptstarttime);
        apptendtimefield.textProperty().bindBidirectional(apptendtime);

    }


    public void apptcustomerfindbuttonpressed(ActionEvent actionEvent) {
        //create consumer and open find page
    }

    public void apptconsultantfindbuttonpressed(ActionEvent actionEvent) {
        //create consumer and open find page
    }

    public void customersavebuttonpressed(ActionEvent actionEvent) {
        //if create == true, create a new db entry and refresh the lookup tables before closing the program
        String addressid = "";
        String cityId = "";
        String countryId = "";

       try {
           ResultSet r = Main.getDBHelper().runQuery.apply("SELECT addressId from address INNER JOIN city ci, country co where address = '" + customeraddress1.get() + "' AND address2  = '" + customeraddress2.get() + "' AND postalCode = '" + customerpostalcode.get() + "'  AND phone = '" + customerphonenumber.get() + "' and ci.city = '" + customercity.get() + "' and co.country = '" + customercountry.get() + "';");
           while (r.next()) {
               addressid = r.getObject(1).toString();
           }
           if (addressid.isEmpty()) {
               ResultSet r2 = Main.getDBHelper().runQuery.apply("SELECT * FROM city INNER JOIN country c WHERE city = '" + customercity.get() + "' AND c.country = '" + customercountry.get() + "';");
               while (r2.next()) {
                   cityId = r2.getObject(1).toString();
               }
               if (cityId.isEmpty()) {
                   ResultSet r3 = Main.getDBHelper().runQuery.apply("SELECT * from country WHERE country = '" + customercountry.get() + "';");
                   while (r3.next()) {
                       countryId = r3.getObject(1).toString();
                   }
                   if (countryId.isEmpty()) {
                       Main.getDBHelper().runDMS.apply("INSERT INTO country (country, createDate, createdBy, lastUpdateBy) values ('" + customercountry.get() + "', NOW(), '" + this.username + "', '" + this.username + "');");
                       ResultSet r4 = Main.getDBHelper().runQuery.apply("SELECT countryId from country where country = '" + customercountry.get() + "';");
                       while (r4.next()) {
                           countryId = r4.getObject(1).toString();
                       }
                   }

                   Main.getDBHelper().runDMS.apply("INSERT INTO city (city, countryId, createDate, createdBy, lastUpdateBy) VALUES ('" + customercity.get() + "', '" + countryId + "', now(), '" + username + "', '" + username + "');");
                   ResultSet r5 = Main.getDBHelper().runQuery.apply("SELECT cityId from city WHERE city = '" + customercity.get() + "' AND countryId = '" + countryId + "'");
                   while (r5.next()) {
                       cityId = r5.getObject(1).toString();
                   }
               }

               Main.getDBHelper().runDMS.apply("INSERT INTO address (address, address2, cityId, postalCode, phone, createDate, createdBy, lastUpdateBy) VALUES ('" + customeraddress1.get() + "', '" + customeraddress2.get() + "', '" + cityId + "', '" + customerpostalcode.get() + "', '" + customerphonenumber.get() + "', now(), '" + username + "', '" + username + "');");
               ResultSet r6 = Main.getDBHelper().runQuery.apply("SELECT addressId from address where address = '" + customeraddress1.get() + "' AND address2  = '" + customeraddress2.get() + "' AND postalCode = '" + customerpostalcode.get() + "'  AND phone = '" + customerphonenumber.get() + "' AND cityId = '" + cityId + "';");
               while (r6.next()) {
                   addressid = r6.getObject(1).toString();
               }
           }
           if (create) {
               Main.getDBHelper().runDMS.apply("INSERT INTO customer (customerName, addressId, active, createDate, createdBy, lastUpdateBy) VALUES ('" + customername.get() + "', '" + addressid + "', '1', now(), '" + username + "', '" + username + "');");
           } else {
               Main.getDBHelper().runDMS.apply("UPDATE customer SET customerName = '" + customername.get() + "', addressId = '" + addressid + "', lastUpdateBy = '" + username + "' WHERE customerId = '" + customerid + "';");
           }
       }
       catch (SQLException e) {
       e.printStackTrace();
       }

        Stage stage = (Stage) customersavebutton.getScene().getWindow();
        stage.close();
    }

    public void apptsavebuttonpressed(ActionEvent actionEvent) {
        //if create == true, create new db entry
    }

    public void apptcancelbuttonpressed(ActionEvent actionEvent) {
        Stage stage = (Stage) apptcancelbutton.getScene().getWindow();
        stage.close();
    }

    public void customercancelbuttonpressed(ActionEvent actionEvent) {
        Stage stage = (Stage) customercancelbutton.getScene().getWindow();
        stage.close();
    }



    //misc functions

    public void loadPane(int i) { //1 = Customer, 2 = Appt
        switch (i) {
            case 1: {
                AddEditCustomerPane.setDisable(false);
                AddEditCustomerPane.setVisible(true);
                AddEditAppointmentPane.setVisible(false);
                if (create){
                    addcustomerlabel.setVisible(true);
                    modifycustomerlabel.setVisible(false);
                }
                else {
                    addcustomerlabel.setVisible(false);
                    modifycustomerlabel.setVisible(true);
                }
                break;
            }
            case 2: {
                AddEditAppointmentPane.setDisable(false);
                AddEditAppointmentPane.setVisible(true);
                AddEditCustomerPane.setVisible(false);
                if (create){
                    addapptlabel.setVisible(true);
                    modifyapptlabel.setVisible(false);
                }
                else {
                    addapptlabel.setVisible(false);
                    modifyapptlabel.setVisible(false);
                }
                break;
            }

        }
    }

    public void initdata(Appointment a, boolean create, String username){
        this.username = username;
        this.create = create;
        this.apptid = a.getId();
        appttitle.set(a.getTitle());
        apptcustomername.set(a.getCustomerName());
        apptconsultantname.set(a.getConsultantName());
        apptdescription.set(a.getDescription());
        appttype.set(a.getType());
        apptlocation.set(a.getLocation());
        apptcontact.set(a.getContact());
        appturl.set(a.getUrl());
        apptstarttime.set(Integer.toString(a.getStart().getHour()) + Integer.toString(a.getStart().getMinute()));
        apptendtime.set(Integer.toString(a.getEnd().getHour()) + Integer.toString(a.getEnd().getMinute()));
    }

    public void initdata(Customer c, boolean create, String username){
        this.username = username;
        this.create = create;
        if (!create && c != null) {
            this.customerid = c.getCustomerId().toString();
            this.customerAddressId = c.getAddressId().toString();
            customername.set(c.getCustomerName());
            ResultSet r = Main.getDBHelper().runQuery.apply("SELECT addressid, address, address2, ci.city, postalCode, co.country, phone from address INNER JOIN city ci, country co WHERE addressId = '" + Integer.toString(c.getAddressId()) + "';");
            try {
                while (r.next()) {
                    customeraddress1.set(r.getObject(2).toString());
                    customeraddress2.set(r.getObject(3).toString());
                    customercity.set(r.getObject(4).toString());
                    customerpostalcode.set(r.getObject(5).toString());
                    customercountry.set(r.getObject(6).toString());
                    customerphonenumber.set(r.getObject(7).toString());
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
