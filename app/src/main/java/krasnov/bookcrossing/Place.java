package krasnov.bookcrossing;

import java.util.ArrayList;
import java.util.List;

public class Place {

    private static long id;
    private String name, address;
    private double latitude, longitude;
    private List<Book> books= new ArrayList<Book>();

    public Place(double latitude, double longitude, String name, String address) {
        this.latitude=latitude;
        this.longitude=longitude;
        this.name=name;
        this.address=address;
        id++;
    }

    public static long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public List<Book> getBooks() {
        return books;
    }
}
