package krasnov.bookcrossing;

public class Book {
	private static int id = 0;
    private Author author;
    private String title, isbn, genge, description;
    private int iconResource;

    public Book(Author author, String title, String isbn, String genge, String description, int iconResource) {
        this.author = author;
        this.title = title;
        this.isbn = isbn;
        this.genge = genge;
        this.description = description;
        this.iconResource = iconResource;
    }

    public Book(Author author, String title, String isbn, String genge, String description){
    	this.author = author;
        this.title = title;
        this.isbn = isbn;
        this.genge = genge;
        this.description = description;
    }
    public String toString(){
        return "qwe";
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getIconResource() {
        return iconResource;
    }

    public String getAuthorToString() {
        return author.toString();
    }
    public int getId(){
    	return id;
    }
}
