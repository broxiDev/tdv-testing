package tdv;

import java.io.File;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class SwaggerServer {
    public static void main(String[] args) throws Exception {
        int port = args.length > 0 ? Integer.parseInt(args[0]) : 8765;
        File projectDir = new File(".");
        try (ServerSocket ss = new ServerSocket(port)) {
            System.out.println("Servidor HTTP en http://localhost:" + port);
            System.out.println("Abrir en Chrome y usar Authorize con el token");
            while (true) {
                Socket s = ss.accept();
                byte[] req = new byte[4096];
                int n = s.getInputStream().read(req);
                String request = new String(req, 0, n);
                String path = request.split(" ")[1];
                if (path.equals("/")) path = "/swagger-ui.html";
                File f = new File(projectDir, path.startsWith("/") ? path.substring(1) : path);
                String content;
                String contentType = "text/plain";
                if (f.exists()) {
                    content = new String(Files.readAllBytes(f.toPath()), StandardCharsets.UTF_8);
                    if (path.endsWith(".html")) contentType = "text/html; charset=utf-8";
                    else if (path.endsWith(".yaml") || path.endsWith(".yml")) contentType = "text/yaml; charset=utf-8";
                } else {
                    content = "404 Not Found";
                }
                byte[] resp = ("HTTP/1.1 200 OK\r\nContent-Type: " + contentType
                        + "\r\nContent-Length: " + content.getBytes(StandardCharsets.UTF_8).length
                        + "\r\nAccess-Control-Allow-Origin: *\r\n\r\n" + content).getBytes(StandardCharsets.UTF_8);
                OutputStream os = s.getOutputStream();
                os.write(resp);
                os.close();
                s.close();
            }
        }
    }
}
