package smtm.rollbackresurrection.controllers;

import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import smtm.rollbackresurrection.Rollbackresurrection;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipOutputStream;

public class RollbackController {
    public static boolean isBackupRunning = false;
    public static void backup(Rollbackresurrection plugin, boolean zipFile, final CommandSender sender) {
        RollbackController.isBackupRunning = true;
        new Thread(() -> {
            try {
                if (!Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "save-all"))
                    throw new Exception("Não foi possível salvar o mundo!");
                sender.sendMessage("Mundo salvo, iniciando backup em 10s...");
                Thread.sleep(10000);
                sender.sendMessage("Iniciando backup...");
                File pluginsFile = plugin.getDataFolder().getParentFile();
                File worldFile = pluginsFile.getParentFile();
                File worldFolder = new File(worldFile, "world");
                File backupFolder = new File(worldFile, "backup");

                if (!backupFolder.exists())
                    backupFolder.mkdir();
                else
                    FileUtils.cleanDirectory(backupFolder);

                if (zipFile) {
                    FileOutputStream fos = new FileOutputStream(new File(backupFolder, "world.zip"));
                    ZipOutputStream zos = new ZipOutputStream(fos);

                    FileController.addDirToZipArchive(zos, worldFolder, "");

                    zos.close();
                    fos.close();
                } else {
                    FileUtils.copyDirectory(worldFolder, new File(backupFolder, "world"));
                }

                sender.sendMessage("Backup realizado com sucesso!");
            } catch (Exception e) {
                sender.sendMessage("Backup falhou! Verifique o console para mais informações.");
                e.printStackTrace();
            } finally {
                RollbackController.isBackupRunning = false;
            }
        }).start();
    }

    public static void rollback(Rollbackresurrection plugin) throws IOException {
        String script = plugin.getDataFolder().getParentFile() + "/backup.sh";
        Runtime.getRuntime().exec(script);
        Bukkit.getServer().shutdown();
    }
}
