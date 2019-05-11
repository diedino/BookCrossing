package krasnov.bookcrossing;

public class Book {

    private static int id = 0;
    private String author, title, isbn, description, code;
    private int iconResource;

    private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public Book(String author, String title, String isbn, String description, int iconResource) {
        this.author = author;
        this.title = title;
        this.isbn = isbn;
        this.description = description;
        this.iconResource = iconResource;
        code = randomCode();
        id++;
    }

    public Book(String author, String title, String isbn, String description) {
        this.author = author;
        this.title = title;
        this.isbn = isbn;
        this.description = description;
        code = randomCode();
        id++;
    }

    private String randomCode() {
        StringBuilder builder = new StringBuilder();
        int count = 6;
        while (count-- != 0) {
            int character = (int) (Math.random() * ALPHA_NUMERIC_STRING.length());
            builder.append(ALPHA_NUMERIC_STRING.charAt(character));
        }
        return builder.toString();
    }

    public String toString() {
        return "qwe";
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}