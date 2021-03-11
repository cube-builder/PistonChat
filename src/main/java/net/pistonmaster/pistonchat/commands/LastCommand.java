package net.pistonmaster.pistonchat.commands;

import net.pistonmaster.pistonchat.utils.CacheTool;
import net.pistonmaster.pistonchat.utils.CommonTool;
import net.pistonmaster.pistonchat.utils.IgnoreTool;
import net.pistonmaster.pistonchat.utils.LanguageTool;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LastCommand implements CommandExecutor, TabExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            Optional<Player> lastSentTo = CacheTool.getLastSentTo(player);
            Optional<Player> lastMessagedOf = CacheTool.getLastMessagedOf(player);

            if (lastSentTo.isPresent()) {
                if (IgnoreTool.isIgnored(player, lastSentTo.get())) {
                    player.sendMessage(CommonTool.getPrefix() + "This person blocked you!");
                } else if (IgnoreTool.isIgnored(lastSentTo.get(), player)) {
                    player.sendMessage(CommonTool.getPrefix() + "You block this person!");
                } else {
                    if (args.length > 0) {
                        CommonTool.sendWhisperTo(player, CommonTool.mergeArgs(args, 0), lastSentTo.get());
                    } else {
                        return false;
                    }
                }
            } else if (lastMessagedOf.isPresent()) {
                if (IgnoreTool.isIgnored(player, lastMessagedOf.get())) {
                    player.sendMessage(CommonTool.getPrefix() + "This person blocked you!");
                } else if (IgnoreTool.isIgnored(lastMessagedOf.get(), player)) {
                    player.sendMessage(CommonTool.getPrefix() + "You block this person!");
                } else {
                    if (args.length > 0) {
                        CommonTool.sendWhisperTo(player, CommonTool.mergeArgs(args, 0), lastMessagedOf.get());
                    } else {
                        return false;
                    }
                }
            } else {
                player.sendMessage(LanguageTool.getMessage("notonline"));
            }
        } else {
            sender.sendMessage(LanguageTool.getMessage("playeronly"));
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return new ArrayList<>();
    }
}
