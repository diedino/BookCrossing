package krasnov.bookcrossing;

public class Book {
    Author author;
    String title, isbn, genge, description;
    public Book(Author author, String title, String isbn, String genge, String description) {
        this.author = author;
        this.title = title;
        this.isbn = isbn;
        this.genge = genge;
        this.description = description;
    }
    public String toString(){
        return "qwe";
    }
}
