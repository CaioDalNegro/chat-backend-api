package controller;

import database.DAO.MessageDAO;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class ChatController {

    @FXML
    private AnchorPane rootPane;

    @FXML
    private TextField messageField;

    @FXML
    private VBox messageContainer;

    @FXML
    private ScrollPane chatScroll;

    private double xOffset = 0;
    private double yOffset = 0;

    private MessageDAO MDAO;

    @FXML
    public void initialize() {
        rootPane.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });

        rootPane.setOnMouseDragged(event -> {
            Stage stage = (Stage) rootPane.getScene().getWindow();
            stage.setX(event.getScreenX() - xOffset);
            stage.setY(event.getScreenY() - yOffset);
        });
    }

    @FXML
    private void closeApp() {
        Platform.exit();
    }

    @FXML
    public void sendMessage() {
        String message = messageField.getText().trim();
        if (!message.isEmpty()) {
            addMessageBubble("Você", message, true);
            messageField.clear();
            scrollToBottom();
        }
    }

    private void addMessageBubble(String sender, String text, boolean outgoing) {
        HBox messageBox = new HBox();
        messageBox.setSpacing(10);
        messageBox.setPadding(new Insets(5));

        ImageView avatar = new ImageView(new Image(getClass().getResourceAsStream("/resources/images/perfil.png")));
        avatar.setFitHeight(30);
        avatar.setFitWidth(30);

        Label messageLabel = new Label(text);
        messageLabel.setWrapText(true);
        messageLabel.setStyle(outgoing
                ? "-fx-background-color: #6747CD; -fx-text-fill: white; -fx-padding: 10; -fx-background-radius: 10;"
                : "-fx-background-color: #E0E0E0; -fx-text-fill: black; -fx-padding: 10; -fx-background-radius: 10;");

        if (outgoing) {
            messageBox.getChildren().addAll(messageLabel, avatar);
            messageBox.setStyle("-fx-alignment: CENTER_RIGHT;");
        } else {
            messageBox.getChildren().addAll(avatar, messageLabel);
            messageBox.setStyle("-fx-alignment: CENTER_LEFT;");
        }

        messageContainer.getChildren().add(messageBox);
    }

    private void scrollToBottom() {
        Platform.runLater(() -> chatScroll.setVvalue(1.0));
    }
}