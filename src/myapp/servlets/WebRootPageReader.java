package myapp.servlets;

import http.server.Constants;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class WebRootPageReader {
    public static String getPage(String webRootPath) throws IOException {
        return readFileAsUtf8(Paths.get(Constants.WEB_ROOT, webRootPath));
    }

    private static String readFileAsUtf8(Path path) throws IOException {
        var contents = Files.readAllBytes(path);
        return new String(contents, "UTF-8");
    }
}
