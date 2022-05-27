package be.kuleuven.timetoclimb.EventViewing;

import java.util.ArrayList;

import be.kuleuven.timetoclimb.Climbinghall;
import be.kuleuven.timetoclimb.Event;

public interface EventViewer {

    /*
    Populate the eventlist from the database
     */
    void populateEventList();

    /*
    Add list of climbinghalls where the events are held
     */
    void addClimbingHalls(ArrayList<Event> events);

    /*
    set Recyclerview adapter
     */
    void setAdapter();

    /*
    Pass event and climbinghall to the eventviewer
     */
    void passEventAndClimbinghall(Event event, Climbinghall climbinghall);

}
