// src/controller/LoginController.java
package controller;

import database.DAO.UserDAO;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import model.User;
import javafx.event.ActionEvent;
import javafx.application.Platform;


import java.io.IOException;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    private UserDAO userDAO = new UserDAO();

    private static User usuarioLogado = null;


    @FXML
    private void closeApp() {
        Platform.exit(); // Fecha a aplicação
    }

    public void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        new Thread(() -> {
            User user = userDAO.findUserByNomeAndSenha(username, password);

            Platform.runLater(() -> {
                if (user != null) {
                    showAlert("Login realizado com sucesso!");
                    usuarioLogado = user;
                    try {
                        Parent root = FXMLLoader.load(getClass().getResource("/view/chat.fxml"));
                        Stage stage = (Stage) usernameField.getScene().getWindow();
                        stage.setScene(new Scene(root));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    showAlert("Usuário ou senha incorretos.");
                }
            });
        }).start();
    }


    public void goToRegister() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/register.fxml"));
            Stage stage = (Stage) usernameField.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static User getUsuarioLogado(){
        return usuarioLogado;
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(message);
        alert.show();
    }
}