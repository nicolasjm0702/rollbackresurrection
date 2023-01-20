package smtm.rollbackresurrection.handlers;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Listener;
import smtm.rollbackresurrection.Rollbackresurrection;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;
import org.apache.commons.io.FileUtils;

public class BackupHandler implements Listener {
    public static boolean isBackupRunning = false;
    public BackupHandler(Rollbackresurrection plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);

        String hour = "23";
        String minute = "50";

        Timer timer = new Timer();
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(hour));
        calendar.set(Calendar.MINUTE, Integer.parseInt(minute));
        calendar.set(Calendar.SECOND, 0);

        timer.schedule(new TimerTask() {
            public void run() {
                Bukkit.broadcastMessage("Um backup está sendo iniciado!");
                backup(plugin, false, Bukkit.getConsoleSender());
            }
        }, calendar.getTime(), 24*60*60*1000);
    }

    public static void backup(Rollbackresurrection plugin, boolean zipFile, final CommandSender sender) {
        isBackupRunning = true;
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

                    addDirToZipArchive(zos, worldFolder, "");

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
                isBackupRunning = false;
            }
        }).start();
    }

    public static void rollback(Rollbackresurrection plugin) throws IOException {
        String script = plugin.getDataFolder().getParentFile() + "/backup.sh";
        Runtime.getRuntime().exec(script);
        Bukkit.getServer().shutdown();
    }

    private static void addDirToZipArchive(ZipOutputStream zos, File fileToZip, String parentDirectoryName) throws Exception {
        if (fileToZip == null || !fileToZip.exists()) {
            return;
        }

        String zipEntryName = fileToZip.getName();
        if (parentDirectoryName!=null && !parentDirectoryName.isEmpty()) {
            zipEntryName = parentDirectoryName + "/" + fileToZip.getName();
        }

        if (fileToZip.isDirectory()) {
            for (File file : fileToZip.listFiles()) {
                addDirToZipArchive(zos, file, zipEntryName);
            }
        } else {
            byte[] buffer = new byte[1024];
            FileInputStream fis = new FileInputStream(fileToZip);
            zos.putNextEntry(new ZipEntry(zipEntryName));
            int length;
            while ((length = fis.read(buffer)) > 0) {
                zos.write(buffer, 0, length);
            }
            zos.closeEntry();
            fis.close();
        }
    }
}


