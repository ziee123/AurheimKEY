package com.aurheim;
import org.bukkit.plugin.java.JavaPlugin;
public class AurheimKEY extends JavaPlugin {
    @Override
    public void onEnable() {
        getCommand("akey").setExecutor((sender, cmd, label, args) -> {
            if(args.length < 2) { sender.sendMessage("§c/akey give <player>"); return true; }
            var t = getServer().getPlayer(args[1]);
            if(t == null) { sender.sendMessage("§cOffline"); return true; }
            t.getInventory().addItem(KeyItem.create());
            sender.sendMessage("§aKunci dikasih ke " + t.getName());
            return true;
        });
        getServer().getPluginManager().registerEvents(new VaultOpener(), this);
    }
}
