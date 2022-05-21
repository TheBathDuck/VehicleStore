package test.test.showroom.menu;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import test.test.Test;
import test.test.showroom.StandModule;
import test.test.utils.GUIHolder;
import test.test.utils.ItemBuilder;
import test.test.utils.Utils;

import java.util.Map;

public class VehicleSubMenu extends GUIHolder {

    ItemFlag[] flags = {ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_ATTRIBUTES};

    public void open(Player player) {
        this.inventory = Bukkit.createInventory(this, 6*9, Utils.color("&cShowroom Menu"));

        for(Map<?,?> vehicleData : Test.getVehiclesConfig().getMapList("voertuigen")) {
            int dura = (Integer) vehicleData.get("itemDamage");
            String name = (String) vehicleData.get("name");
            String item = (String) vehicleData.get("skinItem");
            this.inventory.addItem(createCatogorie(item, dura, name));
        }

        player.openInventory(this.inventory);

    }

    public ItemStack createCatogorie(String item, int durability, String name) {
        Material material = Material.valueOf(item);
        ItemStack car = new ItemBuilder(material)
                .makeUnbreakable(true)
                .setDurability((short) durability)
                .setColoredName(Utils.color("&6" + name))
                .addLoreLine(Utils.color("&7Klik om de voertuigen in deze"))
                .addLoreLine(Utils.color("&7catogorie te bekijken."))
                .setItemFlag(flags)
                .toItemStack();
        return car;
    }

    @Override
    public void onClick(InventoryClickEvent event) {
        if(event.getCurrentItem() == null) return;
        if(event.getCurrentItem().getType().equals(Material.AIR)) return;
        event.setCancelled(true);
        Player player = (Player) event.getWhoClicked();
        if(event.getClickedInventory().equals(this.inventory)) {
            int slot = event.getRawSlot();
            StandModule.getVehicleShowMenu().open(player, slot);
        }



    }
}
