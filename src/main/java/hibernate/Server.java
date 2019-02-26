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
    @Column(name = "SERVER_ID", length = 50, unique = true, nullable = false)
    private Long serverID;

    @Column(name = "SERVER_NAME", length = 50, unique = true, nullable = false)
    private String serverName;

    @Column(name = "SERVERHLSTATS_LINK", nullable = false)
    private String serverHlstatsLink;

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

    public String getServerHlstatsLink() {
        return serverHlstatsLink;
    }

    public void setServerHlstatsLink(String serverHlstatsLink) {
        this.serverHlstatsLink = serverHlstatsLink;
    }
}
