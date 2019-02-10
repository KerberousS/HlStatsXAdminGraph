package hibernate;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Time",
uniqueConstraints =  {@UniqueConstraint(columnNames = {"TIME_ID"})})
public class Times {
    public static final char IN = 'I';
    public static final char OUT = 'O';

    public String adminTimeID;

    private Date dateTime;

    private Admins admin;

    // 'I' or 'O'
    private char inOut;

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "AdminTime_ID", length = 36)
    public String getAdminTimeID() {
        return adminTimeID;
    }
    public void setAdminTimeID(String adminTimeID) {
        this.adminTimeID = adminTimeID;
    }

    @Id
    @Column(name="ADMIN_ID")
    public String getAdminID() { return adminTimeID; }
    public void setAdminID(String adminID) {
        this.adminTimeID = adminTimeID;
    }

    }
