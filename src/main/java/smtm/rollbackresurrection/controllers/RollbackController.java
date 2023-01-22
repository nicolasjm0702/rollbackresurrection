package smtm.rollbackresurrection.controllers;

import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import smtm.rollbackresurrection.RollbackResurrection;

import java.io.File;
import java.io.IOException;

public class RollbackController {
    public static boolean isBackupRunning = false;
    public static void backup(RollbackResurrection plugin, final CommandSender sender) {
        RollbackController.isBackupRunning = true;
        try {
            if (!Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "save-all"))
                throw new Exception("Não foi possível salvar o mundo!");
            sender.sendMessage("Iniciando backup...");
            File pluginsFile = plugin.getDataFolder().getParentFile();
            File worldFile = pluginsFile.getParentFile();
            File worldFolder = new File(worldFile, "world");
            File backupFolder = new File(worldFile, "backup");

            if (!backupFolder.exists())
                backupFolder.mkdir();
            else
                FileUtils.cleanDirectory(backupFolder);

            FileUtils.copyDirectory(worldFolder, new File(backupFolder, "world"));

            sender.sendMessage("Backup realizado com sucesso!");
        } catch (Exception e) {
            sender.sendMessage("Backup falhou! Verifique o console para mais informações.");
            e.printStackTrace();
        } finally {
            RollbackController.isBackupRunning = false;
        }
    }

    public static void rollback(RollbackResurrection plugin) throws IOException {
        String script = plugin.getDataFolder().getParentFile() + "/backup.sh";
        Runtime.getRuntime().exec(script);
        Bukkit.getServer().shutdown();
    }
}
