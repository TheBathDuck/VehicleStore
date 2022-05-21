package test.test.showroom;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import test.test.Test;
import test.test.showroom.commands.ShowroomCommand;
import test.test.showroom.listeners.NPCListener;
import test.test.showroom.menu.VehiclePurchase;
import test.test.showroom.menu.VehicleShowMenu;
import test.test.showroom.menu.VehicleSubMenu;
import test.test.showroom.objects.PacketStand;

import java.util.HashMap;
import java.util.Locale;

public class StandModule {

    private static @Getter @Setter HashMap<Player, PacketStand> packetStands = new HashMap<>();
    private static @Getter VehicleSubMenu vehicleMenu;
    private static @Getter VehicleShowMenu vehicleShowMenu;
    private static @Getter VehiclePurchase vehiclePurchase;

    public StandModule(Test main) {
        vehicleMenu = new VehicleSubMenu();
        vehicleShowMenu = new VehicleShowMenu();
        vehiclePurchase = new VehiclePurchase();

        Bukkit.getPluginManager().registerEvents(new NPCListener(), main);
        main.getCommand("showroom").setExecutor(new ShowroomCommand());
    }

    public Location getShowroomLocation() {
        String world = Test.getInstance().getConfig().getString("showroom-location.world");
        int x = Test.getInstance().getConfig().getInt("showroom-location.x");
        int y = Test.getInstance().getConfig().getInt("showroom-location.y");
        int z = Test.getInstance().getConfig().getInt("showroom-location.z");
        int yaw = Test.getInstance().getConfig().getInt("showroom-location.yaw");
        Location loc = new Location(Bukkit.getWorld(world), x, y, z, yaw, 0);
        return loc;
    }

}
