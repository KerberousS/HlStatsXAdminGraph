package hibernate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "ADMIN",
uniqueConstraints =  {@UniqueConstraint(columnNames = {"ADMIN_ID"})})
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ADMIN_ID", length = 50, nullable = false)
    private Long adminID;

    @Column(name="ADMIN_NAME", unique = true, nullable = false)
    private String adminName;

    @Column(name="ADMIN_LINK", unique = true, nullable = false)
    private String adminLink;

    @Column(name="ADMIN_COLOR", unique = true, nullable = false)
    private String adminColor;

    @Column(name="ADMIN_SERVER", nullable = false)
    private String adminServer;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "adminID")
    private Set<Admin> admins = new HashSet<Admin>(0);

    @Transient
    private Boolean adminSelect;

    @Transient
    private List<LocalDateTime> adminTimeList;

    public Admin() {
    }

    public Admin(String adminName, String adminLink, String adminColor, String adminServer) {
        this.adminName = adminName;
        this.adminLink = adminLink;
        this.adminColor = adminColor;
        this.adminServer = adminServer;
        this.adminSelect = adminSelect;
        this.adminTimeList = adminTimeList;
    }

    public Long getAdminID() { return adminID; }
    public void setAdminID(Long adminID) {
        this.adminID = adminID;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }


    public String getAdminLink() {
        return adminLink;
    }

    public void setAdminLink(String adminLink) {
        this.adminLink = adminLink;
    }

    public String getAdminColor() {
        return adminColor;
    }

    public void setAdminColor(String adminColor) {
        this.adminColor = adminColor;
    }

    public String getAdminServer() { return adminServer; }
    public void setAdminServer(String adminServer) {
        this.adminServer = adminServer;
    }


    public Set<Admin> getAdmins() {
        return admins;
    }
    public void setAdmins(Set<Admin> admins) {
        this.admins = admins;
    }

    public Boolean isSelected() {
        return adminSelect;
    }

    public void setSelected(Boolean adminSelect) {
        this.adminSelect = adminSelect;
    }

    public List<LocalDateTime> getAdminTimeList() {
        return adminTimeList;
    }

    public void setAdminTimeList(List<LocalDateTime> adminTimeList) {
        this.adminTimeList = adminTimeList;
    }
}
