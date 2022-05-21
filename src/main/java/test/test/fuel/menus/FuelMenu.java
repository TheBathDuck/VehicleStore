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
import test.test.fuel.FuelModule;
import test.test.utils.GUIHolder;
import test.test.utils.ItemBuilder;
import test.test.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class FuelMenu extends GUIHolder {

    ItemFlag[] flags = {ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_ATTRIBUTES};

    public void open(Player player) {
        this.inventory = Bukkit.createInventory(this, 3*9, Utils.color("&6Monteur Menu"));


        this.inventory.setItem(13, icon());

        player.openInventory(this.inventory);
    }

    @Override
    public void onClick(InventoryClickEvent event) {
        if(event.getCurrentItem() == null) return;
        if(event.getCurrentItem().getType().equals(Material.AIR)) return;
        ItemStack item = event.getCurrentItem();
        event.setCancelled(true);
        Player player = (Player) event.getWhoClicked();

        player.sendMessage(Utils.color("&6Type het aantal &eliters&6 wat je wilt kopen in de chat, of type &eannuleren&6 om te annuleren."));
        FuelModule.getSettingFuel().add(player);
        player.closeInventory();
    }

    public ItemStack icon() {
        ItemStack item = new ItemBuilder(Material.DIAMOND_HOE)
                .makeUnbreakable(true)
                .setDurability((short) 58)
                .setColoredName(Utils.color("Koop Benzine"))
                .addLoreLine(Utils.color(""))
                .addLoreLine(Utils.color("&7Linkermuis om je hoeveelheid in te stellen."))
                .setItemFlag(flags)
                .toItemStack();
        return item;
    }
}
