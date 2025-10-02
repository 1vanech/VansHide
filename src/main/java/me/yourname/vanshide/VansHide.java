package me.yourname.vanshide;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class VansHide extends JavaPlugin implements Listener {

    public static final String BYPASS_PERMISSION = "vanshide.bypass";

    private VisibilityManager visibilityManager;

    @Override
    public void onEnable() {
        this.visibilityManager = new VisibilityManager(this);

        Bukkit.getPluginManager().registerEvents(this, this);
        Bukkit.getPluginManager().registerEvents(new InteractionBlocker(), this);

        // Apply initial visibility rules for all currently online players
        this.visibilityManager.updateForAllPlayers();
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player joinedPlayer = event.getPlayer();

        // Ensure the joined player sees according to their permission
        this.visibilityManager.updateVisibilityFor(joinedPlayer);

        // Also ensure existing players see the joined player according to their permissions
        for (Player online : Bukkit.getOnlinePlayers()) {
            if (online.equals(joinedPlayer)) {
                continue;
            }
            this.visibilityManager.updateVisibilityFor(online);
        }
    }
}


