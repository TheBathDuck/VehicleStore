package test.test.utils;

import io.github.bananapuncher714.nbteditor.NBTEditor;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import test.test.Test;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

public class Utils {

    public static String color(String msg) {
        return ChatColor.translateAlternateColorCodes('&', msg);
    }

    public static void applyNBTTag(ItemStack itemStack, String key, Object value) {
        ItemStack is = NBTEditor.set(itemStack, value, key);
        ItemMeta itemMeta = is.getItemMeta();
        itemStack.setItemMeta(itemMeta);
    }

    public static String moneyFormat(int price) {
        Locale locale = new Locale("en", "US");
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);;
        String formatted = currencyFormatter.format(price);
        return formatted = formatted.replaceAll("$", "");
    }

    public static double getMoney(Player player) {
        return Test.getInstance().getEconomy().getBalance(player);
    }

    public static void takeMoney(Player player, double amount) {
        Test.getInstance().getEconomy().withdrawPlayer(player, amount);
    }

}
