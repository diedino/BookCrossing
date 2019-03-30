package krasnov.bookcrossing;

import java.util.ArrayList;
import java.util.List;

public class Place {
    private int id;
    private String name, address;
    private double latitude, longitude;
    private List<Book> shelf = new ArrayList<>();

    public Place(double latitude, double longitude, String name, String address) {
        this.latitude=latitude;
        this.longitude=longitude;
        this.name=name;
        this.address=address;
        id++;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getName() {
        return name;
    }
}
