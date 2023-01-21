package smtm.rollbackresurrection.handlers;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import smtm.rollbackresurrection.Rollbackresurrection;

public class MotdHandler implements Listener {
    Rollbackresurrection plugin;
    public MotdHandler(Rollbackresurrection plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        this.plugin = plugin;
    }

    @EventHandler(priority = org.bukkit.event.EventPriority.NORMAL)
    public void onMotd(org.bukkit.event.server.ServerListPingEvent evt) {
        String motd = "Nenhuma morte registrada\n(Ainda)";

        if (DeathHandler.ultimaMorte != null)
            motd = "Ultima morte:\n" + DeathHandler.ultimaMorte;

        evt.setMotd(motd);
    }
}
