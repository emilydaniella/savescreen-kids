package app;

import controller.CriancaController;
import controller.OCRController;
import controller.ResponsavelController;
import controller.RotinaController;
import controller.UserController;
import controller.UsoTelaController;  // IMPORTAR OCRController
import dao.UserDAO;
import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.staticFiles;

public class App {
    public static void main(String[] args) {
        // USA SPARK
        port(8082);
        staticFiles.location("/public");

        get("/", (req, res) -> "Servidor rodando!");

        // INICIALIZA O BANCO DE DADOS DO USUARIO, SENAO CRIA UMA NOVA TABELA
        UserDAO userDAO = new UserDAO();
        userDAO.createTable();

        // CHAMA OS CONTROLADORES E DEFINE AS ROTAS
        new ResponsavelController();
        new CriancaController();
        RotinaController.configurarRotas();
        UserController.configurarRotas();
        UsoTelaController.rotas();

        OCRController.routes();  

        // O CAMINHO QUE RODA NOSSO SERVICO NA WEB
        System.out.println("Servidor rodando em http://localhost:8082/");
    }
}
