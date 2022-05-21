package test.test.showroom.objects;

import net.minecraft.server.v1_12_R1.*;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_12_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import test.test.utils.Utils;

public class PacketStand {

    private Player player;
    private EntityArmorStand stand;


    public PacketStand(Player player, Location location, float yaw, float pitch) {
        this.player = player;
        WorldServer server = ((CraftWorld) player.getWorld()).getHandle();

        EntityArmorStand stand = new EntityArmorStand(server);
        stand.setLocation(location.getX(), location.getY(), location.getZ(), yaw,pitch);
        stand.setInvisible(true);

        PacketPlayOutSpawnEntityLiving packet = new PacketPlayOutSpawnEntityLiving(stand);
        ((CraftPlayer)player).getHandle().playerConnection.sendPacket(packet);
        this.stand = stand;

    }

    public void setModel(int durabilityId) {
        ItemStack item = new ItemStack(Material.DIAMOND_HOE);
        ItemMeta meta = item.getItemMeta();
        meta.setUnbreakable(true);
        item.setItemMeta(meta);
        item.setDurability((short) durabilityId);


        PacketPlayOutEntityEquipment equipment = new PacketPlayOutEntityEquipment(stand.getId(), EnumItemSlot.HEAD, CraftItemStack.asNMSCopy(item));
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(equipment);
    }

    public void destroy() {
        PacketPlayOutEntityDestroy packet = new PacketPlayOutEntityDestroy(stand.getId());
        ((CraftPlayer)player).getHandle().playerConnection.sendPacket(packet);
        this.stand = null;
        this.player = null;
    }

    public void setRider(Player player) {
        stand.getBukkitEntity().setPassenger(player);
    }

    public void setLocation(Location location, float yaw, float pitch) {
        this.stand.setLocation(location.getX(), location.getY(), location.getZ(), yaw, pitch);
        PacketPlayOutEntityTeleport packet = new PacketPlayOutEntityTeleport(this.stand);
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
    }


}
