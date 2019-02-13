package hibernate;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "SERVER",
uniqueConstraints =  {@UniqueConstraint(columnNames = {"SERVER_ID"})})
public class Server {

    private Long serverID;
    private String serverName;

    private Set<Admin> admins = new HashSet<Admin>(0);
    private Set<Server> servers = new HashSet<Server>(0);

    public Server(long serverID, String serverName) {
        this.serverID = serverID;
        this.serverName = serverName;
    }

    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    public long getServerID() {
        return serverID;
    }
    public void setServerID(long userID) {
        this.serverID = serverID;
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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "SERVER_NAME")
    public Set<Server> getServers() {
        return servers;
    }
}
