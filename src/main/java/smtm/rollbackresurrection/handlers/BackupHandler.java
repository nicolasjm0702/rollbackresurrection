package smtm.rollbackresurrection.handlers;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import smtm.rollbackresurrection.RollbackResurrection;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import smtm.rollbackresurrection.controllers.ConfigController;
import smtm.rollbackresurrection.controllers.RollbackController;

public class BackupHandler implements Listener {
    @SuppressWarnings("unchecked")
    public BackupHandler(RollbackResurrection plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        ArrayList<String> backupTimes = (ArrayList<String>) ConfigController.getValue("config.yml", "backup.times");
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(backupTimes.size());

        for (String backupTime : backupTimes) {
            Bukkit.getLogger().info("Backup time: " + backupTime);
            String[] parts = backupTime.split(":");
            int hour = Integer.parseInt(parts[0]);
            int minute = Integer.parseInt(parts[1]);
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, hour);
            calendar.set(Calendar.MINUTE, minute);
            calendar.set(Calendar.SECOND, 0);

            scheduler.scheduleAtFixedRate(() -> {
                Bukkit.broadcastMessage("Um backup está sendo iniciado!");
                Bukkit.getLogger().warning("Um backup está sendo iniciado!");
                RollbackController.backup(plugin, Bukkit.getConsoleSender());
            }, calculateDelay(calendar), 24*60*60, TimeUnit.SECONDS);
        }
    }

    private long calculateDelay(Calendar calendar) {
        Calendar now = Calendar.getInstance();
        long delay = calendar.getTimeInMillis() - now.getTimeInMillis();
        if (delay < 0) {
            delay += 24*60*60*1000;
        }
        return delay;
    }

}


