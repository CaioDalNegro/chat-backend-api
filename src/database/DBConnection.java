package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    public static Connection getConnection(){
        Connection conn = null;
        String url = "jdbc:postgresql://localhost:5432/apichat";
        String usuario = "postgres";
        String senha = "postgres";

        try {
            conn = DriverManager.getConnection(url, usuario, senha);
            System.out.println("Conectado com SUCESSO");
        }catch (SQLException e){
            System.out.println("ERRO na hora de conectar " + e.getMessage());
        }

        return conn;
    }

}
