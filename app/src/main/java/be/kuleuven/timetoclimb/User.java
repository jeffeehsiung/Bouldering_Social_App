package be.kuleuven.timetoclimb;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {
    private String username;
    private String emailAddress;
    private String password;
    private ArrayList<User> friends;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public void createEvent(Event e) {

    }
    public String getUsername() {return username;}
    public String getEmailAddress() {return emailAddress;}
    public String getPassword() {return password;}
}
