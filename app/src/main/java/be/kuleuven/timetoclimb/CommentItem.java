package be.kuleuven.timetoclimb;

import java.io.Serializable;
import java.util.Date;

public class CommentItem implements Serializable {

    private int discussion_id;
    String poster_username,climbing_hall_Name,description, picture, created_datetime;

    public CommentItem(int discussion_id, String poster_username, String climbing_hall_Name, String description, String created_datetime, String b64String){
        this.discussion_id = discussion_id;
        this.poster_username = poster_username;
        this.climbing_hall_Name = climbing_hall_Name;
        this.description = description;
        this.created_datetime = created_datetime;
        this.picture = b64String;
    }

    public int getDiscussion_id() {
        return discussion_id;
    }

    public String getClimbing_hall_id() {
        return climbing_hall_Name;
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

    public String getCreated_datetime() {
        return created_datetime;
    }
}