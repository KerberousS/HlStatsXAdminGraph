package hibernate;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "Time",
uniqueConstraints =  {@UniqueConstraint(columnNames = {"TIME_ID"})})
public class Time {
    public static final char IN = 'I';
    public static final char OUT = 'O';

    private Integer adminTimeID;
    private Date adminTimeDate;
    private Date adminTimeHours;
    private LocalDateTime adminTimeModify;

    // 'I' or 'O'
    private char inOut;

    @Id
    @Column(name="ADMINTIME_ID")
    public Integer getAdminTimeID() { return adminTimeID; }
    public void setAdminTimeID(Integer adminID) {
        this.adminTimeID = adminTimeID;
    }

    @Column(name="ADMINTIME_DATE", length = 36)
    public Date getAdminTimeDateID() {
        return adminTimeDate;
    }
    public void setAdminTimeDateID(Date adminTimeDate) {
        this.adminTimeDate = adminTimeDate;
    }

    @Column(name = "ADMINTIME_HOURS", length = 36)
    public Date getAdminTimeHours() {
        return adminTimeHours;
    }
    public void setAdminTimeHours(Date adminTimeHours) {
        this.adminTimeHours = adminTimeHours;
    }

    @Column(name="ADMINTIME_MODIFIED")
    public LocalDateTime getAdminTimeModify() { return adminTimeModify; }
    public void setAdminTimeModify() { this.adminTimeModify = adminTimeModify; }
    }
