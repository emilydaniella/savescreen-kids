    package dao;

    import java.sql.Connection;
    import java.sql.PreparedStatement;
    import java.sql.ResultSet;
    import java.sql.SQLException;
    import java.sql.Statement;
    import java.util.ArrayList;
    import java.util.List;

    import org.slf4j.Logger;
    import org.slf4j.LoggerFactory;

    import config.DataBaseConnection;
    import model.User;

    public class UserDAO {
        private static final Logger logger = LoggerFactory.getLogger(UserDAO.class);

        // Atualizei para criar a tabela conforme seu esquema real
        public void createTable() {
            String sql = """
                CREATE TABLE IF NOT EXISTS usuario (
                id_responsavel SERIAL PRIMARY KEY,
                nome VARCHAR(100) NOT NULL,
                email VARCHAR(100) NOT NULL UNIQUE,
                senha VARCHAR(100) NOT NULL,
                telefone VARCHAR(100) NOT NULL UNIQUE
                )
            """;
            
        /***********************************************************************************************
         * OS PREPAREDSTATEMENT SAO CHAMADOS NOS AQUIVOS DAO PARA MELHORAR A PERFORMANCE E A SEGURANCA *
         **********************************************************************************************/

         // devem ser evocados com SET e ID, evite (+) por conta do SQL injection
         // email = 'teste@gmail.com' OR '1'='1'
            try (Connection conn = DataBaseConnection.getConnection();
                Statement stmt = conn.createStatement()) {
                stmt.execute(sql);
                logger.info("Tabela usuario criada");
            } catch (SQLException e) {
                logger.error("Erro criando tabela usuario", e);
            }
        }

        // Inserir usuário (adaptado ao seu esquema)
        public User inserir(User u, String type) {
            String sql = """
                INSERT INTO usuario (nome, email, senha, telefone)
                VALUES (?, ?, ?, ?)
                RETURNING id_responsavel
            """;
            
            try (Connection conn = DataBaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
        
                ps.setString(1, u.getUsername());
                ps.setString(2, u.getEmail());
                ps.setString(3, u.getPassword());
                ps.setString(4, u.getTelefone());
        
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        u.setId(rs.getInt("id_responsavel"));
                    }
                }
                logger.info("User inserido: {}", u);
                return u;
            } catch (SQLException e) {
                logger.error("Erro ao inserir user", e);
                throw new RuntimeException(e);
            }
        }
        

        // Buscar por id_responsavel (ajustado)
        public User buscarPorId(int id) {
            String sql = "SELECT * FROM usuario WHERE id_responsavel = ?";
            try (Connection conn = DataBaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

                ps.setInt(1, id);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return new User(
                            rs.getInt("id_responsavel"),
                            rs.getString("nome"),       // no seu modelo, username = nome
                            rs.getString("email"),
                            rs.getString("senha"),
                            rs.getString("telefone")    // se o modelo User tiver telefone
                        );
                    }
                }
            } catch (SQLException e) {
                logger.error("Erro ao buscar user por id", e);
                throw new RuntimeException(e);
            }
            return null;
        }

        // Listar todos (ajustado)
        public List<User> listar() {
            String sql = "SELECT * FROM usuario";
            List<User> lista = new ArrayList<>();
            try (Connection conn = DataBaseConnection.getConnection();
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery(sql)) {

                while (rs.next()) {
                    lista.add(new User(
                        rs.getInt("id_responsavel"),
                        rs.getString("nome"),
                        rs.getString("email"),
                        rs.getString("senha"),
                        rs.getString("telefone")
                    ));
                }
            } catch (SQLException e) {
                logger.error("Erro ao listar users", e);
                throw new RuntimeException(e);
            }
            return lista;
        }

        // Atualizar usuário (ajustado)
        public User atualizar(User u) {
            String sql = """
                UPDATE usuario
                SET nome = ?, email = ?, senha = ?, telefone = ?
                WHERE id_responsavel = ?
            """;
            try (Connection conn = DataBaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

                ps.setString(1, u.getUsername());
                ps.setString(2, u.getEmail());
                ps.setString(3, u.getPassword());
                ps.setString(4, u.getTelefone());
                ps.setInt(5, u.getId());

                ps.executeUpdate();
                logger.info("User atualizado: {}", u);
                return u;
            } catch (SQLException e) {
                logger.error("Erro ao atualizar user", e);
                throw new RuntimeException(e);
            }
        }

        // Deletar usuário (ajustado)
        public boolean deletar(int id) {
            String sql = "DELETE FROM usuario WHERE id_responsavel = ?";
            try (Connection conn = DataBaseConnection.getConnection();
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
