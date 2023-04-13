package me.xsenny_.votetime.commands;

import me.xsenny_.votetime.VoteTime;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DayVote implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player){
            Player p = (Player) sender;
            if (VoteTime.voteOn){
                if (VoteTime.playerWhoVoted.contains(p)){
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', VoteTime.plugin.getConfig().getString("voted-already")));
                }else{
                    VoteTime.playerWhoVoted.add(p);
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', VoteTime.plugin.getConfig().getString("dvoted")));
                    VoteTime.dvotes += 1;
                }
            }
        }

        return true;
    }
}
