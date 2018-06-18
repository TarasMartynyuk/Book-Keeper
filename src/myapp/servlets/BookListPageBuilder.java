package myapp.servlets;

import http.server.Constants;
import myapp.books.Book;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class BookListPageBuilder {

    static final String HEADER_FILENAME = "books/header.html";
    static final String FOOTER_FILENAME = "books/footer.html";

    public String buildBookListPage(Iterable<Book> books) throws IOException {
        var builder = new StringBuilder();

        builder.append(readFileAsUtf8(Paths.get(Constants.WEB_ROOT, HEADER_FILENAME)));

        for(var book : books) {
            builder.append(buildBookListParagraph(book));
        }

        builder.append(readFileAsUtf8(Paths.get(Constants.WEB_ROOT, FOOTER_FILENAME)));

//        return builder.toString();
        return "Hello";
    }

    private String buildBookListParagraph(Book book) {
        return null;
    }

    private String readFileAsUtf8(Path path) throws IOException {
        var contents = Files.readAllBytes(path);
        return new String(contents, "UTF-8");
    }
}
