package models;

import com.avaje.ebean.Model;
import org.mindrot.jbcrypt.BCrypt;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

/**
 * Created by lubuntu on 8/20/16.
 */
@Entity
public class User extends Model {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;

    public String email;

    public String password;

    @OneToMany(mappedBy = "sender")
    public List<ConnectionRequest> connectionRequestsSent;

    @OneToMany(mappedBy = "receiver")
    public List<ConnectionRequest> connectionRequestsReceived;

    @OneToOne
    public Profile profile;

    @ManyToMany
    @JoinTable(name = "user_connecions",
            joinColumns = {
                    @JoinColumn(name = "user_id") /*forward reference */
            },
            inverseJoinColumns =     /*backward reference */
                    {
                            @JoinColumn(name = "connection_id")
                    }

    )
    public Set<User> connections;  /*set coz unique connections s wats reqd */

    public static Finder<Long, User> find = new Finder<Long, User>(User.class);/* the Id we r searching for is of Long type */

    public static User authenticate(String email, String password) {
        User user = User.find.where().eq("email", email).findUnique();
        if (user != null && BCrypt.checkpw(password, user.password)) /* password(plain text at client) is compared to user.password(encrypted at server DB) */ {
            return user;
        }
        return null;
    }

    public User(String email, String password) {
        this.email = email;
        this.password = BCrypt.hashpw(password, BCrypt.gensalt());
    }
}