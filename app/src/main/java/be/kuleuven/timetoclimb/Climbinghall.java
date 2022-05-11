package be.kuleuven.timetoclimb;

import android.graphics.Bitmap;

public class Climbinghall {
    private int id;
    private String hall_name;
    private String address;
    private Bitmap image;

    public Climbinghall(int id, String hall_name, String address, Bitmap image) {
        this.id = id;
        this.hall_name = hall_name;
        this.address = address;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHall_name() {
        return hall_name;
    }

    public void setHall_name(String hall_name) {
        this.hall_name = hall_name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }
}
