package smtm.rollbackresurrection;

import org.bukkit.plugin.java.JavaPlugin;
import smtm.rollbackresurrection.commands.Backup;
import smtm.rollbackresurrection.commands.Rollback;
import smtm.rollbackresurrection.controllers.FileController;
import smtm.rollbackresurrection.handlers.BackupHandler;
import smtm.rollbackresurrection.handlers.DeathHandler;
import smtm.rollbackresurrection.handlers.JoinHandler;
import smtm.rollbackresurrection.handlers.MotdHandler;

public final class Rollbackresurrection extends JavaPlugin {
    private static final String version = "5";

    @Override
    public void onEnable() {
        saveDefaultConfig();

        try {
            DeathHandler.mortes = FileController.carregarMortes(this);
            DeathHandler.ultimaMorte = FileController.lerUltimaMorte(this);
        } catch (Exception e) {
            e.printStackTrace();
        }

        new DeathHandler(this);
        new BackupHandler(this);
        new MotdHandler(this);
        new JoinHandler(this);

        this.getCommand("backup").setExecutor(new Backup());
        this.getCommand("rollback").setExecutor(new Rollback());

        getServer().setWhitelist(false);
        getLogger().info("Rollbackresurrection " + version + " enabled");
    }

    @Override
    public void onDisable() {
        getLogger().info("Rollbackresurrection disabled");
    }
}
