package smtm.rollbackresurrection.handlers;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import smtm.rollbackresurrection.RollbackResurrection;

import static smtm.rollbackresurrection.controllers.RollbackController.isBackupRunning;

public class JoinHandler implements Listener {
    public JoinHandler(RollbackResurrection plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onJoin(org.bukkit.event.player.PlayerJoinEvent evt) {
        if (isBackupRunning)
            evt.getPlayer().kickPlayer("Um backup está em andamento, aguarde alguns segundos e tente novamente.");
        evt.getPlayer().sendMessage("Mortes: " + DeathHandler.mortes.getOrDefault(evt.getPlayer().getName(), 0));
        evt.getPlayer().setDisplayName(evt.getPlayer().getName() + " (" + DeathHandler.mortes.getOrDefault(evt.getPlayer().getName(), 0) + ")");
    }
}
