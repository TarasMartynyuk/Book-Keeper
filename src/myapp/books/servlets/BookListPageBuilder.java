package myapp.books.servlets;

import http.server.Constants;
import myapp.books.Book;
import myapp.servlets.WebRootPageReader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class BookListPageBuilder {

    static final String HEADER_FILENAME = "books/header.html";
    static final String FOOTER_FILENAME = "books/footer.html";

    public String buildBookListPage(Iterable<Book> books) throws IOException {
        var builder = new StringBuilder();

        builder.append(WebRootPageReader.getPage(HEADER_FILENAME));

        for(var book : books) {
            builder.append(buildBookListParagraph(book));
            builder.append("<br><br>");
        }

        builder.append(WebRootPageReader.getPage(FOOTER_FILENAME));

        return builder.toString();
    }

    private String buildBookListParagraph(Book book) {

        return "<p>" + "name:       " + book.getName() + "<br>\n" +
                "author:            " + book.getAuthor() + "<br>\n" +
                "language:          " + book.getLanguage() + "<br>\n" +
                "year:              " + book.getYear() + "<br>";
    }
}
