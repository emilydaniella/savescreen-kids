package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import config.DataBaseConnection;
import model.UsoTela;

public class UsoTelaDAO {

    public void salvar(UsoTela usoTela) {
        String sql = "INSERT INTO uso_tela (id_uso, data_hora_inicio, data_hora_fim) VALUES (?, ?, ?)";

         /***********************************************************************************************
         * OS PREPAREDSTATEMENT SAO CHAMADOS NOS AQUIVOS DAO PARA MELHORAR A PERFORMANCE E A SEGURANCA *
         **********************************************************************************************/

         // devem ser evocados com SET e ID, evite (+) por conta do SQL injection
         // email = 'teste@gmail.com' OR '1'='1'
        try (Connection conn = DataBaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, usoTela.getIdUso());
            stmt.setTimestamp(2, Timestamp.valueOf(usoTela.getDataHoraInicio()));
            stmt.setTimestamp(3, Timestamp.valueOf(usoTela.getDataHoraFim()));
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<UsoTela> listar() {
        List<UsoTela> usos = new ArrayList<>();
        String sql = "SELECT * FROM uso_tela";

        try (Connection conn = DataBaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id_uso");
                LocalDateTime inicio = rs.getTimestamp("data_hora_inicio").toLocalDateTime();
                LocalDateTime fim = rs.getTimestamp("data_hora_fim").toLocalDateTime();
                usos.add(new UsoTela(id, inicio, fim));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return usos;
    }
}
