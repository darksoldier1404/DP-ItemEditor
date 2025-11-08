package com.darksoldier1404.die.functions;

import com.darksoldier1404.dppc.utils.ColorUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

import static com.darksoldier1404.die.ItemEditor.*;

public class DPIEFunction {

    public static void setItemName(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) return;
        Player p = (Player) sender;
        ItemStack item = p.getInventory().getItemInMainHand();
        if (item == null || item.getType().isAir()) {
            p.sendMessage(plugin.getPrefix() + plugin.getLang().get("item_name_hold_item"));
            return;
        }
        ItemMeta meta = item.getItemMeta();
        if (meta == null) {
            p.sendMessage(plugin.getPrefix() + plugin.getLang().get("item_name_cannot_have_name"));
            return;
        }
        String name = ColorUtils.applyColor(String.join(" ", args));
        meta.setDisplayName(name);
        item.setItemMeta(meta);
        p.sendMessage(plugin.getPrefix() + plugin.getLang().getWithArgs("item_name_set", name));
    }

    public static void addItemLore(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) return;
        Player p = (Player) sender;
        ItemStack item = p.getInventory().getItemInMainHand();
        if (item == null || item.getType().isAir()) {
            p.sendMessage(plugin.getPrefix() + plugin.getLang().get("item_lore_hold_item"));
            return;
        }
        ItemMeta meta = item.getItemMeta();
        if (meta == null) {
            p.sendMessage(plugin.getPrefix() + plugin.getLang().get("item_lore_cannot_have_lore"));
            return;
        }
        String lore = ColorUtils.applyColor(String.join(" ", Arrays.copyOfRange(args, 1, args.length)));
        java.util.List<String> loreList = meta.hasLore() && meta.getLore() != null ? new java.util.ArrayList<>(meta.getLore()) : new java.util.ArrayList<>();
        loreList.add(lore);
        meta.setLore(loreList);
        item.setItemMeta(meta);
        p.sendMessage(plugin.getPrefix() + plugin.getLang().getWithArgs("item_lore_added", lore));
    }

    public static void setItemLore(CommandSender sender, String line, String[] args) {
        if (!(sender instanceof Player)) return;
        Player p = (Player) sender;
        ItemStack item = p.getInventory().getItemInMainHand();
        if (item == null || item.getType().isAir()) {
            p.sendMessage(plugin.getPrefix() + plugin.getLang().get("item_lore_hold_item_set"));
            return;
        }
        int lineNumber;
        try {
            lineNumber = Integer.parseInt(line) - 1;
        } catch (NumberFormatException e) {
            p.sendMessage(plugin.getPrefix() + plugin.getLang().get("item_lore_invalid_line"));
            return;
        }
        ItemMeta meta = item.getItemMeta();
        if (meta == null) {
            p.sendMessage(plugin.getPrefix() + plugin.getLang().get("item_lore_cannot_have_lore"));
            return;
        }
        String lore = ColorUtils.applyColor(String.join(" ", Arrays.copyOfRange(args, 2, args.length)));
        java.util.List<String> loreList = meta.hasLore() && meta.getLore() != null ? new java.util.ArrayList<>(meta.getLore()) : new java.util.ArrayList<>();
        if (lineNumber < 0 || lineNumber >= loreList.size()) {
            p.sendMessage(plugin.getPrefix() + plugin.getLang().get("item_lore_invalid_line_number"));
            return;
        }
        loreList.set(lineNumber, lore);
        meta.setLore(loreList);
        item.setItemMeta(meta);
        p.sendMessage(plugin.getPrefix() + plugin.getLang().getWithArgs("item_lore_set", String.valueOf(lineNumber + 1), lore));
    }

    public static void removeItemLore(CommandSender sender, String line) {
        if (!(sender instanceof Player)) return;
        Player p = (Player) sender;
        ItemStack item = p.getInventory().getItemInMainHand();
        if (item == null || item.getType().isAir()) {
            p.sendMessage(plugin.getPrefix() + plugin.getLang().get("item_lore_hold_item_remove"));
            return;
        }
        int lineNumber;
        try {
            lineNumber = Integer.parseInt(line) - 1;
        } catch (NumberFormatException e) {
            p.sendMessage(plugin.getPrefix() + plugin.getLang().get("item_lore_invalid_line"));
            return;
        }
        ItemMeta meta = item.getItemMeta();
        if (meta == null || !meta.hasLore() || meta.getLore() == null) {
            p.sendMessage(plugin.getPrefix() + plugin.getLang().get("item_lore_no_lore_remove"));
            return;
        }
        java.util.List<String> loreList = new java.util.ArrayList<>(meta.getLore());
        if (lineNumber < 0 || lineNumber >= loreList.size()) {
            p.sendMessage(plugin.getPrefix() + plugin.getLang().get("item_lore_invalid_line_number"));
            return;
        }
        loreList.remove(lineNumber);
        meta.setLore(loreList);
        item.setItemMeta(meta);
        p.sendMessage(plugin.getPrefix() + plugin.getLang().getWithArgs("item_lore_removed", String.valueOf(lineNumber + 1)));
    }

    public static void clearItemLore(CommandSender sender) {
        if (!(sender instanceof Player)) return;
        Player p = (Player) sender;
        ItemStack item = p.getInventory().getItemInMainHand();
        if (item == null || item.getType().isAir()) {
            p.sendMessage(plugin.getPrefix() + plugin.getLang().get("item_lore_hold_item_clear"));
            return;
        }
        ItemMeta meta = item.getItemMeta();
        if (meta == null || !meta.hasLore() || meta.getLore() == null || meta.getLore().isEmpty()) {
            p.sendMessage(plugin.getPrefix() + plugin.getLang().get("item_lore_no_lore_clear"));
            return;
        }
        meta.setLore(null);
        item.setItemMeta(meta);
        p.sendMessage(plugin.getPrefix() + plugin.getLang().get("item_lore_cleared"));
    }

    public static void setItemType(CommandSender sender, String type) {
        if (!(sender instanceof Player)) return;
        Player p = (Player) sender;
        ItemStack item = p.getInventory().getItemInMainHand();
        if (item == null || item.getType().isAir()) {
            p.sendMessage(plugin.getPrefix() + plugin.getLang().get("item_type_hold_item"));
            return;
        }
        try {
            item.setType(org.bukkit.Material.valueOf(type.toUpperCase()));
            p.sendMessage(plugin.getPrefix() + plugin.getLang().getWithArgs("item_type_set", type));
        } catch (IllegalArgumentException e) {
            p.sendMessage(plugin.getPrefix() + plugin.getLang().getWithArgs("item_type_invalid", type));
        }
    }

    public static void setCustomModelData(CommandSender sender, String id) {
        if (!(sender instanceof Player)) return;
        Player p = (Player) sender;
        ItemStack item = p.getInventory().getItemInMainHand();
        if (item == null || item.getType().isAir()) {
            p.sendMessage(plugin.getPrefix() + plugin.getLang().get("custom_model_data_hold_item"));
            return;
        }
        try {
            int data = Integer.parseInt(id);
            ItemMeta meta = item.getItemMeta();
            if (meta == null) {
                p.sendMessage(plugin.getPrefix() + plugin.getLang().get("custom_model_data_cannot_have"));
                return;
            }
            meta.setCustomModelData(data);
            item.setItemMeta(meta);
            p.sendMessage(plugin.getPrefix() + plugin.getLang().getWithArgs("custom_model_data_set", String.valueOf(data)));
        } catch (NumberFormatException e) {
            p.sendMessage(plugin.getPrefix() + plugin.getLang().get("custom_model_data_invalid"));
        }
    }

    public static void removeCustomModelData(CommandSender sender) {
        if (!(sender instanceof Player)) return;
        Player p = (Player) sender;
        ItemStack item = p.getInventory().getItemInMainHand();
        if (item == null || item.getType().isAir()) {
            p.sendMessage(plugin.getPrefix() + plugin.getLang().get("custom_model_data_hold_item_remove"));
            return;
        }
        ItemMeta meta = item.getItemMeta();
        if (meta == null || !meta.hasCustomModelData()) {
            p.sendMessage(plugin.getPrefix() + plugin.getLang().get("custom_model_data_no_data_remove"));
            return;
        }
        meta.setCustomModelData(null);
        item.setItemMeta(meta);
        p.sendMessage(plugin.getPrefix() + plugin.getLang().get("custom_model_data_removed"));
    }

    public static void addEnchantment(CommandSender sender, String enchantmentName, String level) {
        if (!(sender instanceof Player)) return;
        Player p = (Player) sender;
        ItemStack item = p.getInventory().getItemInMainHand();
        if (item == null || item.getType().isAir()) {
            p.sendMessage(plugin.getPrefix() + plugin.getLang().get("enchant_hold_item"));
            return;
        }
        ItemMeta meta = item.getItemMeta();
        if (meta == null) {
            p.sendMessage(plugin.getPrefix() + plugin.getLang().get("enchant_cannot_have"));
            return;
        }
        try {
            Enchantment enchantment = Enchantment.getByName(enchantmentName.toUpperCase());
            if (enchantment == null) {
                p.sendMessage(plugin.getPrefix() + plugin.getLang().getWithArgs("enchant_invalid", enchantmentName));
                return;
            }
            int enchantmentLevel = Integer.parseInt(level);
            meta.addEnchant(enchantment, enchantmentLevel, true);
            item.setItemMeta(meta);
            p.sendMessage(plugin.getPrefix() + plugin.getLang().getWithArgs("enchant_added", enchantmentName, String.valueOf(enchantmentLevel)));
        } catch (NumberFormatException e) {
            p.sendMessage(plugin.getPrefix() + plugin.getLang().getWithArgs("enchant_invalid_level", level));
        }
    }

    public static void removeEnchantment(CommandSender sender, String enchantmentName) {
        if (!(sender instanceof Player)) return;
        Player p = (Player) sender;
        ItemStack item = p.getInventory().getItemInMainHand();
        if (item == null || item.getType().isAir()) {
            p.sendMessage(plugin.getPrefix() + plugin.getLang().get("enchant_hold_item_remove"));
            return;
        }
        ItemMeta meta = item.getItemMeta();
        if (meta == null || !meta.hasEnchants()) {
            p.sendMessage(plugin.getPrefix() + plugin.getLang().get("enchant_no_enchants_remove"));
            return;
        }
        Enchantment enchantment = Enchantment.getByName(enchantmentName.toUpperCase());
        if (enchantment == null) {
            p.sendMessage(plugin.getPrefix() + plugin.getLang().getWithArgs("enchant_invalid", enchantmentName));
            return;
        }
        meta.removeEnchant(enchantment);
        item.setItemMeta(meta);
        p.sendMessage(plugin.getPrefix() + plugin.getLang().getWithArgs("enchant_removed", enchantmentName));
    }

    public static void addFlag(CommandSender sender, String flag) {
        if (!(sender instanceof Player)) return;
        Player p = (Player) sender;
        ItemStack item = p.getInventory().getItemInMainHand();
        if (item == null || item.getType().isAir()) {
            p.sendMessage(plugin.getPrefix() + plugin.getLang().get("flag_hold_item_add"));
            return;
        }
        ItemMeta meta = item.getItemMeta();
        if (meta == null) {
            p.sendMessage(plugin.getPrefix() + plugin.getLang().get("flag_cannot_have"));
            return;
        }
        try {
            ItemFlag itemFlag = ItemFlag.valueOf(flag.toUpperCase());
            if (meta.hasItemFlag(itemFlag)) {
                p.sendMessage(plugin.getPrefix() + plugin.getLang().getWithArgs("flag_already_has", flag));
                return;
            }
            meta.addItemFlags(itemFlag);
            item.setItemMeta(meta);
            p.sendMessage(plugin.getPrefix() + plugin.getLang().getWithArgs("flag_added", flag));
        } catch (IllegalArgumentException e) {
            p.sendMessage(plugin.getPrefix() + plugin.getLang().getWithArgs("flag_invalid", flag));
        }
    }

    public static boolean hasFlag(ItemStack item, ItemFlag flag) {
        if (item == null || item.hasItemMeta()) return false;
        ItemMeta meta = item.getItemMeta();
        return meta.hasItemFlag(flag);
    }

    public static void removeFlag(CommandSender sender, String flag) {
        if (!(sender instanceof Player)) return;
        Player p = (Player) sender;
        ItemStack item = p.getInventory().getItemInMainHand();
        if (item == null || item.getType().isAir()) {
            p.sendMessage(plugin.getPrefix() + plugin.getLang().get("flag_hold_item_remove"));
            return;
        }
        ItemMeta meta = item.getItemMeta();
        if (meta == null || meta.getItemFlags().isEmpty()) {
            p.sendMessage(plugin.getPrefix() + plugin.getLang().get("flag_no_flags_remove"));
            return;
        }
        try {
            ItemFlag itemFlag = ItemFlag.valueOf(flag.toUpperCase());
            if (!meta.hasItemFlag(itemFlag)) {
                p.sendMessage(plugin.getPrefix() + plugin.getLang().getWithArgs("flag_does_not_have", flag));
                return;
            }
            meta.removeItemFlags(itemFlag);
            item.setItemMeta(meta);
            p.sendMessage(plugin.getPrefix() + plugin.getLang().getWithArgs("flag_removed", flag));
        } catch (IllegalArgumentException e) {
            p.sendMessage(plugin.getPrefix() + plugin.getLang().getWithArgs("flag_invalid", flag));
        }
    }

    public static void setDurability(CommandSender sender, String durability) {
        if (!(sender instanceof Player)) return;
        Player p = (Player) sender;
        ItemStack item = p.getInventory().getItemInMainHand();
        if (item == null || item.getType().isAir()) {
            p.sendMessage(plugin.getPrefix() + plugin.getLang().get("durability_hold_item"));
            return;
        }
        try {
            int dur = Integer.parseInt(durability);
            if (dur < 0 || dur > item.getType().getMaxDurability()) {
                p.sendMessage(plugin.getPrefix() + plugin.getLang().getWithArgs("durability_invalid_value", String.valueOf(item.getType().getMaxDurability())));
                return;
            }
            item.setDurability((short) dur);
            p.sendMessage(plugin.getPrefix() + plugin.getLang().getWithArgs("durability_set", String.valueOf(dur)));
        } catch (NumberFormatException e) {
            p.sendMessage(plugin.getPrefix() + plugin.getLang().get("durability_invalid_number"));
        }
    }
}
