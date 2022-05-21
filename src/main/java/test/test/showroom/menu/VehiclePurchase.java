package test.test.showroom.menu;

import io.github.bananapuncher714.nbteditor.NBTEditor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import test.test.showroom.StandModule;
import test.test.utils.GUIHolder;
import test.test.utils.ItemBuilder;
import test.test.utils.Utils;

public class VehiclePurchase extends GUIHolder {


    public void open(Player player, Material material, int durability, int price, String vehicleName, String uuid) {
        this.inventory = Bukkit.createInventory(this, 9*3, Utils.color("&cPurchase Vehicle"));
        ItemStack item = new ItemBuilder(material)
                .setColoredName("&6" + vehicleName)
                .addLoreLine(Utils.color("&6Price: &e" + Utils.moneyFormat(price)))
                .setDurability((short) durability)
                .makeUnbreakable(true).toItemStack();
        this.inventory.setItem(13, item);

        ItemStack purchase = new ItemBuilder(Material.CONCRETE).setDurability((short) 5).setColoredName("&aPurchase Vehicle")
            .addLoreLine(Utils.color("&7Click here to confirm your purchase")).toItemStack();
        Utils.applyNBTTag(purchase, "price", price);
        Utils.applyNBTTag(purchase, "uuid", uuid);
        ItemStack cancel = new ItemBuilder(Material.CONCRETE).setDurability((short) 14).setColoredName("&cDecline")
                .addLoreLine(Utils.color("&7Click here to cancel your purchase.")).toItemStack();
        this.inventory.setItem(11, purchase);
        this.inventory.setItem(15, cancel);

        player.openInventory(this.inventory);
    }


    @Override
    public void onClick(InventoryClickEvent event) {
        if(event.getCurrentItem() == null) return;
        if(event.getCurrentItem().getType().equals(Material.AIR)) return;
        event.setCancelled(true);
        Player player = (Player) event.getWhoClicked();
        if(event.getClickedInventory().equals(this.inventory)) {
            ItemStack item = event.getCurrentItem();
            if(item.getType().equals(Material.CONCRETE) && item.getDurability() == 14) {
                player.closeInventory();
                return;
            }
            if(item.getType().equals(Material.CONCRETE) && item.getDurability() == 5) {
                int price = NBTEditor.getInt(item, "price");

                if(!(Utils.getMoney(player) >= price)) {
                    player.sendMessage(Utils.color("&cYou don't have enough money for this."));
                    return;
                }
                Utils.takeMoney(player, price);
                String uuid = NBTEditor.getString(item, "uuid");
                Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), "vehicle givecar "+player.getName()+" " + uuid);
                player.closeInventory();
            }
        }
    }
}
