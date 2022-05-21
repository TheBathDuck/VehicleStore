package test.test.showroom.commands;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import test.test.Test;
import test.test.showroom.StandModule;
import test.test.showroom.objects.PacketStand;
import test.test.utils.Utils;

import java.util.Locale;

public class ShowroomCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if(!(commandSender instanceof Player)) return false;
        Player player = (Player) commandSender;
        if(!player.hasPermission("showroom.admin")) {
            player.sendMessage(Utils.color("&cYou don't have permission to execute this command!"));
            return false;
        }

        if(args.length == 1 && args[0].equalsIgnoreCase("setlocation")) {
            Location location = player.getLocation();
            Test.getInstance().setRoomLcation(location);
            player.sendMessage(Utils.color("&7You set the location of the showroom to your location."));
            return false;
        }
        sendHelp(player);
        return false;
    }

    public void sendHelp(Player player) {
        player.sendMessage(Utils.color("&7&m-----------&7[ &c&LSHOWROOM &7]&7&m-----------"));
        player.sendMessage(Utils.color(""));
        player.sendMessage(Utils.color("&c/showroom setlocation &7- &fSet the location of the vehicles."));
        player.sendMessage(Utils.color(""));
        player.sendMessage(Utils.color("&7&m-----------&7[ &c&lSHOWROOM &7]&7&m-----------"));    }
}
