package hibernate;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "SERVER",
uniqueConstraints =  {@UniqueConstraint(columnNames = {"SERVER_ID"})})
public class Servers {

    private Integer serverId;
    private String serverName;

    private Servers server;
    private Set<Admins> admins = new HashSet<Admins>(0);

    public Servers(Integer serverId, String serverName) {
        this.serverId = serverId;
        this.serverName = serverName;
    }
    @Id
    @Column(name = "SERVER_ID")
    public Integer getServerID() {
        return serverId;
    }
    public void setServerID(Integer serverId) {
        this.serverId = serverId;
    }

    @Column(name = "SERVER_NAME", length = 50, nullable = false)
    public String getServerName() {
    return serverName;
    }
    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "servers")
    public Set<Admins> getAdmins() {
        return admins;
    }
}
