package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import config.DataBaseConnection;

public class OCRDAO {

    public void salvarTexto(String texto) {
        String sql = "INSERT INTO ocr (conteudo) VALUES (?)";

         /***********************************************************************************************
         * OS PREPAREDSTATEMENT SAO CHAMADOS NOS AQUIVOS DAO PARA MELHORAR A PERFORMANCE E A SEGURANCA *
         **********************************************************************************************/

         // devem ser evocados com SET e ID, evite (+) por conta do SQL injection
         // email = 'teste@gmail.com' OR '1'='1'
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, texto);
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Erro ao salvar OCR no banco: " + e.getMessage());
        }
    }
}
