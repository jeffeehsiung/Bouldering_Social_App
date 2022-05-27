package be.kuleuven.timetoclimb;

import java.io.Serializable;
import java.util.Date;

public class Comment implements Serializable {

    private int discussion_id, climbing_hall_id;
    String poster_username,description, picture;
    Date created_datetime;

    public Comment(int discussion_id, String poster_username, int climbing_hall_id, String description, Date created_datetime,String b64String){
        this.discussion_id = discussion_id;
        this.poster_username = poster_username;
        this.climbing_hall_id = climbing_hall_id;
        this.description = description;
        this.created_datetime = created_datetime;
        this.picture = b64String;
    }

    public int getDiscussion_id() {
        return discussion_id;
    }

    public int getClimbing_hall_id() {
        return climbing_hall_id;
    }

    public String getPoster_username() {
        return poster_username;
    }

    public String getDescription() {
        return description;
    }

    public String getPicture() {
        return picture;
    }

    public Date getCreated_datetime() {
        return created_datetime;
    }
}
