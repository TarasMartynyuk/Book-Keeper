package http.server;

import http.server.processors.Processor;
import http.server.processors.StaticResourceProcessor;
import http.server.processors.ServletProcessor;
import http.server.request.HttpRequest;
import http.server.request.Request;
import http.server.response.HttpResponse;
import http.server.response.Response;
import http.server.servlet.AbstractServletsMap;

import java.net.Socket;
import java.net.ServerSocket;
import java.net.InetAddress;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.lang.Thread.sleep;

public class HttpServer {

    /**
     * WEB_ROOT is the directory where our HTML and other files reside. For this
     * package, WEB_ROOT is the "webroot" directory under the working directory.
     * The working directory is the location in the file system from where the
     * java command was invoked.
     */
    // shutdown command
    private static final String SHUTDOWN_COMMAND = "/SHUTDOWN";
    private static final boolean SHUTDOWN = true;
    private static final int PORT = 8888;

    private final AbstractServletsMap servletsMap;

    private final ExecutorService _threadPool = Executors.newFixedThreadPool(5);
    
    public HttpServer(AbstractServletsMap servletsMap) {
        this.servletsMap = servletsMap;
    }
    
    public void await() throws IOException {

        try (var serverSocket = startServerSocket()) {
            System.out.println("Server is waiting for request at port: " + PORT);
            servletsMap.callInit();
            boolean isShutDown = !SHUTDOWN;

            assert serverSocket != null;
            // Loop waiting for a request
            while (!isShutDown) {
                try {
                    Socket socket = serverSocket.accept();

                    _threadPool.execute(() -> {
                        try {
                            boolean res = processRequest(socket);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            servletsMap.callDestroy();
        }
    }

    private boolean processRequest(Socket socket) throws IOException {
        System.out.println("processing socket : " + socket.toString());

        var input = socket.getInputStream();
        var output = socket.getOutputStream();

        var request = new HttpRequest(input);
        System.out.println(request.getRequestAsText());

        var response = new HttpResponse(output);

        String uri = request.getURI();
        if (isNull(uri)) {
            return !SHUTDOWN;
        }

        if (isShutDown(uri)) {
            return SHUTDOWN;
        }

        var processor = selectProcessor(uri);
        processor.process(request, response);

        socket.close();

        return !SHUTDOWN;
    }

    private boolean isNull(String uri) {
        return uri == null;
    }

    private boolean isShutDown(String uri) {
        return uri.equals(SHUTDOWN_COMMAND);
    }

    private Processor selectProcessor(String uri) {
        Processor processor;
        // check if this is a request for a servlet or a static resource
        // a request for a servlet begins with "/servlet/"
        if (uri.startsWith("/servlet/")) {
            processor = new ServletProcessor(servletsMap);
        } else {
            processor = new StaticResourceProcessor();
        }
        return processor;
    }

    private ServerSocket startServerSocket() {
        try {
            return new ServerSocket(PORT, 1, InetAddress.getByName("127.0.0.1"));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
            return null;
        }
    }
}
