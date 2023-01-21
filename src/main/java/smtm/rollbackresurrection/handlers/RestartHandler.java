package smtm.rollbackresurrection.handlers;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import smtm.rollbackresurrection.Rollbackresurrection;

public class RestartHandler implements Listener {
    public RestartHandler(Rollbackresurrection plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler(priority = org.bukkit.event.EventPriority.NORMAL)
    public void onRestart(org.bukkit.event.player.PlayerCommandPreprocessEvent evt) {
        if (!evt.getPlayer().isOp())
            return;
        if (evt.getMessage().equalsIgnoreCase("/restart")) {
            evt.setCancelled(true);
            Bukkit.getServer().shutdown();
        }
    }
}
