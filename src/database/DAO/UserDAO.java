package database.DAO;

import database.DBConnection;
import model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.UUID;

public class UserDAO {

    public User PegarPorNome(String nome){
        String sql = "select * from Users where nome = ?";

        try (Connection conn = DBConnection.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, nome);

            ResultSet rs = stmt.executeQuery();

            User u = new User();
            u.setId(UUID.fromString(rs.getString("id_user")));
            u.setNome(rs.getString("nome"));
            u.setSenha(rs.getString("senha"));

            return u;

        } catch (SQLException e){
            System.out.println("ERRO ao tentar pegar user por nome " + e.getMessage());
            return null;
        }
    }

    public User PegarPorID(UUID id){
        String sql = "select * from Users where nome = ?";

        try (Connection conn = DBConnection.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setObject(1, id);

            ResultSet rs = stmt.executeQuery();

            User u = new User();
            u.setId(UUID.fromString(rs.getString("id_user")));
            u.setNome(rs.getString("nome"));
            u.setSenha(rs.getString("senha"));

            return u;

        } catch (SQLException e){
            System.out.println("ERRO ao tentar pegar user por nome " + e.getMessage());
            return null;
        }
    }

    public boolean remUser(UUID id){
        String sql = "DELETE FROM users where id_user = ?";

        try (Connection conn = DBConnection.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setObject(1, id);

            stmt.executeUpdate();
            return true;

        } catch (SQLException e){
            System.out.println("ERRO ao tentar apagar user " + e.getMessage());
            return false;
        }
    }

    public boolean atualizarUser(UUID id,User u){
        String sql = "UPDATE users set nome = ?, senha = ? where id_user = ?";
        try(Connection conn = DBConnection.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setObject(1, u.getNome());
            stmt.setObject(2, u.getSenha());
            stmt.setObject(3, id);

            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("ERRO ao tentar atualizar " + e.getMessage());
            return false;
        }
    }

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
            System.out.println("ERRO ao adicionar usuário: " + e.getMessage());
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

    public ArrayList<User> todosUser(){
        String sql = "select * from Users";
        ArrayList<User> lista = new ArrayList<>();


        try (Connection conn = DBConnection.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);

            ResultSet rs = stmt.executeQuery();

            while(rs.next()){
                User u = new User();
                u.setId(UUID.fromString(rs.getString("id_user")));
                u.setNome(rs.getString("nome"));
                u.setSenha(rs.getString("senha"));
                lista.add(u);
            }

            return lista;

        } catch (SQLException e){
            System.out.println("ERRO ao tentar pegar todos os user " + e.getMessage());
            return null;
        }
    }
}