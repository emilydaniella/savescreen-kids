package dao;

import model.RestricaoUso;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RestricaoUsoDAO extends DAO {

    public void inserir(RestricaoUso restricao) {
        Connection conn = conectar();
        try {
            String sql = "INSERT INTO restricao_uso (horario_inicio, horario_fim, categoria, limite_diario) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setTime(1, Time.valueOf(restricao.getHorarioInicio()));
            stmt.setTime(2, Time.valueOf(restricao.getHorarioFim()));
            stmt.setString(3, restricao.getCategoria());
            stmt.setInt(4, restricao.getLimiteDiario());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            fecharConexao(conn);
        }
    }

    public List<RestricaoUso> listar() {
        List<RestricaoUso> lista = new ArrayList<>();
        Connection conn = conectar();
        try {
            String sql = "SELECT * FROM restricao_uso";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                RestricaoUso r = new RestricaoUso(
                    rs.getInt("id_restricao"),
                    rs.getTime("horario_inicio").toLocalTime(),
                    rs.getTime("horario_fim").toLocalTime(),
                    rs.getString("categoria"),
                    rs.getInt("limite_diario")
                );
                lista.add(r);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            fecharConexao(conn);
        }
        return lista;
    }

    public RestricaoUso buscarPorId(int id) {
        Connection conn = conectar();
        try {
            String sql = "SELECT * FROM restricao_uso WHERE id_restricao = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new RestricaoUso(
                    rs.getInt("id_restricao"),
                    rs.getTime("horario_inicio").toLocalTime(),
                    rs.getTime("horario_fim").toLocalTime(),
                    rs.getString("categoria"),
                    rs.getInt("limite_diario")
                );
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            fecharConexao(conn);
        }
    }

    public void atualizar(RestricaoUso restricao) {
        Connection conn = conectar();
        try {
            String sql = "UPDATE restricao_uso SET horario_inicio=?, horario_fim=?, categoria=?, limite_diario=? WHERE id_restricao=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setTime(1, Time.valueOf(restricao.getHorarioInicio()));
            stmt.setTime(2, Time.valueOf(restricao.getHorarioFim()));
            stmt.setString(3, restricao.getCategoria());
            stmt.setInt(4, restricao.getLimiteDiario());
            stmt.setInt(5, restricao.getIdRestricao());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            fecharConexao(conn);
        }
    }

    public void deletar(int id) {
        Connection conn = conectar();
        try {
            String sql = "DELETE FROM restricao_uso WHERE id_restricao=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            fecharConexao(conn);
        }
    }

    public void salvar(RestricaoUso restricao) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'salvar'");
    }
}
