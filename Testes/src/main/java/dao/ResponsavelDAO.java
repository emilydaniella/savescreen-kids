package dao;

import model.Responsavel;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ResponsavelDAO extends DAO {

    public void inserir(Responsavel responsavel) {
        String sql = """
            INSERT INTO responsavel
              (username, email, telefone, password)
            VALUES (?, ?, ?, ?)
            RETURNING id_responsavel
        """;

        try (Connection conn = conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, responsavel.getUsername());
            stmt.setString(2, responsavel.getEmail());
            stmt.setString(3, responsavel.getTelefone());
            stmt.setString(4, responsavel.getPassword());

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    responsavel.setId(rs.getInt("id_responsavel"));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir responsável", e);
        }
    }

    public List<Responsavel> listar() {
        List<Responsavel> lista = new ArrayList<>();
        String sql = """
            SELECT id_responsavel, username, email, telefone, password
              FROM responsavel
        """;

        try (Connection conn = conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                lista.add(new Responsavel(
                    rs.getInt("id_responsavel"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("email"),
                    rs.getString("telefone")
                ));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar responsáveis", e);
        }
        return lista;
    }

    public Responsavel buscarPorId(int id) {
        String sql = "SELECT * FROM responsavel WHERE id_responsavel = ?";

        try (Connection conn = conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Responsavel(
                        rs.getInt("id_responsavel"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("email"),
                        rs.getString("telefone")
                    );
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar responsável por ID", e);
        }
        return null;
    }

    public void atualizar(Responsavel responsavel) {
        String sql = """
            UPDATE responsavel
               SET username = ?, email = ?, telefone = ?, password = ?
             WHERE id_responsavel = ?
        """;

        try (Connection conn = conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, responsavel.getUsername());
            stmt.setString(2, responsavel.getEmail());
            stmt.setString(3, responsavel.getTelefone());
            stmt.setString(4, responsavel.getPassword());
            stmt.setInt(5, responsavel.getId());
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar responsável", e);
        }
    }

    public void deletar(int id) {
        String sql = "DELETE FROM responsavel WHERE id_responsavel = ?";

        try (Connection conn = conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar responsável", e);
        }
    }
}
