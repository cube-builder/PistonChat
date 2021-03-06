package me.alexprogrammerde.pistonchat.commands;

import me.alexprogrammerde.pistonchat.utils.CommonTool;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class WhisperCommand implements CommandExecutor, TabExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (args.length > 0) {
                Player receiver = CommonTool.getPlayer(args[0]);

                if (receiver == null) {
                    sender.sendMessage("This player doesn't exist!");
                    return false;
                } else {
                    CommonTool.sendWhisperTo(player, CommonTool.mergeArgs(args, 1), receiver);
                    return true;
                }
            } else {
                return false;
            }
        } else {
            sender.sendMessage("You need to be a player to do this!");
            return false;
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            return null;
        } else {
            return new ArrayList<>();
        }
    }
}
