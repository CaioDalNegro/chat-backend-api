package controller;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class ChatController {

    @FXML
    private ListView<String> messageList;

    @FXML
    private TextField messageField;

    public void sendMessage() {
        String message = messageField.getText();
        if (!message.isEmpty()) {
            messageList.getItems().add("Você: " + message);  // Exibe na lista
            messageField.clear();

            // Aqui você pode adicionar a lógica para enviar via socket usando ChatClient
            // Exemplo:
            // ChatClient.sendMessage("IP_DO_DESTINO", PORTA, message);
        }
    }
}
