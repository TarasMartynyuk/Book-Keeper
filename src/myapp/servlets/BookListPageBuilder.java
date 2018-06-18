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
            builder.append("<br><br>");
        }

        builder.append(readFileAsUtf8(Paths.get(Constants.WEB_ROOT, FOOTER_FILENAME)));

        return builder.toString();
    }

    private String buildBookListParagraph(Book book) {

        return "<p>" + "name:       " + book.getName() + "<br>\n" +
                "author:            " + book.getAuthor() + "<br>\n" +
                "language:          " + book.getLanguage() + "<br>\n" +
                "year:              " + book.getYear() + "<br>";
    }

    private String readFileAsUtf8(Path path) throws IOException {
        var contents = Files.readAllBytes(path);
        return new String(contents, "UTF-8");
    }
}
