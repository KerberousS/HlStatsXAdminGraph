package hibernate;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "SERVER",
uniqueConstraints =  {@UniqueConstraint(columnNames = {"SERVER_ID"})})
public class Server {

    private Integer serverId;
    private String serverName;

    private Server server;
    private Set<Admin> admins = new HashSet<Admin>(0);

    public Server(Integer serverId, String serverName) {
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
    public Set<Admin> getAdmins() {
        return admins;
    }
}
