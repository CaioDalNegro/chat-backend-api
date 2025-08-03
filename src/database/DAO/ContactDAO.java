package database.DAO;

import database.DBConnection;
import model.Contact;
import model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.UUID;

public class ContactDAO {
    private UserDAO udao;

    public ArrayList<User> meusContatos(UUID id_usuarioInterno){
        String sql = "select id_usuarioExterno from Contact where id_usuarioInterno = ?";
        ArrayList<User> lista = new ArrayList<>();


        try (Connection conn = DBConnection.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setObject(1, id_usuarioInterno);

            ResultSet rs = stmt.executeQuery();

            while(rs.next()){
                UUID idexterno = UUID.fromString(rs.getString("id_usuarioExterno"));
                User contato = udao.PegarPorID(idexterno);
                lista.add(contato);
            }

            return lista;

        } catch (SQLException e){
            System.out.println("ERRO ao tentar pegar meus contatos user " + e.getMessage());
            return null;
        }
    }

    public boolean remContato(UUID id_usuarioExterno) {
        String sql = "DELETE FROM Contact WHERE id_usuarioExterno = ?";

        try (Connection conn = DBConnection.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setObject(1, id_usuarioExterno);
            stmt.executeUpdate();

            return true;

        } catch (SQLException e) {
            System.out.println("ERRO ao tentar remover contato: " + e.getMessage());
            return false;
        }
    }


    public boolean addContato(Contact contact) {
        String sql = "INSERT INTO Contact (id_contact, id_usuarioInterno, id_usuarioExterno) values (?, ?, ?)";

        try (Connection conn = DBConnection.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setObject(1, contact.getId());
            stmt.setObject(2, contact.getId_usuarioInterno());
            stmt.setObject(3, contact.getId_usuarioExterno());
            stmt.executeUpdate();

            return true;

        } catch (SQLException e) {
            System.out.println("ERRO ao tentar add contato: " + e.getMessage());
            return false;
        }
    }

}