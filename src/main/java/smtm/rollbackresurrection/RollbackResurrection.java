package smtm.rollbackresurrection;

import org.bukkit.plugin.java.JavaPlugin;
import smtm.rollbackresurrection.commands.BackupCommand;
import smtm.rollbackresurrection.commands.ReloadCommand;
import smtm.rollbackresurrection.commands.RollbackCommand;
import smtm.rollbackresurrection.commands.SudoCommand;
import smtm.rollbackresurrection.controllers.ConfigController;
import smtm.rollbackresurrection.controllers.MortesController;
import smtm.rollbackresurrection.handlers.*;

public final class RollbackResurrection extends JavaPlugin {
    private static final String version = "1.12.2_v1";

    @Override
    public void onEnable() {
        saveDefaultConfig();
        ConfigController.carregarConfiguracoes(this);

        try {
            MortesController.criarTabelas();
            DeathHandler.mortes = MortesController.carregarMortes();
            DeathHandler.ultimaMorte = MortesController.lerUltimaMorte();
        } catch (Exception e) {
            e.printStackTrace();
        }

        new DeathHandler(this);
        new BackupHandler(this);
        new MotdHandler(this);
        new JoinHandler(this);

        this.getCommand("backup").setExecutor(new BackupCommand());
        this.getCommand("rollback").setExecutor(new RollbackCommand());
        this.getCommand("sudo").setExecutor(new SudoCommand());
        this.getCommand("reload").setExecutor(new ReloadCommand());

        getLogger().info("Rollbackresurrection " + version + " enabled");
    }

    @Override
    public void onDisable() {
        getLogger().info("Rollbackresurrection disabled");
    }
}
