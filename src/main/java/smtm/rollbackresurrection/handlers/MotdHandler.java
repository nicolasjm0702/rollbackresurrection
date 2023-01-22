package smtm.rollbackresurrection.handlers;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;
import smtm.rollbackresurrection.RollbackResurrection;

public class MotdHandler implements Listener {
    RollbackResurrection plugin;
    public MotdHandler(RollbackResurrection plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onMotd(ServerListPingEvent evt) {
        String motd = "Nenhuma morte registrada\n(Ainda)";

        if (DeathHandler.ultimaMorte != null)
            motd = "Ultima morte:\n" + DeathHandler.ultimaMorte;

        evt.setMotd(motd);
        evt.setMaxPlayers(Integer.MAX_VALUE);
    }
}
