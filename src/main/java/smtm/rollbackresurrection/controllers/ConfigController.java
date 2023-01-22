package smtm.rollbackresurrection.controllers;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import smtm.rollbackresurrection.RollbackResurrection;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class ConfigController {
    private static HashMap<String, ConfigController> configuracoes = new HashMap<>();
    private final File arquivo;
    private final FileConfiguration config;

    public ConfigController(Plugin plugin, String path) {
        this.arquivo = new File(plugin.getDataFolder(), path);
        if (!this.arquivo.exists()) {
            plugin.saveResource(path, false);
        }
        this.config = YamlConfiguration.loadConfiguration(this.arquivo);
        configuracoes.put(path, this);
    }

    public static Object getValue(String path, String value) {
        return configuracoes.get(path).config.get(value);
    }

    public static void setValue(String path, String value, Object newValue) {
        try {
            configuracoes.get(path).config.set(value, newValue);
            configuracoes.get(path).config.save(configuracoes.get(path).arquivo);
            configuracoes.get(path).config.load(configuracoes.get(path).arquivo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void carregarConfiguracoes(RollbackResurrection plugin) {
        new ConfigController(plugin, "config.yml");
        new ConfigController(plugin, "lang.yml");
        converterVersao();
    }

    public static void recarregarConfiguracoes(RollbackResurrection plugin) {
        configuracoes.clear();
        carregarConfiguracoes(plugin);
    }

    private static void converterVersao() {
        Integer versao = (Integer) getValue("config.yml", "version");
        Bukkit.getLogger().info("Loading config version: " + versao);
        if (versao == null || versao == 1) {
            convertTo2();
            versao = 2;
        }
    }

    private static void convertTo2() {
        Bukkit.getLogger().info("Converting config to version 2");
        ArrayList<String> times = new ArrayList<>();
        times.add("23:59");
        times.add("12:00");
        setValue("config.yml", "version", 2);
        setValue("config.yml", "backup.times", times);
    }
}
