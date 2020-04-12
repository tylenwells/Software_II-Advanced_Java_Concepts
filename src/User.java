import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.function.Function;

public class User {
    private Integer userId;
    private String userName;
    private String password;
    private Integer active;
    private LocalDateTime createDate;
    private String createdBy;
    private LocalDateTime lastUpdate;
    private String lastUpdateBy;
    private String activeString;

    public User(Integer userId, String userName, String password, Integer active, LocalDateTime createDate, String createdBy, LocalDateTime lastUpdate, String lastUpdateBy) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.active = active;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdateBy = lastUpdateBy;
        if(active == 1) {
            this.activeString = "True";
        }
        else if (active == 0){
            this.activeString = "False";
        }
    }

    public User(ResultSet r){
        try{
            while(r.next()){
                this.userId = r.getInt(1);
                this.userName = r.getObject(2).toString();
                this.password = "haha nice try";
                this.active = r.getInt(4);
                this.createDate = convertSQLUTCStrtoLocalTime.apply(r.getObject(5).toString());
                this.createdBy = r.getObject(6).toString();
                this.lastUpdate = convertSQLUTCStrtoLocalTime.apply(r.getObject(7).toString());
                this.lastUpdateBy = r.getObject(8).toString();
                if(active == 1) {
                    this.activeString = "True";
                }
                else if (active == 0){
                    this.activeString = "False";
                }
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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


    public String getActiveString() {
        return activeString;
    }

    public void setActiveString() {
        if(active == 1) {
        this.activeString = "True";
    }
    else if (active == 0){
        this.activeString = "False";
    }
    }
}
