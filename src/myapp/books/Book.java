package myapp.books;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class Book {

    private String name;
    private String author;
    private String language;
    private int year;

    public String getName() {
        return name;
    }

    public String getAuthor() {
        return author;
    }

    public String getLanguage() {
        return language;
    }

    public int getYear() {
        return year;
    }

    public Book(String name, String author, String language, int year) {
        this.name = name;
        this.author = author;
        this.language = language;
        this.year = year;
    }

    public Book() {
    }

    public BasicDBObject toDBObject() {
        var document = new BasicDBObject();

        document.put("name", name);
        document.put("year", year);
        document.put("language", language);
        document.put("author", author);

        return document;
    }

    public static Book fromDBObject(DBObject document) {
        var b = new Book();

        b.name = (String) document.get("name");
        b.year = (Integer) document.get("year");
        b.language = (String) document.get("language");
        b.author = (String) document.get("author");

        return b;
    }
}