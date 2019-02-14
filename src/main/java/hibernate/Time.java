package hibernate;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
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
    public Time(Integer adminTimeID, Date adminTimeDate, Date adminTimeHours, LocalDateTime adminTimeModify) {
        this.adminTimeID = adminTimeID;
        this.adminTimeDate = adminTimeDate;
        this.adminTimeHours = adminTimeHours;
        this.adminTimeModify = adminTimeModify;
    }

    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    @Column(name = "TIME_ID", length = 50, nullable = false)
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
    public void setAdminTimeModify(LocalDateTime adminTimeModify) { this.adminTimeModify = adminTimeModify; }
    }
