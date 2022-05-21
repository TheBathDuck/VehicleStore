package test.test.fuel.menus;

import com.mysql.jdbc.Util;
import io.github.bananapuncher714.nbteditor.NBTEditor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import test.test.utils.GUIHolder;
import test.test.utils.ItemBuilder;
import test.test.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class FuelConfirm extends GUIHolder {

    ItemFlag[] flags = {ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_ATTRIBUTES};

    int pricePerLiter = 20;

    public void open(Player player, int liters) {
        this.inventory = Bukkit.createInventory(this, 3*9, Utils.color("&6Benzine Menu"));

        ItemStack purchase = new ItemBuilder(Material.CONCRETE).setDurability((short) 5).setColoredName("&aAankoop bevestigen")
                .addLoreLine(Utils.color("&7Klik hier om je item te kopen."))
                .setNBT("liters", liters)
                .setNBT("cost", liters * pricePerLiter)
                .setItemFlag(flags)
                .toItemStack();
        ItemStack cancel = new ItemBuilder(Material.CONCRETE).setDurability((short) 14).setColoredName("&cAnnuleren")
                .addLoreLine(Utils.color("&7Klik hier om je aankopen te annuleren.")).toItemStack();
        this.inventory.setItem(11, purchase);
        this.inventory.setItem(13, decoyBenzine(liters, liters * pricePerLiter));
        this.inventory.setItem(15, cancel);

        player.openInventory(this.inventory);
    }

    @Override
    public void onClick(InventoryClickEvent event) {
        if(event.getCurrentItem() == null) return;
        if(event.getCurrentItem().getType().equals(Material.AIR)) return;
        Player player = (Player) event.getWhoClicked();
        if(event.getCurrentItem().getType().equals(Material.CONCRETE) && event.getCurrentItem().getDurability() == 14) {
            player.closeInventory();
            return;
        }
        event.setCancelled(true);
        if(!(event.getCurrentItem().getType().equals(Material.CONCRETE) && event.getCurrentItem().getDurability() == 5)) return;
        ItemStack item = event.getCurrentItem();


        int liters = NBTEditor.getInt(item, "liters");
        int cost = NBTEditor.getInt(item, "cost");

        if(!(Utils.getMoney(player) >= cost)) {
            player.sendMessage(Utils.color("&cJe hebt hier niet genoeg geld voor!"));
            return;
        }
        Utils.takeMoney(player, cost);
        player.sendMessage(Utils.color("&6Je hebt &e" + liters + "L Benzine &6gekocht voor &e€" + cost));
        player.getInventory().addItem(benzineItem(liters, liters));
        player.closeInventory();

    }

    public ItemStack benzineItem(int liter, int literold) {
        ItemStack is = new ItemBuilder(Material.DIAMOND_HOE, 1).setDurability((short) 58).setNBT("mtvehicles.benzineval", "" + literold).setNBT("mtvehicles.benzinesize", "" + liter).toItemStack();
        ItemMeta im = is.getItemMeta();
        List<String> itemlore = new ArrayList<>();
        itemlore.add(Utils.color("&8"));
        itemlore.add(Utils.color("&7Jerrycan &e" + literold + "&7/&e" + liter + " &7liter"));
        assert im != null;
        im.setLore(itemlore);
        im.setUnbreakable(true);
        im.setDisplayName(Utils.color("&6Jerrycan " + liter + "L"));
        is.setItemMeta(im);
        return is;
    }

    public ItemStack decoyBenzine(int liter, int price) {
        ItemStack item = new ItemBuilder(Material.DIAMOND_HOE)
                .makeUnbreakable(true)
                .setDurability((short) 58)
                .setColoredName(Utils.color("&6" + liter + " Liter"))
                .addLoreLine(Utils.color(""))
                .addLoreLine(Utils.color("&6Prijs: &e" + Utils.moneyFormat(price)))
                .addLoreLine(Utils.color(""))
                .addLoreLine(Utils.color("&7Linkermuis om dit item te kopen."))
                .setNBT("liters", liter)
                .setItemFlag(flags)
                .toItemStack();
        return item;
    }
}
