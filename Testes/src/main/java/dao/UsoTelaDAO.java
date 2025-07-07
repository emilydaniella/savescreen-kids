package dao;

import model.UsoTela;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class UsoTelaDAO extends DAO {

    public void salvar(UsoTela usoTela) {
        String sql = "INSERT INTO uso_tela (id_uso, data_hora_inicio, data_hora_fim) VALUES (?, ?, ?)";
        Connection conn = null;

        try {
            conn = conectar();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, usoTela.getIdUso());
            stmt.setTimestamp(2, Timestamp.valueOf(usoTela.getDataHoraInicio()));
            stmt.setTimestamp(3, Timestamp.valueOf(usoTela.getDataHoraFim()));
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            fecharConexao(conn);
        }
    }

    public List<UsoTela> listar() {
        List<UsoTela> usos = new ArrayList<>();
        String sql = "SELECT * FROM uso_tela";
        Connection conn = null;

        try {
            conn = conectar();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                int id = rs.getInt("id_uso");
                LocalDateTime inicio = rs.getTimestamp("data_hora_inicio").toLocalDateTime();
                LocalDateTime fim = rs.getTimestamp("data_hora_fim").toLocalDateTime();
                usos.add(new UsoTela(id, inicio, fim));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            fecharConexao(conn);
        }

        return usos;
    }
}
