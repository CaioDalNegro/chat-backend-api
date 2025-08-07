package database;

import java.net.URI;
import java.sql.Connection;
import java.sql.DriverManager;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class DBConnection {

    private static final HikariDataSource dataSource;

    static {
        HikariConfig config = new HikariConfig();

        // Configuração da URL completa
        config.setJdbcUrl("jdbc:postgresql://switchback.proxy.rlwy.net:10340/railway");
        config.setUsername("postgres");
        config.setPassword("qVQVlLRSPtdrniYBfBNsGnNsSlhYiVya");

        config.setMaximumPoolSize(10); // até 10 conexões simultâneas
        config.setConnectionTimeout(10000); // 10s máx. pra pegar uma conexão

        dataSource = new HikariDataSource(config);
    }

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}


//public class DBConnection {
//
//    public static Connection getConnection() {
//        Connection conn = null;
//
//        String databaseUrl = "postgresql://postgres:qVQVlLRSPtdrniYBfBNsGnNsSlhYiVya@switchback.proxy.rlwy.net:10340/railway";
//
//        String url = "jdbc:postgresql://localhost:5432/apichat";
//        String url = "jdbc:postgresql://us.openport.io:38884/apichat";
//        String usuario = "postgres";
//        String senha = "postgres";
//
//        try {
//            URI dbUri = new URI(databaseUrl);
//
//            String username = dbUri.getUserInfo().split(":")[0];  // postgres
//            String password = dbUri.getUserInfo().split(":")[1];  // senha
//            String host = dbUri.getHost();                        // switchback.proxy.rlwy.net
//            int port = dbUri.getPort();                           // 10340
//            String path = dbUri.getPath();                        // /railway
//
//            // Monta a URL JDBC
//            String jdbcUrl = "jdbc:postgresql://" + host + ":" + port + path;
//
//            // Conecta no banco
//            conn = DriverManager.getConnection(jdbcUrl, username, password);
//            System.out.println("✅ Conectado com sucesso ao PostgreSQL do Railway!");
//        } catch (Exception e) {
//            System.out.println("❌ Erro ao conectar: " + e.getMessage());
//            e.printStackTrace();
//        }
//
//        return conn;
//    }
//}
