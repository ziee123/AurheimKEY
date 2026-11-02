package com.aurheim;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import java.util.List;
public class KeyItem {
    public static ItemStack create() {
        ItemStack item = new ItemStack(Material.TRIPWIRE_HOOK);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§6§lKunci Royal Vault");
        meta.setLore(List.of("§7Klik pintu Bedrock yang ada","§7OAK PLANK dibelakangnya"));
        meta.getPersistentDataContainer().set(new NamespacedKey("aurheimkey", "royal"), PersistentDataType.BYTE, (byte)1);
        item.setItemMeta(meta);
        return item;
    }
}
