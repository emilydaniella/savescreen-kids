package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import config.DataBaseConnection;
import model.Crianca;
import model.Responsavel;

public class CriancaDAO {

    public void inserir(Crianca crianca) {
        String sql = """
            INSERT INTO crianca
            (nome, data_nascimento, id_responsavel)
            VALUES (?, ?, ?)
            RETURNING id_crianca
        """;

        /***********************************************************************************************
         * OS PREPAREDSTATEMENT SAO CHAMADOS NOS AQUIVOS DAO PARA MELHORAR A PERFORMANCE E A SEGURANCA *
         **********************************************************************************************/

         // devem ser evocados com SET e ID, evite (+) por conta do SQL injection
         // email = 'teste@gmail.com' OR '1'='1'
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, crianca.getUsername());
            stmt.setDate(2, Date.valueOf(crianca.getDataNascimento()));
            stmt.setInt(3, crianca.getIdResponsavel());

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    crianca.setId(rs.getInt("id_crianca"));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir criança", e);
        }
    }

    public List<Crianca> listar() {
        String sql = """
                    SELECT
                      c.id_crianca, c.nome,
                      c.data_nascimento, c.id_responsavel,
                      r.id_responsavel AS r_id, r.username AS r_user,
                      r.email    AS r_email,    r.password AS r_pass,
                      r.telefone AS r_tel
                    FROM crianca c
                    JOIN responsavel r
                      ON c.id_responsavel = r.id_responsavel
                """;

        List<Crianca> lista = new ArrayList<>();
        try (Connection conn = DataBaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Responsavel r = new Responsavel(
                        rs.getInt("r_id"),
                        rs.getString("r_user"),
                        rs.getString("r_pass"),
                        rs.getString("r_email"),
                        rs.getString("r_tel"));

                Crianca c = new Crianca(
                        rs.getInt("id_crianca"),
                        rs.getString("nome"),
                        rs.getDate("data_nascimento").toLocalDate(),
                        rs.getInt("id_responsavel"));
                c.setResponsavel(r);
                lista.add(c);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar crianças", e);
        }
        return lista;
    }
    public List<Crianca> buscarPorResponsavel(int idResponsavel) {
        List<Crianca> lista = new ArrayList<>();
        String sql = "SELECT * FROM crianca WHERE id_responsavel = ?";
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, idResponsavel);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Crianca c = new Crianca();
                    c.setId(rs.getInt("id_crianca")); // Corrigido: id_crianca (não "id")
                    c.setUsername(rs.getString("nome")); // conforme a coluna da tabela
                    c.setDataNascimento(rs.getDate("data_nascimento").toLocalDate());
                    c.setIdResponsavel(rs.getInt("id_responsavel"));
                    lista.add(c);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
    
    
    

    public Crianca buscarPorId(int id) {
        String sql = """
            SELECT * 
              FROM crianca 
             WHERE id_crianca = ?
        """;

        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Crianca(
                        rs.getInt("id_crianca"),
                        rs.getString("nome"),
                        rs.getDate("data_nascimento").toLocalDate(),
                        rs.getInt("id_responsavel")
                    );
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar criança por ID", e);
        }
        return null;
    }

    public void atualizar(Crianca crianca) {
        String sql = """
            UPDATE crianca
               SET nome = ?, email = ?, password = ?,
                data_nascimento = ?, id_responsavel = ?
             WHERE id_crianca = ?
        """;

        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, crianca.getUsername());
            stmt.setString(2, crianca.getEmail());
            stmt.setString(3, crianca.getPassword());
            stmt.setDate(5, Date.valueOf(crianca.getDataNascimento()));
            stmt.setInt(6, crianca.getIdResponsavel());
            stmt.setInt(7, crianca.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar criança", e);
        }
    }

    public void deletar(int id) {
        String sql = "DELETE FROM crianca WHERE id_crianca = ?";

        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar criança", e);
        }
    }
}
