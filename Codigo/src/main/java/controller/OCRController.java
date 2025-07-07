package controller;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.MultipartConfigElement;
import javax.servlet.http.Part;

import dao.OCRDAO;
import service.OCRService;
import static spark.Spark.post;

public class OCRController {

    public static void routes() {
        post("/ocr", (req, res) -> {
            req.attribute("org.eclipse.jetty.multipartConfig", new MultipartConfigElement(System.getProperty("java.io.tmpdir")));

            Part foto = req.raw().getPart("foto");
            if (foto == null) {
                res.status(400);
                return "Arquivo não enviado.";
            }

            String originalName = Paths.get(foto.getSubmittedFileName()).getFileName().toString();
            String extension = originalName.contains(".")
                    ? originalName.substring(originalName.lastIndexOf('.'))
                    : ".tmp";

            Path tempFile = Files.createTempFile("upload-", extension);

            try (InputStream input = foto.getInputStream()) {
                Files.copy(input, tempFile, java.nio.file.StandardCopyOption.REPLACE_EXISTING);

                String textoExtraido;
                try {
                    textoExtraido = OCRService.extractText(tempFile.toString());
                } catch (Exception e) {
                    res.status(500);
                    return "Erro no OCR: " + e.getMessage();
                }

                // Salvar no banco de dados
                OCRDAO dao = new OCRDAO();
                dao.salvarTexto(textoExtraido);

                res.status(200);
                res.type("text/plain");
                return textoExtraido;

            } catch (IOException e) {
                res.status(500);
                return "Erro ao processar o arquivo: " + e.getMessage();
            } finally {
                try {
                    Files.deleteIfExists(tempFile);
                } catch (IOException e) {
                    System.err.println("Erro ao deletar arquivo temporário: " + e.getMessage());
                }
            }
        });
    }
}
