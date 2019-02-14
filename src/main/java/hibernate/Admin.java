package hibernate;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "ADMIN",
uniqueConstraints =  {@UniqueConstraint(columnNames = {"ADMIN_ID"})})
public class Admin {

    private Integer adminID;
    private String adminName;
    private String adminLink;
    private String adminColor;

    private Server server;
    private Set<Admin> admins = new HashSet<Admin>(0);

    public Admin(Integer adminID, String adminServer, String adminName, String adminLink, String adminColor) {
        this.adminID = adminID;
        this.adminName = adminName;
        this.adminLink = adminLink;
        this.adminColor = getAdminColor();
    }
    @Id
    @Column(name="ADMIN_ID", unique = true, nullable = false)
    public Integer getAdminID() { return adminID; }
    public void setAdminID(Integer adminID) {
        this.adminID = adminID;
    }

    @Column(name="ADMIN_NAME", unique = true, nullable = false)
    public String getAdminName() { return adminName; }
    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    @Column(name="ADMIN_LINK", unique = true, nullable = false)
    public String getAdminLink() {
        return adminLink;
    }
    public void setAdminLink(String adminLink) {
        this.adminLink = adminLink;
    }

    @Column(name="ADMIN_COLOR", unique = true, nullable = false)
    public String getAdminColor() {
        return adminColor;
    }
    public void setAdminColor(String adminColor) {
        this.adminColor = adminColor;
    }

    public void setServer(Server server) {
        this.server = server;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "adminID")
    public Set<Admin> getAdmins() {
        return admins;
    }
    public void setAdmins(Set<Admin> admins) {
        this.admins = admins;
    }
    }
