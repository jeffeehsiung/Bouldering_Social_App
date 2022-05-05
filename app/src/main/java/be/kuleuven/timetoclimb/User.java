package be.kuleuven.timetoclimb;

import java.util.ArrayList;

public class User {
    private String username;
    private String emailAddress;
    private String password;
    private ArrayList<User> friends;

    public User(String username, String emailAddress) {
        this.username = username;
        this.emailAddress = emailAddress;
    }

    public String getUsername() {return username;}
    public String getEmailAddress() {return emailAddress;}
    public String getPassword() {return password;}
}
