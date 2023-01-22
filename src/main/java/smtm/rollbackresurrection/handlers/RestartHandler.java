package smtm.rollbackresurrection.handlers;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import smtm.rollbackresurrection.RollbackResurrection;

public class RestartHandler implements Listener {
    public RestartHandler(RollbackResurrection plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onRestart(org.bukkit.event.player.PlayerCommandPreprocessEvent evt) {
        if (evt.getMessage().equalsIgnoreCase("/restart")) {
            if (!evt.getPlayer().isOp())
                return;
            evt.setCancelled(true);
            Bukkit.getServer().shutdown();
        }
    }
}
