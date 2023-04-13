package me.xsenny_.votetime;

import me.xsenny_.votetime.commands.DayVote;
import me.xsenny_.votetime.commands.NightVote;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public final class VoteTime extends JavaPlugin {

    public static boolean voteOn = false;
    public static ArrayList<Player> playerWhoVoted;
    public static Integer dvotes;
    public static Integer nvotes;
    public static VoteTime plugin;

    @Override
    public void onEnable() {
        // Plugin startup logic
        dvotes = nvotes = 0;
        plugin = this;
        getConfig().options().copyDefaults();
        saveDefaultConfig();
        Bukkit.getScheduler().runTaskTimer(this, () -> {
            if (voteOn){
                //se finiseaza votele
                playerWhoVoted = new ArrayList<>();
                if (nvotes > dvotes){
                    Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', getConfig().getString("resultsd").replace("{nvotes}", ""+nvotes).replace("{dvotes}", "" + dvotes)));
                    Bukkit.getWorld("world").setTime(13000);
                }else{
                    Bukkit.getWorld("world").setTime(1000);
                    Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', getConfig().getString("resultsn").replace("{nvotes}", ""+nvotes).replace("{dvotes}", ""+dvotes)));
                }
                voteOn = false;
                dvotes = nvotes = 0;
                Bukkit.getWorld("world").setWeatherDuration(0);
            }else{
                if (Bukkit.getWorld("world").getTime() > 13000 && Bukkit.getWorld("world").getTime() < 13400){
                    //se incep votarile
                    Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', getConfig().getString("start")));
                    TextComponent component = new TextComponent(TextComponent.fromLegacyText(ChatColor.translateAlternateColorCodes('&', getConfig().getString("voted"))));
                    component.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/vday"));
                    TextComponent component1 = new TextComponent(TextComponent.fromLegacyText(ChatColor.translateAlternateColorCodes('&', getConfig().getString("voten"))));
                    component1.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/vnight"));
                    for (Player p : Bukkit.getOnlinePlayers()){
                        p.spigot().sendMessage(component);
                        p.spigot().sendMessage(component1);
                    }
                    voteOn = true;
                }
            }
        }, 200L, 400L);

        getCommand("vday").setExecutor(new DayVote());
        getCommand("vnight").setExecutor(new NightVote());

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
