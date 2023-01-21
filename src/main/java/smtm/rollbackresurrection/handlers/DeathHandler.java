package smtm.rollbackresurrection.handlers;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import smtm.rollbackresurrection.Rollbackresurrection;
import smtm.rollbackresurrection.controllers.FileController;
import smtm.rollbackresurrection.controllers.RollbackController;

import java.util.HashMap;

public class DeathHandler implements Listener {
    public static HashMap<String, Integer> mortes = new HashMap<>();
    public static String ultimaMorte = null;
    private final Rollbackresurrection plugin;
    public DeathHandler(Rollbackresurrection plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    @SuppressWarnings("BusyWait")
    public void onDeath(org.bukkit.event.entity.PlayerDeathEvent evt) {
        FileController.adicionarMorte(evt, plugin);
        if (RollbackController.isBackupRunning) {
            evt.getEntity().setHealth(20.0);
            plugin.getServer().setWhitelist(true);
        }

        for (org.bukkit.entity.Player player : evt.getEntity().getWorld().getPlayers())
            player.kickPlayer(evt.getDeathMessage());

        try {
            RollbackController.rollback(plugin);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
