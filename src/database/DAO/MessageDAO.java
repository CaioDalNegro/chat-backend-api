package database.DAO;

import database.DBConnection;
import model.Message;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

public class MessageDAO {


    public boolean addMsg(Message msg){
        String sql = "INSERT INTO Message (id_message, id_destinatario, id_remetente, descricao, datahora) values (?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setObject(1, msg.getId());
            stmt.setObject(2, msg.getDestinatario());
            stmt.setObject(3, msg.getRemetente());
            stmt.setObject(4, msg.getDescricao());
            stmt.setObject(5, msg.getDatahora());
            stmt.executeUpdate();

            return true;

        } catch (SQLException e) {
            System.out.println("ERRO ao tentar add msg: " + e.getMessage());
            return false;
        }
    }

    public boolean remMsg(UUID id){
        String sql = "DELETAR FROM Message where id_message = ?";

        try (Connection conn = DBConnection.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setObject(1, id);
            stmt.executeUpdate();

            return true;

        } catch (SQLException e) {
            System.out.println("ERRO ao apagar msg: " + e.getMessage());
            return false;
        }
    }

    public boolean atualizarMsg(Message msg, UUID id){
        String sql = "UPDATE Message set descricao = ?, datahora = ? where id_message = ?";
        try(Connection conn = DBConnection.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setObject(1, msg.getDescricao());
            stmt.setObject(2, msg.getDatahora());
            stmt.setObject(3, id);

            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("ERRO ao tentar atualizar " + e.getMessage());
            return false;
        }
    }

    public ArrayList<Message> getMensagensPorUsuario(UUID idUsuario, UUID id_Contatoexterno) {
        String sql = "SELECT * FROM message WHERE id_remetente = ? or id_destinatario = ?";
        ArrayList<Message> mensagens = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setObject(1, idUsuario);
            stmt.setObject(2, id_Contatoexterno);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Message msg = new Message();
                msg.setId(UUID.fromString(rs.getString("id_message")));
                msg.setDescricao(rs.getString("descricao"));
                msg.setRemetente(UUID.fromString(rs.getString("id_remetente")));
                msg.setDestinatario(UUID.fromString(rs.getString("id_destinatario")));
                msg.setDatahora(rs.getTimestamp("datahora").toLocalDateTime());

                mensagens.add(msg);
            }

            return mensagens;
        } catch (SQLException e) {
            System.out.println("ERRO ao buscar mensagens: " + e.getMessage());
            return null;
        }
    }

}