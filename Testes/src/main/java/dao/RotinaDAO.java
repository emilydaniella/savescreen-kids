package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.RotinaModel;

public class RotinaDAO extends DAO {

    public void inserir(RotinaModel rotina) {
        Connection conn = conectar();
        try {
            String sql = "INSERT INTO rotina (nome, descricao, horario) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, rotina.getNome());
            stmt.setString(2, rotina.getDescricao());
            stmt.setString(3, rotina.getHorario());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            fecharConexao(conn);
        }
    }

    public List<RotinaModel> listar() {
        List<RotinaModel> lista = new ArrayList<>();
        Connection conn = conectar();
        try {
            String sql = "SELECT * FROM rotina";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                RotinaModel r = new RotinaModel(
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getString("descricao"),
                    rs.getString("horario")
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

    public RotinaModel buscarPorId(int id) {
        Connection conn = conectar();
        try {
            String sql = "SELECT * FROM rotina WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new RotinaModel(
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getString("descricao"),
                    rs.getString("horario")
                );
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            fecharConexao(conn);
        }
    }

    public void atualizar(RotinaModel rotina) {
        Connection conn = conectar();
        try {
            String sql = "UPDATE rotina SET nome=?, descricao=?, horario=? WHERE id=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, rotina.getNome());
            stmt.setString(2, rotina.getDescricao());
            stmt.setString(3, rotina.getHorario());
            stmt.setInt(4, rotina.getId());
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
            String sql = "DELETE FROM rotina WHERE id=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            fecharConexao(conn);
        }
    }
}
