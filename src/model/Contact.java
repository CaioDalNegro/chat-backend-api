package model;

import java.util.UUID;

public class Contact {
    private UUID id;
    private UUID id_usuarioInterno;
    private UUID id_usuarioExterno;

    public Contact(UUID id, UUID id_usuarioExterno, UUID id_usuarioInterno) {
        setId(id);
        setId_usuarioInterno(id_usuarioInterno);
        setId_usuarioExterno(id_usuarioExterno);
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getId_usuarioExterno() {
        return id_usuarioExterno;
    }

    public void setId_usuarioExterno(UUID id_usuarioExterno) {
        this.id_usuarioExterno = id_usuarioExterno;
    }

    public UUID getId_usuarioInterno() {
        return id_usuarioInterno;
    }

    public void setId_usuarioInterno(UUID id_usuarioInterno) {
        this.id_usuarioInterno = id_usuarioInterno;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "id=" + id +
                ", id_usuarioInterno=" + id_usuarioInterno +
                ", id_usuarioExterno=" + id_usuarioExterno +
                '}';
    }
}
