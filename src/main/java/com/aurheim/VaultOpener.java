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
    private boolean isRoyalKey(org.bukkit.inventory.ItemStack item) {
        if(item == null || !item.hasItemMeta()) return false;
        return item.getItemMeta().getPersistentDataContainer().has(new NamespacedKey("aurheimkey", "royal"), PersistentDataType.BYTE);
    }
    @EventHandler
    public void onOpenVault(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        Block b = e.getClickedBlock();
        if(b == null || b.getType() != Material.BEDROCK) return;
        if(!isRoyalKey(p.getInventory().getItemInMainHand())) return;
        boolean isReal = false;
        for(int i=1; i<=5; i++) {
            if(b.getRelative(e.getBlockFace(), i).getType() == Material.OAK_PLANKS) { isReal = true; break; }
        }
        if(!isReal) { p.sendMessage("§cIni bukan pintu Vault asli."); return; }
        for(int x=-15; x<=15; x++) {
            for(int y=0; y<30; y++) {
                for(int z=-1; z<=1; z++) {
                    Block t = b.getRelative(x, y, z);
                    if(t.getType() == Material.BEDROCK) t.setType(Material.AIR);
                }
            }
        }
        p.getInventory().getItemInMainHand().setAmount(p.getInventory().getItemInMainHand().getAmount()-1);
        p.sendMessage("§6§lVault 30x30 TERBUKA!");
        p.playSound(p.getLocation(), "entity.generic.explode", 1f, 0.5f);
    }
}
