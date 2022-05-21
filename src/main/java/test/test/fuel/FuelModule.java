package test.test.fuel;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import test.test.Test;
import test.test.fuel.listeners.ChatListener;
import test.test.fuel.listeners.FuelListener;
import test.test.fuel.menus.FuelConfirm;
import test.test.fuel.menus.FuelMenu;

import java.util.ArrayList;

public class FuelModule {

    private static @Getter FuelMenu fuelMenu;
    private static @Getter FuelConfirm fuelConfirm;
    private static @Getter ArrayList<Player> settingFuel = new ArrayList<>();

    public FuelModule(Test main) {
        Bukkit.getPluginManager().registerEvents(new FuelListener(), main);
        Bukkit.getPluginManager().registerEvents(new ChatListener(), main);

        fuelMenu = new FuelMenu();
        fuelConfirm = new FuelConfirm();

    }

}
