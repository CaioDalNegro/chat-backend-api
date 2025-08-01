package controller;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;

public class RegisterController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField confirmPasswordField;

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
        }
    }

    private void showAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(msg);
        alert.show();
    }
}
