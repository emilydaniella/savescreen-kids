package dao;

import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO extends DAO {
    private static final Logger logger = LoggerFactory.getLogger(UserDAO.class);

    public void createTable() {
        String sql = """
            CREATE TABLE IF NOT EXISTS users (
              id       SERIAL PRIMARY KEY,
              username VARCHAR(255) UNIQUE NOT NULL,
              email    VARCHAR(255) UNIQUE NOT NULL,
              password VARCHAR(255) NOT NULL,
              type     VARCHAR(50) NOT NULL
            )
        """;
        try (Connection conn = conectar();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            logger.info("Tabela users criada");
        } catch (SQLException e) {
            logger.error("Erro criando tabela users", e);
        }
    }

    public User inserir(User u, String type) {
        String sql = """
            INSERT INTO users (username, email, password, type)
            VALUES (?, ?, ?, ?)
            RETURNING id
        """;
        try (Connection conn = conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, u.getUsername());
            ps.setString(2, u.getEmail());
            ps.setString(3, u.getPassword());
            ps.setString(4, type);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    u.setId(rs.getInt("id"));
                }
            }
            logger.info("User inserido: {}", u);
            return u;
        } catch (SQLException e) {
            logger.error("Erro ao inserir user", e);
            throw new RuntimeException(e);
        }
    }

    public User buscarPorId(int id) {
        String sql = "SELECT * FROM users WHERE id = ?";
        try (Connection conn = conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("email")
                    );
                }
            }
        } catch (SQLException e) {
            logger.error("Erro ao buscar user por id", e);
            throw new RuntimeException(e);
        }
        return null;
    }

    public List<User> listar() {
        String sql = "SELECT * FROM users";
        List<User> lista = new ArrayList<>();
        try (Connection conn = conectar();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                lista.add(new User(
                    rs.getInt("id"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("email")
                ));
            }
        } catch (SQLException e) {
            logger.error("Erro ao listar users", e);
            throw new RuntimeException(e);
        }
        return lista;
    }

    public User atualizar(User u) {
        String sql = """
            UPDATE users
               SET username = ?, email = ?, password = ?
             WHERE id = ?
        """;
        try (Connection conn = conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, u.getUsername());
            ps.setString(2, u.getEmail());
            ps.setString(3, u.getPassword());
            ps.setInt(4, u.getId());
            ps.executeUpdate();
            logger.info("User atualizado: {}", u);
            return u;
        } catch (SQLException e) {
            logger.error("Erro ao atualizar user", e);
            throw new RuntimeException(e);
        }
    }

    public boolean deletar(int id) {
        String sql = "DELETE FROM users WHERE id = ?";
        try (Connection conn = conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            int afetados = ps.executeUpdate();
            if (afetados > 0) {
                logger.info("User deletado id={}", id);
                return true;
            }
        } catch (SQLException e) {
            logger.error("Erro ao deletar user", e);
            throw new RuntimeException(e);
        }
        return false;
    }
}
