package join.tittle;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import java.io.File;
import java.util.List;

public class TheJoinTitleCommand implements CommandExecutor {

    private final mcxyr plugin;

    public TheJoinTitleCommand(mcxyr plugin) {
        this.plugin = plugin;
    }

    @Override

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.hasPermission("thejointitle.use")) {
            sender.sendMessage(plugin.getLang("no_permission"));
            return true;
        }
        if (args.length == 0) {
            sender.sendMessage(plugin.getLang("command_usage"));
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "add":
                if (args.length < 3) {
                    sender.sendMessage(plugin.getLang("add_usage"));
                    return true;
                }
                String addName = args[1];
                StringBuilder addMsg = new StringBuilder();
                for (int i = 2; i < args.length; i++) {
                    addMsg.append(args[i]).append(" ");
                }
                String newEntry = addName + ":" + addMsg.toString().trim();
                List<String> specificList = plugin.getConfig().getStringList("specific.players");
                specificList.removeIf(entry -> entry.startsWith(addName + ":"));
                specificList.add(newEntry);
                plugin.getConfig().set("specific.players", specificList);
                plugin.getConfig().set("specific.enabled", true);
                plugin.saveConfig();
                sender.sendMessage(plugin.getLang("add_success").replace("{player}", addName));
                return true;

            case "remove":
                if (args.length < 2) {
                    sender.sendMessage(plugin.getLang("remove_usage"));
                    return true;
                }
                String removeName = args[1];
                List<String> removeList = plugin.getConfig().getStringList("specific.players");
                boolean removed = removeList.removeIf(entry -> entry.startsWith(removeName + ":"));
                if (removed) {
                    plugin.getConfig().set("specific.players", removeList);
                    plugin.saveConfig();
                    sender.sendMessage(plugin.getLang("remove_success").replace("{player}", removeName));
                } else {
                    sender.sendMessage(plugin.getLang("remove_not_found").replace("{player}", removeName));
                }
                return true;

            case "edit":
                if (args.length < 3) {
                    sender.sendMessage(plugin.getLang("edit_usage"));
                    return true;
                }
                String editName = args[1];
                StringBuilder editMsg = new StringBuilder();
                for (int i = 2; i < args.length; i++) {
                    editMsg.append(args[i]).append(" ");
                }
                String newMessage = editMsg.toString().trim();
                List<String> editList = plugin.getConfig().getStringList("specific.players");
                boolean edited = false;
                for (int i = 0; i < editList.size(); i++) {
                    String[] split = editList.get(i).split(":", 2);
                    if (split[0].equalsIgnoreCase(editName)) {
                        editList.set(i, editName + ":" + newMessage);
                        edited = true;
                        break;
                    }
                }
                if (edited) {
                    plugin.getConfig().set("specific.players", editList);
                    plugin.saveConfig();
                    sender.sendMessage(plugin.getLang("edit_success").replace("{player}", editName));
                } else {
                    sender.sendMessage(plugin.getLang("edit_not_found").replace("{player}", editName));
                }
                return true;

            case "toggle":
                if (args.length < 2) {
                    sender.sendMessage(plugin.getLang("toggle_usage"));
                    return true;
                }
                String type = args[1].toLowerCase();
                String toggleKey;
                switch (type) {
                    case "all":
                        toggleKey = "all.enabled";
                        break;
                    case "specificall":
                        toggleKey = "specificall.enabled";
                        break;
                    case "specific":
                        toggleKey = "specific.enabled";
                        break;
                    default:
                        sender.sendMessage(plugin.getLang("toggle_invalid"));
                        return true;
                }
                boolean current = plugin.getConfig().getBoolean(toggleKey);
                plugin.getConfig().set(toggleKey, !current);
                plugin.saveConfig();
                String status = !current ? "on" : "off";
                sender.sendMessage(plugin.getLang("toggle_success").replace("{type}", type).replace("{status}", status));
                return true;

            case "set":
                if (args.length < 4) {
                    sender.sendMessage(plugin.getLang("set_usage"));
                    return true;
                }
                String section = args[1].toLowerCase();
                String setKey = args[2];
                StringBuilder val = new StringBuilder();
                for (int i = 3; i < args.length; i++) {
                    val.append(args[i]).append(" ");
                }
                String setValue = val.toString().trim();

                switch (section) {
                    case "all":
                        plugin.getConfig().set("all." + setKey, setValue);
                        break;
                    case "specificall":
                        if (setKey.equals("players")) {
                            sender.sendMessage(plugin.getLang("set_use_add_remove"));
                            return true;
                        }
                        plugin.getConfig().set("specificall." + setKey, setValue);
                        break;
                    case "specific":
                        if (setKey.equals("players")) {
                            sender.sendMessage(plugin.getLang("set_use_add_remove"));
                            return true;
                        }
                        plugin.getConfig().set("specific." + setKey, setValue);
                        break;
                    default:
                        sender.sendMessage(plugin.getLang("set_invalid_section"));
                        return true;
                }
                plugin.saveConfig();
                plugin.reloadPluginConfig();
                sender.sendMessage(plugin.getLang("set_success").replace("{path}", section + "." + setKey).replace("{value}", setValue));
                return true;

            case "language":
                if (args.length < 2) {
                    sender.sendMessage(plugin.getLang("language_usage"));
                    return true;
                }
                String newLang = args[1];
                File langFile = new File(plugin.getDataFolder() + "/language", newLang + ".yml");
                if (!langFile.exists()) {
                    sender.sendMessage(plugin.getLang("language_not_found").replace("{lang}", newLang));
                    return true;
                }
                plugin.getConfig().set("language", newLang);
                plugin.saveConfig();
                plugin.reloadLang();
                sender.sendMessage(plugin.getLang("language_switched").replace("{lang}", newLang));
                return true;

            case "grant":
                if (!sender.hasPermission("thejointitle.admin")) {
                    sender.sendMessage(plugin.getLang("no_permission"));
                    return true;
                }
                if (args.length < 2) {
                    sender.sendMessage("§c用法: /thejointitle grant <玩家名>");
                    return true;
                }
                Player grantPlayer = Bukkit.getPlayer(args[1]);
                if (grantPlayer == null) {
                    sender.sendMessage("§c玩家不在线");
                    return true;
                }
                grantPlayer.addAttachment(plugin, "thejointitle.use", true);
                sender.sendMessage("§a已授予 " + args[1] + " 使用权限");
                return true;

            case "revoke":
                if (!sender.hasPermission("thejointitle.admin")) {
                    sender.sendMessage(plugin.getLang("no_permission"));
                    return true;
                }
                if (args.length < 2) {
                    sender.sendMessage("§c用法: /thejointitle revoke <玩家名>");
                    return true;
                }
                Player revokePlayer = Bukkit.getPlayer(args[1]);
                if (revokePlayer == null) {
                    sender.sendMessage("§c玩家不在线");
                    return true;
                }
                revokePlayer.addAttachment(plugin, "thejointitle.use", false);
                sender.sendMessage("§a已撤销 " + args[1] + " 的使用权限");
                return true;

            case "reload":
                plugin.reloadPluginConfig();
                plugin.reloadLang();
                sender.sendMessage(plugin.getLang("reload_success"));
                return true;


            case "about":
                sender.sendMessage("§6TheJoinTitle v1.3");
                sender.sendMessage("§e作者: MC_XYR");
                sender.sendMessage(plugin.getLang("about_translator"));
                return true;

            default:
                sender.sendMessage(plugin.getLang("unknown_command"));
                return true;
        }
    }
}