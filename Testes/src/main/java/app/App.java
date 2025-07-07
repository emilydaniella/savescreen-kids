package app;

import controller.CriancaController;
import controller.ResponsavelController;
import controller.RotinaController;
import controller.UserController;
import controller.UsoTelaController;
import dao.UserDAO;

import static spark.Spark.port;
import static spark.Spark.staticFiles;

public class App {
    public static void main(String[] args) {
        //port(4567);
        port(8081);
        staticFiles.location("/pages");

        // Criar UserDAO e garantir a criação da tabela
        UserDAO userDAO = new UserDAO();
        userDAO.createTable();        

        // Instanciando controllers (e configurando as rotas no construtor delas)
        ResponsavelController responsavelController = new ResponsavelController();
        CriancaController criancaController = new CriancaController();
        RotinaController rotinaController = new RotinaController();
        UsoTelaController usoTelaController = new UsoTelaController();
        UserController userController = new UserController();

        System.out.println("Servidor rodando em http://localhost:8081/");

        /*
         * port(8081);
        staticFiles.location("src/main/resources/pages");
        
        // Criar UserDAO e garantir a criação da tabela
        UserDAO userDAO = new UserDAO();
        userDAO.createTable();
        
        // Instanciando os serviços 
        ResponsavelService responsavelService = new ResponsavelService();
        CriancaService criancaService = new CriancaService();
        RotinaService rotinaService = new RotinaService();
        UsoTelaService usoTelaService = new UsoTelaService();
        UserService userService = new UserService();
         

        // Instanciando controllers (e configurando as rotas no construtor delas)
        ResponsavelController responsavelController = new ResponsavelController();
        CriancaController criancaController = new CriancaController();
        RotinaController rotinaController = new RotinaController();
        UsoTelaController usoTelaController = new UsoTelaController();
        UserController userController = new UserController();

        System.out.println("Servidor rodando em http://localhost:8081/");
         */
    }
}
