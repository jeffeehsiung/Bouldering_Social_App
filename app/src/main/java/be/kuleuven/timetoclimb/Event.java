package be.kuleuven.timetoclimb;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Event {
    private String title;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private ArrayList<User> invitedPeople;
    private ArrayList<User> attendees;

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

    public ArrayList<User> getInvitedPeople() {
        return invitedPeople;
    }

    public void setInvitedPeople(ArrayList<User> invitedPeople) {
        this.invitedPeople = invitedPeople;
    }

    public ArrayList<User> getAttendees() {
        return attendees;
    }

    public void setAttendees(ArrayList<User> attendees) {
        this.attendees = attendees;
    }
}
