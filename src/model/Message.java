package model;

import java.security.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

public class Message {
    private UUID id;
    private UUID id_destinatario;
    private UUID id_remetente;
    private String descricao;
    private LocalDateTime datahora;


    public Message(String descricao, UUID id_destinatario, UUID id_remetente, LocalDateTime datahora, UUID id) {
        setDescricao(descricao);
        setDestinatario(id_destinatario);
        setRemetente(id_remetente);
        setDatahora(datahora);
        setId(id);
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public UUID getDestinatario() {
        return id_destinatario;
    }

    public void setDestinatario(UUID destinatario) {
        this.id_destinatario = destinatario;
    }

    public UUID getRemetente() {
        return id_remetente;
    }

    public void setRemetente(UUID remetente) {
        this.id_remetente = remetente;
    }

    public LocalDateTime getDatahora() {
        return datahora;
    }

    public void setDatahora(LocalDateTime datahora) {
        this.datahora = datahora;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Message{" +
                ", id=" + id +
                "datahora=" + datahora +
                ", destinatario=" + id_destinatario +
                ", remetente=" + id_remetente +
                ", descricao='" + descricao + '\'' +

                '}';
    }
}
