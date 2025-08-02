package controller;

import database.DAO.UserDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import model.User;

import java.io.IOException;
import java.util.UUID;

public class RegisterController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField confirmPasswordField;

    UserDAO udao = new UserDAO();

    public void handleRegister() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String confirm = confirmPasswordField.getText();

        if (!password.equals(confirm)) {
            showAlert("As senhas não coincidem!");
        } else if (username.isEmpty() || password.isEmpty()) {
            showAlert("Preencha todos os campos.");
        } else {
            // Simula cadastro (depois ligar com UserDAO)
            showAlert("Usuário registrado com sucesso!");
            if(udao.addUser(new User(UUID.randomUUID(), username, password))){
                showAlert("usuario add no banco");
            }
            else {
                showAlert("usuario nao foi add no banco");
            }
        }
    }

    public void goToLogin() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/login.fxml"));
            Stage stage = (Stage) usernameField.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(msg);
        alert.show();
    }
}
