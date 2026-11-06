package com.aurheim;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.persistence.PersistentDataType;

public class VaultOpener implements Listener {
    private final AurheimKEY plugin;
    public VaultOpener(AurheimKEY plugin) { this.plugin = plugin; }

    private boolean isRoyalKey(org.bukkit.inventory.ItemStack item) {
        if(item == null || !item.hasItemMeta()) return false;
        String key = plugin.getConfig().getString("key-item.pdc-key", "royal");
        return item.getItemMeta().getPersistentDataContainer().has(
            new NamespacedKey(plugin, key), PersistentDataType.BYTE);
    }

    @EventHandler
    public void onOpenVault(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        Block b = e.getClickedBlock();
        if(b == null) return;
        
        Material trigger = Material.valueOf(plugin.getConfig().getString("vault.trigger-block", "BEDROCK"));
        if(b.getType() != trigger) return;
        if(!isRoyalKey(p.getInventory().getItemInMainHand())) return;

        // Validasi vault asli
        if(plugin.getConfig().getBoolean("vault.validation.enabled")) {
            Material checkMat = Material.valueOf(plugin.getConfig().getString("vault.validation.check-block", "OAK_PLANKS"));
            int maxDist = plugin.getConfig().getInt("vault.validation.max-distance", 5);
            boolean isReal = false;
            for(int i=1; i<=maxDist; i++) {
                if(b.getRelative(e.getBlockFace(), i).getType() == checkMat) { isReal = true; break; }
            }
            if(!isReal) {
                p.sendMessage(plugin.getConfig().getString("vault.validation.message-not-real"));
                return;
            }
        }

        int rx = plugin.getConfig().getInt("vault.opening.radius-x", 15);
        int h = plugin.getConfig().getInt("vault.opening.height", 30);
        int rz = plugin.getConfig().getInt("vault.opening.radius-z", 1);
        Material remove = Material.valueOf(plugin.getConfig().getString("vault.opening.block-to-remove", "BEDROCK"));
        Material replace = Material.valueOf(plugin.getConfig().getString("vault.opening.replace-with", "AIR"));

        for(int x=-rx; x<=rx; x++) {
            for(int y=0; y<h; y++) {
                for(int z=-rz; z<=rz; z++) {
                    Block t = b.getRelative(x, y, z);
                    if(t.getType() == remove) t.setType(replace);
                }
            }
        }

        if(plugin.getConfig().getBoolean("key-item.consume-on-use", true)) {
            p.getInventory().getItemInMainHand().setAmount(p.getInventory().getItemInMainHand().getAmount()-1);
        }
        p.sendMessage(plugin.getConfig().getString("vault.effects.message-open"));
        p.playSound(p.getLocation(), 
            plugin.getConfig().getString("vault.effects.sound"), 
            (float)plugin.getConfig().getDouble("vault.effects.sound-volume"), 
            (float)plugin.getConfig().getDouble("vault.effects.sound-pitch"));
    }
}
