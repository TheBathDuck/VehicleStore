package test.test.fuel.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import test.test.fuel.FuelModule;
import test.test.utils.Utils;

public class ChatListener implements Listener {



    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        if(FuelModule.getSettingFuel().contains(player)) {
            event.setCancelled(true);
            FuelModule.getSettingFuel().remove(player);
            String msg = event.getMessage();
            if(msg.contains("annuleer") || msg.contains("annuleren") || msg.contains("stop")) {
                player.sendMessage(Utils.color("&cJe aankoop is geannuleerd."));
                return;
            }
            try {
                Integer.parseInt(msg);
            } catch (Exception e) {
                player.sendMessage(Utils.color("&cJe hebt geen geldig aantal opgegeven! geannuleerd."));
                return;
            }
            int liters = Integer.parseInt(msg);
            FuelModule.getFuelConfirm().open(player, liters);
        }
    }

}
