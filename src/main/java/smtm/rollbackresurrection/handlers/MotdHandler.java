package smtm.rollbackresurrection.handlers;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import smtm.rollbackresurrection.Rollbackresurrection;

import java.io.IOException;

public class MotdHandler implements Listener {
    public MotdHandler(Rollbackresurrection plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler(priority = org.bukkit.event.EventPriority.NORMAL)
    public void onMotd(org.bukkit.event.server.ServerListPingEvent evt) {
        String motd = "Nenhuma morte registrada\n(Ainda)";
        try {
            java.io.File file = new java.io.File(Rollbackresurrection.getPlugin(Rollbackresurrection.class).getDataFolder(), "lastdeath.tmp");
            if (!file.exists()) {
                file.createNewFile();
                throw new Exception("Nenhuma morte registrada");
            }
            java.io.FileReader fr = new java.io.FileReader(file);
            java.io.BufferedReader br = new java.io.BufferedReader(fr);
            String line = br.readLine();
            br.close();
            fr.close();
            if (line == null || line.isEmpty())
                throw new Exception("Nenhuma morte registrada");
            motd = "Ultima morte:\n" + line;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            Bukkit.getLogger().info(e.getMessage());
        } finally {
            evt.setMotd(motd);
            evt.setMaxPlayers(Integer.MAX_VALUE);
        }
    }
}
