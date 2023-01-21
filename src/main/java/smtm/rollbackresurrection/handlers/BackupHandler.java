package smtm.rollbackresurrection.handlers;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import smtm.rollbackresurrection.Rollbackresurrection;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;
import smtm.rollbackresurrection.controllers.RollbackController;

public class BackupHandler implements Listener {
    public BackupHandler(Rollbackresurrection plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);

        String hour = "23"; //todo: get from config
        String minute = "50";

        Timer timer = new Timer();
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(hour));
        calendar.set(Calendar.MINUTE, Integer.parseInt(minute));
        calendar.set(Calendar.SECOND, 0);

        timer.schedule(new TimerTask() {
            public void run() {
                Bukkit.broadcastMessage("Um backup está sendo iniciado!");
                RollbackController.backup(plugin, false, Bukkit.getConsoleSender());
            }
        }, calendar.getTime(), 24*60*60*1000);
    }
}


