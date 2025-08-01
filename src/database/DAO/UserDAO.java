package database.DAO;

import database.DBConnection;
import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserDAO {
    public boolean addUser(User user){
        String sql = "INSERT into Users VALUES (id_user, nome, senha) values (?, ?, ?)";

        try(Connection conn = DBConnection.getConnection()){
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setObject(1, user.getId());
            stmt.setString(2, user.getNome());
            stmt.setString(3, user.getSenha());

            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println(("Erro ao tentar add usuario " + user.getNome() + e.getMessage()));
            return false;
        }
    }

}
