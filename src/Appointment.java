import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.function.Function;

public class Appointment {

    private String title, description,  location, contact, type, url, createdBy, lastUpdateBy, id, customerId, userId, customerName, consultantName;
    private LocalDateTime start, end, createDate, lastUpdate;

    public Appointment() {

    }

    public Appointment(String title, String description, String location, String contact, String type, String url, String createdBy, String lastUpdateBy, String id, String customerId, String userId, String customerName, String consultantName, LocalDateTime start, LocalDateTime end, LocalDateTime createDate, LocalDateTime lastUpdate) {
        this.title = title;
        this.description = description;
        this.location = location;
        this.contact = contact;
        this.type = type;
        this.url = url;
        this.createdBy = createdBy;
        this.lastUpdateBy = lastUpdateBy;
        this.id = id;
        this.customerId = customerId;
        this.userId = userId;
        this.customerName = customerName;
        this.consultantName = consultantName;
        this.start = start;
        this.end = end;
        this.createDate = createDate;
        this.lastUpdate = lastUpdate;
    }

    public Appointment(ResultSet r){
        try {
            while (r.next()) {
                this.title = r.getObject(4).toString();
                this.description = r.getObject(5).toString();
                this.location = r.getObject(6).toString();
                this.contact = r.getObject(7).toString();
                this.type = r.getObject(8).toString();
                this.url = r.getObject(9).toString();
                this.createdBy = r.getObject(13).toString();
                this.lastUpdateBy = r.getObject(15).toString();
                this.id = r.getObject(1).toString();
                this.customerId = r.getObject(2).toString();
                this.userId = r.getObject(3).toString();
                this.start = convertSQLUTCStrtoLocalTime.apply(r.getObject(10).toString());
                this.end = convertSQLUTCStrtoLocalTime.apply(r.getObject(11).toString());
                this.createDate = convertSQLUTCStrtoLocalTime.apply(r.getObject(12).toString());
                this.lastUpdate = convertSQLUTCStrtoLocalTime.apply(r.getObject(14).toString());
                this.customerName = getCustomerStringfromID.apply(this.id);
                this.consultantName = getConsultantStringfromID.apply(this.id);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getLastUpdateBy() {
        return lastUpdateBy;
    }

    public void setLastUpdateBy(String lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }



    Function<String, String> getConsultantStringfromID = (String i1) -> {
        String toReturn = "";
        ResultSet r = Main.getDBHelper().runQuery.apply("SELECT u.userName from appointment INNER JOIN user u on appointment.userId = u.userId WHERE appointmentId = '" + i1 + "';");
        try {
            while (r.next()) {
                toReturn = r.getObject(1).toString();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return toReturn;
    };

    Function<String, String> getCustomerStringfromID = (String i1) -> {
        String toReturn = "";
        ResultSet r = Main.getDBHelper().runQuery.apply("SELECT c.customerName from appointment INNER JOIN customer c on appointment.customerId = c.customerId WHERE appointmentId = '" + i1 + "';");
        try {
            while (r.next()) {
                toReturn = r.getObject(1).toString();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return toReturn;
    };


    Function<String, LocalDateTime> convertSQLUTCStrtoLocalTime = (String s1) -> {
        s1 = s1.substring(0,19);
        LocalDateTime ldt = LocalDateTime.parse(s1, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        LocalDateTime buffldt = ldt;
        ZonedDateTime zdt = ldt.atZone(ZoneId.of("+00:00"));
        ZonedDateTime buffzdt = buffldt.atZone(ZoneId.systemDefault());
        ZoneOffset lzo = buffzdt.getOffset();
        LocalDateTime localTime = zdt.plus(lzo.getTotalSeconds(), ChronoUnit.SECONDS).toLocalDateTime();
        return localTime;
    };

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName() {
        this.customerName = getCustomerStringfromID.apply(this.customerId);
    }

    public String getConsultantName() {
        return consultantName;
    }

    public void setConsultantName() {
        this.consultantName = getConsultantStringfromID.apply(this.userId);
    }
}
