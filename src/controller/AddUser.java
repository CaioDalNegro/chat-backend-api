package controller;

import database.DAO.UserDAO;
import model.User;

import java.util.UUID;

public class AddUser {
    public static void main(String[] args) {
        UserDAO udao = new UserDAO();
        User u = new User(UUID.randomUUID(), "c", "d");

        boolean sucesso = udao.addUser(u);

        if (sucesso) {
            System.out.println("Usuário adicionado com sucesso!");
        } else {
            System.out.println("Erro ao adicionar o usuário.");
        }
    }
}
