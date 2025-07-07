package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import config.DataBaseConnection;
import model.RotinaModel;

public class RotinaDAO {

         /***********************************************************************************************
         * OS PREPAREDSTATEMENT SAO CHAMADOS NOS AQUIVOS DAO PARA MELHORAR A PERFORMANCE E A SEGURANCA *
         **********************************************************************************************/

         // devem ser evocados com SET e ID, evite (+) por conta do SQL injection
         // email = 'teste@gmail.com' OR '1'='1'
    public void inserir(RotinaModel rotina) {
        String sql = "INSERT INTO rotina (nome, descricao, horario, id_responsavel) VALUES (?, ?, ?, ?)";
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, rotina.getNome());
            stmt.setString(2, rotina.getDescricao());
            stmt.setString(3, rotina.getHorario());
            stmt.setInt(4, rotina.getIdResponsavel());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<RotinaModel> listar() {
        List<RotinaModel> lista = new ArrayList<>();
        String sql = "SELECT * FROM rotina";
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                RotinaModel r = new RotinaModel(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("descricao"),
                        rs.getString("horario"),
                        rs.getInt("id_responsavel")
                );
                lista.add(r);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return lista;
    }

    public RotinaModel buscarPorId(int id) {
        String sql = "SELECT * FROM rotina WHERE id = ?";
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new RotinaModel(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("descricao"),
                        rs.getString("horario"),
                        rs.getInt("id_responsavel")
                );
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void atualizar(RotinaModel rotina) {
        String sql = "UPDATE rotina SET nome=?, descricao=?, horario=?, id_responsavel=? WHERE id=?";
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, rotina.getNome());
            stmt.setString(2, rotina.getDescricao());
            stmt.setString(3, rotina.getHorario());
            stmt.setInt(4, rotina.getIdResponsavel());
            stmt.setInt(5, rotina.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deletar(int id) {
        String sql = "DELETE FROM rotina WHERE id=?";
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // insercao de texto OCR
    public void inserirTextoOCR(String nome, String textoExtraido, String horario) {
        String sql = "INSERT INTO rotina (nome, descricao, horario) VALUES (?, ?, ?)";
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nome); 
            stmt.setString(2, textoExtraido); 
            stmt.setString(3, horario);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir OCR na rotina", e);
        }
    }

    public void salvarTextoExtraido(String texto, int id_crianca) {
    String sql = "INSERT INTO rotina (nome, descricao, horario, id_crianca) VALUES (?, ?, ?, ?)";
    try (Connection conn = DataBaseConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setString(1, "Tarefa via OCR");
        stmt.setString(2, texto);
        stmt.setString(3, "08:00"); // pode vir de OCR tambem
        stmt.setInt(4, id_crianca);
        stmt.executeUpdate();
    } catch (SQLException e) {
        throw new RuntimeException("Erro ao salvar texto extraido do OCR", e);
    }
}

    public List<RotinaModel> buscarPorUsuario(int userId) {
        List<RotinaModel> lista = new ArrayList<>();
        String sql = "SELECT * FROM rotina WHERE id_responsavel = ?";
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                RotinaModel r = new RotinaModel(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("descricao"),
                        rs.getString("horario"),
                        rs.getInt("id_responsavel")
                );
                lista.add(r);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return lista;
    }

}
