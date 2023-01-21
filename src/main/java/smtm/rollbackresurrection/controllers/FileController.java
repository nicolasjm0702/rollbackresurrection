package smtm.rollbackresurrection.controllers;

import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.plugin.java.JavaPlugin;
import smtm.rollbackresurrection.Rollbackresurrection;

import java.io.*;
import java.util.HashMap;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class FileController {
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static void salvarUltimaMorte(PlayerDeathEvent evt, JavaPlugin plugin) throws IOException {
        File file = new File(plugin.getDataFolder(), "lastdeath.tmp");
        if (!file.exists())
            file.createNewFile();

        FileWriter fw = new FileWriter(file, false);
        fw.write(evt.getDeathMessage());
        fw.close();
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static String lerUltimaMorte(JavaPlugin plugin) throws IOException {
        File file = new File(plugin.getDataFolder(), "lastdeath.tmp");
        if (!file.exists()) {
            file.createNewFile();
            return null;
        }
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        String line = br.readLine();
        br.close();
        fr.close();
        if (line == null || line.isEmpty())
            return null;
        else
            return line;
    }

    public static void addDirToZipArchive(ZipOutputStream zos, File fileToZip, String parentDirectoryName) throws Exception {
        if (fileToZip == null || !fileToZip.exists()) {
            return;
        }

        String zipEntryName = fileToZip.getName();
        if (parentDirectoryName!=null && !parentDirectoryName.isEmpty()) {
            zipEntryName = parentDirectoryName + "/" + fileToZip.getName();
        }

        if (fileToZip.isDirectory()) {
            for (File file : Objects.requireNonNull(fileToZip.listFiles())) {
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

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static void adicionarMorte(PlayerDeathEvent evt, Rollbackresurrection plugin) {
        HashMap<String, Integer> mortes = new HashMap<>();

        try {
            salvarUltimaMorte(evt, plugin);
        } catch (IOException e) {
            e.printStackTrace();
        }

        File file = new File(Rollbackresurrection.getPlugin(Rollbackresurrection.class).getDataFolder(), "deaths.txt");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line;
            while ((line = br.readLine()) != null) {
                String[] split = line.split(":");
                mortes.put(split[0], Integer.parseInt(split[1]));
            }
            br.close();
            fr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (!mortes.containsKey(evt.getEntity().getName())) {
            mortes.put(evt.getEntity().getName(), 1);
            try {
                FileWriter fw = new FileWriter(file, true);
                fw.write(evt.getEntity().getName() + ":1");
                fw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            mortes.put(evt.getEntity().getName(), mortes.get(evt.getEntity().getName()) + 1);
            try {
                FileWriter fw = new FileWriter(file, false);
                for (String key : mortes.keySet()) {
                    fw.write(key + ":" + mortes.get(key) + "\n");
                }
                fw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static HashMap<String, Integer> carregarMortes(Rollbackresurrection plugin) {
        HashMap<String, Integer> mortes = new HashMap<>();

        File file = new File(plugin.getDataFolder(), "deaths.txt");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line;
            while ((line = br.readLine()) != null) {
                String[] split = line.split(":");
                mortes.put(split[0], Integer.parseInt(split[1]));
            }
            br.close();
            fr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return mortes;
    }
}
