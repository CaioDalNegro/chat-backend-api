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
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Message;
import model.User;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;
import java.time.LocalDateTime;

public class ChatController {

    @FXML
    private AnchorPane rootPane;

    @FXML
    private TextField messageField;

    @FXML
    private VBox contatosContainer;


    @FXML
    private VBox messageContainer;

    @FXML
    private ScrollPane chatScroll;

    private double xOffset = 0;
    private double yOffset = 0;

    private MessageDAO MDAO = new MessageDAO();
    private ContactDAO cDao = new ContactDAO();
    private UserDAO uDao = new UserDAO();

    private User contatoExternoAtual;

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
        mostrarContatos();
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
        if (text == null || text.trim().isEmpty()) {
            System.out.println("Mensagem vazia. Nada foi enviado.");
            return;
        }

        if (usuarioLogado() == null || GetContatoAtualExterno() == null) {
            System.out.println("Usuário logado ou contato atual está nulo.");
            return;
        }

        HBox messageBox = new HBox();
        messageBox.setSpacing(10);
        messageBox.setPadding(new Insets(5));

        ImageView avatar;
        InputStream imageStream = getClass().getResourceAsStream("/resources/images/perfil.png");
        if (imageStream != null) {
            avatar = new ImageView(new Image(imageStream));
        } else {
            System.out.println("Imagem de avatar não encontrada. Usando vazio.");
            avatar = new ImageView(); // fallback vazio
        }

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

        // Criação da mensagem
        UUID idMensagem = UUID.randomUUID();
        UUID idRemetente = usuarioLogado().getId();
        UUID idDestinatario = GetContatoAtualExterno().getId();
        LocalDateTime timestamp = LocalDateTime.now();

        Message novaMensagem = new Message(text, idDestinatario, idRemetente, timestamp, idMensagem);

        // Salvando no banco
        try {
            MDAO.addMsg(novaMensagem);
            System.out.println("Mensagem enviada com sucesso: " + text);
        } catch (Exception e) {
            System.out.println("Erro ao salvar mensagem no banco: " + e.getMessage());
            e.printStackTrace();
        }

        // Exibe na interface
        messageContainer.getChildren().add(messageBox);
    }

    public void mostrarContatos() {
        ArrayList<User> contatos = meusContatos();
        contatosContainer.getChildren().clear();
        if(contatos.size() > 0){

            for (User contato : contatos) {
                HBox contatosBox = new HBox();
                contatosBox.setSpacing(10);
                contatosBox.setPadding(new Insets(5));

                ImageView avatar = new ImageView(new Image(getClass().getResourceAsStream("/resources/images/perfil.png")));

                avatar.setFitHeight(50);
                avatar.setFitWidth(50);

                Label nomeLabel = new Label(contato.getNome());
                nomeLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: white; -fx-font-size: 16;");

                contatosBox.getChildren().addAll(avatar, nomeLabel);

                contatosBox.setOnMouseClicked(event -> {
                    System.out.println("clicou " + contato.getNome());
                    mostrarMinhasMsgContatoClicado(contato.getId());
                    SetContatoAtualExterno(contato);
                });

                contatosContainer.getChildren().add(contatosBox);
            }
        } else {
            HBox contatosBox = new HBox();
            contatosBox.setSpacing(10);
            contatosBox.setPadding(new Insets(5));
            Label nomeLabel = new Label("Voce nao tem contatos crie um contato");
            nomeLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: white; -fx-font-size: 16;");
            contatosBox.getChildren().addAll(nomeLabel);

            contatosContainer.getChildren().add(contatosBox);
        }
    }

    public void mostrarMinhasMsgContatoClicado(UUID id_Contatoexterno){
        ArrayList<Message> minhasMsgsComEsteContato = minhasMsgContatoClicado(id_Contatoexterno);

        messageContainer.getChildren().clear(); // LIMPA as msgs anteriores

        if (minhasMsgsComEsteContato.size() > 0) {
            for (Message m : minhasMsgsComEsteContato) {
                String text = m.getDescricao();
                UUID idRemetente = m.getRemetente();
                boolean outgoing = idRemetente.equals(usuarioLogado().getId()); // 👈 define se você enviou

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
        } else {
            HBox emptyBox = new HBox();
            emptyBox.setSpacing(10);
            emptyBox.setPadding(new Insets(5));

            Label emptyLabel = new Label("Você ainda não enviou nenhuma mensagem. Bora puxar assunto!");
            emptyLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: white; -fx-font-size: 16;");
            emptyBox.getChildren().add(emptyLabel);

            messageContainer.getChildren().add(emptyBox);
        }

        scrollToBottom();
    }


    public ArrayList<User> meusContatos(){
        return cDao.meusContatos(usuarioLogado().getId());
    }


    private ArrayList<Message> minhasMsgContatoClicado(UUID id_Contatoexterno){
        return MDAO.getMensagensPorUsuario(usuarioLogado().getId(), id_Contatoexterno);
    }

    private User usuarioLogado(){
        return LoginController.getUsuarioLogado();
    }

    //A IDEIA E SEMPRE TER COM QUE VC TA CONVERSANDO AGORA
    private User SetContatoAtualExterno(User contatoExternoAtual){
         return this.contatoExternoAtual = contatoExternoAtual;
    }

    private User GetContatoAtualExterno(){
        return this.contatoExternoAtual;
    }

    private void scrollToBottom() {
        Platform.runLater(() -> chatScroll.setVvalue(1.0));
    }
}