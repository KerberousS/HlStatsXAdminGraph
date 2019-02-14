package hibernate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "SERVER",
uniqueConstraints =  {@UniqueConstraint(columnNames = {"SERVER_ID"})})
public class Server implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "SERVER_ID", length = 50, nullable = false)
    private Long serverID;

    @Column(name = "SERVER_NAME", length = 50, nullable = false)
    private String serverName;

//    private Set<Admin> admins = new HashSet<Admin>(0);
    @ElementCollection(targetClass=Server.class)
    private Set<Server> servers = new HashSet<Server>(0);

    public long getServerID() {
        return serverID;
    }
    public void setServerID(long userID) {
        this.serverID = serverID;
    }

    public String getServerName() {
    return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "servers")
//    public Set<Admin> getAdmins() {
//        return admins;
//    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "serverName")
    public Set<Server> getServers() {
        return servers;
    }

    public void setServers(Set<Server> servers) {
        this.servers = servers;
    }
}
