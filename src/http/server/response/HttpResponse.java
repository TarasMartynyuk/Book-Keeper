package http.server.response;

import http.server.Constants;
import myapp.ResponseBuilder;

import java.io.OutputStream;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.File;
import java.io.PrintWriter;

public class HttpResponse implements Response {

    private static final int BUFFER_SIZE = 1024;
    private final OutputStream output;    

    public HttpResponse(OutputStream output) {
        this.output = output;
    }

    /* This method is used to serve a static page */
    @Override
    public void sendStaticResource(String resourceURI) throws IOException {
        var bytes = new byte[BUFFER_SIZE];
        FileInputStream fis = null;
        try {
            /* request.getUri has been replaced by request.getRequestURI */
            var file = new File(Constants.WEB_ROOT, resourceURI);

            fis = new FileInputStream(file);

            var builder = new StringBuilder();
            int ch = fis.read(bytes, 0, BUFFER_SIZE);

            while (ch != -1) {
//                output.write(bytes, 0, ch);
                builder.append(new String(bytes, "UTF-8"));
                ch = fis.read(bytes, 0, BUFFER_SIZE);
            }

            var responce = builder.toString();
            new ResponseBuilder(this).writeOkResponce(responce, getContentTypeFromFilename(resourceURI));

        } catch (FileNotFoundException e) {
            String errorMessage = "HTTP/1.1 404 File Not Found\r\n"
                    + "Content-Type: text/html\r\n"
                    + "Content-Length: 23\r\n"
                    + "\r\n"
                    + "<h1>File Not Found</h1>";
            output.write(errorMessage.getBytes());
        } finally {
            if (fis != null) {
                fis.close();
            }
        }
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        // autoflush is true, println() will flush,
        // but print() will not.
        var writer = new PrintWriter(output, true);
        return writer;
    }

    private String getContentTypeFromFilename(String filename) {
        String extention = getExtention(filename);

        switch (extention) {
            case "html":
                return "text/html";
//            case "pn":
//                return "image/gif";
            default:
                throw new IllegalArgumentException("unknown extention: " + extention);
        }
    }

    private String getExtention(String filename) {

        int i = filename.lastIndexOf('.');
        if (i < 0) {
            throw new IllegalArgumentException("filename does not have a . in it");
        }
        return filename.substring(i+1);
    }
}
