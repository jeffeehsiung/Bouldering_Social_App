package be.kuleuven.timetoclimb;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Event {
    private String organiser;
    private int climbingHallID;
    private String description;
    private String title;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public Event(String organiser, int climbingHallID, String description, String title, LocalDateTime startTime, LocalDateTime endTime) {
        this.organiser = organiser;
        this.climbingHallID = climbingHallID;
        this.description = description;
        this.title = title;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    /*
        Getters and setters
     */
    public String getOrganiser() {
        return organiser;
    }

    public void setOrganiser(String organiser) {
        this.organiser = organiser;
    }

    public int getClimbingHallID() {
        return climbingHallID;
    }

    public void setClimbingHallID(int climbingHallID) {
        this.climbingHallID = climbingHallID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }


}
