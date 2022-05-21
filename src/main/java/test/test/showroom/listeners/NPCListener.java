package test.test.showroom.listeners;

import net.citizensnpcs.api.event.NPCRightClickEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import test.test.Test;
import test.test.showroom.StandModule;
import test.test.utils.Utils;

public class NPCListener implements Listener {
    @EventHandler
    public void onNPCClick(NPCRightClickEvent event) {
        if(event.getNPC() == null) return;
        if(event.getNPC().data().get("showroomNPC") == null) return;
        Player player = event.getClicker();
//        if(!Test.getInstance().getConfig().getBoolean("showroom-location.setup")) {
//            player.sendMessage(Utils.color("&cThe location of the show room hasn't been set yet. Please let an administrator know!"));
//            return;
//        }
        StandModule.getVehicleMenu().open(player);
    }
}
