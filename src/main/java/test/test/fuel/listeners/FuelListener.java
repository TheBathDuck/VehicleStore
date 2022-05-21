package test.test.fuel.listeners;

import net.citizensnpcs.api.event.NPCRightClickEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import test.test.fuel.FuelModule;

public class FuelListener implements Listener {

    @EventHandler
    public void onNPCClick(NPCRightClickEvent event) {
        if(event.getNPC() == null) return;
        if(event.getNPC().data().get("fuelNPC") == null) return;
        Player player = event.getClicker();
        FuelModule.getFuelMenu().open(player);
    }
}
