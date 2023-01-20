package smtm.rollbackresurrection;

import org.bukkit.plugin.java.JavaPlugin;
import smtm.rollbackresurrection.commands.Backup;
import smtm.rollbackresurrection.commands.Rollback;
import smtm.rollbackresurrection.handlers.BackupHandler;
import smtm.rollbackresurrection.handlers.DeathHandler;
import smtm.rollbackresurrection.handlers.MotdHandler;

public final class Rollbackresurrection extends JavaPlugin {
    private static final String version = "5";

    @Override
    public void onEnable() {
        saveDefaultConfig();
        getLogger().info("Rollbackresurrection " + version + " enabled");

        new DeathHandler(this);
        new BackupHandler(this);
        new MotdHandler(this);

        this.getCommand("backup").setExecutor(new Backup());
        this.getCommand("rollback").setExecutor(new Rollback());
    }

    @Override
    public void onDisable() {
        getLogger().info("Rollbackresurrection disabled");
    }
}
