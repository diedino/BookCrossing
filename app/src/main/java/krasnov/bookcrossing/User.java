package krasnov.bookcrossing;

import java.util.ArrayList;
import java.util.List;

public class User {

    private static long id=0;
    private String name, email, password;
    private List<Book> books= new ArrayList<Book>();
    private long taken=0, given=0;

    public User(String name, String email, String password){
        this.name=name;
        this.email = email;
        this.password=password;
        id++;
    }

    public static long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public List<Book> getBooks() {
        return books;
    }
    public void addbook(Book book) {
        books.add(book);
    }
}
