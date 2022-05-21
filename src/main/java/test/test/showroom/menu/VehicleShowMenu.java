package test.test.showroom.menu;

import io.github.bananapuncher714.nbteditor.NBTEditor;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import test.test.Test;
import test.test.showroom.StandModule;
import test.test.showroom.objects.PacketStand;
import test.test.utils.GUIHolder;
import test.test.utils.ItemBuilder;
import test.test.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class VehicleShowMenu extends GUIHolder {

    ItemFlag[] flags = {ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_ATTRIBUTES};

    public void open(Player player, int slot) {
        this.inventory = Bukkit.createInventory(this, 6*9, Utils.color("&cShowroom Menu"));
        drawBar();
        List<Map<?, ?>> vehicles = Test.getVehiclesConfig().getMapList("voertuigen");
        List<Map<?, ?>> skins = (List<Map<?,?>>) vehicles.get(slot).get("cars");
        ArrayList<ItemStack> allVehicles = new ArrayList<>();
        for(Map<?,?> skin : skins) {

            if(skin.get("price") == null) continue;
            int price = (Integer) skin.get("price");
            String vehicleName = (String) skin.get("name");
            String vehicleItem = (String) skin.get("SkinItem");
            String vehicleUUID = (String) skin.get("uuid");
            int vehicleDamage = (Integer) skin.get("itemDamage");

            ItemStack item = createCar(vehicleItem, vehicleDamage, vehicleName, price);
            Utils.applyNBTTag(item, "uuid", vehicleUUID);
            allVehicles.add(item);
            //this.inventory.addItem(createCar(vehicleItem, vehicleDamage, vehicleName));
        }

        for(ItemStack vehicle : allVehicles) {
            this.inventory.addItem(vehicle);
        }

        // Pages could be added in the feature with the loop system.

        player.openInventory(this.inventory);

    }

    public ItemStack createCar(String item, int durability, String name, int price) {
        Material material = Material.valueOf(item);
        ItemStack car = new ItemBuilder(material)
                .makeUnbreakable(true)
                .setDurability((short) durability)
                .setColoredName(Utils.color("&6" + name))
                .addLoreLine("")
                .addLoreLine(Utils.color("&6Prijs: &e" + Utils.moneyFormat(price)))
                .addLoreLine("")
                .addLoreLine(Utils.color("&7Rechter-muis om deze te kopen."))
                .addLoreLine(Utils.color("&7Linker-muis om in de showroom te zetten."))
                .setItemFlag(flags)
                .toItemStack();
        Utils.applyNBTTag(car, "price", price);
        return car;
    }

    public void drawBar() {
        ItemStack empty = new ItemBuilder(Material.STAINED_GLASS_PANE).setDurability((short) 0).setColoredName("&6").toItemStack();
        ItemStack goBack = new ItemBuilder(Material.WOOD_DOOR).setColoredName("&eMain menu.").toItemStack();
        ItemStack close = new ItemBuilder(Material.BARRIER).setColoredName("&cClose").toItemStack();

        int slot = 36;
        for(int i = 0; i<9; i++) {
            this.inventory.setItem(slot, empty);
            slot++;
        }
        this.inventory.setItem(47, goBack);
        this.inventory.setItem(51, close);

    }

    @Override
    public void onClick(InventoryClickEvent event) {
        if(event.getCurrentItem() == null) return;
        if(event.getCurrentItem().getType().equals(Material.AIR)) return;
        event.setCancelled(true);
        if(event.getCurrentItem().getType().equals(Material.STAINED_GLASS_PANE)) return;
        Player player = (Player) event.getWhoClicked();
        if(event.getCurrentItem().getType().equals(Material.WOOD_DOOR)) {
            StandModule.getVehicleMenu().open(player);
            return;
        }
        if(event.getCurrentItem().getType().equals(Material.BARRIER)) {
            player.closeInventory();
            return;
        }
        if(event.getClickedInventory().equals(this.inventory)) {
            ItemStack item = event.getCurrentItem();
            int dura = item.getDurability();
            if(event.getAction() == InventoryAction.PICKUP_HALF) {
                int price = NBTEditor.getInt(item, "price");
                String uuid = NBTEditor.getString(item, "uuid");
                StandModule.getVehiclePurchase().open(player, item.getType(), dura, price, ChatColor.stripColor(item.getItemMeta().getDisplayName()), uuid);
                return;
            }

            Location location = new Location(player.getWorld(), -1212.5, 4, 1114);
            if(StandModule.getPacketStands().get(player) == null) {
                PacketStand packetStand = new PacketStand(player, location, 0, 0);
                packetStand.setModel(dura);
                StandModule.getPacketStands().put(player, packetStand);
                return;
            }
            PacketStand packetStand = StandModule.getPacketStands().get(player);
            packetStand.setModel(dura);
        }
    }
}
