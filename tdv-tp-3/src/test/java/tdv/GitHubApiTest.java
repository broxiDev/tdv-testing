package tdv;

import org.junit.jupiter.api.*;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class GitHubApiTest extends BaseTest {

    private static String token;
    private static String owner;
    private static Integer prNumber;
    private static Thread httpServer;
    private static volatile ServerSocket serverSocket;
    private static final String API_BASE = "https://api.github.com";
    private static final String REPO_NAME = "tdv-tp3-test";
    private static final int SWAGGER_PORT = 8765;

    @BeforeAll
    static void initToken() throws Exception {
        token = System.getenv("GITHUB_TOKEN");
        if (token == null || token.isBlank()) {
            File dotenv = new File(".env");
            if (dotenv.exists()) {
                for (String line : Files.readAllLines(Paths.get(".env"))) {
                    line = line.trim();
                    if (line.startsWith("GITHUB_TOKEN=")) {
                        token = line.substring("GITHUB_TOKEN=".length());
                        break;
                    }
                }
            }
        }
        assertNotNull(token, "GITHUB_TOKEN no configurada. Ponela en .env o en variable de entorno");
        assertFalse(token.isBlank(), "GITHUB_TOKEN no puede estar vacia");
    }

    @BeforeAll
    static void startHttpServer() throws Exception {
        File projectDir = new File(".");
        serverSocket = new ServerSocket(SWAGGER_PORT);
        httpServer = new Thread(() -> {
            try {
                while (true) {
                    Socket s = serverSocket.accept();
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
                        contentType = "text/plain";
                    }
                    byte[] resp = ("HTTP/1.1 200 OK\r\nContent-Type: " + contentType + "\r\nContent-Length: " +
                            content.getBytes(StandardCharsets.UTF_8).length + "\r\nAccess-Control-Allow-Origin: *\r\n\r\n" + content).getBytes(StandardCharsets.UTF_8);
                    OutputStream os = s.getOutputStream();
                    os.write(resp);
                    os.close();
                    s.close();
                }
            } catch (Exception ignored) {}
        }, "swagger-http-server");
        httpServer.setDaemon(true);
        httpServer.start();
        Thread.sleep(500);
    }

    @AfterAll
    static void stopHttpServer() throws Exception {
        if (serverSocket != null) serverSocket.close();
        if (httpServer != null) httpServer.join(1000);
    }

    private void openSwaggerUI() {
        driver.get("http://localhost:" + SWAGGER_PORT + "/swagger-ui.html");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".authorize, .btn.authorize")));
        System.out.println("SwaggerUI cargado correctamente en http://localhost:" + SWAGGER_PORT);
        System.out.println("Navegador: Chrome (controlado por Selenium WebDriver)");
        System.out.println("Session ID: " + ((org.openqa.selenium.remote.RemoteWebDriver) driver).getSessionId());
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> apiCall(String method, String url, Object body) {
        String script =
                "var callback = arguments[arguments.length - 1];\n" +
                "var opts = {\n" +
                "    method: arguments[0],\n" +
                "    headers: {\n" +
                "        'Authorization': 'Bearer ' + arguments[1],\n" +
                "        'Accept': 'application/vnd.github+json',\n" +
                "        'Content-Type': 'application/json'\n" +
                "    }\n" +
                "};\n" +
                "if (arguments[2]) {\n" +
                "    opts.body = JSON.stringify(arguments[2]);\n" +
                "}\n" +
                "fetch(arguments[3], opts)\n" +
                "    .then(function(r) {\n" +
                "        return r.json().then(function(data) {\n" +
                "            callback(JSON.stringify({status: r.status, body: data}));\n" +
                "        });\n" +
                "    })\n" +
                "    .catch(function(err) {\n" +
                "        callback(JSON.stringify({status: 0, body: {error: err.message}}));\n" +
                "    });\n";
        String result = (String) ((JavascriptExecutor) driver).executeAsyncScript(script, method, token, body, url);
        assertNotNull(result, "Respuesta vacia del fetch a " + url);
        return JsonUtil.parse(result);
    }

    private int getStatus(Map<String, Object> resp) {
        return ((Number) resp.get("status")).intValue();
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> getBody(Map<String, Object> resp) {
        return (Map<String, Object>) resp.get("body");
    }

    private void printResult(String testName, int status, Object body) {
        System.out.println("\n=== " + testName + " ===");
        System.out.println("Status: " + status);
        System.out.println("Body: " + JsonUtil.toPrettyString(body));
        System.out.println("====================\n");
    }

    @Test
    @Order(1)
    void testAuth() {
        // Abrir SwaggerUI para mostrar la documentacion desplegada
        openSwaggerUI();

        Map<String, Object> resp = apiCall("GET", API_BASE + "/user", null);
        int status = getStatus(resp);
        Map<String, Object> body = getBody(resp);

        printResult("GET /user (Autenticacion)", status, body);

        assertEquals(200, status, "El token deberia ser valido");
        assertNotNull(body.get("login"), "Deberia devolver el login del usuario");
        owner = (String) body.get("login");
        System.out.println("OK - Autenticacion exitosa como: " + owner);
    }

    @Test
    @Order(2)
    void testListRepos() {
        Map<String, Object> resp = apiCall("GET", API_BASE + "/user/repos?per_page=5&sort=updated", null);
        int status = getStatus(resp);

        @SuppressWarnings("unchecked")
        List<Map<String, Object>> repos = (List<Map<String, Object>>) resp.get("body");

        printResult("GET /user/repos (Listar repositorios)", status, resp.get("body"));

        assertEquals(200, status);
        assertNotNull(repos);
        assertTrue(repos.size() > 0, "Deberia tener al menos un repositorio");

        Map<String, Object> first = repos.get(0);
        assertTrue(first.containsKey("name"), "Cada repo debe tener nombre");
        assertTrue(first.containsKey("private"), "Cada repo debe tener visibilidad");
        assertTrue(first.containsKey("html_url"), "Cada repo debe tener URL");

        String visibilidad = first.get("private") == Boolean.TRUE ? "private" : "public";
        System.out.println("OK - Repositorios listados: " + repos.size());
        System.out.println("  Primer repo: " + first.get("name")
                + " | visibilidad: " + visibilidad
                + " | url: " + first.get("html_url"));
    }

    @Test
    @Order(3)
    void testCreateRepo() {
        Map<String, Object> repoData = Map.of(
                "name", REPO_NAME,
                "description", "Repositorio de prueba para TDV TP3",
                "private", false,
                "auto_init", true
        );

        Map<String, Object> resp = apiCall("POST", API_BASE + "/user/repos", repoData);
        int status = getStatus(resp);
        Map<String, Object> body = getBody(resp);

        printResult("POST /user/repos (Crear repositorio)", status, body);

        if (status == 201) {
            assertEquals(REPO_NAME, body.get("name"));
            assertEquals(false, body.get("private"));
            assertNotNull(body.get("html_url"));
            System.out.println("OK - Repositorio creado: " + body.get("html_url"));
        } else if (status == 422) {
            System.out.println("AVISO - El repositorio ya existe (422): " + body.get("message"));
            System.out.println("       Verificar en: https://github.com/" + body.get("full_name"));
        } else {
            fail("Status inesperado: " + status + " - " + body);
        }
    }

    @Test
    @Order(4)
    void testCreatePR() {
        assertNotNull(owner, "Owner no disponible. testAuth debe ejecutarse primero");
        String repoUrl = API_BASE + "/repos/" + owner + "/" + REPO_NAME;
        String branch = "feature/tp3";

        // 1. Obtener SHA del commit de main
        Map<String, Object> refResp = apiCall("GET", repoUrl + "/git/ref/heads/main", null);
        assertEquals(200, getStatus(refResp));
        String mainCommitSha = (String) ((Map<String, Object>) getBody(refResp).get("object")).get("sha");
        System.out.println("Main commit SHA: " + mainCommitSha);

        // 2. Obtener tree SHA del commit de main
        Map<String, Object> commitResp = apiCall("GET", repoUrl + "/git/commits/" + mainCommitSha, null);
        assertEquals(200, getStatus(commitResp));
        String mainTreeSha = (String) ((Map<String, Object>) getBody(commitResp).get("tree")).get("sha");
        System.out.println("Main tree SHA: " + mainTreeSha);

        // 3. Crear blob con contenido del archivo
        Map<String, Object> blobData = Map.of(
                "content", "Archivo de prueba creado automaticamente para el TP3 de TDV",
                "encoding", "utf-8"
        );
        Map<String, Object> blobResp = apiCall("POST", repoUrl + "/git/blobs", blobData);
        assertEquals(201, getStatus(blobResp));
        String blobSha = (String) getBody(blobResp).get("sha");
        System.out.println("Blob SHA: " + blobSha);

        // 4. Crear tree con el nuevo archivo
        Map<String, Object> treeItem = Map.of(
                "path", "test-tp3.txt",
                "mode", "100644",
                "type", "blob",
                "sha", blobSha
        );
        Map<String, Object> treeData = Map.of(
                "base_tree", mainTreeSha,
                "tree", List.of(treeItem)
        );
        Map<String, Object> treeResp = apiCall("POST", repoUrl + "/git/trees", treeData);
        assertEquals(201, getStatus(treeResp));
        String newTreeSha = (String) getBody(treeResp).get("sha");
        System.out.println("New tree SHA: " + newTreeSha);

        // 5. Crear commit con el nuevo tree
        Map<String, Object> commitData = Map.of(
                "message", "feat: agregar archivo test-tp3.txt para prueba de PR",
                "tree", newTreeSha,
                "parents", List.of(mainCommitSha)
        );
        Map<String, Object> newCommitResp = apiCall("POST", repoUrl + "/git/commits", commitData);
        assertEquals(201, getStatus(newCommitResp));
        String newCommitSha = (String) getBody(newCommitResp).get("sha");
        System.out.println("New commit SHA: " + newCommitSha);

        // 6. Crear branch ref
        Map<String, Object> refData = Map.of(
                "ref", "refs/heads/" + branch,
                "sha", newCommitSha
        );
        Map<String, Object> createRefResp = apiCall("POST", repoUrl + "/git/refs", refData);
        int refStatus = getStatus(createRefResp);
        if (refStatus == 201) {
            System.out.println("Branch '" + branch + "' creada");
        } else if (refStatus == 422) {
            System.out.println("AVISO - Branch '" + branch + "' ya existe, se reutiliza");
        } else {
            fail("Status inesperado al crear branch: " + refStatus);
        }

        // 7. Crear PR
        Map<String, Object> prData = Map.of(
                "title", "TP3 - Pull Request de prueba automatizada",
                "head", branch,
                "base", "main",
                "body", "PR generado automaticamente desde las pruebas Selenium del TP3"
        );
        Map<String, Object> prResp = apiCall("POST", repoUrl + "/pulls", prData);
        int prStatus = getStatus(prResp);
        Map<String, Object> prBody = getBody(prResp);

        printResult("POST /repos/{owner}/{repo}/pulls (Crear PR)", prStatus, prBody);

        if (prStatus == 201) {
            prNumber = ((Number) prBody.get("number")).intValue();
            System.out.println("OK - PR #" + prNumber + " creado: " + prBody.get("html_url"));
        } else if (prStatus == 422) {
            // Buscar PR existente
            System.out.println("AVISO - El PR ya existe, buscando numero...");
            Map<String, Object> listPrResp = apiCall("GET", repoUrl + "/pulls?head=" + owner + ":" + branch + "&state=open", null);
            @SuppressWarnings("unchecked")
            List<Map<String, Object>> existingPrs = (List<Map<String, Object>>) listPrResp.get("body");
            if (existingPrs != null && !existingPrs.isEmpty()) {
                prNumber = ((Number) existingPrs.get(0).get("number")).intValue();
                System.out.println("PR existente encontrado: #" + prNumber);
            } else {
                fail("No se pudo crear ni encontrar un PR existente");
            }
        } else {
            fail("Status inesperado al crear PR: " + prStatus);
        }
    }

    @Test
    @Order(5)
    void testCommentPR() {
        assertNotNull(owner, "Owner no disponible");
        assertNotNull(prNumber, "PR number no disponible. testCreatePR debe ejecutarse primero");

        String url = API_BASE + "/repos/" + owner + "/" + REPO_NAME + "/issues/" + prNumber + "/comments";
        Map<String, Object> commentData = Map.of(
                "body", "Comentario de prueba generado automaticamente desde las pruebas Selenium del TP3"
        );

        Map<String, Object> resp = apiCall("POST", url, commentData);
        int status = getStatus(resp);
        Map<String, Object> body = getBody(resp);

        printResult("POST /repos/{owner}/{repo}/issues/{number}/comments (Comentar PR)", status, body);

        assertEquals(201, status, "Deberia poder comentar en el PR");
        assertNotNull(body.get("id"), "El comentario deberia tener id");
        System.out.println("OK - Comentario agregado al PR #" + prNumber + ": " + body.get("html_url"));
    }
}
