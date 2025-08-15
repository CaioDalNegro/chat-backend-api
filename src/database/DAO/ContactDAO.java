package database.DAO;

import database.DBConnection;
import model.Contact;
import model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.UUID;

public class ContactDAO {
    private final UserDAO udao;

    public ContactDAO() {
        this.udao = new UserDAO();
    }

    public ArrayList<User> meusContatos(UUID id_usuario) {
        String sql = """
                SELECT CASE 
                    WHEN id_usuarioInterno = ? THEN id_usuarioExterno 
                    ELSE id_usuarioInterno 
                END AS contato_id
                FROM Contact
                WHERE id_usuarioInterno = ? OR id_usuarioExterno = ?
                """;

        ArrayList<User> lista = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setObject(1, id_usuario);
            stmt.setObject(2, id_usuario);
            stmt.setObject(3, id_usuario);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                UUID idContato = UUID.fromString(rs.getString("contato_id"));
                User contato = udao.PegarPorID(idContato);
                if (contato != null) {
                    lista.add(contato);
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao pegar contatos: " + e.getMessage());
        }
        return lista;
    }

    public void deleteContato(UUID idUsuario, UUID idContato) {
        String sql = "DELETE FROM Contact WHERE " +
                "(id_usuarioInterno = ? AND id_usuarioExterno = ?) " +
                "OR (id_usuarioInterno = ? AND id_usuarioExterno = ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setObject(1, idUsuario);
            stmt.setObject(2, idContato);
            stmt.setObject(3, idContato);
            stmt.setObject(4, idUsuario);

            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erro ao deletar contato: " + e.getMessage());
        }
    }

    public boolean addContato(Contact contact) {
        String sql = "INSERT INTO Contact (id_contact, id_usuarioInterno, id_usuarioExterno) VALUES (?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setObject(1, contact.getId());
            stmt.setObject(2, contact.getId_usuarioInterno());
            stmt.setObject(3, contact.getId_usuarioExterno());
            stmt.executeUpdate();

            return true;

        } catch (SQLException e) {
            System.out.println("ERRO ao tentar adicionar contato: " + e.getMessage());
            return false;
        }
    }
}