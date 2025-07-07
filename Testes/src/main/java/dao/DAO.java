package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DAO {
    // URL de conexão com o banco de dados PostgreSQL
    private static String URL = "dbsavescreen.postgres.database.azure.com";
    
    // Usuário de teste banco de dados
    private static String USER = "save4kids";

    // Senha do banco de dados
    private static String PASSWORD = "kids4sure!";

    /**
     * Construtor padrão da classe DAO.
     */
    public DAO() {}

    /**
     * Estabelece a conexão com o banco de dados PostgreSQL.
     *
     * @return uma instância de {@link Connection} se a conexão for bem-sucedida.
     * @throws RuntimeException se ocorrer algum erro ao conectar.
     */
    protected Connection conectar() {
        try {
            // Retorna uma conexão com os dados especificados
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            // Lança exceção customizada em caso de falha na conexão
            throw new RuntimeException("Erro ao conectar ao banco de dados", e);
        }
    }

    /**
     * Fecha a conexão com o banco de dados, caso ela não seja nula.
     *
     * @param conn a conexão a ser fechada.
     * @throws RuntimeException se ocorrer erro ao fechar a conexão.
     */
    protected void fecharConexao(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
                System.out.println("Conexão fechada com sucesso!");
            } catch (SQLException e) {
                System.err.println("Erro ao fechar a conexão: " + e.getMessage());
                throw new RuntimeException("Erro ao conectar com o banco de dados");
            }
        }
    }
}
