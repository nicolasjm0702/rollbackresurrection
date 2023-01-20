package smtm.rollbackresurrection.handlers;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import smtm.rollbackresurrection.Rollbackresurrection;

public class DeathHandler implements Listener {
    private final Rollbackresurrection plugin;
    public DeathHandler(Rollbackresurrection plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onDeath(org.bukkit.event.entity.PlayerDeathEvent evt) {
        try {
            java.io.File file = new java.io.File(plugin.getDataFolder(), "lastdeath.tmp");
            if (!file.exists())
                file.createNewFile();

            java.io.FileWriter fw = new java.io.FileWriter(file, false);
            fw.write(evt.getDeathMessage());
            fw.close();
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }

        new Thread(() -> {
            try {
                for (org.bukkit.entity.Player player : evt.getEntity().getWorld().getPlayers()) {
                    player.kickPlayer(evt.getDeathMessage());
                }
                if (BackupHandler.isBackupRunning) {
                    plugin.getServer().setWhitelist(true);
                    while (BackupHandler.isBackupRunning)
                        Thread.sleep(1000);
                    plugin.getServer().setWhitelist(false);
                }
                BackupHandler.rollback(plugin);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}
