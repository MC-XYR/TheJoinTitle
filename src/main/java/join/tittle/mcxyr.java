package join.tittle;

import java.io.File;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.List;

public final class mcxyr extends JavaPlugin implements Listener {

    private FileConfiguration config;
    private FileConfiguration langConfig;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        config = getConfig();
        loadLang();
        getServer().getPluginManager().registerEvents(this, this);
        getCommand("thejointitle").setExecutor(new TheJoinTitleCommand(this));
        getCommand("thejointitle").setTabCompleter(new TheJoinTitleTabCompleter());
    }

    @Override
    public void onDisable() {
    }

    public void loadLang() {
        String language = config.getString("language", "zh_cn");
        File langFile = new File(getDataFolder() + "/language", language + ".yml");
        if (!langFile.exists()) {
            saveResource("language/" + language + ".yml", false);
        }
        langConfig = YamlConfiguration.loadConfiguration(langFile);
    }

    public void reloadLang() {
        loadLang();
    }

    public void reloadPluginConfig() {
        reloadConfig();
        config = getConfig();
    }

    public String getLang(String key) {
        String msg = langConfig.getString(key, "§cMissing: " + key);
        return msg.replace('&', '§');
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        String playerName = event.getPlayer().getName();

        if (config.getBoolean("specific.enabled")) {
            List<String> specificList = config.getStringList("specific.players");
            for (String entry : specificList) {
                String[] split = entry.split(":", 2);
                if (split[0].equalsIgnoreCase(playerName)) {
                    event.setJoinMessage(split[1].replace('&', '§'));
                    return;
                }
            }
        }

        if (config.getBoolean("specificall.enabled")) {
            List<String> specificallList = config.getStringList("specificall.players");
            for (String name : specificallList) {
                if (name.equalsIgnoreCase(playerName)) {
                    event.setJoinMessage(config.getString("specificall.message").replace('&', '§'));
                    return;
                }
            }
        }

        if (config.getBoolean("all.enabled")) {
            event.setJoinMessage(config.getString("all.message").replace('&', '§'));
        }
    }
}