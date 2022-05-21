package test.test;

import lombok.Getter;
import lombok.Setter;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import test.test.fuel.FuelModule;
import test.test.showroom.StandModule;
import test.test.showroom.objects.PacketStand;
import test.test.utils.ConfigurationFile;
import test.test.utils.GUIHolder;

import java.io.File;

public final class Test extends JavaPlugin {

    private static @Getter Test instance;
    private static @Getter FileConfiguration vehiclesConfig;
    private static @Getter @Setter StandModule standModule;
    private static @Getter ConfigurationFile roomData;
    private static Economy econ = null;

    @Override
    public void onEnable() {
        saveDefaultConfig();

        setupEconomy();

        instance = this;

        File vehicleFile = new File(Bukkit.getPluginManager().getPlugin("MTVehicles").getDataFolder(), "vehicles.yml");
        vehiclesConfig = YamlConfiguration.loadConfiguration(vehicleFile);

        roomData = new ConfigurationFile(this, "roomdata.yml", true);
        roomData.saveConfig();

        GUIHolder.init(this);

        new FuelModule(this);

        standModule = new StandModule(this);

    }

    @Override
    public void onDisable() {

        for(PacketStand packetStand : StandModule.getPacketStands().values()) {
            packetStand.destroy();
        }

    }

    public void setRoomLcation(Location location) {
        roomData.getConfig().set("showroom-location.setup", "true");
        roomData.getConfig().set("showroom-location.world", location.getWorld());
        roomData.getConfig().set("showroom-location.x", location.getX());
        roomData.getConfig().set("showroom-location.y", location.getY());
        roomData.getConfig().set("showroom-location.z", location.getZ());
        roomData.getConfig().set("showroom-location.yaw", location.getYaw());
        roomData.saveConfig();

    }

    private void setupEconomy() {
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return;
        }
        econ = rsp.getProvider();
    }

    public Economy getEconomy() {
        return econ;
    }

}
