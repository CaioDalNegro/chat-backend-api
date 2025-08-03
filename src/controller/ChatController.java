package controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class ChatController {

    @FXML
    private ListView<String> messageList;

    @FXML
    private TextField messageField;


    @FXML
    private void closeApp() {
        Platform.exit(); // Fecha a aplicação
    }

    public void sendMessage() {
        String message = messageField.getText().trim();

        if (!message.isEmpty()) {
            messageList.getItems().add("Você: " + message);  // Adiciona mensagem à lista
            messageField.clear();

        }
    }
}
