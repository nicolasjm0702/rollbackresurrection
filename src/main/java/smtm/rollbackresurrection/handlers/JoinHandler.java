package smtm.rollbackresurrection.handlers;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import smtm.rollbackresurrection.Rollbackresurrection;

public class JoinHandler implements Listener {
    public JoinHandler(Rollbackresurrection plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler(priority = org.bukkit.event.EventPriority.NORMAL)
    public void onJoin(org.bukkit.event.player.PlayerJoinEvent evt) {
        evt.getPlayer().sendMessage("Mortes: " + DeathHandler.mortes.getOrDefault(evt.getPlayer().getName(), 0));
        evt.getPlayer().setDisplayName(evt.getPlayer().getName() + " (" + DeathHandler.mortes.getOrDefault(evt.getPlayer().getName(), 0) + ")");
    }
}
