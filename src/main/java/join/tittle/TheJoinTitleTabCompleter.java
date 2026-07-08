package join.tittle;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TheJoinTitleTabCompleter implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String alias, String[] args) {
        if (args.length == 1) {
            return filter(Arrays.asList("add", "remove", "edit", "toggle", "set", "language", "reload", "about", "grant", "revoke"), args[0]);
        }

        if (args.length == 2) {
            switch (args[0].toLowerCase()) {
                case "toggle":
                    return filter(Arrays.asList("all", "specificall", "specific"), args[1]);
                case "set":
                    return filter(Arrays.asList("all", "specificall", "specific"), args[1]);
                case "remove":
                case "edit":
                    return null;
                case "language":
                    return filter(Arrays.asList("zh_cn", "en_us"), args[1]);

                case "grant":
                case "revoke":
                    return null;
            }
        }

        if (args.length == 3 && args[0].equalsIgnoreCase("set")) {
            return filter(Arrays.asList("enabled", "message"), args[2]);
        }

        if (args.length == 4 && args[0].equalsIgnoreCase("set") && args[2].equalsIgnoreCase("enabled")) {
            return filter(Arrays.asList("on", "off"), args[3]);
        }

        return new ArrayList<>();
    }

    private List<String> filter(List<String> options, String input) {
        List<String> result = new ArrayList<>();
        for (String option : options) {
            if (option.toLowerCase().startsWith(input.toLowerCase())) {
                result.add(option);
            }
        }
        return result;
    }
}