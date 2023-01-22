package smtm.rollbackresurrection.controllers;

import org.bukkit.Bukkit;
import org.bukkit.event.entity.PlayerDeathEvent;
import smtm.rollbackresurrection.RollbackResurrection;

import java.io.*;
import java.sql.*;
import java.util.HashMap;

public class MortesController {
    private static ConexaoSQLite conexaoSQL = null;

    public static void criarTabelas() {
        conexaoSQL = new ConexaoSQLite("data.db");
        try {
            conexaoSQL.executarQueryUpdate(
                    "CREATE TABLE IF NOT EXISTS deaths (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "player VARCHAR(32) NOT NULL," +
                    "death_message VARCHAR(128) NOT NULL," +
                    "death_time DATETIME DEFAULT CURRENT_TIMESTAMP" +
                    ")"
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void adicionarMorte(PlayerDeathEvent evt) {
        try {
            conexaoSQL.executarQueryUpdate(
                    "INSERT INTO deaths (" +
                    "player, death_message" +
                    ") VALUES (" +
                    "'" + evt.getEntity().getName() + "', '" + evt.getDeathMessage() + "')"
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static HashMap<String, Integer> carregarMortes() {
        HashMap<String, Integer> mortes = new HashMap<>();
        try {
            ResultSet rs = conexaoSQL.executarQuery("SELECT player, COUNT(*) AS total FROM deaths GROUP BY player");
            while (rs.next())
                mortes.put(rs.getString("player"), rs.getInt("total"));
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Bukkit.getLogger().warning("MORTES: " + mortes.toString());
        return mortes;
    }

    public static String lerUltimaMorte() {
        String ultimaMorte = null;
        try {
            ResultSet rs = conexaoSQL.executarQuery("SELECT death_message FROM deaths ORDER BY death_time DESC LIMIT 1");
            if (rs.next())
                ultimaMorte = rs.getString("death_message");
            rs.close();
            Bukkit.getLogger().warning("ULTIMA MORTE: " + ultimaMorte);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ultimaMorte;
    }
}

class ConexaoSQLite {
    private Connection conexao;
    public ConexaoSQLite(String nomeArquivo) {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        criarConexao(nomeArquivo);
    }
    private void criarConexao(String arquivo) {
        try {
            String url = "jdbc:sqlite:"
                    + RollbackResurrection.getPlugin(RollbackResurrection.class).getDataFolder()
                    + "/" + arquivo;
            conexao = DriverManager.getConnection(url);
            Bukkit.getLogger().info(conexao != null ?
                    "Conexão com o banco de dados estabelecida."
                    : "Não foi possível estabelecer conexão com o banco de dados."
            );
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public ResultSet executarQuery(String query) throws SQLException {
        Statement stmt = conexao.createStatement();
        return stmt.executeQuery(query);
    }
    public void executarQueryUpdate(String query) throws SQLException {
        Statement stmt = conexao.createStatement();
        stmt.executeUpdate(query);
        stmt.close();
    }
}

