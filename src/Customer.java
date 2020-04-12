import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.function.Function;

public class Customer {

    private Integer customerId;
    private String customerName;
    private Integer addressId;
    private Integer active;
    private LocalDateTime createDate;
    private String createdBy;
    private LocalDateTime lastUpdate;
    private String lastUpdateBy;
    private String activeString;
    private String addressString;

    public Customer(Integer customerId, String customerName, Integer addressId, Integer active, LocalDateTime createDate, String createdBy, LocalDateTime lastUpdate, String lastUpdateBy, String activeString, String addressString) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.addressId = addressId;
        this.active = active;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdateBy = lastUpdateBy;
        this.activeString = activeString;
        this.addressString = addressString;
    }

    public Customer(ResultSet r) {
        try {
            while (r.next()) {
                this.customerId = r.getInt(1);
                this.customerName = r.getObject(2).toString();
                this.addressId = r.getInt(3);
                this.active = r.getInt(4);
                this.createDate = convertSQLUTCStrtoLocalTime.apply(r.getObject(5).toString());
                this.createdBy = r.getObject(6).toString();
                this.lastUpdate = convertSQLUTCStrtoLocalTime.apply(r.getObject(7).toString());
                this.lastUpdateBy = r.getObject(8).toString();
                if (this.active == 1) {
                    this.activeString = "True";
                } else if (this.active == 0) {
                    this.activeString = "False";
                }
                this.addressString = getAddressStringfromID.apply(this.addressId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Integer getAddressId() {
        return addressId;
    }

    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }

    public Integer getActive() {
        return active;
    }

    public void setActive(Integer active) {
        this.active = active;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getLastUpdateBy() {
        return lastUpdateBy;
    }

    public void setLastUpdateBy(String lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
    }

    public String getActiveString() {
        return activeString;
    }

    public void setActivestring() {
        if (this.active == 1) {
            this.activeString = "True";
        } else if (this.active == 0) {
            this.activeString = "False";
        }
    }

    public String getAddressString() {
        return addressString;
    }

    public void setAddressstring() {
        this.addressString = getAddressStringfromID.apply(this.addressId);
    }



    private ResultSet getqueryResult(ResultSet r) {
        return r;
    }

    Function<String, LocalDateTime> convertSQLUTCStrtoLocalTime = (String s1) -> {
        s1 = s1.substring(0, 19);
        LocalDateTime ldt = LocalDateTime.parse(s1, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        ZonedDateTime zdt = ldt.atZone(ZoneId.of("+00:00"));
        ZonedDateTime buffzdt = ldt.atZone(ZoneId.systemDefault());
        ZoneOffset lzo = buffzdt.getOffset();
        return zdt.plus(lzo.getTotalSeconds(), ChronoUnit.SECONDS).toLocalDateTime();
    };

    Function<Integer, String> getAddressStringfromID = (Integer i1) -> {
        String toReturn = "";
        ResultSet r = Main.getDBHelper().runQuery.apply("SELECT addressid, address, address2, ci.city, postalCode, co.country from address INNER JOIN city ci, country co WHERE addressId = '" + Integer.toString(i1) + "';");
        try {
            while (r.next()) {
                toReturn = r.getObject(2).toString() + " " + r.getObject(3).toString() + " " + r.getObject(4) + " " + r.getObject(5);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return toReturn;
    };


}