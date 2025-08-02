package database.DAO;

import database.DBConnection;
import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class UserDAO {

    public boolean addUser(User user) {
        String sql = "INSERT INTO users (id_user, nome, senha) VALUES (?, ?, ?)";

        try (Connection conn = DBConnection.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setObject(1, user.getId());
            stmt.setString(2, user.getNome());
            stmt.setString(3, user.getSenha());

            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Erro ao adicionar usuário: " + e.getMessage());
            return false;
        }
    }

    public User findUserByNomeAndSenha(String nome, String senha) {
        String sql = "SELECT * FROM users WHERE nome = ? AND senha = ?";

        try (Connection conn = DBConnection.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, nome);
            stmt.setString(2, senha);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                UUID id = UUID.fromString(rs.getString("id_user"));
                String nomeDb = rs.getString("nome");
                String senhaDb = rs.getString("senha");

                return new User(id, nomeDb, senhaDb);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar usuário: " + e.getMessage());
        }

        return null;
    }
}
