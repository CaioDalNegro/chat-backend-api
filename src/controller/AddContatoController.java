package controller;

import database.DAO.ContactDAO;
import database.DAO.MessageDAO;
import database.DAO.UserDAO;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Contact;
import model.Message;
import model.User;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;
import java.time.LocalDateTime;

import javax.swing.*;

public class AddContatoController {
    @FXML
    private AnchorPane rootPane;

    private double xOffset = 0;
    private double yOffset = 0;

    @FXML
    private TextField campoNome;

    private UserDAO udao = new UserDAO();

    private ContactDAO cdao = new ContactDAO();

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

    public void nomeDoContato(){
        String nome = campoNome.getText();
        User u = udao.PegarPorNome(nome);

        if(u == null){
            showAlert("nao encontrado!");
        } else {
            Contact c = new Contact(UUID.randomUUID(), u.getId(), usuarioLogado().getId());
            cdao.addContato(c);
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/view/chat.fxml"));

                Stage stage = (Stage) rootPane.getScene().getWindow();

                stage.setScene(new Scene(root));
                stage.show();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }



    }

    public void voltar(){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/chat.fxml"));

            Stage stage = (Stage) rootPane.getScene().getWindow();

            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private User usuarioLogado() {
        return LoginController.getUsuarioLogado();
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(message);
        alert.show();
    }
}
