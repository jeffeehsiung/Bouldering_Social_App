package be.kuleuven.timetoclimb.route;

import java.io.Serializable;

public class Route implements Serializable {

    private int routeNO;
    private float grade;
    private String hallName, author, description, routePicture;

    public Route(String hallName, int routeNo, float grade, String author, String description, String routePicture){
        this.hallName = hallName;
        this.routeNO = routeNo;
        this.grade = grade;
        this.author = author;
        this.description = description;
        this.routePicture = routePicture;
    }

    public String getHallName() {
        return hallName;
    }

    public int getRouteNO() {
        return routeNO;
    }

    public float getGrade() {
        return grade;
    }

    public String getAuthor() {
        return author;
    }

    public String getDescription() {
        return description;
    }

    public String getRoutePicture() {
        return routePicture;
    }
}
