package smtm.rollbackresurrection.handlers;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import smtm.rollbackresurrection.RollbackResurrection;
import smtm.rollbackresurrection.controllers.MortesController;
import smtm.rollbackresurrection.controllers.RollbackController;

import java.util.HashMap;

public class DeathHandler implements Listener {
    public static HashMap<String, Integer> mortes = new HashMap<>();
    public static String ultimaMorte = null;
    private final RollbackResurrection plugin;
    public DeathHandler(RollbackResurrection plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onDeath(PlayerDeathEvent evt) {
        MortesController.adicionarMorte(evt);
        if (RollbackController.isBackupRunning)
            evt.getEntity().setHealth(20.0);

        for (Player player : evt.getEntity().getWorld().getPlayers())
            player.kickPlayer(evt.getDeathMessage());

        try {
            RollbackController.rollback(plugin);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
